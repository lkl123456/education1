package com.adks.dubbo.service.admin.operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adks.dbclient.service.singletanent.BaseService;
import com.adks.dubbo.api.data.operation.Adks_operation;
import com.adks.dubbo.dao.admin.operation.OperaDao;

@Service
public class OperaService extends BaseService<OperaDao> {
    @Autowired
    private OperaDao operaDao;

    @Override
    protected OperaDao getDao() {
        return operaDao;
    }

    public List<Adks_operation> getOperationByMenuLik(List<Integer> permissionIdList) {
        return operaDao.getOperationByMenuLik(permissionIdList);
    }
}
