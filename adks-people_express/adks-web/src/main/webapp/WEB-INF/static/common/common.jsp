<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.lang.String" pageEncoding="UTF-8"%>
<%@ page import="com.adks.commons.util.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="com" uri="/WEB-INF/tld/common.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fs"%>
<%
	String contextPath = request.getContextPath();
	String imgServerPath=(String)application.getAttribute(Constants.IMG_SERVER);
%>