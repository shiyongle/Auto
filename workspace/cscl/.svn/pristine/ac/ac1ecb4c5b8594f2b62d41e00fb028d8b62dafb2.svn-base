package com.pc.controller.message;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.message.ImessageDao;
import com.pc.dao.umeng.IUMengPushDao;
import com.pc.model.CL_Message;
import com.pc.model.CL_Umeng_Push;
import com.pc.query.message.CL_MessageQuery;
import com.pc.util.JSONUtil;
import com.pc.util.push.Demo;


@Controller
public class MessageController extends BaseController {
	
	protected static final String LIST_JSP= "/pages/pc/message/list.jsp";
//	protected static final String CREATE_JSP = "/pages/pc/message/create.jsp";
	protected static final String EDIT_JSP = "/pages/pc/message/edit.jsp";
	@Resource
	private ImessageDao messageDao;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private IUMengPushDao iumeng;
	
	/*** 菜单URL*/
	@RequestMapping("/message/list")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return LIST_JSP;
	}

	/*** 修改页面*/
	@RequestMapping("/message/edit")
	public String edit(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
		CL_Message message = this.messageDao.getById(id);
		request.setAttribute("message", message);
		return EDIT_JSP;
	}
	
	
	/*** 修改消息功能*/
	@RequestMapping("/message/update")
	public String update(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_Message message) throws Exception{
		messageDao.updateById(message);
		return writeAjaxResponse(reponse, "success");
	}

	
	/*** 加载列表信息*/
	@RequestMapping("/message/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_MessageQuery mssageQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String username=request.getParameter("fusername");
		if (mssageQuery == null) {
			mssageQuery = newQuery(CL_MessageQuery.class, null);
		}
		if (pageNum != null) {
			mssageQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			mssageQuery.setPageSize(Integer.parseInt(pageSize));
		}
		mssageQuery.setFusername(null);
		if(!"请选择".equals(username)&&username!=null)
		{
			mssageQuery.setFusername(username);
		}
		if(mssageQuery.getType()!=null)
		{
			if(mssageQuery.getType()== -1){
				mssageQuery.setType(null);
			}
		}
		HashMap<String, Object> m = new HashMap<String, Object>();
		Page<CL_Message> page = messageDao.findPage(mssageQuery);
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	/*** 新建-保存
	 * 删除待确认 2017/1/2
	 * */
	/*@RequestMapping("/message/save")
	public String save(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_Message mssage) throws Exception{
		mssage.setCreator(request.getSession().getAttribute("userId").toString());
		mssage.setCreateTime(new Date());
		UserRoleQuery userRoleQuery =new UserRoleQuery();
		if(mssage.getType().equals(0)){//全部用户
			userRoleQuery.setRoleId(0);
			this.sendUmengToService(mssage.getContent());
		}else if(mssage.getType().equals(1)){//货主用户
			userRoleQuery.setRoleId(1);
			CsclPushUtil.SendPushToAll("货主", mssage.getContent());
		}else if(mssage.getType().equals(2)){//车主用户
			userRoleQuery.setRoleId(2);
			CsclPushUtil.SendPushToAll("司机", mssage.getContent());
		}
		List<CL_UserRole> ls = userRoleDao.find(userRoleQuery);
		for(CL_UserRole userrole:ls){
			mssage.setReceiver(userrole.getId());
			mssage.setType(userrole.getRoleId());
			this.messageDao.save(mssage);
		}
		return writeAjaxResponse(reponse, "success");
	}*/
	
	public void sendUmengToService(int userid, String msg)
	{
		
		List<CL_Umeng_Push> umengList=iumeng.getUserUmengRegistration(userid);
		if(umengList.size()==0)
		{
			//获取到用户多台设备
			for (CL_Umeng_Push cl_Umeng_Push : umengList) {
				Demo.SendUmeng(cl_Umeng_Push.getFdevice(), msg, cl_Umeng_Push.getFdeviceType());
				
			}
			messageDao.save(Demo.savePushMessage(msg,userid,umengList.get(0).getFuserType()));
		}
	}
	
}
