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
package com.stevejrong.gradle.multi.scaffold.constant;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 常量类
 * 
 * @author Steve Jrong
 * @since 1.0 create date: 2018年3月6日 下午7:38:28
 */
public final class Constants {
	
	/** 默认软件源 */
	public static final List<String> DEFAULT_GRADLE_REPOSITORIES = new ArrayList<String>();
	/** 默认编码格式 */
	public static final List<String> DEFAULT_BUILD_ENCODE = new ArrayList<String>();
	
	static {
		String[] defaultRepositories = new String[] {
				"http://maven.aliyun.com/nexus/content/groups/public/", "http://repo1.maven.org/maven2/",
				"http://repo2.maven.org/maven2/", "http://uk.maven.org/maven2/", "http://repository.jboss.org/nexus/content/groups/public/",
				"http://mirrors.ibiblio.org/maven2/"
		};
		DEFAULT_GRADLE_REPOSITORIES.addAll(Arrays.asList(defaultRepositories));
		
		String[] defaultBuildEncode = new String[] {
				"UTF-8", "UTF-16", "UTF-16BE", "UTF-16LE", "US-ASCII", "ISO-8859-1", "GBK", "GB2312"
		};
		DEFAULT_BUILD_ENCODE.addAll(Arrays.asList(defaultBuildEncode));
	}

	/** build.gradle文件名 */
	public static final String BUILD_GRADLE_FILE_NAME = "build.gradle";
	/** gradlew文件名 */
	public static final String GRADLEW_FILE_NAME = "gradlew";
	/** gradlew.bat文件名 */
	public static final String GRADLEW_BAT_FILE_NAME = "gradlew.bat";
	/** settings.gradle文件名 */
	public static final String SETTINGS_GRADLE_FILE_NAME = "settings.gradle";
	/** gradle-wrapper.jar文件名 */
	public static final String GRADLE_WRAPPER_JAR_FILE_NAME = "gradle-wrapper";
	/** gradle-wrapper.properties文件名 */
	public static final String GRADLE_WRAPPER_PROPERTIES_FILE_NAME = "gradle-wrapper.properties";
	
	

	/** FreeMarker模板文件后缀名 */
	public static final String FREEMARKER_TEMPLATE_FILE_SUFFIX = ".ftl";
	/** jar包文件后缀名 */
	public static final String JAR_FILE_SUFFIX = ".jar";
	/** JSON文件后缀名 */
	public static final String JSON_FILE_SUFFIX = ".json";

	/** 默认配置的存放目录名 */
	public static final String CONFIG_PATH_DIRECTORY_NAME = "config";
	/** FreeMarker模板文件存放相对路径 */
	public static final String FREEMARKER_TEMPLATE_RELATIVE_PATH = "../template/";
	/** 包根路径 */
	public static final String ROOT_PATH_BY_PACKAGE_RELATIVE_PATH = File.separatorChar + "src" + File.separatorChar
			+ "com" + File.separatorChar + "stevejrong" + File.separatorChar + "gradle" + File.separatorChar + "multi"
			+ File.separatorChar + "scaffold" + File.separatorChar;
	/** src/main/java包相对路径 */
	public static final String SRC_MAIN_PACKAGE_RELATIVE_PATH = File.separatorChar + "src" + File.separatorChar + "main"
			+ File.separatorChar + "java" + File.separatorChar;
	/** src/test/java包相对路径 */
	public static final String SRC_TEST_PACKAGE_RELATIVE_PATH = File.separatorChar + "src" + File.separatorChar + "test"
			+ File.separatorChar + "java" + File.separatorChar;
	/** gradle wrapper相对路径 */
	public static final String GRADLE_WRAPPER_RELATIVE_PATH = File.separatorChar +"gradle" + File.separatorChar +"wrapper" + File.separatorChar;
	
	
	
	/** UTF-8编码 */
	public static final String UTF8 = "UTF-8";
	
	
	
	/** 根项目名称正则表达式 */
	public static final String REGEX_ROOT_PROJECT_NAME = "[A-Za-z0-9-_]{3,}";
	/** 子项目名称正则表达式 */
	public static final String REGEX_SUB_PROJECT_NAME = "[A-Za-z0-9-_]{2,}";
	
	
	
	/** 向导页面图标的相对路径 */
	public static final String WIZARD_PAGE_ICON_RELATIVE_PATH = "icons/gradle-logo-64.png";
	
	
	
	/** 向导页面通用名称 - 快速构建Gradle多模块项目 */
	public static final String WIZARD_PAGE_NAME = "Fast construction of Gradle multi module project";
	/** 向导页面通用标题 - 快速构建可定制配置的Gradle多模块项目 */
	public static final String WIZARD_PAGE_TITLE = "Fast build Gradle multi module project with customizable configuration";
	/** 向导页面一描述 - 设置根项目的基本信息。 */
	public static final String WIZARD_PAGE_ONE_DESCRIPTION = "Setting up the basic information of the root project.";
	/** 向导页面二描述 - 设置子项目的基本信息。 */
	public static final String WIZARD_PAGE_TWO_DESCRIPTION = "Setting up the basic information of subprojects.";
	/** 向导页面三描述 - 设置个性化的生成信息。 */
	public static final String WIZARD_PAGE_THREE_DESCRIPTION = "Setting up personalized generation information.";
	
}
