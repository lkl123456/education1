package com.adks.dubbo.providers.app.author;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.interfaces.app.author.AuthorAppApi;
import com.adks.dubbo.service.app.author.AuthorAppService;

public class AuthorAppApiImpl implements AuthorAppApi {

	@Autowired
	private AuthorAppService authorService;

	/**
	 * 根据讲师id获取讲师信息
	 * 
	 * @param authorId
	 * @return
	 */
	public Adks_author getAuthorById(int authorId) {
		return authorService.getAuthorById(authorId);
	}
}
