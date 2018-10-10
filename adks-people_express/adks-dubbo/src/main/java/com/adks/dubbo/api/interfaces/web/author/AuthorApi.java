package com.adks.dubbo.api.interfaces.web.author;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.author.Adks_author;

public interface AuthorApi {

    public Adks_author getAuthorById(Integer authorId);

    //获取热门讲师
    public List<Adks_author> getTopHotAuthorList(Map map);

    public void initData();
}
