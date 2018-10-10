
//判断数组arr中是否包含指定的item值
function containsTarget(arrs,item){
	for(var i = 0; i < arrs.length; i++){
		if(arrs[i] == item){
			return true;
		}
	}
	return false;
}
//判断数组是否是空
function isEmpty(menus){
	for(var i = 0; i < menus.length; i++){
		if(menus[i] != "" && menus[i] != null && menus[i] != undefined){
			return false;
		}
	}
	return true;
}

//整理数据menu
function operationMenu(menus,userPermisstions){
	var arr0 = userPermisstions.split("");
	$.each(menus, function(j, m) {
		if(m.menus.length > 0){
			var lengths = m.menus.length;
			for(var k = 0; k < lengths; k++){
				var hasnum = containsTarget(arr0,m.menus[k].menuid);
				if(!hasnum){
					var newarr = m.menus.splice(k,1,"");
				}
			}
		}
		if(isEmpty(m.menus)){
			menus.splice(j,1,"{}");
		}
	});
	return menus;
}


function InitLeftMenu(userPermisstions) {
    $(".easyui-accordion").empty();
    var menulist = '<div class="easyui-accordion" data-options="fit:true,border:false" >';
    //根据权限重新整理menu数据
    menus = operationMenu(menus,userPermisstions);
    $.each(menus, function(i, n) {
		if(n.menuname != null && n.menuname != '' && n.menuname != undefined ){
			
			menulist += '<div title="'+n.menuname+'" style="overflow:auto;" iconCls="'+n.icon+'"><ul>';
			$.each(n.menus, function(j, o) {
				if(o.menuname != null && o.menuname != '' && o.menuname != undefined ){
					menulist += '<li><div><a ref="'+o.menuid+'" href="#" rel="' + o.url + '" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.menuname + '</span></a></div></li> ';
				}
			})
			menulist += '</ul></div>';
		}
				
    })
    
	menulist += "</div>";
	$("#accordion").append(menulist);
	
	$('.easyui-accordion li a').click(function(){
		var tabTitle = $(this).text();
		tabTitle = $.trim(tabTitle); 
		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = getIcon(menuid,icon);
		addTab(tabTitle,url,icon);
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});
		
	
	$(".easyui-accordion").accordion();
}

addTab = function(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			iconCls:icon,
			content:createFrame(url),
			closable:true
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
//	tabCloseEven();
}
//如果已经打开tab,则用当前url刷新目标tab
addTab_update = function(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			iconCls:icon,
			content:createFrame(url),
			closable:true
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		var currTab = $('#tabs').tabs('getSelected');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		})
	}
	tabClose();
}


createFrame = function(url){
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

msgShow = function(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

getIcon = function (menuid){
	var icon = 'icon ';
	$.each(menus, function(i, n) {
		if(n.menuname != null && n.menuname != '' && n.menuname != undefined ){
			
			$.each(n.menus, function(j, o) {
				if(o.menuname != null && o.menuname != '' && o.menuname != undefined ){
					
					if(o.menuid==menuid){
						icon += o.icon;
					}
				}
			})
		}
	})
	
	return icon;
}

tabClose = function (){	
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})	
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
				
		var subtitle =$(this).children(".tabs-closable").text();		
		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}

tabCloseEven = function(){
		
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		})
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});	
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		$('#mm-tabcloseright').click();		
		$('#mm-tabcloseleft').click();
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

closePwd = function () {
            	$('#updatePass').window('close');
        	}

serverLogin = function () {
	
	            var $newpass = $('#newPass');
	            var $rePass = $('#rePass');
				
	            if ($newpass.val() == '') {
	                msgShow('系统提示', '请输入密码！', 'warning');
	                return false;
	            }
	            if ($rePass.val() == '') {
	                msgShow('系统提示', '请在一次输入密码！', 'warning');
	                return false;
	            }
	            if ($newpass.val() != $rePass.val()) {
	                msgShow('系统提示', '两次密码不一致！请重新输入', 'warning');
	                return false;
	            }
	            $.post('main.jsp?aa=' + $newpass.val(), function(msg) {
	                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
	                $newpass.val('');
	                $rePass.val('');
					openPwd();
	            })	            
        	}			