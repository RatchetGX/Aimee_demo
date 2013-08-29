package org.ratchetgx.aimee.module.jrgl.jrxm.service;

import org.ratchetgx.aimee.common.webbase.BaseService;
import org.ratchetgx.aimee.module.jrgl.jrxm.dao.JrxmDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JrxmService extends BaseService {
	
	@Autowired
	private JrxmDao jrglDao;
	
	public int getJrxmTotalCount() {
		
		return jrglDao.getJrxmTotalCount();
	}

}
