package Com.Dao.SDK;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.params;

@Service("TableSplitSDKDao")
public class TableSplitSDKDao extends BaseDao implements ITableSplitSDKDao {
	@Override
	public void ExecSplitTableData() {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date start = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.add(Calendar.MONTH, -3);
		params p = new params();
		p.put("curdate", f.format(c.getTime()));
		// System.out.println(f.format(c.getTime()));

		// 出库单子表
		this.ExecBySql(
				" INSERT INTO t_tra_saledeliverentry_h SELECT   * FROM t_tra_saledeliverentry ttse WHERE ttse.FPARENTID IN (SELECT     tts.FID   FROM t_tra_saledeliver tts   WHERE tts.FAUDITED = 1   AND tts.FAUDITDATE < :curdate)",
				p);
		this.ExecBySql(
				" DELETE ttse FROM t_tra_saledeliverentry ttse WHERE ttse.FPARENTID IN (SELECT     tts.FID   FROM t_tra_saledeliver tts   WHERE tts.FAUDITED = 1   AND tts.FAUDITDATE < :curdate)",
				p);

		// 出库单
		this.ExecBySql(
				" INSERT INTO t_tra_saledeliver_h SELECT tts.* FROM t_tra_saledeliver tts WHERE tts.FAUDITED = 1 AND tts.FAUDITDATE < :curdate",
				p);
		this.ExecBySql(
				" DELETE tts FROM t_tra_saledeliver tts WHERE tts.FAUDITED = 1 AND tts.FAUDITDATE < :curdate",
				p);

		// 需求
		this.ExecBySql(
				" INSERT INTO t_ord_firstproductdemand_h SELECT * FROM t_ord_firstproductdemand  WHERE fstate IN('关闭','已完成') AND fcreatetime <:curdate",
				p);
		this.ExecBySql(
				" DELETE FROM t_ord_firstproductdemand WHERE fstate IN('关闭','已完成') AND fcreatetime < :curdate",
				p);
		this.ExecBySql(
				" INSERT INTO t_ord_firstproductdemand_h SELECT  * FROM  t_ord_firstproductdemand f WHERE f.fisdemandpackage = 1 AND f.fstate IN('已设计','确认方案') AND EXISTS( SELECT  s.ffirstproductid FROM  t_ord_schemedesign s WHERE s.ffirstproductid = f.fid GROUP BY s.ffirstproductid  HAVING MAX(IFNULL(fconfirmed,0))=1 AND MIN(fconfirmtime)<:curdate) ",
				p);
		this.ExecBySql(
				" DELETE f FROM t_ord_firstproductdemand f WHERE f.fisdemandpackage = 1 AND f.fstate IN('已设计','确认方案') AND EXISTS( SELECT  s.ffirstproductid FROM  t_ord_schemedesign s WHERE s.ffirstproductid = f.fid GROUP BY s.ffirstproductid  HAVING MAX(IFNULL(fconfirmed,0))=1 AND MIN(fconfirmtime)<:curdate) ",
				p);

		// t_ord_schemedesign 方案设计表
		this.ExecBySql(" INSERT INTO t_ord_schemedesign_h SELECT * FROM t_ord_schemedesign s WHERE NOT EXISTS (SELECT 1 FROM t_ord_firstproductdemand d WHERE d.fid=s.ffirstproductid ) ");
		this.ExecBySql(" DELETE s FROM t_ord_schemedesign s WHERE NOT EXISTS (SELECT 1 FROM t_ord_firstproductdemand d WHERE d.fid=s.ffirstproductid ) ");

		// t_ord_schemedesignentry 分录表
		this.ExecBySql(" INSERT INTO t_ord_schemedesignentry_h SELECT * FROM t_ord_schemedesignentry s WHERE NOT EXISTS (SELECT 1 FROM t_ord_schemedesign d WHERE d.fid=s.fparentid ) ");
		this.ExecBySql(" DELETE s FROM t_ord_schemedesignentry s WHERE NOT EXISTS (SELECT 1 FROM t_ord_schemedesign d WHERE d.fid=s.fparentid ) ");

		// 要货申请
		this.ExecBySql(
				" INSERT INTO t_ord_cusdelivers_h SELECT   *  FROM t_ord_cusdelivers WHERE FISREAD = 1 AND IFNULL(fcreatetime, freqdate) < :curdate	",
				p);
		this.ExecBySql(
				" DELETE   FROM t_ord_cusdelivers WHERE FISREAD = 1   AND IFNULL(fcreatetime, freqdate) < :curdate	",
				p);
		this.ExecBySql(
				" INSERT INTO t_ord_deliverapply_h SELECT   tod.*  FROM t_ord_deliverapply tod WHERE tod.fstate IN (5, 6, 8) AND IFNULL(tod.ftraitid, '')=''  AND tod.fcreatetime < :curdate	",
				p);
		this.ExecBySql(
				" DELETE tod    FROM t_ord_deliverapply tod WHERE tod.fstate IN (5, 6, 8)  AND IFNULL(tod.ftraitid, '')=''  AND tod.fcreatetime < :curdate	",
				p);
		this.ExecBySql(" INSERT INTO t_ord_deliverapply_h SELECT   tod.*  FROM t_ord_deliverapply tod   LEFT JOIN t_ord_schemedesignentry tos   ON tos.fid = tod.ftraitid WHERE IFNULL(tod.ftraitid, '')<>'' AND tos.fid IS NULL	");
		this.ExecBySql(" DELETE tod    FROM t_ord_deliverapply tod     LEFT JOIN t_ord_schemedesignentry tos   ON tos.fid = tod.ftraitid WHERE IFNULL(tod.ftraitid, '')<>'' AND tos.fid IS NULL	");
		this.ExecBySql(" INSERT INTO t_ord_deliverratio_h SELECT   tod.* FROM t_ord_deliverratio tod   LEFT JOIN t_ord_deliverapply tod1     ON tod.fdeliverappid = tod1.fid WHERE tod1.fid IS NULL	");
		this.ExecBySql(" DELETE tod   FROM t_ord_deliverratio tod     LEFT JOIN t_ord_deliverapply tod1       ON tod.fdeliverappid = tod1.fid WHERE tod1.fid IS NULL	");
		this.ExecBySql(" INSERT INTO t_ord_delivers_h SELECT   tod.* FROM t_ord_delivers tod   LEFT JOIN t_ord_deliverratio tod1     ON tod.fid = tod1.FDELIVERID WHERE tod1.fid IS NULL	");
		this.ExecBySql(" DELETE tod   FROM t_ord_delivers tod     LEFT JOIN t_ord_deliverratio tod1       ON tod.fid = tod1.FDELIVERID WHERE tod1.fid IS NULL	");

		// 纸板数据
		this.ExecBySql(" INSERT INTO t_ord_deliverorder_h  SELECT o.* FROM t_ord_deliverorder  o WHERE NOT EXISTS( SELECT  1 FROM t_ord_deliverapply s  WHERE  o.fdeliversID=s.fid ) AND fboxtype=1");
		this.ExecBySql(" DELETE o FROM t_ord_deliverorder o WHERE NOT EXISTS( SELECT  1 FROM t_ord_deliverapply s  WHERE  o.fdeliversID=s.fid ) AND fboxtype=1");
		this.ExecBySql(" INSERT INTO t_ord_deliverorder_h SELECT o.* FROM t_ord_deliverorder  o WHERE NOT EXISTS( SELECT  1 FROM t_ord_delivers s  WHERE  o.fdeliversID=s.fid ) AND fboxtype=0");
		this.ExecBySql(" DELETE o FROM t_ord_deliverorder o  WHERE NOT EXISTS( SELECT  1 FROM t_ord_delivers s  WHERE  o.fdeliversID=s.fid ) AND fboxtype=0");

		// 占用表数据
		this.ExecBySql(" INSERT INTO t_inv_usedstorebalance_h  SELECT u.* FROM t_inv_usedstorebalance u WHERE NOT EXISTS( SELECT 1 FROM  t_ord_deliverorder s  WHERE u.fdeliverorderid=s.fid )");
		this.ExecBySql(" DELETE u FROM t_inv_usedstorebalance u WHERE NOT EXISTS( SELECT 1 FROM  t_ord_deliverorder s  WHERE u.fdeliverorderid=s.fid )");

		// 纸板制造商订单数据
		this.ExecBySql(" INSERT INTO t_ord_productplan_h  SELECT * FROM t_ord_productplan where fboxtype = 1 and   not exists  (select 1 from t_ord_deliverapply d where fdeliverapplyid=d.fid) ");
		this.ExecBySql(" DELETE FROM t_ord_productplan where fboxtype = 1 and  not exists  (select 1 from t_ord_deliverapply d where fdeliverapplyid=d.fid) ");

		// 纸箱生产订单数据
		this.ExecBySql(
				" INSERT INTO t_ord_saleorder_h     SELECT * FROM t_ord_saleorder ss WHERE ss.fcreatetime <  :curdate and  ss.forderid in ( select distinct b.fsaleorderid from t_inv_storebalance b join  (select forderid from t_ord_saleorder where fcreatetime <:curdate and not exists (select 1 from t_ord_delivers ds where ds.fsaleorderid=forderid) ) s on b.fsaleorderid=s.forderid  where b.fsaleorderid not in (select distinct sb.fsaleorderid from t_inv_storebalance sb join t_inv_usedstorebalance ub on ub.fstorebalanceid=sb.fid where fsaleorderid is not null) group by b.fsaleorderid having max(ifnull(b.fbalanceqty,0)) = 0  ) ",
				p);

		// 纸箱制造商订单库存数据
		this.ExecBySql(
				" DELETE ss FROM t_ord_saleorder ss  WHERE ss.fcreatetime < :curdate and  ss.forderid in  ( select distinct b.fsaleorderid from t_inv_storebalance b join  (select forderid from t_ord_saleorder where fcreatetime < :curdate and not exists (select 1 from t_ord_delivers ds where ds.fsaleorderid=forderid) ) s on b.fsaleorderid=s.forderid  where b.fsaleorderid not in (select distinct sb.fsaleorderid from t_inv_storebalance sb join t_inv_usedstorebalance ub on ub.fstorebalanceid=sb.fid where fsaleorderid is not null) group by b.fsaleorderid having max(ifnull(b.fbalanceqty,0)) = 0  )",
				p);

		// 纸箱制造商订单数据
		this.ExecBySql(" INSERT INTO t_ord_productplan_h  SELECT * FROM t_ord_productplan pp WHERE pp.fboxtype = 0 and not exists   (select 1 from t_ord_saleorder s where s.forderid=pp.forderid ) ");
		this.ExecBySql(" DELETE p FROM t_ord_productplan p where p.fboxtype = 0 and not exists  (select 1 from t_ord_saleorder s where s.forderid=p.forderid )");

		// 制造商订单库存数据
		this.ExecBySql(" INSERT INTO t_inv_storebalance_h     SELECT * FROM t_inv_storebalance b  WHERE ifnull(b.ftraitid,'')<>'' and not exists (select 1 from t_ord_schemedesignentry p where p.fid=b.ftraitid) ");
		this.ExecBySql(" DELETE FROM t_inv_storebalance   WHERE ifnull(ftraitid,'')<>'' and not exists (select 1 from t_ord_schemedesignentry p where p.fid=ftraitid) ");
		this.ExecBySql(" INSERT INTO t_inv_storebalance_h     SELECT * FROM t_inv_storebalance b  WHERE b.ftype=1 and not exists (select 1 from t_ord_productplan p where p.fid=b.fproductplanid) ");
		this.ExecBySql(" DELETE FROM t_inv_storebalance where  ftype=1 and not exists (select 1 from t_ord_productplan p where p.fid=fproductplanid) ");

		// 出库记录
		this.ExecBySql(" INSERT INTO t_inv_outwarehouse_h SELECT * FROM t_inv_outwarehouse  o WHERE NOT EXISTS( SELECT  1 FROM t_ord_productplan s  WHERE  s.fid=o.fproductplanid ) AND IFNULL(o.fproductplanid,'')<>''");
		this.ExecBySql(" DELETE o FROM t_inv_outwarehouse o WHERE NOT EXISTS( SELECT  1 FROM t_ord_productplan s  WHERE  s.fid=o.fproductplanid ) AND IFNULL(o.fproductplanid,'')<>''");

		// 入库数据
		this.ExecBySql(" INSERT INTO   t_inv_productindetail_h SELECT * FROM t_inv_productindetail  o WHERE NOT EXISTS( SELECT  1 FROM t_ord_productplan s  WHERE  s.fid=o.fproductplanid ) AND IFNULL(o.fproductplanid,'')<>''");
		this.ExecBySql(" DELETE o FROM t_inv_productindetail o WHERE NOT EXISTS( SELECT  1 FROM t_ord_productplan s  WHERE  s.fid=o.fproductplanid ) AND IFNULL(o.fproductplanid,'')<>''");

		// System.out.println(new Date().getTime() - start.getTime());

		// 视图历史数据生成
		if (f.format(start).equals("2016-03-23")) {
			// start = new Date();
			this.ExecBySql("TRUNCATE TABLE t_ord_deliverorder_mv");
			this.ExecBySql("INSERT INTO t_ord_deliverorder_mv  SELECT d.*, IF(d.fboxtype = 1, CONCAT(pdef.fname, ' ', '/', pdef.flayer, pdef.FTILEMODELID), pdef.fname) _productname, IF(d.fboxtype = 0, cpdt.fname, CONCAT(pdef.fname, ' ', '/', pdef.flayer, pdef.FTILEMODELID)) _custpdtname, c.fname _custname, IFNULL(s.fname, '') _suppliername, IFNULL(st.fbalanceqty, 0) _balanceqty, IFNULL(pd.fcharacter, '') _character, IFNULL(u1.fname, '') _creator, IFNULL(u2.fname, '') _lastupdater, IFNULL(u3.fname, '') _orderman, IFNULL(u4.fname, '') _auditor, IF(d.fboxtype = 1, '', pdef.fnumber) _productnumber, IF(d.fboxtype = 1, CONCAT_WS('x', dp.fboxlength, dp.fboxwidth, dp.fboxheight), CONCAT_WS('x', pdef.fboxlength, pdef.fboxwidth, pdef.fboxheight)) _spec, CONCAT_WS('x', dp.fmateriallength, dp.fmaterialwidth) _mspec, cpdt.fspec _custpdtspec  FROM t_ord_deliverorder d LEFT JOIN t_bd_custproduct cpdt   ON cpdt.fid = d.fcusproductid LEFT JOIN t_sys_user u1   ON u1.fid = d.fcreatorid LEFT JOIN t_sys_user u2   ON u2.fid = d.fupdateuserid LEFT JOIN t_sys_user u3   ON u3.fid = d.fordermanid LEFT JOIN t_sys_user u4   ON u4.fid = d.FAUDITORID LEFT JOIN t_bd_customer c   ON c.fid = d.fcustomerid LEFT JOIN t_sys_supplier s   ON s.fid = d.FsupplierID LEFT JOIN t_pdt_productdef pdef   ON pdef.fid = d.fproductid LEFT JOIN t_ord_deliverapply dp   ON d.fdeliversID = dp.fid LEFT JOIN t_inv_usedstorebalance ust   ON ust.fdeliverorderid = d.fid LEFT JOIN t_inv_storebalance st   ON st.fid = ust.fstorebalanceid LEFT JOIN t_ord_schemedesignentry pd   ON pd.fid = d.ftraitid");
			this.ExecBySql("INSERT INTO t_ord_deliverorder_mv_h SELECT d.*,  IF(d.fboxtype=1,CONCAT(pdef.fname,' ','/',pdef.flayer,pdef.ftilemodelid),pdef.fname) _productname,  IF(d.fboxtype = 0,cpdt.fname,CONCAT(pdef.fname,' ','/',pdef.flayer,pdef.ftilemodelid)) _custpdtname,  c.fname _custname,  IFNULL(s.fname, '') _suppliername,  0 _balanceqty,  IFNULL(pd.fcharacter, '') _character,  IFNULL(u1.fname,'') _creator,  IFNULL(u2.fname,'') _lastupdater,  IFNULL(u3.fname,'') _orderman,  IFNULL(u4.fname,'') _auditor,  IF(d.fboxtype=1,'',pdef.fnumber) _productnumber,  IF(d.fboxtype=1,CONCAT_WS('x',dp.FBOXLENGTH,dp.FBOXWIDTH,dp.FBOXHEIGHT),CONCAT_WS('x',pdef.FBOXLENGTH,pdef.FBOXWIDTH,pdef.FBOXHEIGHT)) _spec,  CONCAT_WS('x',dp.fmateriallength,dp.fmaterialwidth) _mspec,cpdt.fspec _custpdtspec FROM   t_ord_deliverorder_h d    LEFT JOIN t_bd_custproduct cpdt   ON cpdt.fid = d.fcusproductid    LEFT JOIN t_sys_user u1   ON u1.fid = d.fcreatorid    LEFT JOIN t_sys_user u2   ON u2.fid = d.fupdateuserid    LEFT JOIN t_sys_user u3   ON u3.fid = d.fordermanid    LEFT JOIN t_sys_user u4   ON u4.fid = d.fauditorid    LEFT JOIN t_bd_customer c   ON c.fid = d.fcustomerid    LEFT JOIN t_sys_supplier s   ON s.fid = d.fsupplierid    LEFT JOIN t_pdt_productdef pdef   ON pdef.fid = d.fproductid    LEFT JOIN t_ord_deliverapply_h dp   ON d.fdeliversID = dp.fid   LEFT JOIN t_ord_schemedesignentry_h pd   ON pd.fid = d.ftraitid ");
			this.ExecBySql("INSERT INTO t_ord_deliverapply_card_mv_h  SELECT  d.*, s.fname _suppliername, c.fname _custname, cpdt.fname _custpdtname, cpdt.fnumber _custpdtnumber, cpdt.forderunit _orderunit, cpdt.fspec _spec, u1.fname _creator, u2.fname _lastupdater, pd.fcharacter _character  FROM t_ord_deliverapply_h d LEFT JOIN t_sys_user u1   ON u1.fid = d.fcreatorid LEFT JOIN t_sys_user u2   ON u2.fid = d.fupdateuserid LEFT JOIN t_bd_customer c   ON c.fid = d.fcustomerid LEFT JOIN t_bd_custproduct cpdt   ON cpdt.fid = d.fcusproductid LEFT JOIN t_bd_userrelationcustp p   ON p.fcustproductid = d.fcusproductid LEFT JOIN t_ord_schemedesignentry_h pd   ON pd.fid = d.ftraitid LEFT JOIN t_sys_supplier s   ON d.FsupplierID = s.fid  WHERE  d.fboxtype=0");
			this.ExecBySql("INSERT INTO t_ord_deliverapply_board_mv_h  SELECT d.*, pp.fstockinqty _stockinqty, s.fname _suppliername, c.fname _custname, u1.fname _creator, u2.fname _recipient, CONCAT(f.fname, ' ', '/', f.flayer, f.FTILEMODELID) _materialname  FROM t_ord_deliverapply_h d LEFT JOIN t_ord_productplan_h pp   ON d.fplanid = pp.fid LEFT JOIN t_sys_user u1   ON u1.fid = d.fcreatorid LEFT JOIN t_sys_user u2   ON u2.fid = d.frecipient LEFT JOIN t_bd_customer c   ON c.fid = d.fcustomerid LEFT JOIN t_pdt_productdef f   ON f.fid = d.fmaterialfid LEFT JOIN t_sys_supplier s   ON d.FsupplierID = s.fid  WHERE d.fboxtype = 1");

			// System.out.println(new Date().getTime() - start.getTime());
		}
	}

	/**
	 * 平台流量报表
	 */
	@Override
	public void ExecUpdateLoginData() {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowtime = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(nowtime);
		c.add(Calendar.DATE, -1);
		params p = new params();
		p.put("starttime", f.format(c.getTime()));
		p.put("nowtime",f.format(nowtime));
		// TODO Auto-generated method stub
		this.ExecBySql("TRUNCATE TABLE t_sys_loginrecord");
		this.ExecBySql("INSERT INTO t_sys_loginrecord (SELECT  fuserid,MAX(flogintime) fflatestlogin,CONVERT(MIN(flogintime),DATE) ffirstlogin,COUNT(1) fcount FROM t_sys_userdiary where IFNULL(fuserid,'')<>'' GROUP BY fuserid )");
		this.ExecBySql("INSERT INTO t_sys_everydaypdt  SELECT t.fcustomerid,t.fcreatetime,0 pdttype  FROM t_ord_deliverapply_board_mv t WHERE t.fcreatetime BETWEEN :starttime AND :nowtime GROUP BY t.fcustomerid,LEFT(t.fcreatetime, 10)",p);	
		this.ExecBySql("INSERT INTO t_sys_everydaypdt  SELECT d.fcustomerid,d.fcreatetime,1 pdttype    FROM t_ord_firstproductdemand d  JOIN t_bd_customer cust ON d.fcustomerid = cust.fid WHERE IFNULL(d.fisdemandpackage,0) = 0 AND d.fcreatetime BETWEEN :starttime AND :nowtime    GROUP BY d.fcustomerid,LEFT(d.fcreatetime, 10)",p);	
		this.ExecBySql("INSERT INTO t_sys_everydaypdt  SELECT uc.FCUSTOMERID fcustomerid,ftu.fcreatetime,2 pdttype    FROM t_ftu_saledeliver ftu JOIN t_sys_user u ON u.fid = ftu.fcreator JOIN t_bd_usercustomer uc ON uc.fuserid = u.fid JOIN t_bd_customer cust ON cust.fid = uc.FCUSTOMERID WHERE ftu.fcreatetime BETWEEN :starttime AND :nowtime    GROUP BY uc.FCUSTOMERID,LEFT(ftu.fcreatetime, 10)",p);	
		this.ExecBySql("INSERT INTO t_sys_everydaypdt  SELECT d.fcustomerid, d.fcreatetime,3 pdttype FROM t_ord_firstproductdemand d  JOIN t_bd_customer cust ON d.fcustomerid = cust.fid WHERE d.fisdemandpackage = 1 AND d.fcreatetime BETWEEN :starttime AND :nowtime    GROUP BY d.fcustomerid,LEFT(d.fcreatetime,10)",p);	
		this.ExecBySql("INSERT INTO t_sys_everydaypdt  SELECT t.fcustomerid,t.fcreatetime,3 pdttype  FROM t_ord_deliverapply_card_mv t     WHERE t.fcreatetime BETWEEN :starttime AND :nowtime    GROUP BY t.fcustomerid,LEFT(t.fcreatetime,10)",p);
	}
}
