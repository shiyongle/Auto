package Com.Controller.order;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.order.IPreProductDemandDao;
import Com.Entity.System.Useronline;
import Com.Entity.order.PreProductDemandStructure;
import Com.Entity.order.Productdemandfile;

@Controller
public class PreProductDemandController {
	
	@Resource
	private IPreProductDemandDao preProductDemandDao;
	@Resource
	private IBaseSysDao baseSysDao;
	
	@RequestMapping("getPreProductDemandList")
	public String getPreProductDemandList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sql = "select e.fplanfileid,e.fstructurefileid,e.fstructureid,e.fplanid,e.fid,e.fcpname,e.fspec,e.fcustpage,e.fcustomerid,e.fdescription,e1.fname fstructurefilename,e2.fname fplanfilename from t_ord_preproductdemand e" +
				" left join t_ord_productdemandfile e1 on e.fstructurefileid = e1.fid" +
				" left join t_ord_productdemandfile e2 on e.fplanfileid = e2.fid";
		try {
			ListResult result = preProductDemandDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	@RequestMapping("getPreProductDemandStructureList")
	public String getPreProductDemandStructureList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sql = "select e.fsfileid,e.fid,e.fcpname,e.fspec,e.fcustomerid,e.ffileid,e1.fname ffilename from t_ord_preproductdemand_structure e" +
				" left join t_ord_productdemandfile e1 on e.ffileid = e1.fid where 1=1 "+preProductDemandDao.QueryFilterByUser(request, "e.fcustomerid", null);
		try {
			ListResult result = preProductDemandDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	@RequestMapping("getPreProductDemandPlanList")
	public String getPreProductDemandPlanList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sql = "select e.fsfileid,e.fid,e.fcustpage,e.fcustomerid,e.ffileid,e1.fname ffilename from t_ord_preproductdemand_plan e" +
				" left join t_ord_productdemandfile e1 on e.ffileid = e1.fid where 1=1 "+preProductDemandDao.QueryFilterByUser(request, "e.fcustomerid", null);
		try {
			ListResult result = preProductDemandDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	@RequestMapping("delPreProductDemand")
	public String delPreProductDemand(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fidCon = baseSysDao.getCondition(request.getParameter("fidcls"));
		try {
			String sql = "select 1 from t_ord_firstproductdemand where fpreproductdemandid"+fidCon;
			if(preProductDemandDao.QueryExistsBySql(sql)){
				throw new DJException("记录已生成需求，无法删除！");
			}
			sql = "delete from t_ord_preproductdemand where fid"+fidCon;
			preProductDemandDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	@RequestMapping("delPreProductDemandStructure")
	public String delPreProductDemandStructure(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fidcls");
		try {
			String sql = "select 1 from t_ord_preproductdemand where fstructureid = '"+fid+"'";
			if(preProductDemandDao.QueryExistsBySql(sql)){
				throw new DJException("记录已生成需求，无法删除！");
			}
			preProductDemandDao.DelPreProductDemandStructure(fid);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	@RequestMapping("delPreProductDemandPlan")
	public String delPreProductDemandPlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fidcls");
		try {
			String sql = "select 1 from t_ord_preproductdemand where fplanid = '"+fid+"'";
			if(preProductDemandDao.QueryExistsBySql(sql)){
				throw new DJException("记录已生成需求，无法删除！");
			}
			preProductDemandDao.DelPreProductDemandPlan(fid);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	@RequestMapping("savePreProductDemandStructure")
	public String savePreProductDemandStructure(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		PreProductDemandStructure obj = (PreProductDemandStructure) request.getAttribute("PreProductDemandStructure");
		try {
			if(!StringUtils.isEmpty(obj.getFid())){
				PreProductDemandStructure entity = (PreProductDemandStructure) preProductDemandDao.Query(PreProductDemandStructure.class, obj.getFid());
				if(entity==null){
					throw new DJException("产品结构图纸ID不存在！");
				}
				entity.setFcpname(obj.getFcpname());
				entity.setFspec(obj.getFspec());
				entity.setFupdatetime(new Date());
				preProductDemandDao.saveOrUpdate(entity);
			}else{
				obj.setFid(preProductDemandDao.CreateUUid());
				obj.setFcreatorid(userid);
				obj.setFupdatetime(new Date());
				preProductDemandDao.saveOrUpdate(obj);
			}
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	/**
	 * 判断结构图纸或版面图纸对应的产品需求是否已存在
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("demandIsGenerated")
	public String demandIsGenerated(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String fid = request.getParameter("fid");
			if(fid == null){
				throw new DJException("ID不存在！");
			}
			String index = request.getParameter("index");
			String sql = "select 1 from t_ord_preproductdemand where ";
			if("0".equals(index)){		//结构图纸表，否则为平面图纸表
				sql += "fstructureid = '"+fid+"'";
			}else{
				sql += "fplanid = = '"+fid+"'";
			}
			if(preProductDemandDao.QueryExistsBySql(sql)){
				throw new DJException("已生成需求，不允许修改！");
			}
			response.getWriter().write(JsonUtil.result(true, "", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	@RequestMapping("uploadStructureFile")
	public String uploadStructureFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		try {
			preProductDemandDao.saveStructureFile(request,fid);
			response.getWriter().write(JsonUtil.result(true, "上传成功!", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	@RequestMapping("savePreProductDemandPlan")
	public String uploadPlanFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			preProductDemandDao.savePreProductDemandPlan(request);
			response.getWriter().write(JsonUtil.result(true, "上传成功!", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	//需求存草稿
	@RequestMapping("saveProductDemand")
	public String saveProductDemand(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			preProductDemandDao.saveProductDemand(request);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	//需求发布
	@RequestMapping("releaseProductDemand")
	public String releaseProductDemand(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			preProductDemandDao.saveReleaseProductDemand(request);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	@RequestMapping("addPreProductDemandDescription")
	public String addPreProductDemandDescription(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		String fdescription = request.getParameter("fdescription");
		String sql = "update t_ord_preproductdemand set fdescription = :fdescription where fid=:fid";
		params p = new params();
		p.put("fid", fid);
		p.put("fdescription", fdescription);
		try {
			preProductDemandDao.ExecBySql(sql, p);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	@RequestMapping("getDrawingById")
	public String getDrawingById(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		InputStream in = null;
		OutputStream out = null;
		try {
			Productdemandfile obj = (Productdemandfile) preProductDemandDao.Query(Productdemandfile.class, fid);
			if(obj==null){
				throw new DJException("图纸不存在！");
			}
			in = new FileInputStream(UploadFile.getSmallImagePath(obj.getFpath(),true));
			out = response.getOutputStream();
			IOUtils.copy(in, out);
			in.close();
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
}
