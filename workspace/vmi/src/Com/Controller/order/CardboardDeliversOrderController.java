package Com.Controller.order;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.order.IDeliverorderDao;
import Com.Entity.System.Useronline;
import Com.Entity.order.Deliverapply;

@Controller
public class CardboardDeliversOrderController {
	private static final String BASE_SQL = " SELECT fdescription,fstockinqty,fsuppliername, fcreator, fcustname, fplanNumber, fid, fcreatorid, fcreatetime, fnumber, fcustomerid, farrivetime, flinkman, flinkphone, famount, faddress, fstate, foutQty, fmaterialname, fboxmodel, fboxlength, fboxwidth, fboxheight, fmateriallength, fmaterialwidth, fstavetype, fvformula, fhformula, famountpiece, fseries, fouttime, frecipient, freceiptTime FROM t_ord_deliverapply_card_board_view ";

	Logger log = LoggerFactory
			.getLogger(CardboardDeliversOrderController.class);

	@Resource
	private IDeliverorderDao deliverorderDao;

	@RequestMapping(value = "/gainCardboardDeliversOrders")
	public String gainCardboardDeliversOrders(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {
			String sql = BASE_SQL + " where 1 = 1 " + deliverorderDao.QueryFilterByUser(request, "fcustomerid", null);

			ListResult result = deliverorderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/gainCardboardDeliversOrdersMV")
	public String gainCardboardDeliversOrdersMV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String query = request.getParameter("query");
			String fcustomerid = request.getParameter("fcustomerid");
			String sql = "SELECT fwerkname,_materialname fmaterialname,_stockinqty fstockinqty,_suppliername,_creator fcreator,_recipient frecipient,_custname, fplanNumber,fdescription, fid, fcreatorid, fcreatetime, fnumber, fcustomerid, farrivetime, flinkman, flinkphone, famount, faddress, fstate, foutQty, fboxmodel, fboxlength, fboxwidth, fboxheight, fmateriallength, fmaterialwidth, fstavetype, fvformula, fhformula, famountpiece, fseries, fouttime, freceiptTime FROM t_ord_deliverapply_board_mv where ";
			if(StringUtils.isEmpty(fcustomerid)){
				sql = sql + " 1 = 1 " + deliverorderDao.QueryFilterByUser(request, "fcustomerid", null);
			}else{
				sql += "fcustomerid = '"+fcustomerid+"'";
			}
			if(!StringUtils.isEmpty(query)){
				query = JsonUtil.decodeUnicode(query);
				if(query.indexOf("___=")!=-1){
					query = query.replaceAll("___=", "*");
					sql += " and (concat(fboxlength,'*',fboxwidth,'*',fboxheight) like '%"+query+"%' or concat(fmateriallength,'*',fmaterialwidth) like '%"+query+"%')";
				}
			}
			ListResult result = deliverorderDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	/**
	 * 纸板订单接收
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/receiveBoardOrders")
	public String receiveBoardOrders(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fidcls = request.getParameter("fidcls");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			deliverorderDao.ExecReceiveBoardOrders(fidcls,userid);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	@RequestMapping("/unReceiveBoardOrders")
	public String unReceiveBoardOrders(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fidcls = request.getParameter("fidcls");
		try {
			deliverorderDao.ExecUnReceiveBoardOrders(fidcls);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	/*@RequestMapping(value = "/receiveDeliversOrders")
	public String receiveDeliversOrders(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String fids = request.getParameter("fids");

		String state = request.getParameter("goalValue");

		int stateInt = Integer.parseInt(state);

		String userid = MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(
				request);

		int preSelectSP = stateInt == 1 ? 0 : 1;

		try {

			List<String> fidsA = null;

			List<String> fidsA2 = null;

			if (stateInt == 1) {

				String hql = String
						.format(" select fid from Deliverapply where fstate = 0 and fid in ('%s') ",
								fids.replaceAll(",", "','"));

				fidsA = deliverorderDao.QueryByHql(hql);

			} else {

				String hql = String
						.format(" select fid from Deliverapply where fstate = 1 and fid in ('%s') ",
								fids.replaceAll(",", "','"));

				fidsA = deliverorderDao.QueryByHql(hql);

				hql = String
						.format(" select fid from Deliverapply where fstate = 3 and fid in ('%s') ",
								fids.replaceAll(",", "','"));

				fidsA2 = deliverorderDao.QueryByHql(hql);

				// 已下单，未确认

				for (String id : fidsA2) {

					String hql2 = String
							.format(" select faffirmed from ProductPlan where fdeliverapplyid = '%s' ",
									id);

					List<Integer> affirmed = deliverorderDao.QueryByHql(hql2);

					if (affirmed.size() != 0) {

						if (affirmed.get(0) == 0) {

							fidsA.add(id);

						}

					}

				}

			}

			// String hql =
			// String.format(" select fid from Deliverapply where fstate = %d and fid in ('%s') ",
			// preSelectSP, fids.replaceAll(",", "','"));
			//
			// List<String> fidsA = deliverorderDao.QueryByHql(hql);

			if (fidsA.size() == 0) {

				reponse.getWriter().write(
						JsonUtil.result(false, "没有可以操作的条目", "", ""));

				return null;

			}

			deliverorderDao.ExecUpdateState(fidsA, Integer.parseInt(state),
					userid, fidsA2);

			reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}*/

	/*@RequestMapping(value = "/placeCarboardOrder")
	public String placeCarboardOrder(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String fids = request.getParameter("fids");

		String userid = MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(
				request);

		try {

			List<String> fidsA = (List<String>) JSONArray
					.toCollection(JSONArray.fromObject(fids));

			filterIds(fidsA);

			if (fidsA.size() == 0) {

				throw new DJException("没有可以下单的申请");

			}

			List<Deliverapply> deliverapplys = gainDeliverapply(fidsA);

			List<ProductPlan> pps = buildPlans(fidsA, deliverapplys, userid);

			List<Deliverorder> deliverOrders = buildDeliverorders(fidsA,
					deliverapplys, pps, userid);
			
			List<Storebalance> storebalances = buildStorebalances(deliverapplys, pps, userid);
			
			List<Usedstorebalance> usedstorebalances = buildUsedstorebalances(deliverapplys, pps, deliverOrders,storebalances,userid);

			deliverorderDao.ExecDoPlaceCarboardOrder(deliverapplys, pps,
					deliverOrders, storebalances, usedstorebalances);

			reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	private List<Usedstorebalance> buildUsedstorebalances(
			List<Deliverapply> deliverapplys, List<ProductPlan> pps,
			List<Deliverorder> deliverOrders, List<Storebalance> storebalances, String userid) {
		// TODO Auto-generated method stub
		
		List<Usedstorebalance> usedstorebalances = new ArrayList<>();
		
		for (int i = 0; i < deliverapplys.size(); i++) {
			
			Usedstorebalance usedstorebalance = new Usedstorebalance(deliverorderDao.CreateUUid());
			
			usedstorebalance.setFdeliverorderid(deliverOrders.get(i).getFid());
			usedstorebalance.setFproductid(deliverapplys.get(i).getFmaterialfid());
			usedstorebalance.setFratio(1);
			usedstorebalance.setFstorebalanceid(storebalances.get(i).getFid());
			
			usedstorebalance.setFtype(1);
			usedstorebalance.setFusedqty(deliverOrders.get(i).getFamount());
			
			usedstorebalances.add(usedstorebalance);
		}
		
		return usedstorebalances;
	}

	private List<Storebalance> buildStorebalances(
			List<Deliverapply> deliverapplys, List<ProductPlan> pps, String userid) {
		// TODO Auto-generated method stub
		
		List<Storebalance> storebalances = new ArrayList<>();
		
		for (int i = 0; i < deliverapplys.size(); i++) {
			
			Storebalance storebalance = new Storebalance();
			
			storebalance.setFallotqty(0);
			storebalance.setFbalanceqty(0);
			storebalance.setFcreatetime(new Date());
			storebalance.setFcreatorid(userid);
			
			storebalance.setFcustomerid(deliverapplys.get(i).getFcustomerid());
			storebalance.setFdescription(deliverapplys.get(i).getFdescription());
			storebalance.setFid(deliverorderDao.CreateUUid());
			storebalance.setFinqty(0);
			
			storebalance.setForderentryid(null);
			storebalance.setFoutqty(0);
			storebalance.setFproductId(deliverapplys.get(i).getFmaterialfid());
			storebalance.setFproductplanId(deliverapplys.get(i).getFplanid());
			
			storebalance.setFsaleorderid(null);
			storebalance.setFsupplierID(deliverapplys.get(i).getFsupplierid());
			storebalance.setFtraitid(null);
			storebalance.setFtype(1);
			
			storebalance.setFupdatetime(new Date());
			storebalance.setFupdateuserid(userid);
			
			String hql = String.format(" select fwarehouseid, fwarehousesiteid from Supplier where fid = '%s' ", deliverapplys.get(i).getFsupplierid());
			
			List<Object[]> fwarehouseidAndFwarehousesiteids = deliverorderDao.QueryByHql(hql);
			
			storebalance.setFwarehouseId((String)(fwarehouseidAndFwarehousesiteids.get(0))[0]);
			storebalance.setFwarehouseSiteId((String)(fwarehouseidAndFwarehousesiteids.get(0))[1]);
			
			storebalances.add(storebalance);
		}
		
		return storebalances;
	}

	private List<Deliverapply> gainDeliverapply(List<String> fidsA) {
		// TODO Auto-generated method stub

		List<Deliverapply> deliverapplys = new ArrayList<>();

		for (String fid : fidsA) {

			deliverapplys.add((Deliverapply) deliverorderDao.Query(
					Deliverapply.class, fid));

		}

		return deliverapplys;
	}

	private void filterIds(List<String> fidsA) {
		// TODO Auto-generated method stub

		Iterator<String> iter = fidsA.iterator();

		while (iter.hasNext()) {

			String id = iter.next();

			Deliverapply da = (Deliverapply) deliverorderDao.Query(
					Deliverapply.class, id);

			if (da.getFstate() != 1) {

				iter.remove();

			}

		}

	}

	private List<Deliverorder> buildDeliverorders(List<String> fidsA,
			List<Deliverapply> deliverapplys, List<ProductPlan> pps,
			String userid) {
		// TODO Auto-generated method stub

		List<Deliverorder> deliverorders = new ArrayList<>();

		for (int i = 0; i < deliverapplys.size(); i++) {

			String idT = deliverorderDao.CreateUUid();

			Deliverorder deliverorder = new Deliverorder();

			Deliverapply deliverapplyT = deliverapplys.get(i);

			// ProductPlan productPlan = pps.get(i);

			deliverorder.setFid(idT);
			deliverorder.setFaddress(deliverapplyT.getFaddress());
			deliverorder.setFaddressid(deliverapplyT.getFaddressid());
			deliverorder.setFalloted(0);

			deliverorder.setFamount(deliverapplyT.getFamountpiece());
			deliverorder.setFarrivetime(deliverapplyT.getFarrivetime());
			// deliverorder.setFassembleQty();
			// deliverorder.setFaudited(deliverapplyT.getfa)

			// deliverorder.setFauditorid(deliverapplyT.get)
			// deliverorder.setFaudittime(deliverapplyT.getfa)
			// deliverorder.setFbalanceprice(deliverapplyT.getfb)
			// deliverorder.setFbalanceunitprice(deliverapplyT.getfb)

			deliverorder.setFcreatetime(new Date());
			deliverorder.setFcreatorid(userid);
			deliverorder.setFcusfid(deliverapplyT.getFcusfid());
			deliverorder.setFcusproductid(deliverapplyT.getFmaterialfid());

			deliverorder.setFcustomerid(deliverapplyT.getFcustomerid());
			deliverorder.setFdeliversId(deliverapplyT.getFid());
			deliverorder.setFdescription(deliverapplyT.getFdescription());
			deliverorder.setFeasdeliverid(deliverapplyT.getFeasdeliverid());

			deliverorder.setFimportEas(deliverapplyT.getFimportEas());
			deliverorder.setFimportEastime(deliverapplyT.getFimportEastime());
			deliverorder.setFimportEasuserid(deliverapplyT
					.getFimportEasuserid());
			// deliverorder.setFissync(deliverapplyT.getfi)

			deliverorder.setFistoPlan(deliverapplyT.getFistoPlan());
			deliverorder.setFlinkman(deliverapplyT.getFlinkman());
			deliverorder.setFlinkphone(deliverapplyT.getFlinkphone());
			// deliverorder.setFmatched(deliverapplyT.getfm)

			deliverorder.setFnumber(ServerContext.getNumberHelper().getNumber(
					"t_ord_deliverorder", "D", 4, false));

			deliverorder.setFplanNumber(deliverapplyT.getFplanNumber());

			deliverorder.setFordered(deliverapplyT.getFordered());
			deliverorder.setForderentryid(deliverapplyT.getForderentryid());
			deliverorder.setFordermanid(deliverapplyT.getFordermanid());

//			deliverorder.setFordernumber(deliverapplyT.getFordernumber());
			deliverorder.setFordertime(deliverapplyT.getFordertime());
			deliverorder.setFouted(0);
			deliverorder.setFoutorid(deliverapplyT.getFoutorid());

			deliverorder.setFoutQty(deliverapplyT.getFoutQty());
			deliverorder.setFouttime(deliverapplyT.getFouttime());
			// deliverorder.setFpcmordernumber(deliverapplyT.getfp)
			deliverorder.setFplanid(deliverapplyT.getFplanid());

			deliverorder.setFplanNumber(deliverapplyT.getFplanNumber());
			deliverorder.setFplanTime(deliverapplyT.getFplanTime());
			deliverorder.setFproductid(deliverapplyT.getFmaterialfid());
			// deliverorder.setFpurchaseprice(deliverapplyT.getfp)

			// deliverorder.setFpurchaseunitprice(deliverapplyT.getfp)
			// deliverorder.setFsaleorderid(deliverapplyT.getFsaleorderid());
			// deliverorder.setFstorebalanceid(deliverapplyT.getfs)
			// deliverorder.setFstorebalanceid(deliverapplyT.getfs)

			deliverorder.setFsupplierId(deliverapplyT.getFsupplierid());
			deliverorder.setFtraitid(deliverapplyT.getFtraitid());
			deliverorder.setFtype(deliverapplyT.getFtype());
			deliverorder.setFupdatetime(new Date());

			deliverorder.setFupdateuserid(userid);
			deliverorder.setFboxtype((byte) 1);
			deliverorder.setFordernumber(deliverapplyT.getFplanNumber());
			deliverorder.setFaudited(1);
			deliverorder.setFauditorid(userid);
			deliverorder.setFaudittime(new Date());
			deliverorder.setFdescription(deliverapplyT.getFdescription());
			deliverorders.add(deliverorder);

		}

		return deliverorders;
	}

	private List<ProductPlan> buildPlans(List<String> fidsA,
			List<Deliverapply> deliverapplys, String userid) {
		// TODO Auto-generated method stub

		List<ProductPlan> productPlans = new ArrayList<>();

		for (Deliverapply deliverapply : deliverapplys) {

			String idT = deliverorderDao.CreateUUid();

			ProductPlan productPlan = new ProductPlan();

			productPlan.setFtype(1);
			productPlan.setFsupplierid(deliverapply.getFsupplierid());
			productPlan.setFstate(0);
			productPlan.setFproductdefid(deliverapply.getFmaterialfid());

			String num = ServerContext.getNumberHelper().getNumber(
					"t_ord_productplan", "P", 4, false);

			deliverapply.setFplanNumber(num);
			deliverapply.setFplanid(idT);
			
			
			productPlan.setFnumber(num);

			productPlan.setFid(idT);
			productPlan.setFcustomerid(deliverapply.getFcustomerid());
			productPlan.setFcreatorid(userid);

			productPlan.setFcreatetime(new Date());
			productPlan.setFaffirmed(0);
			productPlan.setFamount(deliverapply.getFamountpiece());
			productPlan.setFarrivetime(deliverapply.getFarrivetime());

			productPlan.setFaudited(1);
			productPlan.setFauditorid(userid);
			productPlan.setFaudittime(new Date());
			productPlan.setFcloseed(0);

			productPlan.setFboxtype(1);
			productPlan.setFdeliverapplyid(deliverapply.getFid());
			productPlan.setFdescription(deliverapply.getFdescription());
			// 已分配
			deliverapply.setFstate(3);

			productPlans.add(productPlan);
		}

		return productPlans;
	}*/

	//
	// @RequestMapping(value = "/saveCardboardDeliversOrder")
	// public String saveCardboardDeliversOrder(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException {
	//
	// try {
	// CardboardDeliversOrder po = buildPo(request, deliversOrderDao);
	//
	// deliversOrderDao.ExecSave(po);
	//
	// reponse.getWriter().write(JsonUtil.result(true, "成功", "", ""));
	// } catch (DJException e) {
	// // TODO Auto-generated catch block
	// reponse.getWriter().write(
	// JsonUtil.result(false, e.getMessage(), "", ""));
	// }
	// return null;
	// }
	//
	// private CardboardDeliversOrder buildPo(HttpServletRequest request,
	// IBaseDao dao) {
	// String fid = request.getParameter("fid");
	//
	// CardboardDeliversOrder deliversOrder;
	// //修改
	// if (fid != null && !fid.isEmpty()) {
	//
	// deliversOrder = deliversOrderDao.Query(fid);
	//
	// CardboardDeliversOrder deliversOrderTC = ("CardboardDeliversOrder")
	// request.getAttribute("CardboardDeliversOrder");
	//
	// } else {
	// //新增
	// deliversOrder = ("CardboardDeliversOrder")
	// request.getAttribute("CardboardDeliversOrder");
	//
	// }
	//
	// return deliversOrder;
	// }

	// @RequestMapping(value = "/makeCardboardDeliversOrdertoExcel")
	// public String makeCardboardDeliversOrdertoExcel(HttpServletRequest
	// request,
	// HttpServletResponse reponse) throws IOException {
	// try {
	// String sql = "";
	//
	// ListResult result = deliversOrderDao.QueryFilterList(sql, request);
	// ExcelUtil.toexcel(reponse, result);
	// } catch (DJException e) {
	// // TODO Auto-generated catch block
	// reponse.getWriter().write(
	// JsonUtil.result(false, e.getMessage(), "", ""));
	// }
	//
	// return null;
	//
	// }

	// @RequestMapping(value = "/deleteCardboardDeliversOrders")
	// public String deleteCardboardDeliversOrders(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException {
	//
	// String fids = request.getParameter("fidcls");
	//
	// String fidsS = "('" + fids.replace(",", "','") + "')";
	//
	// try {
	//
	// String sql = " DELETE FROM deliversOrder WHERE fid in %s ";
	//
	// sql = String.format(sql, fidsS);
	//
	// deliversOrderDao.ExecBySql(sql);
	//
	// reponse.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
	// } catch (Exception e) {
	// reponse.getWriter().write(
	// JsonUtil.result(false, e.toString(), "", ""));
	// }
	// return null;
	//
	// }
	@RequestMapping("updateCarboardDescription")
	public void updateCarboardDescription(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String description = request.getParameter("description");
			String fid = request.getParameter("fid");
			if(!StringUtils.isEmpty(description)&&!StringUtils.isEmpty(fid)){
				Deliverapply d = (Deliverapply)deliverorderDao.Query(Deliverapply.class, fid);
				if(d.getFstate()!=0||!StringUtils.isEmpty(d.getFplanNumber())){
					throw new  DJException("必须是已创建状态且订单编号为空的记录！");
				}
				String sql = "update t_ord_deliverapply set fwerkname = '%s',fstate=8 where fid = '%s'";
				sql = String.format(sql, description,fid);
				deliverorderDao.ExecBySql(sql);
				response.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
			}
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "", ""));
		}
	}
}