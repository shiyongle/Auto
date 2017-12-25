package com.pc.dao.UUID;

import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Uuid;

public interface IUuidDao extends IBaseDao<CL_Uuid, Integer> {
	public List<CL_Uuid> getByUUID(String UUID);
	public List<CL_Uuid> getByUUIDAndFid(String UUID,int fid);
}
