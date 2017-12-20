package com.taiji.manager.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the menu database table.
 * 
 */
public class MenuDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="menu_desc")
	private String menuDesc;

	@Column(name="menu_name")
	private String menuName;

	private String url;

	//bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name="menu_num")
	private MenuDto menuDto;

	//bi-directional many-to-one association to Menu
	@OneToMany(mappedBy="menu")
	private List<MenuDto> menuDtos;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="menu_role"
		, joinColumns={
			@JoinColumn(name="menu_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_id")
			}
		)
	private List<RoleDto> roleDtos;

	public MenuDto() {
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuDesc() {
		return this.menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MenuDto getMenu() {
		return this.menuDto;
	}

	public void setMenu(MenuDto menuDto) {
		this.menuDto = menuDto;
	}

	public List<MenuDto> getMenus() {
		return this.menuDtos;
	}

	public void setMenus(List<MenuDto> menuDtos) {
		this.menuDtos = menuDtos;
	}

	public MenuDto addMenus(MenuDto menuDtos) {
		getMenus().add(menuDtos);
		menuDtos.setMenu(this);

		return menuDtos;
	}

	public MenuDto removeMenus(MenuDto menuDtos) {
		getMenus().remove(menuDtos);
		menuDtos.setMenu(null);

		return menuDtos;
	}

	public List<RoleDto> getRoles() {
		return this.roleDtos;
	}

	public void setRoles(List<RoleDto> roleDtos) {
		this.roleDtos = roleDtos;
	}


}