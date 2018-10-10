<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>管理员密码修改</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
    <script>
    $(function() {
    	$('#dlg_admin_pwd').dialog('open');
    })
    // 点击修改密码操作
	function submitChangePwd(){
    	var pwd1 = $("#pwd_new").val();
    	var pwd2 = $("#pwd_new_2").val();
    	if(pwd1 != pwd2){
    		$.messager.alert("提示","两次输入的密码不一致！", "info"); return;
    	}
		$('#changePwd_form').form({
			url:contextpath+'/admin/changePwd',
			onSubmit: function(){
				return $(this).form('validate');
			},
			success:function(data){
				if(data == 'succ'){
					$.messager.alert("提示","密码修改成功!", "info");
					$('#changePwd_form')[0].reset();
				}else if(data == 'old'){
					$.messager.alert("提示","原密码输入错误!", "info");
				}else{
					$.messager.alert("提示","密码修改失败!", "info");
				}
			}
		});
		$('#changePwd_form').submit();
	}
	
	// 关闭窗口
	function closePanel_pwd(){
		parent.$('#tabs').tabs('close', '修改密码');
	}
    </script>
</head>
<body class="easyui-layout" style="background-color: #fff;">
	
	<div id="dlg_admin_pwd" class="easyui-dialog" title="密码修改信息填写" style="width:100%;height:100%;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				buttons:'#dlg-buttons',
				modal:true,closed:true
			">
    <form id="changePwd_form" method="post" action="${pageContext.request.contextPath}/admin/changePwd">
	<div style="padding-left: 210px; padding-top: 60px;">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>原密码:</td>
	    			<td><input class="easyui-textbox" type="password" id="pwd_old" name="pwd_old" data-options="required:true,validType:'length[6,20]'" style="width: 238px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>新密码:</td>
	    			<td><input class="easyui-textbox" type="password" id="pwd_new" name="pwd_new" data-options="required:true,validType:'length[6,20]'" style="width: 238px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>确认密码:</td>
	    			<td><input class="easyui-textbox" type="password" id="pwd_new_2" data-options="required:true,validType:'length[6,20]'" style="width:238px;"></input></td>
	    		</tr>
	    	</table>
	</div>
    </form>
    </div>
    
    <div id="dlg-buttons" style="text-align: left; padding-left: 330px; padding-bottom: 260px;" >
		<a href="#" class="easyui-linkbutton c1" iconCls="icon-ok" onclick="javascript:submitChangePwd();">保存</a>&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton c9 tabs-close" iconCls="icon-cancel" onclick="javascript:closePanel_pwd()">取消</a>
	</div>
	

</body>
</html>