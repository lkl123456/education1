package com.adks.dubbo.api.interfaces.app.author;

import com.adks.dubbo.api.data.author.Adks_author;

public interface AuthorAppApi {
	/**
	 * 根据讲师id获取讲师信息
	 * 
	 * @param authorId
	 * @return
	 */
	public Adks_author getAuthorById(int authorId);
}
