package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gear extends Subsystem {

    private DoubleSolenoid gearLift, gearHold;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Gear() {
    	gearLift = new DoubleSolenoid(RobotMap.gearUp, RobotMap.gearDown);
    	gearHold = new DoubleSolenoid(RobotMap.gearOpen, RobotMap.gearClose);
    }

	public DoubleSolenoid getGearLift() {
		return gearLift;
	}

	public DoubleSolenoid getGearHold() {
		return gearHold;
	}
    
    
}

