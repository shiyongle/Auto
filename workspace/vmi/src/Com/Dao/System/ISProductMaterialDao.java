package Com.Dao.System;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.SupplierDeliverTime;
import Com.Entity.order.Productdemandfile;

public interface ISProductMaterialDao extends IBaseDao {
		public void saveSProduct(HttpServletRequest request);
		public Productdemandfile saveFileOfSProduct(HttpServletRequest request);		   
		public void saveBoardtoCustpdt(HttpServletRequest request);
		public void updateCustpdtByboard(HttpServletRequest request);
		public void saveSupplierDeliverTimeConfig(SupplierDeliverTime obj);
}
