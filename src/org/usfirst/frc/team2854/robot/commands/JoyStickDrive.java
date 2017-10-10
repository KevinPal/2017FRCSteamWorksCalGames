package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class JoyStickDrive extends Command {

	DriveTrain drive;
	
	public JoyStickDrive() {
		requires((DriveTrain)Robot.getSubSystems().get("Drive Train"));
	}

	protected void initialize() {
		drive = (DriveTrain)Robot.getSubSystems().get("Drive Train");
	}

	protected void execute() {
//		drive.drive(OI.joyStick.getRawAxis(1), OI.joyStick.getRawAxis(5));
		double total = sigmoid(OI.joyStick.getRawAxis(2) - OI.joyStick.getRawAxis(3));
		double left = total, right = total;
		double turn = sigmoid(OI.joyStick.getRawAxis(0)/1.5f);
		left -= turn;
		right += turn;
		drive.drive(left, right);
//		SmartDashboard.putNumber("Left stick", OI.joyStick.getRawAxis(1));
//		SmartDashboard.putNumber("Right stick", OI.joyStick.getRawAxis(5));
	}
	
	private double sigmoid(double value) {
		return 2/(1+Math.pow(Math.E, -7 * Math.pow(value, 3)))-1;
	}
	
	private double clamp(double value) {
		return (value < -1 ? -1 : value > 1 ? 1 : value);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		drive.stop();
	}
}
