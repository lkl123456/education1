package com.adks.dubbo.dao.admin.org;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.org.Adks_org_config;

@Component
public class OrgConfigDao extends BaseDao {

    @Override
    protected String getTableName() {
        return "adks_org_config";
    }

    public Adks_org_config getOrgConfigById(Integer orgConfigId) {
        String sql = "select orgConfigId,orgId ,orgName ,orgUrl,orgLogoPath,orgDesc,orgBanner,contactUser,contactPhone,contactQQ,contactMail,copyRight from adks_org_config where orgConfigId="
                + orgConfigId;
        List<Adks_org_config> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_org_config>() {
            @Override
            public Adks_org_config mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_org_config org = new Adks_org_config();
                org.setOrgConfigId(rs.getInt("orgConfigId"));
                org.setOrgId(rs.getInt("orgId"));
                org.setOrgName(rs.getString("orgName"));
                org.setOrgUrl(rs.getString("orgUrl"));
                org.setOrgLogoPath(rs.getString("orgLogoPath"));
                org.setOrgDesc(rs.getString("orgDesc"));
                org.setOrgBanner(rs.getString("orgBanner"));
                org.setContactUser(rs.getString("contactUser"));
                org.setContactPhone(rs.getString("contactPhone"));
                org.setContactQQ(rs.getString("contactQQ"));
                org.setContactMail(rs.getString("contactMail"));
                org.setCopyRight(rs.getString("copyRight"));
                return org;
            }
        });
        if (reslist == null || reslist.size() <= 0)
            return null;
        return reslist.get(0);
    }

    public void deleteOrgConfigByIds(String ids) {
        String sql = "delete from adks_org_config where orgId in ( " + ids + " )";
        mysqlClient.update(sql, new Object[] {});
    }

    public Adks_org_config getOrgConfigByOrgId(Integer orgId) {
        String sql = "select orgConfigId,orgId ,orgName ,orgUrl,orgLogoPath,orgDesc,orgBanner,contactUser,contactPhone,contactQQ,contactMail,copyRight from adks_org_config where orgId=" + orgId;
        List<Adks_org_config> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_org_config>() {
            @Override
            public Adks_org_config mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_org_config org = new Adks_org_config();
                org.setOrgConfigId(rs.getInt("orgConfigId"));
                org.setOrgId(rs.getInt("orgId"));
                org.setOrgName(rs.getString("orgName"));
                org.setOrgUrl(rs.getString("orgUrl"));
                org.setOrgLogoPath(rs.getString("orgLogoPath"));
                org.setOrgDesc(rs.getString("orgDesc"));
                org.setOrgBanner(rs.getString("orgBanner"));
                org.setContactUser(rs.getString("contactUser"));
                org.setContactPhone(rs.getString("contactPhone"));
                org.setContactQQ(rs.getString("contactQQ"));
                org.setContactMail(rs.getString("contactMail"));
                org.setCopyRight(rs.getString("copyRight"));
                return org;
            }
        });
        if (reslist == null || reslist.size() <= 0)
            return null;
        return reslist.get(0);
    }
    
    /**
	 * 
	 * @Title getOrgConfig
	 * @Description
	 * @author xrl
	 * @Date 2017年5月15日
	 * @return
	 */
	public List<Adks_org_config> getOrgConfig(){
		String sql = "select * from adks_org_config oc,adks_org o where oc.orgId=o.orgId and o.parentId=0 ";
		List<Adks_org_config> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org_config>() {
			@Override
			public Adks_org_config mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org_config orgConfig=new Adks_org_config();
				orgConfig.setOrgName(rs.getString("orgName"));
				orgConfig.setOrgUrl(rs.getString("orgUrl"));
				orgConfig.setOrgLogoPath(rs.getString("orgLogoPath"));
				return orgConfig;
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist;
	}
}
