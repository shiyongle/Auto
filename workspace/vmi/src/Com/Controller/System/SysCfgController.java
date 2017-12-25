package Com.Controller.System;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Dao.System.ISyscfgDao;
import Com.Entity.System.Syscfg;
import Com.Entity.System.Useronline;

@Controller
public class SysCfgController {

	Logger log = LoggerFactory.getLogger(RoleController.class);

	@Resource
	private ISyscfgDao syscfgDao;

	@RequestMapping("/selectSysTip")
	public String selectSysTip(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		try {

			String sql = " SELECT fvalue FROM t_sys_syscfg where fkey = 'sysTip' and fuser='3f6112db-a952-11e2-90fc-60a44c5bbef3' "; 

			List<HashMap<String, Object>> sList = syscfgDao.QueryBySql(sql);

			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}

	@RequestMapping("/selectUserCfgs")
	public String selectUserCfgs(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		try {

			reponse.getWriter().write(
					JsonUtil.result(true, "",
							syscfgDao.ExecBuildReponseJson(request)));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/gainOrderWayCfg")
	public String gainOrderWayCfg(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		try {

			reponse.getWriter().write(
					JsonUtil.result(true, "",
							syscfgDao.ExecGainOrderWayCfg(request)));
			

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}

	
	@RequestMapping("/gainCfgByFkey")
	public String gainCfgByFkey(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		String key = request.getParameter("key");
		
		try {

			reponse.getWriter().write(
					JsonUtil.result(true, "",
							syscfgDao.ExecGainCfgByFkey(request, key)));
			

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/updateSyscfgs")
	public String updateSyscfgs(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		try {

			syscfgDao.ExecSaveSyscfgs(request);

			reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/updateSyscfgsByKeyAndValue")
	public String updateSyscfgsByKeyAndValue(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		try {

			syscfgDao.ExecSaveSyscfgsBysKeyAndValue(request);

			reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/getBoardOrderCfg")
	public String getBoardOrderCfg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userId = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		String sql = "select 1 from t_sys_syscfg where fkey='boardOrderCreate' and fuser='"+userId+"'";
		try {
			int has = syscfgDao.QueryExistsBySql(sql)?1:0;
			response.getWriter().write(JsonUtil.result(true, has+"", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	@RequestMapping("/saveBoardOrderCfg")
	public String saveBoardOrderCfg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		boolean add = Boolean.valueOf(request.getParameter("add"));
		String userId = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			syscfgDao.saveBoardOrderCfg(userId,add);
			response.getWriter().write(JsonUtil.result(true,"更改成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}

}
