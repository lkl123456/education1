package com.adks.dubbo.api.interfaces.admin.zhiji;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.zhiji.Adks_rank;
import com.adks.dubbo.commons.Page;

public interface ZhijiApi {
	
	public List<Map<String, Object>> getZhijisList();
	
	public List<Adks_rank> getZhijisListByClass(Integer parentId);
	
	public List<Adks_rank> getZhijisListAll();
	
	public Integer saveZhiji(Adks_rank Zhiji);
	
	public void deleteZhijiByIds(String ids);
	
	public Map<String, Object> getZhijiByName(String name);
	
	//根据id得到Zhiji
	public Adks_rank getZhijiById(Integer ZhijiId);
	//根据名称查看这个职务是否已经存在
	public Map<String,Object> getZhiWuByName(String name);
	
	//根据职级id得到职务list
	public List<Adks_rank> getZhijiListByCon(Integer rankId);
	
	//保存职务
	public Integer saveZhiWu(Adks_rank rank);
	
	//获取机构分页
	public Page<List<Map<String, Object>>> getZhijiListPage(Page<List<Map<String, Object>>> page);
	
}
