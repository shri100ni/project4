package in.co.rays.bean;

/**
 * The Class MarksheetBean.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class MarksheetBean extends BaseBean {
	
	/** The roll no. */
	private String rollNo;
	
	/** The student id. */
	private long studentId;
	
	/** The name. */
	private String name;
	
	/** The physics. */
	private Integer physics;
	
	/** The chemistry. */
	private Integer chemistry;
	
	/** The maths. */
	private Integer maths;

	/**
	 * Gets the roll no.
	 *
	 * @return the roll no
	 */
	public String getRollNo() {
		return rollNo;
	}

	/**
	 * Sets the roll no.
	 *
	 * @param rollNo the new roll no
	 */
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	/**
	 * Gets the student id.
	 *
	 * @return the student id
	 */
	public long getStudentId() {
		return studentId;
	}

	/**
	 * Sets the student id.
	 *
	 * @param studentId the new student id
	 */
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the physics.
	 *
	 * @return the physics
	 */
	public Integer getPhysics() {
		return physics;
	}

	/**
	 * Sets the physics.
	 *
	 * @param physics the new physics
	 */
	public void setPhysics(Integer physics) {
		this.physics = physics;
	}

	/**
	 * Gets the chemistry.
	 *
	 * @return the chemistry
	 */
	public Integer getChemistry() {
		return chemistry;
	}

	/**
	 * Sets the chemistry.
	 *
	 * @param chemistry the new chemistry
	 */
	public void setChemistry(Integer chemistry) {
		this.chemistry = chemistry;
	}

	/**
	 * Gets the maths.
	 *
	 * @return the maths
	 */
	public Integer getMaths() {
		return maths;
	}

	/**
	 * Sets the maths.
	 *
	 * @param maths the new maths
	 */
	public void setMaths(Integer maths) {
		this.maths = maths;
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
		return rollNo;
	}

}
