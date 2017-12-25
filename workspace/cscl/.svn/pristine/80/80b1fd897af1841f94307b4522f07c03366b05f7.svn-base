package com.pc.controller.complain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.org.rapid_framework.page.Page;
import com.pc.controller.BaseController;
import com.pc.dao.usercomplain.IComplainEntryDao;
import com.pc.dao.usercomplain.IUserComplainDao;
import com.pc.model.CL_ComplainEntry;
import com.pc.query.usercomplain.ComplainEntryQuery;
import com.pc.util.JSONUtil;

@Controller
public class ComplainEntryController extends BaseController {
	
	@Resource
	private IUserComplainDao userComplainDao;
	@Resource
	private IComplainEntryDao entryDao;
	
//	protected static final String LIST_JSP= "/pages/pc/complain/complain_list.jsp";
//	protected static final String CREATE_JSP = "/pages/pc/complain/addComplainOrder.jsp";
//	protected static final String EDIT_JSP = "/pages/pc/complain/editComplainOrder.jsp";
	/*** 表单提交日期绑定*/
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");//写上你要的日期格式
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }
	
//	@RequestMapping("/usercomplain/list")
//	public String list(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
//		return LIST_JSP;		
//	}
	
//	@RequestMapping("/usercomplain/edit")
//	public String edit(HttpServletRequest request, HttpServletResponse reponse,Integer id) throws Exception {
//		CL_Complain ccinfo = userComplainDao.getById(id);
//		UserComplainQuery ucquery =  new UserComplainQuery();
//		List<Map<String,Object>> orderList = userComplainDao.getOrderDropdown(".getOrderDropdown",ucquery);
//		if(orderList.size()>0){
//			ccinfo.setFuserName(orderList.get(0).get("fuserName").toString());
//			ccinfo.setFdriverName(orderList.get(0).get("fdriverName").toString());
//		}
//		request.setAttribute("ccinfo", ccinfo);
//		return EDIT_JSP;
//	}
    
//	@RequestMapping("/complainentry/load")
//	public String load(HttpServletRequest request, HttpServletResponse reponse,@ModelAttribute ComplainEntryQuery entryquery) throws Exception {
//		String pageNum = request.getParameter("page");
//		String pageSize = request.getParameter("rows");
//		if (entryquery == null) {
//			entryquery = newQuery(ComplainEntryQuery.class, null);
//		}
//		if (pageNum != null) {
//			entryquery.setPageNumber(Integer.parseInt(pageNum));
//		}
//		if (pageSize != null) {
//			entryquery.setPageSize(Integer.parseInt(pageSize));
//		}
//		Page<CL_ComplainEntry> page = entryDao.findPage(entryquery);
//		HashMap<String, Object> m = new HashMap<String, Object>();
//		m.put("total", page.getTotalCount());
//		m.put("rows", page.getResult());
//		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
//	}
	
//	@RequestMapping("/usercomplain/saveComplain")
//	public String  saveInfo(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_Complain cominfo) throws Exception{
//		cominfo.setFcreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
//		cominfo.setFcreateTime(new Date());
//		this.userComplainDao.save(cominfo);
//		return writeAjaxResponse(reponse, "success");
//	}
	
//	@RequestMapping("/usercomplain/updateComplain")
//	public String  updateInfo(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_Complain cominfo) throws Exception{
//		cominfo.setFcreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
//		cominfo.setFcreateTime(new Date());
//		this.userComplainDao.update(cominfo);
//		return writeAjaxResponse(reponse, "success");
//	}
	
//	// 删除功能
//	@RequestMapping("/usercomplain/del")
//	@Transactional(propagation = Propagation.REQUIRED)
//	public String del(HttpServletRequest request, HttpServletResponse reponse,Integer[] ids) throws Exception {
//		for (Integer id : ids) {
//			CL_Complain ccinfo = userComplainDao.getById(id);
//			if(ccinfo.isFisdeal()){
//				return writeAjaxResponse(reponse, "false");
//			}
//			userComplainDao.deleteById(id);
//		}
//		return writeAjaxResponse(reponse, "success");
//	}
	
}
