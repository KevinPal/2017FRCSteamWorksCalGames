package org.usfirst.frc.team2854.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	/*
	 * 2 -> left drive 2
	 * 4 -> climb 2
	 * 7 -> right drive 1
	 * 5 -> intake
	 * 3 -> climb 1
	 * 8 -> right drive 2
	*/
	
	

	public static int climb1 = 3;
	public static int climb2 = 4;
	public static int rightDrive1 = 7;
	public static int rightDrive2 = 8;
	public static int leftDrive2 = 2;
	public static int intake = 5;
	public static int extraQuestionMark = 6;
	
	public static double inTakePower = 1;

	public static final int fieldHeight = 823, fieldWidth = 1656; //field size in cm
	
	
}
