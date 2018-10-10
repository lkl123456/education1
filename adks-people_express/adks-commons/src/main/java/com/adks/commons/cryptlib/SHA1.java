package com.adks.commons.cryptlib;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * SHA1加密
 */
public class SHA1 {
	/**
	 * 计算SHA-1签名，转换成十六进制字符串返回
	 * @param data - 要签名的字符串
	 * @return - 十六进制的签名
	 */
	public static String sha1Hex(String data) {
		return DigestUtils.sha1Hex(data);
	}
	
	/**
	 * 计算SHA-1签名，转换成十六进制字符串返回
	 * @param data - 要签名的byte[]
	 * @return - 十六进制的签名
	 */
	public static String sha1Hex(byte[] data) {
		return DigestUtils.sha1Hex(data);
	}
	
	/**
	 * 计算SHA-1签名，转换成十六进制字符串返回
	 * @param inputStream 
	 * @return
	 * @throws IOException
	 */
	public static String sha1Hex(InputStream inputStream) throws IOException {
		return DigestUtils.sha1Hex(inputStream);
	}
	
	/**
	 * 计算SHA-1签名
	 * @param data
	 * @return
	 */
	public static byte[] sha1(String data) {
		return DigestUtils.sha1(data);
	}
	
	/**
	 * 计算SHA-1签名
	 * @param data
	 * @return
	 */
	public static byte[] sha1(byte[] data) {
		return DigestUtils.sha1(data);
	}
	
	/**
	 * 计算SHA-1签名
	 * @param data
	 * @return
	 */
	public static byte[] sha1(InputStream inputStream) throws IOException {
		return DigestUtils.sha1(inputStream);
	}
}
