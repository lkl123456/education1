<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>课程管理</title>
<%@include file="../public/header.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/zTree/jquery.ztree.all-3.5.min.js"></script>
	<link href="${pageContext.request.contextPath}/static/admin/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/news/addNews.js"></script>
	<!--  ueditor 引入 begin  -->
	<!-- 配置文件 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script>
	<!--  ueditor 引入 end  -->
	<style type="text/css">
		#imgCss {
			margin-top: 10px;
			height: 100px;
		}
		img {
			float: left;
		}
		.middle {
			line-height: 50px; /*注意这行代码，惹是不写，得不到想要的结果哦*/
			vertical-align: text-bottom;
			text-align: left;
		}
	</style>
</head>
<body class="easyui-layout">
	<input type="hidden" id="imgServer" value="${ossResource}" />
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="新闻信息" style="padding-left: 20px; padding-top: 2px;">
			<form id="newsForm" method="post" enctype="multipart/form-data">
				<input type="hidden" id="newsId" name="newsId" value="${news.newsId }">
				<input type="hidden" id="newsHtmlAdress" name="newsHtmlAdress" value="${news.newsHtmlAdress }">
				<input type="hidden" name="newsFocusPic" id="newsFocusPic" value="${news.newsFocusPic }" />
				<table cellpadding="1">
					<tr>
						<td width="150px" style="text-align: right">新闻名称:</td>
						<td><input class="easyui-textbox" type="text" id="newsTitle" name="newsTitle"
							data-options="required:true,validType:['length[1,50]','isNullOrEmpty']"
							style="width: 238px;" value="${news.newsTitle }"></td>
					</tr>
					<tr>
						<td width="150px" align="right">是否公开:</td>
						<td><input type="radio" id="newsBelong" name="newsBelong" value="1"
							<c:if test="${empty news.newsBelong || news.newsBelong eq 1}">checked="checked"</c:if>>公开&nbsp;&nbsp;
							<input type="radio" id="newsBelong" name="newsBelong" value="2"
							<c:if test="${!empty news.newsBelong && news.newsBelong eq 2}">checked="checked"</c:if>>专属&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<td width="150px" style="text-align: right">所属分类:</td>
						<td><input type="text" disabled="disabled" value="${newsSortName }" /> 
							<input type="hidden" name="newsSortId" value="${newsSortId }"> 
							<input type="hidden" name="newsSortName" value="${newsSortName }">
						</td>
					</tr>
					<!-- 
					<tr>
						<td width="150px" style="text-align: right">所属机构:</td>
						<td><input type="text" disabled="disabled" value="${orgName }" />
						 	<input type="hidden" name="orgId" value="${orgId }"> 
						 	<input type="hidden" name="orgName" value="${orgName }"> 
						 	<input type="hidden" name="orgCode" value="${orgCode }"> 
						 	<input type="hidden" name="newsSortType" value="${newsSortType }">
						</td>
					</tr>
					 -->
					<tr>
						<td width="150px" style="text-align: right">新闻焦点图:</td>
						<td>
							<div id="localImag" style="margin-top: 10px;">
								<img width="201" height="121" id="hpath" />
								<p class="middle">（只能上传 JPG、JPEG、PNG、GIF、BMP、TIF
									格式的图片，建议图片高宽为：315*520，图片大小不超过900KB）</p>
								<br>
							</div> 
							<input type="file" name="newsFocusPicFile" id="myFile" value="" 
							style="width: 373px; margin-bottom: 4px;"
							onchange="showImg('newsFocusPicFile','newsFocusPic')" /> 
						</td>
					</tr>
					<tr>
						<td width="150px" style="text-align: right">新闻类型:</td>
						<td><input type="radio" name="newsType" value="1"
							data-options="required:true" onclick="changeNewsType(1)"
							<c:if test="${empty news.newsType || news.newsType==1 }">checked="checked"</c:if> /><span>文本型</span>
							<input type="radio" name="newsType" value="2"
							data-options="required:true" onclick="changeNewsType(2)"
							<c:if test="${news.newsType==2 }">checked="checked"</c:if> /><span>链接型</span>
						</td>
					</tr>
					<tr id="lianjie">
						<td width="150px" style="text-align: right">新闻链接:</td>
						<td><input class="easyui-textbox" type="text" id="newsLink"
							name="newsLink" data-options="validType:'url'"
							value="${news.newsLink }"></td>
					</tr>
					<tr id="neirong">
						<td width="150px" style="text-align: right">新闻内容:</td>
						<td><input type="hidden" id="newsContent" name="newsContent"
							data-options="multiline:true" style="height: 60px" /> <input
							value='${content }' type="hidden" id="nc" /> <input
							type="hidden" name="content" /> <!-- 加载编辑器的容器 --> <script
								id="container" name="content" type="text/plain"></script> <!-- 实例化编辑器 -->
							<script type="text/javascript">
								var ue = UE
										.getEditor(
												'container',
												{
													toolbars : [ [
															'fullscreen',
															'source',
															'|',
															'undo',
															'redo',
															'|',
															'bold',
															'italic',
															'underline',
															'fontborder',
															'strikethrough',
															'superscript',
															'subscript',
															'removeformat',
															'formatmatch',
															'autotypeset',
															'blockquote',
															'pasteplain',
															'|',
															'forecolor',
															'backcolor',
															'insertorderedlist',
															'insertunorderedlist',
															'selectall',
															'cleardoc',
															'|',
															'rowspacingtop',
															'rowspacingbottom',
															'lineheight',
															'|',
															'customstyle',
															'paragraph',
															'fontfamily',
															'fontsize',
															'|',
															'directionalityltr',
															'directionalityrtl',
															'indent',
															'|',
															'justifyleft',
															'justifycenter',
															'justifyright',
															'justifyjustify',
															'|',
															'touppercase',
															'tolowercase',
															'|',
															'link',
															'unlink',
															'anchor',
															'|',
															'imagenone',
															'imageleft',
															'imageright',
															'imagecenter',
															'|',
															'attachment',
															'emotion',
															'scrawl',
															'map',
															'insertframe',
															'insertcode',
															'pagebreak',
															'template',
															'background',
															'|',
															'horizontal',
															'date',
															'time',
															'spechars',
															'snapscreen',
															'wordimage',
															'|',
															'inserttable',
															'deletetable',
															'insertparagraphbeforetable',
															'insertrow',
															'deleterow',
															'insertcol',
															'deletecol',
															'mergecells',
															'mergeright',
															'mergedown',
															'splittocells',
															'splittorows',
															'splittocols',
															'charts', '|',
															'searchreplace',
															'help', 'drafts',
															'simpleupload' ] ],
													catchRemoteImageEnable : false,
													autoFloatEnabled : false,
													initialFrameWidth : 654,
													initialFrameHeight : 300
												});
							</script> <script>
								//对编辑器的操作最好在编辑器ready之后再做
								ue.ready(function() {
									var content = $("#nc").val();
									ue.setContent("");
									ue.setContent(content, true);
								});
							</script></td>
					</tr>

					<tr height="35px;">
						<td colspan="2"><input class="easyui-linkbutton"
							type="button" value="保存" onclick="submitAdd_news()" />
							&nbsp;&nbsp; <input class="easyui-linkbutton" type="button"
							value="取消" onclick="addNewsCancle()" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

</body>
</html>