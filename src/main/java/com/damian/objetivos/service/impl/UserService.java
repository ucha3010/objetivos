package com.damian.objetivos.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.damian.objetivos.repository.UserRoleRepository;
import com.damian.objetivos.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.damian.objetivos.entity.UserRole;
import com.damian.objetivos.repository.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.damian.objetivos.entity.User user = userRepository.findByUsername(username);
		List<GrantedAuthority> authorities = buildAuthorities(user.getUserRole());
		return buildUser(user, authorities);
	}

	public com.damian.objetivos.entity.User addOrUpdate(com.damian.objetivos.entity.User usuario) {
		return userRepository.save(usuario);
	}

	public boolean delete(String username) {
		try {
			com.damian.objetivos.entity.User user = userRepository.findByUsername(username);
			Set<UserRole> userRoles = userRoleRepository.findByUser(user);
			for(UserRole userRole: userRoles) {
				userRoleRepository.delete(userRole);
			}
			user.setUserRole(null);
			userRepository.delete(user);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean comparePassword(String newPass, String oldPass) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(newPass, oldPass);
	}

	public String generatePassword(String newPass) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(newPass);
	}

	public List<com.damian.objetivos.entity.User> findAll() {
		return userRepository.findAll();
	}

	public com.damian.objetivos.entity.User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	private User buildUser(com.damian.objetivos.entity.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
				true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildAuthorities (Set<UserRole> userRoles) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
		for (UserRole userRole: userRoles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		return new ArrayList<>(grantedAuthorities);
	}

}
