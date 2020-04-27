package br.com.zer0framework.servlet;

import br.com.zer0framework.dao.UserDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.User;
import br.com.zer0framework.utils.HttpRequestUtil;
import br.com.zer0framework.utils.json.JSON;
import br.com.zer0framework.utils.security.AuthenticationUtil;
import jdk.nashorn.api.scripting.ScriptUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {
		"/api/users",
		"/api/users/*"
})
public class UserServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter out = response.getWriter();
		response.setContentType("application/json");

		final String[] split = request.getPathInfo() == null ? null : request.getPathInfo().split("/");
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
			doGetAll(response, out);
		} else if (id != null) {
			doGetById(response, out, id);
		} else if (username != null) {
			doGetByUsername(response, out, username);
		}
	}

	private void doGetAll(HttpServletResponse response, PrintWriter out) {
		try(Connection conn = ConnectionFactory.getConnection(false)){
			final UserDAO userDAO = new UserDAO(conn);

			final List<User> users = userDAO.findAll();
			final String json = JSON.jsonify(users);

			out.print(json);
			out.flush();
		} catch (Exception e){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print(e.getMessage());
			out.flush();
		}
	}

	private void doGetById(HttpServletResponse response, PrintWriter out, Integer id) {
		try(Connection conn = ConnectionFactory.getConnection(false)){
			final UserDAO userDAO = new UserDAO(conn);

			final User user = userDAO.findById(id);
			final String json = JSON.jsonify(user);

			out.print(json);
			out.flush();
		} catch (Exception e){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print(e.getMessage());
			out.flush();
		}
	}

	private void doGetByUsername(HttpServletResponse response, PrintWriter out, String username) {
		try(Connection conn = ConnectionFactory.getConnection(false)){
			final UserDAO userDAO = new UserDAO(conn);

			final User user = userDAO.findByUsername(username);
			final String json = JSON.jsonify(user);

			out.print(json);
			out.flush();
		} catch (Exception e){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print(e.getMessage());
			out.flush();
		}
	}
	/**
	 * Alterar um usuario
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final PrintWriter out = response.getWriter();
		response.setContentType("application/json");

		try(Connection conn = ConnectionFactory.getConnection(true)){
			// TODO impedir que o mesmo ID seja adicionado
			try{
				final UserDAO userDAO = new UserDAO(conn);
				final String json = HttpRequestUtil.getBody(request);

				Map<String, Object> parsedMap = (Map<String, Object>) JSON.parseToMap(json);

				User user = new User();

				String plainPassword = (String) parsedMap.get("password");
				String hashedPassword = AuthenticationUtil.generatePasswordHash(plainPassword);

				user.setPassword(hashedPassword);

				user.setPersonId( Integer.valueOf( (String) parsedMap.get("personId")));

				user.setUsername((String) parsedMap.get("username"));

				userDAO.insert(user);
				response.setStatus(201);

			} catch (Exception e){
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
				conn.rollback();
			}

		} catch (Exception e){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();		
		}
	}
	/**
	 * Excluir usuario
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		final PrintWriter out = response.getWriter();
		Integer id = null;

		try {
			String[] x = request.getRequestURI().split("/");
			try{
			id = Integer.valueOf(x[3]);
			}catch (IndexOutOfBoundsException e){
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.print("Id must be informed");
			}

			try(Connection conn = ConnectionFactory.getConnection(false)) {
				try{
					final UserDAO userDAO = new UserDAO(conn);

					if (userDAO.deleteById(id)) {
						conn.commit();
						response.setStatus(HttpServletResponse.SC_OK);	
					} else {
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);					
					}

				} catch (Exception e) {
					conn.rollback();
					throw e;
				}
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

	//TODO doPut
}