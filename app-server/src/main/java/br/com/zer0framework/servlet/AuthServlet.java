package br.com.zer0framework.servlet;

import br.com.zer0framework.dao.UserDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.User;
import br.com.zer0framework.utils.HttpRequestUtil;
import br.com.zer0framework.utils.json.JSON;
import br.com.zer0framework.utils.security.AuthenticationUtil;
import br.com.zer0framework.utils.security.SecurityUtil;
import br.com.zer0framework.utils.HttpHeaders;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(urlPatterns = "/auth")
public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AuthServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		final PrintWriter out = new PrintWriter(response.getOutputStream());

		 Map<String, String> map = (Map<String, String>) JSON.parseToMap(HttpRequestUtil.getBody(request));

		try (Connection conn = ConnectionFactory.getConnection()) {
			try {
				final UserDAO userDao = new UserDAO(conn);
				final User user = userDao.findByUsername(map.get("username"));

				if (user != null) {
					if (AuthenticationUtil.validatePassword(map.get("password"), user.getPassword())) {

						final String encrypted = SecurityUtil.encryptor(user.getId(), user.getRole());
						response.setStatus(HttpServletResponse.SC_OK);
						response.setHeader(HttpHeaders.CONTENT_TYPE, "applicati2on/json");
						out.print("{ \"token\":\"" + encrypted + "\" }");
						out.flush();
					} else {
						writeUsernamePasswordInvalid(response, out);
					}
				} else {
					writeUsernamePasswordInvalid(response, out);
				}
			} catch (Exception ex) {
				new ServletException(ex);
			}
		} catch (Exception ex) {
			new ServletException(ex);
		}
	}

	protected void writeUsernamePasswordInvalid(HttpServletResponse response, PrintWriter out) throws SQLException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		out.print("{\"messege\":\"Username and/or Password Invalid!\"}");
		out.flush();
	}
}