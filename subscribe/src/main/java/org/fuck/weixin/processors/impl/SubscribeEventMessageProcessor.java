package org.fuck.weixin.processors.impl;

import java.util.Date;


import org.fuck.wexin.domain.User;
import org.fuck.wexin.domain.event.EventInMessage;
import org.fuck.wexin.processors.EventMessageProcessor;
import org.fuck.wexin.repository.UserRepository;
import org.fuck.wexin.service.WeiXinProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("subscribeMessageProcessor")
public class SubscribeEventMessageProcessor implements EventMessageProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(SubscribeEventMessageProcessor.class);
//	@Autowired
//	private AccessTokenManager accessTokenManager;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WeiXinProxy weiXinProxy;

	@Override
	public void onMessage(EventInMessage msg) {
		LOG.trace("关注消息处理器: " + msg);
		String account = msg.getToUserName();
		String openId = msg.getFromUserName();
		// 1.检查用户是否有关注，如果以关注不需要处理
		User user = this.userRepository.findByOpenId(openId);
		// 2.如果用户未关注，则需要调用远程接口获取用户的资料，即使之前有关注，现在也重新获取用户信息
		if (user == null || user.getStatus() != User.Status.IS_SUBSCRIBE) {
		
			// 调用远程接口
			User wxUser = weiXinProxy.getUser(account, openId);
			if (wxUser == null) {
				return;
			}
			// 3.把用户信息保存到数据库
			if (user != null) {
				wxUser.setId(user.getId());
				wxUser.setSubTime(user.getSubTime());
			} else {
				wxUser.setSubTime(new Date());
			}
			wxUser.setStatus(User.Status.IS_SUBSCRIBE);
			this.userRepository.save(wxUser);

			this.weiXinProxy.sendText(account, openId, "欢迎关注我的公众号，回复菜单可以获得人工智能菜单");
		}
	}
}
