package org.ratchetgx.aimee.system.web;

import javax.servlet.http.HttpServletRequest;

import org.ratchetgx.aimee.common.webbase.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController {

	@RequestMapping(value = "index")
    public String showIndexNavigator(ModelMap model, HttpServletRequest request) {
		
		model.put("welcomeMsg", "index!!!");
		return "/system/index";
		
		
	}
}
