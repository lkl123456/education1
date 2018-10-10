<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>课程管理</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
   	
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/zTree/jquery.ztree.all-3.5.min.js"></script>
    <link href="${pageContext.request.contextPath}/static/admin/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/course.js"></script>

</head>
<body class="easyui-layout">
<input type="hidden"  id="imgServer" value="${imgServer}" />
	<!-- 课程分类机构树 -->
	<div data-options="region:'west',split:true,title:'课程分类列表'" style="width:260px;padding:10px;">
		<input type="hidden" id="cc_ztree_node_id" value="0" />
		<input type="hidden" id="cc_ztree_node_name" value="顶级分类" />
		<input type="hidden" id="cc_ztree_node_code" value="0-" />
		<div id="btn_category_div">
			&nbsp;<a href="#" onclick="add_courseCategory();" class="easyui-linkbutton" iconCls="icon-add">添加</a>
		 	&nbsp;<a href="#" onclick="del_courseCategory();" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
		 	&nbsp;<a href="#" onclick="reloadTree();" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
		</div>
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" onclick="add_course();" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="#" onclick="edit_course()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="#" onclick="del_course(-1)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		
		<div>
			<c:if test="${!empty isPlatform}">
			 <input id="s_course_orgSnname" class="easyui-combobox" data-options="url:'/org/orgListSelJson',method:'get',valueField:'uname_sn',textField:'name',prompt:'选择网校...',onChange:function(){doSearch()}" style="width:200px;" />&nbsp;
			</c:if>
			<input type="hidden" id="isPlatform" value="${isPlatform }" />
			<input id="s_course_name" class="easyui-textbox" data-options="prompt:'课程名...'" style="width:200px;height:24px;" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
			&nbsp;&nbsp;<a href="#" onclick="del_course(2);" class="easyui-linkbutton" iconCls="icon-lock" >下架</a>
			&nbsp;<a href="#" onclick="del_course(1);" class="easyui-linkbutton" iconCls="icon-ok" >发布</a>
		</div>
	</div>
	<div id="mainText" data-options="region:'center'" class="easyui-tabs">
		<div title="课程列表数据" data-options="iconCls:'icon icon-nav',fit:true">
			<table id="courselistdatagrid" style="width:800px;height:350px" class="easyui-datagrid" 
					toolbar="#tb" data-options="
					url:'/course/getCourseListJson',
					method:'get',
					singleSelect:false,
					fit:true,
					fitColumns:true,
					striped:true,
					nowarp:false,
					pagination:true,
					pageSize:10,
					pageList:[5,10,15],
					rownumbers:true
					">
				<thead>
					<tr>
						<th data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'name'" align="center" width="100">课程名称</th>
						<th data-options="field:'category_name'" align="center" width="100">所属分类</th>
						<th data-options="field:'org_name',align:'center'" width="100">课程来源</th>
						<th data-options="field:'duration',align:'center'" width="80">播放时长</th>
						<th data-options="field:'update_time',align:'center',formatter:function(value,row,index){if(value != null){var unixTimestamp = new Date(value); return unixTimestamp.Format('yyyy-MM-dd');}}" width="80">更新时间</th>
						<th data-options="field:'status',align:'center',formatter:function(value,row,index){if(value=='0'){return '草稿'}else if(value=='1'){return '已发布'}else if(value=='2'){return '已下架'}}" width="50">发布状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>


	<!-- 添加、编辑课程分类窗口 -->
	<div id="dlg" class="easyui-dialog" title="添加课程分类" style="width:600px;height:300px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_category();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd_category();
					}
				}]
			">
		<div style="padding:10px 120px 20px 120px">
	    <form id="categoryForm" method="post" action="${pageContext.request.contextPath}/courseCategory/saveCourseCategory">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>分类名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width: 160px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>上级分类:</td>
	    			<td>
	    			<input id="cc" name="parent_id" class="easyui-combotree" data-options="url:'/courseCategory/getCourseCategoryTreeJson',method:'get',required:true" style="width:160px;">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>排序:</td>
	    			<td><input name="order_num" class="easyui-textbox" type="text" data-options="required:false,validType:'number'" style="width: 160px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>是否显示:</td>
	    			<td>
		    			<input type="radio" id="t_category_status" name="status" value="1"/>是
						<input type="radio" name="status" value="2"/>否
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	
	<!-- 添加课程信息div  -->
	<div id="dlg_course" class="easyui-dialog" title="添加课程" style="width:620px;height:580px;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				modal:true,closed:true,
				buttons: [{
					text:'发布',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_course(1);
					}
				},{
					text:'存草稿',
					iconCls:'icon-ok',
					handler:function(){
						submitAdd_course(0);
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						cancleAdd_course();
					}
				}]
			">
		<div style="padding-left: 20px; padding-top: 2px;">
	    <form id="courseForm" method="post" enctype="multipart/form-data"
	    	action="${pageContext.request.contextPath}/course/saveCourse">
	    	<input type="hidden" id="t_id" name="id" >
	    	<input type="hidden" id="t_course_status" name="status" value="0"/>
	    	<input type="hidden" id="t_course_categoryId" name="category_id" value="0"/>
	    	<input type="hidden" id="t_course_categoryCode" name="category_code" value="0-"/>
	    	<table cellpadding="5">
				<tr>
					<td>上传视频:</td>
					<td>
					<input type="hidden" id="videopath" name="file_name" >
					<input class="easyui-filebox" name="videoFile" data-options="prompt:'选择一个视频mp4文件...'" style="width:80%">
					<a href="#" onclick="startUpload();" class="easyui-linkbutton c5" iconCls="icon-add">上传开始</a>
					<div id="p" class="easyui-progressbar" style="width:400px;"></div>
					</td>
				</tr>
				<tr>
	    			<td>所属分类:</td>
	    			<td><input class="easyui-textbox" type="text" id="t_course_categoryName" name="category_name" data-options="required:false" style="width: 238px;" readonly="readonly"></td>
	    		</tr>
	    		<tr>
	    			<td>课程名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width: 238px;" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>课程简介:</td>
	    			<td><input class="easyui-textbox" name="description" data-options="multiline:true" style="height:90px; width:238px;" ></input></td>
	    		</tr>
	    		<tr>
	    		
	    			<td>课程图片:</td>
	    			<td>
	    				
						<img id="img_coverpath" src="" height="136" width="181" />
						<br/>
						<input type="file" name="course_path_file" onchange="uploadCourseCover();" />
						<input type="hidden" id="cover_path" name="cover_path"/>
					</td>
	    		</tr>
	    		<tr>
	    			<td>课程时长:</td>
	    			<td><input class="easyui-textbox" type="text" name="duration" data-options="required:true" style="width: 238px;"></input></td>
	    		</tr>
	    	</table>
	    </form>
	    
	    </div>
	</div>
	
</body>
</html>