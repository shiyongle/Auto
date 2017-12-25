package com.action.designschemes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.action.BaseAction;
import com.model.PageModel;
import com.model.designschemes.Designschemes;
import com.model.user.TSysUser;
import com.opensymphony.xwork2.ModelDriven;
import com.service.customer.CustomerManager;
import com.service.designschemes.DesignSchemesManager;
import com.service.productdemandfile.ProductdemandfileManager;
import com.service.user.TSysUserManager;
import com.util.Constant;
import com.util.JSONUtil;

@SuppressWarnings("unchecked")
public class DesignSchemeAction extends BaseAction implements ModelDriven<Designschemes> {
	private static final long serialVersionUID = -3954724788679793223L;
	protected static final String PUBLISH_JSP   = "/pages/designschemes/publish.jsp";
	protected static final String PRODUCT_JSP   = "/pages/designschemes/product_list.jsp";
	protected static final String PRODUCT_EDIT_JSP   = "/pages/designschemes/product_edit.jsp";
	protected static final String StarProductList_JSP   = "/pages/StarProduct/StarProductList.jsp";
	protected static final String ProductDetial_JSP = "/pages/StarProduct/product_detial.jsp";
	@Autowired
	private DesignSchemesManager designSchemesManager;
	@Autowired
	private TSysUserManager userManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ProductdemandfileManager productdemandfileManager;
	private Designschemes designscheme=new Designschemes();
	private PageModel<Designschemes> pageModel;// 分页组件
	private String searchKey;
	@Override
	public Designschemes getModel() {
		// TODO Auto-generated method stub
		return designscheme;
	}
	public PageModel<Designschemes> getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel<Designschemes> pageModel) {
		this.pageModel = pageModel;
	}
	public Designschemes getDesignscheme() {
		return designscheme;
	}
	public void setDesignscheme(Designschemes designscheme) {
		this.designscheme = designscheme;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	/**
	 * 跳转到发布页面，生成一个随机ID
	 * @return
	 */
	public String publish(){
		String fid = designSchemesManager.CreateUUid();
		this.getRequest().setAttribute("fid", fid);
		return PUBLISH_JSP;
	}

	/**
	 * 发布作品
	 * @return
	 */
	public String save(){
		String userId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String jsonStr = getRequest().getParameter("jsonStr");
		JSONArray jasonarry = JSONArray.fromObject(jsonStr);
		HashMap<String,String> imgmap= new HashMap<String,String>();
		for(int i=0;i<jasonarry.size();i++){
			JSONObject jo = jasonarry.getJSONObject(i);
			imgmap.put(jo.get("id").toString(), jo.get("ms").toString());
		}
		HashMap<String,String> map =  this.customerManager.getSupplierIdByUserid(userId);	
		designscheme.setFsupplierid(map.get("fsupplierid"));
		designscheme.setFsuppliername(map.get("fsuppliername"));
		designscheme.setFcreatetime(new Date());
		designscheme.setFcreatorid(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		designscheme.setFstate(1);	
		this.designSchemesManager.save(designscheme);
		this.designSchemesManager.updateImgDesc(imgmap);
		return writeAjaxResponse("success");
	}

	/**
	 * 作品列表
	 * @return
	 */
	public String list()
	{
		String userId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		TSysUser user=userManager.get(userId);
		Object[] queryParams=null;
		Map<String, String> orderby = new HashMap<String,String>();
		orderby.put("ds.fcreatetime", "desc");
		List<Object> ls = new ArrayList<Object>();
		String where="";
		if(user.getFparentid()!=""&&user.getFparentid()!=null)//子账户
		{
			//查询方案中creatorid为当前用户的方案 
			where =" where ds.fcreatorid = ?";
			ls.add(userId);
		}else//管理者
		{
			//查询的当前用户的供应商supplierId
			HashMap<String, String> map=customerManager.getSupplierIdByUserid(userId);
			//然后查询方案，条件（供应商匹配）
			where="where ds.fsupplierid=?";	
			ls.add(map.get("fsupplierid"));
		}
		if(searchKey !=null&&!"".equals(searchKey)){
			where = where +" and (ds.fname like ?)";
			ls.add("%"+searchKey+"%");
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		pageModel=this.designSchemesManager.findBySql(where,queryParams,orderby,pageNo,pageSize);
		List<Designschemes> list=pageModel.getList();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		m.put("totalRecords", pageModel.getTotalRecords());//总条数
		m.put("pageNo", pageModel.getPageNo());//当前页码
		m.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(m));
	}

	/**
	 * 根据类型加载作品数据
	 * @return
	 */
	public String loadTypeSchemes(){
		String ftype=this.getRequest().getParameter("ftype");
		Map<String, String> orderby = new HashMap<String, String>();
		orderby.put("fcreatetime", "desc");
		List<Object> ls = new ArrayList<Object>();
		ls.add(1);
		ls.add(ftype);
		String where = " where fstate = ? and ftype = ? ";
		if(searchKey !=null&&!"".equals(searchKey)){
			where = where +" and (fname like ?)";
			ls.add("%"+searchKey+"%");
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);	   
		HashMap<String,Object> map =new HashMap<String,Object>();
		pageModel = this.designSchemesManager.findAllschemeSql(where, queryParams, orderby, pageNo, pageSize);
		map.put("list", pageModel.getList());
		map.put("totalRecords", pageModel.getTotalRecords());//总条数
		map.put("pageNo", pageModel.getPageNo());//第几页
		map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(map));
	}

	/**
	 * 删除作品
	 * @throws IOException
	 */
	public void delete() throws IOException
	{
		getResponse().setCharacterEncoding("utf-8");
		//删除图片list
		try {
			String id = getRequest().getParameter("fid");
			this.productdemandfileManager.deleteSchemeImgList(id);
			this.designSchemesManager.deleteScheme(id);
			getResponse().getWriter().write(JSONUtil.result(true, "删除成功","",""));		
		} catch (Exception e) {
			// TODO: handle exception
			getResponse().getWriter().write(JSONUtil.result(false, e.getMessage(), "",""));
		}
	}

	/**
	 * 商品上下架
	 */
	public void shelvesUpOff() throws IOException
	{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String id = getRequest().getParameter("fid");
			this.designSchemesManager.updateshelves(id);
			getResponse().getWriter().write(JSONUtil.result(true, "操作成功","",""));		
		} catch (Exception e) {
			getResponse().getWriter().write(JSONUtil.result(false, e.getMessage(), "",""));
		}
	}

	/**
	 * 加载编辑页面的数据
	 * @return
	 */
	public String loadEditInfo()
	{
		String id = getRequest().getParameter("fid");
		designscheme=this.designSchemesManager.get(id);
		List<HashMap<String, String>> imglist=(List<HashMap<String, String>>) this.productdemandfileManager.getBySchemeId(id);
		this.setRequestAttribute("designscheme", designscheme);
		this.setRequestAttribute("imglist", imglist);
		return PRODUCT_EDIT_JSP;
	}

	/**
	 * 修改编辑数据
	 * @return
	 */
	public String edit(){
		String jsonStr = getRequest().getParameter("jsonStr");
		JSONArray jasonarry = JSONArray.fromObject(jsonStr);
		HashMap<String,String> imgmap= new HashMap<String,String>();
		for(int i=0;i<jasonarry.size();i++){
			JSONObject jo = jasonarry.getJSONObject(i);
			imgmap.put(jo.get("id").toString(), jo.get("ms").toString());
		}
		Designschemes scheme=this.designSchemesManager.get(designscheme.getFid());
		scheme.setFname(designscheme.getFname());
		scheme.setFdescription(designscheme.getFdescription());
		scheme.setFtype(designscheme.getFtype());
		scheme.setFauthtype(designscheme.getFauthtype());
		this.designSchemesManager.update(scheme);//修改方案设计
		this.designSchemesManager.updateImgDesc(imgmap);
		//修改图片
		return writeAjaxResponse("success");
	}

	/**
	 * 查看作品详情
	 * @return
	 * @throws ParseException 
	 */
	public String ProductDetial() throws ParseException {
		designscheme=this.designSchemesManager.get(id);
		long publishday=designscheme.getFcreatetime().getTime();
		List<HashMap<String, String>> imglist=(List<HashMap<String, String>>) this.productdemandfileManager.getBySchemeId(id);		
		this.setRequestAttribute("publishday", publishday);
		this.setRequestAttribute("designscheme", designscheme);
		this.setRequestAttribute("imglist", imglist);
		return ProductDetial_JSP;
	}

	public String StarProductList() throws UnsupportedEncodingException {
		String ftype = "";
		ftype = getRequest().getParameter("type");
		ftype=new String(ftype.getBytes("iso-8859-1"), "utf-8");
		this.getRequest().setAttribute("ftype", ftype);
		return StarProductList_JSP;
	}
}
