package com.adks.dubbo.providers.admin.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.api.interfaces.admin.news.SysNoticeApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.news.NewsService;

public class SysNoticeApiImpl implements SysNoticeApi {

	@Autowired
	private NewsService newService;

	@Override
	public Page<List<Map<String, Object>>> getSysNoticeListPage(Page<List<Map<String, Object>>> page) {
		return newService.getSysNoticeListPage(page);
	}

	@Override
	public Adks_news getSysNoticeById(Integer newsId) {
		return newService.getNewsById(newsId);
	}

	@SuppressWarnings("unused")
	@Override
	public Integer saveSysNotice(Adks_news adksNews) {
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		insertColumnValueMap.put("newsTitle", adksNews.getNewsTitle());
		insertColumnValueMap.put("newsContent", adksNews.getNewsContent());
		insertColumnValueMap.put("newsHtmlAdress", adksNews.getNewsHtmlAdress());
		insertColumnValueMap.put("creatorId", adksNews.getCreatorId());
		insertColumnValueMap.put("creatorName", adksNews.getCreatorName());
		insertColumnValueMap.put("createTime", adksNews.getCreateTime());
		insertColumnValueMap.put("sysType", adksNews.getSysType());
		Integer newsId=0;
		if (adksNews.getNewsId() != null) {
			Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
			updateWhereConditionMap.put("newsId", adksNews.getNewsId());
			newsId=newService.update(insertColumnValueMap, updateWhereConditionMap);
		} else {
			newsId= newService.insert(insertColumnValueMap);
		}
		return newsId;
	}

	@Override
	public Map<String, Object> checkNewsName(Map<String, Object> map) {
		return newService.checkNewsName(map);
	}

	/**
	 * 
	 * @Title deleteSysNotice
	 * @Description：删除系统公告
	 * @author xrl
	 * @Date 2017年5月20日
	 * @param newsId
	 */
	@Override
	public void deleteSysNotice(Integer newsId) {
		newService.deleteGradeNoticeById(newsId);
	}

}
