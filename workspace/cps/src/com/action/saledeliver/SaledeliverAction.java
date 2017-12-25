package com.action.saledeliver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.PageModel;
import com.model.customer.Customer;
import com.model.deliverapply.Deliverapply;
import com.model.saledeliver.FTUSaledeliver;
import com.model.saledeliver.FtuParameter;
import com.model.saledeliverdetail.FTUSaledeliverEntry;
import com.opensymphony.xwork2.ModelDriven;
import com.service.customer.CustomerManager;
import com.service.saledeliver.SaledeliverManager;
import com.util.Constant;
import com.util.DJException;
import com.util.ExportExcelUtil;
import com.util.JSONUtil;
import com.util.Params;
import com.util.StringUitl;

/*
 * CPS-VMI-wangc
 */

public class SaledeliverAction extends BaseAction implements ModelDriven<FTUSaledeliver>{
	
	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-11-23 上午9:42:28
	*/
	private static final long serialVersionUID = 4023193644693443115L;
	protected static final String LIST_JSP= "/pages/saledeliver/saledeliver_list.jsp";
	protected static final String CREATE_JSP = "/pages/saledeliver/create.jsp";
	protected static final String EDIT_JSP = "/pages/saledeliver/edit.jsp";
	protected static final String SHOW_JSP = "/pages/saledeliver/show.jsp";
	protected static final String RPT_JSP = "/pages/saledeliver/saldeliverRpt.jsp";
	
	private HashMap<String, Integer> Numbercls = new HashMap<>();
	private String datestr = "";
	private HashMap<String, Integer> TempNumbercls = new HashMap<>();
	@Autowired
	private SaledeliverManager saledeliverManager;
	@Autowired
	private CustomerManager customerManager;
	private FTUSaledeliver saledeliver;
	private FtuParameter ftuParameter = new FtuParameter();
	public FtuParameter getFtuParameter() {
		return ftuParameter;
	}
	public void setFtuParameter(FtuParameter ftuParameter) {
		this.ftuParameter = ftuParameter;
	}
	private PageModel<FTUSaledeliver> pageModel;// 分页组件
	
	public FTUSaledeliver getModel() {
		return saledeliver;
	}
	public FTUSaledeliver getSaledeliver() {
		return saledeliver;
	}

	public void setSaledeliver(FTUSaledeliver saledeliver) {
		this.saledeliver = saledeliver;
	}
	
	public PageModel<FTUSaledeliver> getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel<FTUSaledeliver> pageModel) {
		this.pageModel = pageModel;
	}
	/** 执行搜索 */
	public String list() {
		return LIST_JSP;
	}
	
	public String load(){
		String where =" where usp.fuserid  = ?  and sa.fstate !=1 ";
		Object[] queryParams=null;
		Map<String, String> orderby = new HashMap<String,String>();
		orderby.put("sa.fnumber", "DESC,sd.fsequence");
		String fcreatetime = getRequest().getParameter("fcreatetime");
		String queryfilter = getRequest().getParameter("queryfilter");
		List<Object> ls = new ArrayList<Object>();
		    ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		    if(!StringUitl.isNullOrEmpty(fcreatetime)){
		    	where += " and (DATE_FORMAT(sa.fcreatetime,'%Y-%m-%d')>=? and DATE_FORMAT(sa.fcreatetime,'%Y-%m-%d')<=?)";
		    	ls.add(fcreatetime.split("到")[0].trim());
		    	ls.add(fcreatetime.split("到")[1].trim());
		    }
		    if(!StringUitl.isNullOrEmpty(queryfilter)){
		    	where += "  and (sa.fnumber like ? or c.fname like ? or p.fspec like ? or p.fname like ? or sa.fphone like ?)";
		    	String query="%"+queryfilter+"%";
		    	for (int i = 0; i < 5; i++) {
		    		ls.add(query);
				}
		    }
		    queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		pageModel = this.saledeliverManager.findBySql(where, queryParams, orderby, pageNo, pageSize);
		HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("list", pageModel.getList());
			map.put("totalRecords", pageModel.getTotalRecords());//总条数
			map.put("pageNo", pageModel.getPageNo());//第几页
			map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	
	
	
	
	/** 查看对象*/
	public String show() {
		return SHOW_JSP;
	}
	
	/** 进入新增页面*/
	public String create() {
		return CREATE_JSP;
	}
	
	/** 保存新增对象 */
	public String save() {
		return writeAjaxResponse("1");
	}
	
	/**进入更新页面*/
	public String edit() {
		return EDIT_JSP;
	}
	
	/**保存更新对象*/
	public String update() {
		return writeAjaxResponse("1");
	}
	
	/**删除对象*/
	public String delete() {
		return writeAjaxResponse("1");
	}
	/**获取送货凭证打印模板大小*/
	public String getCfgByFkey(){
		String result ="";
		try {
			String key = getRequest().getParameter("key");
			String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String value = this.saledeliverManager.getCfgByFkey(userFid, key);
			result = JSONUtil.result(true,"","",value);
		} catch (Exception e) {
			// TODO: handle exception
			result = JSONUtil.result(false,e.getMessage(), "", "");
		}
		return writeAjaxResponse(result);
	}
	/**修改送货凭证打印模板大小*/
	public String updateSyscfg(){
		String result ="";
		try {
			String key = getRequest().getParameter("key");
			String value = getRequest().getParameter("value");
			String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			this.saledeliverManager.updateSyscfg(userFid, key, value);
			result = JSONUtil.result(true,"","","");
		} catch (Exception e) {
			// TODO: handle exception
			result = JSONUtil.result(false,e.getMessage(), "", "");
		}
		return writeAjaxResponse(result);
	}
	/**获取送货凭证打印模板HTML文件*/
	@SuppressWarnings("deprecation")
	public void getFtuPrintTemplate() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String sql = "select ftemplate from t_ftu_template where fuserid=:fuid";
			Params p = new Params();
			p.put("fuid", userid);
			List<HashMap<String,Object>> list = this.saledeliverManager.QueryBySql(sql,p);
			String value = this.saledeliverManager.getCfgByFkey(userid, "thePrintTP");
//			File file = new File(request.getServletContext().getRealPath("/"));//获取当前路径
			File file = new File(getRequest().getRealPath("/"));//获取当前绝对路径
			String fpath = 	file.getParent()+"/vmifile/printTemplate";//构造按当前日期保存文件的路径 yyyy-MM-dd
			if(list.size()>0){
				fpath =fpath+"//"+list.get(0).get("ftemplate").toString();
			}else{
				fpath = file.getParentFile().getParent()+"\\送货凭证模板"+value+"联.txt";//D:\\tomcat
				if(value==null){
					fpath = file.getParentFile().getParent()+"\\送货凭证模板3联.txt";//D:\\tomcat
				}
			}
			File files=new File(fpath);
			if(files.isFile() && files.exists()){ //判断文件是否存在  
				InputStreamReader read = new InputStreamReader(new FileInputStream(files),"GBK");//考虑到编码格式  
				BufferedReader bufferedReader = new BufferedReader(read);  
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null){  
					getResponse().getWriter().write(lineTxt);
				}  
				read.close(); 
			}else{  
				throw new Exception("找不到指定的文件!");
			}  
	
		} catch (Exception e) {
			// TODO: handle exception
			getResponse().getWriter().write(
					JSONUtil.result(false,e.getMessage(),"",""));
		}
	}
	public String getFtuConcat() throws IOException {
		getResponse().setCharacterEncoding("utf-8");
		try {
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
					" union select c.fsupplierid from  t_sys_userrole r "+
					" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
			Params p = new Params();
			List<HashMap<String,Object>> list=this.saledeliverManager.QueryBySql(sql,p);
			if(list.size()>0){
				String fsupplierid = (String)list.get(0).get("fsupplierid");
				String number = getFtuNumber(fsupplierid);
				String fsuppliername = getStringValue("t_sys_supplier", fsupplierid, "fname");
				sql = "select fphone,ffax,'"+userid+"' fuserid,'"+number+"' fnumber,fcustomername  fcustname,'"+fsupplierid+"' fsupplierid,'"+fsuppliername+"' fsname,fcustomername  fsuppliername from t_sys_user where fid = '"+userid+"'";
				list = this.saledeliverManager.QueryBySql(sql,p);
				getResponse().getWriter().write(JSONUtil.result(true, "", "",list));
			}else{
				getResponse().getWriter().write(JSONUtil.result(false,"当前用户没有关联制造商，请联系管理员！", "",""));
			}
			
		} catch (DJException e) {
			getResponse().getWriter().write(JSONUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	public String getFtuNumber(String fsupplierid) {
		String table = "t_ftu_saledeliver";
		String startstr = "CPS";
		int length = 4;
		checkdatetime();
		String key =table+startstr+length;
		String nowString=startstr+datestr;
		int num = 0;
			String sql="select max(fnumber) fnumber from "+table+"  where fnumber like :datenow and LENGTH(fnumber)="+(nowString.length()+length)+" and fsupplierid ='"+fsupplierid+"'";
			Params p = new Params();
			p.put("datenow",nowString+"%" );
			List<HashMap<String, Object>> sList= this.saledeliverManager.QueryBySql(sql, p);
			if(sList.size()>0)
			{
				
				if(sList.get(0).get("fnumber")!=null)
				{
					String fnumber=sList.get(0).get("fnumber").toString();
					num = Integer.parseInt(fnumber.substring(fnumber.length()-length+1, fnumber.length()));
				}
			}else{
				num = 0;
			}
			num++;
		return nowString+"-"+"000".substring(0,3-(num+"").length())+num;
	}
	private void checkdatetime()
	{
		Date nowdate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String nowdatestr = df.format(nowdate);
		if(!nowdatestr.equals(datestr))
		{
			datestr=nowdatestr;
			Set<String> keys = Numbercls.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				Numbercls.put(it.next(), 0);
			}
			Set<String> tempkeys = TempNumbercls.keySet();
			Iterator<String> tempit = tempkeys.iterator();
			while (tempit.hasNext()) {
				TempNumbercls.put(tempit.next(), 0);
			}
		}
	}
	public String getStringValue(String tableName,String fid,String fieldName){
		String sql = "select "+fieldName+" from "+tableName+" where fid=:fid";
		Params p = new Params();
		p.put("fid", fid);
		List<HashMap<String, Object>> list = this.saledeliverManager.QueryBySql(sql, p);
		if(list.size()==0){
			return null;
		}else{
			return String.valueOf(list.get(0).get(fieldName));
		}
	}
	public void saveOrupdateFtuParameters() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			FtuParameter fp = (FtuParameter)getRequest().getAttribute("FtuParameter");
			this.saledeliverManager.saveOrupdateFtuParameters(userid, fp);
			getResponse().getWriter().write(
			JSONUtil.result(true, "保存成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(
					JSONUtil.result(false,e.getMessage(),"",""));
		}
	}
	public void delFtuParameter() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String fname = getRequest().getParameter("name");
			String sql = "delete from t_ftu_parameter where fuserid='"+userid+"' and falias='"+fname+"'";
			this.saledeliverManager.ExecBySql(sql);
			getResponse().getWriter().write(
					JSONUtil.result(true, "删除成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(
					JSONUtil.result(false,e.getMessage(),"",""));
		}
	}
	public void getFtuParameterList() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			Params p = new Params();
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String falias = getRequest().getParameter("falias");
			String sql = "select fid,fsaledeliverentry,fieldtype,ifnull(fisprint,0) fisprint,ifnull(fdecimals,0) fdecimals,ifnull(fedit,0) fedit,ifnull(fcomputationalformula,'') fcomputationalformula,falias,fuserid,fname from t_ftu_parameter where fuserid='"+userid+"'";
			if(!StringUitl.isNullOrEmpty(falias)){
				sql += " and falias='"+falias+"'";
			}
			List<HashMap<String,Object>> result = this.saledeliverManager.QueryBySql(sql, p);
			getResponse().getWriter().write(
					JSONUtil.result(true, "","",result));
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(
					JSONUtil.result(false, e.getMessage(), "", ""));
		}
	}
	public void saveAllFtuParams() throws IOException{
		try {
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String data = getRequest().getParameter("ftu");
		    JSONArray json = JSONArray.fromObject(data);
		    List<FtuParameter> fp=(ArrayList<FtuParameter>)JSONArray.toList(json, FtuParameter.class);
			this.saledeliverManager.saveAllFtuParams(userid, fp);
			getResponse().getWriter().write(JSONUtil.result(true, "","" ,""));	
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(JSONUtil.result(false, "", "",e.getMessage()));
		}
	}
	public void savePrintTemplate() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String html = getRequest().getParameter("html").replaceAll("ギ", "%").replaceAll("¥","&yen;");
			File file = new File(getRequest().getRealPath("/"));//获取当前路径
			this.saledeliverManager.savePrintTemplate(userid, html, file);
			String result = JSONUtil.result(true, "保存成功","","");
			getResponse().getWriter().write(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			getResponse().getWriter().write(
					JSONUtil.result(false,e.getMessage(),"",""));
		}
	}
	public void resetPrintTemplate() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			File file = new File(getRequest().getRealPath("/"));//获取当前路径
			this.saledeliverManager.resetPrintTemplate(userid, file);
			String result = JSONUtil.result(true, "重置成功","","");
			getResponse().getWriter().write(result);
		} catch (Exception e) {
			// TODO: handle exception
			getResponse().getWriter().write(
					JSONUtil.result(false,e.getMessage(),"",""));
		}
	}
	public void getFtusaledelivers() throws Exception{
		getResponse().setCharacterEncoding("utf-8");
		try {
			Params p = new Params();
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
					" union select c.fsupplierid from  t_sys_userrole r "+
					" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
			List<HashMap<String,Object>> list=this.saledeliverManager.QueryBySql(sql,p);
			if(list.size()>0){
				String fid = getRequest().getParameter("fid");
				sql = "SELECT sa.fcustomer,sa.fid,CASE sd.famount WHEN 0 THEN p.fprice ELSE IFNULL(sd.fdanjia,ROUND(sd.fprice / sd.famount, 3)) END fdanjia,sd.string1,sd.string2,sd.string3,sd.string4,sd.string5,sd.string6,sd.string7,sd.string8,sd.string9,sd.string10,sa.fsuppliername,sa.fcustAddress,c.fname fcustomername,sa.fphone,sa.ffax,sa.ftel,sa.`fnumber`,p.`fname` fcusproductname,p.`fspec`,p.`funit`,sd.`famount`,sd.`fprice` fprice,SUBSTR(sa.fcreatetime,1,10) fcreatetime,sa.fclerk,sa.fdriver,sd.`fdescription` FROM `t_ftu_saledeliver` sa LEFT JOIN `t_ftu_saledeliverentry` sd ON sd.`fparentid`=sa.fid LEFT JOIN t_bd_custproduct p ON sd.`fftuproductid` = p.`fid` left join t_bd_customer c on sa.fcustomer=c.fid where 1=1 and sa.fstate<>1 and sa.fid in('"+fid+"')"
						+" and  sa.fsupplierid='"+list.get(0).get("fsupplierid")+"' order by sd.fsequence";
				list = this.saledeliverManager.QueryBySql(sql,p);
			}else{
				throw new Exception("请关联制造商！");
			}
			getResponse().getWriter().write(JSONUtil.result(true, "","" ,list));
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(
					JSONUtil.result(false,e.getMessage(),"",""));
		}
	}
	/*送货凭证详情*/
	public String saledeliveredit(){
		String fstate = getRequest().getParameter("fstate");
		String fid = getRequest().getParameter("fid");
		getRequest().setAttribute("fstate", fstate);
		getRequest().setAttribute("fid", fid);
		return "/pages/saledeliver/saledeliver_edit.jsp";
	}
	public void getPrintFTUList() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String fparentid = getRequest().getParameter("fids");
			String size = getRequest().getParameter("size");
			int psize = 9999;
			if(!StringUitl.isNullOrEmpty(size)){
				psize = Integer.valueOf(size);
			}
			HashMap<String,Object> paramsMap = new HashMap();
			String sql = "";
			String result = "";
			Params param = new Params();
			if(!StringUitl.isNullOrEmpty(fparentid)){
				String[] fids = fparentid.split(",");
				for(int i = 0;i<fids.length;i++){
					String sqls = "SELECT  sa.famount,sa.fprice,sa.fdescription,ifnull(sa.fdanjia,c.fprice) fdanjia,ifnull(sa.fcusproductname,c.fname) fcusproductname,ifnull(sa.fspec,c.fspec) fspec,ifnull(sa.funit,c.funit) funit,sa.string1,sa.string2,sa.string3,sa.string4,sa.string5,sa.string6,sa.string7,sa.string8,sa.string9,sa.string10 FROM  t_ftu_saledeliverentry sa  left join t_bd_custproduct c ON sa.`fftuproductid`=c.fid where sa.fparentid='"+fids[i]+"' order by sa.fsequence";
					List<HashMap<String,Object>> plist = this.saledeliverManager.QueryBySql(sqls,param);
					double fprices = 0;
					BigDecimal sum =BigDecimal.valueOf(0);
					for(HashMap<String,Object> p : plist){
						try {
							fprices += Double.valueOf(p.get("fprice")==null?"0":p.get("fprice").toString());//*Double.valueOf(p.get("famount").toString());
							sum = sum.add(BigDecimal.valueOf(Double.valueOf(p.get("famount")==null?"0":p.get("famount").toString())));
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					String chinesefprices = toRMB(fprices);
					DecimalFormat myformat = new DecimalFormat();
					myformat.applyPattern("##,###.00");
					String fsumprices = "0";
					if(fprices!=0){
						fsumprices = myformat.format(fprices);
					}
					String proresult = JSONUtil.result(true, "","",plist);
					
					String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
//					sql = "select fcustomername from t_sys_user where fid='"+userid+"'";
//					List<HashMap<String,Object>> map = ftuDao.QueryBySql(sql);//获取用户信息里面的客户名称
					
					 sql = "SELECT c.fname fcustomername,s.fcustAddress,s.fid,s.fsuppliername,s.fsupplierid,s.fnumber,SUBSTR(s.fcreatetime,1,10) fcreatetime,s.fcreator,s.ffax,s.ftel,s.fdriver,s.fclerk,c.fname fcustomer,s.fphone FROM t_ftu_saledeliver s left join t_bd_customer c on c.fid=s.fcustomer where s.fid='"+fids[i]+"'";
					List<HashMap<String,Object>> salelist = this.saledeliverManager.QueryBySql(sql,param);
					if(plist.size()>psize){
						paramsMap = new HashMap();
						int d = (int) Math.ceil((double)plist.size()/psize);//取不小于它的整数
						int start = 0;
						int end = 0;
						for(int x= 0;x<d;x++){
							double fpricess = 0;
							int sums = 0;
							List<HashMap<String,Object>> pplist = this.saledeliverManager.QueryBySql(sqls+" limit "+start+","+psize,param);
							proresult = JSONUtil.result(true, "","",pplist);
							for(HashMap<String,Object> p : pplist){
								try {
									fpricess += Double.valueOf(p.get("fprice").toString());//*Double.valueOf(p.get("famount").toString());
									sums += Double.valueOf(p.get("famount").toString());
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
							 chinesefprices = toRMB(fpricess);
							 fsumprices = "0";
							 if(fprices!=0){
								fsumprices = myformat.format(fpricess);
							}
							 paramsMap.put("psize", pplist.size());
							 paramsMap.put("fsumprices", fsumprices);
							 paramsMap.put("chinesefprices", chinesefprices);
							 paramsMap.put("sum", sums);
							 paramsMap.put("number", (String)salelist.get(0).get("fnumber")+"-"+(x+1));
							 paramsMap.put("plist", proresult.substring(proresult.indexOf("["),proresult.lastIndexOf("]")+1));
							result += List2Json(salelist,paramsMap);
							start += psize;
							if(x<d-1){
								result +=",";
							}
						}
						
					}else{
						paramsMap.put("plist", proresult.substring(proresult.indexOf("["),proresult.lastIndexOf("]")+1));
						paramsMap.put("psize", plist.size());
						paramsMap.put("fsumprices", fsumprices);
						paramsMap.put("chinesefprices", chinesefprices);
						paramsMap.put("sum" ,sum);
						paramsMap.put("number", salelist.get(0).get("fnumber"));
						result += List2Json(salelist,paramsMap);
					}
					if(i<fids.length-1){
						result +=",";
					}
					//更新打印时间和打印次数
					this.saledeliverManager.saveFTUprint(fids[i]);
				}
			}
			getResponse().getWriter().write("{\"success\":" + true+ ",\"data\":[" + result + "]}");
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(JSONUtil.result(false, "", "",e.getMessage()));
		}
	}
	public static String List2Json(List<HashMap<String,Object>> slist,HashMap<String, Object> map) {
		String result = "";
		if (slist.size() < 1) {
			return "";
		}
		int index = 0;
		String val;
		Iterator<String> it;
		HashMap<String, Object> h = slist.get(0);
		Set<String> set = h.keySet();
		for (HashMap<String, Object> o : slist) {
			it = set.iterator();
			if (index > 0) {
				result = result + ",";
			}
			result = result + "{";
			int keyindex = 0;
			while (it.hasNext()) {
				if (keyindex > 0) {
					result = result + ",";
				}
				String key = it.next();
				val = o.get(key)!=null?o.get(key).toString().replace("\r\n", "<br/>").replace("\r", "<br/>").replace("\n", "<br/>").replace("\\", "\\\\").replace("\"", "\\\""):"";
				result = result + "\"" + key + "\"" + ":\"" + val + "\"";
				keyindex++;
			}
			result = result +",\""+"number"+"\""+ ":\"" + map.get("number") + "\""+",\""+"sum"+"\""+ ":\"" + map.get("sum") + "\""+",\""+"fsumprices"+"\""+ ":\"" + map.get("fsumprices") + "\""+",\""+"chinesefprices"+"\""+ ":\"" + map.get("chinesefprices") + "\""+",\""+"psize"+"\""+ ":\"" + map.get("psize") + "\""+ ",\"" + "product" + "\"" + ":"+map.get("plist")+ "}";
			index++;
		}
		return result;
	}
	public static String toRMB(double money) {  
	    char[] s1 = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};  
	    char[] s4 = {'分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟', '万'};  
	    if(money>100000000){
	    	return "";
	    }
	    String str = String.valueOf(Math.round(money * 100 + 0.00001));  
	    String result = "";  
	   
	    for (int i = 0; i <str.length(); i++) {  
			int n = str.charAt(str.length() - 1 - i) - '0';
			result = s1[n] + "" + s4[i] + result;
		}
	   
	        result = result.replaceAll("零万", "万");  
	        String result1 = "";
	        if(result.indexOf("万")>0){
	        	result1 =result.substring(0,result.indexOf("万"));
	   	       result1 = result1.replaceAll("零仟", "零");  
	   	       result1 = result1.replaceAll("零佰", "零");  
	   	       result1 = result1.replaceAll("零拾", "零"); 
	   	       result1 = result1.replaceAll("零零", "零");  
	   	       if("零".equals(result1.substring(result1.length()-1))){
	   	    	   result1 = result1.substring(0,result1.length()-1);
	   	       }
	   	    return result1+result.substring(result.indexOf("万"))+"整"; 
	        }
	    return result+"整";  
	    }
	
	 public String selectFtuCustomer() throws IOException {
		  getResponse().setCharacterEncoding("utf-8");
		  try {
		//   List<HashMap<String, Object>> list = ftuDao.getFtuRelationName(request, 0);
		   Params p = new Params();
		   String fname = getRequest().getParameter("fname");//取值为null
		   String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		   String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid=:uid"+
		     " union select c.fsupplierid from  t_sys_userrole r "+
//		    " left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
	" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid=:uid ) s where fsupplierid is not null " ;
		   p.put("uid", userid);
		   List<HashMap<String,Object>> list=this.saledeliverManager.QueryBySql(sql,p);
		   p.getData().clear();
		   if(list.size()>0){
		    sql = "select c.fid,c.fname,c.fphone ftel from t_bd_customer c left join t_pdt_productreqallocationrules p on c.fid=p.fcustomerid where 1=1 "
//		      +" and p.fsupplierid='"+list.get(0).get("fsupplierid")+"'";
		      +" and p.fsupplierid=:fsid";
		    p.put("fsid", list.get(0).get("fsupplierid"));
		    if(!StringUitl.isNullOrEmpty(fname)){
		     String str=new String(fname.getBytes("ISO-8859-1"),"UTF-8");
		     sql += " and c.fname like :str";
		     p.put("str", "%"+str.trim()+"%");
		    }
		    //sql += " limit 0,10";
		    list = this.saledeliverManager.QueryBySql(sql,p);
		   }
		   getResponse().getWriter().write(JSONUtil.result(true, "", "",list));
		  } catch (DJException e) {
		   getResponse().getWriter().write(JSONUtil.result(false, "", "",e.getMessage()));
		  }
		  return null;
		 }
		 public void getFTUCustAddress() throws IOException{
		  getResponse().setCharacterEncoding("utf-8");
		  try {
		   String fcustomer = getRequest().getParameter("fcustomer");
		   Params p = new Params();
		   String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
//		   String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
//		     " union select c.fsupplierid from  t_sys_userrole r "+
//		     " left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
		   String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid=:uid"+
		     " union select c.fsupplierid from  t_sys_userrole r "+
		     " left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid=:uid ) s where fsupplierid is not null " ;
		   p.put("uid", userid);
		   List<HashMap<String,Object>> list=this.saledeliverManager.QueryBySql(sql,p);
		   p.getData().clear();
		   if(list.size()>0){
//		    sql = "select a.fid,a.fname from t_bd_address a left join t_bd_custrelationadress cu on a.fid=cu.faddressid left join t_pdt_productreqallocationrules p on cu.fcustomerid=p.fcustomerid left join t_bd_customer c on p.fcustomerid = c.fid where c.fid='"+fcustomer+"'"
//		      +"and p.fsupplierid = '"+list.get(0).get("fsupplierid")+"'";
		    sql = "select a.fid,a.fname from t_bd_address a left join t_bd_custrelationadress cu on a.fid=cu.faddressid left join t_pdt_productreqallocationrules p on cu.fcustomerid=p.fcustomerid left join t_bd_customer c on p.fcustomerid = c.fid where c.fid=:fid "
		      +"and p.fsupplierid = :fsid";
		    p.put("fid", fcustomer);
		    p.put("fsid", list.get(0).get("fsupplierid"));
		     list = this.saledeliverManager.QueryBySql(sql,p);
		   }
		   getResponse().getWriter().write(JSONUtil.result(true,"","",list));
		  } catch (DJException e) {
		   getResponse().getWriter().write(JSONUtil.result(false,e.getMessage(),"",""));
		  }
		 }
		 
		 /*产品列表*/
		 public String selectProductsByCustomers() throws IOException {
		  getResponse().setCharacterEncoding("utf-8");
		  try {
		   String queryName = getRequest().getParameter("fname");
		   String fcustomer = getRequest().getParameter("fcustomer");
		   Params p = new Params();
		   String supplierid = getSupplier();
//		   String sql = "select CONCAT_WS(' / ',c.fname,c.fspec,''+c.fprice)  fviewname,c.fname fname,c.funit,c.fprice fdanjia,c.fspec,c.fid from t_bd_custproduct c left join t_pdt_productdef p on c.fproductid=p.fid left join t_bd_customer cu on c.fcustomerid=cu.fid where 1=1 and cu.fid='"+fcustomer+"'"
//		     +" and p.fsupplierid ='"+supplierid+"'";
		   String sql = "select CONCAT_WS(' / ',c.fname,c.fspec,''+c.fprice)  fviewname,c.fname fname,c.funit,c.fprice fdanjia,c.fspec,c.fid from t_bd_custproduct c left join t_pdt_productdef p on c.fproductid=p.fid left join t_bd_customer cu on c.fcustomerid=cu.fid where 1=1 and cu.fid=:fcus"
		     +" and p.fsupplierid =:fsid ";
		   p.put("fcus", fcustomer);
		   p.put("fsid", supplierid);
		   if(!StringUitl.isNullOrEmpty(queryName)){
		    String str=new String(queryName.getBytes("ISO-8859-1"),"UTF-8");
		    sql += String.format("and (c.fname like '%s' or c.fspec like '%s' )","%"+str+"%","%"+str+"%");
		   }
		   sql += " limit 0,10";
		   List result = this.saledeliverManager.QueryBySql(sql, p);
		   getResponse().getWriter().write(JSONUtil.result(true,"","", result));
		  } catch (DJException e) {
		   getResponse().getWriter().write(JSONUtil.result(false, "", "",e.getMessage()));
		  }
		  return null;
		 }
		 
		 
		 public String getSupplier(){
		  Params p = new Params();
		  String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		  String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid=:uid"+
		    " union select c.fsupplierid from  t_sys_userrole r "+
		    " left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid=:uid ) s where fsupplierid is not null " ;
		  p.put("uid", userid);
		  List<HashMap<String,Object>> list=this.saledeliverManager.QueryBySql(sql,p);
		  return String.valueOf(list.get(0).get("fsupplierid"));
		 } 
		 
		 
		 public void getFTUunit() throws IOException{
		  getResponse().setCharacterEncoding("utf-8");
		  try {
		   String fsupplierid = getSupplier();
		   Params p = new Params();
//		   String sql = "select sd.funit fname from t_ftu_saledeliver sa left join t_ftu_saledeliverentry sd on sa.fid=sd.fparentid where 1=1"+
//		     " and sa.fsupplierid='"+fsupplierid+"' GROUP BY sd.funit";
		   String sql = "select sd.funit fname from t_ftu_saledeliver sa left join t_ftu_saledeliverentry sd on sa.fid=sd.fparentid where 1=1"+
		     " and sa.fsupplierid=:fsid GROUP BY sd.funit";
		   p.put("fsid", fsupplierid);
		   List list = this.saledeliverManager.QueryBySql(sql,p);
		   getResponse().getWriter().write(JSONUtil.result(true, "","" ,list)); 
		  } catch (DJException e) {
		   // TODO: handle exception
		   getResponse().getWriter().write(JSONUtil.result(false, "", "",e.getMessage()));
		  }
		 }
		 
		 
		 public List<HashMap<String, Object>> getFtuRelationName(HttpServletRequest request,int i) throws IOException {
		  String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		  Params p = new Params();
		  String queryName = getRequest().getParameter("fname");
//		  String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
//		    " union select c.fsupplierid from  t_sys_userrole r "+
//		    " left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
		  String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid=:uid"+
		    " union select c.fsupplierid from  t_sys_userrole r "+
		    " left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid=:uid ) s where fsupplierid is not null " ;
		  p.put("uid", userid);
		  List<HashMap<String, Object>> custList=this.saledeliverManager.QueryBySql(sql,p);
		  p.getData().clear();
		  String supplierid = null;
		  if(custList.size()>0)
		  {
		   HashMap<String,Object> map=custList.get(0);
		   supplierid = (String)map.get("fsupplierid");
		  }
//		  sql = "select fid,fname,ftel from t_ftu_relation where fsupplierid='"+supplierid+"' and ftype = "+i;
		  sql = "select fid,fname,ftel from t_ftu_relation where fsupplierid=:fsid and ftype =:ftype ";
		  p.put("fsid", supplierid);
		  p.put("ftype", i);
		  if(!StringUitl.isNullOrEmpty(queryName)){
		   String str=new String(queryName.getBytes("ISO-8859-1"),"UTF-8");
		   sql += " and fname like :str";
		   p.put("str", "%"+str+"%");
		  }
		  List<HashMap<String, Object>> list = this.saledeliverManager.QueryBySql(sql,p);
		  return list;
		 }
	
	
	public String selectFtuClerk() throws IOException {
		getResponse().setCharacterEncoding("utf-8");
		try {
			List<HashMap<String, Object>> list = getFtuRelationName(getRequest(), 2);
			getResponse().getWriter().write(JSONUtil.result(true, "", "",list));
		} catch (DJException e) {
			getResponse().getWriter().write(JSONUtil.result(false, "", "",e.getMessage()));
		}
		return null;
	}
	public String selectFtuDriver() throws IOException {
		getResponse().setCharacterEncoding("utf-8");
		try {
			List<HashMap<String, Object>> list = getFtuRelationName(getRequest(), 1);
			getResponse().getWriter().write(JSONUtil.result(true, "", "",list));
		} catch (DJException e) {
			getResponse().getWriter().write(JSONUtil.result(false, "", "",e.getMessage()));
		}
		return null;
	}
	public void getNumberParams() throws IOException{
		try {
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			Params p = new Params();
//			String sql = "select fid,fieldtype,fdecimals,fedit,fcomputationalformula,falias,fuserid,fname,fisprint,fsaledeliverentry from t_ftu_parameter where fieldtype =1 and fuserid='"+userid+"'";
			String sql = "select fid,fieldtype,fdecimals,fedit,fcomputationalformula,falias,fuserid,fname,fisprint,fsaledeliverentry from t_ftu_parameter where fieldtype =1 and fuserid=:uid";
			p.put("uid", userid);
			List list = this.saledeliverManager.QueryBySql(sql,p);
			getResponse().getWriter().write(JSONUtil.result(true,"","",list));
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(JSONUtil.result(false,e.getMessage(),"",""));
		}
	}
	public void getFtuFcomputationalformula() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			Params p = new Params();
//			String sql = "select fid,fsaledeliverentry,fieldtype,ifnull(fisprint,0) fisprint,ifnull(fdecimals,0) fdecimals,ifnull(fedit,0) fedit,ifnull(fcomputationalformula,'') fcomputationalformula,falias,fuserid,fname from t_ftu_parameter where fuserid='"+userid+"'";
			String sql = "select fid,fsaledeliverentry,fieldtype,ifnull(fisprint,0) fisprint,ifnull(fdecimals,0) fdecimals,ifnull(fedit,0) fedit,ifnull(fcomputationalformula,'') fcomputationalformula,falias,fuserid,fname from t_ftu_parameter where fuserid=:uid";
			p.put("uid", userid);
			List result = this.saledeliverManager.QueryBySql(sql, p);
			getResponse().getWriter().write(
					JSONUtil.result(true, "","",result));
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(
					JSONUtil.result(false, e.getMessage(), "", ""));
		}
	}
	public void saveOrUpdateFtusaledeliver() throws Exception{
		try {
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String data = getRequest().getParameter("FTUSaledeliverEntry");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		    JSONArray json = JSONArray.fromObject(data);
		    List<FTUSaledeliverEntry> fse=(ArrayList<FTUSaledeliverEntry>)JSONArray.toList(json, FTUSaledeliverEntry.class);
			//List<FTUSaledeliverEntry> fse = (List<FTUSaledeliverEntry>)getRequest().getAttribute("FTUSaledeliverEntry");
			//FTUSaledeliver fs = (FTUSaledeliver)getRequest().getAttribute("saledeliver");
			data = getRequest().getParameter("FTUSaledeliver");
			JSONObject jo = JSONObject.fromObject(data);
			FTUSaledeliver fs = (FTUSaledeliver) JSONObject.toBean(jo, FTUSaledeliver.class);
			fs.setFcreatetime(formatter.parse(jo.getString("fcreatetime")));
			String fsupplierid = getSupplier();
			this.saledeliverManager.saveNewFTUSaleDeliver(fsupplierid,fs,fse,userid);
			getResponse().getWriter().write(JSONUtil.result(true, fs.getFid(),"" ,""));
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(
					JSONUtil.result(false,e.getMessage(),"",""));
		}
	}
	public void delFTUsaledeliver() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String fids = getRequest().getParameter("fidcls");
			//fids = "'"+fids.replaceAll(",", "','")+"'";
			Params param=new Params();
			String sql = "update t_ftu_saledeliver set fstate=1 where fid in (:fid)";
			param.put("fid", fids);
			this.saledeliverManager.ExecBySql(sql,param);
			getResponse().getWriter().write(JSONUtil.result(true,"删除成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(JSONUtil.result(false,"删除失败","",""));
		}
	}
	public void updateFTUstate() throws IOException{
		getResponse().setCharacterEncoding("utf-8");
		try {
			String fid = getRequest().getParameter("fid");
			fid = "'"+fid.replaceAll(",", "','")+"'";
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			this.saledeliverManager.updateFTUstate(fid, userid);
			getResponse().getWriter().write(JSONUtil.result(true,"回收成功！","",""));
		} catch (DJException e) {
			// TODO: handle exception
			getResponse().getWriter().write(JSONUtil.result(false,e.getMessage(),"",""));
		}
	}
	
	public String loadRpt(){
		String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fsupplierid=saledeliverManager.getFsupplieridByUser(userid);
		String where =" where sd.fsupplierid=?  and sd.fstate <>1 and   sd.fcustomer=?";
		List<Object> ls = new ArrayList<Object>();
		ls.add(fsupplierid);
		if(fsupplierid==null||StringUitl.isNullOrEmpty(getRequest().getParameter("fcustomerid")))
		{
			PageModel<HashMap<String,Object>> emp=new PageModel<HashMap<String,Object>>();
			emp.setList(new ArrayList<HashMap<String,Object>>());
			emp.setPageSize(10);
			return writeAjaxResponse(JSONUtil.result(emp));
		}
		ls.add(getRequest().getParameter("fcustomerid"));//需传客户值才有数据
		Object[] queryParams=null;
		Map<String, String> orderby = new LinkedHashMap<String,String>();//多个顺序排序
		orderby.put("sd.fnumber", "DESC");
		orderby.put("sa.fsequence","ASC");
		if(!StringUitl.isNullOrEmpty(getRequest().getParameter("timeQuantum")))
		{
			String timeQuantum = getRequest().getParameter("timeQuantum");
			where +=" and sd.fprinttime between  ? and  ? ";
			ls.add(timeQuantum.split(" 到 ")[0].trim()+" 00:00:00");
			ls.add(timeQuantum.split(" 到 ")[1].trim()+" 23:59:59");
		}
		    queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		    PageModel<HashMap<String,Object>>	pageModel = this.saledeliverManager.findBySqlWithRpt(where, queryParams, orderby, pageNo, pageSize);
		return writeAjaxResponse(JSONUtil.result(pageModel));
	}
	//获取报表中的客户对象
	public String getRptInfo()
	{
		String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fsupplierid=saledeliverManager.getFsupplieridByUser(userid);
		List list=this.saledeliverManager.getCustomerByfsupplier(fsupplierid);
		getRequest().setAttribute("customer", list);
		return RPT_JSP;
	}


	/**
	 * 导出execl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String excelFTUstatement(){
		try {
			Params p=new Params();
			String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String sql = "select fcustomername from t_sys_user where fid='"+userid+"'";		
			List<HashMap<String,Object>> list=this.saledeliverManager.QueryBySql(sql, p);
			String fsupplier =(String)list.get(0).get("fcustomername");
			
			String fsupplierid=saledeliverManager.getFsupplieridByUser(userid);
			String where =" where sd.fsupplierid=?  and sd.fstate <>1 and   sd.fcustomer=?";
			List<Object> ls = new ArrayList<Object>();
			ls.add(fsupplierid);
			if(fsupplierid==null||StringUitl.isNullOrEmpty(getRequest().getParameter("fcustomerid")))
			{
				throw new Exception("请选择客户");
			}
			Customer c=customerManager.get(getRequest().getParameter("fcustomerid"));
			String header = c.getFname();
			ls.add(getRequest().getParameter("fcustomerid"));//需传客户值才有数据
			Object[] queryParams=null;
			Map<String, String> orderby = new LinkedHashMap<String,String>();
			orderby.put("sd.fnumber", "DESC");
			orderby.put("sa.fsequence","ASC");
			if(!StringUitl.isNullOrEmpty(getRequest().getParameter("timeQuantum")))
			{
				header+=getRequest().getParameter("timeQuantum").replace(" ", "");
				String timeQuantum = getRequest().getParameter("timeQuantum");
				where +=" and sd.fprinttime between  ? and  ? ";
				ls.add(timeQuantum.split(" 到 ")[0].trim()+" 00:00:00");
				ls.add(timeQuantum.split(" 到 ")[1].trim()+" 23:59:59");
			}
			//选择数据导出
			String fids=getRequest().getParameter("fids");
			if(!StringUitl.isNullOrEmpty(fids)){
				fids = fids.replaceAll(",", "','");
				where =where + " and sa.fid in ('"+fids+"')";
			}
			
			queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		   List<HashMap<String,Object>>	ftulist = this.saledeliverManager.getFTUstatements(where , queryParams, orderby);
		//时间过滤，客户过滤
			/*********导出功能******/
		   header+="销售清单";
		   //header =new String(header.getBytes("ISO-8859-1"),"UTF-8");
			ftutoexcel(header,ftulist,fsupplier);
		}catch(Exception e)
		{
			return writeAjaxResponse("导出失败!"+e.getMessage());
		}
		return null;
	}
	/**
	 * 生成execl文件流
	 * @param header
	 * @param ftulist
	 * @param fsupplier
	 * @throws Exception
	 */
	private  void ftutoexcel(String header, List ftulist,String fsupplier)throws Exception
	{
		WritableWorkbook book = null;
		try {
			getResponse().setContentType("multipart/form-data");
			getResponse().setHeader("Content-Disposition",
					"attachment;fileName=" + URLEncoder.encode(header,"utf-8")+ ".xls");
			book = Workbook.createWorkbook(getResponse().getOutputStream());
			WritableSheet sheet = book.createSheet("数据", 0);
            String headerArr[] = {"序号","出库时间","产品名称","规格","数量","单价","金额","出库单编号","备注"};
            int[] headerArrHight = {10,20,20,20,10,10,10,20,20};

			List<HashMap<String, Object>> list = ftulist;
			WritableFont bold = new WritableFont(WritableFont.createFont("宋体"),18,WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(
					bold);
			headerFormat.setAlignment(Alignment.CENTRE);
			sheet.mergeCells(0,0, headerArr.length-1,0);
			sheet.addCell(new Label(0, 0, header,headerFormat));//标题
			
			
			WritableCellFormat colsStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),14));
			colsStyle.setAlignment(Alignment.CENTRE);//列标题样式
			colsStyle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat contentStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),12));
			contentStyle.setAlignment(Alignment.CENTRE);//内容样式
			contentStyle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			for (int i=0;i<headerArr.length;i++) {//设置标题头
				sheet.addCell(new Label(i, 1, headerArr[i],colsStyle));
				sheet.setColumnView(i, headerArrHight[i]);
			}
			int rowNum = 2;
			int number = 0;
			BigDecimal famount = BigDecimal.ZERO;
			BigDecimal fprices = BigDecimal.ZERO;
			CellView cellView = new CellView();  
		    cellView.setAutosize(true); //设置自动大小
		    //设置内容
		    for(int i=0;i<list.size();i++){
		    	sheet.addCell(new Label(0, rowNum,String.valueOf(++number),contentStyle));
		    	sheet.addCell(new Label(1, rowNum,(String)list.get(i).get("fprinttime"),contentStyle));
		    	sheet.addCell(new Label(2, rowNum,(String)list.get(i).get("fname"),contentStyle));
		    	sheet.addCell(new Label(3, rowNum,(String)list.get(i).get("fspec"),contentStyle));
		    	sheet.addCell(new jxl.write.Number(4, rowNum,StringUitl.isNullOrEmpty(list.get(i).get("famount"))?0:Double.valueOf((String)list.get(i).get("famount")),contentStyle));
		    	sheet.addCell(new jxl.write.Number(5, rowNum,StringUitl.isNullOrEmpty(list.get(i).get("fprice"))?0:Double.valueOf((String)list.get(i).get("fprice")),contentStyle));
		    	sheet.addCell(new jxl.write.Number(6, rowNum,StringUitl.isNullOrEmpty(list.get(i).get("fprices"))?0:Double.valueOf((String)list.get(i).get("fprices")),contentStyle));
		    	sheet.addCell(new Label(7, rowNum,(String)list.get(i).get("fnumber"),contentStyle));
		    	sheet.addCell(new Label(8, rowNum,(String)list.get(i).get("fdescription"),contentStyle));
		    	if(!StringUitl.isNullOrEmpty(list.get(i).get("famount"))){
					famount = famount.add(new BigDecimal((String)list.get(i).get("famount")));
				}
		    	if(!StringUitl.isNullOrEmpty(list.get(i).get("fprices"))){
		    		fprices = fprices.add(new BigDecimal((String)list.get(i).get("fprices")));
				}
				rowNum++;
			}
			WritableCellFormat totalStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),14,WritableFont.BOLD));
			totalStyle.setAlignment(Alignment.CENTRE);//合计样式
			totalStyle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			sheet.mergeCells(0,rowNum, headerArr.length-1,rowNum);
			sheet.addCell(new Label(0, rowNum,"合计:                       数量："+famount+"                  金额："+fprices,totalStyle));
			
			totalStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),14,WritableFont.BOLD));
			totalStyle.setAlignment(Alignment.CENTRE);
			sheet.mergeCells(headerArr.length-2,rowNum+2, headerArr.length-1,rowNum+2);
			sheet.addCell(new Label(headerArr.length-2, rowNum+2,fsupplier,totalStyle));
			book.write();
		} catch (IOException | WriteException e) {
			throw new Exception(e);
		} finally {
			try {
				book.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public String execFtulist(){
		String where =" where usp.fuserid  = ?  and sa.fstate !=1 ";
		Object[] queryParams=null;
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("sa.fnumber", "DESC");
		orderby.put("sd.fsequence","ASC");
		String fcreatetime = getRequest().getParameter("fcreatetime");
		String queryfilter = getRequest().getParameter("queryfilter");
		List<Object> ls = new ArrayList<Object>();
		    ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		    if(!StringUitl.isNullOrEmpty(fcreatetime)){
		    	where += " and (DATE_FORMAT(sa.fcreatetime,'%Y-%m-%d')>=? and DATE_FORMAT(sa.fcreatetime,'%Y-%m-%d')<=?)";
		    	ls.add(fcreatetime.split("到")[0].trim());
		    	ls.add(fcreatetime.split("到")[1].trim());
		    }
		    if(!StringUitl.isNullOrEmpty(queryfilter)){
		    	where += "  and (sa.fnumber like ? or c.fname like ? or p.fspec like ? or p.fname like ? or sa.fphone like ?)";
		    	ls.add("%"+queryfilter+"%");
		    	ls.add("%"+queryfilter+"%");
		    	ls.add("%"+queryfilter+"%");
		    	ls.add("%"+queryfilter+"%");
		    	ls.add("%"+queryfilter+"%");
		    }
		 queryParams = (Object[])ls.toArray(new Object[ls.size()]);
	
			
		WritableWorkbook book = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");

		String fileName = format.format(new Date());
		try {
			getResponse().setContentType("multipart/form-data");
			getResponse().setHeader("Content-Disposition",
					"attachment;fileName=" + fileName+ ".xls");
			book = Workbook.createWorkbook(getResponse().getOutputStream());
			WritableSheet wsheet = book.createSheet("送货凭证", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
            Label titleLabel = new Label( 0 , 0 , " 送货凭证",wcf);
            wsheet.addCell(titleLabel);
            wsheet.setRowView(0, 1000); // 设置第一行的高度
            int[] headerArrHight = {10,20,20,20,30,30,20,20,20,20,20,20,20,20};
            String headerArr[] = {"序号","客户名称","联系电话","出库单编号","产品名称","规格","数量","单位","单价","金额","开单时间","出库时间","备注","司机"};            
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);
            }
    		List<FTUSaledeliver> ftulist = this.saledeliverManager.getFtulist(where, queryParams, orderby);
    		int conut = 2;
    		for(int i=0;i<ftulist.size();i++){
    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
    			wsheet.addCell(new Label( 1 , conut ,ftulist.get(i).getFcustomer(),wcf2));
    			wsheet.addCell(new Label(2, conut,ftulist.get(i).getFphone(),wcf2));
    			wsheet.addCell(new Label(3, conut,ftulist.get(i).getFnumber(),wcf2));
    			wsheet.addCell(new Label(4, conut,ftulist.get(i).getFname(),wcf2));
    			wsheet.addCell(new Label(5, conut,ftulist.get(i).getFspec(),wcf2));
    			wsheet.addCell(new jxl.write.Number(6, conut,StringUitl.isNullOrEmpty(ftulist.get(i).getFamount())?0:Double.valueOf(ftulist.get(i).getFamount()),wcf2));
    			wsheet.addCell(new Label(7, conut,ftulist.get(i).getFunit(),wcf2));
    			wsheet.addCell(new jxl.write.Number(8, conut,StringUitl.isNullOrEmpty(ftulist.get(i).getFdanjia())?0:Double.valueOf(ftulist.get(i).getFdanjia()),wcf2));
    			wsheet.addCell(new jxl.write.Number(9, conut,StringUitl.isNullOrEmpty(ftulist.get(i).getFprice())?0:Double.valueOf(ftulist.get(i).getFprice()),wcf2));
    			wsheet.addCell(new Label(10, conut,timeformat.format(ftulist.get(i).getFcreatetime()),wcf2));
    			wsheet.addCell(new Label(11, conut,ftulist.get(i).getFprinttime()==null?"":timeformat.format(ftulist.get(i).getFprinttime()),wcf2));
    			wsheet.addCell(new Label(12, conut,ftulist.get(i).getFdescription(),wcf2));
    			wsheet.addCell(new Label(13, conut,ftulist.get(i).getFdriver(),wcf2));
    			conut++;
    		}
    		book.write();
		} catch (IOException | WriteException e) {
			return writeAjaxResponse("导出失败!");
		} finally {
			try {
				book.close();
			} catch (Exception e) {
				return writeAjaxResponse("导出失败!"+e.getMessage());
			}
		}
		return null;
	}
	
}
