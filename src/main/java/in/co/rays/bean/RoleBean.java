package in.co.rays.bean;

/**
 * The Class RoleBean.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class RoleBean extends BaseBean{
	
	/** The Constant ADMIN. */
	public static final int ADMIN=1;
	
	/** The Constant STUDENT. */
	public static final int STUDENT=2;
	
	/** The Constant COLLEGE_SCHOOL. */
	public static final int COLLEGE_SCHOOL=3;
	
	/** The Constant KIOSK. */
	public static final int KIOSK=4;
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;

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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
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
		return name;
	}
	

}
