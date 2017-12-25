package com.pc.appInterface.address;

import java.io.IOException;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.org.rapid_framework.page.Page;
import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.message.impl.MessageDao;
import com.pc.model.CL_Message;
import com.pc.model.CL_UserRole;
import com.pc.query.message.CL_MessageQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class AppMessageController extends BaseController {
	
	@Resource
	private MessageDao sendDao;
	@Resource
	private UserRoleDao userRoleDao;
	
	private CL_MessageQuery sendQuery;
	
	/***APP查询列表[标题、时间、读取状态]*/
	@RequestMapping("/app/message/loadSendMsg")
	public String loadSendMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userRoleId,pageNum ,pageSize;
	   HashMap<String, Object> map=new HashMap<String,Object>();
//	   if(request.getParameter("userRoleId")==null || "".equals(request.getParameter("userRoleId"))){
//	    	map.put("success", "false");
//			map.put("msg","请先登录");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
//	    }else{
//	    	userRoleId =Integer.parseInt(request.getParameter("userRoleId"));
//	    }
		if(request.getParameter("pagenum")==null || "".equals(request.getParameter("pagenum"))){
	    	map.put("success", "false");
			map.put("msg","无法获知第几页");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	pageNum = Integer.valueOf(request.getParameter("pagenum"));
	    }
		if(request.getParameter("pagesize")==null || "".equals(request.getParameter("pagesize"))){
	    	map.put("success", "false");
			map.put("msg","无法获知每页显示多少条");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	pageSize = Integer.valueOf(request.getParameter("pagesize"));
	    }
		if (sendQuery == null) {
			sendQuery = newQuery(CL_MessageQuery.class, null);
		}
		if (pageNum != null) {
			sendQuery.setPageNumber(pageNum);
		}
		if (pageSize != null) {
			sendQuery.setPageSize(pageSize);
		}
		int userroleid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		sendQuery.setReceiver(userroleid);
		CL_UserRole user = userRoleDao.getById(userroleid);
		sendQuery.setReceiverPhone(user.getVmiUserPhone());
		Page<CL_Message> page=sendDao.findPage(sendQuery);
		map.put("success", "true");
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		System.out.println(map.get("data"));
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	@RequestMapping("/app/message/readmsg")
	@Transactional
	public String readmsg(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		int fid;
		if(request.getParameter("fid")==null||"".equals(request.getParameter("fid"))){
			map.put("success", "false");
  			map.put("msg","数据有误请联系客服");
  			return writeAjaxResponse(response, JSONUtil.getJson(map));
        }else{
        	fid=Integer.parseInt(request.getParameter("fid"));
        }
		int userroleid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		CL_UserRole user = userRoleDao.getById(userroleid);
        int update =sendDao.updateIsRead(fid,userroleid,1,user.getVmiUserPhone());
        if(update==1)
        {
        	map.put("success", "true");
        	map.put("msg", "状态已修改为已读");
        }
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}


//	/***查询消息信息*/
//	@RequestMapping("/app/query/loadMessage")
//	public String loadMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		Integer userRoleId,pageNum ,pageSize;
//		HashMap<String,Object> map =new HashMap<String,Object>();
//		if(request.getParameter("userRoleId")==null || "".equals(request.getParameter("userRoleId"))){
//	    	map.put("success", "false");
//			map.put("msg","未登录！");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
//	    }else{
//	    	userRoleId = Integer.valueOf(request.getParameter("userRoleId"));
//	    }
//		if(request.getParameter("pagenum")==null || "".equals(request.getParameter("pagenum"))){
//	    	map.put("success", "false");
//			map.put("msg","无法获知第几页");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
//	    }else{
//	    	pageNum = Integer.valueOf(request.getParameter("pagenum"));
//	    }
//		if(request.getParameter("pagesize")==null || "".equals(request.getParameter("pagesize"))){
//	    	map.put("success", "false");
//			map.put("msg","无法获知每页显示多少条");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
//	    }else{
//	    	pageSize = Integer.valueOf(request.getParameter("pagesize"));
//	    }
//		
//		messageQuery = newQuery(CL_MessageQuery.class, null);
//		messageQuery.setId(null);
//		if (pageNum != null) {
//			messageQuery.setPageNumber(pageNum);
//		}
//		if (pageSize != null) {
//			messageQuery.setPageSize(pageSize);
//		}
//		messageQuery.setReceiver(userRoleId);
//		Page<CL_Message> page = messageDao.findPage(messageQuery);
//		HashMap<String, Object> m = new HashMap<String, Object>();
//			m.put("success", "true");
//			m.put("total", page.getTotalCount());
//			m.put("data", page.getResult());
//	        System.out.println(m.get("data"));
//		return writeAjaxResponse(response, JSONUtil.getJson(m));
//	}
}
