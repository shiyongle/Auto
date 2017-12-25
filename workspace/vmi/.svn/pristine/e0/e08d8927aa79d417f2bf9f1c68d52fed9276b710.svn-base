package Com.Controller.order;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Controller.System.RoleController;
import Com.Dao.order.ICusDeliversDao;
import Com.Entity.order.Cusdelivers;

@Controller
public class CusDeliversController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private ICusDeliversDao CusDeliversDao;
	@RequestMapping("/ExportZTcusdelivers")
	public String ExportZTcusdelivers(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			CusDeliversDao.ExecExportZTcusdelivers();
			reponse.getWriter().write(JsonUtil.result(true, "OK","",""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping("/savegenerateDeliversarrivetime")
	public String savegenerateDeliversarrivetime(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH");
			String[] fidcls=request.getParameter("fidcls").split(",");
			Date arrivetime=f.parse(request.getParameter("arrivetime").replace("T",
					" "));
			String sql=" select count(*) counts from t_ord_cusdelivers where date(freqdate)<> '"+request.getParameter("arrivetime").substring(0,10)+"' and fid in('"+request.getParameter("fidcls").replaceAll(",","','")+"')";
			List<HashMap<String, Object>>  results=CusDeliversDao.QueryBySql(sql);
			if(results.size()>0)
			{
				if(((BigInteger)results.get(0).get("counts")).intValue()>0){
					throw new DJException("要修改的交期与发放交期日期要一致!");
				}
			}
			
			Cusdelivers info;
			for(int i=0;i<fidcls.length;i++)
			{
				info=CusDeliversDao.Query(fidcls[i]);
				info.setFarrivetime(arrivetime);
				CusDeliversDao.saveOrUpdate(info);
			}
			reponse.getWriter().write(JsonUtil.result(true, "OK","",""));
			//CusDeliversDao.ExecImportZTcusdelivers();
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping("/ImportZTcusdelivers")
	public String ImportZTcusdelivers(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			CusDeliversDao.ExecImportZTcusdelivers();
			CusDeliversDao.ExecImportcusdelivers();
			reponse.getWriter().write(JsonUtil.result(true, "OK","",""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}


}
