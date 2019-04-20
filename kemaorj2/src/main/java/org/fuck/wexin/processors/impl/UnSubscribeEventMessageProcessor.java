package org.fuck.wexin.processors.impl;

import org.fuck.wexin.domain.event.EventInMessage;
import org.fuck.wexin.processors.EventMessageProcessor;
import org.springframework.stereotype.Service;


//把Bean加入容器管理，默认类名首字母小写作为ID
//如果@Service注解有值则表示自定义ID
@Service("unsubscribeMessageProcessor")
public class UnSubscribeEventMessageProcessor implements EventMessageProcessor {

	@Override
	public void onMessage(EventInMessage msg) {
		System.out.println("取消关注消息处理器: " + msg);
		// 1.解除用户的关注状态
		// 一般不删除数据，而是把数据标记为已经取消关注
	}
}
