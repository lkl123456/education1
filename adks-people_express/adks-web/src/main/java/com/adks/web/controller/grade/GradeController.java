package com.adks.web.controller.grade;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.ComUtil;
import com.adks.commons.util.Constants;
import com.adks.commons.util.DateTimeUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_exam;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.grade.Adks_grade_work;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.web.course.CourseUserApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeExamApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeUserApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeWorkApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping({"/grade"})
public class GradeController {
	
	@Autowired
	private GradeApi gradeApi;
	@Autowired
	private GradeUserApi gradeUserApi;
	@Autowired
	private CourseUserApi courseUserApi;
	@Autowired
	private GradeExamApi gradeExamApi;
	@Autowired
	private GradeWorkApi gradeWorkApi;

	/*进入当前培训班页面*/
	@RequestMapping(value="/gradeCurrentList.do")
	public String gradeCurrentList(Page<List<Adks_grade>> page,HttpServletRequest request,HttpServletResponse response,Integer orgId,Model model){
		
		if(page==null){
			page=new Page<List<Adks_grade>>();
		}
		Map map=new HashMap<>();
		Adks_user user=(Adks_user)request.getSession().getAttribute("user");
		if(user!=null){
			map.put("userId", user.getUserId());
		}
		page.setMap(map);
		page=gradeApi.getGradeCurrentList(page);
		
		model.addAttribute("page", page);
		model.addAttribute("user", user);
		return "/grade/gradeCurrentList";
	}
	
	/*进入历史培训班*/
	@RequestMapping(value="/gradeOverList.do")
	public String gradeOverList(Page<List<Adks_grade>> page,HttpServletRequest request,HttpServletResponse response,Integer orgId,Model model){
		
		if(page==null){
			page=new Page<List<Adks_grade>>();
		}
		Map map=new HashMap<>();
		Adks_user user=(Adks_user)request.getSession().getAttribute("user");
		if(user!=null){
			map.put("userId", user.getUserId());
		}
		page.setMap(map);
		page=gradeApi.getGradeOverList(page);
		
		model.addAttribute("page", page);
		model.addAttribute("user", user);
		return "/grade/gradeOverList";
	}
	
	/*进入班级页面*/
	@RequestMapping(value="/toGradeCenterIndex.do")
	public String toGradeCenterIndex(HttpServletRequest request,HttpServletResponse response,Integer gradeId,Model model){
		Adks_user user=(Adks_user)request.getSession().getAttribute("user");
		String[] orgId=user.getOrgCode().split("A");
        if(orgId.length>=2){
        	model.addAttribute("orgId", orgId[1]);
        }else{
        	model.addAttribute("orgId", orgId[0]);
        }
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("mainFlag", "toGradeCenterIndex");
		return "/index/mainIndex";
	}
	//进入
	@RequestMapping(value="/gradeMessage.do")
	public String gradeMessage(HttpServletRequest request,HttpServletResponse response,Integer gradeId,Model model){
		
		Adks_grade grade = gradeApi.getGradeById(gradeId);
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		
		//学员已获得分数，学时，论文，考试，讨论分数
		Adks_grade_user gradeUser = gradeUserApi.getUserStudysFromGradeUser(user.getUserId(),gradeId); 
		//获取学员在班级内的排名
		Integer userRanking;//排名数字
		if(user.getOrgId()==0){
			userRanking=0;
			gradeUser.setUserRanking(userRanking);
		}else{
			userRanking = gradeApi.getUserRanking(gradeId,user.getUserId());
			gradeUser.setUserRanking(userRanking);
		}
		
		//学员考试,讨论,论文三项学分之和:
		//Integer userExam_dis_work = ((gradeUser.getExamCredit()==null?0:gradeUser.getExamCredit())+(gradeUser.getSchoolWorkCredit()==null?0:gradeUser.getSchoolWorkCredit());
		Integer userExam_dis_work=0;
		Float userbixiuPeriod;  //学员必修学时
		Float userxuanxiuPeriod;  //学员选修学时
		String bixiuAndxuanxiuStr = "0";  //学员总学时
		Double bixiuPeriodLinshi = gradeUser.getRequiredPeriod()==null?0.0:gradeUser.getRequiredPeriod();//必修学时临时变量
		Double xuanxiuPeriodLinshi = gradeUser.getOptionalPeriod()==null?0.0:gradeUser.getOptionalPeriod();//选修学时临时变量
		
//		Float myCredit=userExam_dis_work/100f;
		Double myCredit=bixiuPeriodLinshi+xuanxiuPeriodLinshi;
		Double hourse = myCredit/Double.valueOf(2700);
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
		df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
		String credit = df.format(hourse);//返回的是String类型的
		//查询用户最近正在学的课程列表
		//List<Adks_course_user> cUserList = courseUserApi.getCourseUserByUserIdAndGradeId(user.getUserId(),gradeId);
		
		model.addAttribute("myCredit",credit);  //学分（学时概念）
		model.addAttribute("gradeUser",gradeUser);
		model.addAttribute("userId", user.getUserId());
		model.addAttribute("grade",grade);
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("precisionNum", Constants.precisionNum);
		
		return "/grade/gradeMessage";
	}
	
	//查看班级论文
	@RequestMapping(value="/gradeThesisList.do")
	public String gradeThesisList(Page<List<Adks_grade_work>> page,HttpServletRequest request,HttpServletResponse response,Integer gradeId,String gradeName,Model model){
		if(null == page){
			page = new Page();
		}
		Adks_user user=(Adks_user)request.getSession().getAttribute("user");
		Map map = new HashMap();
		map.put("userId", user.getUserId());
		map.put("gradeId", gradeId);
		
		page.setMap(map);
		page=gradeApi.getGradeWorkByCon(page);
		request.setAttribute("newDate", new Date());
		List<Adks_grade_work> slist = new ArrayList<Adks_grade_work>();
		List<Adks_grade_work> list = page.getRows();
		
		Date edate = null;
		Date sdate = null;
		Date sysdate = new Date();
		
		if(list!=null && list.size()>0){
			for(Adks_grade_work gw:list){
				edate = gw.getEndDate();
				sdate = gw.getStartDate();
				if(sdate.getTime() > sysdate.getTime()){
					gw.setIsOver(1); //未开始
				}
				if(edate.getTime() < sysdate.getTime()){
					gw.setIsOver(2);//已经结束
				}
				if(edate.getTime() > sysdate.getTime() && sdate.getTime() < sysdate.getTime()){
					gw.setIsOver(3);//进行中
				}
				if(gw.getWorkScore() != null ){
					gw.setIsEnd(3);//表示作业已经被批改，无法再修改，提交。
				}else{					
						gw.setIsEnd(4); //表示作业已经提交,还未被老师批改，可以修改。
				}
				slist.add(gw);
			}
			page.setRows(slist);
		}
		model.addAttribute("page",page);
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("gradeName", gradeName);
		model.addAttribute("precisionNum", Constants.precisionNum); 
		return "/grade/gradeThesisList";	
	}
	/**
	 * 点击结业 ,
	 * ckl
	 * 2015年3月23日19:49:05
	 * @return
	 */
	@RequestMapping(value="/graduate.do")
	public String graduate(Integer gradeId, HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){
		Adks_user user = (Adks_user) session.getAttribute("user");
		Adks_grade grade = gradeApi.getGradeById(gradeId);
		//获取班级结业条件，展示,判断学员是否已经结业
		Adks_grade_user gradeUser=gradeUserApi.getUserStudysFromGradeUser(user.getUserId(), gradeId);
		Map map = new HashMap();
		Integer userCourseStudyState = 0; //学员课程学习完成情况标示 ,课程学习达标后，值为2，不可能超过2
		Integer graduate = 0;//结业标示数字（，最大值只能取5，初始化为3,因为考试,论文默认只比较总个数和学员参加的个数作为完成标准）
		Integer userGraduate = 0;//学员完成进度标示(0：啥都没完成；1：完成了一个；最大值不能超过graduate，等于graduate，标示结业)
		
		/*必修选修学时完成标志*/
		Integer bixiuPerioSucc = 0;
		Integer xuanxiuPerioSucc = 0;
		
		Integer bixiuGrade=grade.getRequiredPeriod();//班级需要的必修课程学时
		Integer xuanxiuGrade=grade.getOptionalPeriod();//班级需要的选修课程学时
//		Integer workGrade=grade.getWorkRequire();//班级需要的作业学时
//		Integer examGrade=grade.getExamRequire();//班级需要的考试学时
		
		//班级考试个数
		Integer examCount=0;
		if(grade!=null){
			if(ComUtil.isNotNullOrEmpty(grade.getExamRequire())){
				if(grade.getExamRequire()==1){
					graduate = graduate + 1;
					examCount=gradeExamApi.getGradeExamCount(gradeId);
					map.put("examCount", examCount);
					//学员完成考试个数
					Integer examUserCount = gradeExamApi.getGradeExamUserNum(gradeId, user.getUserId());
					map.put("examUserCount",examUserCount);
					if(examUserCount >= examCount){
						userGraduate = userGraduate + 1;
					}
				}else{
					map.put("examCount", examCount);
					map.put("examUserCount",0);
				}
			}
		}
		//班级作业个数
		Integer workCount=0;
		if(grade!=null){
			if(ComUtil.isNotNullOrEmpty(grade.getWorkRequire())){
				if(grade.getWorkRequire()==1){
					graduate = graduate + 1;
					workCount=gradeWorkApi.getGradeWorkCount(gradeId);
					map.put("workCount", workCount);
					//学员参加论文个数
					Integer workUserCount = gradeWorkApi.getGradeWorkUserNum(gradeId,user.getUserId());
					map.put("workUserCount",workUserCount);
					if(workUserCount >= workCount){
						userGraduate = userGraduate + 1;
					}
				}else{
					map.put("workCount",workCount);
					map.put("workUserCount",0);
				}
			}
		}
		
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
		df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
		Double bixiuGradeUser=gradeUser.getRequiredPeriod();
		Double op = Double.valueOf(bixiuGradeUser)/Double.valueOf(2700);
		String bixiu = df.format(op);//返回的是String类型的
		Double bixiuUser=Double.valueOf(bixiu);
		Double xuanxiuGradeUser=gradeUser.getOptionalPeriod();
		Double rp = Double.valueOf(xuanxiuGradeUser)/Double.valueOf(2700);
		String xuanxiu = df.format(rp);//返回的是String类型的
		Double xuanxiuUser=Double.valueOf(xuanxiu);
		
		if(ComUtil.isNotNullOrEmpty(bixiuGrade)){
			//	trainingRequired.append("必修学分修满 :"+bixiuCredit_total+";	");
			map.put("userbixiuPerio", bixiuUser);
			graduate = graduate + 1;
			if(bixiuGradeUser != null && bixiuUser >= bixiuGrade){
				userGraduate = userGraduate + 1;
				userCourseStudyState = userCourseStudyState + 1;
				bixiuPerioSucc = 1; //必修学分完成了的标志
			}
		}
		
		if(ComUtil.isNotNullOrEmpty(xuanxiuGrade)){
			//	trainingRequired.append("必修学分修满 :"+bixiuCredit_total+";	");
			map.put("userxuanxiuPerio", xuanxiuUser);
			graduate = graduate + 1;
			if(xuanxiuGradeUser != null && xuanxiuUser >= xuanxiuGrade){
				userGraduate = userGraduate + 1;
				userCourseStudyState = userCourseStudyState + 1;
				xuanxiuPerioSucc = 1; //必修学分完成了的标志
			}
		}
		
		map.put("userGraduate", userGraduate);
		map.put("graduate", graduate);
		map.put("userCourseStudyState", userCourseStudyState);
		map.put("bixiuPerioSucc", bixiuPerioSucc);
		map.put("xuanxiuPerioSucc", xuanxiuPerioSucc);
		//获取班级结业与否结束
		model.addAttribute("map",map);
		model.addAttribute("grade",grade);
		model.addAttribute("precisionNum", Constants.precisionNum);
		return "/grade/graduateInfo";
	}
	
	//查看班级论文详情
	@RequestMapping(value="/gradeThesisInfo.do")
	public String  gradeThesisInfo(HttpServletRequest request,Integer currentPageFlag,HttpServletResponse response,Integer gradeId, Integer gradeWorkId,String gradeName,String onlySee,Model model){
		Adks_user user = (Adks_user)request.getSession().getAttribute("user");
		Map map = new HashMap();
		map.put("gradeWorkId", gradeWorkId);
		map.put("userId", user.getUserId());
		Adks_grade_work sw = gradeApi.gradeThesisInfo(map);
		String content = "";
		if(sw!=null && sw.getWorkAnswer() != null){
			content = new String(sw.getWorkAnswer());
		}
		
		model.addAttribute("schoolWork", sw);
		if(sw!=null && ComUtil.isNotNullOrEmpty(sw.getFilePath()) && !"".equals(sw.getFilePath())){
			model.addAttribute("showPath", sw.getFilePath());
		}
		/*if(ComUtil.isNotNullOrEmpty(sw.getSt_path())){
			model.addAttribute("showStPath", FtpUtils.getShowFileName(sw.getSt_path()));
		}*/

		// 获取浏览器版本信息
		model.addAttribute("fullVersion", ComUtil.getIeVersion(request.getHeader("user-agent")));
		if(sw!=null && ((new Date()).after(sw.getEndDate())  || (sw.getWorkScore() !=null && sw.getWorkScore()>=0))){
			onlySee="onlySee";
		}
		if(ComUtil.isNotNullOrEmpty(onlySee)&&!onlySee.equals("undefined")){
			model.addAttribute("onlySee", onlySee);
		}
		Date nowDate = new Date();
		model.addAttribute("content", content);
		model.addAttribute("currentPageFlag", currentPageFlag);//当前页码
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("gradeName", gradeName);
		model.addAttribute("nowDate",nowDate.getTime());
		if(sw!=null){
			model.addAttribute("endDate",sw.getEndDate().getTime());
			model.addAttribute("startDate",sw.getStartDate().getTime());
		}
		model.addAttribute("precisionNum", Constants.precisionNum);
		return  "/grade/gradeThesisInfo";
	}
	
	//保存班级论文
	@RequestMapping(value="/gradeThesisSave.do")
	@ResponseBody
	public void gradeThesisSave(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "fileSchoolWork", required = false) MultipartFile fileSchoolWork,Adks_grade_work sw){
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			
			if (fileSchoolWork != null) {
				String scloolWorkPath = PropertiesFactory.getProperty("ossConfig.properties", "oss.Download_Path");
				String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
				if (StringUtils.isNotEmpty(fileSchoolWork.getOriginalFilename())) {
					// 将MultipartFile转换成文件流
					//CommonsMultipartFile cf = (CommonsMultipartFile) fileSchoolWork;
					//DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					//File file = fi.getStoreLocation();
					InputStream is=fileSchoolWork.getInputStream();
					// 获取文件类型
					String fileType = fileSchoolWork.getOriginalFilename();
					fileType = fileType.substring(fileType.lastIndexOf(".") + 1, fileType.length());

					if (sw.getSubmitFilePath() == null || "".equals(sw.getSubmitFilePath())) {
						// 上传图片
						String new_Path = OSSUploadUtil.uploadFileNewName(is, fileType, scloolWorkPath);
						String[] paths = new_Path.split("/");
						String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
						sw.setSubmitFilePath(code);
					} else if (sw.getSubmitFilePath() != null && !"".equals(sw.getSubmitFilePath())) {
						String new_Path = OSSUploadUtil.updateFile(is, fileType, ossResource + sw.getSubmitFilePath());
						String[] paths = new_Path.split("/");
						String code = "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
						sw.setSubmitFilePath(code);
					}
				} 
			}
				
			Adks_user user=(Adks_user)request.getSession().getAttribute("user");
			//保存adks_grade_work_reply
			Adks_grade_work_reply gwr=new Adks_grade_work_reply();
			if(sw.getGradeWorkReplyId()!=null && sw.getGradeWorkReplyId()!=0){
				gwr.setGradeWorkReplyId(sw.getGradeWorkReplyId());
			}else{
				gwr.setIsCorrent(2);
			}
			gwr.setSubmitFilePath(sw.getSubmitFilePath());
			gwr.setWorkAnswer(sw.getWorkAnswer());
			gwr.setSubmitDate(new Date());
			gwr.setStudentId(user.getUserId());
			gwr.setStudentName(user.getUserName());
			gwr.setWorkId(sw.getGradeWorkId());
			gwr.setWorkTitle(sw.getWorkTitle());
			gradeApi.saveGradeWorkStudent(gwr);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.addHeader("content-type", "text/html");
		pw.write("succ");
		pw.flush();
		pw.close();
	}
	
}
