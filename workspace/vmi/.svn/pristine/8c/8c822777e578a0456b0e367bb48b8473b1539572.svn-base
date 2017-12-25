package Com.Controller.System;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.IUserToUserDao;

@Controller
public class UserToUserController {
	
	@Resource
	private IUserToUserDao userToUserDao;
	
	@RequestMapping(value ="/getUserLeftList")
	public String getUserLeftList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String sql = "SELECT fid,fname FROM t_sys_user where feffect=0 ";
		try {
			ListResult result = userToUserDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	@RequestMapping(value ="/getUserRightList")
	public String getUserRightList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String sql = "select e.fid,e.fname from t_sys_user e left join t_bd_usertouser e1 on e.fid=e1.fuserid";
		try {
			ListResult result = userToUserDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	@RequestMapping(value ="/addUserToUser")
	public String addUserToUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String userID = request.getParameter("myobjectid");
		String fidcls = request.getParameter("fidcls");
		try {
			int count = userToUserDao.ExecAddUserToUser(userID,fidcls);
			if(count==0){
				throw new DJException("请选择未关联的用户操作！");
			}
			response.getWriter().write(JsonUtil.result(true, "您已成功关联"+count+"位用户", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	@RequestMapping(value ="/delUserToUser")
	public String delUserToUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String userID = request.getParameter("myobjectid");
		String fidcls = request.getParameter("fidcls");
		try {
			if(StringUtils.isEmpty(userID)||StringUtils.isEmpty(fidcls)){
				throw new DJException("参数不正确！");
			}
			userToUserDao.ExecDelUserToUser(userID,fidcls);
			response.getWriter().write(JsonUtil.result(true, "操作成功", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
}
