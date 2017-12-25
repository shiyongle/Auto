package Com.Dao.System;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.VmipdtParam;
@Service("VmipdtParamDao")
public class VmipdtParamDao extends BaseDao implements IVmipdtParamDao {

	@Override
	public HashMap<String, Object> ExecSave(VmipdtParam vmi) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (vmi.getFid().isEmpty()) {
			vmi.setFid(this.CreateUUid());
//			this.saveOrUpdate(addr);
		}
//		else{
//			this.Update(addr);
//		}
			this.saveOrUpdate(vmi);
		
		params.put("success", true);
		return params;
	}

	@Override
	public VmipdtParam Query(String fid) {
		// TODO Auto-generated method stub
		return (VmipdtParam) this.getHibernateTemplate().get(
				VmipdtParam.class, fid);
	}

	@Override
	public HashMap<String, Object> ExecPlan(HttpServletRequest request) {
		// TODO Auto-generated method stub
//		String sql=" select t_pdt_vmiproductparam.fid,t_bd_customer.fid as fcustomerid,t_pdt_productdef.fid as fproductid,t_pdt_vmiproductparam.fminstock,t_pdt_vmiproductparam.forderamount,t_pdt_productdef.fnewtype from t_pdt_vmiproductparam,t_pdt_productdef,t_bd_customer where t_bd_customer.fid=t_pdt_productdef.fcustomerid and t_pdt_productdef.fid=t_pdt_vmiproductparam.fproductid "; 
//		List<HashMap<String, Object>> vmipcls= this.QueryBySql(sql);
//		if (vmipcls.size()<=0)
//		{
//			return null;
//		}
//		Calendar time = Calendar.getInstance();
//		time.set(Calendar.MINUTE,0);
//		time.set(Calendar.SECOND,0);
//		time.set(Calendar.DATE, time.getTime().getDate()+3);
//		time.set(Calendar.HOUR_OF_DAY, 9);
//		Date arriveDate = time.getTime();
//		time.set(Calendar.HOUR_OF_DAY, 6);
//		Date incomeTime = time.getTime();
//		String userid=((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
//		String number=this.getFnumber("t_ord_saleorder", "S",4);
//		String startstr=number.substring(0, number.length()-4);
//		int num=Integer.parseInt(number.substring(number.length()-4, number.length()));
//		for(int i=0;i<vmipcls.size();i++)
//		{
//			HashMap<String, Object> info=vmipcls.get(i);
//			if ("1".equals(info.get("fnewtype")) || "3".equals(info.get("fnewtype")))  //普通纸箱
//			{
//				String productid=info.get("fproductid").toString();
//				String customerid=info.get("fcustomerid").toString();
//				int minstock=Integer.parseInt(info.get("fminstock").toString());
//				int orderamount=Integer.parseInt(info.get("forderamount").toString());
//				int balanceqty=0;
//				sql=" select sum(fbalanceqty) fbalanceqty from t_inv_storebalance where fproductid='"+productid+"' ";
//				List<HashMap<String, Object>> balancecls=this.QueryBySql(sql);
//				if (balancecls.size()>0)
//				{
//					if(balancecls.get(0).get("fbalanceqty")!=null)
//					{
//						balanceqty=Integer.parseInt(balancecls.get(0).get("fbalanceqty").toString());
//					}
//				}
//				sql=" select sum(famount-fstockinqty) fmakingqty from t_ord_saleorder where faudited=1 and fproductdefid='"+productid+"' ";
//				List<HashMap<String, Object>> makingqtycls=this.QueryBySql(sql);
//				if (makingqtycls.size()>0)
//				{
//					if(makingqtycls.get(0).get("fmakingqty")!=null)
//					{
//						balanceqty=balanceqty+Integer.parseInt(makingqtycls.get(0).get("fmakingqty").toString());
//					}
//				}
//				sql=" select sum(t_ord_delivers.famount) funsendqty from t_ord_delivers,t_sys_custrelationproduct where t_ord_delivers.fcusproductid=t_sys_custrelationproduct.fcustid and fouted=0 and t_sys_custrelationproduct.fproductdefid='"+productid+"' ";
//				List<HashMap<String, Object>> unsendqtycls=this.QueryBySql(sql);
//				if (unsendqtycls.size()>0)
//				{
//					if(unsendqtycls.get(0).get("funsendqty")!=null)
//					{
//						balanceqty=balanceqty-Integer.parseInt(makingqtycls.get(0).get("funsendqty").toString());
//					}
//				}
//				if(minstock>balanceqty)  //生成订单
//				{
//					String v=num+"";
//					number=startstr+"000000".substring(0,4-v.length())+num;
//					Saleorder soinfo = new Saleorder();
//					String orderEntryid = this.CreateUUid();
//					soinfo.setFid(orderEntryid);
//					soinfo.setForderid(this.CreateUUid());
//					soinfo.setFcreatorid(userid);
//					soinfo.setFcreatetime(new Date());
//					soinfo.setFnumber(number);
//					soinfo.setFproductdefid(productid);
//					soinfo.setFentryProductType(0);
//					soinfo.setFlastupdatetime(new Date());
//					soinfo.setFlastupdateuserid(userid);
//					soinfo.setFamount(orderamount);
//					soinfo.setFcustomerid(customerid);
//					//soinfo.setFcustproduct(fcusproductid);
//					soinfo.setFarrivetime(arriveDate);
//					soinfo.setFbizdate(new Date());
//					soinfo.setFaudited(0);
//					soinfo.setFauditorid(null);
//					soinfo.setFaudittime(null);
//					soinfo.setFamountrate(0);
//					soinfo.setFordertype(3);
//					soinfo.setFseq(1);
//					soinfo.setFimportEas(0);
//				    this.saveOrUpdate(soinfo);
//				    sql=" update t_pdt_vmiproductparam set fdescription='下单正常，库存数量为"+balanceqty+"' where fid='"+info.get("fid").toString()+"' ";
//				    this.ExecBySql(sql);
//				    num++;
//				}
//				else
//				{
//					sql=" update t_pdt_vmiproductparam set fdescription='库存大于最小值，不用下单' where fid='"+info.get("fid").toString()+"' ";
//					 this.ExecBySql(sql);
//				}
//				
//				
//			}
//			
//		}
		
		return null;
	}

	@Override
	public void ExecGetVmiProductParam(String productid,String ftype,String supplierid){
		String sql = "select FPRODUCTID from t_pdt_productdefproducts where FPARENTID='"+productid+"'";
		List<HashMap<String, Object>> list = this.QueryBySql(sql);
		Map map;
		if(list.size()>0){//根据套装查子件
			for(int i=0;i<list.size();i++){
				map = list.get(i);
				this.ExecCompareType(ftype, map.get("FPRODUCTID").toString(), supplierid ,"parent");
			}
			
		}else{//根据子件找套装
			sql = "select FPARENTID from t_pdt_productdefproducts where FPRODUCTID='"+productid+"'";
			list = this.QueryBySql(sql);
			for(int i=0;i<list.size();i++){
				map = list.get(i);
				this.ExecCompareType(ftype, map.get("FPARENTID").toString(), supplierid ,"product");
			}
		}

		
	}
	
/**
 * 查询该产品对应的套装或子件的安全库存，并比较类型
 * @param ftype 该产品类型
 * @param id 查询安全库存表的产品ID
 * @param suppireid 制造商ID
 * @param type 判断是套装还是子件的
 */
	private void ExecCompareType(String ftype,String id,String suppireid,String type){
		String sql ="select ftype from t_pdt_vmiproductparam where fproductid ='"+id+"' and fsupplierid = '"+suppireid+"'";
		List<HashMap<String,Object>> list = this.QueryBySql(sql);
		
			 if(list.size()>0){
				 String ordertype = list.get(0).get("ftype").toString();
//				 if(!ftype.equals(ordertype)){
//					 throw new DJException("该产品对应的套装或子件类型不一致！");
//				 }
				 if(ftype.equals("1")&&type.equals("product")){//子件为订单的类型，他的套装也必须是订单类型
					 if(!ftype.equals(ordertype)){
						 throw new DJException("子件为订单的类型，他的套装也必须是订单类型！");
					 } 
				 }
				 if(ftype.equals("0")&&type.equals("parent")){//套装为通知的类型，他的子件也必须是通知类型！
					 if(!ftype.equals(ordertype)){
						 throw new DJException("套装为通知的类型，他的子件也必须是通知类型！");
					 } 
				 }
			 }
			 
			 if(ftype.equals("0")&&type.equals("parent")){
				 sql = "select 1 from t_inv_storebalance where fbalanceqty>0 and ftype=1 and fsupplierid='%s' and fproductid='%s'";
				 sql = String.format(sql, suppireid,id);
				 list = this.QueryBySql(sql);
				 if(list.size()>0){
					 throw new DJException("该产品类型为通知,他的子件有库存,不能保存！");
				 }
				 
			 }
		 
	}


}
