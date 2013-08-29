package org.ratchetgx.aimee.system.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ratchetgx.aimee.common.webbase.BaseController;
import org.ratchetgx.aimee.system.service.ZxkfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class LoginController extends BaseController {
	
	@Autowired
	private ZxkfService zxkfService;
	
	@RequestMapping(value = "login")
    public String login(ModelMap model, HttpServletRequest request) {
		
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
            return "redirect:login.jsp?error=usernameEmpty";
        }
        
        if (!zxkfService.verifyKfExist(username)) {
        	return "redirect:login.jsp?error=username";
        } 
        
        if (!zxkfService.verifyKfPassword(username, password)) {
            return "redirect:login.jsp?error=password";
        }
		
        Map kfInfoMap = zxkfService.getKfInfo(username);
        kfInfoMap.put("username", username);
        request.getSession().setAttribute("kfInfo", kfInfoMap);
		
		return "redirect:/index.do";
		
	}
}
