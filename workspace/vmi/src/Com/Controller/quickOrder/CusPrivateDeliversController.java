package Com.Controller.quickOrder;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.UploadFile;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.order.IDeliverapplyDao;
import Com.Dao.quickOrder.ICusPrivateDeliversDao;
import Com.Dao.quickOrder.IQuickOrderDao;
import Com.Entity.System.Address;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Useronline;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Mystock;
import Com.Entity.order.SchemeDesignEntry;

@Controller("CusPrivateDeliversController")
public class  CusPrivateDeliversController {
	@Resource
	private ICusPrivateDeliversDao  cusPrivateDeliversDao;


//	/**
//	 * 设置客户产品为常用订单
//	 * @param request fid 多个用，隔开
//	 * @param response
//	 */
//	@RequestMapping("/SaveCusPrivateDelivers")
//	public String setCustproductCommon(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException {
//		String result = "";
//		String fid = request.getParameter("fid");
//		String fsupplierid = request.getParameter("fsupplierid");
//		fidcls="('"+fidcls.replace(",","','")+"')";
//		try {
////			cusPrivateDeliversDao.ExecCustproductCommon(fidcls);
//			result = JsonUtil.result(true,"设置成功", "", "");
//			reponse.setCharacterEncoding("utf-8");
//		} catch (Exception e) {
//			result = JsonUtil.result(false,e.getMessage(), "", "");
//		}
//		reponse.getWriter().write(result);
//
//		return null;
//
//	}	
}
