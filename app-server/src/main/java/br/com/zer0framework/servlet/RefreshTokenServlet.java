package br.com.zer0framework.servlet;

import br.com.zer0framework.utils.security.SecurityUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/refreshToken")
public class RefreshTokenServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        String currentToken = req.getHeader("Authorization");

        Integer userId = null;

        try {
            SecurityUtil.validateToken(currentToken);
            userId = SecurityUtil.getUserIdFromToken(currentToken);
        }catch (Exception e){
            resp.setStatus(403);
        }
        pw.println("{\"newToken\":\""+SecurityUtil.encryptor(userId)+"\"}");
        resp.setStatus(200);
    }
}
