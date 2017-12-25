package com.pc.appInterface.appindexUpload;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.clUpload.IuploadDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.model.Cl_Upload;
import com.pc.model.Util_Option;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
@Controller
public class AppIndexUpController extends BaseController {
	@Resource
	private IuploadDao uploadDao;
	@Resource
	private IUtilOptionDao iUtilOptionDao;
	/***查询轮播图*/
	@RequestMapping("/app/appIndexImg/indexImgLoad")
	public String indexImgLoad(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String, Object> map=new HashMap<>(); 
		List<Cl_Upload> uploads=uploadDao.indexappByType(1);
		map.put("success", "true");
		map.put("data",uploads);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/***查询版本号*/
	@RequestMapping("/app/version/findVersion")
	public String findVersion(HttpServletRequest request,HttpServletResponse response,Integer ftype,String versionCode) throws IOException{
		HashMap<String, Object> map=new HashMap<>(); 
		/*String version = "";
        List<Util_Option>  list = iUtilOptionDao.getVersion(ftype);
        for (Util_Option util : list) {
			if(new BigDecimal(util.getOptionName()).compareTo(versionCode) > 0){
				version = util.getOptionName();
			}
		}*/
		
        Util_Option option = iUtilOptionDao.getVersion2(ftype);
//		Util_Option optionCurrent = iUtilOptionDao.getVersion(versionCode,ftype);
//		option.setOptionFc(optionCurrent.getOptionFc());
//        if(version.length() != 0){
//        	option.setOptionFc(1);
//        }
		if (CacheUtilByCC.isOldVersion(versionCode, ftype)) {
			option.setOptionFc(1);
		}
		map.put("success", "true");
		map.put("data",option);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
}