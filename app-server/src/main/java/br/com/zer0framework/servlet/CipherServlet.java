package br.com.zer0framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.utils.HttpRequestUtil;
import br.com.zer0framework.utils.json.JSON;
import br.com.zer0framework.utils.security.SecurityUtil;

@WebServlet(urlPatterns = "/cipher")
public class CipherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CipherServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		final PrintWriter out = new PrintWriter(response.getOutputStream());

		Map<String, String> map = (Map<String, String>) JSON.parseToMap(HttpRequestUtil.getBody(request));

		try (Connection conn = ConnectionFactory.getConnection()) {
			String mode = ((String) map.get("mode"));
			String cipher = ((String) map.get("cipher"));

			if (mode.equals("encryptor")) {
				out.print(SecurityUtil.encryptor(cipher));
				out.flush();
			} else if (mode.equals("decryptor")) {
				out.print(SecurityUtil.decryptor(cipher));
				out.flush();
			}
		} catch (Exception ex) {
			new ServletException(ex);
		}
	}
}