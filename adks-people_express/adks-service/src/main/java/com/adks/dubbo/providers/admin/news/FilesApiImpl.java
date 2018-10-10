package com.adks.dubbo.providers.admin.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.common.Adks_files;
import com.adks.dubbo.api.interfaces.admin.news.FilesApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.news.FilesService;

public class FilesApiImpl implements FilesApi{
	
	@Autowired
	private FilesService filesService;
	
	@Override
	public Page<List<Map<String, Object>>> getFilesListPage(Page<List<Map<String, Object>>> page) {
		return filesService.getFilesListPage(page);
	}

	@Override
	public Integer saveFiles(Adks_files adksFiles) {
		Map<String, Object> insertColumnValueMap = new HashMap<String,Object>();
		insertColumnValueMap.put("filesName", adksFiles.getFilesName());
		insertColumnValueMap.put("filesType", adksFiles.getFilesType());
		insertColumnValueMap.put("filesDes", adksFiles.getFilesDes());
		insertColumnValueMap.put("filesUrl", adksFiles.getFilesUrl());
		insertColumnValueMap.put("gradeId", adksFiles.getGradeId());
		insertColumnValueMap.put("gradeName", adksFiles.getGradeName());
		insertColumnValueMap.put("orgId", adksFiles.getOrgId());
		insertColumnValueMap.put("orgName", adksFiles.getOrgName());
		insertColumnValueMap.put("orgCode", adksFiles.getOrgCode());
		insertColumnValueMap.put("filesBelong", adksFiles.getFilesBelong());
		insertColumnValueMap.put("creatorId", adksFiles.getCreatorId());
		insertColumnValueMap.put("creatorName", adksFiles.getCreatorName());
		insertColumnValueMap.put("createTime", adksFiles.getCreateTime());
		if(adksFiles.getDownloadNum() == null){
			insertColumnValueMap.put("downloadNum", 0);
		}else{
			insertColumnValueMap.put("downloadNum", adksFiles.getDownloadNum());
		}
		Integer fileId=0;
		if(adksFiles.getFilesId() != null){
			Map<String, Object> updateWhereConditionMap = new HashMap<String,Object>();
			updateWhereConditionMap.put("filesId", adksFiles.getFilesId());
			fileId=filesService.update(insertColumnValueMap, updateWhereConditionMap);
		}else{
			fileId=filesService.insert(insertColumnValueMap);
		}
		return fileId;
	}

	@Override
	public void deleteFilesByIds(String filesIds) {
		filesService.deleteFilesByIds(filesIds);
	}

	@Override
	public Map<String, Object> getFilesInfoById(Integer filesId) {
		return filesService.getFilesInfoById(filesId);
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
	@Override
	public Map<String, Object> checkGradeFilesName(Map<String, Object> map) {
		return filesService.checkGradeFilesName(map);
	}
}
