package com.pc.model;

import java.math.BigDecimal;
import java.util.Date;


public class Util_Option  {
	
	private Integer id;
	private Integer optionId;
	private String optionName;
	private String optionCarTypeName;
	public String getOptionCarTypeName() {
		return optionCarTypeName;
	}

	public void setOptionCarTypeName(String optionCarTypeName) {
		this.optionCarTypeName = optionCarTypeName;
	}

	public Integer getOptionFc() {
		return optionFc;
	}

	public void setOptionFc(Integer optionFc) {
		this.optionFc = optionFc;
	}

	public String getOptionCarOtherName() {
		return optionCarOtherName;
	}

	public void setOptionCarOtherName(String optionCarOtherName) {
		this.optionCarOtherName = optionCarOtherName;
	}

	private String optionCarOtherName;
	private String optionVal;
	private Date optionDate;
	private BigDecimal optionNum;
	private Integer optionFc;  //强制跟新
	private String optionWe;//核载重量（吨）

 
	public String getOptionWe() {
		return optionWe;
	}

	public void setOptionWe(String optionWe) {
		this.optionWe = optionWe;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionVal() {
		return optionVal;
	}

	public void setOptionVal(String optionVal) {
		this.optionVal = optionVal;
	}

	public Date getOptionDate() {
		return optionDate;
	}

	public void setOptionDate(Date optionDate) {
		this.optionDate = optionDate;
	}

	public BigDecimal getOptionNum() {
		return optionNum;
	}

	public void setOptionNum(BigDecimal optionNum) {
		this.optionNum = optionNum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
