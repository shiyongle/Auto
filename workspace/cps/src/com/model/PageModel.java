package com.model;

import java.util.List;
/**
 * 分页组件
 * @param <T> 实体对象
 */
public class PageModel<T> {
	private int totalRecords;   /*** 总记录数*/
	private List<T> list;	    /*** 结果集*/
	private int pageNo;  		/*** 当前页*/
	private int pageSize;       /*** 每页显示多少条*/
	
	
	
	
	

	
	/*** 取得总页数*/
	public int getTotalPages() {
		return (totalRecords + pageSize - 1) / pageSize;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
