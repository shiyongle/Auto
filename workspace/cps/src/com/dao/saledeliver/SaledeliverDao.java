package com.dao.saledeliver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.saledeliver.FTUSaledeliver;
import com.model.saledeliver.FtuParameter;
import com.model.saledeliverdetail.FTUSaledeliverEntry;


/*
 * CPS-VMI-wangc
 */
public interface SaledeliverDao extends IBaseDao<FTUSaledeliver, java.lang.String>{
	
	public PageModel<FTUSaledeliver> findBySql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult);

	public void updateSyscfg(String fuserid, String fkey, String fvalue);
	public void saveOrupdateFtuParameters(List list,FtuParameter fp,String fuserid);


	void saveAllFtuParams(String userid, List<FtuParameter> fp);


	void savePrintTemplate(String fuserid, String html, File file) throws IOException;


	void resetPrintTemplate(String fuserid, File file) throws IOException;

	void saveFTUprint(String fid);

	void saveNewFTUSaleDeliver(String fsupplierid, FTUSaledeliver fs,
			List<FTUSaledeliverEntry> fse, String userid) throws Exception;

	void updateFTUstate(String fnumber, String userid);
	public PageModel<HashMap<String, Object>> findBySqlWithRpt(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult);

	public List getSupplieridByUserid(String userid);
	
	public List getFTUstatements( String where, Object[] queryParams,  Map<String, String> orderby);
	public List<FTUSaledeliver> getFtulist( String where, Object[] queryParams,  Map<String, String> orderby);

}
