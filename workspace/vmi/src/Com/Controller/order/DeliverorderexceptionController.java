package Com.Controller.order;

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
import Com.Controller.System.RoleController;
import Com.Dao.order.DeliverorderexceptionDao;
import Com.Dao.order.IDeliverorderexceptionDao;
import Com.Entity.order.Deliverorderexception;

@Controller
public class DeliverorderexceptionController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Resource
	private IDeliverorderexceptionDao deliverorderexceptionDao;



	/**
	 * 
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-3-18 下午3:49:07  (ZJZ)
	 */
	@RequestMapping("/selectDeliverorderexceptionList") 
	public String selectDeliverorderexceptionList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		
		try {
			
			String sql = DeliverorderexceptionDao.SELECT_BASE_SQL;

			reponse.getWriter().write(JsonUtil.result(true, "", deliverorderexceptionDao.QueryFilterList(sql, request)));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	
	/**
	 * 
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-3-18 下午3:49:10  (ZJZ)
	 */
	@RequestMapping("/selectDeliverorderexceptionByID")
	public String selectDeliverorderexceptionByID(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		
		try {
			
			String sql = DeliverorderexceptionDao.SELECT_BASE_SQL + " where todoe.fid = '%s' ";

			sql = String.format(sql, request.getParameter("fid"));
			
			reponse.getWriter().write(JsonUtil.result(true, "", deliverorderexceptionDao.QueryFilterList(sql, request)));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;  
	}
	
	@RequestMapping("/saveOrUpdateDeliverorderexception")
	public String saveOrUpdateDeliverorderexception(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		
		Deliverorderexception deliverorderexceptionT = (Deliverorderexception) request.getAttribute("Deliverorderexception");
		
		try {
			
			deliverorderexceptionDao.ExecSave(deliverorderexceptionT);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
			
		
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

}
