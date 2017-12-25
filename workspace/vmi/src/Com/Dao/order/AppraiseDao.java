package Com.Dao.order;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.JPushUtil;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ISimplemessageDao;
import Com.Entity.order.Appraise;
@Service("AppraiseDao")
public class AppraiseDao extends BaseDao implements IAppraiseDao{
	@Resource
	private ISimplemessageDao SimplemessageDao;
	public void saveAppraiseInfo(Appraise appraise) {
		//2015-08-28 消息推送
		this.saveOrUpdate(appraise);
		String tsql = " SELECT s.fid,u.fname uname,c.fname cname,s.fname sfname FROM t_ord_schemedesign s ";
		tsql+= " JOIN t_bd_customer c ON c.fid = s.`fcustomerid`  JOIN t_sys_user u ON u.fid = s.`fcreatorid` where s.fid = '"+appraise.getFschemedesignid()+"' ";
		List<HashMap<String, Object>> lists = this.QueryBySql(tsql);
		if(lists.size()>0){
			StringBuffer sb = new StringBuffer();
			sb.append(lists.get(0).get("cname").toString()+"已对"+lists.get(0).get("sfname").toString()+"作出评价！");
			if(appraise.getFbasicappraise()==0){
				sb.append("非常满意!");
			}
			else if(appraise.getFbasicappraise()==1){
				sb.append("满意!");
			}
			else{
				sb.append("不满意!");					
			}
			if(!StringUtils.isEmpty(appraise.getFdescription())){
				sb.append(appraise.getFdescription());		
			}
			HashMap<String, String> messagemap = new HashMap<String, String>();
			messagemap.put("fcontent", sb.toString());
			messagemap.put("frecipientid", (String)lists.get(0).get("fid"));
			messagemap.put("ftype", "3");
			SimplemessageDao.MessageProjectEvaluationWithSender(messagemap);
			JPushUtil.SendPushToAll((String)lists.get(0).get("uname"),sb.toString());//极光推送消息
			JPushUtil.SendPushToIOS((String)lists.get(0).get("uname"),sb.toString());
		}
	}

}
