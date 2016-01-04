package org.godsells;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

public class SeleniumProxy implements InvocationHandler{

	private Object obj;
	private ExtendedWebDriver driver;
	private String[] invokeExceptions = {"toString"};
	private boolean primary;
	public static boolean takeScreenshot=false;
	private final static boolean debugmode=false;

	public Object getObject(){
		return obj;
	}
	
    public SeleniumProxy(Object obj,ExtendedWebDriver driver,String[] invokeExceptions,boolean primary){
    	this.obj = obj;
    	this.driver = driver;
    	this.invokeExceptions=invokeExceptions;
    	this.primary=primary;
    	
	}

	public static Object newInstance(Object obj,ExtendedWebDriver driver,String[] invokeExceptions,boolean primary) {
        return java.lang.reflect.Proxy.newProxyInstance(
            obj.getClass().getClassLoader(),
            obj.getClass().getInterfaces(),
            new SeleniumProxy(obj,driver,invokeExceptions,primary));
    }
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		if(!Arrays.asList(invokeExceptions).contains(method.getName())){
			 long start = System.nanoTime();
			 String exceptionDescription="";
			 int state=0;
			 boolean primary;
			 Object tempReturn=null;
			 try {
				 tempReturn=method.invoke(obj, args);
				 state=0;
			 }
			 catch (InvocationTargetException e) {  
				 state=2;
				 exceptionDescription=e.getCause().getMessage();
				// throw new Exception();
				 throw e;//.getCause();  
			 }  
			 catch(Exception e ){
				 state=2;
				 exceptionDescription=e.getMessage();
				 throw e;
			 }
			 finally{
				 double elapsedTimeInSec = Math.floor((System.nanoTime() - start) * 1.0e-6);
				 addToServiceOutput(state,method,args,elapsedTimeInSec,exceptionDescription);
			 }	 
			 return tempReturn;
		 }
		 else{
			 return method.invoke(obj, args);
		 }
	}
	

	private void addToServiceOutput(int state, Method method, Object[] args,double elapsedTimeInSec,String exceptionDescription){
		if(state==2){
			String totalURI;
			try {
				if(takeScreenshot){
					totalURI = driver.takeScreenshot();
					ServiceOutput.setScreenshot(totalURI);
				}
			} catch (IOException e) {
				
			}
		}
		else if(debugmode==true){
			 try {
				 if(takeScreenshot){
					 driver.takeScreenshot();
				 }
			} catch (IOException e) {

			}
		}
		else{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				
			}
		}
		
		ServiceOutput.setException(exceptionDescription);
		
		ServiceOutput.addRealTime(elapsedTimeInSec);
		
		if(args!=null){
			ServiceOutput.addStepInfo(state,method.getName()+"("+args[0].toString()+")", elapsedTimeInSec, primary);
		}
		else{
			ServiceOutput.addStepInfo(state,method.getName()+"()", elapsedTimeInSec, primary);
		}
	}

}
