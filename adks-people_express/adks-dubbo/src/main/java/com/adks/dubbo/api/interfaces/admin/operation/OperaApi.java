package com.adks.dubbo.api.interfaces.admin.operation;

import java.util.List;

import com.adks.dubbo.api.data.operation.Adks_operation;

public interface OperaApi {

    List<Adks_operation> getOperationByMenuLik(List<Integer> permissionIdList);

}
