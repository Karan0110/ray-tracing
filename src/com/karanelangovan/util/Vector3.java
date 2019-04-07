package com.karanelangovan.util;

public class Vector3 {
	private double x, y, z;

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3() {
		this(0, 0, 0);
	}
	
	public String toString() {
		return "Vector3(" + x + ", " + y + ", " + z + ")";
	}
	
	public static double dotProduct(Vector3 v1, Vector3 v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}
	
	public static Vector3 add(Vector3 v1, Vector3 v2) {
		return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}
	
	public static Vector3 subtract(Vector3 v1, Vector3 v2) {
		return new Vector3(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
	}
	
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}
	
	public Vector3 normalize() {
		double length = getLength();
		return scale(1 / length);
	}
	
	public double getLength() {
		return Math.sqrt(getLength2());
	}
	
	public double getLength2() {
		return x * x + y * y + z * z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
}
