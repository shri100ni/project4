
package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.CourseBean;
import in.co.rays.bean.StudentBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

/**
 * The Class CourseModel.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class CourseModel {
	
	/** The log. */
	private static Logger log = Logger.getLogger(CourseModel.class);

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
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Find by PK.
	 *
	 * @param pk the pk
	 * @return the course bean
	 * @throws ApplicationException the application exception
	 */
	public CourseBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting course by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the course bean
	 * @throws ApplicationException the application exception
	 */
	public CourseBean findByName(String name) throws ApplicationException {
		log.debug("Model findByName Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE NAME=?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Course by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByName End");
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
	public long add(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		CourseBean duplicateCourseName = findByName(bean.getName());

		if (duplicateCourseName != null) {
			throw new DuplicateRecordException("Course Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDuration());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Update.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		CourseBean beanExist = findByName(bean.getName());

		// Check if updated course already exist
		if (beanExist != null && beanExist.getId() != bean.getId()) {

			throw new DuplicateRecordException("Course is already exist");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_COURSE SET NAME=?,DURATION=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			
			pstmt.setString(2, bean.getDuration());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());
			
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in updating Course ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}
	
	/**
	 * Delete.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */
	public void delete(CourseBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
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
 	public List search(CourseBean bean, int pageNo, int pageSize)
	            throws ApplicationException {
	        log.debug("Model search Started");
	        StringBuffer sql = new StringBuffer(
	                "SELECT * FROM ST_COURSE WHERE 1=1");

	        if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" AND id = " + bean.getId());
	            }
	            if (bean.getName() != null && bean.getName().length() > 0) {
	                sql.append(" AND NAME like '" + bean.getName() + "%'");
	            }
	            if (bean.getDescription() != null && bean.getDescription().length() > 0) {
	                sql.append(" AND DESCRIPTION like '" + bean.getDescription() + "%'");
	            }
	            if (bean.getDuration() != null && bean.getDuration().length() > 0) {
	                sql.append(" AND DURATION like '" + bean.getDuration() + "%'");
	            }
	           
	        }

	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;

	            sql.append(" Limit " + pageNo + ", " + pageSize);
	            // sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        ArrayList list = new ArrayList();
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                bean = new CourseBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
					bean.setDuration(rs.getString(3));
					bean.setDescription(rs.getString(4));
	                bean.setCreatedBy(rs.getString(5));
	                bean.setModifiedBy(rs.getString(6));
	                bean.setCreatedDatetime(rs.getTimestamp(7));
	                bean.setModifiedDatetime(rs.getTimestamp(8));
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in search COURSE");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model search End");
	        return list;
	    }

	 /**
 	 * Search.
 	 *
 	 * @param bean the bean
 	 * @return the list
 	 * @throws ApplicationException the application exception
 	 */
 	public List search(CourseBean bean) throws ApplicationException {
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
	        StringBuffer sql = new StringBuffer("select * from ST_COURSE");
	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	CourseBean bean = new CourseBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setDescription(rs.getString(3));
	                bean.setDuration(rs.getString(4));	                
	                bean.setCreatedBy(rs.getString(5));
	                bean.setModifiedBy(rs.getString(6));
	                bean.setCreatedDatetime(rs.getTimestamp(7));
	                bean.setModifiedDatetime(rs.getTimestamp(8));
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in getting list of course");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model list End");
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
}
