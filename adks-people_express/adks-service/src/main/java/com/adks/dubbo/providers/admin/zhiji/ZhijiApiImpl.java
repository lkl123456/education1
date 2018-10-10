package com.adks.dubbo.providers.admin.zhiji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.zhiji.Adks_rank;
import com.adks.dubbo.api.interfaces.admin.zhiji.ZhijiApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.zhiji.ZhijiService;

public class ZhijiApiImpl implements ZhijiApi{

	@Autowired
	private ZhijiService zhijiService;

	@Override
	public List<Map<String, Object>> getZhijisList(){
		return zhijiService.getZhijisList();
	}

	@Override
	public List<Adks_rank> getZhijisListByClass(Integer parentId) {
		List<Adks_rank> list=zhijiService.getZhijisListByClass(parentId);
		if(list!=null && list.size()>0){
			for(Adks_rank rank:list){
				rank.setText(rank.getName());
				List<Adks_rank> child=zhijiService.getZhijisListByClass(rank.getId());
				if(child!=null){
					for(Adks_rank rankChild:child){
						rankChild.setText(rankChild.getName());
					}
				}
				rank.setChildren(child);
			}
		}
		return list;
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
		if(rank.getRankId()!=null){
			Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
			updateWhereConditionMap.put("rankId", rank.getRankId());
			flag=zhijiService.update(insertColumnValueMap, updateWhereConditionMap);
		}else{
			insertColumnValueMap.put("isdelete", rank.getIsdelete());
			flag=zhijiService.insert(insertColumnValueMap);
			if(rank.getParentId()==0){
				insertColumnValueMap.put("rankCode","0A"+flag+"A");
			}else{
				Adks_rank parentZhiji=zhijiService.getZhijiById(rank.getParentId());
				if(parentZhiji!=null && parentZhiji.getRankCode()!=null){
					insertColumnValueMap.put("rankCode",parentZhiji.getRankCode()+flag+"A");
				}
			}
			
			Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
			updateWhereConditionMap.put("rankId", flag);
			Integer rankId=flag;
			zhijiService.update(insertColumnValueMap, updateWhereConditionMap);
		}
		return flag;
	}

	@Override
	public void deleteZhijiByIds(String ids) {
		zhijiService.deleteZhijiByIds(ids);
	}

	@Override
	public Page<List<Map<String, Object>>> getZhijiListPage(Page<List<Map<String, Object>>> page) {
		return zhijiService.getZhijiListPage(page);
	}

	@Override
	public Map<String, Object> getZhijiByName(String name) {
		return zhijiService.getZhijiByName(name);
	}

	@Override
	public Adks_rank getZhijiById(Integer rankId) {
		return zhijiService.getZhijiById(rankId);
	}

	@Override
	public List<Adks_rank> getZhijisListAll() {
		List<Adks_rank> list=zhijiService.getZhijisListAll();
		List<Adks_rank> list1=new ArrayList<Adks_rank>();
		if(list!=null && list.size()>0){
			
			for(Adks_rank rank:list){
				rank.setText(rank.getName());
				List<Adks_rank> child=new ArrayList<Adks_rank>();
				for(Adks_rank temp:list){
					if(temp.getParentId() ==rank.getId()){
						child.add(temp);
					}
				}
				if(child!= null && child.size()>0){
					rank.setChildren(child);
				}
				if(rank.getParentId() == null || rank.getParentId() == 0){
					list1.add(rank);
				}
			}
		}
		return list;
	}
	@Override
	public List<Adks_rank> getZhijiListByCon(Integer rankId) {
		return zhijiService.getZhijiListByCon(rankId);
	}
	@Override
	public Map<String, Object> getZhiWuByName(String name) {
		return zhijiService.getZhiWuByName(name);
	}
	@Override
	public Integer saveZhiWu(Adks_rank rank) {
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
}
