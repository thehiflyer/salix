package se.fearless.asp;

import javafx.geometry.Point3D;

public class Box {

	private final Point3D a;
	private final Point3D b;
	private final Point3D center;

	public Box(Point3D a, Point3D b) {
		this.a = a;
		this.b = b;
		Point3D size = b.subtract(a);
		center = a.add(size.multiply(0.5));
	}


	boolean isSphereInside(double x, double y, double z, double radius) {
		double minX = x - radius;
		double maxX = x + radius;
		double xMinDiff = a.getX() - minX;
		double xMaxDiff = b.getX() - maxX;
		boolean isWithinX = xMinDiff < 0 && xMaxDiff > 0;

		double minY = y - radius;
		double maxY = y + radius;
		double yMinDiff = a.getY() - minY;
		double yMaxDiff = b.getY() - maxY;
		boolean isWithinY = yMinDiff < 0 && yMaxDiff > 0;

		double minZ = z - radius;
		double maxZ = z + radius;
		double zMinDiff = a.getZ() - minZ;
		double zMaxDiff = b.getZ() - maxZ;
		boolean isWithinZ = zMinDiff < 0 && zMaxDiff > 0;

		return isWithinX && isWithinY && isWithinZ;
	}

	public Point3D getCenter() {
		return center;
	}

	public Point3D getA() {
		return a;
	}

	public Point3D getB() {
		return b;
	}
}
