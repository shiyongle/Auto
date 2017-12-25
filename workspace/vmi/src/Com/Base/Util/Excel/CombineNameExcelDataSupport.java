package Com.Base.Util.Excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.ExcelData;
import Com.Entity.order.Cusdelivers;

public class CombineNameExcelDataSupport extends ExcelDataSupport {

	private HashMap<String, Object> cellValuesMap;						// 获取某行，列的引用-值
	private Set<String> columnRefList = new HashSet<>();				// cellValuesMap.keySet()
	private HashMap<String, Object> fixedCellValues = new HashMap<>();	// 单元格的引用-值
	private List<String> columnCombine = new ArrayList<>();				// 获取 含"&"的列
	private List<String> fixedCombine = new ArrayList<>();				// 获取 含"&"的固定单元格
	
	public CombineNameExcelDataSupport(ExcelData excelData, IBaseDao baseDao) {
		super(excelData, baseDao);
		Set<String> set = columnMap.keySet();
		for(String column: set){
			if(column.contains("&")){
				columnCombine.add(column);
				for(String c: column.split("&")){
					columnRefList.add(c);
				}
			}
		}
		set = fixedCellMap.keySet();
		for(String ref: set){
			if(ref.contains("&")){
				fixedCombine.add(ref);
				for(String c: ref.split("&")){
					fixedCellValues.put(c, "");
				}
			}
		}
	}

	@Override
	public void init() {
		tempObj = new Cusdelivers(currentRow);
		cellValuesMap = new HashMap<>();
	}


	@Override
	public boolean saveRowData() {
		String value;
		String field;
		for(String refs: columnCombine){
			value = "";
			field = columnMap.get(refs);
			for(String ref: refs.split("&")){
				if(!value.isEmpty()&&!value.endsWith(" ")){
					value += " ";
				}
				value += cellValuesMap.get(ref)!=null?cellValuesMap.get(ref):"";
			}
			value = value.trim();
			if(!field.contains("-")){
				setProperty(tempObj, field, value);
			}else{
				for(String f: field.split("-")){
					setProperty(tempObj, f, value);
				}
			}
		}
		return this.add(tempObj);
	}

	@Override
	public void saveCellData(String ref, String value) {
		if(fixedCellMap.containsKey(ref)){
			defaultValueMap.put(fixedCellMap.get(ref), value);
			return;
		}else if(fixedCellValues.containsKey(ref)){
			fixedCellValues.put(ref, value);
			return;
		}
		if(starting){
			ref = StringUtils.substringBefore(ref, String.valueOf(currentRow));
			if(columnMap.containsKey(ref)){
				String field = columnMap.get(ref);
				if(!field.contains("-")){
					setProperty(tempObj, field, value);
				}else{
					String[] fields = field.split("-");
					for(String f: fields){
						setProperty(tempObj, f, value);
					}
				}
			}else if(columnRefList.contains(ref)){
				cellValuesMap.put(ref, value);
			}
		}
	}
	
	@Override
	public void beforeInsertData() {
		String value;
		String field;
		for(String refs: fixedCombine){
			value = "";
			field = fixedCellMap.get(refs);
			for(String ref: refs.split("&")){
				if(!value.isEmpty()&&!value.endsWith(" ")){
					value += " ";
				}
				value += fixedCellValues.get(ref)!=null?fixedCellValues.get(ref):"";
			}
			defaultValueMap.put(field, value.trim());
		}
	}
}
