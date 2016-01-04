package org.godsells;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface ExtendedWebDriverInterface {

	void close();
	
	WebElement findElementWithoutProxy(By by);
	
	WebElement findElement(By by);
	
	java.util.List<WebElement> findElements(By by);
	
	void get(java.lang.String url);
	
	String getCurrentUrl();
	
	String getPageSource();
	
	String getTitle();
	
	String getWindowHandle();
	
	java.util.Set<java.lang.String> getWindowHandles();
	
	WebDriver.Options manage();
	
	WebDriver.Navigation navigate();
	
	void quit();

	WebDriver.TargetLocator switchTo();

	public <X> X getScreenshotAs(OutputType<X> target);
	
	public String getScreenshotURI();
}
