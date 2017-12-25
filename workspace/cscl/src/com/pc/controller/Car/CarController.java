package com.pc.controller.Car;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

//import sun.jdbc.odbc.OdbcDef;
import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.Car.impl.CarDao;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.clUpload.impl.UploadDao;
import com.pc.dao.identification.impl.IdentificationDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.dao.usercustomer.impl.UserCustomerDao;
import com.pc.model.CL_Car;
import com.pc.model.CL_UserRole;
import com.pc.query.car.CarQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class CarController extends BaseController {
	protected static final String LIST_JSP = "/pages/pc/car/car_list.jsp";
	protected static final String Add_JSP = "/pages/pc/car/car_add.jsp";
	protected static final String Edit_JSP = "/pages/pc/car/car_edit.jsp";
	protected static final String CARMAP_JSP = "/pages/pc/orderMap/carMap.jsp";
	@Resource
	private CarDao carDao;
	@Resource
	private UserRoleDao userRoleDao;
    @Resource
    private IdentificationDao identificationDao; 
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserCustomerDao customerDao;
    @Resource
    private UploadDao uploadDao;

	@RequestMapping("/car/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return LIST_JSP;
	}

	@RequestMapping("/car/add")
	public String add(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return Add_JSP;
	}

	// 编辑功能
	@RequestMapping("/car/edit")
	public String edit(HttpServletRequest request, HttpServletResponse reponse,
			Integer id) throws Exception {
		CL_Car car = this.carDao.getById(id);
		request.setAttribute("car", car);
		return Edit_JSP;
	}
	
	// 车辆位置
	@RequestMapping("/orderMap/carMap")
	public String carMap(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		return CARMAP_JSP;
	}
	

	@RequestMapping("/car/load")
	public String load(HttpServletRequest request, HttpServletResponse reponse,@ModelAttribute CarQuery carQuery)
			throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (carQuery == null) {
			carQuery = newQuery(CarQuery.class, null);
		}
		if (pageNum != null) {
			carQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			carQuery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_Car> page = carDao.findPage(carQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));

	}

	// 新建 保存
	@RequestMapping("/car/save")
	public String create(HttpServletRequest request,
			HttpServletResponse reponse, CL_Car car) throws Exception {
		carDao.save(car);
		return writeAjaxResponse(reponse, "success");
	}

	// 删除功能
	@RequestMapping("/car/del")
	@Transactional(propagation = Propagation.REQUIRED)
	public String del(HttpServletRequest request, HttpServletResponse reponse,
			Integer[] carIds, Integer[] cusIds) throws Exception {
//		删除司机和客户时设置控制，如果该客户或司机有过订单记录，择不得删除，以避免造成不必要的数据丢失------BY LANCHER BEGIN
		if(carIds !=null && !"".equals(carIds)){
			for (Integer id : carIds) {
				CL_Car car = carDao.getById(id);
				int num = orderDao.getByUserRoleId(car.getUserRoleId(),null);
				if(num < 1){
					this.carDao.deleteById(id);
//				this.identificationDao.deleteByRuleId(id, -1);
					//删除该司机的认证资料
					int parent_id = identificationDao.getByUserRoleId(car.getUserRoleId()).get(0).getId();
					this.uploadDao.deleteByParentId(parent_id);
					this.identificationDao.deleteByUserRoleId(car.getUserRoleId());
					CL_UserRole user = this.userRoleDao.getById(car.getUserRoleId());
					user.setVmiUserName(user.getFname());//因sql中将vmiusername用了别名fname，而在update时取的是vmi，导致最终vmiusername为空，因涉及多处代码，姑且重新设置避免二次错误
					user.setRoleId(1);
					user.setPassIdentify(false);
					this.userRoleDao.update(user);
					this.userRoleDao.updateIsPassIdentify(user);
					return writeAjaxResponse(reponse, "success");
				}else {
					return writeAjaxResponse(reponse, "fail");
				}
			}
		} else {
			for	(Integer id : cusIds){
				CL_UserRole user = userRoleDao.getById(customerDao.getById(id).getFuserRoleId());
				System.out.println(user.getId());
				int num = orderDao.getByUserRoleId(null, user.getId());
				System.out.println(num);
				if(num < 1){
					//删除该客户的认证资料
					int parent_id = identificationDao.getByUserRoleId(user.getId()).get(0).getId();
					this.uploadDao.deleteByParentId(parent_id);
					user.setPassIdentify(false);
					userRoleDao.updateIsPassIdentify(user);
					customerDao.deleteById(id);
					identificationDao.deleteByUserRoleId(user.getId());
					return writeAjaxResponse(reponse, "success");
				}else {
					return writeAjaxResponse(reponse, "fail");
				}
			}
		}
		return writeAjaxResponse(reponse, "fail");
//		删除司机和客户时设置控制，如果该客户或司机有过订单记录，择不得删除，以避免造成不必要的数据丢失------BY LANCHER END
	}

	// 更新功能
	@RequestMapping("/car/update")
	public String update(HttpServletRequest request,
			HttpServletResponse reponse, CL_Car car) throws Exception {
		CL_Car clcar=carDao.getById(car.getId());
		car.setLongitude(clcar.getLongitude());
		car.setLatitude(clcar.getLatitude());
		car.setFluckDriver(clcar.getFluckDriver());
		carDao.update(car);
		return writeAjaxResponse(reponse, "success");

	}
	
	// 2016-4-30     车辆位置模拟  pc端   by lu
	@RequestMapping("/car/drvierPostion")
	public String drvierPostion(HttpServletRequest request,
			HttpServletResponse reponse, CL_Car car) throws Exception {
		HashMap<String, String[]> driverPosition = new HashMap<String, String[]>();
		List<HashMap<String,Object>> resList = new ArrayList<HashMap<String,Object>>();
		driverPosition = ServerContext.getDriverPosition();
		//被指派的司机手中存在的运输中的订单最早的调度时间超过6小时就警告，丢缓存时,取数据库中车辆位置------------BY LANCHER BEGIN------
		if(driverPosition.size()>0){
			Iterator itor = driverPosition.entrySet().iterator();    
			HashMap<String,Object> map =null;
		    while (itor.hasNext()) {
		    	map = new HashMap<String,Object>();
		        Map.Entry entry_d = (Map.Entry)itor.next();  
		        int userId =Integer.parseInt(entry_d.getKey().toString());
		        String[] str = (String[])entry_d.getValue();
		        // app 写入的是数组 按顺序取出需要的数据
		        for(int i=0;i<str.length;i++){
		        	if(i==0){
		        		map.put("lng", str[i]);
		        	}else if(i==1){
		        		map.put("lat", str[i]);
		        	}else if(i==2){
		        		map.put("positionTime", str[i]);
		        	}
		        }
		        List<CL_Car> ls = this.carDao.getByUserRoleId(userId);        
		        Date foperate_time = this.orderDao.findOperateTime(userId, 3);
		        if(foperate_time!=null && !"".equals(foperate_time)){
			        Date nowDate = new Date();
			        if(nowDate.getTime() - foperate_time.getTime() > 21600000 ){
			        	map.put("foperate_time", "超时");
			        }
		        } else {
					map.put("foperate_time", "未指派");
				}
		        
		        if(ls.size()>0){
		        	CL_Car ccinfo = ls.get(0);
		        	map.put("driverName", ccinfo.getDriverName());
		        	map.put("carNum", ccinfo.getCarNum());
		        	map.put("luckDriver", ccinfo.getFluckDriver());
		        	map.put("carType", ccinfo.getCarSpecName()+"_"+ccinfo.getCarTypeName());
		        }
		        resList.add(map);
		    }
			
		}else{
			List<CL_Car> list = carDao.getAllCar();
			HashMap<String,Object> map =null;
			int i = list.size();
			for(CL_Car carinfo:list){
				if(carinfo.getLongitude()!=null && !"".equals(carinfo.getLongitude()) && carinfo.getLatitude()!=null && !"".equals(carinfo.getLatitude())){
					map = new HashMap<String,Object>();
					map.put("driverName", carinfo.getDriverName());
					map.put("carNum", carinfo.getCarNum());
					map.put("lng", carinfo.getLongitude());
					map.put("lat", carinfo.getLatitude());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(carinfo.getFposition_time()!=null && !"".equals(carinfo.getFposition_time())){
						map.put("positionTime", sdf.format(carinfo.getFposition_time()));
					} else {
						map.put("positionTime", "---");
					}
					map.put("luckDriver", carinfo.getFluckDriver());
					map.put("carType", carinfo.getCarSpecName()+"_"+carinfo.getCarTypeName());
					Date foperate_time = this.orderDao.findOperateTime(carinfo.getUserRoleId(), 3);
				       if(foperate_time!=null && !"".equals(foperate_time)){
					       Date nowDate = new Date();
					       if(nowDate.getTime() - foperate_time.getTime() > 21600000 ){
					        map.put("foperate_time", "超时");
					       }
				       } else {
						map.put("foperate_time", "未指派");
					}
					resList.add(map);
				}
			}
		}
		//被指派的司机手中存在的运输中的订单最早的调度时间超过6小时就警告，丢缓存时,取数据库中车辆位置------------BY LANCHER END------
		HashMap m = new HashMap();
		m.put("list", resList);
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));

	}
	
	/**导出excel**/
	@RequestMapping("/car/exportExecl")
	public String exportExecl(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CarQuery carQuery,Integer[] carIds) throws Exception{
		boolean ises =true;
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String fileName = "CAR_"+format.format(new Date())+".xls";
		String path  = CarController.class.getResource("").toURI().getPath();
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("物流车辆", 0);//创建一个工作页，第一个参数为页名，第二个参数表示该工作页在excel中处于哪一页
			SheetSettings ss = wsheet.getSettings();
            ss.setVerticalFreeze(2);  // 设置行冻结前2行
            WritableFont font1 =new WritableFont(WritableFont.createFont("微软雅黑"), 10 ,WritableFont.BOLD);
            WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);
            WritableCellFormat wcf = new WritableCellFormat(font1);	  //设置样式，字体
	            wcf.setAlignment(Alignment.CENTRE);                   //平行居中
	            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   //垂直居中
            WritableCellFormat wcf2 = new WritableCellFormat(font2);  //设置样式，字体
	            wcf2.setBackground(Colour.LIGHT_ORANGE);
	            wcf2.setAlignment(Alignment.CENTRE);                  //平行居中
	            wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
	            wcf2.setWrap(true);  
            wsheet.mergeCells( 0 , 0 , 14 , 0 ); // 合并单元格  
            Label titleLabel = new Label( 0 , 0 , " 同城物流调度平台 ",wcf);
            wsheet.addCell(titleLabel);
            wsheet.setRowView(0, 1000); // 设置第一行的高度
            int[] headerArrHight = {10,15,25,25,20,20,20,20,65,65,20,20,25};
            String headerArr[] = {"序号","好运司机","注册用户","角色类型","车牌号","司机名字","车辆类型","车辆规格","驾驶证编码","司机电话号码","牌照类型","核载","主要活动区域"};
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);	
            }
            if (carQuery == null) {
    			carQuery = newQuery(CarQuery.class, null);
    		}
            if(carIds!=null){
            	carQuery.setIds(carIds);
            }
    		List<CL_Car> list = carDao.find(carQuery);
    		int conut = 2;
    		if(list.size() > 0){
    			for (int i = 0; i < list.size(); i++) {
    				jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
        			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
        			wcfN2.setBackground(Colour.LIGHT_ORANGE);
        			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
        			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
        			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
        			wsheet.addCell(new Label( 1 , conut ,typeFluck(list.get(i).getFluckDriver()),wcf2));//好运司机
        			wsheet.addCell(new Label( 2 , conut ,list.get(i).getUserName() ,wcf2));//注册用户
        			wsheet.addCell(new Label( 3 , conut ,list.get(i).getRoleName(),wcf2));//角色类型
        			wsheet.addCell(new Label( 4 , conut ,list.get(i).getCarNum() ,wcf2));//车牌号
        			wsheet.addCell(new Label( 5 , conut ,list.get(i).getDriverName() ,wcf2));//司机名字
        			wsheet.addCell(new Label( 6 , conut ,list.get(i).getCarTypeName() ,wcf2));//车辆类型
        			wsheet.addCell(new Label( 7 , conut ,list.get(i).getCarSpecName() ,wcf2));//车辆规格
        			wsheet.addCell(new Label( 8 , conut ,list.get(i).getDriverCode() ,wcf2));//驾驶证编码
        			wsheet.addCell(new Label( 9 , conut ,list.get(i).getCarFtel() ,wcf2));//司机电话号码
        			wsheet.addCell(new Label( 10 , conut ,typeLicenseType(list.get(i).getLicenseType()) ,wcf2));//牌照类型
        			wsheet.addCell(new Label( 11 , conut ,list.get(i).getWeight() ,wcf2));//核载
        			wsheet.addCell(new Label( 12 , conut ,list.get(i).getArea() ,wcf2));//主要活动区域
    				conut++;
				}
    		} else {
    			ises = false;
			}
			wwb.write();
			os.flush();
		} catch (Exception e) {
			ises = false;
			e.printStackTrace();
		}finally{
			try {
				if(wwb != null){
					wwb.close();
				}
				if(os != null){
					os.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		result.put("url", fileName);
		if(ises==true){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		return writeAjaxResponse(reponse,JSONUtil.getJson(result));
	}
	
	public String typeFluck(Integer value){
		if(value == 1){
			return "是";
		} else {
			return "否";
		}
	}
	
	public String typeLicenseType(Integer value){
		if(value == null){
			return "";
		} else if(value == 0){
			return "黄牌";
		} else if (value==1){
			return "蓝牌-市";
		} else {
			return "蓝牌-县";
		}
	}
	
	
}
