package org.ratchetgx.aimee;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;
import org.ratchetgx.aimee.module.hhgl.hhxx.service.HhxxService;
import org.ratchetgx.aimee.module.hhgl.hhxx.service.SlkhxxService;
import org.ratchetgx.aimee.weixin.WeixinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Anthony
 *
 */
@Component
public class Service_weixin extends HttpServlet{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SlkhxxService slkhxxService;

	@Autowired
	private HhxxService hhxxService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String echostr = req.getParameter("echostr");
		
		log.info("service_weixin:echostr:" + echostr);
		
		resp.getWriter().write(echostr);
		//OutputStream out = resp.getOutputStream();
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			                                throws ServletException, IOException {
		
		log.info("service_weixin: coming!" );
		
		
		/** 获取POST数据 */
		StringBuffer postData = new StringBuffer();
		BufferedReader sis = request.getReader();
		char[] buf = new char[1024];
	    int len = 0;
		while((len = sis.read(buf))!= -1){
			postData.append(buf,0,len);
		}
		
		/** 微信传入的数据 */
		String sToUserName = "";  		//开发者微信号
		String sKh_khzh = "";			//发送方微信号
		String sKh_openid = "";			//发送方OpenID
		String sMsg_createtime = "";	//消息创建时间 （整型）
		String sMsg_msgtype = "";		//消息类型
		String sMsg_content = "";		//文本消息内容
		String sMsg_picurl = "";		//图片链接
		String sMsg_mediaid = "";		//媒体ID
		String sMsg_format = "";        //语音格式，如amr，speex等
		String sMsg_msgid = "";			//消息id，64位整型
		
		/** 解析微信传入的消息 */
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(postData.toString()); 
			Element root = doc.getRootElement(); 
			
			sToUserName = root.elementTextTrim("ToUserName");
			sKh_khzh = root.elementTextTrim("FromUserName");
			sKh_openid = root.elementTextTrim("FromUserName");
			sMsg_createtime = root.elementTextTrim("CreateTime");
			sMsg_msgtype = root.elementTextTrim("MsgType");
			if ("text".equals(sMsg_msgtype)) {
				sMsg_content = root.elementTextTrim("Content");
			} else if ("image".equals(sMsg_msgtype)) {
				sMsg_picurl = root.elementTextTrim("PicUrl");
			} else if ("voice".equals(sMsg_msgtype)) {
				sMsg_mediaid = root.elementTextTrim("MediaId");
				sMsg_format = root.elementTextTrim("Format");
			}
			sMsg_msgid = root.elementTextTrim("MsgId");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getStackTrace().toString());
		}
		
		
		/** 获取客户信息 */
		Map sUserInfo = WeixinService.getUserInfo(sKh_openid);
		if (sUserInfo == null) {
			log.info("获取用户信息失败，程序退出!");
			return;
		} 
		String sKh_nc = (String)sUserInfo.get("nickname");
		String sKh_xb = (String)sUserInfo.get("sex");
		String sKh_cs = (String)sUserInfo.get("city");
		String sKh_yy = (String)sUserInfo.get("language");
		String sKh_xmbh = "001";
		
		
		/** 保存客户信息到数据库(同时返回客户编号) */
		Map sCustomerInfo = new HashMap();
		sCustomerInfo.put("kh_khzh", sKh_khzh);
		sCustomerInfo.put("kh_openid", sKh_openid);
		sCustomerInfo.put("kh_khzhlx", "1");
		sCustomerInfo.put("kh_nc", sKh_nc);
		sCustomerInfo.put("kh_xb", sKh_xb);
		sCustomerInfo.put("kh_cs", sKh_cs);
		sCustomerInfo.put("kh_yy", sKh_yy);
		sCustomerInfo.put("kh_xmbh", sKh_xmbh);
		String sKh_khbh = slkhxxService.recordCustomerToDB(sCustomerInfo);
		
		/** 获取可用的在线客服编号 */
		//final String sKf_kfbh = "070f1a72-e7c7-11e2-942d-78e40091f9ae"; //对应10086
		final String sKf_kfbh = getAvaliableOnlineKfbh(sKh_khbh);
		if (sKf_kfbh == null) {
			/** TODO:作为无响应的请求保存到数据库：库结构未设计 */
			log.info("未找到在线客服，服务器响应中止！");
			return;
		}
		
		
		/** 向座席端发送消息 */
		String sJsonMsg = ""; 
		JSONObject sJsonObjMsg = new JSONObject();
		try {
			
			sJsonObjMsg.put("meta_source", "customer");
			sJsonObjMsg.put("kh_khbh", sKh_khbh);
			sJsonObjMsg.put("kh_khzh", sKh_khzh);
			sJsonObjMsg.put("kh_openid", sKh_openid);
			sJsonObjMsg.put("kh_nc", sKh_nc);
			sJsonObjMsg.put("kh_xb", sKh_xb);
			sJsonObjMsg.put("kh_cs", sKh_cs);
			sJsonObjMsg.put("kh_yy", sKh_yy);
			sJsonObjMsg.put("msg_id", sMsg_msgid);
			sJsonObjMsg.put("msg_type", sMsg_msgtype);
			sJsonObjMsg.put("msg_content", sMsg_content);
			sJsonObjMsg.put("msg_picurl", sMsg_picurl);
			sJsonObjMsg.put("msg_mediaid", sMsg_mediaid);
			sJsonObjMsg.put("msg_format", sMsg_format);
			sJsonObjMsg.put("msg_sendtime", sdf.format(new Date()));
			
			sJsonMsg = sJsonObjMsg.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e.getStackTrace().toString());
		}
		boolean bSuccess = OnlineCustomerService.sendMsgToOnlineKf(sKf_kfbh, sJsonMsg);
		
		
		/** 保存会话-消息到数据库 */
		Map sMsgInfo = new HashMap();
		sMsgInfo.put("kh_khbh", sKh_khbh);
		sMsgInfo.put("kf_kfbh", sKf_kfbh);
		sMsgInfo.put("msg_id", sMsg_msgid);
		sMsgInfo.put("msg_type", sMsg_msgtype);
		sMsgInfo.put("msg_content", sMsg_content);
		sMsgInfo.put("msg_sendtime", sdf.format(new Date()));
		if (bSuccess) {
			sMsgInfo.put("msg_sendstatus", "1");
		} else {
			sMsgInfo.put("msg_sendstatus", "0");
		}
		bSuccess = hhxxService.recordMsgToDB(sMsgInfo);
		
	}
	
	/** 获取可用客服信息 */
	private String getAvaliableOnlineKfbh(String sKh_khbh) {
		
		String sKf_kfbh = null;
		
		/** 查询最近受理的客服 */
		Map zjslKfxxMap = hhxxService.getZjslKfxx(sKh_khbh);
		if (zjslKfxxMap != null) { /** 找到最近受理的客服 */
			String kfbh = (String)zjslKfxxMap.get("kfbh");
			
			/** 校验该客服是否在线 */
			boolean bOnline = OnlineCustomerService.validateOnline(kfbh);
			if (bOnline) { 
				sKf_kfbh = kfbh;
			}
		} 
		
		/** 随机找一个在线客服 */
		if (sKf_kfbh == null) {
			sKf_kfbh = OnlineCustomerService.getOnlineKfByRandom();
		}
		
		return sKf_kfbh;
	}
	
}
