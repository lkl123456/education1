package com.adks.dubbo.dao.web.news;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.common.Adks_advertise;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.commons.Page;

@Component
public class AdvertiseWebDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_advertise";
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getAdvertiseListPage(Page<List<Map<String, Object>>> page) {
		Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); // limit
																			// 起始位置
		StringBuffer sqlbuffer = new StringBuffer("select * from adks_advertise where 1=1 ");
		StringBuffer countsql = new StringBuffer("select count(1) from adks_advertise where 1=1 ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件 。。
			if (map.get("orgId") != null) {
				sqlbuffer.append(" and orgId like '%" + map.get("orgId") + "%'");
				countsql.append(" and orgId like '%" + map.get("orgId") + "%'");
			}
			if (map.get("orgCode") != null) {
				sqlbuffer.append(" and orgCode like '%" + map.get("orgCode") + "%'");
				countsql.append(" and orgCode like '%" + map.get("orgCode") + "%'");
			}
		}

		// 分页
		sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		String sql = sqlbuffer.toString();
		List<Map<String, Object>> advertiseList = mysqlClient.queryForList(sql, new Object[0]);

		// 查询总记录
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(advertiseList);
		return page;
	}

	public void deleteAdvertiseByIds(String adIds) {
		String sql = "delete from adks_advertise where adId  in (" + adIds + ")";
		mysqlClient.update(sql, new Object[] {});
	}

	public Map<String, Object> getAdvertiseInfoById(Integer adId) {
		String sql = " select * from adks_advertise where 1=1  and adId=" + adId;
		return mysqlClient.queryForMap(sql, new Object[] {});
	}

	public List<Adks_advertise> getAdvertiseByCon(Map map) {
		String sql = "select adId,orgId,orgName,adImgPath,adLocation,adHref,status,creatorId,creatorName,createTime  from adks_advertise where 1=1 "
				+ " and status=1";
		if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
			sql += " and orgId=" + map.get("orgId");
		}
		List<Adks_advertise> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_advertise>() {
			@Override
			public Adks_advertise mapRow(ResultSet rs, int rowNum) throws SQLException {
				Adks_advertise ad = new Adks_advertise();
				ad.setAdId(rs.getInt("adId"));
				ad.setOrgId(rs.getInt("orgId"));
				ad.setOrgName(rs.getString("orgName"));
				ad.setAdImgPath(rs.getString("adImgPath"));
				ad.setAdLocation(rs.getInt("adLocation"));
				ad.setAdHref(rs.getString("adHref"));
				ad.setStatus(rs.getInt("status"));
				ad.setCreatorId(rs.getInt("creatorId"));
				ad.setCreatorName(rs.getString("creatorName"));
				ad.setCreateTime(rs.getDate("createTime"));
				return ad;
			}
		});
		return reslist;
	}
	
	/**
	 * 
	 * @Title getAdvertiseInfoByOrgIdAndLocation
	 * @Description:根据机构Id和位置获取广告
	 * @author xrl
	 * @Date 2017年6月14日
	 * @param map
	 * @return
	 */
	public Adks_advertise getAdvertiseInfoByOrgIdAndLocation(Map<String, Object> map){
		String sql = "select * from adks_advertise "
				+ "where status=1 and orgId=" +map.get("orgId")+" and adLocation="+map.get("adLocation")
				+" order by createTime desc";
        List<Adks_advertise> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_advertise>() {
            @Override
            public Adks_advertise mapRow(ResultSet rs, int rowNum) throws SQLException {
            	Adks_advertise advertise=new Adks_advertise();
            	advertise.setAdId(rs.getInt("adId"));
            	advertise.setAdImgPath(rs.getString("adImgPath"));
            	advertise.setAdLocation(rs.getInt("adLocation"));
            	advertise.setAdHref(rs.getString("adHref"));
            	advertise.setStatus(rs.getInt("status"));
            	advertise.setOrgId(rs.getInt("orgId"));
            	advertise.setOrgCode(rs.getString("orgCode"));
            	advertise.setOrgName(rs.getString("orgName"));
                return advertise;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist.get(0);
	}
}
