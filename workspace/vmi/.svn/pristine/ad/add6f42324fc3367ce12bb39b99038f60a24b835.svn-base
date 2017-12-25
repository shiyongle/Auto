package Com.Controller.System;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.ISimplemessagecfgDao;
import Com.Entity.System.Simplemessagecfg;
import Com.Entity.System.Useronline;

/**
 * 消息持久化功能
 * 
 *
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2014-11-5 上午9:21:40
 */
@Controller
public class SimplemessagecfgController {

	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private ISimplemessagecfgDao simplemessagecfgDao;

	@RequestMapping("/gainSimplemessagecfgs")
	public String gainSimplemessagecfgs(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String userId = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();

		String sql = " SELECT fid, ftype, fneedsms, frecipient, fphone, fuserid FROM t_sys_simplemessagecfg where fuserid = '%s' ";

		sql = String.format(sql, userId);
		
		ListResult result;

		reponse.setCharacterEncoding("utf-8");

		try {
			// 过滤
			result = simplemessagecfgDao.QueryFilterList(sql, request);
			
			//没有就添加默认值
			if (result.getData().size() == 0) {
				
				Simplemessagecfg simplemessagecfg = new Simplemessagecfg();
				
				simplemessagecfg.setFid("");
				simplemessagecfg.setFneedsms(0);
				simplemessagecfg.setFuserid(userId);
				simplemessagecfg.setFtype(1);
				
				simplemessagecfgDao.ExecSave(simplemessagecfg);
				
				Simplemessagecfg simplemessagecfg2 = new Simplemessagecfg();
				
				simplemessagecfg2.setFid("");
				simplemessagecfg2.setFneedsms(0);
				simplemessagecfg2.setFuserid(userId);
				simplemessagecfg2.setFtype(2);
				
				simplemessagecfgDao.ExecSave(simplemessagecfg2);
				
				result = simplemessagecfgDao.QueryFilterList(sql, request);
				
			}
			
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}



	@RequestMapping(value = "/saveSimplemessagecfgs")
	public String saveSimplemessagecfgs(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";
		try {
		
			result = simplemessagecfgDao.ExecSaveSimplemessagecfgs(request);
			
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}
	
}
