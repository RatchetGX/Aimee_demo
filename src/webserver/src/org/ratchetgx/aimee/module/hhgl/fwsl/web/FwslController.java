package org.ratchetgx.aimee.module.hhgl.fwsl.web;

import javax.servlet.http.HttpServletRequest;

import org.ratchetgx.aimee.common.webbase.BaseController;
import org.ratchetgx.aimee.common.webbase.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/hhgl/fwsl")
public class FwslController extends BaseController {

	
	@RequestMapping(value = "switchCustomer")
    public String showJrxm(ModelMap model, HttpServletRequest request) {
		
        try {
            model.put("count", "");

            return "/module/hhgl/fwsl/customerMain";
        } catch (Exception ex) {
            log.error("", ex);
            model.put("errorMessage", ex.getMessage());
            return Constants.ERROR_PAGE;
        }
    }

}
