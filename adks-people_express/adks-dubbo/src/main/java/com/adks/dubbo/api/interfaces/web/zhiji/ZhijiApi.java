package com.adks.dubbo.api.interfaces.web.zhiji;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.zhiji.Adks_rank;

public interface ZhijiApi {
	
	public List<Adks_rank> getZhijiListAll();
	
	//根据名称查看这个职务是否已经存在
	public Map<String,Object> getZHijiByName(String name);
	
	//保存职务
	public Integer saveZhiji(Adks_rank rank);
	
	//根据职级id得到职务list
	public List<Adks_rank> getZhijiListByCon(Integer rankId);
}
