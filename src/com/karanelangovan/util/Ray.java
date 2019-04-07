package com.karanelangovan.util;

import com.karanelangovan.objects.Sphere;

public class Ray {
	private Vector3 origin, direction;

	public Ray(Vector3 origin, Vector3 direction) {
		this.origin = origin;
		this.direction = direction;
	}
	
	public Vector3 getIntersectWith(Sphere sphere) {
		Vector3 O = origin;
		Vector3 C = sphere.getPosition();
		Vector3 v = direction;
		double r = sphere.getRadius();
		
		Vector3 u = Vector3.subtract(C, O);
		
		double OPLength = Vector3.dotProduct(u, v);
		Vector3 OP = v.scale(OPLength);
		
		double CP2 = Vector3.subtract(OP, u).getLength2();
		
		if(CP2 > r * r) return null;
		
		double PX = Math.sqrt(r * r - CP2);
		
		double d1 = OPLength - PX;
		double d2 = OPLength + PX;
		
		Vector3 X1 = Vector3.add(O, v.scale(d1));
		Vector3 X2 = Vector3.add(O, v.scale(d2));
		
		if(d1 >= 1e-8) {
			return X1;
		} else if(d2 >= 1e-8) {
			return X2;
		} else {
			return null;
		}
	}

	public Vector3 getOrigin() {
		return origin;
	}

	public void setOrigin(Vector3 origin) {
		this.origin = origin;
	}

	public Vector3 getDirection() {
		return direction;
	}

	public void setDirection(Vector3 direction) {
		this.direction = direction;
	}
}
