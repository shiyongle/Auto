package Com.Dao.order;

import java.io.File;
import java.nio.file.LinkPermission;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.hibernate.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.JPushUtil;
import Com.Base.Util.SpringContextUtils;
import Com.Dao.System.IProductreqallocationrulesDao;
import Com.Dao.System.ISimplemessageDao;
import Com.Dao.System.ISysUserDao;
import Com.Entity.System.SysUser;
import Com.Entity.order.Firstproductdemand;
import Com.Entity.order.Productdemandfile;
import Com.Entity.order.Productstructure;
import Com.Entity.order.SchemeDesignEntry;
import Com.Entity.order.Schemedesign;
@Service("FirstproductdemandDao")
public class FirstproductdemandDao extends BaseDao implements IFirstproductdemandDao{
	
	@Resource
	private IProductreqallocationrulesDao productreqallocationrulesDao;
	@Resource
	private ISimplemessageDao SimplemessageDao;
	@Resource
	private ISysUserDao sysUserDao;	
	@Override
	public void saveProductFile(String fparentid, List<FileItem> list,String fpath) {
		String fid = null;
		String fname = null;
		Productdemandfile obj ;
		File file = null;
		String sql = null;
		for(FileItem item : list){
			if(!item.isFormField()){
				if(item.getSize()>10 * 1024 * 1024){
					throw new DJException("您上传的文件太大，请选择不超过10M的文件");
				}
				file = new File(fpath);
				if(!file.exists()){
					file.mkdirs();
				}
				fname = item.getName();//item.getName();
				fid = this.CreateUUid();
				fpath = fpath+"/" + fid+fname.substring(fname.lastIndexOf("."));
				try {
					item.write(new File(fpath));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
//				sql = "select fid,ifnull(fcharacter,'') fcharacter,ifnull(fdescription,'') fdescription,ifnull(ftype,'') ftype from t_ord_productdemandfile where fid = '"+state+"'";
//				List<HashMap<String, Object>> list1 = this.QueryBySql(sql);
//				if(list1.size()>0){
//					obj.setFcharacter(list1.get(0).get("fcharacter").toString());
//					obj.setFdescription(list1.get(0).get("fdescription").toString());
//					obj.setFtype(list1.get(0).get("ftype").toString());
//					sql = "select fpath from t_ord_productdemandfile where fparentid = '"+fparentid+"'";
//					List<HashMap<String, Object>> result = this.QueryBySql(sql);
//					if(result.size()>0){	//删除旧的附件
//						file = new File(result.get(0).get("fpath").toString());
//						if(file.exists()){
//							file.delete();
//						}
////						sql = "delete from t_ord_productdemandfile where fparentid = '"+fparentid+"'";
////						this.ExecBySql(sql);
//					}
//				}else{
//					
//				}
				fname = fname.substring(item.getName().lastIndexOf("\\")+1);
				obj = new Productdemandfile();
				obj.setFid(fid);
				obj.setFname(fname);
				obj.setFpath(fpath);
				obj.setFparentid(fparentid);
				this.saveOrUpdate(obj);
			}
		}
		
	}

	@Override
	public void ExecAuditFproductdemand(Firstproductdemand finfo,String userid) {
		// TODO Auto-generated method stub
		finfo.setFstate("已发布");
		finfo.setIsfauditor(true);
		finfo.setFauditortime(new Date());
		finfo.setFauditorid(userid);
		if(StringHelper.isEmpty(finfo.getFsupplierid())){
			String sql="select fid,fsupplierid from t_pdt_productreqallocationrules where fcustomerid='"+finfo.getFcustomerid()+"'";
			List<HashMap<String, Object>> rlist=this.QueryBySql(sql);
			if(rlist.size()==1)
			{
				String fsupplierid=rlist.get(0).get("fsupplierid")==null?"":rlist.get(0).get("fsupplierid").toString();
				if(!"".equals(fsupplierid))
				{
					
					finfo.setFalloted(true);
					finfo.setFallotor(userid);
					finfo.setFallottime(new Date());
					finfo.setFsupplierid(fsupplierid);
					finfo.setFstate("已分配");
				}
			}
		}else{
			finfo.setFalloted(true);
			finfo.setFallotor(userid);
			finfo.setFallottime(new Date());
			finfo.setFsupplierid(finfo.getFsupplierid());
			finfo.setFstate("已分配");
		}
		
		
		this.saveOrUpdate(finfo);
		//2015-08-29 方案新增推送设计师
		if(finfo.getFstate().equals("已分配")){
			this.ExecNewproductdemand();	
		}
		//2015-08-29 方案新增推送设计师
		updateUserConcat(userid,finfo.getFlinkphone());
		if(!StringHelper.isEmpty(finfo.getFsupplierid())&&"39gW7X9mRcWoSwsNJhU12TfGffw=".equals(finfo.getFsupplierid()))
		{
			String ssql="select '"+finfo.getFname()+"' fname,fname cname  from t_bd_customer where fid='"+finfo.getFcustomerid()+"'";
			productreqallocationrulesDao.ExecCreateMessageInfo(ssql);
		}
	}
	/**
	 * 更改当前用户的联系方式
	 * @param flinkphone
	 */
	@Override
	public void updateUserConcat(String userid,String flinkphone) {
		SysUser user = (SysUser) this.Query(SysUser.class, userid);
		int length = flinkphone.length();
		if(length == 11){
			if(!flinkphone.equals(user.getFtel())){
				user.setFtel(flinkphone);
			}
		}else if(!flinkphone.equals(user.getFphone())){
			user.setFphone(flinkphone);
		}
		this.saveOrUpdate(user);
	}

	/**
	 * 查询需求是否关闭
	 * @param fid(需求ID)
	 */
	@Override
	public  boolean closeProductDemand(String fid,IBaseDao b){
		boolean fstate = true;

		String sql = "select 1 from t_ord_firstproductdemand where fid ='" + fid
				+ "' and fstate ='关闭'";
		List list = b.QueryBySql(sql);
		if (list.size() > 0) {
			fstate = true;
		} else {
			fstate = false;
		}
		return fstate;

	}
	
	//2015-08-25 推送新需求
	public void ExecNewproductdemand()  {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("YYYY-MM-dd HH:mm");
		String time=format.format(date);
		int countpd = 0;
		List<HashMap<String, Object>> slist = new ArrayList();
		StringBuffer sb = new StringBuffer();
		String sql = "SELECT c.fid, c.fname,COUNT(c.fid) pcount FROM t_ord_firstproductdemand f JOIN t_bd_customer c ON c.fid = f.fcustomerid where f.fstate = '已分配'  and f.`fsupplierid` = '39gW7X9mRcWoSwsNJhU12TfGffw='  GROUP BY c.fid,c.FNAME";
		slist = this.QueryBySql(sql);
		for (int i = 0; i < slist.size(); i++) {
			countpd += Integer.parseInt(slist.get(i).get("pcount").toString());
			sb = sb.append(slist.get(i).get("fname").toString()+ slist.get(i).get("pcount").toString() + "个;");
		}
		if (countpd > 0) {
			sb.insert(0, time+":你有" + slist.size() + "个客户，" + countpd + "个新需求;");
			String hql = "  FROM SysUser WHERE ftype = 3";
			List<SysUser> sList = sysUserDao.QuerySysUsercls(hql);
			for(int i=0;i<sList.size();i++){
				SysUser user = sList.get(i);
				HashMap<String, String> messagemap = new HashMap<String, String>();
				messagemap.put("fcontent", sb.toString());
				messagemap.put("frecipientid", user.getFid());
				messagemap.put("ftype", "3");
				SimplemessageDao.MessageProjectEvaluationWithSender(messagemap);
				JPushUtil.SendPushToAll(user.getFname(),sb.toString());//极光推送消息
				JPushUtil.SendPushToIOS(user.getFname(),sb.toString());
			}
		}
	}
	//2015-08-25 推送已接收未设计需求（只推送给接受人）
	public void ExecRcvproductdemand() {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("YYYY-MM-dd HH:mm");
		String time=format.format(date);
		int countpd = 0;
		List<HashMap<String, Object>> slist1 = new ArrayList();
		List<HashMap<String, Object>> slist2 = new ArrayList();
		StringBuffer sb = new StringBuffer();
//		sql2+= " f.fcreatetime > CURRENT_DATE GROUP BY u.fid,u.FNAME,c.FNAME ";
		String sql1 = "SELECT u.fid,u.fname,count(f.fid) pcount FROM t_ord_firstproductdemand f JOIN t_bd_customer c ON c.fid = f.fcustomerid "; 
		 sql1 += " JOIN t_sys_user u ON u.fid = f.freceiver WHERE f.fstate = '已接收'  and f.`fsupplierid` = '39gW7X9mRcWoSwsNJhU12TfGffw=' GROUP BY u.fid,u.FNAME ";
		String sql2=" SELECT u.fid,u.fname uname,c.fname cname,COUNT(c.fid) pcount,GROUP_CONCAT(f.fname) ffname FROM t_ord_firstproductdemand f ";
		sql2+=" JOIN t_bd_customer c ON c.fid = f.fcustomerid JOIN t_sys_user u ON u.fid = f.freceiver WHERE  f.fstate = '已接收'  and f.`fsupplierid` = '39gW7X9mRcWoSwsNJhU12TfGffw=' GROUP BY u.fid,u.FNAME,c.FNAME ";
		slist1 = this.QueryBySql(sql1);
		slist2 = this.QueryBySql(sql2);		
		for(int j=0;j<slist1.size();j++){
			countpd  = Integer.parseInt(slist1.get(j).get("pcount").toString());
			int index = 0;
			sb.setLength(0);
			for (int i = 0; i < slist2.size(); i++) {
				if(slist1.get(j).get("fid").toString().equals(slist2.get(i).get("fid").toString())){
					++ index;
					sb = sb.append(slist2.get(i).get("cname").toString()+":"+ slist2.get(i).get("ffname").toString() + ";");
				}
			}
			sb.insert(0,  time+":你有"+ index+ "个客户，" + countpd + "个未设计！");
			HashMap<String, String> messagemap = new HashMap<String, String>();
			messagemap.put("fcontent", sb.toString());
			messagemap.put("frecipientid",slist1.get(j).get("fid").toString());
			messagemap.put("ftype", "3");
			SimplemessageDao.MessageProjectEvaluationWithSender(messagemap);
			JPushUtil.SendPushToAll((String)slist1.get(j).get("fname"),sb.toString());//极光推送消息
			JPushUtil.SendPushToIOS((String)slist1.get(j).get("fname"),sb.toString());
		}
	}
	
	//2015-08-28 推送已接收未设计需求（只推送给接受人）
	public void ExecUnconfirmscheme() {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("YYYY-MM-dd HH:mm");
		String time=format.format(date);
		int countpd = 0;
		List<HashMap<String, Object>> slist1 = new ArrayList();
		List<HashMap<String, Object>> slist2 = new ArrayList();
		StringBuffer sb = new StringBuffer();									
//		sql2+= " JOIN t_sys_user u ON u.fid = s.`fcreatorid` where s.`fconfirmed` = 0 GROUP BY u.`FID`,u.`FNAME`,c.`FNAME` ";
	    String sql1 = "SELECT u.fid,u.fname,COUNT(s.fid) scount FROM t_ord_firstproductdemand f JOIN t_ord_schemedesign s ON s.`ffirstproductid` = f.`fid` "; 
	    sql1 += " JOIN t_bd_customer c ON c.fid = s.`fcustomerid`   ";
		sql1 += " JOIN t_sys_user u ON u.fid = s.`fcreatorid` where s.`fconfirmed` = 0 and f.`fsupplierid` = '39gW7X9mRcWoSwsNJhU12TfGffw=' GROUP BY u.`FID`,u.`FNAME`";
		String sql2=" SELECT u.fid,u.fname uname,c.`FNAME` cname,COUNT(s.`fid`) scount,GROUP_CONCAT(s.`fname`) sname FROM t_ord_firstproductdemand f ";
		sql2+="  JOIN t_ord_schemedesign s ON s.`ffirstproductid` = f.`fid` JOIN t_bd_customer c ON c.fid = s.`fcustomerid`  ";
		sql2+= " JOIN t_sys_user u ON u.fid = s.`fcreatorid` where s.`fconfirmed` = 0 and f.`fsupplierid` = '39gW7X9mRcWoSwsNJhU12TfGffw=' GROUP BY u.`FID`,u.`FNAME`,c.`FNAME` ";
		slist1 = this.QueryBySql(sql1);
		slist2 = this.QueryBySql(sql2);		
		for(int j=0;j<slist1.size();j++){
			countpd  = Integer.parseInt(slist1.get(j).get("scount").toString());
			int index = 0;
			sb.setLength(0);
			for (int i = 0; i < slist2.size(); i++) {
				if(slist1.get(j).get("fid").toString().equals(slist2.get(i).get("fid").toString())){
					++ index;
					sb = sb.append(slist2.get(i).get("cname").toString()+":"+ slist2.get(i).get("sname").toString() + ";");
				}
			}
			sb.insert(0,  time+":你有" + index+ "个客户，" + countpd + "个未确认方案！");
			HashMap<String, String> messagemap = new HashMap<String, String>();
			messagemap.put("fcontent", sb.toString());
			messagemap.put("frecipientid",slist1.get(j).get("fid").toString());
			messagemap.put("ftype", "3");
			SimplemessageDao.MessageProjectEvaluationWithSender(messagemap);
			JPushUtil.SendPushToAll((String)slist1.get(j).get("fname"),sb.toString());//极光推送消息
			JPushUtil.SendPushToIOS((String)slist1.get(j).get("fname"),sb.toString());
		}
	}

	@Override
	public void SaveFirstproduct(Firstproductdemand f,List<Productstructure> productList) {//,List<Productdef> productList

		 IProductstructureDao productstructureDao=(IProductstructureDao) SpringContextUtils.getBean("ProductstructureDao");
		 productstructureDao.SaveOrUpdateProductstructure(productList,f.getFid());//保存产品
	}
}
