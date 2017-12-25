package Com.Controller.System;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.System.IAddressDao;
import Com.Dao.System.INewsDao;
import Com.Dao.System.IProductdefDao;
import Com.Dao.System.IProductdrawDao;
import Com.Dao.System.NewsDao;
import Com.Dao.System.ProductdefDao;
import Com.Entity.System.Address;
import Com.Entity.System.Productdraw;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;

@Controller
public class NewsController {
	public static final String BASE_SQL_PRE = " SELECT tsn.fid, tsn.ftitle, tsn.fcontent,   DATE_FORMAT(tsn.fcreatetime,'%Y-%m-%d %H:%i') fcreatetime, tsn.fcreaterid, top.fpath, tsn.fimgid FROM t_sys_news tsn left join t_ord_productdemandfile top on tsn.fimgid = top.fid ";
	
	public static final String BASE_SQL_BEHIND_NO_LIMIT = " order by tsn.fcreatetime desc ";
	
	public static final String BASE_SQL_BEHIND = BASE_SQL_BEHIND_NO_LIMIT + " limit %d ";
	
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private INewsDao newsDao;

	@Resource
	private IProductdefDao productdefDao;

	/**
	 * 产品图片上传
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2014-4-1 下午1:16:54 (ZJZ)
	 */
//	@RequestMapping("/uploadCusproductdraw")
//	public String uploadCusproductdraw(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException {
//
//		reponse.setCharacterEncoding("utf-8");
//		request.setCharacterEncoding("utf-8");
//		//
//		// boolean st = productdefDao.isHistoryProductVersion(request
//		// .getParameter("fcusproductid"));
//		//
//		// if (st) {
//		//
//		// reponse.getWriter()
//		// .write(JsonUtil
//		// .result(false,
//		// ProductdefDao.VersionOfTheProductCanToperatingHistory,
//		// "", ""));
//		// return null;
//		// }
//
//		try {
//			cusproductdrawDao.ExecSaveProudctImageFacade(request);
//			reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
//		} catch (Exception e) {
//			reponse.getWriter().write(
//					JsonUtil.result(false, e.getMessage(), "", ""));
//		}
//
//		return null;
//	}

	@RequestMapping("/gainIndexImgs")
	public String gainIndexImgs(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {

			ListResult lrT = newsDao.ExecGainIndexImgs(request);

			reponse.getWriter().write(JsonUtil.result(true, "", lrT));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping("/gainVmiNews")
	public String gainVmiNews(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {

			String sql = BASE_SQL_PRE + String.format(BASE_SQL_BEHIND, 20) ;
			
			ListResult lrT = new ListResult();
			
			lrT.setData(newsDao.QueryBySql(sql));
			
//			toRelativeP(lrT);
			
			reponse.getWriter().write(JsonUtil.result(true, "", lrT));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping("/gainVmiNewsNoLimit")
	public String gainVmiNewsNoLimit(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {
			
			
			request.setAttribute("djsort","tsn.fcreatetime desc");

			String sql = BASE_SQL_PRE;
			
//			ListResult lrT = new ListResult();
//			
//			lrT.setData(newsDao.QueryBySql(sql));
			
			
			ListResult lrT = newsDao.QueryFilterList(sql, request);
			
//			toRelativeP(lrT);
			
			reponse.getWriter().write(JsonUtil.result(true, "", lrT));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	@RequestMapping("/gainNewsById")
	public String gainNewsById(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String fid = request.getParameter("fid");

		try {

			String sql = BASE_SQL_PRE + String.format(" where tsn.fid = '%s' ", fid) + String.format(BASE_SQL_BEHIND, 10) ;

//			sql = String.format(sql,fid);

			ListResult lrT = new ListResult();
			
			lrT.setData(newsDao.QueryBySql(sql));
			
//			toRelativeP(lrT);

			reponse.getWriter().write(JsonUtil.result(true, "", lrT));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	@RequestMapping("/saveVmiNew")
	public String saveVmiNew(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {

			newsDao.ExecSaveVmiNew(request);
			
			reponse.getWriter().write(JsonUtil.result(true, "", "",""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping("/deleteNews")
	public String deleteNews(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String fids = request.getParameter("fidcls");
		
		try {

			String hql = " delete from News where fid in %s ";
			
			hql = String.format(hql,String.format("(%s)",fids));
			
			newsDao.ExecByHql(hql);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
}
