package Com.Base.Util.Excel;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.ExcelData;
import Com.Entity.System.ExcelDataEntry;
import Com.Entity.System.ExcelDataTypeEntry;
import Com.Entity.order.Cusdelivers;

public abstract class ExcelDataSupport {
	protected int startRow;
	protected String endText;
	protected int currentRow;
	protected boolean over;
	protected boolean starting;
	protected int blankRow;
	protected HashMap<String, String> defaultValueMap = new HashMap<>();	//field-value
	protected HashMap<String, String> fixedCellMap = new HashMap<>();		//ref-field
	protected HashMap<String, String> columnMap = new HashMap<>();			//ref-field
	protected List<ExcelDataTypeEntry> typeDataList; 
	protected List<Cusdelivers> data;
	protected Cusdelivers tempObj;
	protected Collection<Cusdelivers> tempList;
	protected HashMap<String,Cusdelivers> tempMap;
	protected IBaseDao baseDao;
	protected static String[] requiredField = {"famount","farrivetime","ftype"};
	protected static HashMap<String, String> fieldMap;
	
	static{
		fieldMap = new HashMap<>();
		fieldMap.put("fnumber","采购订单号");
		fieldMap.put("farrivetime","发放配送时间");
		fieldMap.put("flinkman","联系人");
		fieldMap.put("flinkphone","联系电话");
		fieldMap.put("famount","发放配送数量");
		fieldMap.put("fmaktx","发放产品名称");
		fieldMap.put("freqaddress","发放配送地址");
		fieldMap.put("fmaksupplier","发放制造商");
		fieldMap.put("ftype","类型");
		fieldMap.put("fdescription","备注");
	}
	
	public ExcelDataSupport(ExcelData excelData,IBaseDao baseDao) {
		data = new ArrayList<>();
		startRow = excelData.getFstartrow();
		endText = excelData.getFendtext();
		this.baseDao = baseDao;
		String hql = "from ExcelDataEntry where fparentid='"+excelData.getFid()+"' and (fdatacolumn!='' or ffixedcell!='' or ffixedvalue!='')";
		List<ExcelDataEntry> entryList = baseDao.QueryByHql(hql);
		for(ExcelDataEntry excelDataEntry : entryList){
			if(!StringUtils.isEmpty(excelDataEntry.getFfixedvalue())){
				defaultValueMap.put(excelDataEntry.getFtargetfieldvalue(), excelDataEntry.getFfixedvalue());
			}else if(!StringUtils.isEmpty(excelDataEntry.getFfixedcell())){
				fixedCellMap.put(excelDataEntry.getFfixedcell(), excelDataEntry.getFtargetfieldvalue());
			}else{	//数据列允许重复（配送时间和类型的重复）
				String dataColumn = excelDataEntry.getFdatacolumn();
				if(columnMap.containsKey(dataColumn)){
					columnMap.put(dataColumn, columnMap.get(dataColumn)+"-"+excelDataEntry.getFtargetfieldvalue());
				}else{
					columnMap.put(dataColumn, excelDataEntry.getFtargetfieldvalue());
				}
			}
		}
		if(!defaultValueMap.containsKey("ftype")){
			hql = "from ExcelDataTypeEntry where fparentid='"+excelData.getFid()+"' order by ftype desc";	//先判断备货
			typeDataList = baseDao.QueryByHql(hql);
		}
	}
	
	public int getDataLength() {
		return data.size();
	}
	
	public boolean isEnd(){
		return over || blankRow>=5;
	}
	
	public boolean isStart(int currentRow){
		this.currentRow = currentRow;
		return starting = currentRow >=startRow && !isEnd();
	}
	
	public boolean add(Cusdelivers obj){
		if(obj!=null && !StringUtils.isEmpty(obj.getFmaktx())){
			data.add(obj);
			return true;
		}
		return false;
	}
	
	public void destoryTemp(){
		tempObj = null;
		tempList = null;
		tempMap = null;
	}
	
	public void verifyData(Cusdelivers obj){
		String[] requiredField = ExcelDataSupport.requiredField;
		Object fieldValue;
		for(String field : requiredField){
			try {
				fieldValue = BeanUtils.getProperty(obj, field);
			} catch (Exception e){
				throw new RuntimeException(e);
			}
			if(fieldValue==null || "".equals(fieldValue)){
				throw new DJException(fieldMap.get(field)+"是必填项，请将第"+obj.getCurrentRow()+"行数据填写完整！");
			}
		}
	}
	
	public void parseCell(Cell cell){
		String ref = ExcelUtils.getCellReference(cell);
		String value = ExcelUtils.getStringCellValue(cell);
		if(!StringUtils.isEmpty(endText) && endText.equals(value)){
			over = true;
			starting = false;
		}
		saveCellData(ref,value);
	}
	
	public void processRowData(){
		if(!starting){
			return;
		}
		if(saveRowData()){
			blankRow = 0;
		}else{
			blankRow++;
		}
		destoryTemp();
	}
	
	public int insertData(HashMap<String, String> info){
		beforeInsertData();
		defaultValueMap.putAll(info);
		Set<String> fieldSet = defaultValueMap.keySet();
		String sql;
		List<HashMap<Object,String>> list;
		handleFixedValue(data);
		int count=0;
		Calendar calendar;
		for(Cusdelivers obj : data){
			for(String fieldName : fieldSet){
				setProperty(obj, fieldName, defaultValueMap.get(fieldName));
			}
			verifyData(obj);
			if(obj.getFamount()==0){
				continue;
			}
			if("2".equals(obj.getFtype())){
				throw new DJException("第"+obj.getCurrentRow()+"行数据的“类型”不存在或格式不正确，请更改！");
			}
			//自动匹配地址
			sql = "SELECT * FROM (SELECT fcustomerid,fid faddressid FROM t_bd_address UNION SELECT fcustomerid,faddressid FROM `t_bd_custrelationadress`) a WHERE a.fcustomerid='"+obj.getFcustomerid()+"'";
			list = baseDao.QueryBySql(sql);
			if(list.size()==1){
				obj.setFaddress(list.get(0).get("faddressid"));
			}
			//自动匹配客户产品
			sql  = "select fid from t_bd_custproduct where fproductmatching='"+obj.getFmaktx()+"' and fcustomerid='"+obj.getFcustomerid()+"' and feffect = 1 ";
			list = baseDao.QueryBySql(sql);
			if(list.size()==1){
				obj.setFcusproduct(list.get(0).get("fid"));
			}
			sql  = "select fid from t_sys_supplier where fname='"+obj.getFmaksupplier()+"'";
			list = baseDao.QueryBySql(sql);
			if(list.size()==1){
				obj.setFsupplierid(list.get(0).get("fid"));
			}
			String fid = baseDao.CreateUUid();
			obj.setFid(fid);
			obj.setFcusfid(fid);
			obj.setFreqdate(obj.getFarrivetime());
			calendar = Calendar.getInstance();
			calendar.setTime(obj.getFarrivetime());
			calendar.add(Calendar.HOUR_OF_DAY, 9);
			obj.setFarrivetime(calendar.getTime());
			obj.setFcreatetime(new Date());
			baseDao.saveOrUpdate(obj);
			count++;
		}
		return count;
	}
	
	public void setProperty(Cusdelivers obj,String fieldName,String value){
        try {
        	Class<?> type = Cusdelivers.class.getDeclaredField(fieldName).getType();
        	if(!Date.class.isAssignableFrom(type)){
        		try {
        			if("ftype".equals(fieldName)){
        				value = transformTypeValue(value);
        			}else if("famount".equals(fieldName)&&!"".equals(value)){
        				value = (int)Double.parseDouble(value.replace(",", ""))+"";
        			}
					BeanUtils.setProperty(obj, fieldName, value);
				} catch (Exception e) {
					throw new DJException("第"+obj.getCurrentRow()+"行数据的“"+fieldMap.get(fieldName)+"”不存在或格式不正确，请更改！");
				}
        	}else{
        		//需优化...
        		PropertyDescriptor propDesc = new PropertyDescriptor(fieldName,Cusdelivers.class);
        		Method method=propDesc.getWriteMethod();
        		try {
					method.invoke(obj, ExcelUtils.convertStringToDate(value));
				} catch (Exception e){
					try {
						method.invoke(obj, HSSFDateUtil.getJavaDate(Double.valueOf(value)));
					} catch (Exception e1) {
						throw new DJException("第"+obj.getCurrentRow()+"行数据的“日期格式”不正确，请更改！");
					}
				}
        	}
		}catch(DJException e){
			throw e;
		}catch(Exception e1){
			throw new RuntimeException(e1);
		}
    }
	
	protected String transformTypeValue(String value) {
		if(value==null){
			throw new RuntimeException("CusDelivers状态值转换异常！");
		}
		value = value.trim();
		if("0".equals(value)||"1".equals(value)){
			return value;
		}
		if("要货".equals(value)){
			return "0";
		}
		if("备货".equals(value)){
			return "1";
		}
		String setValue;
		String ftype;
		for(ExcelDataTypeEntry entry : typeDataList){
			setValue = entry.getFsetvalue();
			ftype = String.valueOf(entry.getFtype());
			if("为空".equals(setValue) && "".equals(value)){
				return ftype;
			}else if("不为空".equals(setValue) && !"".equals(value)){
				return ftype;
			}else if("固定值".equals(setValue)&& value.equals(entry.getFsettext())){
				return ftype;
			}
		}
		return "2";		//错误类型
//		throw new DJException("类型值不正确，请检查或联系平台设置“数据导入设置”的类型列表");
	}
	/**
	 * 持久化数据前操作
	 */
	protected void beforeInsertData() {}
	/**
	 * 固定单元格格式为“a1_b1_c1”时重写
	 * @param data2	 取this.data
	 */
	protected void handleFixedValue(List<Cusdelivers> data2) {
		
	}
	
	/**
	 * 初始化临时对象
	 */
	public abstract void init();
	/**
	 * 添加行数据
	 * @return	该行有数据保存，则返回true
	 */
	public abstract boolean saveRowData();
	/**
	 * 保存单元格数据
	 * @param ref	单元格引用
	 * @param value	单元格值
	 */
	public abstract void saveCellData(String ref, String value);

	
}
