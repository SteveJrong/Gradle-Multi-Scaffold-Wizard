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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.stevejrong.gradle.multi.scaffold.constant.Constants;

/**
 * Util - 文件工具
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月6日 下午7:27:32
 */
public class FileUtil {
	
	/**
	 * 创建目录
	 * @param directoryPath 目录路径
	 * @return true-创建成功;false-创建失败或已存在
	 */
	public static boolean createDirectory(String directoryPath) {
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			if (directory.mkdirs()) {
				return true;
			}
		}
		return false;
	}
	
	private static File createSimpleFile(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		return file;
	}
	
	/**
	 * 创建文件
	 * @param filePath 文件路径
	 * @return true-创建成功;false-创建失败或已存在
	 */
	public static boolean createFile(String filePath) {
		try {
			createSimpleFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 创建文件并写入内容
	 * @param filePath 文件路径
	 * @param texts 要写入的文本内容
	 * @return true-创建并写入内容成功;false-创建失败或写入内容失败
	 */
	public static boolean createFileWithWriteTexts(String filePath, String texts) {
		File file = null;
		
		try {
			file = createSimpleFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileWriter fw;
		try {
			fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), Constants.UTF8));
			bw.write(texts);
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * 根据配置构建文件
	 * @param createFilePath 创建文件的目标路径
	 * @param configCommonName config配置文件夹下通用的文件名称 如：build.gradle文件的默认配置文件为build.gradle.json、模板文件为build.gradle.ftl，则通用文件名称为build.gradle
	 * @param customResourceMap 用户自定义resourceMap，用于替换默认的模板配置
	 * @return true-构建成功;false-构建失败
	 */
	public static boolean buildFileByConfig(String createFilePath, String configCommonName, Map<String, String> customResourceMap){
		Map<String, String> defaultResourceMap = FreeMarkerUtil.getDefaultFreeMarkerConfig(configCommonName); // 获取默认配置
		if (null != customResourceMap && customResourceMap.size() > 0) {
			defaultResourceMap.putAll(customResourceMap); // 将用户设置替换为模板中的默认值
		}
		String processResult = FreeMarkerUtil.processTemplateToString(configCommonName, defaultResourceMap);
		FileUtil.createFileWithWriteTexts(createFilePath + File.separatorChar + configCommonName, processResult);
		return false;
	}
	
	/**
	 * 二进制类型的文件复制
	 * @param sourceFilePath 源文件路径
	 * @param targetDirectoryPath 目标目录路径
	 * @return
	 * @throws IOException 
	 */
	private static boolean binaryfileCopy(String sourceFilePath, String targetDirectoryPath) {
		BufferedInputStream bufferedInput = null;
		BufferedOutputStream bufferedOutput = null;
		FileInputStream fileInput = null;
		FileOutputStream fileOutput = null;
		try {
			fileInput = new FileInputStream(sourceFilePath);
			fileOutput = new FileOutputStream(targetDirectoryPath);

			bufferedInput = new BufferedInputStream(fileInput);
			bufferedOutput = new BufferedOutputStream(fileOutput);
			byte data[] = new byte[1024];
			int count = 0;
			while ((count = fileInput.read(data, 0, data.length)) != -1) {
				fileOutput.write(data, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedOutput.flush();
				bufferedInput.close();
				bufferedOutput.close();
				fileOutput.close();
				fileInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}
	
	/**
	 * 放入gradle-wrapper.jar文件到指定位置
	 * @param targetDirectoryPath 放入的目标目录位置
	 * @return true-放入成功;false-放入失败
	 */
	public static boolean putGradleWrapperJar(String targetDirectoryPath) {
		binaryfileCopy(SystemUtil.getConfigPath() + File.separatorChar + Constants.GRADLE_WRAPPER_JAR_FILE_NAME + Constants.JAR_FILE_SUFFIX, targetDirectoryPath + File.separatorChar + Constants.GRADLE_WRAPPER_JAR_FILE_NAME + Constants.JAR_FILE_SUFFIX);
		return true;
	}
}
