package com.adks.dubbo.dao.web.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.commons.Page;

@Repository
public class CourseWebDao extends BaseDao {

    @Override
    protected String getTableName() {
        return "adks_course";
    }

    public List<Adks_course> queryAll() {
        List<Adks_course> list = new ArrayList<>();
        String sql = "SELECT distinct courseId,courseName,courseCode,courseType,orgId,orgName,authorId,authorName,courseSortId,courseSortName,courseSortCode,creatorId,creatorName,coursePic,createtime,courseDes FROM adks_course where isAudit=1 and courseStatus=1";
        List<Map<String, Object>> queryList = mysqlClient.queryList(sql);
        String createdate = null;
        try {
            Adks_course course = null;
            for (Map<String, Object> map : queryList) {
                course = new Adks_course();
                if (null != map.get("createtime")) {
                    createdate = (String) map.get("createtime").toString();
                }
                map.remove("createtime");
                course.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdate));
                BeanUtils.populate(course, map);
                list.add(course);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Page<List<Adks_course>> getCoursesListWeb(Page<List<Adks_course>> page) {
        Integer offset = null; //limit 起始位置
        if (page.getCurrentPage() <= 1) {
            offset = 0;
            page.setCurrentPage(1);
        }
        else {
            offset = (page.getCurrentPage() - 1) * page.getPageSize();
        }
        StringBuffer sqlbuffer = new StringBuffer(
                "select distinct courseId, courseName,courseCode, courseType,coursePic,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
                        + "courseTimeLong,orgId,orgName" + ",creatorId,creatorName,createtime"
                        + " from adks_course where 1=1 and isAudit=1 and courseStatus=1 ");
        StringBuffer countsql = new StringBuffer(
                "select count(1) from adks_course where 1=1 and isAudit=1  and courseStatus=1 ");
        Map map = page.getMap();
        if (map != null && map.size() > 0) {
            //添加查询条件 。。
            if (map.get("courseSortId") != null && !"".equals(map.get("courseSortId"))) {
                sqlbuffer.append(" and courseSortId=" + map.get("courseSortId"));
                countsql.append(" and courseSortId=" + map.get("courseSortId"));
            }
            if (map.get("courseType") != null && !"".equals(map.get("courseType"))) {
                sqlbuffer.append(" and courseType=" + map.get("courseType"));
                countsql.append(" and courseType=" + map.get("courseType"));
            }
            if (map.get("courseId") != null && !"".equals(map.get("courseId"))) {
                sqlbuffer.append(" and courseId=" + map.get("courseId"));
                countsql.append(" and courseId=" + map.get("courseId"));
            }
            if (map.get("searchKeyValue") != null && !"".equals(map.get("searchKeyValue"))) {
                sqlbuffer.append(" and courseName like '%" + map.get("searchKeyValue") + "%'");
                countsql.append(" and courseName like '%" + map.get("searchKeyValue") + "%'");
            }
            if (map.get("courseSortCode") != null && !"".equals(map.get("courseSortCode"))) {
                sqlbuffer.append(" and courseSortCode like '%" + map.get("courseSortCode") + "%'");
                countsql.append(" and courseSortCode like '%" + map.get("courseSortCode") + "%'");
            }
            if (map.get("courseSortIds") != null && !"".equals(map.get("courseSortIds"))) {
                sqlbuffer.append(" and courseSortId in (" + map.get("courseSortIds") + ") ");
                countsql.append(" and courseSortId in (" + map.get("courseSortIds") + ") ");
            }
            if (map.get("orgCode") != null && !"".equals(map.get("orgCode"))) {
                sqlbuffer.append(" and ((orgCode like '%" + map.get("orgCode") + "%' and courseBelong <> 2 ) or courseBelong=1 )");
                countsql.append(" and ((orgCode like '%" + map.get("orgCode") + "%' and courseBelong <> 2 ) or courseBelong=1 )");
            }
            if (map.get("authorId") != null && !"".equals(map.get("authorId"))) {
                sqlbuffer.append(" and authorId=" + map.get("authorId"));
                countsql.append(" and authorId=" + map.get("authorId"));
            }
        }
        
        sqlbuffer.append(" order by courseId desc");

        //分页
        sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        String sql = sqlbuffer.toString();

        List<Adks_course> coursesList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course>() {
            @Override
            public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course course = new Adks_course();
                course.setCourseId(rs.getInt("courseId"));
                course.setCourseName(rs.getString("courseName"));
                course.setCourseCode(rs.getString("courseCode"));
                course.setCourseSortId(rs.getInt("courseSortId"));
                course.setCourseSortName(rs.getString("courseSortName"));
                course.setCourseSortCode(rs.getString("courseSortCode"));
                course.setCourseType(rs.getInt("courseType"));
                course.setCoursePic(rs.getString("coursePic"));
                course.setAuthorId(rs.getInt("authorId"));
                course.setAuthorName(rs.getString("authorName"));
                course.setCourseDuration(rs.getInt("courseDuration"));
                course.setCourseTimeLong(rs.getString("courseTimeLong"));
                course.setOrgId(rs.getInt("orgId"));
                course.setOrgName(rs.getString("orgName"));
                course.setCreatorId(rs.getInt("creatorId"));
                course.setCreatorName(rs.getString("creatorName"));
                course.setCreateTime(rs.getDate("createtime"));
                return course;
            }
        });

        //查询总记录
        Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
        page.setTotal(totalcount);
        page.setTotalRecords(totalcount);
        if (totalcount % page.getPageSize() == 0) {
            page.setTotalPage(totalcount / page.getPageSize());
            page.setTotalPages(totalcount / page.getPageSize());
        }
        else {
            page.setTotalPage(totalcount / page.getPageSize() + 1);
            page.setTotalPages(totalcount / page.getPageSize() + 1);
        }

        page.setRows(coursesList);
        return page;
    }

    public List<Adks_course> getCourseListByOrgCode(String orgCode) {
        String sql = "select courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
                + "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
                + ",courseStream,creatorId,creatorName,createtime,courseStudiedLong"
                + " from adks_course where orgCode like '" + orgCode + "%' and isAudit=1 and courseStatus=1 ";
        List<Adks_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course>() {
            @Override
            public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course courseSort = new Adks_course();
                courseSort.setCourseId(rs.getInt("courseId"));
                courseSort.setCourseName(rs.getString("courseName"));
                courseSort.setCourseCode(rs.getString("courseCode"));
                courseSort.setCourseSortId(rs.getInt("courseSortId"));
                courseSort.setCourseSortName(rs.getString("courseSortName"));
                courseSort.setCourseSortCode(rs.getString("courseSortCode"));
                courseSort.setCourseType(rs.getInt("courseType"));
                courseSort.setCoursePic(rs.getString("coursePic"));
                courseSort.setCourseDes(rs.getString("courseDes"));
                courseSort.setAuthorId(rs.getInt("authorId"));
                courseSort.setAuthorName(rs.getString("authorName"));
                courseSort.setCourseDuration(rs.getInt("courseDuration"));
                courseSort.setCourseTimeLong(rs.getString("courseTimeLong"));
                courseSort.setCourseStatus(rs.getInt("courseStatus"));
                courseSort.setCourseCollectNum(rs.getInt("courseCollectNum"));
                courseSort.setCourseClickNum(rs.getInt("courseClickNum"));
                courseSort.setIsAudit(rs.getInt("isAudit"));
                courseSort.setCourseBelong(rs.getInt("courseBelong"));
                courseSort.setIsRecommend(rs.getInt("isRecommend"));
                courseSort.setOrgId(rs.getInt("orgId"));
                courseSort.setOrgName(rs.getString("orgName"));
                courseSort.setCourseStream(rs.getString("courseStream"));
                courseSort.setCreatorId(rs.getInt("creatorId"));
                courseSort.setCreatorName(rs.getString("creatorName"));
                courseSort.setCreateTime(rs.getDate("createtime"));
                return courseSort;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist;
    }

    //更新课程分类的课程量
    public void updateCourseSortCourseNum(Integer courseSortId, Integer flag) {
        if (flag == 1) {
            String sql = "update Adks_course_sort set courseNum=courseNum+1 where courseSortId=" + courseSortId;
            mysqlClient.update(sql, new Object[] {});
        }
        else if (flag == 2) {
            String sql = "update Adks_course_sort set courseNum=courseNum-1 where courseSortId=" + courseSortId;
            mysqlClient.update(sql, new Object[] {});
        }
    }

    public Adks_course getCourseById(Integer courseId) {
        String sql = "select courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
                + "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
                + ",courseStream,creatorId,creatorName,createtime,courseStudiedLong"
                + " from adks_course where courseId =" + courseId;
        List<Adks_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course>() {
            @Override
            public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course courseSort = new Adks_course();
                courseSort.setCourseId(rs.getInt("courseId"));
                courseSort.setCourseName(rs.getString("courseName"));
                courseSort.setCourseCode(rs.getString("courseCode"));
                courseSort.setCourseSortId(rs.getInt("courseSortId"));
                courseSort.setCourseSortName(rs.getString("courseSortName"));
                courseSort.setCourseSortCode(rs.getString("courseSortCode"));
                courseSort.setCourseType(rs.getInt("courseType"));
                courseSort.setCoursePic(rs.getString("coursePic"));
                courseSort.setCourseDes(rs.getString("courseDes"));
                courseSort.setAuthorId(rs.getInt("authorId"));
                courseSort.setAuthorName(rs.getString("authorName"));
                courseSort.setCourseDuration(rs.getInt("courseDuration"));
                courseSort.setCourseTimeLong(rs.getString("courseTimeLong"));
                courseSort.setCourseStatus(rs.getInt("courseStatus"));
                courseSort.setCourseCollectNum(rs.getInt("courseCollectNum"));
                courseSort.setCourseClickNum(rs.getInt("courseClickNum"));
                courseSort.setIsAudit(rs.getInt("isAudit"));
                courseSort.setCourseBelong(rs.getInt("courseBelong"));
                courseSort.setIsRecommend(rs.getInt("isRecommend"));
                courseSort.setOrgId(rs.getInt("orgId"));
                courseSort.setOrgName(rs.getString("orgName"));
                courseSort.setCourseStream(rs.getString("courseStream"));
                courseSort.setCreatorId(rs.getInt("creatorId"));
                courseSort.setCreatorName(rs.getString("creatorName"));
                courseSort.setCreateTime(rs.getDate("createtime"));
                return courseSort;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist.get(0);
    }

    public Adks_course getCourseByName(String courseName) {
        String sql = "select courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
                + "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
                + ",courseStream,creatorId,creatorName,createtime,courseStudiedLong"
                + " from adks_course where courseName ='" + courseName + "'";
        List<Adks_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course>() {
            @Override
            public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course courseSort = new Adks_course();
                courseSort.setCourseId(rs.getInt("courseId"));
                courseSort.setCourseName(rs.getString("courseName"));
                courseSort.setCourseCode(rs.getString("courseCode"));
                courseSort.setCourseSortId(rs.getInt("courseSortId"));
                courseSort.setCourseSortName(rs.getString("courseSortName"));
                courseSort.setCourseSortCode(rs.getString("courseSortCode"));
                courseSort.setCourseType(rs.getInt("courseType"));
                courseSort.setCoursePic(rs.getString("coursePic"));
                courseSort.setCourseDes(rs.getString("courseDes"));
                courseSort.setAuthorId(rs.getInt("authorId"));
                courseSort.setAuthorName(rs.getString("authorName"));
                courseSort.setCourseDuration(rs.getInt("courseDuration"));
                courseSort.setCourseTimeLong(rs.getString("courseTimeLong"));
                courseSort.setCourseStatus(rs.getInt("courseStatus"));
                courseSort.setCourseCollectNum(rs.getInt("courseCollectNum"));
                courseSort.setCourseClickNum(rs.getInt("courseClickNum"));
                courseSort.setIsAudit(rs.getInt("isAudit"));
                courseSort.setCourseBelong(rs.getInt("courseBelong"));
                courseSort.setIsRecommend(rs.getInt("isRecommend"));
                courseSort.setOrgId(rs.getInt("orgId"));
                courseSort.setOrgName(rs.getString("orgName"));
                courseSort.setCourseStream(rs.getString("courseStream"));
                courseSort.setCreatorId(rs.getInt("creatorId"));
                courseSort.setCreatorName(rs.getString("creatorName"));
                courseSort.setCreateTime(rs.getDate("createtime"));
                return courseSort;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist.get(0);
    }

    //得到首页的课程分类
    public List<Adks_course> getTopNewCourseList(Map map) {
        String sql = "select distinct courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
                + "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
                + ",courseStream,creatorId,creatorName,createtime,courseStudiedLong" + " from adks_course where 1=1 ";
        sql += " and isAudit=1 and courseStatus=1 ";
        if (map.get("orgCode") != null && !"".equals(map.get("orgCode"))) {
            sql += " and ((orgCode like '%" + map.get("orgCode") + "%' and courseBelong <> 2 ) or courseBelong=1 )";
        }
        if (map.get("isRecommend") != null && !"".equals(map.get("isRecommend"))) {
            sql += " and isRecommend=" + map.get("isRecommend");
        }
        if (map.get("num") != null && !"".equals(map.get("num"))) {
            sql += " order by createtime desc limit 0," + map.get("num");
        }

        List<Adks_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course>() {
            @Override
            public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course courseSort = new Adks_course();
                courseSort.setCourseId(rs.getInt("courseId"));
                courseSort.setCourseName(rs.getString("courseName"));
                courseSort.setCourseCode(rs.getString("courseCode"));
                courseSort.setCourseSortId(rs.getInt("courseSortId"));
                courseSort.setCourseSortName(rs.getString("courseSortName"));
                courseSort.setCourseSortCode(rs.getString("courseSortCode"));
                courseSort.setCourseType(rs.getInt("courseType"));
                courseSort.setCoursePic(rs.getString("coursePic"));
                courseSort.setCourseDes(rs.getString("courseDes"));
                courseSort.setAuthorId(rs.getInt("authorId"));
                courseSort.setAuthorName(rs.getString("authorName"));
                courseSort.setCourseDuration(rs.getInt("courseDuration"));
                courseSort.setCourseTimeLong(rs.getString("courseTimeLong"));
                courseSort.setCourseStatus(rs.getInt("courseStatus"));
                courseSort.setCourseCollectNum(rs.getInt("courseCollectNum"));
                courseSort.setCourseClickNum(rs.getInt("courseClickNum"));
                courseSort.setIsAudit(rs.getInt("isAudit"));
                courseSort.setCourseBelong(rs.getInt("courseBelong"));
                courseSort.setIsRecommend(rs.getInt("isRecommend"));
                courseSort.setOrgId(rs.getInt("orgId"));
                courseSort.setOrgName(rs.getString("orgName"));
                courseSort.setCourseStream(rs.getString("courseStream"));
                courseSort.setCreatorId(rs.getInt("creatorId"));
                courseSort.setCreatorName(rs.getString("creatorName"));
                courseSort.setCreateTime(rs.getDate("createtime"));
                return courseSort;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist;
    }

    public List<Adks_course> getTopHotCourseList(Map map) {
        String sql = "select distinct courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
                + "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
                + ",courseStream,creatorId,creatorName,createtime,courseStudiedLong" + " from adks_course where 1=1 ";
        sql += " and isAudit=1  and courseStatus=1 ";
        if (map.get("orgCode") != null && !"".equals(map.get("orgCode"))) {
        	sql += " and ((orgCode like '%" + map.get("orgCode") + "%' and courseBelong <> 2 ) or courseBelong=1 ) ";
        }
        if (map.get("isRecommend") != null && !"".equals(map.get("isRecommend"))) {
            sql += " and isRecommend=" + map.get("isRecommend");
        }
        if (map.get("num") != null && !"".equals(map.get("num"))) {
            sql += " order by courseClickNum desc limit 0," + map.get("num");
        }

        List<Adks_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course>() {
            @Override
            public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course courseSort = new Adks_course();
                courseSort.setCourseId(rs.getInt("courseId"));
                courseSort.setCourseName(rs.getString("courseName"));
                courseSort.setCourseCode(rs.getString("courseCode"));
                courseSort.setCourseSortId(rs.getInt("courseSortId"));
                courseSort.setCourseSortName(rs.getString("courseSortName"));
                courseSort.setCourseSortCode(rs.getString("courseSortCode"));
                courseSort.setCourseType(rs.getInt("courseType"));
                courseSort.setCoursePic(rs.getString("coursePic"));
                courseSort.setCourseDes(rs.getString("courseDes"));
                courseSort.setAuthorId(rs.getInt("authorId"));
                courseSort.setAuthorName(rs.getString("authorName"));
                courseSort.setCourseDuration(rs.getInt("courseDuration"));
                courseSort.setCourseTimeLong(rs.getString("courseTimeLong"));
                courseSort.setCourseStatus(rs.getInt("courseStatus"));
                courseSort.setCourseCollectNum(rs.getInt("courseCollectNum"));
                courseSort.setCourseClickNum(rs.getInt("courseClickNum"));
                courseSort.setIsAudit(rs.getInt("isAudit"));
                courseSort.setCourseBelong(rs.getInt("courseBelong"));
                courseSort.setIsRecommend(rs.getInt("isRecommend"));
                courseSort.setOrgId(rs.getInt("orgId"));
                courseSort.setOrgName(rs.getString("orgName"));
                courseSort.setCourseStream(rs.getString("courseStream"));
                courseSort.setCreatorId(rs.getInt("creatorId"));
                courseSort.setCreatorName(rs.getString("creatorName"));
                courseSort.setCreateTime(rs.getDate("createtime"));
                return courseSort;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist;
    }

    public List<Adks_course> getCourseListByIds(Map map) {
        String sql = "select courseId, courseName,courseCode, courseType,coursePic ,courseDes,authorId,authorName,courseSortId,courseSortName,courseSortCode,courseDuration,"
                + "courseTimeLong,courseStatus,courseCollectNum,courseClickNum,orgId,orgName,isAudit,courseBelong,isRecommend"
                + ",courseStream,creatorId,creatorName,createtime,courseStudiedLong" + " from adks_course where 1=1 ";
        sql += " and isAudit=1 and courseStatus=1 ";
        if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
            sql += " and orgId=" + map.get("orgId");
        }
        if (map.get("courseIds") != null && !"".equals(map.get("courseIds"))) {
            sql += " and courseId in (" + map.get("courseIds") + ") ";
        }

        List<Adks_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course>() {
            @Override
            public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course courseSort = new Adks_course();
                courseSort.setCourseId(rs.getInt("courseId"));
                courseSort.setCourseName(rs.getString("courseName"));
                courseSort.setCourseCode(rs.getString("courseCode"));
                courseSort.setCourseSortId(rs.getInt("courseSortId"));
                courseSort.setCourseSortName(rs.getString("courseSortName"));
                courseSort.setCourseSortCode(rs.getString("courseSortCode"));
                courseSort.setCourseType(rs.getInt("courseType"));
                courseSort.setCoursePic(rs.getString("coursePic"));
                courseSort.setCourseDes(rs.getString("courseDes"));
                courseSort.setAuthorId(rs.getInt("authorId"));
                courseSort.setAuthorName(rs.getString("authorName"));
                courseSort.setCourseDuration(rs.getInt("courseDuration"));
                courseSort.setCourseTimeLong(rs.getString("courseTimeLong"));
                courseSort.setCourseStatus(rs.getInt("courseStatus"));
                courseSort.setCourseCollectNum(rs.getInt("courseCollectNum"));
                courseSort.setCourseClickNum(rs.getInt("courseClickNum"));
                courseSort.setIsAudit(rs.getInt("isAudit"));
                courseSort.setCourseBelong(rs.getInt("courseBelong"));
                courseSort.setIsRecommend(rs.getInt("isRecommend"));
                courseSort.setOrgId(rs.getInt("orgId"));
                courseSort.setOrgName(rs.getString("orgName"));
                courseSort.setCourseStream(rs.getString("courseStream"));
                courseSort.setCreatorId(rs.getInt("creatorId"));
                courseSort.setCreatorName(rs.getString("creatorName"));
                courseSort.setCreateTime(rs.getDate("createtime"));
                return courseSort;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist;
    }

    public List<Adks_course> getCourseListByLikedName(Map map) {
        String sql = "select distinct courseId, courseName,courseCode, courseType,coursePic ,courseSortName,courseClickNum "
                + " from adks_course where 1=1 ";
        sql += " and isAudit=1 and courseStatus=1 ";
        if (map.get("courseId") != null && !"".equals(map.get("courseId"))) {
            sql += " and courseId <>" + map.get("courseId");
        }
        if (map.get("courseName") != null && !"".equals(map.get("courseName"))) {
            sql += " and courseName  like '%" + map.get("courseName") + "%' ";
        }
        if (map.get("orgCode") != null && !"".equals(map.get("orgCode"))) {
        	sql += " and ((orgCode like '%" + map.get("orgCode") + "%' and courseBelong <> 2 ) or courseBelong=1 ) ";
        }
        List<Adks_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course>() {
            @Override
            public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course courseSort = new Adks_course();
                courseSort.setCourseId(rs.getInt("courseId"));
                courseSort.setCourseName(rs.getString("courseName"));
                courseSort.setCourseCode(rs.getString("courseCode"));
                courseSort.setCourseSortName(rs.getString("courseSortName"));
                courseSort.setCourseType(rs.getInt("courseType"));
                courseSort.setCoursePic(rs.getString("coursePic"));
                courseSort.setCourseClickNum(rs.getInt("courseClickNum"));
                return courseSort;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist;
    }

    public List<Adks_course> getCourseListByAuthorAndLimitNum(Integer author,Map map) {
        String sql = "select distinct courseId, courseName,courseCode, courseType,coursePic ,courseSortName,courseClickNum,authorName,createTime,courseDes "
                + " from adks_course where 1=1 ";
        sql += " and isAudit=1 and courseStatus=1 ";
        sql += " and authorId=" + author;
        if (map.get("orgCode") != null && !"".equals(map.get("orgCode"))) {
        	sql += " and ((orgCode like '%" + map.get("orgCode") + "%' and courseBelong <> 2 ) or courseBelong=1 ) ";
        }

        List<Adks_course> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_course>() {
            @Override
            public Adks_course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_course courseSort = new Adks_course();
                courseSort.setCourseId(rs.getInt("courseId"));
                courseSort.setCourseName(rs.getString("courseName"));
                courseSort.setCourseCode(rs.getString("courseCode"));
                courseSort.setCourseSortName(rs.getString("courseSortName"));
                courseSort.setCourseType(rs.getInt("courseType"));
                courseSort.setCoursePic(rs.getString("coursePic"));
                courseSort.setCourseClickNum(rs.getInt("courseClickNum"));
                return courseSort;
            }
        });
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist;
    }
    
    public Integer getCourseUserLocation(Integer courseId, Integer gradeId, Integer userId) {
    	String sql="select lastPosition from adks_course_user where userId="+userId+" and courseId="+courseId;
    	Map<String ,Object> map=mysqlClient.queryForMap(sql, null);
    	if(map!=null && map.size()>0){
    		String num=map.get("lastPosition")+"";
    		return Integer.parseInt((num==null || "".equals(num)?"0":num));
    	}
		return 0;
	}

	public Integer getCourseSuperUserLocation(Integer courseId, Integer userId) {
		String sql="select lastPosition from adks_course_user where userId="+userId+" and courseId="+courseId;
    	Map<String ,Object> map=mysqlClient.queryForMap(sql, null);
    	if(map!=null && map.size()>0){
    		String num=map.get("lastPosition")+"";
    		return Integer.parseInt((num==null || "".equals(num)?"0":num));
    	}
		return 0;
	}
	
	public String getVideoServer(Integer courseId){
		String sql="select url from adks_heartbeat where state='alive'";
    	List<Map<String ,Object>> list=mysqlClient.queryForList(sql, null);
    	if(list!=null && list.size()>0){
    		//随机分配
    		Integer index=new Random().nextInt(list.size());
    		return list.get(index).get("url")+"";
    	}
		return null;
	}

	public void updateCourseClickNum(Integer courseId){
		String sql="update adks_course set courseClickNum=courseClickNum+1 where courseId="+courseId;
		mysqlClient.update(sql, null);
	}
	public String getCourseVideoRP(Integer courseId){
		String sql="select courseStream from adks_course where courseId="+courseId;
    	Map<String ,Object> list=mysqlClient.queryForMap(sql, null);
    	if(list!=null && list.size()>0){
    		return list.get("courseStream")+"";
    	}
		return null;
	}
	
	/**
	 * 
	 * @Title updateCourseDuring
	 * @Description：更新课程时长
	 * @author xrl
	 * @Date 2017年5月11日
	 * @param course
	 */
	public void updateCourseDuring(Adks_course course){
		String sql="update adks_course "
				+ "set courseDuration="+course.getCourseDuration()+",courseTimeLong='"+course.getCourseTimeLong()
				+"' where courseId="+course.getCourseId();
		mysqlClient.update(sql, null);
	}
	public void updateCourseChooseNum( Integer courseId,String type){
		String sql="";
		if("on".equals(type)){
			sql = "update adks_course set courseCollectNum=courseCollectNum+1 where courseId="+courseId;
		}else{
			sql = "update adks_course set courseCollectNum=courseCollectNum-1 where courseId="+courseId+" and courseCollectNum is not null and courseCollectNum<>0";
		}
		mysqlClient.update(sql, null);
	}
}
