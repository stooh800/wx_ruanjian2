package org.fuck.wexin.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;

import org.fuck.wexin.domain.User;
import org.fuck.wexin.domain.text.TextOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service // 把当前类的对象加入Spring中管理
public class WeiXinProxy {

	private static final Logger LOG = LoggerFactory.getLogger(WeiXinProxy.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private AccessTokenManager accessTokenManager;
	private HttpClient httpClient = HttpClient.newBuilder().build();

	public User getUser(String account, String openId) {

		String accessToken = accessTokenManager.getToken(account);

		String url = "https://api.weixin.qq.com/cgi-bin/user/info"//
				+ "?access_token=" + accessToken//
				+ "&openid=" + openId//
				+ "&lang=zh_CN";
		// 创建请求
		HttpRequest request = HttpRequest.newBuilder(URI.create(url))//
				.GET()// 以GET方式发送请求
				.build();
		try {
			// 发送请求
			// BodyHandlers里面包含了一系列的响应体处理程序，能够把响应体转换为需要的数据类型
			// ofString表示转换为String类型的数据
			// Charset.forName("UTF-8")表示使用UTF-8的编码转换数据
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString(Charset.forName("UTF-8")));

			// 获取响应体
			String body = response.body();
			LOG.trace("调用远程接口返回的内容：\n{}", body);

			if (!body.contains("errcode")) {
				// 成功
				User user = objectMapper.readValue(body, User.class);
				return user;
			}
		} catch (Exception e) {
			LOG.error("调用远程接口出现错误：" + e.getLocalizedMessage(), e);
		}
		return null;
	}

	public void sendText(String account, String openId, String text) {
		TextOutMessage out = new TextOutMessage(openId, text);

		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
		try {
			String json = this.objectMapper.writeValueAsString(out);
			this.post(url, json);
		} catch (JsonProcessingException e) {
			LOG.error("发送消息出现问题：" + e.getLocalizedMessage(), e);
		}
//
//		// 一般情况下是放入消息队列来发送的，但是这里为了简单起见，暂时不使用消息队列
//		try {
//			String json = this.objectMapper.writeValueAsString(out);
//			LOG.trace("客服接口要发送的消息内容：{}", json);
//
//			String accessToken = accessTokenManager.getToken(account);
//			String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send"//
//					+ "?access_token=" + accessToken;
//
//			// 创建请求
//			HttpRequest request = HttpRequest.newBuilder(URI.create(url))//
//					// 以POST方式发送请求
//					.POST(BodyPublishers.ofString(json, Charset.forName("UTF-8")))//
//					.build();
//
//			HttpResponse<String> response = httpClient//
//					.send(request, BodyHandlers.ofString(Charset.forName("UTF-8")));
//			LOG.trace("发送客服消息的结果：{}", response.body());
//
//		} catch (IOException | InterruptedException e) {
//			LOG.error("发送消息出现问题：" + e.getLocalizedMessage(), e);
//		}
	}

	public void createMenu(String json) {
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
		this.post(url, json);
	}

	private void post(String url, String json) {

		LOG.trace("以POST方式发送信息给微信公众号，内容：\n{}", json);

		// 如果没有加上访问令牌，无法访问微信的公众号服务器！
		String accessToken = accessTokenManager.getToken(null);
		url = url + accessToken;

		// 创建请求
		HttpRequest request = HttpRequest.newBuilder(URI.create(url))//
				// 以POST方式发送请求
				.POST(BodyPublishers.ofString(json, Charset.forName("UTF-8")))//
				.build();

//					HttpResponse<String> response = httpClient//
//							.send(request, BodyHandlers.ofString(Charset.forName("UTF-8")));

		// 异步发送
		CompletableFuture<HttpResponse<String>> future = httpClient.sendAsync(request,
				BodyHandlers.ofString(Charset.forName("UTF-8")));

		// 当有结果以后，会自动调用下面的语句
		future.thenAccept(response -> {
			LOG.trace("POST方式发送信息给微信公众号服务器返回的结果：\n{}", response.body());
		});
	}
}
