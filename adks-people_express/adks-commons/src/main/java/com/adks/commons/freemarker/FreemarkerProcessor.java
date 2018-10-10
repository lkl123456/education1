package com.adks.commons.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * Freemarker模板处理器，可以根据模板及数据生成文件
 * @author panpanxu
 */
class FreemarkerProcessor implements IFreemarkerProcessor {
	private Logger logger = LoggerFactory.getLogger(FreemarkerProcessor.class);
	
	/**
	 * Freemarker配置
	 */
	private final Configuration cfg;
	
	/**
	 * 构造函数
	 * @param templateSource - 模板所在的目录，注意该目录不能在war包里，需要是磁盘上的绝对路径
	 * @throws IOException
	 */
	/**
	 * 构造函数
	 * @param templateSourceType - FreemarkerTemplateSourceType
	 * @param templateSource - 模板来源，根据templateSourceType定义
	 * @throws IOException
	 */
	FreemarkerProcessor(final FreemarkerTemplateSourceType templateSourceType, final String templateSource) throws IOException {
		cfg = new Configuration(Configuration.VERSION_2_3_25);
		if (templateSourceType.equals(FreemarkerTemplateSourceType.FileSystemDir)) {
			cfg.setDirectoryForTemplateLoading(new File(templateSource));
		} else if (templateSourceType.equals(FreemarkerTemplateSourceType.ClasspathBased)) {
			cfg.setClassForTemplateLoading(this.getClass(), templateSource);
		} else if (templateSourceType.equals(FreemarkerTemplateSourceType.UrlBased)) {
			cfg.setTemplateLoader(new URLTemplateLoader() {
				@Override
				protected URL getURL(String templateName) {
					try {
						return new URL(new StringBuilder(templateSource).append(File.separator).append(templateName).toString());
					} catch (MalformedURLException e) {
						logger.error("getURL get error.", e);
						return null;
					}
				}
			});
		}
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
	}
	
	@Override
	public void process(String template, Object data, String outFileName) throws Exception {
		this.process(template, data, new File(outFileName));
	}

	@Override
	public void process(String template, Object data, File outFile) throws Exception {
		Template tpl = cfg.getTemplate(template);
		File parentFile = outFile.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		tpl.process(data, new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
	}

	@Override
	public void process(String template, Object data, Writer writer) throws Exception {
		Template tpl = cfg.getTemplate(template);
		tpl.process(data, writer);
	}

}
