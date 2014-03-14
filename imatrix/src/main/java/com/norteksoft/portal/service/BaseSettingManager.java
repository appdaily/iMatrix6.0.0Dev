package com.norteksoft.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.norteksoft.portal.dao.BaseSettingDao;
import com.norteksoft.portal.entity.BaseSetting;

@Service
@Transactional
public class BaseSettingManager {
	@Autowired
	private BaseSettingDao baseSettingDao;
	
	/**
	 * 保存
	 * @param message
	 */
	public void saveMessage(BaseSetting baseSetting){
		this.baseSettingDao.save(baseSetting);
	}

	public BaseSetting getBaseSettingByCreatorId() {
		return baseSettingDao.getBaseSettingByCreatorId();
	}
	
	public BaseSetting getBaseSettingByLonginName(Long creatorId,Long companyId) {
		return baseSettingDao.getBaseSettingByCreatorId(creatorId,companyId);
	}
}
