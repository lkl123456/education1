//全局的userRole数据
var userRoleIds='';
/*$(function(){
	var userId=$('#userId').val();
	checkDatalig(userId);
})*/
//表格加载成功
function datagridSucc(){
	var userId=$('#userId').val();
	//alert(roleList);
	if(userRoleIds == ''){
		checkDatalig(userId)
	}
}
//复选框选中方法
function checkDatalig(userId){
	 $('#roleList_table').datagrid("clearSelections"); 
	if(userId!=null && userId!=''){
		var ids='';
		$.ajax({
			url: contextpath+'/userIndex/getUserRoleByUser',
			async: false,
			type: "post",
			data: {"userId":userId},
			success: function (data) {
				//alert(data);
				if(data !=null && ''!=data ){
					ids=data;
				}
			},error:function(){
				alert('error');
			}
		});
		userRoleIds=ids;
		datagridCheck(ids);
	}
}
function datagridCheck(ids){
	if(ids!=null && ids!=''){
		var id=ids.split(",");
		var  arrRow=$('#roleList_table').datagrid('getRows');
		if(arrRow!=null && arrRow.length>0){
			for(var r=0;r<arrRow.length;r++){
				var idNum=arrRow[r].roleId;
				//alert(idNum+"-"+ids.indexOf(idNum));
				if(ids.indexOf(idNum)>=0){
					$("#roleList_table").datagrid("selectRow",r);
				}
			}
		}
	}
}

function formatColumnData(value,row,index){
	if(value == 1){
		return '是';
	}else{
		return '否';
	}
}

function formatYes(value,row,index){
	if(value == '1'){
		if(row.roleName == '超级管理员'){
			$("#canAdd").val(1);
		}else{
			$("#canAdd").val(2);
		}
		return "<span style='color:red'>是</span>";
	}else{
		return "否";
	}
}

// 查询按钮点击
function doSearch() {
	var s_role_name=encodeURI($('#s_role_name').val());
	$("#roleList_table").datagrid('load', {
		s_role_name:s_role_name
	});
}

//提交保存
function delDialog(){
	var t_url= contextpath+"/userIndex/delUserRole";
	var selectRows = $('#roleList_table').datagrid('getSelections');
	if (selectRows ==null || selectRows.length <= 0) {  
		$.messager.alert('提示','请至少选中一个角色','warning');
		return ;
	}
	var userId=$("#userId").val();
	$.messager.confirm("确认", "您确定要取消关联吗？", function (r) {  
        if (r) {  
        	var strIds = "";  
            for (var i = 0; i < selectRows.length; i++) {  
                strIds += selectRows[i].roleId + ",";  
            }  
            strIds = strIds.substr(0, strIds.length - 1); 
            $.ajax({
        		url: contextpath+"/userIndex/delUserRole",
        		async: false,
        		type: "post",
        		data: {"userId":userId,"roleIds":strIds},
        		success: function (data) {
        			if (data == "succ") {  
                        //刷新表格，去掉选中状态的 那些行。 
        				userRoleIds='';
        				checkDatalig(userId);
        				$("#canAdd").val(0);
                    }else {  
                        $.messager.alert("操作失败~~", data);  
                    }  
        		},error:function(){
        		}
        	});
        }  
    });  
	
}


//提交保存
function addDialog(){
	var canAdd= $("#canAdd").val();
	if(canAdd == 1 || canAdd == '1'){
		$.messager.alert('提示','该用户已是超级管理员，不能再添加其它的角色','warning');
		return ;
	}
	var selectRows = $('#roleList_table').datagrid('getSelections');
	if (selectRows ==null || selectRows.length <= 0) {  
		$.messager.alert('提示','请选中一个角色','warning');
		return ;
	}
	var isOk=true;
	var strIds = "";  
    for (var i = 0; i < selectRows.length; i++) {  
        strIds += selectRows[i].roleId + ",";  
        if(selectRows[i].roleName == '超级管理员' && (canAdd == 2|| canAdd == '2')){
        	isOk=false;
        	break;
        }
    }  
    if(!isOk){
    	$.messager.alert('提示','该用户已有角色，不能再添加超级管理员角色','warning');
		return ;
    }
    strIds = strIds.substr(0, strIds.length - 1);  
    
    var userId=$("#userId").val();
    
	$.ajax({
		url: contextpath+'/userIndex/addUserRole',
		async: false,
		type: "post",
		data: {"userId":userId,"roleIds":strIds},
		success: function (data) {
			if(data == 'succ'){
				alert("添加成功");
				doSearch();
			}else{
				alert("添加失败，给用户设置的权限过高");
			}
		},error:function(){
			//alert('error');
		}
	});
}
//Date 格式化“yyyy-MM-dd”
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function setRowStyle(index,row){
	if(row.status == 2){
		return 'background-color:#cccccc;font-weight:bold;';
	}
}

