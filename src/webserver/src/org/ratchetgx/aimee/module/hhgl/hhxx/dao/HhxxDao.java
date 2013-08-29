package org.ratchetgx.aimee.module.hhgl.hhxx.dao;

import java.util.Map;

import org.ratchetgx.aimee.common.webbase.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class HhxxDao extends BaseDao {

	/**
	 *  获取最近受理的客服信息
	 */
	public Map getZjslKfxx(String sKh_khbh) {
		
		Map kfxxMap = null;
		
		String sql = "SELECT count(*) as cnt FROM t_hhgl_hhxx WHERE khbh=? ORDER BY hhkssj DESC limit 1";
		int count = this.jt.queryForInt(sql, sKh_khbh);
		if (count > 0) {
			sql = "SELECT hhbh,khbh,kfbh FROM t_hhgl_hhxx WHERE khbh=? ORDER BY hhkssj DESC limit 1";
			kfxxMap = this.jt.queryForMap(sql, sKh_khbh);
		}
		return kfxxMap;
		
	}
	
	/**
	 *  保存会话-消息到数据库
	 */
	public boolean recordMsgToDB(Map sMsgInfo) {
		boolean bSuccess = false;
		
		String sKh_khbh = (String)sMsgInfo.get("kh_khbh");
		String sKf_kfbh = (String)sMsgInfo.get("kf_kfbh");
		String sMsg_msgid = (String)sMsgInfo.get("msg_id");
		String sMsg_msgtype = (String)sMsgInfo.get("msg_type");
		String sMsg_content = (String)sMsgInfo.get("msg_content");
		String sMsg_picurl = (String)sMsgInfo.get("msg_picurl");
		String sMsg_mediaid = (String)sMsgInfo.get("msg_mediaid");
		String sMsg_sendtime = (String)sMsgInfo.get("msg_sendtime");
		String sMsg_sendstatus = (String)sMsgInfo.get("msg_sendstatus");
		
		
		boolean bNewHh = false;  /** 本次为新的会话 */
		String sHh_hhbh = null;
		
		String sql = "SELECT count(*) as cnt FROM t_hhgl_hhxx WHERE khbh=?";
		int count = this.jt.queryForInt(sql, sKh_khbh);
		if (count > 0) { /** 该用户存在会话信息 */
			Map hhxxMap = null;
			
			/** 查出该用户最近一次会话对应的客服编号 */
			sql = "SELECT hhbh,khbh,kfbh,hhkssj FROM t_hhgl_hhxx WHERE khbh=? ORDER BY hhkssj DESC limit 1";
			hhxxMap = this.jt.queryForMap(sql, sKh_khbh);
			String kfbh = (String)hhxxMap.get("kfbh");
			if (kfbh.equals(sKf_kfbh)) {  /** 本次受理的客服与最近一次会话的客服相同 */
				
				sHh_hhbh = (String)hhxxMap.get("hhbh");
				bNewHh = false;
				
			} else { /** 不同 */
				
				/** 产生一个新的会话编号 */
				sHh_hhbh = getNewUuid();
				bNewHh = true;
				
			}
			
		} else {  /** 客户第一次接入 */
			
			/** 产生一个新的会话编号 */
			sHh_hhbh = getNewUuid();
			bNewHh = true;
			
		}
		
		/** 将消息新增到会话表 */
		if (bNewHh) {  /** 新的会话 */
			
			/** 将信息新增到会话表 */
			sql = "INSERT INTO t_hhgl_hhxx (hhbh,khbh,kfbh,hhkssj) "
					+ " VALUES(?,?,?,sysdate())";
			count = this.jt.update(sql, sHh_hhbh, sKh_khbh, sKf_kfbh);
			
		}
		
		/** 将信息保存到消息表 */
		sql = "INSERT INTO t_hhgl_xxxx (xxbh,hhbh,weixin_msgid,xxnr,xxlx,weixin_picurl,weixin_mediaid,xxfx,fszt,fssj) "
				+ " VALUES(uuid(),?,?,?,?,?,?,'1',?,sysdate())";
		count = this.jt.update(sql, sHh_hhbh, sMsg_msgid, sMsg_content, sMsg_msgtype, sMsg_picurl, sMsg_mediaid, sMsg_sendstatus);
		
		return bSuccess;
	}
	
}
