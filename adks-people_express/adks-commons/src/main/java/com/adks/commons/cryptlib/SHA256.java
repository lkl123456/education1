package com.adks.commons.cryptlib;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * SHA256加密
 * @author panpanxu
 */
public class SHA256 {
	/**
	 * 计算SHA-256签名，转换成十六进制字符串返回
	 * @param data - 要签名的字符串
	 * @return - 十六进制的签名
	 */
	public static String sha256Hex(String data) {
		return DigestUtils.sha256Hex(data);
	}
	
	/**
	 * 计算SHA-256签名，转换成十六进制字符串返回
	 * @param data - 要签名的byte[]
	 * @return - 十六进制的签名
	 */
	public static String sha256Hex(byte[] data) {
		return DigestUtils.sha256Hex(data);
	}
	
	/**
	 * 计算SHA-256签名，转换成十六进制字符串返回
	 * @param inputStream 
	 * @return
	 * @throws IOException
	 */
	public static String sha256Hex(InputStream inputStream) throws IOException {
		return DigestUtils.sha256Hex(inputStream);
	}
	
	/**
	 * 计算SHA-256签名
	 * @param data
	 * @return
	 */
	public static byte[] sha256(String data) {
		return DigestUtils.sha256(data);
	}
	
	/**
	 * 计算SHA-256签名
	 * @param data
	 * @return
	 */
	public static byte[] sha256(byte[] data) {
		return DigestUtils.sha256(data);
	}
	
	/**
	 * 计算SHA-256签名
	 * @param data
	 * @return
	 */
	public static byte[] sha256(InputStream inputStream) throws IOException {
		return DigestUtils.sha256(inputStream);
	}
}
