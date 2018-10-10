package com.adks.dubbo.service.admin.author;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.author.Adks_author;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.author.AuthorDao;

@Service
public class AuthorService extends BaseService<AuthorDao> {

	@Autowired
	private SolrServer solrServer;

	@Autowired
	private AuthorDao authorDao;

	@Override
	protected AuthorDao getDao() {
		return authorDao;
	}

	public void deleteAuthor(String ids) {
		authorDao.deleteAuthor(ids);
	}

	public Adks_author getAuthorByName(Map<String, Object> map) {
		return authorDao.getAuthorByName(map);
	}

	public Page<List<Map<String, Object>>> getAuthorListPage(Page<List<Map<String, Object>>> page) {
		return authorDao.getAuthorListPage(page);
	}

	public boolean courseByauthorId(Integer authorId) {
		return authorDao.courseByauthorId(authorId);
	}

	// 若作者名称修改了，同步到课程
	public void checkCourseNameByAuthor(Integer authorId, String authorName) {
		authorDao.checkCourseNameByAuthor(authorId, authorName);
	}

	public Adks_author getAuthorById(Integer authorId) {
		return authorDao.getAuthorById(authorId);
	}

	public void initData() {
		List<Adks_author> list = authorDao.queryAll();
		try {
			for (Adks_author author : list) {
				SolrInputDocument document = new SolrInputDocument();

				document.setField("id", "authorId_" + author.getAuthorId());
				document.setField("authorId", author.getAuthorId());
				document.setField("orgId", author.getOrgId());
				document.setField("authorName", author.getAuthorName());
				document.setField("authorPhoto", author.getAuthorPhoto());
				document.setField("authorSex", author.getAuthorSex());
				document.setField("authorDes", author.getAuthorDes());
				document.setField("longTime", author.getCreateTime().getTime());
				document.setField("objectType", 4);
				// 写入索引库
				solrServer.add(document);
			}
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
