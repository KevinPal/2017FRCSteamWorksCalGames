package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoyStickDrive;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.*;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public CANTalon leftDrive, rightDrive1, rightDrive2;

	public void initDefaultCommand() {
		setDefaultCommand(new JoyStickDrive());
	}

	public DriveTrain() {
		leftDrive = new CANTalon(RobotMap.leftDrive2);
		rightDrive1 = new CANTalon(RobotMap.rightDrive1);
		rightDrive2 = new CANTalon(RobotMap.rightDrive2);
		rightDrive1.setInverted(true);
		rightDrive2.setInverted(true);

	}

	public void drive(double x, double y) {

		leftDrive.set(-x);
		// leftDriveBot.set(x);
		rightDrive1.set(-y);
		// rightDrive2.set(-y);
		// rightDriveBot.set(y);

	}

	public void stop() {
		leftDrive.set(0);
		rightDrive1.set(0);
		// rightDrive2.set(0);
	}

	public void drive() {
		leftDrive.set(-.25);
		rightDrive1.set(-.25);
		// rightDrive2.set(-.25);

	}

}
