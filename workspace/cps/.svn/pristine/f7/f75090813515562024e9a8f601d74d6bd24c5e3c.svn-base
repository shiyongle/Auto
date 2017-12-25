package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.model.user.TSysUser;
import com.opensymphony.xwork2.ActionSupport;
import com.service.user.TSysUserManager;
import com.util.Constant;
import com.util.Params;
/**
 * 基本Action对象，其它Action的父类
 */
public class BaseAction extends ActionSupport implements RequestAware,SessionAware, ApplicationAware {
	@Autowired
	private TSysUserManager userManager;
	private static final long serialVersionUID = 1L;
	protected String id;
	protected String[] ids;
	protected int pageNo = 1;
	protected int pageSize = 10;
	protected Object[] queryParams;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Object[] getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Object[] queryParams) {
		this.queryParams = queryParams;
	}


	public static final String INDEX = "index";
	public static final String REG = "reg";//注册
	public static final String REG_SUCCESS = "reg_success";//注册成功
	public static final String LOGIN = "login";//登录
	public static final String UNPWD = "unpwd";//忘记密码
	public static final String LOGOUT = "logout";
	
	protected Map<String, Object> request;
	protected Map<String, Object> session;
	protected Map<String, Object> application;
	
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	public Object getRequestAttribute(String key) {
		return getRequest().getAttribute(key);
	}
	
	public void setRequestAttribute(String key,Object value){
		getRequest().setAttribute(key, value);
	}
	
	@SuppressWarnings("finally")
	protected String writeAjaxResponse(String ajaxString){
		try {
			getResponse().setContentType("text/html;charset=utf-8");
			PrintWriter out = getResponse().getWriter();
	        out.write(ajaxString);
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			return null;
		}
	}
	
	/**
	 * 用户的"只查看自己"过滤方法;
	 * **/
	public String QueryFilterByUserofuser(String field,String fmark){
		String userid = ServletActionContext.getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		TSysUser userinfo = this.userManager.get(userid);
		String strsql="";
		String sfmark="";
		if(StringUtils.isEmpty(fmark))
		{
			sfmark=" and "; 
		}else
		{
			sfmark=fmark;
		}
		if(userinfo.getFisreadonly()==0) return "";
		if(field!=null && !field.equals(""))
		{
			Params p = new Params();
			String sqls = "select quote(fuserid) userid from t_bd_usertouser where fsuperuserid=:userid";
			p.put("userid", userid);
			List<HashMap<String, Object>> demandList = userManager.QueryBySql(sqls,p);
			if(demandList.size()!=0){//关联用户过滤
				StringBuilder userids=new StringBuilder("'"+userid+"'");
				for(HashMap<String, Object> m : demandList){
					userids.append(","+m.get("userid"));
				}
				strsql = field +" in ("+userids+")";
			}else{
				strsql = field+ " = '"+userid+"'";
			}
		}
		if(strsql.equals("")){
			return "";
		}
		return " "+sfmark +" ("+strsql+")";
	}
	
}
