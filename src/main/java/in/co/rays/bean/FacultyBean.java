package in.co.rays.bean;

import java.util.Date;

import in.co.rays.util.DataUtility;

/**
 * The Class FacultyBean.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class FacultyBean extends BaseBean {
	
	/** The college id. */
	private long collegeId;
	
	/** The subject id. */
	private long subjectId;

	/** The course id. */
	private long courseId;

	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;

	/** The gender. */

	private String gender;
	
	/** The Dob. */
	private Date Dob;
	
	/** The email id. */
	private String emailId;
	
	/** The mob no. */
	private String mobNo;
	
	/** The course name. */
	private String courseName;
	
	/** The college name. */
	private String collegeName;
	
	/** The subject name. */
	private String subjectName;
	
	/**
	 * Gets the college id.
	 *
	 * @return the college id
	 */
	

	public long getCollegeId() {
		return collegeId;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Sets the college id.
	 *
	 * @param collegeId the new college id
	 */
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
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
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * Gets the college name.
	 *
	 * @return the college name
	 */
	public String getCollegeName() {
		return collegeName;
	}

	/**
	 * Sets the college name.
	 *
	 * @param collegeName the new college name
	 */
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}


	/**
	 * Gets the dob.
	 *
	 * @return the dob
	 */
	public Date getDob() {
		return Dob;
	}

	/**
	 * Sets the dob.
	 *
	 * @param dob the new dob
	 */
	public void setDob(Date dob) {
		Dob = dob;
	}

	/**
	 * Gets the email id.
	 *
	 * @return the email id
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Sets the email id.
	 *
	 * @param emailId the new email id
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Gets the mob no.
	 *
	 * @return the mob no
	 */
	public String getMobNo() {
		return mobNo;
	}

	/**
	 * Sets the mob no.
	 *
	 * @param mobNo the new mob no
	 */
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.bean.DropdownListBean#getKey()
	 */
	public String getKey() {

		return "" + id;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.bean.DropdownListBean#getValue()
	 */
	public String getValue() {

		return firstName +""+ lastName;
	}

}
