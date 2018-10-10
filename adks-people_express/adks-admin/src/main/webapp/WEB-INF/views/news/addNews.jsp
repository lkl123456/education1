<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加新闻</title>
<%@include file="../public/header.jsp"%>
<!--  ueditor 引入 begin  -->
	<!-- 配置文件 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script>
	<!--  ueditor 引入 end  -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/news/addNews.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/news/addnews.css" />
	<!-- 配置ueditor的路径 -->
	<script type="text/javascript">
		window.UEDITOR_HOME_URL = "/ueditor/";
	</script>
</head>
<body>
	<div style="padding-top: 2px;">
		<form id="newsForm" method="post" enctype="multipart/form-data" >
			<input type="hidden" id="newsId" name="newsId" value="">
			<input type="hidden" id="newsSortId" name="newsSortId" value="${newsSortId }">
			<input type="hidden" id="newsSortType" name="newsSortType" value="${newsSortType }">
			<input type="hidden" id="newsFocusPic" name="newsFocusPic" value="${news.newsFocusPic }">
			<table cellpadding="1">
				<a class="easyui-linkbutton c4">新闻信息</a>
				<tr>
					<td>新闻名称:</td>
					<td>
						<input class="easyui-textbox" type="text" id="newsTitle" name="newsTitle" data-options="required:true,validType:['length[1,50]','isNullOrEmpty']" 
						style="width: 238px;"></td>
				</tr>
				<tr>
					<td>新闻焦点图:</td>
					<td id="imgCss">
						<div id="localImag" style="margin-top: 10px;">
							<img src="../static/news/images/qsimage.jpg" width="201" height="121" id="hpath" />
							<p class="middle">（只能上传 JPG、JPEG、PNG、GIF、BMP、TIF 格式的图片，建议图片高宽为：315*520，图片大小不超过900KB）</p>
							<br>
						</div> <input type="file" name="newsFocusPicFile" id="myFile" style="width: 373px; margin-bottom: 4px;"
						onchange="headpicPathChange(this.value)" />
					</td>
				</tr>
				<tr>
					<td>新闻类型:</td>
					<td>
						<input type="radio" name="newsType" value="1" data-options="required:true" onclick="changeNewsType(1)"
						<c:if test="${empty news.newsType || news.newsType==1 }">checked="checked"</c:if> /><span>文本型</span>
						<input type="radio" name="newsType" value="2" data-options="required:true" onclick="changeNewsType(2)"
						<c:if test="${news.newsType==2 }">checked="checked"</c:if> /><span>链接型</span>
					</td>
				</tr>
				<tr id="neirong">
					<td>新闻内容:</td>
					<td><input type="hidden" id="newsContent" name="newsContent"
						data-options="multiline:true" style="height: 60px" /> <!-- 加载编辑器的容器 -->
						<script id="container" name="content" type="text/plain"></script>
						<!-- 实例化编辑器 --> <script type="text/javascript">
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
						</script> <script>
							//对编辑器的操作最好在编辑器ready之后再做
							ue.ready(function() {
								var content = '<s:property value="cmsArticle.content" escapeHtml="false" />';
								//设置编辑器的内容
								var id = '<s:property value="cmsArticle.id" />';
								if (null != id && id > 0) {
									ue.setContent(content);
								}
							});
						</script></td>
				</tr>
				<tr id="lianjie" style="display: none">
					<td>新闻链接:</td>
					<td><input class="easyui-textbox" type="text" id="newsLink" name="newsLink" data-options="validType:'url'"></td>
				</tr>
				<tr>
					<td>所属分类:</td>
					<td><input id="newsSortId" class="easyui-combobox" name="newsSortId"
						data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath}/newsSort/getNewsSortList',panelHeight:'auto'" />
					</td>
				</tr>
			</table>
			<div>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="submitAdd_news()">保存</a>  &nbsp;&nbsp;
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"   onclick="cancleAdd_news()">取消</a>  
			</div>
		</form>
	</div>
</body>
</html>