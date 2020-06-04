package br.com.zer0framework.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.zer0framework.model.Contact;

public class ContactDAO {

	protected Connection connection;

	public ContactDAO(Connection conn) {
		this.connection = conn;
	}

	public List<Contact> findAll() {
		List<Contact> result = null;
		try {
			PreparedStatement ps = connection.prepareStatement("select * from contact");
			result = getContactsFromResultSet(ps);
		} catch (Exception e) {
			e.getMessage();
		}

		return result;
	}

	public Contact findById(Integer id) throws SQLException {
		Contact result = null;

		final String sql = "select * from contact where cd_contact = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			final List<Contact> postsFromResultSet = getContactsFromResultSet(ps);
			if (!postsFromResultSet.isEmpty()) {
				final Contact obj = (Contact) postsFromResultSet.get(0);
				if (obj != null) {
					result = obj;
				}
			}
		}

		return result;
	}
	
	public List<Contact> findAllByUserId(Integer userId) throws SQLException {
		final String sql = "select * from contact where cd_user = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, userId);
			return getContactsFromResultSet(ps);
		} catch (Exception e) {
			return null;
		}

	}

	protected List<Contact> getContactsFromResultSet(PreparedStatement ps) throws SQLException {
		final List<Contact> result = new ArrayList<>();

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				final Contact obj = new Contact();
				result.add(obj);

				obj.setId(rs.getInt("cd_contact"));
				obj.setUserId(rs.getInt("cd_user"));
				obj.setForename(rs.getString("ds_forename"));
				obj.setSurname(rs.getString("ds_surname"));
				obj.setTelefone(rs.getString("ds_telefone"));
				obj.setCreated(rs.getDate("dh_created"));
			}
		}

		return result;
	}

	public void insert(Contact contact) throws SQLException {

		String sql = "insert into contact (cd_user, ds_forename, ds_surname, ds_telefone, dh_created) values (?, ?, ?, ?, current_timestamp());";

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, contact.getUserId());
			ps.setString(2, contact.getForename());
			ps.setString(3, contact.getSurname());
			ps.setString(4, contact.getTelefone());

			ps.executeUpdate();
		}
	}

	public void update(Contact contact) throws SQLException {
		if (contact.getId() == null) {
			throw new IllegalArgumentException("Contact id is null, not possible to update!");
		}
		String sql = "update contact set ds_forename = ?, ds_surname = ?, ds_telefone = ? where cd_contact = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, contact.getForename());
			ps.setString(2, contact.getSurname());
			ps.setString(3, contact.getTelefone());
			ps.setInt(4, contact.getId());

			ps.executeUpdate();
		}
	}

	public boolean delete(int contactId) throws SQLException {
		final String sql = "delete from contact where cd_contact = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, contactId);

			return ps.executeUpdate() > 0;
		}
	}

}
