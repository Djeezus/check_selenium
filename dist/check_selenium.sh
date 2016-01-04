#!/bin/bash
WD="$(dirname $0)"
CPDIR="${WD}/lib"

[ -z $JAVA_HOME ] && JAVA_HOME=/eso/jdk1.7.0_15
CLASSPATH=${CPDIR}/java-getopt-1.0.14.jar:${CPDIR}/junit-4.10.jar:${CPDIR}/check-selenium.jar:${CPDIR}/commons-cli-1.2.jar:${CPDIR}/selenium-server-standalone-2.35.0.jar:${CPDIR}/selenium-tests.jar:${WD}/.

$JAVA_HOME/bin/java -cp "${CLASSPATH}" org.godsells.CallSeleniumTest $@
