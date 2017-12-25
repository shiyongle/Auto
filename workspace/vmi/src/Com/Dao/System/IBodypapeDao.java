package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Bodypape;

public interface IBodypapeDao extends IBaseDao {
	public HashMap<String,Object> ExecSave(Bodypape po);
	public Bodypape Query(String fid);
	public void ExecSaveBodyPapes(List<Bodypape> bodyPapes);
		
}