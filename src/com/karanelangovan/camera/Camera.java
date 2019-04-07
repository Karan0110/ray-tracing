package com.karanelangovan.camera;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import com.karanelangovan.objects.Light;
import com.karanelangovan.objects.Sphere;
import com.karanelangovan.util.Ray;
import com.karanelangovan.util.Vector3;

//Always facing in the positive z direction
public class Camera {
	public static final Color BACKGROUND = new Color(135, 206, 235);
	public static final double AMBIENT_COEFFICIENT = 0.3;
	public static final double DIFFUSE_COEFFICIENT = 1 - AMBIENT_COEFFICIENT;

	private Vector3 position;
	private double distanceToWindow;
	private int width, height;

	public Camera(Vector3 position, double distanceToWindow, int width, int height) {
		this.position = position;
		this.distanceToWindow = distanceToWindow;
		this.width = width;
		this.height = height;
	}
	
	public Color rayTrace(Ray ray, LinkedList<Sphere> spheres, Light light) {
		Vector3 closestIntersect = null;
		Sphere intersectedSphere = null;
		double lowestDistance2 = -1;
		
		for(Sphere sphere : spheres) {
			Vector3 intersect = ray.getIntersectWith(sphere);
			if(intersect == null) continue;

			double distance2 = Vector3.subtract(intersect, ray.getOrigin()).getLength2();
			
			if(distance2 < lowestDistance2 || closestIntersect == null) {
				closestIntersect = intersect;
				intersectedSphere = sphere;
				lowestDistance2 = distance2;
			}
		}
		
		if(closestIntersect == null) return BACKGROUND;
		
		Vector3 lightVector = Vector3.subtract(light.getPosition(), closestIntersect).normalize();
		Vector3 normalVector = Vector3.subtract(closestIntersect, intersectedSphere.getPosition()).normalize();
		
		Ray lightRay = new Ray(closestIntersect, lightVector.normalize());
		
		boolean reachesLight = true;
		
		for(Sphere sphere : spheres) {
			Vector3 intersect = lightRay.getIntersectWith(sphere);

			if(intersect != null) {
				reachesLight = false;
				break;
			}
		}
		
		double shade = Vector3.dotProduct(lightVector, normalVector);
		if(!reachesLight || shade < 0) shade = 0;
		
		double colorScalar = AMBIENT_COEFFICIENT + DIFFUSE_COEFFICIENT * shade;
		Color rawColor = intersectedSphere.getColor();
		
		double r = (rawColor.getRed() / 255D) * colorScalar;
		double g = (rawColor.getGreen() / 255D) * colorScalar;
		double b = (rawColor.getBlue() / 255D) * colorScalar;
		
		if(r > 1) r = 1;
		if(g > 1) g = 1;
		if(b > 1) b = 1;
		
		Color color = new Color((float) r, (float) g, (float) b);

		return color;
	}
	
	public void render(Graphics g, LinkedList<Sphere> spheres, Light light) {
		Vector3 windowCenter = Vector3.add(position, new Vector3(0, 0, distanceToWindow));
		Vector3 windowBottomLeft = Vector3.subtract(windowCenter, new Vector3(width / 2, height / 2, 0));
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Vector3 pixelPosition = Vector3.add(windowBottomLeft, new Vector3(x, y, 0));
				Vector3 direction = Vector3.subtract(pixelPosition, position).normalize();
				
				Ray ray = new Ray(position, direction);
				Color pixel = rayTrace(ray, spheres, light);

				g.setColor(pixel);
				g.drawLine(x, height - y, x, height - y);
			}
		}
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public double getDistanceToWindow() {
		return distanceToWindow;
	}

	public void setDistanceToWindow(double distanceToWindow) {
		this.distanceToWindow = distanceToWindow;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
