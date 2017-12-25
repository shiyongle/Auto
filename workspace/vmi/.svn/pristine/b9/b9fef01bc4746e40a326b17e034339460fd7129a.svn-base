package Com.Controller.System;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.SetFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.Excel.ExcelUtils;
import Com.Dao.System.IBodypapeDao;
import Com.Entity.System.Bodypape;

@Controller
public class BodypapeController {
	private static final String BASE_SQL = " SELECT fid, fname, fmanufacturer, fgramweight, ferror, fpopstrength, fhardfold, fwaterabsorption, fbasepapercategory, fusecategory, fremark, fenable,DATE_FORMAT(t_sys_bodypape.fcreatetime,'%Y-%m-%d %H:%i') fcreatetime,DATE_FORMAT(t_sys_bodypape.fupdatetime,'%Y-%m-%d %H:%i') fupdatetime FROM t_sys_bodypape ";
	Logger log = LoggerFactory.getLogger(BodypapeController.class);
	@Resource
	private IBodypapeDao bodypapeDao;

	private static Map<String, Byte> usecategoryMap = new HashMap<>();

	static {

		usecategoryMap.put("面纸", (byte) 1);
		usecategoryMap.put("里纸", (byte) 2);
		usecategoryMap.put("瓦纸", (byte) 3);
		usecategoryMap.put("芯纸", (byte) 4);

	}

	@RequestMapping(value = "/gainBodypapes")
	public String gainBodypapes(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {
			String sql = BASE_SQL;

			ListResult result = bodypapeDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/gainBodypapeByID")
	public String gainBodypapeByID(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {

			String fid = request.getParameter("fid");

			String sql = BASE_SQL + String.format(" where fid = '%s' ", fid);

			ListResult result = bodypapeDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/saveBodypape")
	public String saveBodypape(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {
			Bodypape po = buildPo(request, bodypapeDao);

			bodypapeDao.ExecSave(po);

			reponse.getWriter().write(JsonUtil.result(true, "成功", "", ""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	private Bodypape buildPo(HttpServletRequest request, IBaseDao dao) {
		String fid = request.getParameter("fid");

		Bodypape bodypape;
		// update
		if (fid != null && !fid.isEmpty()) {

			bodypape = (Bodypape) request.getAttribute("Bodypape");

			bodypape.setFupdatetime(new Date());

		} else {
			// add
			bodypape = (Bodypape) request.getAttribute("Bodypape");
			bodypape.setFid("");

		}

		return bodypape;
	}

	@RequestMapping(value = "/makeBodypapetoExcel")
	public String makeBodypapetoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "";

			ListResult result = bodypapeDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping("downloadBodyPapeFile")
	public void downloadBodyPapeFile(HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		String path = "D:\\tomcat\\原纸基础数据excel文件上传模版.xls";
		String name = path.substring(path.lastIndexOf("\\") + 1);

		InputStream in = null;
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			throw new DJException("此附件文件不存在，无法下载！");
		}
		response.setContentType("application/x-msdownload");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ new String(name.getBytes("UTF-8"), "iso-8859-1") + "\"");
		OutputStream out = response.getOutputStream();
		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = in.read(bytes, 0, 1024)) != -1) {
			out.write(bytes, 0, len);
		}
		out.flush();
		in.close();
	}

	@RequestMapping("uploadBodyPapeFile")
	public String uploadBodyPapeFile(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {

			List<String[]> rows = new ArrayList<>();

			Sheet sheet = ExcelUtils.getSheet(request);
			Iterator<Row> rit = sheet.rowIterator();
			Row row;
			int currentRow = 2;
			Iterator<Cell> cit;

			row = rit.next();

			// 读取表格数据
			outer:

			while (rit.hasNext()) {
				row = rit.next();

				if (currentRow == 2) {
					cit = row.cellIterator();

					if (!verifyHeader(cit)) {

						throw new DJException("上传Excel格式不正确");

					}
				} else if (currentRow > 2) {

					String[] cells = new String[10];

					cit = row.cellIterator();

					int i = 0;

					while (cit.hasNext()) {

						String cell = ExcelUtils.getStringCellValue(cit.next());

						if (i == 0) {
							// 终止符
							if (cell.equals("注意事项：")) {
								break outer;
							}

						} else {

							if ((i - 1) >= cells.length) {

								break;

							}
							cells[i - 1] = cell;

						}

						i++;

					}

					rows.add(cells);

				}
				currentRow++;
			}

			// 去掉空行
			List<String[]> effectiveRowsT = buildEffectRows(rows);

			List<Bodypape> bodyPapes = new ArrayList<>();

			// 保存
			for (String[] rowT : effectiveRowsT) {

				int[] reqA = new int[] { 1, 2, 3, 9 };

				for (int j : reqA) {

					// 必填项检测
					if (rowT[j - 1].isEmpty()) {

						throw new DJException("必填项为空");

					}

				}

				Bodypape bodyPape = new Bodypape();

				bodyPape.setFname(rowT[0]);
				bodyPape.setFmanufacturer(rowT[1]);
				bodyPape.setFgramweight(Double.parseDouble(rowT[2]));
				bodyPape.setFerror(Double.parseDouble(rowT[3]));

				bodyPape.setFpopstrength(Double.parseDouble(rowT[4]));
				bodyPape.setFhardfold(Double.parseDouble(rowT[5]));
				bodyPape.setFwaterabsorption(Double.parseDouble(rowT[6]));
				bodyPape.setFbasepapercategory(rowT[7]);

				
				if (!usecategoryMap.containsKey(rowT[8])){
					
					throw new DJException("适用类型错误");
					
				}
									
				bodyPape.setFusecategory(usecategoryMap.get(rowT[8]));
				bodyPape.setFremark(rowT[9]);

				Date nowT = new Date();
				
				bodyPape.setFcreatetime(nowT);
				bodyPape.setFupdatetime(nowT);
				
				bodyPapes.add(bodyPape);
			}

			bodypapeDao.ExecSaveBodyPapes(bodyPapes);

			reponse.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	private List<String[]> buildEffectRows(List<String[]> rows) {
		// TODO Auto-generated method stub

		List<String[]> rowsResult = new ArrayList<>();

		for (String[] row : rows) {

			if (!isEmpty(row)) {

				rowsResult.add(row);

			}

		}

		return rowsResult;
	}

	private boolean isEmpty(String[] row) {
		// TODO Auto-generated method stub

		int emptyCount = 0;

		for (String cell : row) {

			if (cell.isEmpty()) {

				emptyCount++;

			}

		}

		return emptyCount == row.length;
	}

	@RequestMapping("enableBodyPape")
	public String enableBodyPape(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String fids = request.getParameter("fids");

		String fidsS = "('" + fids.replace(",", "','") + "')";

		// 0,1
		String enable = request.getParameter("enable");

		try {

			String sql = String.format(
					" update t_sys_bodypape set fenable=%d WHERE fid in %s ",
					Integer.parseInt(enable), fidsS);

			sql = String.format(sql, fidsS);

			bodypapeDao.ExecBySql(sql);

			reponse.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/deleteBodypapes")
	public String deleteBodypapes(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String fids = request.getParameter("fidcls");

		String fidsS = "('" + fids.replace(",", "','") + "')";

		try {
			// 外键约束让其他表控制
			String sql = " DELETE FROM t_sys_bodypape WHERE fid in %s ";

			sql = String.format(sql, fidsS);

			bodypapeDao.ExecBySql(sql);

			reponse.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}

	/**
	 * 检验
	 * 
	 * @param cit
	 * @return
	 * 
	 * @date 2015-1-31 下午4:40:29 (ZJZ)
	 */
	public boolean verifyHeader(Iterator<Cell> cit) {
		String[] name = { "序号", "原纸名称", "生产厂家", "克重", "误差", "耐破", "耐折", "吸水",
				"原纸类型", "适用类型", "备注" };

		int i = 0;

		while (cit.hasNext()) {
			String a = ExcelUtils.getStringCellValue(cit.next());

			if (!a.equals(name[i++])) {

				return false;

			}
		}

		return true;

	}

}