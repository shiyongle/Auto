
package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.System.Note;

@Service
public class NoteDao extends BaseDao implements INoteDao {


	@Override 
	public HashMap<String, Object> ExecSave( Note po) {
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
	public  Note Query(String fid) {
		// TODO Auto-generated method stub
		return ( Note) this.getHibernateTemplate().get(
				 Note.class, fid);
	}

	@Override
	public void ExecSaveNotes(List<Note> notes) {
		// TODO Auto-generated method stub
		
		for (Note note : notes) {
			ExecSave(note);
		}
		
	}
}
