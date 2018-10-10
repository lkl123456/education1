package com.adks.dubbo.api.interfaces.admin.role;

import java.util.List;
import java.util.Map;

import com.adks.dubbo.api.data.role.Adks_roleVo;
import com.adks.dubbo.api.data.role.Adks_userrole;

public interface UserroleApi {

    public List<Map<String, Object>> getRoleListByUser(Integer userId);

    //删除用户也删除其对应的角色
    public void deleteUserRoleByUserId(String ids);

    public boolean getUserRoleListByRole(Integer roleId);

    //取消用户的角色关联
    public void deleteUserRoleByCon(Integer roleId, Integer userId);

    //根据条件得到用户角色
    public Adks_userrole getUserRoleByCon(Integer roleId, Integer userId);

    public Integer addUserRole(Adks_userrole userrole);

    public Integer isGlobRole(Integer userId);

    //根据用户id获取用户角色关联表
    public List<Adks_roleVo> getRoleListByUserId(Integer userId);
}
