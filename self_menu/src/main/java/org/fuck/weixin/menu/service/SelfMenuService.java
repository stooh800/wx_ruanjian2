package org.fuck.weixin.menu.service;

import org.fuck.weixin.menu.domain.SelfMenu;

public interface SelfMenuService {

	SelfMenu getMenu();

	void save(SelfMenu selfMenu);

}
