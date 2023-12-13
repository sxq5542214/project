/**
 * 
 */
package com.yd.basic.framework.runable;

import org.apache.log4j.Logger;

/**
 * @author ice
 *
 */
public abstract class BaseRunable implements Runnable {
	
	protected Logger log = Logger.getLogger(getClass());
	
	@Override
	public void run() {
		try{
			
			runMethod();
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
	}
	
	public abstract void runMethod() throws Exception;
	
}
