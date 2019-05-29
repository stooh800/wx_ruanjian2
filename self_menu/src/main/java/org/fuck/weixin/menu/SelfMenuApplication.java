package org.fuck.weixin.menu;

import org.fuck.wexin.CommonsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.fuck")
@EntityScan("org.fuck")
public class SelfMenuApplication implements CommonsConfig {

	public static void main(String[] args) {
		SpringApplication.run(SelfMenuApplication.class, args);
	}
}
