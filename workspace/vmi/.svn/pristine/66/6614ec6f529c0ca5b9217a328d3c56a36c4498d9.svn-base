package Com.Controller.System;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.IFrieduserDao;
import Com.Entity.System.Frienduser;
import Com.Entity.System.Useronline;

@Controller
public class FrieduserController {
	@Resource
	private IFrieduserDao FrieduserDao;
	@RequestMapping("getFrieduserList")
	public void getFrieduserList(HttpServletResponse response,HttpServletRequest request) throws IOException{
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			String sql = "select f.fid,u1.fid userid,u2.fid frienduserid,u1.fname ufname,u2.fname friendname,u2.femail,u2.fcustomername,u2.ftel from t_sys_frienduser f left join t_sys_user u1 on f.fuserid=u1.fid left join t_sys_user u2 on u2.fid=f.frienduserid where f.fuserid='"+userid+"'";
			ListResult result = FrieduserDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
			
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, "查询失败", "",""));
		}
		
	}
	@RequestMapping("addFrienduser")
	public void addFrienduser(HttpServletResponse response,HttpServletRequest request) throws IOException{
		String fidcls = request.getParameter("fidcls");
//		String userID = request.getParameter("myobjectid");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			Frienduser fuser;
			for(String fid : fidcls.split(",")){
				fuser = new Frienduser();
				fuser.setFid(FrieduserDao.CreateUUid());
				fuser.setFrienduserid(fid);
				fuser.setFuserid(userid);
				FrieduserDao.saveOrUpdate(fuser);
			}
			response.getWriter().write(JsonUtil.result(true, "关联成功", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, "关联失败", "",""));
		}
	}
	@RequestMapping("delFrienduser")
	public void delFrienduser(HttpServletResponse response,HttpServletRequest request) throws IOException{
		String fidcls = request.getParameter("fidcls");
//		String userID = request.getParameter("myobjectid");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			for(String fid : fidcls.split(",")){
				String sql = "delete from t_sys_frienduser where fid ='"+fid+"'";
				FrieduserDao.ExecBySql(sql);
			}
			response.getWriter().write(JsonUtil.result(true, "取消成功", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, "取消失败", "",""));
		}
	}
	@RequestMapping(value ="/getUsersList")
	public String getUsersList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String sql = "SELECT fid,fname FROM t_sys_user where feffect=0 and fid not in(select frienduserid from t_sys_frienduser)";
		try {
			ListResult result = FrieduserDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
}
