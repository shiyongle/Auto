package com.pc.dao.message;
import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Message;

public interface ImessageDao extends IBaseDao<CL_Message, java.lang.Integer> {
	
	/**获取刚插入的对象的ID*/
	public int getMsgId();
	
	/**批量删除*/
	public int deleteById(Integer[] ids) ;
	
	/**通过指定数据跟新*/
	public int updateById(CL_Message message);
	
	/**通过id 用户ID  状态 更新为已读信息**/
	public int updateIsRead(int id,int receiver,int fisread,String phone) ;
}
