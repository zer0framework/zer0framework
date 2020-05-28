package br.com.zer0framework.dao;

import br.com.zer0framework.model.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {

    private Connection connection;

    public FileDAO(Connection conn) {
        this.connection = conn;
    }

    public List<File> findAll() throws SQLException {
        return (List<File>) getDataFromResultSet(connection.prepareStatement("SELECT * FROM file"));
    }

    private List<?> getDataFromResultSet(PreparedStatement ps) throws SQLException {

        final List<Object> result = new ArrayList<>();

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final File obj = new File();

                obj.setId(rs.getInt("id"));
                obj.setFileName(rs.getString("file_name"));
                obj.setOriginalFileName(rs.getString("original_file_name"));
                obj.setCreated(rs.getDate("created"));
                obj.setFileType(rs.getString("file_type"));
                obj.setLastModified(rs.getTimestamp("last_modified"));

                result.add(obj);
            }
        }
        return result;
    }

    public File findById(Integer id) throws SQLException{
        File result = null;

        final String sql= "SELECT * FROM file WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            final List<File> fileFromResultSet = (List<File>) getDataFromResultSet(ps);
            if(!fileFromResultSet.isEmpty()){
                final File obj = fileFromResultSet.get(0);
                if (obj != null){
                    result = obj;
                }
            }
        }
        return result;
    }

    public List<File> findByFilename(String fileName) throws SQLException{

        List<File> fromResultSet;

        final String sql = "SELECT * FROM file WHERE original_file_name = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,fileName);
            fromResultSet = (List<File>) getDataFromResultSet(ps);
        }
        return fromResultSet;
    }

    public void insert(File file) throws SQLException{

        final String sql = "INSERT INTO file (file_name, original_file_name, file_type, created) VALUES (?,?,?, current_timestamp());";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,file.getFileName());
            ps.setString(2,file.getOriginalFileName());
            ps.setString(3,file.getFileType());

            ps.executeUpdate();
        }
    }

    public void update(File file) throws SQLException{

        final String sql = "UPDATE file SET file_name = ?, original_file_name = ?, file_type = ?, last_modified = current_timestamp() WHERE id = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, file.getFileName());
            ps.setString(2, file.getOriginalFileName());
            ps.setString(3, file.getFileType());
            ps.setInt(4, file.getId());

            ps.executeUpdate();
        }
    }
    public void delete(File file) throws SQLException{

        final String sql = "DELETE FROM file WHERE id = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, file.getId());

            ps.executeUpdate();
        }
    }
}
