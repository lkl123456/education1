package com.adks.commons.freemarker;

/**
 * Freemarker 模板源类型
 * @author panpanxu
 *
 */
public enum FreemarkerTemplateSourceType {
	/**
	 * 文件系统绝对路径
	 */
	FileSystemDir,
	
	/**
	 * 基于classpath的
	 */
	ClasspathBased,
	
	/**
	 * 基于URL的
	 */
	UrlBased
}
