package com.adks.dubbo.providers.admin.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.course.Adks_course_sort;
import com.adks.dubbo.api.interfaces.admin.course.CourseSortApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.course.CourseSortService;

public class CourseSortApiImpl implements CourseSortApi {

    @Autowired
    private CourseSortService courseSortService;

    @Override
    public Integer saveCourseSort(Adks_course_sort courseSort) {
        Integer flag = 0;
        //课程分类
        Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("courseSortName", courseSort.getCourseSortName());
        insertColumnValueMap.put("courseParentId", courseSort.getCourseParentId());
        insertColumnValueMap.put("courseParentName", courseSort.getCourseParentName());
        insertColumnValueMap.put("creatorId", courseSort.getCreatorId());
        insertColumnValueMap.put("creatorName", courseSort.getCreatorName());
        insertColumnValueMap.put("orgId", courseSort.getOrgId());
        insertColumnValueMap.put("orgCode", courseSort.getOrgCode());
        insertColumnValueMap.put("orgName", courseSort.getOrgName());
        insertColumnValueMap.put("courseSortImgPath", courseSort.getCourseSortImgpath());
        if (courseSort.getCourseSortId() != null) {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("courseSortId", courseSort.getCourseSortId());
            flag = courseSortService.update(insertColumnValueMap, updateWhereConditionMap);
        }
        else {
            insertColumnValueMap.put("courseNum", courseSort.getCourseNum());
            insertColumnValueMap.put("createtime", courseSort.getCreateTime());
            flag = courseSortService.insert(insertColumnValueMap);
            if (courseSort.getCourseParentId() == 0) {
                insertColumnValueMap.put("courseSortCode", "0A" + flag + "A");
            }
            else {
                Adks_course_sort parentCourseSort = courseSortService.getCourseSortById(courseSort.getCourseParentId());
                if (parentCourseSort != null && parentCourseSort.getCourseSortCode() != null) {
                    insertColumnValueMap.put("courseSortCode", parentCourseSort.getCourseSortCode() + flag + "A");
                }
            }

            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("courseSortId", flag);
            flag = courseSortService.update(insertColumnValueMap, updateWhereConditionMap);
            courseSortService.deleteCourseSortRedis();
        }
        return flag;
    }

    public Adks_course_sort getCourseSortByName(String courseSortName) {
        return courseSortService.getCourseSortByName(courseSortName);
    }

    @Override
    public List<Adks_course_sort> getCourseSortByParent(Integer parentId) {
        return courseSortService.getCourseSortByParent(parentId);
    }

    @Override
    public Page<List<Map<String, Object>>> getCourseSortListPage(Page<List<Map<String, Object>>> page) {
        return courseSortService.getCourseSortListPage(page);
    }

    @Override
    public void deleteCourseSort(String courseSortCode) {
        courseSortService.deleteCourseSort(courseSortCode);
    }

    @Override
    public Adks_course_sort getCourseSortById(Integer courseSortId) {
        return courseSortService.getCourseSortById(courseSortId);
    }

    public void updateNameUnifed(Integer courseSortId, String courseSortName) {
        courseSortService.updateNameUnifed(courseSortId, courseSortName);
        //修改了名称清除redis缓存
        courseSortService.deleteCourseSortRedis();
    }

    public List<Adks_course_sort> getCourseSortsListAll(Map map) {
        List<Adks_course_sort> list = courseSortService.getCourseSortsListAll(map);
        return list;
    }

	@Override
	public List<Adks_course_sort> getCourseSortParentIsZero() {
		return courseSortService.getCourseSortParentIsZero();
	}
}
