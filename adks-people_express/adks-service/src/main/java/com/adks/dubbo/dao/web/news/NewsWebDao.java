package com.adks.dubbo.dao.web.news;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.news.Adks_news;
import com.adks.dubbo.commons.Page;

@Component
public class NewsWebDao extends BaseDao {

    @Override
    protected String getTableName() {
        return "adks_news";
    }

    public Map<String, Object> getNewsInfoById(Integer newsId) {
        String sql = " select * from adks_news where 1=1 and newsId=" + newsId;
        return mysqlClient.queryForMap(sql, new Object[] {});
    }

    //获取最新新闻
    public List<Adks_news> getTopCmsArticleList(Map map) {
        String sql = "select n1.newsId,n1.newsTitle ,n1.newsContent ,n1.newsSortType,n1.newsFocusPic,n1.newsType,n1.newsLink,n1.newsSortId,n1.newsSortName,n1.newsHtmlAdress,n1.orgId,n1.gradeId,n1.creatorId,"
                + "n1.creatorName,n1.createTime from adks_news n1,adks_news_sort n2 "
                + "where n1.newsSortId=n2.newsSortId and n1.gradeId is null ";
        if (map.get("orgId") != null && !"".equals(map.get("orgId"))) {
            sql += " and (n1.orgId=" + map.get("orgId")+" or n1.newsBelong=1)";
        }
        if(map.get("newsSortId")!=null && !"".equals(map.get("newsSortId"))){
        	sql += " and n1.newsSortId="+map.get("newsSortId");
        }
//        if (map.get("newsSortType") != null && !"".equals(map.get("newsSortType"))) {
//            sql += " and n2.newsSortType=" + map.get("newsSortType");
//        }
        sql += " order by n1.createTime desc limit 0," + map.get("num");
        List<Adks_news> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_news>() {
            @Override
            public Adks_news mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_news news = new Adks_news();
                news.setNewsId(rs.getInt("newsId"));
                news.setNewsTitle(rs.getString("newsTitle"));
                news.setNewsContent(rs.getBytes("newsContent"));
                news.setNewsFocusPic(rs.getString("newsFocusPic"));
                news.setNewsType(rs.getInt("newsType"));
                news.setNewsLink(rs.getString("newsLink"));
                news.setNewsSortId(rs.getInt("newsSortId"));
                news.setNewsSortName(rs.getString("newsSortName"));
                news.setNewsHtmlAdress(rs.getString("newsHtmlAdress"));
                news.setOrgId(rs.getInt("orgId"));
                news.setGradeId(rs.getInt("gradeId"));
                news.setCreatorId(rs.getInt("creatorId"));
                news.setCreatorName(rs.getString("creatorName"));
                news.setCreateTime(rs.getDate("createTime"));
                news.setNewsSortType(rs.getInt("newsSortType"));
                return news;
            }
        });
        if (reslist == null || reslist.size() <= 0)
            return null;
        return reslist;
    }

    public Adks_news getNewsById(Integer newsId) {
        String sql = "select newsId,newsTitle,newsFocusPic,newsType,newsLink,newsSortId,newsSortName,newsHtmlAdress,orgId,gradeId,creatorId,creatorName,createTime from adks_news where 1=1"
                + " and newsId=" + newsId;
        List<Adks_news> reslist = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_news>() {
            @Override
            public Adks_news mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_news news = new Adks_news();
                news.setNewsId(rs.getInt("newsId"));
                news.setNewsTitle(rs.getString("newsTitle"));
                //news.setNewsContent(rs.getBytes("newsContent"));
                news.setNewsFocusPic(rs.getString("newsFocusPic"));
                news.setNewsType(rs.getInt("newsType"));
                news.setNewsLink(rs.getString("newsLink"));
                news.setNewsSortId(rs.getInt("newsSortId"));
                news.setNewsSortName(rs.getString("newsSortName"));
                news.setNewsHtmlAdress(rs.getString("newsHtmlAdress"));
                news.setOrgId(rs.getInt("orgId"));
                news.setGradeId(rs.getInt("gradeId"));
                news.setCreatorId(rs.getInt("creatorId"));
                news.setCreatorName(rs.getString("creatorName"));
                news.setCreateTime(rs.getDate("createTime"));
                return news;
            }
        });
        if (reslist == null || reslist.size() <= 0)
            return null;
        return reslist.get(0);
    }

    //前台界面分页
    public Page<List<Adks_news>> getNewsListPageWeb(Page<List<Adks_news>> page) {
        Integer offset = null; //limit 起始位置
        if (page.getCurrentPage() <= 1) {
            offset = 0;
            page.setCurrentPage(1);
        }
        else {
            offset = (page.getCurrentPage() - 1) * page.getPageSize();
        }
        StringBuffer sqlbuffer = new StringBuffer(
                "select newsId,newsTitle ,newsContent ,newsSortType,newsFocusPic,newsType,newsLink,newsSortId,"
                        + "newsSortName,newsHtmlAdress,orgId,gradeId,creatorId,creatorName,createTime from adks_news where 1=1 ");
        StringBuffer countsql = new StringBuffer("select count(1) from adks_news where 1=1 ");
        Map map = page.getMap();
        if (map != null && map.size() > 0) {
            //添加查询条件 。。
            if (map.get("newsSortId") != null) {
                sqlbuffer.append(" and newsSortId=" + map.get("newsSortId"));
                countsql.append(" and newsSortId=" + map.get("newsSortId"));
            }
            if (map.get("newsTitle") != null) {
                sqlbuffer.append(" and newsTitle like '%" + map.get("newsTitle") + "%'");
                countsql.append(" and newsTitle like '%" + map.get("newsTitle") + "%'");
            }
            if (map.get("orgId") != null) {
                sqlbuffer.append(" and (orgId=" + map.get("orgId")+" or newsBelong=1)");
                countsql.append(" and (orgId=" + map.get("orgId")+" or newsBelong=1)");
            }
            if (map.get("orgCode") != null) {
                sqlbuffer.append(" and (orgCode like '" + map.get("orgCode") + "%'"+" or newsBelong=1)");
                countsql.append(" and (orgCode like '" + map.get("orgCode") + "%'"+" or newsBelong=1)");
            }
        }

        //分页
        sqlbuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        String sql = sqlbuffer.toString();

        List<Adks_news> newsList = mysqlClient.query(sql, new Object[0], new RowMapper<Adks_news>() {
            @Override
            public Adks_news mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adks_news news = new Adks_news();
                news.setNewsId(rs.getInt("newsId"));
                news.setNewsTitle(rs.getString("newsTitle"));
                news.setNewsContent(rs.getBytes("newsContent"));
                news.setNewsFocusPic(rs.getString("newsFocusPic"));
                news.setNewsType(rs.getInt("newsType"));
                news.setNewsLink(rs.getString("newsLink"));
                news.setNewsSortId(rs.getInt("newsSortId"));
                news.setNewsSortName(rs.getString("newsSortName"));
                news.setNewsHtmlAdress(rs.getString("newsHtmlAdress"));
                news.setOrgId(rs.getInt("orgId"));
                news.setGradeId(rs.getInt("gradeId"));
                news.setNewsSortType(rs.getInt("newsSortType"));
                news.setCreatorId(rs.getInt("creatorId"));
                news.setCreatorName(rs.getString("creatorName"));
                news.setCreateTime(rs.getDate("createTime"));
                return news;
            }
        });

        //查询总记录
        Integer totalcount = mysqlClient.queryforInt(countsql.toString(), new Object[0]);
        page.setTotal(totalcount);
        page.setTotalRecords(totalcount);
        if (totalcount % page.getPageSize() == 0) {
            page.setTotalPage(totalcount / page.getPageSize());
            page.setTotalPages(totalcount / page.getPageSize());
        }
        else {
            page.setTotalPage(totalcount / page.getPageSize() + 1);
            page.setTotalPages(totalcount / page.getPageSize() + 1);
        }

        page.setRows(newsList);
        return page;
    }

    public List<Adks_news> queryAll() {
        List<Adks_news> list = new ArrayList<>();
        String sql = "select newsId,newsTitle ,newsContent ,newsSortType,newsFocusPic,newsType,newsLink,newsSortId,newsSortName,newsHtmlAdress,orgId,gradeId,creatorId,creatorName,createTime from adks_news";
        List<Map<String, Object>> queryList = mysqlClient.queryList(sql);
        String createdate = null;
        try {
            Adks_news news = null;
            for (Map<String, Object> map : queryList) {
                news = new Adks_news();
                if (null != map.get("createtime")) {
                    createdate = (String) map.get("createtime").toString();
                }
                map.remove("createtime");
                news.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdate));
                BeanUtils.populate(news, map);
                list.add(news);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
