
package org.usfirst.frc.team2854.robot;

import java.util.HashMap;

import org.usfirst.frc.team2854.commandGroups.CenterAuto;
import org.usfirst.frc.team2854.commandGroups.LeftAuto;
import org.usfirst.frc.team2854.commandGroups.PickUpGear;
import org.usfirst.frc.team2854.robot.commands.CloseGear;
import org.usfirst.frc.team2854.robot.commands.DriveStraight;
import org.usfirst.frc.team2854.robot.commands.JoyStickDrive;
import org.usfirst.frc.team2854.robot.commands.LowerGear;
import org.usfirst.frc.team2854.robot.commands.OpenGear;
import org.usfirst.frc.team2854.robot.commands.RaiseGear;
import org.usfirst.frc.team2854.robot.commands.doors.CloseBotDoor;
import org.usfirst.frc.team2854.robot.commands.doors.OpenBotDoor;
import org.usfirst.frc.team2854.robot.subsystems.BottomDoor;
import org.usfirst.frc.team2854.robot.subsystems.Climb;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.Gear;
import org.usfirst.frc.team2854.robot.subsystems.Intake;
import org.usfirst.frc.team2854.robot.subsystems.TopDoor;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
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
	GearVision gearVision;
	Thread visionThread;
	public static BuiltInAccelerometer acc = new BuiltInAccelerometer();
	public static MyGyro gyro;

	private static HashMap<String, Subsystem> subSystems = new HashMap<String, Subsystem>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {

		subSystems.put("Drive Train", new DriveTrain());
		subSystems.put("BallDoorTop", new TopDoor());
		subSystems.put("BallDoorBot", new BottomDoor());
		subSystems.put("Gear", new Gear());
		subSystems.put("Intake", new Intake());
		subSystems.put("Climb", new Climb());
		// subSystems.put("Location", new LocationSystem());

		// Thread driveStreamThread = (new Thread(driveFeed = new DriveFeed()));
		// driveStreamThread.start();

		oi = new OI();
		chooser.addDefault("Default Auto", new CenterAuto());
		//chooser.addObject("Left Auto", new LeftAuto());
		
		SmartDashboard.putData(Scheduler.getInstance());
//		for (Subsystem s : subSystems.values()) {
//			SmartDashboard.putData(s);
//		}
		SmartDashboard.putData("Drive Straight", new DriveStraight(11 * 12, true));
		UsbCamera cam1 = CameraServer.getInstance().startAutomaticCapture(0);
		UsbCamera cam2 = CameraServer.getInstance().startAutomaticCapture(1);
		gearVision = new GearVision(cam1);
		// cam1.setExposureHoldCurrent();
		// cam1.setWhiteBalanceHoldCurrent();
		SmartDashboard.putData("Auto Mode", chooser);
		gearVision.init();
		visionThread = new Thread(gearVision);
		visionThread.start();
		gyro = new MyGyro();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
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
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		gyro.init();
		autonomousCommand = chooser.getSelected();
		((DriveTrain) subSystems.get("Drive Train")).init();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
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

		// driveFeed.updateData(((LocationSystem)
		// subSystems.get("Location")).getPosition().getX(), ((LocationSystem)
		// subSystems.get("Location")).getPosition().getY());

		Scheduler.getInstance().run();

	}

	@Override
	public void teleopInit() {
		gyro.init();
		((DriveTrain) subSystems.get("Drive Train")).init();
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.

		// UsbCamera cam0 = CameraServer.getInstance().startAutomaticCapture();
		// SmartDashboard.putString("Camera Value", "[" + cam0.getPath() + "]");

		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		OI.openBallDoor.whenPressed(new OpenBotDoor());
		OI.openBallDoor.whenReleased(new CloseBotDoor());
		OI.pickUpGear.whenPressed(new PickUpGear(gearVision));
		OI.raiseGear.whenPressed(new LeftAuto());
		OI.lowerGear.whenPressed(new LowerGear());
		OI.openGear.whenPressed(new OpenGear());
		OI.closeGear.whenPressed(new CloseGear());
		// OI.toggleIntakeButton.whenPressed(new ToggleIntake());
		// if (OI.joyStick.getMagnitude() > .4) {
		// // Scheduler.getInstance().add(new JoyStickDrive());
		// }
		// System.gc();
		gyro.run();
		SmartDashboard.putNumber("Acc X", acc.getX());
		SmartDashboard.putNumber("Acc Y", acc.getY());
		SmartDashboard.putNumber("Acc Z", acc.getZ());

		SmartDashboard.putNumber("Left Encoder",
				((DriveTrain) (Robot.getSubSystems().get("Drive Train"))).getLeftEncoder());
		SmartDashboard.putNumber("Right Encoder",
				-((DriveTrain) (Robot.getSubSystems().get("Drive Train"))).getRightEncoder());
		SmartDashboard.putString("Gear Pos", gearVision.getGearPos().toString());
		SmartDashboard.putBoolean("Has Gear", gearVision.hasGear());
		SmartDashboard.putNumber("Rand", Math.random());
		SmartDashboard.putNumber("Pressure",
				((BottomDoor) subSystems.get("BallDoorBot")).getCompress().getCompressorCurrent());
		// driveFeed.updateData(((LocationSystem)
		// subSystems.get("Location")).getPosition().getX(), ((LocationSystem)
		// subSystems.get("Location")).getPosition().getY());
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
