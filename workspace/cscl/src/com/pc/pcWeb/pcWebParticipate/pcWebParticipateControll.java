package com.pc.pcWeb.pcWebParticipate;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.participate.impl.ParticipateDao;
import com.pc.model.CL_Participate;
import com.pc.query.participate.ParticipateQuery;
import com.pc.util.JSONUtil;

@Controller
public class pcWebParticipateControll extends BaseController {
     @Resource
	private ParticipateDao participateDao;
     
    private ParticipateQuery participateQuery;
    
     public ParticipateQuery getParticipateQuery() {
		return participateQuery;
	}

	public void setParticipateQuery(ParticipateQuery participateQuery) {
		this.participateQuery = participateQuery;
	}

	//添加--司机加盟信息提交申请
	@RequestMapping("/pcWeb/participate/saveParticipate")
	public String saveParticipate(HttpServletRequest request,HttpServletResponse response)throws IOException{
		HashMap<String, Object> map=new HashMap<>();
		int ftype;
		String fname,ftel,fphone,faddress;
	    if(request.getParameter("ftype")==null || "".equals(request.getParameter("ftype"))){
			map.put("success", "false");
			map.put("msg","请选择司机性质！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			ftype = Integer.parseInt(request.getParameter("ftype"));
		}
	    if(request.getParameter("fname")==null || "".equals(request.getParameter("fname"))){
			map.put("success", "false");
			map.put("msg","请输入申请人姓名！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			fname = request.getParameter("fname");
		}
	    if(request.getParameter("ftel")==null || "".equals(request.getParameter("ftel"))){
			map.put("success", "false");
			map.put("msg","请输入联系电话！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			ftel = request.getParameter("ftel");
			
		}
	    if(request.getParameter("faddress")==null || "".equals(request.getParameter("faddress"))){
			map.put("success", "false");
			map.put("msg","请输入联系地址！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			faddress = request.getParameter("faddress");
		}
	    fphone =request.getParameter("fphone");
	    CL_Participate participate=new CL_Participate();
		participate.setFtype(ftype);
		participate.setFname(fname);
		participate.setFtel(ftel);
		participate.setFphone(fphone);
		participate.setFaddress(faddress);
		participateDao.save(participate);
		map.put("success", "true");
		map.put("msg","保存成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
/*	//查询司机加盟所有信息
	@RequestMapping("/pcWeb/participate/findALLParticipate")
	public String findALLParticipate(HttpServletRequest request,HttpServletResponse response)throws IOException{
		List<CL_Participate> list=participateDao.find(participateQuery);
		HashMap<String, Object> map=new HashMap<String, Object>();
		for (CL_Participate cl_Participate : list) {
			map.put("success", "true");
			map.put("data", list);
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}*/
	
	/*//通过ID删除司机加盟信息
	@RequestMapping("/pcWeb/participate/deleteParticipate")
	public String deleteParticipate(HttpServletRequest request,HttpServletResponse response)throws IOException{
		HashMap<String, Object> map=new HashMap<>();
		map.put("success", "true");
		map.put("msg", "删除成功！");
		participateDao.deleteById(participateQuery.getFid());
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}*/
}
	
