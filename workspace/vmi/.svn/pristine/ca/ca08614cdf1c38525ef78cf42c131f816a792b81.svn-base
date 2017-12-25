package Com.Base.Util.mySimpleUtil.excelpaster;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.BaseDao;

public abstract class ExcelPasterCommand {

	public ExcelPasterCommand() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 模板方法
	 *
	 * @param req
	 * @param baseDao
	 * @throws ClassNotFoundException
	 *
	 * @date 2014-12-23 上午8:56:11  (ZJZ)
	 */
	public void saveBeans(HttpServletRequest req, BaseDao baseDao) throws ClassNotFoundException {
		List<?> beans= ExcelPasterUntil.buildJavaBeans(req, baseDao);
		
		doOtherAction(beans);
		
		for (Object object : beans) {
			
			setId(object);
			
		}
		
		ExcelPasterUntil.saveJavaBeans(beans, baseDao);
		
	}

	/**
	 * 设置各个bean的主键
	 *
	 * @param bean
	 *
	 * @date 2014-12-23 上午10:56:44  (ZJZ)
	 */
	public abstract void setId(Object bean);

	/**
	 * 额外变动命令，开闭
	 *
	 * @param beans
	 *
	 * @date 2014-12-23 上午8:56:18  (ZJZ)
	 */
	protected abstract void doOtherAction(List<?> beans);
	
}
