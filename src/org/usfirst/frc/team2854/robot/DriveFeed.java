package org.usfirst.frc.team2854.robot;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.usfirst.frc.team2854.robot.util.Vector;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

public class DriveFeed implements Runnable {

	private CvSource stream;
	private double[] dataFeed;
	private Mat screenMat;
	private Vector robotPos;

	public DriveFeed() {
		stream = CameraServer.getInstance().putVideo("Drive Map", RobotMap.fieldHeight, RobotMap.fieldWidth);
		screenMat = new Mat(RobotMap.fieldWidth, RobotMap.fieldHeight, CvType.CV_8UC1);
		dataFeed = new double[2];
		for (int x = 0; x < RobotMap.fieldWidth; x++) {
			for (int y = 0; y < RobotMap.fieldHeight; y++) {
				screenMat.put(y, x, new byte[] { (byte) 0xff });
			}
		}
		robotPos = new Vector(0, 0);
	}

	public void updateData(double d1, double d2) {
		dataFeed[0] = d1;
		dataFeed[1] = d2;
	}

	@Override
	public void run() {

		while (!Thread.interrupted()) {
			screenMat.put((int) robotPos.getY(), (int) robotPos.getX(), new byte[] { (byte) 0xff });
			robotPos = new Vector(dataFeed[0], dataFeed[1]);
			screenMat.put((int) robotPos.getY(), (int) robotPos.getX(), new byte[] { (byte) 0x00 });
			stream.putFrame(screenMat);

		}

	}

}
