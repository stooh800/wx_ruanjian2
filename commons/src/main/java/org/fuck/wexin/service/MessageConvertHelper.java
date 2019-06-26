package org.fuck.wexin.service;

import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXB;

import org.fuck.wexin.domain.InMessage;
import org.fuck.wexin.domain.image.ImageInMessage;
import org.fuck.wexin.domain.text.TextInMessage;

public class MessageConvertHelper {

	// 1.使用一个Map来记录消息类型和Java类型的关系
	private static final Map<String, Class<? extends InMessage>> typeMap = new ConcurrentHashMap<>();
	static {
		typeMap.put("text", TextInMessage.class);
		typeMap.put("image", ImageInMessage.class);
		typeMap.put("vioce", TextInMessage.class);
		typeMap.put("video", TextInMessage.class);
		typeMap.put("location", TextInMessage.class);
		typeMap.put("event", TextInMessage.class);
		typeMap.put("link", TextInMessage.class);
		typeMap.put("shortvideo", TextInMessage.class);
	}

	public static Class<? extends InMessage> getClass(String xml) {
		// 获取类型
		String type = xml.substring(xml.indexOf("<MsgType><![CDATA[") + 18);
		type = type.substring(0, type.indexOf("]"));

		// 获取Java类
		Class<? extends InMessage> c = typeMap.get(type);
		return c;
	}

	// 2.把XML转换为Java对象
	public static <T extends InMessage> T convert(String xml) {
		Class<? extends InMessage> c = getClass(xml);
		if (c == null) {
			return null;
		}

		// 使用JAXB转换
		@SuppressWarnings("unchecked")
		T msg = (T) JAXB.unmarshal(new StringReader(xml), c);

		return msg;
	}
}
