package se.fearless.asp;

import javafx.geometry.Point3D;

public class Plane {
	private final Point3D pointInPlane;
	private final Point3D direction;

	public Plane(Point3D pointInPlane, Point3D direction) {
		this.pointInPlane = pointInPlane;
		this.direction = direction;
	}


	public double distance(Point3D point) {
		return point.subtract(this.pointInPlane).dotProduct(direction);
	}

	public boolean isSphereInside(Point3D center, double radius) {
		return -distance(center) > radius;
	}

	public boolean isSphereOutside(Point3D center, double radius) {
		return distance(center) > radius;
	}

	public boolean doesSphereItersect(Point3D center, double radius) {
		return Math.abs(distance(center)) <= radius;
	}
}
