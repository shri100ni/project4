package in.co.rays.bean;

/**
 * The Class CourseBean.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class CourseBean extends BaseBean{
	
	/** The Name. */
	private String Name;
	
	/** The description. */
	private String description;

	/** The duration. */
	
	private String duration;
	
	
	
	 /**
 	 * Gets the name.
 	 *
 	 * @return the name
 	 */
	
	
	public String getName() {
		return Name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		Name = name;
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

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	

	/* (non-Javadoc)
	 * @see in.co.rays.bean.DropdownListBean#getKey()
	 */
	public String getKey() {
		
		return ""+id;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.bean.DropdownListBean#getValue()
	 */
	public String getValue() {
		
		return Name;
	}



}
