package com.adks.commons.test.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.adks.commons.freemarker.FreemarkerProcessorFactory;
import com.adks.commons.freemarker.FreemarkerTemplateSourceType;
import com.adks.commons.freemarker.IFreemarkerProcessor;

public class FreemarkerTest {
	
	private IFreemarkerProcessor processor;

	@Test
	public void testFreeMarkerProcessor() throws IOException{
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("name", "ckl");
			processor = FreemarkerProcessorFactory.getProcessor(FreemarkerTemplateSourceType.FileSystemDir, "D:/freemarker");
			processor.process("test.ftl", map, "D:/freemarker/test.html");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
