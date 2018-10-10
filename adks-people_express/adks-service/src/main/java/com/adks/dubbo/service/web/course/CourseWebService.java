package com.adks.dubbo.service.web.course;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.web.course.CourseWebDao;
import com.adks.dubbo.dao.web.search.SolrDeleteDao;
import com.adks.dubbo.util.CourseRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class CourseWebService extends BaseService<CourseWebDao> {

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private SolrDeleteDao solrDeleteDao;

    @Autowired
    private CourseWebDao courseDao;

    @Override
    protected CourseWebDao getDao() {
        return courseDao;
    }

    public List<Adks_course> getCourseListByOrgCode(String orgCode) {
        return courseDao.getCourseListByOrgCode(orgCode);
    }

    public Adks_course getCourseById(Integer courseId) {
        return courseDao.getCourseById(courseId);
    }

    public Adks_course getCourseByName(String courseName) {
        return courseDao.getCourseByName(courseName);
    }

    public void updateCourseSortCourseNum(Integer courseSortId, Integer flag) {
        courseDao.updateCourseSortCourseNum(courseSortId, flag);
    }

    //得到最新课程（首页）
    public List<Adks_course> getTopNewCourseList(Map map) {
        List<Adks_course> list = null;
        if(map!=null && map.get("isRecommend")!=null && !"".equals(map.get("isRecommend"))){
        	//表示推荐课程
        }else{
        	//表示是最新课程
        	if(map.get("orgCode")!=null && !"".equals(map.get("orgCode"))){
        		String orgCode=MapUtils.getString(map, "orgCode");
        		String result=CourseRedisUtil.getCourse(CourseRedisUtil.adks_course_new, orgCode, false);
        		if("".equals(result) || "Nodata".equals(result)){
        			list = courseDao.getTopNewCourseList(map);
        			CourseRedisUtil.addCourse(CourseRedisUtil.adks_course_new, orgCode, list);
        		}else{
        			list=JSONObject.parseArray(result, Adks_course.class);
        		}
        	}
        }
        return list;
    }
    //竟学排行课程
    public List<Adks_course> getTopHotCourseList(Map map) {
        List<Adks_course> list = null;
        if(map.get("orgCode")!=null && !"".equals(map.get("orgCode"))){
    		String orgCode=MapUtils.getString(map, "orgCode");
    		String result=CourseRedisUtil.getCourse(CourseRedisUtil.adks_course_rank, orgCode, false);
    		if("".equals(result) || "Nodata".equals(result)){
    			list = courseDao.getTopHotCourseList(map);
    			CourseRedisUtil.addCourse(CourseRedisUtil.adks_course_rank, orgCode, list);
    		}else{
    			list=JSONObject.parseArray(result, Adks_course.class);
    		}
    	}
        return list;
    }

    public List<Adks_course> getCourseListByIds(Map map) {
        return courseDao.getCourseListByIds(map);
    }

    public Page<List<Adks_course>> getCoursesListWeb(Page<List<Adks_course>> page) {
        return courseDao.getCoursesListWeb(page);
    }
    //课程详情的相关课程
    public List<Adks_course> getCourseListByLikedName(Map map) {
    	List<Adks_course> list = null;
        if(map.get("orgCode")!=null && !"".equals(map.get("orgCode"))){
        	String courseName=MapUtils.getString(map,"courseName");
    		String orgCode=MapUtils.getString(map, "orgCode");
    		String result=CourseRedisUtil.getCourse(CourseRedisUtil.adks_course_about+courseName+"_", orgCode, false);
    		if("".equals(result) || "Nodata".equals(result)){
    			list = courseDao.getCourseListByLikedName(map);
    			CourseRedisUtil.addCourseTimeLong(CourseRedisUtil.adks_course_about+courseName+"_",orgCode , list);
    		}else{
    			list=JSONObject.parseArray(result, Adks_course.class);
    		}
    	}
        return list;
    }
    
    //得到讲师的课程
    public List<Adks_course> getCourseListByAuthorAndLimitNum(Integer author,Map map) {
    	List<Adks_course> list = null;
        if(map.get("orgCode")!=null && !"".equals(map.get("orgCode"))){
    		String orgCode=MapUtils.getString(map, "orgCode");
    		String result=CourseRedisUtil.getCourse(CourseRedisUtil.adks_course_author_course+author+"_", orgCode+"", false);
    		if("".equals(result) || "Nodata".equals(result)){
    			list = courseDao.getCourseListByAuthorAndLimitNum(author,map);
    			CourseRedisUtil.addCourseTimeLong(CourseRedisUtil.adks_course_author_course+author+"_", orgCode+"", list);
    		}else{
    			list=JSONObject.parseArray(result, Adks_course.class);
    		}
    	}
        return list;
    }

    public void initData() {
        List<Adks_course> queryAll = courseDao.queryAll();
        try {
            for (Adks_course course : queryAll) {
                addOneToSolr(course);
            }
            solrServer.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCourseToSolr(Adks_course course) {
        try {
            addOneToSolr(course);
            solrServer.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOneToSolr(Adks_course course) throws Exception {
        SolrInputDocument document = new SolrInputDocument();
        //id 字段是必不可少的，在往solr中插入数据的时候id是唯一默认主键
        document.setField("id", "courseId_" + course.getCourseId());
        document.setField("courseId", course.getCourseId());
        document.setField("orgId", course.getOrgId());
        document.setField("orgName", course.getOrgName());
        document.setField("courseName", course.getCourseName());
        document.setField("coursePic", course.getCoursePic());
        document.setField("courseCode", course.getCourseCode());
        document.setField("courseType", course.getCourseType());
        document.setField("courseSortId", course.getCourseSortId());
        document.setField("courseSortName", course.getCourseSortName());
        document.setField("courseSortCode", course.getCourseSortCode());
        document.setField("creatorId", course.getCreatorId());
        document.setField("creatorName", course.getCreatorName());
        document.setField("createtime", course.getCreateTime());
        document.setField("courseDes", course.getCourseDes());
        document.setField("authorId", course.getAuthorId());
        document.setField("authorName", course.getAuthorName());
        document.setField("longTime", course.getCreateTime().getTime());
        document.setField("objectType", 1);
        //写入索引库
        solrServer.add(document);
    }

    public Integer getCourseUserLocation(Integer courseId, Integer gradeId, Integer userId) {
        return courseDao.getCourseUserLocation(courseId, gradeId, userId);
    }

    public Integer getCourseSuperUserLocation(Integer courseId, Integer userId) {
        return courseDao.getCourseSuperUserLocation(courseId, userId);
    }

    public String getVideoServer(Integer courseId) {
        return courseDao.getVideoServer(courseId);
    }

    public void updateCourseClickNum(Integer courseId) {
        courseDao.updateCourseClickNum(courseId);
    }

    public String getCourseVideoRP(Integer courseId) {
        return courseDao.getCourseVideoRP(courseId);
    }

    public void deleteCourse(Integer courseId) {
        solrDeleteDao.deleteById("courseId", courseId);
    }

    public void updateCourseChooseNum(Integer courseId, String type) {
        courseDao.updateCourseChooseNum(courseId, type);
    }
}
