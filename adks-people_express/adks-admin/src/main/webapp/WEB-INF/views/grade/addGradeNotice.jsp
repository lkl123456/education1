<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加公告</title>
<%@include file="../public/header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/grade/js/addGradeNotice.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/grade/css/addnews.css" />
<!--  ueditor 引入 begin  -->
	<!-- 配置文件 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script>
	<!--  ueditor 引入 end  -->
	<!-- 配置ueditor的路径 -->
	<script type="text/javascript">
		window.UEDITOR_HOME_URL = "/ueditor/";
	</script>
</head>
<body>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<form id="gradeNoticeForm" method="post" action="${pageContext.request.contextPath}/gradeNotice/saveGradeNotice" enctype="multipart/form-data">
			<input type="hidden" id="" name="newsId" value="">
			<input type="hidden" id="gradeId" name="gradeId" value="${gradeId}">
			<input type="hidden" id="newsHtmlAdress" name="newsHtmlAdress">
			<table cellpadding="1">
				<tr>
					<td>公告名称:</td>
					<td><input class="easyui-textbox" type="text" id="" name="newsTitle" data-options="required:true,validType:['length[1,60]','isNullOrEmpty']" style="width: 238px;"></td>
				</tr>
				<tr id="neirong">
					<td>公告内容:</td>
					<td><input type="hidden" id="newsContent" name="newsContent" data-options="multiline:true" style="height: 60px" /> <!-- 加载编辑器的容器 -->
						<script id="container" name="content" type="text/plain"></script>
						<!-- 实例化编辑器 --> 
						<script type="text/javascript">
							var ue = UE.getEditor('container', {
								toolbars : [ [ 'fullscreen', 'source', '|',
										'undo', 'redo', '|', 'bold', 'italic',
										'underline', 'fontborder',
										'strikethrough', 'superscript',
										'subscript', 'removeformat',
										'formatmatch', 'autotypeset',
										'blockquote', 'pasteplain', '|',
										'forecolor', 'backcolor',
										'insertorderedlist',
										'insertunorderedlist', 'selectall',
										'cleardoc', '|', 'rowspacingtop',
										'rowspacingbottom', 'lineheight', '|',
										'customstyle', 'paragraph',
										'fontfamily', 'fontsize', '|',
										'directionalityltr',
										'directionalityrtl', 'indent', '|',
										'justifyleft', 'justifycenter',
										'justifyright', 'justifyjustify', '|',
										'touppercase', 'tolowercase', '|',
										'link', 'unlink', 'anchor', '|',
										'imagenone', 'imageleft', 'imageright',
										'imagecenter', '|', 'attachment',
										'emotion', 'scrawl', 'map',
										'insertframe', 'insertcode',
										'pagebreak', 'template', 'background',
										'|', 'horizontal', 'date', 'time',
										'spechars', 'snapscreen', 'wordimage',
										'|', 'inserttable', 'deletetable',
										'insertparagraphbeforetable',
										'insertrow', 'deleterow', 'insertcol',
										'deletecol', 'mergecells',
										'mergeright', 'mergedown',
										'splittocells', 'splittorows',
										'splittocols', 'charts', '|',
										'searchreplace', 'help', 'drafts',
										'simpleupload' ] ],
								catchRemoteImageEnable : false,
								autoFloatEnabled : false,
								initialFrameWidth : 654,
								initialFrameHeight : 300
							});
						</script> 
						<script>
							//对编辑器的操作最好在编辑器ready之后再做
							ue.ready(function() {
										var content = '<s:property value="news.newsContent" escapeHtml="false" />';
										//设置编辑器的内容
										var id = '<s:property value="news.id" />';
										if (null != id && id > 0) {
											ue.setContent(content);
										}
									});
						</script>
					</td>
				</tr>
			</table>
			<div>
				<input class="easyui-linkbutton" type="button" value="保存" onclick="submitAdd_gradeNotice()" /> &nbsp;&nbsp;
				<input class="easyui-linkbutton" type="button" value="取消" onclick="cancleAdd_gradeNotice()" />  
			</div>
		</form>
	</div>
</body>
</html>