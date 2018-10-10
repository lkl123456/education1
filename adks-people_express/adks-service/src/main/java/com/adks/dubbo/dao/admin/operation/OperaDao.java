package com.adks.dubbo.dao.admin.operation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.adks.dbclient.dao.singaltanent.BaseDao;
import com.adks.dubbo.api.data.operation.Adks_operation;

@Component
public class OperaDao extends BaseDao {

    @Override
    protected String getTableName() {
        // TODO Auto-generated method stub
        return "adks_operation";
    }

    public List<Adks_operation> getOperationByMenuLik(List<Integer> permissionIdList) {
        String id = permissionIdList.toString();
        id = id.substring(1, id.length() - 1);
        String sql = "SELECT * FROM adks_operation WHERE operationid in(SELECT amo.operation_id FROM adks_menulink_operation amo WHERE amo.rmId in (SELECT arml.rmId  FROM adks_role_menu_link arml  WHERE arml.menuLinkId in("
                + id + ")))";
        List<Map<String, Object>> queryList = mysqlClient.queryList(sql);
        List<Adks_operation> list = new ArrayList<>();
        Adks_operation adks_operation = null;
        try {
            for (Map<String, Object> map : queryList) {
                adks_operation = new Adks_operation();
                String createdate = null;
                if (null != map.get("operationcreatetime")) {
                    createdate = (String) map.get("operationcreatetime").toString();
                }
                map.remove("operationcreatetime");
                adks_operation.setOperationCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdate));
                BeanUtils.populate(adks_operation, map);
                list.add(adks_operation);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
