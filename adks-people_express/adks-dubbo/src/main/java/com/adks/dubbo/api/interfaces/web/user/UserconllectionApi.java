package com.adks.dubbo.api.interfaces.web.user;

import java.util.List;

import com.adks.dubbo.api.data.user.Adks_usercollection;
import com.adks.dubbo.commons.Page;

public interface UserconllectionApi {

	public Page<List<Adks_usercollection>> myCollectionList( Page<List<Adks_usercollection>> page);
}
