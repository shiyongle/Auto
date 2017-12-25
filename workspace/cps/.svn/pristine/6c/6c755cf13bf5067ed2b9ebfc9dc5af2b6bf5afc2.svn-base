package com.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
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
import jxl.write.WriteException;

import org.springframework.beans.factory.annotation.Autowired;
import com.action.BaseAction;
import com.model.PageModel;
import com.model.customer.Customer;
import com.model.deliverapply.Deliverapply;
import com.model.deliverapply.DeliverapplyQuery;
import com.model.firstproductdemand.Firstproductdemand;
import com.model.firstproductdemand.FirstproductdemandQuery;
import com.model.myDelivery.MyDelivery;
import com.model.myDelivery.MyDeliveryQuery;
import com.model.mystock.MyStockQuery;
import com.model.mystock.Mystock;
import com.service.customer.CustomerManager;
import com.service.deliverapply.DeliverapplyManager;
import com.service.firstproductdemand.TordFirstproductdemandManager;
import com.service.myDelivery.MyDeliveryManager;
import com.service.mystock.MystockManager;
import com.service.saledeliver.SaledeliverManager;


public class ExportExcelUtil extends BaseAction {
		
	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-8-31 下午1:33:45
	*/
	private static final long serialVersionUID = 1L;
	@Autowired
	private DeliverapplyManager deliverapplyManager;
	private DeliverapplyQuery deliverapplyQuery;
	public DeliverapplyQuery getDeliverapplyQuery() {
		return deliverapplyQuery;
	}
	public void setDeliverapplyQuery(DeliverapplyQuery deliverapplyQuery) {
		this.deliverapplyQuery = deliverapplyQuery;
	}


	/*** 要货导出*/
	public String excelDel() throws Exception{
		boolean ises =true;
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "_"+format.format(new Date())+".xls";
		String path  = ExportExcelUtil.class.getResource("").toURI().getPath().substring(1);
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		/*********************************************************************************/
		String fiid =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
        String where =" where tsy.FID  in ('"+fiid+"')";
        Map<String, String> orderby = new HashMap<String,String>();
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		if(deliverapplyQuery !=null){
			System.out.println("*************************");
			if(!("").equals(deliverapplyQuery.getFordertimeBegin())){//下单开始
				where =where + " and date_format(mv.fcreatetime,'%Y-%m-%d') >= ? ";
				ls.add(deliverapplyQuery.getFordertimeBegin());
			}
			if(!("").equals(deliverapplyQuery.getFordertimeEnd())){//下单结束
				where =where + " and date_format(mv.fcreatetime,'%Y-%m-%d') <= ? ";
				ls.add(deliverapplyQuery.getFordertimeEnd());
			}
			if(!("").equals(deliverapplyQuery.getFconsumetimeBegin())){//周期开始
				where =where + " and date_format(mv.farrivetime,'%Y-%m-%d') >= ? ";
				ls.add(deliverapplyQuery.getFconsumetimeBegin());
			}
			if(!("").equals(deliverapplyQuery.getFconsumetimeEnd())){//周期结束
				where =where + " and date_format(mv.farrivetime,'%Y-%m-%d') <= ? ";
				ls.add(deliverapplyQuery.getFconsumetimeEnd());
			}
			if( !("").equals(deliverapplyQuery.getSearchKey())){//关键字
				where =where + " and (mv._custpdtname like ? or mv._spec like ? or mv.faddress like ? or mv._suppliername like ? or mv.fnumber like ? or mv.fdescription like ? )";
				ls.add("%" +deliverapplyQuery.getSearchKey() +"%");
				ls.add("%" +deliverapplyQuery.getSearchKey() +"%");
				ls.add("%" +deliverapplyQuery.getSearchKey() +"%");
				ls.add("%" +deliverapplyQuery.getSearchKey() +"%");
				ls.add("%" +deliverapplyQuery.getSearchKey() +"%");
				ls.add("%" +deliverapplyQuery.getSearchKey() +"%");
			}
			if(!("").equals(deliverapplyQuery.getFstate())){
				if(deliverapplyQuery.getFstate()==0){//未接收
					where =where + " and mv.fstate = ?";
					ls.add(deliverapplyQuery.getFstate());
				}else if(deliverapplyQuery.getFstate()==1){//已接收
					where =where + " and (mv.fstate = ? or mv.fstate = ? or mv.fstate = ? )";
					ls.add(1);
					ls.add(2);
					ls.add(3);
				}else if(deliverapplyQuery.getFstate()==4){//已入库
					where =where + " and mv.fstate = ?";
					ls.add(deliverapplyQuery.getFstate());
				}else if(deliverapplyQuery.getFstate()==5){//部分发货
					where =where + " and mv.fstate = ?";
					ls.add(deliverapplyQuery.getFstate());
				}else if(deliverapplyQuery.getFstate()==6){//部分发货
					where =where + " and mv.fstate = ?";
					ls.add(deliverapplyQuery.getFstate());
				}
			}
		}
		String[] fids=getRequest().getParameterValues("fids");
		if(fids !=null ){
			String fid ="";
			for(int i=0 ;i<fids.length;i++){
				if(i ==0){
					fid = "'"+fids[i]+"'";
				}else{
					fid = fid + ",'"+fids[i]+"'"; 
				}
			}
			System.out.println(fid);
			where =where + " and mv.fid in ("+fid+")";
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		/*********************************************************************************/
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("要货订单", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
            wsheet.mergeCells( 0 , 0 , 7 , 0 ); // 合并单元格  
            Label titleLabel = new Label( 0 , 0 , " cps智能服务平台 ",wcf);
            wsheet.addCell(titleLabel);
            wsheet.setRowView(0, 1000); // 设置第一行的高度
            int[] headerArrHight = {10,20,10,30,50,20,30,10};
            String headerArr[] = {"序号","产品","数量","制造商","交期/地址","下单时间","申请单号","状态"};
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);
            }
    		List<Deliverapply> list =this.deliverapplyManager.ExecBySql(where, queryParams, orderby);
    		int conut = 2;
    		for(int i=0;i<list.size();i++){
    			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
    			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
    			wcfN2.setBackground(Colour.LIGHT_ORANGE);
    			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
    			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
    			wsheet.addCell(new Label( 1 , conut ,list.get(i).getFpdtname()+(list.get(i).getFordernumber()==null?"":"\r\n"+"订单号："+list.get(i).getFordernumber())+"\r\n"+"规格：" +list.get(i).getFpdtspec(),wcf2));
    			wsheet.addCell(new jxl.write.Number(2,conut,Double.parseDouble(list.get(i).getFamount().toString()), wcfN2));
    			wsheet.addCell(new Label( 3 , conut ,list.get(i).getSuppliername() ,wcf2));
    			wsheet.addCell(new Label( 4 , conut ,list.get(i).getFarrivetime()+"\r\n"+list.get(i).getFaddress() ,wcf2));
    			wsheet.addCell(new Label( 5 , conut ,list.get(i).getPlaceOrderTime() ,wcf2));
    			wsheet.addCell(new Label( 6 , conut ,list.get(i).getFnumber(),wcf2));
    			wsheet.addCell(new Label( 7 , conut ,statusName(list.get(i).getFstate().toString()) ,wcf2));
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
		if(ises==true){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		result.put("url", fileName);
		return writeAjaxResponse(JSONUtil.getJson(result));
	}
	
	/***状态****/
	public String statusName(String value){
		if(value.equals("0")){
			return "已创建";
		}else if(value.equals("1")){
			return "已生成";
		}else if(value.equals("2")){
			return "已下单";
		}else if(value.equals("3")){
			return "已分配";
		}else if(value.equals("4")){
			return "已入库";
		}else if(value.equals("5")){
			return "部分发货";
		}else if(value.equals("6")){
			return "全部发货";
		}
		return value;
	}
	
	@Autowired
	private MystockManager mystockManager;
	private MyStockQuery myStockQuery;
	
	public MyStockQuery getMyStockQuery() {
		return myStockQuery;
	}
	public void setMyStockQuery(MyStockQuery myStockQuery) {
		this.myStockQuery = myStockQuery;
	}
	/*** 备货导出*/
	public String excelMystock() throws Exception{
		boolean ises =true;
		/***************/
		String where = " where tsy.FID  = ?";
		Object[] queryParams = null;
		Map<String, String> orderby = new HashMap<String, String>();
		List<Object> lst = new ArrayList<Object>();
		lst.add(getRequest().getSession().getAttribute(Constant.SESSION_USERID)
				.toString());
		if (myStockQuery != null) {
			System.out.println("*************************");
			if (!("").equals(myStockQuery.getFordertimeBegin())) {// 下单开始
				where = where
						+ " and date_format(m.fplanamount,'%Y-%m-%d') >= ? ";
				lst.add(myStockQuery.getFordertimeBegin());
			}
			if (!("").equals(myStockQuery.getFordertimeEnd())) {// 下单结束
				where = where
						+ " and date_format(m.fplanamount,'%Y-%m-%d') <= ? ";
				lst.add(myStockQuery.getFordertimeEnd());
			}
			if (!("").equals(myStockQuery.getFconsumetimeBegin())) {// 周期开始
				where = where
						+ " and date_format(m.ffinishtime,'%Y-%m-%d') >= ? ";
				lst.add(myStockQuery.getFconsumetimeBegin());
			}
			if (!("").equals(myStockQuery.getFconsumetimeEnd())) {// 周期结束
				where = where
						+ " and date_format(m.ffinishtime,'%Y-%m-%d') <= ? ";
				lst.add(myStockQuery.getFconsumetimeEnd());
			}
			if (!("").equals(myStockQuery.getSearchKey())) {// 关键字
				where = where
						+ " and (c.fname like ? or s.fname like ? or c.FSPEC like ? or m.fnumber like ? or m.fpcmordernumber like ? or m.fdescription like ? )";
				lst.add("%" + myStockQuery.getSearchKey() + "%");
				lst.add("%" + myStockQuery.getSearchKey() + "%");
				lst.add("%" + myStockQuery.getSearchKey() + "%");
				lst.add("%" + myStockQuery.getSearchKey() + "%");
				lst.add("%" + myStockQuery.getSearchKey() + "%");
				lst.add("%" + myStockQuery.getSearchKey() + "%");
			}
			if (!("").equals(myStockQuery.getFstate())) {

				if (myStockQuery.getFstate() == 0) {// 未接收
					where = where + " and m.fstate = ?";
					lst.add(myStockQuery.getFstate());
				} else if (myStockQuery.getFstate() > 0) {// 已接受
					where = where + " and (m.fstate = ? or m.fstate = ?)";
					lst.add(1);
					lst.add(2);
				}
			}
		}
		String[] fids=getRequest().getParameterValues("fids");
		if(fids !=null ){
			String fid ="";
			for(int i=0 ;i<fids.length;i++){
				if(i ==0){
					fid = "'"+fids[i]+"'";
				}else{
					fid = fid + ",'"+fids[i]+"'"; 
				}
			}
			System.out.println(fid);
			where =where + " and m.fid in ("+fid+")";
		}
		queryParams = (Object[]) lst.toArray(new Object[lst.size()]);
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "_"+format.format(new Date())+".xls";
		String path  = ExportExcelUtil.class.getResource("").toURI().getPath().substring(1);
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("备货订单", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
            wsheet.mergeCells( 0 , 0 , 6 , 0 ); // 合并单元格  
            Label titleLabel = new Label( 0 , 0 , " cps智能服务平台 ",wcf);
            wsheet.addCell(titleLabel);
            wsheet.setRowView(0, 1000); // 设置第一行的高度
            int[] headerArrHight = {10,20,10,30,50,20,30};
            String headerArr[] = {"序号","产品","计划数量","制造商","首次发货/备货周期","备货单号","状态"};
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);
            }
    		List<Mystock> list =this.mystockManager.execlBySql(where, queryParams, orderby);
    		int conut = 2;
    		for(int i=0;i<list.size();i++){
    			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
    			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
    			wcfN2.setBackground(Colour.LIGHT_ORANGE);
    			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
    			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
    			wsheet.addCell(new Label( 1 , conut ,list.get(i).getFpdtname()+(list.get(i).getFordernumber()==null?"":"\r\n"+"订单号："+list.get(i).getFordernumber())+"\r\n"+"规格：" +list.get(i).getFpdtspec(),wcf2));
    			wsheet.addCell(new jxl.write.Number(2,conut,Double.parseDouble(list.get(i).getFplanamount().toString()), wcfN2));
    			wsheet.addCell(new Label( 3 , conut ,list.get(i).getSuppliername() ,wcf2));
    			wsheet.addCell(new Label( 4 , conut ,list.get(i).getFfinishtime()+"\r\n"+list.get(i).getFconsumetime() ,wcf2));
    			wsheet.addCell(new Label( 5 , conut ,list.get(i).getFnumber() ,wcf2));
    			wsheet.addCell(new Label( 6 , conut ,statusName2(list.get(i).getFstate().toString()) ,wcf2));
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
		return writeAjaxResponse(JSONUtil.getJson(result));
	}
	
	/***状态****/
	public String statusName2(String value){
		 if(value.equals("1")){
			return "已接受";
		}else {
			return "已接受";
		}
	}
	
	@Autowired
	private MyDeliveryManager myDeliveryManager;
	private MyDeliveryQuery mydeliveryQuery;
	public MyDeliveryQuery getMydeliveryQuery() {
		return mydeliveryQuery;
	}
	public void setMydeliveryQuery(MyDeliveryQuery mydeliveryQuery) {
		this.mydeliveryQuery = mydeliveryQuery;
	}
	
	/*** 收货情况导出*/
	public String excelMyDelivery() throws Exception{
		boolean ises =true;
		/***************/
		String where1 =" where sa.fstate <> 1 and sa.fcustomer IN ( ? )";
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString());
		if(mydeliveryQuery !=null){
			if(!("").equals(mydeliveryQuery.getFarrivetimeBegin())){//下单开始
				where1 =where1 + " and date_format(sa.fprinttime,'%Y-%m-%d') >= ? ";
				ls.add(mydeliveryQuery.getFarrivetimeBegin());
			}
			if(!("").equals(mydeliveryQuery.getFarrivetimeEnd())){//下单结束
				where1 =where1 + " and date_format(sa.fprinttime,'%Y-%m-%d') <= ? ";
				ls.add(mydeliveryQuery.getFarrivetimeEnd());
			}
			if( !("").equals(mydeliveryQuery.getSearchKey())){//关键字
				where1 =where1 + " and (sa.fsuppliername like ?  or sa.fnumber like ? or p.fname like ? or p.fspec like ? or sa.fcustAddress like ? or sd.fdescription like ?)";
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
			}
		}
		String where2 =" where d.fcustomerid IN ( ? )";
		ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString());
		if(mydeliveryQuery !=null){
			if(!("").equals(mydeliveryQuery.getFarrivetimeBegin())){//下单开始
				where2 =where2 + " and date_format(d.fouttime,'%Y-%m-%d') >= ? ";
				ls.add(mydeliveryQuery.getFarrivetimeBegin());
			}
			if(!("").equals(mydeliveryQuery.getFarrivetimeEnd())){//下单结束
				where2 =where2 + " and date_format(d.fouttime,'%Y-%m-%d') <= ? ";
				ls.add(mydeliveryQuery.getFarrivetimeEnd());
			}
			if( !("").equals(mydeliveryQuery.getSearchKey())){//关键字
				where2 =where2 + " and (_suppliername like ?  or d.fnumber like ? or d.fpcmordernumber like ? or _custpdtname like ? or _spec like ? or _mspec like ? or d.faddress like ? or d.fdescription like ? )";
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
			}
		}
		String[] fids=getRequest().getParameterValues("fids");
		if(fids !=null ){
			String fid ="";
			for(int i=0 ;i<fids.length;i++){
				if(i ==0){
					fid = "'"+fids[i]+"'";
				}else{
					fid = fid + ",'"+fids[i]+"'"; 
				}
			}
			System.out.println(fid);
			where1 =where1 + " and sd.fid in ("+fid+")";
			where2 =where2 + " and d.fid  in ("+fid+")";
		}
		
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		orderby.put("farrivetime", "DESC");
		orderby.put("saleorderNumber", "DESC");
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "_"+format.format(new Date())+".xls";
		String path  = ExportExcelUtil.class.getResource("").toURI().getPath().substring(1);
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("收货情况", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
            wsheet.mergeCells( 0 , 0 , 11 , 0 ); // 合并单元格  
            Label titleLabel = new Label( 0 , 0 , " cps智能服务平台 ",wcf);
            wsheet.addCell(titleLabel);
            wsheet.setRowView(0, 1000); // 设置第一行的高度
            int[] headerArrHight = {10,30,20,10,20,20,20,10,30,20,35,35};
            String headerArr[] = {"序号","产品名称","产品规格","订单类型","收货单号","采购订单号","制造商","出库数量","出库时间","下料规格","收货信息","备注"};
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);
            }
    		List<MyDelivery> list =this.myDeliveryManager.execlBySql(where1,where2, queryParams, orderby);
    		int conut = 2;
    		for(int i=0;i<list.size();i++){
    			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
    			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
    			wcfN2.setBackground(Colour.LIGHT_ORANGE);
    			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
    			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
    			wsheet.addCell(new Label( 1 , conut ,list.get(i).getCutpdtname(),wcf2));
    			wsheet.addCell(new Label( 2 , conut ,list.get(i).getFboxspec(),wcf2));
    			wsheet.addCell(new Label( 3 , conut ,typeName(list.get(i).getFboxtype().toString()),wcf2));
    			wsheet.addCell(new Label( 4 , conut ,list.get(i).getSaleorderNumber(),wcf2));
    			wsheet.addCell(new Label( 5 , conut ,list.get(i).getFpcmordernumber(),wcf2));
    			wsheet.addCell(new Label( 6 , conut ,list.get(i).getFsuppliername(),wcf2));
    			wsheet.addCell(new jxl.write.Number(7,conut,Double.parseDouble(list.get(i).getFamount().toString()), wcfN2));
    			wsheet.addCell(new Label( 8 , conut ,list.get(i).getFarrivetime(),wcf2));
    			wsheet.addCell(new Label( 9 , conut ,list.get(i).getFmaterialspec(),wcf2));
    			wsheet.addCell(new Label( 10 , conut ,list.get(i).getFaddress(),wcf2));
    			wsheet.addCell(new Label( 11 , conut ,list.get(i).getFdescription(),wcf2));
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
		return writeAjaxResponse(JSONUtil.getJson(result));
	}
	
	/***状态****/
	public String typeName(String value){
		 if(value.equals("1")){
			return "纸板";
		}else {
			return "纸箱 ";
		}
	}
	
	@Autowired
	private TordFirstproductdemandManager tordFirstproductdemandManager;
	private FirstproductdemandQuery firstproductdemandQuery;
	
	public FirstproductdemandQuery getFirstproductdemandQuery() {
		return firstproductdemandQuery;
	}
	public void setFirstproductdemandQuery(
			FirstproductdemandQuery firstproductdemandQuery) {
		this.firstproductdemandQuery = firstproductdemandQuery;
	}
	public String excelMand() throws Exception{
		boolean ises =true;
		String where =" WHERE f.fcustomerid  = ? ";
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		ls.add(customerId);
		if(firstproductdemandQuery !=null){
			if(!("").equals(firstproductdemandQuery.getFstate())){
				where =where + " and f.fstate  in ('"+firstproductdemandQuery.getFstate()+"')";
			}
			if(!("").equals(firstproductdemandQuery.getSearchKey())){
				where = where + " and (c.fname like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
									   "or f.fname like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or f.fnumber like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or u1.fname  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or ifnull(u2.fname,'设计咨询')  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or ifnull(u2.ftel,'0577-55575290') like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or u3.fname  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or sp.fname  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or f.fdescription like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or f.flinkman  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or f.flinkphone  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
						             ")";
			}
		}
		String[] fids=getRequest().getParameterValues("fids");
		if(fids !=null ){
			String fid ="";
			for(int i=0 ;i<fids.length;i++){
				if(i ==0){
					fid = "'"+fids[i]+"'";
				}else{
					fid = fid + ",'"+fids[i]+"'"; 
				}
			}
			System.out.println(fid);
			where =where + " and f.fid  in ("+fid+")";
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		orderby.put("f.isfauditor", "DESC");
		orderby.put("f.fauditortime", "DESC");
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "_"+format.format(new Date())+".xls";
		String path  = ExportExcelUtil.class.getResource("").toURI().getPath().substring(1);
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("我的需求", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
            wsheet.mergeCells( 0 , 0 , 8 , 0 ); // 合并单元格  
            Label titleLabel = new Label( 0 , 0 , " cps智能服务平台 ",wcf);
            wsheet.addCell(titleLabel);
            wsheet.setRowView(0, 1000); // 设置第一行的高度
            int[] headerArrHight = {10,30,30,40,15,40,20,30,20};
            String headerArr[] = {"序号","需求编号","需求名称","客户名称","需求状态","制造商","发布人","发布时间","联系设计师"};
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);
            }
    		List<Firstproductdemand> list =this.tordFirstproductdemandManager.execlBySql(where, queryParams, orderby);
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
    			wsheet.addCell(new Label( 3 , conut ,list.get(i).getCustomerName(),wcf2));
    			wsheet.addCell(new Label( 4 , conut ,list.get(i).getFstate(),wcf2));
    			wsheet.addCell(new Label( 5 , conut ,list.get(i).getSupplierName(),wcf2));
    			wsheet.addCell(new Label( 6 , conut ,list.get(i).getFauditorid(),wcf2));
    			wsheet.addCell(new Label( 7 , conut ,list.get(i).getFauditortimeString()+"\r\n"+list.get(i).getfTimeString(),wcf2));
    			wsheet.addCell(new Label( 8 , conut ,list.get(i).getFreceiver()+"\r\n"+list.get(i).getFreceiverTel(),wcf2));
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
		return writeAjaxResponse(JSONUtil.getJson(result));
		
	}
	
	
	
	
	/*** 纸板订单导出*/
	public String excelboardDeliverapply() throws Exception{
		boolean ises =true;
		/***************/
		String where =" where  tsy.FID  = ? and  mv.fstate!=7 ";
		String[] fstate=null;
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		
		if(deliverapplyQuery !=null){
			if( !("").equals(deliverapplyQuery.getSearchKey())){//关键字
				where =where + " and (mv._materialname like ? or mv._suppliername like ? or mv.fnumber like ?  ";
				ls.add("%" +deliverapplyQuery.getSearchKey() +"%");
				ls.add("%" +deliverapplyQuery.getSearchKey() +"%");
				ls.add("%" +deliverapplyQuery.getSearchKey() +"%");
				if(deliverapplyQuery.getSearchKey().matches("^\\d\\.?\\d*((\\*|X|x)?(\\d+\\.?\\d*)?){0,3}$")){
						where  += "  or concat(mv.fboxlength,'*',mv.fboxwidth,'*',mv.fboxheight) like ? or concat(mv.fmateriallength,'*',mv.fmaterialwidth) like ?";	
						ls.add("%" +deliverapplyQuery.getSearchKey().replaceAll("X|x","*") +"%");
						ls.add("%" +deliverapplyQuery.getSearchKey().replaceAll("X|x","*") +"%");			
				}
				where +=")";
			}
			if(!("").equals(deliverapplyQuery.getFstate()) && deliverapplyQuery.getFstate()!=null){
				fstate=getRequest().getParameter("deliverapplyQuery.fstate").split(",");
				where+= "and (1=0";
				for(String state:fstate)
				{
					where =where + " or mv.fstate = ?";
					ls.add(state);
				}
				where+= ")";
			}
		}
		String[] fids=getRequest().getParameterValues("fids");
		if(fids !=null ){
			String fid ="";
			for(int i=0 ;i<fids.length;i++){
				if(i ==0){
					fid = "'"+fids[i]+"'";
				}else{
					fid = fid + ",'"+fids[i]+"'"; 
				}
			}
		
			where =where + " and mv.fid in ("+fid+")";
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		orderby.put("mv.fcreatetime", "desc");	
		
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "_"+format.format(new Date())+".xls";
		String path  = ExportExcelUtil.class.getResource("").toURI().getPath().substring(1);
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("纸板订单", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
            wsheet.mergeCells( 0 , 0 , 10 , 0 ); // 合并单元格  
            Label titleLabel = new Label( 0 , 0 , " 纸板订单 ",wcf);
            wsheet.addCell(titleLabel);
            wsheet.setRowView(0, 1000); // 设置第一行的高度
            int[] headerArrHight = {10,30,20,30,30,30,20,20,20,20,20,20};
            String headerArr[] = {"序号","材料","产品特征","纸箱规格","下料规格","压线方式","配送数量(只)","配送数量(片)","制造商","订单编号","交期","订单状态"};
			/*** 箱型：1普通 2全包,3半包,4有底无盖,5有盖无底,6围框,7天地盖,8立体箱 0其他*/
            String fFboxmodel[]={"其他","普通","全包","半包","有底无盖","有盖无底","围框","天地盖","立体箱"};
            String fstatevalue[]={"已下单","已接收","已接收","已接收","已入库","已发货","已发货","已暂存","已作废"};//状态
            
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);
            }
            List<Deliverapply> list=deliverapplyManager.getDeliverapplyboards(where, queryParams, orderby);
    		int conut = 2;
    		for(int i=0;i<list.size();i++){
    			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
    			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
    			wcfN2.setBackground(Colour.LIGHT_ORANGE);
    			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
    			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
    			wsheet.addCell(new Label( 1 , conut ,list.get(i).getFpdtname(),wcf2));
    			wsheet.addCell(new Label( 2 , conut ,(list.get(i).getFboxmodel()>8?"其他":fFboxmodel[list.get(i).getFboxmodel()])+list.get(i).getFseries()+list.get(i).getFstavetype(),wcf2));
    			wsheet.addCell(new Label( 3 , conut ,list.get(i).getFboxlength()+"*"+list.get(i).getFboxwidth()+"*"+list.get(i).getFboxheight(),wcf2));
    			wsheet.addCell(new Label( 4 , conut ,list.get(i).getFmateriallength()+"*"+list.get(i).getFmaterialwidth(),wcf2));
    			wsheet.addCell(new Label( 5 , conut ,(list.get(i).getFvline()==null?"":"纵："+list.get(i).getFvline())+(list.get(i).getFhline()==null?"":"\r\n横:"+list.get(i).getFhline()),wcf2));
    			wsheet.addCell(new jxl.write.Number( 6 , conut ,list.get(i).getFamount(),wcf2));
    			wsheet.addCell(new jxl.write.Number( 7 , conut ,list.get(i).getFamountpiece(),wcf2));
    			wsheet.addCell(new Label( 8 , conut ,list.get(i).getSuppliername(),wcf2));
    			wsheet.addCell(new Label( 9 , conut ,list.get(i).getFnumber(),wcf2));
    			wsheet.addCell(new Label( 10 , conut ,list.get(i).getFarrivetimeString(),wcf2));
    			wsheet.addCell(new Label( 11 , conut ,list.get(i).getFstate()>fstatevalue.length?"未知":fstatevalue[list.get(i).getFstate()],wcf2));
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
		return writeAjaxResponse(JSONUtil.getJson(result));
	}
	
}
