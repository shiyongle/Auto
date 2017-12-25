package Com.Dao.System;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Dao.order.IDeliverapplyDao;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Productdef;
import Com.Entity.System.SupplierDeliverTime;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Productdemandfile;

@Service("SProductMaterialDao")
public class SProductMaterialDao extends BaseDao implements ISProductMaterialDao {
	
	@Resource
	IBaseSysDao baseSysDao;
	@Resource
	IDeliverapplyDao daDao;	
	@Resource
	IProductdefDao pdtDao;
	@Resource
	ICustproductDao cpDao;
	
	@Override
	public void saveSProduct(HttpServletRequest request) {
		String userid = baseSysDao.getCurrentUserId(request);
		String fsupplierid = baseSysDao.getCurrentSupplierid(userid);
		Productdef obj = (Productdef) request.getAttribute("Productdef");
		Custproduct custObj = null;
		Date now = new Date();
		if(StringUtils.isEmpty(obj.getFid())){
			obj.setFid(this.CreateUUid());
			obj.setFcreatorid(userid);
			obj.setFcreatetime(now);
			custObj = new Custproduct();
			custObj.setFid(obj.getFcustpdtid());
			custObj.setFcreatetime(now);
			custObj.setFproductid(obj.getFid());
		}else{
			String hql = "from Custproduct where fproductid = '"+obj.getFid()+"'";
			List<Custproduct> list = this.QueryByHql(hql);
			if(list.size()==0){
				throw new DJException("产品未关联客户产品！");
			}else{
				custObj = list.get(0);
			}
		}
		custObj.setFname(obj.getFname());
		custObj.setFcustomerid(obj.getFcustomerid());
		custObj.setFmaterial(obj.getFmaterialcode());
		custObj.setFdescription(obj.getFdescription());
		custObj.setFspec(obj.getFcharacter());
		custObj.setFprice(obj.getFprice());
		custObj.setFlastupdatetime(now);
		obj.setFsupplierid(fsupplierid);
		obj.setFeffect(1);
		obj.setFlastupdatetime(now);
		this.saveOrUpdate(custObj);
		this.saveOrUpdate(obj);
		//改版面图
		String ffileid = obj.getFfileid();
		if(!StringUtils.isEmpty(ffileid)){
			String sql = "update t_ord_productdemandfile set ftype='版面图' where fid= '"+ffileid+"'";
			if(this.ExecBySql(sql)>0){
				sql = "update t_ord_productdemandfile set ftype='' where fparentid= '"+custObj.getFid()+"' and fid != '"+ffileid+"'";
				this.ExecBySql(sql);
			}
		}
	}

	@Override
	public Productdemandfile saveFileOfSProduct(HttpServletRequest request) {
		String fparentid = request.getParameter("parentid");
		String sql = "update t_ord_productdemandfile set ftype='' where fparentid= :fparentid";
		params p = new params();
		p.put("fparentid", fparentid);
		this.ExecBySql(sql, p);
		Map<String,String> map = new HashMap<>();
		map.put("fparentid", fparentid);
		List<Productdemandfile> fileList = UploadFile.upload(request,map);
		return fileList.get(0);
	}
	/**
	 * 小数点后为0，则取整数
	 * @param fboxlength
	 * @return
	 */
	private String transferNum(BigDecimal fboxlength) {
		double num = fboxlength.doubleValue();
		 if(num % 1.0 == 0){
		        return String.valueOf((long)num);
	    }
	    return String.valueOf(num);
	}
	
	@Override
	public void saveBoardtoCustpdt(HttpServletRequest request) {
		String userid = baseSysDao.getCurrentUserId(request);
		String fsupplierid = baseSysDao.getCurrentSupplierid(userid);
		String apID = request.getParameter("fboardid");
		String fname = request.getParameter("fname");
		String fcustomerid =  request.getParameter("fcustomerid");
		String fdescription = request.getParameter("fdescription");
		BigDecimal fprice = new BigDecimal(request.getParameter("fprice"));
		Productdef pdtInfo = this.getPdtInfobyApid(apID);
		pdtInfo.setFcharacter(transferNum(pdtInfo.getFboxlength())+"x"+transferNum(pdtInfo.getFboxwidth())+"x"+transferNum(pdtInfo.getFboxheight()));
		pdtInfo.setFcreatetime(new Date());
		pdtInfo.setFcreatorid(userid);
		pdtInfo.setFsupplierid(fsupplierid);
		pdtInfo.setFname(fname);
		pdtInfo.setFcustomerid(fcustomerid);
		pdtInfo.setFdescription(fdescription);
		pdtInfo.setFprice(fprice);
		Custproduct custpdtInfo = new Custproduct();
		custpdtInfo.setFeffect(1);
		custpdtInfo.setFid(this.CreateUUid());
		custpdtInfo.setFproductid(pdtInfo.getFid());
		custpdtInfo.setFname(pdtInfo.getFname());
		custpdtInfo.setFcustomerid(pdtInfo.getFcustomerid());
		custpdtInfo.setFdescription(pdtInfo.getFdescription());
		custpdtInfo.setFspec(pdtInfo.getFcharacter());
		custpdtInfo.setFprice(pdtInfo.getFprice());
		custpdtInfo.setFspec(pdtInfo.getFcharacter());
		String sql = "select fid from t_bd_custproduct where fcustomerid='"+fcustomerid+"' and fname='"+pdtInfo.getFname()+"' and fspec='"+pdtInfo.getFcharacter()+"' and  feffect=1" ;
		if(baseSysDao.QueryExistsBySql(sql)){
			throw new DJException("已存在相同规格相同名称的产品！");
		}
		pdtDao.saveOrUpdate(pdtInfo);
		cpDao.saveOrUpdate(custpdtInfo);
	}
	
	

	private Productdef getPdtInfobyApid (String apID){
		Productdef pdtInfo = new Productdef();		
		Deliverapply daInfo = (Deliverapply)daDao.Query( apID);
		pdtInfo.setFid(this.CreateUUid());
		pdtInfo.setFeffect(1);
		//取出要货对应产品
        Productdef mtpdtInfo = pdtDao.Query(daInfo.getFmaterialfid());
        pdtInfo.setFmaterialfid(mtpdtInfo.getFid());
        pdtInfo.setFmtsupplierid(daInfo.getFsupplierid());
        pdtInfo.setFtilemodelid(mtpdtInfo.getFtilemodelid());
        pdtInfo.setFboxmodelid(daInfo.getFboxmodel().toString());
        pdtInfo.setFmateriallength(daInfo.getFmateriallength());
        pdtInfo.setFmaterialwidth(daInfo.getFmaterialwidth());
        pdtInfo.setFseries(daInfo.getFseries());
        pdtInfo.setFhformula(daInfo.getFhline());
        pdtInfo.setFhstaveexp(daInfo.getFhstaveexp());
        pdtInfo.setFvformula(daInfo.getFvline());
        pdtInfo.setFvstaveexp(daInfo.getFvstaveexp());
        pdtInfo.setFqueryname(mtpdtInfo.getFname()+"/"+mtpdtInfo.getFlayer()+mtpdtInfo.getFtilemodelid());
        pdtInfo.setFlayer(mtpdtInfo.getFlayer());
        pdtInfo.setFstavetype(daInfo.getFstavetype());
        pdtInfo.setFboxlength(daInfo.getFboxlength());
        pdtInfo.setFboxwidth(daInfo.getFboxwidth());
        pdtInfo.setFboxheight(daInfo.getFboxheight());
        pdtInfo.setFisboardcard(1);
        pdtInfo.setFhformula0(daInfo.getFhformula());
		pdtInfo.setFhformula1(daInfo.getFhformula1());
		pdtInfo.setFvformula0(daInfo.getFvformula());
		pdtInfo.setFdefine1(daInfo.getFdefine1());
		pdtInfo.setFdefine2(daInfo.getFdefine2());
		pdtInfo.setFdefine3(daInfo.getFdefine3());
		return pdtInfo;
	}

	@Override
	public void updateCustpdtByboard(HttpServletRequest request) {
		String userid = baseSysDao.getCurrentUserId(request);
		String fcustpdtid = request.getParameter("fcustpdtid");
		String fboardid =  request.getParameter("fboardid");
		Custproduct  cpInfo = cpDao.Query(fcustpdtid);
		Productdef opdtInfo = pdtDao.Query(cpInfo.getFproductid());
		Productdef pdtInfo = this.getPdtInfobyApid(fboardid);
		pdtInfo.setFid(cpInfo.getFproductid());
		if(StringUtils.isEmpty(pdtInfo.getFcharacter())){
			String spec = transferNum(pdtInfo.getFboxlength())+"x"+transferNum(pdtInfo.getFboxwidth())+"x"+transferNum(pdtInfo.getFboxheight());
			pdtInfo.setFcharacter(spec);
			cpInfo.setFspec(spec);
			this.saveOrUpdate(cpInfo);
		}
		//引用传递bug
		//Productdef pdtInfo = pdtDao.Query(cpInfo.getFproductid());
		if(pdtInfo.getFisboardcard()!=0){
			pdtInfo.setFlastupdatetime(new Date());
			pdtInfo.setFlastupdateuserid(userid);
			pdtInfo.setFsupplierid(opdtInfo.getFsupplierid());
			pdtInfo.setFname(opdtInfo.getFname());
			pdtInfo.setFcustomerid(opdtInfo.getFcustomerid());
			pdtInfo.setFdescription(opdtInfo.getFdescription());
			pdtInfo.setFprice(opdtInfo.getFprice());
			pdtInfo.setFisboardcard(1);
			pdtDao.Delete(opdtInfo);
			pdtDao.saveOrUpdate(pdtInfo);
		}
	}

	@Override
	public void saveSupplierDeliverTimeConfig(SupplierDeliverTime obj) {
		if(StringUtils.isEmpty(obj.getFid())){
			obj.setFid(this.CreateUUid());
		}
		this.saveOrUpdate(obj);
	}
}
