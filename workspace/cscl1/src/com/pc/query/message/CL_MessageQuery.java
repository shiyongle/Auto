package com.pc.query.message;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.pc.query.aBase.BaseQuery;

public class CL_MessageQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Integer id;
	/** 发送内容 */
	private java.lang.String content;
	/** 类型 */
	private java.lang.Integer type;
	/** 接收人 */
	private java.lang.Integer receiver;
	private String receiverPhone;
	private Integer fbusinessType;
	/** 创建人 */
	private java.lang.Integer creator;
	/** 创建时间 */
    private java.lang.String title;
	/** 标题*/
	private java.lang.String searchCreator;
	private java.lang.String searchReceiver;
	private List<String> fids;
	
	private String fusername;

    public Integer getFbusinessType() {
		return fbusinessType;
	}

	public void setFbusinessType(Integer fbusinessType) {
		this.fbusinessType = fbusinessType;
	}

	public String getFusername() {
		return fusername;
	}

	public void setFusername(String fusername) {
		this.fusername = fusername;
	}

	public List<String> getFids() {
		return fids;
	}

	public void setFids(List<String> fids) {
		this.fids = fids;
	}

	public java.lang.String getSearchCreator() {
		return searchCreator;
	}

	public void setSearchCreator(java.lang.String searchCreator) {
		this.searchCreator = searchCreator;
	}

	public java.lang.String getSearchReceiver() {
		return searchReceiver;
	}

	public void setSearchReceiver(java.lang.String searchReceiver) {
		this.searchReceiver = searchReceiver;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

 
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;

	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContent(java.lang.String value) {
		this.content = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getReceiver() {
		return this.receiver;
	}
	
	public void setReceiver(java.lang.Integer value) {
		this.receiver = value;
	}
	
	public java.lang.Integer getCreator() {
		return this.creator;
	}
	
	public void setCreator(java.lang.Integer value) {
		this.creator = value;
	}
	
	public java.util.Date getCreateTimeBegin() {
		return this.createTimeBegin;
	}
	
	public void setCreateTimeBegin(java.util.Date value) {
		this.createTimeBegin = value;
	}	
	
	public java.util.Date getCreateTimeEnd() {
		return this.createTimeEnd;
	}
	
	public void setCreateTimeEnd(java.util.Date value) {
		if(value != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(value);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.createTimeEnd = calendar.getTime();
		}else {
			this.createTimeEnd = value;
		}
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

