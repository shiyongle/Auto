/*
 * CPS-VMI-wangc
 */

package com.service.saledeliver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.saledeliver.SaledeliverDao;
import com.model.PageModel;
import com.model.saledeliver.FTUSaledeliver;
import com.model.saledeliver.FtuParameter;
import com.model.saledeliverdetail.FTUSaledeliverEntry;
import com.model.system.Syscfg;
import com.service.IBaseManagerImpl;
import com.util.Params;
import com.util.StringUitl;

@Service("saledeliverManager")
@Transactional(rollbackFor = Exception.class)
public class SaledeliverManagerImpl extends IBaseManagerImpl<FTUSaledeliver,java.lang.String> implements SaledeliverManager{
	@Autowired
	private SaledeliverDao saledeliverDao;
	
	public IBaseDao<FTUSaledeliver, java.lang.String> getEntityDao() {
		return this.saledeliverDao;
	}

	@Override
	public PageModel<FTUSaledeliver> findBySql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult) {
		return this.saledeliverDao.findBySql(where, queryParams, orderby, pageNo, maxResult);
	}

	@Override
	public String getCfgByFkey(String fuserid, String fkey) {
		// TODO Auto-generated method stub
		try {
//			String hql = "from Syscfg where fuser = "+fuserid+" and fkey ="+fkey;
//			List<Syscfg> s = saledeliverDao.QueryByHql(hql);
			Object[] param=new Object[]{fuserid,fkey};
			String hql = "from Syscfg where fuser = ? and fkey =?";
			List<Syscfg> s = saledeliverDao.QueryByHql(hql,param);
			return s.get(0).getFvalue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	@Override
	public void updateSyscfg(String fuserid, String fkey,String fvalue){
		this.saledeliverDao.updateSyscfg(fuserid, fkey, fvalue);
	}

	@Override
	public void saveOrupdateFtuParameters(String fuserid, FtuParameter fp){
		// TODO Auto-generated method stub
		try {
			Params p = new Params();
			if(!StringUitl.isNullOrEmpty(fp)){
				String sql = "select fid from t_ftu_parameter where fuserid='"+fuserid+"' and falias='"+fp.getFname()+"'";
				List<HashMap<String,Object>> list = this.QueryBySql(sql,p);
				saledeliverDao.saveOrupdateFtuParameters(list, fp, fuserid);
			}
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
	}
	@Override
	public void saveAllFtuParams(String userid,List<FtuParameter> fp){
		saledeliverDao.saveAllFtuParams(userid, fp);
	}
	@Override
	public void savePrintTemplate(String fuserid,String html,File file) throws IOException{
		saledeliverDao.savePrintTemplate(fuserid, html, file);
	}
	@Override
	public void resetPrintTemplate(String fuserid,File file)throws IOException{
		saledeliverDao.resetPrintTemplate(fuserid, file);
	}
	@Override
	public void saveFTUprint(String fid){
		saledeliverDao.saveFTUprint(fid);
	}
	@Override
	public void saveNewFTUSaleDeliver(String fsupplierid,FTUSaledeliver fs,List<FTUSaledeliverEntry> fse,String userid) throws Exception{
		saledeliverDao.saveNewFTUSaleDeliver(fsupplierid, fs, fse, userid);
	}
	@Override
	public void updateFTUstate(String fid,String userid){
		saledeliverDao.updateFTUstate(fid, userid);
	}
	@Override
	public PageModel<HashMap<String, Object>> findBySqlWithRpt(String where,
			Object[] queryParams, Map<String, String> orderby, int pageNo,
			int maxResult) {
		// TODO Auto-generated method stub
		return saledeliverDao.findBySqlWithRpt(where, queryParams, orderby, pageNo, maxResult);
	}

	@Override//获取用户关联的制造商
	public String getFsupplieridByUser(String userid) {
		// TODO Auto-generated method stub
		List<HashMap<String,Object>> list=saledeliverDao.getSupplieridByUserid(userid);
		return list.size()>0 ? (String)list.get(0).get("fsupplierid") : null;
	}

	@Override
	public List getCustomerByfsupplier(String fsupplierid) {
		if(fsupplierid==null) return null;
		// TODO Auto-generated method stub
		Params p =new Params();
		String sql = "select c.fid,c.fname from t_bd_customer c left join t_pdt_productreqallocationrules p on c.fid=p.fcustomerid where p.fsupplierid=:fsupplierid";
		p.put("fsupplierid", fsupplierid);
		@SuppressWarnings("unchecked")
		List<HashMap<String,Object>>  list =this.QueryBySql(sql, p);
		return list;
			   
	}

	@Override
	public List getFTUstatements(String where, Object[] queryParams,
			Map<String, String> orderby) {
		// TODO Auto-generated method stub
		return this.saledeliverDao.getFTUstatements(where, queryParams, orderby);
	}

	@Override
	public List<FTUSaledeliver> getFtulist(String where, Object[] queryParams,
			Map<String, String> orderby) {
		// TODO Auto-generated method stub
		return this.saledeliverDao.getFtulist(where, queryParams, orderby);
	}
}
