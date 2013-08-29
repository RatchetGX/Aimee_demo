package org.ratchetgx.aimee.module.hhgl.hhxx.service;

import java.util.Map;

import org.ratchetgx.aimee.common.webbase.BaseService;
import org.ratchetgx.aimee.module.hhgl.hhxx.dao.SlkhxxDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlkhxxService extends BaseService {

	@Autowired
	private SlkhxxDao slkhxxDao;
	
	/**
	 *  保存客户信息(同时返回客户编号)
	 */
	public String recordCustomerToDB(Map sCustomerInfo) {
		
		return slkhxxDao.recordCustomerToDB(sCustomerInfo);
		
	}
	
}
