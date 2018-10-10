package com.adks.dubbo.providers.admin.operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.operation.Adks_operation;
import com.adks.dubbo.api.interfaces.admin.operation.OperaApi;
import com.adks.dubbo.service.admin.operation.OperaService;

public class OperaApiImpl implements OperaApi {
    @Autowired
    private OperaService operaService;

    @Override
    public List<Adks_operation> getOperationByMenuLik(List<Integer> permissionIdList) {
        return operaService.getOperationByMenuLik(permissionIdList);
    }

}
