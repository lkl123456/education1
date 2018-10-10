<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>list</title>
    <link href="${pageContext.request.contextPath}/static/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/easyui-lang-zh_CN.js"></script>
   

    <script type="text/javascript">    	
	    $(function() {	
			
	    });


		var toolbar = [{
			text:'添加',
			iconCls:'icon-add',
			handler:function(){
				$('#w').window('open')
			}
		},{
			text:'删除',
			iconCls:'icon-cut',
			handler:function(){alert('cut')}
		},'-',{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){alert('save')}
		}];
	
    </script>

</head>
<body>	

	<table style="width:800px;height:350px" class="easyui-datagrid" title="数据列表"
				data-options="
				url:'/index/managersJson',
				method:'get',
				singleSelect:true,
				collapsible:true,
				fit:true,
				fitColumns:true,
				pagination:true,
				pageSize:10,
				pageList:[5,10,15],
				rownumbers:true,
				toolbar:toolbar
				">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id'" align="center" width="80">管理员id</th>
					<th data-options="field:'user_name'" align="center" width="100">用户名</th>
					<th data-options="field:'password',align:'center'" width="80">密码</th>
					<th data-options="field:'mobile',align:'center'" width="80">手机</th>
					<th data-options="field:'email',align:'center'" width="150">邮箱</th>
					<th data-options="field:'state',align:'center'" width="50">状态</th>
					<th data-options="field:'admin_type',align:'center'" width="50">管理员类型</th>
					<th data-options="field:'org_id',align:'center'" width="50">分校id</th>
					<th data-options="field:'create_time',align:'center'" width="50">创建时间</th>
					<th data-options="field:'update_time',align:'center'" width="50">更新时间</th>
				</tr>
			</thead>
		</table>
		
		<div id="w" class="easyui-window" title="Window Layout" data-options="iconCls:'icon-save'" style="width:500px;height:200px;padding:5px;">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'east',split:true" style="width:100px"></div>
				<div data-options="region:'center'" style="padding:10px;">
					jQuery EasyUI framework help you build your web page easily.
				</div>
				<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:alert('ok')" style="width:80px">Ok</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:alert('cancel')" style="width:80px">Cancel</a>
				</div>
			</div>
		</div>
		
</body>
</html>