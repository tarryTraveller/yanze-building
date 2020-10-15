
package com.yanze.building.controller.core;

import com.yanze.building.controller.base.BaseController;
import com.yanze.building.dao.model.SysUser;
import com.yanze.building.lang.util.JsonResult;
import com.yanze.building.lang.util.JsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

@Controller
@RequestMapping("login")
public class LoginController extends BaseController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toLogin() {
		return "login/login";
	}

	@RequestMapping(value = "error", method = RequestMethod.GET)
	public String error() {
		return "error/403";
	}

	@RequestMapping(value = "check", method = RequestMethod.POST)
	@ResponseBody
	@JsonView
	public Object loginUser(SysUser sysUser) {
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword());
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(usernamePasswordToken); // 完成登录
			String username2 = (String) subject.getPrincipal();
			return JsonUtil.ObjectToJson(new JsonResult(200, "成功", username2));
		} catch (UnknownAccountException e) {
			return JsonUtil.ObjectToJson(new JsonResult(5000, "用户名不存在", null));
		} catch (IncorrectCredentialsException e) {
			return JsonUtil.ObjectToJson(new JsonResult(5001, "密码错误", null));
		} catch (Exception e) {
			return JsonUtil.ObjectToJson(new JsonResult(5002, "其他错误", null));
		}

	}
}
