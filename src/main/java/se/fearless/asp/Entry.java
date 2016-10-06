package se.fearless.asp;

import javafx.geometry.Point3D;

public class Entry<T> {
	private final T value;
	private final Point3D position;
	private final double radius;

	public Entry(T value, double x, double y, double z, double radius) {
		this.value = value;
		this.position = new Point3D(x, y, z);
		this.radius = radius;
	}

	public T getValue() {
		return value;
	}

	public Point3D getPosition() {
		return position;
	}

	public double getRadius() {
		return radius;
	}
}
