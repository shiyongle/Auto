package com.dao.certificate;

import java.util.HashMap;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.certificate.DesignerCertificateInfo;
import com.model.customer.Customer;

public interface DesignerCertificateInfoDao extends IBaseDao<DesignerCertificateInfo, java.lang.Integer> {

	DesignerCertificateInfo getDesignerCertificateInfoByUserId(String userId);
	
}
