package org.usfirst.frc.team2854.commandGroups;

import org.usfirst.frc.team2854.robot.commands.CloseGear;
import org.usfirst.frc.team2854.robot.commands.Delay;
import org.usfirst.frc.team2854.robot.commands.DriveStraight;
import org.usfirst.frc.team2854.robot.commands.LowerGear;
import org.usfirst.frc.team2854.robot.commands.OpenGear;
import org.usfirst.frc.team2854.robot.commands.RaiseGear;
import org.usfirst.frc.team2854.robot.commands.Turn;
import org.usfirst.frc.team2854.robot.commands.doors.CloseBotDoor;
import org.usfirst.frc.team2854.robot.commands.doors.OpenBotDoor;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallDumpAuto extends CommandGroup {

    public BallDumpAuto() {
    	addSequential(new CloseGear());
    	addSequential(new RaiseGear());
    	addSequential(new DriveStraight(93.25, true, .6));
    	addSequential(new Delay(1));
        addSequential(new Turn(-45));
        addSequential(new DriveStraight(3 * 12, true, .6));
        addSequential(new Delay(1));
        addSequential(new OpenGear());
        addSequential(new LowerGear());
        addSequential(new DriveStraight(1 * 12, false, .6));
        addSequential(new Turn(180));
        addSequential(new DriveStraight(1 * 12, true, .6));
        addSequential(new OpenBotDoor());
        addSequential(new Delay(3));
        addSequential(new CloseBotDoor());
        
    }
}
