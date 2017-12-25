package Com.Controller.SDK;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.JsonUtil;
import Com.Dao.order.IDeliverorderDao;
import Com.Dao.traffic.ITruckassembleDao;
import Com.Entity.System.Useronline;

@Controller
public class TruckassembleSDKControl {



	Logger log = LoggerFactory.getLogger(TruckassembleSDKControl.class);

	@Resource
	private ITruckassembleDao TruckassembleDao;
	
	@Resource
	private IDeliverorderDao DeliverorderDao;
	
	
	
//	private boolean iscreatTruckassemble=false;
	

	@RequestMapping(value = "/docreateTruckassembleAndSaledeliver")
	public synchronized String docreateTruckassembleAndSaledeliver(
			HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		String result = "";
		String supplierid="";
		try {
			
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			String sql = "select FISFILTER from t_sys_user where FID='"+userid+"' ";
			List<HashMap<String,Object>> list= TruckassembleDao.QueryBySql(sql);
			if (list.get(0).get("FISFILTER")!=null && list.get(0).get("FISFILTER").toString().equals("1")) {
				throw new DJException("管理用户不能操作！");
			}
			sql = " SELECT FSUPPLIERID FROM t_bd_usersupplier where FUSERID = '%s' union select s.fsupplierid from  t_bd_rolesupplier s left join t_sys_userrole r on r.froleid=s.froleid where r.fuserid='%s'";
			sql = String.format(sql, userid,userid);
			list= TruckassembleDao.QueryBySql(sql);
			if (list.size()!=1) {
				throw new DJException("管理用户不能自运发货！");
			}
			supplierid=list.get(0).get("FSUPPLIERID").toString();
//			if(iscreatTruckassemble)
//			{
//				throw new DJException("系统正在执行装配，请稍后再试.....");
//			}
//			iscreatTruckassemble=true;
			/**验证传递的数据**/
			HashMap<String, Object> params = new HashMap<String, Object>();
			HashMap<String, Object> entryparams = new HashMap<String, Object>();
			String truckinfo = request.getParameter("truckinfo");//主表信息
			JSONArray jsa=null,jsaentry=null;
			if(truckinfo!=null&&!"".equals(truckinfo)){
			 jsa = DataUtil.getJsonArrayByS(truckinfo);
			}
			String deliverorders = request.getParameter("deliverorders");//配送订单集合Json数据
			if(deliverorders!=null&&!"".equals(deliverorders)){ jsaentry = DataUtil.getJsonArrayByS(deliverorders);}
			String fids="";//保存配送信息集合
			if(jsa!=null&&jsa.size()>0&&jsaentry!=null&&jsaentry.size()>0)
			{
				String fsaleid=((JSONObject)jsa.get(0)).getString("fsaleno");
				if(fsaleid!=null&&!"".equals(fsaleid))
				{	
					/**传递表头信息**/
					params.put("fsaleid",fsaleid);//发货单单号
					params.put("fdriver", ((JSONObject)jsa.get(0)).getString("fdriver"));//司机
					params.put("ftelephone", ((JSONObject)jsa.get(0)).getString("ftelephone"));//电话
					params.put("flicensenum", ((JSONObject)jsa.get(0)).getString("flicensenum"));//车牌号
					
					
					for(int j=0;j<jsaentry.size();j++)
					{
					JSONObject o = jsaentry.getJSONObject(j);
					String deliverorderid=o.getString("deliverorderid");
					int realfamount=0;
					if(!DataUtil.positiveIntegerCheck(o.getString("realfamount")==null?"":o.getString("realfamount")))
					{
						continue;
					}
					realfamount=o.getInt("realfamount");
					if(!entryparams.containsKey(deliverorderid)){
					DataUtil.verifyNotNullAndDataAndPermissionsByValue(new String[][]{{deliverorderid,"配送ID"}},
								new String[][]{{"fid","t_ord_deliverorder",null,null,deliverorderid}}, request,
								DeliverorderDao);
					fids=fids+deliverorderid+",";
					}else
					{
						realfamount=new Integer(entryparams.get(deliverorderid).toString())+realfamount;
					}
					entryparams.put(deliverorderid, realfamount);
					}
				}else
				{
					throw new DJException("发货单单号不能为空！");
				}
				
			}else
			{
				throw new DJException("传递的数据缺少信息,有误！");
			}
			if(fids.length()<=0){
				result = JsonUtil.result(true,"成功!", "", "");
//				iscreatTruckassemble=false;
				return null;
			}
			fids="('"+fids.substring(0,fids.length()-1).replace(",", "','")+"')";
//			sql = " select * from t_ord_deliverorder where ( ifnull(fmatched,0)=1 or ifnull(faudited,0)=0 ) and fid in " + fids;
			sql = " select * from t_ord_deliverorder where ifnull(faudited,0)=0 and fid in " + fids;
			List<HashMap<String, Object>> deliverordercls = DeliverorderDao.QueryBySql(sql);
			if(deliverordercls.size()>0)
			{
				throw new DJException("部分配送信息未审核，不能配货！");
			}
//			 sql = " select * from t_ord_deliverorder where ifnull(fmatched,0)=0 and fid in " + fids;
			sql = " select * from t_ord_deliverorder where fid in " + fids;
			 deliverordercls = DeliverorderDao.QueryBySql(sql);
			params.put("deliverordercls", deliverordercls);
			params.put("userid", ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid());
			params.put("fsupplierid", supplierid);
			DeliverorderDao.ExecCreateTruckassembleSDK(params,entryparams);	
			result = JsonUtil.result(true,"成功!", "", "");
//			iscreatTruckassemble=false;
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
			if(!e.getMessage().startsWith("系统正在执行装配，请稍后再试"))
			{
//				iscreatTruckassemble=false;
			}
		}/*finally{
			iscreatTruckassemble=false;
		}*/
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}
}