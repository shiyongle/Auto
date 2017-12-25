package com.action.mystock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.PageModel;
import com.model.address.Address;
import com.model.custproduct.TBdCustproduct;
import com.model.mystock.MyStockQuery;
import com.model.mystock.Mystock;
import com.model.productdemandfile.Productdemandfile;
import com.service.address.AddressManager;
import com.service.customer.CustomerManager;
import com.service.custproduct.TBdCustproductManager;
import com.service.mystock.MystockManager;
import com.service.productdemandfile.ProductdemandfileManager;
import com.util.Constant;
import com.util.JSONUtil;
import com.util.StringUitl;

public class MystockAction extends BaseAction{

	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private AddressManager addressManager;
	@Autowired
	private ProductdemandfileManager productdemandfileManager;
	@Autowired
	private TBdCustproductManager custproductManager;
	@Autowired
	private ProductdemandfileManager productdemanManager;
	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public int getMouth() {
		return mouth;
	}

	public void setMouth(int mouth) {
		this.mouth = mouth;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public Mystock getMystock() {
		return mystock;
	}

	public void setMystock(Mystock mystock) {
		this.mystock = mystock;
	}

	public MyStockQuery getMyStockQuery() {
		return myStockQuery;
	}

	public void setMyStockQuery(MyStockQuery myStockQuery) {
		this.myStockQuery = myStockQuery;
	}


	/***
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * Company: CPS-TEAM
	 * </p>
	 * 
	 * @author WANGC
	 * @date 2015-8-12 下午4:03:37
	 */
	private static final long serialVersionUID = -8530606026964494926L;
	@Autowired
	private MystockManager mystockManager;
	private PageModel<Mystock> pageModel;// 分页组件
	private Mystock mystock;// 实体对象
	private MyStockQuery myStockQuery;// 多条件查询的封装对象
	private int day;//首次发货与创建时间的时间差，也就是修改页面的2日内
	private int mouth;//备货周期与创建时间差，
	private Date currentTime;
	protected static final String LIST_JSP = "/pages/mystock/list.jsp";
	protected static final String EDIT_JSP = "/pages/mystock/edit.jsp";

	public PageModel<Mystock> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<Mystock> pageModel) {
		this.pageModel = pageModel;
	}
	
	/**
	 * 进入备货列表页面
	 * 
	 * @return
	 */
	public String list() {
		System.out.println("111");
		return LIST_JSP;
	}

	/**
	 * 多条件查询备货列表
	 * 
	 * @return
	 */
	public String load() {
		//2016-4-2 by les 统一改成根据客户-只查看自己过滤	
		//String where = " where tsy.FID  = ?";
		String where =" where m.fcustomerid = ?";	
		Object[] queryParams = null;
		Map<String, String> orderby = new HashMap<String, String>();
		List<Object> lst = new ArrayList<Object>();
		lst.add(getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID)
				.toString());
		if (myStockQuery != null) {
			System.out.println("*************************");
			/*if (!("").equals(myStockQuery.getFordertimeBegin())) {// 下单开始
				where = where
						+ " and date_format(m.fplanamount,'%Y-%m-%d') >= ? ";
				lst.add(myStockQuery.getFordertimeBegin());
			}
			if (!("").equals(myStockQuery.getFordertimeEnd())) {// 下单结束
				where = where
						+ " and date_format(m.fplanamount,'%Y-%m-%d') <= ? ";
				lst.add(myStockQuery.getFordertimeEnd());
			}
			if (!("").equals(myStockQuery.getFconsumetimeBegin())) {// 周期开始
				where = where
						+ " and date_format(m.ffinishtime,'%Y-%m-%d') >= ? ";
				lst.add(myStockQuery.getFconsumetimeBegin());
			}
			if (!("").equals(myStockQuery.getFconsumetimeEnd())) {// 周期结束
				where = where
						+ " and date_format(m.ffinishtime,'%Y-%m-%d') <= ? ";
				lst.add(myStockQuery.getFconsumetimeEnd());
			}*/
			if (!("").equals(myStockQuery.getSearchKey())) {// 关键字
				where = where
						+ " and (c.fname like ? or s.fname like ? or c.FSPEC like ? or m.fnumber like ? or m.fpcmordernumber like ? or m.fdescription like ? )";
				lst.add("%" + myStockQuery.getSearchKey() + "%");
				lst.add("%" + myStockQuery.getSearchKey() + "%");
				lst.add("%" + myStockQuery.getSearchKey() + "%");
				lst.add("%" + myStockQuery.getSearchKey() + "%");
				lst.add("%" + myStockQuery.getSearchKey() + "%");
				lst.add("%" + myStockQuery.getSearchKey() + "%");
			}
			if (!("").equals(myStockQuery.getFstate()) && myStockQuery.getFstate() !=null) {

				if (myStockQuery.getFstate() == 0) {// 未接收
					where = where + " and m.fstate = ?";
					lst.add(myStockQuery.getFstate());
				} else if (myStockQuery.getFstate() > 0) {// 已接受
					where = where + " and (m.fstate = ? or m.fstate = ? or m.fstate = ?)";
					lst.add(1);
					lst.add(2);
					lst.add(3);
				}
			}
		}
		queryParams = (Object[]) lst.toArray(new Object[lst.size()]);
		System.out.println(where);
		pageModel = this.mystockManager.findBySql(where, queryParams, orderby,
				pageNo, pageSize);
		List<Mystock> mtlist = pageModel.getList();
		/*
		 * 改为通用的方法获取图片字节流;
		 * for(int i=0;i<mtlist.size();i++){
			mtlist.get(i).setLatestpathImg(this.getPicture(mtlist.get(i).getFcustproductid()));
		}*/
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", pageModel.getList());
		map.put("totalRecords", pageModel.getTotalRecords());// 总条数
		map.put("pageNo", pageModel.getPageNo());// 第几页
		map.put("pageSize", pageModel.getPageSize());// 每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(map));
	}



	/**
	 * 删除
	 */
	public String delete() {
		
		Mystock s=mystockManager.get(id);
		if((s.getFordered()!=null&&s.getFordered()==1)||s.getFstate()>0)
		{
			return writeAjaxResponse("已接收订单不能删除!");
		}
		this.mystockManager.delete("mystock", id);
		return writeAjaxResponse("success");
	}

	/**
	 * 跳转到修改的页面，通过action查到相应的产品信息
	 * @throws ParseException 
	 */
	public String edit() {
		Mystock mt = this.mystockManager.get(id);
		getRequest().setAttribute("mystock", mt);
		//首次发货时间与创建时间相减的天数来确认前台修改界面的选中
		Long long_day=mt.getFfinishtime().getTime()-mt.getFcreatetime().getTime();
		day=(int) (long_day/(1000*60*60*24)+1);
		//备货周期与创建时间相减的天数来确认前台修改页面中的选中
		Long long_mouth=mt.getFconsumetime().getTime()-mt.getFcreatetime().getTime();
		mouth=(int) (long_mouth/(1000*60*60*24)+1);
		//获取当前时间
		currentTime=new Date();
		return EDIT_JSP;
	}
	/**
	 * 修改备货信息
	 */
	public String update(){
		Mystock mt = this.mystockManager.get(mystock.getFid());
		mt.setFplanamount(mystock.getFplanamount());
		mt.setFaveragefamount(mystock.getFaveragefamount());
		mt.setFfinishtime(mystock.getFfinishtime());
		mt.setFconsumetime(mystock.getFconsumetime());
		mt.setFremark(mystock.getFremark());
		this.mystockManager.update(mt);
		return writeAjaxResponse("success");
	}
	
	/*** 备货-首发日期*/
	public String setFirstTime(){
		int days=Integer.parseInt(getRequest().getParameter("day"));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.DAY_OF_MONTH, days);
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("date", sf.format(c2.getTime()));
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	/*** 备货-月周期*/
	public String setTime(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.MONTH, 1);
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("date", sf.format(c2.getTime()));
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	public String editMystockOrder(){
		String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fid = getRequest().getParameter("fid");
		Mystock m =this.mystockManager.get(fid);
		
		try {
			//库存
			/*List<Supplier> supplier = this.custproductManager.getBySupplier(userFid);*/
			List<HashMap<String,Object>> list = this.custproductManager.getCustproductStock(m.getFcustomerid(),m.getFsupplierid());
			
			getRequest().setAttribute("stock", list);//客户产品库存
		} catch (Exception e) {
			// TODO: handle exception
		}
		//地址
		String fcustomerid = this.customerManager.getCustomerIdByUserid(userFid);
		List<Address> address= new ArrayList<Address>();
		if(!StringUitl.isNullOrEmpty(fcustomerid)){
			address =this.addressManager.getByCustomerId(fcustomerid);
		}
		//附件
		List<Productdemandfile> listPfile = this.productdemanManager.getByParentId(m.getFcustproductid());
		List<String> url =new ArrayList<String>();
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort();
		if(listPfile.size() >0){
			String path = "";
			for(int i=0;i<listPfile.size();i++){
				if(i>=5){//前端最多只能显示5张图片
					break;
				}
				path = (listPfile.get(i).getFpath());
				path =basePath + path.split("webapps")[1];
				url.add(path);
			}
		}
		//客户产品信息
		TBdCustproduct c = this.custproductManager.get(m.getFcustproductid());
		
		getRequest().setAttribute("custproduct", c);//客户产品信息
		getRequest().setAttribute("fsupplierid", m.getFsupplierid());//制造商ID
		getRequest().setAttribute("productfile", url);//客户产品附件
		getRequest().setAttribute("address", address);//客户地址
		getRequest().setAttribute("type", 0);//0"下单按钮" 1"加入购物车" 2"批量加入购物车"
		getRequest().setAttribute("fcustproducts", m.getFcustproductid());//记录客户产品ID
		getRequest().setAttribute("mystock", m);//记录客户产品ID
		getRequest().setAttribute("picurl", this.getPicture(c.getFid()));//放置默认大图片
		getRequest().setAttribute("fbacthstock", true);//记录客户产品ID
		return "/pages/deliverapply/orderOnline.jsp";
	}
	
	/*** 通过方案ID获取图片路径*/
	public String getPicture(String pid){
		String path = null;
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort();
		//2015-10-30 调用原来方法
		List<Productdemandfile> listfile = productdemandfileManager.getImagesByParentId(pid);
		if(listfile.size()>0){
			path = (listfile.get(0).getFpath()).replace("vmifile", "smallvmifile");
			path =basePath + path.split("webapps")[1];	
		}
		if(path==null){
			path = basePath + "/vmifile/defaultpic.png";
		}
		return path;
	}
}
