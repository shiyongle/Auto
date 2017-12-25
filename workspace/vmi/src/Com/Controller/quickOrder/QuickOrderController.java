package Com.Controller.quickOrder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.order.IDeliverapplyDao;
import Com.Dao.quickOrder.ICusPrivateDeliversDao;
import Com.Dao.quickOrder.IQuickOrderDao;
import Com.Entity.System.Address;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Productdef;
import Com.Entity.System.Productreqallocationrules;
import Com.Entity.System.Supplier;
import Com.Entity.System.SupplierDeliverTime;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;
import Com.Entity.order.CusPrivateDelivers;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Mystock;

@Controller("QuickOrderController")
public class QuickOrderController {
	@Resource
	private IQuickOrderDao QuickOrderDao;
	@Resource
	private IDeliverapplyDao deliverapplyDao; 
	@Resource
	private IBaseSysDao baseSysDao;

	@Resource
	private ICusPrivateDeliversDao  cusPrivateDeliversDao;
	/**
	 * 设置客户产品为常用订单
	 * @param request fid 多个用，隔开
	 * @param response
	 */
	@RequestMapping("/setCustproductCommon")
	public String setCustproductCommon(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		String fstate=StringUtils.isEmpty(request.getParameter("g"))?"0":request.getParameter("g");//标记
		try {
			QuickOrderDao.ExecCustproductCommon(fidcls,fstate);
			result = JsonUtil.result(true,"设置成功", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
		}
		reponse.getWriter().write(result);

		return null;

	}

	
	
	
//	@RequestMapping("/GetQuickCustProductList")
//	public String GetQuickCustProductList(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException {
//		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
//		String fsupplierid="";
//		if(request.getParameter("sort")==null){
//		request.setAttribute("djsort", "c.fiscommon desc,c.fcreatetime desc");
//		}
//		String sql="";
////		="SELECT c.fid,c.fname productName,c.fspec productSpec,c.fordercount orderCount,c.forderamount productCount ,f.fsupplierid FROM t_bd_custproduct c LEFT JOIN t_pdt_productdef  f ON c.fproductid=f.fid where ifnull(c.fcharacterid,'')='' ";
//		ListResult result;
//		reponse.setCharacterEncoding("utf-8");
//		try {
//			String DDefaultfilter = request.getParameter("Defaultfilter");
//			//没有选制造商 默认不出现数据
//			if (DDefaultfilter == null || "".equals(DDefaultfilter)|| !DDefaultfilter.contains("fsupplierid")) {
//				reponse.getWriter().write(JsonUtil.result(true,"","",""));
//				return null;
//			}
//			//根据选择的制造商，判断改账号是否多个客户存在该制造商
//			JSONArray j = JSONArray.fromObject(DDefaultfilter);
//			for (int i = j.size() - 1; i >= 0; i--) {
//				JSONObject o = j.getJSONObject(i);
//				DDefaultfilter = o.getString("myfilterfield");
//				if(DDefaultfilter.contains("fsupplierid")&&!o.getString("CompareType").contains("null"))
//				{
//					String customerid=GetIsSingleSupplier(userid,o.getString("value"));
//					//sql="SELECT c.fid,c.fname productName,c.fspec productSpec,c.fordercount orderCount,c.forderamount productCount ,'"+customerid+"' fcustomerid FROM t_bd_custproduct c inner JOIN t_pdt_productdef  f ON c.fproductid=f.fid where ifnull(c.fcharacterid,'')='' ";
//					sql="SELECT c.fid,c.fname productName,c.fspec productSpec, c.fcustomerid,0 stock,0 inLineStock,0 finishedProductStock,0 neededToSendCount,c.flastorderfamount recentlyOrderCount,c.flastordertime recentlyOrderDate,c.fiscommon FROM t_bd_custproduct c inner JOIN t_pdt_productdef  f ON c.fproductid=f.fid where c.fcustomerid='"+customerid+"' and c.feffect=1 and ifnull(c.fcharacterid,'')='' ";
//
//					break;
//				}
//				continue;
//			}
//			if("".equals(sql))
//			{
//				reponse.getWriter().write(JsonUtil.result(true,"","",""));
//				return null;
//			}
//			result = QuickOrderDao.QueryFilterList(sql, request);
//			//对应的客户产品的图片赋值
//			for (HashMap<String, Object> record : result.getData()) 
//			{
//				String pathT = getQuickFilePath(request,(String)record.get("fid"));
////				if(pathT!=null&&!"".equals(pathT))
//					record.put("proImgUrl",StringUtils.isEmpty(pathT)?"/vmifile/defaultpic.png":pathT);
//				
//			}
//			reponse.getWriter().write(JsonUtil.result(true,"",result));
//		} catch (DJException e) {
//			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
//		}
//	
//		return null;
//	}
	
	@RequestMapping("/GetQuickCustProductList")
	public String GetQuickCustProductList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		String fsupplierid="";
		if(request.getParameter("sort")==null){
		request.setAttribute("djsort", "c.fiscommon desc,c.fcreatetime desc");
		}
		String sql="";
//		="SELECT c.fid,c.fname productName,c.fspec productSpec,c.fordercount orderCount,c.forderamount productCount ,f.fsupplierid FROM t_bd_custproduct c LEFT JOIN t_pdt_productdef  f ON c.fproductid=f.fid where ifnull(c.fcharacterid,'')='' ";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			String DDefaultfilter = request.getParameter("Defaultfilter");
			//没有选制造商 默认不出现数据
			if (DDefaultfilter == null || "".equals(DDefaultfilter)|| !DDefaultfilter.contains("fsupplierid")) {
				reponse.getWriter().write(JsonUtil.result(true,"","",""));
				return null;
			}
			//根据选择的制造商，判断改账号是否多个客户存在该制造商
			JSONArray j = JSONArray.fromObject(DDefaultfilter);
			for (int i = j.size() - 1; i >= 0; i--) {
				JSONObject o = j.getJSONObject(i);
				DDefaultfilter = o.getString("myfilterfield");
				if(DDefaultfilter.contains("fsupplierid")&&!o.getString("CompareType").contains("null"))
				{
					String customerid=GetIsSingleSupplier(userid,o.getString("value"));
					sql="SELECT ";
					int  isstock=((Supplier)QuickOrderDao.Query(Supplier.class, o.getString("value"))).getFisManageStock();
					fsupplierid=o.getString("value");
				List<HashMap<String, Object>> timeconfig=QuickOrderDao.QueryBySql("SELECT fdays,fdefaultdays from t_sys_supplierdelivertime where fsupplierid='"+fsupplierid+"'");
				if(timeconfig.size()>0){
					sql+=timeconfig.get(0).get("fdays")+" fdays,"+timeconfig.get(0).get("fdefaultdays")+" fdefaultdays,";
				}
					
			//sql="SELECT c.fid,c.fname productName,c.fspec productSpec,c.fordercount orderCount,c.forderamount productCount ,'"+customerid+"' fcustomerid FROM t_bd_custproduct c inner JOIN t_pdt_productdef  f ON c.fproductid=f.fid where ifnull(c.fcharacterid,'')='' ";
//					sql="SELECT c.fid,c.fname productName,c.fspec productSpec, c.fcustomerid,0 stock,0 inLineStock,0 finishedProductStock,0 neededToSendCount,c.flastorderfamount recentlyOrderCount,c.flastordertime recentlyOrderDate,c.fiscommon FROM t_bd_custproduct c inner JOIN t_pdt_productdef  f ON c.fproductid=f.fid where c.fcustomerid='"+customerid+"' and c.feffect=1 and ifnull(c.fcharacterid,'')='' ";
					sql+=" c.fid,'"+isstock+"' hasStock,c.fproductid,c.fname productName,c.fnumber productNumber,c.fspec productSpec,c.fprice,  c.fcustomerid,c.flastorderfamount recentlyOrderCount,c.flastordertime recentlyOrderDate,c.flastordertype  recentlyOrderType, c.fiscommon FROM t_bd_custproduct c inner JOIN t_pdt_productdef  f ON c.fproductid=f.fid where c.fcustomerid='"+customerid+"' and c.feffect=1 and ifnull(c.fcharacterid,'')='' ";
					break;
				}
				continue;
			}
			if("".equals(sql))
			{
				reponse.getWriter().write(JsonUtil.result(true,"","",""));
				return null;
			}
			result = QuickOrderDao.QueryFilterList(sql, request);
			//对应的客户产品的图片赋值
			HashMap balancemap=null;
			for (HashMap<String, Object> record : result.getData()) 
			{
				String pathT = getQuickFilePath(request,(String)record.get("fid"));
//				if(pathT!=null&&!"".equals(pathT))
					record.put("proImgUrl",StringUtils.isEmpty(pathT)?"/vmifile/defaultpic.png":pathT);
					if("1".equals((String)record.get("hasStock"))){
					String	fproductid=(String)record.get("fproductid");
					sql="select sum(amount) amount ,sum(fusedqty) fusedqty,sum(fmakingqty) fmakingqty,sum(amount+fmakingqty) fbalanceqty from ("
					+" select sum(fbalanceqty) amount,0 fusedqty,0 fmakingqty from  t_inv_storebalance  where  fproductid = '%s' and fsupplierid='%s' "
					+" union select 0 amount,sum(fusedqty) fusedqty,0 fmakingqty from t_inv_usedstorebalance left join t_ord_deliverorder o ON o.fid= t_inv_usedstorebalance.fdeliverorderid WHERE fusedqty > 0 AND t_inv_usedstorebalance.fproductid = '%s'   AND fsupplierid='%s' "
					+" union select 0 amount,0 fusedqty, sum(case when famount < ifnull(fstockinqty, 0) then 0 else famount - ifnull(fstockinqty, 0) end) fmakingqty "
					+" from t_ord_productplan  where fcloseed = 0 and faudited = 1 and fboxtype = 0 and fproductdefid = '%s' and fsupplierid='%s' ) s";
					sql=String.format(sql, fproductid,fsupplierid,fproductid,fsupplierid,fproductid,fsupplierid);
					balancemap = (HashMap<String, Object>)QuickOrderDao.QueryBySql(sql).get(0);
					record.put("finishedProductStock", balancemap.get("amount"));//成品
					record.put("neededToSendCount",balancemap.get("fusedqty"));//发货数量
					record.put("inLineStock", balancemap.get("fmakingqty"));//在生产
					record.put("stock",balancemap.get("fbalanceqty"));//库存
					}
				
			}
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}

	/**
	 * 上传附件
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/uploadQuickCustProductImg")
	public String uploadQuickCustProductImg(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		String result = "";
		try {
			QuickOrderDao.ExecUploadQuickCustProductImg(request);

			result = JsonUtil.result(true, "保存成功!", "", "");

		} catch (Exception e) {

			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.getWriter().write(result); 
		return null;
	}
	

/**
 * 删除图片
 * @param request
 * @param reponse
 * @return
 * @throws IOException
 */
	@RequestMapping("/deleteQuickCustProductAccessorys")
	public String deleteQuickCustProductAccessorys(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		String result = "";
		try {
			
			String fids = request.getParameter("fidcls");
			String message = QuickOrderDao.ExecDelFileQuick(fids);
			result = JsonUtil.result(true, (message==null)?"删除成功!":message, "", "");

		} catch (DJException e) {

			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.getWriter().write(result); 
		return null;
	}
	/**
	 * 根据用户对应的客户，查找相应的供应商
	 * @param request
	 * @param response
	 */
	@RequestMapping("/GetSuppliersOfCustomer")
	public String GetSuppliersOfCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			String fcustomerid=QuickOrderDao.getCurrentCustomerids(userid);
			if("".equals(fcustomerid))
			{
				 throw new DJException("该账号没有关联任何客户，请联系客服进行设置");
			}
			String sql="SELECT DISTINCT fsupplierid fid ,s.fname fname,r.fbacthstock  FROM t_pdt_productreqallocationrules r LEFT JOIN t_sys_supplier s ON s.fid=r.fsupplierid  where  r.fcustomerid IN ("+fcustomerid+")";
			result = QuickOrderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	
	
	/**
	 * 根据用户对应的客户，查找相应的供应商
	 * @param request
	 * @param response
	 */
	@RequestMapping("/GetIsSingleSupplierRe")
	public String GetIsSingleSupplierRe(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			String fsupplierid = request.getParameter("fsupplierid");
			GetIsSingleSupplier(userid,fsupplierid);
			reponse.getWriter().write(JsonUtil.result(true,"","",""));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	
	/**
	 * 根据用户对应的客户，查找客户中是否存在相同的制造商
	 * @param fuserid
	 * @param fsupplierid
	 * @throws DJException
	 */
	public String GetIsSingleSupplier(String fuserid,String fsupplierid) throws DJException
	{
		String fcustomerid=QuickOrderDao.getCurrentCustomerids(fuserid);
		if("".equals(fcustomerid))
		{
			 throw new DJException("该账号没有关联任何客户，请联系客服进行设置");
		}
		if(StringUtils.isEmpty(fsupplierid)) throw new DJException("请选择制造商");
		List<HashMap> suplierlist=QuickOrderDao.QueryBySql("SELECT COUNT(fid),fcustomerid FROM t_pdt_productreqallocationrules  WHERE  fcustomerid IN ("+fcustomerid+")and fsupplierid='"+fsupplierid+"' GROUP BY fsupplierid HAVING COUNT(fid)=1");
		if(suplierlist.size()==0){
			 throw new DJException("该账号存在相同制造商，请联系客服进行维护");
		}
		return (String)suplierlist.get(0).get("fcustomerid");
	}
	
	
	/**
	 * 根据客户产品id获取对应的图纸（减缩图）
	 * @param fuserid
	 * @param fsupplierid
	 * @throws DJException
	 */
		@RequestMapping("/GetQuickCustproductImg")
		public String GetQuickCustproductImg(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");
			try {
				request.setAttribute("djsort", "f.fcreatetime desc");
				String fid = request.getParameter("fid");
				String sql = " SELECT ifnull(f.fbid,f.fid) fid,f.fname,f.fpath 'url'  FROM  t_ord_productdemandfile f where fparentid like '%s' and NOT EXISTS(SELECT p.fparentid FROM t_ord_productdemandfile p WHERE p.fparentid = '%s' and p.fbid=f.fid ) ";
				sql = String.format(sql, "%"+fid,"#"+fid);
				ListResult result = QuickOrderDao.QueryFilterList(sql, request);

				for (HashMap<String, Object> record : result.getData()) {
					String pathT = getFilePathByPath((String)record.get("fpath"));
					record.put("fpath", pathT);
				}
				
				reponse.getWriter().write(JsonUtil.result(true,"",result));
			} catch (DJException e) {
				reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
			}
		
			return null;
		}
	
		
		/**
		 * 保存要货申请
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/SaveQuickOrder")
		public  String SaveQuickOrder(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
		String result = "";
		Deliverapply vinfo=null;
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH");
		try{
			DataUtil.verifyNotNullAndDataAndPermissions(new String[][]{{"suplierID","制造商"},{"cusProductID","客户产品"},{"addressID","地址"}},
					null, request,
					QuickOrderDao);
		String fsupplierid=request.getParameter("suplierID");
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String customerid=GetIsSingleSupplier(userid,fsupplierid);//判断是否是单一供应商

		vinfo=new Deliverapply();
		vinfo.setFid(QuickOrderDao.CreateUUid());
		vinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
		vinfo.setFcreatorid(userid);
		vinfo.setFcreatetime(new Date());
		vinfo.setFiscreate(0);
		vinfo.setFordernumber("");
		vinfo.setFupdatetime(new Date());
		vinfo.setFupdateuserid(userid);
		vinfo.setFcustomerid(customerid);
		vinfo.setFcusproductid(request.getParameter("cusProductID"));
		vinfo.setFsupplierid(fsupplierid);
		vinfo.setFaddressid(request.getParameter("addressID"));
		Address addinfo=(Address)QuickOrderDao.Query(Address.class,vinfo.getFaddressid());
		if(addinfo==null)throw new DJException("地址不正确！");
		vinfo.setFaddress(addinfo.getFdetailaddress());
		vinfo.setFlinkman(addinfo.getFlinkman());
		vinfo.setFlinkphone(addinfo.getFphone());
		
		if(!DataUtil.dateFormatCheck(request.getParameter("submitDate"), true)){
			throw new DJException("送达日期格式错误或小于当前时间！");
		}
		String timeFrame= StringUtils.isEmpty(request.getParameter("timeFrame"))?"09":(request.getParameter("timeFrame").length()==1?"0":"")+request.getParameter("timeFrame");
		String farrivetime=request.getParameter("submitDate")+" "+timeFrame;
		
		if(!DataUtil.dateFormatCheck(farrivetime+":00:00.000", true)){
			throw new DJException("送达日期格式错误或小于当前时间！");
		}
		vinfo.setFarrivetime(f.parse(farrivetime));
		if(!DataUtil.positiveIntegerCheck(request.getParameter("count"))){
			throw new DJException("要货数量必须大于0！");
		}
		vinfo.setFamount(new Integer(request.getParameter("count")));
		vinfo.setFdescription(request.getParameter("remark"));
		QuickOrderDao.ExecSaveQuickDeliverapply(vinfo);
		
		result = JsonUtil.result(true,"保存成功!", "", "");

		}
		catch(Exception e)
		{
			result = JsonUtil.result(false,e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
			return null;

		}
		
		
		
		/**
		 * 根据客户产品获取最新图片路径
		 * @param request
		 * @param fid
		 * @return
		 * @throws IOException
		 */
		public String getQuickFilePath(HttpServletRequest request,String fid) throws IOException{
			try {
				String sql=" SELECT d.fid,IFNULL(f.fpath,d.fpath) fpath FROM t_ord_productdemandfile   d  LEFT JOIN t_ord_productdemandfile f ON d.fid=f.fbid WHERE d.fparentid='"+fid+"' ORDER BY d.fcreatetime desc";
//				String sql = "select * from t_ord_productdemandfile where fparentid = '"+fid+"' order by  fcreatetime desc";
				List<HashMap<String,Object>> list = QuickOrderDao.QueryBySql(sql);
				if(list.size()==0)
				{
					return "";
				}
				String path = (String)list.get(0).get("fpath");
				return getFilePathByPath(path);
				
			} catch (DJException e) {
				// TODO: handle exception
				return null;
			}
		}
	
		/**
		 * 根据路径构造路径
		 * @param request
		 * @param fid
		 * @return
		 * @throws IOException
		 */
		public String getFilePathByPath(String path)
		{
			if("".equals(path)||path==null)
			{
				return "";
			}
			if(path.lastIndexOf("vmifile")==-1){
				path = "file/schemedesign/"+ path.substring(path.lastIndexOf("/") + 1);
				return path;
			}else{
				String newpath = "/"+path.substring(path.lastIndexOf("vmifile"));
				return newpath;
			}
		}
		
		//获取该客户产品所有图片
		@RequestMapping("/selectQuickAccessoryByCusPId")
		public String selectQuickAccessoryByCusPId(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {

			reponse.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");

			String cusProductId = request.getParameter("fcusproductid");

			try {

				String sql = " SELECT fid,fname,fpath FROM t_ord_productdemandfile";
				
				if (cusProductId != null) {
					
					sql += " where fparentid = '%s' ";
					sql = String.format(sql, cusProductId);
				} else {
					sql += " where 1<>1 ";
					
				}
				sql+=" order by fcreatetime desc ";
				ListResult result = QuickOrderDao.QueryFilterList(sql, request);
				for (HashMap<String, Object> record : result.getData()) {
					String pathT =getFilePathByPath((String)record.get("fpath"));
					record.put("fpath", pathT);
				}
				
				reponse.getWriter().write(JsonUtil.result(true, "", result));
			} catch (DJException e) {
				// TODO Auto-generated catch block
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		

	    @RequestMapping("/getQuickUserToCustAddress")
	    public String getQuickUserToCustAddress(HttpServletRequest request,HttpServletResponse response) throws IOException {
	      String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	      String sql="";
	      try {  
	        String fcustomerid = QuickOrderDao.getCurrentCustomerids(userid);
	        SysUser user  = (SysUser)baseSysDao.Query(SysUser.class, userid);
	        //只查看自己地址与客户无关
	        if(user.getFisreadonly()==1)
	        {
	          sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone,ad.fdetailaddress FROM t_bd_address ad JOIN t_bd_useraddress cd  ON ad.`FID` = cd.`faddress` where cd.`fuserid` = '"+userid+"'";
	          List list= QuickOrderDao.QueryBySql(sql);
	          response.getWriter().write(JsonUtil.result(true, "", ""+list.size(),list));
	          
	        }else
	        {
	          sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone,ad.fdetailaddress FROM t_bd_address ad JOIN t_bd_CustRelationAdress cd ON ad.`FID` = cd.`faddressid` where   cd.feffect=1 and cd.`fcustomerid` in ("+fcustomerid+") ";
	          ListResult result = QuickOrderDao.QueryFilterList(sql, request);
	          response.getWriter().write(JsonUtil.result(true, "", result));
	        }
	      } catch (DJException e) {
	        response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
	      }
	      return null;
	    }
		
		
	    @RequestMapping("/getQuickUserDefaultAddress")
	    public String getQuickUserDefaultAddress(HttpServletRequest request,HttpServletResponse response) throws IOException {
	      String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	      try {
	        String sql ="";
	        //快速下单帐号可以关联多个客户
	        //baseSysDao.getCurrentCustomerid(userid);
	        SysUser user  = (SysUser)baseSysDao.Query(SysUser.class, userid);
	        String fcustomerid = request.getParameter("fcustomerid");
	        //只查看自己地址与客户无关
	        if(user.getFisreadonly()==1){ 
	        	sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID` where ud.`fuserid` = '"+userid+"' and ud.`fdefault` = 1";
	        	List list= QuickOrderDao.QueryBySql(sql);
	        	response.getWriter().write(JsonUtil.result(true, "", ""+list.size(),list));
	        }
	        else{
	        	//2015-06-10 快速下单的帐号也能设置默地址，加个控制
	        	sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID`  where ud.`fuserid` = '"+userid+"' and ud.`fdefault` = 1";
	        	List list= QuickOrderDao.QueryBySql(sql);
	            if(list.size()>0){
	            	response.getWriter().write(JsonUtil.result(true, "", ""+list.size(),list));
	            	return null;
	            }
	           //2015年6月9日  快速下单模式，用户关联多个客户，默认地址需要根据客户去筛选
	           sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID`  ";
	           sql  += " JOIN t_bd_custrelationadress cd ON cd.faddressid = ud.faddress where  ud.fdefault = 1 and cd.fcustomerid = '"+fcustomerid+"'";
		        ListResult result = QuickOrderDao.QueryFilterList(sql, request);
		        response.getWriter().write(JsonUtil.result(true, "", result));  
	        }
	      } catch (DJException e) {
	        response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
	      }
	      return null;
	    }
		
		
		
		/**
		 *  纸箱库存-查询数据
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value = "/queryBoxStoreBalances")
		public String queryBoxStoreBalances(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String sql,ftypesql="",fproductid="";
			HashMap<String, Object> balancemap=null;
			try {
				request.setAttribute("djsort", "c.fname desc");
			sql="SELECT c.fid,c.fname,r.fname cname,c.fproductid FROM t_bd_custproduct c INNER JOIN  t_pdt_productdef f ON f.fid=c.fproductid LEFT JOIN t_bd_customer r ON r.fid=c.fcustomerid where 1=1 "+QuickOrderDao.QueryFilterByUser(request, "c.fcustomerid", null);
			ListResult result =QuickOrderDao.QueryFilterList(sql, request);
			//对应的客户产品的图片赋值
			for (HashMap<String, Object> record : result.getData()) 
			{
				fproductid=(String)record.get("fproductid");
				sql="select sum(amount) amount ,sum(fusedqty) fusedqty,sum(fmakingqty) fmakingqty,sum(amount+fmakingqty) fbalanceqty from ("
				+" select sum(fbalanceqty) amount,0 fusedqty,0 fmakingqty from  t_inv_storebalance  where  fproductid = '%s' "
				+" union select 0 amount,sum(fusedqty) fusedqty,0 fmakingqty from t_inv_usedstorebalance where fusedqty > 0 and fproductid = '%s' "
				+" union select 0 amount,0 fusedqty, sum(case when famount < ifnull(fstockinqty, 0) then 0 else famount - ifnull(fstockinqty, 0) end) fmakingqty "
				+" from t_ord_productplan  where fcloseed = 0 and faudited = 1 and fboxtype = 0 and fproductdefid = '%s' ) s";
				sql=String.format(sql, fproductid,fproductid,fproductid);
				balancemap = (HashMap<String, Object>)QuickOrderDao.QueryBySql(sql).get(0);
				record.put("amount", balancemap.get("amount"));
				record.put("fusedqty",balancemap.get("fusedqty"));
				record.put("fmakingqty", balancemap.get("fmakingqty"));
				record.put("fbalanceqty",balancemap.get("fbalanceqty"));
			} 
			reponse.getWriter().write(JsonUtil.result(true, "",result));
				
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		
		
		
		
		@RequestMapping("saveMystockOrDeliverapply")
		public void saveMystockOrDeliverapply(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Mystock mystock = (Mystock)request.getAttribute("Mystock");
			Deliverapply deliverapply = (Deliverapply)request.getAttribute("Deliverapply");
			String fsupplierid=request.getParameter("fsupplierids");
			String fcustproductid=request.getParameter("fcustproductid");
			try {
				
				String fcustomerid=	GetIsSingleSupplier(userid,fsupplierid);
				Custproduct cinfo=(Custproduct)QuickOrderDao.Query(Custproduct.class,fcustproductid);
				if(cinfo==null)
				{
					throw new DJException("客户产品有误");
				}
				if(mystock!=null){
					mystock.setFid(QuickOrderDao.CreateUUid());
					mystock.setFnumber(ServerContext.getNumberHelper().getNumber("mystock", "B", 4, false));
					mystock.setFcreatetime(new Date());
					mystock.setFcreateid(userid);
					mystock.setFcustomerid(fcustomerid);
					mystock.setFcustproductid(fcustproductid);
					mystock.setFstate(0);
					mystock.setFisconsumed(0);
					mystock.setFunit(StringUtils.isEmpty(cinfo.getFunit())?"只(套)/片":cinfo.getFunit());
				}
				
				if(deliverapply!=null)
				{
					deliverapply.setFid(QuickOrderDao.CreateUUid());
					deliverapply.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
					deliverapply.setFcreatorid(userid);
					deliverapply.setFcreatetime(new Date());
					deliverapply.setFiscreate(0);
//					deliverapply.setFordernumber("");
					deliverapply.setFupdatetime(new Date());
					deliverapply.setFupdateuserid(userid);
					deliverapply.setFcustomerid(fcustomerid);
					deliverapply.setFcusproductid(fcustproductid);
					if(StringUtils.isEmpty(deliverapply.getFsupplierid())){
						throw new DJException("制造商不能为空");
					}
//					Supplier sinfo=(Supplier)QuickOrderDao.Query(Supplier.class,deliverapply.getFsupplierid());
//					if(sinfo.getFisManageStock()!=1){
//						Custproduct cpinfo=(Custproduct)QuickOrderDao.Query(Custproduct.class,deliverapply.getFcusproductid());
//						deliverapply.setFmaterialfid(cpinfo.getFproductid());
//					}
					////根据分配规则里的是否快速下单确定是否走快速下单
					List<Productreqallocationrules> rule=QuickOrderDao.QueryByHql(String.format(" from Productreqallocationrules where fsupplierid='%s' and fcustomerid='%s'",deliverapply.getFsupplierid(),deliverapply.getFcustomerid()));
					if(rule.size()==1&&rule.get(0).getFisstock()==1)
					{
							Custproduct cpinfo=(Custproduct)QuickOrderDao.Query(Custproduct.class,deliverapply.getFcusproductid());
							deliverapply.setFmaterialfid(cpinfo.getFproductid());
					}
					Address addinfo=(Address)QuickOrderDao.Query(Address.class,deliverapply.getFaddressid());
					if(addinfo==null)throw new DJException("地址不正确！");
					deliverapply.setFaddress(addinfo.getFdetailaddress());
					deliverapply.setFlinkman(addinfo.getFlinkman());
					deliverapply.setFlinkphone(addinfo.getFphone());
					String timeFrame= StringUtils.isEmpty(request.getParameter("fatime"))?"09":(request.getParameter("fatime").length()==1?"0":"")+request.getParameter("fatime");
					deliverapply.getFarrivetime().setHours(new Integer(timeFrame));					
					if(!DataUtil.dateFormatCheck(f.format(deliverapply.getFarrivetime()), true)){
						throw new DJException("送达日期格式错误或小于当前时间！");
					}
//					if(!DataUtil.dateFormatCheck(f.format(deliverapply.getFarrivetime()), true,2)){
//							throw new DJException("送达日期格式大于当前时间+2天！");
//					}
					if(!DataUtil.positiveIntegerCheck(deliverapply.getFamount().toString())){
						throw new DJException("要货数量必须大于0！");
					}
				}
				QuickOrderDao.ExecSaveMystockOrDeliverapply(deliverapply,mystock);
				response.getWriter().write(JsonUtil.result(true, "保存成功","",""));
			} catch (Exception e) {
//				 TODO: handle exception
				response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
			}
		}
		
		
//		@RequestMapping("/getIs")
//		public String getQuickUserDefaultAddress(HttpServletRequest request,HttpServletResponse response) throws IOException {
//			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
//			try {
//				baseSysDao.getCurrentCustomerid(userid);
//				//正常情况下 应该用户地址表中只有唯一地址,也就是默认地址
//				String sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID` where ud.`fuserid` = '"+userid+"'";
//				ListResult result = QuickOrderDao.QueryFilterList(sql, request);
//				response.getWriter().write(JsonUtil.result(true, "", result));	
//			} catch (DJException e) {
//				response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
//			}
//			return null;
//		}
		
		
		//批量新增要货申请;制造商统一
		@RequestMapping(value="/SaveQuickbatchDeliverapply")
		public  String SaveQuickbatchDeliverapply(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			try {
				String userid = ((Useronline) request.getSession().getAttribute(
						"Useronline")).getFuserid();
				List<Deliverapply> applist= new ArrayList<Deliverapply>();
//				DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				List<Deliverapply> list = (ArrayList<Deliverapply>)request.getAttribute("Deliverapply");
				List<Mystock> list2 = (List<Mystock>) request.getAttribute("Mystock");
				if((list==null||list.size()==0) && (list2==null||list2.size()==0)){
					throw new DJException("请至少填写一条记录再保存！");
				}
				Deliverapply deliverapply;
				Address addinfo;
				Date now = new Date();
				for(int i=0;i<list.size();i++){
					deliverapply = list.get(i);
//					if(deliverapply.getFboxmodel()<2){//fboxmodel= 0 
						if("0".equals(deliverapply.getFtype())){//ftype=0 要货1备货
							if(StringUtils.isEmpty(deliverapply.getFsupplierid())){
								throw new DJException("制造商不能为空");
							}
	//						String customerid=GetIsSingleSupplier(userid,deliverapply.getFsupplierid());//判断是否是单一供应商
	//						deliverapply.setFcustomerid(customerid);
							if(!DataUtil.positiveIntegerCheck(deliverapply.getFamount().toString())){
								throw new DJException("配送数量不能小于1！");
							}
							if(StringUtils.isEmpty(deliverapply.getFcusproductid())){
								throw new DJException("包装物名称不能为空！");
							}
							if(StringUtils.isEmpty(deliverapply.getFaddressid())){
								throw new DJException("送货地址不能为空！");
							}
							if(StringUtils.isEmpty(deliverapply.getFcustomerid())){
								throw new DJException("客户不能为空！");
							}
							if(deliverapply.getFarrivetime()==null){
								throw new DJException("交期日期不能为空");
							}
							//有做出入库管理按原先逻辑走
	//						Supplier sinfo=(Supplier)QuickOrderDao.Query(Supplier.class,deliverapply.getFsupplierid());
	//						if(sinfo.getFisManageStock()!=1){
	//						pinfo=(Custproduct)QuickOrderDao.Query(Custproduct.class,deliverapply.getFcusproductid());
	//						deliverapply.setFmaterialfid(pinfo.getFproductid());
	//						}
							//根据分配规则里的是否快速下单确定是否走快速下单
							List<Productreqallocationrules> rule=QuickOrderDao.QueryByHql(String.format(" from Productreqallocationrules where fsupplierid='%s' and fcustomerid='%s'",deliverapply.getFsupplierid(),deliverapply.getFcustomerid()));
							if(rule.size()==1&&rule.get(0).getFisstock()==1)
							{
									Custproduct cpinfo=(Custproduct)QuickOrderDao.Query(Custproduct.class,deliverapply.getFcusproductid());
									deliverapply.setFmaterialfid(cpinfo.getFproductid());
							}
							addinfo=(Address)QuickOrderDao.Query(Address.class,deliverapply.getFaddressid());
							if(addinfo==null)throw new DJException("地址不正确！");
							deliverapply.setFaddress(addinfo.getFdetailaddress());
							deliverapply.setFlinkman(addinfo.getFlinkman());
							deliverapply.setFlinkphone(addinfo.getFphone());
							if(StringUtils.isEmpty(deliverapply.getFseries())){
								throw new DJException("交期时间不能为空");
							}
							int ptime=("true".equals(deliverapply.getFseries())?9:14);
							deliverapply.getFarrivetime().setHours(ptime);
	//						if(!DataUtil.dateFormatCheck(f.format(deliverapply.getFarrivetime()), true,0)){
	//							throw new DJException("送达日期格式错误或小于当前时间！");
	//						}
							if(!DataUtil.dateFormatCheck(f.format(deliverapply.getFarrivetime()), true,2)){
								throw new DJException("送达日期格式大于当前时间+2天！");
							}
	
							deliverapply.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
							deliverapply.setFcreatorid(userid);
							deliverapply.setFcreatetime(now);
							deliverapply.setFiscreate(0);
							deliverapply.setFupdatetime(now);
							deliverapply.setFupdateuserid(userid);
							applist.add(deliverapply);
						}
//				}else
//				{
//					throw new DJException("暂时不支持导入功能");
//				}
				}
				
				for(Mystock mystock: list2){
					if(mystock.getFconsumetime() == null){
						throw new DJException("请填写备货的备货周期！");
					}
					if(mystock.getFfinishtime() == null){
						throw new DJException("请填写备货的首次发货时间！");
					}
					if(mystock.getFplanamount() == 0){
						throw new DJException("请填写备货的计划数量！");
					}
					mystock.setFnumber(ServerContext.getNumberHelper().getNumber("mystock", "B", 4, false));
					mystock.setFcreatetime(now);
					mystock.setFcreateid(userid);
					mystock.setFstate(0);
					mystock.setFunit(baseSysDao.getStringValue("t_bd_custproduct", mystock.getFcustproductid(), "funit"));
				}
				
				QuickOrderDao.ExecSaveDeliverapplyOrCusdelivers(applist,list2,null,userid);

				reponse.getWriter().write(JsonUtil.result(true, "保存成功！", "", ""));
			} catch (Exception e) {
				reponse.getWriter().write(
						JsonUtil.result(false, "保存失败，" + e.getMessage(), "", ""));
			}

			return null;
		}
		
		/**
		 * 保存暂存订单
		 * @param request fid 多个用，隔开
		 * @param response
		 */
		@RequestMapping("/SaveCusPrivateDelivers")
		public String SaveCusPrivateDelivers(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String result = "";
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fcustproductid = request.getParameter("fid");
			String fsupplierid = request.getParameter("suplierID");
			int ftype= request.getParameter("ftype")==null?0:new Integer(request.getParameter("ftype"));
			int fordertype=request.getParameter("fordertype")==null?0:new Integer(request.getParameter("fordertype"));
			try {
				if(cusPrivateDeliversDao.QueryExistsBySql("select fid from t_ord_cusprivatedelivers where  fsupplierid='"+fsupplierid+"'and fcusproduct='"+fcustproductid+"' and fcreatorid='"+userid+"' and ftype="+ftype ))
				{
					throw new DJException("产品已在购物车中！");
				}
				CusPrivateDelivers cinfo=new CusPrivateDelivers();
				cinfo.setFid(cusPrivateDeliversDao.CreateUUid());
				cinfo.setFsupplierid(fsupplierid);
				cinfo.setFcustomerid(GetIsSingleSupplier(userid,fsupplierid));
				cinfo.setFcreatetime(new Date());
				cinfo.setFcreatorid(userid);
				cinfo.setFtype(ftype);//0为要货,1备货
				cinfo.setFcusproduct(fcustproductid);
				cinfo.setFordertype(fordertype);//是否实时计算库存 0 为否 1 为是 ，暂存才存
				cinfo.setFmaktx("");
				cinfo.setFamount(0);
				cinfo.setFreqaddress("");
				cusPrivateDeliversDao.saveOrUpdate(cinfo);
				result = JsonUtil.result(true,"保存成功", "", "");
				reponse.setCharacterEncoding("utf-8");
			} catch (Exception e) {
				result = JsonUtil.result(false,e.getMessage(), "", "");
			}
			reponse.getWriter().write(result);

			return null;

		}	
		
		
		@RequestMapping("/GetCusprivatedeliversByuser")
		public String GetCusprivatedeliversByuser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		SupplierDeliverTime supplierDeliverTime = null;
		String fsupplierid;
		Calendar calendar;
		ArrayList<SupplierDeliverTime> supplierDeliverTimeList;
		HashMap<String, SupplierDeliverTime> deliverTimeMap = new HashMap<>();
		try {
			String sql = "SELECT c.fid,c.fnumber fpcmordernumber,c.faveragefamount,c.farrivetime ffinishtime,c.freqdate fconsumetime,c.fnumber fordernumber,c.fcustomerid  fcustomerid,c.fsupplierid fsupplierid, s.fname fsuppliername ,c.fcusproduct fcustproductid,c.fcusproduct fcusproductid ,c.fordertype fboxmodel,c.ftype,p.fname cutpdtname ,p.fspec fspec,c.famount famount,c.famount fplanamount,ifnull(c.faddress,'') faddressid,ifnull(ad.fname,'') faddress ,c.fdescription,DATE(c.farrivetime) farrivetime,   IF(DATE_FORMAT(c.farrivetime,'%p')='AM','true','false')  fseries FROM t_ord_cusprivatedelivers c LEFT JOIN t_sys_supplier s ON  c.fsupplierid=s.fid LEFT JOIN t_bd_custproduct p ON p.fid=c.fcusproduct left join t_bd_address ad on ad.fid=c.faddress "
			+ " where c.fcreatorid ='" + userid + "'";
			ListResult result = QuickOrderDao.QueryFilterList(sql, request);

			List<HashMap<String, Object>> data = result.getData();

			// 添加暂存默认地址
			for (HashMap<String, Object> hashMap : data) {
				fsupplierid = (String) hashMap.get("fsupplierid");
				if(!deliverTimeMap.containsKey(fsupplierid)){
					supplierDeliverTimeList = (ArrayList<SupplierDeliverTime>) QuickOrderDao.QueryByHql("from SupplierDeliverTime where fsupplierid = '"+fsupplierid+"'");
					if(supplierDeliverTimeList.size()>0){
						supplierDeliverTime = supplierDeliverTimeList.get(0);
					}else{
						supplierDeliverTime = new SupplierDeliverTime();
					}
					deliverTimeMap.put(fsupplierid, supplierDeliverTime);
				}else{
					supplierDeliverTime = deliverTimeMap.get(fsupplierid);
				}
				hashMap.put("fdays", supplierDeliverTime.getFdays());
				if(hashMap.get("farrivetime")==null){
					calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, supplierDeliverTime.getFdefaultdays());
					hashMap.put("farrivetime", calendar.getTime());
				}
				if("1".equals(hashMap.get("ftype")+"") || !StringUtils.isEmpty((String)hashMap.get("faddressid"))){	// 备货不返回地址
					continue;
				}

				String sql1 = "";
				// 快速下单帐号可以关联多个客户

				SysUser user = (SysUser) baseSysDao
						.Query(SysUser.class, userid);
				String fcustomerid = (String) hashMap.get("fcustomerid");
				
				// 只查看自己地址与客户无关
				if (user.getFisreadonly() == 1) {
					sql1 = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID` where ud.`fuserid` = '"
							+ userid + "' and ud.`fdefault` = 1";
					List list = QuickOrderDao.QueryBySql(sql1);
					
					if (list.size() > 0) {
						
						hashMap.put("faddress", ((HashMap<String, Object>) list
								.get(0)).get("fname"));
						hashMap.put("faddressid",
								((HashMap<String, Object>) list.get(0))
								.get("fid"));
						
						continue;
					}
				} else {
					// 2015-06-10 快速下单的帐号也能设置默地址，加个控制
					sql1 = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID`  where ud.`fuserid` = '"
							+ userid + "' and ud.`fdefault` = 1";
					List list = QuickOrderDao.QueryBySql(sql1);
					if (list.size() > 0) {
						
						hashMap.put("faddress", ((HashMap<String, Object>) list
								.get(0)).get("fname"));
						hashMap.put("faddressid",
								((HashMap<String, Object>) list.get(0))
								.get("fid"));
						
						continue;
					}
					// 2015年6月9日 快速下单模式，用户关联多个客户，默认地址需要根据客户去筛选
					sql1 = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID`  ";
					sql1 += " JOIN t_bd_custrelationadress cd ON cd.faddressid = ud.faddress where  ud.fdefault = 1 and cd.fcustomerid = '"
							+ fcustomerid + "'";
					ListResult result1 = QuickOrderDao.QueryFilterList(sql1,
							request);
					if (result1.getData().size() > 0) {
						
						hashMap.put("faddress",
								((HashMap<String, Object>) result1.getData()
										.get(0)).get("fname"));
						hashMap.put("faddressid",
								((HashMap<String, Object>) result1.getData()
										.get(0)).get("fid"));
						
					}
					
					continue;
				}
				

			}
			addStockForCusprivatedelivers(data);	// 添加库存 

			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
		
		private void addStockForCusprivatedelivers(List<HashMap<String, Object>> data) {
			String fsupplierid,fcusproductid;
			for(HashMap<String, Object> map: data){
				if("1".equals(map.get("ftype")+"")){	// 备货不用算库存
					map.put("fstock", "-");
					continue;
				}
				if("0".equals(map.get("fboxmodel")+"")){
					map.put("fstock", "-");
				}else{
					fsupplierid = (String) map.get("fsupplierid");
					fcusproductid = (String) map.get("fcusproductid");
					map.put("fstock", getCustProductStock(fsupplierid,fcusproductid));
				}
			}
		}



		/**
		 * 计算客户产品的库存
		 * @param fsupplierid
		 * @param fcusproductid
		 * @return
		 */
		public String getCustProductStock(String fsupplierid,
				String fcusproductid) {
			String fproductid = baseSysDao.getStringValue("t_bd_custproduct", fcusproductid,"fproductid");
			String sql = "SELECT SUM(fbalanceqty) amount FROM t_inv_storebalance WHERE fproductid = '"+fproductid+"' AND fsupplierid = '"+fsupplierid+"'"; 
			String amount = baseSysDao.getStringValue(sql, "amount");
			if(amount==null || "0".equals(amount)){
				return "0";
			}
			sql = "SELECT SUM(fusedqty) fusedqty FROM t_inv_usedstorebalance LEFT JOIN t_ord_deliverorder o ON o.fid = t_inv_usedstorebalance.fdeliverorderid WHERE fusedqty > 0 AND t_inv_usedstorebalance.fproductid = '"+fproductid+"' AND fsupplierid = '"+fsupplierid+"'"; 
			String usedqty = baseSysDao.getStringValue(sql, "fusedqty");
			if(usedqty==null){
				return amount;
			}else{
				return String.valueOf(Integer.valueOf(amount) - Integer.valueOf(usedqty));
			}
		}




		/**
		 * 保存暂存订单
		 * @param request fid 多个用，隔开
		 * @param response
		 */
		@RequestMapping("/deleteCusPrivateDelivers")
		public String DeleteCusPrivateDelivers(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String result = "";
			String fid = request.getParameter("fid");
			String condition = baseSysDao.getCondition(fid);
			try {
				QuickOrderDao.ExecBySql("delete from t_ord_cusprivatedelivers where fid"+condition);
				result = JsonUtil.result(true,"删除成功", "", "");
			} catch (Exception e) {
				result = JsonUtil.result(false,e.getMessage(), "", "");
			}
			reponse.getWriter().write(result);

			return null;

		}	
		
		/**
		 * 禁用产品（快速下单产品的删除）
		 * @param request fid 多个用，隔开
		 * @param response
		 */
		@RequestMapping("/forbiddenCusproduct")
		public String forbiddenCusproduct(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String result = "";
			String fidcls = request.getParameter("fids");
			fidcls="('"+fidcls.replace(",","','")+"')";
			try {
//				if(QuickOrderDao.QueryExistsBySql("select fid from t_ord_cusprivatedelivers where fcusproduct in " +fidcls))			throw new DJException("该产品在暂存列表中，不能删除！");
				QuickOrderDao.ExecBySql("update t_bd_custproduct set feffect=0 where fid in "+fidcls);
				result = JsonUtil.result(true,"删除成功", "", "");
				reponse.setCharacterEncoding("utf-8");
			} catch (Exception e) {
				result = JsonUtil.result(false,e.getMessage(), "", "");
			}
			reponse.getWriter().write(result);

			return null;

		}	
		
		
		/**
		 * 保存客户产品
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/SaveQuickCusproduct")
		public  String SaveQuickCusproduct(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String result = "";
			Custproduct cinfo=null;
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			try{
				String fname=request.getParameter("fname");
				if(StringUtils.isEmpty(fname)){
					throw new DJException("产品名称不能为空！");
				}
				String fsupplierid=request.getParameter("fsupplierid");
				String customerid=GetIsSingleSupplier(userid,fsupplierid);//判断是否是单一供应商
				String fspec=request.getParameter("fspec")==null?"":request.getParameter("fspec").replace('*','X');
				String sql = "select fid from t_bd_custproduct where fcustomerid='"+customerid+"' and  REPLACE( fname,'*','X')='"+fname+"' and REPLACE( fspec,'*','X')='"+fspec+"' and  feffect=1" ;
				if(QuickOrderDao.QueryExistsBySql(sql)){
					throw new DJException("已存在相同规格相同名称的产品！");
				}
				Date now = new Date();
				cinfo=new Custproduct();
				cinfo.setFid(QuickOrderDao.CreateUUid());
				cinfo.setFcreatorid(userid);
				cinfo.setFcreatetime(now);
				cinfo.setFlastupdatetime(now);
				cinfo.setFlastupdateuserid(userid);
				cinfo.setFcustomerid(customerid);
				cinfo.setFspec(fspec);
				cinfo.setFname(fname);
				cinfo.setFtype(1);
				Productdef p = new Productdef();
				p.setFid(QuickOrderDao.CreateUUid());
				p.setFcreatorid(userid);
				p.setFcreatetime(now);
				p.setFlastupdatetime(now);
				p.setFcusproductname(cinfo.getFname());
				p.setFeffect(1);
				p.setFname(cinfo.getFname());
				p.setFcharacter(cinfo.getFspec());
				p.setFcustomerid(cinfo.getFcustomerid());
				p.setFsupplierid(fsupplierid);
				cinfo.setFproductid(p.getFid());
				QuickOrderDao.ExecSaveQuickCustproduct(cinfo, p);
				result = JsonUtil.result(true,"保存成功!", "","[{\"fid\":\""+cinfo.getFid()+"\"}]");
			}
			catch(Exception e)
			{
				result = JsonUtil.result(false,e.getMessage(), "", "");
			}
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(result);
			return null;

		}
		
		
		/**
		 * 要货申请的自动生成管理等。
		 * @param request 
		 * @param response
		 */
		@RequestMapping("/autocreatedelivers")
		public String autocreatedelivers(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String result = "";
		
			try {
				deliverapplyDao.ExecAutoCreateDeliverapply();
				result = JsonUtil.result(true,"生成成功", "", "");
				reponse.setCharacterEncoding("utf-8");
			} catch (Exception e) {
				result = JsonUtil.result(false,e.getMessage(), "", "");
			}
			reponse.getWriter().write(result);

			return null;

		}	
		
		
//		/**
//		 * 要货申请的取消自动生成管理。
//		 * @param request 
//		 * @param response
//		 */
//		@RequestMapping("/deletecreatedelivers")
//		public String deletecreatedelivers(HttpServletRequest request,
//				HttpServletResponse reponse) throws IOException {
//			String result = "";
//			String fidcls = request.getParameter("fids");
//			fidcls="('"+fidcls.replace(",","','")+"')";
//			try {
//				String sql="SELECT QUOTE(fid) fid FROM t_ord_deliverapply WHERE IFNULL(fmaterialfid,'')<>'' and  fiscreate=1 and fid in "+fidcls;
//				List<HashMap<String, Object>> custList=QuickOrderDao.QueryBySql(sql);
//				if(custList.size()==0)
//				{
//			        throw new DJException("没有可取消的记录!（通过快速下单且已生成管理）");
//				}
//				String cids="";//符合记录fid
//				StringBuilder custids=new StringBuilder();
//				for(HashMap<String, Object> m : custList){
//					custids.append(m.get("fid")+",");
//				}
//				cids = custids.toString();
//				cids="("+cids.substring(0, cids.length()-1)+")";
//				if(QuickOrderDao.QueryExistsBySql("select fid from t_ord_productplan where faffirmed=1 and fdeliverapplyid  in "+cids)){
//					throw new DJException("存在已接收订单，不能取消！"); 
//				}
//				deliverapplyDao.ExecDeleteCreateDeliverapply(cids);
//				result = JsonUtil.result(true,"取消成功", "", "");
//				reponse.setCharacterEncoding("utf-8");
//			} catch (Exception e) {
//				result = JsonUtil.result(false,e.getMessage(), "", "");
//			}
//			reponse.getWriter().write(result);
//
//			return null;
//
//		}	
		
		@RequestMapping("/selectDeliverapplyCustsMVPhone")
		public String selectDeliverapplyCustsMVPhone(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			
			ListResult result;
			try {
				String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
				String sql = "select _suppliername fsuppliername,_custpdtnumber,_orderunit forderunit,_creator fcreator,_lastupdater flastupdater,_custname,_custpdtname,_character fcharacter,_spec fspec,fwerkname,fid,date_format(fcreatetime,'%Y-%m-%d %H:%i') fcreatetime,fnumber,fcustomerid,fcusproductid,date_format(farrivetime,'%Y-%m-%d %H:%i') farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fsaleorderid,fordernumber,forderentryid,ifnull(fiscreate,0) fiscreate,fstate,ftype,ftraitid,foutQty from t_ord_deliverapply_card_mv d where 1=1 ";
				if(deliverapplyDao.QueryExistsBySql("select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'")){
					sql += " and fcusproductid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')";
				}
				sql += deliverapplyDao.QueryFilterByUserofuser(request,"fcreatorid","and")+ deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
				request.setAttribute("djsort","fcreatetime desc");
				result = deliverapplyDao.QueryFilterList(sql, request);
				//对应的客户产品的图片赋值
				for (HashMap<String, Object> record : result.getData()) 
				{
					String pathT = getQuickFilePath(request,(String)record.get("fcusproductid"));
//					if(pathT!=null&&!"".equals(pathT))
						record.put("proImgUrl", pathT==null?"":pathT);
					
				}
				deliverapplyDao.addStateInfo(result.getData());
				response.getWriter().write(JsonUtil.result(true,"",result));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false,e.getMessage(),"","")); 
			}
		
			return null;
		}
		
	
		
	    /**
	     * 根据用户对应的制造商，查找相应的客户
	     * @param request
	     * @param response
	     */
	    @RequestMapping("/GetStanderCustomersOfSupplier")
	    public String GetStanderCustomersOfSupplier(HttpServletRequest request,
		        HttpServletResponse reponse) throws IOException {
		      String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		      List result;
		      reponse.setCharacterEncoding("utf-8");
		      try {
		        String fsupplierid=QuickOrderDao.getCurrentSupplierid(userid);
		        String sql="SELECT fcustomerid fid ,s.fname fname FROM t_pdt_productreqallocationrules r LEFT JOIN t_bd_customer s ON s.fid=r.fcustomerid where    r.fsupplierid ="+fsupplierid+" ORDER BY CONVERT(fname USING gbk)";
		        result = QuickOrderDao.QueryBySql(sql);
		        reponse.getWriter().write(JsonUtil.result(true,"","",result));
		      } catch (DJException e) {
		        reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		      }
		    
		      return null;
		    }
	    
	    /**
	     * 根据用户对应的制造商，查找相应的客户
	     * @param request
	     * @param response
	     */
	    @RequestMapping("/GetCustomersOfSupplier")
	    public String GetCustomersOfSupplier(HttpServletRequest request,
		        HttpServletResponse reponse) throws IOException {
		      String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		      ListResult result;
		      reponse.setCharacterEncoding("utf-8");
		      try {
		        String fsupplierid=QuickOrderDao.getCurrentSupplierid(userid);
		        String sql="SELECT fcustomerid fid ,s.fname fname FROM t_pdt_productreqallocationrules r LEFT JOIN t_bd_customer s ON s.fid=r.fcustomerid where    r.fsupplierid ="+fsupplierid;
		        result = QuickOrderDao.QueryFilterList(sql, request);
		        reponse.getWriter().write(JsonUtil.result(true,"",result));
		      } catch (DJException e) {
		        reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		      }
		    
		      return null;
		    }
		
	    
	  @RequestMapping("/GetSupOfPdtInfo")
		public String GetSupOfPdtInfo(HttpServletRequest request,
					HttpServletResponse reponse) throws IOException {
				String result = "";
				String sql = "SELECT cp.fid,cp.fname,cp.fspec,cp.fdescription,cp.fcustomerid  FROM t_bd_custproduct cp " +
						" where cp.fid = '"
						+ request.getParameter("fid") + "'";
				List<HashMap<String, Object>> sList = QuickOrderDao.QueryBySql(sql);
				reponse.setCharacterEncoding("utf-8");
				// reponse.getWriter().write(JsonUtil.result(true,"",SupplierDao.QueryCountBySql(sql),sList));
				reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
				return null;
		
			}
		
	     /**
	     * 保存客户产品
	     * @param request
	     * @param reponse
	     * @return
	     * @throws IOException
	     */
	    @RequestMapping(value="/SaveSupOfCusproduct")
	    public  String SaveSupOfCusproduct(HttpServletRequest request,
			        HttpServletResponse reponse) throws IOException {
			    String result = "";
			    Custproduct cinfo=null;
			    String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			    try{
			    String fname=request.getParameter("fname");
			    if(StringUtils.isEmpty(fname)){
			      throw new DJException("产品名称不能为空！");
			    }
			    String fcustomerid=request.getParameter("fcustomerid");
			    String fsupplierid=QuickOrderDao.getCurrentSupplierid(userid);
			    //去掉头尾单引号
			    fsupplierid = fsupplierid.replace("'", "");
			    String fspec=request.getParameter("fspec")==null?"":request.getParameter("fspec").replace('*','X');
			    String fdescription = request.getParameter("fdescription")==null?"":request.getParameter("fdescription");
			    if(request.getParameter("fid") != null && !"".equals(request.getParameter("fid"))){
			      cinfo = (Custproduct)QuickOrderDao.Query(Custproduct.class, request.getParameter("fid"));
			      cinfo.setFlastupdatetime(new Date());
			      cinfo.setFlastupdateuserid(userid);
			      cinfo.setFspec(fspec);
			      cinfo.setFname(fname);
			      cinfo.setFtype(1);
			      cinfo.setFdescription(fdescription);
			      Productdef p =(Productdef) QuickOrderDao.Query(Productdef.class, cinfo.getFproductid());
			      p.setFcusproductname(cinfo.getFname());
			      p.setFeffect(1);
			      p.setFname(cinfo.getFname());
			      p.setFcharacter(cinfo.getFspec());
			      p.setFdescription(fdescription);
			      QuickOrderDao.ExecSaveQuickCustproduct(cinfo, p);
			    }
			    else{
			      String sql = "select fid from t_bd_custproduct where fcustomerid='"+fcustomerid+"' and  REPLACE( fname,'*','X')='"+fname+"' and REPLACE( fspec,'*','X')='"+fspec+"' and  feffect=1" ;
			      if(QuickOrderDao.QueryExistsBySql(sql)){
			        throw new DJException("已存在相同规格相同名称的产品！");
			      }
			      cinfo=new Custproduct();
			      cinfo.setFid(QuickOrderDao.CreateUUid());
			      cinfo.setFcreatorid(userid);
			      cinfo.setFcreatetime(new Date());
			      cinfo.setFlastupdatetime(new Date());
			      cinfo.setFlastupdateuserid(userid);
			      cinfo.setFcustomerid(fcustomerid);
			      cinfo.setFspec(fspec);
			      cinfo.setFname(fname);
			      cinfo.setFtype(1);
			      cinfo.setFdescription(fdescription);
			      Productdef p = new Productdef();
			      p.setFid(QuickOrderDao.CreateUUid());
			      p.setFcreatorid(userid);
			      p.setFcreatetime(new Date());
			      p.setFcusproductname(cinfo.getFname());
			      p.setFeffect(1);
			      p.setFname(cinfo.getFname());
			      p.setFcharacter(cinfo.getFspec());
			      p.setFcustomerid(cinfo.getFcustomerid());
			      p.setFsupplierid(fsupplierid);
			      p.setFdescription(fdescription);
			      cinfo.setFproductid(p.getFid());
			      QuickOrderDao.ExecSaveQuickCustproduct(cinfo, p);
			    }    
			    result = JsonUtil.result(true,"保存成功!", "", "");
			    }
			    catch(Exception e)
			    {
			      result = JsonUtil.result(false,e.getMessage(), "", "");
			    }
			    reponse.setCharacterEncoding("utf-8");
			    reponse.getWriter().write(result);
			      return null;
		
					}
	    

	  //批量新增要货申请;制造商统一
	  		@RequestMapping(value="/SaveCusPrivateDeliversForHolder")
	  		public  String SaveCusPrivateDeliversForHolder(HttpServletRequest request,
	  				HttpServletResponse reponse) throws IOException {
	  			try {
	  				List<CusPrivateDelivers> clist= new ArrayList<CusPrivateDelivers>();
	  				List<Deliverapply> list = (ArrayList<Deliverapply>)request.getAttribute("Deliverapply");
	  				List<Mystock> stockList = (List<Mystock>) request.getAttribute("Mystock");
	  				if(list.size()==0 && stockList.size()==0){
		  				reponse.getWriter().write(JsonUtil.result(true, "", "0", ""));
		  				return null;
	  				}
	  				Deliverapply deliverapply;
	  				CusPrivateDelivers cdelivers=null;
	  				for(int i=0;i<list.size();i++){
	  					deliverapply = list.get(i);
	  					cdelivers=(CusPrivateDelivers)QuickOrderDao.Query(CusPrivateDelivers.class,deliverapply.getFid());
	  					if(!StringUtils.isEmpty(deliverapply.getFordernumber())){
	  					cdelivers.setFnumber(deliverapply.getFordernumber());//采购订单号
	  					}
	  					if(deliverapply.getFamount()!=null){
	  						cdelivers.setFamount(deliverapply.getFamount());//数量
	  					}
	  					if(deliverapply.getFarrivetime()!=null){
		  				deliverapply.getFarrivetime().setHours("true".equals(deliverapply.getFseries())?9:14);
		  				cdelivers.setFarrivetime(deliverapply.getFarrivetime());//配送时间
		  				}
	  					if(!StringUtils.isEmpty(deliverapply.getFaddressid())){
		  					cdelivers.setFaddress(deliverapply.getFaddressid());//地址
		  				}
	  					if(!StringUtils.isEmpty(deliverapply.getFdescription())){
		  					cdelivers.setFdescription(deliverapply.getFdescription());//备注
		  				}
	  					clist.add(cdelivers);
	  				}
	  				for(Mystock stock: stockList){
	  					cdelivers = (CusPrivateDelivers)QuickOrderDao.Query(CusPrivateDelivers.class,stock.getFid());
	  					cdelivers.setFamount(stock.getFplanamount());
	  					cdelivers.setFaveragefamount(stock.getFaveragefamount());
	  					cdelivers.setFarrivetime(stock.getFfinishtime());
	  					cdelivers.setFreqdate(stock.getFconsumetime());
	  					clist.add(cdelivers);
	  				}
	  				QuickOrderDao.ExecSaveCusPrivateDeliversForHolder(clist);

	  				reponse.getWriter().write(JsonUtil.result(true, "保存成功！", "", ""));
	  			} catch (Exception e) {
	  				reponse.getWriter().write(
	  						JsonUtil.result(false, "保存失败，" + e.getMessage(), "", ""));
	  			}

	  			return null;
	  		}
	  		
	  		//2015-06-15 客户订单信息
	  		@RequestMapping("/gainCustDelApplylist")
	  		public String gainCustDelApplylist(HttpServletRequest request,
	  				HttpServletResponse response) throws IOException {
	  			
	  			ListResult result;
	  			try {
	  				String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	  				String sql = "select mv.fid fid,mv.fcusproductid,mv._custpdtname fpdtname,mv._spec fpdtspec,mv.famount famount,mv.foutqty,mv.farrivetime,mv.faddress, ";
	  				sql += "mv._suppliername fsupplier,mv.fordernumber,mv.fnumber,date_format(mv.fcreatetime,'%Y-%m-%d %H:%i') fcreatetime,mv.fstate,mv.fdescription from  t_ord_deliverapply_card_mv mv  where 1 = 1";		
	  				if(deliverapplyDao.QueryExistsBySql("select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'")){
	  					sql += " and mv.fcusproductid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')";
	  				}
	  				sql += deliverapplyDao.QueryFilterByUserofuser(request,"fcreatorid","and")+ deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
	  				request.setAttribute("djsort","mv.fcreatetime desc");
	  				result = deliverapplyDao.QueryFilterList(sql, request);
	  				//deliverapplyDao.addStateInfo(result.getData());
	  				
	  				
					for (HashMap<String, Object> record : result.getData()) 
					{
						String stateShowerF  = this.getApplyStateByID(record.get("fid").toString());
							record.put("stateShowerF", stateShowerF);
						
					}
	  				response.getWriter().write(JsonUtil.result(true,"",result));
	  				
	  				
	  			} catch (DJException e) {
	  				response.getWriter().write(JsonUtil.result(false,e.getMessage(),"","")); 
	  			}
	  		
	  			return null;
	  		}
	  		
		  	//2015-06-15 要货ID构造 json用户界面展示 状态信息
	  		public String getApplyStateByID(String fid) throws IOException {
	  			try {
	  				String sql = "select date_format(d.fcreatetime,'%Y-%m-%d %H:%i') createtime,date_format(d.farrivetime,'%Y-%m-%d %H:%i') farrivetime,o.fstate,date_format(o.fcreatetime,'%Y-%m-%d %H:%i') statetime FROM t_ord_deliverapply d ";
	  				sql += " left join t_ord_orderstate o ON d.fid = o.fdeliverapplyid where  d.fid = '"+fid+"' order by o.fstate ";
	  				List mylist = new ArrayList();
	  				List<HashMap<String, Object>> list  = deliverapplyDao.QueryBySql(sql);
	  				String resStr = "";
	  				if(list.size()>0){
	  					resStr +="[{\"fstate\":0,\"time\"  :\""+list.get(0).get("createtime").toString()+"\",\"isLater\" : false},";
	  				}
	  				for(int i=0;i<list.size();i++){
	  					if(list.get(i).get("fstate")!=null && list.get(i).get("fstate").toString().equals("3")){
	  						int sub1 = subHours(list.get(i).get("createtime").toString(),list.get(i).get("statetime").toString());
	  						if(sub1>=2){
	  							resStr +="{\"fstate\":1,\"time\"  :\""+list.get(i).get("statetime").toString()+"\",\"isLater\" : true},";
	  						}
	  						else{
	  							resStr +="{\"fstate\":1,\"time\"  :\""+list.get(i).get("statetime").toString()+"\",\"isLater\" : false},";
	  						}
	  					}	  		
	  					else if(list.get(i).get("fstate")!=null && list.get(i).get("fstate").toString().equals("4")){
	  						int sub1 = subHours(list.get(i).get("statetime").toString(),list.get(i).get("farrivetime").toString());
	  						if(sub1>=24){
	  							resStr +="{\"fstate\":4,\"time\"  :\""+list.get(i).get("statetime").toString()+"\",\"isLater\" : true},";
	  						}
	  						else{
	  							resStr +="{\"fstate\":4,\"time\"  :\""+list.get(i).get("statetime").toString()+"\",\"isLater\" : false},";
	  						}
	  					}	  	
	  					else if(list.get(i).get("fstate")!=null && list.get(i).get("fstate").toString().equals("5")){
	  						resStr +="{fstate:5,\"time\"  :\""+list.get(i).get("statetime").toString()+"\",isLater : false},";
	  					}	  	
	  					else if(list.get(i).get("fstate")!=null && list.get(i).get("fstate").toString().equals("6")){
	  						resStr +="{fstate:6,\"time\"  :\""+list.get(i).get("statetime").toString()+"\",isLater : false},";
	  					}	  	
	  				}
	  				if(!resStr.equals(""))
	  				{
	  					return resStr.substring(0, resStr.length()-1)+"]";
	  				}	  	
	  				return resStr;
	  			} catch (DJException e) {
	  				return e.getMessage();
	  			}
	  		
	  		}
	  		
	  		private int subHours(String begin,String end){	  			
	  			int subhour = 0;
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      				
					Date bgTime = sdf.parse(begin);
					Date edTime = sdf.parse(end);
					long diff = edTime.getTime() - bgTime.getTime();
					long day = diff / (24 * 60 * 60 * 1000);  
			         long   hour = (diff / (60 * 60 * 1000) - day * 24);  
					subhour = new Long(hour).intValue(); 
		  			return subhour;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return subhour;				
	  		}
	  		
			@RequestMapping("saveCustDelApplyInfo")
			public void saveCustDelApplyInfo(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
				String userid = ((Useronline) request.getSession().getAttribute(
						"Useronline")).getFuserid();
				DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				
				Deliverapply neliverapply = (Deliverapply)request.getAttribute("Deliverapply");			
				Deliverapply odeliverapply = deliverapplyDao.Query(neliverapply.getFid());
				try {
					Custproduct cinfo=(Custproduct)QuickOrderDao.Query(Custproduct.class,odeliverapply.getFcusproductid());
					if(cinfo==null)
					{
						throw new DJException("客户产品已禁用！");
					}
					neliverapply.setFmaterialfid(odeliverapply.getFmaterialfid());
					//2015年6月16日 此处是通过快速下单生成要货,只准修改
					if(!StringUtils.isEmpty(odeliverapply.getFid())){
					{
						if(odeliverapply.getFiscreate()==1){
							throw new DJException("已生成要货管理不能修改!");
						}
						neliverapply.setFupdatetime(new Date());
						neliverapply.setFiscreate(0);
						neliverapply.setFupdateuserid(userid);
						neliverapply.setFcreatetime(odeliverapply.getFcreatetime());
						neliverapply.setFcreatorid(odeliverapply.getFcreatorid());
						neliverapply.setFnumber(odeliverapply.getFnumber());
						Address addinfo=(Address)QuickOrderDao.Query(Address.class,neliverapply.getFaddressid());
						if(addinfo==null){
							throw new DJException("地址不正确！");
						}
						neliverapply.setFaddress(addinfo.getFdetailaddress());
						neliverapply.setFlinkman(addinfo.getFlinkman());
						neliverapply.setFlinkphone(addinfo.getFphone());
						String timeFrame= StringUtils.isEmpty(request.getParameter("fatime"))?"09":(request.getParameter("fatime").length()==1?"0":"")+request.getParameter("fatime");
						neliverapply.getFarrivetime().setHours(new Integer(timeFrame));					
						if(!DataUtil.dateFormatCheck(f.format(neliverapply.getFarrivetime()), true)){
							throw new DJException("送达日期格式错误或小于当前时间！");
						}
						if(!DataUtil.positiveIntegerCheck(neliverapply.getFamount().toString())){
							throw new DJException("要货数量必须大于0！");
						}
					}
					deliverapplyDao.saveOrUpdate(neliverapply);		
					response.getWriter().write(JsonUtil.result(true, "保存成功","",""));
					} 
				}catch (Exception e) {
					response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
				}
			}
			
			@RequestMapping("/getCustDelApplyInfo")
			public String getCustDelApplyInfo(HttpServletRequest request,
					HttpServletResponse reponse) throws IOException {
				reponse.setCharacterEncoding("utf-8");
				try {
					String fid = request.getParameter("fid");
					String sql = "select d.fid,d.fordernumber,d.fsupplierid,d.fcusproductid,d.fcustomerid,d.fordernumber,d.faddressid faddressid_fid,d.faddress faddressid_fname,date_format(d.farrivetime,'%Y-%m-%d') farrivetime,  case   when date_format(d.farrivetime,'%H')>12 then 14 else 9 end fatime,d.famount,d.fdescription from t_ord_deliverapply d  where d.fid = '"+request.getParameter("fid")+"'" ;
					List<HashMap<String,Object>> result= deliverapplyDao.QueryBySql(sql);
					reponse.getWriter().write(JsonUtil.result(true,"","",result));
				} catch (Exception e) {
					reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
				}
				return null;
			}			
			
			@RequestMapping("/delCustDelApplyInfo")
			public String delCustDelApplyInfo(HttpServletRequest request,HttpServletResponse reponse) throws IOException {
				String result = "";
				String fidcls = request.getParameter("fidcls");
				fidcls="('"+fidcls.replace(",","','")+"')";
				try {
					if(deliverapplyDao.QueryExistsBySql("select fid from t_ord_deliverapply where fstate<>0 and fstate<>7 and fboxtype=1 and fid in "+fidcls))
					{
						throw new DJException("已生成订单，不允许删除");
					}
					deliverapplyDao.ExecBySql("delete from t_ord_deliverapply where  (fstate=0 or fstate=7) and fid in "+fidcls);
					result = JsonUtil.result(true,"删除成功!", "", "");
				} catch (Exception e) {
					result = JsonUtil.result(false,e.getMessage(), "", "");
				}
				reponse.getWriter().write(result);
				return null;
			}
			
			
			
			
			 /**
		     * 制造商客户产品列表显示
		     * @param request
		     * @param reponse
		     * @return
		     * @throws IOException
		     */
		    @RequestMapping("/GetQuickProductforSupplierListPhone")
		    public String GetQuickProductforSupplierListPhone(HttpServletRequest request,
				        HttpServletResponse reponse) throws IOException {
				      String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
				      if(request.getParameter("sort")==null){
				    	  request.setAttribute("djsort", "c.fiscommon desc,c.fcreatetime desc");
				      }
				      String sql="";
				      ListResult result;
				      try {
				    	  String fsupplierid=baseSysDao.getCurrentSupplierid(userid);
				    	  sql="SELECT f.fid productid,c.fid,c.fname productName,c.fspec productSpec, c.fdescription,c.fcustomerid,c.flastorderfamount recentlyOrderCount,c.flastordertime recentlyOrderDate,f.fisboardcard FROM t_bd_custproduct c inner JOIN t_pdt_productdef  f ON c.fproductid=f.fid where  c.feffect=1 and ifnull(c.fcharacterid,'')='' and f.fsupplierid='"+fsupplierid+"'";
				    	  result = deliverapplyDao.QueryFilterList(sql, request);
				    		for (HashMap<String, Object> record : result.getData()) 
							{
								String pathT = getQuickFilePath(request,(String)record.get("fid"));
								record.put("proImgUrl",StringUtils.isEmpty(pathT)?"/vmifile/defaultpic.png":pathT);
							}
				    	  reponse.getWriter().write(JsonUtil.result(true,"",result));
				      } catch (DJException e) {
				        reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
				      }
				    
				      return null;
			}
	    @RequestMapping("/UpdateQuickCusproduct")
	    public void UpdateQuickCusproduct(HttpServletRequest request,
		        HttpServletResponse response) throws IOException{
	    	try {
				String fid = request.getParameter("fid");
				String fname = request.getParameter("fname");
				String fspec = request.getParameter("fspec");//setFcharacter
				String sql = "UPDATE t_bd_custproduct c,t_pdt_productdef p SET c.fisupdate=1,c.fname='"+fname+"',c.fspec='"+fspec+"',p.FNAME='"+fname+"',p.fcharacter='"+fspec+"',p.flastupdatetime=now(),c.flastupdatetime=now()	 WHERE c.fid='"+fid+"' AND c.fproductid=p.FID";
				deliverapplyDao.ExecBySql(sql);
				response.getWriter().write(JsonUtil.result(true,"修改成功！","",""));
			} catch (DJException e) {
				// TODO: handle exception
				response.getWriter().write(JsonUtil.result(true,e.getMessage(),"",""));
			}
	    }
}