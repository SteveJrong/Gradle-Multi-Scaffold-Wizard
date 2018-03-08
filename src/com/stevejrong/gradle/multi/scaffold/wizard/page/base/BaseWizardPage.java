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
package com.stevejrong.gradle.multi.scaffold.wizard.page.base;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月8日 下午9:49:59
 */
public class BaseWizardPage extends WizardPage {

	protected BaseWizardPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite composite) {
	}
	
	/**
	 * 页面验证
	 * @param eventsValidateResult 多个事件方法返回值
	 */
	protected void pageValidate(String... eventsValidateResult) {
		boolean result = false;
		StringBuffer msg = new StringBuffer();
		
		for (String validateResult : eventsValidateResult) {
			result = validateResult.equals(""); // 此判断表示每次验证的结果，结果为true或false，如果为true则表示当前事件验证通过，否则验证不通过
			if (!result) { // 当出现一次验证不通过时，记录下错误信息后跳出
				msg.append(validateResult);
				break;
			} else {
				// 通过继续执行验证
			}
		}
		
		setPageComplete(result);
		setErrorMessage(msg.length() > 0 ? msg.toString() : null);
	}
}
