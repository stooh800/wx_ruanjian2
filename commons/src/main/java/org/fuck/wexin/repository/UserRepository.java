package org.fuck.wexin.repository;

import org.fuck.wexin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Spring Data JPA会自动生成此接口的实例，程序员不需要写实现类
// 实现的原理是动态代理技术
@Repository

public interface UserRepository extends JpaRepository<User, String> {
	// 会自动根据方法生成SQL语句
	// select * from wx_user where open_id = ?
	User findByOpenId(String openId);
}
