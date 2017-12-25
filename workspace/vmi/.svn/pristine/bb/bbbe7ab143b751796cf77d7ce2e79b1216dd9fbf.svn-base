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
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.ITakenumberformulaDao;
import Com.Entity.System.Takenumberformula;

@Controller
public class TakenumberformulaController {
	
	public static final String SUCCESS = "成功";

	Logger log = LoggerFactory.getLogger(TakenumberformulaController.class);

	@Resource
	private ITakenumberformulaDao takenumberformulaDao;

	@RequestMapping("/selectTakenumberformulas")
	public String selectTakenumberformulas(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		try {
			result = takenumberformulaDao.QueryselectTakenumberformulas(request);
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	@RequestMapping("/selectTakenumberformulaByID")
	public String selectTakenumberformulaByID(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		String id = request.getParameter("fid");
		
		try {
			result = takenumberformulaDao.QueryselectTakenumberformulaByID(request, id);
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	@RequestMapping("/deleteTakenumberformulas")
	public String deleteTakenumberformulas(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		String fidcls = request.getParameter("fidcls");
		
		String[] ids = fidcls.split(",");
		
		try {
			takenumberformulaDao.ExecDeleteTakenumberformulas(ids);
			
			reponse.getWriter().write(JsonUtil.result(true, SUCCESS, "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	@RequestMapping("/saveTakenumberformula")
	public String saveTakenumberformula(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		Takenumberformula takenumberformulaT = (Takenumberformula) request.getAttribute("Takenumberformula");
		
		try {
			takenumberformulaDao.ExecSave(takenumberformulaT);
			
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
