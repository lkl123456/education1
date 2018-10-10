package com.adks.commons.util;

import com.adks.commons.cryptlib.SHA1;
import com.adks.commons.util.Constants;

/**
 * 密码相关API
 * @author panpanxu
 */
public class PasswordUtil {
	public static final String getSHA1Password(String password) {
		String userPassword = String.format("%s--%s--", Constants.SHA1_SECRET_KEY, password);
        return SHA1.sha1Hex(userPassword);
	}
	public static void main(String[] args) {
		System.out.println(getSHA1Password("111111"));
	}
}
