package com.pc.pcWeb.pcWebOrder;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.FileUploadException;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.org.rapid_framework.page.Page;

import com.pc.appInterface.api.DongjingClient;
import com.pc.controller.BaseController;
import com.pc.dao.Car.ICarDao;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.carRanking.IcarRankingDao;
import com.pc.dao.clUpload.IuploadDao;
import com.pc.dao.identification.impl.IdentificationDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.dao.orderCarDetail.IorderCarDetailDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.dao.protocol.IprotocolDao;
import com.pc.dao.refuse.IrefuseDao;
import com.pc.dao.rule.IRuleDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.model.CL_Car;
import com.pc.model.CL_CarRanking;
import com.pc.model.CL_Feedback;
import com.pc.model.CL_Identification;
import com.pc.model.CL_Order;
import com.pc.model.CL_OrderCarDetail;
import com.pc.model.CL_OrderDetail;
import com.pc.model.CL_Protocol;
import com.pc.model.CL_Refuse;
import com.pc.model.CL_Rule;
import com.pc.model.CL_UserRole;
import com.pc.model.Cl_Upload;
import com.pc.model.Util_Option;
import com.pc.model.Util_UserOnline;
import com.pc.query.order.OrderQuery;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
import com.pc.util.RedisUtil;
import com.pc.util.ServerContext;
import com.pc.util.file.FastDFSUtil;

@Controller
public class pcWebOrderControll extends BaseController {
	@Resource
	private IUtilOptionDao optionDao;
	@Resource
	private IRuleDao ruleDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private IorderDetailDao orderDetailDao;
	@Resource
	private IUserRoleDao userRoleDao;
	@Resource
	private IorderCarDetailDao orderCarDetailDao;
	@Resource
	private ICarDao cardao;
	@Resource
	private IprotocolDao protocolDao;
	@Resource
	private IuploadDao iuploadDao;
	@Resource
	private IaddtoDao iaddtoDao;
	@Resource
	private IdentificationDao identificationDao;
	@Resource
	private IcarRankingDao icarRankingDao;
	@Resource
	private IrefuseDao refuseDao;

	private OrderQuery orderQuery;
	public OrderQuery getOrderQuery() {
		return orderQuery;
	}
	public void setOrderQuery(OrderQuery orderQuery) {
		this.orderQuery = orderQuery;
	}
	
	protected static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl5nbvmL8Q8tYGcJAgwS4qdqp2" +
			"Rwme5FKaR+11vXy89Biu8ruF/KmdS4pk+4gEmoPuHLFc6V6VQ77CgtpgboBDjveU" +
			"n3HnsN1N2LH/hmn8gDvw+0e7lLDFVEGC6L8d9z+yj0zGe0XMDeEW5zJlVCA2FOYq" +
			"oQAOkIntynv/nfyP6wIDAQAB";

	protected static final String PERSONALPUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcu9tqg+XyDB7qygFxn4UQu00T" +
			"WrnbQmIDUnE8zhDf9ZuCr5Czil1XVR4ApnpcVUTTXHoW2WpzBw1gm53OdKjgPc2Q" +
			"yRq+ROlo7NhYCRFan+b4p+RqGL+U+alMH1zv1Q+LSgQP6QF9loKAsC3i70KdBw4G" +
			"n7K8fTzoY8PtMqHlSwIDAQAB";

	/***获取所有车厢（所有车辆规格）*/
	@RequestMapping("/pcWeb/order/loadSpec")
	public String loadSpec(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<Util_Option> list = optionDao.getAllCarType();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("data", list);
		System.out.println(m.get("data"));
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/***根据车厢规格id获得车辆类型*/
	@RequestMapping("/pcWeb/order/loadCarType")
	public String loadCarType(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer optionId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(request.getParameter("optionId")==null || "".equals(request.getParameter("optionId"))){
			map.put("success", "false");
			map.put("msg","请先登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			optionId = Integer.parseInt(request.getParameter("optionId"));
		}
		List<Util_Option> list = optionDao.getAllCarSpecByCarType(optionId);
		map.put("success", "true");
		map.put("data", list);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/***根据车厢规格id获得其他要求*/
	@RequestMapping("/pcWeb/order/CarOther")
	public String CarOther(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer optionId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(request.getParameter("optionId")==null || "".equals(request.getParameter("optionId"))){
			map.put("success", "false");
			map.put("msg","请先登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			optionId = Integer.parseInt(request.getParameter("optionId"));
		}
		List<Util_Option> list = optionDao.getOtherByCarSpec(optionId);
		map.put("success", "true");
		map.put("data", list);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/***获取所有货物类型*/
	@RequestMapping("/pcWeb/order/loadGoods")
	public String loadGoods(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<Util_Option> list = optionDao.getAllGoodsType();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("data", list);
		System.out.println(m.get("data"));
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	 
    /**获取协议运费*/
	@RequestMapping("/pcWeb/order/proRuleCost")
	  public String proRuleCost (HttpServletRequest request,HttpServletResponse response) throws IOException{
			HashMap<String, Object> map=new HashMap<>();
			Integer userroleId,orderType,fopint,specId,protocolId;
			BigDecimal mileage=new BigDecimal(0);
			BigDecimal vod=new BigDecimal(0);
			BigDecimal weh=new BigDecimal(0);
			BigDecimal vodtoWeh=new BigDecimal(0);
			BigDecimal freight=new BigDecimal(0);
			BigDecimal famount=new BigDecimal(0);
			if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
		    	map.put("success", "false");
				map.put("msg","未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
		    }
		    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
		    userroleId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		    //判断用户是否欠费
		   /* String phone = userRoleDao.getById(userroleId).getVmiUserPhone();
		    if(phone!=null&&!phone.isEmpty()){
		    	String requestDataString="";
		    	String resString="";
		    	JSONObject jo;
		    	String Developer = RSA.encrypt("CS",PUBLICKEY);
		    	String hzUsenameE = RSA.encrypt(phone,PERSONALPUBLICKEY);
		    	requestDataString = "{\"data\": {\"Developer\": \""+Developer+"\",\"Usename\": \""+hzUsenameE+"\"}}";
		    	requestDataString = Base64.encode(requestDataString.getBytes());
		    	resString = ServerContext.UsePayBalance(requestDataString);
		    	jo = JSONObject.fromObject(resString);
		    	if("true".equals(jo.get("success").toString())){
		    		if(new BigDecimal(jo.get("data").toString()).compareTo(new BigDecimal(0))<0){
		    			map.put("success", "false");
		    			map.put("msg","欠费了哦！是不是上次订单的运费没结清？");
		    			return writeAjaxResponse(response, JSONUtil.getJson(map));
		    		}
		    	}*/
		    	
//		    }
		    if(request.getParameter("orderType")==null || "".equals(request.getParameter("orderType"))){
				map.put("success", "false");
				map.put("msg","无法获知订单类型！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				orderType = Integer.parseInt(request.getParameter("orderType"));
			}
		  	
	    	if(request.getParameter("fpoint")==null || "".equals(request.getParameter("fpoint"))){
				fopint=1;
			}else{
				fopint =Integer.valueOf(request.getParameter("fpoint").toString()) ; 
			}
	    	if(request.getParameter("protocolId")==null || "".equals(request.getParameter("protocolId"))){
				map.put("success", "false");
				map.put("msg","该用户不是协议用户");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				protocolId=Integer.parseInt(request.getParameter("protocolId"));
			}
			CL_Protocol protocol=protocolDao.getById(protocolId);
			if(protocol.getStatus()==0){
				map.put("success", "false");
				map.put("msg","该协议已经失效");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			 if(orderType!=2){
			    	if(request.getParameter("specId")==null || "".equals(request.getParameter("specId"))){
						map.put("success", "false");
						map.put("msg","请选择车厢！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}else{
						specId = Integer.parseInt(request.getParameter("specId"));
					}
		     }
	     if(orderType!=3){
			if((request.getParameter("mileage").toString()!=null&& "".equals(request.getParameter("mileage").toString()))||(request.getParameter("mileage").toString().equals("0"))){
				map.put("success", "false");
				map.put("msg","请输入里程数！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			else{
				mileage=(new BigDecimal(request.getParameter("mileage").toString()).setScale(1,BigDecimal.ROUND_HALF_UP));//里程
			}
	     }
	    	//============================================计算运费=============开始=====================================//
		     //整车计算运费
	    	//		============================================协议计算运费=============开始=====================================//
		//逻辑整改，PC端谁特么理你	
	     /*if(orderType==1){//整车计算运费
				if(protocol!=null){
					//实际运输距离大于规则起步公里数:运费=(规则起步价+(实际距离-规则起步公里数)×规则公里单价)
					if(mileage.compareTo(protocol.getStartKilometre())==1){
						BigDecimal km = mileage.subtract(protocol.getStartKilometre());//减法
						BigDecimal fee1 =km.multiply(protocol.getOutKilometre());//乘法
						BigDecimal opintprice=new BigDecimal(0);
						if(fopint>protocol.getFopint()){
							Integer foutopint=fopint-protocol.getFopint();
							opintprice=protocol.getFoutopint().multiply(new BigDecimal(foutopint));
						}
						if(protocol.getFdiscount()!=null){
							BigDecimal dis=protocol.getFdiscount();//折扣
							BigDecimal fre= protocol.getStartPrice().add(fee1).add(opintprice); //协议折扣运费
							if(request.getParameter("fincrementServe").indexOf("装卸") != -1){
								freight=fre.multiply(dis).add(protocol.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP);
							} else {
							freight=fre.multiply(dis).setScale(1, BigDecimal.ROUND_HALF_UP);
							}
						}
						else{
							if(request.getParameter("fincrementServe").indexOf("装卸") != -1){
								freight=protocol.getStartPrice().add(fee1).add(opintprice).add(protocol.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP);//加法 无折扣情况下的运费
							} else{
							freight=protocol.getStartPrice().add(fee1).add(opintprice).setScale(1, BigDecimal.ROUND_HALF_UP);//加法 无折扣情况下的运费
							}
						}
						//实际运输距离小于/等于规则起步公里数:运费=规则起步价
					}else if(mileage.compareTo(protocol.getStartKilometre())==-1 || mileage.compareTo(protocol.getStartKilometre())==0){
						if(protocol.getFdiscount()!=null){
							BigDecimal dis=protocol.getFdiscount();
							BigDecimal opintprice=new BigDecimal(0);
							if(fopint>protocol.getFopint()){
								Integer foutopint=fopint-protocol.getFopint();
								opintprice=protocol.getFoutopint().multiply(new BigDecimal(foutopint));
							}
							BigDecimal fre=opintprice.add(protocol.getStartPrice());
							if(request.getParameter("fincrementServe").indexOf("装卸") != -1){
								freight=fre.multiply(dis).add(protocol.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP);
							} else{
								freight=fre.multiply(dis).setScale(1, BigDecimal.ROUND_HALF_UP);
							}
						}else{
							BigDecimal opintprice=new BigDecimal(0);
							if(fopint>protocol.getFopint()){
								Integer foutopint=fopint-protocol.getFopint();
								opintprice=protocol.getFoutopint().multiply(new BigDecimal(foutopint));
							}
							if(request.getParameter("fincrementServe").indexOf("装卸") != -1){
								freight=protocol.getStartPrice().add(opintprice).add(protocol.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP);
							} else {
							freight=protocol.getStartPrice().add(opintprice).setScale(1, BigDecimal.ROUND_HALF_UP);
							}
						}
					}
				}else{
					map.put("success", "false");
					map.put("msg","所选车厢,规则遗失,请联系客服");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}else if(orderType==2){//零担计算运费
				if(request.getParameter("famount").toString()!=null&& "".equals(request.getParameter("famount").toString())){
					map.put("success", "false");
					map.put("msg","请输入零担数量！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				else{
					famount=(new BigDecimal(request.getParameter("famount").toString()).setScale(1,BigDecimal.ROUND_HALF_UP));//里程
				}
				 
				if(protocol!=null){
					BigDecimal opintprice=new BigDecimal(0);
					if(fopint>protocol.getFopint()){
						Integer foutopint=fopint-protocol.getFopint();
						opintprice=protocol.getFoutopint().multiply(new BigDecimal(foutopint));
					}
					BigDecimal price=protocol.getStartPrice() ;
					if(mileage.compareTo(protocol.getStartKilometre())==1){
						BigDecimal km=mileage.subtract(protocol.getStartKilometre());//减法
						BigDecimal feel=km.multiply(protocol.getOutKilometre());//乘法
						price=price.add(feel);
					} 
					if(famount.compareTo(protocol.getStartNumber())==1){
						BigDecimal number=famount.subtract(protocol.getStartNumber());//减法
						BigDecimal feelNumber=number.multiply(protocol.getOutNumprice());//乘法
						price=price.add(feelNumber);
					} 
					if(request.getParameter("fincrementServe").indexOf("装卸") != -1){
						freight=price.add(opintprice).add(protocol.getFadd_service()).setScale(1,BigDecimal.ROUND_HALF_UP);
					} else {
					freight=price.add(opintprice).setScale(1,BigDecimal.ROUND_HALF_UP);
					}
				}else{
					map.put("success", "false");
					map.put("msg","零担规则遗失,请联系客服");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}else if(orderType==3){//包天计费规则
				if(protocol!=null){
					freight=protocol.getTimePrice().setScale(1,BigDecimal.ROUND_HALF_UP);
				}
				else{
					map.put("success", "false");
					map.put("msg","包天规则遗失,请联系客服");
					return writeAjaxResponse(response, JSONUtil.getJson(map)); 
				}
			}*/
			map.put("success", "true");
			map.put("data",freight);
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
	
	
    /**获取运费*/
	@RequestMapping("/pcWeb/order/ruleCost")
	  public String ruleCost (HttpServletRequest request,HttpServletResponse response) throws IOException{
			HashMap<String, Object> map=new HashMap<>();
			Integer userroleId,orderType,fopint,specId;
			BigDecimal mileage=new BigDecimal(0);
			BigDecimal vod=new BigDecimal(0);
			BigDecimal weh=new BigDecimal(0);
			BigDecimal vodtoWeh=new BigDecimal(0);
			BigDecimal freight=new BigDecimal(0);
			BigDecimal fincrementServe = new BigDecimal(0);//增值服务，装卸
			/*if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
		    	map.put("success", "false");
				map.put("msg","未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
		    }
		    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
		    userroleId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		  //判断用户是否欠费
		    String phone = userRoleDao.getById(userroleId).getVmiUserPhone();
		    if(phone!=null&&!phone.isEmpty()){
		    	String requestDataString="";
		    	String resString="";
		    	JSONObject jo;
		    	String Developer = RSA.encrypt("CS",PUBLICKEY);
		    	String hzUsenameE = RSA.encrypt(phone,PERSONALPUBLICKEY);
		    	requestDataString = "{\"data\": {\"Developer\": \""+Developer+"\",\"Usename\": \""+hzUsenameE+"\"}}";
		    	requestDataString = Base64.encode(requestDataString.getBytes());
		    	resString = ServerContext.UsePayBalance(requestDataString);
		    	jo = JSONObject.fromObject(resString);
		    	if("true".equals(jo.get("success").toString())){
		    		if(new BigDecimal(jo.get("data").toString()).compareTo(new BigDecimal(0))<0){
		    			map.put("success", "false");
		    			map.put("msg","欠费了哦！是不是上次订单的运费没结清？");
		    			return writeAjaxResponse(response, JSONUtil.getJson(map));
		    		}
		    	}
		    	
//		    }
		    if(request.getParameter("orderType")==null || "".equals(request.getParameter("orderType"))){
				map.put("success", "false");
				map.put("msg","无法获知订单类型！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				orderType = Integer.parseInt(request.getParameter("orderType"));
			}
		  	
	    	if(request.getParameter("fopint")==null || "".equals(request.getParameter("fopint"))){
				fopint=1;
			}else{
				fopint =Integer.valueOf(request.getParameter("fopint").toString()) ; 
			}
	    	//============================================计算运费=============开始=====================================//
		     //整车计算运费
		    if(orderType==1){
		    	if(request.getParameter("specId")==null || "".equals(request.getParameter("specId"))){
					map.put("success", "false");
					map.put("msg","请选择车厢！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					specId = Integer.parseInt(request.getParameter("specId"));
				}
		    	
		    	CL_Rule ruleZ = ruleDao.getZhengche(specId);
		    	if(request.getParameter("mileage").toString()!=null&& "".equals(request.getParameter("mileage").toString())){
					map.put("success", "false");
					map.put("msg","请输入里程数！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				else{
					mileage=(new BigDecimal(request.getParameter("mileage").toString()).setScale(1,BigDecimal.ROUND_HALF_UP));//里程
				}
		    	CL_Rule rule =this.ruleDao.getZhengche(specId);
		    	//逻辑整改，PC端注释
				if(rule!=null){
					BigDecimal opintprice=new BigDecimal(0);
					Integer foutopint=0;
					if(fopint>rule.getFopint()){
						foutopint=fopint-rule.getFopint();
						opintprice=rule.getOutfopint().multiply(new BigDecimal(foutopint));
					}else
					    opintprice=rule.getOutfopint().multiply(new BigDecimal(foutopint));
					//实际运输距离大于规则起步公里数:运费=(规则起步价+(实际距离-规则起步公里数)×规则公里单价)
					if(mileage.compareTo(rule.getStartKilometre())==1){
						BigDecimal km = mileage.subtract(rule.getStartKilometre());//减法
						BigDecimal fee1 =km.multiply(rule.getKilometrePrice());//乘法
						if(request.getParameter("fincrementServe").indexOf("装卸") != -1){
							freight=rule.getStartPrice().add(fee1).add(opintprice).add(ruleZ.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP);//加法
						} else {
							freight=rule.getStartPrice().add(fee1).add(opintprice).setScale(1, BigDecimal.ROUND_HALF_UP);
						}
						//实际运输距离小于/等于规则起步公里数:运费=规则起步价
					}else if(mileage.compareTo(rule.getStartKilometre())==-1 || mileage.compareTo(rule.getStartKilometre())==0){
						if(request.getParameter("fincrementServe").indexOf("装卸") != -1){
							freight=rule.getStartPrice().add(opintprice).add(ruleZ.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP);
						} else {
							freight=rule.getStartPrice().add(opintprice).setScale(1, BigDecimal.ROUND_HALF_UP);
						}
					}
				}else{
					map.put("success", "false");
					map.put("msg","所选车厢,规则遗失,请联系客服");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
		    }
		    
			 else if(orderType==2){//零担计算运费
				 if(request.getParameter("volume")!=null&&!"".equals(request.getParameter("volume").toString())) {
						vod = new BigDecimal(request.getParameter("volume").toString());//体积
						vodtoWeh=vod.multiply(new BigDecimal(200));
					}
				    if(request.getParameter("weight")!=null&&!"".equals(request.getParameter("weight").toString())){
				    	weh=new BigDecimal(request.getParameter("weight").toString());//重量
				    }
					weh=weh.multiply(new BigDecimal(1000));
			//CL_Rule ruleVol =this.ruleDao.getLingdanForVolume();
			// ruleWeh =this.ruleDao.getLingdanForWeight();
			//	BigDecimal opintprice=new BigDecimal(0);
			//	BigDecimal Wehopintprice=new BigDecimal(0);
			//	Integer foutopint=0;
//				if(fopint>ruleVol.getFopint()){
//					foutopint=fopint-ruleVol.getFopint();
//					opintprice=ruleVol.getOutfopint().multiply(new BigDecimal(foutopint));
//				}
//				if(fopint>ruleWeh.getFopint()){
//					foutopint=fopint-ruleWeh.getFopint();
//					Wehopintprice=ruleWeh.getOutfopint().multiply(new BigDecimal(foutopint));
	//
//				}
				//			 	Integer foutopint=fopint+1-ruleVol.getFopint();
				//				 BigDecimal opintprice=ruleVol.getOutfopint().multiply(new BigDecimal(foutopint));
//				if(ruleVol!=null && ruleWeh!=null){
//					BigDecimal volfee = ruleVol.getStartPrice().add(opintprice).add(order.getVolume().subtract(new BigDecimal("1")).multiply(ruleVol.getOutPrice()));
//					BigDecimal wehfee = ruleWeh.getStartPrice().add(Wehopintprice).add(order.getWeight().subtract(new BigDecimal("1")).multiply(ruleWeh.getOutPrice()));
//					//按体积计算运费大于按重量计算的运费
//					if(order.getVolume().compareTo(new BigDecimal("1"))==-1 && order.getWeight().compareTo(new BigDecimal("1"))==-1){
//						if(ruleVol.getStartPrice().compareTo(ruleWeh.getStartPrice())==1){
//							order.setFreight(ruleVol.getStartPrice().add(opintprice).setScale(1, BigDecimal.ROUND_HALF_UP));
//						}else{
//							order.setFreight(ruleWeh.getStartPrice().add(Wehopintprice).setScale(1, BigDecimal.ROUND_HALF_UP));
//						}
//					}else if(volfee.compareTo(wehfee)==1){
//						order.setCollection(2);
//						order.setFreight(volfee.setScale(1, BigDecimal.ROUND_HALF_UP));
//					}else{
//						order.setCollection(1);
//						order.setFreight(wehfee.setScale(1, BigDecimal.ROUND_HALF_UP));
//					}
//				}else{
//					map.put("success", "false");
//					map.put("msg","零担规则遗失,请联系客服");
//					return writeAjaxResponse(response, JSONUtil.getJson(map));
//				}
				//  2016-6-18 新需求 按重量（公斤）区间来算 价格  BY twr start
				CL_Rule ruleV = ruleDao.getLingdanForVolume();
				CL_Rule ruleW = ruleDao.getLingdanForWeight();
				BigDecimal finalWeight=new BigDecimal(0);
				if(vodtoWeh.compareTo(weh)<=0){
				     finalWeight=weh;
				       //按重量计算 为1
				}else if(vodtoWeh.compareTo(weh)==1){
					 finalWeight=vodtoWeh;
					  //按体积计算  为2
					 
				}
				finalWeight=finalWeight.subtract(new BigDecimal(75));//保底公斤
				if(finalWeight.compareTo(new BigDecimal(0))<=0){
					if(request.getParameter("fincrementServe").indexOf("装卸") != -1){
						freight=new BigDecimal(30).add(ruleW.getFadd_service());
					} else {
						freight=new BigDecimal(30);
					}
				}
				else{
					if(request.getParameter("fincrementServe").indexOf("装卸") != -1){
						 if(finalWeight.compareTo(new BigDecimal(500))<=0){
							 freight=finalWeight.multiply(new BigDecimal("0.4")).add(new BigDecimal(30)).add(ruleW.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP);//0.4 500公斤以下
						  }
						  if(finalWeight.compareTo(new BigDecimal(700))<=0&&finalWeight.compareTo(new BigDecimal(500))==1){
							  freight=finalWeight.multiply(new BigDecimal("0.36")).add(new BigDecimal(30)).add(ruleW.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP);//0.4*0.9 500-700公斤
						  }
						  if(finalWeight.compareTo(new BigDecimal(1000))<=0&&finalWeight.compareTo(new BigDecimal(700))==1){
							  freight=finalWeight.multiply(new BigDecimal("0.32")).add(new BigDecimal(30)).add(ruleW.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP);//0.4*0.8 700-900公斤
						  }
						  if(finalWeight.compareTo(new BigDecimal(1000))==1){
							  freight=finalWeight.multiply(new BigDecimal("0.28")).add(new BigDecimal(30)).add(ruleW.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP);//0.4*0.7 1000公斤以上
						  }
					} else {
					  if(finalWeight.compareTo(new BigDecimal(500))<=0){
						 freight=finalWeight.multiply(new BigDecimal("0.4")).add(new BigDecimal(30)).setScale(1, BigDecimal.ROUND_HALF_UP);//0.4 500公斤以下
					  }
					  if(finalWeight.compareTo(new BigDecimal(700))<=0&&finalWeight.compareTo(new BigDecimal(500))==1){
						  freight=finalWeight.multiply(new BigDecimal("0.36")).add(new BigDecimal(30)).setScale(1, BigDecimal.ROUND_HALF_UP);//0.4*0.9 500-700公斤
					  }
					  if(finalWeight.compareTo(new BigDecimal(1000))<=0&&finalWeight.compareTo(new BigDecimal(700))==1){
						  freight=finalWeight.multiply(new BigDecimal("0.32")).add(new BigDecimal(30)).setScale(1, BigDecimal.ROUND_HALF_UP);//0.4*0.8 700-900公斤
					  }
					  if(finalWeight.compareTo(new BigDecimal(1000))==1){
						  freight=finalWeight.multiply(new BigDecimal("0.28")).add(new BigDecimal(30)).setScale(1, BigDecimal.ROUND_HALF_UP);//0.4*0.7 1000公斤以上
					  }
					}
				}
			}*/
			map.put("success", "true");
			map.put("data",freight);
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
	
	
	/***新增协议订单*/
	@RequestMapping("/pcWeb/order/saveProtocolOrder")
	@Transactional
	public   String saveProtocolOrder(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userroleId,orderType,specId= null,protocolId=null,fopint=1 ,funitId=null,freceiptSave=0,userId;
		String orderString,addressDeliverString,addressReceiptString=null,carTypeId=null,otherId=null,fremark=null;
		HashMap<String, Object> map = new HashMap<String, Object>();
	     net.sf.json.JSONObject address=new JSONObject(); 	
	     net.sf.json.JSONArray addressDel=new JSONArray(), addressRec=new JSONArray();
	     
	     if(request.getSession().getId()==null || "".equals(request.getSession().getId()) || !ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	 map.put("success", "false");
				map.put("msg","未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
	     }
			
	    Util_UserOnline userInfo=ServerContext.getUseronline().get(request.getSession().getId().toString());
	    userroleId =userInfo.getFuserId();
	    
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		// 停止接单开始
		/*SimpleDateFormat formatTemp = new SimpleDateFormat("yyyy-MM-dd");
		CL_Refuse refuse = refuseDao.getById(1);
		Date startTime = refuse.getFstart_time();// 停止接单开始时间
		Date endTime = refuse.getFend_time();// 停止接单结束时间
		if (startTime != null) {
			String start = formatTemp.format(startTime);
			String end = formatTemp.format(endTime);
			Date now = new Date();// 当前时间
			if (!(refuse.getFkey() != null
					&& refuse.getFkey().equals("allowPerson")
					&& refuse.getFvalues() != null && !"".equals(refuse.getFvalues()) && refuse.getFvalues()
					.contains(userroleId+""))) {
				if (startTime.before(now) && endTime.after(now)) {
					map.put("success", "false");
					map.put("msg", start + "至" + end + "期间停止接单!");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}
		}*/
		HashMap<String, Object> tm = null;
		try {
			tm = RedisUtil.IsSystemDefend(0,userroleId);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(tm != null){
			return writeAjaxResponse(response, JSONUtil.getJson(tm));
		}
		// 停止接单结束
		
		/*if (request.getParameter("id") == null
				|| "".equals(request.getParameter("id"))) {
			map.put("success", "false");
			map.put("msg", "请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userroleId = Integer.parseInt(request.getParameter("id"));
		}*/
		// 判断用户是否欠费
		BigDecimal balance = userRoleDao.getById(userroleId).getFbalance();

		if (balance.compareTo(new BigDecimal(0)) == -1) {
			return this.poClient(response, false, "请先结清您欠的费用！");
		}
		
		//20160530业务再次确认个人认证跳过允许下单;
		//新增下单前认证审核通过判断，开始;
		CL_Order order =new CL_Order();
		order.setFordesource("pcWeb");
		CL_UserRole userinfo = userRoleDao.getById(userroleId);
		if(userinfo.isPassIdentify()){
			order.setIdentifyType(3);
		}else{
			List<CL_Identification> identifications = identificationDao.getStatusByUserRoleId(userroleId);
			map.put("success", "true");
//				order.setIdentifyType());//认证状态值：3正常下单;0 跳到认证界面;1 直接提示拨打客服电话通过认证; 2 跳到认证结果界面
			map.put("data","{\"identifyType\":\""+identifications.get(0).getStatus()+"\"}");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		//新增下单前认证审核通过判断，结束;
		
		if(request.getParameter("fremark")==null || "".equals(request.getParameter("fremark"))){
			fremark=request.getParameter("fremark");
		}else{
			fremark = request.getParameter("fremark").replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
		}
		
		if(request.getParameter("protocolId")==null || "".equals(request.getParameter("protocolId"))){
			map.put("success", "false");
			map.put("msg","该用户不是协议用户");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			protocolId=Integer.parseInt(request.getParameter("protocolId"));
		}
		CL_Protocol protocol=protocolDao.getById(protocolId);
		if(protocol.getStatus()==0){
			map.put("success", "false");
			map.put("msg","该协议已经失效");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if(request.getParameter("orderType")==null || "".equals(request.getParameter("orderType"))){
			map.put("success", "false");
			map.put("msg","无法获知订单类型！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			orderType = Integer.parseInt(request.getParameter("orderType"));
		}
		  
		if(orderType==2){
			map.put("success", "false");
			map.put("msg","系统已经停止零单服务！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		
		if(orderType==1||orderType==3){
			if(request.getParameter("specId")==null || "".equals(request.getParameter("specId"))||request.getParameter("specId")=="null"){
				map.put("success", "false");
				map.put("msg","请选择车厢！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				specId = Integer.parseInt(request.getParameter("specId"));
			}
			if(request.getParameter("carTypeId")==null || "".equals(request.getParameter("carTypeId"))||request.getParameter("carTypeId")=="null"){
				map.put("success", "false");
				map.put("msg","请选择车型！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				carTypeId = request.getParameter("carTypeId");
			}
			otherId = request.getParameter("otherId");
		}
		if(request.getParameter("fopint")==null || "".equals(request.getParameter("fopint"))){
			fopint=1;
		}else{
			fopint =Integer.valueOf(request.getParameter("fopint").toString()) ; 
		}
		
		//====区分是否是pcWeb的入口     if(为pcWeb入口获取前端参数)  by  2016-06-25  twr start
       if(request.getParameter("pcWeb")!=null&&"1".equals(request.getParameter("pcWeb"))) {
				String addressDeliver=null,addressDeliverName=null,zheng_time1=null,famount=null,length=null,zheng_time2=null,addressDeliverPhone=null;
			     String deliverLongitude=null,deliverLatitude=null,fincrementServe=null;
			     String[] addressReceipt=null,addressReceiptName=null,addressReceiptPhone=null,longitude=null,latitude=null;
			    BigDecimal purposeAmount=new BigDecimal(0);
				
			    if(orderType==2){
			    	if(request.getParameter("length")==null || "".equals(request.getParameter("length"))){
						map.put("success", "false");
						map.put("msg","请选择长度！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}else{
						length =request.getParameter("length"); 
					}
             		order.setLength(new BigDecimal(length));//长度
             		if(request.getParameter("famount")==null || "".equals(request.getParameter("famount"))){
						map.put("success", "false");
						map.put("msg","请选择数量！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}else{
						famount =request.getParameter("famount"); 
					}
         			if(new BigDecimal(famount).compareTo(new BigDecimal(0))==1){
         				order.setFamount(new BigDecimal(famount));
         			}
                }
			    if(request.getParameter("zheng_time1")==null || "".equals(request.getParameter("zheng_time1"))){
					map.put("success", "false");
					map.put("msg","请选择装车时间！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					zheng_time1 =request.getParameter("zheng_time1")  ; 
				}
				if(request.getParameter("zheng_time2")==null || "".equals(request.getParameter("zheng_time2"))){
					map.put("success", "false");
					map.put("msg","请选择装车时间！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					zheng_time2 = request.getParameter("zheng_time2"); 
				}
				try {
					 order.setCreateTime(new Date());
					 String  loadedTime=zheng_time1+" "+zheng_time2;
					 Date loadTime =format.parse(loadedTime);
					 long time=loadTime.getTime()-order.getCreateTime().getTime();
					 long ss=time-60*60000;
					 if(ss>=0){
						 order.setLoadedTime(loadTime);
					 }
					 else{
						 map.put("success", "false");
						 map.put("msg","装车时间必须大于创建时间一小时！");
						 return writeAjaxResponse(response, JSONUtil.getJson(map));
					 }
				} catch (ParseException e) {
					e.printStackTrace();
				}
			    if(request.getParameter("fincrementServe")==null || "".equals(request.getParameter("fincrementServe"))){
					 fincrementServe=null;
				}else{
					 fincrementServe=request.getParameter("fincrementServe");
					 order.setFincrementServe(fincrementServe);
				}
				if(request.getParameter("freceiptSave")==null || "".equals(request.getParameter("freceiptSave"))){
					freceiptSave=0;
				}else{
					freceiptSave=Integer.valueOf(request.getParameter("freceiptSave").toString());
				    
				}
				if(request.getParameter("purposeAmount")==null || "".equals(request.getParameter("purposeAmount"))){
					purposeAmount=new BigDecimal(0);
				}else{
					purposeAmount=BigDecimal.valueOf(Double.parseDouble(request.getParameter("purposeAmount").toString()));
				   order.setPurposeAmount(purposeAmount);
				}
				if(request.getParameter("addressDeliver")==null || "".equals(request.getParameter("addressDeliver"))){
					map.put("success", "false");
					map.put("msg","请填写发货地点");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					addressDeliver = request.getParameter("addressDeliver"); 
				}
				if(request.getParameter("addressDeliverPhone")==null || "".equals(request.getParameter("addressDeliver"))){
					map.put("success", "false");
					map.put("msg","请填写发货人电话");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					addressDeliverPhone = request.getParameter("addressDeliverPhone"); 
				}
				if(request.getParameter("addressDeliverName")==null || "".equals(request.getParameter("addressDeliver"))){
					map.put("success", "false");
					map.put("msg","请填写发货人姓名");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					addressDeliverName = request.getParameter("addressDeliverName"); 
				}
			
				if(request.getParameter("deliverLongitude")==null || "".equals(request.getParameter("deliverLongitude"))){
					map.put("success", "false");
					map.put("msg","发货经度丢失！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					deliverLongitude = request.getParameter("deliverLongitude"); 
				}
				if(request.getParameter("deliverLatitude")==null || "".equals(request.getParameter("deliverLatitude"))){
					map.put("success", "false");
					map.put("msg","发货纬度丢失！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					deliverLatitude = request.getParameter("deliverLatitude"); 
				}
			
				address.put("linkman", addressDeliverName);
				address.put("addressName", addressDeliver);
				address.put("longitude", deliverLongitude);
				address.put("latitude", deliverLatitude);
				address.put("phone", addressDeliverPhone);
				addressDel.add(0, address);
			
			  if(request.getParameter("goodsTypeId")==null || "".equals(request.getParameter("goodsTypeId"))){
					map.put("success", "false");
					map.put("msg","货品类型必须填写！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
			  }else{
					order.setGoodsTypeId(request.getParameter("goodsTypeId").toString());
			  } 
			  if(request.getParameter("goodsTypeName")==null || "".equals(request.getParameter("goodsTypeName"))){
					map.put("success", "false");
					map.put("msg","货品类型名字必须填写！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					order.setGoodsTypeName(request.getParameter("goodsTypeName"));
			  } 
			  if(request.getParameter("type")==null || "".equals(request.getParameter("type"))){
					map.put("success", "false");
					map.put("msg","订单类型！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					order.setType(Integer.valueOf(request.getParameter("type").toString()));//订单类型
			  }

				if(order.getType()==2){
					map.put("success", "false");
					map.put("msg","系统已经停止零单服务！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				
				if(orderType!=3){
					//===收货地址
					if(request.getParameter("addressReceipt")==null || "".equals(request.getParameter("addressReceipt"))){
						map.put("success", "false");
						map.put("msg","请填写收货地点");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}else{
						addressReceipt=request.getParameterValues("addressReceipt");
					}
					if(request.getParameter("addressReceiptPhone")==null || "".equals(request.getParameter("addressReceiptPhone"))){
						map.put("success", "false");
						map.put("msg","请填写收货人电话");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}else{
						addressReceiptPhone = request.getParameterValues("addressReceiptPhone"); 
					}
					if(request.getParameter("addressReceiptName")==null || "".equals(request.getParameter("addressReceiptName"))){
						map.put("success", "false");
						map.put("msg","收货人姓名");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}else{
						addressReceiptName= request.getParameterValues("addressReceiptName"); 
					}
					
					if(request.getParameter("longitude")==null || "".equals(request.getParameter("longitude"))){
						map.put("success", "false");
						map.put("msg","收货经度丢失！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}else{
						longitude = request.getParameterValues("longitude"); 
					}
					if(request.getParameter("latitude")==null || "".equals(request.getParameter("latitude"))){
						map.put("success", "false");
						map.put("msg","发货纬度丢失！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}else{
						latitude = request.getParameterValues("latitude"); 
					}
			        for(int i=0;i<fopint;i++){
			        	address.put("linkman", addressReceiptName[i]);
						address.put("addressName",addressReceipt[i]);
						address.put("longitude", longitude[i]);
						address.put("latitude", latitude[i]);
						address.put("phone", addressReceiptPhone[i]);
						addressRec.add(i, address);
				  }
					if(request.getParameter("mileage").toString()!=null&& "".equals(request.getParameter("mileage").toString())){
						map.put("success", "false");
						map.put("msg","请输入里程数！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}
					else{
						order.setMileage(new BigDecimal(request.getParameter("mileage").toString()).setScale(1,BigDecimal.ROUND_HALF_UP));//里程
					}
				}
		   }
		   //(pcWeb出口获得前端参数 ) by 2016-06-25 twr end
		 //=======   （App入口获取前端参数） by  2016-06-25  twr  start
		   else{
			   if(request.getParameter("order")==null || "".equals(request.getParameter("order"))){
					map.put("success", "false");
					map.put("msg","请先录入订单信息！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					orderString =request.getParameter("order")  ; 
				}
				
				if(request.getParameter("addressDeliver")==null || "".equals(request.getParameter("addressDeliver"))){
					map.put("success", "false");
					map.put("msg","请先选择发货地址！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}else{
					addressDeliverString = request.getParameter("addressDeliver"); 
				}
               
				if(protocol.getType()!=3){
					
					if(request.getParameter("addressReceipt")==null || "".equals(request.getParameter("addressReceipt"))){
						map.put("success", "false");
						map.put("msg","请先选择收货地址！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}else{
						addressReceiptString = request.getParameter("addressReceipt"); 
					}
				} 
				JSONArray jsonsorder = JSONArray.fromObject(orderString);
				
//				CL_Order order =new CL_Order();
				order.setType(jsonsorder.getJSONObject(0).getInt("type"));//订单类型
				order.setGoodsTypeId(jsonsorder.getJSONObject(0).get("goodsTypeId").toString());//货物类型ID
				order.setGoodsTypeName(jsonsorder.getJSONObject(0).get("goodsTypeName").toString());//货物类型名称
				order.setCreateTime(new Date());
			    if(orderType==2){
             		order.setLength(new BigDecimal(jsonsorder.getJSONObject(0).get("length").toString()));//长度
         			if(new BigDecimal(jsonsorder.getJSONObject(0).get("famount").toString()).compareTo(new BigDecimal(0))==1){
         				order.setFamount(new BigDecimal(jsonsorder.getJSONObject(0).get("famount").toString()));
         			}
                }
				try {
					 Date loadTime =format.parse(jsonsorder.getJSONObject(0).get("loadedTime").toString());
					 long time=loadTime.getTime()-order.getCreateTime().getTime();
					 long ss=time-60*60000;
					 if(ss>=0){
						 order.setLoadedTime(format.parse(jsonsorder.getJSONObject(0).get("loadedTime").toString()));
					 }
					 else{
						 map.put("success", "false");
						 map.put("msg","装车时间必须大于创建时间一小时！");
						 return writeAjaxResponse(response, JSONUtil.getJson(map));
					 }
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(protocol.getType()!=3){
					order.setMileage(new BigDecimal(jsonsorder.getJSONObject(0).get("mileage").toString()).setScale(1,BigDecimal.ROUND_HALF_UP));//里程
				}
				
		   }
		   //===（APP出口 获取参数完毕） by 2016-06-25  twr end
		
	   	if(userInfo.isSub()){
			order.setCreator(userroleId);//创建人
			order.setSubUserId(userInfo.getFsubId());//创建人
			order.setSubName(userInfo.getFusername());
		}else{
			order.setCreator(userroleId);//创建人
		}
		order.setOperator(userroleId);//操作人ID
		order.setProtocolId(protocolId);//协议表IDf
		order.setFremark(fremark);
		order.setFopint(fopint);
		order.setProtocolType(1);
		if(protocol.getFunitId()!=null){
			order.setFunitId(protocol.getFunitId());	
		}
		//		============================================协议计算运费=============开始=====================================//
		String serve = order.getFincrementServe();
		//逻辑整改，PC端注释
		/*if(order.getType()==1){//整车计算运费
			if(protocol!=null){
				//实际运输距离大于规则起步公里数:运费=(规则起步价+(实际距离-规则起步公里数)×规则公里单价)
				if(order.getMileage().compareTo(protocol.getStartKilometre())==1){
					BigDecimal km = order.getMileage().subtract(protocol.getStartKilometre());//减法
					BigDecimal fee1 =km.multiply(protocol.getOutKilometre());//乘法
					BigDecimal opintprice=new BigDecimal(0);
					if(fopint>protocol.getFopint()){
						Integer foutopint=fopint-protocol.getFopint();
						opintprice=protocol.getFoutopint().multiply(new BigDecimal(foutopint));
					}
					if(protocol.getFdiscount()!=null){
						BigDecimal dis=protocol.getFdiscount();//折扣
						BigDecimal fre= protocol.getStartPrice().add(fee1).add(opintprice); //协议折扣运费
						if(serve != null){
							if(serve.indexOf("装卸") != -1){
								order.setFreight(fre.multiply(dis).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
							} else {
								order.setFreight(fre.multiply(dis).setScale(2, BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
							}
						} else {
							order.setFreight(fre.multiply(dis).setScale(2, BigDecimal.ROUND_HALF_UP));
							order.setForiginfreight(order.getFreight());
						}
					} else{
						if(serve != null){
							if(serve.indexOf("装卸") != -1){
								order.setFreight(protocol.getStartPrice().add(fee1).add(opintprice).add(protocol.getFadd_service()).setScale(1, BigDecimal.ROUND_HALF_UP));//加法 无折扣情况下的运费
								order.setForiginfreight(order.getFreight());
							} else {
								order.setFreight(protocol.getStartPrice().add(fee1).add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP));//加法 无折扣情况下的运费
								order.setForiginfreight(order.getFreight());
							}
						} else {
							order.setFreight(protocol.getStartPrice().add(fee1).add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP));//加法 无折扣情况下的运费
							order.setForiginfreight(order.getFreight());
						}
					}
					//实际运输距离小于/等于规则起步公里数:运费=规则起步价
				}else if(order.getMileage().compareTo(protocol.getStartKilometre())==-1 || order.getMileage().compareTo(protocol.getStartKilometre())==0){
					if(protocol.getFdiscount()!=null){
						BigDecimal dis=protocol.getFdiscount();
						BigDecimal opintprice=new BigDecimal(0);
						if(fopint>protocol.getFopint()){
							Integer foutopint=fopint-protocol.getFopint();
							opintprice=protocol.getFoutopint().multiply(new BigDecimal(foutopint));
						}
						BigDecimal fre=opintprice.add(protocol.getStartPrice());
						if(serve != null){
							if(serve.indexOf("装卸") != -1){
								order.setFreight(fre.multiply(dis).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
							} else {
								order.setFreight(fre.multiply(dis).setScale(2, BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
							}
						} else {
						order.setFreight(fre.multiply(dis).setScale(2, BigDecimal.ROUND_HALF_UP));
						order.setForiginfreight(order.getFreight());
						}
					}else{
						BigDecimal opintprice=new BigDecimal(0);
						if(fopint>protocol.getFopint()){
							Integer foutopint=fopint-protocol.getFopint();
							opintprice=protocol.getFoutopint().multiply(new BigDecimal(foutopint));
						}
						if(serve != null){
							if(serve.indexOf("装卸") != -1){
								order.setFreight(protocol.getStartPrice().add(opintprice).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
							} else {
								order.setFreight(protocol.getStartPrice().add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
							}
						} else {
						order.setFreight(protocol.getStartPrice().add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP));
						order.setForiginfreight(order.getFreight());
						}
					}
				}
			}else{
				map.put("success", "false");
				map.put("msg","所选车厢,规则遗失,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}else if(order.getType()==2){//零担计算运费
		
			if(protocol!=null){
				BigDecimal opintprice=new BigDecimal(0);
				if(fopint>protocol.getFopint()){
					Integer foutopint=fopint-protocol.getFopint();
					opintprice=protocol.getFoutopint().multiply(new BigDecimal(foutopint));
				}
				BigDecimal price=protocol.getStartPrice() ;
				if(order.getMileage().compareTo(protocol.getStartKilometre())==1){
					BigDecimal km=order.getMileage().subtract(protocol.getStartKilometre());//减法
					BigDecimal feel=km.multiply(protocol.getOutKilometre());//乘法
					price=price.add(feel);
				} 
				if(order.getFamount().compareTo(protocol.getStartNumber())==1){
					BigDecimal number=order.getFamount().subtract(protocol.getStartNumber());//减法
					BigDecimal feelNumber=number.multiply(protocol.getOutNumprice());//乘法
					price=price.add(feelNumber);
				} 
				if(serve != null){
					if(serve.indexOf("装卸") != -1){
						order.setFreight(price.add(opintprice).add(protocol.getFadd_service()).setScale(2,BigDecimal.ROUND_HALF_UP));
						order.setForiginfreight(order.getFreight());
					} else {
						order.setFreight(price.add(opintprice).setScale(2,BigDecimal.ROUND_HALF_UP));
						order.setForiginfreight(order.getFreight());
					}
				} else {
					order.setFreight(price.add(opintprice).setScale(2,BigDecimal.ROUND_HALF_UP));
					order.setForiginfreight(order.getFreight());
				}
			}else{
				map.put("success", "false");
				map.put("msg","零担规则遗失,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}else if(order.getType()==3){//包天计费规则
			if(protocol!=null){
				order.setFreight(protocol.getTimePrice().setScale(2,BigDecimal.ROUND_HALF_UP));
				order.setForiginfreight(order.getFreight());
			}
			else{
				map.put("success", "false");
				map.put("msg","包天规则遗失,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map)); 
			}
		}*/
		order.setNumber(CacheUtilByCC.getOrderNumber(new String("CL_Order"), new String ("O"), 8));
		order.setStatus(1);
	
		
		
		/**计算运费 Start*/
//		order.setFdriverfee(order.getFreight().multiply(CalcTotalField.calDriverFee(order.getFreight())).setScale(1,BigDecimal.ROUND_HALF_UP));
		/**计算运费 End*/		
		
       //测试记得注释
	//	order.setFreight(new BigDecimal("0.01"));
		
		this.orderDao.save(order);
		//============================================整车计算运费=============结束=====================================//
		//===================== 增加订单车辆明细===============================开始=====================================//
		if(order.getType()==1 ||order.getType()==3 ){
			String[] carTypeString=carTypeId.split(",");
			if(otherId!=null && !"".equals(otherId) && !otherId.contains("null")){
				String[] carOtherString=otherId.split(",");
				for(int m=0;m<carOtherString.length;m++){
					for(int n=0;n<carTypeString.length;n++){
						CL_OrderCarDetail orderCarDetail1=new CL_OrderCarDetail();
						orderCarDetail1.setOrderId(order.getId());
						orderCarDetail1.setCarSpecId(specId);
						System.out.println(carTypeString[n]);
						System.out.println(Integer.valueOf(carTypeString[n]));
						if(Integer.valueOf(carTypeString[n])>22){
							System.out.println("**********************数据有问题************************"+order.getNumber());
						}
						orderCarDetail1.setCarTypeId(Integer.valueOf(carTypeString[n]));
						orderCarDetail1.setCarOtherId(Integer.valueOf(carOtherString[m]));
						orderCarDetail1.setCreateTime(new Date());
						orderCarDetailDao.save(orderCarDetail1);
					}
				}
			}else{
				for(int i=0;i<carTypeString.length;i++){
					CL_OrderCarDetail orderCarDetail2=new CL_OrderCarDetail();
					orderCarDetail2.setOrderId(order.getId());
					orderCarDetail2.setCarSpecId(specId);
					if(Integer.valueOf(carTypeString[i])>22){
						System.out.println("**********************数据有问题************************"+order.getNumber());
					}
					orderCarDetail2.setCarTypeId(Integer.valueOf(carTypeString[i]));
					orderCarDetail2.setCreateTime(new Date());
					orderCarDetailDao.save(orderCarDetail2);
				}
			}
		}
		//===================== 增加订单车辆明细===============================结束=====================================//
		this.createDetail(addressDel,order.getId(),1);
		if(protocol.getType()!=3){
			this.createDetail(addressRec,order.getId(),2);
		}
		order.setFreight(order.getFreight().setScale(2, BigDecimal.ROUND_HALF_UP));
		order.setForiginfreight(order.getFreight());
		JSONArray JOArry=new JSONArray();
		String [] title= new String[]{"装卸","电子回单","回单原件","上楼","代收货款"};
		String [] context=new String []{"线下协商、支付","免费","线下协商、支付","线下协商、支付","免费"};
		for(int i=0;i<title.length;i++){
			JSONObject  JO=new JSONObject();
			JO.put("id", i);
			JO.put("title", title[i]);
			JO.put("context",context[i]);
			JO.put("isc", 0);
			JOArry.add(i,JO);
		}
		if(order.getType()!=3){
			order.setServer(JOArry);
			//			order.setServer("[{\"id\":\"0\",\"title\":\"装卸\",\"context\":\"线下协商、支付\",\"isc\":\"0\"},{\"id\":\"1\",\"title\":\"电子回单\",\"context\":\"免费\",\"isc\":\"0\"},{\"id\":\"2\",\"title\":\"回单原件\",\"context\":\"免费\",\"isc\":\"0\"},{\"id\":\"3\",\"title\":\"上楼\",\"context\":\"线下协商、支付\",\"isc\":\"0\"},{\"id\":\"4\",\"title\":\"代收货款\",\"context\":\"免费\",\"isc\":\"0\"}]");

		}
		  
	   if(userRoleDao.getById(userroleId).isProtocol()){
		   //根据卸货地址数判断是否可以支持运费到付     BY  CC  2016-05-17 START
		   if(addressRec.size()==1)
		   {
			   order.setPayMethod("0,1,2,3,4,5");
		   }
		   else
		   {
			   order.setPayMethod("0,1,2,3,5");
		   }
		   
		 //根据卸货地址数判断是否可以支持运费到付     BY  CC  2016-05-17 END
			 //order.setPayMethod("0,1,2,3,4,5");//2016-05-17 CC BAK
	   }
	   else{
		 //根据卸货地址数判断是否可以支持运费到付     BY  CC  2016-05-17 START
		   if(addressRec.size()==1)
		   {
			   order.setPayMethod("0,1,2,3,4");
		   }
		   else
		   {
			   order.setPayMethod("0,1,2,3");
		   }
		//根据卸货地址数判断是否可以支持运费到付     BY  CC  2016-05-17 end 
        //order.setPayMethod("0,1,2,3,4");//2016-05-17 CC BAK
	   }
		map.put("success", "true");
		map.put("data",order);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**
	 * 字符串转换unicode
	 */
	//	 public  String stringToUnicode(String string) {
	//	     StringBuffer unicode = new StringBuffer();
	//	     for (int i = 0; i < string.length(); i++) {
	//	         // 取出每一个字符
	//	        char c = string.charAt(i);
	//	         // 转换为unicode
	//	         unicode.append("\\u" + Integer.toHexString(c));
	//	     }
	//	     return unicode.toString();
	//	 }
	//
	//	 private String Emoji2unicode(String s)
	//	 {
	//		 String result="";
	//		 for(int i=0;i<s.length();i++)
	//		 { 
	//			 s.matches(regex)
	//		 }
	//		 return result;
	//	 }
	//	 
	//	 

	/***新增订单*/
	@SuppressWarnings("null")
	@RequestMapping("/pcWeb/order/saveOrder")
	@Transactional
	public  String saveOrder(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userroleId,orderType=null,specId= null,fopint=1,freceiptSave=0;
		String orderString,addressDeliverString,addressReceiptString,carTypeId=null,otherId=null,fremark=null;
		BigDecimal vod=new BigDecimal(0);
		BigDecimal weh=new BigDecimal(0);
		BigDecimal vodtoWeh=new BigDecimal(0);
		HashMap<String, Object> map = new HashMap<String, Object>();
	     net.sf.json.JSONObject address=new JSONObject(); 	
	     net.sf.json.JSONArray addressDel=new JSONArray(), addressRec=new JSONArray();
	     
	     if(request.getSession().getId()==null || "".equals(request.getSession().getId()) || !ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	 map.put("success", "false");
				map.put("msg","未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
	     }
			
	    Util_UserOnline userInfo=ServerContext.getUseronline().get(request.getSession().getId().toString());
	    userroleId =userInfo.getFuserId();
		    
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		// 停止接单开始
		/*SimpleDateFormat formatTemp = new SimpleDateFormat("yyyy-MM-dd");
		CL_Refuse refuse = refuseDao.getById(1);
		Date startTime = refuse.getFstart_time();// 停止接单开始时间
		Date endTime = refuse.getFend_time();// 停止接单结束时间
		if (startTime != null) {
			String start = formatTemp.format(startTime);
			String end = formatTemp.format(endTime);
			Date now = new Date();// 当前时间
			if (!(refuse.getFkey() != null
					&& refuse.getFkey().equals("allowPerson")
					&& refuse.getFvalues() != null && !"".equals(refuse.getFvalues()) && refuse.getFvalues()
					.contains(userroleId+""))) {
				if (startTime.before(now) && endTime.after(now)) {
					map.put("success", "false");
					map.put("msg", start + "至" + end + "期间停止接单!");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}
		}*/
		HashMap<String, Object> tm = null;
		try {
			tm = RedisUtil.IsSystemDefend(0,userroleId);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(tm != null){
			return writeAjaxResponse(response, JSONUtil.getJson(tm));
		}
		//停止接单结束
		
		/*if (request.getParameter("id") == null
				|| "".equals(request.getParameter("id"))) {
			map.put("success", "false");
			map.put("msg", "请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userroleId = Integer.parseInt(request.getParameter("id"));
		}*/
		// 判断用户是否欠费
		BigDecimal balance = userRoleDao.getById(userroleId).getFbalance();

		if (balance.compareTo(new BigDecimal(0)) == -1) {
			return this.poClient(response, false, "请先结清您欠的费用！");
		}
		
		//20160530业务再次确认个人认证跳过允许下单;
    	//新增下单前认证审核通过判断，开始;
  		CL_Order order =new CL_Order();
  		order.setFordesource("pcWeb");
  		CL_UserRole userinfo = userRoleDao.getById(userroleId);
  		if(userinfo.isPassIdentify()){
  			order.setIdentifyType(3);
  		}else{
  			List<CL_Identification> identifications = identificationDao.getStatusByUserRoleId(userroleId);
  			map.put("success", "true");
//	  				order.setIdentifyType());//认证状态值：3正常下单;0 跳到认证界面;1 直接提示拨打客服电话通过认证; 2 跳到认证结果界面
  			map.put("data","{\"identifyType\":\""+identifications.get(0).getStatus()+"\"}");
  			return writeAjaxResponse(response, JSONUtil.getJson(map));
  		}
  		//新增下单前认证审核通过判断，结束;
		if(request.getParameter("orderType")==null || "".equals(request.getParameter("orderType"))){
			map.put("success", "false");
			map.put("msg","无法获知订单类型！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			orderType = Integer.parseInt(request.getParameter("orderType"));
		}
		
		if(orderType==2){
			map.put("success", "false");
			map.put("msg","系统已经停止零单服务！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		
		if(request.getParameter("fopint")==null || "".equals(request.getParameter("fopint"))){
			fopint=1;
		}else{
			fopint =Integer.valueOf(request.getParameter("fopint").toString()) ; 
		}
		
		if(orderType==1){
			if(request.getParameter("specId")==null || "".equals(request.getParameter("specId"))){
				map.put("success", "false");
				map.put("msg","请选择车厢！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				specId = Integer.parseInt(request.getParameter("specId"));
			}
			if(request.getParameter("carTypeId")==null || "".equals(request.getParameter("carTypeId"))){
				map.put("success", "false");
				map.put("msg","请选择车型！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				carTypeId = request.getParameter("carTypeId");
			}
			otherId = request.getParameter("otherId");
		}
		//====区分是否是pcWeb的入口     if(为pcWeb入口获取前端参数)  by  2016-06-25  twr start
	   if(request.getParameter("pcWeb")!=null&&"1".equals(request.getParameter("pcWeb"))) {
			String addressDeliver=null,addressDeliverName=null,zheng_time1=null,zheng_time2=null,addressDeliverPhone=null;
		     String deliverLongitude=null,deliverLatitude=null,fincrementServe=null;
		     String[] addressReceipt=null,addressReceiptName=null,addressReceiptPhone=null,longitude=null,latitude=null;
		    BigDecimal purposeAmount=new BigDecimal(0);
		    if(request.getParameter("zheng_time1")==null || "".equals(request.getParameter("zheng_time1"))){
				map.put("success", "false");
				map.put("msg","请选择装车时间！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				zheng_time1 =request.getParameter("zheng_time1")  ; 
			}
			if(request.getParameter("zheng_time2")==null || "".equals(request.getParameter("zheng_time2"))){
				map.put("success", "false");
				map.put("msg","请选择装车时间！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				zheng_time2 = request.getParameter("zheng_time2"); 
			}
			try {
				 order.setCreateTime(new Date());
				 String  loadedTime=zheng_time1+" "+zheng_time2;
				 Date loadTime =format.parse(loadedTime);
				 long time=loadTime.getTime()-order.getCreateTime().getTime();
				 long ss=time-60*60000;
				 if(ss>=0){
					 order.setLoadedTime(loadTime);
				 }
				 else{
					 map.put("success", "false");
					 map.put("msg","装车时间必须大于创建时间一小时！");
					 return writeAjaxResponse(response, JSONUtil.getJson(map));
				 }
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(request.getParameter("fremark")==null || "".equals(request.getParameter("fremark"))){
				fremark=request.getParameter("fremark");
			}else{
				fremark = new String(request.getParameter("fremark").replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));
			}
		    if(request.getParameter("fincrementServe")==null || "".equals(request.getParameter("fincrementServe"))){
				 fincrementServe=null;
			}else{
				 fincrementServe=request.getParameter("fincrementServe");
			     order.setFincrementServe(fincrementServe);
			}
			if(request.getParameter("freceiptSave")==null || "".equals(request.getParameter("freceiptSave"))){
				freceiptSave=0;
			}else{
				freceiptSave=Integer.valueOf(request.getParameter("freceiptSave").toString());
			}
			if(request.getParameter("purposeAmount")==null || "".equals(request.getParameter("purposeAmount"))){
				purposeAmount=new BigDecimal(0);
			}else{
				purposeAmount=BigDecimal.valueOf(Double.parseDouble(request.getParameter("purposeAmount").toString()));
				order.setPurposeAmount(purposeAmount);
			}
			if(request.getParameter("addressDeliver")==null || "".equals(request.getParameter("addressDeliver"))){
				map.put("success", "false");
				map.put("msg","请填写发货地点");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				addressDeliver = request.getParameter("addressDeliver"); 
			}
			if(request.getParameter("addressDeliverPhone")==null || "".equals(request.getParameter("addressDeliver"))){
				map.put("success", "false");
				map.put("msg","请填写发货人电话");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				addressDeliverPhone = request.getParameter("addressDeliverPhone"); 
			}
			if(request.getParameter("addressDeliverName")==null || "".equals(request.getParameter("addressDeliver"))){
				map.put("success", "false");
				map.put("msg","请填写发货人姓名");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				addressDeliverName = request.getParameter("addressDeliverName"); 
			}
		
			if(request.getParameter("deliverLongitude")==null || "".equals(request.getParameter("deliverLongitude"))){
				map.put("success", "false");
				map.put("msg","发货经度丢失！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				deliverLongitude = request.getParameter("deliverLongitude"); 
			}
			if(request.getParameter("deliverLatitude")==null || "".equals(request.getParameter("deliverLatitude"))){
				map.put("success", "false");
				map.put("msg","发货纬度丢失！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				deliverLatitude = request.getParameter("deliverLatitude"); 
			}
		
			address.put("linkman", addressDeliverName);
			address.put("addressName", addressDeliver);
			address.put("longitude", deliverLongitude);
			address.put("latitude", deliverLatitude);
			address.put("phone", addressDeliverPhone);
			addressDel.add(0, address);
			//===收货地址
			if(request.getParameter("addressReceipt")==null || "".equals(request.getParameter("addressReceipt"))){
				map.put("success", "false");
				map.put("msg","请填写收货地点");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				addressReceipt=request.getParameterValues("addressReceipt");
			}
			if(request.getParameter("addressReceiptPhone")==null || "".equals(request.getParameter("addressReceiptPhone"))){
				map.put("success", "false");
				map.put("msg","请填写收货人电话");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				addressReceiptPhone = request.getParameterValues("addressReceiptPhone"); 
			}
			if(request.getParameter("addressReceiptName")==null || "".equals(request.getParameter("addressReceiptName"))){
				map.put("success", "false");
				map.put("msg","收货人姓名");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				addressReceiptName= request.getParameterValues("addressReceiptName"); 
			}
			
			if(request.getParameter("longitude")==null || "".equals(request.getParameter("longitude"))){
				map.put("success", "false");
				map.put("msg","收货经度丢失！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				longitude = request.getParameterValues("longitude"); 
			}
			if(request.getParameter("latitude")==null || "".equals(request.getParameter("latitude"))){
				map.put("success", "false");
				map.put("msg","发货纬度丢失！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				latitude = request.getParameterValues("latitude"); 
			}
	        for(int i=0;i<fopint;i++){
	        	address.put("linkman", addressReceiptName[i]);
				address.put("addressName",addressReceipt[i]);
				address.put("longitude", longitude[i]);
				address.put("latitude", latitude[i]);
				address.put("phone", addressReceiptPhone[i]);
				addressRec.add(i, address);
		  }
		  if(request.getParameter("goodsTypeId")==null || "".equals(request.getParameter("goodsTypeId"))){
				map.put("success", "false");
				map.put("msg","货品类型必须填写！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
		  }else{
				order.setGoodsTypeId(request.getParameter("goodsTypeId").toString());
		  } 
		  if(request.getParameter("goodsTypeName")==null || "".equals(request.getParameter("goodsTypeName"))){
				map.put("success", "false");
				map.put("msg","货品类型名字必须填写！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				order.setGoodsTypeName(request.getParameter("goodsTypeName"));
		  } 
		  if(request.getParameter("type")==null || "".equals(request.getParameter("type"))){
				map.put("success", "false");
				map.put("msg","订单类型！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				order.setType(Integer.valueOf(request.getParameter("type").toString()));//订单类型
		  }
		  
			if(order.getType()==2){
				map.put("success", "false");
				map.put("msg","系统已经停止零单服务！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			
			if(request.getParameter("volume")!=null&&!"".equals(request.getParameter("volume").toString())) {
				vod = new BigDecimal(request.getParameter("volume").toString());//体积
				vodtoWeh=vod.multiply(new BigDecimal(200));
			}
		    order.setVolume(vod);//体积
		    if(request.getParameter("weight")!=null&&!"".equals(request.getParameter("weight").toString())){
		    	weh=new BigDecimal(request.getParameter("weight").toString());//重量
		    }
			order.setWeight(weh);
			weh=weh.multiply(new BigDecimal(1000));
			if(request.getParameter("length")==null||"".equals(request.getParameter("length"))){
				order.setLength(new BigDecimal("0"));
			}else{
			order.setLength(new BigDecimal(request.getParameter("length")));//长度
			}
			if(request.getParameter("mileage").toString()!=null&& "".equals(request.getParameter("mileage").toString())){
				map.put("success", "false");
				map.put("msg","请输入里程数！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			else{
				order.setMileage(new BigDecimal(request.getParameter("mileage").toString()).setScale(1,BigDecimal.ROUND_HALF_UP));//里程
			}
	   }
	   //(pcWeb出口获得前端参数 ) by 2016-06-25 twr end
	//=======   （App入口获取前端参数） by  2016-06-25  twr  start
	   else{
				//		CL_UserRole userinfo = userRoleDao.getById(userroleId);
				//		if(userinfo.isPassIdentify()){
				//		}else{
				//			List<CL_Identification> identifications = identificationDao.getStatusByUserRoleId(userroleId);
				//			map.put("success", "true");
				////				order.setIdentifyType());//认证状态值：3正常下单;0 跳到认证界面;1 直接提示拨打客服电话通过认证; 2 跳到认证结果界面
				//			map.put("data","{\"identifyType\":\""+identifications.get(0).getStatus()+"\"}");
				//			return writeAjaxResponse(response, JSONUtil.getJson(map));
				//		}
						//新增下单前认证审核通过判断，结束;
					//	getRequestParameter(request, response, map, "无法获知订单类型！","orderType",orderType);
			if(request.getParameter("fremark")==null || "".equals(request.getParameter("fremark"))){
				fremark=request.getParameter("fremark");
			}else{
				fremark = request.getParameter("fremark").replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
			}
		              if(request.getParameter("order")==null || "".equals(request.getParameter("order"))){
							map.put("success", "false");
							map.put("msg","请先录入订单信息！");
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						}else{
							orderString =request.getParameter("order")  ; 
						}
						if(request.getParameter("addressDeliver")==null || "".equals(request.getParameter("addressDeliver"))){
							map.put("success", "false");
							map.put("msg","请先选择发货地址！");
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						}else{
							addressDeliverString = request.getParameter("addressDeliver"); 
						}
						if(request.getParameter("addressReceipt")==null || "".equals(request.getParameter("addressReceipt"))|| request.getParameter("addressReceipt").equals("[]")){
							map.put("success", "false");
							map.put("msg","请先选择收货地址！");
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						}else{
							addressReceiptString = request.getParameter("addressReceipt"); 
						}
						JSONArray jsonsorder = JSONArray.fromObject(orderString);
				//		CL_Order order =new CL_Order();
						order.setType(jsonsorder.getJSONObject(0).getInt("type"));//订单类型
						order.setGoodsTypeId(jsonsorder.getJSONObject(0).get("goodsTypeId").toString());//货物类型ID
						order.setGoodsTypeName(jsonsorder.getJSONObject(0).get("goodsTypeName").toString());//货物类型名称
						if(jsonsorder.getJSONObject(0).get("volume")!=null&&!"".equals(jsonsorder.getJSONObject(0).get("volume").toString())) {
							vod = new BigDecimal(jsonsorder.getJSONObject(0).get("volume").toString());//体积
							vodtoWeh=vod.multiply(new BigDecimal(200));
						}
					    order.setVolume(vod);//体积
					    if(jsonsorder.getJSONObject(0).get("weight")!=null&&!"".equals(jsonsorder.getJSONObject(0).get("weight").toString())){
					    	weh=new BigDecimal(jsonsorder.getJSONObject(0).get("weight").toString());//重量
					    }
						order.setWeight(weh);
						weh=weh.multiply(new BigDecimal(1000));
						order.setLength(new BigDecimal(jsonsorder.getJSONObject(0).get("length").toString()));//长度
						order.setCreateTime(new Date());
						try {
							 Date loadTime =format.parse(jsonsorder.getJSONObject(0).get("loadedTime").toString());
							 long time=loadTime.getTime()-order.getCreateTime().getTime();
							 long ss=time-60*60000;
							 if(ss>=0){
								 order.setLoadedTime(format.parse(jsonsorder.getJSONObject(0).get("loadedTime").toString()));
							 }
							 else{
								 map.put("success", "false");
								 map.put("msg","装车时间必须大于创建时间一小时！");
								 return writeAjaxResponse(response, JSONUtil.getJson(map));
							 }
						} catch (ParseException e) {
							e.printStackTrace();
						}
						if(jsonsorder.getJSONObject(0).get("mileage").toString()!=null&& "".equals(jsonsorder.getJSONObject(0).get("mileage").toString())){
							map.put("success", "false");
							map.put("msg","请输入里程数！");
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						}
						else{
							order.setMileage(new BigDecimal(jsonsorder.getJSONObject(0).get("mileage").toString()).setScale(1,BigDecimal.ROUND_HALF_UP));//里程
						}
						  addressDel = JSONArray.fromObject(addressDeliverString);
						  addressRec = JSONArray.fromObject(addressReceiptString);
	   }
	   //===（APP出口 获取参数完毕） by 2016-06-25  twr end
	       
	   
					 	if(userInfo.isSub()){
							order.setCreator(userroleId);//创建人
							order.setSubUserId(userInfo.getFsubId());//创建人
							order.setSubName(userInfo.getFusername());
						}else{
							order.setCreator(userroleId);//创建人
						}
						order.setFremark(fremark);
						order.setOperator(userroleId);//
						order.setFopint(fopint);
						order.setProtocolType(0);
				
						//============================================计算运费=============开始=====================================//
						String serve = order.getFincrementServe();
						//逻辑整改，PC端注释
						/*if(order.getType()==1){//整车计算运费
							CL_Rule rule =this.ruleDao.getZhengche(specId);
							if(rule!=null){
								BigDecimal opintprice=new BigDecimal(0);
								Integer foutopint=0;
								if(fopint>rule.getFopint()){
									foutopint=fopint-rule.getFopint();
									opintprice=rule.getOutfopint().multiply(new BigDecimal(foutopint));
								}
								opintprice=rule.getOutfopint().multiply(new BigDecimal(foutopint));
								//实际运输距离大于规则起步公里数:运费=(规则起步价+(实际距离-规则起步公里数)×规则公里单价)
								if(order.getMileage().compareTo(rule.getStartKilometre())==1){
									BigDecimal km = order.getMileage().subtract(rule.getStartKilometre());//减法
									BigDecimal fee1 =km.multiply(rule.getKilometrePrice());//乘法
									if(serve != null){
										if(serve.indexOf("装卸") != -1){
											order.setFreight(rule.getStartPrice().add(fee1).add(opintprice).add(rule.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP));//加法
											order.setForiginfreight(order.getFreight());
										} else {
											order.setFreight(rule.getStartPrice().add(fee1).add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP));//加法
											order.setForiginfreight(order.getFreight());
										}
									} else {
										order.setFreight(rule.getStartPrice().add(fee1).add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP));//加法
										order.setForiginfreight(order.getFreight());
									}
								
									//实际运输距离小于/等于规则起步公里数:运费=规则起步价
								}else if(order.getMileage().compareTo(rule.getStartKilometre())==-1 || order.getMileage().compareTo(rule.getStartKilometre())==0){
									if(serve != null){
										if(serve.indexOf("装卸") != -1){
											order.setFreight(rule.getStartPrice().add(opintprice).add(rule.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP));
											order.setForiginfreight(order.getFreight());
										} else {
											order.setFreight(rule.getStartPrice().add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP));
											order.setForiginfreight(order.getFreight());
										}
									} else {
										order.setFreight(rule.getStartPrice().add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP));
										order.setForiginfreight(order.getFreight());
									}
									
								}
							}else{
								map.put("success", "false");
								map.put("msg","所选车厢,规则遗失,请联系客服");
								return writeAjaxResponse(response, JSONUtil.getJson(map));
							}
						}else if(order.getType()==2){//零担计算运费
						//CL_Rule ruleVol =this.ruleDao.getLingdanForVolume();
						// ruleWeh =this.ruleDao.getLingdanForWeight();
						//	BigDecimal opintprice=new BigDecimal(0);
						//	BigDecimal Wehopintprice=new BigDecimal(0);
						//	Integer foutopint=0;
				//			if(fopint>ruleVol.getFopint()){
				//				foutopint=fopint-ruleVol.getFopint();
				//				opintprice=ruleVol.getOutfopint().multiply(new BigDecimal(foutopint));
				//			}
				//			if(fopint>ruleWeh.getFopint()){
				//				foutopint=fopint-ruleWeh.getFopint();
				//				Wehopintprice=ruleWeh.getOutfopint().multiply(new BigDecimal(foutopint));
				//
				//			}
							//			 	Integer foutopint=fopint+1-ruleVol.getFopint();
							//				 BigDecimal opintprice=ruleVol.getOutfopint().multiply(new BigDecimal(foutopint));
				//			if(ruleVol!=null && ruleWeh!=null){
				//				BigDecimal volfee = ruleVol.getStartPrice().add(opintprice).add(order.getVolume().subtract(new BigDecimal("1")).multiply(ruleVol.getOutPrice()));
				//				BigDecimal wehfee = ruleWeh.getStartPrice().add(Wehopintprice).add(order.getWeight().subtract(new BigDecimal("1")).multiply(ruleWeh.getOutPrice()));
				//				//按体积计算运费大于按重量计算的运费
				//				if(order.getVolume().compareTo(new BigDecimal("1"))==-1 && order.getWeight().compareTo(new BigDecimal("1"))==-1){
				//					if(ruleVol.getStartPrice().compareTo(ruleWeh.getStartPrice())==1){
				//						order.setFreight(ruleVol.getStartPrice().add(opintprice).setScale(1, BigDecimal.ROUND_HALF_UP));
				//					}else{
				//						order.setFreight(ruleWeh.getStartPrice().add(Wehopintprice).setScale(1, BigDecimal.ROUND_HALF_UP));
				//					}
				//				}else if(volfee.compareTo(wehfee)==1){
				//					order.setCollection(2);
				//					order.setFreight(volfee.setScale(1, BigDecimal.ROUND_HALF_UP));
				//				}else{
				//					order.setCollection(1);
				//					order.setFreight(wehfee.setScale(1, BigDecimal.ROUND_HALF_UP));
				//				}
				//			}else{
				//				map.put("success", "false");
				//				map.put("msg","零担规则遗失,请联系客服");
				//				return writeAjaxResponse(response, JSONUtil.getJson(map));
				//			}
							//  2016-6-18 新需求 按重量（公斤）区间来算 价格  BY twr start
							CL_Rule ruleVol =this.ruleDao.getLingdanForVolume();
							CL_Rule ruleWeh =this.ruleDao.getLingdanForWeight();
							BigDecimal finalWeight=new BigDecimal(0);
							if(vodtoWeh.compareTo(weh)<=0){
							     finalWeight=weh;
							 	order.setCollection(1);  //按重量计算 为1
							}else if(vodtoWeh.compareTo(weh)==1){
								 finalWeight=vodtoWeh;
								 order.setCollection(2);  //按体积计算  为2
								 
							}
							finalWeight=finalWeight.subtract(new BigDecimal(75));//保底公斤
							if(finalWeight.compareTo(new BigDecimal(0))<=0){
								if(serve != null){
									if(serve.indexOf("装卸") != -1){
										order.setFreight(new BigDecimal(30).add(ruleWeh.getFadd_service()));
										order.setForiginfreight(order.getFreight());
									} else {
										order.setFreight(new BigDecimal(30));
										order.setForiginfreight(order.getFreight());
									}
								} else {
								order.setFreight(new BigDecimal(30));
								order.setForiginfreight(order.getFreight());
								}
							}
							else{
								if(serve != null){
									if(serve.indexOf("装卸") != -1){
										if(finalWeight.compareTo(new BigDecimal(500))<=0){
											  order.setFreight(finalWeight.multiply(new BigDecimal("0.4")).add(new BigDecimal(30)).add(ruleWeh.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4 500公斤以下
											  order.setForiginfreight(order.getFreight());
										  }
										  if(finalWeight.compareTo(new BigDecimal(700))<=0&&finalWeight.compareTo(new BigDecimal(500))==1){
											  order.setFreight(finalWeight.multiply(new BigDecimal("0.36")).add(new BigDecimal(30)).add(ruleWeh.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4*0.9 500-700公斤
											  order.setForiginfreight(order.getFreight());
										  }
										  if(finalWeight.compareTo(new BigDecimal(1000))<=0&&finalWeight.compareTo(new BigDecimal(700))==1){
											  order.setFreight(finalWeight.multiply(new BigDecimal("0.32")).add(new BigDecimal(30)).add(ruleWeh.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4*0.8 700-900公斤
											  order.setForiginfreight(order.getFreight());
										  }
										  if(finalWeight.compareTo(new BigDecimal(1000))==1){
											  order.setFreight(finalWeight.multiply(new BigDecimal("0.28")).add(new BigDecimal(30)).add(ruleWeh.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4*0.7 1000公斤以上
											  order.setForiginfreight(order.getFreight());
										  }
									} else {
										if(finalWeight.compareTo(new BigDecimal(500))<=0){
											  order.setFreight(finalWeight.multiply(new BigDecimal("0.4")).add(new BigDecimal(30)).add(ruleWeh.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4 500公斤以下
											  order.setForiginfreight(order.getFreight());
										  }
										  if(finalWeight.compareTo(new BigDecimal(700))<=0&&finalWeight.compareTo(new BigDecimal(500))==1){
											  order.setFreight(finalWeight.multiply(new BigDecimal("0.36")).add(new BigDecimal(30)).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4*0.9 500-700公斤
											  order.setForiginfreight(order.getFreight());
										  }
										  if(finalWeight.compareTo(new BigDecimal(1000))<=0&&finalWeight.compareTo(new BigDecimal(700))==1){
											  order.setFreight(finalWeight.multiply(new BigDecimal("0.32")).add(new BigDecimal(30)).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4*0.8 700-900公斤
											  order.setForiginfreight(order.getFreight());
										  }
										  if(finalWeight.compareTo(new BigDecimal(1000))==1){
											  order.setFreight(finalWeight.multiply(new BigDecimal("0.28")).add(new BigDecimal(30)).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4*0.7 1000公斤以上
											  order.setForiginfreight(order.getFreight());
										  }
									}
								} else {
									
									if(finalWeight.compareTo(new BigDecimal(500))<=0){
										order.setFreight(finalWeight.multiply(new BigDecimal("0.4")).add(new BigDecimal(30)).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4 500公斤以下
										order.setForiginfreight(order.getFreight());
									}
									if(finalWeight.compareTo(new BigDecimal(700))<=0&&finalWeight.compareTo(new BigDecimal(500))==1){
										order.setFreight(finalWeight.multiply(new BigDecimal("0.36")).add(new BigDecimal(30)).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4*0.9 500-700公斤
										order.setForiginfreight(order.getFreight());
									}
									if(finalWeight.compareTo(new BigDecimal(1000))<=0&&finalWeight.compareTo(new BigDecimal(700))==1){
										order.setFreight(finalWeight.multiply(new BigDecimal("0.32")).add(new BigDecimal(30)).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4*0.8 700-900公斤
										order.setForiginfreight(order.getFreight());
									}
									if(finalWeight.compareTo(new BigDecimal(1000))==1){
										order.setFreight(finalWeight.multiply(new BigDecimal("0.28")).add(new BigDecimal(30)).setScale(2, BigDecimal.ROUND_HALF_UP));//0.4*0.7 1000公斤以上
										order.setForiginfreight(order.getFreight());
									}
								}
							}
						}*/
						// 2016-6-18 新需求 按重量（公斤）区间来算 价格  BY twr  end
						order.setNumber(CacheUtilByCC.getOrderNumber(new String("CL_Order"), new String ("O"), 8));
						order.setStatus(1);
				
						
						
						/**计算运费 Start*/
//						order.setFdriverfee(order.getFreight().multiply(CalcTotalField.calDriverFee(order.getFreight())).setScale(1,BigDecimal.ROUND_HALF_UP));
						/**计算运费 End*/
                       //测试记得注释
					//	order.setFreight(new BigDecimal("0.01"));
						this.orderDao.save(order);
						//============================================整车计算运费=============结束=====================================//
						//===================== 增加订单车辆明细===============================开始=====================================//
						if(order.getType()==1 ){
							String[] carTypeString=carTypeId.split(",");
							if(otherId!=null && !"".equals(otherId) && !otherId.contains("null")){
								String[] carOtherString=otherId.split(",");
								for(int m=0;m<carOtherString.length;m++){
									for(int n=0;n<carTypeString.length;n++){
										CL_OrderCarDetail orderCarDetail1=new CL_OrderCarDetail();
										orderCarDetail1.setOrderId(order.getId());
										orderCarDetail1.setCarSpecId(specId);
										System.out.println(carTypeString[n]);
										System.out.println(Integer.valueOf(carTypeString[n]));
										if(Integer.valueOf(carTypeString[n])>22){
											System.out.println("**********************数据有问题************************"+order.getNumber());
										}
										orderCarDetail1.setCarTypeId(Integer.valueOf(carTypeString[n]));
										orderCarDetail1.setCarOtherId(Integer.valueOf(carOtherString[m]));
										orderCarDetail1.setCreateTime(new Date());
										orderCarDetailDao.save(orderCarDetail1);
									}
								}
							}else{
								for(int i=0;i<carTypeString.length;i++){
									CL_OrderCarDetail orderCarDetail2=new CL_OrderCarDetail();
									orderCarDetail2.setOrderId(order.getId());
									orderCarDetail2.setCarSpecId(specId);
									if(Integer.valueOf(carTypeString[i])>22){
										System.out.println("**********************数据有问题************************"+order.getNumber());
									}
									orderCarDetail2.setCarTypeId(Integer.valueOf(carTypeString[i]));
									orderCarDetail2.setCreateTime(new Date());
									orderCarDetailDao.save(orderCarDetail2);
								}
							}
						}
	 	//===================== 增加订单车辆明细===============================结束=====================================//
				    	this.createDetail(addressDel,order.getId(),1);
						this.createDetail(addressRec,order.getId(),2);
						order.setFreight(order.getFreight().setScale(2, BigDecimal.ROUND_HALF_UP));
						order.setForiginfreight(order.getFreight());
						JSONArray JOArry=new JSONArray();
						if(addressRec.size()>1){
							String [] title= new String[]{"装卸","电子回单","回单原件","上楼"};
							String [] context=new String []{"线下协商、支付","免费","线下协商、支付","线下协商、支付"};
							for(int i=0;i<title.length;i++){
								JSONObject  JO=new JSONObject();
								JO.put("id", i);
								JO.put("title", title[i]);
								JO.put("context",context[i]);
								JO.put("isc", 0);
								JOArry.add(i,JO);
							}
						}
			    	else{
							String [] title= new String[]{"装卸","电子回单","回单原件","上楼","代收货款"};
							String [] context=new String []{"线下协商、支付","免费","线下协商、支付","线下协商、支付","免费"};
							for(int i=0;i<title.length;i++){
								JSONObject  JO=new JSONObject();
								JO.put("id", i);
								JO.put("title", title[i]);
								JO.put("context",context[i]);
								JO.put("isc", 0);
								JOArry.add(i,JO);
							}
						}
						order.setServer(JOArry);
						//order.setServer("[{\"id\":\"0\",\"title\":\"装卸\",\"context\":\"线下协商、支付\",\"isc\":\"0\"},{\"id\":\"1\",\"title\":\"电子回单\",\"context\":\"免费\",\"isc\":\"0\"},{\"id\":\"2\",\"title\":\"回单原件\",\"context\":\"免费\",\"isc\":\"0\"},{\"id\":\"3\",\"title\":\"上楼\",\"context\":\"线下协商、支付\",\"isc\":\"0\"},{\"id\":\"4\",\"title\":\"代收货款\",\"context\":\"免费\",\"isc\":\"0\"}]");
						if(addressRec.size()>1){
							order.setPayMethod("0,1,2,3");
						}
						else{
							order.setPayMethod("0,1,2,3,4");
						}
						map.put("success", "true");
						map.put("data",order);
						return writeAjaxResponse(response, JSONUtil.getJson(map));
	
	}

	//创建订单明细发货
	public void createDetail(JSONArray address,Integer orderId,Integer detailType){
		Integer ranInt=null;
		String ranString=null;
		for (int i = 0; i < address.size(); i++) {
			ranInt=new Random().nextInt(900000)+100000;
			ranString=ranInt.toString();
			JSONObject jdel = address.getJSONObject(i);
			CL_OrderDetail detail =new CL_OrderDetail();
			detail.setOrderId(orderId);
			detail.setDetailType(detailType);
			detail.setLinkman(jdel.get("linkman").toString());
			detail.setPhone(jdel.get("phone").toString());
			detail.setAddressName(jdel.get("addressName").toString());
			detail.setLongitude(jdel.get("longitude").toString());
			detail.setLatitude(jdel.get("latitude").toString());
			detail.setSnumber(i+1);
			detail.setSecurityCode(ranString);
			detail.setPass(0);
			this.orderDetailDao.save(detail);
		}
	}
	
	/***设为常用订单*/
	@RequestMapping("/pcWeb/order/setCommon")
	public String setCommon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer orderId,isCommon;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(request.getParameter("orderId")==null || "".equals(request.getParameter("orderId"))){
			map.put("success", "false");
			map.put("msg","数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			orderId = Integer.parseInt(request.getParameter("orderId"));
		}
		if(request.getParameter("isCommon")==null || "".equals(request.getParameter("isCommon"))){
			map.put("success", "false");
			map.put("msg","设为常用订单失败请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			isCommon = Integer.parseInt(request.getParameter("isCommon"));
		}
		this.orderDao.updateByUserId(orderId, isCommon);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("msg","设置成功！" );
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/**根据订单状态筛选订单*/
	@RequestMapping("/pcWeb/order/filtrateOrder")
	public String filtrateOrder(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer status,pageNum ,pageSize,type;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(request.getParameter("status")==null || "".equals(request.getParameter("status"))){
			map.put("success", "false");
			map.put("msg","数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			status = Integer.parseInt(request.getParameter("status"));
		}
		if(request.getParameter("pageNum")==null || "".equals(request.getParameter("pageNum"))){
			map.put("success", "false");
			map.put("msg","无法获知第几页");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		if(request.getParameter("type")==null || "".equals(request.getParameter("type"))){
			map.put("success", "false");
			map.put("msg","数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			type = Integer.parseInt(request.getParameter("type"));
		}
		if(request.getParameter("pageSize")==null || "".equals(request.getParameter("pageSize"))){
			map.put("success", "false");
			map.put("msg","无法获知每页显示多少条");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if(orderQuery==null){
			orderQuery = newQuery(OrderQuery.class, null);
		}
		if (pageNum != null) {
			orderQuery.setPageNumber(pageNum);
		}
		if (pageSize != null) {
			orderQuery.setPageSize(pageSize);
		}
		if(status!=null){
			orderQuery.setStatus(status);
		}
		if(type!=null){
			orderQuery.setType(type);
		}
		Page<CL_Order> page=orderDao.findPage(orderQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("total", page.getTotalCount());
		m.put("data", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/**APP删除订单*/
	@RequestMapping("/pcWeb/order/delorder")
	public String delorder(HttpServletRequest request,HttpServletResponse response) throws IOException{
	          Integer orderId,userType;
	       	  HashMap<String, Object> map = new HashMap<String, Object>();
	          if(request.getParameter("orderId")==null||"".equals(request.getParameter("orderId"))){
	        	 map.put("success", "false");
	  			 map.put("msg","数据有误请联系客服");
	  			return writeAjaxResponse(response, JSONUtil.getJson(map));
	          }else{
	        	   orderId=Integer.parseInt(request.getParameter("orderId"));
	          }
	          if(request.getParameter("userType")==null||"".equals(request.getParameter("userType"))){
	        	 map.put("success", "false");
	  			 map.put("msg","数据有误请联系客服");
	  			return writeAjaxResponse(response, JSONUtil.getJson(map));
	          }else{
	        	  userType=Integer.parseInt(request.getParameter("userType"));
	          }
	        CL_Order order =this.orderDao.getById(orderId);
	        if(order.getFdelOrder()!=0){
	        		 order.setFdelOrder(3);
	        	     this.orderDao.update(order);
	        }
	        if(order.getFdelOrder()==0){
	             switch (userType) {
				   case 1:
					   order.setFdelOrder(1);
		        	   this.orderDao.update(order);
					break;
				   case 2:
					   order.setFdelOrder(2);
		        	   this.orderDao.update(order);
					break;
	      }
	     }
		map.put("success", "true");
		map.put("msg", "修改成功");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	
 }
	/**车主抢单
	 * */
	@RequestMapping("/pcWeb/driver/ownerGrab")
	public String ownerGrab(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer userId, orderId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			RedisUtil
					.Lock("lock.com.pc.pcWeb.pcWebOrder.pcWeb.driver.ownerGrab");

			if (request.getParameter("userId") == null
					|| "".equals(request.getParameter("userId"))) {
				map.put("success", "false");
				map.put("msg", "未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				userId = Integer.valueOf(request.getParameter("userId"));
			}
			if (request.getParameter("orderId") == null
					|| "".equals(request.getParameter("orderId"))) {
				map.put("success", "false");
				map.put("msg", "数据有误请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				orderId = Integer.parseInt(request.getParameter("orderId"));
			}
			// 判断订单是否为未抢状态
			CL_Order orderInfo = orderDao.IsExistIdByStatus(orderId);
			if (orderInfo != null && !orderInfo.equals("")) {
				orderDao.updateByOrderId(userId, userId, orderId, 3);// 运输中
				// ====抢单成功后向货主发送验证码==//
				List<CL_OrderDetail> detailList = new ArrayList<>();
				if (orderInfo.getType() == 3) {
					detailList = orderDetailDao.getByOrderId(orderId, 1);
				} else {
					detailList = orderDetailDao.getByOrderId(orderId, 2);
				}
				CL_UserRole userRole = userRoleDao.getById(userId);
				CL_UserRole userRole1 = userRoleDao.getById(orderDao.getById(
						orderId).getCreator());
				List<CL_Car> cars = cardao.getByUserRoleId(userId);
				DongjingClient djcn = ServerContext.createVmiClient();
				// String
				// msgString1="您的订单已被抢,为您服务的 %s,电话:"+userRole.getVmiUserPhone()+",车牌号:%s,客服:0577-85391111,更多优惠下载手机APP,http://fir.im/d1q4";
				String msgString1 = "您的订单已被抢,为您服务的 %s,电话:"
						+ userRole.getVmiUserPhone()
						+ ",车牌号:%s,客服:0577-85391111";
				// String
				// msgString="您的货物在运输途中,为您服务的 %s,电话:"+userRole.getVmiUserPhone()+",车牌号:"+cars.get(0).getCarNum()+",客服:0577-85391111,更多优惠下载手机APP,http://fir.im/d1q4";
				String msgString = "您的货物在运输途中,为您服务的 %s,电话:"
						+ userRole.getVmiUserPhone() + ",车牌号:"
						+ cars.get(0).getCarNum() + ",客服:0577-85391111";
				djcn.setMethod("getDeatilCode");
				djcn.setRequestProperty("ftel", userRole1.getVmiUserPhone());
				djcn.setRequestProperty("fname", cars.get(0).getDriverName());
				djcn.setRequestProperty("fcode", cars.get(0).getCarNum());
				djcn.setRequestProperty("msgString", msgString1);
				djcn.SubmitData();
				for (CL_OrderDetail detail : detailList) {
					djcn.setMethod("getDeatilCode");
					djcn.setRequestProperty("ftel", detail.getPhone());
					djcn.setRequestProperty("fname", cars.get(0)
							.getDriverName());
					djcn.setRequestProperty("fcode", detail.getSecurityCode());
					djcn.setRequestProperty("msgString", msgString);
					djcn.SubmitData();
				}
				map.put("success", "true");
				map.put("msg", "抢单成功！");
			} else {
				map.put("success", "false");
				map.put("msg", "该单已经被抢！");
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("msg", "该单已经被抢！");
		} finally {
			RedisUtil
					.del("lock.com.pc.pcWeb.pcWebOrder.pcWeb.driver.ownerGrab");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**循环单列表
	 * @throws ParseException */
	@RequestMapping("/pcWeb/order/pcWebcycleorder")
	public String pcWebcycleorder(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		int userId=0,type=0,pageNum=0,pageSize=0,isCommon=0,status=0;
		HashMap<String,Object> map =new HashMap<String,Object>();
//		if(request.getParameter("userId")==null || "".equals(request.getParameter("userId"))){
//			map.put("success", "false");
//			map.put("msg","未登录！");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
//		}else{
//			userId = Integer.parseInt(request.getParameter("userId"));
//		}
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
			map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    Util_UserOnline useronline=ServerContext.getUseronline().get(request.getSession().getId().toString());
	    userId =useronline.getFuserId();
		   
	    status=getRequestParameter(request, response, map, "无法获知订单状态", "status", status); 
	    pageSize=getRequestParameter(request, response, map,"无法获知每页显示多少条", "pagesize", pageSize); 
	    pageNum=getRequestParameter(request, response, map, "无法获知第几页", "pagenum", pageNum); 
//		if(request.getParameter("status")==null || "".equals(request.getParameter("status"))){
//			map.put("success", "false");
//			map.put("msg","无法获知订单状态");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
//		}else{
//			status = Integer.parseInt(request.getParameter("status"));
//		}
//		if(request.getParameter("pagenum")==null || "".equals(request.getParameter("pagenum"))){
//			map.put("success", "false");
//			map.put("msg","无法获知第几页");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
//		}else{
//			pageNum = Integer.valueOf(request.getParameter("pagenum"));
//		}
//		if(request.getParameter("pagesize")==null || "".equals(request.getParameter("pagesize"))){
//			map.put("success", "false");
//			map.put("msg","无法获知每页显示多少条");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
//		}else{
//			pageSize = Integer.valueOf(request.getParameter("pagesize"));
//		}
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   
		orderQuery = newQuery(OrderQuery.class, null);
		if(useronline.isSub()){
			
			orderQuery.setSubUserId(useronline.getFsubId());
		}else{
			orderQuery.setCreator(userId);
		}
		if(request.getParameter("type")!=null && !"".equals(request.getParameter("type"))){
			orderQuery.setType(Integer.parseInt(request.getParameter("type")));
		}
		if(request.getParameter("isCommon")!=null && !"".equals(request.getParameter("isCommon"))){
			orderQuery.setIsCommon(Integer.parseInt(request.getParameter("isCommon")));
		}
		if(request.getParameter("loadedTimeBegin")!=null && !"".equals(request.getParameter("loadedTimeBegin"))){
			orderQuery.setLoadedTimeBegin(sdf.parse(request.getParameter("loadedTimeBegin")));
		}
		if(request.getParameter("loadedTimeEnd")!=null && !"".equals(request.getParameter("loadedTimeEnd"))){
			orderQuery.setLoadedTimeEnd(sdf.parse(request.getParameter("loadedTimeBegin")));
		}
		if(request.getParameter("key")!=null && !"".equals(request.getParameter("key"))){
			 GregorianCalendar gc=new GregorianCalendar(); 
		       Date a=new Date();
		       gc.setTime(a);
		       Integer tie=0;
		       String time1=sdf.format(a);
		       java.util.Date timeDate1=sdf.parse(time1);
		       java.util.Date time2=new Date();
		    	tie=Integer.valueOf(request.getParameter("key").toString());
		    	if(tie==1){
		    		gc.add(GregorianCalendar.DATE, -3);
		    		time2=sdf.parse(sdf.format(gc.getTime()));
		    	}
		    	if(tie==2){
		    		gc.add(GregorianCalendar.DATE, -7);
		    		time2=sdf.parse(sdf.format(gc.getTime()));
		    	}
		    	if(tie==3){
		    		gc.add(GregorianCalendar.MONTH, -1);
		    		time2=sdf.parse(sdf.format(gc.getTime()));
		    	}
		    	orderQuery.setLoadedTimeBegin(time2);
		    	orderQuery.setLoadedTimeEnd(timeDate1);
		}
		  
		orderQuery.setPageNumber(pageNum);
		orderQuery.setPageSize(pageSize);
		orderQuery.setStatus(null);
		orderQuery.setHuoLoadOrder(status);
		Page<CL_Order> page = orderDao.findPage(orderQuery);
		List<CL_Order> list =page.getResult();
		for(CL_Order order:list){
			CL_OrderDetail detailOne = this.orderDetailDao.getByOrderIdForDeliverAddress(order.getId());
			if(detailOne==null){
				map.put("success", "false");
				map.put("msg","订单编号为: "+order.getNumber()+"   数据遗失请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			order.setTakephone(detailOne.getPhone());
			order.setTakelinkman(detailOne.getLinkman());
			order.setTakeAddress(detailOne.getAddressName());
			if(order.getType()!=3 ){
				CL_OrderDetail detailTwo = this.orderDetailDao.getByOrderIdForConsigneeAddress(order.getId());
				if(detailTwo==null){
					map.put("success", "false");
					map.put("msg","订单编号为: "+order.getNumber()+"  数据遗失请联系客服");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				order.setRecphone(detailTwo.getPhone());
				order.setReclinkman(detailTwo.getLinkman());
				order.setRecAddress(detailTwo.getAddressName());
			}
			List<Util_Option>  ups=optionDao.getCarTypeByOrderIdName(order.getId());
			String carSpecName="",carOtherName="";
			Integer carSpecId=null;
			if(ups.size()>0){
				carSpecId=ups.get(0).getOptionId();
				carSpecName=ups.get(0).getOptionName();
				carOtherName =ups.get(0).getOptionCarOtherName();
			}
			for(Util_Option option :ups){
				if(!carSpecName.contains(option.getOptionCarTypeName())){
					carSpecName = carSpecName+" "+option.getOptionCarTypeName();
				}
				if(carOtherName!=null && "".equals(carOtherName)&&!carOtherName.contains(option.getOptionCarOtherName())&&!"".equals(option.getOptionCarOtherName())&&option.getOptionCarOtherName()!=null){
					carOtherName=carOtherName+" "+option.getOptionCarOtherName();
				}
			}
			if(carOtherName==null ||"".equals(carOtherName)){
				carSpecName=carSpecName+" "+order.getGoodsTypeName();
			}
			else if(carOtherName!=null&&!"".equals(carOtherName)){
				carSpecName=carSpecName+" "+carOtherName+" "+order.getGoodsTypeName();
			}
			order.setCarTypeName(carSpecName);
			order.setCarSpecId(carSpecId);
			//	 		List<Util_Option> gbbspec = this.optionDao.getCarSpecByOrderId(order.getId());
			//	 		if(carspec.size()>0){
			//	 			String carSpec =null;
			//	 			carSpec = carspec.get(0).getOptionName()+" ";
			//	 			List<Util_Option> cartype = this.optionDao.getCarTypeByOrderId(order.getId());
			//	 			String carType="";
			//	 			for(Util_Option option :cartype){
			//	 				carType = carType+","+option.getOptionName();
			//	 			}
			//	 			order.setCarTypeName(carSpec+" "+carType);
			//	 		}
		}
		map.put("success", "true");
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**车辆位置*/
	@RequestMapping("/pcWeb/order/carposition")
	public String carposition(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userId,status,pageNum ,pageSize;
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(request.getParameter("userId")==null || "".equals(request.getParameter("userId"))){
			map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			userId = Integer.parseInt(request.getParameter("userId"));
		}
		if(request.getParameter("status")==null || "".equals(request.getParameter("status"))){
			map.put("success", "false");
			map.put("msg","无法获知追踪状态！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			status = Integer.parseInt(request.getParameter("status"));
		}
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
		orderQuery = newQuery(OrderQuery.class, null);
		orderQuery.setCreator(userId);
		orderQuery.setStatus(status);
		orderQuery.setPageNumber(pageNum);
		orderQuery.setPageSize(pageSize);
		Page<CL_Order> page = orderDao.findPage(orderQuery);
		List<CL_Order> list =page.getResult();
		for(CL_Order order:list){
			CL_OrderDetail detailOne = this.orderDetailDao.getByOrderIdForDeliverAddress(order.getId());
			if(detailOne==null){
				map.put("success", "false");
				map.put("msg","订单编号为: "+order.getNumber()+"   数据遗失请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			order.setTakephone(detailOne.getPhone());
			order.setTakelinkman(detailOne.getLinkman());
			order.setTakeAddress(detailOne.getAddressName());
			CL_OrderDetail detailTwo = this.orderDetailDao.getByOrderIdForConsigneeAddress(order.getId());
			if(detailTwo==null){
				map.put("success", "false");
				map.put("msg","订单编号为: "+order.getNumber()+"  数据遗失请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			order.setRecphone(detailTwo.getPhone());
			order.setReclinkman(detailTwo.getLinkman());
			order.setRecAddress(detailTwo.getAddressName());
			if(order.getUserRoleId()!=null){
				order.setOrderDriverName(cardao.getCarTypeByUserRoleId(order.getUserRoleId()).getDriverName());
			}
		}
		map.put("success", "true");
		map.put("total", page.getTotalCount());
		map.put("data", list);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**取消订单*/
	@RequestMapping("/pcWeb/order/cancelorder")
	public String cancelorder(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer userId,orderId;
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		
		if(request.getParameter("orderId")==null||"".equals(request.getParameter("orderId"))){
			map.put("success", "false");
			map.put("msg","数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			orderId=Integer.parseInt(request.getParameter("orderId"));
		}
		CL_Order order = this.orderDao.getById(orderId);
		//将发送退款请求放到服务端，确保支付退款与业务系统状态一致  BY CC 2016-03-04 START
		if(order.getStatus()!=1 )
		{
			map.put("success", "false");
			map.put("msg","该订单不能取消");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		//将发送退款请求放到服务端，确保支付退款与业务系统状态一致  BY CC 2016-03-04 END		
		order.setStatus(6);
		order.setOperator(userId);
		this.orderDao.update(order);
		map.put("success", "true");
		map.put("msg", "操作成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/** 再次下单接口*/
	@RequestMapping("/pcWeb/order/againorder")
	public String againorder(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userId,orderId;
		HashMap<String,Object> map =new HashMap<String,Object>();
	     
	     if(request.getSession().getId()==null || "".equals(request.getSession().getId()) || !ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	 map.put("success", "false");
				map.put("msg","未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
	     }
			
	    Util_UserOnline userInfo=ServerContext.getUseronline().get(request.getSession().getId().toString());
	    userId =userInfo.getFuserId();
	    
		if(request.getParameter("orderId")==null||"".equals(request.getParameter("orderId"))){
			map.put("success", "false");
			map.put("msg","数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			orderId=Integer.parseInt(request.getParameter("orderId"));
		}
		CL_Order order = this.orderDao.getById(orderId);
		order.setTakeList(this.orderDetailDao.getByOrderId(orderId, 1));
		order.setRecList(this.orderDetailDao.getByOrderId(orderId, 2));
		order.setCarList(this.orderCarDetailDao.getByOrderId(orderId));
		map.put("success", "true");
		map.put("data", order);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	/**货主订单跟踪*/
	@RequestMapping("/pcWeb/shipper/getCarposition")
	public String getCarposition(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userId;
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(request.getParameter("userId")==null || "".equals(request.getParameter("userId"))){
			map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			userId = Integer.valueOf(request.getParameter("userId"));
		}
		String[] st = null;
		st = ServerContext.getDriverPosition().get(Integer.toString(userId));
		if(st != null && st.length>0){
			String Longitude = st[0];
			String Latitude = st[1];
			map.put("success", "true");
			map.put("data", "[{\"Longitude\":\""+Longitude+"\",\"Latitude\":\""+Latitude+"\"}]");
		}else{
			map.put("success", "false");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/*** 用户反馈 新建-保存*/
	@RequestMapping("/pcWeb/feedback/feedbackSave")
	public String feedbackSave(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_Feedback feedback) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(request.getParameter("content")==null || "".equals(request.getParameter("content"))){
			map.put("success", "false");
			map.put("msg","请先录入反馈信息！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		feedback.setCreateTime(new Date());
		feedback.setCreatorId(Integer.parseInt(request.getParameter("userId").toString()));
		feedback.setContent(request.getParameter("content"));
		feedback.setFphone(request.getParameter("fphone"));
		this.userRoleDao.saveFeedback(feedback);
		map.put("success", "true");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/*** 获取用户已成单次数、被评价次数*/
	@RequestMapping("/pcWeb/order/getEndOrderCount")
	public String getEndOrderCount(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_Feedback feedback) throws Exception{
		Integer userId;
		HashMap<String,Object> map =new HashMap<String,Object>();
		try{
			if(request.getParameter("userId")==null || "".equals(request.getParameter("userId"))){
				map.put("success", "false");
				map.put("msg","未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				userId = Integer.valueOf(request.getParameter("userId"));
			}
			CL_UserRole userRole=userRoleDao.getById(userId);
			map.put("success", "true");
			map.put("data", "[{\"endOrderTimes\":\""+userRole.getEndOrderTimes()+"\",\"rateTimes\":\""+userRole.getRateTimes()+"\"}]");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}catch(Exception e){
			map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
	}

	@RequestMapping("/pcWeb/order/TimeChangeByCar")
	public String TimeChangeByCar(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_Feedback feedback) throws Exception{
		Integer orderId,type;
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(request.getParameter("type")==null || "".equals(request.getParameter("type"))){
			map.put("success", "false");
			map.put("msg","类型有误！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			type=Integer.valueOf(request.getParameter("type").toString());
		}
		if(request.getParameter("orderId")==null || "".equals(request.getParameter("orderId"))){
			map.put("success", "false");
			map.put("msg","订单有误！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			orderId=Integer.valueOf(request.getParameter("orderId").toString());
		}
		if(type==1){
			orderDao.updateByArriveTime(orderId, new Date());
		}
		if(type==2){
			orderDao.updateByLeaveTime(orderId, new Date());
		}
		map.put("success", "true");
		map.put("msg","操作成功");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/** 锁定金额查询(一星期) */
	@RequestMapping("/pcWeb/order/lockedAmount") 
	public String LockedAmount(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer urid=0;
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(request.getParameter("urid")==null||"".equals(request.getParameter("urid")))
		{
			map.put("success", "false");
			map.put("msg","用户ID为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else
		{
			urid=Integer.parseInt(request.getParameter("urid"));
		}
		//根据司机ID查询订单表中所有的金额，条件大于当前日期减7
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();  
        c.add(Calendar.DATE, - 7);  
//        Date fcopTime = c.getTime();
        Date fcopTime = sdf.parse((sdf.format(c.getTime())));
        String lockedMoney =orderDao.getLockedAmount(urid, fcopTime);
		map.put("success", "true");
		map.put("data", lockedMoney);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	@RequestMapping("/pcWeb/order/receiptSave")
//	@Transactional(propagation=Propagation.REQUIRED)
    public String receiptSave(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		 System.out.println("ssssssssssssssssssssssssssssssssssss");
		 request.setCharacterEncoding("UTF-8");
		 Integer userId,ftakeproblem,frecproblem,fcarproblem,orderId,type,orderDetailId;
		 HashMap<String,Object> map =new HashMap<String,Object>();
		 if(request.getParameter("userId")==null || "".equals(request.getParameter("userId"))){
		     map.put("success", "false");
			 map.put("msg","未登录！");
			 return writeAjaxResponse(response, JSONUtil.getJson(map));
		 }else{
		    userId=Integer.valueOf(request.getParameter("userId").toString());
		 }
		 if(request.getParameter("orderId")==null || "".equals(request.getParameter("orderId"))){
		     map.put("success", "false");
			 map.put("msg","订单数据有误,请联系客服！");
			 return writeAjaxResponse(response, JSONUtil.getJson(map));
		 }else{
			 orderId=Integer.valueOf(request.getParameter("orderId").toString());
		 }
		 if(request.getParameter("orderDetailId")==null || "".equals(request.getParameter("orderDetailId"))){
				map.put("success", "false");
				map.put("msg","订单数据ID有误,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				orderDetailId = Integer.parseInt(request.getParameter("orderDetailId"));
			}
		/* List<Cl_Upload> cupde=iuploadDao.getByOrderIdAndByMode(orderDetailId, "cl_receiptSave");
		  if(cupde.size()>0){
				iuploadDao.deleteByMode(cupde.get(0).getId(), cupde.get(0).getModelName());
			}*/
		 CL_OrderDetail orderDetail = orderDetailDao.getById(orderDetailId);
			if (orderDetail.getFstatus() == 0) {
		  	request.setCharacterEncoding("UTF-8");
//			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory()); // 创建一个新的文件上传对象
//			upload.setHeaderEncoding("utf-8");//避免中文名乱码
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
			try {	
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 表单中对应的文件名；
				Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//				List<FileItem> list  = upload.parseRequest(request);
//				File file = new File(request.getServletContext().getRealPath("\\"));//获取当前路径
//				String fpath = 	file.getParent()+"/csclreceiptfile/"+df.format(new Date())+"/"+dfs.format(new Date());//构造按当前日期保存文件的路径 yyyy-MM-dd
//				String fname = file.getName();//文件名
//				file = new File(fpath);
//				if(!file.exists()){
//					file.mkdirs();
//				}
				for (Map.Entry<String, MultipartFile> item : fileMap.entrySet()) {
					MultipartFile multifile = item.getValue();
					SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
					String fname = sf.format(new Date())+"-"+multifile.getOriginalFilename();
					try {
						//新增附件信息
						Cl_Upload up = new Cl_Upload();
//						int m =fpath.indexOf("csclreceiptfile");
//						String newPath = fpath.substring(m,fpath.length());
							up.setName(fname);
							up.setUrl(FastDFSUtil.upload_could_change_file_url(multifile.getBytes(),FilenameUtils.getExtension(fname)));
							up.setParentId(orderDetailId);
							//up.setParentId
							up.setRemark("回单上传,订单明细Id");
							up.setCreateTime(new Date());
							up.setModelName("cl_receiptSave");
							up.setCreateId(userId);
							this.iuploadDao.save(up);
							orderDetail.setFstatus(1);
							orderDetailDao.update(orderDetail);
							return this.poClient(response, true, "操作成功!");
//						file = new File(fpath,fname);
//						DataOutputStream out=new DataOutputStream(new FileOutputStream(fpath+"/"+fname));
//						InputStream is=null;
//						try {
//							is = multifile.getInputStream();
//							byte[] b = new byte[is.available()];
//							is.read(b);
//							out.write(b);
//						} catch (Exception e) {
//							// TODO: handle exception
//						} finally {
//							if (is != null) {
//								is.close();
//							}
//							if (out != null) {
//								out.close();
//							}
//						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			} else {
				return this.poClient(response, false, "Move your hands!");
			}
			return this.poClient(response, false, "异常!");
  
		
		 /*System.out.println("ssssssssssssssssssssssssssssssssssss");
		 request.setCharacterEncoding("UTF-8");
		 Integer userId,ftakeproblem,frecproblem,fcarproblem,orderId,type,orderDetailId;
		 HashMap<String,Object> map =new HashMap<String,Object>();
		 if(request.getParameter("userId")==null || "".equals(request.getParameter("userId"))){
		     map.put("success", "false");
			 map.put("msg","未登录！");
			 return writeAjaxResponse(response, JSONUtil.getJson(map));
		 }else{
		    userId=Integer.valueOf(request.getParameter("userId").toString());
		 }
		 if(request.getParameter("orderId")==null || "".equals(request.getParameter("orderId"))){
		     map.put("success", "false");
			 map.put("msg","订单数据有误,请联系客服！");
			 return writeAjaxResponse(response, JSONUtil.getJson(map));
		 }else{
			 orderId=Integer.valueOf(request.getParameter("orderId").toString());
		 }
		 if(request.getParameter("orderDetailId")==null || "".equals(request.getParameter("orderDetailId"))){
				map.put("success", "false");
				map.put("msg","订单数据ID有误,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				orderDetailId = Integer.parseInt(request.getParameter("orderDetailId"));
			}
		 List<Cl_Upload> cupde=iuploadDao.getByOrderIdAndByMode(orderDetailId, "cl_receiptSave");
		  if(cupde.size()>0){
				iuploadDao.deleteByMode(cupde.get(0).getId(), cupde.get(0).getModelName());
			}
		  	request.setCharacterEncoding("UTF-8");
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory()); // 创建一个新的文件上传对象
			upload.setHeaderEncoding("utf-8");//避免中文名乱码
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
			try {	
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 表单中对应的文件名；
				Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
				List<FileItem> list  = upload.parseRequest(request);
				File file = new File(request.getServletContext().getRealPath("\\"));//获取当前路径
				String fpath = 	file.getParent()+"/csclreceiptfile/"+df.format(new Date())+"/"+dfs.format(new Date());//构造按当前日期保存文件的路径 yyyy-MM-dd
				String fname = file.getName();//文件名
				file = new File(fpath);
				if(!file.exists()){
					file.mkdirs();
				}
				for (Map.Entry<String, MultipartFile> item : fileMap.entrySet()) {
					MultipartFile multifile = item.getValue();
					SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
					fname = sf.format(new Date())+"-"+multifile.getOriginalFilename();
					try {
						//新增附件信息
						Cl_Upload up = new Cl_Upload();
						int m =fpath.indexOf("csclreceiptfile");
						String newPath = fpath.substring(m,fpath.length());
							up.setName(fname);
							up.setUrl(newPath+"/"+fname);
							up.setParentId(orderDetailId);
							//up.setParentId
							up.setRemark("回单上传,订单明细Id");
							up.setCreateTime(new Date());
							up.setModelName("cl_receiptSave");
							up.setCreateId(userId);
						this.iuploadDao.save(up);
						this.orderDetailDao.updateFrea(orderDetailId,2);
						file = new File(fpath,fname);
						DataOutputStream out=new DataOutputStream(new FileOutputStream(fpath+"/"+fname));
						InputStream is=null;
						try {
							is = multifile.getInputStream();
							byte[] b = new byte[is.available()];
							is.read(b);
							out.write(b);
						} catch (Exception e) {
							// TODO: handle exception
						} finally {
							if (is != null) {
								is.close();
							}
							if (out != null) {
								out.close();
							}
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			} catch (FileUploadException e) {
				throw new RuntimeException(e);
			}
			map.put("success", "true");
			map.put("msg","操作成功!");
//			System.out.println("************上传打印******************");
//			System.out.println(JSONUtil.getJson(map));
//			System.out.println("************上传打印******************");
			return writeAjaxResponse(response, JSONUtil.getJson(map));*/
   }
	/** 查询司机排名前三*/
	@RequestMapping("/pcWeb/order/carRankingLoad") 
	public String carRankingLoad(HttpServletRequest request,HttpServletResponse response) throws Exception{
		  HashMap<String,Object> map=new HashMap<String,Object>();
			List<CL_CarRanking> rankingList=icarRankingDao.getAll();
			if(rankingList.size()>0){
				for(CL_CarRanking ras:rankingList){
					ras.setFname(ras.getFname().substring(0,1)+"师傅");
					ras.setFnumber(ras.getFnumber().substring(0,ras.getFnumber().length()-2)+"**");
					ras.setType(999);//用于抢单列表特殊 显示 
				}
			}
			map.put("success", "true");
			map.put("data", rankingList);
			System.out.println("-----------------返回排名----------------------");
			System.out.println(JSONUtil.getJson(map));
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	 }

	
}
