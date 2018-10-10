package com.adks.dubbo.api.interfaces.admin.user;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.user.Adks_user;
import com.adks.dubbo.commons.ApiResponse;
import com.adks.dubbo.commons.Page;

public interface UserApi {
	
	/*判断同一个机构下是否有相同的用户名*/
	public Adks_user getUserByName(String userName);
	/*判断用户名是否已经存在*/
	public boolean checkUserName(String userName);
	/**
	 * 查询用户信息
	 * @param username
	 * @return
	 */
	Adks_user getUserInfo(String username);
	
	/**
	 * 获取用户列表分页数据
	 * @param DataGridPage<List<User>>
	 * @return DataGridPage<List<User>>
	 */
	Page<List<Map<String, Object>>> getUserListPage(Page<List<Map<String, Object>>> paramPage);
	
	/**
	 * 保存一个用户
	 * @param user
	 
	void saveUser(User user);
	
	/**
	 * 修改/重置密码，停用，恢复
	 * @return
	 */
	Integer changeUser(Adks_user user,Integer handleType,String userName);
	
	public Integer saveUser(Adks_user user);
	
	/**
	 * 批量删除用户
	 * @param userIds
	 */
	void deleteUserByIds(String userIds,Map map);
	
	/**
	 * 根据id获取用户信息
	 * @param userId
	 * @return
	 */
	Map<String, Object> getUserInfoById(Integer userId);
	
	public ApiResponse<Map> loginByName(String username,String password);
	
	/**
	 * 
	 * @Title getUserInfoByUserId
	 * @Description:根据userId获取用户信息
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getUserInfoByUserId(Integer userId);
	
	/**
	 * 
	 * @Title getSelGradeHeadTeacherPage
	 * @Description：获取班主任角色的用户
	 * @author xrl
	 * @Date 2017年3月30日
	 * @param page
	 * @return
	 */
	public Page<List<Map<String, Object>>> getSelGradeHeadTeacherPage(Page<List<Map<String, Object>>> page);
	
	public Integer updatePassword(Integer userId,String userName,String password);
	public boolean canDelUser(Integer userId);
	//根据电话号码查询用户
	public Map<String, Object> getUserInfoByPhone(String userPhone);
	
	//清除缓存
	public void deleteUserToRedis(Integer userId,String userName);
}
