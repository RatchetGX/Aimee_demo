package org.ratchetgx.aimee.module.jrgl.jrxm.web;

import javax.servlet.http.HttpServletRequest;

import org.ratchetgx.aimee.common.webbase.BaseController;
import org.ratchetgx.aimee.common.webbase.Constants;
import org.ratchetgx.aimee.module.jrgl.jrxm.service.JrxmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/jrgl/jrxm")
public class JrxmController extends BaseController {
	
	@Autowired
	private JrxmService jrglService;
	
	
	@RequestMapping(value = "showJrxm")
    public String showJrxm(ModelMap model, HttpServletRequest request) {
        try {
        	
        	int count = jrglService.getJrxmTotalCount();
        	
            model.put("count", count);

            return "/module/jrgl/jrxm/jrxm";
        } catch (Exception ex) {
            log.error("", ex);
            model.put("errorMessage", ex.getMessage());
            return Constants.ERROR_PAGE;
        }
    }
}
