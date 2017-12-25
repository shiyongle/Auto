package com.dao.certificate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dao.IBaseDaoImpl;
import com.model.certificate.DesignerCertificateInfo;

@Repository("DesignerCertificateInfoDao")
public class DesignerCertificateInfoImpl extends
		IBaseDaoImpl<DesignerCertificateInfo, java.lang.Integer> implements
		DesignerCertificateInfoDao {

	@Override
	public DesignerCertificateInfo getDesignerCertificateInfoByUserId(
			String userId) {
		String hql = "from DesignerCertificateInfo where fuserid = ? ";
		Query query = this.getSessionFactory().getCurrentSession()
				.createQuery(hql);
		query.setString(0, userId);
		List<DesignerCertificateInfo> list = query.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
