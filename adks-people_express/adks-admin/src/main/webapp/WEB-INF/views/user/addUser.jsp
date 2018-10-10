<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
	var contextpath = '${pageContext.request.contextPath}';
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加用户</title>
<%@include file="../public/header.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/user/js/addUser.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/news/addnews.css" />
</head>
<body style="background:#fff;">
	<div style="padding-top: 2px;">
		<form id="hh" method="post" action="${pageContext.request.contextPath}/userIndex/saveUser"
				enctype="multipart/form-data">
				<input type="hidden" id="userId" name="userId" value="${user.userId }" />
				<input type="hidden" id="orgId" name="orgId" value="${user.orgId }" />
				<input type="hidden" id="orgNameTrue" value="${user.orgName }" /> 
				<input type="hidden" id="rankName" name="rankName" value="${user.rankName }" />
				<input type="hidden" id="positionName" name="positionName" value="${user.positionName }" /> 
				<input type="hidden" id="orgCode" name="orgCode" value="${user.orgCode }" /> 
				<input type="hidden" id="headPhoto" name="headPhoto" value="${user.headPhoto }"/>
				<input type="hidden" id="userParty"  value="${user.userParty }"/>
				<input type="hidden" id="rankId"  value="${user.rankId }"/>
				<input type="hidden" id="positionIdTwo"  value="${user.positionId }"/>
				<input type="hidden" id="userNationality"  value="${user.userNationality }"/>
				<table cellpadding="5">
					<a class="easyui-linkbutton c4">基本信息</a>
					<tr>
						<td>用户名:</td>
						<td><input class="easyui-textbox" type="text" name="userName" id="userName" value="${user.userName }" 
							<c:if test="${!empty user }">disabled="disabled"</c:if>
							data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></input> <span style="color: red"> * </span>
							<c:if test="${!empty user }">
								<input type="hidden" name="userName" value="${user.userName }"/>
							</c:if>
						</td>
					</tr>
					<c:if test="${empty user || empty user.userId}">
					<tr>
						<td>密码:</td>
						<td><input class="easyui-textbox" type="password" name="userPassword" id="userPassword" 
							data-options="required:true,validType:['isNullOrEmpty','length[6,32]']"></input><span style="color: red"> * </span></td>
					</tr>
					</c:if>
					<tr>
						<td>姓 名:</td>
						<td><input class="easyui-textbox" type="text" id="userRealName" value="${user.userRealName }"
							name="userRealName" data-options="required:true,validType:['isNullOrEmpty','length[1,32]']"></input><span style="color: red"> * </span></td>
					</tr>
					<tr>
						<td>性别:</td>
						<td>
							<input type="radio" name="userSex" value="1" <c:if test="${empty user.userSex || user.userSex==1 }">checked="checked"</c:if> />男
							<input type="radio" name="userSex" value="2" <c:if test="${user.userSex==2 }">checked="checked"</c:if>/>女
						</td>
					</tr>
					<tr>
						<td>用户图像:</td>
						<td>
							<c:if test="${!empty user && ! empty user.headPhoto && user.headPhoto !=' ' }">
								<img id="uiPath" height="100px" width="100px" src="${ossResource }${user.headPhoto}" />
							</c:if>
							<c:if test="${empty user ||empty user.headPhoto || user.headPhoto ==' ' }">
								<img id="uiPath" height="100px" width="100px"  src="../static/admin/images/qsimage.jpg"/>
							</c:if>
							 <br /> <input
							class="easyui-filebox" id="headPhotofile" name="headPhotofile"
							data-options="prompt:'选择一张头像...'" style="width: 200px"></input></td>
					</tr>
					<tr>
						<td>身份证号:</td>
						<td><input class="easyui-textbox" type="text" id="cardNumer" value="${user.cardNumer }"
							name="cardNumer"></input></td>
					</tr>
					<tr>
						<td>出生日期:</td>
						<td><input id="userBirthday" type="text" value="${user.userBirthday }"
							class="easyui-datebox" data-options="editable:false"></input></td>
					</tr>
					<tr>
						<td>民族:</td>
						<td><input class="easyui-combobox" data-options="required:true,editable:false" type="text" id="ss1"
							name="userNationality"></input> <span style="color: red"> * </span></td>
					</tr>
				</table>
				<table cellpadding="5">
					<a class="easyui-linkbutton c4">联系方式</a>
					<tr>
						<td>邮 箱:</td>
						<td><input class="easyui-textbox" type="text" name="userMail" id="userMail" value="${user.userMail }"
							style="width: 200px;"></input></td>
					</tr>
					<tr>
						<td>手 机:</td>
						<td><input class="easyui-textbox" type="text" id="userPhone" value="${user.userPhone }" data-options="required:true" name="userPhone"></input>
						</td>
					</tr>

				</table>

				<table cellpadding="5">
					<a class="easyui-linkbutton c4">学习信息</a>
					<tr>
						<td>所属部门:</td>
						<td><input type="text" name="orgName" id="orgName" value="${user.orgName }" onclick="showOrgTree();"></input><span style="color: red"> * </span>
							<ul id="treeDemo1" class="ztree" style="display:none;"></ul>
						</td>
					</tr>
					<tr>
						<td>政治面貌:</td>
						<td><input class="easyui-combobox" id="ss2" name="userParty" data-options="required:true,editable:false" 
							style="width: 100px;"> <span style="color: red"> * </span> <!-- <option value="1">团员</option>
							<option value="2">党员</option>
							<option value="3">无</option> --> </input></td>
					</tr>
					<tr>
						<td>职 级:</td>
						<td><select id="zhijiTree" name="rankId" style="width: 200px;" onchange="changeZhiWu();"></select>
							&nbsp;&nbsp;
							职务：
			             	<select id="positionId" name="positionId">
							</select>
						</td>
					</tr>
					<tr>
						<td>手动输入职务：</td>
						<td>
							<input type="text" placeholder="以上没有合适的职务，请手动输入" name="rankNameStr"  id="rankNameStr" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<td>用户状态:</td>
						<td><input type="radio" name="userStatus" <c:if test="${empty user || user.userStatus==1 }">checked="checked"</c:if>  value="1" />开通 
							<input type="radio" name="userStatus" <c:if test="${!empty user && user.userStatus==2 }">checked="checked"</c:if> value="2" />停用
						</td>
					</tr>
					<tr>
						<td>有效期至:</td>
						<td><input id="overdate" type="text" class="easyui-datebox" data-options="editable:false" value="${user.overdate }"></input>
						</td>
					</tr>
				</table>
				<div>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="submitAdd()">保存</a>  &nbsp;&nbsp;
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"   onclick="cancleAdd()">取消</a>  
				</div>
			</form>
	</div>
</body>
</html>