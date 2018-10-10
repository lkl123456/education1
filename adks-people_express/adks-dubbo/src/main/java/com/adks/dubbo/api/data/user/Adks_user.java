package com.adks.dubbo.api.data.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台user实体
 * @author ckl
 * @since 2016-12-14 13:14:24
 *
 */
public class Adks_user implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer userId;  //用户ID
	private String userName;   //用户名
	private String userPassword;   //用户密码
	private String userRealName;   //真实姓名
	private Integer orgId;     //机构的ID
	private String orgName;    //机构名称
	private String orgCode;    //机构层级结构
	private String headPhoto;   //头像路径
	private Integer userSex;    //性别  1代表男，2代表女
	private String userMail;    //邮箱
	private String userPhone;   //手机号码
	private Date userBirthday;  //出生日期
	private Integer userParty;   //政治面貌
	private String cardNumer;    //身份证号码
	private Integer userNationality;   //民族
	private Date overdate;     //用户使用截至时间
	private Integer userStatus;    //用户状态情况（激活状态，停用状态) 1代表激活，2代表停用
	private Integer rankId;    //职级Id
	private String rankName;   //职级名称
	private Integer userStudyLong;  //用户观看时长(秒数累加)
	private Integer userOnlineLong;  //用户在线时长(秒数累加)
	private Integer creatorId;   //创建人
	private String creatorName;   //创建人姓名
	private Date createdate;    //用户创建时间
	private Integer positionId;//职务id
	private String positionName;//职务名称
	
	//以下仅仅是显示使用
	private String rankNameStr; 
	
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getRankNameStr() {
		return rankNameStr;
	}
	public void setRankNameStr(String rankNameStr) {
		this.rankNameStr = rankNameStr;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getHeadPhoto() {
		return headPhoto;
	}
	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}
	public Integer getUserSex() {
		return userSex;
	}
	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public Date getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}
	public Integer getUserParty() {
		return userParty;
	}
	public void setUserParty(Integer userParty) {
		this.userParty = userParty;
	}
	public String getCardNumer() {
		return cardNumer;
	}
	public void setCardNumer(String cardNumer) {
		this.cardNumer = cardNumer;
	}
	public Integer getUserNationality() {
		return userNationality;
	}
	public void setUserNationality(Integer userNationality) {
		this.userNationality = userNationality;
	}
	public Date getOverdate() {
		return overdate;
	}
	public void setOverdate(Date overdate) {
		this.overdate = overdate;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public Integer getRankId() {
		return rankId;
	}
	public void setRankId(Integer rankId) {
		this.rankId = rankId;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	public Integer getUserStudyLong() {
		return userStudyLong;
	}
	public void setUserStudyLong(Integer userStudyLong) {
		this.userStudyLong = userStudyLong;
	}
	public Integer getUserOnlineLong() {
		return userOnlineLong;
	}
	public void setUserOnlineLong(Integer userOnlineLong) {
		this.userOnlineLong = userOnlineLong;
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
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
}
