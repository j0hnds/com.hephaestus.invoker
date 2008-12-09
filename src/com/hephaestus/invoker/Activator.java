package com.hephaestus.invoker;

import java.util.Hashtable;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.hephaestus.http.HttpMethodInvoker;
import com.hephaestus.http.invokers.DeleteMethodInvoker;
import com.hephaestus.http.invokers.GetMethodInvoker;
import com.hephaestus.http.invokers.PostMethodInvoker;
import com.hephaestus.http.invokers.PutMethodInvoker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.hephaestus.invoker";

	// The shared instance
	private static Activator plugin;
	
	private ServiceRegistration srGet;
	private ServiceRegistration srPost;
	private ServiceRegistration srPut;
	private ServiceRegistration srDelete;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		registerServices(context);
	}

	private void registerServices(BundleContext context) {
		// Instantiate an instance of the service
		HttpMethodInvoker getInvoker = new GetMethodInvoker();
		HttpMethodInvoker postInvoker = new PostMethodInvoker();
		HttpMethodInvoker putInvoker = new PutMethodInvoker();
		HttpMethodInvoker deleteInvoker = new DeleteMethodInvoker();
		
		Hashtable<String,String> getProperties = new Hashtable<String,String>();
		getProperties.put("METHOD", "GET");
		srGet = context.registerService(HttpMethodInvoker.class.getName(), getInvoker, getProperties);
		
		Hashtable<String,String> postProperties = new Hashtable<String,String>();
		postProperties.put("METHOD", "POST");
		srPost = context.registerService(HttpMethodInvoker.class.getName(), postInvoker, postProperties);
		
		Hashtable<String,String> putProperties = new Hashtable<String,String>();
		putProperties.put("METHOD", "PUT");
		srPut = context.registerService(HttpMethodInvoker.class.getName(), putInvoker, putProperties);
		
		Hashtable<String,String> deleteProperties = new Hashtable<String,String>();
		deleteProperties.put("METHOD", "DELETE");
		srDelete = context.registerService(HttpMethodInvoker.class.getName(), deleteInvoker, deleteProperties);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		if (srGet != null) {
			srGet.unregister();
		}
		if (srPost != null) {
			srPost.unregister();
		}
		if (srPut != null) {
			srPut.unregister();
		}
		if (srDelete != null) {
			srDelete.unregister();
		}
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
