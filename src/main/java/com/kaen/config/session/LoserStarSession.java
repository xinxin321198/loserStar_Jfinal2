package com.kaen.config.session;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * author: loserStar
 * date: 2023年12月21日上午11:09:33
 * remarks:自定义的seesion对象
 */
public abstract class LoserStarSession implements Serializable{
	protected static final long serialVersionUID = 1L;
	/**
	 * sessionId唯一标识
	 */
	protected String sessionId;
	/**
	 * 存储的数据Map
	 */
	@SuppressWarnings("unused")
	protected Map<String, Object> attrMap = null;
	
	/**
	 * 空的构造方法设为私有，允许直接new，请需要使用两个参数的生成，
	 */
	@SuppressWarnings("unused")
	private LoserStarSession() {
		
	}
	public LoserStarSession(String sessionId, Map<String, Object> attrMap) {
		super();
		this.sessionId = sessionId;
		this.attrMap = attrMap;
	}
	/**
	 * 获取当前seesion的id（该id全球唯一，最好使用uuid）
	 * @return
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * 存储数据（需实现）
	 * @param key 键
	 * @param value 值
	 */
	public abstract void setAttr(String key,Object value);
	
	/**
	 * 获取值（需实现）
	 * @param key
	 * @return
	 */
	public abstract <T> T getAttr(String key);
	
	/**
	 * 删除值（需实现）
	 * @param key
	 */
	public abstract void removeAttribute(String key);
	
	/**
	 * 销毁该session对象（需实现从缓存中移除）
	 */
	public abstract void destroy();
	
	public Map<String, Object> getAttrMap() {
		return attrMap;
	}
}
