package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.IntakeDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

	private CANTalon inTake;
	private boolean isRunning = false;

	public Intake() {
		inTake = new CANTalon(RobotMap.intake);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new IntakeDrive());
    }
    
    public void runIntake(double upValue, double downValue) {
    	inTake.set(upValue - downValue);
    }

	public boolean isRunning() {
		return isRunning;
	}
}

