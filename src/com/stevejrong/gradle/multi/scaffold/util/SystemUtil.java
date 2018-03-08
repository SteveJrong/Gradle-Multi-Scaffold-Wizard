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

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;

import com.stevejrong.gradle.multi.scaffold.activator.Activator;
import com.stevejrong.gradle.multi.scaffold.constant.Constants;

/**
 * Util - 系统工具
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月6日 下午10:14:29
 */
public class SystemUtil {
	
	/**
	 * 获取配置文件的根路径
	 * 
	 * @return
	 */
	public static String getConfigPath() {
		URL url = null;
		String configPath = null;
		try {
			url = Activator.getDefault().getBundle().getResource(Constants.CONFIG_PATH_DIRECTORY_NAME);
			configPath = FileLocator.toFileURL(url).getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return configPath;
	}
}
