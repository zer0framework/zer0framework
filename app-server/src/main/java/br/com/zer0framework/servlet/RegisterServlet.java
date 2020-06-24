package br.com.zer0framework.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zer0framework.dao.PersonDAO;
import br.com.zer0framework.dao.UserDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.Person;
import br.com.zer0framework.model.User;
import br.com.zer0framework.utils.HttpRequestUtil;
import br.com.zer0framework.utils.json.JSON;
import br.com.zer0framework.utils.security.AuthenticationUtil;

@WebServlet(urlPatterns = { "/register/*" })
public class RegisterServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");

		try (Connection conn = ConnectionFactory.getConnection(true)) {
			try {

				final String[] split = request.getPathInfo() == null ? null : request.getPathInfo().split("/");

				final String json = HttpRequestUtil.getBody(request);
				Map<String, Object> parsedMap = (Map<String, Object>) JSON.parseToMap(json);

				if (split[1].equalsIgnoreCase("Person")) {

					PersonDAO personDAO = new PersonDAO(conn);

					Person person = new Person();

					person.setName((String) parsedMap.get("name"));

					person.setBirthdate(new SimpleDateFormat("yyyy/MM/dd").parse((String) parsedMap.get("birthdate")));

					person.setJob((String) parsedMap.get("job"));

					personDAO.insert(person);
				} else if (split[1].equalsIgnoreCase("User")) {
					User user = new User();
					String plainPassword = (String) parsedMap.get("password");
					String hashedPassword = AuthenticationUtil.generatePasswordHash(plainPassword);

					user.setPassword(hashedPassword);

					user.setPersonId(Integer.valueOf((String) parsedMap.get("personId")));

					user.setUsername((String) parsedMap.get("username"));

					user.setEmail((String) parsedMap.get("email"));

					UserDAO userDAO = new UserDAO(conn);
					try {
						userDAO.insert(user);
					} catch (Exception e) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						throw new IllegalArgumentException("person id ja existente");
					}
				}

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

}