package org.fuck.weixin.menu.repository;

import org.fuck.weixin.menu.domain.SelfMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfMenuRepository extends JpaRepository<SelfMenu, String> {

}
