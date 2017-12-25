package com.pc.controller.protocol;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.Car.impl.CarDao;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.protocol.IprotocolDao;
import com.pc.model.CL_Protocol;
import com.pc.model.CL_UserRole;
import com.pc.query.protocol.ProtocolQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class ProtocolController extends BaseController{
	
	@Resource
	private IprotocolDao protocolDao;
	@Resource
	private IorderDao orderDao;
	@Resource 
	private IUserRoleDao userRoleDao;
	@Resource
	private CarDao carDao;
	
	protected static final String ALLDAY_LIST_JSP= "/pages/pc/protocol/driverAllDayProtocol_list.jsp";
	protected static final String DRIVER_LIST_JSP= "/pages/pc/protocol/driverProtocol_list.jsp";
	protected static final String LIST_JSP= "/pages/pc/protocol/protocol_list.jsp";
	protected static final String CETAREALLDAY_JSP= "/pages/pc/protocol/driverAllDay_create.jsp";
	protected static final String CETARE_JSP= "/pages/pc/protocol/proctocol_createCarRule.jsp";
	protected static final String CETARELESS_JSP= "/pages/pc/protocol/proctocol_createLessRule.jsp";
	protected static final String CETAREDAY_JSP= "/pages/pc/protocol/proctocol_createDayRule.jsp";
	protected static final String CREATE_DRIVER_JSP= "/pages/pc/protocol/proctocol_createDriverRule.jsp";

	protected static final String EDITALLDAY_JSP= "/pages/pc/protocol/driverAllDay_edit.jsp";
	protected static final String EDITDAYRULE_JSP= "/pages/pc/protocol/proctocol_editDayRule.jsp";
	protected static final String EDITLESSRULE_JSP= "/pages/pc/protocol/proctocol_editLessRule.jsp";
	protected static final String EDITCARRULE_JSP= "/pages/pc/protocol/proctocol_editCarRule.jsp";
	protected static final String EDITDRIVERRULE_JSP= "/pages/pc/protocol/proctocol_editDriverRule.jsp";
	
	@RequestMapping("/protocol/driverList")
	public String DriverList(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return DRIVER_LIST_JSP;
	}
	
	@RequestMapping("/protocol/driverAllDayList")
	public String DriverAllDayList(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return ALLDAY_LIST_JSP;
	}
	
	@RequestMapping("/protocol/list")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return LIST_JSP;
	}
	
	@RequestMapping("/protocol/addDriverRule")
	public String addDriverRule(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return CREATE_DRIVER_JSP;
	}
	
	@RequestMapping("/protocol/addDriverAllDayRule")
	public String addDriverAllDayRule(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return CETAREALLDAY_JSP;
	}
	
	@RequestMapping("/protocol/addCarRule")
	public String addCarRule(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return CETARE_JSP;
	}
	@RequestMapping("/protocol/addLessRule")
	public String addLessRule(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return CETARELESS_JSP;
	}
	@RequestMapping("/protocol/addDayRule")
	public String addDayRule(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return CETAREDAY_JSP;
	}
 
	
	
	@RequestMapping("/protocol/editLessRule")
	public String editLessRule(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
	    CL_Protocol protocol =protocolDao.getById(id);
	    request.setAttribute("protocol", protocol); 
		return EDITLESSRULE_JSP;
	}
	
	@RequestMapping("/protocol/editAllDayRule")
	public String editAllDayRule(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
		CL_Protocol protocol =protocolDao.getById(id);
		request.setAttribute("protocol", protocol);
		return EDITALLDAY_JSP;
	}
	
	@RequestMapping("/protocol/editDayRule")
	public String editDayRule(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
	    CL_Protocol protocol =protocolDao.getById(id);
		request.setAttribute("protocol", protocol);
		return EDITDAYRULE_JSP;
	}
	@RequestMapping("/protocol/editCarRule")
	public String editCarRule(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
		CL_Protocol protocol =protocolDao.getById(id);
		protocol.setFdiscount((protocol.getFdiscount().multiply(new BigDecimal(100))));
		System.out.println(protocol.getFdiscount());
		request.setAttribute("protocol", protocol);
		return EDITCARRULE_JSP;
	}
	
	
	/*** 加载协议用车信息*/
	@RequestMapping("/protocol/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute ProtocolQuery protocolQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String seachKey =request.getParameter("userName");
		if (protocolQuery == null) {
			protocolQuery = newQuery(ProtocolQuery.class, null);
		}
		if (pageNum != null) {
			protocolQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			protocolQuery.setPageSize(Integer.parseInt(pageSize));
		}
		protocolQuery.setSearchKey(null);
		if(seachKey !=null && !seachKey.equals("请选择")){
			protocolQuery.setSearchKey(seachKey);
		}
		//司机客户价格分离
		protocolQuery.setSortColumns("clp.type in (1,2,3)");
		Page<CL_Protocol> page = protocolDao.findPage(protocolQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("total", page.getTotalCount());
			m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	/***增加整车协议规则*/
	@RequestMapping("/protocol/saveCarRule")
	public String saveCarRule(HttpServletRequest request,HttpServletResponse reponse,CL_Protocol protocol) throws Exception{
		if(protocolDao.getBySpecAndType( protocol.getCarSpecId(),protocol.getUserRoleId(),1).size()>0){
			return writeAjaxResponse(reponse, "failure");
		}
		if(protocol.getFdiscount()!=null||"".equals(protocol.getFdiscount())){
			protocol.setFdiscount(protocol.getFdiscount().divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_EVEN));
			
		}
		protocol.setCreateTime(new Date());
		protocol.setFname("整车协议规则");
		protocol.setStatus(1);
		protocol.setFopint(1);
		protocol.setType(1);
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		protocol.setFcreator(user.getId());
		protocol.setFoperator(user.getId());
		protocol.setFoperate_time(new Date());
		protocolDao.save(protocol);
		//updateUserProtocol(protocol.getUserRoleId());
		return writeAjaxResponse(reponse, "success");
	}
	
	/***增加零担协议规则*/
	@RequestMapping("/protocol/saveLessRule")
	public String saveLessRule(HttpServletRequest request,HttpServletResponse reponse,CL_Protocol protocol) throws Exception{
        List<CL_Protocol> ls=protocolDao.findByfunitId(protocol.getFunitId(),protocol.getUserRoleId());
		if(ls.size()>0){
    		return writeAjaxResponse(reponse, "failure");
        }
		protocol.setCreateTime(new Date());
		protocol.setFname("零担协议规则");
		protocol.setStatus(1);
		protocol.setType(2);
		protocol.setFopint(1);
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		protocol.setFcreator(user.getId());
		protocol.setFoperator(user.getId());
		protocol.setFoperate_time(new Date());
		protocolDao.save(protocol);
		//updateUserProtocol(protocol.getUserRoleId());
		return writeAjaxResponse(reponse, "success");
	}

	/***增加包车协议规则*/
	@RequestMapping("/protocol/saveDayRule")
	public String saveDayRule(HttpServletRequest request,HttpServletResponse reponse,CL_Protocol protocol) throws Exception{
		List<CL_Protocol> ls=protocolDao.getBySpecAndType( protocol.getCarSpecId(),protocol.getUserRoleId(),3);
		if(ls.size()>0){
			return writeAjaxResponse(reponse, "failure");
		}
		protocol.setCreateTime(new Date());
		protocol.setFname("包车协议规则");
		protocol.setStatus(1);
		protocol.setType(3);
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		protocol.setFcreator(user.getId());
		protocol.setFoperator(user.getId());
		protocol.setFoperate_time(new Date());
		protocolDao.save(protocol);
		//updateUserProtocol(protocol.getUserRoleId());
		return writeAjaxResponse(reponse, "success");
	}
	
	//修改整车协议规则*/
	@RequestMapping("/protocol/updateCarXY")
	public String updateCarXY(HttpServletRequest request,HttpServletResponse reponse,CL_Protocol protocol) throws Exception{
		protocol.setFdiscount(protocol.getFdiscount().divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_EVEN));
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		protocol.setFoperator(user.getId());
		protocol.setFoperate_time(new Date());
		protocolDao.updateCarRule(protocol);
		return writeAjaxResponse(reponse, "success");
	}
 
	
	/***修改零担协议规则*/
	@RequestMapping("/protocol/updateLessRule")
	public String updateLessRule(HttpServletRequest request,HttpServletResponse reponse,CL_Protocol protocol) throws Exception{
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		protocol.setFoperator(user.getId());
		protocol.setFoperate_time(new Date());
		protocolDao.updateLessRule(protocol);
		return writeAjaxResponse(reponse, "success");
	}
	
	/***修改包天协议规则*/
	@RequestMapping("/protocol/updateDayZT")
	public String updateDayZT(HttpServletRequest request,HttpServletResponse reponse,CL_Protocol protocol) throws Exception{
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		protocol.setFoperator(user.getId());
		protocol.setFoperate_time(new Date());
		protocolDao.updateDayRule(protocol);
		return writeAjaxResponse(reponse, "success");
	}
	
	

	// 删除功能
	@RequestMapping("/protocol/del")
	@Transactional(propagation = Propagation.REQUIRED)
	public String del(HttpServletRequest request, HttpServletResponse reponse,
			Integer[] ids) throws Exception {
		for (Integer id : ids) {
	    	 //2016-5-6 by lu 有关联订单的协议不准删除
			CL_Protocol protocol=this.protocolDao.getById(id);
			if(orderDao.getOrderCountByProtocol(protocol.getUserRoleId(), protocol.getId())>0){
				return writeAjaxResponse(reponse, "false");
			}
			this.protocolDao.deleteById(id);
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	
	
	//更改状态
	@RequestMapping("/protocol/change")
	public String change(HttpServletRequest request,
			HttpServletResponse reponse, Integer[] ids) throws Exception {
		for (Integer id : ids) {
			CL_Protocol protocol = this.protocolDao.getById(id);
			int stat = protocol.getStatus();
			if (stat == 1) {
				protocol.setStatus(0);
			} else {
				protocol.setStatus(1);
			}
			String vmi_user_fid = ServerContext.getUseronline()
					.get(request.getSession().getId()).getFuserid();
			CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(
					vmi_user_fid, 3);
			protocol.setFoperator(user.getId());
			protocol.setFoperate_time(new Date());
			protocolDao.update(protocol);
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	private void updateUserProtocol(Integer userid){		
		CL_UserRole urinfo = userRoleDao.getById(userid);
		if(urinfo.isProtocol()){
			return;
		}
		urinfo.setProtocol(true);
		//直接调用save方法必须保证   getByid取出的实体完整
		//userRoleDao.save(urinfo);
		userRoleDao.updateProtocol(urinfo);
	}
	
	@RequestMapping("/protocol/editDriverRule")
	public String editDriverRule(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
	    CL_Protocol protocol =protocolDao.getById(id);
	    request.setAttribute("protocol", protocol); 
		return EDITDRIVERRULE_JSP;
	}
	
	/*** 司机加载协议用车信息*/
	@RequestMapping("/protocol/driverLoad")
	public String driverLoad(HttpServletRequest request,HttpServletResponse reponse,Integer fdriver_type,@ModelAttribute ProtocolQuery protocolQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String cusName =request.getParameter("cusName");
		if (protocolQuery == null) {
			protocolQuery = newQuery(ProtocolQuery.class, null);
		}
		if (pageNum != null) {
			protocolQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			protocolQuery.setPageSize(Integer.parseInt(pageSize));
		}
		protocolQuery.setSearchKey(null);
		if(cusName !=null && !cusName.equals("请选择")){
			protocolQuery.setCusName(cusName);
		}
		//司机客户价格分离
		if(fdriver_type != null){
			protocolQuery.setSortColumns("clp.type = 4 and clp.fdriver_type = 1");//包天
		}else {
			protocolQuery.setSortColumns("clp.type = 4 and (clp.fdriver_type =2 or ISNULL(clp.fdriver_type))");//非包天
		}
		Page<CL_Protocol> page = protocolDao.findPage(protocolQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("total", page.getTotalCount());
			m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	// 司机计费新建 保存
	@RequestMapping("/rule/saveDriverBill")
	public String saveBill(HttpServletRequest request,
			HttpServletResponse reponse, CL_Protocol protocol) throws Exception {
		if(protocol.getFdriver_type() == null){
			if (protocolDao.getBySpecAndType(protocol.getCarSpecId(),protocol.getUserRoleId(),4).size() > 0){
				return writeAjaxResponse(reponse, "failure");
			}
		}else {
			if (protocolDao.getBySpecAndTypeAndDriver(protocol.getCarSpecId(),protocol.getUserRoleId(),4,1).size() > 0){
				return writeAjaxResponse(reponse, "failure");
			}
		}
		protocol.setStatus(1);
		protocol.setType(4);// 司机协议计费规则
		protocol.setFname("司机协议运价规则");
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		protocol.setFcreator(user.getId());
		protocol.setCreateTime(new Date());
		protocol.setFoperator(user.getId());
		protocol.setFoperate_time(new Date());
		protocol.setFopint(1);//默认点数1
		Integer specId = protocol.getCarSpecId();
		//包天新建时会传fdriver_type=1
		if(protocol.getFdriver_type() == null){//null则表示不是包天新建，设为2
			//增值服务暂不开通
			/*if (specId == 1) {
				protocol.setFadd_service("0");
			}
			if (specId == 2) {
				protocol.setFadd_service("50");
			}
			if (specId == 3) {
				protocol.setFadd_service("100");
			}
			if (specId == 5) {
				protocol.setFadd_service("150");
			}
			if (specId == 6) {
				protocol.setFadd_service("不支持");
			}*/
//			protocol.setFdriver_type(2);存在老数据，暂时就null吧
		} 
		protocolDao.save(protocol);
		return writeAjaxResponse(reponse, "success");
	}
	
	/***修改司机运价协议规则*/
	@RequestMapping("/protocol/updateDriverProtocol")
	public String updateDriverProtocol(HttpServletRequest request,HttpServletResponse reponse,CL_Protocol protocol) throws Exception{
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		protocol.setFcreator(user.getId());
		protocol.setFoperator(user.getId());
		protocol.setFopint(1);//默认点数1
		protocol.setFoperate_time(new Date());
		protocolDao.updateLessRule(protocol);
		return writeAjaxResponse(reponse, "success");
	}
	
	/*@RequestMapping("/protocol/carSpec")
	public String carSpec(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception{
		Integer carSpecId = carDao.getByUserRoleId(id).get(0).getCarSpecId();
		
		return writeAjaxResponse(response, JSONUtil.getJson(carSpecId));
	}*/
	
}
