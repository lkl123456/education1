package com.adks.dubbo.providers.admin.course;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.commons.zip.DeleteDirOrFile;
import com.adks.commons.zip.ZipToFile;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.interfaces.admin.course.CourseApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.course.CourseService;
import com.adks.dubbo.service.web.course.CourseWebService;

public class CourseApiImpl implements CourseApi {
    @Autowired
    private CourseWebService courseWebService;

    @Autowired
    private CourseService courseService;

    //    @Autowired
    //    private CourseAppService courseAppService;

    @Override
    public List<Adks_course> getCourseListByOrgCode(String orgCode) {
        return courseService.getCourseListByOrgCode(orgCode);
    }

    @Override
    public boolean courseBycourseSort(Integer courseSortId) {
        return courseService.courseBycourseSort(courseSortId);
    }

    @Override
    public void deleteCourse(String courseIds,boolean isBelong) {
        courseService.deleteCourse(courseIds,isBelong);
        //courseWebService.deleteCourse(courseId);
    }

    @Override
    public Integer saveCourse(Adks_course course) {
        Integer flag = 0;
        //org
        Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("courseName", course.getCourseName());
        insertColumnValueMap.put("courseCode", course.getCourseCode());
        insertColumnValueMap.put("courseType", course.getCourseType());
        insertColumnValueMap.put("coursePic", course.getCoursePic());
        insertColumnValueMap.put("courseDes", course.getCourseDes());
        insertColumnValueMap.put("authorId", course.getAuthorId());
        insertColumnValueMap.put("authorName", course.getAuthorName());
        insertColumnValueMap.put("courseSortId", course.getCourseSortId());
        insertColumnValueMap.put("courseSortName", course.getCourseSortName());
        insertColumnValueMap.put("courseSortCode", course.getCourseSortCode());
        insertColumnValueMap.put("courseDuration", course.getCourseDuration());
        insertColumnValueMap.put("courseTimeLong", course.getCourseTimeLong());
        insertColumnValueMap.put("courseStatus", course.getCourseStatus());
        insertColumnValueMap.put("courseCollectNum", course.getCourseCollectNum());
        insertColumnValueMap.put("courseClickNum", course.getCourseClickNum());

        insertColumnValueMap.put("orgId", course.getOrgId());
        insertColumnValueMap.put("orgCode", course.getOrgCode());
        insertColumnValueMap.put("orgName", course.getOrgName());
        insertColumnValueMap.put("isAudit", course.getIsAudit());
        insertColumnValueMap.put("courseBelong", course.getCourseBelong());
        insertColumnValueMap.put("isRecommend", course.getIsRecommend());

        insertColumnValueMap.put("creatorId", course.getCreatorId());
        insertColumnValueMap.put("creatorName", course.getCreatorName());

        if (course.getCourseId() != null) {
            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("courseId", course.getCourseId());
            flag = courseService.update(insertColumnValueMap, updateWhereConditionMap);
            //            courseAppService.getCourseById(course.getCourseId());
            //更新课程，除了课程分类、大家正在看、排行、观看记录、专题班级课程，其余全部清除
            courseService.deleteRedis("new,rank,gradeCourse,about,author_course_"+course.getAuthorId());
        }
        else {
            insertColumnValueMap.put("courseStream", course.getCourseStream());
            insertColumnValueMap.put("courseStudiedLong", course.getCourseStudiedLong());
            insertColumnValueMap.put("createtime", course.getCreateTime());
            flag = courseService.insert(insertColumnValueMap);
            //如果flag有值，就更新课程分类的课程量
            updateCourseSortCourseNum(course.getCourseSortId(), 1);
            String _courseName = course.getCourseName();
    		if(course.getCourseName().length() > 4){
    			_courseName = course.getCourseName().substring(0, course.getCourseName().length()-4);
    		}
            //添加课程，只需清除最新课程,排行，讲师主讲，相关课程的缓存
            courseService.deleteRedis("new,rank,author_course_"+course.getAuthorId()+",about_"+_courseName);
        }
        //Adks_course adks_course = getCourseById(flag);
        //courseWebService.addCourseToSolr(adks_course);
        return flag;
    }
    //如果课程换了讲师
    public void deleteAuthorCourseRedis(Integer authorId,Integer oldAuthorId){
    	courseService.deleteRedis("author_course_"+authorId);
    	courseService.deleteRedis("author_course_"+oldAuthorId);
    }

    //flag 1增加2减少
    public void updateCourseSortCourseNum(Integer courseSortId, Integer flag) {
        courseService.updateCourseSortCourseNum(courseSortId, flag);
    }

    @Override
    public Page<List<Map<String, Object>>> getCourseListPage(Page<List<Map<String, Object>>> page) {
        return courseService.getCourseListPage(page);
    }

    @Override
    public void courseUpdateData(Integer courseId, Integer flag, String result) {
        courseService.courseUpdateData(courseId, flag, result);
    }

    public Adks_course getCourseById(Integer courseId) {
        return courseService.getCourseById(courseId);
    }

    public Adks_course getCourseByName(String courseName) {
        return courseService.getCourseByName(courseName);
    }

    public void checkCourseNametoTabs(Integer courseId, String courseName) {
        courseService.checkCourseNametoTabs(courseId, courseName);
    }

	@Override
	public boolean canDelCourse(Integer courseId) {
		return courseService.canDelCourse(courseId);
	}
	@Override
	//三分屏解压上传阿里云
	public String zipFileToAliyun(String courseCode,Adks_course course,String courseWareFileFileName){
		
		String zip_Path = PropertiesFactory.getProperty("config.properties","base.path.course.zip");
		System.out.println("========================zipFileToAliyun zip_Path="+zip_Path);
		String unzip_Path = PropertiesFactory.getProperty("config.properties","base.path.course.unzip");
		System.out.println("========================zipFileToAliyun unzip_Path="+unzip_Path);
		String dirzip_Path = PropertiesFactory.getProperty("config.properties","base.path.dir.zip");
		System.out.println("========================zipFileToAliyun dirzip_Path="+dirzip_Path);
		String dirunzip_Path = PropertiesFactory.getProperty("config.properties","base.path.dir.unzip");
		System.out.println("========================zipFileToAliyun dirunzip_Path="+dirunzip_Path);
		String scormPath = PropertiesFactory.getProperty("ossConfig.properties","oss.Scorm_Path");
		System.out.println("========================zipFileToAliyun scormPath="+scormPath);
		boolean zipPath = ZipToFile.zipToFile(courseCode.trim(), zip_Path.trim(),unzip_Path.trim());
		System.out.println("========================zipFileToAliyun zipPath="+zipPath);
		String code="";
		if (zipPath) {
			// 上传到阿里云OSS
			if (courseWareFileFileName != null && !"".equals(courseWareFileFileName)) {
				if (course.getCourseCode() == null || "".equals(course.getCourseCode())) { // 第一次添加
					File file = new File(dirunzip_Path + courseCode);
					if (file.exists()) {
						String path_new = OSSUploadUtil.uploadDirIndex(dirunzip_Path + courseCode, scormPath);
						//File f = new File(dirunzip_Path + courseCode);
						File fi = new File(dirzip_Path + courseCode + ".zip");
						// boolean uzipDir=new
						// DeleteDir().deleteDir(f);
						boolean zip = false;
						if (fi.exists()) {
							zip = fi.delete();
						}
						if (path_new != null && !"".equals(path_new)) {
							code=courseCode;
						}
					} else {
						System.out.println("==============Zip File does not exist");
					}

				} else if (course.getCourseCode() != null
						&& !"".equals(course.getCourseCode())) { // 更改
					File file = new File(dirunzip_Path + courseCode);
					if (file.exists()) {
						String path_update = OSSUploadUtil.uploadDirIndex(dirunzip_Path + courseCode, scormPath);
						File fi = new File(dirzip_Path + courseCode + ".zip");
						boolean zip = false;
						if (fi.exists()) {
							zip = fi.delete();
						}
						if (path_update != null && !"".equals(path_update)) {
							code=courseCode;
						}
					} else {
						System.out.println("==============Zip File does not exist");
					}
				}
			}
		} else {
			code="";
		}
		return courseCode;
	}

	/**
	 * 
	 * @Title getCourseStatisticsListPage
	 * @Description
	 * @author xrl
	 * @Date 2017年6月19日
	 * @param page
	 * @return
	 */
	@Override
	public Page<List<Map<String, Object>>> getCourseStatisticsListPage(Page<List<Map<String, Object>>> page) {
		return courseService.getCourseStatisticsListPage(page);
	}

	/**
	 * 
	 * @Title courseApi
	 * @Description
	 * @author xrl
	 * @Date 2017年6月19日
	 * @param map
	 * @return
	 */
	@Override
	public List<Adks_course> getCourseStatisticsList(Map<String, Object> map) {
		return courseService.getCourseStatisticsList(map);
	}

}
