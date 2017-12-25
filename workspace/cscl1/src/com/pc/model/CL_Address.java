package com.pc.model;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CL_Address  {
	
	//alias
	public static final String TABLE_ALIAS = "地址表";
	public static final String ALIAS_ID = "自增主键";
	public static final String ALIAS_ADDRESS_NAME = "详细地址";
	public static final String ALIAS_LINK_MAN = "联系人";
	public static final String ALIAS_LINK_PHONE = "联系人电话";
	public static final String ALIAS_TYPE = "类型，1：收货人,2:发货人";
	
	private java.lang.Integer id;
	private java.lang.Integer userRoleId;
	private java.lang.String addressName;
	private java.lang.String linkMan;
	private java.lang.String linkPhone;
	private java.lang.Integer type;
	private java.lang.String latitude;
	private java.lang.String longitude;
	private java.util.Date createTime;	//创建时间
	private java.util.Date lastEditTime; //最后一次编辑的时间

	public CL_Address(){}

	public CL_Address(java.lang.Integer id){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public java.lang.Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(java.lang.Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public void setAddressName(java.lang.String value) {
		this.addressName = value;
	}
	
	public java.lang.String getAddressName() {
		return this.addressName;
	}
	public void setLinkMan(java.lang.String value) {
		this.linkMan = value;
	}
	
	public java.lang.String getLinkMan() {
		return this.linkMan;
	}
	public void setLinkPhone(java.lang.String value) {
		this.linkPhone = value;
	}
	
	public java.lang.String getLinkPhone() {
		return this.linkPhone;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
	public java.lang.String getLatitude() {
		return latitude;
	}

	public void setLatitude(java.lang.String latitude) {
		this.latitude = latitude;
	}

	public java.lang.String getLongitude() {
		return longitude;
	}

	public void setLongitude(java.lang.String longitude) {
		this.longitude = longitude;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(java.util.Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AddressName",getAddressName())
			.append("LinkMan",getLinkMan())
			.append("LinkPhone",getLinkPhone())
			.append("Type",getType())
			.append("latitude",getLatitude())
			.append("longitude",getLongitude())
			.toString();
	}
	
	public String toString(String separator) {
		StringBuffer sb = new StringBuffer();
			sb.append(ALIAS_ID+":").append(id).append(separator);
			sb.append(ALIAS_ADDRESS_NAME+":").append(addressName).append(separator);
			sb.append(ALIAS_LINK_MAN+":").append(linkMan).append(separator);
			sb.append(ALIAS_LINK_PHONE+":").append(linkPhone).append(separator);
			sb.append(ALIAS_TYPE+":").append(type).append(separator);
		return sb.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CL_Address == false) return false;
		if(this == obj) return true;
		CL_Address other = (CL_Address)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

