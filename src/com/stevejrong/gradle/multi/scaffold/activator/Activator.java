package com.stevejrong.gradle.multi.scaffold.activator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * 激活器类
 * 控制插件的生命周期
 * 
 * @author Steve Jrong
 * @since 1.0
 * create date: 2018年3月6日 上午11:59:19
 */
public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.stevejrong.gradle.multi.scaffold.wizard.GradleMultiScaffoldWizard"; // 插件ID

	private static Activator plugin;

	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
