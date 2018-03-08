/**
 * Copyright 2018 Steve Jrong - https://www.stevejrong.top

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.gradle.multi.scaffold.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.stevejrong.gradle.multi.scaffold.constant.Constants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Util - FreeMarker工具
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月6日 下午8:39:54
 */
public class FreeMarkerUtil {
	private static Configuration cfg = new Configuration();
	
	/**
	 * 将FreeMarker模板文件进行处理并返回处理后的字符串结果
	 * @param templateName 模板名称
	 * @param resourceMap 数据源Map集合
	 * @return 处理后的字符串结果
	 */
	public static String processTemplateToString(String templateName, Map<String, String> resourceMap) {
		Template template = null;
		StringWriter templateWriter = null;
		try {
			cfg.setDirectoryForTemplateLoading(new File(SystemUtil.getConfigPath()));
			template = cfg.getTemplate(templateName + Constants.FREEMARKER_TEMPLATE_FILE_SUFFIX, Constants.UTF8);

			templateWriter = new StringWriter();
			template.process(resourceMap, templateWriter);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}

		return templateWriter.toString();
	}
	
	/**
	 * 根据默认的JSON配置文件名获取系统默认的配置
	 * @param jsonConfigFileName JSON配置文件名
	 * @return 可遍历的且以键值对存储的JSONObject对象，键值对即为FreeMarker模板中对应键所具有的的一系列默认值
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getDefaultFreeMarkerConfig(String jsonConfigFileName) {
		InputStream inputStream = null;
		String filePath = null;
		String freeMarkerContents = null;

		try {
			filePath = SystemUtil.getConfigPath() + jsonConfigFileName + Constants.JSON_FILE_SUFFIX;
			inputStream = new FileInputStream(filePath);
			freeMarkerContents = IOUtils.toString(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JSONObject.parseObject(freeMarkerContents, Map.class);
	}
}
