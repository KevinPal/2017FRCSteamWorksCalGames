package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BottomDoor extends Subsystem {

    private Compressor compress;
    private DoubleSolenoid door;
    
    public BottomDoor() {
    	compress = new Compressor();
    	compress.setClosedLoopControl(true);
    	//door1 = new DoubleSolenoid(RobotMap.doorPiston1Open, RobotMap.doorPiston1Close);
    	door = new DoubleSolenoid(RobotMap.doorPiston1Open, RobotMap.doorPiston1Close);
    
    }

    public void open() {
    	door.set(Value.kForward);
    }

    public void close() {
    	door.set(Value.kReverse);
    }

	public Compressor getCompress() {
		return compress;
	}



	@Override
	protected void initDefaultCommand() {

	}





}

