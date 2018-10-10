package com.adks.dubbo.commons;

import java.io.Serializable;

/**
 * 排序类
 */
public class Sort implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 排序方式
	 */
	private Direction direction;
	
	/**
	 * 排序字段
	 */
	private String property;
	
	/**
	 * 排序方向，升序或降序
	 * @author panpanxu
	 *
	 */
	public enum Direction {
		/**
		 * 升序
		 */
		ASC,
		
		/**
		 * 降序
		 */
		DESC
	}
	
	/**
	 * 是否为升序
	 * @return
	 */
	public boolean isAsc() {
		return Direction.ASC.equals(direction);
	}
	
	/**
	 * 是否为降序
	 * @return
	 */
	public boolean isDesc() {
		return Direction.DESC.equals(direction);
	}
	
	/**
	 * 构造函数
	 * @param direction - 排序方式
	 * @param property - 排序字段
	 */
	public Sort(Direction direction, String property) {
		this.direction = direction;
		this.property = property;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
}
