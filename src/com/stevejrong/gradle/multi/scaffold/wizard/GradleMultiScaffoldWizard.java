package com.stevejrong.gradle.multi.scaffold.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.stevejrong.gradle.multi.scaffold.activator.Activator;
import com.stevejrong.gradle.multi.scaffold.business.service.impl.GradleRootProject;
import com.stevejrong.gradle.multi.scaffold.business.service.impl.GradleSubProject;
import com.stevejrong.gradle.multi.scaffold.util.GradleProjectUtil;
import com.stevejrong.gradle.multi.scaffold.util.StorageUtil;
import com.stevejrong.gradle.multi.scaffold.wizard.page.GradleMultiScaffoldPageOne;
import com.stevejrong.gradle.multi.scaffold.wizard.page.GradleMultiScaffoldPageThree;
import com.stevejrong.gradle.multi.scaffold.wizard.page.GradleMultiScaffoldPageTwo;

/**
 * 向导配置器类
 * 在提供的容器中创建一个新的文件资源。如果在向导打开时在工作区中选择了容器资源（文件夹或项目），
 * 它将接受它作为目标容器。向导将创建一个文件扩展名“摇篮”。如果为相同的扩展注册一个示例多页编辑器
 * （也可以作为模板），它将能够打开它。
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月6日 下午12:02:18
 */
public class GradleMultiScaffoldWizard extends Wizard implements INewWizard {
	private ISelection selection;
	private GradleMultiScaffoldPageOne pageOne;
	private GradleMultiScaffoldPageTwo pageTwo;
	private GradleMultiScaffoldPageThree pageThree;

	/**
	 * 向导配置器初始化
	 */
	public GradleMultiScaffoldWizard() {
		super();
		setNeedsProgressMonitor(true); // 设置此向导需要进度条显示
	}

	/**
	 * 在向导配置器中添加所需要的所有页面
	 */
	public void addPages() {
		pageOne = new GradleMultiScaffoldPageOne(selection);
		pageTwo = new GradleMultiScaffoldPageTwo(selection);
		pageThree = new GradleMultiScaffoldPageThree(selection);
		
		addPage(pageOne);
		addPage(pageTwo);
		addPage(pageThree);
	}

	/**
	 * 在向导中点击最后一步的“完成”按钮触发的方法
	 * 这里将执行最根本的业务逻辑。如果向导未完成，则返回false，否则返回true结束整个向导
	 */
	public boolean performFinish() {
		GradleRootProject rootProject = StorageUtil.getInstance();
		
		// 设置根项目信息
		rootProject.setProjectPath(pageOne.getRootProjectPathText());
		rootProject.setProjectName(pageOne.getRootProjectNameText());
		rootProject.setBuildEncode(pageThree.getBuildEncodeComboText());
		rootProject.setRepositoryUrl(pageThree.getRepositoriesComboText());
		
		// 设置子项目信息
		for (Text textControl : pageTwo.getAllTextControl()) {
			GradleSubProject subProject = new GradleSubProject();
			subProject.setProjectPath(pageOne.getRootProjectPathText() + File.separatorChar + pageOne.getRootProjectNameText() + File.separatorChar + textControl.getText());
			subProject.setProjectName(textControl.getText());
			subProject.setBuildEncode(pageThree.getBuildEncodeComboText());
			subProject.setRepositoryUrl(pageThree.getRepositoriesComboText());
			
			rootProject.getSubProjects().add(subProject);
		}
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(rootProject, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		
		StorageUtil.cleanUp();
		return true;
	}

	/**
	 * 这是一个具体的业务处理方法
	 * 如点击向导的“完成”按钮后，会新建一个文件或者项目等。此方法会在performFinish()方法中调用，配合IProgressMonitor来显示处理任务的进度
	 * 
	 * @param containerName
	 * @param fileName
	 * @param monitor
	 * @throws CoreException
	 */
	private void doFinish(GradleRootProject rootProject, IProgressMonitor monitor) throws CoreException {
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (root.getLocationURI().getPath().length() <= 0) {
			throwCoreException("The main project location is not set correctly, please set the correct main project location."); // 主项目存储路径设置不正确，请设置正确的主项目存储路径。
		}
		
		monitor.beginTask("Create multi module projects.", rootProject.getSubProjects().size() + 1);
		
		createRootProject(rootProject, monitor);
		createSubProject(rootProject, monitor);
	}
	
	/**
	 * 创建根项目
	 * @param rootProject
	 * @param monitor
	 */
	void createRootProject(GradleRootProject rootProject, IProgressMonitor monitor) {
		monitor.setTaskName("Building root project "+ rootProject.getProjectName() + "...");
		
		String fullProjectPath = rootProject.getProjectPath() + File.separatorChar + rootProject.getProjectName(); // 项目完全路径，包括项目名称
		GradleProjectUtil.createSimpleGradleProject(rootProject, fullProjectPath);
		
		monitor.worked(1);
	}
	
	/**
	 * 创建子项目
	 * @param subProjects
	 * @param monitor
	 */
	private void createSubProject(GradleRootProject rootProject, IProgressMonitor monitor) {
		List<GradleSubProject> subProjects = rootProject.getSubProjects();
		
		for (int i = 0; i < subProjects.size(); i++) {
			monitor.setTaskName("Building subproject "+ subProjects.get(i).getProjectName() + "...");
			
			String fullProjectPath = rootProject.getProjectPath() + File.separatorChar + rootProject.getProjectName() + File.separatorChar + subProjects.get(i).getProjectName(); // 项目完全路径，包括项目名称
			GradleProjectUtil.createSimpleGradleProject(subProjects.get(i), fullProjectPath);
			
			monitor.worked(i + 1);
		}
	}
	
	/**
	 * 取消向导时触发的方法
	 */
	@Override
	public boolean performCancel() {
		boolean request = MessageDialog.openConfirm(getShell(), "Cancel the creation wizard", "Do you cancel the creation of a task?");
		return request;
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK, message, null);
		throw new CoreException(status);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}