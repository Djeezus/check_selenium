#!/bin/bash
#
# shell wrapperke om java te compilen
# - gert -
CPDIR=$(pwd)/lib
[ -d "org/godsells/tests" ] || mkdir -p org/godsells/tests
JDK_HOME=/eso/jdk1.7.0_15
CLASSPATH=${CPDIR}/junit-4.10.jar:${CPDIR}/selenium-server-standalone-2.35.0.jar:${CPDIR}/check-selenium.jar
JAVAC="${JDK_HOME}/bin/javac"
# eso packages ofcourse ;-)
sed -i 's,com.example.tests,org.godsells.test,g' $@
${JAVAC} -d ./ -cp "${CLASSPATH}" $@
