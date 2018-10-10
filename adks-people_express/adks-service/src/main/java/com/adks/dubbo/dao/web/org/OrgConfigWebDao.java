package com.adks.dubbo.dao.web.org;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.menu.Adks_menu;
import com.adks.dubbo.api.data.menu.Adks_menulink;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.data.org.Adks_org_config;
import com.adks.dubbo.commons.Page;

@Component
public class OrgConfigWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_org_config";
	}
	
	public Adks_org_config getOrgConfigByOrgId(Integer orgId){
		String sql = "select orgConfigId,orgId ,orgName ,orgUrl,orgLogoPath,orgDesc,orgBanner,contactUser,contactPhone,contactQQ,contactMail,copyRight from adks_org_config where orgId="+orgId;
		List<Adks_org_config> reslist = mysqlClient.query(sql, new Object[0],new RowMapper<Adks_org_config>() {
			@Override
			public Adks_org_config mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_org_config org=new Adks_org_config();
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
			}});
		if(reslist==null || reslist.size()<=0)
			return null;
		return reslist.get(0);
	}
}
