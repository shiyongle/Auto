package Com.Controller.SDK;

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
import Com.Dao.order.IDeliverorderDao;
import Com.Dao.order.ISaleOrderDao;
import Com.Entity.order.Deliverorder;

@Controller
public class DeliverorderSDKController {
	Logger log = LoggerFactory.getLogger(DeliverorderSDKController.class);
	@Resource
	private IDeliverorderDao DeliverorderDao;
	@Resource
	private ISaleOrderDao saleOrderDao;
	/**
	 * 配送单发货接口
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getDeliverOrderSDK")
	public String getDeliverOrderSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select pdef.feasproductid,concat(pdef.fname,'(',ifnull(pdef.fversion,''),')') fproductname,c.fname as fcustomername,c.fid as fcustomerid,d.fid,d.faddressid,d.fnumber,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,ifnull(fordernumber,'') fordernumber,fplanid,ifnull(d.ftraitid,'') ftraitid from t_ord_deliverorder d left join t_bd_customer c on c.fid=d.fcustomerid  left join t_pdt_productdef pdef on pdef.fid=d.fproductid where fouted=0 and  ifnull(d.faudited,0)=1 and d.fimportEAS=1 and d.fissync=0 ";
		sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = DeliverorderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	/**
	 * 更改配送订单的导入标志
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("ImportedDeliverOrderSDK")
	public String ImportedDeliverOrderSDK(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String fid = request.getParameter("fid");
		try {
			saleOrderDao.ExecUpdateImportStateSDK(fid);
			response.getWriter().write(JsonUtil.result(true, "导入成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	/**
	 * 配送订单退回
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("backDeliverOrderSDK")
	public String backDeliverOrderSDK(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String fid = request.getParameter("fid");
		try {
			if(fid.equals("") || fid==null){
				throw new DJException("配送单ID不正确！");
			}
			Deliverorder order = DeliverorderDao.Query(fid);
			if(order==null||order.getFboxtype()==1)//纸板统一在纸板订单中判断
			{
			
				response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
				return null;
			}

			if(order.getFassembleQty()>0 || (order.getFoutQty()==null?0:order.getFoutQty())>0){
				throw new DJException("该配送订单已提货，不能退回！");
			}
			order.setFimportEas(0);
			order.setFissync(0);
			if(order.getFboxtype()==0)
			{
				order.setFaudited(0);
				order.setFauditorid("");
				order.setFaudittime(null);
			}
//			order.setFmatched(0);
			DeliverorderDao.saveOrUpdate(order);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
}
