package com.adks.dubbo.api.interfaces.admin.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;

public interface SysNoticeApi {

	/**
	 * 系统公告分页列表
	 * 
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getSysNoticeListPage(Page<List<Map<String, Object>>> paramPage);

	/**
	 * 根据公告ID获取公告详情
	 * 
	 * @param newsId
	 * @return
	 */
	public Adks_news getSysNoticeById(Integer newsId);

	/**
	 * 保存系统公告
	 * 
	 * @param adksNews
	 */
	public Integer saveSysNotice(Adks_news adksNews);

	/**
	 * 检查系统公告名在该系统中的唯一性
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkNewsName(Map<String, Object> map);
	
	/**
	 * 
	 * @Title deleteSysNotice
	 * @Description：删除系统公告
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param newsId
	 */
	public void deleteSysNotice(Integer newsId);
}
