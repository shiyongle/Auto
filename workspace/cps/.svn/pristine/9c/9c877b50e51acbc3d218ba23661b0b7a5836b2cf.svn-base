package com.service.invite;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.invite.InviteDao;
import com.service.IBaseManagerImpl;
import com.model.invite.Invite;


@Service("inviteManager")
@Transactional
public  class InviteManagerImpl extends IBaseManagerImpl<Invite,java.lang.Integer> implements InviteManager  {
	
	@Autowired
	private InviteDao inviteDao;
	
	@Override
	protected IBaseDao<Invite, java.lang.Integer> getEntityDao() {
		return this.inviteDao;
	}
	
	/**
	 * 被邀请人注册，添加上下线关系
	 */
	@Override
	public void saveInvite(Invite invite) {
		this.inviteDao.save(invite);
	}

	/**
	 * 查询转介绍表
	 */
	@Override
	public List<Invite> getInviteeList(int fapptype,Date starttime,Date endtime,int isReward,int fuserid) {
		return this.inviteDao.getInviteeList(fapptype,starttime,endtime,isReward,fuserid);
	}
	
	/**
	 * 根据邀请人查询其所有下线
	 */
	@Override
	public List<Invite> getInviteeListById(String fid,int fapptype) {
		return this.inviteDao.getInviteeListById(fid,fapptype);
	}

	/**
	 * 根据用户id查询其上线，即邀请人
	 */
	@Override
	public Invite getInviterIdByuserid(String fid,int fapptype) {
		return this.inviteDao.getInviterIdByuserid(fid,fapptype);
	}

}
