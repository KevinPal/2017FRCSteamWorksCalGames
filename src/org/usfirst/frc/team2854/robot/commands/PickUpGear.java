package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.GearVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickUpGear extends CommandGroup {


	
    public PickUpGear(GearVision vision) {
//        addParallel(new DriveForward(time));
//        addSequential(new Delay(time/2f));
//        addSequential(new CloseGear());
//        addSequential(new RaiseGear());
    	
    	addParallel(new OpenGear());
    	addParallel(new LowerGear());
    	addSequential(new DriveToGear(vision));
    	addSequential(new DriveForward(.2));
    	addSequential(new DriveForward(.1));
    	addParallel(new CloseGear());
    	addSequential(new RaiseGear());
    	
    }
}
