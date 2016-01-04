package org.godsells;

/*

 * This is a nagios plugin to integrate Selenium Test Cases into Nagios.
 * Copyright (C) 2010 Christian Zunker (devops.abyss@googlemail.com)
 * Adapted for grid support + GnuGetop command line parsing by Gert Vandelaer (gert@godsells.org)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.godsells.SeleniumTestClass;

// sanity requires GetOpt
import gnu.getopt.Getopt;

public class CallSeleniumTest {

	private final int NAGIOS_RC_OK = 0;
	private final int NAGIOS_RC_WARNING = 1;
	private final int NAGIOS_RC_CRITICAL = 2;
	private final int NAGIOS_RC_UNKNOWN = 3;

	private final String NAGIOS_TEXT_OK = "OK";
	private final String NAGIOS_TEXT_WARNING = "WARNING";
	private final String NAGIOS_TEXT_CRITICAL = "CRITICAL";
	private final String NAGIOS_TEXT_UNKNOWN = "UNKNOWN";

	private static boolean	DEBUG = false ;

	//TODO: compile java files only when no class file found. this way the user does not have to compile the sources.
	//		what is when i get compile errors? => return NAGIOS_UNKNOWN?

	private Result runJUnitTest(String className) throws ClassNotFoundException {
		Class<?> seleniumTestClass = Class.forName(className);
		return new JUnitCore().run(seleniumTestClass);
	}

	public static void main(String[] argv) throws Exception {

        Getopt g = new Getopt("check_selenium", argv, "3dhvc:b:g:s:p:t:");
        int x;

	boolean detailedInfo=false ;
	boolean nagios3=false;
	System.setProperty("detailedInfo","0");
	System.setProperty("timeout","30");
	System.setProperty("browser","internet explorer");
	System.setProperty("proxy","null");
	String scenario = null ;

        while ((x = g.getopt()) != -1)
        {
            switch (x) {
		case 'h':
			help();
			usage();
		case 'b':
			System.setProperty("browser",g.getOptarg());
			break;
		case 'g':
			System.setProperty("gridhub",g.getOptarg());
			break;
		case 'p':
			System.setProperty("proxy",g.getOptarg());
			break;
		case 's':
			System.setProperty("scrot",g.getOptarg());
			break;
		case 'd':
			System.setProperty("detailedInfo","1");
			detailedInfo=true;
			break;
		case 't':
			System.setProperty("timeout",g.getOptarg());
			break;	
		case '3':
			nagios3=true;
			break;
		case 'c':
			scenario = g.getOptarg();
			break;
		case 'v':
			DEBUG = true ;
			break;
		default:
			usage();
            }
	}

	CallSeleniumTest seTest = new CallSeleniumTest();

	String output = seTest.NAGIOS_TEXT_UNKNOWN + " - there was a booboo |";
	int nagios_rc = seTest.NAGIOS_RC_UNKNOWN;

	try {
		// TODO : "d"-option : implement RRD4J to feed rrds directly with timing info of each step
		Result result = seTest.runJUnitTest(scenario);
			if (result.wasSuccessful()) {
				ServiceOutput.setName(scenario);
				ServiceOutput.setState(seTest.NAGIOS_TEXT_OK);
				ServiceOutput.setTotalTime(result.getRunTime());
				if(detailedInfo){
					output = ServiceOutput.getTotalString();
				}
				output = ServiceOutput.getTotalStringText();
				nagios_rc = seTest.NAGIOS_RC_OK;
			} else {
				String failureMessage = result.getFailures().toString();
				ServiceOutput.setName(scenario);
				ServiceOutput.setState(seTest.NAGIOS_TEXT_CRITICAL);
				if (nagios3) {
					ServiceOutput.setTotalTime(result.getRunTime());
					if(detailedInfo){
						output = ServiceOutput.getTotalString();
					}
					output = output + failureMessage;
				} else {
					ServiceOutput.setTotalTime(result.getRunTime());
					if(detailedInfo){
						output = ServiceOutput.getTotalString();
					}
					output = output + withoutNewlines(failureMessage);
				}
				nagios_rc = seTest.NAGIOS_RC_CRITICAL;
			}
		} catch (NoClassDefFoundError ex) {
			printStackTraceWhenVerbose(g, ex);
			output = seTest.NAGIOS_TEXT_UNKNOWN + " - " + scenario + ": " + messageWithoutNewlines(ex) + " |";
			nagios_rc = seTest.NAGIOS_RC_UNKNOWN;
		} catch (ClassNotFoundException ex) {
			printStackTraceWhenVerbose(g, ex);
			output = seTest.NAGIOS_TEXT_UNKNOWN + " - " + scenario + ": Testcase class " + messageWithoutNewlines(ex) + " not found! |";
			nagios_rc = seTest.NAGIOS_RC_UNKNOWN;
		} catch (Throwable ex) {
			printStackTraceWhenVerbose(g, ex);
			output = seTest.NAGIOS_TEXT_CRITICAL + " - " + scenario + ": " + messageWithoutNewlines(ex) + " |";
			nagios_rc = seTest.NAGIOS_RC_CRITICAL;
		} finally {
			System.out.println(output);
			System.exit(nagios_rc);
		}
	}

	private static String messageWithoutNewlines(final Throwable ex) {
		return withoutNewlines(ex.getMessage());
	}

	private static String withoutNewlines(final String message) {
		return message.replaceAll("\n", " ")
			.replaceAll("  ", " ")
			.replaceAll("  ", " ")
			.replaceAll("  ", " ");
	}

	private static void printStackTraceWhenVerbose(final Getopt g, final Throwable ex) {
		if (DEBUG) {
			ex.printStackTrace();
		}
	}

	private static void help() {
		System.out.println("A selnium wrapper to be used with Nagios");
		System.out.println("");
		System.out.println("This version of check_selenium was tested with:");
		System.out.println("  - selenium server 2.32.0");
		System.out.println("  - selenium ide 1.9+");
		System.out.println("  - test case exported as JUnit 4 (Webdriver)");
	}

	private static void usage() {
		System.out.println("./check_selenium");
		System.out.println("-h  : This cruft");
		System.out.println("-b  : browser to use	; eg. -b chrome or <empty> for IExploder");
		System.out.println("-g  : grid selenium 	; eg. -g http://10.10.10.10:4444/wd/hub");
		System.out.println("-p  : remote browser proxy	; eg. -p http://localhost:3128");
		System.out.println("-s  : screenshot path	; eg. -s /my/path/for/screenshots");
		System.out.println("-d  : detailed step info");
		System.out.println("-t  : timeout in seconds	; eg. -t 10");
		System.out.println("-3  : Nagios3 compatible");
		System.out.println("-c  : scenario to run	; eg. -c \"org.godsells.GoogleTest\"");
		System.out.println("-v  : be verbose about it");
		System.exit (0);
	}
}
