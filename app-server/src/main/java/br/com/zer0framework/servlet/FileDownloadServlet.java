package br.com.zer0framework.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/api/download")
public class FileDownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/OCTET-STREAM");
        String fileName = req.getParameter("fileName");

        resp.setHeader("Content-disposition", "attachment; filename="+fileName);

        PrintWriter pw = resp.getWriter();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\zeroRepository\\"+ fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                pw.println(line);
            }
        }catch (Exception e){
            resp.setStatus(500);
        }
        resp.setStatus(200);
    }
}
