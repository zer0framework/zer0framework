package br.com.zer0framework.dao;

import br.com.zer0framework.model.User;
import br.com.zer0framework.utils.security.AuthenticationUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	protected Connection connection;

	public UserDAO(Connection conn) {
		this.connection = conn;
	}
	
	public User findByUsername(String username) throws SQLException {
		User result = null;

		final String sql = "select * from user where ds_username = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, username);
			List<User> usersFromResultSet = getUsersFromResultSet(ps);
			if (!usersFromResultSet.isEmpty()) {
				final User obj = (User) usersFromResultSet.get(0);
				if (obj != null) {
					result = obj;
				}
			}
		}

		return result;
	}

	public List<User> findAll() {
		List<User> result = null;
		try {
			PreparedStatement ps = connection.prepareStatement("select * from user");
			result = getUsersFromResultSet(ps);
		} catch (Exception e) {
			e.getMessage();
		}

		return result;
	}

	public User findById(Integer id) throws SQLException {
		User result = null;

		final String sql = "select * from user where cd_user = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			final List<User> usersFromResultSet = getUsersFromResultSet(ps);
			if (!usersFromResultSet.isEmpty()) {
				final User obj = (User) usersFromResultSet.get(0);
				if (obj != null) {
					result = obj;
				}
			}
		}

		return result;
	}

	protected List<User> getUsersFromResultSet(PreparedStatement ps) throws SQLException {
		final List<User> result = new ArrayList<>();

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				final User obj = new User();
				result.add(obj);

				obj.setId(rs.getInt("cd_user"));
				obj.setUsername(rs.getString("ds_username"));
				obj.setPassword(rs.getString("ds_password"));
				obj.setPersonId(rs.getInt("cd_person"));
				obj.setEmail(rs.getString("ds_email"));
				obj.setCreated(rs.getDate("dh_created"));
			}
		}

		return result;
	}

	public void insert(User user) throws SQLException {
		if (user.getPersonId() == null) {
			throw new IllegalArgumentException("Person is null, not possible to insert!");
		}

		String sql = "insert into user (ds_username, ds_password, cd_person, ds_email, dh_created) values (?, ?, ?, ?, current_timestamp());";
		
		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getPersonId());
			ps.setString(4,user.getEmail());

			ps.executeUpdate();
		}
	}

	public void update(User user) throws SQLException {
		if (user.getId() == null) {
			throw new IllegalArgumentException("User is null, not possible to update!");
		}
		if (user.getId() == null) {
			throw new IllegalArgumentException("User id is null, not possible to update!");
		}
		String sql = "update user set ds_username = ?, ds_password = ?, cd_person = ?, ds_email where cd_user = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getPersonId());
			ps.setString(4, user.getEmail());
			ps.setInt(5, user.getId());

			ps.executeUpdate();
		}
	}

	public boolean delete(User user) throws SQLException {
		return deleteById(user.getId());
	}

	public boolean deleteById(int userId) throws SQLException {
		final String sql = "delete from user where cd_user = ?";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, userId);

			return ps.executeUpdate() > 0;
		}
	}

	public void updatePassword(String newPassword, String email) throws SQLException {

		final String sql = "UPDATE user SET ds_password = ? WHERE ds_email = ? ";

		try (PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, newPassword);
			ps.setString(2,email);

			ps.executeUpdate();
		}
	}

}
