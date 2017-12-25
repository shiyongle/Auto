package Com.Controller.other;

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


@Controller
public class DeliverapplyPaperboardDeleteLogController {
	Logger log = LoggerFactory.getLogger(DeliverapplyPaperboardDeleteLogController.class);
	
	@Resource
	private ISimplemessagecfgDao simplemessagecfgDao;

	@RequestMapping(value = "/gainDeliverapplyPaperboardDeleteLogs")
	public String gainDeliverapplyPaperboardDeleteLogs(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			
			request.setAttribute("djsort", " fdeletetime desc ");
			
			String sql = "SELECT `fmateriallength`, `fdeletetime`, `fmaterialwidth`, `fvline`, `fhline`, `famount`, `faddress`, `fcreatetime`, `cusName`, `fboxlength`, `fboxwidth`, `fboxheight`, `fmaterialf`,famountpiece FROM t_ord_deliverapply_paperboard_delete_log ";
			
			ListResult result = simplemessagecfgDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	/**
	 * 自动删除一个月以前的数据
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2015-4-7 上午11:26:49  (ZJZ)
	 */
	@RequestMapping(value = "/deleteDeliverapplyPaperboardDeleteLogs")
	public String deleteDeliverapplyPaperboardDeleteLogs(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

//		String fids = request.getParameter("fidcls");
//		
//		String fidsS = "('" + fids.replace(",", "','") + "')";

		try {
			
			String sql = " delete from t_ord_deliverapply_paperboard_delete_log where fdeletetime not between DATE_SUB(now(),INTERVAL 1 MONTH) and now() ";

//			sql = String.format(sql, fidsS);
			
			simplemessagecfgDao.ExecBySql(sql);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}
	

}