package Com.Controller.System;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.IProductdefDao;
import Com.Dao.System.IProductdrawDao;
import Com.Dao.System.ProductdefDao;
import Com.Entity.System.Productdraw;

@Controller
public class ProductdrawController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private IProductdrawDao productdrawDao;

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
	@RequestMapping("/uploadProudctImage")
	public String uploadProudctImage(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		boolean st = productdefDao.isHistoryProductVersion(request
				.getParameter("fproductID"));

		if (st) {

			reponse.getWriter()
					.write(JsonUtil
							.result(false,
									ProductdefDao.VersionOfTheProductCanToperatingHistory,
									"", ""));
			return null;
		}

		try {
			productdrawDao.ExecSaveProudctImageFacade(request);
			reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	@RequestMapping("/selectProductdraws")
	public String selectProductdraws(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		String productID = request.getParameter("productID");

		try {
			ListResult lrT = productdrawDao.ExecselectProductdrawsByProduct(
					productID, request);
			reponse.getWriter().write(JsonUtil.result(true, "", lrT));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	@RequestMapping("/deleleProudctDraws")
	public String deleleProudctDraws(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String result = "";

		

		String fidcls = request.getParameter("fidcls");

		String[] ids = new String[1];
		
		if (fidcls.contains(",")) {
			
			ids = fidcls.split(","); 
			
		} else {
			
			ids[0] = fidcls; 
			
		}

		String productID = selectProudctIDByDraw(ids[0]);
		
		boolean st = productdefDao.isHistoryProductVersion(productID);

		if (st) {

			reponse.getWriter()
					.write(JsonUtil
							.result(false,
									ProductdefDao.VersionOfTheProductCanToperatingHistory,
									"", ""));
			return null;
		}
		
		try {
			productdrawDao.ExecDeleteProudctDraws(ids, request);
			result = JsonUtil.result(true, "删除成功!", "", "");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("Productdraw error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}

	private String selectProudctIDByDraw(String id) {
		// TODO Auto-generated method stub
		
		Productdraw pd = productdrawDao.Query(id);

		return pd.getFproductId();
	}
}
