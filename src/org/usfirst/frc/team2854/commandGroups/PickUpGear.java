package org.usfirst.frc.team2854.commandGroups;

import org.usfirst.frc.team2854.robot.GearVision;
import org.usfirst.frc.team2854.robot.commands.CloseGear;
import org.usfirst.frc.team2854.robot.commands.DriveForward;
import org.usfirst.frc.team2854.robot.commands.DriveToGear;
import org.usfirst.frc.team2854.robot.commands.LowerGear;
import org.usfirst.frc.team2854.robot.commands.OpenGear;
import org.usfirst.frc.team2854.robot.commands.RaiseGear;

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
