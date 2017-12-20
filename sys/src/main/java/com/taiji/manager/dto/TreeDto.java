package com.taiji.manager.dto;

import java.io.Serializable;
import javax.persistence.*;

import com.taiji.manager.entity.Menu;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the menu database table.
 * 
 */
public class TreeDto implements Serializable {

	/**
	 * Description:
	 * @author xxx
	 */
	private static final long serialVersionUID = 3350278308140672888L;

	@Id
	private String id;

	@Column(name="menu_desc")
	private String menuDesc;

	@Column(name="menu_name")
	private String text;

	private String url;

	//bi-directional many-to-one association to Menu
//	@ManyToOne
//	@JoinColumn(name="menu_num")
//	private TreeDto TreeDto;

	//bi-directional many-to-one association to Menu
	@OneToMany(mappedBy="menu")
	private List<TreeDto> nodes;

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
	
	
	
	public TreeDto() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

//	public TreeDto getTreeDto() {
//		return TreeDto;
//	}
//
//	public void setTreeDto(TreeDto treeDto) {
//		TreeDto = treeDto;
//	}

	public List<TreeDto> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeDto> nodes) {
		this.nodes = nodes;
	}

	public List<RoleDto> getRoleDtos() {
		return roleDtos;
	}

	public void setRoleDtos(List<RoleDto> roleDtos) {
		this.roleDtos = roleDtos;
	}

//	@Override
//	public String toString() {
//		return "TreeDto [id=" + id + ", menuDesc=" + menuDesc + ", text=" + text + ", url=" + url + ", TreeDto="
//				+ TreeDto + ", nodes=" + nodes + ", roleDtos=" + roleDtos + "]";
//	}
	
	
	
}