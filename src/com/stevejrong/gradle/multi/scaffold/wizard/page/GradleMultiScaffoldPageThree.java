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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.stevejrong.gradle.multi.scaffold.activator.Activator;
import com.stevejrong.gradle.multi.scaffold.constant.Constants;
import com.stevejrong.gradle.multi.scaffold.wizard.page.base.BaseWizardPage;

/**
 * 页面三
 * 自定义生成设置
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月8日 上午10:40:46
 */
public class GradleMultiScaffoldPageThree extends BaseWizardPage {
	private Composite container; // 容器
	private Combo repositoriesCombo; // 软件源下拉框对象
	private Combo buildEncodeCombo; // 构建编码格式下拉框对象
	
	public String getRepositoriesComboText() {
		return repositoriesCombo.getText();
	}

	public String getBuildEncodeComboText() {
		return buildEncodeCombo.getText();
	}

	public GradleMultiScaffoldPageThree(ISelection selection) {
		super(Constants.WIZARD_PAGE_NAME);
		pageInit();
		dataInit();
	}
	
	void pageInit() {
		setTitle(Constants.WIZARD_PAGE_TITLE);
		setDescription(Constants.WIZARD_PAGE_THREE_DESCRIPTION);
		ImageDescriptor image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, Constants.WIZARD_PAGE_ICON_RELATIVE_PATH);
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
		layout.verticalSpacing = 5;
		
		/* ---------------------- start：软件仓库源 ---------------------- */
		Label repositoriesLabel = new Label(container, SWT.NULL);
		repositoriesLabel.setText("repositories :");
		repositoriesCombo = new Combo(container, SWT.BORDER);
		GridData gridDataOfRepositoriesCombo = new GridData(GridData.FILL_HORIZONTAL);
		repositoriesCombo.setLayoutData(gridDataOfRepositoriesCombo);
		Constants.DEFAULT_GRADLE_REPOSITORIES.forEach(action -> {
			repositoriesCombo.add(action);
		});
		repositoriesCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pageValidate(repositoriesComboSelectionChanged(), buildEncodeComboSelectionChanged());
			}
		});
		/* ---------------------- end：软件仓库源 ---------------------- */
		
		/* ---------------------- start：构建编码格式 ---------------------- */
		Label buildEncodeLabel = new Label(container, SWT.NULL);
		buildEncodeLabel.setText("build encode :");
		 buildEncodeCombo = new Combo(container, SWT.BORDER);
		GridData gridDataOfBuildEncodeCombo = new GridData(GridData.FILL_HORIZONTAL);
		buildEncodeCombo.setLayoutData(gridDataOfBuildEncodeCombo);
		Constants.DEFAULT_BUILD_ENCODE.forEach(action -> {
			buildEncodeCombo.add(action);
		});
		buildEncodeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pageValidate(repositoriesComboSelectionChanged(), buildEncodeComboSelectionChanged());
			}
		});
		/* ---------------------- end：构建编码格式 ---------------------- */
		
		setControl(container); // 将容器设置到当前窗体中
	}

	String repositoriesComboSelectionChanged() {
		if (null == getRepositoriesComboText() || getRepositoriesComboText().trim().equals("")) {
			return "The source of the software is not selected. Please select a software source."; // 软件源未选择，请选择一个软件源。
		}
		return "";
	}
	
	String buildEncodeComboSelectionChanged() {
		if (null == getBuildEncodeComboText() || getBuildEncodeComboText().trim().equals("")) {
			return "The encoding format is not selected, please select a Gradle compiled encoding format."; // 编码格式未选择，请选择一个Gradle编译时的编码格式。
		}
		return "";
	}
}
