package com.adks.dubbo.api.interfaces.web.org;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.commons.Page;

public interface OrgApi {
	
	public Adks_org getOrgById(Integer orgId);
	
	//以下是org配置信息的方法
	public Adks_org_config getOrgConfigByOrgId(Integer orgId);
	
	/*
	 * 获取顶级机构列表（首页排序）
	 */
	public List<Adks_org> getOrgTopList(Map map);
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
	
	//得到所有的一级机构
	public List<Adks_org> getOrgParentIdIsZero();
}
