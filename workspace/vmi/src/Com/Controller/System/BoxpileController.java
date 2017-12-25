package Com.Controller.System;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.IBoxpileDao;
import Com.Entity.System.Boxpile;

@Controller
public class BoxpileController {
	@Resource
	private IBoxpileDao boxpileDao;
	@RequestMapping("getBoxpileList")
	public void getBoxpileList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sql = "select * from t_ord_boxpile";
		try {
			ListResult list = boxpileDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "", ""));
		}
	}
	@RequestMapping("saveOrupdateBoxpile")
	public void saveOrupdateBoxpile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Boxpile b = new Boxpile();//(Boxpile)request.getAttribute("Boxpile");
		String fid = request.getParameter("fid");
		String fname = request.getParameter("fname");
		String fnumber = request.getParameter("fnumber");
		try {
			if(fid==null||"".equals(fid)){
				b.setFid(boxpileDao.CreateUUid());
			}else{
				b.setFid(fid);
			}
			b.setFname(fname);
			b.setFnumber(fnumber);
			boxpileDao.saveOrUpdate(b);
			response.getWriter().write(JsonUtil.result(true, "保存成功", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	
	@RequestMapping("delBoxpile")
	public void delBoxpile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fids = request.getParameter("fidcls");
		fids ="('"+fids.replaceAll(",", "','")+"')";
		try {
			String sql = "delete from t_ord_boxpile where fid in "+fids;
			boxpileDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "删除成功", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	
	@RequestMapping("getBoxpileInfo")
	public void getBoxpileInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		try {
			String sql = "select *  from t_ord_boxpile where fid = '"+fid+"'";
			List list = boxpileDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
}
