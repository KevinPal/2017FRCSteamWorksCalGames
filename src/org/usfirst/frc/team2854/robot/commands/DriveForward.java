package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {

	private double time;
	private long startTime, lastTime;
	private double totalTime;
	private DriveTrain drive;
	
    public DriveForward(double time) {
        requires(Robot.getSubSystems().get("Drive Train"));
        this.time = time;

        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        startTime = System.nanoTime();
        lastTime = System.nanoTime();
        drive = (DriveTrain) (Robot.getSubSystems().get("Drive Train"));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	startTime = System.nanoTime();
    	totalTime += (startTime - lastTime)/(1E9);
    	
    	drive.drive();
    	
    	
    	lastTime = startTime;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return totalTime > time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drive.stop();
    }
    
}
