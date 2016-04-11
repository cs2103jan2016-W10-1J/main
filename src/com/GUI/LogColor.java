//@@author A0149484R
package com.GUI;

public class LogColor {


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
	
	public static String setStringWithRGB(String outputStr, int r, int g, int b){
		outputStr = "<font color=\"rgb("+String.valueOf(r)+", "+String.valueOf(g)+", "+String.valueOf(b)+")\">" + outputStr + "</font>";
		return outputStr;
	}
	
}
