package org.fuck.wexin.processors.impl;

import org.fuck.wexin.domain.event.EventInMessage;
import org.fuck.wexin.processors.EventMessageProcessor;
import org.fuck.wexin.service.AccessTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//把Bean加入容器管理，默认类名首字母小写作为ID
//如果@Service注解有值则表示自定义ID
@Service("subscribeMessageProcessor")
public class SubscribeEventMessageProcessor implements EventMessageProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(SubscribeEventMessageProcessor.class);
	@Autowired
	private AccessTokenManager accessTokenManager;

	@Override
	public void onMessage(EventInMessage msg) {
		LOG.trace("关注消息处理器: " + msg);
		// 1.检查用户是否有关注，如果以关注不需要处理
		// 2.如果用户未关注，则需要调用远程接口获取用户的资料，即使之前有关注，现在也重新获取用户信息
		// 2.1.先要获取访问令牌，重点、难点
		// 2.2.调用远程接口
		// 3.把用户信息保存到数据库
		String token = accessTokenManager.getToken("null");
		LOG.trace("访问令牌: " + token);
	}
}
