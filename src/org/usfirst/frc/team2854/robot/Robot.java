
package org.usfirst.frc.team2854.robot;

import java.util.HashMap;

import org.usfirst.frc.team2854.robot.commands.CloseGear;
import org.usfirst.frc.team2854.robot.commands.JoyStickDrive;
import org.usfirst.frc.team2854.robot.commands.LowerGear;
import org.usfirst.frc.team2854.robot.commands.OpenGear;
import org.usfirst.frc.team2854.robot.commands.PickUpGear;
import org.usfirst.frc.team2854.robot.commands.RaiseGear;
import org.usfirst.frc.team2854.robot.commands.doors.CloseDoors;
import org.usfirst.frc.team2854.robot.commands.doors.OpenDoors;
import org.usfirst.frc.team2854.robot.subsystems.BottomDoor;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.Gear;
import org.usfirst.frc.team2854.robot.subsystems.TopDoor;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {


	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	DriveFeed driveFeed;

	private static HashMap<String, Subsystem> subSystems = new HashMap<String, Subsystem>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		subSystems.put("Drive Train", new DriveTrain());
		subSystems.put("BallDoorTop", new TopDoor());
		subSystems.put("BallDoorBot", new BottomDoor());
		subSystems.put("Gear", new Gear());
		//subSystems.put("Intake", new Intake());
		//subSystems.put("Location", new LocationSystem());
		
		//Thread driveStreamThread = (new Thread(driveFeed = new DriveFeed()));
		//driveStreamThread.start();
		
		oi = new OI();
		chooser.addDefault("Default Auto", new JoyStickDrive());
		SmartDashboard.putData("Auto mode", chooser);
		
		
	
	}	

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		//driveFeed.updateData(((LocationSystem) subSystems.get("Location")).getPosition().getX(), ((LocationSystem) subSystems.get("Location")).getPosition().getY());
		
		Scheduler.getInstance().run();
		
	
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		
			OI.openDoorButton.whenPressed(new OpenDoors());
			OI.openDoorButton.whenReleased(new CloseDoors());
			OI.pickUpGearButton.whenPressed(new PickUpGear());
			OI.upGearButton.whenPressed(new RaiseGear());
			OI.downGearButton.whenPressed(new LowerGear());
			OI.openGearButton.whenPressed(new OpenGear());
			OI.closeGearButton.whenPressed(new CloseGear());

		
		SmartDashboard.putNumber("Pressure", ((BottomDoor) subSystems.get("Ball Door")).getCompress().getCompressorCurrent());
		//driveFeed.updateData(((LocationSystem) subSystems.get("Location")).getPosition().getX(), ((LocationSystem) subSystems.get("Location")).getPosition().getY());
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	public static HashMap<String, Subsystem> getSubSystems() {
		return subSystems;
	}
}
