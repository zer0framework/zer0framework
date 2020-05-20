package br.com.zer0framework.dao;

import br.com.zer0framework.model.Post;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

	private Connection connection;

	public PostDAO(Connection conn) {
		this.connection = conn;
	}

	public List<Post> findAll() throws SQLException {
		List<Post> result = null;

		final PreparedStatement ps = connection.prepareStatement("select * from post");
		result = getPostsFromResultSet(ps);

		return result;
	}

	public Post findById(Integer id) throws SQLException {
		Post result = null;

		final String sql = "select * from post where cd_post = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			final List<Post> postsFromResultSet = getPostsFromResultSet(ps);
			if (!postsFromResultSet.isEmpty()) {
				final Post obj = (Post) postsFromResultSet.get(0);
				if (obj != null) {
					result = obj;
				}
			}
		}

		return result;
	}

	protected List<Post> getPostsFromResultSet(PreparedStatement ps) throws SQLException {

		final List<Post> result = new ArrayList<>();

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				final Post obj = new Post();
				result.add(obj);

				obj.setId(rs.getInt("cd_post"));
				obj.setTitle(rs.getString("ds_title"));
				obj.setBody(rs.getString("ds_body"));
				obj.setUserId(rs.getInt("cd_user"));
				obj.setCreated(rs.getDate("dh_created"));
			}
		}

		return result;
	}

	public void insert(Post post) throws SQLException {
		if (post.getId() == null) {
			throw new IllegalArgumentException("Post is null, not possible to insert!");
		}
		if (post.getId() == null) {
			throw new IllegalArgumentException("Post id is NOT null, try update!");
		}
		String sql = "insert into post (cd_user, ds_title, ds_body, dh_created) values (?, ?, ?, current_timestamp());";

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, post.getUserId());
			ps.setString(2, post.getTitle());
			ps.setString(3, post.getBody());

			ps.executeUpdate();
		}
	}

	public void update(Post post) throws SQLException {
		if (post.getId() == null) {
			throw new IllegalArgumentException("Post is null, not possible to update!");
		}
		if (post.getId() == null) {
			throw new IllegalArgumentException("Post id is null, not possible to update!");
		}
		String sql = "update post set ds_title = ?, ds_body = ? where cd_post = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, post.getTitle());
			ps.setString(2, post.getBody());
			ps.setInt(3, post.getId());

			ps.executeUpdate();
		}
	}

	public boolean delete(Integer id) throws SQLException {
		final String sql = "delete from post where cd_post = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);

			return ps.executeUpdate() > 0;
	    }
	}
}
