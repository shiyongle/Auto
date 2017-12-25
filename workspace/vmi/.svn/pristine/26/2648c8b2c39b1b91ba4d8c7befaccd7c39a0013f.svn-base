package Com.Controller.System;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.IProductCycleDao;
import Com.Dao.System.IProductdefDao;
import Com.Entity.System.Productcycle;
import Com.Entity.System.Useronline;


@Controller
public class ProductCycleController { 
	Logger log = LoggerFactory.getLogger(ProductCycleController.class);
	@Resource
	private IProductCycleDao productCycleDao;
	
	@Resource
	private IProductdefDao productdefDao;

	@RequestMapping(value = "/SaveProductCycle")
	public String SaveProductCycle(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		Productcycle pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				pinfo = productCycleDao.Query(fid);
			} else {
				pinfo = new Productcycle();
				pinfo.setFid(fid);
				pinfo.setFcreatorid(userid);
				pinfo.setFcreatetime(new Date());
			}
			pinfo.setFlastupdatetime(new Date());
			pinfo.setFlastupdateuserid(userid);
//			pinfo.setFname(request.getParameter("fname"));
//			pinfo.setFnumber(request.getParameter("fnumber"));
//			pinfo.setFdescription(request.getParameter("fdescription"));
			pinfo.setFproductdefid(request.getParameter("fproductdefid"));
			pinfo.setFsupplierid(request.getParameter("fsupplierid"));
			pinfo.setFlifecycle(Integer.valueOf(request.getParameter("flifecycle")));
			HashMap<String, Object> params = productCycleDao.ExecSave(pinfo);
			if (params.get("success") == Boolean.TRUE) {
				result = "{success:true,msg:'保存成功!'}";
			} else {
				result = "{success:false,msg:'保存失败!'}";
			}
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}
	
	@RequestMapping(value = "/GetCycles")
	public String GetCycles(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select d.fid,d.fproductdefid fproductdefid_fid, f.fname fproductdefid_fname, d.fsupplierid fsupplierid_fid, s.fname fsupplierid_fname,d.flifecycle,d.flastupdateuserid ,u2.fname fname2, d.flastupdatetime ,d.fcreatetime ,d.fcreatorid ,u1.fname fname1 FROM t_bd_productcycle d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid  left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid where 1=1 "+productCycleDao.QueryFilterByUser(request, null, "d.fsupplierid");
//			String fid=request.getParameter("fid");
//			if(fid!=null&&!"".equals(fid))
//			{
//				sql+=" where d.fid='"+fid+"'";
//			}
			ListResult result = productCycleDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
			
			
			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/getLifeCycles")
	public String getLifeCycles(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
//			String sql = "SELECT pc.FID as fid, pc.FPRODUCTDEFID as productid, pc.FSUPPLIERID as providerid, pc.FLIFECYCLE as plifecycle, pc.fproportion as mproportion, s.FNAME as providername FROM t_bd_productcycle pc, t_sys_supplier s where pc.FSUPPLIERID = s.FID ";
//			String sql = "SELECT pc.FID as fid, pc.FPRODUCTDEFID as productid, pc.FSUPPLIERID as providerid, pc.FLIFECYCLE as plifecycle, pc.proportion as mproportion, s.FNAME as providername FROM t_bd_productcycle pc left join t_sys_supplier s on pc.FSUPPLIERID = s.FID ";
			
//			String fid = request.getParameter("productid");
//			if (fid != null && !"".equals(fid)) {
//				sql += " and pc.FPRODUCTDEFID = \'" + fid + "\'";
//			}
//			List<HashMap<String, Object>> sList = productCycleDao.QueryBySql(sql);
			String sql = "SELECT pc.fid, pc.fproductdefid, pc.fsupplierid, pc.flifecycle, pc.fproportion, s.FNAME as fsuppliername FROM t_bd_productcycle pc, t_sys_supplier s where pc.FSUPPLIERID = s.FID ";
			ListResult result = productCycleDao.QueryFilterList(sql, request);
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/GetCycleInfo")
	public String GetCycleInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select d.fid,d.fproductdefid fproductdefid_fid, f.fname fproductdefid_fname, d.fsupplierid fsupplierid_fid, s.fname fsupplierid_fname,d.flifecycle FROM t_bd_productcycle d   left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid ";
			String fid=request.getParameter("fid");
			if(fid!=null&&!"".equals(fid))
			{
				sql+=" where d.fid='"+fid+"'";
			}
			List<HashMap<String, Object>> sList = productCycleDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/GetCycleProductInfo")
	public String GetCycleProductInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select d.fid,d.fname FROM t_pdt_productdef d where d.fid='"
					+ request.getParameter("fid") + "'";
			;
			List<HashMap<String, Object>> sList = productCycleDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}

	@RequestMapping(value = "/DeleteCycles")
	public String deleteCycles(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String hql = "Delete FROM Productcycle where fid in " + fidcls;
			productCycleDao.ExecByHql(hql);
			result = "{success:true,msg:'删除成功!'}";
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
					+ "'}";
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}
	
	
	@RequestMapping(value = "/saveProportion")
	public String saveProportion(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result="";
		try{
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String[] fproductid = request.getParameter("fid").split(",");
			List<Productcycle> productcyclelist = (List<Productcycle>) request.getAttribute("Productcycle");
			
			for(String productid : fproductid){
				double proportion = 0;
				HashMap<String, String> suppliermap = new HashMap<String, String>();
				
				for(int i=0;i<productcyclelist.size();i++){
					Productcycle productcycle = productcyclelist.get(i);
					if(productcycle.getFproportion()==0){
						throw new DJException("分配比例不能为0% !");
					}
					proportion += productcycle.getFproportion();
//					if(productcycle.getFproductdefid()!=null && !productcycle.getFproductdefid().equals("")){
//						productid = productcycle.getFproductdefid();
//					}
					
					if(suppliermap.containsKey(productcycle.getFsupplierid())){
						throw new DJException("制造商添加重复！");
					}
					suppliermap.put(productcycle.getFsupplierid(), "supplier");
				}
				
				if(proportion!=100){
					throw new DJException("分配比例的和必须为100% !");
				}
				
				String isHistory= productdefDao.QueryIsHistory("('"+productid+"')");
				if(!"false".equals(isHistory))
				{
					throw new DJException(isHistory);
				}
				
				//检查生产比例是否删除安全库存存在的制造商;
				String sql = "select FSUPPLIERID from t_pdt_vmiproductparam where fproductid = '"+productid+"'";
				List<HashMap<String, Object>> vmiproductparamcls=productCycleDao.QueryBySql(sql);
				for(int j=0;j<vmiproductparamcls.size();j++)
				{
					if(!suppliermap.containsKey(vmiproductparamcls.get(j).get("FSUPPLIERID").toString())){
						throw new DJException("不能删除安全库存存在的制造商！");
					}
				}
				
				String hql="delete FROM Productcycle where fproductdefid='" + productid+"'";
				productCycleDao.ExecByHql(hql);
			
				for(int k=0;k<productcyclelist.size();k++)
				{
					Productcycle pinfo=productcyclelist.get(k);
					pinfo.setFid(productCycleDao.CreateUUid());
					pinfo.setFproductdefid(productid);
					productCycleDao.saveOrUpdate(pinfo);
				}
			}
//			String productid = "";
			
			reponse.getWriter().write(
					JsonUtil.result(true, "保存成功!", "", ""));
		} catch (Exception e) {
			// TODO: handle exception

			reponse.getWriter().write(
					JsonUtil.result(false, "保存失败!" + e.getMessage(), "", ""));
		}
		return null;
	}

	
	@RequestMapping(value = "/updateProportion")
	public String updateProportion(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		boolean succes = false;

		HashMap<String, Object> params;
		
		String json = request.getReader().readLine();

		JSONArray jsonA = getJsonArrayByS(json);

		Productcycle productcycle;
		
		try {
			
				for (int i = 0; i < jsonA.size(); i++) {
					productcycle = productCycleDao.Query(((JSONObject) jsonA.get(i))
							.getString("fid"));
					params = dealWithPO(productcycle, (JSONObject) jsonA.get(i),
							request, reponse, true);
					if (params.get("success") == Boolean.TRUE) {
						succes = true;
					}
				}
			

			if (succes) {
				reponse.getWriter().write(
						JsonUtil.result(true, "保存succes", "", ""));
			} else {
				reponse.getWriter().write(
						JsonUtil.result(false, "保存失败!", "", ""));
			}

		} catch (Exception e) {
			// TODO: handle exception

			reponse.getWriter().write(
					JsonUtil.result(false, "保存失败!" + e.toString(), "", ""));
		}

		return null;
	}

	private HashMap<String, Object> dealWithPO(Productcycle productcycle,
			JSONObject jsobj, HttpServletRequest request,
			HttpServletResponse reponse, boolean update) {
		// TODO Auto-generated method stub

//		String result = "";

		HashMap<String, Object> params;

//		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");

		productcycle.setFproductdefid(jsobj.getString("productid"));
		productcycle.setFsupplierid(jsobj.getString("providerid"));
		productcycle.setFlifecycle(jsobj.getInt("plifecycle"));
		productcycle.setFproportion(jsobj.getDouble("mproportion"));

//		if (update) {
//			params = productCycleDao.update(productcycle);
//
//		} else {
//			params = productCycleDao.ExecSave(productcycle);
//		}
		
		 params = productCycleDao.ExecSave(productcycle);
		
		return params;
		

	}

	@RequestMapping(value = "/deleteProportions")
	public String deleteProportions(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		boolean succes = true;

		String result = "";

		String json = request.getReader().readLine();

		JSONArray jsona = getJsonArrayByS(json);
		
		try {
			for (int i = 0; i < jsona.size(); i++) {
				productCycleDao.Delete(productCycleDao.Query(((JSONObject) jsona.get(i)).getString("fid")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelDeliversList error", e);
			succes = false;
		}
			
		if (succes) {
			result = JsonUtil.result(true, "删除成功!", "", "");
		}

		reponse.getWriter().write(result);
		return null;
	}

	private JSONArray getJsonArrayByS(String s) {

		JSONObject jso = JSONObject.fromObject(s);
		
		String dataT = jso.getString("data");
		
		JSONArray jsa = null;

		if (dataT.charAt(0) == '{') {
			dataT = "[" + dataT + "]";
		}

		jsa = JSONArray.fromObject(dataT);

		return jsa;
	}
	
}