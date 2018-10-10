package com.adks.dubbo.providers.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.adks.dubbo.api.data.user.Adks_usercollection;
import com.adks.dubbo.api.interfaces.web.user.UserconllectionApi;
import com.adks.dubbo.commons.Page;
import com.adks.dubbo.service.web.user.UsercollectionWebService;

public class UsercollectionApiImpl implements UserconllectionApi{

	@Autowired
	private UsercollectionWebService ucService;
	
	@Override
	public Page<List<Adks_usercollection>> myCollectionList(Page<List<Adks_usercollection>> page) {
		return ucService.myCollectionList(page);
	}
	
}
