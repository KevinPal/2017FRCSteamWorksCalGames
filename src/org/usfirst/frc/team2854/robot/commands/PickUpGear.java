package org.usfirst.frc.team2854.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickUpGear extends CommandGroup {

	final double time = .5;
	
    public PickUpGear() {
        addParallel(new DriveForwardTimed(time));
        addSequential(new Delay(time/2f));
        addSequential(new CloseGear());
        addSequential(new RaiseGear());
    }
}
