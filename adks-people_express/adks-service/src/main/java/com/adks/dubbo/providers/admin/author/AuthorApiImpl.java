package com.adks.dubbo.providers.admin.author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.api.data.org.Adks_org;
import com.adks.dubbo.api.interfaces.admin.author.AuthorApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.author.AuthorService;

public class AuthorApiImpl implements AuthorApi {
	
	@Autowired
	private AuthorService authorService;

	@Override
	public void deleteAuthor(String ids) {
		authorService.deleteAuthor(ids);
	}

	@Override
	public Adks_author getAuthorByName(Map<String, Object> map) {
		return authorService.getAuthorByName(map);
	}
	
	public Adks_author getAuthorById(Integer authorId){
		return authorService.getAuthorById(authorId);
	}

	@Override
	public Page<List<Map<String, Object>>> getAuthorListPage(Page<List<Map<String, Object>>> page) {
		return authorService.getAuthorListPage(page);
	}

	public boolean courseByauthorId(Integer authorId){
		return authorService.courseByauthorId(authorId);
	}
	
	public Integer saveAuthor(Adks_author author){
		Integer flag=0;
		//org
		Map<String,Object> insertColumnValueMap = new HashMap<String,Object>();
		insertColumnValueMap.put("authorName", author.getAuthorName());
		insertColumnValueMap.put("authorDes", author.getAuthorDes());
		insertColumnValueMap.put("authorFirstLetter", author.getAuthorFirstLetter());
		insertColumnValueMap.put("authorPhoto", author.getAuthorPhoto());
		insertColumnValueMap.put("authorSex", author.getAuthorSex());
		insertColumnValueMap.put("orgId", author.getOrgId());
		insertColumnValueMap.put("orgName", author.getOrgName());
		insertColumnValueMap.put("orgCode", author.getOrgCode());
		insertColumnValueMap.put("creatorName", author.getCreatorName());
		insertColumnValueMap.put("creatorId", author.getCreatorId());
		
		if(author.getAuthorId()!=null){
			
			Map<String,Object> updateWhereConditionMap = new HashMap<String,Object>();
			updateWhereConditionMap.put("authorId", author.getAuthorId());
			flag=authorService.update(insertColumnValueMap, updateWhereConditionMap);
			
		}else{
			insertColumnValueMap.put("createTime", author.getCreateTime());
			flag=authorService.insert(insertColumnValueMap);
		}
		return flag;
	}
	//若作者名称修改了，同步到课程
	public void checkCourseNameByAuthor(Integer authorId,String authorName){
		authorService.checkCourseNameByAuthor(authorId,authorName);
	}
}
