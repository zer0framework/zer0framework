package br.com.zer0framework.servlet;

import br.com.zer0framework.email.EmailSender;
import br.com.zer0framework.utils.HttpRequestUtil;
import br.com.zer0framework.utils.json.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/resetPasswordRequest")
public class ResetPasswordRequestServlet extends HttpServlet {
   
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
     try {
        Map<String, String> map = (Map<String, String>) JSON.parseToMap(HttpRequestUtil.getBody(request));

        String email = map.get("email");

        EmailSender.sendResetPasswordEmail(email);

        response.setStatus(200);
     }catch (Exception e){
         response.setStatus(500);
     }
    }
}
