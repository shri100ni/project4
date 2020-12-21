package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.CourseBean;
import in.co.rays.bean.FacultyBean;
import in.co.rays.bean.StudentBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

/**
 * The Class FacultyModel.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class FacultyModel {
	
	/** The log. */
	private static Logger log = Logger.getLogger(FacultyModel.class);

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
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
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
	 * @return the faculty bean
	 * @throws ApplicationException the application exception
	 */
	public FacultyBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setSubjectId(rs.getLong(3));
				bean.setCourseId(rs.getLong(4));
				bean.setFirstName(rs.getString(5));
				bean.setLastName(rs.getString(6));
				bean.setGender(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setEmailId(rs.getString(9));
				bean.setMobNo(rs.getString(10));
				bean.setCourseName(rs.getString(11));
				bean.setCollegeName(rs.getString(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Faculty by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the faculty bean
	 * @throws ApplicationException the application exception
	 */
	public FacultyBean findByEmail(String email) throws ApplicationException {
		log.debug("Model findByName Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_ID=?");
		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setSubjectId(rs.getLong(3));
				bean.setCourseId(rs.getLong(4));
				bean.setFirstName(rs.getString(5));
				bean.setLastName(rs.getString(6));
				bean.setGender(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setEmailId(rs.getString(9));
				bean.setMobNo(rs.getString(10));
				bean.setCourseName(rs.getString(11));
				bean.setCollegeName(rs.getString(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
				}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Faculty by Name");
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
	public long add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
System.out.println("hello add model");
		
		CourseModel coumodel= new CourseModel();
		CourseBean coubean=    coumodel.findByPK(bean.getCourseId());
		bean.setCourseName(coubean.getName());		
		
		SubjectModel  smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPK(bean.getSubjectId());
		bean.setSubjectName(sbean.getName());
		
		CollegeModel colmodel=new CollegeModel();
		CollegeBean colbean=colmodel.findByPK(bean.getCollegeId());
		bean.setCollegeName(colbean.getName());
		
		FacultyBean duplicateFacultyName = findByEmail(bean.getEmailId());

		if (duplicateFacultyName != null) {
			throw new DuplicateRecordException("Faculty Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getCollegeId());
			pstmt.setLong(3, bean.getSubjectId());
			pstmt.setLong(4, bean.getCourseId());			
			pstmt.setString(5, bean.getFirstName());
			pstmt.setString(6, bean.getLastName());
			pstmt.setString(7, bean.getGender());
			pstmt.setDate(8, new java.sql.Date(bean.getDob().getTime()));
//			pstmt.setDate(8, new java.sql.Date(bean.getDob().getTime()));			
			pstmt.setString(9, bean.getEmailId());
			pstmt.setString(10, bean.getMobNo());
			pstmt.setString(11, bean.getCourseName());
			pstmt.setString(12, bean.getCollegeName());
			pstmt.setString(13, bean.getSubjectName());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDatetime());
			pstmt.setTimestamp(17, bean.getModifiedDatetime());
			System.out.println(bean.getModifiedDatetime());
			pstmt.executeUpdate();
			System.out.println("add data");
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
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Faculty");
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
	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		CourseModel coumodel= new CourseModel();
		CourseBean coubean=    coumodel.findByPK(bean.getCourseId());
		bean.setCourseName(coubean.getName());		
		
		SubjectModel  smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPK(bean.getSubjectId());
		bean.setSubjectName(sbean.getName());
		
		CollegeModel colmodel=new CollegeModel();
		CollegeBean colbean=colmodel.findByPK(bean.getCollegeId());
		bean.setCollegeName(colbean.getName());
		FacultyBean beanExist = findByEmail(bean.getEmailId());

		// Check if updated course already exist
		if (beanExist != null && beanExist.getId() != bean.getId()) {

			throw new DuplicateRecordException("Faculty is already exist");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_FACULTY COLLEGE_ID=?,SUBJECT_ID=?,COURSE_ID=?,FIRSET_NAME=?,LAST_NAME=?,GENDER=?,DOB=?,EMAIL_ID=?,MOBILE_NO=?,COURSE_NAME=?,COLLEGE_NAME=?,SUBJECT_NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		
			pstmt.setLong(1, bean.getCollegeId());
			pstmt.setLong(2, bean.getSubjectId());
			pstmt.setLong(3, bean.getCourseId());			
			pstmt.setString(4, bean.getFirstName());
			pstmt.setString(5, bean.getLastName());
			pstmt.setString(6, bean.getGender());
			pstmt.setDate(7, new java.sql.Date(bean.getDob().getTime()));			
			pstmt.setString(8, bean.getEmailId());
			pstmt.setString(9, bean.getMobNo());
			pstmt.setString(10, bean.getCourseName());
			pstmt.setString(11, bean.getCollegeName());
			pstmt.setString(12, bean.getSubjectName());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreatedDatetime());
			pstmt.setTimestamp(16, bean.getModifiedDatetime());
			pstmt.setLong(17, bean.getId());
			
		int i=pstmt.executeUpdate();
		System.out.println(i);
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
//			throw new ApplicationException("Exception in updating Faculty ");
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
	public void delete(FacultyBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete Faculty");
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
 	public List search(FacultyBean bean, int pageNo, int pageSize)
	            throws ApplicationException {
	        log.debug("Model search Started");
	        StringBuffer sql = new StringBuffer(
	                "SELECT * FROM ST_FACULTY WHERE 1=1");

	        if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" AND id = " + bean.getId());
	            }
	            if (bean.getCollegeId() != 0 && bean.getCollegeId() > 0) {
	                sql.append(" AND COLLEGE_ID like '" + bean.getCollegeId() + "%'");
	            }
	            if (bean.getSubjectId() != 0 && bean.getSubjectId() > 0) {
	                sql.append(" AND SUBJECT_ID like '" + bean.getSubjectId() + "%'");
	            }
	            if (bean.getCourseId() != 0 && bean.getCourseId() > 0) {
	                sql.append(" AND Course_Id like '" + bean.getCourseId() + "%'");
	            }
	            if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
	                sql.append(" AND FIRST_Name like '" + bean.getFirstName() + "%'");
	            }
	            if (bean.getLastName() != null && bean.getLastName().length() > 0) {
	                sql.append(" AND LAST_Name like '" + bean.getLastName() + "%'");
	            }
	            if (bean.getEmailId() != null && bean.getEmailId().length() > 0) {
	                sql.append(" AND EMAIL_ID like '" + bean.getEmailId() + "%'");
	            }
	            if (bean.getMobNo() != null && bean.getMobNo().length() > 0) {
	                sql.append(" AND MOBILE_NO like '" + bean.getMobNo() + "%'");
	            }
	            if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
	                sql.append(" AND Course_Name like '" + bean.getCourseName() + "%'");
	            }
	            if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
	                sql.append(" AND COLLEGE_Name like '" + bean.getCollegeName() + "%'");
	            }
	            if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
	                sql.append(" AND SUBJECT_Name like '" + bean.getSubjectName() + "%'");
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
	                bean = new FacultyBean();
	                
	                bean.setId(rs.getLong(1));
					bean.setCollegeId(rs.getLong(2));
					bean.setSubjectId(rs.getLong(3));
					bean.setCourseId(rs.getLong(4));
					bean.setFirstName(rs.getString(5));
					bean.setLastName(rs.getString(6));
					bean.setGender(rs.getString(7));
					bean.setDob(rs.getDate(8));
					bean.setEmailId(rs.getString(9));
					bean.setMobNo(rs.getString(10));
					bean.setCourseName(rs.getString(11));
					bean.setCollegeName(rs.getString(12));
					bean.setSubjectName(rs.getString(13));
					bean.setCreatedBy(rs.getString(14));
					bean.setModifiedBy(rs.getString(15));
					bean.setCreatedDatetime(rs.getTimestamp(16));
					bean.setModifiedDatetime(rs.getTimestamp(17));                
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in search Faculty");
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
 	public List search(FacultyBean bean) throws ApplicationException {
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
	        StringBuffer sql = new StringBuffer("select * from ST_FACULTY");
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
	            	FacultyBean bean = new FacultyBean();
	            	bean.setId(rs.getLong(1));
					bean.setCollegeId(rs.getLong(2));
					bean.setSubjectId(rs.getLong(3));
					bean.setCourseId(rs.getLong(4));
					bean.setFirstName(rs.getString(5));
					bean.setLastName(rs.getString(6));
					bean.setGender(rs.getString(7));
					bean.setDob(rs.getDate(8));
					bean.setEmailId(rs.getString(9));
					bean.setMobNo(rs.getString(10));
					bean.setCourseName(rs.getString(11));
					bean.setCollegeName(rs.getString(12));
					bean.setSubjectName(rs.getString(13));
					bean.setCreatedBy(rs.getString(14));
					bean.setModifiedBy(rs.getString(15));
					bean.setCreatedDatetime(rs.getTimestamp(16));
					bean.setModifiedDatetime(rs.getTimestamp(17));
		
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in getting list of Faculty");
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
