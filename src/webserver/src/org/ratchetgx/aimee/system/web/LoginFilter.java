package org.ratchetgx.aimee.system.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFilter implements Filter {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        log.info("LoginFilter:DoBeforeProcessing");

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
         log.info("LoginFilter:DoAfterProcessing");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                      throws IOException, ServletException {

    	log.info("LoginFilter:doFilter()");

        doBeforeProcessing(request, response);
        
        HttpServletResponse resp = (HttpServletResponse)response;

        try {
        	
            /** 验证码校验 */
            /*
            String sessionValidateCode = (String) ((HttpServletRequest) request).getSession(true).getAttribute("validateCode");
            String paramValidateCode = request.getParameter("validateCode");
            if (paramValidateCode != null) {
                paramValidateCode = paramValidateCode.toLowerCase();
            }
            if (sessionValidateCode == null || !sessionValidateCode.toLowerCase().equals(paramValidateCode)) {
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp?error=validateCode");
                rd.forward(request, response);
                return;
            }
            */
            
        	/** 用户名密码校验 */
        	String username = request.getParameter("j_username");
        	String password = request.getParameter("j_password");
            if (username == null || "".equals(username)) {
                resp.sendRedirect("login.jsp?error=usernameEmpty");
                return;
            }
            
            if (false) {
                resp.sendRedirect("login.jsp?error=username");
                return;
            } 
            
            /*
            if (...) {
                resp.sendRedirect("login.jsp?error=password");
                return;
            } */
            
            //RequestDispatcher rd = request.getRequestDispatcher("/index.do");
            //rd.forward(request, response);
            resp.sendRedirect("index.do");
            return;
            
            //chain.doFilter(request, response);
        } catch (Exception e) {
            log.debug(e.getStackTrace().toString());
            e.printStackTrace();
            sendProcessingError(e, response);
        }

        doAfterProcessing(request, response);

    }
    
    private void sendProcessingError(Exception exception, ServletResponse response) throws IOException {
    	OutputStream out = response.getOutputStream();
    	String errorInfo = "<html><body>登录过程出现异常</body></html>";
    	out.write(errorInfo.getBytes());
    	out.flush();
    	out.close();
    }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
