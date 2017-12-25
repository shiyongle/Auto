/*package com.pc.controller.coupons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.controller.order.OrderController;
import com.pc.dao.coupons.ICouponsDao;
import com.pc.model.CL_Coupons;
import com.pc.query.coupons.CL_CouponsQuery;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;

@Controller
public class CouponsSalesmanController extends BaseController{
	
	protected static final String LIST_JSP= "/pages/pc/coupons/coupons_sales_list.jsp";
	protected static final String CREATE_JSP= "/pages/pc/coupons/coupons_sales_create.jsp";
	@Resource
	private ICouponsDao couponsDao;
	
	
	
	*//*** 表单提交日期绑定*//*
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//写上你要的日期格式
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    *//***业务员好运券管理*//*
	@RequestMapping("/couponsSales/list")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return LIST_JSP;
	}
	
	*//***业务员好运券管理*//*
	@RequestMapping("/couponsSales/create")
	public String create(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return CREATE_JSP;
	}
	
	*//***业务员好运券管理-加载列表信息*//*
	@RequestMapping("/couponsSales/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_CouponsQuery couponsQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (couponsQuery == null) {
			couponsQuery = newQuery(CL_CouponsQuery.class, null);
		}
		if (pageNum != null) {
			couponsQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			couponsQuery.setPageSize(Integer.parseInt(pageSize));
		}
		HashMap<String, Object> m = new HashMap<String, Object>();
		couponsQuery.setType(1);
		Page<CL_Coupons> page = couponsDao.findPage(couponsQuery);
		List<CL_Coupons> footers = this.couponsDao.getFooter(couponsQuery);
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		m.put("footer", footers);
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	*//***业务员好运券管理-加载列表信息*//*
	@RequestMapping("/couponsSales/save")
	@Transactional
	public String save(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_Coupons coupons) throws Exception{
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = request.getParameter("startTime");
		Date start = date.parse(startTime);
		String now = date.format(new Date());
		if(start.before(date.parse(now))){
			return this.poClient(reponse, false, "有效期不合逻辑!");
		}
		if(coupons.getTotalDollars().compareTo(BigDecimal.ZERO) <= 0 ){
			return this.poClient(reponse, false, "金额不合逻辑!");
		}
//		String code = MD5Util.getBatchCode();
		BigDecimal dollars =coupons.getTotalDollars().divide(coupons.getTotalQuantity(),0,BigDecimal.ROUND_UP);
		for(int i = 0;i<coupons.getTotalQuantity().intValue();i++){
			CL_Coupons cp = new CL_Coupons();
//			cp.setBatchNumber(code);
			cp.setDollars(dollars);
			cp.setCompareDollars(dollars);
			cp.setDescribes(coupons.getDescribes());
			cp.setCreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
			cp.setCreateTime(new Date());
			cp.setIsEffective(1);//有效
			cp.setIsOverdue(0);//未过期
			cp.setStartTime(coupons.getStartTime());
			cp.setEndTime(coupons.getEndTime());
			cp.setSalesMan(coupons.getSalesMan());
			cp.setRedeemCode(MD5Util.getUUIDNumber());
//			cp.setType(1);
			this.couponsDao.save(cp);
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	public String isEffectiveName(String value){
		 if(value.equals("0")){
			return "失效";
		}else {
			return "有效";
		}
	}
	
	*//**导出excel**//*
	@RequestMapping("/couponsSales/exportExecl")
	public String exportExecl(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_CouponsQuery couponsQuery,Integer[] ids) throws Exception{
		boolean ises =true;
		*//***************//*
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "cps_"+format.format(new Date())+".xls";
		String path  = OrderController.class.getResource("").toURI().getPath();
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("业务好运券", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
            int[] headerArrHight = {5,20,20,20,20,20,100,20};
            String headerArr[] = {"序号","开始时间","结束时间","面额","满N元可用","是否有效","兑换码","创建时间"};
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);
            }
            if (couponsQuery == null) {
            	couponsQuery = newQuery(CL_CouponsQuery.class, null);
    		}
            couponsQuery.setType(1);
            if(ids!=null){
            	couponsQuery.setIds(ids);
            }
    		List<CL_Coupons> list = this.couponsDao.find(couponsQuery);
    		int conut = 2;
    		for(int i=0;i<list.size();i++){
    			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
    			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
    			wcfN2.setBackground(Colour.LIGHT_ORANGE);
    			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
    			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
    			wsheet.addCell(new Label( 1 , conut ,list.get(i).getStartTimeString(),wcf2));
    			wsheet.addCell(new Label( 2 , conut ,list.get(i).getEndTimeString(),wcf2));
    			wsheet.addCell(new jxl.write.Number(3,conut,Double.parseDouble(list.get(i).getDollars().toString()), wcfN2));
    			wsheet.addCell(new jxl.write.Number(4,conut,Double.parseDouble(list.get(i).getCompareDollars().toString()), wcfN2));
    			wsheet.addCell(new Label( 5 , conut ,isEffectiveName(list.get(i).getIsEffective().toString()) ,wcf2));
    			wsheet.addCell(new Label( 6 , conut ,list.get(i).getRedeemCode() ,wcf2));
    			wsheet.addCell(new Label( 7 , conut ,list.get(i).getCreateTimeString() ,wcf2));
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
	
}*/
