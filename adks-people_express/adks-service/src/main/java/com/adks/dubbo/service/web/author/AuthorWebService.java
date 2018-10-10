package com.adks.dubbo.service.web.author;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.dao.web.author.AuthorWebDao;

@Service
public class AuthorWebService extends BaseService<AuthorWebDao> {

    @Autowired
    private AuthorWebDao authorWebDao;

    @Override
    protected AuthorWebDao getDao() {
        return authorWebDao;
    }

    public Adks_author getAuthorById(Integer authorId) {
        return authorWebDao.getAuthorById(authorId);
    }

    public List<Adks_author> getTopHotAuthorList(Map map) {
        return authorWebDao.getTopHotAuthorList(map);
    }
}
