package org.ratchetgx.aimee.weixin;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MediaGetterServlet extends HttpServlet {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		
		String sMediaId = request.getParameter("mediaId");
		if (sMediaId == null || "".equals(sMediaId)) {
			log.info("mediaId未指定，获取媒体文件失败！");
			return;
		}
		
		log.info("mediaId:" + sMediaId);
		
		String sAccessToken = WeixinService.getAccessToken();
		
		/** 下载媒体文件并输出 */
		//response.setContentType("application/octet-stream");
		response.setContentType("audio/amr");
		//response.setHeader("Content-Disposition", "attachment;filename=aaa.amr");
		//response.set
		String urlStr =  "http://api.weixin.qq.com/cgi-bin/media/get?"
			      +  "access_token=" + sAccessToken
			      +  "&media_id=" + sMediaId;
		URL url = new URL(urlStr); 
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

        log.info("media output finished, mediaId:" + sMediaId);
	}
	
	private void bak_outputMedia(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		
		String sMediaId = request.getParameter("mediaId");
		if (sMediaId == null || "".equals(sMediaId)) {
			log.info("mediaId未指定，获取媒体文件失败！");
			return;
		}
		
		log.info("mediaId:" + sMediaId);
		
		String sAccessToken = WeixinService.getAccessToken();
		
		/** 媒体文件保存路径 */
		String webRootPath = request.getServletContext().getRealPath("/download");
		String uuidStr = UUID.randomUUID().toString();
		
		/** 下载媒体文件 */
		String urlStr =  "http://api.weixin.qq.com/cgi-bin/media/get?"
			      +  "access_token=" + sAccessToken
			      +  "&media_id=" + sMediaId;
		URL url = new URL(urlStr); 
		URLConnection uc = url.openConnection(); 
		InputStream in = uc.getInputStream(); 
		File mediaFile = new File(webRootPath + "/" + uuidStr + ".amr");
		OutputStream out = new FileOutputStream(mediaFile);
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = in.read(b, 0, 1024)) > 0) {
			out.write(b, 0, len);
		}
		out.flush();
        in.close(); 

        
        /** 将媒体文件从amr格式转为mp3格式 */
        File mp3File = new File(webRootPath + "/" + uuidStr + ".mp3");
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");  /** 须加载目标格式的编码器而不是源格式的解码器 */
        audio.setBitRate(new Integer(128000));
        audio.setChannels(new Integer(2));
        audio.setSamplingRate(new Integer(44100));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        try {
			encoder.encode(mediaFile, mp3File, attrs);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("出现异常", e);
			return;
		}

        
        /** 将mp3格式的媒体文件通过servlet输出 */
        in = new FileInputStream(mp3File);
        out = response.getOutputStream();
		b = new byte[1024];
		len = 0;
		while ((len = in.read(b, 0, 1024)) > 0) {
			out.write(b, 0, len);
		}
		out.flush();
        in.close(); 
        
        log.info("media output finished, mediaId:" + sMediaId);
	}

}
