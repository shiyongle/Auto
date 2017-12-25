package com.pc.controller.message;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.message.IMessageSendDao;
import com.pc.dao.message.ImessageDao;
import com.pc.model.CL_Message;
import com.pc.query.userRole.UserRoleQuery;
//import com.pc.dao.aBase.impl.sBaseDao;

@Controller
public class MessageSendController extends BaseController {
	
	protected static final String CREATE_JSP = "/pages/pc/message/create.jsp";
	@Resource
	private IMessageSendDao messageSendDao;
//	@Resource
//	private sBaseDao sbaseDao;
	@Resource
	private ImessageDao messageDao;
	@Resource
	private UserRoleDao userRoleDao;
	
	/*** 新建信息*/
	@RequestMapping("/messagesend/create")
	public String create(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setAttribute("relate", "relatepic");
		return CREATE_JSP;
	}

	
	/*** 新建消息并添加进发送消息表*/
	@RequestMapping("/messagesend/save")
	public String save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_Message message) throws Exception{
		message.setCreator(request.getSession().getAttribute("userRoleId").toString());
		System.out.println(message.getContent());
		message.setCreateTime(new Date());
		UserRoleQuery userRoleQuery =new UserRoleQuery();
		UserRoleQuery userRoleQuery2=null;
		if(message.getType().equals(0)){//货主和司机
			userRoleQuery.setRoleId(1);
			userRoleQuery2 =new UserRoleQuery();
			userRoleQuery2.setRoleId(2);
			message.setType(5);
//			CsclPushUtil.SendMsgToAll(mssage.getContent());
		}else if(message.getType().equals(1)){//货主用户
			message.setType(3);
			userRoleQuery.setRoleId(1);
//			CsclPushUtil.SendPushToAll("货主", mssage.getContent());
		}else if(message.getType().equals(2)){//车主用户
			message.setType(4);
			userRoleQuery.setRoleId(2);
//			CsclPushUtil.SendPushToAll("司机", mssage.getContent());
		}
		this.messageDao.save(message);
//		sbaseDao.save(message);
		System.out.print(message.getId().toString());
		messageSendDao.insertByuserrole(message.getId());
		return writeAjaxResponse(response, "success");
//		List<CL_UserRole> ls = userRoleDao.find(userRoleQuery);
//		if(userRoleQuery2!=null)
//		{
//			List<CL_UserRole> ls2=userRoleDao.find(userRoleQuery2);
//			for (CL_UserRole cl_UserRole : ls2) {
//				ls.add(cl_UserRole);
//			}
//		}
//		System.out.println(ls.size());
//		for(CL_UserRole userrole:ls){
//			CL_MessageSend send=new CL_MessageSend();
//			send.setFmessageid(this.messageDao.getMsgId());
//			send.setFreceiverid(userrole.getId());
//			send.setFisread(0);
//			this.messageSendDao.save(send);
//		}
//		return writeAjaxResponse(response, "success");
	}

	/*** 删除消息*/
	@RequestMapping("/messagesend/delete")
	@Transactional(propagation = Propagation.REQUIRED)
	public String delete(HttpServletRequest request,HttpServletResponse response,Integer[] ids) throws Exception{
		this.messageDao.deleteById(ids);
		this.messageSendDao.deleteByMsgId(ids);
		return writeAjaxResponse(response, "success");
	}
	
}
