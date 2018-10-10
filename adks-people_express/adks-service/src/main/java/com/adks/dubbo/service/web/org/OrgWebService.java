package com.adks.dubbo.service.web.org;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.dao.web.org.OrgWebDao;
import com.adks.dubbo.util.OrgRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class OrgWebService extends BaseService<OrgWebDao> {

    @Autowired
    private OrgWebDao orgDao;

    @Override
    protected OrgWebDao getDao() {
        return orgDao;
    }

    public Adks_org getOrgById(Integer orgId) {
    	Adks_org org =null;
    	String result=OrgRedisUtil.getObject(OrgRedisUtil.adks_org_id, orgId);
		if("".equals(result) || "Nodata".equals(result)){
			org = orgDao.getOrgById(orgId);
			OrgRedisUtil.addOrg(OrgRedisUtil.adks_org_id, orgId, org);
		}else{
			org=JSONObject.parseObject(result, Adks_org.class);
		}
        return org;
    }

    public List<Adks_org> getOrgTopList(Map map) {
        List<Adks_org> list = null;
        Integer orgId=MapUtils.getInteger(map, "orgId");
        String result=OrgRedisUtil.getObject(OrgRedisUtil.adks_org_topList, orgId);
		if("".equals(result) || "Nodata".equals(result)){
			 list = orgDao.getOrgTopList(map);
			OrgRedisUtil.addTopOrgList(OrgRedisUtil.adks_org_topList, orgId, list);
		}else{
			list=JSONObject.parseArray(result, Adks_org.class);
		}
        return list;
    }

    public Adks_org getTopOrgAvgStudyTimeUserList(Map map) {
        return orgDao.getTopOrgAvgStudyTimeUserList(map);
    }
    
    public List<Adks_org> getOrgParentIdIsZero() {
    	List<Adks_org> list = null;
        String result=OrgRedisUtil.getObject(OrgRedisUtil.adks_org_topList, 0);
		if("".equals(result) || "Nodata".equals(result)){
			 list = orgDao.getOrgParentIdIsZero();
			OrgRedisUtil.addTopOrgList(OrgRedisUtil.adks_org_topList, 0, list);
		}else{
			list=JSONObject.parseArray(result, Adks_org.class);
		}
		return list;
	}
}
