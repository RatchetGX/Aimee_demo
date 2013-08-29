package org.ratchetgx.aimee;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.json.JSONException;
import org.json.JSONObject;
import org.ratchetgx.aimee.weixin.WeixinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMsgReceiver extends WebSocketServlet {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, final HttpServletRequest request) {
		
		log.info("Enter into ServerMsgReceiver...");
				
		/** 获取客服编号 */
		final String sKf_kfbh = request.getParameter("kfbh");
		
		if (sKf_kfbh == null || "".equals(sKf_kfbh)) {
			log.info("ServerMsgReceiver:invalid ==> {kf_kfbh:"+sKf_kfbh+"}");
			throw new NullPointerException("客服编号不可空");
		}
		
		final ServletContext application = request.getServletContext();
		
		return new MessageInbound() {
			
	        /* 
	         * 当客户端与服务器建立连接时
	         */
	        @Override
			protected void onOpen(WsOutbound out) {
	        	log.info("收到客服端WebSocket连接请求:" + "{kf_kfbh:"+sKf_kfbh+"}");
		            
	        	/** 注册到在线客服区 */
	        	boolean bSuccess = OnlineCustomerService.registerConn(sKf_kfbh, out);
			}
	        
	        /* 
	         * 当服务器端收到客户端消息时
	         */
			@Override
	        protected void onTextMessage(CharBuffer charBuffer) throws IOException {
				log.info("收到来自客户端的消息:" + charBuffer +":{kf_kfbh:" + sKf_kfbh + "}");
	            
				String sMsg = charBuffer.toString();
				JSONObject sJsonSendMsg;
				try {
					sJsonSendMsg = new JSONObject(sMsg);
					String sKh_khbh = sJsonSendMsg.getString("khbh");
					String sKh_openid = sJsonSendMsg.getString("openid");
					String sMsg_content = sJsonSendMsg.getString("message");
					WeixinService.sendMsgToUser(sKh_openid, "text", sMsg_content);
				} catch (JSONException e) {
					e.printStackTrace();
					log.info(e.getMessage());
				}
				
	        }

	        @Override
	        protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {
	        	
	        }

			@Override
			protected void onClose(int status) {
				log.info("客户端浏览器已断开连接:" + "{kf_kfbh:"+sKf_kfbh+"}");
				
				WsOutbound out = this.getWsOutbound();
				OnlineCustomerService.removeOfflineKf(sKf_kfbh);
				
			}
	        
	     };

	}
	
}
