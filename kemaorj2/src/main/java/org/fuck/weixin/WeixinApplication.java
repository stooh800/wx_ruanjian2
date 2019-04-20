package org.fuck.weixin;
import org.fuck.wexin.CommonsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("org.fuck")
public class WeixinApplication implements CommonsConfig {

	public static void main(String[] args) {
		SpringApplication.run(WeixinApplication.class, args);
	}

}
