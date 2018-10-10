/**
 * 
 */
package com.adks.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.app.util.JsonResponse;
import com.adks.dubbo.api.data.version.Adks_version;
import com.adks.dubbo.api.interfaces.app.version.VersionAppApi;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/api/update")
public class VersionController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(VersionController.class);

	@Autowired
	private VersionAppApi versionAppApi;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateAppVersion.do")
	@ResponseBody
	public JsonResponse updateAppVersion(String data) throws IOException {
		String error = null;

		/* 将json字符串解析为map */
		ObjectMapper mapper = new ObjectMapper();
		/* 返回参数 */
		Map resultMap = new HashMap();

		if (StringUtils.isNotEmpty(data)) {
			Map map = mapper.readValue(data, Map.class);

			/* 获取版本号 */
			String versionCode = MapUtils.getString(map, "v");
			if (StringUtils.isNotEmpty(versionCode)) {
				Adks_version version = versionAppApi.getVersion();
				if (version != null) {
					if (versionCode.equals(version.getVersionCode())) {
						resultMap.put("message", "已是最新版本");
						resultMap.put("result", false);
					} else {
						resultMap.put("message", "请更新到最新版本");
						resultMap.put("result", true);
						resultMap.put("apkUrl", version.getApkUrl());
					}
				} else {
					resultMap.put("message", "未发布版本信息");
				}
			}
		} else
			error = "未接受到参数";

		if (StringUtils.isNotEmpty(error)) {
			return JsonResponse.error(resultMap);
		} else {
			return JsonResponse.success(resultMap);
		}
	}

}
