package com.adks.dubbo.api.interfaces.admin.org;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.commons.Page;

public interface OrgApi {
	
	//清除首页机构集合的缓存
	public void deleteOrgTopRedis(String parentIds);
	
	public List<Map<String, Object>> getOrgsList();
	//combotree在使用该方法
	public List<Adks_org> getOrgsListByClass(Integer parentId);
	
	public List<Adks_org> getOrgsListByParent(Integer parentId);
	
	//将舍弃
	public List<Adks_org> getOrgsListAll(String orgCode);
	public List<Adks_org> getOrgsListAll2(Integer parentId);
	
	public Integer saveOrg(Adks_org org);
	
	public void deleteOrgByIds(String ids);
	//机构是同步机构表跟用户表的机构名称
	public void checkOrgNameToUser(Integer orgId,String orgName);
	
	public Map<String, Object> getOrgByName(String name);
	
	//根据id得到Org
	public Adks_org getOrgById(Integer orgId);
	
	//获取机构分页
	public Page<List<Map<String, Object>>> getOrgListPage(Page<List<Map<String, Object>>> page);
	
	
	//以下是org配置信息的方法
	
	public Integer saveOrgConfig(Adks_org_config orgConfig);
	
	public void deleteOrgCinfig(String ids);
	
	public Adks_org_config getOrgConfigById(Integer orgConfigId);
	public Adks_org_config getOrgConfigByOrgId(Integer orgId);
	
	public boolean checkOrgHasUser(String code);
	/**
	 * 
	 * @Title: getTopOrgAvgStudyTimeUserList 
	 * @Description: 机构学员平均学时排行
	 * @param @param map
	 * @param @return  
	 * @return List<CourseUser> 
	 * @throws 
	 * @author zlm 
	 * @date May 18, 2015 9:55:07 AM
	 */
	public Adks_org  getTopOrgAvgStudyTimeUserList(Map map);
	
	public void updateOrgUserNum(String orgCode,Integer falg);
	
	/**
	 * 
	 * @Title getOrgConfig
	 * @Description:获取机构配置
	 * @author xrl
	 * @Date 2017年5月15日
	 * @return
	 */
	public List<Adks_org_config> getOrgConfig();
	
	//机构学习统计
	public Page<List<Map<String, Object>>> getOrgStatisticsListJson(Page<List<Map<String, Object>>> page);
	//查询机构的总学时、总人数、毕业人数
	public List<Map<String, Object>> getOrgStudyList(String orgCode);
}
