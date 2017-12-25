package com.dao.system;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.dao.IBaseDao;
import com.model.custproduct.TBdCustproduct;
import com.model.system.Simplemessage;
import com.model.system.Simplemessagecfg;

public interface ISimplemessageDao extends IBaseDao<Simplemessage, java.lang.Integer> {
		public void MessageProjectEvaluationWithSender(HashMap<String, String> map);

}
