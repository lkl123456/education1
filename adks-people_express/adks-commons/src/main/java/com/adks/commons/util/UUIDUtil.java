package com.adks.commons.util;


import java.util.UUID;

/** 
 * @ClassName: UUIDUtil 
 * @Description: TODO(GUIDUtil.java) 
 * @author harry 
 * @date 2017年1月4日 下午6:56:41 
 *  
*/
public class UUIDUtil {

	public static String generateUUID() {
		UUID ud = UUID.randomUUID();
		return ud.toString().replaceAll("-", "");
	}

	public static void main(String[] argv) {
		String t = generateUUID();
		System.out.println(t);
		System.out.println(t.length());
	}
}
