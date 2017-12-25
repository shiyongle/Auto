package Com.Controller.order;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Dao.order.IStatementDao;
import Com.Entity.System.Useronline;
import Com.Entity.order.Productdemandfile;
import Com.Entity.order.Statement;

@Controller
public class StatementController {
	Logger log = LoggerFactory.getLogger(StatementController.class);

	@Autowired
	private IStatementDao statementDao;

	@RequestMapping(value = "/GetStatementList")
	public String GetStatementList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		request.setAttribute("djsort", "tos.fcustomerid, tos.fmonth desc");
		String sql = "SELECT tos.fid,tos.fcustomerid, tbc.fname fcustname,tos.ffileid,tos.fmonth,tos.fcreateuserid,tos.fcreatetime, tsu.fname fcreatename "
				+ " FROM t_ord_statement tos "
				+ " LEFT JOIN t_sys_user tsu ON tsu.fid = tos.fcreateuserid "
				+ " LEFT JOIN t_bd_customer tbc on tbc.fid=tos.fcustomerid where 1=1"
				+ statementDao.QueryFilterByUser(request, "tos.fcustomerid",
						null);

		String fcustomerid = request.getParameter("fcustomerid");
		if (!StringUtils.isEmpty(fcustomerid)) {
			sql += " and tos.fcustomerid='" + fcustomerid + "' ";
		}

		ListResult result;
		try {
			result = statementDao.QueryFilterList(sql, request);
			// result.setTotal("10000");
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	@RequestMapping(value = "/DeleteStatement")
	public String DeleteStatement(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";
		try {
			String fidcls = request.getParameter("fidcls");
			fidcls = "('" + fidcls.replace(",", "','") + "')";

			String sql = "select ffileid from t_ord_statement where fid in "
					+ fidcls;

			List<HashMap<String, Object>> list = statementDao.QueryBySql(sql);
			String files = "";
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					files += list.get(i).get("ffileid");
				} else {
					files += list.get(i).get("ffileid") + ",";
				}
			}

			UploadFile.delFile(files);

			String delSql = "delete from t_ord_statement where fid in "
					+ fidcls;
			statementDao.ExecBySql(delSql);
			result = JsonUtil.result(true, "删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(result);
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		return null;

	}

	/**
	 * 上传对账单PDF
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws FileUploadException
	 */
	@RequestMapping("uploadStatementFile")
	public String uploadStatementFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			FileUploadException {
		
		String fcustomerid = new String(request.getParameter("fcustomerid").getBytes("ISO-8859-1"),"UTF-8");
		String fmonth = new String(request.getParameter("fyearmonth").getBytes("ISO-8859-1"),"UTF-8");
		params p = new params();
		p.put("fcustomerid", fcustomerid);
		p.put("fmonth", fmonth);
		
		String sql = "SELECT tos.fid "
				+ " FROM t_ord_statement tos "
				+ " WHERE tos.fcustomerid=:fcustomerid and fmonth=:fmonth ";
		int cnt = statementDao.QueryCountBySql(sql, p);
		if(cnt > 0 ){
			response.getWriter().write(JsonUtil.result(false, "此客户该月份的对账单已存在！", "", ""));
		} else {
			Statement statement = new Statement();
			statement.setFid(statementDao.CreateUUid());
	
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("fparentid", statement.getFid());
			map.put("ftype", "对账单");
	
			try {
				String userId = ((Useronline) request.getSession().getAttribute(
						"Useronline")).getFuserid();
				List<Productdemandfile> file = UploadFile.upload(request, map);
				if (file != null && file.size() >= 1) {
					
					statement.setFcreateuserid(userId);
					statement.setFcreatetime(new Date());
					statement.setFcustomerid(fcustomerid);
					statement.setFfileid(file.get(0).getFid());
					statement.setFmonth(fmonth);
					statementDao.saveOrUpdate(statement);
				}
				response.getWriter().write(JsonUtil.result(true, "附件上传成功！", "", ""));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			}
		}
		return null;
	}
	
	/**
	 * 上传对账单PDF
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws FileUploadException
	 */
	@RequestMapping("viewpdf")
	public String viewpdf(HttpServletRequest request,
			HttpServletResponse response) throws IOException  {
		String fid = request.getParameter("fid");
		try {
			UploadFile.viewpdf(response, fid);
		} catch (DJException e) {
			response.getOutputStream().write(e.getMessage().getBytes());
		}
		return null;
	}
}
