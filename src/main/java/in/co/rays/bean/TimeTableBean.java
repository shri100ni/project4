package in.co.rays.bean;

import java.util.Date;

/**
 * The Class TimeTableBean.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class TimeTableBean extends BaseBean{

	/** The course id. */
	private long courseId;
	
	/** The course name. */
	private String courseName;
	
	/** The subject id. */
	private long subjectId;
	
	/** The subject name. */
	private String subjectName;
	
	/** The semester. */
	private String semester;
	
	/** The exam date. */
	private Date examDate;
	
	/** The exam time. */
	private String examTime;
	
	

	  /**
  	 * Gets the exam time.
  	 *
  	 * @return the exam time
  	 */
  	public String getExamTime() {
		return examTime;
	}
	
	/**
	 * Sets the exam time.
	 *
	 * @param examTime the new exam time
	 */
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	
	/**
	 * Gets the course id.
	 *
	 * @return the course id
	 */
	
	public long getCourseId() {
		return courseId;
	}
	
	/**
	 * Sets the course id.
	 *
	 * @param courseId the new course id
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	
	/**
	 * Gets the course name.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}
	
	/**
	 * Sets the course name.
	 *
	 * @param courseName the new course name
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	/**
	 * Gets the subject id.
	 *
	 * @return the subject id
	 */
	public long getSubjectId() {
		return subjectId;
	}
	
	/**
	 * Sets the subject id.
	 *
	 * @param subjectId the new subject id
	 */
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	
	/**
	 * Gets the subject name.
	 *
	 * @return the subject name
	 */
	public String getSubjectName() {
		return subjectName;
	}
	
	/**
	 * Sets the subject name.
	 *
	 * @param subjectName the new subject name
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	/**
	 * Gets the semester.
	 *
	 * @return the semester
	 */
	public String getSemester() {
		return semester;
	}
	
	/**
	 * Sets the semester.
	 *
	 * @param semester the new semester
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	
	
	/**
	 * Gets the exam date.
	 *
	 * @return the exam date
	 */
	public Date getExamDate() {
		return examDate;
	}
	
	/**
	 * Sets the exam date.
	 *
	 * @param examDate the new exam date
	 */
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see in.co.rays.bean.DropdownListBean#getKey()
	 */
	public String getKey() {
		
		return id+"";
	}
	
	/* (non-Javadoc)
	 * @see in.co.rays.bean.DropdownListBean#getValue()
	 */
	public String getValue() {
		
		return examDate+"";
	}

}
