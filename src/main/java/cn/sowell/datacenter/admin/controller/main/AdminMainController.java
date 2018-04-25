package cn.sowell.datacenter.admin.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.sowell.datacenter.admin.controller.AdminConstants;

@Controller
@RequestMapping("/admin")
public class  AdminMainController {
	
	
	@RequestMapping("/login")
	public String login(@RequestParam(name="error",required=false) String error, Model model){
		model.addAttribute("error", error);
		model.addAttribute("errorMap", AdminConstants.ERROR_CODE_MAP);
		return "/admin/common/login.jsp";
	}
	
	@RequestMapping({"/", ""})
	public String index(){
		return "/admin/index.jsp";
	}
	
}
