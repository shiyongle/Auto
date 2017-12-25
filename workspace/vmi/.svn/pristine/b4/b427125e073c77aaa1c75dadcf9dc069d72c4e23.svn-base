package Com.Base.Util.Excel;

import org.apache.commons.lang.StringUtils;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.ExcelData;
import Com.Entity.order.Cusdelivers;

public class BaseExcelDataSupport extends ExcelDataSupport {

	public BaseExcelDataSupport(ExcelData excelData,IBaseDao baseDao) {
		super(excelData,baseDao);
	}

	@Override
	public void init() {
		tempObj = new Cusdelivers(currentRow);
	}


	@Override
	public void saveCellData(String ref, String value) {
		if(fixedCellMap.containsKey(ref)){
			defaultValueMap.put(fixedCellMap.get(ref), value);
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
			}
		}
	}

	@Override
	public boolean saveRowData() {
		return this.add(tempObj);
	}
	

}
