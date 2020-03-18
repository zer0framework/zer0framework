package br.com.zer0framework.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class ConnectionFactory {
	
	/**
	 * Hide constructor
	 */
	private ConnectionFactory() {
		// Nothing to do
	}
	
	/*
	 * @return a connection with application database.
	 */
	public static Connection getConnection(boolean autoCommit) {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/zer0frameworkDB");
			Connection conn = ds.getConnection();
			conn.setAutoCommit(autoCommit);
			return conn;
		} catch (NamingException | SQLException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	public static Connection getConnection() {
		return getConnection(true);
	}
}