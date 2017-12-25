package Com.Dao.order;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.UploadFile;
import Com.Entity.System.Useronline;
import Com.Entity.order.Firstproductdemand;
import Com.Entity.order.PreProductDemand;
import Com.Entity.order.PreProductDemandPlan;
import Com.Entity.order.PreProductDemandStructure;
import Com.Entity.order.Productdemandfile;

@Service("preProductDemandDao")
public class PreProductDemandDao extends BaseDao implements
		IPreProductDemandDao {

	@Resource
	private IFirstproductdemandDao firstproductdemandDao;
	
	@Override
	public void saveStructureFile(HttpServletRequest request, String fid) {
		UploadFile.delFileByParentId(fid);
		String fileId = UploadFile.upload(request, fid,true)[0];
		PreProductDemandStructure obj = (PreProductDemandStructure) this.Query(PreProductDemandStructure.class, fid);
		obj.setFfileid(fileId);
//		obj.setFsfileid(smalFileId);
	}

	@Override
	public void savePreProductDemandPlan(HttpServletRequest request) {
		String fid = this.CreateUUid();
		String fileId = UploadFile.upload(request, fid,true)[0];
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String fcustomerid = (String) request.getAttribute("fcustomerid");
		String fcustpage = (String) request.getAttribute("fcustpage");
		PreProductDemandPlan obj = new PreProductDemandPlan(fid);
		obj.setFcreatorid(userid);
		obj.setFcustomerid(fcustomerid);
		obj.setFfileid(fileId);
//		obj.setFsfileid(smalFileId);
		obj.setFcustpage(fcustpage);
		obj.setFupdatetime(new Date());
		this.saveOrUpdate(obj);
	}

	@Override
	public void DelPreProductDemandPlan(String fid) {
		UploadFile.delFileByParentId(fid);
		String sql = "delete from t_ord_preproductdemand_plan where fid='"+fid+"'";
		this.ExecBySql(sql);
	}

	@Override
	public void DelPreProductDemandStructure(String fid) {
		UploadFile.delFileByParentId(fid);
		String sql = "delete from t_ord_preproductdemand_structure where fid='"+fid+"'";
		this.ExecBySql(sql);
	}

	@Override
	public Firstproductdemand handleProductDemand(HttpServletRequest request) throws IOException {
		String fid = this.CreateUUid(); //需求id
		UploadFile.upload(request, fid);
		String userId = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		String jsonObj = (String) request.getAttribute("Firstproductdemand");
		String jsonArray = (String) request.getAttribute("Productdemandfile");
		Firstproductdemand obj = (Firstproductdemand) JsonUtil.jsToObject(jsonObj, "Com.Entity.order.Firstproductdemand");
		if(!StringUtils.isEmpty(obj.getFname())){
			if(this.QueryExistsBySql("select 1 from t_ord_firstproductdemand where fname = '"+obj.getFname()+"'")){
				throw new DJException("需求名称已存在，不允许保存！");
			}
		}
		List<Productdemandfile> list = JsonUtil.jsTolist(jsonArray, "Com.Entity.order.Productdemandfile");
		Productdemandfile fileObj;
		String fileId;
		String path;
		String newPath;
		if(list.size()>0){
			obj.setFisaccessory(true);
			for(Productdemandfile o : list){
				if(!StringUtils.isEmpty(o.getFid())){	//结构或平面图的附件
					fileObj = (Productdemandfile) this.Query(Productdemandfile.class, o.getFid());
					if(fileObj == null){
						throw new DJException("附件ID不正确！");
					}
					fileId = this.CreateUUid();
					o.setFid(fileId);
					o.setFparentid(fid);
					path = fileObj.getFpath();
					newPath = path.substring(0,path.lastIndexOf("/")+1)+fileId+path.substring(path.lastIndexOf("."));
					o.setFpath(newPath);
					this.saveOrUpdate(o);
					UploadFile.copyFile(path, newPath);
				}
			}
		}
		obj.setFid(fid);
		obj.setFcreatid(userId);
		obj.setFcreatetime(new Date());
		String preProductDemandId = (String) request.getAttribute("fpreproductdemandid");
		if(StringUtils.isEmpty(preProductDemandId)){
			preProductDemandId = this.CreateUUid();
			PreProductDemandStructure sObj = (PreProductDemandStructure) this.Query(PreProductDemandStructure.class,(String)request.getAttribute("fstructureid"));
			PreProductDemandPlan pObj = (PreProductDemandPlan) this.Query(PreProductDemandPlan.class,(String)request.getAttribute("fplanid"));
			PreProductDemand preProductDemand = new PreProductDemand(preProductDemandId);
			preProductDemand.setFcpname(sObj.getFcpname());
			preProductDemand.setFcreatorid(userId);
			preProductDemand.setFcustomerid(obj.getFcustomerid());
			preProductDemand.setFcustpage(pObj.getFcustpage());
			preProductDemand.setFdescription(obj.getFdescription());
			preProductDemand.setFplanfileid(pObj.getFfileid());
			preProductDemand.setFplanid(pObj.getFid());
			preProductDemand.setFspec(sObj.getFspec());
			preProductDemand.setFstructurefileid(sObj.getFfileid());
			preProductDemand.setFstructureid(sObj.getFid());
			preProductDemand.setFupdatetime(new Date());
			this.saveOrUpdate(preProductDemand);
		}
		obj.setFpreproductdemandid(preProductDemandId);
		return obj;
	}

	@Override
	public void saveProductDemand(HttpServletRequest request)
			throws IOException {
		Firstproductdemand obj = this.handleProductDemand(request);
		obj.setFstate("存草稿");
		this.saveOrUpdate(obj);
	}


	@Override
	public void saveReleaseProductDemand(HttpServletRequest request) throws IOException {
		Firstproductdemand obj = this.handleProductDemand(request);
		if(StringUtils.isEmpty(obj.getFname())){
			throw new DJException("需求未完成编辑。请填写需求名称。");
		}
		firstproductdemandDao.ExecAuditFproductdemand(obj,obj.getFcreatid());
	}
	
}
