package com.taiji.manager.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiji.manager.dto.RoleDto;
import com.taiji.manager.entity.Role;
import com.taiji.manager.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
//	@Autowired
//	private Role role;

	public void save(RoleDto roleDto) {
		Role role = new Role();
		BeanUtils.copyProperties(roleDto, role);
		roleRepository.save(role);
	}

	public void delete(String id) {
		roleRepository.delete(id);
	}
	
	
	
}
