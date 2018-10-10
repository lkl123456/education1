package com.adks.dubbo.service.admin.paper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.paper.Adks_paper;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.paper.PaperDao;

@Service
public class PaperService extends BaseService<BaseDao> {

	private static final Logger logger = LoggerFactory.getLogger(PaperService.class);

	@Autowired
	private PaperDao paperDao;

	@Override
	protected PaperDao getDao() {
		return paperDao;
	}

	public Adks_paper getPaperById(Integer id) {
		return paperDao.getPaperById(id);
	}

	public void deletePaperByIds(String ids) {
		paperDao.deletePaper(ids);
	}

	public Page<List<Map<String, Object>>> getPaperListPage(Page<List<Map<String, Object>>> paramPage) {
		return paperDao.getPaperListPage(paramPage);
	}

	public Integer savePaper(Adks_paper paper) {
		Integer flag = 0;
		Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
		try {
			insertColumnValueMap.put("paperName", paper.getPaperName());
			insertColumnValueMap.put("score", paper.getScore());
			insertColumnValueMap.put("qsNum", paper.getQsNum());
			insertColumnValueMap.put("danxuanNum", paper.getDanxuanNum());
			insertColumnValueMap.put("danxuanScore", paper.getDanxuanScore());
			insertColumnValueMap.put("duoxuanNum", paper.getDuoxuanNum());
			insertColumnValueMap.put("duoxuanScore", paper.getDuoxuanScore());
			insertColumnValueMap.put("panduanNum", paper.getPanduanNum());
			insertColumnValueMap.put("panduanScore", paper.getPanduanScore());
			insertColumnValueMap.put("tiankongNum", paper.getTiankongNum());
			insertColumnValueMap.put("tiankongScore", paper.getTiankongScore());
			insertColumnValueMap.put("wendaNum", paper.getWendaNum());
			insertColumnValueMap.put("wendaScore", paper.getWendaScore());
			insertColumnValueMap.put("paperType", paper.getPaperType());
			insertColumnValueMap.put("orgId", paper.getOrgId());
			insertColumnValueMap.put("orgName", paper.getOrgName());
			insertColumnValueMap.put("creatorId", paper.getCreatorId());
			insertColumnValueMap.put("creatorName", paper.getCreatorName());
			insertColumnValueMap.put("paperHtmlAdress", paper.getPaperHtmlAdress());
			if (paper.getPaperId() != null) {
				Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
				updateWhereConditionMap.put("paperId", paper.getPaperId());
				flag = paperDao.update(insertColumnValueMap, updateWhereConditionMap);
			} else {
				insertColumnValueMap.put("createTime", paper.getCreateTime());
				flag = paperDao.insert(insertColumnValueMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public Map<String, Object> getPaperByName(Map<String, Object> map) {
		return paperDao.getPaperByName(map);
	}
}
