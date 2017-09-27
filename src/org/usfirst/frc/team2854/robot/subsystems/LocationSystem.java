package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.UpdateLocation;
import org.usfirst.frc.team2854.robot.util.Vector;


import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LocationSystem extends Subsystem {

	private BuiltInAccelerometer accel;
	private SPI gyro;
	
	private Vector globalAcceleration, globalVelocity, position, localAcceleration, localVelocity;
	
	

    public void initDefaultCommand() {
        setDefaultCommand(new UpdateLocation());
    }
    
    public LocationSystem() {
		
		accel = new BuiltInAccelerometer();
		gyro = new SPI(OI.gyroPort);
		gyro.setClockRate(4000000);
		gyro.setClockActiveHigh();
		gyro.setChipSelectActiveLow();
		gyro.setMSBFirst();
		gyro.resetAccumulator();
		globalAcceleration = new Vector(0, 0);
		globalVelocity = new Vector(0, 0);
		localAcceleration = new Vector(0, 0);
		localVelocity = new Vector(0, 0);
		position = new Vector((DriverStation.getInstance().getAlliance()==DriverStation.Alliance.Blue ? 0 : RobotMap.fieldWidth-1), RobotMap.fieldHeight/5d * DriverStation.getInstance().getLocation());
		//TODO make the starting positions actual starting positions
    }
    
    public double getForwardAcceleration() {
    	return accel.getZ() * 9.81 * 100; //g's to cm/s^2
    }
    


	public Vector getGlobalAcceleration() {
		return globalAcceleration;
	}

	public void setGlobalAcceleration(Vector globalAcceleration) {
		this.globalAcceleration = globalAcceleration;
	}

	public Vector getGlobalVelocity() {
		return globalVelocity;
	}

	public void setGlobalVelocity(Vector globalVelocity) {
		this.globalVelocity = globalVelocity;
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public Vector getLocalAcceleration() {
		return localAcceleration;
	}

	public void setLocalAcceleration(Vector localAcceleration) {
		this.localAcceleration = localAcceleration;
	}

	public Vector getLocalVelocity() {
		return localVelocity;
	}

	public void setLocalVelocity(Vector localVelocity) {
		this.localVelocity = localVelocity;
	}

	public BuiltInAccelerometer getAccel() {
		return accel;
	}

	public SPI getGyro() {
		return gyro;
	}
}

