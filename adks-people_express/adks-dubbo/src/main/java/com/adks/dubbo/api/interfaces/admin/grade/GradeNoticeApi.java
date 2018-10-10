package com.adks.dubbo.api.interfaces.admin.grade;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;

/**
 * 
 * ClassName GradeNotice
 * @Description：班级公告Api
 * @author xrl
 * @Date 2017年4月26日
 */
public interface GradeNoticeApi {

	/**
	 * 
	 * @Title getGradeNoticeListPage
	 * @Description：班级公告分页列表
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeNoticeListPage(Page<List<Map<String, Object>>> paramPage);
	
	/**
	 * 
	 * @Title getGradeNoticeById
	 * @Description:根据公告ID获取公告详情
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param newsId
	 * @return
	 */
	public Adks_news getGradeNoticeById(Integer newsId);
	
	/**
	 * 
	 * @Title saveGradeNotice
	 * @Description：保存班级公告
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param adksNews
	 */
	public Integer saveGradeNotice(Adks_news adksNews);
	
	/**
	 * 
	 * @Title checkNewsName
	 * @Description：检查班级公告名在该班级中的唯一性
	 * @author xrl
	 * @Date 2017年5月10日
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkNewsName(Map<String, Object> map);
	
	/**
	 * 
	 * @Title deleteGradeNoticeByIds
	 * @Description：删除班级公告
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param newsId
	 */
	public void deleteGradeNoticeByIds(Integer newsId);
}
