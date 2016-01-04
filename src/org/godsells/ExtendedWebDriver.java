package org.godsells;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ExtendedWebDriver extends RemoteWebDriver implements TakesScreenshot, ExtendedWebDriverInterface {
	private String screenshotURI;
	
	public ExtendedWebDriver(URL url, DesiredCapabilities dc,String screenshotURI){ 
		super(url, dc);
		this.screenshotURI=screenshotURI;
	}
	
	public String getScreenshotURI(){
		return screenshotURI;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target)
			throws WebDriverException {
		if ((Boolean) getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT)) {
		   return target.convertFromBase64Png(execute(DriverCommand.SCREENSHOT).getValue().toString());
		}
		return null;
	}

	public String takeScreenshot() throws IOException{
		 DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		 Date date = new Date();
		 long seconds = System.currentTimeMillis() / 1000l;//dateFormat.format(date)
		 String totalURI=getScreenshotURI()+"screenshot_"+seconds+".png";
		 String totalURIJPG=getScreenshotURI()+"screenshot_"+seconds+".jpg";
		 File f = getScreenshotAs(OutputType.FILE);//((ExtendedWebDriverInterface)proxy).getScreenshotAs(OutputType.FILE);
	 	 File o = new File(totalURI);
	 	 try {
			FileUtils.copyFile(f, o);
			String command="convert "+totalURI+" -quality 20% "+totalURIJPG;
			Runtime.getRuntime().exec(command); 
		 } 
	 	 finally{
	 		 
	 	 }
	 	 return totalURIJPG;
	}
	
	
	public WebElement findElementWithoutProxy(By by){
        return super.findElement(by);
	}
	
	@Override
	public WebElement findElement(By by){
	    String[] invokeExceptions = {"toString" };
        return (WebElement)SeleniumProxy.newInstance(super.findElement(by),this,invokeExceptions,false);
	}
	
	@Override
	public java.util.List findElements(By by){
		String[] invokeExceptions = {"toString" };
		java.util.List<WebElement> tempSet=super.findElements(by);
		java.util.List<WebElement> newSet=new ArrayList<WebElement>();
		for (int i = 0; i < tempSet.size(); i++) {
			newSet.add(i,(WebElement)SeleniumProxy.newInstance(tempSet.get(i), this,invokeExceptions,false));
		}
		return newSet;
	}
	
	@Override
	public TargetLocator switchTo(){
		String[] invokeExceptions = {"toString" };
        return (TargetLocator)SeleniumProxy.newInstance(new ExtendedRemoteTargetLocator(),this,invokeExceptions,false);
        //super.switchTo()
	}
	
	public class ExtendedRemoteTargetLocator extends RemoteTargetLocator implements TargetLocator{
		
		public ExtendedRemoteTargetLocator() {
			super();
		}
		
		@Override
        public WebDriver frame(WebElement webE) {
        	if(webE instanceof WebElement){//Dit is lelijk!!!
        		return super.frame(webE);
        	}
        	else{
        		return super.frame((WebElement) ((SeleniumProxy)webE).getObject());
        	}
        }
    }
}
