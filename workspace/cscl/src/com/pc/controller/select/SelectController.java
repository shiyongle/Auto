package com.pc.controller.select;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.select.impl.UtilOptionDao;
import com.pc.model.Util_Option;
import com.pc.util.JSONUtil;
@Controller
public class SelectController extends BaseController{
	
	@Resource
	private UtilOptionDao optionDao;
	
	@Resource 
	private UserRoleDao userRoleDao;
	
	//获取所有有效车辆规格
	@RequestMapping("/select/getAllCarType")
	public String getAllCarType(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getAllCarType();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//获取所有车辆规格
	@RequestMapping("/select/getAllCarType1")
	public String getAllCarType1(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getAllCarType1();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//根据规格来获取对应的车型
	@RequestMapping("/select/getAllCarSpecByCarType")
	public String getAllCarSpecByCarType(HttpServletRequest request,HttpServletResponse reponse,Integer optionTemp){
		List<Util_Option> lst= optionDao.getAllCarSpecByCarType(optionTemp);
		for(Util_Option ol :lst){
			if(("任意车型").equals(ol.getOptionName())){
				lst.remove(ol);
				break;
			}
		}
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		lst.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(lst));
	}
	
	
	//获取所有车辆规格
	@RequestMapping("/select/getDriverByCarTypeId")
	public String getDriverByCarTypeId(HttpServletRequest request,HttpServletResponse reponse,Integer optionTemp,Integer carSpecId){
		List<Util_Option> options= optionDao.getDriverByCarTypeId(optionTemp,carSpecId);
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	//获取所有货主
	@RequestMapping("/select/getByRoleId")
	public String getByRoleId(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getByRoleId();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}

	//获取所有货主
	@RequestMapping("/select/getAll")
	public String getAll(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getAll();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//获取所有提交认证用户
		@RequestMapping("/select/getByIdentify")
		public String getByIdentify(HttpServletRequest request,HttpServletResponse reponse){
			List<Util_Option> options= optionDao.getByIdentify();
			Util_Option o = new Util_Option();
			o.setOptionId(-1);
			o.setOptionName("请选择");
			options.add(0, o);
			return writeAjaxResponse(reponse,JSONUtil.getJson(options));
		}
	//获取所有用户
	@RequestMapping("/select/getByRoleCarId")
	public String getByRoleCarId(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getByRoleCarId();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	
	//获取所有零担类型单位
	@RequestMapping("/select/getByOtherTypeByUnit")
	public String getByOtherTypeByUnit(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getByOtherTypeByUnit();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//获取所有货主
	@RequestMapping("/select/getAllConsignor")
	public String getAllConsignor(HttpServletRequest request,HttpServletResponse reponse,Integer couponsId){
		List<Util_Option> options= optionDao.getAllConsignor(couponsId);
		if(options.size()>0){
			Util_Option o = new Util_Option();
			o.setOptionId(-1);
			o.setOptionName("请选择");
			options.add(0, o);
			Util_Option o2 = new Util_Option();
			o2.setOptionId(0);
			o2.setOptionName("全部货主");
			options.add(1, o2);
		}else{
			Util_Option o = new Util_Option();
			o.setOptionId(-1);
			o.setOptionName("请选择");
			options.add(0, o);
		}
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//获取所有客户下拉框
	@RequestMapping("/select/getAllCustId")
	public String getAllCustId(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getAllCustId();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//获取所有完成订单下拉框
	@RequestMapping("/select/getFinishOrders")
	public String getFinishOrders(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getFinishOrders();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//所有司机下拉框
	@RequestMapping("/select/getAllDrivers")
	public String getAllDrivers(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getAllDrivers();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//所有客户名称下拉框（new）
	@RequestMapping("/select/getAllCustomers")
	public String getAllCustomers(HttpServletRequest request,HttpServletResponse response){
		List<Util_Option> options = optionDao.getAllCustomerNew();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
	//所有客户名称下拉框
	/*@RequestMapping("/select/getAllCustomers")
	public String getAllCustomers(HttpServletRequest request,HttpServletResponse response,Integer optionTemp,Integer userRoleId){
		List<Util_Option> options = optionDao.getAllCustomer(optionTemp, userRoleId);
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}*/
	
	//客户ID对应的发货地址下拉框(type=1)
	@RequestMapping("/select/getAllAddress")
	public String getAllAdress(HttpServletRequest request,HttpServletResponse response,Integer optionTemp,Integer userRoleId){
		List<Util_Option> options = optionDao.getAllCustomer(optionTemp, userRoleId);
		System.out.println(options.size());
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionVal("请选择");
		options.add(0, o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
	
	
	/*//客户ID对应的卸货地址下拉框(type=2)
	@RequestMapping("/select/getAllAddressRec")
	public String getAllAddressRec(HttpServletRequest request,HttpServletResponse response,Integer optionTemp,Integer userRoleId){
		optionDao.getAllCustomer(optionTemp, userRoleId);
		return null;
		
	}*/
	
	@RequestMapping("/select/getAllCus")
	public String getAllCus(HttpServletRequest request,HttpServletResponse response){
		 List<Util_Option> options = optionDao.getAllCus();
		 Util_Option o = new Util_Option();
		 o.setOptionId(-1);
		 o.setOptionName("请选择");
		 options.add(0,o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
	@RequestMapping("/select/getAllDriver")
	public String getAllDriver(HttpServletRequest request,HttpServletResponse response){
		List<Util_Option> options = optionDao.getAllDriver();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0,o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}

	@RequestMapping("/select/getAllDriverAndSpec")
	public String getAllDriverAndSpec(HttpServletRequest request,HttpServletResponse response){
		List<Util_Option> options = optionDao.getAllDrivers();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0,o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
	@RequestMapping("/select/getUseFactory")
	public String getUseFactory(HttpServletRequest request,HttpServletResponse response){
		List<Util_Option> options = optionDao.getUseFactory();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("全部");
		options.add(0,o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
	@RequestMapping("/select/getAllProvince")
	public String getAllProvince(HttpServletRequest request,HttpServletResponse response){
		List<Util_Option> options = optionDao.getAllProvince();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0,o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
	@RequestMapping("/select/getAllCity")
	public String getAllCity(HttpServletRequest request,HttpServletResponse response,Integer fprovince_id){
		List<Util_Option> options = optionDao.getAllCity(fprovince_id);
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0,o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
	@RequestMapping("/select/getAllArea")
	public String getAllArea(HttpServletRequest request,HttpServletResponse response,Integer fcity_id){
		List<Util_Option> options = optionDao.getAllArea(fcity_id);
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0,o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
	//查询区域，目前前端写死，留着备用
	@RequestMapping("/select/getAllArea2")
	public String getAllArea(HttpServletRequest request,HttpServletResponse response){
		List<Util_Option> options = optionDao.getAllArea();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0,o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
	//查询所有有效的通用增值服务
	@RequestMapping("/select/getEffectiveAllGeneralAdd")
	public String getEffectiveAllGeneralAdd(HttpServletRequest request, HttpServletResponse response){
		List<Util_Option> options = optionDao.getEffectiveAllGeneralAdd();
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
	@RequestMapping("/select/getAllCoupons")
	public String getAllCoupons(HttpServletRequest request, HttpServletResponse response){
		List<Util_Option> options = optionDao.getAllCoupons();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0,o);
		return writeAjaxResponse(response, JSONUtil.getJson(options));
	}
	
}