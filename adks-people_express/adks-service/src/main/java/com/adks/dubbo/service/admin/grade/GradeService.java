package com.adks.dubbo.service.admin.grade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.commons.oss.OSSUploadUtil;
import com.adks.commons.util.PropertiesFactory;
import com.adks.dbclient.dao.multitanent.MultitenantBaseDao;
import com.adks.dbclient.service.multinanent.BaseMultinanentService;
import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.grade.Adks_grade;
import com.adks.dubbo.api.data.grade.Adks_grade_user;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.dao.admin.grade.GradeDao;

@Service
public class GradeService extends BaseService<GradeDao> {

	@Autowired
	private GradeDao gradeDao;

	@Override
	protected GradeDao getDao() {
		return gradeDao;
	}

	/**
	 * 
	 * @Title getGradeListPage
	 * @Description:获取班级分页列表
	 * @author xrl
	 * @Date 2017年3月25日
	 * @param paramPage
	 * @return
	 */
	public Page<List<Map<String, Object>>> getGradeListPage(Page<List<Map<String, Object>>> paramPage) {
		return gradeDao.getGradeListPage(paramPage);
	}

	/**
	 * 
	 * @Title getGradeById
	 * @Description：根据gradeId获取班级信息
	 * @author xrl
	 * @Date 2017年3月25日
	 * @param gradeId
	 * @return
	 */
	public Map<String, Object> getGradeById(Integer gradeId) {
		return gradeDao.getGradeById(gradeId);
	}

	public List<Adks_grade> gradeTopCurrentList(Map map) {
		return gradeDao.gradeTopCurrentList(map);
	}

	// 前台班级分页
	public Page<List<Adks_grade>> getGradeListWeb(Page<List<Adks_grade>> page) {
		return gradeDao.getGradeListWeb(page);
	}

	/**
	 * 
	 * @Title delGradeByGradeIdId
	 * @Description：删除班级
	 * @author xrl
	 * @Date 2017年4月27日
	 * @param gradeId
	 */
	public void delGradeByGradeIdId(Integer gradeId) {
		String ossResource = PropertiesFactory.getProperty("ossConfig.properties", "oss.resource");
		Map<String, Object> gradeMap = gradeDao.getGradeById(gradeId);
		gradeDao.delGradeByGradeIdId(gradeId);
		if (gradeMap != null) {
			String gradeImgPath = ossResource + (String) gradeMap.get("gradeImg");
			if (gradeImgPath != null && !"".equals(gradeImgPath)) {
				OSSUploadUtil.deleteFile(gradeImgPath);
			}
			String certificateImgPath = ossResource + (String) gradeMap.get("certificateImg");
			if (certificateImgPath != null && !"".equals(certificateImgPath)) {
				OSSUploadUtil.deleteFile(certificateImgPath);
			}
			String eleSealPath = ossResource + (String) gradeMap.get("eleSeal");
			if (eleSealPath != null && !"".equals(eleSealPath)) {
				OSSUploadUtil.deleteFile(eleSealPath);
			}
		}
	}
	
	/**
	 * 
	 * @Title getGradesJson
	 * @Description
	 * @author xrl
	 * @Date 2017年4月28日
	 * @param map
	 * @return
	 */
	public List<Adks_grade> getGradesJson(Map<String, Object> map){
		return gradeDao.getGradesJson(map);
	}
	
	 /**
     * 
     * @Title updateGradeUserNum
     * @Description：删除班级后更新班级人数
     * @author xrl
     * @Date 2017年5月5日
     * @param map
     */
    public void updateGradeUserNum(Map<String, Object> map){
    	gradeDao.updateGradeUserNum(map);
    }
    /**
     * 
     * @Title checkGradeName
     * @Description:检查班级名称在该机构下是否重复
     * @author xrl
     * @Date 2017年5月8日
     * @param map
     * @return
     */
    public Map<String, Object> checkGradeName(Map<String, Object> map){
    	return gradeDao.checkGradeName(map);
    }
    
    /**
     * 
     * @Title updateGradeCourseNum
     * @Description：更新班级课程数量
     * @author xrl
     * @Date 2017年5月22日
     * @param map
     */
    public void updateGradeCourseNum(Map<String, Object> map){
    	gradeDao.updateGradeCourseNum(map);
    }
}
