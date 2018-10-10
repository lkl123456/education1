package com.adks.dubbo.service.admin.org;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.dao.admin.org.OrgConfigDao;
import com.adks.dubbo.dao.admin.org.OrgDao;
import com.alibaba.fastjson.JSONObject;

@Service
public class OrgConfigService extends BaseService<OrgConfigDao> {

    @Autowired
    private OrgConfigDao orgConfigDao;

    @Override
    protected OrgConfigDao getDao() {
        return orgConfigDao;
    }

    public Adks_org_config getOrgConfigById(Integer orgConfigId) {
        return orgConfigDao.getOrgConfigByOrgId(orgConfigId);
    }

    public void deleteOrgConfigByIds(String ids) {
    	//更新机构配置集合，用于首页显示
        orgConfigDao.deleteOrgConfigByIds(ids);
    }

    public Adks_org_config getOrgConfigByOrgId(Integer orgId) {
        Adks_org_config org_config = orgConfigDao.getOrgConfigByOrgId(orgId);
        return org_config;
    }

}
