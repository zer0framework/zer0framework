package br.com.zer0framework.servlet;

import br.com.zer0framework.dao.FileDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.utils.FileUtil;
import br.com.zer0framework.utils.HttpRequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.regex.Pattern;

@WebServlet("/api/fileUpload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024
		* 50)
public class FileUploadServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String[] vect = HttpRequestUtil.getFile(request);
		final String csv = vect[0];
		final String csvName = vect[1];

		final FileDAO fileDAO = new FileDAO(ConnectionFactory.getConnection());

		try (PrintWriter writer = new PrintWriter(new File("C:\\zeroRepository\\" + csvName))) {
			try {
				fileDAO.insert(new br.com.zer0framework.model.File(csvName + ":" + FileUtil.stringRandomizer(3),
						csvName, csvName.split(Pattern.quote("."))[1]));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			writer.write(csv);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
