package com.kaen.config.session.impl;

import java.util.Map;

import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.kaen.config.session.LoserStarSession;

/**
 * author: loserStar
 * date: 2023年12月21日上午9:36:18
 * remarks:基于redis的session
 */
public class RedisSession extends LoserStarSession{
	
	public RedisSession(String sessionId, Map<String, Object> attrMap) {
		super(sessionId, attrMap);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void setAttr(String key,Object value) {
		this.attrMap.put(key, value);
		Cache cache = Redis.use();
        cache.set(this.sessionId, this);
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
        Cache cache = Redis.use();
        cache.set(this.sessionId, this);
    }
	@Override
	public void destroy() {
		Cache cache = Redis.use();
		cache.del(this.sessionId);
	}
}
