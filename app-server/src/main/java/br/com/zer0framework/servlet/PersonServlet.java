package br.com.zer0framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zer0framework.dao.PersonDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.Person;
import br.com.zer0framework.utils.HttpRequestUtil;
import br.com.zer0framework.utils.json.JSON;

@WebServlet(urlPatterns = { "/api/person", "/api/person/*" })
public class PersonServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		final PrintWriter pw = resp.getWriter();
		resp.setContentType("application/json");

		final String[] split = req.getPathInfo() == null ? null : req.getPathInfo().split("/");
		Integer id = null;
		String username = null;
		if (split != null && split.length == 2) {
			try {
				id = Integer.parseInt(split[1]);
			} catch (Exception e) {
				id = null;
			}
			if (id == null) {
				username = split[1];
			}
		}

		if (split == null) {
			doGetAll(resp, pw);
		} else if (id != null) {
			doGetById(resp, pw, id);
		} else if (username != null) {
			doGetByUsername(resp, pw, username);
		}
	}

	public void doGetById(HttpServletResponse resp, PrintWriter pw, Integer id) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			final PersonDAO personDAO = new PersonDAO(conn);

			final Person person = personDAO.findById(id);
			final String json = JSON.jsonify(person);

			pw.print(json);
			pw.flush();
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pw.print(e.getMessage());
			pw.flush();
		}

	}

	public void doGetAll(HttpServletResponse resp, PrintWriter pw) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			final PersonDAO personDAO = new PersonDAO(conn);

			final List<Person> personList = personDAO.findAll();
			final String json = JSON.jsonify(personList);

			pw.print(json);
			pw.flush();

		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pw.print(e.getMessage());
			pw.flush();
		}
	}

	public void doGetByUsername(HttpServletResponse resp, PrintWriter pw, String username) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			final PersonDAO personDAO = new PersonDAO(conn);

			final Person person = personDAO.findByUsername(username);
			final String json = JSON.jsonify(person);

			pw.print(json);
			pw.flush();
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pw.print(e.getMessage());
			pw.flush();
		}
	}

	// TODO Implement manager Id.
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		final PrintWriter pw = resp.getWriter();
		resp.setContentType("application/json");

		try (Connection conn = ConnectionFactory.getConnection()) {
			final PersonDAO personDAO = new PersonDAO(conn);
			final String json = HttpRequestUtil.getBody(req);

			Map<String, Object> map = (Map<String, Object>) JSON.parseToMap(json);

			Integer i;
			try {
				i = Integer.valueOf((String) map.get("managerPersonId"));
			} catch (NullPointerException | NumberFormatException ex) {
				i = null;
			}
			personDAO.insert(new Person(null, (String) map.get("name"),
					new SimpleDateFormat("yyyy/MM/dd").parse((String) map.get("birthdate")), (String) map.get("job"), i,
					null));
			pw.print(json);
			pw.flush();
			resp.setStatus(HttpServletResponse.SC_CREATED);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) {
		try (Connection connection = ConnectionFactory.getConnection(true)) {
			final PersonDAO personDAO = new PersonDAO(connection);
			final Map<String, String> map = (Map<String, String>) JSON.parseToMap(HttpRequestUtil.getBody(req));

			Person person = new Person();
			person.setName(map.get("name"));
			person.setId(Integer.valueOf(map.get("id")));
			person.setBirthdate(new SimpleDateFormat("yyyy/MM/dd").parse(map.get("birthdate")));
			person.setJob(map.get("job"));

			personDAO.update(person);

			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) {
		try (Connection conn = ConnectionFactory.getConnection(true)) {
			final PersonDAO personDAO = new PersonDAO(conn);

			final Integer id = Integer.valueOf(req.getParameter("id"));
			personDAO.delete(id);
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
