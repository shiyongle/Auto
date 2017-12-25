package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.System.Bodypape;

@Service
public class BodypapeDao extends BaseDao implements IBodypapeDao {


	@Override 
	public HashMap<String, Object> ExecSave( Bodypape po) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (po.getFid().isEmpty()) {
			po.setFid(this.CreateUUid());
		}
		this.saveOrUpdate(po);

		params.put("success", true);
		return params;
	}

	@Override
	public  Bodypape Query(String fid) {
		// TODO Auto-generated method stub
		return ( Bodypape) this.getHibernateTemplate().get(
				 Bodypape.class, fid);
	}

	@Override
	public void ExecSaveBodyPapes(List<Bodypape> bodyPapes) {
		// TODO Auto-generated method stub
		
		for (Bodypape bodypape : bodyPapes) {
		
			ExecSave(bodypape);
			
		}
		
	}
}
