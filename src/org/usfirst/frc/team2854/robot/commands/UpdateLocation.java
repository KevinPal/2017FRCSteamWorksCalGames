package org.usfirst.frc.team2854.robot.commands;

import java.util.spi.LocaleServiceProvider;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.LocationSystem;
import org.usfirst.frc.team2854.robot.util.Matrix;
import org.usfirst.frc.team2854.robot.util.Vector;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateLocation extends Command {

	private LocationSystem locSys;
	
	private long startTime, lastTime;
	
    public UpdateLocation() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getSubSystems().get("Location"));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	locSys = (LocationSystem) Robot.getSubSystems().get("Location");
    	startTime = System.nanoTime();
    	lastTime = startTime;
    }


    protected void execute() {
    	
    	startTime = System.nanoTime();
    	double deltaTime = (startTime - lastTime) / (double)(1E9);
    	
    	locSys.setLocalAcceleration(new Vector(locSys.getForwardAcceleration(), 0, 0));  //No side accel bc gyro acounts for that
    	locSys.setLocalVelocity(locSys.getLocalVelocity().add(locSys.getLocalAcceleration().multiply(deltaTime)));
    	locSys.setGlobalAcceleration(locSys.getLocalAcceleration().muliply(new Matrix().rotationYMatrix(locSys.getGyro().getAccumulatorValue())));
    	locSys.setGlobalVelocity(locSys.getGlobalVelocity().muliply(new Matrix().rotationYMatrix(locSys.getGyro().getAccumulatorValue())));
    	locSys.setPosition(locSys.getPosition().add(locSys.getGlobalVelocity().multiply(deltaTime)));
    	
    	SmartDashboard.putNumber("Gyro" , locSys.getGyro().getAccumulatorValue());
    	SmartDashboard.putString("Local Acceleration", locSys.getLocalAcceleration().toString());
    	SmartDashboard.putString("Local Velocity", locSys.getLocalVelocity().toString());
    	SmartDashboard.putString("Global Acceleration", locSys.getGlobalAcceleration().toString());
    	SmartDashboard.putString("Global Velocity", locSys.getGlobalVelocity().toString());
    	SmartDashboard.putString("Position", locSys.getPosition().toString());
    	
    	lastTime = startTime;
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
