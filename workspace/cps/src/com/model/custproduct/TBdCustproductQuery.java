package com.model.custproduct;

public class TBdCustproductQuery {
	
	private String supplierId;  /*** 制造商*/
	private int fiscommon;  /*** false 不是，true 是*/
	private String searchKey;   /*** 关键字*/

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public int getFiscommon() {
		return fiscommon;
	}

	public void setFiscommon(int fiscommon) {
		this.fiscommon = fiscommon;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	

	
}
