package Com.Controller.System;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import Com.Dao.System.ISupplierjudgementDao;
import Com.Entity.System.Supplierjudgement;

@Controller
public class SupplierjudgementController {
	
	public static final String SUCCESS = "成功";

	Logger log = LoggerFactory.getLogger(SupplierjudgementController.class);

	@Resource
	private ISupplierjudgementDao supplierjudgementDao;

	@RequestMapping("/selectSupplierjudgements")
	public String selectSupplierjudgements(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		try {
			result = supplierjudgementDao.QueryselectSupplierjudgements(request);
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	@RequestMapping("/selectSupplierjudgementByID")
	public String selectSupplierjudgementByID(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		String id = request.getParameter("fid");
		
		try {
			result = supplierjudgementDao.QueryselectSupplierjudgementByID(request, id);
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	@RequestMapping("/getSupplierjudgementrpt")
	public String getSupplierjudgementrpt(HttpServletRequest request,
			HttpServletResponse reponse)throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);
		request.setAttribute("djsort"," t.fyear,t.fmonth desc ");
		try {
			result = supplierjudgementDao.QueryFilterList("SELECT t.fid,t.fsupplierid,s.fname supplier,t.fyear,t.fmonth,t.fsupplierJudgementid,st.fname supplierJudgement,t.fsupplierJudgeProjectid,sp.fname supplierJudgeProject,t.fresultValue,t.fjudgeProjectScore,t.fjudgementScore,t.fendScore FROM t_sys_supplierjudgementrpt t left join t_sys_supplier s on s.fid=t.fsupplierid left join t_sys_supplierJudgement st on st.fid=t.fsupplierJudgementid left join t_sys_supplierJudgeProject sp on sp.fid=t.fsupplierJudgeProjectid ", request);
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/SupplierjudgementrpttoExcel")
	public String SupplierjudgementrpttoExcel(HttpServletRequest request,
			HttpServletResponse reponse)throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);
		request.setAttribute("djsort"," t.fyear,t.fmonth desc ");
		try {
			result = supplierjudgementDao.QueryFilterList("SELECT s.fname 制造商,t.fyear 年,t.fmonth 月,st.fname 评估类型,sp.fname 评估项目,t.fresultValue 结果值,t.fjudgeProjectScore 项目得分,t.fjudgementScore 类型得分,t.fendScore 最终得分 FROM t_sys_supplierjudgementrpt t left join t_sys_supplier s on s.fid=t.fsupplierid left join t_sys_supplierJudgement st on st.fid=t.fsupplierJudgementid left join t_sys_supplierJudgeProject sp on sp.fid=t.fsupplierJudgeProjectid ", request);
//			reponse.getWriter().write(JsonUtil.result(true, "", result));
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/createAssessmentResult")
	public String createAssessmentResult(HttpServletRequest request,
			HttpServletResponse reponse)throws Exception {
		
		setReqAndRepCharacterEncodingUTF8(request, reponse);
		
		try {
			supplierjudgementDao.SaveAssessmentResult(request);
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			return null;
		}
		reponse.getWriter().write(JsonUtil.result(true, "生成评价结果成功！", "",""));
		return null;
	}
	
	@RequestMapping("/calculateResult")
	public String calculateResult(HttpServletRequest request,
			HttpServletResponse reponse)throws Exception {
		
		setReqAndRepCharacterEncodingUTF8(request, reponse);
		
		try {
			supplierjudgementDao.SaveAssessmentResult(request);
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			return null;
		}
		reponse.getWriter().write(JsonUtil.result(true, "计算结果值成功！", "",""));
		return null;
	}
	
	@RequestMapping("/SaveResultValue")
	public String SaveResultValue(HttpServletRequest request,
			HttpServletResponse reponse)throws Exception {
		
		setReqAndRepCharacterEncodingUTF8(request, reponse);
		
		try {
			supplierjudgementDao.SaveAssessmentResult(request);
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			return null;
		}
		reponse.getWriter().write(JsonUtil.result(true, "修改结果值成功！", "",""));
		return null;
	}
	
	@RequestMapping("/calculateScore")
	public String calculateScore(HttpServletRequest request,
			HttpServletResponse reponse)throws Exception {
		
		setReqAndRepCharacterEncodingUTF8(request, reponse);
		
		try {
			supplierjudgementDao.SaveAssessmentResult(request);
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			return null;
		}
		reponse.getWriter().write(JsonUtil.result(true, "计算得分成功！", "",""));
		return null;
	}
	
	@RequestMapping("/deleteSupplierjudgements")
	public String deleteSupplierjudgements(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		String fidcls = request.getParameter("fidcls");
		
		String[] ids = fidcls.split(",");
		
		try {
			supplierjudgementDao.ExecDeleteSupplierjudgements(ids);
			
			reponse.getWriter().write(JsonUtil.result(true, SUCCESS, "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	@RequestMapping("/saveSupplierjudgement")
	public String saveSupplierjudgement(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		Supplierjudgement spMentT = (Supplierjudgement) request.getAttribute("Supplierjudgement");
		
		try {
			supplierjudgementDao.ExecSave(spMentT);
			
			reponse.getWriter().write(JsonUtil.result(true, SUCCESS, "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	private void setReqAndRepCharacterEncodingUTF8(HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {
		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
	}

}
