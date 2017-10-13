package org.usfirst.frc.team2854.commandGroups;

import org.usfirst.frc.team2854.robot.commands.CloseGear;
import org.usfirst.frc.team2854.robot.commands.Delay;
import org.usfirst.frc.team2854.robot.commands.DriveStraight;
import org.usfirst.frc.team2854.robot.commands.LowerGear;
import org.usfirst.frc.team2854.robot.commands.OpenGear;
import org.usfirst.frc.team2854.robot.commands.RaiseGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterAuto extends CommandGroup {

    public CenterAuto() {
    	addSequential(new CloseGear());
        addSequential(new RaiseGear());
        addSequential(new DriveStraight(93.25, true, .4));
        addSequential(new Delay(2d));
        addSequential(new OpenGear());
        addSequential(new LowerGear());
        addSequential(new DriveStraight(10d, false, .5));
    }
}
