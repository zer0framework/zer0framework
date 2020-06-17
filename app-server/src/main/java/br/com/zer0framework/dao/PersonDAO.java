package br.com.zer0framework.dao;

import br.com.zer0framework.model.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    private Connection connection;

    public PersonDAO(Connection conn) {
        this.connection = conn;
    }

    public List<Person> findAll() throws SQLException {
        return (List<Person>) getDataFromResultSet(connection.prepareStatement("SELECT * FROM person "));
    }

    public Person findById(Integer id) throws SQLException {
        Person result = null;

        final String sql = "select * from person where cd_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            final List<Person> personFromResultSet = (List<Person>) getDataFromResultSet(ps);
            if (!personFromResultSet.isEmpty()) {
                final Person obj = personFromResultSet.get(0);
                if (obj != null) {
                    result = obj;
                }
            }
        }

        return result;
    }

    public Person findByUsername(String username) throws SQLException {
        Person result = null;

        final String sql = "select * from person where nm_person = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            List<Person> usersFromResultSet = (List<Person>) getDataFromResultSet(ps);
            if (!usersFromResultSet.isEmpty()) {
                final Person obj = usersFromResultSet.get(0);
                if (obj != null) {
                    result = obj;
                }
            }
        }

        return result;
    }

    public void insert(Person person) throws SQLException
    {
        String sql = "INSERT INTO person (nm_person, dt_birthdate, ds_job, cd_person_manager, dh_created) VALUES (?,?,?,?, CURRENT_TIMESTAMP())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, person.getName());
            ps.setDate(2,  new java.sql.Date(person.getBirthdate().getTime()));
            ps.setString(3, person.getJob());
            try {
                ps.setInt(4, (person.getManagerPersonId()));
            }catch (Exception e){
                ps.setNull(4, Types.INTEGER);
            }


            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void update(Person person) throws SQLException{
        if (person.getId() == null){
            throw new IllegalArgumentException("Cannot update null person");
        }
        String sql = "UPDATE person SET nm_person = ?, dt_birthdate = ?, ds_job = ? WHERE cd_person = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, person.getName());
            ps.setDate(2, new java.sql.Date(person.getBirthdate().getTime()));
            ps.setString(3, person.getJob());
            ps.setInt(4, person.getId());

            ps.executeUpdate();
        }
    }

    public void delete(Integer id) throws SQLException{
        if (id == null){
            throw new IllegalArgumentException("Cannot delete unexistent person");
        }

        String sql = "DELETE FROM person WHERE cd_person = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private List<?> getDataFromResultSet(PreparedStatement ps) throws SQLException {

        final List<Object> result = new ArrayList<>();

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Person obj = new Person();

                obj.setId(rs.getInt("cd_person"));
                obj.setName(rs.getString("nm_person"));
                obj.setBirthdate(rs.getDate("dt_birthdate"));
                obj.setJob(rs.getString("ds_job"));
                obj.setManagerPersonId(rs.getInt("cd_person_manager"));

                result.add(obj);
            }
        }
        return result;
    }
}