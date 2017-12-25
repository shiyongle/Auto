package Com.Controller.System;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.data.CusWinType;
import Com.Dao.System.ICusWinCfgDao;
import Com.Entity.System.CusWinCfg;
import Com.Entity.System.Useronline;

/**
 * 窗口配置类
 * 
 * 
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2013-12-19 下午3:22:07
 */
@Controller
public class CusWinCfgController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private ICusWinCfgDao cusWinCfgDao;

	@RequestMapping("/getWinCfg")
	public String getWinCfg(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {

			String userId = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();

			String sql = "SELECT cfg.fid, cfg.fPositionx, cfg.fPositiony,cfg.ftitle, cfg.fwidth, cfg.fheight ,cfg.ftype, cfg.fcode, user.fname as userName FROM t_sys_cuswincfg cfg left join t_sys_user user on cfg.fuserid = user.fid where cfg.fuserid = '%s' and cfg.fid = '"
					+ request.getParameter("fid") + "'";

			sql = String.format(sql, userId);

			List<HashMap<String, Object>> sList = cusWinCfgDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/getWinCfgTypes")
	public String getWinCfgTypes(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {

			List<HashMap<String, Object>> sList = buildObjList(CusWinType.cusWinTypes);
			
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}

	private List<HashMap<String, Object>> buildObjList(
			Map<String, CusWinType> cuswintypes) {
		// TODO Auto-generated method stub
		
		List<HashMap<String, Object>> listt = new ArrayList<>();
		
		for (String key : cuswintypes.keySet()) {
			
			HashMap<String, Object> mapt = new HashMap<>();
			
			mapt.put("fid", cuswintypes.get(key).getFid());
			mapt.put("ftype", cuswintypes.get(key).getFtype());
			mapt.put("ftypename", cuswintypes.get(key).getFtypename());
			mapt.put("fcode", cuswintypes.get(key).getFcode());
			
			listt.add(mapt);
			
		}
		
		return listt;
	}

	@RequestMapping("/getWinCfgsByUser")
	public String getWinCfgsByUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String userId = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();

		String sql = "SELECT cfg.fid, cfg.fPositionx, cfg.fPositiony,cfg.ftitle, cfg.fwidth, cfg.fheight ,cfg.ftype, cfg.fcode, user.fname as userName FROM t_sys_cuswincfg cfg left join t_sys_user user on cfg.fuserid = user.fid where cfg.fuserid = '%s' ";

		sql = String.format(sql, userId);

		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = cusWinCfgDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/deleteWinCfgs")
	public String deletePricerangs(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";

		String fidclsS = request.getParameter("fidcls");
		String fidcls = "('"+fidclsS.replace(",", "','")+"')";
		try {
			String hql = "Delete FROM CusWinCfg where fid in " + fidcls;
			cusWinCfgDao.ExecByHql(hql);
			result = JsonUtil.result(true, "删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping(value = "/saveOrUpdateCusWinCfg")
	public String saveAndUpdateCusWinCfg(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		request.setCharacterEncoding("utf-8");
		reponse.setCharacterEncoding("utf-8"); // command
		
//		CusWinCfg info =(CusWinCfg) request.getAttribute("CusWinCfg") ;
//		info.setFuserid(fuserid)
		
		if (request.getParameter("fid").equals("")) {
			saveWinCfgs(request, reponse);
		} else {
			updateCusWinCfg(request, reponse);
		}

		return null;
	}

	@RequestMapping(value = "/updateCusWinCfgs")
	public String updateCusWinCfgs(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		boolean succes = true;
		
		String r = "";
		
		request.setCharacterEncoding("utf-8");
		reponse.setCharacterEncoding("utf-8"); // command

		String data = request.getParameter("data");
		JSONArray jsa = JSONArray.fromObject(data);

		try {
			for (int i = 0; i < jsa.size(); i++) {

				CusWinCfg cwc = generateObjByJsonO((JSONObject)jsa.get(i), request);

				cusWinCfgDao.ExecSave(cwc);
			}
		} catch (Exception e) {
			// TODO: handle exception
			succes = false;
			r = e.getMessage();
		}

		if (succes) {
			
			r = "修改成功！";
			
		}
		
		reponse.getWriter().write(
					JsonUtil.result(succes, r, "", ""));
		
		return null;
	}

	
	private CusWinCfg generateObjByJsonO(JSONObject jso, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		CusWinCfg cwc = new CusWinCfg();

		
		cwc.setFid(jso.getString("fid"));

		cwc.setFuserid(((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid());
		
		cwc.setFpositionx(new Integer(jso.getString("fPositionx")));
		cwc.setFpositiony(new Integer(jso.getString("fPositiony")));
		cwc.setFtitle(jso.getString("ftitle"));
		cwc.setFwidth(new BigDecimal(jso.getString("fwidth")));
		cwc.setFheight(new BigDecimal(jso.getString("fheight"))); 
		cwc.setFtype(jso.getString("ftype"));
		
		String codet = jso.getString("fcode").replace("\"","\\\"");
		
		cwc.setFcode(codet);

		return cwc;
	}

	private String saveWinCfgs(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		request.setCharacterEncoding("utf-8");
		reponse.setCharacterEncoding("utf-8"); // command
		try {
			CusWinCfg cusWinCfg = buildWinCfg(request, true);

			cusWinCfgDao.ExecSave(cusWinCfg);
			reponse.getWriter().write(JsonUtil.result(true, "保存成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	private String updateCusWinCfg(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		request.setCharacterEncoding("utf-8");
		reponse.setCharacterEncoding("utf-8"); // command
		try {
			CusWinCfg cusWinCfg = buildWinCfg(request, false);

			cusWinCfgDao.ExecSave(cusWinCfg);
			reponse.getWriter().write(JsonUtil.result(true, "修改成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	private CusWinCfg buildWinCfg(HttpServletRequest request, boolean buildNew) {
		// TODO Auto-generated method stub

		CusWinCfg cwc = new CusWinCfg();

		if (buildNew) {

			cwc.setFid("");

		} else {

			cwc.setFid(request.getParameter("fid"));

		}

		cwc.setFuserid(((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid());
		cwc.setFpositionx(new Integer(request.getParameter("fPositionx")));
		cwc.setFpositiony(new Integer(request.getParameter("fPositiony")));
		cwc.setFtitle(request.getParameter("ftitle"));
		cwc.setFwidth(new BigDecimal(request.getParameter("fwidth")));
		cwc.setFheight(new BigDecimal(request.getParameter("fheight")));
		cwc.setFtype(request.getParameter("ftype"));
		
//		info.setFcode(info.getFcode());
		
//		String codet = request.getParameter("fcode").replace("\"","\\\"");
		String codet = request.getParameter("fcode");
		
		cwc.setFcode(codet);

		return cwc;
	}
	


}
