package br.com.zer0framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zer0framework.dao.ContactDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.Contact;
import br.com.zer0framework.utils.HttpRequestUtil;
import br.com.zer0framework.utils.json.JSON;

@WebServlet(urlPatterns = { "/api/contacts", "/api/contacts/*" })
public class ContactServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final PrintWriter out = response.getWriter();
		response.setContentType("application/json");

		final String[] split = request.getPathInfo() == null ? null : request.getPathInfo().split("/");
		Integer id = null;
		if (split != null && split.length == 2) {
			try {
				id = Integer.parseInt(split[1]);
			} catch (Exception e) {
				id = null;
			}
		}

		if (request.getParameter("offset") != null) {
			doGetPagination(request, response, out);
		} else if (split == null) {
			doGetAll(response, out);
		} else if (id != null) {
			doGetById(response, out, id);
		} else {
			doGetByUserId(request, response, out);
		}
	}

	private void doGetAll(HttpServletResponse response, PrintWriter out) {
		try (Connection conn = ConnectionFactory.getConnection(true)) {
			final ContactDAO contactDAO = new ContactDAO(conn);

			final List<Contact> posts = contactDAO.findAll();
			final String json = JSON.jsonify(posts);

			out.print(json);
			out.flush();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print(e.getMessage());
			out.flush();
		}
	}

	private void doGetById(HttpServletResponse response, PrintWriter out, Integer id) {
		try (Connection conn = ConnectionFactory.getConnection(true)) {
			final ContactDAO contactDAO = new ContactDAO(conn);

			final Contact contact = contactDAO.findById(id);
			final String json = JSON.jsonify(contact);

			out.print(json);
			out.flush();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print(e.getMessage());
			out.flush();
		}
	}

	public void doGetByUserId(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try (Connection conn = ConnectionFactory.getConnection(true)) {
			final ContactDAO contactDAO = new ContactDAO(conn);

			final Integer id = Integer.valueOf(request.getParameter("userId"));
			final List<Contact> contact = contactDAO.findAllByUserId(id);
			final String json = JSON.jsonify(contact);

			out.print(json);
			out.flush();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print(e.getMessage());
			out.flush();
		}
	}

	public void doGetPagination(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try (Connection conn = ConnectionFactory.getConnection(true)) {
			final ContactDAO contactDAO = new ContactDAO(conn);

			final Integer id = Integer.valueOf(request.getParameter("userId"));
			final Integer offset = Integer.valueOf(request.getParameter("offset"));
			final List<Contact> contact = contactDAO.findPagination(id, offset);
			final String json = JSON.jsonify(contact);

			out.print(json);
			out.flush();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print(e.getMessage());
			out.flush();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");

		try (Connection conn = ConnectionFactory.getConnection(true)) {
			try {
				final ContactDAO contactDAO = new ContactDAO(conn);
				final String json = HttpRequestUtil.getBody(request);

				Map<String, Object> parsedMap = (Map<String, Object>) JSON.parseToMap(json);

				Contact contact = new Contact();

				contact.setUserId(Integer.valueOf((String) parsedMap.get("userId")));
				contact.setForename((String) parsedMap.get("forename"));
				contact.setSurname((String) parsedMap.get("surname"));
				contact.setTelefone((String) parsedMap.get("telefone"));

				contactDAO.insert(contact);
				response.setStatus(201);

			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
				conn.rollback();
			}

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse res) {
		try (Connection conn = ConnectionFactory.getConnection(true)) {
			final ContactDAO contactDAO = new ContactDAO(conn);
			final Map<String, String> map = (Map<String, String>) JSON.parseToMap(HttpRequestUtil.getBody(req));

			Contact contact = new Contact();

			contact.setId(Integer.valueOf((String) map.get("contactId")));
			contact.setForename((String) map.get("forename"));
			contact.setSurname((String) map.get("surname"));
			contact.setTelefone((String) map.get("telefone"));

			contactDAO.update(contact);
			res.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) {
		try (Connection conn = ConnectionFactory.getConnection(true)) {

			final String[] split = req.getPathInfo().split("/");
			final ContactDAO contactDAO = new ContactDAO(conn);

			contactDAO.delete(Integer.valueOf(split[1]));
			res.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}