package org.fuck.wexin;

import org.fuck.wexin.domain.InMessage;
import org.fuck.wexin.json.JsonRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public interface CommonsConfig extends CommandLineRunner,  DisposableBean {

	public static final Logger LOG = LoggerFactory.getLogger(CommonsConfig.class);	
	@Bean
	public default XmlMapper xmlMapper() {
		XmlMapper mapper = new XmlMapper();
		return mapper;
	}

	@Bean
	public default ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}

	@Bean
	public default RedisTemplate<String, ? extends InMessage> inMessageTemplate(//
			@Autowired RedisConnectionFactory connectionFactory) {

		RedisTemplate<String, ? extends InMessage> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setValueSerializer(new JsonRedisSerializer());

		return template;
	}

	@Bean
	default <T> RedisTemplate<String, T> redisTemplate(//
			@Autowired RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, T> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setValueSerializer(new JsonRedisSerializer());
		return template;
	}
	public final Object stopMonitor = new Object();
	@Override
	public default void run(String... args) throws Exception {
		new Thread(() -> {
			synchronized (stopMonitor) {
				try {
					stopMonitor.wait();
				} catch (InterruptedException e) {
					LOG.error("无法等待停止通知：" + e.getLocalizedMessage(), e);
				}
			}
		}).start();
	}
	@Override
	public default void destroy() throws Exception {
		synchronized (stopMonitor) {
			stopMonitor.notify();
		}
	}
}
