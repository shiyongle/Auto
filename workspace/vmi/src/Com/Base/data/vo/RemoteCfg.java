package Com.Base.data.vo;

/**
 * 用于excel批量黏贴获取隐藏域的vo
 * 
 *
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2015-1-6 下午2:03:18
 */
public class RemoteCfg {

	private String beanName;
	private String sourceField;
	private String sourceFieldValue;
	private String goalField ;
	private String tip;
	private String goalDataIndex;
	private String sourceFieldInBean;


	public RemoteCfg(String beanName, String sourceField,
			String sourceFieldValue, String goalField, String tip,
			String goalDataIndex, String sourceFieldInBean) {
		super();
		this.beanName = beanName;
		this.sourceField = sourceField;
		this.sourceFieldValue = sourceFieldValue;
		this.goalField = goalField;
		this.tip = tip;
		this.goalDataIndex = goalDataIndex;
		this.sourceFieldInBean = sourceFieldInBean;
	}



	public String getSourceFieldInBean() {
		return sourceFieldInBean;
	}



	public void setSourceFieldInBean(String sourceFieldInBean) {
		this.sourceFieldInBean = sourceFieldInBean;
	}



	public String getGoalDataIndex() {
		return goalDataIndex;
	}




	public void setGoalDataIndex(String goalDataIndex) {
		this.goalDataIndex = goalDataIndex;
	}




	public String getBeanName() {
		return beanName;
	}



	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}



	public String getSourceField() {
		return sourceField;
	}



	public void setSourceField(String sourceField) {
		this.sourceField = sourceField;
	}



	public String getSourceFieldValue() {
		return sourceFieldValue;
	}



	public void setSourceFieldValue(String sourceFieldValue) {
		this.sourceFieldValue = sourceFieldValue;
	}



	public String getGoalField() {
		return goalField;
	}



	public void setGoalField(String goalField) {
		this.goalField = goalField;
	}



	public String getTip() {
		return tip;
	}



	public void setTip(String tip) {
		this.tip = tip;
	}



	public RemoteCfg() {
		// TODO Auto-generated constructor stub
	}

}
