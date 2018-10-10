package com.adks.commons.freemarker;

import java.io.File;
import java.io.Writer;

/**
 * 定义Freemarker处理器的方法
 * @author panpanxu
 *
 */
public interface IFreemarkerProcessor {
	/**
	 * 根据模板及数据生成相应的文件
	 * @param template - 模板名称
	 * @param data - 数据
	 * @param outFileName - 输出文件全路径名称
	 * @throws Exception
	 */
	void process(String template, Object data, String outFileName) throws Exception;
	
	/**
	 * 根据模板及数据生成相应的文件
	 * @param template - 模板名称
	 * @param data - 数据
	 * @param outFile - 输出文件
	 * @throws Exception
	 */
	void process(String template,  Object data, File outFile) throws Exception;
	
	/**
	 * 根据模板及数据生成相应的数据到writer里
	 * @param template - 模板名称
	 * @param data - 数据
	 * @param writer - 输入writer
	 * @throws Exception
	 */
	void process(String template,  Object data, Writer writer) throws Exception;
}
