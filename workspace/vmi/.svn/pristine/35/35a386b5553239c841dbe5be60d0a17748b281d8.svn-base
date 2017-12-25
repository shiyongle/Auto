package Com.Controller.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import Com.Base.Util.ServerContext;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.order.IDeliverapplyDao;
import Com.Entity.System.ProducePlan;
import Com.Entity.System.Productdef;
import Com.Entity.System.Useronline;
import Com.Entity.order.Deliverapply;

@Controller
public class BoardOrderController {
	Logger log = LoggerFactory.getLogger(BoardOrderController.class);
	@Resource
	private IDeliverapplyDao deliverapplyDao; 
	@Resource
	private IBaseSysDao baseSysDao;
	@Resource
	private DeliverapplyController deliverapplyController;
	
	/**
	 * 判断用于下单的产品档案的信息是否维护完全
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/verifyProductInfoForBoardOrder")
	public String verifyProductInfoForBoardOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String errorMsg;
		String fproductid = request.getParameter("fproductid");
		Productdef productObj = (Productdef) deliverapplyDao.Query(Productdef.class, fproductid);
		Deliverapply deliverapply = new Deliverapply();
		addProductInfo(deliverapply, productObj);
		errorMsg = validateBoardProduct(deliverapply);
		if(errorMsg == null){
			response.getWriter().write(JsonUtil.result(true, "","", ""));
		}else{
			response.getWriter().write(JsonUtil.result(false, errorMsg, "", ""));
		}
		return null;
	}
	/**
	 * 产品档案采购纸板订单
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveBoardOrderFromProduct")
	public String saveBoardOrderFromProduct(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String amount = request.getParameter("famount");
		String addressid = request.getParameter("faddressid");
		String productid = request.getParameter("fproductid");
		Deliverapply deliverapply = new Deliverapply();
		deliverapply.setFaddressid(addressid);
		Productdef product = (Productdef) deliverapplyDao.Query(Productdef.class, productid);
		try {
			addProductInfo(deliverapply,product);						//纸板订单添加产品信息
			deliverapply.setFarrivetime(deliverapplyController.getBoardOrderArriveTime(product));
			deliverapply.setFamount(Integer.valueOf(amount));
			if("不连做".equals(deliverapply.getFseries())){
				deliverapply.setFamountpiece(deliverapply.getFamount()*2);
			}else{
				deliverapply.setFamountpiece(deliverapply.getFamount());
			}
			deliverapply.setFstate(7);
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			saveBoardOrder(deliverapply,userid);
			response.getWriter().write(JsonUtil.result(true, "保存成功！","", ""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "保存失败，" + e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
	 * 纸板订单单款新增的保存
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */

	public  String saveBoardSignleDeliverapply(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			Deliverapply deliverapply = (Deliverapply) request.getAttribute("Deliverapply");
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			saveBoardOrder(deliverapply,userid);
			response.getWriter().write(JsonUtil.result(true, "保存成功！","", ""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "保存失败，" + e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
	 * 保存纸板订单
	 * @param deliverapply
	 * @param userid
	 */
	private void saveBoardOrder(Deliverapply deliverapply, String userid) {
		Productdef product=(Productdef)deliverapplyDao.Query(Productdef.class, deliverapply.getFmaterialfid());
		validateBoardDeliverapply(deliverapply,product);	// 校验纸板订单
		String sql = "select fname,flinkman,fphone from t_bd_address where fid = '"+deliverapply.getFaddressid()+"'";
		HashMap<String, String> addressInfo = baseSysDao.getMapData(sql);
		String customerid = baseSysDao.getCurrentCustomerid(userid);
		if(addressInfo==null){
			throw new DJException("地址ID不存在！");
		}
		deliverapply.setFaddress(addressInfo.get("fname"));
		deliverapply.setFlinkman(addressInfo.get("flinkman"));
		deliverapply.setFlinkphone(addressInfo.get("fphone"));
		if(product.getFeffected()==0){
			deliverapply.getFarrivetime().setHours(17);
		}
		//2015-06-29 片数控制
		if(deliverapply.getFseries().equals("连做")){
			deliverapply.setFamountpiece(deliverapply.getFamount());
		}
		if(deliverapply.getFseries().equals("不连做")){
			deliverapply.setFamountpiece(deliverapply.getFamount()*2);
		}
		if(deliverapply.getFboxlength()==null){
			deliverapply.setFboxlength(BigDecimal.ZERO);
		}
		if(deliverapply.getFboxwidth()==null){
			deliverapply.setFboxwidth(BigDecimal.ZERO);
		}
		if(deliverapply.getFboxheight()==null){
			deliverapply.setFboxheight(BigDecimal.ZERO);
		}
		String fid = deliverapply.getFid();
		if(StringUtils.isEmpty(fid)){
			deliverapply.setFid(deliverapplyDao.CreateUUid());
			deliverapply.setFcreatorid(userid);
			deliverapply.setFcreatetime(new Date());
			deliverapply.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "ZB", 4, false));
			deliverapply.setFiscreate(0);
		}
		deliverapply.setFupdatetime(new Date());
		deliverapply.setFupdateuserid(userid);
		deliverapply.setFcustomerid(customerid);
		deliverapply.setFboxtype(1);
		deliverapply.setFtype("0");
		//2015-05-15 横向公式异常多出()处理
		if(deliverapply.getFboxmodel().intValue()==1){
			if(deliverapply.getFhstaveexp().contains("(")){
				deliverapply.setFhstaveexp(deliverapply.getFhstaveexp().replace("(", "").replace(")", ""));
			}
		}
		//2015-05-18有盖无底的箱型,为了保证能匹配到eas产品,后台直接将纵向公式+[0]
		if(deliverapply.getFboxmodel().intValue()==5){
			deliverapply.setFvstaveexp(deliverapply.getFvstaveexp()+"+[0]");
		}
		deliverapply.setFvstaveexp(deliverapply.getFvstaveexp().replace("[w-0]", "[w]"));
		deliverapply.setFhstaveexp(deliverapply.getFhstaveexp().replace("[w-0]", "[w]"));
		deliverapply.setFhstaveexp(deliverapply.getFhstaveexp().replace("[w/2]+[h]+[w/2]", "[(w)/2]+[h]+[(w)/2]"));
		deliverapply.setFvstaveexp(deliverapply.getFvstaveexp().replace("[w/2]+[h]+[w/2]", "[(w)/2]+[h]+[(w)/2]"));
		deliverapplyDao.saveBoardDeliverapply(deliverapply);
	}

	/**
	 * 校验纸板订单
	 * @param deliverapply
	 * @param product
	 */
	private void validateBoardDeliverapply(Deliverapply deliverapply,Productdef product){
		String sql;
		if(product.getFeffect()==0){
			throw new DJException("此纸板材料已禁用，无法下单！");
		}
		validateBoardArriveTime(deliverapply, product);				// 校验配送时间
		String errorMsg = validateBoardProduct(deliverapply);		// 校验纸板信息
		if(errorMsg != null){
			throw new DJException(errorMsg);
		}
		//更改操作
		String fid = deliverapply.getFid();
		if(!StringUtils.isEmpty(fid)){
			sql = "select fstate from t_ord_deliverapply where fid = '"+fid+"'";
			List<HashMap<String, Object>> list = deliverapplyDao.QueryBySql(sql);
			if(list.size()==0){
				throw new DJException("订单ID错误！");
			}
			String state = list.get(0).get("fstate")+"";
			if(!"0".equals(state) && !"7".equals(state)){
				throw new DJException("已接收订单无法更改！");
			}
		}
		if(StringUtils.isEmpty(deliverapply.getFamount()+"")){
			throw new DJException("配送数量不能为空！");
		}
		if(deliverapply.getFamountpiece()==null || deliverapply.getFamountpiece()==0){
			throw new DJException("配送数量片数不能为空！");
		}
		if(StringUtils.isEmpty(deliverapply.getFamountpiece()+"")){
			throw new DJException("配送数量片数不能为空！");
		}
		if(StringUtils.isEmpty(deliverapply.getFaddressid())){
			throw new DJException("地址不能为空！");
		}
		
	}
	/**
	 * 校验纸板订单中的产品信息
	 * @param deliverapply
	 * @return
	 */
	private String validateBoardProduct(Deliverapply deliverapply) {
		String errorMsg = null;
		if(StringUtils.isEmpty(deliverapply.getFmaterialfid())){
			errorMsg = "材料不能为空！"; return errorMsg;
		}
		BigDecimal materialLength = deliverapply.getFmateriallength();
		BigDecimal materialWidth = deliverapply.getFmaterialwidth();
		if(materialLength==null){
			errorMsg = "下料规格长度不能为空！"; return errorMsg;
		}
		if(materialWidth==null){
			errorMsg = "下料规格宽度不能为空！"; return errorMsg;
		}
		float mLength = materialLength.floatValue();
		float mWidth = materialWidth.floatValue();
		if(mLength<=0.5 || mWidth<=0.5){
			errorMsg = "纸板下料规格的长宽必须大于0.5！"; return errorMsg;
		}
		String boxModel = deliverapply.getFboxmodel()+"";
		if(StringUtils.isEmpty(boxModel)){
			errorMsg = "箱型不能为空！"; return errorMsg;
		}
		if("1".equals(boxModel)){
			if(StringUtils.isEmpty(deliverapply.getFhformula()+"") || StringUtils.isEmpty(deliverapply.getFvformula()+"") ||StringUtils.isEmpty(deliverapply.getFhformula1()+"")){
				errorMsg = "公式的接舌和系数不能为空！"; return errorMsg;
			}
		}
		if("2".equals(boxModel) ||"3".equals(boxModel) ||"4".equals(boxModel) ||"5".equals(boxModel) ||"6".equals(boxModel) ||"8".equals(boxModel)){
			if(StringUtils.isEmpty(deliverapply.getFhformula()+"")){
				errorMsg = "横向公式的接舌不能为空！"; return errorMsg;
			}
		}
		if("2".equals(boxModel) ||"3".equals(boxModel) ||"4".equals(boxModel) ||"5".equals(boxModel) ||"8".equals(boxModel)){
			if(StringUtils.isEmpty(deliverapply.getFvformula()+"")){
				errorMsg = "纵向公式的系数不能为空！"; return errorMsg;
			}
		}
		if(StringUtils.isEmpty(deliverapply.getFstavetype())){
			errorMsg = "压线方式不能为空！"; return errorMsg;
		}
		if(StringUtils.isEmpty(deliverapply.getFseries())){
			errorMsg = "成型方式不能为空！"; return errorMsg;
		}
		
		//2015-06-29 片数控制
		BigDecimal boxLength = deliverapply.getFboxlength();
		BigDecimal boxWidth = deliverapply.getFboxwidth();
		BigDecimal boxHeight = deliverapply.getFboxheight();
		if(!"不压线".equals(deliverapply.getFstavetype())){
			if(StringUtils.isEmpty(boxHeight+"")){
				errorMsg = "纸箱高度不能为空！"; return errorMsg;
			}
			if(StringUtils.isEmpty(boxWidth+"")){
				errorMsg = "纸箱宽度不能为空！"; return errorMsg;
			}
			if(StringUtils.isEmpty(boxLength+"")){
				errorMsg = "纸箱长度不能为空！"; return errorMsg;
			}
			if(deliverapply.getFboxmodel()!=0){
				if(StringUtils.isEmpty(deliverapply.getFvstaveexp())){
					errorMsg = "纵向公式不能为空！"; return errorMsg;
				}
				if(StringUtils.isEmpty(deliverapply.getFhstaveexp())){
					errorMsg = "横向公式不能为空！"; return errorMsg;
				}
			}
		}
		if(boxLength!=null&&boxWidth!=null&&boxHeight!=null){
			float bLength = boxLength.floatValue();
			float bWidth = boxWidth.floatValue();
			float bHeight = boxHeight.floatValue();
			if(bLength!=0&&bWidth!=0&&bHeight!=0){
				if(bLength<=0.5||bWidth<=0.5||bHeight<=0.5){
					errorMsg = "纸板的长宽高必须大于0.5！"; return errorMsg;
				}
				if(bWidth-bLength>0.5){
					errorMsg = "纸箱规格的宽度不能大于长度！"; return errorMsg;
				}
			}
		}
		return null;
	}
	/**
	 * 校验纸板订单配送时间
	 * @param deliverapply
	 * @param product
	 */
	private void validateBoardArriveTime(Deliverapply deliverapply,
			Productdef product) {
		ProducePlan planInfo=null;
		Calendar cday;
		if(deliverapply.getFarrivetime()==null){
			throw new DJException("配送时间不能为空！");
		}
		List<ProducePlan> plans=deliverapplyDao.QueryByHql("from ProducePlan p where p.fproductid='"+ deliverapply.getFmaterialfid()+"' and p.fsupplierid='"+deliverapply.getFsupplierid()+"'");
		if(plans!=null&&plans.size()>0){
			planInfo=plans.get(0);
		}
		try {
			cday = deliverapplyController.getFirstArrivetimeDate(planInfo,product);
		} catch (Exception e) {
			throw new DJException("排产日期计算出错，请上报平台更改！");
		}
		String fristday=new SimpleDateFormat("yyyy-MM-dd").format(cday.getTime());
		if(new Integer(product.getFnewtype())==2)
		{
			cday.add(Calendar.DAY_OF_MONTH, 1);//普默认+1
		}else if(product.getFeffected()==0)//早班
		{
			cday.add(Calendar.DAY_OF_MONTH, -1);//普默认-1 早班取排产日当天
		}
		if(deliverapply.getFarrivetime().before(cday.getTime())){
			throw new DJException("该材料:"+product.getFname()+",首排产日为"+fristday+",请根据首排产选择配送时间");
		}
	}

	/**
	 * 将产品信息添加进订单
	 * @param deliverapply
	 * @param productid
	 */
	private void addProductInfo(Deliverapply deliverapply, Productdef product) {
		deliverapply.setFmaterialfid(product.getFmaterialfid());
		deliverapply.setFmateriallength(product.getFmateriallength());
		deliverapply.setFmaterialwidth(product.getFmaterialwidth());
		deliverapply.setFboxmodel(Integer.valueOf(product.getFboxmodelid()));
		deliverapply.setFstavetype(product.getFstavetype());
		deliverapply.setFseries(product.getFseries());
		deliverapply.setFboxlength(product.getFboxlength());
		deliverapply.setFboxwidth(product.getFboxwidth());
		deliverapply.setFboxheight(product.getFboxheight());
		deliverapply.setFhstaveexp(product.getFhstaveexp());
		deliverapply.setFvstaveexp(product.getFvstaveexp());
		deliverapply.setFsupplierid(product.getFmtsupplierid());
		deliverapply.setFhformula(product.getFhformula0());
		deliverapply.setFhformula1(product.getFhformula1());
		deliverapply.setFvformula(product.getFvformula0());
		deliverapply.setFdefine1(product.getFdefine1());
		deliverapply.setFdefine2(product.getFdefine2());
		deliverapply.setFdefine3(product.getFdefine3());
		deliverapply.setFhline(product.getFhformula());
		deliverapply.setFvline(product.getFvformula());
	}

}

 	
