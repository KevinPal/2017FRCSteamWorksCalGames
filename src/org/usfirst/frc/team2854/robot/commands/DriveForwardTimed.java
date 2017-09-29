package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveForwardTimed extends TimedCommand {

	private DriveTrain drive;
	
    public DriveForwardTimed(double timeout) {
        super(timeout);
        requires(Robot.getSubSystems().get("DriveTrain"));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive = (DriveTrain) Robot.getSubSystems().get("DriveTrain");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.drive();
    }

    // Called once after timeout
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
