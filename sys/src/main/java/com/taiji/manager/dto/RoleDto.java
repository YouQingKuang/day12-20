package com.taiji.manager.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
public class RoleDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="role_desc")
	private String roleDesc;

	@Column(name="role_name")
	private String roleName;

	//bi-directional many-to-many association to Menu
	@ManyToMany(mappedBy="roles")
	private List<MenuDto> menuDtos;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="roles")
	private List<UserDto> userDtos;

	public RoleDto() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<MenuDto> getMenus() {
		return this.menuDtos;
	}

	public void setMenus(List<MenuDto> menuDtos) {
		this.menuDtos = menuDtos;
	}

	public List<UserDto> getUsers() {
		return this.userDtos;
	}

	public void setUsers(List<UserDto> userDtos) {
		this.userDtos = userDtos;
	}

}