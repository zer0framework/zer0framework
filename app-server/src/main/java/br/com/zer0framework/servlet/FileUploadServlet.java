package br.com.zer0framework.servlet;

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

@WebServlet("/api/fileUpload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class FileUploadServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String csv = HttpRequestUtil.getFile(request);

        //TODO verificar se arquivo com esse nome ja existe, caso existir, perguntar se deseja sobrescrever

        try (PrintWriter writer = new PrintWriter(new File("C:\\zeroRepository\\test.csv"))) {

            writer.write(csv);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }
}
