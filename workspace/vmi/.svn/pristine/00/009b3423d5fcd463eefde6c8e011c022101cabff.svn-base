package Com.Dao.System;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import jxl.read.biff.BiffException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.UploadFile;
import Com.Base.Util.Excel.ExcelUtils;
import Com.Entity.System.Address;
import Com.Entity.System.CustRelationAdress;
import Com.Entity.System.Custaccountbalance;
import Com.Entity.System.Customer;
import Com.Entity.System.Productreqallocationrules;
import Com.Entity.System.Useronline;
@Service("customerDao")
public class CustomerDao extends BaseDao implements ICustomer {
	@Resource
	private IBaseSysDao baseSysDao;
	
	@Override
	public HashMap<String, Object> ExecSave(Customer cust) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (cust.getFid().isEmpty()) {
			cust.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(cust);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Customer Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Customer.class, fid);
	}

	@Override
	public void ExecImpCusSDK(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Customer cus=(Customer)request.getAttribute("Customer");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		if(cus==null || cus.getFid()==null || cus.getFid().equals(""))
		{
			throw new DJException("客户ID为空");
		}
		String sql=" select fid from t_bd_customer where fid ='"+cus.getFid()+"' ";
		if(QueryExistsBySql(sql))
		{
			return;
		}
		cus.setFcreatetime(new Date());
		cus.setFcreatorid(userid);
		cus.setFlastupdatetime(new Date());
		cus.setFlastupdateuserid(userid);
		saveOrUpdate(cus);
	}
	
	@Override
	public void ExecSaveCustaccountbalanceSDK(String customerid) {
		// TODO Auto-generated method stub
		String sql=" delete from t_bd_custaccountbalance ";
		this.ExecBySql(sql);
		String[] a = customerid.split(",");
		
		for(int i=0;i<a.length;i++){
			if(QueryExistsBySql("select 1 from t_bd_customer where fid='"+a[i]+"'")){
				Custaccountbalance cabinfo = new Custaccountbalance();
				cabinfo.setFcreatetime(new Date());
				cabinfo.setFcustomerid(a[i]);
				cabinfo.setFid(this.CreateUUid());
				cabinfo.setFisread(0);
				saveOrUpdate(cabinfo);
			}
		}
	}
	
	/**
	 * 客户管理Excel上传客户
	 */
		@Override
		public int saveCustomerExcel(HttpServletRequest request) throws IOException {
			
			Sheet sheet = ExcelUtils.getSheet(request);
			Iterator<Row> rit = sheet.rowIterator();
			Row row;
			int currentRow = 0;
			int length = 0;
			Iterator<Cell> cit;
			while(rit.hasNext()){
				row = rit.next();
				currentRow += 1;
				if(currentRow==1){
					cit = row.cellIterator();
					boolean verify = custFormVerify(cit);
					if(verify==false){
						throw new DJException("上传Excel格式不正确");
					}
				}
				if(currentRow>1){
					cit = row.cellIterator();
					List<String> list = saveCellCust(cit);
					if(list!=null){
						try{
						boolean m = saveListToTable(list,request);
						if(m==true){
							length+=1;
						}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
//					else{
//						throw new DJException("请重新检验Excel中的必填项");
//					}
				}
			}
//			if(length==0){
//				throw new DJException("请重新检验Excel");
//			}
			return length;
		}
		
		public List<String> saveCellCust(Iterator<Cell> cit){
			int i = 0;
			int y = 0;
			List<String> list = new ArrayList<String>();
			while(cit.hasNext()){
				i=i+1;
				String a = ExcelUtils.getStringCellValue(cit.next());
				if(a.equals("") && (i==2||i==3)){
					y++;
				}
				if(y>1){
					return null;
				}
				if(i>1){
					list.add(a);
				}
			}
			return list;
		}
		
		public boolean saveListToTable(List<String> list,HttpServletRequest request)throws IOException, ParseException {
			try {
				String userid  = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
				List<HashMap<String,Object>>map = this.QueryBySql("select fsupplierid from t_bd_usersupplier where fuserid='"+userid+"'");
				String fsupplierid = map.get(0).get("fsupplierid").toString();
				Customer c = new Customer();
				String sql = "select c.fid from t_bd_customer c left join t_pdt_productreqallocationrules p on c.fid=p.fcustomerid where c.fname='"+list.get(0)+"' and p.fsupplierid = '"+fsupplierid+"'";
				map = this.QueryBySql(sql);
				if(map.size()==0){//客户
					c.setFid(this.CreateUUid());
					c.setFname(list.get(0));
					c.setFphone(list.get(1));
					c.setFnumber(list.get(0));
					c.setFaddress(list.get(2));
					c.setFdescription(list.get(3));
					c.setFcreatetime(new Date());
					this.saveOrUpdate(c);
					
					Productreqallocationrules p = new Productreqallocationrules();
					p.setFid(this.CreateUUid());
					p.setFcreatetime(new Date());
					p.setFcreatorid(userid);
					p.setFcustomerid(c.getFid());
					p.setFsupplierid(fsupplierid);
					this.saveOrUpdate(p);
					
				}else{
					return false;
				}
				
				return true;
			} catch (DJException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		/**
		 * 检验在客户管理上传EXCEL格式是否正确
		 * @param cit
		 */
		public boolean custFormVerify(Iterator<Cell> cit){
			String[] name = {"编号","* 客户名称","* 联系电话","地址","描述"};
			List<String> list = new ArrayList<String>();
			while(cit.hasNext()){
				String a = ExcelUtils.getStringCellValue(cit.next());
				list.add(a);
			}
			for (int i = 0; i < name.length; i++) {
				String m = name[i];
				if(!list.get(i).equals(m)){
					return false;
				}
			}
			return true;
		}
		@Override
		public void saveOrUpdateMyCustomer(HttpServletRequest request){

			List<Address> addressList = (List<Address>)request.getAttribute("Address");
			Customer customer = (Customer)request.getAttribute("Customer");
			Productreqallocationrules p = new Productreqallocationrules();
			
//			Address address = new Address();
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String fsupplierid = baseSysDao.getCurrentSupplierid(userid);
			if(!StringUtils.isEmpty(customer)){
				
				if(StringUtils.isEmpty(customer.getFid())){//客户表及选择供应商表
					customer.setFid(this.CreateUUid());
					customer.setFcreatetime(new Date());
					customer.setFcreatorid(userid);
					
					
					p.setFid(this.CreateUUid());
					p.setFcreatetime(new Date());
					p.setFcreatorid(userid);
					p.setFcustomerid(customer.getFid());
					p.setFsupplierid(fsupplierid);
					this.saveOrUpdate(p);
				}
				saveOrUpdateAddress(request,customer.getFid());//保存地址
				
				this.saveOrUpdate(customer);
				
			}
		
		}
		@Override
		public void saveOrUpdateAddress(HttpServletRequest request,String customerid){
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			List<Address> addressList = (List<Address>)request.getAttribute("Address");
			String sql = "select faddressid from t_bd_custrelationadress where fcustomerid='"+customerid+"'";
			List<HashMap<String,Object>> list = this.QueryBySql(sql);
			if(list.size()>0){//如果有地址 删除是所有的地址
				sql = "delete from t_bd_custrelationadress where fcustomerid='"+customerid+"'";
				this.ExecBySql(sql);
				String addressid = "";
				for(int i = 0;i<list.size();i++){
					addressid += list.get(0).get("faddressid");
					if(i<list.size()-1){
						addressid += ",";
					}
				}
				addressid = addressid.replaceAll(",", "','");
				sql = "delete from t_bd_address where fid in('"+addressid+"')";
				this.ExecBySql(sql);
			}
			if(!StringUtils.isEmpty(addressList)){//如果填写了地址 保存地址信息和客户管理地址
				for(Address address : addressList){
						address.setFid(this.CreateUUid());
						address.setFcreatetime(new Date());
						address.setFcreatorid(userid);
						address.setFdetailaddress(address.getFname());
						address.setFcustomerid(customerid);
						
						CustRelationAdress cd = new CustRelationAdress();
						cd.setFid(this.CreateUUid());
						cd.setFaddressid(address.getFid());
						cd.setFcustomerid(customerid);
						cd.setFeffect(1);
						this.saveOrUpdate(cd);//客户关联地址表
						this.saveOrUpdate(address);//地址表
					
				}
			}
		}
		
		@Override
		public void DelMyCustomer(HttpServletRequest request,String customerid){
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String fsupplierid = baseSysDao.getCurrentSupplierid(userid);
			for(String fid : customerid.split(",")){
				String sql ="select fid from t_pdt_productreqallocationrules where fcustomerid='"+fid+"'";
				List<HashMap<String,Object>> list = this.QueryBySql(sql);
				if(list.size()>1){
					new DJException("该客户已被其他制造商关联,不能删除！");
				}
				sql = "select fisinvited from t_bd_customer where fid='"+fid+"'";
				list = this.QueryBySql(sql);
				if("1".equals(list.get(0).get("fisinvited"))){
					new DJException("该客户已被邀请,不能删除！");
				}
				sql="select fid from t_ftu_saledeliver where fcustomer ='"+fid+"' and fstate != 1";
				if(this.QueryExistsBySql(sql)){
					throw new DJException("该客户已经生成送货凭证，不能删除");
				}
				if(this.QueryExistsBySql("select fid from t_ord_deliverapply where fcustomerid ='"+fid+"'"))
				{
					throw new DJException("该客户已经要货，不能删除");
				}
				sql = "select faddressid from t_bd_custrelationadress where fcustomerid='"+fid+"'";
				list = this.QueryBySql(sql);
				if(list.size()>0){//如果有地址 删除是所有的地址
					sql = "delete from t_bd_custrelationadress where fcustomerid='"+fid+"'";
					this.ExecBySql(sql);
					String addressid = "";
					for(int i = 0;i<list.size();i++){
						addressid += list.get(i).get("faddressid");
						if(i<list.size()-1){
							addressid += ",";
						}
					}
					addressid = addressid.replaceAll(",", "','");
					sql = "delete from t_bd_address where fid in('"+addressid+"')";
					this.ExecBySql(sql);
				}
				sql = "delete from t_pdt_productreqallocationrules where fcustomerid='"+fid+"' and fsupplierid='"+fsupplierid+"'";
				this.ExecBySql(sql);//删除选择供应商
				sql = "delete from t_bd_customer where fid='"+fid+"'";
				this.ExecBySql(sql);//删除客户信息
			}
		}
		@Override
		public String saveMyCustomerExcel(HttpServletRequest request) throws BiffException, IOException {
			try {
				String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
				String sql = "select fid,fpath from t_ord_productdemandfile where fparentid='"+userid+"'";
				List<HashMap<String,Object>> list = this.QueryBySql(sql);
				String filePath = "";
				String fileid = "";
				int rows = 0;
				int length = 0;
				for(int i=0;i<list.size();i++){
					filePath = (String)list.get(i).get("fpath");
					fileid = (String)list.get(i).get("fid");
					if(filePath.indexOf(".xls")<0){
						throw new DJException("上传的是非Excel格式！");
					}
					InputStream is = new FileInputStream(filePath);
					Workbook book = ExcelUtils.getWorkBook(is);
					Sheet sheet = book.getSheetAt(0);
					Iterator<Row> rit = sheet.rowIterator();
					Row row;
					int currentRow = 0;
					Iterator<Cell> cit;
					while(rit.hasNext()){ 
						row = rit.next();
						currentRow += 1;
						if(currentRow==1){
							cit = row.cellIterator();
							boolean verify = MycustFormVerify(cit);
							if(verify==false){
								throw new DJException("上传Excel格式不正确");
							}
						}
						if(currentRow>1){
							cit = row.cellIterator();
							List<String> filelist = saveMyCellCust(cit);
							if(filelist!=null){
								try{
								boolean m = saveListToMyCustomer(filelist,request);
								if(m==true){
									length+=1;
								}
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						}
					}
					rows = rows+currentRow;//记录所有表的行数
					UploadFile.delFile(fileid);//不管是否上传成功都要删除所有已上传的附件
				}
				String result = "";
				if(length==rows-list.size()){
					result = "操作成功，共导入"+list.size()+"个Excel的"+length+"条数据!";
				}else{
					result = "操作成功，共导入"+list.size()+"个Excel的"+length+"条数据！还有"+(rows-length-list.size())+"条数据,因为客户名称相同或名称为空没有导入！";//currentRow-length-1 除去标题计算导入产品的总条数。
				}
				return result;
				  
			} catch (Exception e) {
				// TODO: handle exception
				throw new DJException("上传Excel格式不正确");
			}
		}
		public boolean MycustFormVerify(Iterator<Cell> cit){
			String[] name = {"编号","* 客户名称","手机号","座机号","传真","地址","收货人","电话","备注"};
			List<String> list = new ArrayList<String>();
			while(cit.hasNext()){
				String a = this.getStringCellValue(cit.next());
				list.add(a);
			}
			for (int i = 0; i < name.length; i++) {
				String m = name[i];
				if(!list.get(i).equals(m)){
					return false;
				}
			}
			return true;
		}
		public boolean saveListToMyCustomer(List<String> list,HttpServletRequest request)throws IOException, ParseException {
			try {
				String userid  = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
				List<HashMap<String,Object>>map = this.QueryBySql("select fsupplierid from t_bd_usersupplier where fuserid='"+userid+"'");
				String fsupplierid = map.get(0).get("fsupplierid").toString();
				Customer c = new Customer();
				Address address = new Address();
				String sql = "select c.fid from t_bd_customer c left join t_pdt_productreqallocationrules p on c.fid=p.fcustomerid where c.fname='"+list.get(0)+"' and p.fsupplierid = '"+fsupplierid+"'";
				map = this.QueryBySql(sql);
				if(map.size()==0){//客户
					c.setFid(this.CreateUUid());
					c.setFname(list.get(0));
					c.setFphone(list.get(1));
					c.setFartificialpersonphone(list.get(2));
					c.setFfax(list.get(3));
					c.setFaddress(list.get(4));
					c.setFdescription(list.get(7));
					c.setFcreatorid(userid);
					c.setFcreatetime(new Date());
					this.saveOrUpdate(c);
					
					Productreqallocationrules p = new Productreqallocationrules();
					p.setFid(this.CreateUUid());
					p.setFcreatetime(new Date());
					p.setFcreatorid(userid);
					p.setFcustomerid(c.getFid());
					p.setFsupplierid(fsupplierid);
					this.saveOrUpdate(p);
					
					address.setFid(this.CreateUUid());
					address.setFname(list.get(4));
					address.setFdetailaddress(list.get(4));
					address.setFlinkman(list.get(5));
					address.setFphone(list.get(6));
					address.setFcreatorid(userid);
					address.setFcreatetime(new Date());
					address.setFcustomerid(c.getFid());
					this.saveOrUpdate(address);//地址
					
					CustRelationAdress cd = new CustRelationAdress();
					cd.setFid(this.CreateUUid());
					cd.setFaddressid(address.getFid());
					cd.setFcustomerid(c.getFid());
					cd.setFeffect(1);
					this.saveOrUpdate(cd);//客户关联地址表
					
				}else{
					return false;
				}
				
				return true;
			} catch (DJException e) {
				e.printStackTrace();
			}
			return false;
		}
		public List<String> saveMyCellCust(Iterator<Cell> cit){
			int i = 0;
			List<String> list = new ArrayList<String>();
			while(cit.hasNext()){
				i=i+1;
				String a = this.getStringCellValue(cit.next());
				if(a.equals("") && (i==2)){
					return null;
				}
				if(i>1){
					list.add(a);
				}
			}
			return list;
		}
		
		public static String getStringCellValue(Cell cell) {
	        String strCell = "";
	        switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_STRING:
	            strCell = cell.getStringCellValue();
	            break;
	        case Cell.CELL_TYPE_NUMERIC:
	            double value = cell.getNumericCellValue();
	            
	            if(DateUtil.isCellDateFormatted(cell)){
	            	Date date = cell.getDateCellValue();
	            	Calendar c = Calendar.getInstance();
	            	c.setTime(date);
	            	strCell = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)+ "-" + c.get(Calendar.DAY_OF_MONTH);
	            }else{
	            	strCell = new DecimalFormat("#").format(value);
//	            	strCell = String.valueOf(value);
	            	
	            }
	            break;
	        case Cell.CELL_TYPE_BOOLEAN:
	            strCell = String.valueOf(cell.getBooleanCellValue());
	            break;
	        case Cell.CELL_TYPE_BLANK:
	            strCell = "";
	            break;
	        default:
	            strCell = "";
	            break;
	        }
	        if (strCell.equals("") || strCell == null) {
	            return "";
	        }
	        return strCell;
	    }
}
