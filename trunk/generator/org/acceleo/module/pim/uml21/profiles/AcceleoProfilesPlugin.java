package org.acceleo.module.pim.uml21.profiles;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class AcceleoProfilesPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.acceleo.module.pim.uml21.profiles";

	// The shared instance
	private static AcceleoProfilesPlugin plugin;
	
	/**
	 * The constructor
	 */
	public AcceleoProfilesPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
	public static AcceleoProfilesPlugin getDefault() {
		return plugin;
	}

}
