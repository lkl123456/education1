package com.adks.dubbo.providers.admin.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.api.interfaces.admin.news.NewsSortApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.news.NewsSortService;

public class NewsSortApiImpl implements NewsSortApi {

	@Autowired
	private NewsSortService newsSortService;

	@Override
	public Page<List<Map<String, Object>>> getNewsSortListPage(Page<List<Map<String, Object>>> page) {
		return newsSortService.getNewsSortListPage(page);
	}

	@Override
	public Integer saveNewsSort(Adks_news_sort adksNewsSort) {
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		insertColumnValueMap.put("newsSortName", adksNewsSort.getNewsSortName());
		insertColumnValueMap.put("newsSortLocation", adksNewsSort.getNewsSortLocation());
		insertColumnValueMap.put("newsSortType", adksNewsSort.getNewsSortType());
		insertColumnValueMap.put("orgId", adksNewsSort.getOrgId());
		insertColumnValueMap.put("orgName", adksNewsSort.getOrgName());
		insertColumnValueMap.put("orgCode", adksNewsSort.getOrgCode());
		insertColumnValueMap.put("creatorId", adksNewsSort.getCreatorId());
		insertColumnValueMap.put("creatorName", adksNewsSort.getCreatorName());
		insertColumnValueMap.put("createTime", adksNewsSort.getCreateTime());

		Integer newsSortId=0;
		if (adksNewsSort.getNewsSortId() != null) {
			Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
			updateWhereConditionMap.put("newsSortId", adksNewsSort.getNewsSortId());
			newsSortId=newsSortService.update(insertColumnValueMap, updateWhereConditionMap);
		} else {
			newsSortId=newsSortService.insert(insertColumnValueMap);
		}
		return newsSortId;
//		map.put("orgId", adksNewsSort.getOrgId());
//		map.put("orgCode", adksNewsSort.getOrgCode());
//		newsSortService.setNewsSortToRedis(map);
	}

	@Override
	public void deleteNewsSortByIds(Map<String, Object> map) {
		newsSortService.deleteNewsSortByIds(map);

	}

	@Override
	public List<Adks_news_sort> getNewsSortList(Map<String, Object> map) {
		return newsSortService.getNewsSortList(map);
	}

	@Override
	public Map<String, Object> getNewsSortById(Integer newsSortId) {
		return newsSortService.getNewsSortById(newsSortId);
	}

	@Override
	public List<Adks_news_sort> getNewsSortListByCon(Map<String, Object> map) {
		return newsSortService.getNewsSortListByCon(map);
	}

}
