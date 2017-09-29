package org.usfirst.frc.team2854.robot.commands.doors;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.BottomDoor;
import org.usfirst.frc.team2854.robot.subsystems.TopDoor;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenTopDoor extends Command {
	
	
	TopDoor door;

    public OpenTopDoor() {
       requires(Robot.getSubSystems().get("BallDoorTop"));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	door = (TopDoor) Robot.getSubSystems().get("BallDoorTop");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	door.open();
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
