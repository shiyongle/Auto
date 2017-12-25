package Com.Controller.System;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

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
import Com.Dao.System.ISupplierjudgeprojectDao;
import Com.Entity.System.Supplierjudgeproject;

@Controller
public class SupplierjudgeprojectController {

	private static final String NUMBER_OR_NAME_IS_NOT_UNIQUE = "编码或名称重复，请修改";

	public static final String SUCCESS = "成功";

	Logger log = LoggerFactory.getLogger(SupplierjudgeprojectController.class);

	@Resource
	private ISupplierjudgeprojectDao supplierjudgeprojectDao;

	@Resource
	private ISupplierJudgeProjectentryDao supplierJudgeProjectentryDao;

	@RequestMapping("/selectSupplierjudgeprojects")
	public String selectSupplierjudgeprojects(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		try {
			result = supplierjudgeprojectDao
					.QueryselectSupplierjudgeprojects(request);

			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	@RequestMapping("/selectSupplierjudgeprojectByID")
	public String selectSupplierjudgeprojectByID(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		String id = request.getParameter("fid");

		try {
			result = supplierjudgeprojectDao
					.QueryselectSupplierjudgeprojectByID(request, id);

			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	@RequestMapping("/deleteSupplierjudgeprojects")
	public String deleteSupplierjudgeprojects(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		String fidcls = request.getParameter("fidcls");

		String[] ids = fidcls.split(",");

		try {
			supplierjudgeprojectDao.ExecDeleteSupplierjudgeprojects(ids);

			reponse.getWriter().write(JsonUtil.result(true, SUCCESS, "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	@RequestMapping("/saveSupplierjudgeproject")
	public String saveSupplierjudgeproject(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		setReqAndRepCharacterEncodingUTF8(request, reponse);

		Supplierjudgeproject supplierjudgeprojectT = (Supplierjudgeproject) request
				.getAttribute("Supplierjudgeproject");

		try {
			String id = supplierjudgeprojectT.getFid();

			// 保存是判断ID，以区分新增和修改
			if (id == null || id.trim().isEmpty()) {

				id = supplierjudgeprojectDao.CreateUUid();
				supplierjudgeprojectT.setFid(id);
				
			}

			if (isNumberAndNameUnique(supplierjudgeprojectT)) {
				supplierjudgeprojectDao.ExecSave(supplierjudgeprojectT);
			} else {

				throw new DJException(NUMBER_OR_NAME_IS_NOT_UNIQUE);

			}
			
			// 保存分路
			supplierJudgeProjectentryDao.ExecSaveSupplierJudgeProjectentryS(id,
					request);

			reponse.getWriter().write(JsonUtil.result(true, SUCCESS, "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	/**
	 * 
	 * 
	 * @param supplierjudgeprojectT
	 * @return
	 * 
	 * @date 2014-4-26 下午4:24:32 (ZJZ)
	 */
	private boolean isNumberAndNameUnique(
			Supplierjudgeproject supplierjudgeprojectT) {
		// TODO Auto-generated method stub

		//
		String hql = " from  Supplierjudgeproject where (fnumber = '%s' or fname = '%s') and fid <> '%s' ";

		hql = String.format(hql, supplierjudgeprojectT.getFnumber(),
				supplierjudgeprojectT.getFname(),
				supplierjudgeprojectT.getFid());

		List lT = supplierjudgeprojectDao.QueryByHql(hql);

		return lT.size() == 0;
	}

	private void setReqAndRepCharacterEncodingUTF8(HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {
		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
	}

}
