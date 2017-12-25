package com.action.customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.PageModel;
import com.model.customer.Customer;
import com.model.productreqallocationrules.Productreqallocationrules;
import com.opensymphony.xwork2.ModelDriven;
import com.service.customer.CustomerManager;
import com.service.saledeliver.SaledeliverManager;
import com.util.Constant;
import com.util.JSONUtil;
import com.util.StringUitl;

public class CustomerAction extends BaseAction implements ModelDriven<Customer> {
	
	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-8-7 下午6:00:36
	*/
	private static final long serialVersionUID = 1138368588219067554L;
	private Customer customer=new Customer();
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SaledeliverManager saledeliverManager;
	@Override
	public Customer getModel() {
		return customer;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String update(){
		System.out.println("-------------------");
		Customer cr = this.customerManager.get(customer.getFid());
		cr.setFartificialperson(customer.getFartificialperson());
		cr.setFbarcode(customer.getFbarcode());
		cr.setFlastupdatetime(new Date());
		cr.setFisinternalcompany(1);//已认证
		this.customerManager.update(cr);
		System.out.println("+++++++++++++++++++");
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("success", "success");
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	public String companyUpdate(){
		System.out.println("-------------------");
		Customer cr = this.customerManager.get(customer.getFid());
		cr.setFname(customer.getFname());
		cr.setFartificialperson(customer.getFartificialperson());
		cr.setFartificialpersonphone(customer.getFartificialpersonphone());
		cr.setFtxregisterno(customer.getFtxregisterno());
		cr.setFlastupdatetime(new Date());
		this.customerManager.update(cr);
		System.out.println("+++++++++++++++++++");
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("success", "success");
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	//客户管理列表界面
	public String list(){
		return "/pages/customer/customer_list.jsp";
	}
	
	//客户管理列表查询数据
	public String getMyCustomersList()throws IOException{
		try {
			String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String filter = getRequest().getParameter("filter");
			String fsupplierid= saledeliverManager.getFsupplieridByUser(userid);
			String where =" where sup.fsupplierid=? and c.fname like ?";
			List<Object> ls = new ArrayList<Object>();
			ls.add(fsupplierid);
			ls.add(StringUitl.isNullOrEmpty(filter)?"%%":"%"+new String(filter.getBytes("ISO-8859-1"),"UTF-8")+"%");
			Map<String, String> orderby = new LinkedHashMap<String,String>();//多个顺序排序
			orderby.put("c.fcreatetime", "DESC");
			queryParams = (Object[])ls.toArray(new Object[ls.size()]);
			PageModel<HashMap<String,Object>>	pageModel  = customerManager.findBySql(where, queryParams, orderby, pageNo, pageSize);
			return writeAjaxResponse(JSONUtil.getJson(pageModel));
		} catch (Exception e) {
			getResponse().getWriter().write(JSONUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}
	
	//客户管理新增、修改数据
	public void saveOrUpdateCustomer() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			Customer c = (Customer)getRequest().getAttribute("Customer");
			String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String fsupplierid= saledeliverManager.getFsupplieridByUser(userid);
			Productreqallocationrules p = new Productreqallocationrules();
			if(StringUitl.isNullOrEmpty(c.getFid())){
				c.setFid(this.customerManager.CreateUUid());
				c.setFcreatetime(new Date());
				c.setFcreatorid(userid);
				p.setFid(this.customerManager.CreateUUid());
				p.setFcreatetime(new Date());
				p.setFcreatorid(userid);
				p.setFcustomerid(c.getFid());
				p.setFsupplierid(fsupplierid);
				this.customerManager.saveOrUpdate(p);
			}
			this.customerManager.saveOrUpdate(c);
			getResponse().getWriter().write(JSONUtil.result(true, "保存成功","",""));
		} catch (Exception e) {
			// TODO: handle exception
			getResponse().getWriter().write(JSONUtil.result(false, e.getMessage(), "",""));
		}
	}
	
	public String editCustomer(){
		try {
			String fid = getRequest().getParameter("fid");
			Customer c = this.customerManager.get(fid);
			getRequest().setAttribute("customer", c);
			return "/pages/customer/customer_edit.jsp";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	public void delCustomer() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String fid = getRequest().getParameter("fid");
			String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String fsupplierid= saledeliverManager.getFsupplieridByUser(userid);
			if(StringUitl.isNullOrEmpty(fid)){
				throw new Exception("请选择数据！");
			}
//			String sql = "delete from t_bd_customer where fid in "+fid;
//			this.customerManager.ExecBySql(sql);
			this.customerManager.delCustomer(fid, userid, fsupplierid);
			getResponse().getWriter().write(JSONUtil.result(true, "删除成功","",""));
		} catch (Exception e) {
			// TODO: handle exception
			getResponse().getWriter().write(JSONUtil.result(false, e.getMessage(), "",""));
		}
	}
}
