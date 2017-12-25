package com.dao.invite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.dao.IBaseDaoImpl;
import com.model.invite.Invite;

@SuppressWarnings("unchecked")
@Repository("inviteDao")
public class InviteDaoImpl extends IBaseDaoImpl<Invite,java.lang.Integer> implements InviteDao {

	/**
	 * 根据邀请人查询其所有下线
	 */
	@Override
	public List<Invite> getInviteeList(int fapptype,Date starttime,Date endtime,int isReward,int fuserid) {
		String hql="from Invite where fapptype=?";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(endtime!=null)
		{
			Calendar   calendar   =  Calendar.getInstance();; 
			calendar.setTime(endtime); 
			calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
			endtime=calendar.getTime();   
		}
		if(starttime!=null)
		{
			hql+=" and fcreatetime>='"+sdf.format(starttime)+"'";
		}
		if(endtime!=null)
		{
			hql+=" and fcreatetime<='"+sdf.format(endtime)+"'";
		}
		if(isReward==1)
		{
			hql+=" and fbonus =0";
		}
		if(isReward==2)
		{
			hql+=" and fbonus !=0";
		}
		if(fuserid!=0&&fuserid!=-1)
		{
			hql+=" and fuserroleid="+fuserid;
		}
//		String sql="SELECT si.fid,si.fuserid,si.fuserroleid,si.finviteeid,si.fcreatetime,si.fbonus,si.fapptype,user1.fname fusername,user2.fname finviteename FROM t_sys_invite si LEFT JOIN t_sys_user user1 ON (si.fuserid=user1.fid) LEFT JOIN t_sys_user user2 ON (si.finviteeid=user2.fid) where si.fapptype=?";
//		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);  
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
		query.setInteger(0, fapptype);
		List<Invite> list = query.list(); 
		if(list != null && list.size() > 0){
			return list;
		}
		return null;  
	}
	
	
	/**
	 * 根据邀请人查询其所有下线
	 */
	@Override
	public List<Invite> getInviteeListById(String uid,int fapptype) {
		String hql="from Invite where finviterid=? and fapptype=?";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, uid);  
	    query.setInteger(1, fapptype);  
	    List<Invite> list = query.list(); 
	    if(list != null && list.size() > 0){
			return list;
		}
	    return null;  
	}

	/**
	 * 根据用户id查询其上线，即邀请人
	 */
	@Override
	public Invite getInviterIdByuserid(String uid,int fapptype) {
		String hql="from Invite where finviteeid=? and fapptype=?";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, uid);  
	    query.setInteger(1, fapptype);  
	    List<Invite> list = query.list(); 
	    if(list != null && list.size() > 0){
			return list.get(0);
		}
	    return null;  
	}

}
