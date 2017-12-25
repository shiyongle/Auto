package com.dao.invite;

import java.util.Date;
import java.util.List;

import com.dao.IBaseDao;
import com.model.invite.Invite;

public interface InviteDao extends IBaseDao<Invite,java.lang.Integer> {
	
	public List<Invite> getInviteeList(int fapptype,Date starttime,Date endtime,int isReward,int fuserid);
	
	public List<Invite> getInviteeListById(String fid,int fapptype );
	
	public Invite getInviterIdByuserid(String fid,int fapptype);
}
