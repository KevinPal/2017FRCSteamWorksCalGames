package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Climb;
import org.usfirst.frc.team2854.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbDrive extends Command {

	
	private Climb climb;
	
    public ClimbDrive() {
        requires(Robot.getSubSystems().get("Climb"));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	climb = (Climb) Robot.getSubSystems().get("Climb");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	climb.setDrive(OI.joyStick1.getRawAxis(1));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
