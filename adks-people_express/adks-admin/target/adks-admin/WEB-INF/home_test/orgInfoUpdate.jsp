<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">    	
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>网校基本信息</title>
    <link href="${pageContext.request.contextPath}/static/admin/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/ui-cupertino/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/themes/color.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/admin/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/orgInfoUpdate.js"></script>
    
</head>
<body class="easyui-layout" style="background-color: #fff;">
	
	<div id="dlg_org_update" class="easyui-dialog" title="信息填写" style="width:100%;height:100%;padding:10px;"
			data-options="
				iconCls: 'icon-save',
				buttons:'#dlg-buttons',
				modal:true,closed:true
			">
    <form id="orgForm_update" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/org/updateOrgInfo">
	<div style="padding-left: 210px; padding-top: 60px;">
	    	<input type="hidden" name="id" value="${map.id }" />
	    	<table cellpadding="5">
	    		<tr>
	    			<td>网校名称:</td>
	    			<td><input class="easyui-textbox" name="name" value="${map.name }" data-options="required:true,validType:'length[1,32]'" style="width: 238px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>网校LOGO:</td>
	    			<td>
						<img src="/static/admin/images/def.jpg" id="img_src_logo" name="imgServerView" width="193" height="168" onclick="click_upload_img()" />
						<span style="font-size: 12px; color: #777;" >温馨提示：上传请点击左侧图片！</span>
						<input type="file" name="file" style="display: none;" id="file_input" onchange="upload_file()"/>
						<input type="hidden" id="logo_path" name="logo_path" value="${map.logo_path }"/>
						<input type="hidden" id="imgServer" name="imgServer" value="${map.imgServer }"/>
					</td>
	    		</tr>
	    		<tr>
	    			<td>详细地址:</td>
	    			<td><input class="easyui-textbox" type="text" name="address" value="${map.address }" data-options="required:true" style="width: 278px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>网校介绍:</td>
	    			<td><input class="easyui-textbox" type="text" name="description" value="${map.description }" data-options="multiline:true,required:true" style="height:80px; width:278px;"></input></td>
	    		</tr>
	    	</table>
	</div>
    </form>
    </div>
    
    <div id="dlg-buttons" style="text-align: left; padding-left: 330px; padding-bottom: 90px;" >
		<a href="#" class="easyui-linkbutton c1" iconCls="icon-ok" onclick="javascript:submitForm();">保存</a>
		<a href="#" class="easyui-linkbutton c9 tabs-close" iconCls="icon-cancel" onclick="javascript:closePanel()">取消</a>
	</div>
	

</body>
</html>