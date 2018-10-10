package com.adks.dubbo.api.data.course;

import java.io.Serializable;
import java.util.Date;

public class HeartBeat implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 自增id
	 */
	private  Integer id;
	
	/**
	 * 课件地址
	 */
	private  String Url;
	
	/**
	 * 课件所属范围
	 */
	private  String Area;
	
	/**
	 * 状态  活着/死了  alive/dead
	 */
	private  String State;
	
	/**
	 * 记录时间
	 */
	private  Date RecvedTime;
	
	/**
	 * 记录时间毫秒数
	 */
	private  Long RecvedTimeLong;
	

	
	private static HeartBeat heartBeat = null;
	
	
	
	private HeartBeat() {
		
	}
	
	
	public static HeartBeat getHeartBeatInstance() {
		
		if (heartBeat == null) {
			
			heartBeat = new HeartBeat(); 
		}
		
		return heartBeat;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public Date getRecvedTime() {
		return RecvedTime;
	}

	public void setRecvedTime(Date recvedTime) {
		RecvedTime = recvedTime;
	}


	public Long getRecvedTimeLong() {
		return RecvedTimeLong;
	}


	public void setRecvedTimeLong(Long recvedTimeLong) {
		RecvedTimeLong = recvedTimeLong;
	}
	
	
}
