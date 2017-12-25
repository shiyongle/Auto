package Com.Dao.order;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Controller.order.DeliverapplyController;
import Com.Controller.order.DeliverorderController;
import Com.Dao.System.ICustomer;
import Com.Dao.System.ISupplierDao;
import Com.Entity.System.Address;
import Com.Entity.System.Customer;
import Com.Entity.System.ProducePlan;
import Com.Entity.System.Productdef;
import Com.Entity.System.Supplier;
import Com.Entity.System.Useronline;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.ProductPlan;
@Service("productPlanDao")
public class ProductPlanDao extends BaseDao implements IProductPlanDao {
	@Resource ISupplierDao SupplierDao;
	@Resource ICustomer customerDao;
	@Override
	public HashMap<String, Object> ExecSave(ProductPlan cust) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (cust.getFid().isEmpty()) {
			cust.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(cust);
		
		params.put("success", true);
		return params;
	}

	@Override
	public ProductPlan Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				ProductPlan.class, fid);
	}

	@Override
	public HashMap<String, Object> ExecProductPlanDao(PreparedStatement stmt, Connection conn,HashMap<String, Object> params,int type) throws Exception {
		// TODO Auto-generated method stub
		if(type == 1){	//导入EAS系统
			ArrayList<String> insertOrdersql = (ArrayList<String>)params.get("insertOrdersql");
			ArrayList<String> insertEntrysql = (ArrayList<String>)params.get("insertEntrysql");
			ArrayList<String> updatecol = (ArrayList<String>)params.get("updatesql");
			
			for(int i=0;i<insertOrdersql.size();i++){
				String orderinfo = insertOrdersql.get(i);
				if(orderinfo.length()>0){
					stmt = conn.prepareStatement(orderinfo);
					stmt.execute();
				}
			}
			
			for(int j=0;j<insertEntrysql.size();j++){
				String entryinfo = insertEntrysql.get(j);
				if(entryinfo.length()>0){
					stmt = conn.prepareStatement(entryinfo);
					stmt.execute();
				}
			}

			for(int k=0;k<updatecol.size();k++){
				String updatesql = updatecol.get(k);
				if(updatesql.length()>0){
					ExecBySql(updatesql);
				}
			}
		}
		
		if(type == 2){	//收回制造商订单
			ArrayList<String> updatecol = (ArrayList<String>)params.get("updatecol");
			for(int j=0;j<updatecol.size();j++){
				String updatesql = updatecol.get(j).toString();
				if(updatesql.length()>0){
					ExecBySql(updatesql);
				}
			}
			
		}
		params.put("success", true);
		return params;
	}
	@Override
	public String ExecToDeliversBoard(HttpServletRequest request){
		DeliverapplyController deliverapply = (DeliverapplyController) SpringContextUtils.getBean("deliverapplyController");
		String fids = request.getParameter("fid");
		Address address = (Address)request.getAttribute("Address");
		String userid = ((Useronline)(request.getSession().getAttribute("Useronline"))).getFuserid();
		String sql = "";
		Deliverapply d;
		Customer c;
		List<ProductPlan> p;
		List<Productdef> pro;
		String fcreateboard = "";
		for(String fid : fids.split(",")){
			sql = "from ProductPlan where fid ='"+fid+"'";
			p= this.QueryByHql(sql);
			if(p.size()>0){
				if(p.get(0).getFstate()==0){
					throw new DJException("未接收不能采购纸板！");
				}
				sql = "from Productdef where fid='"+p.get(0).getFproductdefid()+"'";
				pro = this.QueryByHql(sql);
				if(pro.size()>0){
					if(p.get(0).isFcreateboard()==1){//标记状态为0的才能生成暂存纸板 ||p.get(0).isFcreateboard()==2
//						throw new DJException("已经生成采购的不能重复生成！");
							continue;
					}
					
					d = new Deliverapply();
					d.setFid(this.CreateUUid());
					p.get(0).setFcreateboard(1);//0为未采购 1为暂存采购  2为线下采购
					
					if(StringUtils.isEmpty(pro.get(0).getFmaterialfid())||StringUtils.isEmpty(pro.get(0).getFsupplierid())||StringUtils.isEmpty(pro.get(0).getFmateriallength())||StringUtils.isEmpty(pro.get(0).getFmaterialwidth())){
//						throw new DJException("当前产品没有对应材料或对应材料信息不完整！");
						p.get(0).setFcreateboard(2);
						this.saveOrUpdate(p.get(0));
						continue;//线下采购不生成暂存订单 跳过下面代码
					}
					if(pro.get(0).getFmateriallength().intValue()==0||pro.get(0).getFmateriallength().intValue()==0){
//						throw new DJException("下料规格不能为0！");
						p.get(0).setFcreateboard(2);
						this.saveOrUpdate(p.get(0));
						continue;//线下采购不生成暂存订单 跳过下面代码
					}
					
					d.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
					d.setFaddress(address.getFdetailaddress());
					d.setFaddressid(address.getFid());
					d.setFlinkman(address.getFlinkman());
					d.setFlinkphone(address.getFphone());
					d.setFamount(p.get(0).getFamount());
					if("不连做".equals(pro.get(0).getFseries())){
						d.setFamountpiece(d.getFamount()*2);
					}else{
						d.setFamountpiece(d.getFamount());
					}
					d.setFseries(pro.get(0).getFseries());
					d.setFstavetype(pro.get(0).getFstavetype());
//				d.setFarrivetime(p.get(0).getFaffirmtime());
					if(StringUtils.isEmpty(pro.get(0).getFboxheight())||StringUtils.isEmpty(pro.get(0).getFboxwidth())||StringUtils.isEmpty(pro.get(0).getFboxlength())){
						String fspec = pro.get(0).getFcharacter();
						if(!StringUtils.isEmpty(fspec)){
							d.setFboxlength(new BigDecimal(fspec.substring(0, fspec.indexOf("X"))));
							d.setFboxwidth(new BigDecimal(fspec.substring(fspec.indexOf("X")+1, fspec.lastIndexOf("X"))));
							d.setFboxheight(new BigDecimal(fspec.substring(fspec.lastIndexOf("X")+1,fspec.length())));
						}
					}else{
						d.setFboxheight(pro.get(0).getFboxheight());
						d.setFboxlength(pro.get(0).getFboxlength());
						d.setFboxwidth(pro.get(0).getFboxwidth());
					}
					
					d.setFmateriallength(pro.get(0).getFmateriallength());
					d.setFmaterialwidth(pro.get(0).getFmaterialwidth());
					try {
						d.setFboxmodel(Integer.valueOf(pro.get(0).getFboxmodelid()));
					} catch (Exception e) {
						// TODO: handle exception
						d.setFboxmodel(1);
					}
					d.setFboxtype(1);
					d.setFcreatetime(new Date());
					d.setFcreatorid(userid);
					d.setFcusproductid(p.get(0).getFcustproduct());
					d.setFcustomerid(p.get(0).getFsupplierid());//客户赋制造商ID
					d.setFmaterialfid(pro.get(0).getFmaterialfid());
					d.setFdefine1(pro.get(0).getFdefine1());
					d.setFdefine2(pro.get(0).getFdefine2());
					d.setFdefine3(pro.get(0).getFdefine3());
					d.setFdescription(p.get(0).getFdescription());
					d.setFhformula(pro.get(0).getFhformula0());
					d.setFhformula1(pro.get(0).getFhformula1());
					d.setFhline(pro.get(0).getFhformula());
					d.setFvline(pro.get(0).getFvformula());
					d.setFhstaveexp(pro.get(0).getFhstaveexp());
//					d.setFsupplierid(pro.get(0).getFsupplierid());
					d.setFtype("0");
					d.setFvstaveexp(pro.get(0).getFvstaveexp());
					d.setFstate(7);
					d.setFsupplierid(pro.get(0).getFmtsupplierid());
					d.setFvformula(pro.get(0).getFvformula0());
					Date cday = deliverapply.getBoardOrderArriveTime(pro.get(0));
					d.setFarrivetime(cday);
					c = (Customer)customerDao.Query(Customer.class, p.get(0).getFcustomerid());
					d.setFlabel(c.getFname());
					p.get(0).setFdeliversboardid(d.getFid());
					this.saveOrUpdate(d);
					this.saveOrUpdate(p.get(0));
					fcreateboard = "[{\"fcreateboard\":"+p.get(0).isFcreateboard()+"}]";
				}else{
					throw new DJException("没有找到对应的产品信息！");
				}
				
			}
		}
		return fcreateboard;
	}

	
}
