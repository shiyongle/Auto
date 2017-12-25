package Com.Dao.System;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Productdef;
import Com.Entity.order.Productdemandfile;

public interface IExcelDataDao extends IBaseDao {

	void saveExcelData(HttpServletRequest request);

	void DelExcelDataInfo(String fid);

	int saveImportExcel(HttpServletRequest request) throws IOException;

	String saveCustExcel(HttpServletRequest request) throws IOException;
	
	Productdef saveProductForExcel(Custproduct custproduct,HttpServletRequest request);

	String SaveJxlExcel(InputStream file, HttpServletRequest request);

	String saveCustExcel(InputStream in, HttpServletRequest request)
			throws IOException;

	List<Productdemandfile> saveUploadFile(HttpServletRequest request);
}
