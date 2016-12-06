package se.fearless.asp;

import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Asp<T> {
	private final AspNode<T> root;

	public Asp(double x1, double y1, double z1, double x2, double y2, double z2) {
		root = new AspNode<>(new Point3D(x1, y1, z1), new Point3D(x2, y2, z2));
	}

	public Iterable<T> findIntersecting(double x, double y, double z, double radius) {
		List<T> result = new ArrayList<T>();
		root.addIntersectingToList(new Point3D(x, y, z), radius, result);
		return result;
	}

	public void add(T entry, double x, double y, double z, double radius) {
		root.add(new Entry<>(entry, x, y, z, radius));
	}

	public int getNumberOfChildNodes() {
		return root.getNumberOfChildNodes();
	}
}
