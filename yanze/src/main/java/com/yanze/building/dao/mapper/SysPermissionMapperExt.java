
package com.yanze.building.dao.mapper;

import java.util.List;

import com.yanze.building.dao.model.SysPermission;
import org.springframework.stereotype.Repository;


@Repository
public interface SysPermissionMapperExt extends SysPermissionMapper {
	List<SysPermission> getPermsByRoleId(String roleId);
}