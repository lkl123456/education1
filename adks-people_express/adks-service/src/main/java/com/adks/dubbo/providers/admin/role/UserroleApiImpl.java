package com.adks.dubbo.providers.admin.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.role.Adks_roleVo;
import com.adks.dubbo.api.data.role.Adks_userrole;
import com.adks.dubbo.api.interfaces.admin.role.UserroleApi;
import com.adks.dubbo.service.admin.role.UserroleService;

public class UserroleApiImpl implements UserroleApi {

    @Autowired
    private UserroleService userroleService;

    @Override
    public List<Map<String, Object>> getRoleListByUser(Integer userId) {
        return userroleService.getRoleListByUser(userId);
    }

    @Override
    public void deleteUserRoleByUserId(String ids) {
        userroleService.deleteUserRoleByUserId(ids);
    }

    @Override
    public boolean getUserRoleListByRole(Integer roleId) {
        return userroleService.getUserRoleListByRole(roleId);
    }

    @Override
    public void deleteUserRoleByCon(Integer roleId, Integer userId) {
        userroleService.deleteUserRoleByCon(roleId, userId);
    }

    @Override
    public Adks_userrole getUserRoleByCon(Integer roleId, Integer userId) {
        return userroleService.getUserRoleByCon(roleId, userId);
    }

    @Override
    public Integer addUserRole(Adks_userrole userrole) {
        Integer flag = 0;
        //userrole
        Map<String, Object> insertColumnValueMap = new HashMap<String, Object>();
        insertColumnValueMap.put("userId", userrole.getUserId());
        insertColumnValueMap.put("roleId", userrole.getRoleId());
        if (userrole.getUrid() != null) {

            Map<String, Object> updateWhereConditionMap = new HashMap<String, Object>();
            updateWhereConditionMap.put("urid", userrole.getUrid());
            flag = userroleService.update(insertColumnValueMap, updateWhereConditionMap);

        }
        else {
            flag = userroleService.insert(insertColumnValueMap);
        }
        return flag;
    }

    @Override
    public Integer isGlobRole(Integer userId) {
        return userroleService.isGlobRole(userId);
    }

    @Override
    public List<Adks_roleVo> getRoleListByUserId(Integer userId) {
        return userroleService.getRoleListByUserId(userId);
    }
}
