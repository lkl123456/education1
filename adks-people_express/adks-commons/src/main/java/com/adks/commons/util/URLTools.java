package com.adks.commons.util;

public class URLTools {
	 public static boolean isLocalAbsoluteURL(String strURL)
	  {
	    String strTempURL = strURL.trim();
	    if (strTempURL.startsWith("/"))
	      return true;
	    if (strTempURL.startsWith("\\\\"))
	      return true;
	    if (strTempURL.toLowerCase().startsWith("file:///")) {
	      return true;
	    }

	    return ((strTempURL.indexOf(":/") > 0) || (strTempURL.indexOf(":\\") > 0));
	  }

	  public static String getLocalAbsoluteURL(String strURL)
	  {
	    String strTempURL = strURL.trim();

	    if (strTempURL.startsWith("/")) {
	      strTempURL = "file://" + strTempURL;
	      return strTempURL;
	    }
	    if (strTempURL.startsWith("\\\\")) {
	      strTempURL = strTempURL.replaceAll("/", "\\\\");
	      return strTempURL; }
	    if (strTempURL.toLowerCase().startsWith("file:///")) {
	      return strTempURL;
	    }
	    if ((strTempURL.indexOf(":/") > 0) || (strTempURL.indexOf(":\\") > 0)) {
	      strTempURL = "file:///" + strTempURL;
	      strTempURL = strTempURL.replaceAll("\\\\", "/");
	      return strTempURL;
	    }
	    return strTempURL;
	  }
}
