package com.adks.dubbo.dao.web.search;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 * solr的删除方法类
 * <b>Application name:</b>CSTP维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2017 CSTP部版权所有。<br>
 * <b>Company:</b>CSTP<br>
 * <b>Date:</b>2017年5月11日<br>
 * @author Author
 * @version $Revision$
 */
@Component
public class SolrDeleteDao {
    @Autowired
    private SolrServer solrServer;

    /**
     * {根据索引主键删除文档}
     * @param type 类型 分新闻news 课程course 专题 grade
     * @param typeId 对象类型的id值
     * 
     */
    public void deleteById(String type, int typeId) {
        String id = type + "_" + typeId;
        try {
            solrServer.deleteById(id);
            solrServer.commit();
        }
        catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {根据索引主键删除文档}
     * @param type 类型 分新闻news 课程course 专题 grade
     * @param typeId 对象类型的id值
     * 
     */
    public void deleteByIds(String type, String typeIds) {
        String[] split = typeIds.split(",");
        List<String> list = new LinkedList<>();
        for (int i = 0; i < split.length; i++) {
            String id = type + "_" + split[i];
            list.add(id);
        }
        try {
            solrServer.deleteById(list);
            solrServer.commit();
        }
        catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {删除所有文档}
     * 
     */
    public void deleteAll() {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        try {
            solrServer.deleteByQuery(query.toString());
            solrServer.commit();
        }
        catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }
}
