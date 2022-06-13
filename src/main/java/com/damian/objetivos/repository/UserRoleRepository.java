package com.damian.objetivos.repository;

import com.damian.objetivos.entity.User;
import com.damian.objetivos.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Serializable>{
	
	public abstract Set<UserRole> findByUser(User user);

	public abstract void deleteByUserRoleId(int userRoleId);

}
