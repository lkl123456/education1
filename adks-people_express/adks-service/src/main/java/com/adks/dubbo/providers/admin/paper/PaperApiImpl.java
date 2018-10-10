package com.adks.dubbo.providers.admin.paper;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dbclient.mysql.MysqlClient;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.api.interfaces.admin.paper.PaperApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.admin.paper.PaperService;

public class PaperApiImpl implements PaperApi {

	@Autowired
	private MysqlClient mysqlClient;

	@Autowired
	private PaperService paperService;

	@Override
	public Page<List<Map<String, Object>>> getPaperListPage(Page<List<Map<String, Object>>> page) {
		return paperService.getPaperListPage(page);
	}

	@Override
	public Integer savePaper(Adks_paper paper) {
		Integer flag = 0;
		flag = paperService.savePaper(paper);
		return flag;
	}

	@Override
	public void deletePaperByIds(String ids) {
		paperService.deletePaperByIds(ids);
	}

	@Override
	public Adks_paper getPaperById(Integer id) {
		return paperService.getPaperById(id);
	}

	@Override
	public Map<String, Object> getPaperByName(Map<String, Object> map) {
		return paperService.getPaperByName(map);
	}

}
