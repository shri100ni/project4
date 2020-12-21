package in.co.rays.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class BaseBean.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public abstract class BaseBean implements Serializable, DropdownListBean, Comparable<BaseBean> {
	
	/** The id. */
	protected long id;
	
	/** The created by. */
	protected String createdBy;
	
	/** The modified by. */
	protected String modifiedBy;
	
	/** The created datetime. */
	protected Timestamp createdDatetime;
	
	/** The modified datetime. */
	protected Timestamp modifiedDatetime;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the modified by.
	 *
	 * @return the modified by
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets the modified by.
	 *
	 * @param modifiedBy the new modified by
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Gets the created datetime.
	 *
	 * @return the created datetime
	 */
	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	/**
	 * Sets the created datetime.
	 *
	 * @param createdDatatime the new created datetime
	 */
	public void setCreatedDatetime(Timestamp createdDatatime) {
		this.createdDatetime = createdDatatime;
	}

	/**
	 * Gets the modified datetime.
	 *
	 * @return the modified datetime
	 */
	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	/**
	 * Sets the modified datetime.
	 *
	 * @param modifiedDatetime the new modified datetime
	 */
	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
