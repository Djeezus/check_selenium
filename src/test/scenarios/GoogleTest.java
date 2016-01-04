package test.scenarios;

import static org.junit.Assert.*;

import java.awt.Frame;

import org.godsells.SeleniumTestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.junit.*;

public class GoogleTest extends SeleniumTestClass {

  @Before
  public void setUp() throws Exception {
        super.initialise("http://www.google.com");
  }

  @Test
  public void GoogleTest() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.id("gbqfq")).clear();
    driver.findElement(By.id("gbqfq")).sendKeys("godsells.org");
  }

}
