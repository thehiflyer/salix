package se.fearless.asp;

import javafx.geometry.Point3D;

public class Entry<T> {
	private final T value;
	private Point3D position;
	private final double radius;
	private AspNode<T> currentNode;

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

	public void updateNode(AspNode<T> node, double x, double y, double z) {

	}

	public void updateNode(AspNode<T> node) {
		this.currentNode = node;
	}

	public AspNode<T> getNode() {
		return currentNode;
	}

	public void updatePosition(int x, int y, int z) {
		position = new Point3D(x, y, z);
	}
}
