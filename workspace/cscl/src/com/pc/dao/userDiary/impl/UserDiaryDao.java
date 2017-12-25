package com.pc.dao.userDiary.impl;


import org.springframework.stereotype.Service;


import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.userDiary.IuserDiaryDao;
import com.pc.model.CL_UserDiary;

@Service("userDiaryDao")
public class UserDiaryDao extends BaseDao<CL_UserDiary, Integer> implements IuserDiaryDao {
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_UserDiary";
	}
}
