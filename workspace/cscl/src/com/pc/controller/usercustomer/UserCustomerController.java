package com.pc.controller.usercustomer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.usercustomer.IUserCustomerDao;
import com.pc.model.CL_UserCustomer;
import com.pc.query.usercustomer.UserCustomerQuery;
import com.pc.util.JSONUtil;

@Controller
public class UserCustomerController extends BaseController{	
	protected static final String LIST_JSP= "/pages/pc/customer/customer_list.jsp";
	protected static final String EDIT_JSP= "/pages/pc/customer/edit_customer.jsp";
	@Resource
	private IUserCustomerDao userCustomerDao;
	//private UserCustomerQuery userCustomerQuery;
	
	@RequestMapping("/usercustomer/saveCustomer")
	public String  saveInfo(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_UserCustomer ucinfo) throws Exception{
		ucinfo.setFcreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
		ucinfo.setFcreateTime(new Date());
		//coupons.setRedeemCode(MD5Util.getUUIDNumber());
		this.userCustomerDao.save(ucinfo);
		return writeAjaxResponse(reponse, "success");
	}
	
	@RequestMapping("/usercustomer/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return LIST_JSP;
	}	
	//加载列表数据
	@RequestMapping("/usercustomer/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute UserCustomerQuery userCustomerQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (userCustomerQuery == null) {
			userCustomerQuery = newQuery(UserCustomerQuery.class, null);
		}
		if (pageNum != null) {
			userCustomerQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			userCustomerQuery.setPageSize(Integer.parseInt(pageSize));
		}
		//调度平台用户,只查询运营角色
		Page<CL_UserCustomer> page = userCustomerDao.findPage(userCustomerQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("total", page.getTotalCount());
			m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	
	@RequestMapping("/usercustomer/editCustomer")
	public String editCustomer(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		int fid = Integer.parseInt(request.getParameter("fid"));
		CL_UserCustomer custinfo = userCustomerDao.getById(fid);
		request.setAttribute("custinfo", custinfo);
		return EDIT_JSP;
	}

	@RequestMapping("/usercustomer/updateCustomer")
	public String updateCustomer(HttpServletRequest request,HttpServletResponse reponse,CL_UserCustomer ucinfo) throws Exception{
		userCustomerDao.update(ucinfo);
		return writeAjaxResponse(reponse, "success");
	}
	
	/**导出excel**/
	@RequestMapping("/usercustomer/exportExecl")
	public String exportExecl(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute UserCustomerQuery ucquery,Integer[] ids) throws Exception{
		boolean ises =true;
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "cps_"+format.format(new Date())+".xls";
		String path  = UserCustomerController.class.getResource("").toURI().getPath();
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("客户资料", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
            int[] headerArrHight = {7,20,15,10,20,20,10,20,25,15,30,10,20,10,25};
            String headerArr[] = {"序号","客户编码","客户名称","客户简称","登录帐号","登录手机","客户类型","客户所在区域","业务员","业务员所在部门","结算周期","认证时间"};
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);
            }
            if (ucquery == null) {
            	ucquery = newQuery(UserCustomerQuery.class, null);
    		}
    		List<CL_UserCustomer> list = this.userCustomerDao.find(ucquery);
    		int conut = 2;
    		for(int i=0;i<list.size();i++){
    			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
    			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
    			wcfN2.setBackground(Colour.LIGHT_ORANGE);
    			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
    			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
    			wsheet.addCell(new Label( 1 , conut ,list.get(i).getFnumber(),wcf2));
    			wsheet.addCell(new Label( 2 , conut ,list.get(i).getFname(),wcf2));
    			wsheet.addCell(new Label( 3 , conut ,list.get(i).getFsimplename(),wcf2));
    			wsheet.addCell(new Label( 4 , conut ,list.get(i).getUserName(),wcf2));
    			wsheet.addCell(new Label( 5 , conut ,list.get(i).getUserPhone(),wcf2));//手机
    			wsheet.addCell(new Label( 6 , conut ,list.get(i).getFtype(),wcf2));
    			wsheet.addCell(new Label( 7 , conut ,list.get(i).getFregion() ,wcf2));
    			wsheet.addCell(new Label( 8 , conut ,list.get(i).getFsalesMan() ,wcf2));
    			wsheet.addCell(new Label( 9 , conut ,list.get(i).getFsalesManDept() ,wcf2));
    			wsheet.addCell(new Label( 10 , conut ,list.get(i).getFsettleCycle() ,wcf2));
    			wsheet.addCell(new Label( 11 , conut ,list.get(i).getCreateTimeString() ,wcf2));
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
		return writeAjaxResponse(reponse,JSONUtil.getJson(result));
	}
}
