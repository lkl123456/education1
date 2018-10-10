<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>民航党员在线课堂-移动端下载</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
<!-- 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/download/css/download.css" />
 -->
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

body {
	background: #fff;
	font-size: 14px;
	font-family: "宋体";
}

a img {
	border: none;
}

.d_header {
	background: url(../../static/images/headerbg1.jpg) no-repeat;
	width: 100%;
	height: 552px;
}

.d_header .con {
	width: 1000px;
	margin: 0 auto;
	zoom: 1;
	overflow: hidden;
}

.d_header .con .l_side {
	float: left;
	display: inline;
	padding: 80px 0 0 0;
	width: 550px;
}

.d_header .con .l_side .btns {
	padding: 30px 0 0 0;
}

a.btn_ios, a.btn_android, a.btn_ios:hover, a.btn_android:hover {
	background: url(../../static/images/l_btn.png) no-repeat;
	display: inline-block;
	width: 172px;
	height: 45px;
	margin-right: 15px;
}

a.s_i, a.s_a {
	display: inline-block;
	width: 172px;
	height: 36px;
	margin-right: 15px;
	color: #fff;
	text-decoration: none;
	text-align: center;
	line-height: 36px;
}

a.s_i:hover, a.s_a:hover {
	color: #ccc;
}

a.btn_android {
	background-position: -185px 0;
}

a.btn_ios:hover {
	background-position: 0 -45px;
}

a.btn_android:hover {
	background-position: -185px -45px;
}

.d_header .con .r_side {
	float: right;
	display: inline;
}

div.i1, div.i2 {
	padding: 20px 0;
	text-align: center;
}

div.i2 {
	background: #f8f8f8;
}

.bg_all_01 {
	display: none;
	opacity: 0.6;
	background: #000;
	width: 100%;
	height: 100%;
	left: 0;
	top: 0;
	position: fixed;
	filter: alpha(opacity = 60);
}

.saoma {
	display: none;
	z-index: 99;
	position: fixed;
	top: 200px;
	margin-left: 50%;
	background: rgb(247, 247, 249);
	left: -162px;
	width: 320px;
	height: 260px;
	border: #ddd solid 1px;
}

.saoma dt {
	position: relative;
	font-size: 16px;
	line-height: 30px;
	height: 30px;
	padding-top: 18px;
	text-align: center;
	font-weight: bold;
	color: #666
}

.saoma dd {
	background: url(../../static/images/ios.png) center 30px no-repeat;
	height: 180px;
}

.saoma .android {
	background: url(../../static/download/images/1495609957.png) center 30px no-repeat;
}

.saoma dt span {
	background: url(../../static/images/chadiao.gif) no-repeat;
	width: 24px;
	height: 26px;
	cursor: pointer;
	position: absolute;
	right: 12px;
	top: 15px;
}

.top {
	height: 33px;
	line-height: 33px;
	border-bottom: 2px solid #0069b7;
	width: 100%
}

.top .t_con {
	width: 1000px;
	font-size: 12px;
	margin: 0 auto;
}

.top .t_con a {
	color: #333;
	text-decoration: none;
}

.top .t_con a:hover {
	color: #0069b7;
}
.btns{zoom:1; overflow:hidden;}
.btns .erweima{float:left; width:120px; height:120px;}
.btns .app_s_item{margin:10px 0 0 20px;}
</style>
</head>
<div class="top">
	<div class="t_con">
		<a
			href="${pageContext.request.contextPath}/index/index/${orgId}.shtml"><<
			返回首页</a>
	</div>
</div>
<div class="d_header">
	<div class="con">
		<div class="l_side">
			<img src="${pageContext.request.contextPath}/static/images/l_img.png" />
			<div class="btns">
			<img src="${pageContext.request.contextPath}/static/download/images/qrcode.png" class="erweima"/>
			<img src="${pageContext.request.contextPath}/static/images/l_btn1.png" class="app_s_item" />
			<!-- 
				<a href="javascript:alert('正在建设中...');" class="btn_ios mob_a1"></a><a href="${pageContext.request.contextPath}/static/download/mhdyzxkt_v1.0.apk" class="btn_android mob_a2"></a>
				<a href="javascript:alert('正在建设中...');" class="s_i mob_a3">扫描下载</a><a href="#" class="s_a mob_a4">扫描下载</a> 
				<a href="javascript:alert('正在建设中...');" class="s_i">点击下载</a><a href="${pageContext.request.contextPath}/static/download/mhdyzxkt_v1.0.apk" class="s_a">点击下载</a>
			 -->
			</div>
			<!-- 
			<div class="mobi_2"></div>
			<div class="mobi_3"></div>
			<div class="bg_all_01"></div>
			<dl class="saoma">
				<dt>
					扫码安装 <span></span>
				</dt>
				<dd class="android"></dd>
			</dl>
			 -->
		</div>
		<div class="r_side">
			<img src="${pageContext.request.contextPath}/static/images/p_img.png" />
		</div>
	</div>
</div>
<div class="i1">
	<img src="${pageContext.request.contextPath}/static/images/i1.jpg" />
</div>
<div class="i1">
	<img src="${pageContext.request.contextPath}/static/images/i3.jpg" />
</div>
</body>
</html>