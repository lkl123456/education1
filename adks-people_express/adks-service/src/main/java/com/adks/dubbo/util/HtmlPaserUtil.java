package com.adks.dubbo.util;

import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;

public class HtmlPaserUtil {
	/**
	 * 按页面方式处理.解析标准的html页面
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String readByHtml(String content) throws Exception
	{
		Parser myParser;
		myParser = Parser.createParser(content, "GB2312");
		
		HtmlPage visitor = new HtmlPage(myParser);
		
		myParser.visitAllNodesWith(visitor);
		
		NodeList nodelist ;
		nodelist = visitor.getBody();        
		return nodelist.asString().trim();
		
	}
	 /**
	  * 替换特殊字符
	  * @param desc
	  * @return
	  * @throws Exception
	  */
	 public static String ReplaceHtml(String desc) throws Exception{
		if (desc.contains("&nbsp;")) {
				desc=desc.replaceAll("&nbsp;", "");
			}
		if (desc.contains("&ldquo;")) {
			desc=desc.replaceAll("&ldquo;", "“");
		}
		if (desc.contains("&rdquo;")) {
			desc=desc.replaceAll("&rdquo;", "”");
		}
		if (desc.contains("&quot;")) {
			desc=desc.replaceAll("&quot;", "\"");
		}
		if (desc.contains("&mdash;")) {
			desc=desc.replaceAll("&mdash;", "—");
		}
		if (desc.contains("&lsquo;")) {
		    desc=desc.replaceAll("&lsquo;", "‘");
		}
		if (desc.contains("&rsquo;")) {
		    desc=desc.replaceAll("&rsquo;", "’");
		}
		if (desc.contains("&hellip;")) {
		    desc=desc.replaceAll("&hellip;", "…");
		}
		if (desc.contains("&middot;")) {
		    desc=desc.replaceAll("&middot;", "·");
		}
		return desc;
	 }
	    
}
