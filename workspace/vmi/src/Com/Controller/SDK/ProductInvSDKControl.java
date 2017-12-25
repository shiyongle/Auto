package Com.Controller.SDK;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.Inv.IOutWarehouseDao;
import Com.Dao.Inv.IProductInvDao;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.Inv.IproductindetailDao;
import Com.Dao.System.ISysUserDao;
import Com.Dao.order.IProductPlanDao;
import Com.Entity.System.Supplier;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;

@Controller
public class ProductInvSDKControl {


	public static final String WAREHOUSE_SITE_IS_UNSET = "库位未设置";
	public static final String WAREHOUSE_IS_UNSET = "仓库未设置";
	public static final String THERE_IS_TOO_MANY_SUPPLIER = "当前用户关联太多制造商";
	public static final String THERE_IS_NO_SUPPLIER = "当前用户没有关联制造商";

	Logger log = LoggerFactory.getLogger(ProductInvSDKControl.class);

	@Resource
	private IProductInvDao productInvDao;
	@Resource
	private ISysUserDao SysUserDao;
	@Resource
	private IStorebalanceDao storebalanceDao; 
	@Resource
	private IOutWarehouseDao outWarehouseDao;
	@Resource
	private IproductindetailDao ProductindetailDao;
	
	@Resource
	private IProductPlanDao productplan;
	/***
	 *  出入库接口
	 * @param request:productplanid：制造商订单ID；fproductid：产品ID;famount:出入库数量--+入，-出
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doInOutWarehouse")
	public String doInOutWarehouse(
			HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		reponse.setCharacterEncoding("utf-8");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String productplanid=request.getParameter("productplanid");
		String ftraitid=request.getParameter("traitid")==null?"":request.getParameter("traitid");
		Integer famount = 0;
		try {
			if(request.getParameter("ftype")==null)
			{
				throw new DJException("ftype为空");
			}
			String ftype=request.getParameter("ftype");//判断是否存在普通调拨
//		String sql="";

			if(!DataUtil.IntegerCheck(request.getParameter("famount")==null?"":request.getParameter("famount").toString()))
			{
				throw new DJException("输入的数量不是整数或为0！");
			}
			famount=new Integer(request.getParameter("famount"));
			if(famount==0)
			{
				throw new DJException("输入的数量不能为0！");
			}
			
			SysUser userinfo=SysUserDao.Query(userid);
			if(userinfo.getFisfilter()==1)
			{
				throw new DJException("管理帐号，不能操作");
			}
			String[] warehouserAndSiteAndSuppliers = findWAREHOUSEandSITE(
					request, reponse);
			
			if(ftype.contains("普通调拨"))
			{
				userid+=",普通调拨";
			}
			
			if(productplanid!=null&&!"".equals(productplanid))//订单类型
			{
				DataUtil.verifyNotNullAndDataAndPermissions(null,
						new String[][]{{"productplanid","t_ord_productplan",null,"FSUPPLIERID"}}, request,
						productInvDao);
				//入库就会关闭订单，导致后续入库一直提示关闭，不能入库  ,暂时放开此控制    BY  CC 2014-07-10
				List<HashMap<String,Object>> result=productInvDao.QueryBySql("select * from t_ord_productplan where faffirmed=1 and fid='"+productplanid+"'");
				if(result.size()==0)
				{
					throw new DJException("该订单未确认，不能操作出入库！");
				}
				HashMap map=result.get(0);
				if(famount>0)//入库
				{
					ProductindetailDao.saveproductindetail((String)map.get("FID"),famount,warehouserAndSiteAndSuppliers[0],warehouserAndSiteAndSuppliers[1],userid,(String)map.get("FPRODUCTDEFID"),(String)map.get("forderid"),(String)map.get("fparentorderid"),warehouserAndSiteAndSuppliers[2],ftraitid,true);
					
				}else//出库
				{
					outWarehouseDao.saveOutWarehouse((String)map.get("FID"),famount*-1,warehouserAndSiteAndSuppliers[0],warehouserAndSiteAndSuppliers[1],userid,(String)map.get("FPRODUCTDEFID"),(String)map.get("forderid"),(String)map.get("fparentorderid"),warehouserAndSiteAndSuppliers[2],ftraitid,true);			
				}
			}else//通知类型
			{
				String fproductid = request.getParameter("fproductid");
				DataUtil.verifyNotNullAndDataAndPermissions(new String[][]{{"fproductid","产品ID"}},
						new String[][]{{"fproductid","t_pdt_productdef",null,null}}, request,
						productInvDao);
				List<HashMap<String, Object>> checksupplier=productInvDao.QueryBySql(" select fid from t_bd_productcycle where fproductdefid='"+fproductid+"' and fsupplierid='"+warehouserAndSiteAndSuppliers[2]+"' ");
				if(checksupplier.size()<=0)
				{
					throw new DJException("用户关联的制造商和产品的制造商不匹配，不能操作");
				}
			checksupplier=productInvDao.QueryBySql(" select p.fid,ifnull(f.fnewtype,0) fnewtype ,p.fproductid,'1' as famount from t_pdt_vmiproductparam  p left join t_pdt_productdef f on f.fid=p.fproductid where p.fproductid='"+fproductid+"' and p.fsupplierid='"+warehouserAndSiteAndSuppliers[2]+"' and p.ftype=0  ");
			if(checksupplier.size()<=0)
			{
				throw new DJException("该产品没有设置通知管理类型，不能操作");
			}
			int producttype=Integer.valueOf(checksupplier.get(0).get("fnewtype").toString());
			if(producttype==0)
			{
				throw new DJException("该产品类型有误");
			}
			if(producttype==2||producttype==4)
			{
				
				List<HashMap<String, Object>> productlists=productInvDao.QueryBySql("select fproductid,famount from t_pdt_productdefproducts where fparentid='"+fproductid+"'");
				if(productlists.size()>0)
				{
					checksupplier.clear();
					checksupplier.addAll(productlists);
				}
			}
			productInvDao.updateWarehouseInOutAndStorebalance(checksupplier, userid,
					famount, warehouserAndSiteAndSuppliers[0],
					warehouserAndSiteAndSuppliers[1],
					warehouserAndSiteAndSuppliers[2]);
			}

			reponse.getWriter().write(JsonUtil.result(true, "", "", ""));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	/**
	 * 根据供应商查询对应的库位以及仓库
	 * @param supplierID
	 * @return
	 */
	private String[] selectWAREHOUSEandSITEBysupplierID(String supplierID) {
		// TODO Auto-generated method stub

		String hql = " from  Supplier s where s.fid = '%s' ";

		hql = String.format(hql, supplierID);

		Supplier supplierT = (Supplier) storebalanceDao.QueryByHql(hql).get(0);

		String[] warehouseParam = new String[] { supplierT.getFwarehouseid(),
				supplierT.getFwarehousesiteid() };

		// 空值报错
		if (warehouseParam[0] == null || warehouseParam[0].equals("")) {

			throw (new DJException(WAREHOUSE_IS_UNSET));

		}

		if (warehouseParam[1] == null || warehouseParam[1].equals("")) {

			throw (new DJException(WAREHOUSE_SITE_IS_UNSET));

		}

		return warehouseParam;
	}
	/**
	 * 根据用户获取观关联的供应商
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private String selectSupplierByUser(String userId) throws Exception {
		// TODO Auto-generated method stub

		String supplierID = null;

		String sql = " SELECT FSUPPLIERID FROM t_bd_usersupplier where FUSERID = '%s' union select s.fsupplierid from  t_bd_rolesupplier s left join t_sys_userrole r on r.froleid=s.froleid where r.fuserid='%s'";

		sql = String.format(sql, userId,userId);

		List<HashMap<String, Object>> listT = storebalanceDao.QueryBySql(sql);

		if (listT.size() > 1) {

			throw (new DJException(THERE_IS_TOO_MANY_SUPPLIER));

		} else if (listT.size() == 0) {

			throw (new DJException(THERE_IS_NO_SUPPLIER));

		} else {

			supplierID = (String) listT.get(0).get("FSUPPLIERID"); 
		}

		return supplierID;
	}
	/**
	 *  查找用户对应的供应商以及供应商对应的仓库与库位
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	private String[] findWAREHOUSEandSITE(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {

		reponse.setCharacterEncoding("utf-8");

		String userId = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();

			String supplierID = selectSupplierByUser(userId);

			String[] wAREHOUSEandSITE = selectWAREHOUSEandSITEBysupplierID(supplierID);

			StringBuilder sb = new StringBuilder();

			sb.append(supplierID);

			// 用逗号隔开的参数

			return new String[] { wAREHOUSEandSITE[0], wAREHOUSEandSITE[1],
					supplierID };
	}
	
	/**
	 * 根据制造商入库明细ID更新是否同步（fstate）为1
	 * @param request 
	 * @param reponse
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("updateSupplierIndetail")
	public String updateSupplierIndetail(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		String fid = request.getParameter("fid");
		if(!StringUtils.isEmpty(fid)){
			fid = "'"+fid.replace(",", "','")+"'";
			String sql = "update t_inv_supplierIndetail set fstate=1 where fid in("+fid+")";
			try {
				int i = productplan.ExecBySql(sql);
				if(i>0){
					reponse.getWriter().write(JsonUtil.result(true, "修改成功!", "",""));
					return null;
				}else{
					reponse.getWriter().write(
							JsonUtil.result(false, "修改失败!", "", ""));
				}
			} catch (DJException e) {
				// TODO: handle exception
				reponse.getWriter().write(
						JsonUtil.result(false, "修改失败!", "", ""));
			}
		}
		return null;
	}
	
	/**
	 * 查询东经同步状态（fstate）为0的外购单入库明细接口；
	 * @param request 
	 * @param reponse
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getSupplierIndetailSDK")
	public String getSupplierIndetailSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
			try {
				String sql = "select fid,fsupplierid,fpcmordernumber,famount,finqty,fintime,ifnull(fstate,0) fstate from t_inv_supplierIndetail where ifnull(fstate,0)=0 ";
				ListResult result=productplan.QueryFilterList(sql, request);
				reponse.getWriter().write(JsonUtil.result(true, "", result));
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
	}

}
