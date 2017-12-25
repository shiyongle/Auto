package com.pc.controller.abnormity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.Car.ICarDao;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.abnormity.IabnormityDao;
import com.pc.dao.clUpload.IuploadDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.orderCarDetail.IorderCarDetailDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.dao.protocol.impl.ProtocolDao;
import com.pc.dao.rule.impl.RuleDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.model.CL_Abnormity;
import com.pc.model.CL_Car;
import com.pc.model.CL_Order;
import com.pc.model.CL_OrderCarDetail;
import com.pc.model.CL_OrderDetail;
import com.pc.model.CL_UserRole;
import com.pc.model.Cl_Upload;
import com.pc.model.Util_Option;
import com.pc.query.abnormity.abnormityQuery;
import com.pc.util.JSONUtil;
import com.pc.util.LatitudeLongitudeDI;
import com.pc.util.ServerContext;
@Controller
public class AbnormityController extends BaseController {
	protected static final String ERROR_LIST= "/pages/pc/error/error_list.jsp";
	protected static final String ERRORCONTROL_LIST= "/pages/pc/error/errorControl.jsp";	

	 @Resource
	 private IuploadDao iuploadDao;
	 @Resource
	 private IabnormityDao iabnormityDao;
	 @Resource
	 private IorderDao orderDao;
	 @Resource
	 private IorderCarDetailDao orderCarDetailDao; 
	 @Resource
	 private IorderDetailDao orderDetailDao;
	 @Resource
	 private IUtilOptionDao optionDao;
	 @Resource
	 private UserRoleDao userRoleDao;
	 @Resource
	 private ICarDao carDao;
	 @Resource
	 private ProtocolDao protocolDao;
	 @Resource
	 private RuleDao ruleDao;
	 
	 @RequestMapping("/abnormity/list")
	 public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		 return ERROR_LIST;
	 }
	 
	 //异常调度
	 @RequestMapping("/abnormity/errorControl")
	 public String errorControl(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		request.setAttribute("forderId",request.getParameter("forderId"));//把需要指派的订单id保存在request中
		request.setAttribute("fabid",request.getParameter("fabid"));//把异常反馈id记录，需要在他的remark中记录异常调度信息
		List<CL_OrderCarDetail> cocinfoList = orderCarDetailDao.getByOrderId(Integer.parseInt(request.getParameter("forderId")));
		if(cocinfoList.size()>0){
			request.setAttribute("cocinfo",cocinfoList.get(0));//原订单司机的车辆信息
		}
		return ERRORCONTROL_LIST;
	 }
	 
	 @RequestMapping("/abnormity/load")
	 public String load(HttpServletRequest request,HttpServletResponse reponse,abnormityQuery query) throws Exception{		 
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if("-1".equals(request.getParameter("forderId"))){
			query.setForderId(null);
		}
		if (query == null) {
			query = newQuery(abnormityQuery.class, null);
		}
		if (pageNum != null) {
			query.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			query.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_Abnormity> page = iabnormityDao.findPage(query);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		for(CL_Abnormity abinfo:page.getResult()){
			//得到图片路径
//			List<Cl_Upload> picList = iuploadDao.getCl_UploadsByParentId(abinfo.getFid());	
//			if(picList.size()>0){
//				abinfo.setPicUrl(picList.get(0).getUrl());
//			}
			//获取订单信息
			List<Map<String, Object>> qlist = iabnormityDao.getOrderInfoByAbId(abinfo.getFid());
			if(qlist.size()>0){
				abinfo.setUserName(qlist.get(0).get("userName").toString());
				abinfo.setOrderNumber(qlist.get(0).get("orderNumber").toString());
			}
		}
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	 }
	 
	 //获取异常的图片列表
	 @RequestMapping("/abnormity/getPicUrls")
	 public String getPicUrls(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		 String fid = request.getParameter("fid");
		 String mode=request.getParameter("mode");
		 HashMap<String, JSONArray> m = new HashMap<String, JSONArray>();
		 JSONArray ja = new JSONArray();
		 List<Cl_Upload> picList = iuploadDao.getByOrderIdAndByMode(Integer.parseInt(fid),mode);	
		 for(Cl_Upload upinfo:picList){
			 JSONObject jo = new JSONObject();
			 jo.put("imgUrl", upinfo.getUrl());
			 ja.add(jo);
		 }
		 m.put("imgList", ja);
		 return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	 }
	 
	 //轮循查询 十五分钟内是否有新异常订单
	 @RequestMapping("/abnormity/haveNewAbnormal")
	 public String haveNewAbnormal(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		 int i = iabnormityDao.haveNewAbnormal();
		 if(i>0){
			 return writeAjaxResponse(reponse, "success");
		 }
	     return writeAjaxResponse(reponse, "false");
	 }
	 
	 
		/***加载司机姓名**/
		@RequestMapping("/abnormity/controlList")
		public String controlList(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
			int orderid=Integer.parseInt(request.getParameter("orderid").toString());
			CL_Order order =this.orderDao.getById(orderid);
			CL_OrderDetail  detailInfo=orderDetailDao.getByOrderId(orderid, 1).get(0);
			List<CL_Car> ls=new ArrayList<CL_Car>();
			if(order.getType()==1){
				//根据订单ID,获取其所需车型（去掉重复）
				List<Util_Option> cartype = this.optionDao.getCarTypeByOrderId(orderid);
				if(cartype.size()>0){
					if(cartype.size()==1){
							if(("任意车型").equals(cartype.get(0).getOptionName())){
								List<CL_Car> carlist = this.carDao.getByCarSpecId(cartype.get(0).getId());
								if (carlist!=null) {
									ls = controlCar(detailInfo,carlist);
								}
							}else{
								List<CL_Car> temp=carDao.getUrIdByCarType(cartype.get(0).getOptionId());
								if (temp!=null) {
									ls = controlCar(detailInfo,temp);
								}
							}
					}else{
						for(Util_Option option :cartype){
							List<CL_Car> temp=carDao.getUrIdByCarType(option.getOptionId());
							if (temp!=null) {
								ls = controlCar(detailInfo,temp);
							}
						}
					}
				}
			}else{
				List<CL_Car> carlist = this.carDao.getAllCar();
				if(carlist.size()>0){
					ls = controlCar(detailInfo,carlist);
				}
			}
			
			return writeAjaxResponse(reponse,JSONUtil.getJson(ls));
		}
		
	// 异常调度
	@RequestMapping("/abnormity/errorassign")
	@Transactional
	public String errorassign(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int id = Integer.parseInt(request.getParameter("userRoleId"));// 获取被指派司机的id
		int orderid = Integer.parseInt(request.getParameter("forderId"));// 获取被指派订单的id
		int abid = Integer.parseInt(request.getParameter("fabid"));// 异常反馈ID，remark中记录异常调度信息
		// 判断订单是否为未抢状态
		// CL_Order orderInfo =orderDao.IsExistIdByStatus(orderid);
		// 异常调度只需判断是否完成
		CL_Order clo = orderDao.getById(orderid);
		if (clo.getStatus() >= 4) {
			map.put("success", "false");
			map.put("data", "完成订单无法异常调度！");
		} else {
			// 更新订单司机记录前，先记录老司机
			CL_Abnormity abinfo = iabnormityDao.getById(abid);
			String remark = abinfo.getFremark() == null ? "原司机"
					+ clo.getUserRoleId() + ";新司机" + id : abinfo.getFremark()
					+ "原司机ID" + clo.getUserRoleId() + ";新司机" + id;
			clo.setUserRoleId(id);// 修改被指派订单的司机的id
			clo.setStatus(3);// 修改订单状态从派车中变更为运输中
			clo.setOperator(Integer.parseInt(request.getSession()
					.getAttribute("userRoleId").toString()));
			iabnormityDao.updateRemark(abid, remark);
			orderDao.update(clo);
			map.put("success", "true");
			map.put("data", "操作成功！");
		}
		return writeAjaxResponse(reponse, JSONUtil.getJson(map));
	}
	 
		/***构建司机直线距离及是否在线**/
		public List<CL_Car> controlCar(CL_OrderDetail detailInfo,List<CL_Car> carlist) throws Exception{
			List<CL_Car> carlistN = new ArrayList<CL_Car>();
			for(int i=(carlist.size()-1);i>-1;i--){
				CL_Car car = carlist.get(i);
				CL_UserRole ur = userRoleDao.getById(car.getUserRoleId());
				if(ur!=null && ur.getVmiUserPhone()!=null){
					car.setCarFtel(ur.getVmiUserPhone());
				}else{
					carlist.remove(i);
					continue;
				}
				
				String userRoleid = Integer.toString(car.getUserRoleId());
				if(!ServerContext.getDriverPosition().containsKey(userRoleid)){
					car.setFisOnline(0);//司机是否在线(0为否，1为是);
					car.setFstraightStretch(new BigDecimal(0));
					carlistN.add(car);
					carlist.remove(i);
				}else{
					//司机与提货点的直线距离;
					String Longitude = ServerContext.getDriverPosition().get(userRoleid)[0];
					String Latitude = ServerContext.getDriverPosition().get(userRoleid)[1];
					BigDecimal straightStretch = LatitudeLongitudeDI.GetDistance(Double.parseDouble(detailInfo.getLatitude()), Double.parseDouble(detailInfo.getLongitude()), Double.parseDouble(Latitude), Double.parseDouble(Longitude));
					
					//在当前时间1分钟之前则为不在线;
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					String PositionTime = ServerContext.getDriverPosition().get(userRoleid)[2];
					Date afterDate = new Date((new Date()) .getTime() - 60000);
					if(sdf.parse(PositionTime).before(afterDate)){
						car.setFisOnline(0);//司机是否在线(0为否，1为是);
						car.setFstraightStretch(new BigDecimal(0));
						carlistN.add(car);
						carlist.remove(i);
					}else{
						car.setFstraightStretch(straightStretch);
						car.setFisOnline(1);//司机是否在线(0为否，1为是);
					}
				}
			}
			Collections.sort(carlist);
			carlist.addAll(carlistN);
			return carlist;
		}
		
}
