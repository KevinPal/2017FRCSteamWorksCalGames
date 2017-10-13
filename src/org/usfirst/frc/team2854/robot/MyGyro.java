package org.usfirst.frc.team2854.robot;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MyGyro {
	private I2C i2cBus;
	private SPI spi;
	short cX = 0, cY = 0, cZ = 0;
	private ADXRS450_Gyro gyro;

	byte[] dataBuffer = new byte[6];

	boolean useSpi = true;
	
	private long startTime, lastTime, deltaTime;

	public MyGyro() {
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();
		startTime = System.nanoTime();
		gyro.reset();
		
	}
	
	public void init() {
		gyro.reset();
	}

	public void run() {
		SmartDashboard.putNumber("Gyro plz?", gyro.getAngle());
	}
	
	public double getAngle() {
		return gyro.getAngle();
	}

}
