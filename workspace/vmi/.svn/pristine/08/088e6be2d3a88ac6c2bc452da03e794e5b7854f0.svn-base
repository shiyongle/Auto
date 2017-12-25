package Com.Base.Util.Excel;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.ExcelData;
import Com.Entity.order.Cusdelivers;

public class MultiExcelDataSupport extends ExcelDataSupport {

	private String[] keyList = null;		//格式：$a$b$c2$
	private HashMap<String, String> cellRefValueMap = new HashMap<>();		//	格式：$ref-value
	public MultiExcelDataSupport(ExcelData excelData,IBaseDao baseDao) {
		super(excelData,baseDao);
		Set<String> columnSet = columnMap.keySet();
		Set<String> fixedSet = fixedCellMap.keySet();
		String[] ss;
		int length = 0;
		HashMap<String, String> addMap = new HashMap<>();
		for(String ref : columnSet){
			if(ref.contains("-")){
				throw new DJException("设置的数据列不能重复,请联系平台更改！");
			}
			if(ref.contains("_")){
				ss = ("$"+ref).replace("_", "_$").split("_");	//列前加$来和普通列区分
				for(String s : ss){
					addMap.put(s, columnMap.get(ref));
				}
				if(keyList==null){
					length = ss.length;
					keyList=ss;
				}else{
					if(length != ss.length){
						throw new DJException("基础设置的数据列中字符长度不一致，请联系平台设置！");
					}
					for(int i=0;i<length;i++){
						keyList[i] = keyList[i]+ss[i];
					}
				}
			}
		}
		columnMap.putAll(addMap);
		addMap.clear();
		for(String ref : fixedSet){
			if(ref.contains("_")){
				ss = ("$"+ref).replace("_", "_$").split("_");
				if(length != ss.length){
					throw new DJException("基础设置的“固定单元”的字符长度和“数据列”不一致，请联系平台设置！");
				}
				for(String s : ss){
					addMap.put(s, fixedCellMap.get(ref));
				}
				for(int i=0;i<length;i++){
					keyList[i] = keyList[i]+ss[i];
				}
			}
		}
		fixedCellMap.putAll(addMap);
		for(int i=0;i<length;i++){
			keyList[i] = keyList[i]+"$";
		}
		if(keyList == null){
			throw new DJException("调用错误类解析！");
		}
	}
	
	@Override
	protected void handleFixedValue(List<Cusdelivers> data) {
		String key;
		int group;
		Set<String> sKeys = cellRefValueMap.keySet();	//元素为$c2格式
		for(Cusdelivers obj : data){
			group = obj.getGroup();
			key = keyList[group];						//元素为$a$c2$c22$格式
			for(String sKey : sKeys){
				if(key.contains(sKey+"$")){
					setProperty(obj,fixedCellMap.get(sKey), cellRefValueMap.get(sKey));
				}
			}
		}
	}
	
	@Override
	public void init() {
		tempMap = new HashMap<>();
		for(int i=0;i<keyList.length;i++){
			tempMap.put(keyList[i], new Cusdelivers(currentRow,i));
		}
		tempList = tempMap.values();
	}

	@Override
	public void saveCellData(String ref, String value) {
		String $ref;
		if(fixedCellMap.containsKey(ref)){
			defaultValueMap.put(fixedCellMap.get(ref), value);
		}else{
			$ref = "$"+ref;
			if(fixedCellMap.containsKey($ref)){
				cellRefValueMap.put($ref, value);
			}
		}
		if(starting){
			String columnRef = StringUtils.substringBefore(ref, String.valueOf(currentRow));
			if(columnMap.containsKey(columnRef)){
				for(Cusdelivers obj : tempList){
					setProperty(obj, columnMap.get(columnRef), value);
				}
			}else{
				columnRef = "$"+columnRef;
				if(columnMap.containsKey(columnRef)){
					Set<String> refSet = tempMap.keySet();
					String exRef = columnRef + "$";
					for(String s : refSet){
						if(s.contains(exRef)){
							setProperty(tempMap.get(s), columnMap.get(columnRef), value);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean saveRowData() {
		boolean isAdd = false;
		for(Cusdelivers obj : tempList){
			if(this.add(obj)){
				isAdd = true;
			}
		}
		return isAdd;
	}
	
}
