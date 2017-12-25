package Com.Controller.System;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Dao.System.IButtonDao;
import Com.Dao.System.IMainMenuDao;
import Com.Entity.System.Mainmenuitem;
import Com.Entity.System.Button;
import Com.Entity.System.Useronline;

@Controller
@RequestMapping("/Button")
public class ButtonController {
	@Resource
	private IButtonDao ButtonDao;
	@Resource
	private IMainMenuDao MainMenuDao;

	@RequestMapping("/GetButtonList")
	public String GetButtonList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fid = request.getParameter("fid");
		String hql = "";
		Mainmenuitem mainmenuitem = MainMenuDao.Query(fid);
		reponse.setCharacterEncoding("utf-8");
		try {
			if (mainmenuitem.isleaf()) {
				hql = " FROM Button where fparentid='" + fid + "' ";
				List<Button> sList = ButtonDao.QueryByHql(hql);
				reponse.getWriter().write(GetSysButtonJSON(sList));
			} else {
				hql = " FROM Mainmenuitem where fparentid='" + fid
						+ "' order by forder";
				List<Mainmenuitem> sList = ButtonDao.QueryByHql(hql);
				reponse.getWriter().write(GetMainmenuJSON(sList));
			}
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));

		}
		return null;

	}
	@RequestMapping("/Delpermission")
	public String Delpermission(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String PermissionID = request.getParameter("PermissionID");
		String Userid = request.getParameter("UserID");
		reponse.setCharacterEncoding("utf-8");
		try {
			ButtonDao.ExecDelPermission(PermissionID, Userid);
			reponse.getWriter().write(
					JsonUtil.result(true,"分配成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false,e.getMessage(), "", ""));
		}
		return null;

	}
	@RequestMapping("/Savepermission")
	public String Savepermission(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String PermissionID = request.getParameter("PermissionID");
		String Userid = request.getParameter("UserID");
		reponse.setCharacterEncoding("utf-8");
		try {
			ButtonDao.ExecSetPermission(PermissionID, Userid);
			reponse.getWriter().write(
					JsonUtil.result(true,"分配成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false,e.getMessage(), "", ""));
		}
		return null;

	}
	
	
	@RequestMapping("/GetButtonInfo")
	public String GetButtonInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			reponse.getWriter().write(
					JsonUtil.result(true, "", "",
							Button2Json(ButtonDao.Query(fid))));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));

		}
		return null;

	}
	@RequestMapping("/GetUserPermissionList")
	public String GetUnUserPermissionList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fid = request.getParameter("fid");
		String userid = request.getParameter("userid");
		String hql = "";
		Mainmenuitem mainmenuitem = MainMenuDao.Query(fid);
		reponse.setCharacterEncoding("utf-8");
		try {
			if (mainmenuitem.isleaf()) {
				hql = "SELECT t FROM Button t where fparentid='" + fid + "' and exists( FROM Userpermission t1 where t1.fresourcesid=t.fid and t1.fuserid='"+userid+"') ";
				List<Button> sList = ButtonDao.QueryByHql(hql);
				reponse.getWriter().write(GetSysButtonJSON(sList));
			} else {
				hql = "SELECT t FROM Mainmenuitem t where fparentid='" + fid+ "' and exists( FROM Userpermission t1 where t1.fresourcesid=t.fid and t1.fuserid='"+userid+"') "
						+ " order by forder";
				List<Mainmenuitem> sList = ButtonDao.QueryByHql(hql);
				reponse.getWriter().write(GetMainmenuJSON(sList));
			}
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));

		}
		return null;

	}
	
	
	
	@RequestMapping("/GetSysPermissionList")
	public String GetUserButtonList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fid = request.getParameter("fid");
		String userid = request.getParameter("userid");
		String hql = "";
		Mainmenuitem mainmenuitem = MainMenuDao.Query(fid);
		reponse.setCharacterEncoding("utf-8");
		try {
			if (mainmenuitem.isleaf()) {
				hql = "SELECT t FROM Button t where fparentid='" + fid + "' and not exists( FROM Userpermission t1 where t1.fresourcesid=t.fid and t1.fuserid='"+userid+"') ";
				List<Button> sList = ButtonDao.QueryByHql(hql);
				reponse.getWriter().write(GetSysButtonJSON(sList));
			} else {
				hql = "SELECT t FROM Mainmenuitem t where fparentid='" + fid+ "' and not exists( FROM Userpermission t1 where t1.fresourcesid=t.fid and t1.fuserid='"+userid+"') "
						+ " order by forder";
				List<Mainmenuitem> sList = ButtonDao.QueryByHql(hql);
				reponse.getWriter().write(GetMainmenuJSON(sList));
			}
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));

		}
		return null;

	}
	
	private String Button2Json(Button v)
	{
		String result = "{fid:\"" + v.getFid() + "\",fparentid:\""
				+ v.getFparentid()+ "\",fbuttonaction:\""
						+ v.getFbuttonaction()+ "\",fname:\""
				+ v.getFname()+ "\",fpath:\"" + v.getFpath() + "\",fbuttonid:\""+v.getFbuttonid()+"\"}";
		return result;
		
	}
	
	private String GetSysButtonJSON(List<Button> sList) {
		if (sList.size()<1)
		{
			return "";
		}
		String result = "[";
		int index = 0;
		for (Button info : sList) {
			if (index > 0) {
				result = result + ",";
			}
			result = result + "{fid:\"" + info.getFid() + "\",fparentid:\""
					+ info.getFparentid() + "\",fisleaf:2,fname:\""
					+ info.getFname()+ "\",fpath:\"" + info.getFpath() + "\",text:\"" + info.getFname()
					+ "\",leaf:true}";
			index++;
		}
		return result+"]";

	}

	private String GetMainmenuJSON(List<Mainmenuitem> sList) {
		if (sList.size()<1)
		{
			return "";
		}
		String result = "[";
		int index = 0;
		for (Mainmenuitem info : sList) {
			if (index > 0) {
				result = result + ",";
			}
			result = result + "{fid:\"" + info.getFid() + "\",forder:"
					+ info.getForder() + ",fparentid:\"" + info.getFparentid()
					+ "\",flevel:" + info.getFlevel()
					+ ",fisleaf:"
					+ info.getFisleaf() + ",fname:\"" + info.getFname()
					+ "\",text:\"" + info.getFname()+ "\",fpath:\"" + info.getFpath() + "\",furl:\""
					+ info.getFurl() + "\",leaf:false}";
			index++;
		}
		return result+"]";

	}

	@RequestMapping("/DelButton")
	public String DelButton(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		// command
		reponse.setCharacterEncoding("utf-8");
		try {
			ButtonDao.ExecDelButton(request);
			reponse.getWriter().write(JsonUtil.result(true, "删除成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	// MainMenuTree
	@RequestMapping("/SaveButton")
	public String SaveButton(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		request.setCharacterEncoding("utf-8");
		reponse.setCharacterEncoding("utf-8"); // command
		try {
			Button info = new Button();
			info.setFid(request.getParameter("fid"));
			info.setFparentid(request.getParameter("fparentid"));
			info.setFname(request.getParameter("fname"));
			info.setFbuttonaction(request.getParameter("fbuttonaction"));
			info.setFbuttonid(request.getParameter("fbuttonid"));
			info.setFpath(request.getParameter("fpath"));
			ButtonDao.ExecSave(info);
			reponse.getWriter().write(JsonUtil.result(true, "保存成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	@RequestMapping("/RecoverPermissions")
	public String RecoverPermissions(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		// command
		reponse.setCharacterEncoding("utf-8");
		String fid = request.getParameter("fid");
		String sql="";
		try {
				if(ButtonDao.QueryExistsBySql("select fid from t_sys_mainmenuitem where fid='"+fid+"' and flevel<=1"))//深度为2的节点不能回收权限
					throw new DJException("不能回收该节点权限");
			ButtonDao.ExecRecoverPermissions(fid);
			reponse.getWriter().write(JsonUtil.result(true, "回收权限成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
	 *       判断菜单，按钮权限  是否有权限查看
	 */
	@RequestMapping("/GetMainmenuAndButton")
	public String GetMainmenuAndButton(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		Useronline useronlineinfo = (Useronline) request.getSession()
				.getAttribute("Useronline");
		reponse.setCharacterEncoding("utf-8");
		String menufurl = request.getParameter("menufurl");// 菜单配置项
		String buttonaction = request.getParameter("buttonaction");// 权限按钮事件
		String Uiurl="";
		String msg=StringUtils.isEmpty(request.getParameter("msg"))?"您没有此权限":request.getParameter("msg");//错误提示消息
		try {
			String[] menuUrl = menufurl.split(",");
			if (!"3f6112db-a952-11e2-90fc-60a44c5bbef3".equals(useronlineinfo.getFuserid())) {//eas跳过
			for(int i=0;i<menuUrl.length;i++)//循环判断是否有权限，有则直接返回
			{
				
				if(getuserpermission(useronlineinfo.getFuserid(),menuUrl[i],""))
				{
					Uiurl=menuUrl[i];
					break;
				}
			}
			if("".equals(Uiurl))//遍历后没有权限，提示
			{
				throw new DJException(msg);
			}
			}
			Uiurl="".equals(Uiurl)?menuUrl[0]:Uiurl;
			reponse.getWriter().write(JsonUtil.result(true,Uiurl, "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	

	/**
	 * 查询是否有权限
	 * @param userid  用户信息
	 * @param menufurl 菜单权限；根据菜单的FURL判断
	 * @param buttonaction 权限控制；根据 buttonAction判断是会否有权限
	 * @return 是否有权限
	 */
	private Boolean getuserpermission(String userid,String menufurl,String buttonaction)
	{
		String sql="";
		if(!StringUtils.isEmpty(menufurl))//菜单项是否存在，是否有权限
		{
			sql = "SELECT t.fid  FROM t_sys_mainmenuitem t where t.furl like '%"
					+ menufurl
					+ "%' and exists(select t1.fresourcesid FROM t_sys_userpermission t1 where t1.fresourcesid=t.fid and (t1.fuserid='"
					+ userid
					+ "' or t1.fuserid in (select t2.froleid from t_sys_userrole t2 where t2.fuserid='"
					+ userid + "'))) ";
			if (!ButtonDao.QueryExistsBySql(sql))
			{
				return  false;
			}
		}
		if (!StringUtils.isEmpty(buttonaction))// 按钮事件是否存在是否有权限
			{
			sql = "select fid from t_sys_button where fbuttonaction like '%"+ buttonaction + "%'";
					if (ButtonDao.QueryExistsBySql(sql)) {
					sql = "select t_sys_userpermission.fid from t_sys_userpermission inner join t_sys_button  on t_sys_userpermission.fresourcesid=t_sys_button.fid where  (t_sys_userpermission.fuserid='"
							+ userid
							+ "' or t_sys_userpermission.fuserid in (select froleid from t_sys_userrole where fuserid='"
							+ userid
							+ "')) and t_sys_button.fbuttonaction like'%"
							+ buttonaction + "%'";
					if (!ButtonDao.QueryExistsBySql(sql)) {
						return false;
					}
				}
			}
		return true;
	}
	
	
}
