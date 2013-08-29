package org.ratchetgx.aimee;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.catalina.websocket.WsOutbound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnlineCustomerService {
	
	private static Logger log = LoggerFactory.getLogger(OnlineCustomerService.class);
	
	private static Map appKfBrowserMap = new ConcurrentHashMap();
	
	/*
	 * 注册到在线客服区
	 */
	public static boolean registerConn(String sKf_kfbh, WsOutbound out) {
		
		/** 将客服浏览器输出流实例放入全局MAP */
        String kfBrowserKey = sKf_kfbh;
        appKfBrowserMap.put(kfBrowserKey, out);
        
        log.info("注册在线客服成功，kf_kfbh:" + sKf_kfbh);
        return true;
        
	}
	
	/*
	 * 向在线客服发送消息
	 */
	public static boolean sendMsgToOnlineKf(String sKf_kfbh, String sMsg) {
		
		boolean bSuccess = false;
		
		/** 找出合适的客服浏览器输出流实例 */
        WsOutbound out =  (WsOutbound)appKfBrowserMap.get(sKf_kfbh);
        if (out != null) {
	        try {
			    CharBuffer buffer = CharBuffer.wrap(sMsg);
	            out.writeTextMessage(buffer);
	            out.flush();
	            
	            bSuccess = true;
			} catch (IOException e) {
				log.info(e.getMessage());
				e.printStackTrace();
			}
        }
	        
        
        if (bSuccess) {
        	log.info("向在线客服发送消息成功:{kf_kfbh:"+sKf_kfbh+",msg:"+sMsg+"}");
        } else {
        	log.info("向在线客服发送消息失败:{kf_kfbh:"+sKf_kfbh+",msg:"+sMsg+"}");
        }
		
		return bSuccess;
	}
	
	/*
	 * 校验指定客服是否在线
	 */
	public static boolean validateOnline(String sKf_kfbh) {
		
		boolean bSuccess = false;
		
		/** 找出合适的客服浏览器输出流实例 */
        WsOutbound out =  (WsOutbound)appKfBrowserMap.get(sKf_kfbh);
        if (out != null) {
			try {
			    CharBuffer buffer = CharBuffer.wrap("");
	            out.writeTextMessage(buffer);
	            out.flush();
	            
	            bSuccess = true;
			} catch (IOException e) {
				log.info(e.getMessage());
				e.printStackTrace();
			}
        }
        
        if (bSuccess) {
        	log.info("校验指定客服在线状态:在线:kf_kfbh:" + sKf_kfbh);
        } else {
        	log.info("校验指定客服在线状态:不在线:kf_kfbh:" + sKf_kfbh);
        }
        
        return bSuccess;
	}
	
	/*
	 * 随机获取在线客服
	 */
	public static String getOnlineKfByRandom() {

		String sKf_kfbh = null;
              
        int onlineKfCount = appKfBrowserMap.size(); /** 在线客服数量 */
        if (onlineKfCount > 0) {
        	
        	Random random = new Random();
            int randomInt = random.nextInt();
            if (randomInt < 0) {
            	randomInt *= -1;
            }
            
            List list = new ArrayList(appKfBrowserMap.keySet());
        	for (int i = 0; i < onlineKfCount * 10; i++) {
        		int randomInt2 = randomInt % onlineKfCount;  
        		
        		log.debug("why:onlineKfCount:" + onlineKfCount + ",randomInt:" + randomInt + ",randomInt2:" + randomInt2);
        		
        		String tempKfbh = (String)list.get(randomInt2);
        		if (validateOnline(tempKfbh)) {
        			sKf_kfbh = tempKfbh;
        			break;
        		}
        		
        	}
        	
        } else {
        	return null;
        }
        
        log.info("随机获取在线客服，kf_kfbh:" + sKf_kfbh);
        
        return sKf_kfbh;
	}
	
	/*
	 * 移除离线的客服
	 */
	public static boolean removeOfflineKf(String sKf_kfbh) {
		
		appKfBrowserMap.remove(sKf_kfbh);
		log.info("移除离线的客服，kf_kfbh:" + sKf_kfbh);
		
		return true;
	}

}
