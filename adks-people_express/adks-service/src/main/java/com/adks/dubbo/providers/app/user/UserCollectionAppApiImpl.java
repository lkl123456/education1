package com.adks.dubbo.providers.app.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.user.Adks_usercollection;
import com.adks.dubbo.api.interfaces.app.user.UserCollectionAppApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.app.user.UserCollectionAppService;

public class UserCollectionAppApiImpl implements UserCollectionAppApi {

	@Autowired
	private UserCollectionAppService collectionService;

	@Override
	public Page<List<Map<String, Object>>> getCollectionPage(Page<List<Map<String, Object>>> page) {
		return collectionService.getCollectionPage(page);
	}

	@Override
	public int saveUserCollection(Adks_usercollection usercollection) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		try {
			insertColumnValueMap.put("courseId", usercollection.getCourseId());
			insertColumnValueMap.put("courseName", usercollection.getCourseName());
			insertColumnValueMap.put("courseCode", usercollection.getCourseCode());
			insertColumnValueMap.put("userId", usercollection.getUserId());
			insertColumnValueMap.put("authorId", usercollection.getAuthorId());
			insertColumnValueMap.put("authorName", usercollection.getAuthorName());
			insertColumnValueMap.put("createDate", usercollection.getCreateDate());
			insertColumnValueMap.put("publishDate", usercollection.getPublishDate());
			insertColumnValueMap.put("courseDuration", usercollection.getCourseDuration());
			insertColumnValueMap.put("courseType", usercollection.getCourseType());
			insertColumnValueMap.put("courseImg", usercollection.getCourseImg());
			insertColumnValueMap.put("courseSortName", usercollection.getCourseSortName());
			insertColumnValueMap.put("courseTimeLong", usercollection.getCourseTimeLong());

			if (usercollection.getUserConId() != null) {
				Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
				updateWhereConditionMap.put("userConId", usercollection.getUserConId());
				flag = collectionService.update(insertColumnValueMap, updateWhereConditionMap);
			} else {
				flag = collectionService.insert(insertColumnValueMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public int deleteCollection(int id) {
		return collectionService.deleteCollection(id);
	}

	@Override
	public Map<String, Object> getByUCId(int userId, int courseId) {
		return collectionService.getByUCId(userId, courseId);
	}
}
