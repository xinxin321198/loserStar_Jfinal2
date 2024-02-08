package com.kaen.config.session;

/**
 * author: loserStar
 * date: 2023年12月21日下午12:05:15
 * remarks:自定义的session管理器，集成它，去利用缓存实现一个session管理
 */
public interface LoserStarSessionManager {
	/**
	 * 根据sessionId去获取缓存中的session对象或者sessionId为null就创建一个对象
	 */
	public abstract  LoserStarSession getSession(String sessionId) throws Exception;
}
