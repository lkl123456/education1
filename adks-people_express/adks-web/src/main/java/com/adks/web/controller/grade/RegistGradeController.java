package com.adks.web.controller.grade;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.commons.util.DateTimeUtil;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.course.Adks_course_user;
import com.adks.dubbo.api.data.exam.Adks_exam_score;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_course;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.api.data.grade.Adks_grade_work_reply;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.api.interfaces.web.course.CourseApi;
import com.adks.dubbo.api.interfaces.web.course.CourseUserApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeCourseApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeExamApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeUserApi;
import com.adks.dubbo.api.interfaces.web.grade.GradeWorkApi;
import com.adks.dubbo.api.interfaces.web.user.UserApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping({ "/registGrade" })
public class RegistGradeController {

	@Autowired
	private GradeApi gradeWebApi;

	@Autowired
	private CourseApi courseWebApi;
	@Autowired
	private GradeUserApi gradeUserWebApi;
	@Autowired
	private GradeCourseApi gradeCourseWebApi;
	@Autowired
	private CourseUserApi courseUserApi;
	@Autowired
	private GradeWorkApi gradeWorkApi;
	@Autowired
	private GradeExamApi gradeExamApi;
	@Autowired
	private UserApi userApi;

	/**
	 * 转到 班级列表List 页面
	 * 
	 * @Description:
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/toRegisterGradeList.do")
	public String toRegisterGradeList(Model model, Integer orgId) {
		model.addAttribute("mainFlag", "toRegisterGradeList");
		model.addAttribute("orgId", orgId);
		return "/index/mainIndex";
	}

	@RequestMapping(value = "/registerGradeList.do")
	public String registerGradeList(Page<List<Adks_grade>> page, HttpServletRequest request,
			HttpServletResponse response, Integer orgId, Model model) {
		// 得到登录用户的user
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");

		if (page == null) {
			page = new Page<List<Adks_grade>>();
		}
		Map map = new HashMap<>();
		if(user!=null){
			map.put("userId", user.getUserId());
		}
		page.setMap(map);
		page = gradeWebApi.getGradeListWeb(page);
		Map gradeCourseMap = new HashMap<Integer, List<Adks_grade_course>>();
		if (page.getRows() != null && page.getRows().size() > 0) {
			List<Adks_grade> grades = page.getRows();
			List<Adks_grade> gList = null;
			// 根据userId判断用户所属的班级
			if (user != null) {
				gList = gradeWebApi.registerGradeListByUserid(user.getUserId());
			}

			for (Adks_grade grade : grades) {
				// 根据登录用户的班级判断属性能否进班
				if (gList != null) {
					for (Adks_grade grade2 : gList) {
						if (grade.getGradeId() == grade2.getGradeId()) {
							Date nowdate = new Date();
							boolean flag = grade.getEndDate().before(nowdate);
							if (flag) {
								grade.setIsDatePast(1);
								break;
							} else {
								grade.setIsDatePast(2);
								break;
							}
						} else {
							grade.setIsDatePast(3);
						}
					}
				} else {
					grade.setIsDatePast(3);
				}

				Map map3 = new HashMap();
				map3.put("gradeId", grade.getGradeId());
				List<Adks_grade_course> gcList = gradeCourseWebApi.getGradeCourseByGradeId(map3);
				gradeCourseMap.put(grade.getGradeId(), gcList);
			}
		}
		model.addAttribute("gradeCourseMap", gradeCourseMap);
		model.addAttribute("page", page);
		model.addAttribute("orgId", orgId);
		return "/grade/registerGradeList";
	}

	// 专题课程目录下载
	@RequestMapping(value = "/downloadGradeInfo.do")
	public void downloadGradeInfo(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			Integer gradeId, String gradeName) {
		Map map = new HashMap();
		map.put("gradeId", gradeId);

		// 专题课程
		List<Adks_grade_course> gcList = gradeCourseWebApi.getGradeCourseByGradeId(map);
		HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件

		// Sheet样式
		// HSSFCellStyle sheetStyle = workbook.createCellStyle();
		// 背景色的设定
		// sheetStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		// 前景色的设定
		// sheetStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// 填充模式
		// sheetStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
		// 设置列的样式
		// for (int i = 0; i <= 14; i++) {
		// sheet.setDefaultColumnStyle((short) i, sheetStyle);
		// }
		// 设置字体
		HSSFFont headfont = workbook.createFont();
		headfont.setFontName("黑体");
		headfont.setFontHeightInPoints((short) 22);// 字体大小
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		// 另一个样式
		HSSFCellStyle headstyle = workbook.createCellStyle();
		headstyle.setFont(headfont);
		headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		headstyle.setLocked(true);
		headstyle.setWrapText(true);// 自动换行
		// 另一个字体样式
		HSSFFont columnHeadFont = workbook.createFont();
		columnHeadFont.setFontName("宋体");
		columnHeadFont.setFontHeightInPoints((short) 14);
		columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 列头的样式
		HSSFCellStyle columnHeadStyle = workbook.createCellStyle();
		columnHeadStyle.setFont(columnHeadFont);
		columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		columnHeadStyle.setLocked(true);
		columnHeadStyle.setWrapText(true);
		columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色
		columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
		columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
		columnHeadStyle.setBorderRight((short) 1);// 边框的大小
		columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
		// 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）
		columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index);

		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11);
		// 普通单元格样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中
		style.setWrapText(true);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft((short) 1);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight((short) 1);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
		style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
		// 另一个样式
		HSSFCellStyle centerstyle = workbook.createCellStyle();
		centerstyle.setFont(font);
		centerstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		centerstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		centerstyle.setWrapText(true);
		centerstyle.setLeftBorderColor(HSSFColor.BLACK.index);
		centerstyle.setBorderLeft((short) 1);
		centerstyle.setRightBorderColor(HSSFColor.BLACK.index);
		centerstyle.setBorderRight((short) 1);
		centerstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		centerstyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
		centerstyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
		try {

			// 生成课程分类课程列表页
			Adks_course course = null;
			HSSFSheet sheetCourse = null;
			HSSFRow rowCourse = null;
			HSSFCell cellCourse0 = null;
			HSSFCell cellCourse1 = null;
			HSSFCell cellCourse2 = null;
			HSSFCell cellCourse3 = null;
			HSSFCell cellCourse4 = null;
			HSSFCell cellCourse5 = null;
			HSSFCell cellCourse6 = null;
			HSSFCell cellCourse7 = null;

			sheetCourse = workbook.createSheet(gradeName);// 创建一个Excel的Sheet
			sheetCourse.createFreezePane(0, 1);// 冻结
			sheetCourse.setColumnWidth(0, 2000);
			sheetCourse.setColumnWidth(1, 4000);
			sheetCourse.setColumnWidth(2, 8000);
			sheetCourse.setColumnWidth(3, 4000);
			sheetCourse.setColumnWidth(4, 16000);
			sheetCourse.setColumnWidth(5, 4000);
			sheetCourse.setColumnWidth(6, 4000);
			sheetCourse.setColumnWidth(7, 4000);
			rowCourse = sheetCourse.createRow(0);
			cellCourse0 = rowCourse.createCell(0);
			cellCourse0.setCellValue(new HSSFRichTextString("序号"));
			cellCourse0.setCellStyle(columnHeadStyle);
			cellCourse1 = rowCourse.createCell(1);
			cellCourse1.setCellValue(new HSSFRichTextString("所属分类"));
			cellCourse1.setCellStyle(columnHeadStyle);
			cellCourse2 = rowCourse.createCell(2);
			cellCourse2.setCellValue(new HSSFRichTextString("课件名称"));
			cellCourse2.setCellStyle(columnHeadStyle);
			cellCourse3 = rowCourse.createCell(3);
			cellCourse3.setCellValue(new HSSFRichTextString("演讲人"));
			cellCourse3.setCellStyle(columnHeadStyle);
			cellCourse4 = rowCourse.createCell(4);
			cellCourse4.setCellValue(new HSSFRichTextString("课程简介"));
			cellCourse4.setCellStyle(columnHeadStyle);
			cellCourse5 = rowCourse.createCell(5);
			cellCourse5.setCellValue(new HSSFRichTextString("课程类型"));
			cellCourse5.setCellStyle(columnHeadStyle);
			cellCourse6 = rowCourse.createCell(6);
			cellCourse6.setCellValue(new HSSFRichTextString("课程时长"));
			cellCourse6.setCellStyle(columnHeadStyle);
			cellCourse7 = rowCourse.createCell(7);
			cellCourse7.setCellValue(new HSSFRichTextString("更新日期"));
			cellCourse7.setCellStyle(columnHeadStyle);

			HSSFRow row = null;
			if (null != gcList && gcList.size() > 0) {
				String cids = "";
				for (int i = 0; i < gcList.size(); i++) {
					if (i == 0) {
						cids += gcList.get(i).getCourseId();
					} else {
						cids += "," + gcList.get(i).getCourseId();
					}
				}
				map.put("courseIds", cids);

				List<Adks_course> clist = courseWebApi.getCourseListByIds(map);
				for (int x = 0; x < clist.size(); x++) {
					course = clist.get(x);
					row = sheetCourse.createRow(x + 1);
					cellCourse0 = row.createCell(0);
					cellCourse0.setCellValue(new HSSFRichTextString(x + 1 + ""));
					cellCourse0.setCellStyle(style);
					cellCourse1 = row.createCell(1);
					cellCourse1.setCellValue(new HSSFRichTextString(course.getCourseSortName()));
					cellCourse1.setCellStyle(style);
					cellCourse2 = row.createCell(2);
					cellCourse2.setCellValue(new HSSFRichTextString(course.getCourseName()));
					cellCourse2.setCellStyle(style);
					cellCourse3 = row.createCell(3);
					cellCourse3.setCellValue(new HSSFRichTextString(course.getAuthorName()));
					cellCourse3.setCellStyle(style);
					cellCourse4 = row.createCell(4);
					cellCourse4.setCellValue(new HSSFRichTextString(course.getCourseDes()));
					cellCourse4.setCellStyle(style);
					cellCourse5 = row.createCell(5);
					cellCourse5
							.setCellValue(new HSSFRichTextString(course.getCourseType() == 170 ? "SCORM课件" : "普通视频"));
					cellCourse5.setCellStyle(style);
					cellCourse6 = row.createCell(6);
					cellCourse6.setCellValue(new HSSFRichTextString(course.getCourseTimeLong()));
					cellCourse6.setCellStyle(style);
					cellCourse7 = row.createCell(7);
					cellCourse7.setCellValue(
							new HSSFRichTextString(DateTimeUtil.dateToString(course.getCreateTime(), "yyyy-MM-dd")));
					cellCourse7.setCellStyle(style);

				}
			}
			String filename = gradeName + ".xls";// 设置下载时客户端Excel的名称
			filename = this.encodeFilename(filename, request);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置下载文件中文件的名称
	 * 
	 * @param filename
	 * @param request
	 * @return
	 */
	public static String encodeFilename(String filename, HttpServletRequest request) {
		/**
		 * 获取客户端浏览器和操作系统信息 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE
		 * 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
		 * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1;
		 * zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
		 */
		String agent = request.getHeader("USER-AGENT");
		try {
			if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
				String newFileName = URLEncoder.encode(filename, "UTF-8");
				newFileName = StringUtils.replace(newFileName, "+", "%20");
				if (newFileName.length() > 150) {
					newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
					newFileName = StringUtils.replace(newFileName, " ", "%20");
				}
				return newFileName;
			}
			if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
				return MimeUtility.encodeText(filename, "UTF-8", "B");

			return filename;
		} catch (Exception ex) {
			return filename;
		}
	}

	/* 查看专题培训班级的档案 */
	@RequestMapping(value = "/checkGradeUserDetail.do")
	public String checkGradeUserDetail(Integer gradeId, HttpServletRequest request, Model model) {
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");
		user = userApi.getUserInfoById(user.getUserId());
		Adks_grade grade = gradeWebApi.getGradeById(gradeId);
		Adks_grade_user gradeUser = gradeUserWebApi.getGradeUserByCon(grade.getGradeId(), user.getUserId());
		if (gradeUser.getRequiredPeriod() != null) {
			Double requiredPeriod = gradeUser.getRequiredPeriod();
			Double rp = Double.valueOf(requiredPeriod) / Double.valueOf(2700);
			DecimalFormat df = new DecimalFormat("0.0");// 格式化小数，不足的补0
			df.setRoundingMode(RoundingMode.FLOOR); // 取消四舍五入
			String credit = df.format(rp);// 返回的是String类型的
			gradeUser.setRequiredPeriod(Double.valueOf(credit));
		}
		if (gradeUser.getOptionalPeriod() != null) {
			Double optionalPeriod = gradeUser.getOptionalPeriod();
			Double op = Double.valueOf(optionalPeriod) / Double.valueOf(2700);
			DecimalFormat df = new DecimalFormat("0.0");// 格式化小数，不足的补0
			df.setRoundingMode(RoundingMode.FLOOR); // 取消四舍五入
			String credit = df.format(op);// 返回的是String类型的
			gradeUser.setOptionalPeriod(Double.valueOf(credit));
		}

		Map map = new HashMap();
		map.put("gradeId", gradeId);
		map.put("userId", user.getUserId());
		map.put("gcState", 1);
		// 获取必修课程列表
		List<Adks_course_user> requriedCourseUserList = courseUserApi.getCourseUserByCon(map);
		map.put("gcState", 2);
		// 获取选修课程列表
		List<Adks_course_user> optionalCourseUserList = courseUserApi.getCourseUserByCon(map);
		/*
		 * //获取讨论列表 List<DiscussReplyScore> discussReplyScoreList =
		 * gradeDiscussDao.getUserDiscussReplyScoreList(map);
		 */
		// 获取论文列表
		List<Adks_grade_work_reply> studentWorkList = gradeWorkApi.getGradeWorkReplyByCon(map);
		// 获取考试列表
		List<Adks_exam_score> examScoreList = gradeExamApi.getExamScoreByCon(map);
		String ationalityName = userApi.getNationalityName(user.getUserNationality());
		model.addAttribute("user", user);
		model.addAttribute("grade", grade);
		model.addAttribute("gradeUser", gradeUser);
		model.addAttribute("ationalityName", ationalityName);
		model.addAttribute("requriedCourseUserList", requriedCourseUserList);
		model.addAttribute("optionalCourseUserList", optionalCourseUserList);
		// model.addAttribute("discussReplyScoreList", discussReplyScoreList);
		model.addAttribute("studentWorkList", studentWorkList);
		model.addAttribute("examScoreList", examScoreList);

		return "/grade/gradeUserDetail";
	}

	@RequestMapping(value = "/toGradeInfo", method = RequestMethod.POST)
	public void toGradeInfo(int gradeId, HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException {
		PrintWriter pWriter = response.getWriter();
		String resultStr = "";
		// 得到登录用户的user
		Adks_user user = (Adks_user) request.getSession().getAttribute("user");

		Adks_grade grade = gradeWebApi.getGradeById(gradeId);

		List<Adks_grade> gList = null;
		// 根据userId判断用户所属的班级
		if (user != null) {
			gList = gradeWebApi.registerGradeListByUserid(user.getUserId());
		}

		// 根据登录用户的班级判断属性能否进班
		if (gList != null) {
			for (Adks_grade grade2 : gList) {
				if (grade.getGradeId() == grade2.getGradeId()) {
					Date nowdate = new Date();
					boolean flag = grade.getEndDate().before(nowdate);
					if (flag) {
						resultStr = "isGetInfo1";// 该班级已经结束
						break;
					} else {
						resultStr = "isGetInfo2";// 可以查看班级详细
						break;
					}
				} else {
					resultStr = "isGetInfo3";// 未加入班级
				}
			}
		} else {
			grade.setIsDatePast(3);
		}

		pWriter.write(resultStr);
		pWriter.flush();
		pWriter.close();
	}
}
