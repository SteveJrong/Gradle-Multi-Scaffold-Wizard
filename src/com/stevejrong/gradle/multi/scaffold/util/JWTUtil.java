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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * Util - JWT工具
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月7日 下午11:33:50
 */
public class JWTUtil {
	
	/**
	 * 根据按钮组件的显示文字名获取按钮控件
	 * <br> 仅当在一个容器中存在一个与此按钮控件显示文字对应的控件有效。如果有多个显示文字一致的控件，可能会发生意外结果。
	 * 
	 * @param container 容器
	 * @param buttonText 按钮组件显示的文字
	 * @return Button组件
	 */
	public static Control getButtonControlByButtonText(Composite container, String buttonText) {
		Control[] controls = container.getChildren();
		for (Control control : controls) {
			if (control instanceof Button && ((Button) control).getText().equals(buttonText)) {
				return control;
			}
		}
		return null;
	}
	
	/**
	 * 获取所有的文本框组件
	 * 
	 * @param container 容器
	 * @return 所有文本框组件
	 */
	public static List<Text> getAllTextControl(Composite container) {
		List<Text> allText = new ArrayList<Text>();
		
		Control[] controls = container.getChildren();
		for (Control control : controls) {
			if (control instanceof Text) {
				allText.add((Text)control);
			}
		}
		return allText;
	}
	
}
