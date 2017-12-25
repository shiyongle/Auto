package com.pc.controller.identification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.appInterface.api.DongjingClient;
import com.pc.controller.BaseController;
import com.pc.controller.order.OrderController;
import com.pc.dao.Car.impl.CarDao;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.clUpload.impl.UploadDao;
import com.pc.dao.identification.impl.IdentificationDao;
import com.pc.dao.message.ImessageDao;
import com.pc.dao.umeng.IUMengPushDao;
import com.pc.dao.usercustomer.IUserCustomerDao;
import com.pc.model.CL_Car;
import com.pc.model.CL_Identification;
import com.pc.model.CL_Message;
import com.pc.model.CL_Umeng_Push;
import com.pc.model.CL_UserCustomer;
import com.pc.model.CL_UserRole;
import com.pc.model.Cl_Upload;
import com.pc.query.identification.IdentificationQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;
import com.pc.util.push.Demo;

@Controller
public class IdentificationController extends BaseController {
	protected static final String LIST_JSP = "/pages/pc/identification/list.jsp";
	protected static final String HUOLIST_JSP = "/pages/pc/identification/huoList.jsp";
	protected static final String VETTED_JSP = "/pages/pc/identification/vetted.jsp";
	protected static final String VETTEDBOSS_JSP = "/pages/pc/identification/vettedForBoss.jsp";
	protected static final String FIND_JSP = "/pages/pc/identification/find.jsp";
	protected static final String QUERY1_JSP = "/pages/pc/identification/query1.jsp";
	protected static final String QUERY2_JSP = "/pages/pc/identification/query2.jsp";
	protected static final String UPDATE_JSP = "/pages/pc/identification/updateinfo.jsp";

	@Resource
	private UploadDao uploadDao;
	@Resource
	private CarDao carDao;
	@Resource
	private IdentificationDao identificationDao;
	@Resource
	private IUserRoleDao iuserRoleDao;
	@Resource
	private ImessageDao messageDao;
	@Resource
	private IUserCustomerDao usercustomerDao;
	@Resource
	private IUMengPushDao iumeng;

	// 有待审核的记录实现消息推送，每五分钟查询一次
	@RequestMapping("/iden/getIdenOfType")
	public String getIdenOfType(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		List<CL_Identification> lst = identificationDao.getIdentificationsByStatus(1);
		if (lst != null && lst.size() > 0) {
			return writeAjaxResponse(reponse, "success");
		}
		return writeAjaxResponse(reponse, "false");
	}

	// 车主认证列表界面
	@RequestMapping("/iden/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		return LIST_JSP;
	}

	// 货主认证列表界面
	@RequestMapping("/iden/huoList")
	public String huoList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		return HUOLIST_JSP;
	}

	// 查询界面
	@RequestMapping("/iden/findIden")
	public String findIden(HttpServletRequest request, HttpServletResponse reponse, Integer id) throws Exception {
		CL_Identification iden = identificationDao.getById(id);
		request.setAttribute("iden", iden);
		return FIND_JSP;
	}

	// 车主认证界面数据加载
	@RequestMapping("/iden/load")
	public String load(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute IdentificationQuery identificationQuery) throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String username = request.getParameter("fusername");
		if (identificationQuery == null) {
			identificationQuery = newQuery(IdentificationQuery.class, null);
		}
		if (pageNum != null) {
			identificationQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			identificationQuery.setPageSize(Integer.parseInt(pageSize));
		} // 多条件查询的属性
		identificationQuery.setUserRoleId(2);
		identificationQuery.setFusername(null);
		if (!"请选择".equals(username) && username != null) {
			identificationQuery.setFusername(username);
		}
		Page<CL_Identification> page = identificationDao.findPage(identificationQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}

	// 车主认证界面数据加载
	@RequestMapping("/iden/loadHuo")
	public String loadHuo(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute IdentificationQuery identificationQuery) throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String username = request.getParameter("fusername");
		if (identificationQuery == null) {
			identificationQuery = newQuery(IdentificationQuery.class, null);
		}
		if (pageNum != null) {
			identificationQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			identificationQuery.setPageSize(Integer.parseInt(pageSize));
		} // 多条件查询的属性
		identificationQuery.setUserRoleId(1);
		identificationQuery.setFusername(null);
		if (!"请选择".equals(username) && username != null) {
			identificationQuery.setFusername(username);
		}
		Page<CL_Identification> page = identificationDao.findHuoPage(identificationQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}

	/*** 针对车主审核 **/
	@RequestMapping("/iden/vetted")
	public String vetted(HttpServletRequest request, HttpServletResponse reponse, Integer id) throws Exception {
		// 获取单条记录
		List<Cl_Upload> clu_lst = uploadDao.getCl_UploadsByParentId(id);
		CL_Identification cli = identificationDao.getById(id);// 获取审核对象，方便在审核时更改
		request.getSession().setAttribute("cli", cli);
		if (clu_lst.size() > 0) {
			if (clu_lst.size() != 1) {
				for (int i = 0; i < clu_lst.size(); i++) {
					int semp = i + 1;
					String temp = "img0" + semp;
					request.setAttribute(temp, clu_lst.get(i).getUrl());
				}
			} else {
				if (clu_lst.get(0) != null) {
					request.setAttribute("img01", clu_lst.get(0).getUrl());
					request.setAttribute("img02", "");
				}

			}
		}
		return VETTED_JSP;
	}

	/** 针对货主审核 */
	@RequestMapping("/iden/vettedForBoss")
	public String vettedForBoss(HttpServletRequest request, HttpServletResponse reponse, Integer id) throws Exception {
		// 获取单条记录
		List<Cl_Upload> clu_lst = uploadDao.getCl_UploadsByParentId(id);
		CL_Identification cli = identificationDao.getById(id);// 获取审核对象，方便在审核时更改
		request.getSession().setAttribute("cli", cli);
		if (clu_lst.size() > 0) {
			if (clu_lst.size() != 1) {
				for (int i = 0; i < clu_lst.size(); i++) {
					int semp = i + 1;
					String temp = "img0" + semp;
					request.setAttribute(temp, clu_lst.get(i).getUrl());
				}
			} else {
				if (clu_lst.get(0) != null) {
					request.setAttribute("img01", clu_lst.get(0).getUrl());
					request.setAttribute("img02", "");
				}
			}
		}
		return VETTEDBOSS_JSP;
	}

	// 数据表格加载之前，要确定路片路径
	@RequestMapping("/iden/getImg")
	public String getImg(HttpServletRequest request, HttpServletResponse reponse, Integer id) throws Exception {
		// 获取单条记录
		List<Cl_Upload> clu_lst = uploadDao.getCl_UploadsByParentId(id);
		String img01 = null;// 保存第一个路径
		String img02 = null;// 保存第二个路径
		if (clu_lst.size() > 0) {
			if (clu_lst.size() != 1) {
				for (int i = 0; i < clu_lst.size(); i++) {
					if (i == 0) {
						img01 = clu_lst.get(0).getUrl();
					} else if (i == 1) {
						img02 = clu_lst.get(1).getUrl();
					}

				}
			} else {
				if (clu_lst.get(0) != null) {
					img01 = clu_lst.get(0).getUrl();
					img02 = "";
				}
			}
			Map<String, String> m = new HashMap<String, String>();
			if (img01 != null) {
				m.put("img01", img01);
			}
			if (img02 != null) {
				m.put("img02", img02);
			}
			return writeAjaxResponse(reponse, JSONUtil.getJson(m));
		} else {
			return writeAjaxResponse(reponse, "fail");
		}
	}

	// 审核
	@RequestMapping("/iden/pass")
	@Transactional
	public String pass(@ModelAttribute("identification") CL_Identification identification, HttpServletRequest request,
			HttpServletResponse reponse, Integer id) throws Exception {
		CL_Identification cli = (CL_Identification) request.getSession().getAttribute("cli");
		Integer userRoleId = cli.getUserRoleId();// 角色主键
		CL_UserRole us = iuserRoleDao.getById(userRoleId);
		// 先根据审核类型确定是通过还是驳回 1:pass 2:refuse
		String result = request.getParameter("result");
		if (Integer.parseInt(result) == 1) {
			// 先更改认证表的状态，审核时间和原因
			String backReason = request.getParameter("reason");
			cli.setStatus(3);// 状态为3表示已通过
			cli.setBackReason(backReason);
			cli.setAuditTime(new Date());
			this.identificationDao.update(cli);
			us.setPassIdentify(true);
			iuserRoleDao.updateIsPassIdentify(us);
			// 如果是车主就向车辆表中插入一条数据
			if (cli.getRoleId() == 2) {
				String carNum = request.getParameter("carNum");
				String driverName = request.getParameter("driverName");
				String carType = request.getParameter("type");// 车型
				String driverNum = request.getParameter("driverNum");
				String carSpecId = request.getParameter("spec");// 规格
				String licenseType = request.getParameter("licenseType");
				String activeArea = request.getParameter("activeArea");
				CL_Car clc = new CL_Car();
				clc.setUserRoleId(userRoleId);
				clc.setCarNum(carNum);
				clc.setDriverName(driverName);
				clc.setCarType(Integer.parseInt(carType));
				clc.setDriverCode(driverNum);
				clc.setFluckDriver(0);
				clc.setCarSpecId(Integer.parseInt(carSpecId));
				clc.setLicenseType(Integer.parseInt(licenseType));
				clc.setFprovince(request.getParameter("fprovince"));
				clc.setFcity(request.getParameter("fcity"));
				clc.setActiveArea(Integer.parseInt(activeArea));
				if (carDao.getCarTypeByUserRoleId(userRoleId) == null) {
					this.carDao.save(clc);
					CL_Message message = new CL_Message();
					message.setCreateTime(new Date());
					message.setCreator(request.getSession().getAttribute("userRoleId").toString());
					message.setContent("您提交的认证信息,已审核通过！");
					message.setReceiver(userRoleId);
					message.setType(2);
					message.setTitle("审核通过");
					messageDao.save(message);
					this.sendUmengToService(userRoleId, "您提交的认证信息,已审核通过！");
					DongjingClient djcn = ServerContext.createVmiClient();
					String msgString = "您提交的认证信息,已审核通过！";
					djcn.setMethod("getDeatilCode");
					djcn.setRequestProperty("ftel", us.getVmiUserPhone());
					djcn.setRequestProperty("fname", "1");
					djcn.setRequestProperty("fcode", "1");
					djcn.setRequestProperty("msgString", msgString);
					// djcn.SubmitData();
					// 无论成功与否，清楚session的value
					request.getSession().removeAttribute("cli");
				}
			} else {
				CL_Message message = new CL_Message();
				message.setCreateTime(new Date());
				message.setCreator(request.getSession().getAttribute("userRoleId").toString());
				message.setContent("您提交的认证信息,已审核通过！");
				message.setReceiver(userRoleId);
				message.setType(1);
				message.setTitle("审核通过");
				messageDao.save(message);
				this.sendUmengToService(userRoleId, "您提交的认证信息,已审核通过！");
				DongjingClient djcn = ServerContext.createVmiClient();
				String msgString = "您提交的认证信息,已审核通过！";
				djcn.setMethod("getDeatilCode");
				djcn.setRequestProperty("ftel", us.getVmiUserPhone());
				djcn.setRequestProperty("fname", "1");
				djcn.setRequestProperty("fcode", "1");
				djcn.setRequestProperty("msgString", msgString);
				djcn.SubmitData();
				// 无论成功与否，清楚session的value
				request.getSession().removeAttribute("cli");

				// 2016-4-19 by les 货主（个人/企业）认证通过都生成客户资料
				List<CL_UserCustomer> list = usercustomerDao.getByUrid(cli.getUserRoleId());
				CL_UserCustomer ucinfo = null;
				if (list.size() > 0) {
					ucinfo = list.get(0);
					ucinfo.setFname(cli.getName());
					// 认证时间
					ucinfo.setFaudittime(new Date());
					usercustomerDao.update(ucinfo);
				} else {
					ucinfo = new CL_UserCustomer();
					int maxId = usercustomerDao.getMaxId() + 1;
					// 0 代表前面补充0
					// 4 代表长度为4
					// d 代表参数为正数型
					String strNumber = "C" + String.format("%08d", maxId);
					ucinfo.setFnumber(strNumber);
					ucinfo.setFcreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
					ucinfo.setFcreateTime(new Date());
					ucinfo.setFname(cli.getName());
					// 认证时间
					ucinfo.setFaudittime(new Date());
					ucinfo.setFuserRoleId(cli.getUserRoleId());
					usercustomerDao.save(ucinfo);
				}
				// 2016-4-19 by les 货主（个人/企业）认证通过都生成客户资料
			}
		} else {
			cli.setStatus(2);// 状态改成已驳回
			String backReason = request.getParameter("reason");
			cli.setBackReason(backReason);
			cli.setAuditTime(new Date());
			identificationDao.update(cli);
			CL_Message message = new CL_Message();
			message.setCreateTime(new Date());
			message.setCreator(request.getSession().getAttribute("userRoleId").toString());
			message.setContent("您提交的认证信息,审核失败！");
			message.setReceiver(userRoleId);
			message.setType(1);
			message.setTitle("审核失败");
			this.sendUmengToService(userRoleId, "您提交认证有误，已被驳回，请重新提交！");
			DongjingClient djcn = ServerContext.createVmiClient();
			String msgString = "您提交认证有误，已被驳回，请重新提交！";
			djcn.setMethod("getDeatilCode");
			djcn.setRequestProperty("ftel", us.getVmiUserPhone());
			djcn.setRequestProperty("fname", "1");
			djcn.setRequestProperty("fcode", "1");
			djcn.setRequestProperty("msgString", msgString);
			djcn.SubmitData();
			// 无论成功与否，清楚session的value
			request.getSession().removeAttribute("cli");
		}
		return writeAjaxResponse(reponse, "success");
	}

	// 数据表格加载之前，要确定路片路径
	@RequestMapping("/iden/queryInfo")
	public String queryInfo(HttpServletRequest request, HttpServletResponse reponse, Integer id) throws Exception {
		CL_Identification identification = this.identificationDao.getById(id);
		request.setAttribute("identification", identification);
		List<CL_Car> carlist = this.carDao.getByUserRoleId(identification.getUserRoleId());
		if (carlist.size() > 0) {
			request.setAttribute("car", carlist.get(0));
		}
		List<Cl_Upload> clu_lst = uploadDao.getCl_UploadsByParentId(id);
		if (clu_lst.size() > 0) {
			if (clu_lst.size() != 1) {
				for (int i = 0; i < clu_lst.size(); i++) {
					int semp = i + 1;
					String temp = "img0" + semp;
					request.setAttribute(temp, clu_lst.get(i).getUrl());
				}
			} else {
				if (clu_lst.get(0) != null) {
					request.setAttribute("img01", clu_lst.get(0).getUrl());
					request.setAttribute("img02", "");
				}
			}
		}
		if (identification.getRoleId() == 1) {
			return QUERY1_JSP;
		} else {
			return QUERY2_JSP;
		}
	}

	// 修改界面
	@RequestMapping("/iden/updateInfo")
	public String updateInfo(HttpServletRequest request, HttpServletResponse reponse, Integer id) throws Exception {
		CL_Identification iden = identificationDao.getById(id);// 根据传过来的id获取队友的认证对象
		request.getSession().setAttribute("updateIden", iden);// 保存在session作用域里面
		List<CL_Car> carlist = this.carDao.getByUserRoleId(iden.getUserRoleId());
		if (carlist.size() > 0) {
			request.setAttribute("car", carlist.get(0));
		}
		List<Cl_Upload> clu_lst = uploadDao.getCl_UploadsByParentId(id);
		if (clu_lst.size() > 0) {
			if (clu_lst.size() != 1) {
				for (int i = 0; i < clu_lst.size(); i++) {
					int semp = i + 1;
					String temp = "img0" + semp;
					request.setAttribute(temp, clu_lst.get(i).getUrl());
				}
			} else {
				if (clu_lst.get(0) != null) {
					request.setAttribute("img01", clu_lst.get(0).getUrl());
					request.setAttribute("img02", "");
				}
			}
		}
		return UPDATE_JSP;
	}

	// 设置取消好运司机
	@RequestMapping("/iden/updateLuckDr")
	@Transactional
	public String updateLuckDr(HttpServletRequest request, HttpServletResponse reponse, Integer[] ids)
			throws Exception {
		for (Integer id : ids) {
			Integer roleId = this.identificationDao.getById(id).getUserRoleId();
			CL_Car car = this.carDao.getCarTypeByUserRoleId(roleId);
			Integer luck = car.getFluckDriver();
			if (luck == 0) {
				car.setFluckDriver(1);
			} else {
				car.setFluckDriver(0);
			}
			carDao.updateLuckDr(car);
		}
		return writeAjaxResponse(reponse, "success");
	}

	// 修改对应的认证信息，但是不能修改已经通过的
	@RequestMapping("/iden/updateExclude")
	@Transactional
	public String updateExclude(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		// 获取修改的认证对象
		CL_Identification iden = (CL_Identification) request.getSession().getAttribute("updateIden");
		// 根据user_role_id获取对应的car的对象
		List<CL_Car> lst = carDao.getByUserRoleId(iden.getUserRoleId());
		// 如果数据具有唯一标识，那么获取集合中的第一个车辆对象
		CL_Car car = lst.get(0);
		// 获取表单传递过来的修改数据，设置并且修改
		car.setCarNum(request.getParameter("carNum"));
		car.setDriverCode(request.getParameter("driverNum"));
		car.setDriverName(request.getParameter("driverName"));
		car.setFprovince(request.getParameter("fprovince"));
		car.setFcity(request.getParameter("fcity"));
		car.setActiveArea(Integer.parseInt(request.getParameter("activeArea")));
		car.setCarSpecId(Integer.parseInt((request.getParameter("spec"))));
		String carType = request.getParameter("type");// 先获取下拉框的值，如果为空，那么再获取隐藏域的值
		if ("请选择".equals(carType)) {
			carType = request.getParameter("typemaster");
		}
		car.setCarType(Integer.parseInt(carType));
		// 设置数据完毕，进行修改
		int row = carDao.update(car);
		if (row != 1) {
			return writeAjaxResponse(reponse, "fail");// 修改成功
		}
		return writeAjaxResponse(reponse, "success");// 修改成功
	}

	/** 导出excel **/
	@RequestMapping("/iden/exportExecl")
	public String exportExecl(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute IdentificationQuery idenQuery, Integer[] ids, String roletype) throws Exception {
		// String type=request.getParameter("type");
		boolean ises = true;
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "cps_" + format.format(new Date()) + ".xls";
		String path = OrderController.class.getResource("").toURI().getPath();
		path = path.substring(0, path.lastIndexOf("/WEB-INF")) + "/excel/" + fileName;

		try {
			File file = new File(path);
			if (!file.isFile()) {
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("认证列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
			SheetSettings ss = wsheet.getSettings();
			ss.setVerticalFreeze(2); // 设置行冻结前2行
			WritableFont font1 = new WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.BOLD);
			WritableFont font2 = new WritableFont(WritableFont.createFont("微软雅黑"), 9, WritableFont.NO_BOLD);
			WritableCellFormat wcf = new WritableCellFormat(font1); // 设置样式，字体
			wcf.setAlignment(Alignment.CENTRE); // 平行居中
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
			WritableCellFormat wcf2 = new WritableCellFormat(font2); // 设置样式，字体
			wcf2.setBackground(Colour.LIGHT_ORANGE);
			wcf2.setAlignment(Alignment.CENTRE); // 平行居中
			wcf2.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
			wcf2.setWrap(true);
			wsheet.mergeCells(0, 0, 14, 0); // 合并单元格
			Label titleLabel = new Label(0, 0, " 同城物流调度平台 ", wcf);
			wsheet.addCell(titleLabel);
			wsheet.setRowView(0, 1000); // 设置第一行的高度
			if (idenQuery == null) {
				idenQuery = newQuery(IdentificationQuery.class, null);
			}
			String username = request.getParameter("fusername");
			idenQuery.setFusername(null);
			if (ids != null) {
				idenQuery.setIds(ids);
			}
			if (!"请选择".equals(username) && username != null) {
				idenQuery.setFusername(username);
			}
			int conut = 2;
			if ("huo".equals(roletype)) {
				idenQuery.setUserRoleId(1);// 货主导出
				int[] headerArrHight = { 5, 20, 70, 30, 20, 20, 40, 20, 20, 20 };
				String headerArr[] = { "序号", "状态", "用户名称", "手机号", "认证类型", "角色类型", "业务员", "业务部门", "客户区域", "注册时间" };
				for (int i = 0; i < headerArr.length; i++) {
					wsheet.addCell(new Label(i, 1, headerArr[i], wcf));
					wsheet.setColumnView(i, headerArrHight[i]);
				}
				List<CL_Identification> list = this.identificationDao.find(idenQuery);
				for (int i = 0; i < list.size(); i++) {
					jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");// 设置数字格式
					jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);// 设置表单格式
					wcfN2.setBackground(Colour.LIGHT_ORANGE);
					wcfN2.setAlignment(Alignment.CENTRE); // 平行居中
					wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
					wsheet.addCell(new Label(0, conut, String.valueOf(i + 1), wcf2));
					wsheet.addCell(new Label(1, conut, statusName(list.get(i).getStatus().toString()), wcf2));
					wsheet.addCell(new Label(2, conut, list.get(i).getName(), wcf2));
					wsheet.addCell(new Label(3, conut, list.get(i).getVmiUserPhone(), wcfN2));
					wsheet.addCell(new Label(4, conut, typeName(list.get(i).getType().toString()), wcfN2));
					wsheet.addCell(new Label(5, conut, list.get(i).getRoleName(), wcf2));
					wsheet.addCell(new Label(6, conut, list.get(i).getFsaleman(), wcf2));
					wsheet.addCell(new Label(7, conut, list.get(i).getFsalemanDept(), wcf2));
					wsheet.addCell(new Label(8, conut, list.get(i).getFregion(), wcf2));
					wsheet.addCell(new Label(9, conut, list.get(i).getRegisterTimeString() == null ? ""
							: list.get(i).getRegisterTimeString().toString(), wcf2));
					conut++;
				}
			} else if ("car".equals(roletype))// 车主导出
			{
				idenQuery.setUserRoleId(2);
				int[] headerArrHight = { 5, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20 };
				String headerArr[] = { "序号", "状态", "用户名", "手机号", "认证类型", "角色类型", "驾驶员", "驾驶证号", "车牌号", "牌照类型", "规格名称",
						"车型名称", "好运司机", "注册时间" };
				for (int i = 0; i < headerArr.length; i++) {
					wsheet.addCell(new Label(i, 1, headerArr[i], wcf));
					wsheet.setColumnView(i, headerArrHight[i]);
				}
				List<CL_Identification> list = this.identificationDao.find(idenQuery);
				for (int i = 0; i < list.size(); i++) {
					jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");// 设置数字格式
					jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);// 设置表单格式
					wcfN2.setBackground(Colour.LIGHT_ORANGE);
					wcfN2.setAlignment(Alignment.CENTRE); // 平行居中
					wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
					wsheet.addCell(new Label(0, conut, String.valueOf(i + 1), wcf2));
					wsheet.addCell(new Label(1, conut, statusName(list.get(i).getStatus().toString()), wcf2));
					wsheet.addCell(new Label(2, conut, list.get(i).getName(), wcf2));
					wsheet.addCell(new Label(3, conut, list.get(i).getVmiUserPhone(), wcfN2));
					wsheet.addCell(new Label(4, conut, typeName(list.get(i).getType().toString()), wcfN2));
					wsheet.addCell(new Label(5, conut, list.get(i).getRoleName(), wcf2));
					wsheet.addCell(new Label(6, conut, list.get(i).getDriverName(), wcf2));
					wsheet.addCell(new Label(7, conut, list.get(i).getDriverCode(), wcf2));
					wsheet.addCell(new Label(8, conut, list.get(i).getCarNumber(), wcf2));
					wsheet.addCell(new Label(9, conut, licenseTypeName(
							list.get(i).getLicenseType() == null ? "" : list.get(i).getLicenseType().toString()),
							wcf2));
					wsheet.addCell(new Label(10, conut, list.get(i).getSpecName(), wcf2));
					wsheet.addCell(new Label(11, conut, list.get(i).getTypeName(), wcf2));
					wsheet.addCell(new Label(12, conut, fluckDriverName(
							list.get(i).getFluckDriver() == null ? "" : list.get(i).getFluckDriver().toString()),
							wcf2));
					wsheet.addCell(new Label(13, conut,
							list.get(i).getRegisterTimeString() == null ? "" : list.get(i).getRegisterTimeString(),
							wcf2));
					conut++;
				}
			}

			wwb.write();
			os.flush();
		} catch (Exception e) {
			ises = false;
			e.printStackTrace();
		} finally {
			try {
				if (wwb != null) {
					wwb.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		result.put("url", fileName);
		if (ises == true) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return writeAjaxResponse(reponse, JSONUtil.getJson(result));
	}

	public String statusName(String value) {
		if (value.equals("1")) {
			return "待审核";
		} else if (value.equals("2")) {
			return "已驳回";
		} else if (value.equals("3")) {
			return "已通过";
		} else if (value.equals("4")) {
			return "已关闭";
		} else {
			return "未知";
		}
	}

	public String typeName(String value) {
		if (value.equals("1")) {
			return "企业认证";
		} else if (value.equals("2")) {
			return "个人认证";
		} else {
			return "未知";
		}
	}

	public String licenseTypeName(String value) {
		if (value.equals("0")) {
			return "黄牌";
		} else if (value.equals("1")) {
			return "蓝牌-市";
		} else if (value.equals("2")) {
			return "蓝牌-县";
		} else {
			return "未知";
		}
	}

	public String fluckDriverName(String value) {
		if (value.equals("0")) {
			return "普通司机";
		} else if (value.equals("1")) {
			return "好运司机";
		} else {
			return "未知";
		}
	}
	
	public void sendUmengToService(int userid, String msg)
	{
		
		List<CL_Umeng_Push> umengList=iumeng.getUserUmengRegistration(userid);
		if(umengList.size() > 0)
		{
			//获取到用户多台设备
			for (CL_Umeng_Push cl_Umeng_Push : umengList) {
				Demo.SendUmeng(cl_Umeng_Push.getFdevice(), msg, cl_Umeng_Push.getFdeviceType());
				
			}
			messageDao.save(Demo.savePushMessage("信息认证",msg,userid,umengList.get(0).getFuserType()));
		}
	}

}
