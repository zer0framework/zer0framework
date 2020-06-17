package br.com.zer0framework.servlet;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zer0framework.dao.UserDAO;
import br.com.zer0framework.email.EmailService;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.User;
import br.com.zer0framework.utils.HttpRequestUtil;
import br.com.zer0framework.utils.json.JSON;

@WebServlet("/resetPasswordRequest")
public class ResetPasswordRequestServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> map = (Map<String, String>) JSON.parseToMap(HttpRequestUtil.getBody(request));

			String email = map.get("email");

			Connection conn = ConnectionFactory.getConnection();
			final UserDAO userDao = new UserDAO(conn);
			final User user = userDao.findByEmail(email);

			String token = String.valueOf(user.getId());

			EmailService.sendEmail("Password Reset: ", email, token);

		} catch (Exception e) {
			response.setStatus(500);
		}
		response.setStatus(200);
	}
}
