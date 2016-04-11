//@@author A0100111R
package com.Main;
/*
 * This Commander interface is created to facilitate that 
 * in the Processor object, the Parser object can pass in 
 * any objects of sub-class of Commander Class, which can 
 * be received by the Processor object without knowing its
 * specific class type. This is an implementation of high-level
 * OOP so as to apply de-coupling and enhance cohesiveness.
 */
public interface Commander {
	String execute();
	String undo();

}
