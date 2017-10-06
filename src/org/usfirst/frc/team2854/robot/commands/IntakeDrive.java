package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeDrive extends Command {

	
	private Intake intake;
	
    public IntakeDrive() {
        requires(Robot.getSubSystems().get("Intake"));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake = (Intake) Robot.getSubSystems().get("Intake");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.runIntake(OI.joyStick1.getRawAxis(3), OI.joyStick1.getRawAxis(2));
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
