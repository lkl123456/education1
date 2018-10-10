package com.adks.admin.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import com.adks.commons.util.DateTimeUtil;
import com.adks.dubbo.api.data.course.Adks_course;
import com.adks.dubbo.api.data.question.Adks_question;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.commons.ApiResponse;

/**
 * apache poi 操作excel 方法
 * 
 * @author ckl
 *
 */
public class ExcelUtil {
	/**
	 * excle 读取试题集合，兼容2003-2007-2010excel
	 * 
	 * @param type
	 *            :目标实体类型
	 * @param path
	 *            ：excel 文件
	 * @return
	 */
	public static ApiResponse<List<Adks_question>> readExcelToQuestion(InputStream inputStream) {

		try {
			Workbook workbook = WorkbookFactory.create(inputStream);
			if (workbook == null) {
				return ApiResponse.fail(500, "Excel workbook is null...");
			}
			Adks_question question = null;
			List<Adks_question> questions = new ArrayList<>();
			System.out.println("sheet个数:" + workbook.getNumberOfSheets());
			// 循环excel sheet
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workbook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				System.out.println("第" + (numSheet + 1) + "个sheet的行数：" + sheet.getLastRowNum());
				// 循环row，从第四行开始,前两行是介绍,第三行是表头
				for (int rowNum = 3; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					if (row != null) {
						question = new Adks_question();
						// 试题类型
						Cell cell_questionType = row.getCell(0);
						// 试题分值
						Cell cell_questionValue = row.getCell(1);
						// 试题名称
						Cell cell_questionName = row.getCell(2);
						// 试题选项
						Cell cell_questionOptions = row.getCell(3);
						// 试题答案
						Cell cell_questionAnwsers = row.getCell(4);
						String questionTypeStr = cell_questionType.getStringCellValue();
						if (StringUtils.isNotEmpty(questionTypeStr)) {
							switch (questionTypeStr) {
							case "单选题":
								question.setQuestionType(1);
								break;
							case "多选题":
								question.setQuestionType(2);
								break;
							case "判断题":
								question.setQuestionType(3);
								break;
							case "填空题":
								question.setQuestionType(4);
								break;
							case "问答题":
								question.setQuestionType(5);
								break;
							}
						}
						double questionValueStr = cell_questionValue.getNumericCellValue();
						int qv = (int) questionValueStr;
						question.setQuestionValue(qv);

						String questionNameStr = cell_questionName.getStringCellValue();
						if (StringUtils.isNotEmpty(questionNameStr)) {
							question.setQuestionName(questionNameStr);
						}

						String questionOptionsStr = cell_questionOptions.getStringCellValue();
						if (StringUtils.isNotEmpty(questionOptionsStr)) {
							String[] options = questionOptionsStr.split(";");
							if (options != null && options.length > 0) {
								if (StringUtils.isNotEmpty(options[0])) {
									question.setOptionA(options[0]);
								}
								if (options.length > 2 && StringUtils.isNotEmpty(options[1])) {
									question.setOptionB(options[1]);
								}
								if (options.length > 3 && StringUtils.isNotEmpty(options[2])) {
									question.setOptionC(options[2]);
								}
								if (options.length > 4 && StringUtils.isNotEmpty(options[3])) {
									question.setOptionD(options[3]);
								}
								if (options.length > 5 && StringUtils.isNotEmpty(options[4])) {
									question.setOptionE(options[4]);
								}
								if (options.length > 6 && StringUtils.isNotEmpty(options[5])) {
									question.setOptionF(options[5]);
								}
								if (options.length > 7 && StringUtils.isNotEmpty(options[6])) {
									question.setOptionG(options[6]);
								}
								if (options.length > 8 && StringUtils.isNotEmpty(options[7])) {
									question.setOptionH(options[7]);
								}
							}
						}

						String questionAnswers = cell_questionAnwsers.getStringCellValue();
						if (StringUtils.isNotEmpty(questionAnswers)) {
							question.setAnwsers(questionAnswers);
						}

						questions.add(question);
					}
				}

			}
			return ApiResponse.success("Excel read success!", questions);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ApiResponse.fail(500, "Excel异常，请检查");
	}

	public static void downloadExcelQuestion(List<Adks_question> questions, HttpServletResponse response,
			String fileName) throws Exception {
		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell(0);
		// 设置单元格内容
		cell.setCellValue("考试系统试题导出列表");
		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		// 在sheet里创建第二行
		HSSFRow row2 = sheet.createRow(1);
		// 创建单元格并设置单元格内容
		row2.createCell(0).setCellValue("题型");
		row2.createCell(1).setCellValue("分数");
		row2.createCell(2).setCellValue("题目内容");
		row2.createCell(3).setCellValue("可选项");
		row2.createCell(4).setCellValue("答案");

		if (questions != null) {
			int j = 2;
			for (Adks_question q : questions) {
				HSSFRow row3 = sheet.createRow(j);
				if (q.getQuestionType() != 0) {
					switch (q.getQuestionType()) {
					case 1:
						row3.createCell(0).setCellValue("单选题");
						break;
					case 2:
						row3.createCell(0).setCellValue("多选题");
						break;
					case 3:
						row3.createCell(0).setCellValue("判断题");
						break;
					case 4:
						row3.createCell(0).setCellValue("填空题");
						break;
					case 5:
						row3.createCell(0).setCellValue("问答题");
						break;
					default:
						break;
					}
				}
				row3.createCell(1).setCellValue(q.getQuestionValue());
				row3.createCell(2).setCellValue(q.getQuestionName());
				String options = "";
				if (StringUtils.isNotEmpty(q.getOptionA())) {
					options += q.getOptionA() + ";";
				}
				if (StringUtils.isNotEmpty(q.getOptionB())) {
					options += q.getOptionB() + ";";
				}
				if (StringUtils.isNotEmpty(q.getOptionC())) {
					options += q.getOptionC() + ";";
				}
				if (StringUtils.isNotEmpty(q.getOptionD())) {
					options += q.getOptionD() + ";";
				}
				if (StringUtils.isNotEmpty(q.getOptionE())) {
					options += q.getOptionE() + ";";
				}
				if (StringUtils.isNotEmpty(q.getOptionF())) {
					options += q.getOptionF() + ";";
				}
				if (StringUtils.isNotEmpty(q.getOptionG())) {
					options += q.getOptionG() + ";";
				}
				if (StringUtils.isNotEmpty(q.getOptionH())) {
					options += q.getOptionH() + ";";
				}
				row3.createCell(3).setCellValue(options);
				row3.createCell(4).setCellValue(q.getAnwsers());
				j++;
			}
		}
		OutputStream out = response.getOutputStream();
		response.setHeader("Content-disposition",
				"attachment;filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
		response.setContentType("application/msexcel;charset=UTF-8");
		wb.write(out);
		out.flush();
		out.close();
		System.out.println("dao chu cheng gong ~");
	}
	
	/**
	 * 
	 * @Title downloadExcelCourseStatistics
	 * @Description：下载课程学习统计
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param course
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public static void downloadExcelCourseStatistics(List<Adks_course> courseList, HttpServletResponse response,
			String fileName) throws Exception {
		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell(0);
		// 设置单元格内容
		cell.setCellValue("课程学习统计");
		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		//设置行高
		row1.setHeight((short)1000);
		//设置列宽
		sheet.setColumnWidth(0,13000);
		sheet.setColumnWidth(1,7000);
		sheet.setColumnWidth(2,3000);
		sheet.setColumnWidth(3,3000);
		// 设置字体   
		HSSFFont font = wb.createFont();   
		//font.setColor(HSSFFont.COLOR_RED);   //颜色  
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //加粗   
		font.setFontHeightInPoints((short)20);     //字体大小
		// 设置样式   
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);   //左右居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中 
		cell.setCellStyle(cellStyle);
		// 在sheet里创建第二行
		HSSFRow row2 = sheet.createRow(1);
		//设置行高
		row2.setHeight((short)500);
		// 创建单元格并设置单元格内容
		row2.createCell(0).setCellValue("课程名称");
		row2.createCell(1).setCellValue("课程分类");
		row2.createCell(2).setCellValue("点击量");
		row2.createCell(3).setCellValue("收藏量");
		if(courseList!=null&&courseList.size()>0){
			int j = 2;
			for (Adks_course course : courseList) {
				HSSFRow row3 = sheet.createRow(j);
				row3.createCell(0).setCellValue(course.getCourseName());
				if(course.getCourseSortName()!=null){
					row3.createCell(1).setCellValue(course.getCourseSortName());
				}
				if(course.getCourseClickNum()!=null){
					row3.createCell(2).setCellValue(course.getCourseClickNum());
				}
				if(course.getCourseCollectNum()!=null){
					row3.createCell(3).setCellValue(course.getCourseCollectNum());
				}
				j++;
			}
		}
		OutputStream out = response.getOutputStream();
		response.setHeader("Content-disposition","attachment;filename="+URLEncoder.encode(fileName + ".xls", "UTF-8"));
		response.setContentType("application/msexcel;charset=UTF-8");
		wb.write(out);
		out.flush();
		out.close();
		System.out.println("export CourseStatistics success!!!");
	}

	/**
	 * 
	 * @Title downloadExcelGradeStudyStatistics
	 * @Description：下载班级学习统计
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param gradeStudyList
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public static void downloadExcelGradeStudyStatistics(List<Map<String, Object>> gradeStudyList, HttpServletResponse response,
			String fileName) throws Exception {
		Map<String, Object> grade=gradeStudyList.get(0);
		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet("【"+grade.get("gradeName")+"】"+"学员学习统计");
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell(0);
		if(gradeStudyList!=null&&gradeStudyList.size()>0){
			if(grade!=null){
				if(grade.get("gradeName")!=null){
					Date date=new Date();
					String time=DateTimeUtil.dateToString(date,"yyyy-MM-dd HH:mm");
					// 设置单元格内容
					cell.setCellValue("【"+grade.get("gradeName")+"】"+"学员学习统计"+"-("+"导出时间:"+time+")");
				}else{
					// 设置单元格内容
					cell.setCellValue("学习统计");
				}
			}else{
				// 设置单元格内容
				cell.setCellValue("学习统计");
			}
		}else{
			// 设置单元格内容
			cell.setCellValue("学习统计");
		}
		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
		//设置行高
		row1.setHeight((short)1000);
		//设置列宽
		sheet.setColumnWidth(0,5000);
		sheet.setColumnWidth(1,5000);
		sheet.setColumnWidth(2,5000);
		sheet.setColumnWidth(3,5000);
		sheet.setColumnWidth(4,5000);
		sheet.setColumnWidth(5,5000);
		sheet.setColumnWidth(6,5000);
		sheet.setColumnWidth(7,5000);
		sheet.setColumnWidth(8,5000);
		sheet.setColumnWidth(9,5000);
		sheet.setColumnWidth(10,5000);
		sheet.setColumnWidth(11,10000);
		// 设置字体   
		HSSFFont font = wb.createFont();   
		//font.setColor(HSSFFont.COLOR_RED);   //颜色  
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //加粗   
		font.setFontHeightInPoints((short)20);     //字体大小
		// 设置样式   
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);   //左右居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中 
		cell.setCellStyle(cellStyle);
		// 在sheet里创建第二行
		HSSFRow row2 = sheet.createRow(1);
		// 创建单元格并设置单元格内容
		row2.createCell(0).setCellValue("用户名");
		row2.createCell(1).setCellValue("真实姓名");
		row2.createCell(2).setCellValue("总学时");
		row2.createCell(3).setCellValue("必修学时");
		row2.createCell(4).setCellValue("选修学时");
		row2.createCell(5).setCellValue("考试成绩");
		row2.createCell(6).setCellValue("作业成绩");
		row2.createCell(7).setCellValue("是否毕业");
		row2.createCell(8).setCellValue("性别");
		row2.createCell(9).setCellValue("手机号码");
		row2.createCell(10).setCellValue("所在机构");
		row2.createCell(11).setCellValue("所在班级");
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
		df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
		if(gradeStudyList!=null&&gradeStudyList.size()>0){
			int j=2;
			for (Map<String, Object> gradeUser: gradeStudyList) {
				HSSFRow row3 = sheet.createRow(j);
				row3.createCell(0).setCellValue((String)gradeUser.get("userName"));
				row3.createCell(1).setCellValue((String)gradeUser.get("userRealName"));
				if(gradeUser.get("period")!=null){
					Double period=(Double)gradeUser.get("period")/Double.valueOf(2700);
					String pd = df.format(period);//返回的是String类型的
					row3.createCell(2).setCellValue(pd);
				}
				if(gradeUser.get("requiredPeriod")!=null){
					Double requiredPeriod=(Double)gradeUser.get("requiredPeriod")/Double.valueOf(2700);
					String rpd = df.format(requiredPeriod);//返回的是String类型的
					row3.createCell(3).setCellValue(rpd);
				}
				if(gradeUser.get("optionalPeriod")!=null){
					Double optionalPeriod=(Double)gradeUser.get("optionalPeriod")/Double.valueOf(2700);
					String opd = df.format(optionalPeriod);//返回的是String类型的
					row3.createCell(4).setCellValue(opd);
				}
				if(gradeUser.get("examScore")!=null){
					row3.createCell(5).setCellValue(((BigDecimal)gradeUser.get("examScore")).intValue());
				}
				if(gradeUser.get("workScore")!=null){
					row3.createCell(6).setCellValue(((BigDecimal)gradeUser.get("workScore")).intValue());
				}
				if(gradeUser.get("isGraduate")!=null){
					if((Integer)gradeUser.get("isGraduate")==1){
						row3.createCell(7).setCellValue("已毕业");
					}else{
						row3.createCell(7).setCellValue("未毕业");
					}
				}
				if(gradeUser.get("userSex")!=null){
					if((Integer)gradeUser.get("userSex")==2){
						row3.createCell(8).setCellValue("女");
					}else{
						row3.createCell(8).setCellValue("男");
					}
				}
				if(gradeUser.get("userPhone")!=null){
					row3.createCell(9).setCellValue((String)gradeUser.get("userPhone"));
				}
				if(gradeUser.get("orgName")!=null){
					row3.createCell(10).setCellValue((String)gradeUser.get("orgName"));
				}
				if(gradeUser.get("gradeName")!=null){
					row3.createCell(11).setCellValue((String)gradeUser.get("gradeName"));
				}
				
				j++;
			}
		}
		OutputStream out = response.getOutputStream();
		response.setHeader("Content-disposition","attachment;filename="+URLEncoder.encode(fileName + ".xls", "UTF-8"));
		response.setContentType("application/msexcel;charset=UTF-8");
		wb.write(out);
		out.flush();
		out.close();
		System.out.println("export GradeStudyStatistics success!!!");
	}
	
	/**
	 * 
	 * @Title downloadExcelGradePerformance
	 * @Description
	 * @author xrl
	 * @Date 2017年6月20日
	 * @param gradeStudyList
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public static void downloadExcelGradePerformance(List<Map<String, Object>> gradeStudyList, HttpServletResponse response,
			String fileName) throws Exception {
		Map<String, Object> grade=gradeStudyList.get(0);
		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet("【"+grade.get("gradeName")+"】"+"学员成绩统计");
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell(0);
		if(gradeStudyList!=null&&gradeStudyList.size()>0){
			if(grade!=null){
				if(grade.get("gradeName")!=null){
					Date date=new Date();
					String time=DateTimeUtil.dateToString(date,"yyyy-MM-dd HH:mm");
					// 设置单元格内容
					cell.setCellValue("【"+grade.get("gradeName")+"】"+"学员成绩统计"+"-("+"导出时间:"+time+")");
				}else{
					// 设置单元格内容
					cell.setCellValue("学员成绩统计");
				}
			}else{
				// 设置单元格内容
				cell.setCellValue("学员成绩统计");
			}
		}else{
			// 设置单元格内容
			cell.setCellValue("学员成绩统计");
		}
		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		//设置行高
		row1.setHeight((short)1000);
		//设置列宽
		sheet.setColumnWidth(0,5000);
		sheet.setColumnWidth(1,5000);
		sheet.setColumnWidth(2,5000);
		sheet.setColumnWidth(3,5000);
		sheet.setColumnWidth(4,5000);
		sheet.setColumnWidth(5,5000);
		sheet.setColumnWidth(6,5000);
		sheet.setColumnWidth(7,5000);
		sheet.setColumnWidth(8,5000);
		sheet.setColumnWidth(9,10000);
		// 设置字体   
		HSSFFont font = wb.createFont();   
		//font.setColor(HSSFFont.COLOR_RED);   //颜色  
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //加粗   
		font.setFontHeightInPoints((short)20);     //字体大小
		// 设置样式   
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);   //左右居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中 
		cell.setCellStyle(cellStyle);
		// 在sheet里创建第二行
		HSSFRow row2 = sheet.createRow(1);
		// 创建单元格并设置单元格内容
		row2.createCell(0).setCellValue("用户名");
		row2.createCell(1).setCellValue("真实姓名");
		row2.createCell(2).setCellValue("总学时");
		row2.createCell(3).setCellValue("必修学时");
		row2.createCell(4).setCellValue("选修学时");
		row2.createCell(5).setCellValue("是否毕业");
		row2.createCell(6).setCellValue("性别");
		row2.createCell(7).setCellValue("手机号码");
		row2.createCell(8).setCellValue("所在机构");
		row2.createCell(9).setCellValue("所在班级");
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
		df.setRoundingMode(RoundingMode.FLOOR);  //取消四舍五入
		if(gradeStudyList!=null&&gradeStudyList.size()>0){
			int j=2;
			for (Map<String, Object> gradeUser: gradeStudyList) {
				HSSFRow row3 = sheet.createRow(j);
				row3.createCell(0).setCellValue((String)gradeUser.get("userName"));
				row3.createCell(1).setCellValue((String)gradeUser.get("userRealName"));
				if(gradeUser.get("period")!=null){
					Double period=(Double)gradeUser.get("period")/Double.valueOf(2700);
					String pd = df.format(period);//返回的是String类型的
					row3.createCell(2).setCellValue(pd);
				}
				if(gradeUser.get("requiredPeriod")!=null){
					Double requiredPeriod=(Double)gradeUser.get("requiredPeriod")/Double.valueOf(2700);
					String rpd = df.format(requiredPeriod);//返回的是String类型的
					row3.createCell(3).setCellValue(rpd);
				}
				if(gradeUser.get("optionalPeriod")!=null){
					Double optionalPeriod=(Double)gradeUser.get("optionalPeriod")/Double.valueOf(2700);
					String opd = df.format(optionalPeriod);//返回的是String类型的
					row3.createCell(4).setCellValue(opd);
				}
				if(gradeUser.get("isGraduate")!=null){
					if((Integer)gradeUser.get("isGraduate")==1){
						row3.createCell(5).setCellValue("已毕业");
					}else{
						row3.createCell(5).setCellValue("未毕业");
					}
				}
				if(gradeUser.get("userSex")!=null){
					if((Integer)gradeUser.get("userSex")==2){
						row3.createCell(6).setCellValue("女");
					}else{
						row3.createCell(6).setCellValue("男");
					}
				}
				if(gradeUser.get("userPhone")!=null){
					row3.createCell(7).setCellValue((String)gradeUser.get("userPhone"));
				}
				if(gradeUser.get("orgName")!=null){
					row3.createCell(8).setCellValue((String)gradeUser.get("orgName"));
				}
				if(gradeUser.get("gradeName")!=null){
					row3.createCell(9).setCellValue((String)gradeUser.get("gradeName"));
				}
				
				j++;
			}
		}
		OutputStream out = response.getOutputStream();
		response.setHeader("Content-disposition","attachment;filename="+URLEncoder.encode(fileName + ".xls", "UTF-8"));
		response.setContentType("application/msexcel;charset=UTF-8");
		wb.write(out);
		out.flush();
		out.close();
		System.out.println("export GradePerformance success!!!");
	}
	
	/**
	 * excle 读取用户返回User集合，兼容2003-2007-2010excel
	 * 
	 * @param type
	 *            :目标实体类型
	 * @param path
	 *            ：excel 文件
	 * @return
	 */
	public static ApiResponse<List<Adks_user>> readExcelToUsers(InputStream inputStream) {

		try {
			Workbook workbook = WorkbookFactory.create(inputStream);
			if (workbook == null) {
				return ApiResponse.fail(500, "Excel workbook is null...");
			}
			Adks_user user = null;
			List<Adks_user> userList = new ArrayList<>();
			System.out.println("sheet个数:" + workbook.getNumberOfSheets());
			// 循环excel sheet
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workbook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				System.out.println("第" + (numSheet + 1) + "个sheet的行数：" + sheet.getLastRowNum());
				// 循环row，从第二行开始，第一行是表头
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					if (row != null) {
						user = new Adks_user();
						Cell cell_loginName = row.getCell(0);
						Cell cell_userName = row.getCell(1);
						//Cell cell_email = row.getCell(2);

						user.setUserName(cell_loginName.getStringCellValue());
						user.setUserRealName(cell_userName.getStringCellValue());
						//user.setUserMail(cell_email.getStringCellValue());
						userList.add(user);
					}
				}

			}
			return ApiResponse.success("Excel read success!", userList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ApiResponse.fail(500, "Excel异常，请检查");
	}

	/**
	 * excel xlsx高版本
	 * 
	 * @param path
	 * @return
	 */
	public static List<Object> readEscel_2007(String path) {
		return null;
	}

	/**
	 * excel xls 低版本
	 * 
	 * @param path
	 * @return
	 */
	public static List<Object> readExcel_2003(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getSuffix(String path) {
		if (path == null || "".equals(path.trim())) {
			return null;
		}
		if (path.contains(Constants.POINT)) {
			return path.substring(path.lastIndexOf(Constants.POINT) + 1, path.length());
		}
		return null;
	}
}
