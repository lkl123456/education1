package com.adks.dubbo.providers.web.org;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.api.interfaces.web.org.OrgApi;
import com.adks.dubbo.service.web.org.OrgConfigWebService;
import com.adks.dubbo.service.web.org.OrgWebService;

public class OrgApiImpl implements OrgApi{

	@Autowired
	private OrgWebService orgService;
	@Autowired
	private OrgConfigWebService orgConfigService;

	@Override
	public Adks_org getOrgById(Integer orgId) {
		return orgService.getOrgById(orgId);
	}

	@Override
	public Adks_org_config getOrgConfigByOrgId(Integer orgId) {
		return orgConfigService.getOrgConfigByOrgId(orgId);
	}
	/*
	 * 获取顶级机构列表
	 */
	public List<Adks_org> getOrgTopList(Map map){
		return orgService.getOrgTopList(map);
	}
	public Adks_org  getTopOrgAvgStudyTimeUserList(Map map){
		return orgService.getTopOrgAvgStudyTimeUserList(map);
	}

	@Override
	public List<Adks_org> getOrgParentIdIsZero() {
		return orgService.getOrgParentIdIsZero();
	}
}
