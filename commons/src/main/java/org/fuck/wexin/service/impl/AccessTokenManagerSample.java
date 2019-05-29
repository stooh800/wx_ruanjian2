package org.fuck.wexin.service.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.fuck.wexin.domain.ResponseError;
import org.fuck.wexin.domain.ResponseMessage;
import org.fuck.wexin.domain.ResponseToken;
import org.fuck.wexin.service.AccessTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AccessTokenManagerSample implements AccessTokenManager {

	private static final Logger LOG = LoggerFactory.getLogger(AccessTokenManagerSample.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<String, ResponseToken> redisTemplate;

	@Override
	public String getToken(String account) throws RuntimeException {
		String key = "wx_access_token";
		ResponseToken token = redisTemplate.boundValueOps(key).get();
		if (token == null) {
			for (int i = 0; i < 10; i++) {
				LOG.trace("数据库里面没有令牌，需要重新获取，正在获取分布式事务锁...");
				// 加上分布式的事务锁
				Boolean locked = redisTemplate.boundValueOps(key + "_lock")//
						// 如果数据库里面本身已经有对应的KEY，那么不能再增加相同的KEY，会等待指定的时间
						.setIfAbsent(new ResponseToken(), 1, TimeUnit.MINUTES);
				LOG.trace("获取分布式事务锁结束，结果：{}", locked);
				if (locked != null && locked == true) {
					try {
						token = redisTemplate.boundValueOps(key).get();
						if (token == null) {
							LOG.trace("调用远程接口获取令牌");
							// 再次获取令牌，还是没有令牌，此时就要调用远程接口
							token = getRemoteToken(account);
							// 把获取到的令牌，存储到数据库
							redisTemplate.boundValueOps(key).set(token, token.getExpiresIn(), TimeUnit.SECONDS);
						}
					} finally {
						// 删除分布式事务锁
						redisTemplate.delete(key + "_lock");
						synchronized (this) {
							// 通知其他的线程继续执行，主要是wait方法要继续执行！
							this.notifyAll();
						}
					}
					// 只要得到锁，即可跳出循环，不需要重试
					break;
				} else {
					// throw new RuntimeException("没有获得事务锁，无法更新令牌");
					synchronized (this) {
						try {
							// 等待一分钟以后，重新尝试获得锁
							this.wait(60 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		if (token == null) {
			throw new RuntimeException("无法获得访问令牌");
		}
		return token.getToken();
	}

	private ResponseToken getRemoteToken(String account) {

		// 此时完全不管过期的问题，也不管开发者的身份问题，调用此方法，总是获取一个新的令牌。

		String appid = "wx375cd9c53c364fc4";
		String appSecret = "ad91eb3762d1336c39a417173bc47aba";

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"//
				+ "&appid=" + appid//
				+ "&secret=" + appSecret;

		// 创建HTTP客户端，可以重复使用，但是此时不考虑重复使用
		HttpClient hc = HttpClient.newBuilder().build();
		// 创建请求
		HttpRequest request = HttpRequest.newBuilder(URI.create(url))//
				.GET()// 以GET方式发送请求
				.build();
		ResponseMessage msg;
		try {
			// 发送请求
			// BodyHandlers里面包含了一系列的响应体处理程序，能够把响应体转换为需要的数据类型
			// ofString表示转换为String类型的数据
			// Charset.forName("UTF-8")表示使用UTF-8的编码转换数据
			HttpResponse<String> response = hc.send(request, BodyHandlers.ofString(Charset.forName("UTF-8")));

			// 获取响应体
			String body = response.body();

			LOG.trace("调用远程接口的返回值:\n{}", body);

			if (body.contains("errcode")) {
				// 错误
				msg = objectMapper.readValue(body, ResponseError.class);
				msg.setStatus(2);
			} else {
				// 成功
				msg = objectMapper.readValue(body, ResponseToken.class);
				msg.setStatus(1);
			}

			if (msg.getStatus() == 1) {
				// 成功，返回令牌
				return (ResponseToken) msg;
			}
		} catch (Exception e) {
			throw new RuntimeException("获取访问令牌出现问题：" + e.getLocalizedMessage(), e);
		}

		throw new RuntimeException("获取访问令牌出现问题，"//
				+ "错误代码=" + ((ResponseError) msg).getErrorCode() //
				+ "，错误信息=" + ((ResponseError) msg).getErrorMessage());
	}
}