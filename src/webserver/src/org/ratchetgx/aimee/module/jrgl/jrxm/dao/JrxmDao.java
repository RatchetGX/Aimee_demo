package org.ratchetgx.aimee.module.jrgl.jrxm.dao;

import org.ratchetgx.aimee.common.webbase.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class JrxmDao extends BaseDao {

	public int getJrxmTotalCount() {
		
		String sql = "select 999 ";
		return this.jt.queryForInt(sql);
	}
}
