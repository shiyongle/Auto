package com.dao.system;

import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.IBaseDaoImpl;
import com.model.custproduct.TBdCustproduct;
import com.model.system.Simplemessage;
import com.model.system.Simplemessagecfg;
import com.util.JSONUtil;

@Repository("simplemessageDao")
public class SimplemessageDao extends IBaseDaoImpl<Simplemessage, java.lang.Integer> implements ISimplemessageDao {

	
	

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
}
