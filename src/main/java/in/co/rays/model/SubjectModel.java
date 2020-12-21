package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.bean.CourseBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

/**
 * The Class SubjectModel.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class SubjectModel {
	
	/** The log. */
	private static Logger log = Logger.getLogger(SubjectModel.class);

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
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_SUBJECT");
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
	 * @return the subject bean
	 * @throws ApplicationException the application exception
	 */
	public SubjectBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
		SubjectBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Subject by pk");
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
	 * @return the subject bean
	 * @throws ApplicationException the application exception
	 */
	public SubjectBean findByName(String name) throws ApplicationException {
		log.debug("Model findByName Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE NAME=?");
		SubjectBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Subject by Name");
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
	public long add(SubjectBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
		CourseModel coumodel= new CourseModel();
		CourseBean coubean=    coumodel.findByPK(bean.getCourseId());
		bean.setCourseName(coubean.getName());	
		
		SubjectBean duplicateSubjectName = findByName(bean.getName());

		if (duplicateSubjectName != null) {
			throw new DuplicateRecordException("Subject Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setLong(3, bean.getCourseId());
			pstmt.setString(4, bean.getCourseName());
			pstmt.setString(5, bean.getDescription());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
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
			throw new ApplicationException("Exception : Exception in add Subject");
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
	public void update(SubjectBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		CourseModel coumodel= new CourseModel();
		CourseBean coubean=    coumodel.findByPK(bean.getCourseId());
		bean.setCourseName(coubean.getName());	
		
		SubjectBean beanExist = findByName(bean.getName());

		// Check if updated course already exist
		if (beanExist != null && beanExist.getId() != bean.getId()) {

			throw new DuplicateRecordException("Subject is already exist");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_SUBJECT SET NAME=?,Course_Id=?,Course_name=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setLong(2, bean.getCourseId());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			
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
			throw new ApplicationException("Exception in updating Subject ");
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
	public void delete(SubjectBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete Subject");
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
 	public List search(SubjectBean bean, int pageNo, int pageSize)
	            throws ApplicationException {
	        log.debug("Model search Started");
	        StringBuffer sql = new StringBuffer(
	                "SELECT * FROM ST_SUBJECT WHERE 1=1");

	        if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" AND id = " + bean.getId());
	            }
	            if (bean.getName() != null && bean.getName().length() > 0) {
	                sql.append(" AND NAME like '" + bean.getName() + "%'");
	            }
	            if (bean.getCourseId() != 0 && bean.getCourseId() > 0) {
	                sql.append(" AND Course_Id like '" + bean.getCourseId() + "%'");
	            }
	            if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
	                sql.append(" AND Course_Name like '" + bean.getCourseName() + "%'");
	            }
	           
	            if (bean.getDescription() != null && bean.getDescription().length() > 0) {
	                sql.append(" AND Description like '" + bean.getDescription() + "%'");
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
	                bean = new SubjectBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setCourseId(rs.getLong(3));
	                bean.setCourseName(rs.getString(4));
	                bean.setDescription(rs.getString(5));
	                bean.setCreatedBy(rs.getString(6));
	                bean.setModifiedBy(rs.getString(7));
	                bean.setCreatedDatetime(rs.getTimestamp(8));
	                bean.setModifiedDatetime(rs.getTimestamp(9));
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in search Subject");
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
 	public List search(SubjectBean bean) throws ApplicationException {
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
	        StringBuffer sql = new StringBuffer("select * from ST_SUBJECT");
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
	            	SubjectBean bean = new SubjectBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setCourseId(rs.getLong(3));
	                bean.setCourseName(rs.getString(4));
	                bean.setDescription(rs.getString(5));
	                bean.setCreatedBy(rs.getString(6));
	                bean.setModifiedBy(rs.getString(7));
	                bean.setCreatedDatetime(rs.getTimestamp(8));
	                bean.setModifiedDatetime(rs.getTimestamp(9));
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in getting list of Subject");
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
