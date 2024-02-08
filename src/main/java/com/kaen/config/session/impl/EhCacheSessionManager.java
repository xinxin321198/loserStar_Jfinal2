package com.kaen.config.session.impl;

import java.util.HashMap;
import java.util.UUID;

import com.jfinal.plugin.ehcache.CacheKit;
import com.kaen.config.session.LoserStarSession;
import com.kaen.config.session.LoserStarSessionManager;

public class EhCacheSessionManager implements LoserStarSessionManager {
	/**
	 * 对应ehcache.xml中的一个缓存配置名称
	 */
	public static String ehCacheSessionName = "EhCacheSession";
	
	@Override
	public LoserStarSession getSession(String sessionId) throws Exception{
		Object session = null;
		try {
			if (sessionId==null) {
				//seesionId为null就创建一个seesion
				session = new EhCacheSession(UUID.randomUUID().toString(),new HashMap<String, Object>());
				CacheKit.put(ehCacheSessionName, ((LoserStarSession)session).getSessionId(), session);
			}else {
				//sessionId有值，就查缓存有没有，没有就创建一个
				session = CacheKit.get(ehCacheSessionName, sessionId);
				if (session==null) {
					session = new EhCacheSession(UUID.randomUUID().toString(),new HashMap<String, Object>());
					CacheKit.put(ehCacheSessionName, ((LoserStarSession)session).getSessionId(), session);
				}
			}
			if (session instanceof EhCacheSession) {
			}else {
				session = null;
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("创建或获取会话出错，请清除网站cookie后刷新网页重试");
		}
		return (EhCacheSession) session;
	}
}
