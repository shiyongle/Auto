package Com.Controller.Oqa;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.Oqa.IQualityCheckDao;
import Com.Entity.Oqa.QualityCheck;
import Com.Entity.System.Useronline;


@Controller
public class QualityCheckController {
	Logger log = LoggerFactory.getLogger(QualityCheckController.class);
	@Resource
	private IQualityCheckDao qualityCheckDao;

	@RequestMapping(value = "/SaveQualityCheck")
	public String SaveQualityCheck(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		QualityCheck pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				pinfo = qualityCheckDao.Query(fid);
				pinfo.setFlastupdatetime(new Date());
				pinfo.setFlastupdateuserid(userid);

			} else {
				pinfo = new QualityCheck();
				pinfo.setFid(fid);
				pinfo.setFcreatorid(userid);
				pinfo.setFcreatetime(new Date());
				pinfo.setFnumber(qualityCheckDao.getFnumber("t_oqa_qualitycheck","Z", 3));
			}
			
			pinfo.setFdeliverid(request.getParameter("fdeliverid"));
			pinfo.setFsaleorderid(request.getParameter("fsaleorderid"));
			pinfo.setFcheckresult(Integer.valueOf(request.getParameter("fcheckresult")));
			pinfo.setFquestdescription(request.getParameter("fquestdescription"));
			HashMap<String, Object> params = qualityCheckDao.ExecSave(pinfo);
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true,"保存成功!", "", "");
			} else {
				result = JsonUtil.result(false,"保存失败!", "", "");
			}
		} catch (IndexOutOfBoundsException e) {
			result = JsonUtil.result(false,"编码流水号已经用完！", "", "");
		}catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping(value = "/GetQualityCheck")
	public String GetQualityCheck(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql="select o.fid, o.fnumber,o.fsaleorderid,ifnull(s.fnumber,'') salefnumber,o.fdeliverid ,ifnull(d.fnumber,'') fdelivernumber,o.fcheckresult,ifnull(u1.fname,'') as cfname,"
					+" ifnull(u2.fname,'') as lfname,o.fcreatetime,ifnull(o.flastupdatetime,'') flastupdatetime"
					+" from t_oqa_qualitycheck o "
					+" left join t_ord_saleorder s on s.fid=o.fsaleorderid "
					+" left join t_ord_delivers d on d.fid=o.fdeliverid"
					+" left join t_sys_user  u1 on u1.fid= o.fcreatorid "
					+" left join t_sys_user  u2 on u2.fid= o.flastupdateuserid " ;
					
			ListResult result = qualityCheckDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/GetQualityCheckInfo")
	public String GetQualityCheckInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql="select o.fid, o.fnumber,o.fsaleorderid as fsaleorderid_fid,ifnull(s.fnumber,'') fsaleorderid_fnumber,o.fdeliverid as fdeliverid_fid ,ifnull(d.fnumber,'') fdeliverid_fnumber,o.fcheckresult,o.fquestdescription"
					+" from t_oqa_qualitycheck o "
					+" left join t_ord_saleorder s on s.fid=o.fsaleorderid "
					+" left join t_ord_delivers d on d.fid=o.fdeliverid ";
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				sql += " where o.fid='" + fid + "'";
			}
			List<HashMap<String, Object>> sList = qualityCheckDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/DeleteQualityCheck")
	public String DeleteQualityCheck(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String hql = "Delete FROM QualityCheck where  fid in " + fidcls;
			qualityCheckDao.ExecByHql(hql);
			result = JsonUtil.result(true,"删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}

	
	
		
	@RequestMapping(value = "/QualityChecktoexcel")
	public String QualityChecktoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql="select o.fid, o.fnumber as '编码',o.fsaleorderid as '订单ID',ifnull(s.fnumber,'') as '订单编码',o.fdeliverid as '要货计划id',ifnull(d.fnumber,'') as '要货计划编码',o.fcheckresult as '检验结果',o.fquestdescription as '问题描述' ,ifnull(u1.fname,'') as '创建人',"
					+" o.fcreatetime as '创建时间', ifnull(u2.fname,'') as '修改人',ifnull(o.flastupdatetime,'') as '修改时间'" 
					+" from t_oqa_qualitycheck o "
					+" left join t_ord_saleorder s on s.fid=o.fsaleorderid "
					+" left join t_ord_delivers d on d.fid=o.fdeliverid"
					+" left join t_sys_user  u1 on u1.fid= o.fcreatorid "
					+" left join t_sys_user  u2 on u2.fid= o.flastupdateuserid " ;
			ListResult result = qualityCheckDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	
	
}