package com.adks.dubbo.api.data.log;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * ClassName Adks_log
 * @Description：系统操作日志
 * @author xrl
 * @Date 2017年6月22日
 */
public class Adks_log implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer logId;   //操作日志ID
	private Integer operateType;  //操作类型（添加：81  修改：82  删除：83  批改：84）
	private Integer moduleId;   //模块Id
	private Integer dataId;   //数据ID（即被执行操作的数据的Id）
	private String dateName;   //数据Name（即被执行操作的数据的Name）
	private Integer creatorId;   //操作执行者的Id
	private String creatorName;  //操作执行者的Name
	private Integer orgId;   //操作执行者的机构Id
	private String orgCode;    //操作执行者的机构Code
	private Date createTime;   //操作时间
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public Integer getOperateType() {
		return operateType;
	}
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public Integer getDataId() {
		return dataId;
	}
	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}
	public String getDateName() {
		return dateName;
	}
	public void setDateName(String dateName) {
		this.dateName = dateName;
	}
	public Integer getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
