package br.com.zer0framework.servlet;
import br.com.zer0framework.dao.UserDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.utils.HttpRequestUtil;
import br.com.zer0framework.utils.json.JSON;
import br.com.zer0framework.utils.security.AuthenticationUtil;
import br.com.zer0framework.utils.security.SecurityUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Map;


@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter("key");

        try {
            SecurityUtil.validateResetPasswordKey(key);
        }catch (Exception e){
            response.setStatus(401);
            return;
        }
        String userEmail = SecurityUtil.getEmailFromResetPasswordKey(key);

        Map<String, String> map = (Map<String, String>) JSON.parseToMap(HttpRequestUtil.getBody(request));

        UserDAO userDAO = new UserDAO(ConnectionFactory.getConnection());

        try {
            userDAO.updatePassword(AuthenticationUtil.generatePasswordHash(map.get("password")),userEmail);
        } catch (SQLException e) {
            response.setStatus(500);
        } catch (NoSuchAlgorithmException e) {
            response.setStatus(500);
        } catch (InvalidKeySpecException e) {
            response.setStatus(500);
        }
        response.setStatus(200);
    }
}
