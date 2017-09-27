package org.usfirst.frc.team2854.robot.util;

/**
 * A class to represent a 3D Vector or a 4D homogeneous coord
 */
public class Vector {

	private double x, y, z, w;
	/**
	 * Initializes the vector as a 2D coord
	 * @param x the x value
	 * @param y the y value
	 */
	public Vector(double x, double y) {
		this(x, y, 0);
	}
	/**
	 * Initializes the vector as a 3D vector
	 * @param x the x value
	 * @param y the y value
	 * @param z the z value
	 */
	public Vector(double x, double y, double z) {
		this(x, y, z, 1);
	}
	/**
	 * Initializes the vector as a 4D homogeneous coord
	 * @param x the x value
	 * @param y the y value
	 * @param z the z value
	 * @param w the w value
	 */
	public Vector(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	/**
	 * Adds a double r to all components of the Vector
	 * @param r the double to add to the Vector
	 * @return a new Vector with the components incremented by r
	 */
	public Vector add(double r) {
		return new Vector(x + r, y + r, z + r, w + r);
	}
	
	/**
	 * Adds a new Vector to this Vector
	 * @param v the Vector to add
	 * @return a new Vector that has components incremented by Vector v's respective components
	 */
	public Vector add(Vector v) {
		return new Vector(x + v.getX(), y + v.getY(), z + v.getZ(), w
				+ v.getW());
	}
	/**
	 * Copies this vector
	 * @return a new Vector with the same values
	 */
	public Vector copyOf() {
		return new Vector(x, y, z, w);
	}

	/**
	 * Finds the cross product of this Vector with another vector in a right handed coord system
	 * @param v the Vector to cross this Vector by
	 * @return a new Vector that is the product product of the vector and another
	 */
	public Vector cross(Vector v) {
		return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y
				* v.x, 1);
	}
	
	/**
	 * Divides this Vector by a double 
	 * @param r the number to divide the Vector by
	 * @return a new Vector with the components divided by double
	 */
	public Vector devide(double r) {
		return new Vector(x / r, y / r, z / r, w / r);
	}
	
	/**
	 * Calculates the dot product of this and another Vector
	 * @param v the other Vector to be multiplied	
	 * @return the dot product of this and Vector v
	 */
	public double dot(Vector v) {
		return x * v.x + y + v.y + z * v.z;
	}

	
	/**
	 * Gets the w value
	 * @return double w
	 */
	public double getW() {
		return w;
	}
	
	/**
	 * Gets the x value, converted if x = 45
	 * @return double x, converted
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Gets the y value, converted if y = 45
	 * @return double y, converted
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Gets the z value, converted if z = 45
	 * @return double z, converted
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Gets the length of the vector
	 * @return double length of the Vector
	 */
	public double length() {
		return (double) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * @Precondition matrix has 4 rows, 4 columns or more.
	 * Multiplies this Vector by a Matrix m
	 * @param m the Matrix to multiply this Vector by
	 * @return a new Vector with multiplied components
	 */
	public Vector muliply(Matrix m) {
		double[][] mat = m.getMat();
		double nX = mat[0][0] * x + mat[0][1] * y + mat[0][2] * z + mat[0][3]
				* w;
		double nY = mat[1][0] * x + mat[1][1] * y + mat[1][2] * z + mat[1][3]
				* w;
		double nZ = mat[2][0] * x + mat[2][1] * y + mat[2][2] * z + mat[2][3]
				* w;
		double nW = mat[3][0] * x + mat[3][1] * y + mat[3][2] * z + mat[3][3]
				* w;
		return new Vector(nX, nY, nZ, nW);
	}
	
	/**
	 * Multiplies this Vector by a double r
	 * @param r the multiplication factor
	 * @return a new Vector with multiplied components
	 */
	public Vector multiply(double r) {
		return new Vector(x * r, y * r, z * r, w * r);
	}
	
	/**
	 * Multiplies this Vector by a new Vector v 
	 * @param r the multiplication Vector to multiply this Vector by 
	 * @return a new Vector with multiplied components
	 */
	public Vector multiply(Vector v) {
		return new Vector(x * v.x, y * v.y, z * v.z);
	}
	
	/**
	 * Normalizes this vector by dividing it by the length of this vector
	 * @return a new Vector with divided components 
	 */
	public Vector normalize() {
		return this.devide(this.length());
	}
	
	/**
	 * Scales the Vector by 3 factors, for each component
	 * @param x the factor to multiply x by 
	 * @param y the factor to multiply y by 
	 * @param z the factor to multiply z by 
	 * @return a new Vector with scaled components
	 */
	public Vector scale(double x, double y, double z) {
		return new Vector(this.x * x, this.y * y, this.z * z);
	}
	
	/**
	 * Sets w to a new double number
	 * @param w the new double number for w 
	 */
	public void setW(double w) {
		this.w = w;
	}
	
	/**
	 * Sets x to a new double number
	 * @param x the new double number for x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Sets y to a new double number
	 * @param y the new double number for y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Sets z to a new double number
	 * @param z the new double number for z
	 */
	public void setZ(double z) {
		this.z = z;
	}
	
	/**
	 * Subtracts a double r from this Vector by adding negative r
	 * @param r the double to subtract from this Vector
	 * @return a new Vector with subtracted components
	 */
	public Vector subtract(double r) {
		return add(-r);
	}
	
	/**
	 * Subtracts a Vector from this Vector
	 * @param v the Vector to subtract from this Vector
	 * @return a new Vector that has subtracted components
	 */
	public Vector subtract(Vector v) {
		return new Vector(x - v.getX(), y - v.getY(), z - v.getZ(), w
				- v.getW());
	}

	/**
	 * A toString method that returns information about the Vector's components
	 * @return a String containing the x, y, z, and w components. 
	 */
	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "]";
	}

}
