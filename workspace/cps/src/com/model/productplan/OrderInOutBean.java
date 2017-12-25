package com.model.productplan;

public class OrderInOutBean {
	private int stockIn;
	private int stockOut;
	private String productPlanId;
	private String userid;
	private boolean isInStock;
	public int getStockIn() {
		return stockIn;
	}
	public void setStockIn(int stockIn) {
		this.stockIn = stockIn;
	}
	public int getStockOut() {
		return stockOut;
	}
	public void setStockOut(int stockOut) {
		this.stockOut = stockOut;
	}
	public String getProductPlanId() {
		return productPlanId;
	}
	public void setProductPlanId(String productPlanId) {
		this.productPlanId = productPlanId;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public boolean isInStock() {
		return isInStock;
	}
	public void setInStock(boolean isInStock) {
		this.isInStock = isInStock;
	}
	@Override
	public String toString() {
		return "OrderInOutBean [stockIn=" + stockIn + ", stockOut=" + stockOut
				+ ", productPlanId=" + productPlanId + ", userid=" + userid
				+ ", isInStock=" + isInStock + "]";
	}
	
	
	
	
}
