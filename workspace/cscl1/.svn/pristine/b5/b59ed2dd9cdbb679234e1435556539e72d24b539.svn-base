package com.pc.dao.bank;

import java.util.List;
import java.util.Map;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Bank;

public interface IbankDao extends IBaseDao<CL_Bank,java.lang.Integer>{
	
    /****修改 绑定状态 默认 和是否生效*/
	public int updateByStatus(Integer fdefault,Integer feffect,String number, Integer fid, Integer fuserId);
	
	/***根据用户id 和银行卡号查询**/
	public CL_Bank getByUserIdAndFcardNumber(String fcardNumber,Integer fuserId);
   /**用户删除*/	
   public int updateByFdel(Integer fdel,Integer fid, Integer fuserId);
   
   /**用户根据主键以及创建人ID*/
  public CL_Bank getByIdAndFcreator(Integer fid,Integer fuserId);
  
  /***根据用户id 和银行卡号查询 有效的银行卡**/
  public CL_Bank getByUserIdAndFcardNumberAndV(String fcardNumber,Integer fuserId);
  
  /**根据用户ID 获取银行卡列表*/
  public List<Map<String, Object>> getByUserList(Integer fuserId,Integer feffect);
  
  /**批量移除用户银行卡**/
  public int removeUserBank(String fids,Integer fuserId);
  
  /**根据id**/
  public CL_Bank getBankInfo(Integer fid,Integer fuserId,Integer feffect);
  
}
