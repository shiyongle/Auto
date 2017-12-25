package Com.Controller.other;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.order.IComplexDao;

@Controller
public class ComplexController {
	@Autowired
	IBaseSysDao dao;
	@Resource
	IComplexDao complexDao;
	private boolean vmiPlanIsRunning;
	/**
	 * vmi下单
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/vmiplan")
	public String vmiplan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			if(vmiPlanIsRunning){
				throw new DJException("正在下单，请稍后再试！");
			}
			vmiPlanIsRunning = true;
			String userid = request.getParameter("userid");
			verifyVmiplan(request,response);
			complexDao.ExecVmiplan(userid);
			response.getWriter().write(JsonUtil.result(true, "下单成功", "", ""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}catch (Exception e) {
			vmiPlanIsRunning = false;
			throw new RuntimeException(e);
		}
		vmiPlanIsRunning = false;
		return null;
	}
	private void verifyVmiplan(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = " select fnumber from t_ord_saleorder  where fallot=0 and "
				+ "faudited=1 and exists (select fid from t_pdt_vmiproductparam "
				+ "where t_pdt_vmiproductparam.fproductid=t_ord_saleorder.fproductdefid) limit 0,1";
		List<HashMap<String, Object>> vmipcls = dao.QueryBySql(sql);
		if (vmipcls.size() > 0) {
			throw new DJException("存在已审核但未分配的生产订单");
		}
	}
}
