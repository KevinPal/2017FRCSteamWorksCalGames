package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Gear;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CloseGear extends Command {

	Gear gear;
	private boolean raise;
	
    public CloseGear() {
        requires(Robot.getSubSystems().get("Gear"));

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gear = (Gear) Robot.getSubSystems().get("Gear");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        	gear.getGearHold().set(Value.kForward);
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
