var setting1 = {
	async : {
		enable : true,
		url : contextpath + "/userIndex/getOrgsJson",
		autoParam : [ "id=parentId" ],
		// otherParam : ["orgunameValue",orgunameValue],
		type : "get"
	},
	view : {
		dblClickExpand : false
	},
	callback : {
		onClick : changeValue,
		onAsyncSuccess : zTreeOnSuccess
	}
};
//默认展开一级部门
function zTreeOnSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo1");
	var nodes = treeObj.getNodesByParam("id", "1", null);
	if (nodes) {
		treeObj.expandNode(nodes[0], true, false, true);
	}
}
//添加机构
function changeValue(event, treeId, treeNode, clickFlag) {
	if(treeNode.id!=null && (treeNode.id!='' || treeNode.id == 0)){
		$("#orgName").val(treeNode.name);
		$("#orgId").val(treeNode.id);
		$("#orgCode").val(treeNode.orgCode);
		$("#treeDemo1").css("display","none");
	}
}
function showOrgTree(){
	$("#treeDemo1").css("display","");
}
$(function() {
	$.fn.zTree.init($("#treeDemo1"), setting1);
	$("#treeDemo1").css("display","none");
	var userId=$("#userId").val();
	if(userId == null || userId == ''){
		$("#overdate").datebox('setValue', '2099-12-30');
	}
	// 民族下拉框
	$('#ss1').combobox({
		url : contextpath+ "/userIndex/getUserNationalityJson?name=nationality",
		valueField : 'id',
		textField : 'text'
		/*onLoadSuccess : function() {
			$('#ss1').combobox('select', '3');// 菜单项可以text或者id
		}*/
	});
	// 政治面貌下拉框
	$('#ss2').combobox({
		url : contextpath+ "/userIndex/getUserNationalityJson?name=politics",
		valueField : 'id',
		textField : 'text'
		/*onLoadSuccess : function() {
			$('#ss2').combobox('select', '14');// 菜单项可以text或者id
		}*/
	});
	var rankId=$("#rankId").val();
	// 职级树
	$('#zhijiTree').combotree({
		url : contextpath + "/zhiji/getZhijisJson",
		method : 'get',
		onLoadSuccess : function(node, data) {
			//var node = $('#zhijiTree').combotree("tree").tree('getRoot');
			if(rankId!=null && rankId!=''){
				$('#zhijiTree').combotree("setValue", rankId);
			}else{
				$('#zhijiTree').combotree("setValue",data[0].id);
			}
			changeZhiWu();
		},
		onChange:function(node, data) {
			changeZhiWu();
		}
	});
	var userParty=$("#userParty").val();
	if(userParty==null || userParty==''){
		$('#ss2').combobox('select', '14');// 菜单项可以text或者id
	}else{
		$('#ss2').combobox('select',userParty );// 菜单项可以text或者id
	}
	var userNationality=$("#userNationality").val();
	if(userNationality==null || userNationality==''){
		$('#ss1').combobox('select', '3');// 菜单项可以text或者id
	}else{
		$('#ss1').combobox('select', userNationality);// 菜单项可以text或者id
	}
});
//提交保存
function submitAdd() {
	var orgId=$("#orgId").val();
	if(orgId==null ||orgId == ''){
		alert("请选择所属部门");
		return ;
	}
	var t1 = $('#zhijiTree').combotree('tree'); // 获取树对象
	var n1 = t1.tree('getSelected'); // 获取选择的节点
	$("#rankName").val(n1.text);
	var positionName=$("#positionId").find("option:selected").text();
	$("#positionName").val(positionName);
	//手机号码验证
	var userPhone=$("#userPhone").val();
	var phontSpan=vailUserCellphone(userPhone);
	if(phontSpan != ''){
		alert(phontSpan);
		return ;
	}
	//验证电话号码的唯一性
	var userId=$("#userId").val();
	var falg=true;
	$.ajax({
		async:false,
		url:contextpath+"/userIndex/checkUserPhone.do",
		data:{"userPhone":userPhone,"userId":userId},
		type:"post", 
		success:function(data){
			if(data=='error'){
				alert("手机号码已被使用");
				falg=false;
			}
		}
	});
	if(!falg){
		return;
	}
	//邮箱验证
	var userMail=$("#userMail").val();
	var userMailSpan=vailUserMail(userMail);
	if(userMailSpan != ''){
		alert(userMailSpan);
		return ;
	}
	//验证身份证号
	var cardNumer=$("#cardNumer").val();
	var cardNumerSpan=test(cardNumer);
	if(cardNumerSpan!= '验证通过'){
		alert(cardNumerSpan);
		return ;
	}
	//
	$('#hh').form({
		url : contextpath + '/userIndex/saveUser',
		onSubmit : function(param) {
			param.userBir = $('#userBirthday').datebox('getValue');
			param.odate = $('#overdate').datebox('getValue');
			// param.orgName=$('#orgName').html();
			return $(this).form('validate');
		},
		success : function(data) {
			if (data == 'succ') {
				changeTab();
			} else if (data == 'snnameExists') {
				$.messager.alert("提示", "用户名已经存在，请重新输入!");
			}
		}
	});
	$('#hh').submit();
}
function changeZhiWu(){
	var t1 = $('#zhijiTree').combotree('tree'); // 获取树对象
	var n1 = t1.tree('getSelected'); // 获取选择的节点
	var rankId=n1.id;
	var positionIdTwo=$("#positionIdTwo").val();
	$.ajax({
		async:false,
		url:contextpath+"/userIndex/getZhiwu.do?zhijiId="+rankId,
		type:'post',
		success:function(data){
			var dataJSON=$.parseJSON(data);
			$('#positionId').empty();
			$('#positionId').append("<option value='' selected = 'true'></option>");
			for (i = 0; i < dataJSON.length; i++){
				if(positionIdTwo !=null && positionIdTwo != '' && positionIdTwo == dataJSON[i]['rankId']){
					$('#positionId').append("<option value='"+dataJSON[i]['rankId']+"' selected='selected'>"+dataJSON[i]['rankName']+"</option>");
				}else{
					$('#positionId').append("<option value='"+dataJSON[i]['rankId']+"'>"+dataJSON[i]['rankName']+"</option>");
				}
			}
		}
	});
}
function changeTab() {
	var url = contextpath +'/userIndex/userList';
	parent.addTab_update('用户管理', url, 'icon-save');
	parent.$('#tabs').tabs('close', '添加用户');
	parent.$('#tabs').tabs('close', '编辑用户');
}
function cancleAdd(){
	parent.$('#tabs').tabs('close', '添加用户');
	parent.$('#tabs').tabs('close', '编辑用户');
}