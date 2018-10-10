package com.adks.dubbo.providers.admin.grade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.commons.util.RedisConstant;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.interfaces.admin.grade.GradeNoticeApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.news.NewsService;
import com.alibaba.fastjson.JSONObject;
/**
 * 
 * ClassName GradeNoticeApi
 * @Description：班级公告Api实现
 * @author xrl
 * @Date 2017年4月26日
 */
public class GradeNoticeApiImpl implements GradeNoticeApi {
	
	@Autowired
	private NewsService newService;

	/**
	 * 
	 * @Title getGradeNoticeListPage
	 * @Description：班级公告分页列表
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param paramPage
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getGradeNoticeListPage(Page<List<Map<String, Object>>> paramPage) {
		return newService.getGradeNoticeListPage(paramPage);
	}

	/**
	 * 
	 * @Title getGradeNoticeById
	 * @Description:根据公告ID获取公告详情
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param newsId
	 * @return
	 */
	@Override
	public Adks_news getGradeNoticeById(Integer newsId) {
		return newService.getNewsById(newsId);
	}

	/**
	 * 
	 * @Title saveGradeNotice
	 * @Description：保存班级公告
	 * @author xrl
	 * @Date 2017年4月26日
	 * @param adksNews
	 */
	@Override
	public Integer saveGradeNotice(Adks_news adksNews) {
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("newsTitle", adksNews.getNewsTitle());
        insertColumnValueMap.put("newsContent", adksNews.getNewsContent());
        insertColumnValueMap.put("newsHtmlAdress", adksNews.getNewsHtmlAdress());
        insertColumnValueMap.put("gradeId", adksNews.getGradeId());
        insertColumnValueMap.put("creatorId", adksNews.getCreatorId());
        insertColumnValueMap.put("creatorName", adksNews.getCreatorName());
        insertColumnValueMap.put("createTime", adksNews.getCreateTime());
        insertColumnValueMap.put("sysType", adksNews.getSysType());
        int newsId=0;
        if (adksNews.getNewsId() != null) {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("newsId", adksNews.getNewsId());
            newsId=newService.update(insertColumnValueMap, updateWhereConditionMap);
        }
        else {
            newsId = newService.insert(insertColumnValueMap);
        }
        return newsId;
	}

	/**
	 * 
	 * @Title checkNewsName
	 * @Description：检查班级公告名在该班级中的唯一性
	 * @author xrl
	 * @Date 2017年5月10日
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, Object> checkNewsName(Map<String, Object> map) {
		return newService.checkNewsName(map);
	}

	/**
	 * 
	 * @Title deleteGradeNoticeByIds
	 * @Description：删除班级公告
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param newsId
	 */
	@Override
	public void deleteGradeNoticeByIds(Integer newsId) {
		newService.deleteGradeNoticeById(newsId);
	}

}
