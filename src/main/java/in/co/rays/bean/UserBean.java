package in.co.rays.bean;

import java.sql.Timestamp;
import java.util.Date;

/**
 * The Class UserBean.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class UserBean extends BaseBean {
	
	/** The Constant ACTIVE. */
	public static final String ACTIVE = "Active";
	
	/** The Constant INACTIVE. */
	public static final String INACTIVE = "Inactive";
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The login. */
	private String login;
	
	/** The password. */
	private String password;
	
	/** The confirm password. */
	private String confirmPassword;
	
	/** The dob. */
	private Date dob;
	
	/** The mobile no. */
	private String mobileNo;
	
	/** The role id. */
	private long roleId;
	
	/** The un successful login. */
	private int unSuccessfulLogin;
	
	/** The gender. */
	private String gender;
	
	/** The last login. */
	private Timestamp lastLogin;
	
	/** The lock. */
	private String lock = INACTIVE;
	
	/** The registered IP. */
	private String registeredIP;
	
	/** The last login IP. */
	private String lastLoginIP;

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
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the confirm password.
	 *
	 * @return the confirm password
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Sets the confirm password.
	 *
	 * @param confirmPassword the new confirm password
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Gets the dob.
	 *
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the dob.
	 *
	 * @param dob the new dob
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Gets the mobile no.
	 *
	 * @return the mobile no
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile no.
	 *
	 * @param mobileNo the new mobile no
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

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
	 * Gets the un successful login.
	 *
	 * @return the un successful login
	 */
	public int getUnSuccessfulLogin() {
		return unSuccessfulLogin;
	}

	/**
	 * Sets the un successful login.
	 *
	 * @param unSuccessfulLogin the new un successful login
	 */
	public void setUnSuccessfulLogin(int unSuccessfulLogin) {
		this.unSuccessfulLogin = unSuccessfulLogin;
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
	 * Gets the last login.
	 *
	 * @return the last login
	 */
	public Timestamp getLastLogin() {
		return lastLogin;
	}

	/**
	 * Sets the last login.
	 *
	 * @param lastLogin the new last login
	 */
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * Gets the lock.
	 *
	 * @return the lock
	 */
	public String getLock() {
		return lock;
	}

	/**
	 * Sets the lock.
	 *
	 * @param lock the new lock
	 */
	public void setLock(String lock) {
		this.lock = lock;
	}

	/**
	 * Gets the registered IP.
	 *
	 * @return the registered IP
	 */
	public String getRegisteredIP() {
		return registeredIP;
	}

	/**
	 * Sets the registered IP.
	 *
	 * @param registeredIP the new registered IP
	 */
	public void setRegisteredIP(String registeredIP) {
		this.registeredIP = registeredIP;
	}

	/**
	 * Gets the last login IP.
	 *
	 * @return the last login IP
	 */
	public String getLastLoginIP() {
		return lastLoginIP;
	}

	/**
	 * Sets the last login IP.
	 *
	 * @param lastLoginIP the new last login IP
	 */
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}


	/* (non-Javadoc)
	 * @see in.co.rays.bean.DropdownListBean#getKey()
	 */
	public String getKey() {
		return id + "";
	}

	/* (non-Javadoc)
	 * @see in.co.rays.bean.DropdownListBean#getValue()
	 */
	public String getValue() {
		return login ;
	}

}
