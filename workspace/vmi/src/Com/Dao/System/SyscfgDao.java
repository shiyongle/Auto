package Com.Dao.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Entity.System.Syscfg;
import Com.Entity.System.Useronline;

@Service
public class SyscfgDao extends BaseDao implements ISyscfgDao {
	@Autowired
	private IBaseSysDao baseSysDao;
	
	//key,keyname,defaultvalue
	public static String[][] USER_CFG_CFG = new String[][] {
			{ "safeWidget", "启用安全控件", "0" }, { "orderWay", "下单方式", "multi" },
			{ "autoDepart", "自动发车", "1" },
			/**
			 * 0是为选择，2是二联，3是三联
			 */
			{ "thePrintTP", "打印模板模式", "0" } ,
			{"affirmSchemedesign","确认方案","0"}};

	public static Map<String, String> USER_CFG_CFG_MAP = new HashMap<String, String>();
	
	static{
		
		initCfgMap();
		
	}
	
	public static void initCfgMap() {
		
		for (String[] USER_CFG_CFGi : USER_CFG_CFG) {
			
			USER_CFG_CFG_MAP.put(USER_CFG_CFGi[0], USER_CFG_CFGi[2]);
			
		}
		
	}
	
	@Override
	public HashMap<String, Object> ExecSave(Syscfg syscfg) {
		// TODO Auto-generated method stub

		HashMap<String, Object> params = new HashMap<>();

		if (syscfg.getFid().isEmpty()) {

			syscfg.setFid(this.CreateUUid());

		}

		this.saveOrUpdate(syscfg);

		params.put("success", true);
		return params;
	}

	@Override
	public Syscfg Query(String fid) {
		// TODO Auto-generated method stub

		return (Syscfg) this.getHibernateTemplate().get(Syscfg.class, fid);
	}

	@Override
	public void ExecSaveSyscfgs(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String userid = ((Useronline) req.getSession().getAttribute(
				"Useronline")).getFuserid();
		Syscfg syscfg =(Syscfg) req.getAttribute("Syscfg");
		
		syscfg.setFuser(userid);
		
		ExecBySql("delete from t_sys_syscfg where fuser='" + userid
				+ "' and fkey='" + syscfg.getFkey() + "'");
		if("affirmSchemedesign".equals(syscfg.getFkey())){
			String customerid = baseSysDao.getCurrentCustomerid(userid);
			String sql = "update t_bd_customer set fschemedesign='"+syscfg.getFvalue()+"' where fid ='"+customerid+"'";
			this.ExecBySql(sql);
		}else if("thePrintTP".equals(syscfg.getFkey())){//送货凭证改变模板时，删除所有配置
			String sql = "delete from t_ftu_parameter where fuserid ='"+userid+"'";
			this.ExecBySql(sql);
			sql = "delete from t_ftu_template where fuserid ='"+userid+"'";
			this.ExecBySql(sql);
		}
		
		ExecSave(syscfg);

	}
	
	@Override
	public void ExecSaveSyscfgsBysKeyAndValue(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		
		List<Syscfg> oldSyscfgs = QueryByHql(String.format("from Syscfg where fkey = '%s' and fuser = '%s' ", key, userid));
		
		if (!oldSyscfgs.isEmpty()) {
			
			Syscfg sT = oldSyscfgs.get(0);
			
			sT.setFvalue(value);
			
			ExecSave(sT);
			
		} else {
			
			Syscfg newSysCfg = new Syscfg("", key, value, userid);
			
			ExecSave(newSysCfg);
			
			
		}

		
	}
	
	
	@Override
	public ListResult ExecBuildReponseJson(HttpServletRequest req) {
		// TODO Auto-generated method stub

		String userid = ((Useronline) req.getSession().getAttribute(
				"Useronline")).getFuserid();

		String hql = " from Syscfg where fuser = '%s' ";

		hql = String.format(hql, userid);

		List<Syscfg> sysCfgs = QueryByHql(hql);

//		filterSyscfgByCfg(sysCfgs);

		return buildCfgJson(sysCfgs, userid);
	}

	private ListResult buildCfgJson(List<Syscfg> sysCfgs, String userid) {
		// TODO Auto-generated method stub

		ListResult lr = new ListResult();
		List<HashMap<String, Object>> cfgs = new ArrayList<>();

		// 遍历
		for (String[] userCfg : USER_CFG_CFG) {

			boolean needToSave = true;

			/**
			 * 后面的目标值
			 */
			HashMap<String, Object> mapT = new HashMap<>();
			mapT.put("fkey", userCfg[0]);
			mapT.put("fkeyName", userCfg[1]);
			mapT.put("fvalue", userCfg[2]);

			for (Syscfg info : sysCfgs) {
				/**
				 * 如果数据库中有值
				 */
				if (userCfg[0].equals(info.getFkey())) {
					mapT.put("fid", info.getFid());
					mapT.put("fvalue", info.getFvalue());

					needToSave = false;
					break;

				}
			}

			// 如果在数据库中没值，则添加一个默认值
			if (needToSave) {

				Syscfg syscfgT = new Syscfg();
				syscfgT.setFuser(userid);
				syscfgT.setFkey((String) (mapT.get("fkey")));
				syscfgT.setFvalue((String) (mapT.get("fvalue")));
				syscfgT.setFid("");

				ExecSave(syscfgT);

			}

			cfgs.add(mapT);
		}
		lr.setData(cfgs);
		lr.setTotal(cfgs.size() + "");
		return lr;
	}

	@Override
	public ListResult ExecGainOrderWayCfg(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		return ExecGainCfgByFkey(request, "orderWay");
		
//		String hql = " select fvalue from Syscfg where fuser = '%s' and fkey = 'orderWay' ";
//		
//		List<String> values = QueryByHql(String.format(hql, MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(request)));
//		
//		ListResult lr = new ListResult();
//		
//		List<HashMap<String, Object>> listT = new ArrayList<HashMap<String,Object>>();
//		
//		HashMap<String, Object> mapT = new HashMap<>();
//		
//		if (values.size() == 0) {
//
//			mapT.put("fvalue", "single");
//		} else {
//
//			mapT.put("fvalue", values.get(0));
//		}
//			
//		
//		listT.add(mapT);
//		
//		lr.setData(listT);
//		
//		return lr;
	}
	
	@Override
	public ListResult ExecGainCfgByFkey(HttpServletRequest request, String fkey) {
		// TODO Auto-generated method stub
		
		String hql = " select fvalue from Syscfg where fuser = '%s' and fkey = '%s' ";
		
		List<String> values = QueryByHql(String.format(hql, MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(request),fkey));
		
		ListResult lr = new ListResult();
		
		List<HashMap<String, Object>> listT = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String, Object> mapT = new HashMap<>();
		
		//如果数据库中没有值就取默认值
		if (values.size() == 0) {

			mapT.put("fvalue", USER_CFG_CFG_MAP.get(fkey));
		} else {

			mapT.put("fvalue", values.get(0));
		}
			
		
		listT.add(mapT);
		
		lr.setData(listT);
		
		return lr;
	}

	@Override
	public void saveBoardOrderCfg(String userId, boolean add) {
		String sql;
		if(add){
			sql = "select 1 from t_sys_syscfg where fkey='boardOrderCreate' and fuser='"+userId+"'";
			if(!this.QueryExistsBySql(sql)){
				Syscfg syscfg = new Syscfg();
				syscfg.setFid(this.CreateUUid());
				syscfg.setFkey("boardOrderCreate");
				syscfg.setFvalue("1");
				syscfg.setFuser(userId);
				this.saveOrUpdate(syscfg);
			}
		}else{
			sql = "delete from t_sys_syscfg where fkey='boardOrderCreate' and fuser='"+userId+"'";
			this.ExecBySql(sql);
		}
	}

	
	
}
