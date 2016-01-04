package org.godsells;

import java.util.ArrayList;
import java.util.List;

public class ServiceOutput {

	private static final boolean debugmode=false;
	
	private static String serviceOutputName="";
	private static String serviceOutputState="";
	private static String serviceOutputException="";
	private static double totalTime=0;
	private static double realTime=0;
	private static List<String> serviceOutputStepInfo= new ArrayList<String>();
	private static String serviceOutputScreenshot="";
	
	public static void setName(String name){
		serviceOutputName=name;
	}
	
	public static void setState(String state){
		serviceOutputState=state;
	}
	
	public static void setException(String exception){
		serviceOutputException=exception;
	}
	
	public static void addTotalTime(double totalTime){
		ServiceOutput.totalTime=ServiceOutput.totalTime+totalTime;
	}
	
	public static void setTotalTime(double totalTime){
		ServiceOutput.totalTime=totalTime;
	}
	
	public static void addRealTime(double realTime){
		ServiceOutput.realTime=ServiceOutput.realTime+realTime;
	}
	
	public static void setRealTime(double realTime){
		ServiceOutput.realTime=realTime;
	}
	
	public static void addStepInfo(int state,String name,double time, boolean primary){
		String stepInfoString="<tr>";
		
		if(primary){
			String stateDescription;
			switch (state) {
		        case 0:  stateDescription="OK     ";
		                 break;
		        case 2:  stateDescription="CRIT   ";
		                 break;
		        case 3:  stateDescription="UNKNOWN";
		                 break;
		        default: stateDescription="INVALID";
		                 break;
			}
			stepInfoString=stepInfoString+"<td>"+stateDescription+"</td>";
			stepInfoString=stepInfoString+"<td>"+name+"</td>";
		}
		else{
			String stateDescription;
			switch (state) {
		        case 0:  stateDescription="   v   ";
		                 break;
		        case 2:  stateDescription="   x   ";
		                 break;
		        case 3:  stateDescription="   u   ";
		                 break;
		        default: stateDescription="   i   ";
		                 break;
			}
			stepInfoString=stepInfoString+"<td>"+stateDescription+"</td>";
			stepInfoString=stepInfoString+"<td>"+name+"</td>";
		}
		stepInfoString=stepInfoString+"<td>"+time+" ms</td>";
		
		stepInfoString=stepInfoString+"</tr>";
		
		serviceOutputStepInfo.add(stepInfoString);
	}
	
	public static void setScreenshot(String screenshot){
		serviceOutputScreenshot=screenshot;
	}
	

	public static String getTotalString(){
		if(debugmode==false){
			String totalString="<b>"+serviceOutputName+"</b><br><br>";
			totalString=totalString+"<i>"+serviceOutputState+"</i><br><br>";
			//totalString=totalString+serviceOutputException+"<br><br><br>";
			double overheadTime=totalTime-realTime;
			totalString=totalString+"Totaltime:"+totalTime+"    Overheadtime:"+overheadTime+"    Realtime:"+realTime+"<br>";
			totalString=totalString+"<br><table  style=\"border-width: 1px; border-color:#000000; border-style: solid;\">";
			for (int i = 0; i < serviceOutputStepInfo.size(); i++) {
				totalString=totalString+serviceOutputStepInfo.get(i);
			}
			totalString=totalString+"</table><br>";
			if(serviceOutputScreenshot!=""){
				String temp=serviceOutputScreenshot;
				temp=temp.substring(14,temp.length());
				temp="http://esomonbnc1/"+temp;
				totalString=totalString+"<center><img class=\"graphimage\" src=\""+temp+"\" border=\"0\" width=60%></center>";
			}
			return totalString;
		}
		else{
			String totalString=serviceOutputName+"&lt;br&gt;\n";
			totalString=totalString+serviceOutputState+"&lt;br&gt;\n";
			totalString=totalString+serviceOutputException+"&lt;br&gt;\n";
			double overheadTime=totalTime-realTime;
			totalString=totalString+"Totaltime:"+totalTime+"    Overheadtime:"+overheadTime+"    Realtime:"+realTime+"&lt;br&gt;\n";
			totalString=totalString+"&lt;table&gt;\n";
			for (int i = 0; i < serviceOutputStepInfo.size(); i++) {
				totalString=totalString+serviceOutputStepInfo.get(i)+"\n";
			}
			totalString=totalString+"&lt;/table&gt;&lt;br&gt;\n";
			if(serviceOutputScreenshot!=""){
				totalString=totalString+"&lt;img class='graphimage' src='"+serviceOutputScreenshot+"' border='0' width=100%&gt;\n";
			}
			return totalString;
		}
	}

	public static String getTotalStringText() {
		String totalStringText=serviceOutputName+"\n";
                        totalStringText=totalStringText+serviceOutputState+"\n";
                        totalStringText=totalStringText+serviceOutputException+"\n";
                        double overheadTime=totalTime-realTime;
                        totalStringText=totalStringText+"Totaltime:"+totalTime+"    Overheadtime:"+overheadTime+"    Realtime:"+realTime+"\n";
                        totalStringText=totalStringText+"\n";
                        for (int i = 0; i < serviceOutputStepInfo.size(); i++) {
                                totalStringText=totalStringText+serviceOutputStepInfo.get(i)+"\n";
                        }
                        totalStringText=totalStringText+"\n";
                        if(serviceOutputScreenshot!=""){
                                totalStringText=totalStringText+"&lt;img class='graphimage' src='"+serviceOutputScreenshot+"' border='0' width=100%&gt;\n";
                        }
                        return totalStringText;
	}

	
}
