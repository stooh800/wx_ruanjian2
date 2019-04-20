package org.fuck.wexin.service.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;

import org.fuck.wexin.domain.ResponseError;
import org.fuck.wexin.domain.ResponseMessage;
import org.fuck.wexin.domain.ResponseToken;
import org.fuck.wexin.service.AccessTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class AccessTokenManagerSample implements AccessTokenManager {
    @Autowired
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String getToken(String account) throws RuntimeException {
		// 此时完全不管过期的问题，也不管开发者的身份问题，调用此方法，总是获取一个新的令牌。

		String appid = "wxa5b1fb16151df1ff";
		String appSecret = "b720c322ced8f98c676e5757d4d68f7a";

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
				return ((ResponseToken) msg).getToken();
			}
		} catch (Exception e) {
			throw new RuntimeException("获取访问令牌出现问题：" + e.getLocalizedMessage(), e);
		}

		throw new RuntimeException("获取访问令牌出现问题，"//
				+ "错误代码=" + ((ResponseError) msg).getErrorCode() //
				+ "，错误信息=" + ((ResponseError) msg).getErrorMessage());
	}

}
