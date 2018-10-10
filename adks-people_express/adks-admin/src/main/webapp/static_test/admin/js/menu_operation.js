var menus = [
{
    "menuid": "1",
    "icon": "icon-man",
    "menuname": "基本设置",
    "menus": 
	[
	 {
        "menuid": "11",
        "menuname": "网校基本信息",
        "icon": "icon-nav",
        "url": "/org/toUpdateOrgInfo"
    }, 
    {
        "menuid": "12",
        "menuname": "网校个性化设置",
        "icon": "icon-nav",
        "url": "/org/orgIndexTemplate"
    },
   
    {
        "menuid": "13",
        "menuname": "管理员管理",
        "icon": "icon-nav",
        "url": "/admin/adminList"
    }
	,
    {
        "menuid": "13",
        "menuname": "修改密码",
        "icon": "icon-nav",
        "url": "role.html"
    }
    ]
},
{
    "menuid": "1",
    "icon": "icon-ok",
    "menuname": "用户管理",
    "menus": [{
        "menuid": "11",
        "menuname": "网校开通",
        "icon": "icon-nav",
        "url": "/org/orgList"
    }, {
        "menuid": "12",
        "menuname": "用户管理",
        "icon": "icon-nav",
        "url": "/user/userList"
    }
	]
}, {
    "menuid": "2",
    "icon": "icon-reload",
    "menuname": "课程管理",
    "menus": [{
        "menuid": "21",
        "menuname": "课程管理",
        "icon": "icon-nav",
        "url": "/course/courseList"
    }]
},
	{
    "menuid": "2",
    "icon": "icon-undo",
    "menuname": "培训管理",
    "menus": [{
        "menuid": "21",
        "menuname": "培训设置",
        "icon": "icon-nav",
        "url": "/grade/gradeList"
    },
    		{
        "menuid": "22",
        "menuname": "培训学员统计",
        "icon": "icon-nav",
        "url": "form.html"
    }
    ]
}

];
