package org.ratchetgx.aimee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMsgSender extends HttpServlet{

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
										throws ServletException, IOException {
				
		/** 获取客服编号 */
		final String sKf_kfbh = request.getParameter("kfbh");
		
		/** 获取消息内容 */
		final String sMsg = request.getParameter("msg");
		
		if (sKf_kfbh == null || "".equals(sKf_kfbh) || sMsg == null || "".equals(sMsg)) {
			log.info("客服编号、消息内容不可为空 : {kf_kfbh:"+sKf_kfbh+",msg:"+sMsg+"}");
			throw new NullPointerException("客服编号、消息内容不可为空");
		}
		
		boolean bSent = OnlineCustomerService.sendMsgToOnlineKf(sKf_kfbh, sMsg);
        
        if (bSent) {
            response.getWriter().write("msg send success:{kf_kfbh:"+sKf_kfbh+",msg:"+sMsg+"}");
        } else {
            response.getWriter().write("msg send failed:{kf_kfbh:"+sKf_kfbh+",msg:"+sMsg+"}");
        }
		
	}

}
