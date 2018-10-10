package com.adks.dubbo.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.commons.Page;

@Component
public class UserDao extends BaseDao {

    @Override
    protected String getTableName() {
        return "adks_user";
    }
    
    public Map<String, Object> getUserById(Integer arg0) {
    	String sql = "select * from adks_user where userId="+arg0;
        List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist.get(0);
    }

    //修改用户密码
    public Integer updatePassword(Integer userId, String password) {
    	 String sql = "update adks_user set userPassword='"+password+"' where userId =" + userId;
        Integer i= mysqlClient.update(sql, new Object[] {});
        return i;
	}
    
    public boolean isSuperManager(Integer userId) {
        String sql = "select urid from adks_userrole where userId=" + userId + " and roleId in (select roleId from adks_role where roleName='超级管理员')";
        Map<String, Object> map = mysqlClient.queryForMap(sql, new Object[0]);
        if (map != null && map.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUserName(String userName) {
        String sql = "select userId from adks_user where userName = '" + userName + "'";
        Map<String, Object> map = mysqlClient.queryForMap(sql, new Object[0]);
        if (map != null && map.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 自定义方法，获取部门dept json数据，拼接给zTree使用
     * @param parent
     * @return
     */
    public List<Map<String, Object>> queryDeptJson(Integer parent) {
        String sql = "select id,parent_id as pId,name from dept where 1=1";
        if (parent != null && !"".equals(parent)) {
            sql = sql + " and parent_id = " + parent;
        }
        List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist;
    }

    public Page<List<Map<String, Object>>> getUserListPage(Page<List<Map<String, Object>>> page) {
        Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
        StringBuffer sqlbuffer = new StringBuffer("select * from adks_user where 1=1 ");
        StringBuffer countsql = new StringBuffer("select count(1) from adks_user where 1=1 ");
        Map map = page.getMap();
        if (map != null && map.size() > 0) {
            //添加查询条件 。。
            if (map.get("orgCode") != null) {
                sqlbuffer.append(" and orgCode like '" + map.get("orgCode") + "%'");
                countsql.append(" and orgCode like '" + map.get("orgCode") + "%'");
            }
            if (map.get("userName") != null) {
                sqlbuffer.append(" and (userName like '%" + map.get("userName")+"%' or userRealName like '%"+map.get("userName")+"%')");
                countsql.append(" and (userName like '%" + map.get("userName") +"%' or userRealName like '%"+map.get("userName")+"%')");
            }
            //班级选学员，排除已选过的学员 id
            if (map.get("userIds") != null) {
                sqlbuffer.append(" and userId not in ( " + map.get("userIds") + " ) ");
                countsql.append(" and userId not in ( " + map.get("userIds") + " ) ");
            }
            //部门id信息筛选-班级选择学员列表 的学员部门树筛选条件
            if (map.get("orgIds") != null) {
                sqlbuffer.append(" and orgId  in ( " + map.get("orgIds") + " ) ");
                countsql.append(" and orgId  in ( " + map.get("orgIds") + " ) ");
            }
            
            //职务
            if (map.get("rankId") != null) {
                sqlbuffer.append(" and rankId=" + map.get("rankId"));
                countsql.append(" and rankId=" + map.get("rankId"));
            }
            
            if (map.get("positionId") != null) {
                sqlbuffer.append(" and positionId=" + map.get("positionId"));
                countsql.append(" and positionId=" + map.get("positionId"));
            }
            
            //用户状态
            if(map.get("userStatus")!=null){
            	 sqlbuffer.append(" and userStatus=" + map.get("userStatus"));
                 countsql.append(" and userStatus=" + map.get("userStatus"));
            }

        }

        //分页
        sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        String sql = sqlbuffer.toString();
        List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
        for (Map<String, Object> map2 : userlist) {
            if (map2.get("userBirthday") != null) {
                map2.put("userBirthday", DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("userBirthday").toString())));
            }
            if (map2.get("createdate") != null) {
                map2.put("createdate", DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("createdate").toString())));
            }
            if (map2.get("overdate") != null) {
                map2.put("overdate", DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("overdate").toString())));
            }
            if (map2.get("userPassword") != null) {
                map2.put("userPassword","111111");
            }
        }
        //查询总记录
        Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
        page.setTotal(totalcount);
        page.setRows(userlist);
        return page;
    }

    public void deleteUserByIds(String userIds) {
        String sql = "delete from adks_user where userId in ( " + userIds + " )";
        mysqlClient.update(sql, new Object[] {});
    }

    /*public Map<String, Object> getUserByName(String username) {
        Object[] obj = new Object[] { username };
        String sql = " select userId,userName,userPassword,headPhoto,userStatus,userRealName,orgId,orgName,orgCode  " + " from adks_user  where userName=? ";
        return mysqlClient.queryForMap(sql, obj);
    }*/

    public Map<String, Object> getUserByUserName(String username) {
        Object[] obj = new Object[] { username };
        String sql = " select * from adks_user  where userName=? ";
        return mysqlClient.queryForMap(sql, obj);
    }

    public List<Map<String, Object>> getUserRoleByUserId(Integer userId) {
        Object[] obj = new Object[] { userId };
        String sql = " select *  " + " from adks_userrole  where roleId!=4 and userId=?  order by roleId";
        return mysqlClient.queryForList(sql, obj);
    }

    public Map<String, Object> getFirstMenu() {
        String sql = " select * from adks_menu ";
        return mysqlClient.queryForMap(sql, new Object[0]);
    }

   /* public Map<String, Object> getUserInfoByUserId(Integer userId) {
        Object[] obj = new Object[] { userId };
        String sql = " select userId,userName,userRealName,orgId,orgName,orgCode from adks_user where userId=? ";
        return mysqlClient.queryForMap(sql, obj);
    }*/

    /**
     * 
     * @Title getSelGradeHeadTeacherPage
     * @Description：获取班主任角色的用户
     * @author xrl
     * @Date 2017年3月30日
     * @param page
     * @return
     */
    public Page<List<Map<String, Object>>> getSelGradeHeadTeacherPage(Page<List<Map<String, Object>>> page) {
        Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
        StringBuffer sqlbuffer = new StringBuffer("select * from adks_user u,adks_userrole ur " + "where ur.roleId=5 and ur.userId=u.userId");
        StringBuffer countsql = new StringBuffer("select count(1) from adks_user u,adks_userrole ur where ur.roleId=5 and ur.userId=u.userId ");
        Map map = page.getMap();
        if (map != null && map.size() > 0) {
            //添加查询条件 。。
            if (map.get("orgCode") != null) {
                sqlbuffer.append(" and u.orgCode like '" + map.get("orgCode") + "%'");
                countsql.append(" and u.orgCode like '" + map.get("orgCode") + "%'");
            }
            if (map.get("userName") != null) {
                sqlbuffer.append(" and (u.userRealName like '%" + map.get("userName") + "%' or u.userName like '%"+ map.get("userName") +"%')");
                countsql.append(" and (u.userRealName like '%" + map.get("userName") + "%' or u.userName like '%"+ map.get("userName") +"%')");
            }
            //班级选学员，排除已选过的学员 id
            if (map.get("userIds") != null) {
                sqlbuffer.append(" and u.userId not in ( " + map.get("userIds") + " ) ");
                countsql.append(" and u.userId not in ( " + map.get("userIds") + " ) ");
            }
            //部门id信息筛选-班级选择学员列表 的学员部门树筛选条件
            if (map.get("orgIds") != null) {
                sqlbuffer.append(" and u.orgId  in ( " + map.get("orgIds") + " ) ");
                countsql.append(" and u.orgId  in ( " + map.get("orgIds") + " ) ");
            }
            if(map.get("orgCodes")!=null){
            	sqlbuffer.append(" and u.orgCode like '" + map.get("orgCodes") + "%'");
                countsql.append(" and u.orgCode like '" + map.get("orgCodes") + "%'");
            }

        }

        //分页
        sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        String sql = sqlbuffer.toString();
        List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
        for (Map<String, Object> map2 : userlist) {
            if (map2.get("userBirthday") != null) {
                map2.put("userBirthday", DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("userBirthday").toString())));
            }
            if (map2.get("createdate") != null) {
                map2.put("createdate", DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("createdate").toString())));
            }
            if (map2.get("overdate") != null) {
                map2.put("overdate", DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("overdate").toString())));
            }
        }
        //查询总记录
        Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
        page.setTotal(totalcount);
        page.setRows(userlist);
        return page;
    }
    public boolean canDelUser(Integer userId) {
		String sql="select userId from adks_grade_user where userId="+userId;
		List<Map<String,Object>> list=mysqlClient.queryForList(sql, null);
		if(list!=null && list.size()>0){
			return false;
		}
		String sql1="select userId from adks_course_user where userId="+userId;
		List<Map<String,Object>> list1=mysqlClient.queryForList(sql1, null);
		if(list1!=null && list1.size()>0){
			return false;
		}
		return true;
	}
    public Map<String, Object> getUserInfoByPhone(String userPhone) {
    	String sql = "select userId,userName,userPhone from adks_user where userPhone='"+userPhone+"'";
        List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist.get(0);
	}
}
