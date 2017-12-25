package com.pc.dao.abnormity;


import java.util.List;
import java.util.Map;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Abnormity;

public interface IabnormityDao extends  IBaseDao<CL_Abnormity, java.lang.Integer>{
     /****根据 id修改upId** */
      public int updateUpId(Integer id,Integer fuploadId);

      /****根据投诉查询订单信息** */
      public List<Map<String,Object>> getOrderInfoByAbId(Integer fid);
      
      /****查询十五分钟内是否有新投诉** */
      public int haveNewAbnormal();
            
      /****根据 id修改remark** */
      public int updateRemark(Integer id,String fremark);
}
