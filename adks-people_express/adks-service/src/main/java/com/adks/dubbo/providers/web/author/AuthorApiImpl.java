package com.adks.dubbo.providers.web.author;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.interfaces.web.author.AuthorApi;
import com.adks.dubbo.service.admin.author.AuthorService;
import com.adks.dubbo.service.web.author.AuthorWebService;

public class AuthorApiImpl implements AuthorApi {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorWebService authorWebService;

    @Override
    public Adks_author getAuthorById(Integer authorId) {
        return authorWebService.getAuthorById(authorId);
    }

    @Override
    public List<Adks_author> getTopHotAuthorList(Map map) {
        return authorWebService.getTopHotAuthorList(map);
    }

    @Override
    public void initData() {
        authorService.initData();

    }

}
