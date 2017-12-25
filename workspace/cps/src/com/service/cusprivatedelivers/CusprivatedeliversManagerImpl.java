/*
 * CPS-VMI-wangc
 */

package com.service.cusprivatedelivers;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.address.AddressDao;
import com.dao.cusprivatedelivers.CusprivatedeliversDao;
import com.dao.custproduct.TBdCustproductDao;
import com.model.PageModel;
import com.model.address.Address;
import com.model.cusprivatedelivers.CusPrivateDelivers;
import com.model.custproduct.TBdCustproduct;
import com.model.deliverapply.Deliverapply;
import com.model.mystock.Mystock;
import com.service.IBaseManagerImpl;
import com.util.Params;

@Service("cusprivatedeliversManager")
@Transactional(rollbackFor = Exception.class)
public class CusprivatedeliversManagerImpl extends IBaseManagerImpl<CusPrivateDelivers,java.lang.String> implements CusprivatedeliversManager{

	@Autowired
	private CusprivatedeliversDao cusprivatedeliversDao;
	
	@Autowired
	private AddressDao addressDao;

	@Autowired
	private TBdCustproductDao custproductDao;

	public void setTordCusprivatedeliversDao(CusprivatedeliversDao dao) {
		this.cusprivatedeliversDao = dao;
	}
	
	public IBaseDao<CusPrivateDelivers, java.lang.String> getEntityDao() {
		return this.cusprivatedeliversDao;
	}

	@Override
	public PageModel<CusPrivateDelivers> findBySql(String where,
			Object[] queryParams, Map<String, String> orderby, int pageNo,
			int maxResult) {
		// TODO Auto-generated method stub
		return this.cusprivatedeliversDao.findBySql(where, queryParams, orderby, pageNo, maxResult);
	}

	@Override
	public void deleteImpl(String[] fid) {
		for(String id:fid){
			this.cusprivatedeliversDao.delete("t_ord_cusprivatedelivers", id);
		}
	}
	
	@Override
	public void updateImpl(List<CusPrivateDelivers> list) throws Exception {
		String deliverapplysql="update t_ord_cusprivatedelivers set famount=:famount,farrivetime=:farrivetime where fid=:fid";
		String mystocksql="update t_ord_cusprivatedelivers set famount=:famount,farrivetime=:farrivetime,freqdate=:freqdate where fid=:fid";
		String time="00";
		Params param=null;
		for(CusPrivateDelivers p:list){
			param=new Params();
			param.put("famount", p.getFamount());
			time=p.getFarrivezone()==null?time:("上午".equals(p.getFarrivezone())?"09":"14");
			param.put("farrivetime",p.getFarriveString()+" "+time);
			param.put("fid", p.getFid());
			if("".equals(p.getFconsumetime())){
				this.cusprivatedeliversDao.ExecBySql(deliverapplysql,param);
			}
			else
			{
				param.put("freqdate",p.getFconsumetime());
				this.cusprivatedeliversDao.ExecBySql(mystocksql,param);
			}
		}
	}

	public String createDeliverapply(String[] fid,String userid){
//		String sql = "select ifnull(fisstock,0) fisstock from t_pdt_productreqallocationrules where fsupplierid='%s' and fcustomerid='%s' order by fisstock desc";
		String sql = "select ifnull(fisstock,0) fisstock from t_pdt_productreqallocationrules where fsupplierid=:fsid and fcustomerid=:fcid order by fisstock desc";
		CusPrivateDelivers c=null;
		Deliverapply d=null;
		Address adress=null;
		TBdCustproduct cp=null;
		Mystock stock=null;
		List<HashMap<String,Object>> list=null;
		HashMap<String,Integer> configmap=new HashMap<String,Integer>();
		for(String id:fid)
		{
			c=this.get(id);
			if(c.getFamount()==null||c.getFamount()==0){
				continue;
			}
			cp=this.custproductDao.get(c.getFcusproduct());
			if(c.getFtype()==0){//要货
			adress=this.addressDao.get(c.getFaddress());
			if(adress == null){
				return "未选择地址！";
			}
			d=new Deliverapply();
			d.setFmaterialfid(cp.getFproductid());
			  if("39gW7X9mRcWoSwsNJhU12TfGffw=".equals(c.getFsupplierid()))//新版客户平台下单。客户为同一个
			  {
				  Params params=new Params();
				  if(!configmap.containsKey(c.getFsupplierid()+c.getFcustomerid()))
				  {
					  params.put("fsid", c.getFsupplierid());
					  params.put("fcid", c.getFcustomerid());
					 list = this.QueryBySql(sql, params);
					 configmap.put(c.getFsupplierid()+c.getFcustomerid(),list.size()>0?Integer.parseInt(list.get(0).get("fisstock").toString()):0);
				  }  
				  if(configmap.get(c.getFsupplierid()+c.getFcustomerid())==0){	
					  d.setFmaterialfid(null);
				  }
			  }
			d.setFid(this.CreateUUid());
			d.setFnumber(this.getNumber("t_ord_deliverapply", "N", 4, true));
			d.setFcreatorid(userid);
			d.setFcreatetime(new Date());
			d.setFordernumber(c.getFnumber());
			d.setFcustomerid(c.getFcustomerid());
			d.setFcusproductid(c.getFcusproduct());
			d.setFsupplierid(c.getFsupplierid());
			d.setFdescription(c.getFdescription());
			d.setFaddressid(c.getFaddress());
			d.setFaddress(adress.getFdetailaddress());
			d.setFlinkman(adress.getFlinkman());
			d.setFlinkphone(adress.getFphone());
			d.setFarrivetime(c.getFarrivetime());
			d.setFupdatetime(new Date());
			d.setFupdateuserid(userid);
			d.setFboxtype(0);
			d.setFamount(c.getFamount());
			d.setFtype("0");
			d.setFiscreate(0);
			d.setFstate(0);
			this.save(d);
			cp.setFlastorderfamount(c.getFamount());
			cp.setFlastordertime(new Date());
			this.save(cp);
			}
			else//备货
			{
				stock=new Mystock();
				stock.setFid(this.CreateUUid());
				stock.setFnumber(this.getNumber("mystock","B", 4,true));
				stock.setFplanamount(c.getFamount());
				stock.setFfinishtime(c.getFarrivetime());
				stock.setFconsumetime(c.getFreqdate());
				stock.setFaveragefamount(0);
				stock.setFcreateid(userid);
				stock.setFcreatetime(new Date());
				stock.setFstate(0);
				stock.setFcustomerid(c.getFcustomerid());
				stock.setFcustproductid(c.getFcusproduct());
				stock.setFsupplierid(c.getFsupplierid());
				stock.setFpcmordernumber(c.getFnumber());
				stock.setFunit(cp.getFunit());
				stock.setFremark(c.getFdescription());
				this.save(stock);
			}
			this.delete(c);
		}
		return null;
	}
	
	@Override
	public void saveCusprivatedelivers(String fids,CusPrivateDelivers cusPrivateDelivers,String userid,String fcustomerid){
		for(String fcustproductid : fids.split(",")){
			CusPrivateDelivers c = new CusPrivateDelivers();
			c.setFid(this.CreateUUid());
			c.setFcusproduct(fcustproductid);
			c.setFaddress(cusPrivateDelivers.getFaddress());
			c.setFamount(cusPrivateDelivers.getFamount());
			c.setFarrivetime(cusPrivateDelivers.getFarrivetime());
			c.setFdescription(cusPrivateDelivers.getFdescription());
			c.setFnumber(cusPrivateDelivers.getFnumber());
			c.setFsupplierid(cusPrivateDelivers.getFsupplierid());
			c.setFreqdate(cusPrivateDelivers.getFreqdate());
			c.setFtype(cusPrivateDelivers.getFtype());
			c.setFcreatetime(new Date());
			c.setFcreatorid(userid);
			c.setFcustomerid(fcustomerid);
			c.setFordertype(0);
			this.save(c);
		}
	}
}
