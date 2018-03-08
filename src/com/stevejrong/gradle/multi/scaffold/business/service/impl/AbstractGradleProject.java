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
package com.stevejrong.gradle.multi.scaffold.business.service.impl;

/**
 * Abstract业务 - Gradle项目
 * 
 * @author Steve Jrong
 * @since 1.0 create date: 2018年3月8日 下午1:48:24
 */
public abstract class AbstractGradleProject {
	protected String projectPath; // 项目存储路径
	protected String projectName; // 项目名称
	protected String repositoryUrl; // 软件仓库
	protected String buildEncode; // Gradle插件构建时的编码格式

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public String getBuildEncode() {
		return buildEncode;
	}

	public void setBuildEncode(String buildEncode) {
		this.buildEncode = buildEncode;
	}
}
