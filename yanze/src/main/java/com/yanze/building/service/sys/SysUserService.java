
package com.yanze.building.service.sys;

import java.util.List;

import com.yanze.building.dao.mapper.SysPermissionMapperExt;
import com.yanze.building.dao.mapper.SysRoleMapperExt;
import com.yanze.building.dao.mapper.SysUserMapperExt;
import com.yanze.building.dao.model.SysPermission;
import com.yanze.building.dao.model.SysRole;
import com.yanze.building.dao.model.SysUser;
import com.yanze.building.dao.model.SysUserExample;
import com.yanze.building.enums.common.YesNo;
import com.yanze.building.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysUserService extends BaseService {

	@Autowired
	private SysUserMapperExt sysUserMapperExt;
	@Autowired
	private SysRoleMapperExt sysRoleMapperExt;
	@Autowired
	private SysPermissionMapperExt sysPermissionMapperExt;

	public SysUser getByName(String username) {
		SysUserExample example = new SysUserExample();
		example.createCriteria().andIsDeletedEqualTo(YesNo.NO.value).andUsernameEqualTo(username);
		List<SysUser> list = sysUserMapperExt.selectByExample(example);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	public List<SysRole> getRolesByUserId(String userId) {
		List<SysRole> list = sysRoleMapperExt.getRoleByUserId(userId);
		return list;
	}

	public List<SysPermission> getPermsByRoleId(String roleId) {
		List<SysPermission> list = sysPermissionMapperExt.getPermsByRoleId(roleId);
		return list;
	}
}
