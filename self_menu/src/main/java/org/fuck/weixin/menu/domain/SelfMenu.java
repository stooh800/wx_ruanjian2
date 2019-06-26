package org.fuck.weixin.menu.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

// 表示自定义菜单
@Entity
@Table(name = "wx_self_menu")
public class SelfMenu {

	@Id
	@Column(length = 36)
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	private String id;
	@OneToMany(cascade = CascadeType.ALL)
	// 当前类对应的表是主表，@JoinColumn会在子表生成一个名为menu_id的外键列
	@JoinColumn(name = "menu_id")
	private List<MenuButton> subMenus = new LinkedList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<MenuButton> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<MenuButton> subMenus) {
		this.subMenus = subMenus;
	}
}
