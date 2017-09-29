package org.usfirst.frc.team2854.robot.commands.doors;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class OpenDoors extends CommandGroup {

    public OpenDoors() {
    	addParallel(new OpenTopDoor());
    	addParallel(new OpenBotDoor());
    }
}
