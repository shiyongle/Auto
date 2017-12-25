package Com.Controller.System;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.ISupplierJudgeProjectentryDao;
import Com.Entity.System.SupplierJudgeProjectentry;
import Com.Entity.System.Supplierjudgeproject;

@Controller
public class SupplierJudgeProjectentryController {
	
	public static final String SUCCESS = "成功";

	Logger log = LoggerFactory.getLogger(SupplierJudgeProjectentryController.class);

	@Resource
	private ISupplierJudgeProjectentryDao supplierJudgeProjectentryDao;

	@RequestMapping("/selectSupplierJudgeProjectentrys")
	public String selectSupplierJudgeProjectentrys(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		try {
			result = supplierJudgeProjectentryDao.QueryselectSupplierJudgeProjectentrys(request);
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

//	@RequestMapping("/selectSupplierJudgeProjectentryByID")
//	public String selectSupplierJudgeProjectentryByID(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException {
//		ListResult result;
//
//		setReqAndRepCharacterEncodingUTF8(request, reponse);
//
//		String id = request.getParameter("fid");
//		
//		try {
//			result = supplierassessmentresultDao.QueryselectSupplierJudgeProjectentryByID(request, id);
//			
//			reponse.getWriter().write(JsonUtil.result(true, "", result));
//		} catch (DJException e) {
//			reponse.getWriter().write(
//					JsonUtil.result(false, e.getMessage(), "", ""));
//		}
//		return null;
//
//	}
	
	@RequestMapping("/deleteSupplierJudgeProjectentrys")
	public String deleteSupplierJudgeProjectentrys(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		String fidcls = request.getParameter("fidcls");
		
		String[] ids = fidcls.split(",");
		
		try {
			supplierJudgeProjectentryDao.ExecDeleteSupplierJudgeProjectentrys(ids);
			
			reponse.getWriter().write(JsonUtil.result(true, SUCCESS, "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	@RequestMapping("/saveSupplierJudgeProjectentry")
	public String saveSupplierJudgeProjectentry(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		Supplierjudgeproject spMentT = (Supplierjudgeproject) request.getAttribute("Supplierjudgeproject");
		
		ArrayList<SupplierJudgeProjectentry> supplierJudgeProjectentrys = (ArrayList)request.getAttribute("SupplierJudgeProjectentry");
		
		try {
			
			for (SupplierJudgeProjectentry supplierJudgeProjectentryT : supplierJudgeProjectentrys) {
				
				supplierJudgeProjectentryT.setFparentId(spMentT.getFid());
				
				supplierJudgeProjectentryDao.ExecSave(supplierJudgeProjectentryT);
				
			}
			
			//全部成功才为成功
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
