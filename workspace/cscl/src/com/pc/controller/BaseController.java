package com.pc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.org.rapid_framework.page.PageRequest;

import com.pc.util.JSONUtil;
import com.pc.util.PageRequestFactory;
import com.pc.util.ServerContext;

public class BaseController {
	
	public <T extends PageRequest> T newQuery(Class<T> queryClazz,String defaultSortColumns){
		PageRequest query = PageRequestFactory.bindPageRequest(org.springframework.beans.BeanUtils.instantiateClass(queryClazz),((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(),defaultSortColumns);
		return (T)query;
    }
	
	@SuppressWarnings("finally")
	protected String writeAjaxResponse(HttpServletResponse response ,String ajaxString){
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
	        out.write(ajaxString);
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			return null;
		}
	}
	/**
	 * 返回数据给前端   操作类
	 * @param response  响应返回 
	 * @param isStatus  请求状态 
	 */
	protected String poClient( HttpServletResponse response,Boolean isStatus)
    {
        return this.poClient(response, isStatus, null);
    }
	/**
	 * 返回数据给前端  
	 * @param response 响应返回
	 * @param isStatus  请求状态
	 * @param chatString  响应给前端数据
	 */
    protected String poClient( HttpServletResponse response,Boolean isStatus,String chatString) 
    {
        return this.poClient(response, isStatus, chatString, -1);
    }
    protected String poClient( HttpServletResponse response,Boolean isStatus,String chatString,int total) 
    {
        try {
        	String resopnString="";
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			if(chatString==null || chatString.length()==0)
	        {
				resopnString="{\"success\":\"" + isStatus + "\"}";
	        	
	        }else
	        {
	            if(isStatus)
	            {
	            	if(total!=-1)
	            		resopnString="{\"success\":\"" + isStatus + "\",\"data\":" + chatString+",\"total\":\""+total+"\"}";
	            	else
	            		resopnString="{\"success\":\"" + isStatus + "\",\"data\":" + chatString+"}";
	            }else
	            {
	            	resopnString="{\"success\":\"" + isStatus  + "\",\"msg\":\"" + chatString + "\"}";
	            }
	        }
	        System.out.println(resopnString);
	        out.write(resopnString);
			  out.close();
		} catch (IOException e) {
			System.out.println("传入参数异常");
			e.printStackTrace();
		}
        return null;
    }

	protected String poClient( Boolean isStatus,String chatString) {
		return this.poClient(isStatus, chatString, 0);
	}
	/**
	 * 取消对HttpServletResponese的依赖，结果直接返回
	 * @param isStatus
	 * @param chatString
	 * @param total
	 * @return
	 */
	protected String poClient(Boolean isStatus,String chatString,int total) {
		String resopnString="";
		if(chatString==null) {
			resopnString="{\"success\":\"" + isStatus + "\"}";
		} else {
			if(isStatus) {
				if(total!=0)
					resopnString="{\"success\":\"" + isStatus + "\",\"data\":" + chatString+",\"total\":\""+total+"\"}";
				else
					resopnString="{\"success\":\"" + isStatus + "\",\"data\":" + chatString+"}";
			} else {
				resopnString="{\"success\":\"" + isStatus  + "\",\"msg\":\"" + chatString + "\"}";
			}
		}
		Logger.getLogger(getClass().getName()).log(Level.ALL, resopnString);
		return resopnString;
	}
	
	 /****   判断抽取类 
	 *  str 错误返回信息
	 *  toStr 前端获取信息 
	 *  T t 传入初始化的 定义变量
	 * @return */
	@SuppressWarnings("unchecked")
	protected  <T> T getRequestParameter(HttpServletRequest request,HttpServletResponse response,HashMap<String, Object> map,String str,String toStr,T t){
        if(request.getParameter(toStr)==null || "".equals(request.getParameter(toStr))){
 	    	map.put("success", "false");
			map.put("msg",str);
			return (T) writeAjaxResponse(response, JSONUtil.getJson(map));
       } 
         if(t instanceof Integer){
     		t=(T) Integer.valueOf(request.getParameter(toStr));
     		return t;
        }
         if(t instanceof String){
         	t=(T) request.getParameter(toStr);
    		return t;
        }
        return t;
      }
}