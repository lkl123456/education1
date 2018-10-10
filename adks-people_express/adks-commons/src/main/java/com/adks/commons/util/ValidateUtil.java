package com.adks.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
	public static String getHostName(String referer){

        String http="http://(.+?)/";
        String https="https://(.+?)/";
        Pattern pattern_http = Pattern.compile(referer.contains("https")?https:http);
        Matcher matcher_http = pattern_http.matcher(referer);
        String  result="";
        if(matcher_http.find()) {
            result=matcher_http.group(1);
        }
        return result;
    }
}
