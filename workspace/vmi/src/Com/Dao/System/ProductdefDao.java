package Com.Dao.System;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.Excel.ExcelUtils;
import Com.Base.Util.file.MyMultiFileUploadHelper;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Controller.System.ProductdefController;
import Com.Entity.System.CommonMaterial;
import Com.Entity.System.Productdef;
import Com.Entity.System.Useronline;
import Com.Entity.order.Productdemandfile;

@Service("productdefDao")
public class ProductdefDao extends BaseDao implements IProductdefDao {

	public static final String VersionOfTheProductCanToperatingHistory = "历史版本的产品不能操作！";

	@Override
	public HashMap<String, Object> ExecSave(Productdef cust) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (cust.getFid().isEmpty()) {
			String fid = this.CreateUUid();
			cust.setFid(fid);
			if(cust.getFbaseid()==null){
				cust.setFbaseid(fid);
			}
		}
		this.saveOrUpdate(cust);

		params.put("success", true);
		return params;
	}

	@Override
	public Productdef Query(String fid) {
		// TODO Auto-generated method stub
		return (Productdef) this.getHibernateTemplate().get(Productdef.class,
				fid);
	}

	@Override
	public String QueryIsHistory(String fid) {
		boolean flag = false;

		String jsonString = "";

		String sql = "select d.fid FROM t_pdt_productdef d where d.fishistory=1 and d.fid in "
				+ fid;

		List list = this.QueryBySql(sql);

		flag = list.size() == 0;
		
		jsonString = flag == false ? VersionOfTheProductCanToperatingHistory : "false";
		
		return jsonString;
	}

	@Override
	public boolean dealHistoryProduct(String fid, HttpServletResponse reponse) throws IOException {
		// TODO Auto-generated method stub
		String st = QueryIsHistory(fid);
		
		if (st.equals("false")) {
			
			reponse.getWriter().write(
					JsonUtil.result(false, st, "", ""));
			
			return true;
			
		}
		
		return false;
	}

	@Override
	public boolean isHistoryProductVersion(String fid) {
		// TODO Auto-generated method stub
		
		Productdef pdt = Query(fid); 
		
		boolean r = pdt.getFishistory() == 1;
		
		return r;
	}

	@Override
	public void ExecDealWithUploadFile(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		//保存文件
		MyMultiFileUploadHelper myMultiFileUploadHelper = MyMultiFileUploadHelper.getMyMultiFileUploadHelper();
		
		String fileName = MySimpleToolsZ.getMySimpleToolsZ().dealWithChinese( request.getParameter("name"));
		
		String unionFileName = CreateUUid() + fileName.substring(fileName.indexOf("."));
		
		myMultiFileUploadHelper.upload("file/product", unionFileName, request);
		
		//保存实体
		Productdemandfile productdemandfile = new Productdemandfile();
		
		productdemandfile.setFname(fileName);
		productdemandfile.setFpath(request.getServletContext().getRealPath(
				ProductdefController.FILE_RELATIVE_PATH).replace("\\", "/") + "/" + unionFileName);
		productdemandfile.setFid(CreateUUid());
		productdemandfile.setFcreatetime(new Date());
		productdemandfile.setFparentid(request.getParameter("fparentid"));
		
		saveOrUpdate(productdemandfile);
		
	}

	@Override
	public void ExecDeleteProductAccessorys(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		
		
		String fids = request.getParameter("fids");
		
		String[] fidsS = fids.split(",");
		
		//删除文件
		for (String fid : fidsS) {
			
			Productdemandfile productdemandfile = (Productdemandfile) Query(Productdemandfile.class, fid);
			
			File file = new File(productdemandfile.getFpath());
			
			file.delete();
			
		}
		
		//删除数据库记录
		
		String fidsA = "('" + fids.replace(",", "','") + "')";
		
		String sql = " DELETE FROM t_ord_productdemandfile WHERE fid in %s ";
		
		sql = String.format(sql, fidsA);
		
		ExecBySql(sql);
		
		
	}
	@Override
	public int saveExcel(HttpServletRequest request) throws IOException{
		String fsupplierid = request.getParameter("action");
		Row row;
		int currentRow = 0;
		int length = 0;
		Sheet sheet = ExcelUtils.getSheet(request);
		Iterator<Row> rit = sheet.rowIterator();
		Iterator<Cell> cit;
		while(rit.hasNext()){
			row = rit.next();
			currentRow += 1;
			if(currentRow==2){
				cit = row.cellIterator();
				boolean verify = custFormVerify(cit);
				if(verify==false){
					throw new DJException("上传Excel格式不正确");
				}
			}
			if(currentRow>2){//从第三列开始
				cit = row.cellIterator();
				List<String> list = saveCellCust(cit);
				if(list!=null){
					try{
//						if("注意事项：".equals(list.get(0))){
//							break;
//						}
						boolean m = saveListToTable(fsupplierid,list,request);
						if(m==true){
							length+=1;
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
//				if(list==null && length==0){
//					throw new DJException("请重新检验Excel中的必填项");
//				}
			}
		}
		if(length==0){
			throw new DJException("请重新检验Excel");
		}
		return length;
	}
	public boolean custFormVerify(Iterator<Cell> cit){
		String[] name = {"序号","材料","代码","层数","楞型"};
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
	public boolean saveListToTable(String fsupplierid,List<String> list,HttpServletRequest request)throws IOException, ParseException {
		try {
			String userid  = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			Productdef p = new Productdef();
			p.setFid(this.CreateUUid());
			p.setFname(list.get(0));
			p.setFnumber(list.get(1));
			p.setFlayer((int)(0+Double.valueOf(list.get(2))));
			p.setFtilemodelid(list.get(3));
			p.setFcreatetime(new Date());
			p.setFboxtype(1);
			p.setFeffect(1);
			p.setFcreatorid(userid);
			p.setFsupplierid(fsupplierid);
			this.saveOrUpdate(p);
			return true;
		} catch (DJException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void ExecAddCommonMaterial(String fidcls, String fsupplierid,
			String fcustomerid) {
		String[] fids = fidcls.split(",");
		CommonMaterial obj;
		for(String materialid: fids){
			obj = new CommonMaterial();
			obj.setFid(this.CreateUUid());
			obj.setFcustomerid(fcustomerid);
			obj.setFsupplierid(fsupplierid);
			obj.setFmaterialid(materialid);
			this.saveOrUpdate(obj);
		}
	}

}
