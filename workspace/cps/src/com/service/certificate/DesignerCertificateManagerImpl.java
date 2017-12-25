package com.service.certificate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.certificate.DesignerCertificateInfoDao;
import com.model.certificate.DesignerCertificateInfo;
import com.service.IBaseManagerImpl;
@Service("DesignerCertificateManager")
@Transactional(rollbackFor = Exception.class)
public class DesignerCertificateManagerImpl extends IBaseManagerImpl<DesignerCertificateInfo, java.lang.Integer> implements DesignerCertificateManager {

	@Autowired
	private DesignerCertificateInfoDao designerCertificateInfoDao;
	
	@Override
	protected IBaseDao<DesignerCertificateInfo, Integer> getEntityDao() {
		return this.designerCertificateInfoDao;
	}

	@Override
	public DesignerCertificateInfo getDesignerCertificateInfoByUserId(
			String userId) {
		return designerCertificateInfoDao.getDesignerCertificateInfoByUserId(userId);
	}
}
