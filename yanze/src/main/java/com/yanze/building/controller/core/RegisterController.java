
package com.yanze.building.controller.core;

import com.yanze.building.controller.base.BaseController;
import com.yanze.building.service.core.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

@Controller
@RequestMapping("register")
public class RegisterController extends BaseController {

	@Autowired
	private RegisterService registerService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String register() {
		// TODO
		return "register/register";
	}

	// 点击显示《法律声明和隐私权政策》
	@RequestMapping(value = "item", method = RequestMethod.GET)
	public String showItem() {
		return "register/register_item";
	}

	@RequestMapping(value = "check", method = RequestMethod.POST)
	@ResponseBody
	@JsonView
	public Object check(String username) {
		boolean bool = registerService.checkUserName(username);
		if (bool) {
			return SUCCESS;
		}
		return ERROR;
	}
}
