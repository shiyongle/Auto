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
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ISysLogDao;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;

@Controller
public class SysLogController {
	
	@Resource
	private ISysLogDao sysLogDao;
	@Resource
	private IBaseSysDao baseSysDao;
	
	@RequestMapping(value="/getSysLogList")
	public String getSysLogList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SysUser sysUser = baseSysDao.getCurrentUser(request);
		int filter = sysUser.getFisfilter();	// 1是查看所有用户
		String sql = "select fid,faction,fdata,fsource,ftime";
		if(filter == 1){
			sql += ",fip,fuser,fsuccess,fmessage";
		}
		sql += " from t_sys_log where ftime > DATE_SUB(NOW(),INTERVAL 1 MONTH) ";
		if(filter != 1){
			sql +="and fsuccess=1 and fuserid ='"+sysUser.getFid()+"'";
		}
		try {
			ListResult result = sysLogDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	
}
