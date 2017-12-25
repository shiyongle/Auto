package com.pc.controller.couponsActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.couponsActivity.impl.CouponsActivityDaoImpl;
import com.pc.dao.message.ImessageDao;
import com.pc.dao.umeng.IUMengPushDao;
import com.pc.model.CL_CouponsActivity;
import com.pc.model.CL_Umeng_Push;
import com.pc.model.CL_UserRole;
import com.pc.query.couponsActivity.CouponsActivityQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;
import com.pc.util.push.Demo;

@Controller
public class CouponsActivityController extends BaseController {
	
	protected static final String LIST_JSP = "/pages/pc/couponsActivity/couponsActivity_list.jsp";
	protected static final String CREATE_JSP = "/pages/pc/couponsActivity/couponsActivity_create.jsp";
	protected static final String EDIT_JSP = "/pages/pc/couponsActivity/couponsActivity_edit.jsp";
	protected static final String VIEW_JSP = "/pages/pc/couponsActivity/couponsActivity_view.jsp";
	
	@Resource
	private CouponsActivityDaoImpl couponsActivityDao;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private IUMengPushDao iumeng;
	@Resource
	private ImessageDao messageDao;
	
	@RequestMapping("/couponsActivity/list")
	public String list(HttpServletRequest request, HttpServletResponse response){
		return LIST_JSP;
	}
	
	@RequestMapping("/couponsActivity/create")
	public String create(HttpServletRequest request, HttpServletResponse response){
		return CREATE_JSP;
	}
	
	@RequestMapping("/couponsActivity/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, Integer id){
		HashMap<String, Object> map = new HashMap<String, Object>();
		CL_CouponsActivity couponsActivity = couponsActivityDao.getById(id);
		if(couponsActivity.getFeffective() != 2){//已经生效1与过期3的活动不允许修改
			map.put("success", "false");
			map.put("msg", "请选择待生效的活动");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		request.setAttribute("couponsActivity", couponsActivity);
		return EDIT_JSP;
	}
	
	@RequestMapping("/couponsActivity/change")
	public String change(HttpServletRequest request, HttpServletResponse response, Integer id){
		HashMap<String, Object> map = new HashMap<String, Object>();
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		if(user == null){
			map.put("success", "false");
			map.put("msg", "非我族类！");
		}
		CL_CouponsActivity couponsActivity = couponsActivityDao.getById(id);
		if(couponsActivity.getFeffective() == 1){//已经生效1的改为失效
			couponsActivity.setFeffective(2);
			couponsActivity.setOperator(user.getId());
			couponsActivity.setOperateTime(new Date());
			couponsActivityDao.update(couponsActivity);
			map.put("success", "true");
			map.put("msg", "已失效");
		} else if (couponsActivity.getFeffective() == 2) {
			couponsActivity.setFeffective(1);
			couponsActivity.setOperator(user.getId());
			couponsActivity.setOperateTime(new Date());
			couponsActivityDao.update(couponsActivity);
			map.put("success", "true");
			map.put("msg", "已生效");
		} else {
			map.put("success", "false");
			map.put("msg", "该状态不允许修改");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	@RequestMapping("/couponsActivity/view")
	public String view(HttpServletRequest request, HttpServletResponse response, Integer id){
		CL_CouponsActivity couponsActivity = couponsActivityDao.getById(id);
		request.setAttribute("couponsActivity", couponsActivity);
		return VIEW_JSP;
	}
	
	@RequestMapping("/couponsActivity/load")
	public String load(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CouponsActivityQuery couponsActivityQuery){
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (couponsActivityQuery == null) {
			couponsActivityQuery = newQuery(CouponsActivityQuery.class, null);
		}
		if (pageNum != null) {
			couponsActivityQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			couponsActivityQuery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_CouponsActivity> page = couponsActivityDao.findPage(couponsActivityQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/couponsActivity/save")
	public String save(HttpServletRequest request, HttpServletResponse response, @ModelAttribute final CL_CouponsActivity couponsActivity, Integer[] ids, String activityStartTime, String activityEndTime, String useStartTime, String useEndTime) throws Exception{
		HashMap<String, Object> m = new HashMap<>();
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		if(user == null){
			m.put("success", "false");
			m.put("msg", "非我族类！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		DateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(couponsActivity.getFactivityType() != null && couponsActivity.getFactivityType() == 1){//充值送的活动
			String topUpActivityString = request.getParameter("topUpActivityString");
			JSONArray topUpActivity = JSONArray.fromObject(topUpActivityString);
			for (int i = 0; i < topUpActivity.size(); i++) {
				JSONObject js = topUpActivity.getJSONObject(i);
				String js1 = (String)js.get("ides");// 优惠模板id
				String[] ides = js1.split(",");
				BigDecimal dollars = new BigDecimal(js.get("dollars").toString());// 充值金额
				if (activityStartTime != null && !"".equals(activityStartTime)) {
					couponsActivity.setFactivityStartTime(fmtDateTime.parse(activityStartTime));
				}
				if (activityEndTime != null && !"".equals(activityEndTime)) {
					if (fmtDateTime.parse(activityEndTime).before(new Date())) {
						m.put("success", "false");
						m.put("msg", "你这活动都结束了还创建个毛！");
						return writeAjaxResponse(response, JSONUtil.getJson(m));
					}
					couponsActivity.setFactivityEndTime(fmtDateTime.parse(activityEndTime));
				}
				if (useStartTime != null && !"".equals(useStartTime)) {
					couponsActivity.setFuseStartTime(fmtDateTime.parse(useStartTime));
				}
				if (useEndTime != null && !"".equals(useEndTime)) {
					couponsActivity.setFuseEndTime(fmtDateTime.parse(useEndTime));
				}
				for (String id : ides) {
					couponsActivity.setFtopUpDollars(dollars);
					couponsActivity.setFcouponsId(Integer.parseInt(id));
					couponsActivity.setFeffective(1);// 活动刚生成时先定位待生效
					couponsActivity.setCreator(user.getId());
					couponsActivity.setCreateTime(new Date());
					couponsActivity.setOperator(user.getId());
					couponsActivity.setOperateTime(new Date());
					couponsActivityDao.save(couponsActivity);
				}
				m.put("success", "success");
				m.put("msg", "保存成功!");
			}
		} else {
			if(activityStartTime != null && !"".equals(activityStartTime)){
				couponsActivity.setFactivityStartTime(fmtDateTime.parse(activityStartTime));
			}
			if(activityEndTime != null && !"".equals(activityEndTime)){
				if(fmtDateTime.parse(activityEndTime).before(new Date())){
					m.put("success", "false");
					m.put("msg", "你这活动都结束了还创建个毛！");
					return writeAjaxResponse(response, JSONUtil.getJson(m));
				}
				couponsActivity.setFactivityEndTime(fmtDateTime.parse(activityEndTime));
			}
			if(useStartTime != null && !"".equals(useStartTime)){
				couponsActivity.setFuseStartTime(fmtDateTime.parse(useStartTime));
			}
			if(useEndTime != null && !"".equals(useEndTime)){
				couponsActivity.setFuseEndTime(fmtDateTime.parse(useEndTime));
			}
			for (Integer id : ids) {
				couponsActivity.setFcouponsId(id);
				couponsActivity.setFeffective(1);//活动刚生成时先定位待生效
				couponsActivity.setCreator(user.getId());
				couponsActivity.setCreateTime(new Date());
				couponsActivity.setOperator(user.getId());
				couponsActivity.setOperateTime(new Date());
				couponsActivityDao.save(couponsActivity);
			}
			new Thread(){
				public void run(){
					String issueUser = couponsActivity.getFissueUser();//所有指派的用户手机号
					String[] userPhoneArr = issueUser.split(",");
					for (int i = 0; i < userPhoneArr.length; i++) {
						sendUmengToService(userPhoneArr[i], "优惠券活动", "您有新的优惠券可以领取");
					}
				}
			}.start();
			m.put("success", "success");
			m.put("msg", "保存成功!");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/couponsActivity/update")
	public String update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CL_CouponsActivity couponsActivity){
		HashMap<String, Object> m = new HashMap<>();
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		if(user == null){
			m.put("success", "false");
			m.put("msg", "非我族类！");
		}
		couponsActivity.setOperator(user.getId());
		couponsActivity.setOperateTime(new Date());
		couponsActivityDao.update(couponsActivity);
		m.put("success", "success");
		m.put("msg", "修改成功!");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/**
	 * 发布活动
	 * @param request
	 * @param response
	 * @param ids
	 * @return
	 */
	@RequestMapping("/couponsActivity/issue")
	public String issue(HttpServletRequest request, HttpServletResponse response, Integer[] ids){
		HashMap<String, Object> map = new HashMap<String, Object>();
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		if(user == null){
			map.put("success", "false");
			map.put("msg", "非我族类！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		for (Integer id : ids) {
			CL_CouponsActivity couponsActivity = couponsActivityDao.getById(id);
			if(couponsActivity.getFeffective() != 2){
				map.put("success", "false");
				map.put("msg", "请选择待生效的活动");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			couponsActivity.setFeffective(1);//发布
			couponsActivity.setOperator(user.getId());
			couponsActivity.setOperateTime(new Date());
			couponsActivityDao.update(couponsActivity);
		}
		map.put("success", "true");
		map.put("msg", "发布成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	@RequestMapping("/couponsActivity/exportExecl")
	public String exportExecl(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CouponsActivityQuery couponsActivityQuery, Integer[] ids) throws Exception{
			boolean ises =true;
			/***************/
			Map<String, Object> result = new HashMap<String, Object>();
			WritableWorkbook wwb = null;
			OutputStream os = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "cps_"+format.format(new Date())+".xls";
			String path  = CouponsActivityController.class.getResource("").toURI().getPath();
			path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
			try {
				File file = new File(path);
				if(!file.isFile()){
					file.createNewFile();
				}
				os = new FileOutputStream(file);
				wwb = Workbook.createWorkbook(os);
				WritableSheet wsheet = wwb.createSheet("优惠活动", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				SheetSettings ss = wsheet.getSettings();
	            ss.setVerticalFreeze(2);  // 设置行冻结前2行
	            WritableFont font1 =new WritableFont(WritableFont.createFont("微软雅黑"), 10 ,WritableFont.BOLD);
	            WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);
	            WritableCellFormat wcf = new WritableCellFormat(font1);	  //设置样式，字体
		            wcf.setAlignment(Alignment.CENTRE);                   //平行居中
		            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   //垂直居中
	            WritableCellFormat wcf2 = new WritableCellFormat(font2);  //设置样式，字体
		            wcf2.setBackground(Colour.LIGHT_ORANGE);
		            wcf2.setAlignment(Alignment.CENTRE);                  //平行居中
		            wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
		            wcf2.setWrap(true);  
	            wsheet.mergeCells( 0 , 0 , 14 , 0 ); // 合并单元格  
	            Label titleLabel = new Label( 0 , 0 , " 同城物流调度平台 ",wcf);
	            wsheet.addCell(titleLabel);
	            wsheet.setRowView(0, 1000); // 设置第一行的高度
	            int[] headerArrHight = {7,10,10,25,15,15,25,25,25,25,15,25};
	            String headerArr[] = {"序号","前置条件","业务类型","优惠券名称","优惠活动状态","优惠券类型","活动开始时间","活动结束时间","使用有效期开始时间","使用有效期结束时间","使用有效期(天)","操作时间"};
	            for (int i = 0; i < headerArr.length; i++) {
	            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
	            	wsheet.setColumnView(i, headerArrHight[i]);
	            }
	            if (couponsActivityQuery == null) {
	            	couponsActivityQuery = newQuery(CouponsActivityQuery.class, null);
	    		}
	            if(ids!=null){
	            	couponsActivityQuery.setIds(ids);
	            }
	    		List<CL_CouponsActivity> list = this.couponsActivityDao.find(couponsActivityQuery);
	    		int conut = 2;
	    		for(int i=0;i<list.size();i++){
	    			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
	    			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
	    			wcfN2.setBackground(Colour.LIGHT_ORANGE);
	    			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
	    			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
	    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
	    			if(list.get(i).getFactivityType() != null && list.get(i).getFactivityType() == 1){
	    				wsheet.addCell(new Label( 1 , conut ,"充值送",wcf2));
	    			} else {
	    				wsheet.addCell(new Label( 1 , conut ,"发放人群",wcf2));
					}
	    			wsheet.addCell(new Label( 2 , conut ,list.get(i).getFbusinessType(),wcf2));
	    			wsheet.addCell(new Label( 3 , conut ,list.get(i).getFcouponsName(),wcf2));
	    			wsheet.addCell(new Label( 4 , conut ,formatEffective(list.get(i).getFeffective()),wcf2));
	    			if(list.get(i).getFcouponsType() == 1){
	    				if(list.get(i).getFdiscount() != null){
	    					wsheet.addCell(new Label( 5 , conut ,"折扣", wcfN2));
	    				} else if(list.get(i).getFsubtract() != null) {
	    					wsheet.addCell(new Label( 5 , conut ,"直减", wcfN2));
						} else {
							wsheet.addCell(new Label( 5 , conut ,"错误类型", wcfN2));
						}
	    				
	    			} else if (list.get(i).getFcouponsType() == 2) {
	    				wsheet.addCell(new Label( 5 , conut ,list.get(i).getFaddserviceName(), wcfN2));
					} else {
						wsheet.addCell(new Label( 5 , conut ,"错误类型", wcfN2));
					}
	    			wsheet.addCell(new Label( 6 , conut ,list.get(i).getFactivityStartTimeString() ,wcf2));
	    			wsheet.addCell(new Label( 7 , conut ,list.get(i).getFactivityEndTimeString() ,wcf2));
	    			wsheet.addCell(new Label( 8 , conut ,list.get(i).getFuseStartTimeString() ,wcf2));
	    			wsheet.addCell(new Label( 9 , conut ,list.get(i).getFuseEndTimeString() ,wcf2));
	    			wsheet.addCell(new Label( 10 , conut ,list.get(i).getFuseTime() == null?"":list.get(i).getFuseTime().toString() ,wcf2));
	    			wsheet.addCell(new Label( 11 , conut ,list.get(i).getOperateTimeString() ,wcf2));
	    			conut++;
	    		}
				wwb.write();
				os.flush();
			} catch (Exception e) {
				ises =false;
				e.printStackTrace();
			}finally{
				try {
					if(wwb != null){
						wwb.close();
					}
					if(os != null){
						os.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			result.put("url", fileName);
			if(ises==true){
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		
			return writeAjaxResponse(response,JSONUtil.getJson(result));
	}
	
	public String formatEffective(Integer value){
		if(value == 1){
			return "有效";
		}else if(value == 2){
			return "待生效";
		} else if(value == 3) {
			return "过期";
		} else {
			return "状态有误";
		}
	}
	
	/**
	 * 推送服务
	 * 
	 * @param userPhone
	 *            推送给的用户手机
	 * @param msg
	 *            推送内容
	 */
	public void sendUmengToService(String userPhone, String title,String msg) {

		List<CL_Umeng_Push> umengList = iumeng.getUserPhone(userPhone);
		if (umengList .size() > 0) {
			// 获取到用户多台设备
			for (CL_Umeng_Push cl_Umeng_Push : umengList) {
				Demo.SendUmeng(cl_Umeng_Push.getFdevice(), msg, cl_Umeng_Push.getFdeviceType());
			}
			messageDao.save(Demo.savePushMessage(title,msg, userPhone, umengList.get(0).getFuserType(),1));
		}
	}
	
}
