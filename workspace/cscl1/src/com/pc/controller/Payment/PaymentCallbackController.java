package com.pc.controller.Payment;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pc.controller.BaseController;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.util.pay.OrderMsg;


@Controller
public class PaymentCallbackController extends BaseController{
	
	private Logger log = Logger.getLogger(getClass().getName());

	@Resource
	private IFinanceStatementDao financeStatementDao;
	
	@RequestMapping("/paymentCallback")
	@ResponseBody
	public String paymentCallback(@ModelAttribute OrderMsg ordermsg) throws IOException {
		log.info(""+ordermsg);
		HashMap<String, Object> param = new HashMap<String, Object>();
		int success= financeStatementDao.updateBusinessType(ordermsg,param);
		/*
		 * 里面方法已经将0的记录到cl_paylog里，为了节省资源暂不重新接收回调;
		 * if(success==0){
			return "failure";
		}*/
		return "success";
	}
}
