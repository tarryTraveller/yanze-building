
package com.yanze.building.dao.mapper;

import java.util.List;

import com.yanze.building.dao.model.SysRole;
import org.springframework.stereotype.Repository;


@Repository
public interface SysRoleMapperExt extends SysRoleMapper {
	public List<SysRole> getRoleByUserId(String userId);
}