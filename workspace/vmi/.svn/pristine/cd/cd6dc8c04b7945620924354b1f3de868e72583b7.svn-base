package Com.Controller.System;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonObject;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Dao.System.IProducePlanDao;
import Com.Entity.System.MaterialLimit;
import Com.Entity.System.ProducePlan;
import Com.Entity.System.Supplierboardplanconfig;
import Com.Entity.System.Useronline;

@Controller
public class ProducePlanController {
	
	@Resource
	private IProducePlanDao producePlanDao;
	
	@RequestMapping(value = "/getProducePlanList")
	public String getProducePlanList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fsupplierid = request.getParameter("fsupplierid");
		String fname = request.getParameter("prouductName");
		try {
			if(StringUtils.isEmpty(fsupplierid)){
				throw new DJException("制造商ID为空！");
			}
			String sql = "select e.fid,concat(e.fname,' ',e.fnumber,' ','/',e.flayer,e.ftilemodelid) fname,e.ftilemodelid,e1.fcreatetime,e1.fupdatetime,e.feffected fstate,e1.fid fproduceplanid,e1.fisweek,e1.fweek,e1.fday,e1.fnoday from t_pdt_productdef e " +
					"left join t_pdt_produceplan e1 on e.fid=e1.fproductid where e.fboxtype = 1 and e.fsupplierid ='"+fsupplierid+"'";
			if(!StringUtils.isEmpty(fname)){
				sql += "and ( e.fname like '%"+fname+"%' or e.ftilemodelid like '%"+fname+"%' )";
			}

			List<HashMap<String, Object>> list = producePlanDao.QueryBySql(sql);
			HashMap map=new HashMap();	
			JSONObject object =new JSONObject();
			object.put("timeconfig","");
			List qlist=producePlanDao.QueryByHql(String.format(" from Supplierboardplanconfig where fsupplierid='%s' ",fsupplierid));
			if(qlist.size()>0) 	object.put("timeconfig",""+((Supplierboardplanconfig)qlist.get(0)).getForderlasttime());
			response.getWriter().write(JsonUtil.result(true, "", object.toString(),list));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/saveProducePlan")
	public String saveProducePlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ProducePlan producePlan = (ProducePlan) request.getAttribute("ProducePlan");
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			producePlanDao.saveProducePlan(producePlan,userid);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "操作失败！", "",""));
		}
		return null;
	}
	
	@RequestMapping(value = "/deleteProducePlan")
	public String deleteProducePlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fidcls = request.getParameter("fidcls");
		try {
			producePlanDao.DelProducePlan(fidcls);
			response.getWriter().write(JsonUtil.result(true, "删除成功！","",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(true, e.getMessage(),"",""));
		}
		return null;
	}
	//查询客户的材料总长和总高
	@RequestMapping(value = "/selectMaterialLimit")
	public String selectMaterialLimit(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
					" union select c.fcustomerid from  t_sys_userrole r "+
					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
			List<HashMap<String, Object>> custList=producePlanDao.QueryBySql(sql);
			String customerid = null;
			int size = custList.size();
			if(size==1)
			{
				HashMap<String,Object> map=custList.get(0);
				customerid =map.get("fcustomerid").toString();
			}else if(size>1){
				throw new DJException("当前帐号关联多个客户,不能执行操作！");
			}else{
				throw new DJException("当前帐号未关联客户,不能执行操作！");
			}
			sql = "select fmaxwidth,fmaxlength,fminwidth,fminlength from t_sys_material_limit where fcustomerid ='"+customerid+"'";
			List<HashMap<String, Object>> data = producePlanDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "", data.size()+"",data));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
		return null;
	}
	
	@RequestMapping(value = "/selectMaterialLimitByCustomer")
	public String selectMaterialLimitByCustomer(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String customerid = request.getParameter("customerid");
			String sql = "select fmaxwidth,fmaxlength,fminwidth,fminlength from t_sys_material_limit where fcustomerid ='"+customerid+"'";
			List<HashMap<String, Object>> data = producePlanDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "", data.size()+"",data));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
		return null;
	}
	
	//保存客户的材料总长和总高
	@RequestMapping(value = "/saveMaterialLimit")
	public String saveMaterialLimit(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			String customerid = request.getParameter("customerid");
			String sql;
			if(StringUtils.isEmpty(customerid)){	//是纸板下单，而不是产品档案编辑时保存
				sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
						" union select c.fcustomerid from  t_sys_userrole r "+
						" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
				List<HashMap<String, Object>> custList=producePlanDao.QueryBySql(sql);
				if(custList.size()==1)
				{
					HashMap<String,Object> map=custList.get(0);
					customerid =map.get("fcustomerid").toString();
				}else{
					throw new DJException("当前帐号关联多个客户,不能执行操作！");
				}
			}
			MaterialLimit materialLimit = new MaterialLimit();
			materialLimit.setFmaxlength(Double.valueOf(request.getParameter("fmaxlength")));
			materialLimit.setFmaxwidth(Double.valueOf(request.getParameter("fmaxwidth")));
			materialLimit.setFminlength(Double.valueOf(request.getParameter("fminlength")));
			materialLimit.setFminwidth(Double.valueOf(request.getParameter("fminwidth")));
			materialLimit.setFcustomerid(customerid);
			producePlanDao.saveMaterialLimit(materialLimit);
			response.getWriter().write(JsonUtil.result(true, "", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
		return null;
	}
	
	//保存材料的白班夜班
		@RequestMapping(value = "/saveMaterialState")
		public String saveMaterialState(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			try {
				String fid = request.getParameter("fid");
				if(StringUtils.isEmpty(fid)){
					throw new DJException("请选择记录操作");
				}
				String ftype = request.getParameter("ftype");
				fid = fid.replaceAll(",", "','");
				producePlanDao.ExecBySql("update t_pdt_productdef set feffected="+ftype+" where fid in ('"+fid+"') and fboxtype=1 ");

				response.getWriter().write(JsonUtil.result(true, "设置成功", "",""));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
			}
			return null;
		}
		
		
		//保存材料的白班夜班
				@RequestMapping(value = "/saveLastTimeConfig")
				public String saveLastTimeConfig(HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					try {
						DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						String timeValue = request.getParameter("timeValue");
						String fsupplierid=request.getParameter("fsupplierid");
						if(StringUtils.isEmpty(fsupplierid)) throw new DJException("请选择制造商,再操作！");
						Supplierboardplanconfig config=null;
						List list=producePlanDao.QueryByHql(String.format(" from Supplierboardplanconfig where fsupplierid='%s' ",fsupplierid));
						if(StringUtils.isEmpty(timeValue))
						{
							if(list.size()>0) producePlanDao.Delete(list.get(0));
						}
						else{
						if(list.size()==0){
								 config=new Supplierboardplanconfig();
								config.setFid(producePlanDao.CreateUUid());
								config.setForderlasttime(f.parse(timeValue));
								config.setFsupplierid(fsupplierid);
						}else{
							config=(Supplierboardplanconfig)list.get(0);
							config.setForderlasttime(f.parse(timeValue));
						}
						producePlanDao.saveOrUpdate(config);
						}
						response.getWriter().write(JsonUtil.result(true, "设置成功", "",""));
					} catch (DJException e) {
						response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
					}catch (Exception e) {
						response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
					}
					return null;
				}
		
	
}
