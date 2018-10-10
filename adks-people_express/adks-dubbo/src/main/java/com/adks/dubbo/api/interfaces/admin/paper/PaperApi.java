package com.adks.dubbo.api.interfaces.admin.paper;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.commons.Page;

public interface PaperApi {

	public Integer savePaper(Adks_paper paper);

	public void deletePaperByIds(String ids);

	public Adks_paper getPaperById(Integer id);

	/**
	 * 获取试题列表分页数据
	 * 
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getPaperListPage(Page<List<Map<String, Object>>> page);

	public Map<String, Object> getPaperByName(Map<String, Object> map);
}
