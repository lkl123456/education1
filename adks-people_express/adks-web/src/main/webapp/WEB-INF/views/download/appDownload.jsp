<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>民航党员在线课堂-移动端下载</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/download/js/download.js"></script>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

a {
	text-decoration: none;
}

img {
	max-width: 100%;
	height: auto;
}

.weixin-tip {
	display: none;
	position: fixed;
	left: 0;
	top: 0;
	bottom: 0;
	background: rgba(0, 0, 0, 0.8);
	filter: alpha(opacity = 80);
	height: 100%;
	width: 100%;
	z-index: 100;
}

.weixin-tip p {
	text-align: center;
	margin-top: 10%;
	padding: 0 5%;
}
</style>
</head>
<body>
	<div class="weixin-tip">
		<p>
			<img src="${pageContext.request.contextPath}/static/download/images/live_weixin.png" alt="微信打开" />
		</p>
	</div>
</body>
</html>