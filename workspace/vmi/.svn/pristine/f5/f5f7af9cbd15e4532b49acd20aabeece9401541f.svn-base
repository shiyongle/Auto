package Com.Controller.System;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.ISimplemessageDao;
import Com.Entity.System.Simplemessage;
import Com.Entity.System.Useronline;

/**
 * 简单消息控制类
 * 
 * 
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2014-2-11 上午10:12:51
 */
@Controller
public class SimplemessageController {

	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private ISimplemessageDao simplemessageDao;

	private String preAction = null;
	@RequestMapping("/getindexmsg")
	public String getindexmsg(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		String resultstring="";
		String userId = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String sql="select fid from t_sys_simplemessage where fhaveReaded=0 and frecipient='"+userId+"'";
		List<HashMap<String, Object>> msg=simplemessageDao.QueryBySql(sql);
		resultstring="\"msgcount\":\""+msg.size()+"\"";
		sql="select fid from t_ord_productplan where faudited=1 and fcloseed=0 and faffirmed=0 "+simplemessageDao.QueryFilterByUser(request, null, "fsupplierid");;
		msg=simplemessageDao.QueryBySql(sql);
		resultstring=resultstring+",\"ordercount\":\""+msg.size()+"\"";
		sql="select fid from t_ord_deliverorder where faudited=1 and fouted=0 "+simplemessageDao.QueryFilterByUser(request, null, "fsupplierid");;
		msg=simplemessageDao.QueryBySql(sql);
		resultstring=resultstring+",\"deliverordercount\":\""+msg.size()+"\"";
		reponse.getWriter().write(JsonUtil.result(true,"{"+resultstring+"}", "",""));
		return null;
	}
	
	

	@RequestMapping("/getMsgFacade")
	public String getMsgFacade(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		
		
		
//		String action = request.getParameter("myAction");
//
//		int ftype = new Integer(request.getParameter("ftype"));
//
		
		// if (action == null || action.equals("") ||
		// action.equals("beenSented")) {
		//
		// getSimplemessages(request, reponse);
		//
		// } else

		
		
//		if (action.equals("readed")) {
//
//			getReceivedSimplemessages(request, reponse, 1);
//
//		} else if (action.equals("unread")) {
//
//			getReceivedSimplemessages(request, reponse, 0);
//
//		}

		// else
		//
		// if (preAction.equals("beenSented")) {
		//
		// getSimplemessages(request, reponse);
		//
		// } else if (preAction.equals("readed")) {
		//
		// getReceivedSimplemessages(request, reponse, 1);
		//
		// } else if (preAction.equals("unread")) {
		//
		// getReceivedSimplemessages(request, reponse, 0);
		//
		// }

		// preAction = action;

		return getReceivedSimplemessages(request, reponse);

	}

	/**
	 * 获取发送的消息
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2014-2-11 上午10:13:05 (ZJZ)
	 */
	@RequestMapping("/getSimplemessages")
	public String getSimplemessages(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String userId = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();

		String sql = "SELECT tss.fid, tss.fsender,tss.ftime, tss.fcontent, tss.fremark, tss.frecipient, tss.fhaveReaded, tss.freceivingTime, tsu.fname as fsenderName, tsu2.fname as frecipientName FROM t_sys_simplemessage tss left join t_sys_user tsu on tss.fsender = tsu.fid left join t_sys_user tsu2 on tss.frecipient = tsu2.fid where tss.fsender = '%s' ";

		String fid = request.getParameter("fid");

		if (fid != null && !fid.equals("")) {

			sql += " and tss.fid = \'" + fid + "\' ";

		}

		sql = String.format(sql, userId);

		ListResult result;

		reponse.setCharacterEncoding("utf-8");

		try {
			// 过滤
			result = simplemessageDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	/**
	 * 根据id获取消息，用于查看
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2014-2-13 上午10:19:38 (ZJZ)
	 */
	@RequestMapping("/getSimplemessageById")
	public String getSimplemessageById(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String userId = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String sql = " SELECT fid, fsender, ftime, fcontent, fremark, frecipient, fhaveReaded, freceivingTime FROM t_sys_simplemessage ";

		String fid = request.getParameter("fid");

		if (fid != null && !fid.equals("")) {

			sql += " where fid = \'" + fid + "\' ";

		}

		ListResult result;

		reponse.setCharacterEncoding("utf-8");

		try {
			result = simplemessageDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
			if(!result.getData().get(0).get("fsender").equals(userId))
			{
				setMessageReaderState(fid, true);
			}
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	/**
	 * 获取接收到的消息
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2014-2-11 上午10:13:19 (ZJZ)
	 */
	@RequestMapping("/getReceivedSimplemessages")
	public String getReceivedSimplemessages(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		int action = request.getParameter("myAction") == null ? -1 : new Integer(request.getParameter("myAction"));

		//消息类型
		int ftype = request.getParameter("ftype") == null ? -1 : new Integer(request.getParameter("ftype"));
		
		String userId = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();

		String sql = " SELECT tss.fid, tss.fsender, tss.ftime, tss.fcontent, tss.fremark, tss.frecipient, tss.fhaveReaded, tss.freceivingTime, tsu.fname as fsenderName, tsu2.fname as frecipientName,tss.ftype FROM t_sys_simplemessage tss left join t_sys_user tsu on tss.fsender = tsu.fid left join t_sys_user tsu2 on tss.frecipient = tsu2.fid where tss.frecipient = '%s' ";

		sql = String.format(sql, userId);
		
		String fid = request.getParameter("fid");

		if (fid != null && !fid.equals("")) {

			sql += " and tss.fid = \'" + fid + "\' ";

		}
		
		if (action != -1) {
			
			sql += " and tss.fhaveReaded = %d ";
			sql = String.format(sql, action);
		}
		
		if (ftype != -1) {
			
			sql += String.format(" and tss.ftype = %d ", ftype);
			
		}
		
		if (request.getParameter("sort") == null) {
			
//			sql += " order by ftime desc ,fhaveReaded ";
			request.setAttribute("djsort", " ftime desc ,fhaveReaded");
			
		}
		
		
		
		ListResult result;

		reponse.setCharacterEncoding("utf-8");

		try {
			result = simplemessageDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));

		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	private void setHaveReadedAll(String userId) {
		// TODO Auto-generated method stub

		List<Simplemessage> simplemessages = simplemessageDao
				.findSimplemessagesByRecipient(userId);

		for (Simplemessage messageT : simplemessages) {

			messageT.setFhaveReaded(1);

			simplemessageDao.ExecSave(messageT);
		}
	}

	/**
	 * 添加消息
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2014-2-11 上午10:13:33 (ZJZ)
	 */
	@RequestMapping(value = "/saveSimplemessage")
	public String saveSimplemessage(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";
		try {
			// String userid = ((Useronline) request.getSession().getAttribute(
			// "Useronline")).getFuserid();
			//
			// Simplemessage simplemessage = (Simplemessage) request
			// .getAttribute("Simplemessage");
			//
			// simplemessage.setFsender(userid);
			//
			// simplemessage.setFtime(new Date());
			//
			// saveSimplemessages(simplemessage,
			// request.getParameter("Simplemessage"), userid);

			// result = "{success:true,msg:'保存成功!'}";

			result = simplemessageDao.ExecSaveSimplemessage(request);

		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}

	// /**
	// * 保存具体方法，可能保存多条数据
	// *
	// * @param simplemessage
	// * @param parameter
	// * @param userid
	// * @throws CloneNotSupportedException
	// *
	// * @date 2014-2-13 上午9:38:12 (ZJZ)
	// */
	// private void saveSimplemessages(Simplemessage simplemessage,
	// String parameter, String userid) throws CloneNotSupportedException {
	// // TODO Auto-generated method stub
	//
	// // 进行一些必要的设置
	//
	// JSONObject jso = JSONObject.fromObject(parameter);
	//
	// JSONArray jsa = jso.getJSONArray("frecipient");
	//
	// // 用于去掉重复的id
	// Set<String> setRecipientIds = new HashSet<>();
	//
	// for (int i = 0; i < jsa.size(); i++) {
	//
	// // 不发送给自己
	// if (userid.equals(jsa.getString(i))) {
	//
	// continue;
	//
	// }
	//
	// setRecipientIds.add(jsa.getString(i));
	//
	// }
	//
	//
	// //保存
	// for (String id : setRecipientIds) {
	//
	// Simplemessage objT = (Simplemessage) simplemessage.clone();
	//
	// objT.setFrecipient(id);
	//
	// simplemessageDao.ExecSave(objT);
	// }
	//
	// }

	@RequestMapping(value = "/setSimplemessageReadState")
	public String setSimplemessageReadState(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";

		String fidcls = request.getParameter("fidcls");

		String[] ids = fidcls.split(",");

		reponse.setCharacterEncoding("utf-8");

		try {

			// 遇到错误就退出
			for (String id : ids) {

				setMessageReaderState(id, true);

				if (id.equals(ids[0])) {
					result = JsonUtil.result(true, "部分成功！", "", "");
				}

				if (id.equals(ids[ids.length - 1])) {
					result = JsonUtil.result(true, "全部成功！", "", "");
				}

			}

		} catch (Exception e) {

			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelDeliversList error", e);

		}

		reponse.getWriter().write(result);
		return null;
	}

	/**
	 * 
	 * 
	 * @param id
	 *            ，主键
	 * @param readed
	 *            ，设置的状态
	 * @throws Exception
	 * 
	 * @date 2014-2-11 下午2:47:11 (ZJZ)
	 */
	private void setMessageReaderState(String id, boolean readed)
			throws Exception {
		// TODO Auto-generated method stub

		Simplemessage messageT = simplemessageDao.Query(id);
		if (readed) {
			if (messageT.getFhaveReaded() == 0) {
				messageT.setFhaveReaded(1);
				messageT.setFreceivingTime(new Date());
				simplemessageDao.saveOrUpdate(messageT);
			}
		} else {
			if (messageT.getFhaveReaded() == 1) {
				messageT.setFhaveReaded(0);
				messageT.setFreceivingTime(null);
				simplemessageDao.saveOrUpdate(messageT);
			}
		}
		//
		//
		//
		//
		// if ((messageT.getFhaveReaded() == 1 && readed)
		// || (messageT.getFhaveReaded() == 0 && !readed)) {
		//
		// throw (new Exception("部分设置无效"));
		//
		// }
		//
		// messageT.setFhaveReaded(readed ? 1 : 0);
		//
		// if (readed) {
		//
		// messageT.setFreceivingTime(new Date());
		//
		// }
		//
		// simplemessageDao.saveOrUpdate(messageT);

	}
	
	@RequestMapping(value = "/gainMsgTipCount")
	public String gainMsgTipCount(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		String userId = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		
		String sql = String.format(" SELECT * FROM t_sys_simplemessage where  frecipient = '%s' ", userId);
		
		try {
			int unReadedCount= simplemessageDao.QueryBySql(sql + " and fhaveReaded = 0 ").size();
			
			int unReceivingCount = simplemessageDao.QueryBySql(sql + " and ftype = 1 and fhaveReaded = 0 ").size();
			
			int projectEvaluation = simplemessageDao.QueryBySql(sql + " and ftype = 2 and fhaveReaded = 0 ").size();
			
			int productionreminds = simplemessageDao.QueryBySql(sql + " and ftype = 3 and fhaveReaded = 0 ").size();
			
			String json = "{success:true,msg:'成功!',unReadedCount:'%s',unReceivingCount:'%s',projectEvaluation:'%s',productionreminds:'%s'}";

			reponse.getWriter().write(String.format(json, unReadedCount, unReceivingCount, projectEvaluation,productionreminds));
		
		} catch (Exception e) {
			// TODO: handle exception
			
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			
		}
	
		return null;
	}
	
	
	

	/**
	 * 获取接收到的消息 (APP 接口方法)
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getMsgWithApp")
	public String getMsgWithApp(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		int action = request.getParameter("myAction") == null ? -1
				: new Integer(request.getParameter("myAction"));// 全部，已读，未读
		// 消息类型
		int ftype = request.getParameter("ftype") == null ? -1 : new Integer(
				request.getParameter("ftype"));

		String userId = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();

		String sql = " SELECT tss.fid, tss.fsender, tss.ftime, tss.fcontent, tss.fremark, tss.frecipient, tss.fhaveReaded, tss.freceivingTime, tsu.fname as fsenderName, tsu2.fname as frecipientName,tss.ftype FROM t_sys_simplemessage tss left join t_sys_user tsu on tss.fsender = tsu.fid left join t_sys_user tsu2 on tss.frecipient = tsu2.fid where tss.frecipient = '%s' ";
		sql = String.format(sql, userId);
		String fid = request.getParameter("fid");
		if (fid != null && !fid.equals("")) {

			sql += " and tss.fid = \'" + fid + "\' ";
		}
		if (action != -1) {

			sql += " and tss.fhaveReaded = %d ";
			sql = String.format(sql, action);
		}

		if (ftype != -1) {

			sql += String.format(" and tss.ftype = %d ", ftype);

		}
		if (!StringUtils.isEmpty(request.getParameter("msgtime"))) {

			sql += String.format(" and UNIX_TIMESTAMP(tss.ftime) > UNIX_TIMESTAMP('%s')",
					request.getParameter("msgtime"));

		}
		if (request.getParameter("sort") == null) {

			sql += " order by ftime desc ,fhaveReaded ";

		}
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = simplemessageDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

}
