package org.usfirst.frc.team2854.robot;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2854.robot.util.Vector;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearVision implements Runnable {

	CvSink cvSink;
	CvSource outputStream;
	Mat source, output, bgImg, bgHist;

	private volatile boolean hasGear;
	private volatile Vector gearPos;

	private final int gearHue = 100;
	private final int ballHue = 70;
	private final int range = 10;

	private boolean isReady = false;
	private UsbCamera cam;

	private boolean shouldRun = true;

	private volatile boolean running = false;
	private boolean finished = false;

	public GearVision(UsbCamera camera) {

		cvSink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo("Gear", 640, 480);
		cam = camera;
		source = new Mat();
		output = new Mat();
		bgImg = new Mat();
		gearPos = new Vector(2, 2);
	}

	public void init() {

		cvSink.grabFrame(bgImg);
		bgImg.convertTo(bgImg, CvType.CV_8U);
		Imgproc.cvtColor(bgImg, bgImg, Imgproc.COLOR_RGB2HSV);
		Imgproc.medianBlur(bgImg, bgImg, 11);
		bgHist = getHistogram(bgImg, true, 300, 300, 180).get(0);
		isReady = true;
		outputStream.putFrame(bgImg);
	}

	@Override
	public void run() {

		System.gc();
		if (!isReady) {
			throw new RuntimeException("BG was not ready, call the init function to take a bg pic");
		}	

		while (!Thread.interrupted()) {
			//System.out.println("In Gear Vision: " + running);
			try {
				SmartDashboard.putBoolean("Is CV Running", running);
			
				if (!running) {
					
					Mat m = new Mat();
					cvSink.grabFrame(m);
					outputStream.putFrame(m);
					m.release();
					continue;
				}
//				if (!shouldRun) {
//					try {
//						Thread.sleep(10);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					shouldRun = true;
//					continue;
//				} else {
//					shouldRun = false;
//				}
				cvSink.grabFrame(source);
				SmartDashboard.putString("Memory", "Free Mem " + Runtime.getRuntime().freeMemory() + " Total Memory: "
						+ Runtime.getRuntime().totalMemory() + " Max Memory: " + Runtime.getRuntime().maxMemory());
				
				source.convertTo(source, CvType.CV_8U);
				Imgproc.cvtColor(source, source, Imgproc.COLOR_RGB2HSV);
				Imgproc.medianBlur(source, source, 11);
				
				Mat gearHist = getHistogram(source, true, 300, 300, 180).get(0);
				
				Mat subtract = new Mat();

				Core.subtract(gearHist, bgHist, subtract);
				List<Double> spikes = findSpikes(subtract);
				subtract.release();
				gearHist.release();

				Mat threshHold = new Mat();
				// System.out.println(spikes);
				for (double spike : spikes) {
					
					Scalar lower = new Scalar(spike - 10, 100, 100);
					Scalar upper = new Scalar(spike + 10, 255, 255);
					
					Core.inRange(source, lower, upper, threshHold);

					ArrayList<MatOfPoint> contour = new ArrayList<MatOfPoint>();
					Mat heirarchy = new Mat();
					Imgproc.findContours(threshHold, contour, heirarchy, Imgproc.RETR_EXTERNAL,
							Imgproc.CHAIN_APPROX_SIMPLE);
					// System.out.println(contour.size());
					ArrayList<Vector> gears = new ArrayList<Vector>();
					hasGear = false;
					for (int i = 0; i < contour.size(); i++) {
						Rect boundingRect = Imgproc.boundingRect(contour.get(i));
						Imgproc.rectangle(source, boundingRect.tl(), boundingRect.br(), new Scalar(255, 0, 0), 1);
						String text;
						if (spike < gearHue + range && spike > gearHue - range) {
							text = "Gear";
							hasGear = true;

							gears.add((new Vector((boundingRect.tl().x + boundingRect.br().x) / 2d,
									(boundingRect.tl().y + boundingRect.br().y) / 2d)
											.multiply(new Vector(1d / source.cols(), 1d / source.rows()))).subtract(.5)
													.multiply(2));

						} else if (spike < ballHue + range && spike > ballHue - range) {
							text = "Ball";
						} else {
							text = "Other";
						}
						Point textLocation = new Point(boundingRect.tl().x,
								(boundingRect.tl().y + boundingRect.br().y) / 2d);
						Imgproc.putText(source, text, textLocation, Core.FONT_HERSHEY_PLAIN, 1 / 2d,
								new Scalar(0, 0, 0));
						
					}
					if (hasGear) {
						Vector min = new Vector(0, 10);
						for (Vector v : gears) {
							if (v.getY() < min.getY()) {
								min = v;
							}
						}
						gearPos = min;
					}
				}

				outputStream.putFrame(source);
				finished = true;
			} catch (Exception e) {
				System.out.println("*********ERROR: ****" + e.getMessage());
			}
		}
	}

	private List<Double> findSpikes(Mat hist) {

		double mean = findAverage(hist);
		double stDev = findStdDev(hist, mean);
		boolean spike = false;
		ArrayList<Double> spikes = new ArrayList<Double>();
		ArrayList<Double> spikeValues = new ArrayList<Double>();
		double value, average;
		for (int i = 0; i < hist.rows(); i++) {
			value = hist.get(i, 0)[0];
			if (value > mean + stDev / 2) {
				spikeValues.add((double) i);
			}
			if (value > mean + 3 * stDev) {

				spike = true;
			}
			if (value < mean + stDev / 2) {
				if (spike) {
					average = 0;
					for (double d : spikeValues) {
						average += d;
					}
					spikes.add(average / spikeValues.size());
					spike = false;
				}
				spikeValues.clear();
			}
		}

		return spikes;
	}

	private double findAverage(Mat hist) {
		double average = 0;
		for (int i = 0; i < hist.rows(); i++) {
			average += hist.get(i, 0)[0];
		}
		return average / hist.rows();
	}

	private double findStdDev(Mat hist, double average) {
		double devations = 0;
		for (int i = 0; i < hist.rows(); i++) {
			devations += Math.pow(hist.get(i, 0)[0] - average, 2);
		}
		return Math.sqrt(devations / (hist.rows() - 1));

	}
	


	private List<Mat> getHistogram(Mat frame, boolean gray, int hist_w, int hist_h, int range) {

		List<Mat> images;
		MatOfInt histSize = new MatOfInt(range);
		MatOfInt channels = new MatOfInt(0);
		MatOfFloat histRange = new MatOfFloat(0, range);
		Mat hist_b;

		Mat hist_g;
		Mat hist_r;
		Mat histImage;
		
		images = new ArrayList<Mat>();
		Core.split(frame, images);



		 hist_b = new Mat();

		 hist_g = new Mat();
		 hist_r = new Mat();

		histImage = new Mat(hist_h, hist_w, CvType.CV_8U, new Scalar(0, 0, 0));

		Imgproc.calcHist(images.subList(0, 1), channels, new Mat(), hist_b, histSize, histRange, false);

		if (!gray) {
			Imgproc.calcHist(images.subList(1, 2), channels, new Mat(), hist_g, histSize, histRange, false);
			Imgproc.calcHist(images.subList(2, 3), channels, new Mat(), hist_r, histSize, histRange, false);
		}
		Core.normalize(hist_b, hist_b, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());

		if (!gray) {
			Core.normalize(hist_g, hist_g, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
			Core.normalize(hist_r, hist_r, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
		}
		List<Mat> hists = new ArrayList<Mat>(3);

		hists.add(hist_b);
		hists.add(hist_g);
		hists.add(hist_r);
		return hists;
	}

	private Mat showHistogram(Mat frame, boolean gray, int hist_w, int hist_h, boolean normalize, int range) {
		// split the frames in multiple images
		List<Mat> images = new ArrayList<Mat>();
		Core.split(frame, images);

		// set the number of bins at 256
		MatOfInt histSize = new MatOfInt(range);
		// only one channel
		MatOfInt channels = new MatOfInt(0);
		// set the ranges
		MatOfFloat histRange = new MatOfFloat(0, range);

		// compute the histograms for the B, G and R components
		Mat hist_b = new Mat();
		Mat hist_g = new Mat();
		Mat hist_r = new Mat();

		// B component or gray image
		Imgproc.calcHist(images.subList(0, 1), channels, new Mat(), hist_b, histSize, histRange, false);

		// G and R components (if the image is not in gray scale)
		if (!gray) {
			Imgproc.calcHist(images.subList(1, 2), channels, new Mat(), hist_g, histSize, histRange, false);
			Imgproc.calcHist(images.subList(2, 3), channels, new Mat(), hist_r, histSize, histRange, false);
		}

		// draw the histogram
		// int hist_w = 150; // width of the histogram image
		// int hist_h = 150; // height of the histogram image
		int bin_w = (int) Math.round(hist_w / histSize.get(0, 0)[0]);
		//
		Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC3, new Scalar(0, 0, 0));
		// normalize the result to [0, histImage.rows()]
		if (normalize) {
			Core.normalize(hist_b, hist_b, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());

			// for G and R components
			if (!gray) {
				Core.normalize(hist_g, hist_g, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
				Core.normalize(hist_r, hist_r, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
			}
		}
		// effectively draw the histogram(s)
		for (int i = 1; i < histSize.get(0, 0)[0]; i++) {
			// B component or gray image
			Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_b.get(i - 1, 0)[0])),
					new Point(bin_w * (i), hist_h - Math.round(hist_b.get(i, 0)[0])), new Scalar(255, 0, 0), 2, 8, 0);
			// G and R components (if the image is not in gray scale)
			if (!gray) {
				Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_g.get(i - 1, 0)[0])),
						new Point(bin_w * (i), hist_h - Math.round(hist_g.get(i, 0)[0])), new Scalar(0, 255, 0), 2, 8,
						0);
				Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_r.get(i - 1, 0)[0])),
						new Point(bin_w * (i), hist_h - Math.round(hist_r.get(i, 0)[0])), new Scalar(0, 0, 255), 2, 8,
						0);
			}
		}

		return histImage;

	}

	public boolean hasGear() {
		return hasGear;
	}

	public Vector getGearPos() {
		return gearPos;
	}

	public boolean isReady() {
		return isReady;
	}

	public boolean isRunning() {
		return running;
	}

	public synchronized void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

}
