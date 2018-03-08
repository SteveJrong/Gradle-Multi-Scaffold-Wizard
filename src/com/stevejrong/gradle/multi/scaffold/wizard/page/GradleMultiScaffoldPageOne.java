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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.stevejrong.gradle.multi.scaffold.activator.Activator;
import com.stevejrong.gradle.multi.scaffold.constant.Constants;
import com.stevejrong.gradle.multi.scaffold.util.RegexUtil;
import com.stevejrong.gradle.multi.scaffold.wizard.page.base.BaseWizardPage;

/**
 * 页面一
 * 根项目相关设置
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月6日 下午1:11:17
 */
public class GradleMultiScaffoldPageOne extends BaseWizardPage {
	private Composite container; // 容器
	private Text rootProjectPathText; // 根项目存储路径
	private Text rootProjectNameText; // 根项目名称
	
	/**
	 * 获取用户选择的根项目存储位置
	 * @return 用户选择的根项目存储位置
	 */
	public String getRootProjectPathText() {
		return null == rootProjectPathText ? null : rootProjectPathText.getText();
	}
	
	/**
	 * 获取用户填写的根项目名称
	 * @return 用户填写的根项目名称
	 */
	public String getRootProjectNameText() {
		return null == rootProjectNameText ? null : rootProjectNameText.getText();
	}
	
	public GradleMultiScaffoldPageOne(ISelection selection) {
		super("Fast construction of Gradle multi module project"); // 快速构建Gradle多模块项目
		pageInit();
		dataInit();
		super.pageValidate(rootProjectPathChanged(), rootProjectNameChanged());
	}

	void pageInit() {
		setTitle("Fast build Gradle multi module project with customizable configuration"); // 快速构建可定制配置的Gradle多模块项目
		setDescription("Setting up the basic information of the root project."); // 设置根项目的基本信息。
		ImageDescriptor image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/gradle-logo-64.png");
		setImageDescriptor(image);
	}
	
	void dataInit() {
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite composite) {
		container = new Composite(composite, SWT.NULL);
		
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		
		/* ----------------------------- start：根项目存储位置选择 ----------------------------- */
		Label rootProjectPathLabel = new Label(container, SWT.NULL);
		rootProjectPathLabel.setText("Root project location :");
		rootProjectPathText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gridDataOfRootProjectPath = new GridData(GridData.FILL_HORIZONTAL);
		rootProjectPathText.setLayoutData(gridDataOfRootProjectPath);
		rootProjectPathText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				pageValidate(rootProjectPathChanged(), rootProjectNameChanged());
			}
		});
		Button browseButton = new Button(container, SWT.PUSH);
		browseButton.setText("Browse...");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				browseButtonSelectionChanged();
			}
		});
		/* ----------------------------- end：根项目存储位置选择 ----------------------------- */
		
		
		/* ----------------------------- start：根项目名称 ----------------------------- */
		Label rootProjectNameLabel = new Label(container, SWT.NULL);
		rootProjectNameLabel.setText("Root project name :");
		rootProjectNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gridDataOfRootProjectName = new GridData(GridData.FILL_HORIZONTAL);
		rootProjectNameText.setLayoutData(gridDataOfRootProjectName);
		rootProjectNameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				pageValidate(rootProjectPathChanged(), rootProjectNameChanged());
			}
		});
		/* ----------------------------- end：根项目名称 ----------------------------- */
		
		setControl(container); // 将容器设置到当前窗体中
	}

	String rootProjectPathChanged() {
		if (null == getRootProjectPathText() || getRootProjectPathText().trim().length() <= 0) {
			return "The root project path does not exist, please select the storage location of the root project.";
		} else {
			IResource mainProjectPathContainer = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getRootProjectPathText()));
			if (null != mainProjectPathContainer && !mainProjectPathContainer.isAccessible()) {
				return "This path is read-only, please change the other path."; // 路径是只读的，提示用户更换其他路径
			}
		}
		return "";
	}
	
	 String browseButtonSelectionChanged() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setFilterPath(root.getLocationURI().getPath().substring(1, root.getLocationURI().getPath().lastIndexOf("/")));
		dialog.open(); // 打开目录选择对话框。在用户选择好目录之前，线程一直处于阻塞状态
		this.rootProjectPathText.setText(dialog.getFilterPath()); // 用户点击对话框中的“确定”按钮后，将主项目位置Text文本框的值更新为用户选择的位置
		return "";
	}
	
	 String rootProjectNameChanged() {
		if (null == getRootProjectNameText() || getRootProjectNameText().trim().length() <= 0) {
			return "The name of the root project is empty, please fill in the correct name of the root project.";
		} else if (!RegexUtil.matcher(Constants.REGEX_ROOT_PROJECT_NAME, getRootProjectNameText())) {
			return "The name of the root project is not correct, please fill in the correct name of the root project.";
		}
		return "";
	}
}
