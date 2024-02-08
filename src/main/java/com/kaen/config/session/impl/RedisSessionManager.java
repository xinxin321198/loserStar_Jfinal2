package com.kaen.config.session.impl;

import java.util.HashMap;
import java.util.UUID;

import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.kaen.config.session.LoserStarSession;
import com.kaen.config.session.LoserStarSessionManager;

/**
 * 
 * author: loserStar
 * date: 2023年12月21日下午12:09:59
 * remarks:redis的session管理器
 */
public class RedisSessionManager implements LoserStarSessionManager {

	@Override
	public RedisSession getSession(String sessionId) throws Exception {
		Object session = null;
		try {
			Cache cache = Redis.use();
			if (sessionId==null) {
				//seesionId为null就创建一个seesion
				session = new RedisSession(UUID.randomUUID().toString(),new HashMap<String, Object>());
				cache.set(((LoserStarSession)session).getSessionId(), session);
			}else {
				//sessionId有值，就查缓存有没有，没有就创建一个
				session = cache.get(sessionId);
				if (session==null) {
					session = new RedisSession(UUID.randomUUID().toString(),new HashMap<String, Object>());
					cache.set(((LoserStarSession)session).getSessionId(), session);
				}
			}
			if (session instanceof RedisSession) {
			}else {
				session = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("创建或获取会话出错，请清除网站cookie后刷新网页重试");
		}
		
		
		return (RedisSession) session;
	}

}
