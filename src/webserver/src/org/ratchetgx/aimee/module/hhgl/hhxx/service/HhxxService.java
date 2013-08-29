package org.ratchetgx.aimee.module.hhgl.hhxx.service;

import java.util.Map;

import org.ratchetgx.aimee.common.webbase.BaseService;
import org.ratchetgx.aimee.module.hhgl.hhxx.dao.HhxxDao;
import org.ratchetgx.aimee.module.hhgl.hhxx.dao.SlkhxxDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HhxxService extends BaseService {
	
	@Autowired
	private HhxxDao hhxxDao;

	/**
	 *  获取最近受理的客服信息
	 */
	public Map getZjslKfxx(String sKh_khbh) {
		
		return hhxxDao.getZjslKfxx(sKh_khbh);
		
	}
	
	/**
	 *  保存会话-消息到数据库
	 */
	public boolean recordMsgToDB(Map sMsgInfo) {
		
		return hhxxDao.recordMsgToDB(sMsgInfo);
		
	}
	
}
