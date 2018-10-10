package com.adks.dubbo.service.admin.org;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.org.OrgDao;
import com.adks.dubbo.util.OrgRedisUtil;

@Service
public class OrgService extends BaseService<OrgDao> {

    @Autowired
    private OrgDao orgDao;

    public List<Map<String, Object>> getOrgsList() {
        return orgDao.getOrgsList();
    }

    public boolean checkOrgHasUser(String code) {
        return orgDao.checkOrgHasUser(code);
    }

    @Override
    protected OrgDao getDao() {
        return orgDao;
    }
    //清除缓存
    public void deleteRedisByName(String redisName){
    	OrgRedisUtil.emptyObject(redisName);
    } 

    //机构是同步机构表跟用户表的机构名称
    public void checkOrgNameToUser(Integer orgId, String orgName) {
        orgDao.checkOrgNameToUser(orgId, orgName);
    }

    public List<Adks_org> getOrgsListByParent(Integer parentId) {
        return orgDao.getOrgsListByParent(parentId);
    }

    public List<Adks_org> getOrgsListByClass(Integer parentId) {
        return orgDao.getOrgsListByClass(parentId);
    }

    public Adks_org getOrgById(Integer orgId) {
        return orgDao.getOrgById(orgId);
    }

    public void deleteOrgByIds(String ids) {
    	//清除org以及orgConfig的缓存
    	String[] orgIds=ids.split(",");
    	if(orgIds.length>0){
    		String orgIdsRedis="";
    		String orgConfigIdsRedis="";
    		for(int i=0;i<orgIds.length;i++){
    			orgIdsRedis+="id__"+orgIds[i]+",";
    			orgConfigIdsRedis+="config_id__"+orgIds[i]+",";
    		}
    		OrgRedisUtil.emptyObject(orgIdsRedis);
    		OrgRedisUtil.emptyObject(orgConfigIdsRedis);
    	}
        orgDao.deleteOrgByIds(ids);
    }

    public Page<List<Map<String, Object>>> getOrgListPage(Page<List<Map<String, Object>>> page) {
        return orgDao.getOrgListPage(page);
    }

    public Map<String, Object> getOrgByName(String name) {
        return orgDao.getOrgByName(name);
    }

    public List<Adks_org> getOrgsListAll(String orgCode) {
        return orgDao.getOrgsListAll(orgCode);
    }

    public List<Adks_org> getOrgsListAll2(Integer parentId) {
        List<Adks_org> list = orgDao.getOrgsListAll2(parentId);
        return list;
    }
    
    public Adks_org getTopOrgAvgStudyTimeUserList(Map map) {
        return orgDao.getTopOrgAvgStudyTimeUserList(map);
    }

    public void updateOrgUserNum(String orgCode,Integer falg) {
    	orgDao.updateOrgUserNum(orgCode,falg);
	}
    
    /**
     * 
     * @Title getOrgConfig
     * @Description：获取机构配置
     * @author xrl
     * @Date 2017年5月15日
     * @return
     */
    public List<Adks_org_config> getOrgConfig(){
    	return orgDao.getOrgConfig();
    }
    //机构学习统计
    public Page<List<Map<String, Object>>> getOrgStatisticsListJson(Page<List<Map<String, Object>>> page) {
		return orgDao.getOrgStatisticsListJson(page);
	}
    //根据机构查询机构的学习总学时、总人数
    public List<Map<String, Object>> getOrgStudyList(String orgCode){
		return orgDao.getOrgStudyList(orgCode);
	}
}
