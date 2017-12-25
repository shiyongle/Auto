package com.pc.util.pay;

/**
 * 
 * @author 小千
 * @date 2016年12月23日上午12:02:44
 */
public class OrderMsg {
	private String payState; //订单状态  1：成功  2： 处理中 3：失败
	private String serviceProvider;//支付公司 0:余额 1：bank 2：微信 3：阿里
	private String serviceProviderType; //第三方支付方式 0:余额操作 1：PAB  2：CMB 3：SDK 4：QR 
	private String order; //业务订单号
	private String payOrder;//支付订单号
	private Integer userId;//取缓存中的用户id
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	private String errorMsg;//支付失败 信息
	
	public String getErrorMsg() {
		if(errorMsg==null)
			errorMsg="未知错误！";
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public String getServiceProviderType() {
		return serviceProviderType;
	}
	public void setServiceProviderType(String serviceProviderType) {
		this.serviceProviderType = serviceProviderType;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getPayOrder() {
		return payOrder;
	}
	public void setPayOrder(String payOrder) {
		this.payOrder = payOrder;
	}
	@Override
	public String toString() {
		return "OrderMsg [payState=" + payState + ", serviceProvider=" + serviceProvider + ", serviceProviderType="
				+ serviceProviderType + ", order=" + order + ", payOrder=" + payOrder + "]";
	}
	
	
	
}
