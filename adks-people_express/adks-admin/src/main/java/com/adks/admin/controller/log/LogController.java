package com.adks.admin.controller.log;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adks.dubbo.api.interfaces.admin.log.LogApi;

/**
 * 
 * ClassName LogController
 * @Description：系统操作日志
 * @author xrl
 * @Date 2017年6月22日
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {

	private final Logger logger = LoggerFactory.getLogger(LogController.class);
	@Autowired
	private LogApi logApi;
	
}
