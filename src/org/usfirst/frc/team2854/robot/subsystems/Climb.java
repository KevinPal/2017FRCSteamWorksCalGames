package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.ClimbDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climb extends Subsystem {

   CANTalon climb1, climb2;

   public Climb() {
	   climb1 = new CANTalon(RobotMap.climb1);
	   climb2 = new CANTalon(RobotMap.climb2);
   }
   
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ClimbDrive());
    }
    
    public void setDrive(double value) {
    	climb1.set(Math.abs(value));
    	climb2.set(-Math.abs(value));
    }
}

