package Com.Dao.System;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.System.Simplemessage;
import Com.Entity.System.Useronline;

@Service("simplemessageDao")
public class SimplemessageDao extends BaseDao implements ISimplemessageDao {

	@Override
	public HashMap<String, Object> ExecSave(Simplemessage simplemessage) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (simplemessage.getFid().isEmpty()) {
			simplemessage.setFid(this.CreateUUid());
		}
		this.saveOrUpdate(simplemessage);

		params.put("success", true);
		return params;
	}

	@Override
	public Simplemessage Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Simplemessage.class, fid);
	}

	@Override
	public List<Simplemessage> findSimplemessagesByRecipient(String id) {
		// TODO Auto-generated method stub

		return getHibernateTemplate().find(
				" from  Simplemessage s where s.frecipient = ?", id);

	}

	@Override
	public String ExecSaveSimplemessage(HttpServletRequest request) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		
		
		
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();

		Simplemessage simplemessage = (Simplemessage) request
				.getAttribute("Simplemessage");

		simplemessage.setFsender(userid);

		simplemessage.setFtime(new Date());

		saveSimplemessages(simplemessage,
				request.getParameter("Simplemessage"), userid);

		return "{success:true,msg:'保存成功!'}";
	}

	/**
	 * 保存具体方法
	 * 
	 * @param simplemessage
	 * @param parameter
	 * @param userid
	 * @throws CloneNotSupportedException
	 * 
	 *
	 */
	private void saveSimplemessages(Simplemessage simplemessage,
			String parameter, String userid) throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		// 进行一些必要的设置

		JSONObject jso = JSONObject.fromObject(parameter);

		JSONArray jsa = jso.getJSONArray("frecipient");

		// 用于去掉重复的id
		Set<String> setRecipientIds = new HashSet<>();

		for (int i = 0; i < jsa.size(); i++) {

			// 不发送给自己
			if (userid.equals(jsa.getString(i))) {

				continue;

			}

			setRecipientIds.add(jsa.getString(i));

		}

		
		//保存
		for (String id : setRecipientIds) {

			Simplemessage objT = (Simplemessage) simplemessage.clone();

			objT.setFrecipient(id);

			ExecSave(objT);
		}

	}
	@Override
	public void MessageProjectEvaluation(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		Simplemessage simplemessage = new Simplemessage();
		simplemessage.setFcontent(map.get("fcontent"));
		simplemessage.setFhaveReaded(0);
		simplemessage.setFrecipient(map.get("frecipientid"));
		simplemessage.setFtime(new Date());
		simplemessage.setFtype(new Integer(map.get("ftype")));
		simplemessage.setFsender("3c3c9f29-64b9-11e4-bdb9-00ff6b42e1e5");
		simplemessage.setFid(CreateUUid());
		this.saveOrUpdate(simplemessage);
	}
	/**
	 * 根据发送人发送消息，不是administrator发送
	 */
	@Override
	public void MessageProjectEvaluationWithSender(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		Simplemessage simplemessage = new Simplemessage();
		simplemessage.setFcontent(map.get("fcontent"));
		simplemessage.setFhaveReaded(0);
		simplemessage.setFrecipient(map.get("frecipientid"));
		simplemessage.setFtime(new Date());
		simplemessage.setFtype(new Integer(map.get("ftype")));
		simplemessage.setFsender(StringUtils.isEmpty(map.get("fsender"))?"3c3c9f29-64b9-11e4-bdb9-00ff6b42e1e5":map.get("fsender"));
		simplemessage.setFid(CreateUUid());
		this.saveOrUpdate(simplemessage);
	}
	
	@Override
	public void RequstReceiving(){
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date newdate = new Date();
		newdate.setMinutes(newdate.getMinutes()-15);
		String sql = "select ds.fapplayid,d.fnumber,d.fid,d.farrivetime,d.famount from t_ord_deliverorder d left join t_ord_delivers ds on ds.fid=d.fdeliversid where d.fouted = 1 and d.fouttime >= '"+f.format(newdate)+"' ";
		List<HashMap<String,Object>> data= QueryBySql(sql);
		String fcontent = "";
		for (int i = 0; i < data.size(); i++) {
			HashMap o = data.get(i);
			String[] fidcls = o.get("fapplayid").toString().split(",");
			String fapplayid = fidcls[0];
			sql = "select cp.fname cusproduct,dp.fcreatorid,u.fname creator from t_ord_deliverapply dp left join t_bd_custproduct cp on cp.fid=dp.fcusproductid left join t_sys_user u on u.fid=dp.fcreatorid where dp.fid = '"+fapplayid+"' ";
			HashMap<String, Object> deliverapplyinfo = (HashMap<String, Object>)QueryBySql(sql).get(0);
			String cusproduct = deliverapplyinfo.get("cusproduct").toString();
			fcontent = "尊敬的"+deliverapplyinfo.get("creator").toString()+"用户您以下产品已于"+o.get("farrivetime").toString()+"发出，请注意查收！  产品名称："+cusproduct+"，数量："+o.get("famount").toString()+"，交期："+o.get("farrivetime").toString();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("fcontent", fcontent);
			map.put("frecipientid", deliverapplyinfo.get("fcreatorid").toString());
			map.put("ftype", "1");
			MessageProjectEvaluation(map);
		}
			
	}
}
