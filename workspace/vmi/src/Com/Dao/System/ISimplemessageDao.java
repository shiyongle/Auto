package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Simplemessage;

public interface ISimplemessageDao extends IBaseDao {
	public HashMap<String, Object> ExecSave(Simplemessage simplemessage);

	public Simplemessage Query(String fid);

	/**
	 * 获取该接受者所有消息
	 *
	 * @param id
	 * @return
	 *
	 * @date 2014-2-11 上午10:28:53  (ZJZ)
	 */
	public List<Simplemessage> findSimplemessagesByRecipient(String id);
	
	String ExecSaveSimplemessage(HttpServletRequest request) throws CloneNotSupportedException;
	public void MessageProjectEvaluation(HashMap<String, String> map) ;
	public void MessageProjectEvaluationWithSender(HashMap<String, String> map) ;
	public void RequstReceiving();
}
