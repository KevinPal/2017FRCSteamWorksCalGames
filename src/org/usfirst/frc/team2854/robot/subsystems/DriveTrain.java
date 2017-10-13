package org.usfirst.frc.team2854.robot.subsystems;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoyStickDrive;

import com.ctre.CANTalon;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private CANTalon leftDrive1, leftDrive2, rightDrive1, rightDrive2;
	private int leftEncoderOffSet = 0, rightEncoderOffSet = 0;
	
	//private CvSource outputStream = CameraServer.getInstance().putVideo("Console", 640, 480);

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
		leftDrive1.setEncPosition(0);
		rightDrive1.setEncPosition(0);
		resetEncoders();
	}
		
	public void init() {
		leftDrive1.reset();
		rightDrive2.reset();
		leftDrive1.setVoltageRampRate(20);
		leftDrive2.setVoltageRampRate(20);
		rightDrive1.setVoltageRampRate(20);
		rightDrive2.setVoltageRampRate(20);
		resetEncoders();

	}
	
	public void resetEncoders() {
		leftEncoderOffSet = leftDrive1.getEncPosition();
		rightEncoderOffSet = rightDrive2.getEncPosition();
	}

	public void drive(double x, double y) {
//		System.out.println("LT1: " + leftDrive1.getControlMode().toString());
//		System.out.println("LT2: " + leftDrive2.getControlMode().toString());
//		System.out.println("RT1: " + rightDrive1.getControlMode().toString());
//		System.out.println("RT2: " + rightDrive2.getControlMode().toString());

		leftDrive1.set(y );
		leftDrive2.set(y );
		rightDrive1.set(x);
		rightDrive2.set(x);
		
		SmartDashboard.putNumber("Left Drive 1", leftDrive1.get());
		SmartDashboard.putNumber("Left Drive 2", leftDrive2.get());
		SmartDashboard.putNumber("Right Drive 1", rightDrive1.get());
		SmartDashboard.putNumber("Right Drive 2", rightDrive2.get());
		//rightDrive1.setControlMode(TalonControlMode.);
		// rightDriveBot.set(y);
		//Mat output = new Mat(500, 500, CvType.CV_8UC3, new Scalar(0, 255, 0));
	//	Imgproc.putText(output, "Left Encoder: " + getLeftEncoder() + " Right Encoder: " + getRightEncoder(), new Point(0,0), Core.FONT_HERSHEY_PLAIN, 1,
		//		new Scalar(255, 0, 0));
	//	outputStream.putFrame(output);
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
	//14719.911789037797
	public double getLeftEncoder() {
		return leftDrive1.getEncPosition() - leftEncoderOffSet + Math.random();
	}
	public double getRightEncoder() {
		
		
		return rightDrive1.getEncPosition() - rightEncoderOffSet + Math.random();
		                                     
		                                     
	}

}
