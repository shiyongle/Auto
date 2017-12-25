package com.pc.dao.message.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.message.ImessageDao;
import com.pc.model.CL_Message;


@Service("messageDao")
public class MessageDao extends BaseDao<CL_Message, java.lang.Integer> implements ImessageDao {

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Message";
	}

	@Override
	public int getMsgId() {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getMsgId");
	}
	
	@Override
	public int deleteById(Integer[] ids) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ids", ids);
		return this.getSqlSession().delete(getIbatisSqlMapNamespace()+".deletepl",map);
	}

	@Override
	public int updateById(CL_Message message) {
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updatePart",message);
	}
	
	@Override
	public int updateIsRead(int id,int receiver,int fisread,String phone){
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("receiver", receiver);
		map.put("fisread", fisread);
		map.put("phone", phone);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateIsRead",map);
	}
	
}
