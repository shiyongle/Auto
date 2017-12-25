package com.service.invite;

import java.util.Date;
import java.util.List;

import com.model.invite.Invite;
import com.service.IBaseManager;

public interface InviteManager extends IBaseManager<Invite, java.lang.Integer>{

	public void saveInvite(Invite invite);

	public List<Invite> getInviteeList(int fapptype,Date starttime,Date endtime,int isReward,int fuserid);
	
	public List<Invite> getInviteeListById(String fid,int fapptype);

	public Invite getInviterIdByuserid(String fid,int fapptype);
}
