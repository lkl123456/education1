package com.adks.dubbo.dao.app.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.commons.Page;

@Repository
public class UserCollectionAppDao extends BaseDao {

	@Override
	protected String getTableName() {
		return "adks_usercollection";
	}

	/**
	 * 删除收藏
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public int deleteCollection(int id) {
		String sql = "delete from adks_usercollection where userConId =" + id;
		return mysqlClient.update(sql, new Object[] {});
	}

	/**
	 * 收藏列表
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<List<Map<String, Object>>> getCollectionPage(Page<List<Map<String, Object>>> page) {
		Integer offset = page.getStartLimit(); // limit
		String sqlbuffer = "select "
				+ "uc.userConId,uc.courseId,uc.courseCode,uc.courseName,uc.userId,uc.authorId,uc.authorName,uc.courseType as cwType,uc.courseDuration,uc.courseSortName,uc.courseTimeLong as durationTime,c.coursePic as pathImg,c.createtime as createDate "
				+ "from adks_usercollection uc,adks_course c where 1=1 and c.courseId=uc.courseId ";
		StringBuffer countsql = new StringBuffer(
				"select count(1) from adks_usercollection uc,adks_course c where 1=1 and c.courseId=uc.courseId ");
		Map map = page.getMap();
		if (map != null && map.size() > 0) {
			// 添加查询条件
			if (map.get("userId") != null) {
				sqlbuffer += " and uc.userId =" + map.get("userId");
				countsql.append(" and uc.userId =" + map.get("userId"));
			}
		}
		sqlbuffer += " order by uc.createDate desc";
		// 分页
		sqlbuffer += " limit " + offset + ", " + page.getPageSize();
		List<Map<String, Object>> list = mysqlClient.queryForList(sqlbuffer, new Object[0]);
		Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
		page.setTotal(totalcount);
		page.setRows(list);
		return page;
	}

	/**
	 * 删除收藏
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Map<String, Object> getUCId(int userId, int courseId) {
		String sql = "select *  from adks_usercollection where userId =" + userId + " and courseId = " + courseId;
		return mysqlClient.queryForMap(sql, new Object[0]);
	}
}