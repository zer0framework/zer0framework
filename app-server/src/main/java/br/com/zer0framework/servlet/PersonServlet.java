package br.com.zer0framework.servlet;

import br.com.zer0framework.dao.PersonDAO;
import br.com.zer0framework.jdbc.ConnectionFactory;
import br.com.zer0framework.model.Person;
import br.com.zer0framework.utils.HttpRequestUtil;
import br.com.zer0framework.utils.json.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {
        "/api/person",
        "/api/person/*"
})
public class PersonServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final PrintWriter pw = resp.getWriter();
        resp.setContentType("application/json");

        final String[] split = req.getPathInfo() == null ? null : req.getPathInfo().split("/");
        Integer id = null;
        String username = null;
        if (split != null && split.length == 2) {
            try {
                id = Integer.parseInt(split[1]);
            } catch (Exception e) {
                id = null;
            }
            if (id == null) {
                username = split[1];
            }
        }

        if (split == null) {
            doGetAll(resp, pw);
        } else if (id != null) {
            doGetById(resp, pw, id);
        } else if (username != null) {
            doGetByUsername(resp, pw, username);
        }
    }

    public void doGetById(HttpServletResponse resp, PrintWriter pw, Integer id){
       try(Connection conn = ConnectionFactory.getConnection()) {
           final PersonDAO personDAO = new PersonDAO(conn);

           final Person person = personDAO.findById(id);
           final String json = JSON.jsonify(person);

           pw.print(json);
           pw.flush();
       }catch (Exception e){
           resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
           pw.print(e.getMessage());
           pw.flush();
       }

    }
    public void doGetAll(HttpServletResponse resp, PrintWriter pw){
        try(Connection conn = ConnectionFactory.getConnection()){
            final PersonDAO personDAO = new PersonDAO(conn);

            final List<Person> personList = personDAO.findAll();
            final String json = JSON.jsonify(personList);

            pw.print(json);
            pw.flush();

        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            pw.print(e.getMessage());
            pw.flush();
        }
    }

    public void doGetByUsername(HttpServletResponse resp, PrintWriter pw, String username){
        try(Connection conn = ConnectionFactory.getConnection()){
            final PersonDAO personDAO = new PersonDAO(conn);


            final Person person = personDAO.findByUsername(username);
            final String json = JSON.jsonify(person);

            pw.print(json);
            pw.flush();
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            pw.print(e.getMessage());
            pw.flush();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");

        try(Connection conn = ConnectionFactory.getConnection()) {
                final PersonDAO personDAO = new PersonDAO(conn);
                final String json = HttpRequestUtil.getBody(req);

                Map<String, Object> map = JSON.parseToMap(json);

                Integer i;
                try {
                     i = Integer.valueOf((String) map.get("managerPersonId"));
                }catch (Exception e){
                    i=null;
                }
                personDAO.insert(new Person(
                        null,
                        (String) map.get("name"),
                         new SimpleDateFormat("yyyy/MM/dd").parse( (String) map.get("birthdate")),
                        // se alguem souber um jeito mais simples de fazer isso, vá em frente
                        (String) map.get("job"),
                        i,
                        null)
                );
                resp.setStatus(201);
            }catch (Exception e){
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        }
    // TODO doPost, doPut, doDelete
}
