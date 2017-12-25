package Com.Controller.order;

import java.io.IOException;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.order.IAppraiseDao;
import Com.Entity.System.Useronline;
import Com.Entity.order.Appraise;

@Controller
public class AppraiseController {
	@Resource
	IAppraiseDao AppraiseDao;
	@RequestMapping("getAppraiseListByfid")
	public void getAppraiseListByfid(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String schemeDensignId = request.getParameter("schemeDensignId");
		try {
			String sql = "select a.fid,a.fbasicappraise,a.fdescription,u.fname fappraiseman,a.fappraisetime,a.fschemedesignid from t_ord_appraise a left join t_sys_user u on u.fid=a.fappraiseman where fschemedesignid='"+schemeDensignId+"'";
			ListResult result = AppraiseDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "",result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
	}
	@RequestMapping("saveOrUpdateAppraise")
	public void saveOrUpdateAppraise(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String fbasicappraise = request.getParameter("fbasicappraise");
		int basicappraise = 0;
		if(!StringUtils.isEmpty(fbasicappraise)){
			basicappraise = Integer.parseInt(request.getParameter("fbasicappraise"));
		}
		String fdescription = request.getParameter("fdescription");
		String fschemedesignid = request.getParameter("schemeDensignId");
		try {
			if(StringUtils.isEmpty(fbasicappraise)&&StringUtils.isEmpty(fdescription)){
				throw new DJException("基本评价和详细描述,必填其中之一！");
			}
			Appraise a = new Appraise();
			a.setFappraiseman(userid);
			a.setFappraisetime(new Date());
			a.setFbasicappraise(basicappraise);
			a.setFdescription(fdescription);
			a.setFid(AppraiseDao.CreateUUid());
			a.setFschemedesignid(fschemedesignid);
			AppraiseDao.saveAppraiseInfo(a);
			response.getWriter().write(JsonUtil.result(true,"保存成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
}
