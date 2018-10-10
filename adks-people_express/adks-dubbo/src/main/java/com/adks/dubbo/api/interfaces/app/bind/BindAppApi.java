package com.adks.dubbo.api.interfaces.app.bind;

import java.util.List;

import com.adks.dubbo.api.data.user.Adks_user_bind;

public interface BindAppApi {

	public List<Adks_user_bind> getByUserId(int userId);

	public void deleteUserByIds(String userIds);

	public void deleteByRemoteUserIdAndUserId(String remoteUserId, int userId);

	public void saveUser(Adks_user_bind user);
}
