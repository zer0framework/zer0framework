package br.com.zer0framework.servlet;

import br.com.zer0framework.dao.UserDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.User;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter out = new PrintWriter(response.getOutputStream());

		final String username = request.getParameter("username");
		final String password = request.getParameter("password");

		try (Connection conn = ConnectionFactory.getConnection()) {
			try {
				final UserDAO userDao = new UserDAO(conn);
				final User user = userDao.findByUsername(username);

				if (user != null) {
					if (AuthenticationUtil.validatePassword(password, user.getPassword())) {

						final String encrypted = SecurityUtil.encryptor(String.valueOf(user.getId()));
						response.setStatus(HttpServletResponse.SC_OK);
						response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
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
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		out.print("Username and/or Password Invalid!");
		out.flush();
	}
}