package Com.Controller.System;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.ImageUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Dao.System.IBaseSysDao;
import Com.Entity.System.SysUser;
import Com.Entity.order.Productdemandfile;

@Controller
public class BaseSysController {

	Logger log = LoggerFactory.getLogger(BaseSysController.class);
	@Autowired
	private IBaseSysDao dao;
	/**
	 * 根据父id获取文件id串
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getFilesByParent")
	public String getFilesByParent(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		try {
			if(StringUtils.isEmpty(fid)){
				throw new DJException("ID为空！");
			}
//			String sql = "select group_concat(fid) fids from t_ord_productdemandfile where fparentid = '#"+fid+"' order by fcreatetime desc";
			String sql = "select group_concat(fid) fids from t_ord_productdemandfile where fparentid = '"+fid+"' order by fcreatetime desc";
			HashMap<String, String> map = dao.getMapData(sql);
			/*if(map.get("fids")==null){
				map = dao.getMapData(sql);
			}*/
			String fids = map.get("fids")!=null?map.get("fids"):"";
			response.getWriter().write(JsonUtil.result(true, fids,"",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "操作失败！", "",""));
		}
		
		return null;
	}
	
	/**
	 * 根据父id获取图片文件id串
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getImageFilesByParent")
	public String getImageFilesByParent(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		try {
			if(StringUtils.isEmpty(fid)){
				throw new DJException("ID为空！");
			}
			String hql = "from Productdemandfile where fparentid = '"+fid+"'";
			List<Productdemandfile> list = dao.QueryByHql(hql);
			list = extractImageList(list);
			StringBuilder fids = new StringBuilder("");
			for(Productdemandfile file: list){
				if(fids.length()!=0){
					fids.append(",");
				}
				fids.append(file.getFid());
			}
			response.getWriter().write(JsonUtil.result(true, fids.toString(),"",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "操作失败！", "",""));
		}
		
		return null;
	}
	
	/**
	 * 根据父id获取文件信息列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getFileListByParent")
	public String getFileListByParent(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String fparentid = request.getParameter("fparentid");
			if(StringUtils.isEmpty(fparentid)){
				throw new DJException("传参失败，未上传父ID");
			}
			String sql = "select fid,fname,ftype,fpath,fcreatetime from t_ord_productdemandfile where fparentid = :fparentid";
			params p = new params();
			p.put("fparentid", fparentid);
			List<HashMap<String, Object>> result = dao.QueryBySql(sql, p);
			response.getWriter().write(JsonUtil.result(true, "","",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "操作失败！", "",""));
		}
		return null;
	}
	/**
	 * 根据客户id获取要货申请导入的附件
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getFileListOfImportOrder")
	public String getFileListOfImportOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try{
			String fcustomerid = request.getParameter("fcustomerid");
			if(StringUtils.isEmpty(fcustomerid)){
				throw new DJException("未上传客户ID");
			}
			String sql = "select fid,fname,fpath,fcreatetime,ifnull(fcharacter,'') fcharacter from t_ord_productdemandfile where fparentid = :fparentid and ftype='订单导入' and fcreatetime>DATE_SUB(CURDATE(), INTERVAL 30 DAY) order by fcreatetime desc";
			params p = new params();
			p.put("fparentid", fcustomerid);
			List<HashMap<String, Object>> result = dao.QueryBySql(sql, p);
			response.getWriter().write(JsonUtil.result(true, "","",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "操作失败！", "",""));
		}
		return null;
	}
	/**
	 * 根据文件id下载文件
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("downLoadFile")
	public String downLoadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UploadFile.download(response, request.getParameter("fid"));
		return null;
	}
	/**
	 * 通过id获取文件流
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getFileSource")
	public String getFileSource(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		InputStream in = null;
		OutputStream out = null;
		try {
			Productdemandfile obj = (Productdemandfile) dao.Query(Productdemandfile.class, fid);
			if(obj==null){
				throw new DJException("图纸不存在！");
			}else if(!ImageUtil.isImage(obj.getFname())){
				throw new DJException("文件不是图片类型！");
			}
			in = new FileInputStream(UploadFile.getSmallImagePath(obj.getFpath(),true));
			out = response.getOutputStream();
			IOUtils.copy(in, out);
			in.close();
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	/**
	 * 通过父id获取文件流
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getFileSourceByParentId")
	public String getFileSourceByParentId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		InputStream in = null;
		OutputStream out = null;
		String path = null;
		try {
			Productdemandfile obj = null;
			String hql = "from Productdemandfile where fparentid = '"+fid+"'";
			List<Productdemandfile> list = dao.QueryByHql(hql);
			list = extractImageList(list);
			if(list.size()>0){
				obj = list.get(0);
				path = UploadFile.getSmallImagePath(obj.getFpath(),true);
			}else{
				path = new File(request.getServletContext().getRealPath("")).getParent() + "/vmifile/defaultpic.png";
				if(!new File(path).exists()){
					path = request.getServletContext().getRealPath("") + "/vmifile/defaultpic.png";
				}
			}
			in = new FileInputStream(path);
			out = response.getOutputStream();
			IOUtils.copy(in, out);
			in.close();
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	/**
	 * 提取是图片的文件列表
	 * @param list
	 * @return
	 */
	private List<Productdemandfile> extractImageList(List<Productdemandfile> list) {
		List<Productdemandfile> result = new ArrayList<>();
		for(Productdemandfile file: list){
			if(ImageUtil.isImage(file.getFname())){
				result.add(file);
			}
		}
		return result;
	}
	/**
	 * 通过父id获取文件流,用于手机端
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getAppFileSourceByParentId")
	public String getAppFileSourceByParentId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return getFileSourceByParentId(request,response);
	}
	/**
	 * 判断用户是否是"查看全部客户"
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/getUserFilter")
	public String getUserFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SysUser sysUser = dao.getCurrentUser(request);
		boolean isFilter = false;
		if(sysUser.getFisfilter()==1){
			isFilter = true;
		}
		response.getWriter().write(JsonUtil.result(isFilter, "", "",""));
		return null;
	}
	/**
	 * 获取用户联系方式
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/getUserContact")
	public String getUserContact(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SysUser sysUser = dao.getCurrentUser(request);
		String phone = sysUser.getFtel();
		if(StringUtils.isEmpty(phone)){
			phone = sysUser.getFphone();
		}
		if(!StringUtils.isEmpty(phone)){
			response.getWriter().write(JsonUtil.result(true, phone, "",""));
		}else{
			response.getWriter().write(JsonUtil.result(false, "", "",""));
		}
		return null;
	}
}
