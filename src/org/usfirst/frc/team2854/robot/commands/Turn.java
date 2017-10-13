package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.MyGyro;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Turn extends Command {

	private double angle;
	private DriveTrain drive;
	private double turnSpeed = .3;
	private MyGyro gyro;
	
	
    public Turn(double angle) {
        requires(Robot.getSubSystems().get("Drive Train"));
        this.angle = angle * 2/3f;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive = (DriveTrain)Robot.getSubSystems().get("Drive Train");
    	gyro = Robot.gyro;
    	gyro.init();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(angle < 0) {
    		drive.drive(turnSpeed, -turnSpeed);
    	} else {
    		drive.drive(-turnSpeed, turnSpeed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(angle < 0) {
        	return gyro.getAngle() < angle;
        } else {
        	return gyro.getAngle() > angle;
        }
    	
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Done");
    	drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
