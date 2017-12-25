/*
 * CPS-VMI-wangc
 */

package com.service.saledeliver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.saledeliver.FTUSaledeliver;
import com.model.saledeliver.FtuParameter;
import com.model.saledeliverdetail.FTUSaledeliverEntry;
import com.service.IBaseManager;

public interface SaledeliverManager extends IBaseManager<FTUSaledeliver, java.lang.String>{
	
	public PageModel<FTUSaledeliver> findBySql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult);
	public String getCfgByFkey(String fuserid,String fkey);
	public void  updateSyscfg(String fuserid, String fkey, String fvalue);
	public void  saveOrupdateFtuParameters(String fuserid,FtuParameter fp);
	void saveAllFtuParams(String userid, List<FtuParameter> fp);
	void savePrintTemplate(String fuserid, String html, File file) throws IOException;
	void resetPrintTemplate(String fuserid, File file) throws IOException;
	void saveFTUprint(String fid);
	void saveNewFTUSaleDeliver(String fsupplierid,FTUSaledeliver fs,
			List<FTUSaledeliverEntry> fse, String userid) throws Exception;
	void updateFTUstate(String fid, String userid);
	public PageModel<HashMap<String,Object>> findBySqlWithRpt(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult);

	public String getFsupplieridByUser(String userid);
	public List getCustomerByfsupplier(String fsupplierid);
 	public List getFTUstatements( String where, Object[] queryParams,  Map<String, String> orderby);
	public List<FTUSaledeliver> getFtulist( String where, Object[] queryParams,  Map<String, String> orderby);

}
