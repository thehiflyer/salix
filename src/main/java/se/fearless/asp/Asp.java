package se.fearless.asp;

import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Asp<T> {
	private final AspNode<T> root;
	private final Map<T, Entry<T>> entryLookup = new ConcurrentHashMap<>();

	public Asp(double x1, double y1, double z1, double x2, double y2, double z2, int splitThreshold) {
		root = new AspNode<>(new Point3D(x1, y1, z1), new Point3D(x2, y2, z2), splitThreshold);
	}

	public Collection<T> findIntersecting(double x, double y, double z, double radius) {
		List<T> result = new ArrayList<T>();
		root.addIntersectingToList(new Point3D(x, y, z), radius, result);
		return result;
	}

	public void add(T entry, double x, double y, double z, double radius) {
		Entry<T> internalEntry = new Entry<>(entry, x, y, z, radius);
		root.add(internalEntry);
		entryLookup.put(entry, internalEntry);
	}

	public int getNumberOfChildNodes() {
		return root.getNumberOfChildNodes();
	}

	public void move(T entry, double x, double y, double z) {
		Entry<T> internalEntry = entryLookup.get(entry);
		AspNode<T> currentNode = internalEntry.getNode();
		if (currentNode.isWithinBounds(x, y, z, internalEntry.getRadius())) {
			currentNode.updateNodeAndPositionForEntry(internalEntry, x, y, z);
			return;
		}
		root.updateNodeAndPositionForEntry(internalEntry, x, y, z);
	}
}
