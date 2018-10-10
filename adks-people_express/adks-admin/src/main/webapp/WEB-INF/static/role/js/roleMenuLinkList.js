//全局的menuLinkRole数据
var menuLinkTpRoleIds='';
//之前选中的角色
var checkRoleId='';

$(function(){
	//$.fn.zTree.init($("#treeDemo"), setting);
	$('#roleList').combobox({    
	    url:contextpath+"/roleMenuLink/getRolesJson", 
	    valueField:'id',    
	    textField:'text',
	    /*onLoadSuccess:function(){
	    	$('#roleList').combobox('select','1');//菜单项可以text或者id
	    }*/
	    onSelect:function(param){
	    	if(param!=null){
	    		checkDatalig(param.id);
	    	}else{
	    		menuLinkTpRoleIds='';
	    		checkRoleId='';
	    	}
	    }
	});
	
});

//表格加载成功
function datagridSucc(){
	var roleList=$('#roleList').combobox('getValue');
	//alert(roleList);
	if(checkRoleId!=roleList){
		checkDatalig(roleList)
	}else{
		datagridCheck(menuLinkTpRoleIds);
	}
}
//复选框选中方法
function checkDatalig(roleId){
	 $('#menuLinklistdatagrid').datagrid("clearSelections"); 
	 checkRoleId=roleId;
	if(roleId!=null && roleId!=''){
		var ids='';
		$.ajax({
			url: contextpath+'/roleMenuLink/getMenuLinkIdByRole',
			async: false,
			type: "post",
			data: {"roleId":roleId},
			success: function (data) {
				if(data !=null && ''!=data ){
					ids=data;
				}
			},error:function(){
				alert('error');
			}
		});
		menuLinkTpRoleIds=ids;
		//alert(ids);
		datagridCheck(ids);
	}
}
function datagridCheck(ids){
	if(ids!=null && ids!=''){
		var id=ids.split(",");
		var  arrRow=$('#menuLinklistdatagrid').datagrid('getRows');
		if(arrRow!=null && arrRow.length>0){
			for(var r=0;r<arrRow.length;r++){
				var idNum=arrRow[r].menuLinkId;
				for(var i=0;i<id.length;i++){
					if(id[i]==idNum){
						$("#menuLinklistdatagrid").datagrid("selectRow",r);
					}
				}
			}
		}
	}
}

// 查询按钮点击
function doSearch() {
	var s_menuName=encodeURI($('#s_menuName').val());
	var s_menuLinkName=encodeURI($('#s_menuLinkName').val());
	$("#menuLinklistdatagrid").datagrid('load', {
		s_menuName : s_menuName,
		s_menuLinkName:s_menuLinkName
	});
}

//提交保存
function delDialog(){
	var t_url= contextpath+"/roleMenuLink/delMenuLinkRole";
	var selectRows = $('#menuLinklistdatagrid').datagrid('getSelections');
	if (selectRows ==null || selectRows.length <= 0) {  
		$.messager.alert('提示','请至少选中一条菜单链接','warning');
		return ;
	}
	var role=$('#roleList').combobox('getValue');
	if(role ==null || role ==''){
		$.messager.alert('提示','请选中一个角色','warning');
		return ;
	}
	
	$.messager.confirm("确认", "您确定要取消关联吗？", function (r) {  
        if (r) {  
        	var strIds = "";  
            for (var i = 0; i < selectRows.length; i++) {  
                strIds += selectRows[i].menuLinkId + ",";  
            }  
            strIds = strIds.substr(0, strIds.length - 1); 
            
            $.ajax({
        		url: contextpath+"/roleMenuLink/delMenuLinkRole",
        		async: false,
        		type: "post",
        		data: {"menuLinkIds":strIds,"roleId":role},
        		success: function (data) {
        			if (data == "succ") {  
                        //刷新表格，去掉选中状态的 那些行。  
                    	$('#roleList').combobox('select','');//菜单项可以text或者id
                    }else {  
                        $.messager.alert("操作失败~~", data);  
                    }  
        		},error:function(){
        		}
        	});
            $('#menuLinklistdatagrid').datagrid("clearSelections");
        }  
    });  
	
}


//提交保存
function addDialog(){
	var selectRows = $('#menuLinklistdatagrid').datagrid('getSelections');
	if (selectRows ==null || selectRows.length <= 0) {  
		$.messager.alert('提示','请至少选中一条菜单链接','warning');
		return ;
	}
	var roleId=$('#roleList').combobox('getValue');
	if(roleId ==null || roleId ==''){
		$.messager.alert('提示','请选中一个角色','warning');
		return ;
	}
	var strIds = "";  
    for (var i = 0; i < selectRows.length; i++) {  
        strIds += selectRows[i].menuLinkId + ",";  
    }  
    strIds = strIds.substr(0, strIds.length - 1);  
    
	$.ajax({
		url: contextpath+'/roleMenuLink/saveMenuLinkRole',
		async: false,
		type: "post",
		data: {"roleId":roleId,"menuLinkIds":strIds},
		success: function (data) {
			alert("关联成功");
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

