package com.action.menuTree;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.user.TSysUser;
import com.util.Constant;

public class MenuTreeAction extends BaseAction {

	private static final long serialVersionUID = -119224895098937459L;
	
	protected static final String MENU_CENTER_JSP   = "/pages/menuTree/menu_center.jsp";

	//菜单栏
	public String center() throws IOException{
		if(getRequest().getSession()!=null && getRequest().getSession().getAttribute(Constant.SESSION_USERID)!=null && !"".equals(getRequest().getSession().getAttribute(Constant.SESSION_USERID))){
			TSysUser loginUser = (TSysUser) getRequest().getSession().getAttribute("cps_user") ;
			if(loginUser.getFtype() != 0 && loginUser.getFtype() != 3){
				HttpServletRequest request =ServletActionContext.getRequest();
				String url = new String("/singlelogin.do?sessionid="+request.getSession().getId());
			    HttpServletResponse response = ServletActionContext.getResponse();
			    response.sendRedirect(url);
			    return null;
			}
		}
//		List<Menu> list=this.menuManager.findAll();
//		getRequest().setAttribute("menulist", list);
		getRequest().getSession().setAttribute("menu", getRequest().getParameter("menu"));
		return MENU_CENTER_JSP;
	}
	

}
