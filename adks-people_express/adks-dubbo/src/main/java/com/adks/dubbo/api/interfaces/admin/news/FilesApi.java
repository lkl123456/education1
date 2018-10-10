package com.adks.dubbo.api.interfaces.admin.news;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.common.Adks_files;
import com.adks.dubbo.commons.Page;

public interface FilesApi {
	/**
	 * 获取教参分页列表
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getFilesListPage(Page<List<Map<String, Object>>> page);
	
	/**
	 * 保存一条教参
	 * @param adksFiles
	 */
	public Integer saveFiles(Adks_files adksFiles);
	
	
	/**
	 * 批量删除教参
	 * @param filesIds
	 */
	public void deleteFilesByIds(String filesIds);
	
	/**
	 * 根据id获取教参
	 * @param filesId
	 * @return
	 */
	public Map<String, Object> getFilesInfoById(Integer filesId);
	
	/**
	 * 
	 * @Title checkGradeFilesName
	 * @Description:检查班级资料名在该班级中的唯一性
	 * @author xrl
	 * @Date 2017年5月9日
	 * @param map
	 * @return
	 */
	public Map<String, Object> checkGradeFilesName(Map<String, Object> map);
}
