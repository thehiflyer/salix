package se.fearless.asp;

import javafx.geometry.Point3D;

import java.util.EnumMap;
import java.util.Map;

public class Box {

	private final Point3D a;
	private final Point3D b;
	private final Point3D center;
	private final Map<Face, Plane> planes = new EnumMap<>(Face.class);

	public Box(Point3D a, Point3D b) {
		this.a = new Point3D(Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()));
		this.b = new Point3D(Math.max(a.getX(), b.getX()), Math.max(a.getY(), b.getY()), Math.max(a.getZ(), b.getZ()));
		Point3D size = b.subtract(a);
		center = a.add(size.multiply(0.5));
		planes.put(Face.RIGHT, new Plane(b, new Point3D(1, 0, 0)));
		planes.put(Face.LEFT, new Plane(a, new Point3D(-1, 0, 0)));
		planes.put(Face.TOP, new Plane(b, new Point3D(0, 0, 1)));
		planes.put(Face.BOTTOM, new Plane(a, new Point3D(0, 0, -1)));
		planes.put(Face.FRONT, new Plane(a, new Point3D(0, -1, 0)));
		planes.put(Face.BACK, new Plane(b, new Point3D(0, 1, 0)));
	}


	boolean isSphereInside(double x, double y, double z, double radius) {
		Point3D sphereCenter = new Point3D(x, y, z);
		for (Plane plane : planes.values()) {
			if (!plane.isSphereInside(sphereCenter, radius))  {
				return false;
			}
		}
		return true;
	}

	boolean isSphereIntersecting(double x, double y, double z, double radius) {
		Point3D sphereCenter = new Point3D(x, y, z);
		Plane left = planes.get(Face.LEFT);
		Plane right = planes.get(Face.RIGHT);
		Plane front = planes.get(Face.FRONT);
		Plane back = planes.get(Face.BACK);
		Plane top = planes.get(Face.TOP);
		Plane bottom = planes.get(Face.BOTTOM);
		boolean in_left   = !left.isSphereOutside(sphereCenter, radius);
		boolean in_right  = !right.isSphereOutside(sphereCenter, radius);
		boolean in_front  = !front.isSphereOutside(sphereCenter, radius);
		boolean in_back   = !back.isSphereOutside(sphereCenter, radius);
		boolean in_top    = !top.isSphereOutside(sphereCenter, radius);
		boolean in_bottom = !bottom.isSphereOutside(sphereCenter, radius);

		if (top.doesSphereItersect(sphereCenter, radius) &&
				in_left && in_right && in_front && in_back) {
			return true;
		}

		if (bottom.doesSphereItersect(sphereCenter, radius) &&
				in_left && in_right && in_front && in_back) {
			return true;
		}

		if (left.doesSphereItersect(sphereCenter, radius) &&
				in_top && in_bottom && in_front && in_back) {
			return true;
		}

		if (right.doesSphereItersect(sphereCenter, radius) &&
				in_top && in_bottom && in_front && in_back) {
			return true;
		}

		if (front.doesSphereItersect(sphereCenter, radius) &&
				in_top && in_bottom && in_left && in_right) {
			return true;
		}

		if (back.doesSphereItersect(sphereCenter, radius) &&
				in_top && in_bottom && in_left && in_right) {
			return true;
		}

		return false;
	}

	public boolean isSphereOutside(double x, double y, double z, double radius) {
		return !(isSphereInside(x, y, z, radius) || isSphereIntersecting(x, y, z, radius));
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
