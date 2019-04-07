package com.karanelangovan.objects;

import java.awt.Color;

import com.karanelangovan.util.Vector3;

public class Sphere {
	private Vector3 position;
	private double radius;

	private Color color;

	public Sphere(Vector3 position, double radius, Color color) {
		this.position = position;
		this.radius = radius;
		this.color = color;
	}

	public Sphere(Vector3 position, double radius) {
		this(position, radius, Color.RED);
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
