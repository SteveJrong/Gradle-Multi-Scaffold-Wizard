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

import java.util.HashMap;

import com.stevejrong.gradle.multi.scaffold.business.service.impl.AbstractGradleProject;
import com.stevejrong.gradle.multi.scaffold.constant.Constants;

/**
 * Util - Gradle项目工具
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月7日 下午7:35:26
 */
public class GradleProjectUtil {
	
	/**
	 * 创建单个Gradle项目
	 * @param rootProject
	 * @param fullProjectPath
	 * @return
	 */
	public static boolean createSimpleGradleProject(AbstractGradleProject project, String fullProjectPath) {
		// 创建主项目文件夹
		FileUtil.createDirectory(fullProjectPath);
		// 创建gradlew文件
		FileUtil.buildFileByConfig(fullProjectPath, Constants.GRADLEW_FILE_NAME, null);
		// 创建gradlew.bat文件
		FileUtil.buildFileByConfig(fullProjectPath, Constants.GRADLEW_BAT_FILE_NAME, null);
		// 创建build.gradle文件
		FileUtil.buildFileByConfig(fullProjectPath, Constants.BUILD_GRADLE_FILE_NAME, new HashMap<String, String>(){
			private static final long serialVersionUID = 81507219267150664L;
			{
				put("fmProjectName", project.getProjectName());
				put("fmEncoding", project.getBuildEncode());
				put("fmRepositories", project.getRepositoryUrl());
			}
		});
		// 创建settings.gradle文件
		FileUtil.buildFileByConfig(fullProjectPath, Constants.SETTINGS_GRADLE_FILE_NAME, new HashMap<String, String>(){
			private static final long serialVersionUID = 81507219267150664L;
			{
				put("fmProjectName", project.getProjectName());
				put("fmEncoding", project.getBuildEncode());
			}
		});
		
		// 创建src/main/java和src/test/java包结构
		FileUtil.createDirectory(fullProjectPath + Constants.SRC_MAIN_PACKAGE_RELATIVE_PATH);
		FileUtil.createDirectory(fullProjectPath + Constants.SRC_TEST_PACKAGE_RELATIVE_PATH);
		
		// 创建gradle及其子文件夹
		FileUtil.createDirectory(fullProjectPath + Constants.GRADLE_WRAPPER_RELATIVE_PATH);
		
		// 放入gradle-wrapper.jar文件
		FileUtil.putGradleWrapperJar(fullProjectPath + Constants.GRADLE_WRAPPER_RELATIVE_PATH);
		
		// 创建gradle-wrapper.properties配置文件
		FileUtil.buildFileByConfig(fullProjectPath + Constants.GRADLE_WRAPPER_RELATIVE_PATH, Constants.GRADLE_WRAPPER_PROPERTIES_FILE_NAME, null);
		
		return true;
	}

}
