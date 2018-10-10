package com.adks.dubbo.service.admin.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.role.Adks_role;
import com.adks.dubbo.api.data.role.Adks_roleVo;
import com.adks.dubbo.api.data.role.Adks_userrole;
import com.adks.dubbo.dao.admin.role.RoleDao;
import com.adks.dubbo.dao.admin.role.UserRoleDao;

@Service
public class UserroleService extends BaseService<UserRoleDao> {

    @Autowired
    private UserRoleDao userroleDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    protected UserRoleDao getDao() {
        return userroleDao;
    }

    public List<Map<String, Object>> getRoleListByUser(Integer userId) {
        return userroleDao.getRoleListByUser(userId);
    }

    public void deleteUserRoleByUserId(String ids) {
        userroleDao.deleteUserRoleByUserId(ids);
    }

    public List<Adks_roleVo> getRoleListByUserId(Integer userId) {
        List<Adks_roleVo> list = new ArrayList<>();
        //根据用户id获取角色id
        List<Map<String, Object>> roleListByUser = userroleDao.getRoleListByUser(userId);
        Adks_roleVo adks_userrole = null;
        try {
            for (Map<String, Object> map : roleListByUser) {
                adks_userrole = new Adks_roleVo();
                Adks_role adks_role = roleDao.getRoleById((Integer) map.get("roleId"));
                BeanUtils.copyProperties(adks_userrole, adks_role);
                list.add(adks_userrole);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean getUserRoleListByRole(Integer roleId) {
        return userroleDao.getUserRoleListByRole(roleId);
    }

    //取消用户角色关联
    public void deleteUserRoleByCon(Integer roleId, Integer userId) {
        userroleDao.deleteUserRoleByCon(roleId, userId);
    }

    public Adks_userrole getUserRoleByCon(Integer roleId, Integer userId) {
        return userroleDao.getUserRoleByCon(roleId, userId);
    }

    public Integer isGlobRole(Integer userId) {
        return userroleDao.isGlobRole(userId);
    }
}
