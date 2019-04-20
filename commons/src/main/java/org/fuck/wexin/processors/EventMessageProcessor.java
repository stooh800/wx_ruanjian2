package org.fuck.wexin.processors;

import org.fuck.wexin.domain.event.EventInMessage;

public interface EventMessageProcessor {
	void onMessage(EventInMessage msg);

}
