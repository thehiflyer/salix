package se.fearless.asp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Asp<T> {
	private final AspNode<T> root = new AspNode<>();

	public Iterable<T> findIntersecting(double x, double y, double z, double radius) {
		List<T> result = new ArrayList<T>();
		root.addIntersectingToList(x, y, z, radius, result);
		return result;
	}

	public void add(T entry, double x, double y, double z, double radius) {
		root.add(new Entry<>(entry, x, y, z, radius));
	}
}
