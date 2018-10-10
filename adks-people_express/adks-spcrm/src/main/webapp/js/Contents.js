/***************************单视频页面参数配置******************************/
//当前项目地址
var $BasePathURL_ = "http://120.27.9.213/adks-spcrm/";

//单视频播放地址
var $VideoServer_ = "http://120.27.9.213/adks-spcrm/videoPlay";

//微信wap播放地址
var $VideoWapServer_ = "http://120.27.9.213/adks-spcrm/videoWapPlay";

//前台web地址
var $webURL_ = "http://120.27.9.213/"; 

//图片地址
var $userHeadPicURL_ = "http://120.27.9.213:1889/";

//通用服务地址
var $commonServiceURL_ = "http://124.202.203.42/";

//评论所属类型（eg： 课程，活动...）
var $commentType_ = 794;

//流媒体地址
var videoServer = "http://mhdyzxktwj.oss-cn-beijing.aliyuncs.com/";

//主体ID 如（当前课程，活动等...的ID）
var $belongedId_ ;  

//用户id
var $userId_ ;

//课程评价
var $evaluateScore = 0;

//监控时长 秒
var checkTimes=600;

//课程是否有知识问答
var $hasQuestion = true; //默认课程不带知识问答，如果有，播放完毕，需要在播放页面弹出答题
//////////////////////////////////////////////////////////////////////////////////////////