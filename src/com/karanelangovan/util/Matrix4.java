package com.karanelangovan.util;

public class Matrix4 {
	private double[][] entries;
	
	public static final Matrix4 IDENTITY = new Matrix4(new double[][] {
		{1, 0, 0, 0},
		{0, 1, 0, 0},
		{0, 0, 1, 0},
		{0, 0, 0, 1},
	});

	//must be 4x4
	//4th row must be (0, 0, 0, 1)
	private Matrix4(double[][] entries) {
		this.entries = entries;
	}
	
	public String toString() {
		String[] rows = new String[4];
		
		for(int i = 0; i < 4; i++) {
			double[] erow = entries[i];
			String row = "[" + erow[0] + ", " + erow[1] + ", " + erow[2] + ", " + erow[3] + "],\n";
			rows[i] = row;
		}
		
		return rows[0] + rows[1] + rows[2] + rows[3];
	}
	
	public static Matrix4 getRotationMatrix(double theta, char axis) {
		
		switch(axis) {
		case 'x':
			return getXRotationMatrix(theta);
		case 'y':
			return getXRotationMatrix(theta);
		case 'z':
			return getXRotationMatrix(theta);
		default:
			throw new IllegalArgumentException("axis of rotation must be x, y or z!");
		}
	}
	
	public static Matrix4 getXRotationMatrix(double theta) {
		double st = Math.sin(theta);
		double ct = Math.cos(theta);

		return new Matrix4(new double[][]{
			{1, 0,   0,  0},
			{0, ct,  st, 0},
			{0, -st, ct, 0},
			{0, 0,   0,  1},
		});
	}
	
	public static Matrix4 getYRotationMatrix(double theta) {
		double st = Math.sin(theta);
		double ct = Math.cos(theta);

		return new Matrix4(new double[][]{
			{ct,  0,   -st,  0},
			{0,   1,   0,    0},
			{st,  0,   ct,   0},
			{0,   0,   0,    1},
		});
	}
	
	public static Matrix4 getZRotationMatrix(double theta) {
		double st = Math.sin(theta);
		double ct = Math.cos(theta);

		return new Matrix4(new double[][]{
			{ct,  st,  0,  0},
			{-st, ct,  0,  0},
			{0,   0,   1,  0},
			{0,   0,   0,  1},
		});
	}
	
	public static Matrix4 getScalingMatrix(double x, double y, double z) {
		return new Matrix4(new double[][]{
			{x,  0,  0,  0},
			{0,  y,  0,  0},
			{0,  0,  z,  0},
			{0,  0,  0,  1},
		});
	}
	
	public static Matrix4 getScalingMatrix(double s) {
		return getScalingMatrix(s, s, s);
	}
	
	public static Matrix4 getTranslationMatrix(double x, double y, double z) {
		return new Matrix4(new double[][]{
			{1,  0,  0,  x},
			{0,  1,  0,  y},
			{0,  0,  1,  z},
			{0,  0,  0,  1},
		});
	}
	
	public Vector3 transform(Vector3 input) {
		double x = input.getX();
		double y = input.getY();
		double z = input.getZ();
		
		double tx = x * entries[0][0] + y * entries[0][1] + z * entries[0][2] + entries[0][3];
		double ty = x * entries[1][0] + y * entries[1][1] + z * entries[1][2] + entries[1][3];
		double tz = x * entries[2][0] + y * entries[2][1] + z * entries[2][2] + entries[2][3];
		
		return new Vector3(tx, ty, tz);
	}
	
	public static Matrix4 multiply(Matrix4 m1, Matrix4 m2) {
		double[][] entries = new double[4][4];
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				double entry = 0D;
				for(int k = 0; k < 4; k++) {
					entry += m1.entries[i][k] * m2.entries[k][j];
				}
				
				entries[i][j] = entry;
			}
		}
		
		return new Matrix4(entries);
	}
	
	public Matrix4 compose(Matrix4 other) {
		return multiply(other, this);
	}
}
