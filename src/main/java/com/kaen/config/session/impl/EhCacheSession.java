package com.kaen.config.session.impl;

import java.util.Map;

import com.jfinal.plugin.ehcache.CacheKit;
import com.kaen.config.session.LoserStarSession;

/**
 * 
 * author: loserStar
 * date: 2023年12月21日上午11:30:07
 * remarks:基于EhCache的seesion实现
 */
public class EhCacheSession extends LoserStarSession {
	public EhCacheSession(String sessionId, Map<String, Object> attrMap) {
		super(sessionId, attrMap);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void setAttr(String key, Object value) {
		this.attrMap.put(key, value);
		CacheKit.put(EhCacheSessionManager.ehCacheSessionName, this.sessionId, this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAttr(String key) {
		Object object = this.attrMap.get(key);
		return object!=null?(T)object:null;
	}

	@Override
	public void removeAttribute(String key) {
		this.attrMap.remove(key);
		CacheKit.put(EhCacheSessionManager.ehCacheSessionName, this.sessionId, this);
	}

	@Override
	public void destroy() {
		CacheKit.remove(EhCacheSessionManager.ehCacheSessionName, this.sessionId);
	}
}
