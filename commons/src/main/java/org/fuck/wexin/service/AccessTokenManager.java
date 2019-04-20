package org.fuck.wexin.service;

public interface AccessTokenManager {
	/**
	 * 
	 * 此方法都要返回一个合法的令牌，如果没有获取令牌应该抛出异常
	 * @param account 微信公众号的微信号，要根据微信号找到对应的开发者ID，要通过开发者ID和密码才能得到正确的令牌
	 * @return
	 * 没有令牌throws RuntimeException
	 */
	String getToken(String account) throws RuntimeException;

}
