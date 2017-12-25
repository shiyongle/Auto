package Com.Dao.System;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUploadException;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;
import Com.Entity.System.News;

public interface INewsDao extends IBaseDao {
	News Query(String fid);

	void ExecSave(News news) throws DJException;

	ListResult ExecGainIndexImgs(HttpServletRequest request);
	
	void ExecSaveVmiNew (HttpServletRequest request) throws UnsupportedEncodingException, FileUploadException, IOException;
}
