package Com.Dao.System;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.JsonUtil;
import Com.Entity.System.Simplemessagecfg;

@Service("simplemessagecfgDao")
public class SimplemessagecfgDao extends BaseDao implements ISimplemessagecfgDao {

	@Override
	public HashMap<String, Object> ExecSave(Simplemessagecfg simplemessagecfg) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (simplemessagecfg.getFid().isEmpty()) {
			simplemessagecfg.setFid(this.CreateUUid());
		}
		this.saveOrUpdate(simplemessagecfg);

		params.put("success", true);
		return params;
	}

	@Override
	public Simplemessagecfg Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Simplemessagecfg.class, fid);
	}

	@Override
	public String ExecSaveSimplemessagecfg(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		
		
		return null;
	}

	@Override
	public String ExecSaveSimplemessagecfgs(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String recordsS = request.getParameter("records");
		
		JSONArray simplemessagecfgs = JSONArray.fromObject(recordsS);
		
		for (int i = 0; i < simplemessagecfgs.size(); i++) {
			
			JSONObject simplemessagecfgJson = simplemessagecfgs.getJSONObject(i);
			
			saveSimplemessagecfg(simplemessagecfgJson);
			
		}

		return JsonUtil.result(true, "", "", "");
	}

	private void saveSimplemessagecfg(JSONObject simplemessagecfgJson) {
		// TODO Auto-generated method stub
		
		Simplemessagecfg simplemessagecfg = new Simplemessagecfg(simplemessagecfgJson.getString("fid"));
		
		simplemessagecfg.setFneedsms(simplemessagecfgJson.getBoolean("fneedsms")?1:0);
		simplemessagecfg.setFtype(simplemessagecfgJson.getInt("ftype"));
		simplemessagecfg.setFrecipient(simplemessagecfgJson.getString("frecipient"));
		simplemessagecfg.setFphone(simplemessagecfgJson.getString("fphone"));
		simplemessagecfg.setFuserid(simplemessagecfgJson.getString("fuserid"));
		
		ExecSave(simplemessagecfg);
		
	}
}
