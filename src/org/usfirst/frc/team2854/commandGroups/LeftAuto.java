package org.usfirst.frc.team2854.commandGroups;

import org.usfirst.frc.team2854.robot.commands.Delay;
import org.usfirst.frc.team2854.robot.commands.DriveStraight;
import org.usfirst.frc.team2854.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftAuto extends CommandGroup {

    public LeftAuto() {
    	addSequential(new DriveStraight(6 * 12, true));
    	addSequential(new Delay(1));
        addSequential(new Turn(-45));
        addSequential(new DriveStraight(6 * 12, true));
        
    }
}
