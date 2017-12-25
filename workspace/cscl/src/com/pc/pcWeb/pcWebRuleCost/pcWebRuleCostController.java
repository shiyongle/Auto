package com.pc.pcWeb.pcWebRuleCost;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.print.attribute.standard.JobHoldUntil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.pc.controller.BaseController;
import com.pc.dao.rule.IRuleDao;
import com.pc.model.CL_Rule;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;
@Controller
public class pcWebRuleCostController extends BaseController {
     
	@Resource
	private IRuleDao ruleDao;
	
	
	
 
}
