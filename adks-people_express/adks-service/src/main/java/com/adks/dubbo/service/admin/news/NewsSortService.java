package com.adks.dubbo.service.admin.news;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.news.Adks_news_sort;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.news.NewsSortDao;
import com.adks.dubbo.util.NewsRedisUtil;

@Service
public class NewsSortService extends BaseService<NewsSortDao> {

	@Autowired
	private NewsSortDao newsSortDao;

	@Override
	protected NewsSortDao getDao() {
		return newsSortDao;
	}

	public Page<List<Map<String, Object>>> getNewsSortListPage(Page<List<Map<String, Object>>> page) {
		return newsSortDao.getNewsSortListPage(page);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteNewsSortByIds(Map<String, Object> map) {

//		String orgIdsStr = MapUtils.getString(map, "delOrgIds");
		String newsSortIds = MapUtils.getString(map, "delNewsSortIds");
//		String[] orgIds = orgIdsStr.split(",");

//		Set set = new HashSet();
//		for (int i = 0; i < orgIds.length; i++) {
//			set.add(orgIds[i]);
//		}
//		String[] orgIdsNoSame = (String[]) set.toArray(new String[set.size()]);
//
//		String orgId = "";
//		for (String string : orgIdsNoSame) {
//			orgId += string + "_" + 6 + ",";
//		}
//		orgId = orgId.substring(0, orgId.length() - 1);
//
//		NewsRedisUtil.emptyNews(orgId);
		newsSortDao.deleteNewsSortByIds(newsSortIds);

//		List<Adks_news_sort> list = newsSortDao.getNewsSortListByCon(map);
//		NewsRedisUtil.addNewsSort(MapUtils.getInteger(map, "orgId"), list);

	}

	public Map<String, Object> getNewsSortInfoById(Integer newsSortId) {
		return newsSortDao.queryOneById(newsSortId);
	}

	public List<Adks_news_sort> getNewsSortList(Map<String, Object> map) {
		return newsSortDao.getNewsSortList(map);
	}

	public Map<String, Object> getNewsSortById(Integer newsSortId) {
		return newsSortDao.getNewsSortById(newsSortId);
	}

	public List<Adks_news_sort> getNewsSortListByCon(Map<String, Object> map) {
		return newsSortDao.getNewsSortListByCon(map);
	}

	public void setNewsSortToRedis(Map<String, Object> map) {
		NewsRedisUtil.emptyNews(MapUtils.getInteger(map, "orgId") + "_" + 6);

		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
			List<Adks_news_sort> list = newsSortDao.getNewsSortListByCon(map);
			NewsRedisUtil.addNewsSort(MapUtils.getInteger(map, "orgId"), list);
		}
	}
}
