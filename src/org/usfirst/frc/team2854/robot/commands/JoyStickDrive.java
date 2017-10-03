package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

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

		drive.drive(-OI.joyStick.getRawAxis(5), -OI.joyStick.getRawAxis(1));
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
