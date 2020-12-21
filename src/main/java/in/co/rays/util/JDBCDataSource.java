package in.co.rays.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.search.IntegerComparisonTerm;


import com.mchange.v2.c3p0.ComboPooledDataSource;


import in.co.rays.exception.ApplicationException;

/**
 * The Class JDBCDataSource.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class JDBCDataSource {
	
	/** The datasource. */
	private static JDBCDataSource datasource;

	/**
	 * Instantiates a new JDBC data source.
	 */
	private JDBCDataSource() {
	}

	/** The cpds. */
	private ComboPooledDataSource cpds = null;

	/**
	 * Gets the single instance of JDBCDataSource.
	 *
	 * @return single instance of JDBCDataSource
	 */
	public static JDBCDataSource getInstance() {
		if (datasource == null) {
			ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.bundle.system");
			datasource = new JDBCDataSource();
			datasource.cpds = new ComboPooledDataSource();
			try {
				datasource.cpds.setDriverClass(rb.getString("driver"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			datasource.cpds.setJdbcUrl(rb.getString("url"));
			
			datasource.cpds.setUser(rb.getString("username"));
			datasource.cpds.setPassword(rb.getString("password"));
			
			datasource.cpds.setInitialPoolSize(new Integer((String) rb.getString("initialPoolSize")));
			datasource.cpds.setAcquireIncrement(new Integer((String) rb.getString("acquireIncrement")));

			datasource.cpds.setMaxPoolSize(new Integer((String) rb.getString("maxPoolSize")));
			datasource.cpds.setMinPoolSize(new Integer((String) rb.getString("minPoolSize")));
			
			datasource.cpds.setMaxIdleTime(DataUtility.getInt(rb.getString("timeout")));

		}
		return datasource;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws Exception the exception
	 */
	public static Connection getConnection() throws Exception {
		return (Connection) getInstance().cpds.getConnection();
	}

	/**
	 * Close connection.
	 *
	 * @param connection the connection
	 */
	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}

	 /**
 	 * Trn rollback.
 	 *
 	 * @param connection the connection
 	 * @throws ApplicationException the application exception
 	 */
 	public static void trnRollback(Connection connection)
	            throws ApplicationException {
	        if (connection != null) {
	            try {
	                connection.rollback();
	            } catch (SQLException ex) {
	                throw new ApplicationException(ex.toString());
	            }
	        }
	    }
}
