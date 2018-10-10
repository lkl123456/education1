var menus = [
{
    "icon": "icon-man",
    "menuname": "基本设置",
    "menus": 
	[
	 {
        "menuid": "1",
        "menuname": "网校基本信息",
        "icon": "icon-nav",
        "url": "/org/toUpdateOrgInfo"
    }, 
    {
        "menuid": "2",
        "menuname": "网校个性化设置",
        "icon": "icon-nav",
        "url": "/org/orgIndexTemplate"
    },
//	 {
//        "menuid": "11",
//        "menuname": "角色定义",
//        "icon": "icon-nav",
//        "url": "list-user.html"
//    }, 
   
    {
        "menuid": "3",
        "menuname": "管理员管理",
        "icon": "icon-nav",
        "url": "/admin/adminList"
    }
	,
//	{
//        "menuid": "13",
//        "menuname": "新闻广告",
//        "icon": "icon-nav",
//        "url": "role.html"
//    },
//    {
//        "menuid": "13",
//        "menuname": "在线状态",
//        "icon": "icon-nav",
//        "url": "role.html"
//    },
    {
        "menuid": "4",
        "menuname": "修改密码",
        "icon": "icon-nav",
        "url": "/admin/toChangePwd"
    }
    ]
},
{
    "icon": "icon-ok",
    "menuname": "用户管理",
    "menus": [{
        "menuid": "5",
        "menuname": "网校开通",
        "icon": "icon-nav",
        "url": "/org/orgList"
    }, {
        "menuid": "6",
        "menuname": "用户管理",
        "icon": "icon-nav",
        "url": "/user/userList"
    }
	]
}, {
    "icon": "icon-reload",
    "menuname": "课程管理",
    "menus": [{
        "menuid": "7",
        "menuname": "课程管理",
        "icon": "icon-nav",
        "url": "/course/courseList"
    }]
},
	{
    "icon": "icon-undo",
    "menuname": "培训管理",
    "menus": [{
        "menuid": "8",
        "menuname": "培训设置",
        "icon": "icon-nav",
        "url": "/grade/gradeList"
    },
    		{
        "menuid": "9",
        "menuname": "培训学员统计",
        "icon": "icon-nav",
        "url": "form.html"
    }
    ]
}

];
