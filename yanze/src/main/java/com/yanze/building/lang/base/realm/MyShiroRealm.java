
package com.yanze.building.lang.base.realm;

import java.util.List;

import com.yanze.building.dao.model.SysPermission;
import com.yanze.building.dao.model.SysRole;
import com.yanze.building.dao.model.SysUser;
import com.yanze.building.service.sys.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


public class MyShiroRealm extends AuthorizingRealm {

	// 用于用户查询
	@Autowired
	private SysUserService sysUserService;

	// 角色权限和对应权限添加
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 获取登录用户名
		String name = (String) principalCollection.getPrimaryPrincipal();
		// 查询用户名称
		SysUser sysUser = sysUserService.getByName(name);
		List<SysRole> list = sysUserService.getRolesByUserId(sysUser.getUserId());
		// 添加角色和权限
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		for (SysRole sysRole : list) {
			// 添加角色
			simpleAuthorizationInfo.addRole(sysRole.getRoleName());
			List<SysPermission> roleList = sysUserService.getPermsByRoleId(sysRole.getId());
			for (SysPermission sysPermission : roleList) {
				// 添加权限
				simpleAuthorizationInfo.addStringPermission(sysPermission.getPermissionName());
			}
		}
		return simpleAuthorizationInfo;
	}

	// 用户认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
		if (authenticationToken.getPrincipal() == null) {
			return null;
		}
		// 获取用户信息
		String username = authenticationToken.getPrincipal().toString();
		SysUser sysUser = sysUserService.getByName(username);
		if (sysUser == null) {
			// 这里返回后会报出对应异常
			return null;
		} else {
			// 这里验证authenticationToken和simpleAuthenticationInfo的信息
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, sysUser.getPassword().toString(), getName());
			return simpleAuthenticationInfo;
		}
	}

}
