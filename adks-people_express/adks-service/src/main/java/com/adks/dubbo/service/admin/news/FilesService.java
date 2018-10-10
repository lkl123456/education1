package com.adks.dubbo.service.admin.news;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.news.FilesDao;

@Service
public class FilesService extends BaseService<FilesDao>{

	@Autowired
	private FilesDao filesDao;
	
	@Override
	protected FilesDao getDao() {
		return filesDao;
	}

	public Page<List<Map<String, Object>>> getFilesListPage(Page<List<Map<String, Object>>> page) {
		return filesDao.getFilesListPage(page);
	}
	

	public void deleteFilesByIds(String filesIds) {
		filesDao.deleteFilesByIds(filesIds);
	}

	public Map<String, Object> getFilesInfoById(Integer filesId) {
		return filesDao.getFilesInfoById(filesId);
	}
	
	/**
	 * 
	 * @Title checkGradeFilesName
	 * @Description:检查班级资料名在该班级中的唯一性
	 * @author xrl
	 * @Date 2017年5月9日
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkGradeFilesName(Map<String, Object> map){
		return filesDao.checkGradeFilesName(map);
	}
}
