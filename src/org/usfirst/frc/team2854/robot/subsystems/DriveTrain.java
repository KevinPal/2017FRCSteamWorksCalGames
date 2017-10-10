package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoyStickDrive;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.*;
import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private CANTalon leftDrive1, leftDrive2, rightDrive1, rightDrive2;

	public void initDefaultCommand() {
		setDefaultCommand(new JoyStickDrive());
	}

	public DriveTrain() {
		leftDrive1 = new CANTalon(RobotMap.leftDrive2);
		leftDrive2 = new CANTalon(RobotMap.extraQuestionMark);
		rightDrive1 = new CANTalon(RobotMap.rightDrive1);
		rightDrive2 = new CANTalon(RobotMap.rightDrive2);
		rightDrive1.setInverted(true);
		rightDrive2.setInverted(true);


	}

	public void drive(double x, double y) {
		System.out.println("LT1: " + leftDrive1.getControlMode().toString());
		System.out.println("LT2: " + leftDrive2.getControlMode().toString());
		System.out.println("RT1: " + rightDrive1.getControlMode().toString());
		System.out.println("RT2: " + rightDrive2.getControlMode().toString());

		leftDrive1.set(y);
		leftDrive2.set(y);
		rightDrive1.set(x);
		rightDrive2.set(x);
		
		SmartDashboard.putNumber("Left Drive 1", leftDrive1.get());
		SmartDashboard.putNumber("Left Drive 2", leftDrive2.get());
		SmartDashboard.putNumber("Right Drive 1", rightDrive1.get());
		SmartDashboard.putNumber("Right Drive 2", rightDrive2.get());
		//rightDrive1.setControlMode(TalonControlMode.);
		// rightDriveBot.set(y);

	}

	public void stop() {
		leftDrive1.set(0);
		leftDrive2.set(0);
		rightDrive1.set(0);
		rightDrive2.set(0);
	}

	public void drive() {
		leftDrive1.set(.25);
		leftDrive2.set(.25);
		rightDrive1.set(.25);
		rightDrive2.set(.25);

	}
	public int getLeftEncoder() {
		return leftDrive2.getEncPosition();
	}
	public int getRightEncoder() {
		return rightDrive2.getEncPosition();
		                                     
		                                     
	}

}
