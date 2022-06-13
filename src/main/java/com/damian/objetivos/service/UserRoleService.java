package com.damian.objetivos.service;

import com.damian.objetivos.entity.User;
import com.damian.objetivos.entity.UserRole;
import com.damian.objetivos.model.UserRoleModel;

import java.util.Set;

public interface UserRoleService {
	
	public abstract UserRoleModel findByUser(User user);
	
	public abstract Set<UserRole> findRolesByUser(User user);
	
	public abstract void save(UserRole userRole);
	
	public abstract void delete(int userRoleId);

	public abstract void actualizarRoles(UserRoleModel userRoleModel);

}
