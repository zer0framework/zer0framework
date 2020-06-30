package br.com.zer0framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zer0framework.dao.UserDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.User;

@WebServlet(urlPatterns = {"/checkUserAvailability/*"})
public class CheckUserAvailabilityServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		final PrintWriter pw = resp.getWriter();
		resp.setContentType("application/json");

		try (Connection conn = ConnectionFactory.getConnection()) {

			final UserDAO userDAO = new UserDAO(conn);
			final List<User> userList = userDAO.findAll();

			String[] pathInfo = req.getPathInfo().split("/");
			String username = pathInfo[1];

			boolean available = true;
			for (User user : userList) {
				if (user.getUsername().contentEquals(username)) {
					available = false;
				}
			}
			if (available == true) {
				pw.print(true);
				pw.flush();
			} else {
				pw.print(false);
				pw.flush();
			}

		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pw.print(e.getMessage());
			pw.flush();
		}

	}

}