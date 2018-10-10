package com.adks.dubbo.providers.web.search;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.interfaces.web.search.SearchApi;
import com.adks.dubbo.commons.SearchResult;
import com.adks.dubbo.service.web.search.SearchService;

public class SearchApiImpl implements SearchApi {

    @Autowired
    private SearchService searchService;

    @Override
    public SearchResult search(String queryString, String queryType,Integer page, Integer rows) {
        return searchService.search(queryString,queryType, page, rows);
    }
}
