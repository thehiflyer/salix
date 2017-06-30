package se.fearless.salix;

import javafx.geometry.Point3D;

public class Entry<T> {
	private final T value;
	private Point3D position;
	private final double radius;
	private SalixNode<T> currentNode;
	private boolean intersectsNodeBounds = false;

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

	public void updateNode(SalixNode<T> node) {
		this.currentNode = node;
		updateIntersectionOfNodeBounds();
	}

	public void updateIntersectionOfNodeBounds() {
		if (currentNode != null) {
			intersectsNodeBounds = currentNode.isIntersectingSplitPlanes(position.getX(), position.getY(), position.getZ(), radius);
		}
	}

	public SalixNode<T> getNode() {
		return currentNode;
	}

	public void updatePosition(double x, double y, double z) {
		position = new Point3D(x, y, z);
		updateIntersectionOfNodeBounds();
	}

	public boolean isIntersectsNodeBounds() {
		return intersectsNodeBounds;
	}

	@Override
	public String toString() {
		return "Entry{" +
				"value=" + value +
				", position=" + position +
				", intersectsNodeBounds=" + intersectsNodeBounds +
				'}';
	}
}
