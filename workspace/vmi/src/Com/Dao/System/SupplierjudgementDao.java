package Com.Dao.System;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;
import Com.Base.Util.SpringContextUtils;
import Com.Entity.System.Supplier;
import Com.Entity.System.Supplierjudgement;
import Com.Entity.System.Supplierjudgementrpt;
import Com.Entity.System.Supplierjudgeproject;
import Com.Entity.System.Takenumberformula;
import Com.Entity.System.Useronline;
@Service
public class SupplierjudgementDao extends BaseDao implements ISupplierjudgementDao {
	private ISupplierjudgeprojectDao SupplierjudgeprojectDao = (ISupplierjudgeprojectDao)SpringContextUtils.getBean("supplierjudgeprojectDao");
	private ITakenumberformulaDao takenumberformulaDao = (ITakenumberformulaDao) SpringContextUtils.getBean("takenumberformulaDao");
	
	private static final String SELECT_BASE = " SELECT fid, fnumber, fname, frate FROM t_sys_supplierJudgement ";

	@Override
	public HashMap<String, Object> ExecSave(Supplierjudgement judge) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (judge.getFid().isEmpty()) {
			judge.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(judge);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Supplierjudgement Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Supplierjudgement.class, fid);
	}

	@Override
	public ListResult QueryselectSupplierjudgements(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String sql = SELECT_BASE;
		
		ListResult lrT = QueryFilterList(sql, request);
		
		return lrT;
	}

	@Override
	public ListResult QueryselectSupplierjudgementByID(
			HttpServletRequest request, String id) {
		// TODO Auto-generated method stub
		
		String sql = SELECT_BASE + " where fid = '%s' ";
		
		sql = String.format(sql, id);
		
		ListResult lrT = QueryFilterList(sql, request);
		
		return lrT;
	}

	@Override
	public void ExecDeleteSupplierjudgements(String[] ids) {
		// TODO Auto-generated method stub
		
		for ( String id : ids ) {
			
			Supplierjudgement spjm = Query(id);
			
			Delete(spjm);
			
		}
		
	}

	@Override
	public void SaveAssessmentResult(
			HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		try{
			String type = request.getParameter("type");
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			Calendar currentCalendar = Calendar.getInstance();
			int year = currentCalendar.get(Calendar.YEAR);
			int month = currentCalendar.get(Calendar.MONTH);
			
			if(!type.equals("") && type.equals("0")){//修改结果值
				try{
					List sprpts = QueryByHql("from Supplierjudgementrpt where fid = '"+request.getParameter("fid")+"'");
					if(sprpts.size()>0){
						Supplierjudgementrpt sprptinfo = (Supplierjudgementrpt)sprpts.get(0);
						Double fresultValue = Double.valueOf(request.getParameter("fresultValue"));
						sprptinfo.setFresultValue(fresultValue);
						saveOrUpdate(sprptinfo);
					}
				}catch(Exception sql){
					throw new DJException("修改结果值失败，填写数据有误！");
				}
				
			}else if(!type.equals("") && type.equals("1")){//生成评价结果
				try{
					List suppliers = QueryByHql("from Supplier ");
					List supplierJudgeProjects = QueryByHql("from Supplierjudgeproject ");
					List<Supplierjudgementrpt> spjrptlist = new ArrayList<Supplierjudgementrpt>();
					Supplierjudgementrpt spjrptinfo ;
					for(int i=0;i<suppliers.size();i++) {
						Supplier supplier = (Supplier)suppliers.get(i);
						
						for(int j=0;j<supplierJudgeProjects.size();j++){
							Supplierjudgeproject supplierjudgeproduct = (Supplierjudgeproject) supplierJudgeProjects.get(j);
							
							spjrptinfo = new Supplierjudgementrpt();
							spjrptinfo.setFcreatetime(currentCalendar.getTime());
							spjrptinfo.setFcreatorid(userid);
							spjrptinfo.setFid(CreateUUid());
							spjrptinfo.setFsupplierid(supplier.getFid());
							
							spjrptinfo.setFyear(year);
							spjrptinfo.setFmonth(month);
							spjrptinfo.setFsupplierJudgementid(supplierjudgeproduct.getFsupplierJudgementId());
							spjrptinfo.setFsupplierJudgeProjectid(supplierjudgeproduct.getFid());
							
							spjrptinfo.setFresultValue(0.0);
							spjrptinfo.setFjudgeProjectScore(0.0);
							spjrptinfo.setFjudgementScore(0.0);
							spjrptinfo.setFendScore(0.0);
							
							spjrptlist.add(spjrptinfo);
						}
					}
					
					for(int k=0;k<spjrptlist.size();k++){
						Supplierjudgementrpt info = spjrptlist.get(k);
						saveOrUpdate(info);
					}
				}catch(Exception e){
					throw new DJException("生成评价结果失败！");
				}
				
			}else if(!type.equals("") && type.equals("2")){//计算结果值
				List sprpts = QueryByHql("from Supplierjudgementrpt where fyear = "+year+" and fmonth = "+month);
				for(int i=0;i<sprpts.size();i++){
					Supplierjudgementrpt sprptinfo = (Supplierjudgementrpt)sprpts.get(i);
					
					if(sprptinfo.getFresultValue() == null || sprptinfo.getFresultValue() <= 0){
						Supplierjudgeproject sptinfo = SupplierjudgeprojectDao.Query(sprptinfo.getFsupplierJudgeProjectid());
						Takenumberformula tnfinfo = takenumberformulaDao.Query(sptinfo.getFtakeNumberFormulaId());
						if(tnfinfo.getFsqlStatement()==null || tnfinfo.getFsqlStatement().equals("")){
							throw new DJException("计算结果值失败，评估项目取数公式不能为空！");
						}else if(!tnfinfo.getFsqlStatement().contains("select")){
							throw new DJException("计算结果值失败，评估项目取数公式SQL错误！");
						}
						try{
							List<HashMap<String, Object>> resultValues = QueryBySql(tnfinfo.getFsqlStatement());
							if (resultValues.size() > 0) {
								Double fresultValue = Double.valueOf(resultValues.get(0).get("resultValue").toString());
								sprptinfo.setFresultValue(fresultValue);
								saveOrUpdate(sprptinfo);
							}
						}catch(Exception sql){
							throw new DJException("计算结果值失败，评估项目取数公式SQL错误！");
						}
					}
				}
			}else if(!type.equals("") && type.equals("3")){//计算得分
				List sprpts = QueryByHql("from Supplierjudgementrpt where fyear = "+year+" and fmonth = "+month);
				for(int i=0;i<sprpts.size();i++){
					Supplierjudgementrpt sprptinfo = (Supplierjudgementrpt)sprpts.get(i);
					Supplierjudgeproject sptinfo = SupplierjudgeprojectDao.Query(sprptinfo.getFsupplierJudgeProjectid());
					
					if(sprptinfo.getFresultValue() != null || sprptinfo.getFresultValue() > 0){
						try{
							if(sprptinfo.getFresultValue()!=null && sprptinfo.getFresultValue()>0){
								List<HashMap<String, Object>> sjpentry = QueryBySql("select fhighlimit,flowlimit,fscore from t_sys_supplierjudgeprojectentry where fparentid='"+sprptinfo.getFsupplierJudgeProjectid()+"'");
								Double fjudgeProjectScore = 0.0;
								Double fjudgementScore = 0.0;
								Double fendScore = 0.0;
								for(int j=0;j<sjpentry.size();j++) {
									Double fhighlimit = Double.valueOf(sjpentry.get(j).get("fhighlimit").toString());
									Double flowlimit = Double.valueOf(sjpentry.get(j).get("flowlimit").toString());
									Double fscore = Double.valueOf(sjpentry.get(j).get("fscore").toString());
									if(sprptinfo.getFresultValue()>=flowlimit && sprptinfo.getFresultValue()<=fhighlimit){
										fjudgeProjectScore = fscore;
									}
								}
								sprptinfo.setFjudgeProjectScore(fjudgeProjectScore);
								fjudgementScore = fjudgeProjectScore * sptinfo.getFrate();
								sprptinfo.setFjudgementScore(fjudgementScore);
								Supplierjudgement sjinfo = Query(sptinfo.getFsupplierJudgementId());
								fendScore = fjudgementScore * sjinfo.getFrate();
								sprptinfo.setFendScore(fendScore);
								saveOrUpdate(sprptinfo);
							}
						}catch(Exception sql){
							throw new DJException("计算得分失败，请检查评估项目区间上下限得分数据！");
						}
					}
					
				}
			}
			
			List supplierJudgements = QueryByHql("from Supplierjudgement ");
			
		}catch(Exception e){
			throw new DJException(e.getMessage());
		}
	}
	
}