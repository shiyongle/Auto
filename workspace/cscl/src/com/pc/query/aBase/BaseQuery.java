package com.pc.query.aBase;

import org.apache.log4j.Logger;

import cn.org.rapid_framework.page.PageRequest;

@SuppressWarnings("rawtypes")
public class BaseQuery extends PageRequest implements java.io.Serializable {
	
	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-12-11 下午4:55:04
	*/
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(BaseQuery.class);
	
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final String QUERY_LIKE = "1";
	public static final String QUERY_EQUAL = "0";
	//查询类型：精确查询、模糊查询
	private String queryType = QUERY_EQUAL;
	
    static {
        log.info("BaseQuery.DEFAULT_PAGE_SIZE="+DEFAULT_PAGE_SIZE);
    }
    
	public BaseQuery() {
		setPageSize(DEFAULT_PAGE_SIZE);
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	  
}