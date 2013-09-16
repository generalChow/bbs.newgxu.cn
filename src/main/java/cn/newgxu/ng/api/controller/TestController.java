package cn.newgxu.ng.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@RequestMapping("/api/test")
	@ResponseBody
	public ModelAndView test(ModelAndView model){
		model.addObject("test", "yes");
		return model;
	}
}
