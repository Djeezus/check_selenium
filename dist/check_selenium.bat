set JAVA="C:\Program Files\Java\jdk1.7.0_09\bin\java"
set CLASSPATH=junit\junit-4.10.jar;lib\selenium-java-client-driver.jar;lib\check-selenium.jar;lib\commons-cli-1.2.jar;com\eso\scenarios\.;lib\selenium-java-2.28.0.jar;server\selenium-server-standalone-2.28.0.jar;.\.

%JAVA% -classpath "%CLASSPATH%" info.devopsabyss.CallSeleniumTest %*

