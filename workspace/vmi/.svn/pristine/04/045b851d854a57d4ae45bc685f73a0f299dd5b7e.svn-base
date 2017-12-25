package Com.Dao.mobile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.UploadFile;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.order.IDeliverapplyDao;
import Com.Entity.System.Custproduct;
import Com.Entity.System.ProducePlan;
import Com.Entity.System.Productdef;
import Com.Entity.order.CusPrivateDelivers;
import Com.Entity.order.Cusdelivers;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Mystock;
import Com.Entity.order.SchemeDesignEntry;

@Service("mobileSDKDao")
public class MobileSDKDao extends BaseDao implements IMobileSDKDao{

	@Resource
	IDeliverapplyDao deliverapplyDao;
	@Override
	public void ExecUpdateShopFamont(List<Deliverapply> list) throws Exception {
		// TODO Auto-generated method stub
		String sql="";
		for(Deliverapply apply:list)
		{
			if(apply.getFboxtype()==1)//纸板数量更新
			{
				sql="update t_ord_deliverapply set famount=%d,famountpiece=IF(fseries='连做',famount,2*famount) where fid='%s'";
				this.ExecBySql(String.format(sql,apply.getFamount(),apply.getFid()));
			}else////纸箱数量更新
			{
				sql="update t_ord_cusprivatedelivers set famount=%d where fid='%s'";
				this.ExecBySql(String.format(sql,apply.getFamount(),apply.getFid()));
			}
		}
		
	}

	@Override
	public void ExecDeleteBoardShopRecord(String fidcls) throws Exception {
		this.ExecBySql("delete from t_ord_deliverapply where fboxtype=1 and (fstate=0 or fstate=7) and fid"+fidcls);
		if(this.QueryExistsBySql("select 1 from t_ord_productplan where fdeliversboardid"+fidcls)){//我的业务生成的纸板订单删除时需要，更改我的业务状态
			this.ExecBySql("update  t_ord_productplan set fcreateboard=0 where fdeliversboardid"+fidcls);
		}
	}

	@Override
	public int ExecUpdateBoardState(List<Deliverapply> list) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer fidcls=new StringBuffer();
		for(Deliverapply apply :list)
		{
			fidcls.append(apply.getFid()+",");
		}
		fidcls.delete(fidcls.length()-1,fidcls.length());
		int count=deliverapplyDao.ExecUpdateBoardStateToCreate(fidcls.toString());
		String sql = "update t_ord_deliverapply set famount=%d,famountpiece=IF(fseries='连做',famount,2*famount) where fid='%s'";
		for(Deliverapply apply :list)
		{
			this.ExecBySql(String.format(sql, apply.getFamount(),apply.getFid()));
		}
		return count;
	}

	@Override
	public void SaveCusprivate(CusPrivateDelivers c,Deliverapply a) throws Exception {
		// TODO Auto-generated method stub
		this.save(c);
		if(a!=null)this.Delete(a);
	}


	
	
}
