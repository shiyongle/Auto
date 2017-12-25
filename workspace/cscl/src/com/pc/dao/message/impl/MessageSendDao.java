package com.pc.dao.message.impl;

import java.util.HashMap;
import org.springframework.stereotype.Service;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.message.IMessageSendDao;
import com.pc.model.CL_MessageSend;

@Service("messageSendDao")
public class MessageSendDao extends BaseDao<CL_MessageSend, java.lang.Integer> implements IMessageSendDao {

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_MessageSend";
	}

	@Override
	public int deleteByMsgId(Integer[] ids) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ids", ids);
		return this.getSqlSession().delete(getIbatisSqlMapNamespace()+".deletepl",map);
	}

	@Override
	public int insertByuserrole(int fmessageid) {
		return this.getSqlSession().insert(getIbatisSqlMapNamespace()+".insertByuserrole",fmessageid);
	}

	@Override
	public int updatePart(int fid) {
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updatePart",fid);
	}
	
//	@Override
//	public CL_MessageSend getByMsgId(int fmessageid) {
//		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByMsgId",fmessageid);
//	}
	
}
