package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.MyGyro;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraight extends Command {

	DriveTrain drive;
	private double speed = .6;
	private double distance;
	private MyGyro gyro;
	private BuiltInAccelerometer acc;
	private boolean forward;

	private long startTime, lastTime;
	double a = 0, v = 0, p = 0;
	
	public DriveStraight(double distance, boolean forward, double speed) {
		requires(Robot.getSubSystems().get("Drive Train"));
		this.distance = distance;
		this.forward = forward;
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		drive = (DriveTrain) (Robot.getSubSystems().get("Drive Train"));
		gyro = Robot.gyro;
		acc = Robot.acc;
		drive.resetEncoders();
		startTime = System.nanoTime();
		lastTime = System.nanoTime();
		gyro.init();
		a = 0;
		v = 0;
		p = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		startTime = System.nanoTime();
		long deltaTime = (startTime - lastTime);
		a = acc.getY();
		
		v += a * deltaTime;
		p += v * deltaTime;
		SmartDashboard.putNumber("Position Ofset", p);
		double angle = gyro.getAngle();
		
		drive.drive(-(speed * (forward ? 1 : -1) - angle/45f), -(speed * (forward ? 1 : -1) + angle/45f));
		lastTime = startTime;
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(drive.getLeftEncoder()) > 14000/187d*distance;
	}
	//187 in
	// Called once after isFinished returns true
	protected void end() {
		drive.stop();
		drive.resetEncoders();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
