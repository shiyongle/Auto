package Com.Dao.SDK;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.Productdef;
import Com.Entity.System.ProductdefProducts;
import Com.Entity.System.Useronline;

@Service("ProductdefSDKDao")
public class ProductdefSDKDao extends BaseDao implements IProductdefSDKDao {

	@Override
	public void ExecImpPdtSDK(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		Productdef pinfo =(Productdef)request.getAttribute("Productdef");
		String sql="";
		if(pinfo.getFeasproductId().isEmpty())
		{
			throw new DJException("EASid为空");
		}
		if(pinfo.getFbaseid().isEmpty())
		{
			throw new DJException("baseid为空");
		}
		if(pinfo.getFcustomerid().isEmpty())
		{
			throw new DJException("客户为空");
		}
		if(pinfo.getFid()==null || pinfo.getFid().equals(""))
		{
			pinfo.setFid(CreateUUid());
		}
		sql="select fid from t_pdt_productdef where feasproductid='"+pinfo.getFeasproductId()+"'";
		List<HashMap<String,Object>> pdtcls=QueryBySql(sql);
		if(pdtcls.size()>0)
		{
			return;
		}
		sql="select fid from t_bd_customer where fid='"+pinfo.getFcustomerid()+"'";
		pdtcls=QueryBySql(sql);
		if(pdtcls.size()<=0)
		{
			throw new DJException("客户不存在");
		}
		sql="update  t_pdt_productdef  set feffected=0,feffect=0,fishistory=1 where fbaseid='"+pinfo.getFbaseid()+"'";
		this.ExecBySql(sql);
//		//修改历史结构数据 ，改成新产品
//		sql="update t_pdt_productdefproducts  set   fproductid='"+pinfo.getFid()+"' where fproductid in (select fid from t_pdt_productdef where fbaseid='"+pinfo.getFbaseid()+"' )";
//		this.ExecBySql(sql);
		pinfo.setFcreatorid(userid);
		pinfo.setFcreatetime(new Date());
		pinfo.setFaudited(0);
		pinfo.setFlastupdatetime(new Date());
		pinfo.setFlastupdateuserid(userid);
		pinfo.setFeffect(0);
		pinfo.setFishistory(0);
		this.saveOrUpdate(pinfo);
	}

	@Override
	public void ExecImpPdtChildSDK(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String sql="";
		String parentfid="";
		if(request.getAttribute("ProductdefProducts")==null)
		{
			throw new DJException("没有数据");
		}
		List<ProductdefProducts> ProductdefProductscls=(ArrayList<ProductdefProducts>)request.getAttribute("ProductdefProducts");
		if(ProductdefProductscls.size()<=0)
		{
			throw new DJException("没有数据");
		}
		for(int i=0;i<ProductdefProductscls.size();i++)
		{
			ProductdefProducts info=ProductdefProductscls.get(i);
			if(info.getFparentid().equals(info.getFproductid()))
			{
				throw new DJException("上下级产品不能为同一个产品");
			}
			if(info.getFid()==null || info.getFid().equals(""))
			{
				info.setFid(CreateUUid());
			}
			sql=" select fid from t_pdt_productdef where feasproductid='"+info.getFparentid()+"' ";
			List<HashMap<String,Object>> results=QueryBySql(sql);
			if(results.size()>0)
			{
				info.setFparentid(results.get(0).get("fid").toString());
				if(!parentfid.equals("") && !parentfid.equals(info.getFparentid()))
				{
					throw new DJException("上级产品不一致");
				}
				parentfid=info.getFparentid();
			}
			else
			{
				throw new DJException("找不到上级产品");
			}
			sql=" select fid from t_pdt_productdef where feasproductid='"+info.getFproductid()+"' ";
		    results=QueryBySql(sql);
			if(results.size()>0)
			{
				info.setFproductid(results.get(0).get("fid").toString());
			}
			else
			{
				throw new DJException("找不到子件产品:"+info.getFproductid());
			}
			sql="select fid from t_pdt_productdefproducts where fparentid='"+info.getFparentid()+"' and fproductid='"+info.getFproductid()+"'";
			if(QueryExistsBySql(sql))
			{
				throw new DJException("已经存在相同的结构数据");
			}
			saveOrUpdate(info);
		}
		if(ProductdefProductscls.size()>0)
		{
			ExecBySql("update t_pdt_productdef set fnewtype=4 where fid='"+parentfid+"'");
		}
	}

}
