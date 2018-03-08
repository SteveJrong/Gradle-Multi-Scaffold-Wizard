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
package com.stevejrong.gradle.multi.scaffold.wizard.page;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.stevejrong.gradle.multi.scaffold.activator.Activator;
import com.stevejrong.gradle.multi.scaffold.constant.Constants;
import com.stevejrong.gradle.multi.scaffold.util.JWTUtil;
import com.stevejrong.gradle.multi.scaffold.util.RegexUtil;
import com.stevejrong.gradle.multi.scaffold.wizard.page.base.BaseWizardPage;

/**
 * 页面二
 * 子项目相关设置
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月6日 下午3:11:32
 */
public class GradleMultiScaffoldPageTwo extends BaseWizardPage {
	private Composite container; // 容器
	
	public List<Text> getAllTextControl() {
		return JWTUtil.getAllTextControl(container);
	}

	public GradleMultiScaffoldPageTwo(ISelection selection) {
		super("Fast construction of Gradle multi module project"); // 快速构建Gradle多模块项目
		pageInit();
		dataInit();
	}
	
	void pageInit() {
		setTitle("Fast build Gradle multi module project with customizable configuration"); // 快速构建可定制配置的Gradle多模块项目
		setDescription("Setting up the basic information of subprojects."); // 设置子项目的基本信息。
		ImageDescriptor image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/gradle-logo-64.png");
		setImageDescriptor(image);
	}
	
	void dataInit() {
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite composite) {
		container = new Composite(composite, SWT.NULL); // 实例化容器
		
		GridLayout layout = new GridLayout(); // 创建表格布局
		container.setLayout(layout); // 将创建的表格布局和容器相关联
		layout.numColumns = 2;
		layout.verticalSpacing = 8;
		
		@SuppressWarnings("unused")
		Label spaceLabel = new Label(container, SWT.NULL);
		
		Button addSubProjectButton = new Button(container, SWT.PUSH);
		GridData gridDataOfAddSubProject = new GridData(GridData.HORIZONTAL_ALIGN_END);
		addSubProjectButton.setText("Add new subproject");
		addSubProjectButton.setLayoutData(gridDataOfAddSubProject);
		addSubProjectButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Label subProjectLabel = new Label(container, SWT.NULL);
				subProjectLabel.setText("Subproject name :");
				
				Text subProjectText = new Text(container, SWT.BORDER | SWT.SINGLE);
				GridData gridDataOfsubProject = new GridData(GridData.FILL_HORIZONTAL);
				subProjectText.setLayoutData(gridDataOfsubProject);
				subProjectText.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						pageValidate(subProjectTextChanged(getAllTextControl().toArray()));
					}
				});
				
				container.layout();
			}
		});
		
		setControl(container);
	}
	
	String subProjectTextChanged(Object... subProjectText) {
		for (int i = 0; i < subProjectText.length; i++) {
			Text currentText = (Text) subProjectText[i];
			String content = currentText.getText();
			if (content.trim().length() <= 0 || "".equals(content)) {
				return "The name of the subproject you filled out is invalid, please fill in the correct project name.";
			} else if (!RegexUtil.matcher(Constants.REGEX_SUB_PROJECT_NAME, content)) {
				return "The name of the subproject is not correct, please fill in the correct name of the subproject.";
			}
		}
		
		return "";
	}
}
