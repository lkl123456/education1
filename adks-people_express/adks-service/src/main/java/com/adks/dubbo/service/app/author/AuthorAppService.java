package com.adks.dubbo.service.app.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.dao.app.author.AuthorAppDao;

@Service
public class AuthorAppService extends BaseService<AuthorAppDao> {

	@Autowired
	private AuthorAppDao authorDao;

	@Override
	protected AuthorAppDao getDao() {
		return authorDao;
	}
	/**
	 * 根据讲师id获取讲师信息
	 * 
	 * @param authorId
	 * @return
	 */
	public Adks_author getAuthorById(int authorId) {
		return authorDao.getAuthorById(authorId);
	}
}
