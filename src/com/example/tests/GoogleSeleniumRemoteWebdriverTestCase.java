package com.example.tests

// Import classes needed later on
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.thoughtworks.selenium.Selenium;


// IDE export jUnit WebDrv
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class GoogleSeleniumRemoteWebdriverTestCase {
        private WebDriver driver ;
        private String baseUrl;
        private StringBuffer verificationErrors = new StringBuffer();
        @Before
        public void setUp() throws Exception {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                driver = new RemoteWebDriver(new URL("http://10.119.170.210:4444/wd/hub"), capabilities);
                baseUrl = "http://www.google.de/";
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }

        @Test
        public void testGoogleSeleniumWebdriverTestCase() throws Exception {
                driver.get(baseUrl);
                driver.findElement(By.id("gbqfq")).clear();
                driver.findElement(By.id("gbqfq")).sendKeys("selenium hq");
        }

        @After
        public void tearDown() throws Exception {
                driver.quit();
                String verificationErrorString = verificationErrors.toString();
                if (!"".equals(verificationErrorString)) {
                        fail(verificationErrorString);
                }
        }

        private boolean isElementPresent(By by) {
                try {
                        driver.findElement(by);
                        return true;
                } catch (NoSuchElementException e) {
                        return false;
                }
        }
}
