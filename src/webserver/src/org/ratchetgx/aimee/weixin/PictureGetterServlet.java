package org.ratchetgx.aimee.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PictureGetterServlet extends HttpServlet {
	
	private static Logger log = LoggerFactory.getLogger(WeixinService.class);

	@Override
	public void service(ServletRequest request, ServletResponse response)
				throws ServletException, IOException {
		
		String sPicUrl = request.getParameter("picurl");
		if (sPicUrl == null || "".equals(sPicUrl)) {
			log.info("picurl未指定，获取媒体文件失败！");
			return;
		}
		
		log.info("picurl:" + sPicUrl);
		
		URL url = new URL(sPicUrl); 
		URLConnection uc = url.openConnection(); 
		InputStream in = uc.getInputStream(); 
		OutputStream out = response.getOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = in.read(b, 0, 1024)) > 0) {
			out.write(b, 0, len);
		}
		out.flush();
        in.close(); 
        
        log.info("picture output finished, picurl:" + sPicUrl);
		
	}

	
	
}
