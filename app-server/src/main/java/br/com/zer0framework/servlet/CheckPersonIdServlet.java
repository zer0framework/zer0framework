package br.com.zer0framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zer0framework.dao.PersonDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.Person;

@WebServlet(urlPatterns = { "/checkPersonId/*"})
public class CheckPersonIdServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		final PrintWriter pw = resp.getWriter();
		resp.setContentType("application/json");

		final String[] split = req.getPathInfo() == null ? null : req.getPathInfo().split("/");
		try (Connection conn = ConnectionFactory.getConnection()) {
			PersonDAO pd = new PersonDAO(conn);
			Person person = pd.findByUsername(split[1]);
			pw.print(person.getId());
			pw.flush();
		} catch (SQLException e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pw.print(e.getMessage());
			pw.flush();
		} 
	}
}
