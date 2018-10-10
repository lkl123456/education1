package com.adks.dubbo.api.interfaces.web.search;

import com.adks.dubbo.commons.SearchResult;

public interface SearchApi {

	SearchResult search(String queryString, String queryType, Integer page, Integer rows);
}
