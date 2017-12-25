package Com.Controller.Logistics;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.UploadFile;
import Com.Dao.Logistics.ICarDao;
import Com.Entity.Logistics.Car;
import Com.Entity.System.Useronline;

@Controller
public class CarController {
	@Resource
	ICarDao CarDao;
	@RequestMapping("getCarList")
	public void getCarList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String sql = "select IF(IFNULL(p.fpath,'')='','vmi/images/cps.jpg',SUBSTRING_INDEX(p.fpath,'webapps/',-1)) fpath,c.fcustomerid,c.fcarage,c.fseats,c.fcreatetime,c.fcreaterid,CONCAT_WS('*',c.fcarlength,c.fcarwidth,c.fcarheight) fcarbody,c.fid,c.fcartype,c.fcarKG,c.fcarnumber,c.fdrivername,c.fdriverphone,c.fcarpicture,c.fdrivingexperience,c.fcarlength,c.fcarwidth,c.fcarheight,c.fname,c.fdescription from t_ord_car c left join (select * from t_ord_productdemandfile group by fparentid) p on c.fid=p.fparentid";
			ListResult result = CarDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("delCarList")
	public void delCarList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String fid = request.getParameter("fidcls");
			CarDao.DelCar(fid);
			response.getWriter().write(JsonUtil.result(true,"删除成功","",""));
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("saveOrUpdateCar")
	public void saveOrUpdateCar(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			Car car = (Car)request.getAttribute("Car");
			if(!StringUtils.isEmpty(car)){
				if(StringUtils.isEmpty(car.getFid())){
					Car carL = (Car) CarDao.Query(Car.class, car.getFid());
					if(StringUtils.isEmpty(carL)){
						car.setFid(CarDao.CreateUUid());
						car.setFcreaterid(userid);
						car.setFcreatetime(new Date());
					}
				}
				CarDao.saveOrUpdate(car);
			}
			response.getWriter().write(JsonUtil.result(true,"保存成功！","",""));
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("getCarInfo")
	public void getCarInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String fid = request.getParameter("fid");
			String sql = "select fcustomerid,fcarage,fseats,fcreatetime,fcreaterid,CONCAT_WS('*',fcarlength,fcarwidth,fcarheight) fcarbody,fid,fcartype,fcarKg,fcarnumber,fdrivername,fdriverphone,fcarpicture,fdrivingexperience,fcarlength,fcarwidth,fcarheight,fname,fdescription from t_ord_car where fid='"+fid+"'";
			List result = CarDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,"查询失败","",""));
		}
	}
	@RequestMapping("getCarId")
	public void getCarId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String fid = CarDao.CreateUUid();
			response.getWriter().write(JsonUtil.result(true,fid,"",""));
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("delCarImage")
	public void delCarImage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String fid = request.getParameter("fid");
			Car carL = (Car) CarDao.Query(Car.class, fid);
			File file;
			if(StringUtils.isEmpty(carL)){
				String sql = "select * from t_ord_productdemandfile where fparentid ='"+fid+"'";
				List<HashMap<String,Object>> list = CarDao.QueryBySql(sql);
				if(list.size()>0){
					file = new File((String)list.get(0).get("fpath"));
					if(file.exists()){
						file.delete();
					}
					sql = "delete from t_ord_productdemandfile where fparentid ='"+fid+"'";
					CarDao.ExecBySql(sql);//删除附件表信息
				}
			}
			response.getWriter().write(JsonUtil.result(true,"删除成功","",""));
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
}
