package Com.Controller.System;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ServerContext;
import Com.Dao.System.IMainMenuDao;
import Com.Dao.System.ISysUserDao;
import Com.Dao.order.ICusDeliversDao;
import Com.Entity.System.Mainmenuitem;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;
import Com.Entity.System.Userpermission;
import cn.org.rapid_framework.page.Page;

import com.dongjing.api.*;
import com.google.gson.JsonObject;
import com.pc.model.CL_Factory;
import com.pc.query.factory.FactoryQuery;
import com.pc.util.JSONUtil;

@Controller
public class MainmenuController {
	protected static final String LIST_JSP = "/pages/pc/system/refuse_list.jsp";
	protected static final String CREATE_JSP = "/pages/pc/factory/refuse_create.jsp";
	protected static final String EDIT_JSP = "/pages/pc/factory/refuse_edit.jsp";
	
	@Resource
	private ISysUserDao SysUserDao;
	@Resource
	private IMainMenuDao MainMenuDao;
	@Resource
	private ICusDeliversDao CusDeliversDao;

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		return "index";
	}

	@RequestMapping("/download")
	public String download(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			reponse.setCharacterEncoding("utf-8");
			String type = request.getParameter("type");
			String filepath = "";
			if (type.length() <= 0) {
				throw new DJException("参数不对");
			} else if (type.equals("cert")) {
				reponse.setHeader("Content-Disposition",
						"attachment;fileName=dongjing.csr");
				filepath = request.getRealPath("/") + "cert\\dongjing.csr";
			} else if (type.equals("winjre32")) {
				reponse.setHeader("Content-Disposition",
						"attachment;fileName=jre-7u51-windows-i586.exe");
				filepath = request.getRealPath("/")
						+ "tools\\jre-7u51-windows-i586.exe";
			} else if (type.equals("winjre64")) {
				reponse.setHeader("Content-Disposition",
						"attachment;fileName=jre-7u51-windows-x64.exe");
				filepath = request.getRealPath("/")
						+ "tools\\jre-7u51-windows-x64.exe";
			} else if (type.equals("certmanual")) {
				//response.addHeader("Content-Length", "" + file.length());
//				reponse.setHeader("Content-Disposition",
//						"attachment;fileName="+new String("安全控件安装操作手册.doc".getBytes("gbk"),
//								"iso-8859-1"));
//				filepath = request.getRealPath("/") + "Manual\\安全控件安装操作手册.doc";
				reponse.setHeader("Content-Disposition",
				"attachment;fileName="+new String("东经智能协同平台控件安装包.zip".getBytes("gbk"),
						"iso-8859-1"));
		         filepath = request.getRealPath("/") + "Manual\\东经智能协同平台控件安装包.zip";
			}
			File file = new File(filepath);
			reponse.addHeader("Content-Length", "" + file.length()); 
			reponse.setContentType("multipart/form-data");
			InputStream inputStream = new FileInputStream(filepath);
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				reponse.getOutputStream().write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	/*@RequestMapping("/test")
	public String test(HttpServletRequest request) {
		try
		{
			CusDeliversDao.ExecImportcusdelivers();
		}
		catch(Exception e)
		{
			System.out.print(e.getMessage());
		}
		
		return "test";

	}*/

	@RequestMapping("/test1")
	public String test1(HttpServletRequest request, HttpServletResponse reponse)
			throws IOException {
		DongjingClient c=new DongjingClient();
		c.setURL("http://202.107.222.99:8066/ichat/cgi");
		c.setMethod("user_detailed");
		c.setRequestProperty("account", "71");
		if(c.GetData())
		{
		  String r=	c.getResponse().getResultString();
		}
		return "test1";

	}
	
	@RequestMapping("/uploadStatic")
	public String uploadStatic(HttpServletRequest request) {
		return "uploadStatic"; 

	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse reponse)
			throws IOException {
		String reqCode = request.getParameter("reqCode");
		String sessionid = request.getSession().getId();

		if (reqCode != null && "logout".equals(reqCode) && sessionid != null
				&& !sessionid.isEmpty()) {
			String sql = "delete from t_sys_useronline where fsessionid='"
					+ sessionid + "'";
			MainMenuDao.ExecBySql(sql);
			
			//20151020同步清除Session缓存;
			ServerContext.delUseronline(sessionid);
		}
//		reponse.sendRedirect("/index.jsp");
		reponse.sendRedirect("/vmi/index.jsp");
//		return "login";
		return null;
	}

	@RequestMapping("/test2")
	public String test2(HttpServletRequest request, HttpServletResponse reponse)
			throws IOException {
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write("{success:true,data:\"asdf'\"\"}"); // URLEncoder.encode(
		return null;

	}

	@RequestMapping("/GetMainMenu")
	public String GetMainMenu(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fid = request.getParameter("fid");
		String result = "{success:true,data:";
		String hql = " FROM Mainmenuitem where fid='" + fid
				+ "' order by forder";
		List<Mainmenuitem> sList = MainMenuDao.QueryMainmenuItemcls(hql);
		reponse.setCharacterEncoding("utf-8");
		result = result + GetJSON(sList) + "}";
		reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping("/MainMenuPanel")
	public String MainMenuPanel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		Useronline useronlineinfo = (Useronline) request.getSession()
				.getAttribute("Useronline");
		String result = "{success:true,menus: [";
		// String sql =
		// "SELECT fid,fname,fisleaf,furl FROM t_sys_mainmenuitem where flevel=0 order by forder";
		// String hql =
		// "SELECT t  FROM Mainmenuitem t where t.fparentid='a51803d8-a65d-11e2-a596-60a44c5bbef3' and exists( FROM Userpermission t1 where t1.fresourcesid=t.fid and t1.fuserid='"+useronlineinfo.getFuserid()+"') "
		String hql = "";
		if (useronlineinfo.getFusername().equals("EAS")) {
			hql = "SELECT t FROM Mainmenuitem t where fparentid='a51803d8-a65d-11e2-a596-60a44c5bbef3' order by forder";
		} else {
			hql = "SELECT t  FROM Mainmenuitem t where t.fparentid='a51803d8-a65d-11e2-a596-60a44c5bbef3' and exists( FROM Userpermission t1 where t1.fresourcesid=t.fid  and (t1.fuserid='"
					+ useronlineinfo.getFuserid()
					+ "' or t1.fuserid in (select t2.froleid from UserRole t2 where t2.fuserid='"
					+ useronlineinfo.getFuserid()
					+ "'))) "
					+ " order by forder";
		}
		List<Mainmenuitem> sList = MainMenuDao.QueryMainmenuItemcls(hql);
		reponse.setCharacterEncoding("utf-8");
		result = result + GetJSON(sList) + "]}";
		reponse.getWriter().write(result);
		return null;

	}

	// MainMenuTree
	@RequestMapping("/MainMenuTree")
	public String MainMenuTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		Useronline useronlineinfo = (Useronline) request.getSession()
				.getAttribute("Useronline");
		request.setCharacterEncoding("utf-8");
		String fparentid = request.getParameter("fid");
		String result = "{children: [";
		// String hql = " FROM Mainmenuitem where fparentid='" + fparentid
		// + "' order by forder";
		String hql = "";
		if (useronlineinfo.getFusername().equals("EAS")) {
			hql = "SELECT t FROM Mainmenuitem t where fparentid='" + fparentid
					+ "' order by forder";
		} else {
			hql = "SELECT t FROM Mainmenuitem t where fparentid='"
					+ fparentid
					+ "' and exists( FROM Userpermission t1 where t1.fresourcesid=t.fid   and (t1.fuserid='"
					+ useronlineinfo.getFuserid()
					+ "' or t1.fuserid in (select t2.froleid from UserRole t2 where t2.fuserid='"
					+ useronlineinfo.getFuserid() + "'))) "
					+ " order by forder";
		}
		List<Mainmenuitem> sList = MainMenuDao.QueryMainmenuItemcls(hql);
		
		//2015-10-08新增新旧版本的UI切换功能
		uiEditionChange(request,useronlineinfo.getFuserid(),sList);
		
		reponse.setCharacterEncoding("utf-8");
		result = result + GetJSON(sList) + "]}";
		reponse.getWriter().write(result);
		return null;
	}

	//2015-10-08新增新旧版本的UI切换功能
	private void uiEditionChange(HttpServletRequest request, String userId,List<Mainmenuitem> sList) {
		// TODO Auto-generated method stub
		HashMap<String, String> uiEditionMap = getEditionMap(request);
		String FoldUI="",FnewUI="",Fuserid="";
		for(int k=0;k<sList.size();k++){
				Mainmenuitem m = sList.get(k);
				if( uiEditionMap.size()==0){
						List<HashMap<String, Object>> lists = MainMenuDao.QueryBySql("select Fid,FoldUI,FnewUI,Fuserid from t_sys_UIEditionChange ");
						for(int j=0;j<lists.size();j++){
							HashMap<String, Object> uiEdition = lists.get(j);
							FoldUI = uiEdition.get("FoldUI").toString();
							FnewUI = uiEdition.get("FnewUI").toString();
							Fuserid = uiEdition.get("Fuserid").toString();
							if(FoldUI.equals(m.getFurl()) && Fuserid.equals(userId)){
								m.setFurl(FnewUI);
							}
							uiEditionMap.put(Fuserid+FoldUI, FnewUI);
							request.getServletContext().setAttribute("uiEditionMap", uiEditionMap);
						}
				}else{
								if(uiEditionMap.containsKey(userId+m.getFurl())){
									m.setFurl(uiEditionMap.get(userId+m.getFurl()));
								}
				}
		}
	}
	
	//2015-10-08保存新版切换记录接口
	@RequestMapping(value="/saveUIEditionChange")
	public String saveUIEditionChange(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try{
				HashMap<String, String> uiEditionMap = getEditionMap(request);
				
				String userId = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
				String FoldUI = request.getParameter("FoldUI");
				String FnewUI=request.getParameter("FnewUI");
				String insertsql="";
				
				List<HashMap<String, Object>> lists = MainMenuDao.QueryBySql("select fid from t_sys_UIEditionChange where FoldUI = '"+FoldUI+"' and Fuserid = '"+userId+"' ");
				if(lists.size()>0){
					MainMenuDao.ExecBySql("delete from t_sys_UIEditionChange where FoldUI = '"+FoldUI+"' and Fuserid = '"+userId+"' ");
					
					if(uiEditionMap.containsKey(userId+FoldUI)){
						uiEditionMap.remove(userId+FoldUI);
					}
					response.getWriter().write(JsonUtil.result(true, "操作成功！","",""));
				}else{
					insertsql="INSERT INTO t_sys_UIEditionChange (Fid,FoldUI,FnewUI,Fuserid,Fcreatetime ) VALUES( ";
					insertsql=insertsql+"'"+MainMenuDao.CreateUUid()+"',";
					insertsql=insertsql+"'"+FoldUI+"',";
					insertsql=insertsql+"'"+FnewUI+"',";
					insertsql=insertsql+"'"+userId+"',";
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
					insertsql=insertsql+"'"+df.format(new Date())+"')";
					MainMenuDao.ExecBySql(insertsql);
					
					uiEditionMap.put(userId+FoldUI, FnewUI);
					request.getServletContext().setAttribute("uiEditionMap", uiEditionMap);
					response.getWriter().write(JsonUtil.result(true, "操作成功！","",""));
				}
				
		}catch(Exception e){
			response.getWriter().write(JsonUtil.result(false, "操作失败！", "",""));
		}
		return null;
	}

	//2015-10-08获取缓存内的新版数据
	private HashMap<String, String>  getEditionMap(HttpServletRequest request) {
		HashMap<String, String>  uiEditionMap = (HashMap<String, String> ) request.getServletContext().getAttribute("uiEditionMap");
		if(uiEditionMap==null ){
			uiEditionMap = new HashMap<String, String> ();
		}
		return uiEditionMap;
	}

	@RequestMapping("/DelMainMenu")
	public String DelMainMenu(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		// command
		reponse.setCharacterEncoding("utf-8");
		try {
			MainMenuDao.DelMainmenuitem(request);
			reponse.getWriter().write(JsonUtil.result(true, "删除成功!", "", ""));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	// MainMenuTree
	@RequestMapping("/SaveMainMenu")
	public String SaveMainMenu(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
																// command
		String result = "";
		try {
			request.setCharacterEncoding("utf-8");
			reponse.setCharacterEncoding("utf-8");
			Mainmenuitem info = new Mainmenuitem();
			info.setFid(request.getParameter("fid"));
			info.setFisleaf("true".equals(request.getParameter("fisleaf")) ? 1
					: 0);
			info.setFparentid(request.getParameter("fparentid"));
			info.setFname(request.getParameter("fname"));
			info.setForder(Integer.parseInt(request.getParameter("forder")
					.toString()));
			info.setFurl(request.getParameter("furl"));
			info.setFlevel(Integer.parseInt(request.getParameter("flevel")));
			info.setFpath(request.getParameter("fpath"));
			info.setFiconCls(request.getParameter("ficonCls"));// ficonCls
			HashMap<String, Object> params = MainMenuDao.ExecSave(info);
			if (params.get("success") == Boolean.TRUE) {
				reponse.getWriter().write(
						JsonUtil.result(true, "保存成功!", "", ""));
			}

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	// private String verifyInput(Mainmenuitem info){
	// String result="";
	// IMainMenuDao IMainMenuDao = (IMainMenuDao) SpringContextUtils
	// .getBean("MainMenuDao");
	// if (info.getFid()==null)
	// {
	// info.setFid(IMainMenuDao.CreateUUid());
	// }
	// if (info.getFparentid()==null)
	// {
	// result="父节点数据异常，请重新登录！";
	// }
	//
	//
	// return null;
	//
	// }

	private String GetJSON(List<Mainmenuitem> sList) {
		String result = "";
		int index = 0;
		for (Mainmenuitem info : sList) {
			if (index > 0) {
				result = result + ",";
			}
			result = result + "{fid:\"" + info.getFid() + "\",iconCls:\""
					+ info.getFiconCls() + "\",forder:" + info.getForder()
					+ ",fparentid:\"" + info.getFparentid() + "\",flevel:"
					+ info.getFlevel() + ",fisleaf:" + info.isleaf()
					+ ",fname:\"" + info.getFname() + "\",text:\""
					+ info.getFname() + "\",fpath:\"" + info.getFpath()
					+ "\",furl:\"" + info.getFurl() + "\",leaf:"
					+ info.isleaf() + "}";
			index++;
		}
		return result;

	}
	
}
