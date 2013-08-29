package org.ratchetgx.aimee.system.dao;

import java.util.Map;

import org.ratchetgx.aimee.common.webbase.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Anthony
 *
 */
@Repository
public class ZxkfDao extends BaseDao {

	
	/**
	 *  校验客服是否存在
	 */
	public boolean verifyKfExist(String username) {
		
		String sql = "select count(*) as cnt from t_zxkf_kfxx where yxzt='1' and (kfhm=? or dzyx=? or sjh=?)";
		int count = this.jt.queryForInt(sql, username, username, username);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 校验客服密码是否正确
	 */
	public boolean verifyKfPassword(String username, String password) {
		
		String sql = "select count(*) as cnt from t_zxkf_kfxx where yxzt='1' and (kfhm=? or dzyx=? or sjh=?) and kfmm=?";
		int count = this.jt.queryForInt(sql, username, username, username, password);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取客服信息
	 */
	public Map getKfInfo(String username) {
		
		String sql = "select * from t_zxkf_kfxx where yxzt='1' and (kfhm=? or dzyx=? or sjh=?) limit 1";
		Map kfInfo = this.jt.queryForMap(sql, username, username, username);
		return kfInfo;
	}
	
}
