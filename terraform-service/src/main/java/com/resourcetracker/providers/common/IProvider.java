package com.resourcetracker.providers.common;

import java.net.URL;

public interface IProvider {
	/**
	 * Starts remote execution on a chosen provider
	 * @param context When user runs ResourceTracker, it reads
	 *                configuration file, then creates special context
	 *                to run some execution data on a remote resource.
	 * @return URL endpoint to the remote resources where execution is
	 * going
	 */
	public URL start(String context);

	/**
	 * Stops remote execution on a chosen provider
	 */
	public void stop();
}
