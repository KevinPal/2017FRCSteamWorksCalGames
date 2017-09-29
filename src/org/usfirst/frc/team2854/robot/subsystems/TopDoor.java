package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TopDoor extends Subsystem {

    private DoubleSolenoid door;
    
    public TopDoor() {
    	door = new DoubleSolenoid(RobotMap.doorPiston1Open, RobotMap.doorPiston1Close);
    
    }

    public void open() {
    	door.set(Value.kForward);
    }

    public void close() {
    	door.set(Value.kReverse);
    }



	@Override
	protected void initDefaultCommand() {

	}





}

