package com.adks.commons.util;

/**
 * 常量
 * @author panpanxu
 *
 */
public class Constants {
	// SHA-1签名密钥
	public static final String SHA1_SECRET_KEY = PropertiesFactory.getProperty(PropertiesFactory.CONFIG_PROPERTIES, "sha1.secret.key");
	
	public static final String CLIENT_MAXAGE="1";
	
	public static final String IMG_SERVER = "img.server";
	
	public static final Integer ZERO = 0;
	
	public static final Integer  YES = 1;
	
	public static final Integer NO = 2;
	
	//前台页面使用的变量
	public static final String CACHE_CONTROL_MAX_AGE = "cache-Control.max-age";
	
	public static final String CACHE_CONTROL_CONTENT = "cache-Control.content";
	
	//前台使用 名字
	public static final String APP_NAME = "app.name";
	//前台 keywords 
	public static final String APP_KEYWORS = "app.keywords";
	//前台 描述
	public static final String APP_DESCRIPTION = "app.description";
	
	//精度值
	public static final Integer precisionNum = 100;
	
	//秒和小时的进度转换精度值
	public static final Integer secondNum = 3600;
	
	/** 班级考试类型 */
	public static final Integer GRADE_EXAM_T1 = 90;  // 班级结业考试
	public static final Integer GRADE_EXAM_T2 = 91;  // 班级普通考试
	public static final Integer GRADE_EXAM_T3 = 92;  // 班级模拟练习
	
	/** 试题类型 */
	public static final Integer QUESTION_TYPE_DANXUAN = 71;  // 单选题
	public static final Integer QUESTION_TYPE_DUOXUAN = 73;  // 多选题
	public static final Integer QUESTION_TYPE_PANDUAN = 75;  // 判断题
	public static final Integer QUESTION_TYPE_TIANKONG = 77;  // 填空题
	public static final Integer QUESTION_TYPE_WENDA = 79;  // 问答题
	
	/** 页面分页条数 */
	public static final Integer PAGE_SIZE_THREE = 3;
	public static final Integer PAGE_SIZE_FIVE = 5;
	public static final Integer PAGE_SIZE_SIX = 6;
	public static final Integer PAGE_SIZE_SIXTEEN = 16;
	public static final Integer PAGE_SIZE_TEN = 10;
	
	/** 页面ajax 提交处理后返回的结果标识 **/
	public static String RESULT_SUCC = "succ";  
	public static String RESULT_ERROR = "error";
	
	/** 文章管理 */
	public static final Integer TPXW = 15;//图片新闻
	public static final Integer APPTP = 2;//APP图片
	public static final Integer TZGG = 16;//通知公告
	public static final Integer XWZX = 18;//新闻资讯
	public static final Integer PXDT = 17;//培训动态
	
	/**课程状态 必修选修*/
	public static final Integer BXKC = 500;//必修课程
	public static final Integer XXKC = 501;//选修课程
	
	/**民族枚举*/
	public static final Integer MZ = 213;
	
	public static final Integer ZIMU = 218;  //字母
	public static final Integer AUTHOR_ORG = 223; //讲师单位

	/**
	 * 帮助中心
	 */
	public static final Integer XXLC =1;// 学习流程
	public static final Integer CZZN =2;// 操作指南
	public static final Integer CJWT =3;// 常见问题
	public static final Integer KQBD =4;// 考前必读
}
