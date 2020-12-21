package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.bean.CourseBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.bean.TimeTableBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

/**
 * The Class TimeTableModel.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class TimeTableModel {
	
	/** The log. */
	private static Logger log = Logger.getLogger(TimeTableModel.class);

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
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");
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
	 * @return the time table bean
	 * @throws ApplicationException the application exception
	 */
	public TimeTableBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting TimeTable by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
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
	public long add(TimeTableBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		
		
		CourseModel coumodel= new CourseModel();
		CourseBean coubean=    coumodel.findByPK(bean.getCourseId());
		bean.setCourseName(coubean.getName());
		
		
		
		SubjectModel  smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPK(bean.getSubjectId());
		bean.setSubjectName(sbean.getName());
		
		
		TimeTableBean bean11 = checkBycds(bean.getCourseId(), bean.getSemester(),  new java.sql.Date(bean.getExamDate().getTime()));
		TimeTableBean bean12 = checkBycss(bean.getCourseId(), bean.getSubjectId(), bean.getSemester());
		
		if(bean11 != null || bean12 != null){ 
			throw new DuplicateRecordException("TimeTable already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getCourseId());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setLong(4, bean.getSubjectId());
			pstmt.setString(5, bean.getSubjectName());
			pstmt.setString(6, bean.getSemester());
			pstmt.setDate(7, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(8, bean.getExamTime());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());

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
	public void update(TimeTableBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		CourseModel coumodel= new CourseModel();
		CourseBean coubean=    coumodel.findByPK(bean.getCourseId());
		String courseName= coubean.getName();
		
		SubjectModel  smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPK(bean.getSubjectId());
		String subjectName= sbean.getName();
	
		TimeTableBean beanExist1 = checkBycds(bean.getCourseId(), bean.getSemester(),  new java.sql.Date(bean.getExamDate().getTime()));
		TimeTableBean beanExist2 = checkBycss(bean.getCourseId(), bean.getSubjectId(), bean.getSemester());
		 if(beanExist1 != null || beanExist2 != null){ 

			throw new DuplicateRecordException("TimeTable is already exist");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_TIMETABLE SET COURSE_ID=?,COURSE_NAME=?,SUBJECT_ID=?,SUBJECT_NAME=?,SEMESTER=?,EXAM_DATE=?,EXAM_TIME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, bean.getCourseId());
			pstmt.setString(2, bean.getCourseName());
			pstmt.setLong(3, bean.getSubjectId());
			pstmt.setString(4, bean.getSubjectName());
			System.out.println(bean.getSubjectName());
			pstmt.setString(5, bean.getSemester());
			pstmt.setDate(6, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(7, bean.getExamTime());
			System.out.println(bean.getExamTime());

			pstmt.setString(8, bean.getCreatedBy());
			System.out.println(bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());

			System.out.println(bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.setLong(12, bean.getId());
			System.out.println(bean.getId());
			// int ii=pstmt.executeUpdate();
			int i = pstmt.executeUpdate();
			System.out.println(i);
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in updating TimeTable ");
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
	public void delete(TimeTableBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete TimeTable");
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
	public List search(TimeTableBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
//		System.out.println("data in model --- "+bean.getExamDate());
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

			if (bean.getSubjectId() != 0 && bean.getSubjectId() > 0) {
				sql.append(" AND SUBJECT_ID = " + bean.getSubjectId());
			}
			if (bean.getCourseId() != 0 && bean.getCourseId() > 0) {
				sql.append(" AND Course_Id = " + bean.getCourseId() );
			}

			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND Course_Name like '" + bean.getCourseName() + "%'");
			}

			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND SUBJECT_Name like '" + bean.getSubjectName() + "%'");
			}
			if (bean.getExamDate() != null) {
				
//				System.out.println("date search called--------->");
				java.sql.Date ob = new java.sql.Date(bean.getExamDate().getTime());
				sql.append(" AND EXAM_DATE like '" + ob + "%'");
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
				bean = new TimeTableBean();

				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search TimeTable");
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
	public List search(TimeTableBean bean) throws ApplicationException {
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
		StringBuffer sql = new StringBuffer("select * from ST_TIMETABLE");
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
				TimeTableBean bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			/*
			 * throw new ApplicationException(
			 * "Exception : Exception in getting list of TimeTable");
			 */
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

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the time table bean
	 * @throws ApplicationException the application exception
	 */
	public TimeTableBean findByName(String name) throws ApplicationException {
		log.debug("TimeTable Model findByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE SubjectName = ?");
		Connection conn = null;
		TimeTableBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByName Method of TimeTable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTable Model findByName method End");
		return bean;
	}

	/**
	 * Check bycss.
	 *
	 * @param CourseId the course id
	 * @param SubjectId the subject id
	 * @param semester the semester
	 * @return the time table bean
	 * @throws ApplicationException the application exception
	 */
	public TimeTableBean checkBycss(long CourseId, long SubjectId, String semester) throws ApplicationException {
		Connection conn = null;
		TimeTableBean bean = null;
		// java.util.Date ExamDAte,String ExamTime
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Subject_ID=? AND Semester=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Timetable Model list method End");
		return bean;
	}

	/**
	 * Check bycds.
	 *
	 * @param CourseId the course id
	 * @param Semester the semester
	 * @param ExamDate the exam date
	 * @return the time table bean
	 * @throws ApplicationException the application exception
	 */
	public TimeTableBean checkBycds(long CourseId, String Semester, Date ExamDate) throws ApplicationException {
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE ID=? AND Semester=? AND Exam_Date=?");

		Connection conn = null;
		TimeTableBean bean = null;
		// Date ExDate = new Date(ExamDAte.getTime());

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setString(2, Semester);
			ps.setDate(3, (java.sql.Date) ExamDate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));				
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
					}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Timetable Model list method End");
		return bean;
	}

}




/*package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.bean.FacultyBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.bean.TimeTableBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

*//**
 * The Class TimeTableModel.
 *//*
public class TimeTableModel {
	
	*//** The log. *//*
	private static Logger log = Logger.getLogger(TimeTableModel.class);

	*//**
	 * Next PK.
	 *
	 * @return the integer
	 * @throws DatabaseException the database exception
	 *//*
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");
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

	*//**
	 * Find by PK.
	 *
	 * @param pk the pk
	 * @return the time table bean
	 * @throws ApplicationException the application exception
	 *//*
	public TimeTableBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting TimeTable by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	*//**
	 * Adds the.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 *//*
	public long add(TimeTableBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		TimeTableBean duplicateTimeTable = findByPK(bean.getId());

		if (duplicateTimeTable != null) {
			throw new DuplicateRecordException("TimeTable already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getCourseId());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setLong(4, bean.getSubjectId());
			pstmt.setString(5, bean.getSubjectName());
			pstmt.setString(6, bean.getSemester());
			pstmt.setDate(7, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(8, bean.getExamTime());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());

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

	*//**
	 * Update.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 *//*
	public void update(TimeTableBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		TimeTableBean beanExist = findByPK(bean.getId());

		// Check if updated course already exist
		if (beanExist != null && beanExist.getId() != bean.getId()) {

			throw new DuplicateRecordException("TimeTable is already exist");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_TIMETABLE SET COURSE_ID=?,COURSE_NAME=?,SUBJECT_ID=?,SUBJECT_NAME=?,SEMESTER=?,EXAM_DATE=?,EXAM_TIME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, bean.getCourseId());
			pstmt.setString(2, bean.getCourseName());
			pstmt.setLong(3, bean.getSubjectId());
			pstmt.setString(4, bean.getSubjectName());
			System.out.println(bean.getSubjectName());
			pstmt.setString(5, bean.getSemester());
			pstmt.setDate(6, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(7, bean.getExamTime());
			System.out.println(bean.getExamTime());

			pstmt.setString(8, bean.getCreatedBy());
			System.out.println(bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());

			System.out.println(bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.setLong(12, bean.getId());
			System.out.println(bean.getId());
			// int ii=pstmt.executeUpdate();
			int i = pstmt.executeUpdate();
			System.out.println(i);
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in updating TimeTable ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	*//**
	 * Delete.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 *//*
	public void delete(TimeTableBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	*//**
	 * Search.
	 *
	 * @param bean the bean
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list
	 * @throws ApplicationException the application exception
	 *//*
	public List search(TimeTableBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

			if (bean.getSubjectId() != 0 && bean.getSubjectId() > 0) {
				sql.append(" AND SUBJECT_ID like '" + bean.getSubjectId() + "%'");
			}
			if (bean.getCourseId() != 0 && bean.getCourseId() > 0) {
				sql.append(" AND Course_Id like '" + bean.getCourseId() + "%'");
			}

			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND Course_Name like '" + bean.getCourseName() + "%'");
			}

			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND SUBJECT_Name like '" + bean.getSubjectName() + "%'");
			}
			if (bean.getExamDate() != null) {
				sql.append(" AND EXAM_DATE like '" + bean.getExamDate() + "%'");
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
				bean = new TimeTableBean();

				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	*//**
	 * Search.
	 *
	 * @param bean the bean
	 * @return the list
	 * @throws ApplicationException the application exception
	 *//*
	public List search(TimeTableBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	*//**
	 * List.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list
	 * @throws ApplicationException the application exception
	 *//*
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_TIMETABLE");
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
				TimeTableBean bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			
			 * throw new ApplicationException(
			 * "Exception : Exception in getting list of TimeTable");
			 
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}

	*//**
	 * List.
	 *
	 * @return the list
	 * @throws ApplicationException the application exception
	 *//*
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	*//**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the time table bean
	 * @throws ApplicationException the application exception
	 *//*
	public TimeTableBean findByName(String name) throws ApplicationException {
		log.debug("TimeTable Model findByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE SubjectName = ?");
		Connection conn = null;
		TimeTableBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByName Method of TimeTable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTable Model findByName method End");
		return bean;
	}

	*//**
	 * Check bycss.
	 *
	 * @param CourseId the course id
	 * @param SubjectId the subject id
	 * @param semester the semester
	 * @return the time table bean
	 * @throws ApplicationException the application exception
	 *//*
	public TimeTableBean checkBycss(long CourseId, long SubjectId, String semester) throws ApplicationException {
		Connection conn = null;
		TimeTableBean bean = null;
		// java.util.Date ExamDAte,String ExamTime
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE CourseID=? AND SubjectID=? AND Semester=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Timetable Model list method End");
		return bean;
	}

	*//**
	 * Check bycds.
	 *
	 * @param CourseId the course id
	 * @param Semester the semester
	 * @param ExamDate the exam date
	 * @return the time table bean
	 * @throws ApplicationException the application exception
	 *//*
	public TimeTableBean checkBycds(long CourseId, String Semester, Date ExamDate) throws ApplicationException {
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE CourseID=? AND Semester=? AND ExamDate=?");

		Connection conn = null;
		TimeTableBean bean = null;
		// Date ExDate = new Date(ExamDAte.getTime());

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setString(2, Semester);
			ps.setDate(3, (java.sql.Date) ExamDate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));				
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
					}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Timetable Model list method End");
		return bean;
	}

}
*/