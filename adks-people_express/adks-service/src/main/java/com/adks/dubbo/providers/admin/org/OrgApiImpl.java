package com.adks.dubbo.providers.admin.org;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.api.interfaces.admin.org.OrgApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.org.OrgConfigService;
import com.adks.dubbo.service.admin.org.OrgService;

public class OrgApiImpl implements OrgApi {

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgConfigService orgConfigService;

    @Override
    public List<Map<String, Object>> getOrgsList() {
        return orgService.getOrgsList();
    }

    @Override
    public List<Adks_org> getOrgsListByClass(Integer parentId) {
        List<Adks_org> list = orgService.getOrgsListByClass(parentId);
        return list;
    }

    //机构是同步机构表跟用户表的机构名称
    public void checkOrgNameToUser(Integer orgId, String orgName) {
        orgService.checkOrgNameToUser(orgId, orgName);
    }

    @Override
    public List<Adks_org> getOrgsListByParent(Integer parentId) {
        List<Adks_org> list = orgService.getOrgsListByParent(parentId);
        return list;
    }

    @Override
    public Integer saveOrg(Adks_org org) {
        Integer flag = 0;
        //org
        Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("orgName", org.getOrgName());
        insertColumnValueMap.put("parentId", org.getParentId());
        insertColumnValueMap.put("parentName", org.getParentName());
        insertColumnValueMap.put("creatorId", org.getCreatorId());
        insertColumnValueMap.put("creatorName", org.getCreatorName());
        insertColumnValueMap.put("usernum", org.getUsernum());
        insertColumnValueMap.put("orgstudylong", org.getOrgstudylong());
        //orgConfig
        Map<String, Object> insertColumnValueMapConfig = new HashMap<String, Object>();
        insertColumnValueMapConfig.put("orgName", org.getOrgName());
        if (org.getOrgId() != null) {

            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("orgId", org.getOrgId());
            flag = orgService.update(insertColumnValueMap, updateWhereConditionMap);

            Map<String, Object> updateWhereConditionMapConfig = new HashMap<String, Object>();
            updateWhereConditionMapConfig.put("orgId", org.getOrgId());
            flag = orgService.update(insertColumnValueMapConfig, updateWhereConditionMapConfig);
            //更新时，清除这个机构的缓存以及配置的缓存
            orgService.deleteRedisByName("id__"+org.getOrgId());
            orgService.deleteRedisByName("config_id__"+org.getOrgId());
            orgService.deleteRedisByName("topList__"+org.getParentId());
            if(org.getParentId()==0){
            	orgService.deleteRedisByName("config_List_");
            }
        }
        else {
            insertColumnValueMap.put("createtime", org.getCreatetime());
            flag = orgService.insert(insertColumnValueMap);
            if (org.getParentId() == 0) {
                insertColumnValueMap.put("orgCode", "0A" + flag + "A");
            }
            else {
                Adks_org parentOrg = orgService.getOrgById(org.getParentId());
                if (parentOrg != null && parentOrg.getOrgCode() != null) {
                    insertColumnValueMap.put("orgCode", parentOrg.getOrgCode() + flag + "A");
                }
            }
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("orgId", flag);
            Integer orgId = flag;
            orgService.update(insertColumnValueMap, updateWhereConditionMap);
            //System.out.println("orgId:" + orgId);
            //保存机构的配置信息
            insertColumnValueMapConfig.put("orgId", orgId);
            System.out.println("orgName:" + insertColumnValueMapConfig.get("orgName"));
            orgConfigService.insert(insertColumnValueMapConfig);
            //添加时，清除首页排行的缓存
            orgService.deleteRedisByName("topList__"+org.getParentId());
            if(org.getParentId()==0){
            	 orgService.deleteRedisByName("config_List_");
            }
        }
        return flag;
    }

    @Override
    public boolean checkOrgHasUser(String code) {
        return orgService.checkOrgHasUser(code);
    }

    @Override
    public void deleteOrgByIds(String ids) {
        orgService.deleteOrgByIds(ids);
    }

    @Override
    public Page<List<Map<String, Object>>> getOrgListPage(Page<List<Map<String, Object>>> page) {
        return orgService.getOrgListPage(page);
    }

    @Override
    public Map<String, Object> getOrgByName(String name) {
        return orgService.getOrgByName(name);
    }

    @Override
    public Adks_org getOrgById(Integer orgId) {
        return orgService.getOrgById(orgId);
    }

    @Override
    public List<Adks_org> getOrgsListAll2(Integer parentId) {
        List<Adks_org> list = orgService.getOrgsListAll2(parentId);
        return list;
    }
    @Override
    public List<Adks_org> getOrgsListAll(String orgCode) {
        List<Adks_org> list = orgService.getOrgsListAll(orgCode);
        Map<Long,List<Adks_org>> map=new HashMap<Long,List<Adks_org>>(); 
        return list;
    }
    @Override
    public Integer saveOrgConfig(Adks_org_config orgConfig) {
        Integer flag = 0;

        Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("orgName", orgConfig.getOrgName());
        insertColumnValueMap.put("orgUrl", orgConfig.getOrgUrl());
        insertColumnValueMap.put("orgLogoPath", orgConfig.getOrgLogoPath());
        insertColumnValueMap.put("orgDesc", orgConfig.getOrgDesc());
        insertColumnValueMap.put("orgBanner", orgConfig.getOrgBanner());
        insertColumnValueMap.put("contactUser", orgConfig.getContactUser());
        insertColumnValueMap.put("contactPhone", orgConfig.getContactPhone());
        insertColumnValueMap.put("contactQQ", orgConfig.getContactQQ());
        insertColumnValueMap.put("contactMail", orgConfig.getContactMail());
        insertColumnValueMap.put("copyRight", orgConfig.getCopyRight());

        //判断是保存还是更新
        Adks_org_config oc = orgConfigService.getOrgConfigById(orgConfig.getOrgId());
        if (oc == null) {//保存
            insertColumnValueMap.put("orgId", orgConfig.getOrgId());
            orgConfigService.insert(insertColumnValueMap);
            flag = 1;
        }
        else {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("orgId", orgConfig.getOrgId());
            flag = orgConfigService.update(insertColumnValueMap, updateWhereConditionMap);
            orgService.deleteRedisByName("config_id__"+orgConfig.getOrgId());
            Adks_org org=orgService.getOrgById(orgConfig.getOrgId());
            if(org.getParentId()==0){
            	orgService.deleteRedisByName("config_List_");
            }
        }
        return flag;
    }

    @Override
    public void deleteOrgCinfig(String ids) {
        orgConfigService.deleteOrgConfigByIds(ids);
    }

    @Override
    public Adks_org_config getOrgConfigById(Integer orgConfigId) {
        return orgConfigService.getOrgConfigById(orgConfigId);
    }

    @Override
    public Adks_org_config getOrgConfigByOrgId(Integer orgId) {
        return orgConfigService.getOrgConfigByOrgId(orgId);
    }

    public Adks_org getTopOrgAvgStudyTimeUserList(Map map) {
        return orgService.getTopOrgAvgStudyTimeUserList(map);
    }

	@Override
	public void updateOrgUserNum(String orgCode,Integer falg) {
		orgService.updateOrgUserNum(orgCode,falg);
	}

	/**
	 * 
	 * @Title getOrgConfig
	 * @Description:获取机构配置
	 * @author xrl
	 * @Date 2017年5月15日
	 * @return
	 */
	@Override
	public List<Adks_org_config> getOrgConfig() {
		return orgService.getOrgConfig();
	}

	@Override
	public void deleteOrgTopRedis(String parentIds) {
		String[] idStrings=parentIds.split(",");
		if(idStrings!=null && idStrings.length>0){
			String orgListRedis="";
			for(int i=0;i<idStrings.length;i++){
				if(idStrings[i]!=null && !"".equals(idStrings[i])){
					orgListRedis+="topList__"+idStrings[i]+",";
					if("0".equals(idStrings[i])){
						orgService.deleteRedisByName("config_List_");
					}
				}
    		}
			orgService.deleteRedisByName(orgListRedis);
		}
	}

	@Override
	public Page<List<Map<String, Object>>> getOrgStatisticsListJson(Page<List<Map<String, Object>>> page) {
		return orgService.getOrgStatisticsListJson(page);
	}
	@Override
	public List<Map<String, Object>> getOrgStudyList(String orgCode){
		return orgService.getOrgStudyList(orgCode);
	}
}
