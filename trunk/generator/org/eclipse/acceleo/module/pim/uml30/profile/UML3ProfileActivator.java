package org.eclipse.acceleo.module.pim.uml30.profile;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class UML3ProfileActivator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.acceleo.module.pim.uml30.profiles"; //$NON-NLS-1$

	// The shared instance
	private static UML3ProfileActivator plugin;

	/**
	 * The constructor
	 */
	public UML3ProfileActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static UML3ProfileActivator getDefault() {
		return plugin;
	}

}
