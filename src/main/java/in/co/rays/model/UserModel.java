package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.exception.RecordNotFoundException;
import in.co.rays.util.EmailBuilder;
import in.co.rays.util.EmailMessage;
import in.co.rays.util.EmailUtility;
import in.co.rays.util.JDBCDataSource;

/**
 * The Class UserModel.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class UserModel {
	
	/** The log. */
	private static Logger log = Logger.getLogger(UserModel.class);
	
	/** The role id. */
	private long roleId;

	/**
	 * Gets the role id.
	 *
	 * @return the role id
	 */
	public long getRoleId() {
		 
		return roleId;
	}

	/**
	 * Sets the role id.
	 *
	 * @param roleId the new role id
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
	 * Next PK.
	 *
	 * @return the integer
	 * @throws DatabaseException the database exception
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_USER");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
			// e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Find by login.
	 *
	 * @param login the login
	 * @return the user bean
	 * @throws ApplicationException the application exception
	 */
	public UserBean findByLogin(String login) throws ApplicationException {
		log.debug("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN=?");
		UserBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by login");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByLogin End");
		return bean;

	}

	/**
	 * Adds the.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public long add(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		
		
		Connection conn = null;
		int pk = 0;
		UserBean existbean = findByLogin(bean.getLogin());
		if (existbean != null) {
			throw new DuplicateRecordException("Login Id already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false);
//			System.out.println("hhhhhhhhh");
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
//			System.out.println("kkkkkkkkkkk");
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getLogin());
			pstmt.setString(5, bean.getPassword());			
			pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));			
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setLong(8, bean.getRoleId());
			pstmt.setInt(9, bean.getUnSuccessfulLogin());
			pstmt.setString(10, bean.getGender());
			pstmt.setTimestamp(11, bean.getLastLogin());
			pstmt.setString(12, bean.getLock());
			pstmt.setString(13, bean.getRegisteredIP());
			pstmt.setString(14, bean.getLastLoginIP());
			pstmt.setString(15, bean.getCreatedBy());
			pstmt.setString(16, bean.getModifiedBy());
			pstmt.setTimestamp(17, bean.getCreatedDatetime());
			pstmt.setTimestamp(18, bean.getModifiedDatetime());
			int i=pstmt.executeUpdate();
			System.out.println("data inserted"+i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				//throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			//throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Delete.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */
	public void delete(UserBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_USER WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	/**
	 * Find by PK.
	 *
	 * @param pk the pk
	 * @return the user bean
	 * @throws ApplicationException the application exception
	 */
	public UserBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ID=?");
		UserBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;

	}

	/**
	 * Update.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public void update(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		UserBean beanExist = findByLogin(bean.getLogin());
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("LoginId is already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,UNSUCCESSFUL_LOGIN=?,GENDER=?,LAST_LOGIN=?,USER_LOCK=?,REGISTERED_IP=?,LAST_LOGIN_IP=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getLogin());
			pstmt.setString(4, bean.getPassword());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setLong(7, bean.getRoleId());
			pstmt.setInt(8, bean.getUnSuccessfulLogin());
			pstmt.setString(9, bean.getGender());
			pstmt.setTimestamp(10, bean.getLastLogin());
			pstmt.setString(11, bean.getLock());
			pstmt.setString(12, bean.getRegisteredIP());
			pstmt.setString(13, bean.getLastLoginIP());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDatetime());
			pstmt.setTimestamp(17, bean.getModifiedDatetime());
			pstmt.setLong(18, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			// throw new ApplicationException("Exception in updating User");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Search.
	 *
	 * @param bean the bean
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
	
		log.debug("Model search Started");
		ArrayList list = new ArrayList();
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append("AND ID=" + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getGender());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
			}
			if (bean.getRoleId() > 0) {
				sql.append(" AND ROLE_ID = " + bean.getRoleId());
			}
			if (bean.getUnSuccessfulLogin() > 0) {
				sql.append(" AND UNSUCCESSFUL_LOGIN = " + bean.getUnSuccessfulLogin());
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + bean.getGender() + "%'");
			}
			if (bean.getLastLogin() != null && bean.getLastLogin().getTime() > 0) {
				sql.append(" AND LAST_LOGIN = " + bean.getLastLogin());
			}
			if (bean.getRegisteredIP() != null && bean.getRegisteredIP().length() > 0) {
				sql.append(" AND REGISTERED_IP like '" + bean.getRegisteredIP() + "%'");
			}
			if (bean.getLastLoginIP() != null && bean.getLastLoginIP().length() > 0) {
				sql.append(" AND LAST_LOGIN_IP like '" + bean.getLastLoginIP() + "%'");
			}
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" Limit " + pageNo + ", " + pageSize);
			}
			System.out.println();
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new UserBean();
					bean.setId(rs.getLong(1));
					bean.setFirstName(rs.getString(2));
					bean.setLastName(rs.getString(3));
					bean.setLogin(rs.getString(4));
					bean.setPassword(rs.getString(5));
					bean.setDob(rs.getDate(6));
					bean.setMobileNo(rs.getString(7));
					bean.setRoleId(rs.getLong(8));
					bean.setUnSuccessfulLogin(rs.getInt(9));
					bean.setGender(rs.getString(10));
					bean.setLastLogin(rs.getTimestamp(11));
					bean.setLock(rs.getString(12));
					bean.setRegisteredIP(rs.getString(13));
					bean.setLastLoginIP(rs.getString(14));
					bean.setCreatedBy(rs.getString(15));
					bean.setModifiedBy(rs.getString(16));
					bean.setCreatedDatetime(rs.getTimestamp(17));
					bean.setModifiedDatetime(rs.getTimestamp(18));
					list.add(bean);
				

				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Database Exception..", e);
				throw new ApplicationException("Exception : Exception in search user");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

			log.debug("Model search End");
		}
		return list;

	}

	/**
	 * Search.
	 *
	 * @param bean the bean
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search(UserBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * List.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_USER");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					UserBean bean = new UserBean();
					bean.setId(rs.getLong(1));
					bean.setFirstName(rs.getString(2));
					bean.setLastName(rs.getString(3));
					bean.setLogin(rs.getString(4));
					bean.setPassword(rs.getString(5));
					bean.setDob(rs.getDate(6));
					bean.setMobileNo(rs.getString(7));
					bean.setRoleId(rs.getLong(8));
					bean.setUnSuccessfulLogin(rs.getInt(9));
					bean.setGender(rs.getString(10));
					bean.setLastLogin(rs.getTimestamp(11));
					bean.setLock(rs.getString(12));
					bean.setRegisteredIP(rs.getString(13));
					bean.setLastLoginIP(rs.getString(14));
					bean.setCreatedBy(rs.getString(15));
					bean.setModifiedBy(rs.getString(16));
					bean.setCreatedDatetime(rs.getTimestamp(17));
					bean.setModifiedDatetime(rs.getTimestamp(18));
					list.add(bean);
				}
				rs.close();
			} catch (Exception e) {
				log.error("Database Exception..", e);
				throw new ApplicationException("Exception : Exception in getting list of users");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model list End");
		}
		return list;

	}

	/**
	 * List.
	 *
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Authenticate.
	 *
	 * @param login the login
	 * @param password the password
	 * @return the user bean
	 * @throws ApplicationException the application exception
	 */
	public UserBean authenticate(String login, String password) throws ApplicationException {
		log.debug("Model authenticate Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN = ? AND PASSWORD = ?");
		UserBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

			}
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model authenticate End");
		return bean;
	}


	/**
	 * Gets the roles.
	 *
	 * @param bean the bean
	 * @return the roles
	 * @throws ApplicationException the application exception
	 */
	public List getRoles(UserBean bean) throws ApplicationException {
		log.debug("Model get roles Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE role_Id=?");
		Connection conn = null;
		List list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, bean.getRoleId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model get roles End");
		return list;
	}

	/**
	 * Change password.
	 *
	 * @param id the id
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return true, if successful
	 * @throws RecordNotFoundException the record not found exception
	 * @throws ApplicationException the application exception
	 */
	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {
		log.debug("model changePassword Started");
		boolean flag = false;
		UserBean beanExist = null;
		beanExist = findByPK(id);
		if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
			beanExist.setPassword(newPassword);
			try {
				update(beanExist);
			} catch (DuplicateRecordException e) {
				log.error(e);
				throw new ApplicationException("LoginId is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", beanExist.getLogin());
		map.put("password", beanExist.getPassword());
		map.put("firstName", beanExist.getFirstName());
		map.put("lastName", beanExist.getLastName());
		String message = EmailBuilder.getChangePassWordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(beanExist.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		log.debug("Model changePassword End");
		return flag;
	}

	/**
	 * Update access.
	 *
	 * @param bean the bean
	 * @return the user bean
	 * @throws ApplicationException the application exception
	 */
	public UserBean updateAccess(UserBean bean) throws ApplicationException {
		return null;
	}

	/**
	 * Register user.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public long registerUser(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		
		long pk = add(bean);
		
		System.out.println("pk value is"+pk);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		String message = EmailBuilder.getUserRegistrationMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successful for ORS Project SunilOS");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		return pk;
	}

	/**
	 * Reset password.
	 *
	 * @param bean the bean
	 * @return true, if successful
	 * @throws ApplicationException the application exception
	 */
	public boolean resetPassword(UserBean bean) throws ApplicationException {
		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
		UserBean userData = findByPK(bean.getId());
		userData.setPassword(newPassword);
		try {
			update(userData);
		} catch (DuplicateRecordException e) {
			return false;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		map.put("firstName", bean.getFirstName());
		map.put("lastName", bean.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		return true;
	}

	/**
	 * Forget password.
	 *
	 * @param login the login
	 * @return true, if successful
	 * @throws ApplicationException the application exception
	 * @throws RecordNotFoundException the record not found exception
	 */
	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		UserBean userData = findByLogin(login);
		boolean flag = false;
		if (userData == null) {
			throw new RecordNotFoundException("Email ID does not exists !");
		}
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("login", userData.getLogin());
			map.put("password", userData.getPassword());
			map.put("firstName", userData.getFirstName());
			map.put("lastName", userData.getLastName());
			String message = EmailBuilder.getForgetPasswordMessage(map);
			EmailMessage msg = new EmailMessage();
			msg.setTo(login);
			msg.setSubject("SUNARYS ORS Password reset");
			msg.setMessage(message);
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
			flag = true;

			return flag;
		}

	}


