package com.pc.controller.Rating;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.org.rapid_framework.page.Page;
import com.pc.controller.BaseController;
import com.pc.dao.rating.IratingDao;
import com.pc.model.CL_Rating;
import com.pc.query.rating.RatingQuery;
import com.pc.util.JSONUtil;

@Controller
public class RatingController extends BaseController {
	@Resource
	private IratingDao ratingDao;
	
	protected static final String RATING_JSP= "/pages/pc/rating/rating_list.jsp";

	@RequestMapping("/rating/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return RATING_JSP;
	}
	
	@RequestMapping("/rating/load")
	public String load(HttpServletRequest request, HttpServletResponse reponse,RatingQuery rateQuery) throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (rateQuery == null) {
			rateQuery = newQuery(RatingQuery.class, null);
		}
		if (pageNum != null) {
			rateQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			rateQuery.setPageSize(Integer.parseInt(pageSize));
		}		
//		rateQuery.setSortColumns("co.number desc");
//		Page<CL_Rating> page = ratingDao.findRatePage(rateQuery);
		rateQuery.setSortColumns("orderNum desc");
		Page<CL_Rating> page = ratingDao.findPage(rateQuery);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(map));
	}
}