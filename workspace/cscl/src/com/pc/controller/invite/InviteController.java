package com.pc.controller.invite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pc.appInterface.api.DongjingClient;
import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.model.CL_Invite;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class InviteController extends BaseController {


	@Resource
	private UserRoleDao userRoleDao;

	protected static final String LIST_JSP= "/pages/pc/invite/list.jsp";

	@RequestMapping("/invite/list")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return LIST_JSP;
	}

	@RequestMapping("/invite/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_Invite ivt) throws Exception{
		Date starttime=ivt.getCreateTimeBegin();
		Date endtime=ivt.getCreateTimeEnd();
		Integer isReward=ivt.getIsReward();//否1是2
		Integer fuserid=ivt.getFuserroleid();
//		List<CL_Invite> list=new ArrayList<CL_Invite>();
		DongjingClient a =ServerContext.createVmiClient();
		a.setMethod("getInviteeList");
		a.setRequestProperty("starttime", starttime==null?"":starttime.toString());
		a.setRequestProperty("endtime", endtime==null?"":endtime.toString());
		a.setRequestProperty("isReward", isReward.toString());
		a.setRequestProperty("fuserid", fuserid.toString());
		a.SubmitData();
		JSONObject jbsc =net.sf.json.JSONObject.fromObject(a.getResponse().getResultString());
		JSONArray array = new JSONArray();
		if(jbsc.get("invitelist")!=null)
		{
			array=JSONArray.fromObject(jbsc.get("invitelist"));
		}	
		return writeAjaxResponse(reponse,JSONUtil.getJson(array));
	}

}
