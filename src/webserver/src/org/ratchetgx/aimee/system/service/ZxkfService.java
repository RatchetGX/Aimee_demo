package org.ratchetgx.aimee.system.service;

import java.util.Map;

import org.ratchetgx.aimee.common.webbase.BaseService;
import org.ratchetgx.aimee.system.dao.ZxkfDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZxkfService extends BaseService {
	
	@Autowired
	private ZxkfDao zxkfDao;
	
	/**
	 *  校验客服是否存在
	 */
	public boolean verifyKfExist(String username) {
		return zxkfDao.verifyKfExist(username);
	}
	
	/**
	 * 校验密码是否正确
	 */
	public boolean verifyKfPassword(String username, String password) {
		return zxkfDao.verifyKfPassword(username, password);
	}
	
	/**
	 * 获取客服信息
	 */
	public Map getKfInfo(String username) {
		return zxkfDao.getKfInfo(username);
	}
}
