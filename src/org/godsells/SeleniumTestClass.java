package org.godsells;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumTestClass {
	
	private String screenshotURI="screenshots/";
	
	//protected ExtendedWebDriverInterface driver;
	protected boolean detailedOutput=false;
	protected boolean takeScreenshot=false;
	protected String baseUrl;
	protected String browser;
	protected long timeout;
	
	
	protected ExtendedWebDriverInterface driver;

	protected StringBuffer verificationErrors = new StringBuffer();
	
	public void initialise(String baseUrl) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		  
		long timeout=Long.valueOf(System.getProperty("timeout"));
		  
		if(System.getProperty("detailedOutput")=="1"){
			detailedOutput=true;}
		else{
			detailedOutput=false;}
		
		String scrot=System.getProperty("scrot");
		if(scrot==null){
			 takeScreenshot=false;}
		else{
			screenshotURI=scrot;
			capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			takeScreenshot=true;}
			
	    capabilities.setBrowserName(System.getProperty("browser"));

		if ( System.getProperty("browser").equals("chromeportable")) {
			capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
			capabilities.setCapability("chrome.binary","C:/ESO/GoogleChromePortable/chrome.exe");
			capabilities.setBrowserName("chrome");
		} 
		if ( System.getProperty("browser").equals("chrome")) {
			capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
		}

		/*
		if ( System.getProperty("proxy").contains(":"))  {
			System.out.println("proxy arg : " + System.getProperty("proxy"));
			Proxy prx = new Proxy();
			prx.setHttpProxy(System.getProperty("proxy"));
			prx.setHttpsProxy(System.getProperty("proxy"));
			capabilities.setCapability(CapabilityType.PROXY, prx) ;
		} else { 
			System.out.println("proxy arg nada");
		}
		*/

	    this.baseUrl=baseUrl;
	    
	    String gridURI=System.getProperty("gridhub");
	        
	    String[] invokeExceptions = {"manage", "quit","getScreenshotAs" };
	        
	    if(detailedOutput||takeScreenshot){
	        ExtendedWebDriver tempDriver =new ExtendedWebDriver(new URL(gridURI), capabilities,screenshotURI);
	        driver = (ExtendedWebDriverInterface)SeleniumProxy.newInstance(tempDriver,tempDriver,invokeExceptions,true);
	        SeleniumProxy.takeScreenshot=takeScreenshot;
	    }
	     else {
	       	driver = new ExtendedWebDriverWithoutProxy(new URL(gridURI), capabilities);
	    }
	         
	     driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		if (!System.getProperty("browser").contains("chrome")) {
		driver.manage().window().maximize();
		}	  	  
	  }

     @After
     public void tearDown() throws Exception {
    	 
    	/**Runtime rt = Runtime.getRuntime();
    	Process pr = rt.exec("dxdiag");**/
     	driver.quit();
             //String verificationErrorString = verificationErrors.toString();
     	String verificationErrorString="";
             if (!"".equals(verificationErrorString)) {
                     fail(verificationErrorString);
             }
     }

     protected boolean isElementPresent(By by) {
            try {
                    driver.findElement(by);
                    return true;
            } catch (NoSuchElementException e) {
                    return false;
            }
    }
 
}
