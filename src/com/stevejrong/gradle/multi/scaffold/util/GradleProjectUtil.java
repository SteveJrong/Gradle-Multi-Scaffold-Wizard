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
import java.util.HashMap;

import com.stevejrong.gradle.multi.scaffold.business.service.impl.AbstractGradleProject;
import com.stevejrong.gradle.multi.scaffold.business.service.impl.GradleRootProject;
import com.stevejrong.gradle.multi.scaffold.business.service.impl.GradleSubProject;
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
	 * @param project
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
		// 创建主项目的settings.gradle文件
		if (project instanceof GradleRootProject) {
			createSettingsGradleFileByRootProject(fullProjectPath, (GradleRootProject) project, Constants.SETTINGS_GRADLE_FILE_NAME);
		} else {
			// 创建子项目的settings.gradle文件
			createSettingsGradleFileBySubProject(fullProjectPath, (GradleSubProject) project, Constants.SETTINGS_GRADLE_FILE_NAME);
		}
		
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
	
	/**
	 * 创建子项目的settings.gradle文件
	 * @param fullProjectPath
	 * @param subProject
	 * @param settingsGradleFileName
	 */
	private static void createSettingsGradleFileBySubProject(String fullProjectPath, GradleSubProject subProject,
			String settingsGradleFileName) {
		FileUtil.buildFileByConfig(fullProjectPath, Constants.SETTINGS_GRADLE_FILE_NAME, new HashMap<String, String>(){
			private static final long serialVersionUID = 81507219267150664L;
			{
				put("fmProjectName", subProject.getProjectName());
				put("fmEncoding", subProject.getBuildEncode());
			}
		});
	}

	/**
	 * 创建主项目的settings.gradle文件
	 * @param fullProjectPath
	 * @param rootProject
	 * @param settingsGradleFileName
	 */
	private static void createSettingsGradleFileByRootProject(String fullProjectPath, GradleRootProject rootProject, String settingsGradleFileName) {
		FileUtil.buildAndAppendStrFileToByConfig(fullProjectPath, Constants.SETTINGS_GRADLE_FILE_NAME, new HashMap<String, String>(){
			private static final long serialVersionUID = 81507219267150664L;
			{
				put("fmProjectName", rootProject.getProjectName());
				put("fmEncoding", rootProject.getBuildEncode());
			}
		}, executeSettingsGradleFileCodeByRootProject(rootProject));
	}

	/**
	 * 处理主项目的settings.gradle文件配置
	 * @param rootProject
	 * @return
	 */
	private static StringBuilder executeSettingsGradleFileCodeByRootProject(GradleRootProject rootProject) {
		StringBuilder strByInclude = new StringBuilder("\n");
		StringBuilder strBySubProjectPath = new StringBuilder("\n");
		
		if (null != rootProject && null != rootProject.getSubProjects() && rootProject.getSubProjects().size() > 0) {
			for (int i = 0; i < rootProject.getSubProjects().size(); i++) {
				// 处理include部分
				executeIncludeCode(rootProject, strByInclude, i);
				// 处理各个子项目路径部分
				executeSubProjectsPathCode(rootProject, strBySubProjectPath, i);
			}
		}
		
		return strByInclude.append("\n").append(strBySubProjectPath).append("\n");
	}

	/**
	 * 处理各个子项目相对于主项目的相对路径，使得被包含进来的子项目可以被主项目找到
	 * @param rootProject
	 * @param strByInclude
	 * @param i
	 */
	private static void executeIncludeCode(GradleRootProject rootProject, StringBuilder strByInclude, int i) {
		if (i == 0) {
			strByInclude.append("\ninclude\t");
		}else {
			strByInclude.append("\t\t");
		}
		strByInclude.append("'" + rootProject.getSubProjects().get(i).getProjectName() + "'");
		if (i + 1 < rootProject.getSubProjects().size()) {
			strByInclude.append(",\n");
		}
	}
	
	/**
	 * 处理“Include”代码部分，使得子项目包含到主项目中
	 * @param rootProject
	 * @param strBySubProjectPath
	 * @param i
	 */
	private static void executeSubProjectsPathCode(GradleRootProject rootProject, StringBuilder strBySubProjectPath,
			int i) {
		strBySubProjectPath.append("project(':" + rootProject.getSubProjects().get(i).getProjectName() + "').projectDir "
				+ "= new File(settingsDir, '" + File.separatorChar + rootProject.getSubProjects().get(i).getProjectName() + "')\n");
	}
}
