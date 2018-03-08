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

import java.util.List;

/**
 * Util - 集合工具
 * 
 * @author Steve Jrong
 * @since 1.0 create date: 2018年3月8日 下午10:24:36
 */
public class CollectionUtil {

	public static Object[] listToArray(List<?> listCollection) {
		Object[] strings = new Object[listCollection.size()];
		return listCollection.toArray(strings);
	}
}
