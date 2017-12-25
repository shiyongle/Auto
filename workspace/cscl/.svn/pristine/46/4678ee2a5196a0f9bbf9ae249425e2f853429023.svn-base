package com.pc.util;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.message.ImessageDao;
import com.pc.dao.umeng.IUMengPushDao;
import com.pc.model.CL_Umeng_Push;
import com.pc.util.push.Demo;

@Controller
public class JFYTest extends BaseController {
	@Resource
	private IFinanceStatementDao finDao; // 测试Dao

	@Resource
	private IUMengPushDao iumeng;

	@Resource
	private ImessageDao messageDao;
	/*** 测试方法 */
	@RequestMapping("/test")
	public String JFYTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String testcode = request.getParameter("testcode");

		if (String_Custom.noNull(testcode))
			return this.poClient(response, false, "测试的code都不传 什么意思 什么意思");

		if (String_Custom.noNumber(testcode))
			return this.poClient(response, false, "coee是数值型的哦");

		List<CL_Umeng_Push> umengList = iumeng.getUserUmengRegistration(Integer.parseInt(testcode));
		if (umengList .size() > 0) {
			// 获取到用户多台设备
			for (CL_Umeng_Push cl_Umeng_Push : umengList) {
				Demo.SendUmeng(cl_Umeng_Push.getFdevice(), "一路好运推送测试123!", cl_Umeng_Push.getFdeviceType());
			}
			messageDao.save(Demo.savePushMessage("一路好运推送测试!",Integer.parseInt(testcode),umengList.get(0).getFuserType()));
		}
		return this.poClient(response, true);
	}
}
