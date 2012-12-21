set CLASSPATH=junit\junit-4.10.jar;lib\selenium-java-client-driver.jar;server\selenium-server-standalone-2.26.0.jar;lib\check-selenium.jar;lib\commons-cli-1.2.jar;lib\selenium-example-tests.jar;libs\selenium-java-2.26.0.jar;libs\*;com\eso\scenarios\*

set JAVAC="C:\Program Files\Java\jdk1.7.0_03\bin\javac"
%JAVAC% -cp "%CLASSPATH%" -d ./ %*
