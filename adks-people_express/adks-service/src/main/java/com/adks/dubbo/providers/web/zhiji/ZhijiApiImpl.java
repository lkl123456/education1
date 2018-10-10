package com.adks.dubbo.providers.web.zhiji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.zhiji.Adks_rank;
import com.adks.dubbo.api.interfaces.web.zhiji.ZhijiApi;
import com.adks.dubbo.service.web.zhiji.ZhijiWebService;

public class ZhijiApiImpl implements ZhijiApi{

	@Autowired
	private ZhijiWebService zhijiService;
	
	@Override
	public List<Adks_rank> getZhijiListAll() {
		List<Adks_rank> list=zhijiService.getZhijiListAll();
		return list;
	}

	@Override
	public Map<String, Object> getZHijiByName(String name) {
		return zhijiService.getZHijiByName(name);
	}

	@Override
	public Integer saveZhiji(Adks_rank rank) {
		Integer flag=0;
		//rank
		Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
		insertColumnValueMap.put("rankName", rank.getRankName());
		insertColumnValueMap.put("parentId", rank.getParentId());
		insertColumnValueMap.put("parentName", rank.getParentName());
		insertColumnValueMap.put("orderNum", rank.getOrderNum());
		insertColumnValueMap.put("isdelete", rank.getIsdelete());
		insertColumnValueMap.put("zhijiId", rank.getZhijiId());
		flag=zhijiService.insert(insertColumnValueMap);
		
		insertColumnValueMap.clear();
		insertColumnValueMap.put("rankCode","0A"+flag+"A");
		
		Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
		updateWhereConditionMap.put("rankId", flag);
		Integer rankId=flag;
		flag=zhijiService.update(insertColumnValueMap, updateWhereConditionMap);
		return rankId;
	}

	@Override
	public List<Adks_rank> getZhijiListByCon(Integer rankId) {
		return zhijiService.getZhijiListByCon(rankId);
	}

}
