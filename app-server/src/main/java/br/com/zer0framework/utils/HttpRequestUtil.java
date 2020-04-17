package br.com.zer0framework.utils;

import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HttpRequestUtil {

    public static String getBody(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        body = stringBuilder.toString();
        return body;
    }

    public static String getFile(HttpServletRequest request) throws IOException, ServletException {

        // TODO pegar nome do arquivo
        Part part = request.getPart("file");

        InputStream imp = part.getInputStream();
        byte[] x = null;

        try {
            x = IOUtils.readFully(imp, imp.available(), true);

        }catch (IOException e){
            e.printStackTrace();
        }
        return new String(x, StandardCharsets.UTF_8);
    }
}
