package com.action.address;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.PageModel;
import com.model.address.Address;
import com.opensymphony.xwork2.ModelDriven;
import com.service.address.AddressManager;
import com.service.customer.CustomerManager;
import com.util.Constant;
import com.util.JSONUtil;

public class AddressAction extends BaseAction implements ModelDriven<Address> {
	
	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-8-7 下午6:00:36
	*/
	private static final long serialVersionUID = 1138368588219067554L;
	protected static final String LIST_JSP = "/pages/address/list.jsp";
	protected static final String CREATE_JSP = "/pages/address/create.jsp";
	protected static final String EDIT_JSP = "/pages/address/edit.jsp";
	protected static final String NEW_EDIT_JSP = "/pages/address/new_edit.jsp";
	
	private Address address=new Address();
	@Autowired
	private AddressManager addressManager;
	@Autowired
	private CustomerManager customerManager;
	private PageModel<Address> pageModel;// 分页组件
	@Override
	public Address getModel() {
		return address;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public PageModel<Address> getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel<Address> pageModel) {
		this.pageModel = pageModel;
	}
	public String save(){
		String userId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fcustomerid = this.customerManager.getCustomerIdByUserid(userId);
		address.setFid(addressManager.CreateUUid());
		address.setFcustomerid(fcustomerid);
		address.setFcreatetime(new Date());
		address.setFcreatorid(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		address.setFname(address.getFdetailaddress());
		this.addressManager.saveImpl(address);
		return writeAjaxResponse("success");
	}
	/***修改*/
	public String update(){
		Address ad = this.addressManager.get(address.getFid());
		ad.setFlinkman(address.getFlinkman());
		ad.setFphone(address.getFphone());
		ad.setFdetailaddress(address.getFdetailaddress());
		this.addressManager.update(ad);
		return writeAjaxResponse("success");
	}
	/***删除**/
	public  String delete(){
		this.addressManager.deleteImpl(ids);
		return writeAjaxResponse("success");
	}
	
	public String isDefault(){
//		String where =" where u.FID  = ? ";
//		Object[] queryParams=null;
//		List<Object> ls = new ArrayList<Object>();
		String userId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
//		ls.add(userId);
//		where =where + " and ad.fid in ('"+id+"')";
//		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
//		List<Address> list =this.addressManager.getById(where, queryParams);
		this.addressManager.setDefaultImpl(id, userId);
		return writeAjaxResponse("success");
	}
	
	
	public String load(){
//		String where =" where u.FID  = ?" + QueryFilterByUserofuser("ad.fcreatorid","and") ;
//		Object[] queryParams=null;
//		Map<String, String> orderby = new HashMap<String,String>();
//		List<Object> ls = new ArrayList<Object>();
//		ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
//		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
//		System.out.println(where);
		//2015-12-30  获取地址  根据登录信息就好了
		String where =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		pageModel = this.addressManager.findBySql(where, queryParams, null, pageNo, pageSize);
		HashMap<String,Object> map =new HashMap<String,Object>();
			map.put("list", pageModel.getList());
			map.put("totalRecords", pageModel.getTotalRecords());//总条数
			map.put("pageNo", pageModel.getPageNo());//第几页
			map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	public String loadDefaultFirst(){
		String userId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		List<Address> list = addressManager.getByCustomerId(customerId, userId);
		return writeAjaxResponse(JSONUtil.getJsonString(list));
	}
	
	public String isEnabled(){
		String isenable =getRequest().getParameter("isenable");
		String where =" where u.FID  = ? ";
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		String fid ="";
		for(int i=0 ;i<ids.length;i++){
			if(i ==0){
				fid = "'"+ids[i]+"'";
			}else{
				fid = fid + ",'"+ids[i]+"'"; 
			}
		}
		where =where + " and ad.fid in ("+fid+")";
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		List<Address> list =this.addressManager.getById(where, queryParams);
		this.addressManager.isEnabledImpl(list, isenable);
		return writeAjaxResponse("success");
	}
	
	public String create(){
		getRequest().setAttribute("fcustomerid", getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString());
		return CREATE_JSP;
	}
	
	public String edit(){
		Address pt = this.addressManager.get(id);
		getRequest().setAttribute("address", pt);
		return EDIT_JSP;
	}
	
	public String new_edit(){
		Address pt = this.addressManager.get(id);
		getRequest().setAttribute("address", pt);
		return NEW_EDIT_JSP;
	}
	
	public String list(){
		getRequest().setAttribute("fcustomerid", getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString());
		return LIST_JSP;
	}

	
}
