
package com.yanze.building.controller.core;

import com.yanze.building.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("index")
public class IndexController extends BaseController {

	// 首页
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(@RequestParam(name = "username") String username, Model model) {
		model.addAttribute("username", username);
		return "index";
	}

}
