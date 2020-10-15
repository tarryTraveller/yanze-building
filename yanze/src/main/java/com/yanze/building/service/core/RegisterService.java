
package com.yanze.building.service.core;

import java.util.List;

import com.yanze.building.dao.mapper.SysUserMapperExt;
import com.yanze.building.dao.model.SysUser;
import com.yanze.building.dao.model.SysUserExample;
import com.yanze.building.enums.common.YesNo;
import com.yanze.building.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegisterService extends BaseService {

	@Autowired
	private SysUserMapperExt sysUserMapperExt;

	public boolean checkUserName(String userName) {
		SysUserExample example = new SysUserExample();
		example.createCriteria().andIsDeletedEqualTo(YesNo.NO.value).andUsernameEqualTo(userName);
		List<SysUser> list = sysUserMapperExt.selectByExample(example);
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}
}
