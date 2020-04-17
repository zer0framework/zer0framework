package br.com.zer0framework.servlet;

import br.com.zer0framework.dao.PostDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.Post;
import br.com.zer0framework.utils.json.JSON;

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

@WebServlet(urlPatterns = {
		"/api/posts",
		"/api/posts/*"
})
public class PostServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
	}

	public void destroy() {
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter out = response.getWriter();
		response.setContentType("application/json");

		final String[] split = request.getPathInfo() == null ? null : request.getPathInfo().split("/");
		Integer id = null;
		String postname = null;
		if (split != null && split.length == 2) {
			try {
				id = Integer.parseInt(split[1]);
			} catch (Exception e) {
				id = null;
			}
			if (id == null) {
				postname = split[1];
			}
		}

		if (split == null) {
			doGetAll(response, out);
		} else if (id != null) {
			doGetById(response, out, id);
		}
	}

	private void doGetAll(HttpServletResponse response, PrintWriter out) {
		try(Connection conn = ConnectionFactory.getConnection(false)){
			final PostDAO postDAO = new PostDAO(conn);

			final List<Post> posts = postDAO.findAll();
			final String json = JSON.jsonify(posts);

			out.print(json);
			out.flush();
		} catch (Exception e){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print(e.getMessage());
			out.flush();
		}
	}

	private void doGetById(HttpServletResponse response, PrintWriter out, Integer id) {
		try (Connection conn = ConnectionFactory.getConnection(false)) {
			final PostDAO postDAO = new PostDAO(conn);

			final Post post = postDAO.findById(id);
			final String json = JSON.jsonify(post);

			out.print(json);
			out.flush();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print(e.getMessage());
			out.flush();
		}
	}

	//TODO doPost, doPut, doDelete
}