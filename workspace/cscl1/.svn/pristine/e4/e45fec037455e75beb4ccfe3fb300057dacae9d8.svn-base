package com.pc.model;


import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_Rating  {
		protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	//alias
		public static final String TABLE_ALIAS = "评价表";
		public static final String ALIAS_ID = "自增主键";
		public static final String ALIAS_DRIVERNAME = "司机名字";
		public static final String ALIAS_CARNUM = "司机车牌号";
		public static final String ALIAS_ORDERNUM="订单号";
		public static final String ALIAS_ESTIMATE="好评差评(1:好评 2：差评)";
		public static final String ALIAS_REMARK="备注";
		public static final String ALIAS_ESTIME="评价时间";
		public static final String ALIAS_SERVICE="服务态度";
		public static final String ALIAS_TIMELINESS="时效性";
		public static final String ALIAS_COMPLETE="货物完整情况";
		public static final String ALIAS_RATING_TYPE="评价表类型 （0:货主对司机评价 1:司机对货主评价）";

		private java.lang.Integer id;
		private java.lang.String name;
		private java.lang.String carNum;
		private java.lang.String orderNum;
		private java.lang.Integer estimate;
		private java.lang.String remark;
		private java.util.Date esTime;
		private java.math.BigDecimal service;
		private java.math.BigDecimal timeliness;
		private java.math.BigDecimal complete;
		private java.lang.Integer ratingType; // 评价类型 0是货主对司机 1是司机对货主；
		private java.math.BigDecimal  ratingScore; // 司机对货主评价 总分；
		 //====非数据字段==/
		
		private java.lang.Integer orderid;
		private java.lang.String number;
		
		public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm";
		
		public String getCreateTimeString() {
			return DateConvertUtils.format(getEsTime(), FORMAT_CREATE_TIME);
		}
		public void setCreateTimeString(String value) {
			setEsTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
		}
		
		public java.lang.Integer getOrderid() {
			return orderid;
		}
		public void setOrderid(java.lang.Integer orderid) {
			this.orderid = orderid;
		}
		public java.lang.String getNumber() {
			return number;
		}
		public void setNumber(java.lang.String number) {
			this.number = number;
		}
		public java.util.Date getEsTime() {
			return this.esTime;
		}
		public void setEsTime(java.util.Date value) {
			this.esTime = value;
		}
 
		public java.lang.Integer getRatingType() {
			return ratingType;
		}
		public void setRatingType(java.lang.Integer ratingType) {
			this.ratingType = ratingType;
		}
		public java.math.BigDecimal getRatingScore() {
			return ratingScore;
		}
		public void setRatingScore(java.math.BigDecimal ratingScore) {
			this.ratingScore = ratingScore;
		}
		public java.lang.Integer getId() {
			return id;
		}
		public void setId(java.lang.Integer id) {
			this.id = id;
		}
	 
		public java.lang.String getName() {
			return name;
		}
		public void setName(java.lang.String name) {
			this.name = name;
		}
		public java.lang.String getOrderNum() {
			return orderNum;
		}
		public void setOrderNum(java.lang.String orderNum) {
			this.orderNum = orderNum;
		}
		public java.lang.Integer getEstimate() {
			return estimate;
		}
		public void setEstimate(java.lang.Integer estimate) {
			this.estimate = estimate;
		}
		public java.lang.String getRemark() {
			return remark;
		}
		public void setRemark(java.lang.String remark) {
			this.remark = remark;
		}
	 
		public java.math.BigDecimal getService() {
			return service;
		}
		public void setService(java.math.BigDecimal service) {
			this.service = service;
		}
		public java.math.BigDecimal getTimeliness() {
			return timeliness;
		}
		public void setTimeliness(java.math.BigDecimal timeliness) {
			this.timeliness = timeliness;
		}
		public java.math.BigDecimal getComplete() {
			return complete;
		}
		public void setComplete(java.math.BigDecimal complete) {
			this.complete = complete;
		}
		public java.lang.String getCarNum() {
			return carNum;
		}
		public void setCarNum(java.lang.String carNum) {
			this.carNum = carNum;
		}
	
}

