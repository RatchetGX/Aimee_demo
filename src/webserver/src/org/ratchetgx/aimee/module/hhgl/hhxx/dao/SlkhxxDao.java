package org.ratchetgx.aimee.module.hhgl.hhxx.dao;

import java.util.Map;

import org.ratchetgx.aimee.common.webbase.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class SlkhxxDao extends BaseDao {

	/**
	 *  保存客户信息(同时返回客户编号)
	 */
	public String recordCustomerToDB(Map sCustomerInfo) {
		String sKh_khbh = "";
		
		String sKh_khzh = (String)sCustomerInfo.get("kh_khzh");
		String sKh_openid = (String)sCustomerInfo.get("kh_openid");
		String sKh_khzhlx = (String)sCustomerInfo.get("kh_khzhlx");
		String sKh_nc = (String)sCustomerInfo.get("kh_nc");
		String sKh_xb = (String)sCustomerInfo.get("kh_xb");
		String sKh_cs = (String)sCustomerInfo.get("kh_cs");
		String sKh_yy = (String)sCustomerInfo.get("kh_yy");
		String sKh_xmbh = (String)sCustomerInfo.get("kh_xmbh");
		
		String sql = "SELECT count(*) as cnt FROM t_hhgl_slkhxx WHERE openid=?";
		int count = this.jt.queryForInt(sql, sKh_openid);
		if (count > 0) { /** 已存在此客户 */
			
			/** 查出客户编号 */
			sql = "SELECT khbh FROM t_hhgl_slkhxx WHERE openid=?";
			sKh_khbh = (String)this.jt.queryForObject(sql, String.class, sKh_openid);
			
		} else {  /** 客户第一次接入 */
			
			/** 查出客户编号 */
			sKh_khbh = getNewUuid();
			
			/** 新增入数据库 */
			sql = "INSERT INTO t_hhgl_slkhxx (khbh,khzh,openid,khzhlx,nc,xb,cs,yy,xmbh,ccslsj) "
				+ " VALUES(?,?,?,?,?,?,?,?,?,sysdate())";
		    count = this.jt.update(sql, sKh_khbh, sKh_khzh, sKh_openid, sKh_khzhlx, sKh_nc, sKh_xb, sKh_cs, sKh_yy, sKh_xmbh);
		    
		}
		return sKh_khbh;
	}
	
}
