package org.ratchetgx.aimee.common.webbase;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class HttpServletProxy extends HttpServlet {    
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
    
    private String targetBean;
    private HttpServlet proxy;
    
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        proxy.service(req, res);
    }
    
    public void init() throws ServletException {
    	
        this.targetBean = getServletName();
        log.info("xxxx:" + targetBean);
        getServletBean();
        proxy.init(getServletConfig());
        
        log.info("HttpServletProxy初始化成功；目标Servlet:" + targetBean);
    }

    private void getServletBean() {
    	
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        this.proxy = (HttpServlet) wac.getBean(targetBean);
        
    }
}
