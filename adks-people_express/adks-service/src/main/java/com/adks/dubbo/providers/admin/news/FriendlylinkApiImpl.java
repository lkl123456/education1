package com.adks.dubbo.providers.admin.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.common.Adks_friendly_link;
import com.adks.dubbo.api.interfaces.admin.news.FriendlylinkApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.news.FriendlylinkService;

public class FriendlylinkApiImpl implements FriendlylinkApi{
	
	@Autowired
	private FriendlylinkService friendlylinkService;
	
	@Override
	public Page<List<Map<String, Object>>> getFriendlylinkListPage(Page<List<Map<String, Object>>> page) {
		return friendlylinkService.getFriendlylinkListPage(page);
	}

	@Override
	public Integer saveFriendlylink(Adks_friendly_link adksFriendlylink) {
		Map<String, Object> insertColumnValueMap = new HashMap<String,Object>();
		insertColumnValueMap.put("fdLinkName", adksFriendlylink.getFdLinkName());
		insertColumnValueMap.put("fdLinkHref", adksFriendlylink.getFdLinkHref());
		insertColumnValueMap.put("orgId", adksFriendlylink.getOrgId());
		insertColumnValueMap.put("orgName", adksFriendlylink.getOrgName());
		insertColumnValueMap.put("orgCode", adksFriendlylink.getOrgCode());
		insertColumnValueMap.put("creatorId", adksFriendlylink.getCreatorId());
		insertColumnValueMap.put("creatorName", adksFriendlylink.getCreatorName());
		insertColumnValueMap.put("createTime", adksFriendlylink.getCreateTime());
		Integer fLinkId=0;
		if(adksFriendlylink.getFdLinkId() != null){
			Map<String, Object> updateWhereConditionMap = new HashMap<String,Object>();
			updateWhereConditionMap.put("fdLinkId", adksFriendlylink.getFdLinkId());
			fLinkId=friendlylinkService.update(insertColumnValueMap, updateWhereConditionMap);
		}else{
			fLinkId=friendlylinkService.insert(insertColumnValueMap);
		}
		return fLinkId;
	}

	@Override
	public void deleteFriendlylinkByIds(String fdLinkIds) {
		friendlylinkService.deleteFriendlylinkByIds(fdLinkIds);
	}

	@Override
	public Map<String, Object> getFriendlylinkInfoById(Integer fdLinkId) {
		return friendlylinkService.getFriendlylinkInfoById(fdLinkId);
	}

}
