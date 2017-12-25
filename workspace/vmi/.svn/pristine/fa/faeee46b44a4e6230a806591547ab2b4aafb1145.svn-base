package Com.Dao.System;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Controller.System.NewsController;
import Com.Controller.System.ProductdefController;
import Com.Entity.System.Button;
import Com.Entity.System.Mainmenuitem;
import Com.Entity.System.News;
import Com.Entity.System.Userpermission;

@Service
public class NewsDao extends BaseDao implements INewsDao {

	public static final String NEWS_IMG_RELATIVEPATH = "news/img";

	@Override
	public News Query(String fid) {
		return (News) this.getHibernateTemplate().get(News.class, fid);
	}

	@Override
	public void ExecSave(News news) throws DJException {
		// TODO Auto-generated method stub
		this.saveOrUpdate(news);
	}

	@Override
	public ListResult ExecGainIndexImgs(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String sql = NewsController.BASE_SQL_PRE + " where tsn.fimgid != -1 " + String.format(NewsController.BASE_SQL_BEHIND, 8) ;
		
		List<HashMap<String, Object>> result = QueryBySql(sql);
		
		for (HashMap<String, Object> item : result) {
		
			String url = "javascript:parent.DJ.myComponent.img.jqueryImgCarousel.JqueryImgCarouselContainer.showContentView(%d,\'%s\');";
			
			
			
			String text = (String) item.get("ftitle");
			
			if (((String) item.get("fimgid")).equals("-1")) {

				item.put("url", request.getServletContext().getContextPath() + "/images/defaultNewsBg.jpg");
			} else {

				String href = MySimpleToolsZ
						.getMySimpleToolsZ()
						.getRelativePathWithFileName(
								request.getServletContext().getContextPath() + "/"
										+ NEWS_IMG_RELATIVEPATH + "/",
								(String) item.get("fpath"));
				item.put("url", href);
				
				url = String.format(url,item.get("fid"),href);
			}
			
			item.put("text", text);
			item.put("href",url);
				
		}
		
		ListResult listResult = new ListResult();
		
		listResult.setData(result);
		
		return listResult;
	}

	@Override
	public void ExecSaveVmiNew(HttpServletRequest request) throws FileUploadException, IOException {
		// TODO Auto-generated method stub
		
		String relativePath = NEWS_IMG_RELATIVEPATH;
		
		//save accessory accessoryId
		Map<String, String> params = MySimpleToolsZ.getMySimpleToolsZ().saveFileAndGainParamsMap(request, "news", relativePath, this);
		
		
		String user = MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(request);
		
		News news = new News();
		
		news.setFtitle(params.get("ftitle"));
		news.setFcontent(params.get("fcontent"));
		news.setFcreaterid(user);
		news.setFimgid(params.get(MySimpleToolsZ.ACCESSORY_ID));
		
		ExecSave(news);
		
	}

}
