package com.adks.dubbo.service.web.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.dao.web.org.OrgConfigWebDao;
import com.adks.dubbo.util.OrgRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class OrgConfigWebService extends BaseService<OrgConfigWebDao> {

    @Autowired
    private OrgConfigWebDao orgConfigDao;

    @Override
    protected OrgConfigWebDao getDao() {
        return orgConfigDao;
    }

    public Adks_org_config getOrgConfigByOrgId(Integer orgId) {
        Adks_org_config org_config = null;
        String result=OrgRedisUtil.getObject(OrgRedisUtil.adks_org_config_id, orgId);
		if("".equals(result) || "Nodata".equals(result)){
			org_config = orgConfigDao.getOrgConfigByOrgId(orgId);
			OrgRedisUtil.addOrgConfig(OrgRedisUtil.adks_org_config_id, orgId, org_config);
		}else{
			org_config=JSONObject.parseObject(result, Adks_org_config.class);
		}
        return org_config;
    }

}
