package com.adks.dubbo.dao.admin.menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.commons.util.DateUtils;
import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.menu.Adks_menulink;
import com.adks.dubbo.commons.Page;

@Component
public class MenuLinkDao extends BaseDao {

    @Override
    protected String getTableName() {
        return "adks_menulink";
    }

    //菜单链接分页
    public Page<List<Map<String, Object>>> getMenuLinkListPage(Page<List<Map<String, Object>>> page) {
        Integer offset = (page.getCurrentPage() - 1) * page.getPageSize(); //limit 起始位置
        StringBuffer sqlbuffer = new StringBuffer("select ml.menuLinkId,ml.menuLinkName,ml.linkUrl,ml.mLinkDisplay,ml.menuId,m.menuName"
                + ",ml.orderNum,ml.creatorId,ml.creatorName,ml.createTime from adks_menulink ml,adks_menu m where m.menuId=ml.menuId and ml.menuLinkId>0 ");
        StringBuffer countsql = new StringBuffer("select count(ml.menuLinkId) from adks_menulink ml,adks_menu m where m.menuId=ml.menuId and ml.menuLinkId>0 ");
        Map map = page.getMap();
        if (map != null && map.size() > 0) {
            //添加查询条件 。。
            if (map.get("menuLinkName") != null) {
                sqlbuffer.append(" and ml.menuLinkName like '%" + map.get("menuLinkName") + "%'");
                countsql.append(" and ml.menuLinkName like '%" + map.get("menuLinkName") + "%'");
            }
            if (map.get("menuName") != null) {
                sqlbuffer.append(" and m.menuName like '%" + map.get("menuName") + "%' ");
                countsql.append(" and m.menuName like '%" + map.get("menuName") + "%' ");
            }
            if (map.get("menuId") != null) {
                sqlbuffer.append(" and m.menuId =" + map.get("menuId") + " ");
                countsql.append(" and m.menuId =" + map.get("menuId") + " ");
            }
        }

        sqlbuffer.append(" order by ml.orderNum ");
        countsql.append(" order by ml.orderNum ");

        //分页
        sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        String sql = sqlbuffer.toString();
        List<Map<String, Object>> userlist = mysqlClient.queryForList(sql, new Object[0]);
        for (Map<String, Object> map2 : userlist) {
            if (map2.get("createTime") != null) {
                map2.put("createTime", DateUtils.getDate2SStr(DateUtils.getStr2LDate(map2.get("createTime").toString())));
            }
        }
        //查询总记录
        Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
        page.setTotal(totalcount);
        page.setRows(userlist);
        return page;
    }

    //菜单链接删除
    public void deleteMenuLinkById(String ids) {
        String sql = "delete from adks_menulink where menuLinkId in ( " + ids + " )";
        mysqlClient.update(sql, new Object[] {});
    }

    public Map<String, Object> getMenuLinkByName(String menuLinkName) {
        String sql = "select menuLinkId,menuLinkName,linkUrl,mLinkDisplay,orderNum,menuId,creatorId,creatorName,createTime" + " from adks_menulink  where menuLinkName='" + menuLinkName + "'";

        List<Map<String, Object>> reslist = mysqlClient.queryForList(sql, new Object[0]);
        if (reslist == null || reslist.size() <= 0) {
            return null;
        }
        return reslist.get(0);
    }

    //首页登录获取所有的菜单
    public List<Adks_menulink> getMenuLinkAll() {
        String sql = "select menuLinkId,menuLinkName,linkUrl,menuId" + " from adks_menulink order by orderNum";
        List<Adks_menulink> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_menulink>() {
            @Override
            public Adks_menulink mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_menulink menulink = new Adks_menulink();
                menulink.setMenuLinkId(rs.getInt("menuLinkId"));
                menulink.setMenuid(rs.getInt("menuLinkId"));
                menulink.setMenuId(rs.getInt("menuId"));
                menulink.setMenuname(rs.getString("menuLinkName"));
                menulink.setIcon("icon-" + menulink.getMenuLinkId());
                menulink.setUrl(rs.getString("linkUrl"));
                ;
                return menulink;
            }
        });
        if (reslist == null || reslist.size() <= 0)
            return null;
        return reslist;
    }

    public List<Adks_menulink> getMenuLinkByRoleList(List<Integer> roleIdList) {
        String id = roleIdList.toString();
        id = id.substring(1, id.length() - 1);
        String sql = "SELECT DISTINCT am.* FROM adks_role_menu_link arml LEFT JOIN adks_menulink am  ON arml.menuLinkId = am.menuLinkId WHERE arml.menuLinkId in(am.menuLinkId) AND roleId in (" + id
                + ")";
        List<Map<String, Object>> queryForList = mysqlClient.queryList(sql);
        List<Adks_menulink> list = new ArrayList<>();
        Adks_menulink adks_menulink = null;
        try {
            for (Map<String, Object> map : queryForList) {
                adks_menulink = new Adks_menulink();
                String createdate = null;
                if (null != map.get("createTime")) {
                    createdate = (String) map.get("createTime").toString();
                }
                map.remove("createTime");
                adks_menulink.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdate));
                BeanUtils.populate(adks_menulink, map);
                list.add(adks_menulink);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
