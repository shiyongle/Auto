package Com.Dao.order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.UserCustomer;
import Com.Entity.System.Useronline;
import Com.Entity.order.Cusdelivers;
@Service("CusDeliversDao")
public class CusDeliversDao extends BaseDao implements ICusDeliversDao {
//	private static boolean isImporting = false; 
//	private static boolean isExporting = false; 
//	private static boolean isCusdeliverExporting = false; 

	@Override
	public synchronized void ExecImportZTcusdelivers() throws Exception {
		// TODO Auto-generated method stub
//		if(!isImporting)
//		{
//			isImporting=true;
			Connection conn = null;
			PreparedStatement stmt = null;
			 ResultSet rs=null; //数据库查询结果集 
			try {
				//外网通时使用此代码start
				//conn = DBHelper.getConnection("192.168.2.104", "eas_521_dj", "sa", "djdb");   //此方法需要外网
				//外网通时使用此代码end	
				
				
				//外网不通时使用此代码start
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String connectionUrl = "jdbc:sqlserver://10.10.253.123;database=VMIDB;user=DongjingUser;password=Dong@jing#User";
				conn = DriverManager.getConnection(connectionUrl);
				//外网不通时使用此代码end
				 StringBuffer sql=new StringBuffer("");
				 sql.append(" select t.*,werk.NAME fwerkname from dbo.PM_ORDERINFO t inner join T_ORD_CUSDELIVER t1 on t.pkid=t1.ref_id left join VM_WERKLIFNR_VMI werk on werk.CODE=t.WERKS where t1.isread=0");
				 stmt=conn.prepareStatement(sql.toString());
				 String pkids="";
				 String execsql="";
				 rs=stmt.executeQuery();
				 while(rs.next())
				 {
					 execsql="INSERT INTO t_ord_cusdelivers (fid,fnumber,farrivetime,flinkman,famount,fdescription,fisread,fcusfid,fmaktx,freqaddress,freqdate,fwerkname,fcustomerid) VALUES (";
					 execsql=execsql+"'"+this.CreateUUid()+"',";
					 execsql=execsql+"'"+rs.getString("BILL_NO")+"-"+rs.getString("BILL_ITEM")+"',";
//					 execsql=execsql+"'"+rs.getString("reqdate")+"',";
					 execsql=execsql+"'"+rs.getString("reqdate").substring(0,11)+"09:00:00.0"+"',";
					 execsql=execsql+"'"+rs.getString("creator")+"',";
					 execsql=execsql+""+rs.getBigDecimal("qty").intValue()+",";
					 execsql=execsql+"'"+rs.getString("remark")+"',";
					 execsql=execsql+"0,";
					 execsql=execsql+"'"+rs.getString("pkid")+"',";
					 execsql=execsql+"'"+rs.getString("maktx")+"',";
					 execsql=execsql+"'"+rs.getString("REQADDRESS")+"',";
					 execsql=execsql+"'"+rs.getString("reqdate")+"',";
					 execsql=execsql+"'"+rs.getString("fwerkname")+"',";
					 execsql=execsql+"'KNG83wEeEADgDmm4wKgCZ78MBA4='";
					 execsql=execsql+")";
					 this.ExecBySql(execsql);
					 if(pkids.length()>0)
					 {
						 pkids=pkids+","+rs.getString("pkid");
					 }
					 else
					 {
						 pkids=rs.getString("pkid");
					 }
//					 info=new Cusdelivers();
//					 info.setFaddress(rs.getString("REQADDRESS"));
//					 info.setFamount(rs.getBigDecimal("qty").intValue());
//					 info.setFarrivetime(rs.getTimestamp("reqdate"));
//					 info.setFdescription(rs.getString("remark"));
//					 info.setFlinkman(rs.getString("creator"));
//					 info.setFlinkphone("");
//					 info.setFnumber("");
//					 info.setFmaktx(rs.getString("maktx"));
//					 info.setFreqaddress(rs.getString("REQADDRESS"));
//					 //info.setFlinkphone(flinkphone);
//					 info.setFproductname(rs.getString("maktx"));
//					 info.setFcusfid(rs.getString("pkid"));
//					 info.setFid(this.CreateUUid());
//					 this.saveOrUpdate(info);
				 }
				 execsql="update t_ord_cusdelivers cusd,t_bd_custproduct cpd set cusd.fcusproduct=cpd.fid where cusd.fisread=0 and ifnull(cusd.fcusproduct,'')='' and cusd.fmaktx=cpd.fname ";
				 this.ExecBySql(execsql);
				 execsql="update t_ord_cusdelivers cusd,t_bd_address ad set cusd.faddress=ad.fid where cusd.fisread=0 and ifnull(cusd.faddress,'')='' and cusd.freqaddress=ad.fname ";
				 this.ExecBySql(execsql);
				 if(pkids.length()>0)
				 {
					 sql=new StringBuffer(" update T_ORD_CUSDELIVER set isread=1 where ref_id in ("+pkids+") ");
					 stmt=conn.prepareStatement(sql.toString());
					 stmt.execute();
				 }
//				 isImporting=false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				isImporting=false;
				throw new DJException(e.toString());
			}
			finally {
				try {
					if (stmt != null) {
						stmt.close();
						stmt = null;
					}

					if (conn != null) {
						conn.close();
						conn = null;
					}
				}
				catch (Exception e)
				{
					
				}
//				isImporting=false;
			}
//		}
//		else
//		{
//			throw new DJException("上次任务还未执行完成，请稍后再试......");
//		}
	}

	@Override
	public Cusdelivers Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Cusdelivers.class, fid);
	}

	@Override
	public synchronized void ExecExportZTcusdelivers() throws Exception {
		// TODO Auto-generated method stub
//		if(!isExporting)
//		{
//			isExporting=true;
			Connection conn = null;
			PreparedStatement stmt = null;
			String deliverids="";
			try {
				
				String sql="select * from t_tra_saledeliver where fisexport=0 and faudited=1 AND  fcustomerid='KNG83wEeEADgDmm4wKgCZ78MBA4='";
				List<HashMap<String, Object>> deliverheadcls=this.QueryBySql(sql);
				//外网通时使用此代码start
				//conn = DBHelper.getConnection("192.168.2.104", "eas_521_dj", "sa", "djdb");   //此方法需要外网
				//外网通时使用此代码end	
				
				
				//外网不通时使用此代码start
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String connectionUrl = "jdbc:sqlserver://10.10.253.123;database=VMIDB;user=DongjingUser;password=Dong@jing#User";
				conn = DriverManager.getConnection(connectionUrl);
				conn.setAutoCommit(false);
				//外网不通时使用此代码end
				for(int i=0;i<deliverheadcls.size();i++)
				{
					if(i>0)
					{
						deliverids=deliverids+",";
					}
					deliverids=deliverids+"'"+deliverheadcls.get(i).get("FID")+"'";
					String insertsql="INSERT INTO [VMIDB].[dbo].[T_DELIVER_HEADER] ([BILL_NO],[BILL_STATUS],[WERKS],[DELIVERVEN],[DELIVERAREA],[DELIVERDATE],[FORWARDER],[FORWARDERPHONE],[CARNUMBER],[CREATEDATE],[CREATOR],[ISFINISH],[REMARK]) VALUES (";
					insertsql=insertsql+"'"+deliverheadcls.get(i).get("FNUMBER")+"',";
					insertsql=insertsql+"'正常',";
					insertsql=insertsql+"'工厂',";
					insertsql=insertsql+"'1577B710',"; //发货供方编码
					insertsql=insertsql+"'104国道丽岙段东经路1号',"; //发货地址
					insertsql=insertsql+"'"+deliverheadcls.get(i).get("FBIZDATE").toString()+"',"; //发货时间
					insertsql=insertsql+"'东经',"; //承运人 
					insertsql=insertsql+"'',";   //FTRUCKID   承运人联系方式
					insertsql=insertsql+"'"+(deliverheadcls.get(i).get("FTRUCKID")==null?"":deliverheadcls.get(i).get("FTRUCKID").toString())+"',"; //车牌号
					insertsql=insertsql+"'"+deliverheadcls.get(i).get("FBIZDATE").toString()+"',"; //发货单创建时间
					insertsql=insertsql+"'东经',";  //发货单创建人
					insertsql=insertsql+"0,"; //完成发货
					insertsql=insertsql+"'')";  //备注
					stmt=conn.prepareStatement(insertsql);
					stmt.execute();
					
					
					sql="SELECT SD.FNUMBER billno,sde.fseq,sup.fnumber supnum,sup.fname,sup.faddress,sd.ftruckid,dap.fcusfid,sde.famount / da.fdelivernum * da.fdeliverapplynum as famount FROM t_tra_saledeliverentry sde left join t_tra_saledeliver sd on sde.FPARENTID = sd.fid ";
					sql=sql+"left join t_ord_deliverorder dod on sde.FDELIVERORDERID = dod.fid left join t_ord_deliverratio da on da.fdeliverid = dod.fdeliversID left join t_ord_deliverapply dap on dap.fid = da.fdeliverappid left join t_sys_supplier sup on sup.fid = sd.FSUPPLIER WHERE SD.FID='";
					sql=sql+deliverheadcls.get(i).get("FID")+"'";
					List<HashMap<String, Object>> deliverentrycls=this.QueryBySql(sql);
					for(int entry =0;entry<deliverentrycls.size();entry++)
					{
						if(deliverentrycls.get(entry).get("fcusfid")==null || deliverentrycls.get(entry).get("fcusfid").toString().equals(""))
						{
							continue;
						}
						insertsql="INSERT INTO [VMIDB].[dbo].[T_DELIVER_ITEM] ([BILL_NO],[BILL_ITEM],[ITEM_STATUS],[MATNR],[MAKTX],[QTY],[UNIT],[VENDORID],[PO_NO],[PO_ITEM],[RECEIPTQTY],[ISFINISH],[REMARK],[PO_PKID]) VALUES (";
						insertsql=insertsql+"'"+deliverentrycls.get(entry).get("billno")+"',"; //发货单号
						insertsql=insertsql+"'"+(entry+1)+"',"; //分录
						insertsql=insertsql+"'正常',";  //状态
						insertsql=insertsql+"'',";  //物料编码
						insertsql=insertsql+"'',";  //物料描述
						insertsql=insertsql+"'"+deliverentrycls.get(entry).get("famount")+"',"; //发货数量
						insertsql=insertsql+"'',";  //单位
						insertsql=insertsql+"'"+deliverentrycls.get(entry).get("supnum")+"',"; //供方编码
						insertsql=insertsql+"'',";  //采购单号
						insertsql=insertsql+"0,";  //采购单号分录
						insertsql=insertsql+"0,";  //已经收货数量
						insertsql=insertsql+"0,";  //是否完成
						insertsql=insertsql+"'',";  //备注
						insertsql=insertsql+deliverentrycls.get(entry).get("fcusfid")+")";  //采购单PKID
						stmt=conn.prepareStatement(insertsql);
						stmt.execute();
					}
					
				}
				stmt=conn.prepareStatement("UPDATE T_DELIVER_ITEM  SET MATNR=isnull(PM_ORDERINFO.MATNR,'无编码'),MAKTX=isnull(PM_ORDERINFO.MAKTX,'无名称'),UNIT=isnull(PM_ORDERINFO.UNIT,'无单位'),WERKS=isnull(PM_ORDERINFO.WERKS,'N'),PO_NO=isnull(PM_ORDERINFO.BILL_NO,'无订单'),PO_ITEM=isnull(PM_ORDERINFO.BILL_ITEM,0),ISUPDATE=1  from dbo.T_DELIVER_ITEM left join dbo.PM_ORDERINFO ON T_DELIVER_ITEM.PO_PKID=PM_ORDERINFO.PKID where T_DELIVER_ITEM.ISUPDATE=0");
				stmt.execute();
				if(deliverids.length()>0)
				{
					this.ExecBySql("UPDATE t_tra_saledeliver SET FISEXPORT=1 where fid in ("+deliverids+")");
				}
				
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				throw new DJException(e.getMessage());
				// TODO: handle exception
			}
			finally {
//				isExporting=false;
				try {
					if (stmt != null) {
						stmt.close();
						stmt = null;
					}

					if (conn != null) {
						conn.close();
						conn = null;
					}
				}
				catch (Exception e)
				{
					
				}
			}
//		}
//		else
//		{
//			throw new DJException("上次任务还未执行完成，请稍后再试......");
//		}
	}

	@Override
	public void ExecImpCusDeliversSDK(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Cusdelivers info = (Cusdelivers) request.getAttribute("Cusdelivers");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		if(info==null)
		{
			throw new DJException("Cusdelivers对象不能为空");
		}
		if(info.getFreqdate()==null)
		{
			throw new DJException("交货时间不能为空");
		}
		else
		{
			info.setFarrivetime(info.getFreqdate());
		}
		if(info.getFamount()<=0)
		{
			throw new DJException("数量必须大于0");
		}
		if(info.getFid()!=null && !info.getFid().equals(""))
		{
			return;
		}
		else
		{
			info.setFid(CreateUUid());
		}
		if (info.getFcusfid() != null
				&& !info.getFcusfid().equals("")
				&& QueryExistsBySql(" select fid from t_ord_cusdelivers  where fcusfid='"
						+ info.getFcusfid() + "' ")) {
			throw new DJException("已存在相同fcusfid的记录");
		}
		List<UserCustomer> cuscls=QueryByHql(" from UserCustomer where fuserid='"+userid+"'");
		if(cuscls.size()!=1)
		{
			throw new DJException("客户信息有误");
		}
		info.setFcustomerid(cuscls.get(0).getFcustomerid());
		saveOrUpdate(info);
		String execsql="update t_ord_cusdelivers cusd,t_bd_custproduct cpd set cusd.fcusproduct=cpd.fid where cusd.fisread=0 and ifnull(cusd.fcusproduct,'')='' and cusd.fmaktx=cpd.fname ";
		this.ExecBySql(execsql);
		execsql="update t_ord_cusdelivers cusd,t_bd_address ad set cusd.faddress=ad.fid where cusd.fisread=0 and ifnull(cusd.faddress,'')='' and cusd.freqaddress=ad.fname ";
		this.ExecBySql(execsql);
	}

	@Override
	public synchronized void ExecImportcusdelivers() throws Exception {
//		if(!isCusdeliverExporting)
//		{
//			isCusdeliverExporting=true;
			//声明Connection对象
			Connection tempcon=null;
			Connection vmicon=null;
			Statement vmistatement=null;
			Statement tempstatement=null;
			try {
			
				
				
				// TODO Auto-generated method stub
				List<HashMap<String,Object>> interfacecfgcls  =  QueryBySql("select fcustomerid,faccount,fpassword,fdatabase,FDEFAULTUSER from t_bd_interfacecfg where feffect=1");
				String pkids="";
				String execsql="";
				for (HashMap<String, Object> interfacecfginfo : interfacecfgcls) {
					if(interfacecfginfo.get("fcustomerid")==null || interfacecfginfo.get("fcustomerid").toString().equals("") || interfacecfginfo.get("FDEFAULTUSER")==null || interfacecfginfo.get("FDEFAULTUSER").toString().equals(""))
					{
						continue;
					}
					//构建中间数据库连接

					//驱动程序名
					String driver = "com.mysql.jdbc.Driver";
					//URL指向要访问的数据库名mydata
					String  tempurl = "jdbc:mysql://121.40.160.69:3306/"+interfacecfginfo.get("fdatabase");
					//MySQL配置时的用户名
					String  tempuser = interfacecfginfo.get("faccount").toString();
					//MySQL配置时的密码
					String  temppassword =interfacecfginfo.get("fpassword").toString();
					  //加载驱动程序
		            Class.forName(driver);
		            //1.getConnection()方法，连接MySQL数据库！！
		            tempcon = DriverManager.getConnection( tempurl, tempuser, temppassword);
		            tempstatement = tempcon.createStatement();
		            
		            //构建VMI数据库连接

					//URL指向要访问的数据库名mydata
					String vmiurl = "jdbc:mysql://127.0.0.1:3306/vmi";
					//MySQL配置时的用户名
					String vmiuser ="djvmi";
					//MySQL配置时的密码
					String vmipassword ="dongjingvmi";
					  //加载驱动程序
		            Class.forName(driver);
		            //1.getConnection()方法，连接MySQL数据库！！
		            vmicon = DriverManager.getConnection(vmiurl,vmiuser,vmipassword);
		            vmicon.setAutoCommit(false);
		            vmistatement = vmicon.createStatement();

		            
		            
		            
		            ResultSet rs = tempstatement.executeQuery("select * from "+interfacecfginfo.get("fdatabase")+".t_vmi_cusdeliver where fisread=0");
		            ResultSet checkrs=null;
		            while(rs.next()){
						 if(pkids.length()>0)
						 {
							 pkids=pkids+",'"+rs.getString("FID")+"'";
						 }
						 else
						 {
							 pkids="'"+rs.getString("FID")+"'";
						 }
		            	 if(!StringHelper.isEmpty(rs.getString("FID")))
		            	 {
		            		 checkrs=vmistatement.executeQuery("select fid from vmi.t_ord_cusdelivers where fcusfid='"+rs.getString("FID")+"'");
		            		 if(checkrs.next())
		            		 {
		            			 continue;
		            		 }
		            	 }
		            	 
						 execsql="INSERT INTO vmi.t_ord_cusdelivers (fid,fnumber,farrivetime,flinkman,famount,fdescription,fisread,fcusfid,fmaktx,freqaddress,freqdate,fwerkname,fcustomerid,fcreatorid) VALUES (";
						 execsql=execsql+"'"+this.CreateUUid()+"',";
						 execsql=execsql+"'"+rs.getString("FBILL_NO")+"-"+rs.getString("FBILL_SEQ")+"',";
						 execsql=execsql+"'"+rs.getString("FREQDATE").substring(0,11)+"09:00:00.0"+"',";
						 execsql=execsql+"'"+rs.getString("FCREATOR")+"',";
						 execsql=execsql+""+ rs.getString("FQTY")+",";
						 execsql=execsql+"'"+rs.getString("FREMARK")+"',";
						 execsql=execsql+"0,";
						 execsql=execsql+"'"+rs.getString("FID")+"',";
						 execsql=execsql+"'"+rs.getString("FPRODUCTNAME")+"',";
						 execsql=execsql+"'"+rs.getString("FREADDRESS")+"',";
						 execsql=execsql+"'"+rs.getString("FREQDATE").substring(0,11)+"17:00:00.0"+"',";
						 execsql=execsql+"'',";
						 execsql=execsql+"'"+interfacecfginfo.get("fcustomerid")+"',";
						 execsql=execsql+"'"+ interfacecfginfo.get("FDEFAULTUSER")+"'";
						 execsql=execsql+")";
						 vmistatement.executeUpdate(execsql);

					
		            }
		            if(pkids.length()>0)
					 {
						 execsql=" update "+interfacecfginfo.get("fdatabase")+".t_vmi_cusdeliver set fisread=1 where fid in ("+pkids+") ";
						 tempstatement.executeUpdate(execsql);
					 }
		            vmicon.commit();
		            if(checkrs!=null)
		            {
		            	checkrs.close();
		            }
		            rs.close();
		            
		            
		            
		            
		            
//					List<HashMap<String,Object>> cusdelivercls=QueryBySql("select * from "+interfacecfginfo.get("fdatabase")+".t_vmi_cusdeliver where fisread=0");
//					if(interfacecfginfo.get("fcustomerid")==null || interfacecfginfo.get("fcustomerid").toString().equals(""))
//					{
//						continue;
//					}
//					pkids="";
//					for (HashMap<String, Object> info : cusdelivercls) {
//						 execsql="INSERT INTO t_ord_cusdelivers (fid,fnumber,farrivetime,flinkman,famount,fdescription,fisread,fcusfid,fmaktx,freqaddress,freqdate,fwerkname,fcustomerid) VALUES (";
//						 execsql=execsql+"'"+this.CreateUUid()+"',";
//						 execsql=execsql+"'"+info.get("FBILL_NO")+"-"+info.get("FBILL_SEQ")+"',";
//						 execsql=execsql+"'"+info.get("FREQDATE")+"',";
//						 execsql=execsql+"'"+info.get("FCREATOR")+"',";
//						 execsql=execsql+""+ info.get("FQTY").toString()+",";
//						 execsql=execsql+"'"+info.get("FREMARK")+"',";
//						 execsql=execsql+"0,";
//						 execsql=execsql+"'"+info.get("FID")+"',";
//						 execsql=execsql+"'"+info.get("FPRODUCTNAME")+"',";
//						 execsql=execsql+"'"+info.get("FREADDRESS")+"',";
//						 execsql=execsql+"'"+info.get("FREQDATE")+"',";
//						 execsql=execsql+"'',";
//						 execsql=execsql+"'"+interfacecfginfo.get("fcustomerid")+"'";
//						 execsql=execsql+")";
//						 this.ExecBySql(execsql);
//						 if(pkids.length()>0)
//						 {
//							 pkids=pkids+",'"+info.get("FID")+"'";
//						 }
//						 else
//						 {
//							 pkids="'"+info.get("FID")+"'";
//						 }
//					}
//					 if(pkids.length()>0)
//					 {
//						 execsql=" update "+interfacecfginfo.get("fdatabase")+".t_vmi_cusdeliver set fisread=1 where fid in ("+pkids+") ";
//						 ExecBySql(execsql);
//					 }
					
				}
				execsql="update t_ord_cusdelivers cusd,t_bd_custproduct cpd set cusd.fcusproduct=cpd.fid where cusd.fisread=0 and ifnull(cusd.fcusproduct,'')='' and cusd.fmaktx=cpd.fname ";
				this.ExecBySql(execsql);
				execsql="update t_ord_cusdelivers cusd,t_bd_address ad set cusd.faddress=ad.fid where cusd.fisread=0 and ifnull(cusd.faddress,'')='' and cusd.freqaddress=ad.fname ";
				this.ExecBySql(execsql);
//				throw new DJException("err");
			} catch (Exception e) {
				// TODO: handle exception
				vmicon.rollback();
			}
			finally
			{
				tempstatement.close();
	            vmistatement.close();
	            tempcon.close();
	            vmicon.close();
//				isCusdeliverExporting=false;
			}
//		}
//		else
//		{
//			throw new DJException("上次任务还未执行完成，请稍后再试......");
//		}
		
	}

}
