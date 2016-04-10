package com.GUI;

public class LogColor {


	// Done by Matthew
	public static String setStringInGreen(String outputStr){
		outputStr = "<font color=\"green\">" + outputStr + "</font>";
		return outputStr;
	}
	
	public static String setStringInRed(String outputStr){
		outputStr = "<font color=\"red\">" + outputStr + "</font>";
		return outputStr;
	}
	
	public static String setStringInBlue(String outputStr){
		outputStr = "<font color=\"blue\">" + outputStr + "</font>";
		return outputStr;
	}
	
	public static String setStringWithRGB(String outputStr, String r, String g, String b){
		outputStr = "<font color=\"rgb("+r+", "+g+", "+b+")\">" + outputStr + "</font>";
		return outputStr;
	}
	
}
