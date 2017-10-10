package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.GearVision;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToGear extends Command {

	private GearVision vision;
	private DriveTrain driveTrain;
	private final double testMultiplier = .2;
	private final double lowerGearBound = .55;
	
    public DriveToGear(GearVision vision) {
        requires(Robot.getSubSystems().get("Drive Train"));
        this.vision = vision;
    
     
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	driveTrain = (DriveTrain)Robot.getSubSystems().get("Drive Train");
		vision.setRunning(true);
		vision.setFinished(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		//System.out.println("Is Running? "  + vision.getGearPos());
    		while(!vision.isFinished()) {
    			System.out.println("Waiting for finish");
    		}
    		
    		
    		double leftDrive = 1, rightDrive = 1;
    		double gearPos = vision.getGearPos().getX();
    		if(gearPos > 0) {
    			rightDrive = 1 - gearPos;
    		} else if(gearPos < 0) {
    			leftDrive = 1 + gearPos;
    		}	
    		leftDrive *= testMultiplier;
    		rightDrive *= testMultiplier;
    		driveTrain.drive(-leftDrive, -rightDrive);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return vision.getGearPos().getY() > lowerGearBound;
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveTrain.stop();
    	vision.setRunning(false);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
