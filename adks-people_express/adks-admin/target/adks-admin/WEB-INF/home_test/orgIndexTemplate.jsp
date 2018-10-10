<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>政府云学堂后台管理</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link id="easyuiTheme" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
   
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
	<script>
		$(function(){
			
			$('#dlg_org_update').dialog('open');
			//加载form表单数据，默认写死orgId=1，后续需要判断登录用户的orgId
			$('#org_config').form('load','/template/loadTemplateFormDate');
			
		});
		
		function addSubmit(){
			$('#org_config').form('submit',{
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(data){//resId
					var datajson = $.parseJSON(data);
					if(datajson['mesg'] == 'succ'){
						$.messager.alert('提示','信息保存成功!','warning');
					}
				},error:function(){
					alert('error');
				}
			});
		}
	</script>
</head>



<body class="easyui-layout" style="background-color: #fff;">
	
	<div id="dlg_org_update" class="easyui-dialog" title="网校个性化设置" style="width:100%;height:100%;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				buttons:'#dlg-buttons',
				modal:true,closed:true
			">
    <form id="org_config" method="post" action="${pageContext.request.contextPath}/template/saveOrgConfig">
    	<div style="padding-left: 210px; padding-top: 60px;">
    	<input type="hidden" name="id" value="" />
    	<input type="hidden" name="org_id" value="" />
	    	<table cellpadding="5">
	    	<!-- 
	    		<tr>
	    			<td>当前分校id:</td>
					<td>
				    	<input type="text" name="org_id" value="" />
					</td>
	    		</tr> -->
	    		<tr>
	    			<td>首页模板:</td>
	    			<td>
	    				<input class="easyui-combobox" 
							name="template_id"
							data-options="
									url:'/template/getTemplateJson',
									method:'get',
									valueField:'id',
									textField:'text',
									panelHeight:'auto'
							">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>颜色:</td>
	    			<td>
	    			<select class="easyui-combobox" name="color_value" style="width:200px;">
						<option value="1">绿色</option>
						<option value="2">红色</option>
						<option value="3">蓝色</option>
					</select>
	    			</td>
	    		</tr>
	    		<br>
	    		
	    		<tr>
	    			<td>编辑底部:</td>
	    			<td>
	    				电话：<input type="text" name="phone"/><br><br><br>
	    				版权：<input class="easyui-textbox" type="text" name="copyright" style="width:400px;height:60px" data-options="multiline:true,required:false"></input>
	    			</td>
	    		</tr>
	    	</table>
	    	</div>
	    </form>
    </div>
    
    <div id="dlg-buttons" style="text-align: left; padding-left: 330px; padding-bottom: 90px;" >
		<a href="#" class="easyui-linkbutton c5" iconCls="icon-ok" onclick="javascript:addSubmit();">生成</a>
		<a href="#" class="easyui-linkbutton c7" iconCls="icon-ok" onclick="javascript:;">预览</a>
		<a href="#" class="easyui-linkbutton c9 tabs-close" iconCls="icon-cancel" onclick="javascript:parent.$('#tabs').tabs('close', '网校个性化设置');">取消</a>
	</div>
	

</body>
</html>