package com.adks.dubbo.api.interfaces.admin.author;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.commons.Page;

public interface AuthorApi {
	public void deleteAuthor(String ids);
	
	public Adks_author getAuthorByName(Map<String, Object> map);
	
	public Adks_author getAuthorById(Integer authorId);
	
	public Page<List<Map<String, Object>>> getAuthorListPage(Page<List<Map<String, Object>>> page);
	
	public boolean courseByauthorId(Integer authorId);
	
	public Integer saveAuthor(Adks_author author);
	
	//若作者名称修改了，同步到课程
	public void checkCourseNameByAuthor(Integer authorId,String authorName);
}
