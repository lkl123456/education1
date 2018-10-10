package com.adks.commons.freemarker;

import java.io.IOException;

/**
 * Freemarker 处理器工厂类
 * @author panpanxu
 */
public class FreemarkerProcessorFactory {
	/**
	 * 获取Freemarker处理器实例
	 * @param templateSourceType - 模板文件来源类型<code>FreemarkerTemplateSourceType</code>。
	 * @param tplSourcePath - 模板来源路径，根据templateSourceType而定(FileSystemDir: 表示来源为本地服务器的一个绝对路径目录。UrlBased: 表示模板来源可能在网络上http://..； 
	 * 						  可能在classpath下classpath://.. 等)
	 * @return - 模板处理器
	 * @throws IOException - 如果模板路径不正确或无法访问会有IOException抛出
	 */
	public static IFreemarkerProcessor getProcessor(FreemarkerTemplateSourceType templateSourceType, String tplSourcePath) throws IOException {
		return new FreemarkerProcessor(templateSourceType, tplSourcePath);
	}
}
