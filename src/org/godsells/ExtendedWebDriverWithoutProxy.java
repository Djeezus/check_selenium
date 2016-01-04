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


public class ExtendedWebDriverWithoutProxy extends RemoteWebDriver implements
		ExtendedWebDriverInterface {

	public ExtendedWebDriverWithoutProxy(URL url,
			DesiredCapabilities capabilities) {
		super(url, capabilities);
	}

	@Override
	public WebElement findElementWithoutProxy(By by) {
		return super.findElement(by);
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScreenshotURI() {
		// TODO Auto-generated method stub
		return null;
	}


	

}
