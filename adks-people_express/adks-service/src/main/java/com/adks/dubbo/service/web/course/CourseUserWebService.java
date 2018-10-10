package com.adks.dubbo.service.web.course;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.util.ComUtil;
import com.adks.commons.util.DateTimeUtil;
import com.adks.commons.util.RedisConstant;
import com.adks.dbclient.redis.RedisClient;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.course.CourseJson;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.web.course.CourseUserWebDao;
import com.adks.dubbo.dao.web.course.CourseWebDao;
import com.adks.dubbo.util.CourseRedisUtil;
import com.alibaba.fastjson.JSONObject;

@Service
public class CourseUserWebService extends BaseService<CourseUserWebDao> {
    @Autowired
    private RedisClient redisClient;

    @Autowired
    private CourseUserWebDao courseUserDao;
    
    private DecimalFormat df = new DecimalFormat("######0.00");

    @Override
    protected CourseUserWebDao getDao() {
        return courseUserDao;
    }
    
    @Autowired
    private CourseWebDao courseDao;
    
    public Adks_course_user getCourseUserByGradeIdCourseIdAndUserIdforCourseJD(Map map) {
        return courseUserDao.getCourseUserByGradeIdCourseIdAndUserIdforCourseJD(map);
    }

    //大家正在看（首页）
    public List<Adks_course_user> getTopCourseUserList(Map map) {
        List<Adks_course_user> list = null;
        if(map.get("orgCode")!=null && !"".equals(map.get("orgCode"))){
        	String orgCode=MapUtils.getString(map, "orgCode");
    		String result=CourseRedisUtil.getCourse(CourseRedisUtil.adks_course_looking, orgCode, false);
    		if("".equals(result) || "Nodata".equals(result)){
    			list = courseUserDao.getTopCourseUserList(map);
    			CourseRedisUtil.addCourseUser(CourseRedisUtil.adks_course_looking, orgCode, list);
    		}else{
    			list=JSONObject.parseArray(result, Adks_course_user.class);
    		}
        }
        return list;
    }

    //该方法目前没有使用到
    public String getStudyCourseTime(Integer userId,String username) {
    	String time = "";
    	 Map<String, Object> map = courseUserDao.getStudyCourseTime(userId);
         Double studyCourseTime=0.0;
         if((map.get("studyAllTimeLong") !=null && !"".equals(map.get("studyAllTimeLong")))&& (map.get("courseDuration") != null && !"".equals(map.get("courseDuration"))) && 
         		((Integer)((BigDecimal)map.get("courseDuration")).intValue())<=((Integer)((BigDecimal)map.get("studyAllTimeLong")).intValue())){
         	studyCourseTime = map.get("courseDuration") == null ? null: (double) ((Long) ((BigDecimal) map.get("courseDuration")).longValue()/(double) 3600);
         }else{
         	studyCourseTime = map.get("studyAllTimeLong") == null ? null: (double) ((Long) ((BigDecimal) map.get("studyAllTimeLong")).longValue()/ (double)3600);
         }
         time = studyCourseTime == null ? "0.00" : df.format(studyCourseTime);
        return time;
    }

    public Page<List<Adks_course_user>> getCourseUserByUserId(Page<List<Adks_course_user>> page) {
        return courseUserDao.getCourseUserByUserId(page);
    }

    public Page<List<Adks_course_user>> getUserCourseViewByUserId(Page<List<Adks_course_user>> page) {
        return courseUserDao.getUserCourseViewByUserId(page);
    }

    public List<Adks_course_user> getCourseUserByCon(Map map) {
        return courseUserDao.getCourseUserByCon(map);
    }
    
	private static int  Time_Gap = 10;//时间差距
	
	private boolean isOver = false;//是否学完
	
	private boolean isRepeat = false;//是否重复学习
    /**
     * 批量更新-用户学习进度
     * @param courseJsonMap 用户记录对象Map
     */
    public void updateUserStudySpeed(CourseJson courseJson){
    	
    	/**1、判断并设置【课程总时长】到 courseJson*******************************/
    	if(courseJson.getDuration()!=null&&!courseJson.getDuration().equals("")&&!courseJson.getDuration().equals("null")&&!courseJson.getDuration().equals("undefined")){
    		
    		Double duration =Double.parseDouble(courseJson.getDuration());
    		if(ComUtil.isNotNullOrEmpty(duration)){
    			
    			String durationStr = DateTimeUtil.longToString(duration.longValue());
    			if(ComUtil.isNotNullOrEmpty(durationStr)){
					courseJson.setDuration(durationStr);
				}
			}
		}
    	
    	/**2、获取【课程】基本信息*******************************/
		Adks_course _course = null;
		/**2.1 先从Redis取课程对象*******************************/
		if(courseJson.getCourseId()!=null&&!courseJson.getCourseId().equals("")&&!courseJson.getCourseId().equals("null")&&!courseJson.getCourseId().equals("undefined")){
			
			String courseRedisName = RedisConstant.adks_people_express_course+courseJson.getCourseCode();
			
			//A、*******从Redis取课程对象********
			_course =JSONObject.parseObject(redisClient.get(courseRedisName),Adks_course.class); 
		}
		
		/**2.2 如果Redis中未存该课 直接查询*******************************/
		if(_course == null){
			if(courseJson.getCourseId()!=null&&!courseJson.getCourseId().equals("")&&!courseJson.getCourseId().equals("null")&&!courseJson.getCourseId().equals("undefined")){
				_course = courseDao.getCourseById(Integer.parseInt(courseJson.getCourseId()));
				//B、*******把该课 存入Redis********
				//把该课 存入Redis
				String courseRedisName = RedisConstant.adks_people_express_course+_course.getCourseCode();
				redisClient.set(courseRedisName, JSONObject.toJSONString(_course));
			}
		}
		
		/**3、更新课程总时长******************************/
		if (_course !=null) {
			if (ComUtil.isNotNullOrEmpty(courseJson.getDuration())){
				Integer duration=DateTimeUtil.stringToInteger(courseJson.getDuration());
				Integer courseTimeLong=DateTimeUtil.stringToInteger(_course.getCourseTimeLong());
				if ((!duration.equals(courseTimeLong))&&(duration>60)) {
					_course.setCourseDuration(DateTimeUtil.stringToInteger(courseJson.getDuration()));
					_course.setCourseTimeLong(courseJson.getDuration());
					//C、*******更新课程时长********
					courseDao.updateCourseDuring(_course);
				}
			}
		}
    	
    	isOver = false;
		
    	Adks_course_user _courseUser = null;
    	/**
    	 * 4、获取并更新【用户学习记录】
    	 * GreadId 班级ID，如果不为空  代表:班级用户 、为空或者CourseSuper 代表:课件超市
    	 */
    	if (ComUtil.isNotNullOrEmpty(courseJson.getUserId())&&
    		ComUtil.isNotNullOrEmpty(courseJson.getCourseId())&& 
    		ComUtil.isNotNullOrEmpty(courseJson.getLocation())&& 
    		ComUtil.isNotNullOrEmpty(courseJson.getSessiontime())) {
    		
    		/**4.1 获取【用户学习记录】对象*******************************/
    		//D、*******获取学习记录对象********
    		_courseUser = courseUserDao.getCourseUserByCourseIdAndUserId(Integer.parseInt(courseJson.getCourseId()), Integer.parseInt(courseJson.getUserId()));
    		
    		if(_courseUser == null) {
    			/**4.2 首次保存【用户学习记录】*******************************/
    			_courseUser = saveCourseUser(_courseUser,courseJson, _course);
    		}else{
    			/**4.3 更新【用户学习记录】*******************************/
    			isRepeat = false;
				//查询课程是否学完，防止重复加分 学习状态（是否学完   1.已学完；2.未学完）
				if (_courseUser.getIsOver() == 1) {
					
					isOver = true;//表示已学完
					isRepeat = true;//表示重复学习 不添加学分
				}
				updateCourseUser(_courseUser,courseJson,_course);
    		}
    	}
    	
    	/**
    	 * 5、更新【班级用户学时】
    	 * 
    	 * 只有当 已学完 且 非重复学习 才能更新学时  即：isOver == true && isRepeat == false
		 * isOver    true/已学完          false/未学完 
		 * isRepeat  true/重复学习      false/非重复学习 
		 */
		if (isOver == true && isRepeat == false) {
			updateGradeUser(_courseUser,courseJson.getUserId());
		}
    }
    
    
    /**
	 * 第一次观看该课程、没有发现学习记录则添加
	 * 
	 * @param _courseUser 课程用户
	 * @param userId      用户ID
	 * @param gradeId     班级ID
	 * @param courseId    课程ID
	 * @param _course     课程对象
	 * @return
	 */
	public Adks_course_user saveCourseUser(Adks_course_user _courseUser,CourseJson courseJson,Adks_course _course) {
		
		if (_courseUser == null) {
			
			Map<String, Object> courseUserMap=new HashMap<>();
			
			//用户ID
			courseUserMap.put("userId", Integer.parseInt(courseJson.getUserId()));
			//课程ID
			courseUserMap.put("courseId", Integer.parseInt(courseJson.getCourseId()));
			//课程名称
			courseUserMap.put("courseName", _course.getCourseName());
			//课程编码
			courseUserMap.put("courseCode", _course.getCourseCode());
			//课程时长 int
			courseUserMap.put("courseDuration", _course.getCourseDuration());
			//课程时长 time
			courseUserMap.put("courseDurationLong", _course.getCourseTimeLong());
			//课程类型  单视频/171  三分屏/170
			courseUserMap.put("courseCwType", _course.getCourseType());
			//课程图片
			courseUserMap.put("courseImg", _course.getCoursePic());
			//讲师ID
			courseUserMap.put("authorId", _course.getAuthorId());
			
			//本次学习时长
			Long SessinoTimeLong = DateTimeUtil.stringToLong(courseJson.getSessiontime());
			if(SessinoTimeLong!=null){
				courseUserMap.put("studyCourseTime", SessinoTimeLong.intValue());
			}
			
			//根据约束条件 （学习时长 + N秒) >= 课程总时长 《==》学完 ，把学员学习时长 改成 课程时长
			if((SessinoTimeLong.intValue()+Time_Gap) >= _course.getCourseDuration()){
				//学习状态（是否学完   1.已学完；2.未学完）
				courseUserMap.put("isOver", 1);
				//学习总时长 （单位：秒）
				courseUserMap.put("studyAllTimeLong", _course.getCourseDuration());
				//学习总时长（格式：01:23:30）
				courseUserMap.put("studyAllTime", _course.getCourseTimeLong());
				//播放位置
				courseUserMap.put("lastPosition", 0);
				
				//所在班级
				if(ComUtil.isNotNullOrEmpty(courseJson.getGreadId())){
					if(ComUtil.isNotNullOrEmpty(_courseUser.getGrades())){
						String[] gradeIds=_courseUser.getGrades().split(",");
						if(!ArrayUtils.contains(gradeIds,_courseUser.getGradeId())){
							courseUserMap.put("grades", _courseUser.getGrades()+","+courseJson.getGreadId());
						}
					}else{
						courseUserMap.put("grades",courseJson.getGreadId());
					}
				}
				
				isOver =  true;
			}else {
				//学习状态（是否学完   1.已学完；2.未学完）
				courseUserMap.put("isOver", 2);
				//学习总时长 （单位：秒）
				courseUserMap.put("studyAllTimeLong", SessinoTimeLong.intValue());
				//学习总时长（格式：01:23:30）
				courseUserMap.put("studyAllTime", courseJson.getSessiontime());
				//最后播放位置
				if(ComUtil.isNotNullOrEmpty(courseJson.getLocation())){
					Double Location = Double.parseDouble(courseJson.getLocation());
					courseUserMap.put("lastPosition", Location.intValue());
				}
			}
			//最后播放日期
			courseUserMap.put("lastStudyDate", new Date());
			//是否收藏(1.收藏；2.未收藏)
			courseUserMap.put("isCollection", 2);
			//选课时间
			courseUserMap.put("xkdate", new Date());
			//E、*******执行保存学习记录********
			courseUserDao.insert(courseUserMap);
			
		}
		
		return  _courseUser;
	}
	
	/**
	 * 更新用户学习进度信息
	 * 
	 * @param _courseUser
	 * @param Sessiontime
	 * @param Location
	 * @param userId
	 * @param gradeId
	 * @param courseId
	 * @param _course
	 */
	private void updateCourseUser(Adks_course_user _courseUser,CourseJson courseJson,Adks_course _course) {
		
		Map<String, Object> courseUserMap=new HashMap<>();
		
		//isOver 学习状态（是否学完   1.已学完；2.未学完） 未学完更新进度 已学完不更新进度 只更新时间点
		Long  NewSessionTimeLong = DateTimeUtil.stringToLong(courseJson.getSessiontime());
		//学习时长 ThisSessionTime = OldSessionTime + NewSessionTime
		Integer ThisSessionTime = _courseUser.getStudyAllTimeLong() + NewSessionTimeLong.intValue();
		
		//最后播放位置
		if(ComUtil.isNotNullOrEmpty(courseJson.getLocation())){
			
			if(ComUtil.isNotNullOrEmpty(courseJson.getLocation())){
				Double Location = Double.parseDouble(courseJson.getLocation());
				courseUserMap.put("lastPosition", Location.intValue());
			}
		}

		//本次学习时长
		
		if(ComUtil.isNotNullOrEmpty(courseJson.getSessiontime())){
			courseUserMap.put("studyCourseTime", NewSessionTimeLong.intValue());
		}
		//未学完 --- 则更新进度
		if (_courseUser.getIsOver()!= 1) {
			//先判断用户学习进度是否为空
			if(ComUtil.isNotNullOrEmpty(_courseUser.getStudyAllTimeLong())){
				
				courseUserMap.put("studyAllTimeLong", ThisSessionTime);
				courseUserMap.put("studyAllTime", DateTimeUtil.longToString(ThisSessionTime));
			}else {
				//空值，直接保存
				courseUserMap.put("studyAllTimeLong", NewSessionTimeLong.intValue());
				courseUserMap.put("studyAllTime", courseJson.getSessiontime());
			}
			//根据约束条件 （学习时长 + N秒) >= 课程总时长 《==》学完 ，把学员学习时长 改成 课程时长
			if((ThisSessionTime.intValue()+Time_Gap) >= _course.getCourseDuration()){
				//学习状态（是否学完   1.已学完；2.未学完）
				courseUserMap.put("isOver", 1);
				//学习总时长 （单位：秒）
				courseUserMap.put("studyAllTimeLong", _course.getCourseDuration());
				//学习总时长（格式：01:23:30）
				courseUserMap.put("studyAllTime", _course.getCourseTimeLong());
				//播放位置
				courseUserMap.put("lastPosition", 0);
				
				//所在班级
				if(ComUtil.isNotNullOrEmpty(courseJson.getGreadId())){
					if(ComUtil.isNotNullOrEmpty(_courseUser.getGrades())){
						String[] gradeIds=_courseUser.getGrades().split(",");
						if(!ArrayUtils.contains(gradeIds,courseJson.getGreadId())){
							courseUserMap.put("grades", _courseUser.getGrades()+","+courseJson.getGreadId());
						}
					}else{
						courseUserMap.put("grades",courseJson.getGreadId());
					}
				}
				
				isOver =  true;
			}else{
				courseUserMap.put("isOver", 2);
			}
		}
		//最后播放日期
		courseUserMap.put("lastStudyDate", new Date());
		
		//更新学习记录 
		//F、*******执行更新学习记录********
		Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
		updateWhereConditionMap.put("courseUserId", _courseUser.getCourseUserId());
		courseUserDao.update(courseUserMap, updateWhereConditionMap);
	}
	
	/**
	 * 更新 班级用户 必修/选修 学习情况
	 * 
	 * @param _courseUser
	 * @param UserId
	 * @param gradeId
	 */
	private void  updateGradeUser(Adks_course_user _courseUser,String UserId) {
		
		if(ComUtil.isNotNullOrEmpty(UserId)){
			//查询用户所在班级集合
			List<String> gList = courseUserDao.getGradeList(UserId);
			//循环更新用户所在班级的课程学时
			if(gList!=null&&gList.size()>0){
				String gradeId =null;
				//班级用户_gradeUser
				Adks_grade_user _gradeUser = null;
				//班级课程_gradeCourse
				Adks_grade_course _gradeCourse = null;
				//更新班级用户集合gradeUserMap
				Map<String, Object> gradeUserMap=null;
				//班级
				String grades="";
				if(_courseUser!=null){
					grades=_courseUser.getGrades();
				}
				
				for(int i=0;i<gList.size();i++){
					gradeId = gList.get(i);
					gradeUserMap=new HashMap<>();
//					boolean flag=false;
//					if(ComUtil.isNotNullOrEmpty(grades)){
//						String[] gradeIds=grades.split(",");
//						if(!ArrayUtils.contains(gradeIds,gradeId.toString())){
//							grades=grades+","+gradeId;
//							flag=true;
//						}
//					}else{
//						grades=gradeId;
//						flag=true;
//					}
					
					if (ComUtil.isNotNullOrEmpty(UserId) && ComUtil.isNotNullOrEmpty(gradeId)){
						//G、*******获取班级用户********
						_gradeUser = courseUserDao.getGradeUser(Integer.parseInt(gradeId), Integer.parseInt(UserId));
						if(_gradeUser != null){
							gradeUserMap.put("gradeUserId", _gradeUser.getGradeUserId());
						}
					}
					
					if (ComUtil.isNotNullOrEmpty(UserId) && _courseUser!=null){
						
						//H、*******获取班级课程********
						_gradeCourse = courseUserDao.getGradeCourse(Integer.parseInt(gradeId), _courseUser.getCourseId());
					}
					
					if(_gradeUser != null && _gradeCourse!=null){
						
						Double ReqAndOpt = 0.0;//课程学时
						if(ComUtil.isNotNullOrEmpty(_gradeCourse.getCredit())){
							ReqAndOpt = _gradeCourse.getCredit();//课程学分
						}
						
						Integer GcState = 0;//课程必修&选修
						if(ComUtil.isNotNullOrEmpty(_gradeCourse.getGcState())){
							GcState = _gradeCourse.getGcState();//状态
						}
						
						if(GcState == 1){// 必修 
							//必修学时 ReqTime = OldReq + NewReq
							if(ComUtil.isNotNullOrEmpty(_gradeUser.getRequiredPeriod())){
								gradeUserMap.put("requiredPeriod", _gradeUser.getRequiredPeriod()+ReqAndOpt);
							}else{
								gradeUserMap.put("requiredPeriod", ReqAndOpt);
							}
						}else if(GcState == 2){// 选修
							//选修学时 OptTime = OldOpt + NewOpt
							if(ComUtil.isNotNullOrEmpty(_gradeUser.getOptionalPeriod())){
								gradeUserMap.put("optionalPeriod", _gradeUser.getOptionalPeriod()+ReqAndOpt);
							}else{
								gradeUserMap.put("optionalPeriod", ReqAndOpt);
							}
						}
						//I、*******更新班级用户必修学时和选修学时********
						courseUserDao.updateGradeUser(gradeUserMap);
						
						if(ComUtil.isNotNullOrEmpty(grades)){
							String[] gradeIds=grades.split(",");
							if(!ArrayUtils.contains(gradeIds,gradeId.toString())){
								grades=grades+","+gradeId;
							}
						}else{
							grades=gradeId;
						}
					}
				}
				if(ComUtil.isNotNullOrEmpty(grades)){
					Map<String, Object> courseUserMap=new HashMap<String,Object>();
					courseUserMap.put("grades", grades);
					Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
					updateWhereConditionMap.put("courseUserId", _courseUser.getCourseUserId());
					courseUserDao.update(courseUserMap, updateWhereConditionMap);
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title getCourseUserByCourseIdAndUserId
	 * @Description：根据userId和courseId获取学习记录
	 * @author xrl
	 * @Date 2017年6月3日
	 * @param map
	 * @return
	 */
	public Adks_course_user getCourseUserByCourseIdAndUserId(Map<String, Object> map){
		return courseUserDao.getCourseUserByCourseIdAndUserId(map);
	}
	
	public void updateCourseUserForGrades(Map<String, Object> map){
		courseUserDao.updateCourseUserForGrades(map);
	}
}