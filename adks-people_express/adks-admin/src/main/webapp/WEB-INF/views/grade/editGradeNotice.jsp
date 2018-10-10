<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
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
</head>
<body>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<form id="gradeNoticeForm" method="post" action="${pageContext.request.contextPath}/gradeNotice/saveGradeNotice" enctype="multipart/form-data">
			<!-- 隐藏域信息start -->
			<input type="hidden" id="newsId" name="newsId" value="${news.newsId }">
			<input type="hidden" id="newsHtmlAdress" name="newsHtmlAdress" value="${news.newsHtmlAdress }">
			<input type="hidden" id="gradeId" name="gradeId" value="${news.gradeId }">
			<input value='${content}' type="hidden" id="nc" />
			<!-- 隐藏域信息end -->
			<table cellpadding="1"  >
				<tr>
					<td width="150px" style="text-align: right">公告名称:</td>
					<td><input class="easyui-textbox" type="text" id="newsTitle" name="newsTitle"
						data-options="required:true,validType:['isNullOrEmpty','length[2,20]']" style="width: 238px;" value="${news.newsTitle }">
					</td>
				</tr>
				<tr id="neirong">
					<td width="150px" style="text-align: right">新闻内容:</td>
					<td><input type="hidden" id="newsContent" name="newsContent" data-options="multiline:true" style="height: 60px" /> 
					<input type="hidden" name="content" /> 
					<!-- 加载编辑器的容器 --> 
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
								var content = $("#nc").val();
								ue.setContent("");
								ue.setContent(content, true);
							});
						</script></td>
				</tr>
			</table>
			<div>
				<input class="easyui-linkbutton" type="button" value="保存" onclick="addNewsOk()" /> &nbsp;&nbsp;
				<input class="easyui-linkbutton" type="button" value="取消" onclick="addNewsCancle()" />
			</div>
		</form>
	</div>
</body>
</html>