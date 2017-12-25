package Com.Controller.other;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinamobile.openmas.client.Sms;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.mySimpleUtil.MsgSendHelper;
import Com.Base.Util.mySimpleUtil.MyForeignKeyUntil;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Base.Util.mySimpleUtil.RandomValidateCode;
import Com.Base.Util.mySimpleUtil.ValidateCode;
import Com.Base.Util.mySimpleUtil.lvcsmsrel.LVCSMSFacade;
import Com.Base.data.vo.RemoteCfg;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.IRegisterAuthenticationDao;
import Com.Dao.System.ISimplemessagecfgDao;
import Com.Dao.System.ISupCustomerInfoDao;
import Com.Entity.System.Useronline;

/**
 * 通用的控制器类，一般放通用方法
 * 
 * 
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2015-1-7 下午4:27:52
 */
@Controller
public class CommonController {
	Logger log = LoggerFactory.getLogger(CommonController.class);

	@Resource
	private ISimplemessagecfgDao simplemessagecfgDao;
	@Resource
	private IBaseSysDao baseSysDao;
	@Resource
	private IRegisterAuthenticationDao registerAuthenticationDao;
	/**
	 * 获取批量黏贴的隐藏域
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2015-1-7 上午8:25:31 (ZJZ)
	 */
	@RequestMapping(value = "/gainValuesByOtherFieldRemoteCfgs")
	public String gainCommons(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {

			String conditions = request.getParameter("remoteCfgss");

			JSONArray jsa = JSONArray.fromObject(conditions);

			List<List<Map<String, Object>>> data = new ArrayList<>();

			for (int i = 0; i < jsa.size(); i++) {

				List<RemoteCfg> remoteCfgs = JsonUtil.jsTolist(
						jsa.getString(i), RemoteCfg.class.getName());

				List<Map<String, Object>> fkeys = new ArrayList<>();

				for (RemoteCfg remoteCfg : remoteCfgs) {

					Object fkey = MyForeignKeyUntil.gainSingleFid(
							simplemessagecfgDao, remoteCfg.getBeanName(),
							new String[] { remoteCfg.getSourceFieldInBean(),
									remoteCfg.getSourceFieldValue() },
							remoteCfg.getTip(), remoteCfg.getGoalField());

					Map<String, Object> mapt = new HashMap<String, Object>();

					mapt.put("dataIndex", remoteCfg.getGoalDataIndex());
					mapt.put("value", fkey);

					fkeys.add(mapt);
				}

				data.add(fkeys);

			}

			JSONArray resultData = JSONArray.fromObject(data);

			reponse.getWriter().write(
					JsonUtil.result(true, "", "", resultData.toString()));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	/**
	 * 
	 * 目前用户是管理员
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2015-1-17 上午9:29:21 (ZJZ)
	 */
	@RequestMapping(value = "/isAdminCurrentUser")
	public String isAdminCurrentUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {

			boolean isAdmin = MySimpleToolsZ.getMySimpleToolsZ().isAdmin(
					request);

			JSONObject jso = new JSONObject();

			jso.put("isAdmin", isAdmin);

			reponse.getWriter().write(
					JsonUtil.result(true, "", "", jso.toString()));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	/**
	 * 查看验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/hasValidateCode")
	public String hasValidateCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ValidateCode validateCode = (ValidateCode) request.getSession().getAttribute(ValidateCode.RANDOMCODEKEY);
		boolean hasCode = false;
		if(validateCode != null && validateCode.getCode() != null){
			hasCode = true;
		}
		response.getWriter().write(JsonUtil.result(hasCode, "", "",""));
		return null;
	}
	/**
	 * 获取验证码图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * 
	 */
	@RequestMapping(value = "/getValidateCode")
	public String getValidateCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
		try {
			ValidateCode validateCode = (ValidateCode) request.getSession().getAttribute(ValidateCode.RANDOMCODEKEY);
			if(validateCode == null || validateCode.getCode() == null){
				throw new DJException("验证码不存在！");
			}
			validateCode.getRandomCode(response);
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	/**
	 * 验证验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * 
	 * @date 2015-3-3 下午3:19:33 (ZJZ)
	 */
	@RequestMapping(value = "/validateVCode")
	public String validateVCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String vCode = request.getParameter("vCode");

		HttpSession session = request.getSession();

		RandomValidateCode randomValidateCode = RandomValidateCode.gainRandomValidateCode(session);

		try {

			if (randomValidateCode.isRightVC(vCode)) {

				response.getWriter().write(
						JsonUtil.result(true, "", "", "{isValidate:true}"));

			} else {

				response.getWriter().write(
						JsonUtil.result(true, "", "","{isValidate:false}"));

			}

		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
	 * 获取校验码状态
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 *
	 * @date 2015-3-21 上午8:59:15  (ZJZ)
	 */
	@RequestMapping(value = "/gainValidateVCodeState")
	public String gainValidateVCodeState(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();

		RandomValidateCode randomValidateCode = RandomValidateCode.gainRandomValidateCode(session);

		try {

			if (randomValidateCode.isVerified()) {

				response.getWriter().write(
						JsonUtil.result(true, "", "", "{isValidate:true}"));

			} else {

				response.getWriter().write(
						JsonUtil.result(true, "", "","{isValidate:false}"));

			}

		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	/**
	 * 获取一次性登录码
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 *
	 * @date 2015-5-7 下午3:29:31  (ZJZ)
	 */
	@RequestMapping(value = "/gainLoginValidateVCode")
	public String gainLoginValidateVCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(simplemessagecfgDao);
		
		

		try {

			lVCSMSFacade.doGainLVCAction(request.getParameter("name"));
			response.getWriter().write(
					JsonUtil.result(true, "", "", ""));
			
			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
	 * 注册时获取一次性验证码 （手机注册）
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/gainLoginValidateVCodeTel")
	public String gainLoginValidateVCodeTel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(simplemessagecfgDao);
		
		try {
			String name=request.getParameter("name");
			if(StringUtils.isEmpty(name))
			{
				throw new DJException("请输入手机号");
			}
			if(!"-1".equals(MySimpleToolsZ.isTheRightUserNamePhone(name,baseSysDao)))
			{
				throw new DJException("该手机账号已被注册！");
			}
			if(!"-1".equals(MySimpleToolsZ.isTheRightCustomer(name,baseSysDao)))
			{
				throw new DJException("该手机信息已被使用！请联系平台处理！（联系电话：0577-85391777)");
			}
			lVCSMSFacade.sendLVCToGoalPhone(name,name);//发送随机码
			response.getWriter().write(
					JsonUtil.result(true, "", "", ""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	

	
	
	

	/**
	 * 是正确的用户
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 *
	 * @date 2015-5-7 下午4:20:36  (ZJZ)
	 */
	@RequestMapping(value = "/isTheRightUserName")
	public String isTheRightUserName(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(simplemessagecfgDao);
		
		
		try {
			
			if (lVCSMSFacade.isTheLegalUserName(request.getParameter("name"))){
				
				response.getWriter().write(
						JsonUtil.result(true, "", "", ""));
				
			}
			
			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
	 * 改变密码
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 *
	 * @date 2015-5-8 下午2:46:24  (ZJZ)
	 */
	@RequestMapping(value = "/changePWByLVC")
	public String changePWByLVC(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(simplemessagecfgDao);
		
		
		try {

			String pw = request.getParameter("password");
			String name = request.getParameter("name");
			String lvc = request.getParameter("lvc");

			if(lVCSMSFacade.changeThePassWord(lvc, name, pw)){
				
				response.getWriter().write(JsonUtil.result(true, "修改成功，请使用新密码登录", "", ""));
				
			}else {
				
				response.getWriter().write(JsonUtil.result(false, "校验码错误", "", ""));
				
			}

			

		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	/**
	 * android log 文件上传
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws FileUploadException
	 *
	 * @date 2015-5-13 下午3:46:55  (ZJZ)
	 */
	@RequestMapping(value = "/uploadAndroidVmiLog")
	public String uploadAndroidVmiLog(HttpServletRequest request,
			HttpServletResponse response) throws IOException, FileUploadException {

		
		SimpleDateFormat sFormat = new SimpleDateFormat();

		sFormat.applyPattern("yyMMddHHmmssZ");
		
		String fileName = sFormat.format(new Date());
		
		String relativePath = "log/androidLog";
		
		final String[] supportedSuffix = new String []{"log", "txt"};
		
		try {

			MySimpleToolsZ.saveFile(request, fileName, relativePath,  new MySimpleToolsZ.FileRelActions() {
				
				@Override
				public boolean dealWithFileSuffix(String suffix) {
					// TODO Auto-generated method stub
					
					for (String suffixNow : supportedSuffix) {
						
						
						if(suffix.contains(suffixNow)) {
							
							
							
							return true;
						}
						
						
					}
					
					throw new DJException("文件后缀不符合");
					
//					return false;
				}
				
				
			});
			
			
			response.getWriter().write(
					JsonUtil.result(true,"", "", ""));
			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	

	/**
	 * 
	 * 短信发送接口
	 * 
	 * msg，string
	 * phones，json array
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws FileUploadException
	 *
	 * @date 2015-5-19 上午8:28:08  (ZJZ)
	 */
	@RequestMapping(value = "/sendMsg")
	public String sendMsg(HttpServletRequest request,
			HttpServletResponse response) throws IOException, FileUploadException {

		String msg = request.getParameter("msg");
		String phones = request.getParameter("phones");
		
		String[] phonesA = (String[]) JSONArray.toArray(JSONArray.fromObject(phones)) ;
		
		
		try {

			MsgSendHelper.sendMsg(msg, phonesA);
			
			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	/**
	 * 提交注册
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/submitRegister")
	public String submitRegister(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(simplemessagecfgDao);
		try {

			String name = request.getParameter("name");
			String lvc = request.getParameter("lvc");
			if(!"-1".equals(MySimpleToolsZ.isTheRightUserNamePhone(name,baseSysDao)))
			{
				throw new DJException("该手机账号已被注册！");
			}
			if(!"-1".equals(MySimpleToolsZ.isTheRightCustomer(name,baseSysDao)))
			{
				throw new DJException("该手机信息已被使用！请联系平台处理！（联系电话：0577-85391777)");
			}
			if(lVCSMSFacade.loginByLVC(name,lvc)){
				//生成用户与客户资料
				registerAuthenticationDao.saveRegisterUserAndCustomerInfo(request);
				response.getWriter().write(JsonUtil.result(true, "注册成功", "", ""));
				
			}else {
				
				response.getWriter().write(JsonUtil.result(false, "校验码错误", "", ""));
				
			}

			

		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	
	/**
	 * 根据手机号获取一次性验证码 （用于APP）
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/gainValidateVCodeByTel")
	public String gainValidateVCodeByTel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(simplemessagecfgDao);
		
		try {
			String name=request.getParameter("name");
			if(StringUtils.isEmpty(name))
			{
				throw new DJException("请输入手机号");
			}
			
			lVCSMSFacade.sendLVCToGoalPhone(name,name);//发送随机码
			response.getWriter().write(
					JsonUtil.result(true, "", "", ""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	/**
	 * 绑定手机号 (替换手机号 APP用)
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 *
	 */
	@RequestMapping(value = "/changeFtelByLVC")
	public String changeFtelByLVC(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(simplemessagecfgDao);
		
		
		try {
			String name = request.getParameter("name");
			String lvc = request.getParameter("lvc");
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			if(MySimpleToolsZ.isTheRightUserNamePhoneEmailByfid(name,userid,baseSysDao))
			{
				throw new DJException("该手机账号已被使用！");
			}
			if(lVCSMSFacade.loginByLVC(name,lvc)){
				
				String sql = String
						.format(" UPDATE `t_sys_user` SET `ftel`='%s' WHERE `fid`='%s'  ",
								name, userid);
				registerAuthenticationDao.ExecBySql(sql);
				response.getWriter().write(JsonUtil.result(true, "绑定成功", "", ""));
				
			}else {
				
				response.getWriter().write(JsonUtil.result(false, "校验码错误", "", ""));
				
			}

			

		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	
}