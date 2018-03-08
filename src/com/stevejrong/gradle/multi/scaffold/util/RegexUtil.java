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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util - 正则表达式工具
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月8日 下午3:36:32
 */
public class RegexUtil {

	/**
	 * 正则表达式验证
	 * @param regularExpression 正则表达式
	 * @param content 待验证的文本内容
	 * @return true-文本内容符合正则表达式格式;false-文本内容不符合正则表达式格式
	 */
	public static boolean matcher(String regularExpression, String content) {
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(content);
		return matcher.matches();
	}
	
}
