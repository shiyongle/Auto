package Com.Dao.Inv;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Entity.Inv.Outwarehouse;
import Com.Entity.Inv.Productindetail;
import Com.Entity.Inv.Storebalance;
import Com.Entity.Inv.Usedstorebalance;

@Service("productInvDao")
public class ProductInvDao extends BaseDao implements IProductInvDao {

	private static final String THE_ZERO_COUNT_IS_UNAVAILABLE = "调整量不能为零";

	private static final String TRUE_BQTY_IS_LOWER_THAN_0 = "预计的结存将会＜0";

	@Resource
	private IStorebalanceDao storebalanceDao; 

	@Resource
	private IproductindetailDao productindetailDao;

	@Resource
	private IOutWarehouseDao outwarehouse;

	@Override
	public void updateWarehouseInOut(List<HashMap<String, Object>> products, String user,
			int theSetNumber, String fwarehouseId, String fwarehouseSiteId,
			String fsupplerID) {
		// TODO Auto-generated method stub
		if(user.contains("普通调拨"))
		{
			user=user.split(",")[0];
		}
		if (theSetNumber == 0) {
			
			throw new DJException(THE_ZERO_COUNT_IS_UNAVAILABLE);
			
		}
		for(int i=0;i<products.size();i++){
			String productId=products.get(i).get("fproductid").toString();
			int famount=Integer.valueOf(products.get(i).get("famount").toString());
		
		if (theSetNumber > 0) {

			Productindetail productindetailT = new Productindetail();

			productindetailT.setFid("");

			productindetailT.setFnumber(ServerContext.getNumberHelper()
					.getNumber("t_inv_productindetail ", "R", 4, false));
			productindetailT.setFcreatorid(user);
			productindetailT.setFcreatetime(new Date());
			productindetailT.setFtype(1);
			productindetailT.setFproductId(productId);
			productindetailT.setFinqty(new BigDecimal(theSetNumber*famount));

			productindetailT.setFwarehouseId(fwarehouseId);
			productindetailT.setFwarehouseSiteId(fwarehouseSiteId);

			productindetailT.setFaudited(1);
			productindetailT.setFauditorid(user);
			productindetailT.setFaudittime(new Date());

			productindetailT.setFsupplierid(fsupplerID);

			productindetailDao.ExecSave(productindetailT);

		} else {

//			theSetNumber = -theSetNumber;

			Outwarehouse outwarehouseT = new Outwarehouse();

			outwarehouseT.setFid(""); 

			outwarehouseT.setFnumber(ServerContext.getNumberHelper().getNumber(
					"t_inv_outwarehouse ", "U", 4, false));
			outwarehouseT.setFcreatorid(user);
			outwarehouseT.setFcreatetime(new Date());

			// outwarehouseT.setFtype(1);
			outwarehouseT.setFproductid(productId);
			outwarehouseT.setFoutqty(new BigDecimal(theSetNumber*famount*-1));

			outwarehouseT.setFwarehouseid(fwarehouseId);
			outwarehouseT.setFwarehousesiteid(fwarehouseSiteId);

			outwarehouseT.setFaudited(1);
			outwarehouseT.setFauditorid(user);
			outwarehouseT.setFaudittime(new Date());

			outwarehouseT.setFsupplierid(fsupplerID);

			outwarehouse.ExecSave(outwarehouseT);
			
			

		}
		}
	}

	@Override
	public void updateOrSaveStorebalance(List<HashMap<String, Object>> products, int theSetNumber,
			String fwarehouseId, String fwarehouseSiteId, String fsupplerID,
			String user) {
		// TODO Auto-generated method stub
		int isallot=0;//是否调拨标记 1:为调拨
		if(user.contains("普通调拨"))
		{
			user=user.split(",")[0];
			isallot=1;
		}
		for(int i=0;i<products.size();i++){
		String fproductID=products.get(i).get("fproductid").toString();
		int famount=Integer.valueOf(products.get(i).get("famount").toString());
		String hql = " from Storebalance s where s.fproductId = '%s' and  s.ftype = 0 and s.fwarehouseId = '%s' and s.fwarehouseSiteId = '%s' and s.fsupplierID = '%s' ";

		hql = String.format(hql, fproductID, fwarehouseId, fwarehouseSiteId,
				fsupplerID); 

		List<Storebalance> storebalancesT = storebalanceDao.QueryByHql(hql);

		Storebalance storebalanceT;

		// 新增
		if (storebalancesT.size() == 0) {

			if(isallot==1)
			{
				throw (new DJException("不存在库存记录，不能调拨！"));
			}
			storebalanceT = new Storebalance("");

			storebalanceT.setFcreatorid(user);

			storebalanceT.setFproductId(fproductID);

			storebalanceT.setFcreatetime(new Date());

			storebalanceT.setFwarehouseId(fwarehouseId);
			storebalanceT.setFwarehouseSiteId(fwarehouseSiteId);
			storebalanceT.setFsupplierID(fsupplerID);

			storebalanceT.setFtype(0);
			storebalanceT.setFallotqty(0);
			setStorebalanceQty(storebalanceT, theSetNumber*famount);

		} else {
			// 更新
			
			storebalanceT = storebalancesT.get(0);
			
			storebalanceT.setFupdatetime(new Date());
			storebalanceT.setFupdateuserid(user);
			if(isallot==1){
			setFallotQty(storebalanceT,theSetNumber*famount);
			}
			setStorebalanceQty(storebalanceT, theSetNumber*famount);
		}

		// storebalanceDao.saveOrUpdate(storebalanceT);

		storebalanceDao.ExecSave(storebalanceT);
		}
	}
	/**
	 *  调拨数量控制
	 * @param storebalanceT
	 * @param theSetNumber
	 */
	public void setFallotQty(Storebalance storebalanceT,
			Integer theSetNumber)
	{
		int allotQty = storebalanceT.getFallotqty() == null ? 0 : storebalanceT
				.getFallotqty();
		if (theSetNumber >= 0) {
			allotQty=allotQty-theSetNumber;
			if(allotQty<0)
			{
				allotQty=0;
//				storebalanceT.setFallotqty(storebalanceT.getFallotqty());
//				throw (new DJException("调拨数据异常！"));
			}
			storebalanceT.setFallotqty(allotQty);
		}else
		{
			storebalanceT.setFallotqty(allotQty-theSetNumber);
		}
		
	}
	private void setStorebalanceQty(Storebalance storebalanceT,
			Integer theSetNumber) {
		// TODO Auto-generated method stub

		int inT = storebalanceT.getFinqty() == null ? 0 : storebalanceT
				.getFinqty();
		int outT = storebalanceT.getFoutqty() == null ? 0 : storebalanceT
				.getFoutqty();
		int qtyT = storebalanceT.getFbalanceqty() == null ? 0 : storebalanceT
				.getFbalanceqty();

		if (theSetNumber >= 0) {

			int in = inT + theSetNumber;
			int bqty = qtyT + theSetNumber;

			storebalanceT.setFinqty(in);
			storebalanceT.setFbalanceqty(bqty);
		} else {

			theSetNumber = Math.abs(theSetNumber);

			int out = outT + theSetNumber;
			int bqty = qtyT - theSetNumber;

			// 出库占用量
			int footprint = selectFamountInDeliverorderByStorebalance(storebalanceT
					.getFid());

			// 记录占用的结存量
			int trueBqty = bqty - footprint;

			if (trueBqty < 0) {

				throw (new DJException(TRUE_BQTY_IS_LOWER_THAN_0));

			}

			storebalanceT.setFoutqty(out);
			storebalanceT.setFbalanceqty(bqty);
		}
	}

	private int selectFamountInDeliverorderByStorebalance(String fid) {
		// TODO Auto-generated method stub

		int footprint = 0;

		String hql = " from Usedstorebalance d where d.fstorebalanceid = '%s' and  d.fusedqty > 0 ";

		hql = String.format(hql, fid);

		List<Usedstorebalance> usedstorebalancesT = storebalanceDao.QueryByHql(hql);

		for (Usedstorebalance usedstorebalanceT : usedstorebalancesT) {

			footprint += usedstorebalanceT.getFusedqty();

		}

		return footprint;
	}

	@Override
	public void updateWarehouseInOutAndStorebalance(List<HashMap<String, Object>> products,
			String user, int theSetNumber, String fwarehouseId,
			String fwarehouseSiteId, String fsupplerID) {
		// TODO Auto-generated method stub

		updateOrSaveStorebalance(products, theSetNumber, fwarehouseId,
				fwarehouseSiteId, fsupplerID, user); 

		updateWarehouseInOut(products, user, theSetNumber, fwarehouseId,
				fwarehouseSiteId, fsupplerID);

	}

}
