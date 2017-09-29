package org.usfirst.frc.team2854.robot.commands.doors;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CloseDoors extends CommandGroup {

    public CloseDoors() {
    	addParallel(new OpenTopDoor());
    	addParallel(new OpenBotDoor());
    }
}
