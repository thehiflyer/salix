package se.fearless.asp;

import javafx.geometry.Point3D;
import se.fearless.asp.metrics.Metrics;
import se.fearless.asp.metrics.NoOpMetrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Asp<T> {
	private final AspNode<T> root;
	private final Map<T, Entry<T>> entryLookup = new ConcurrentHashMap<>();
	private final Metrics metrics;

	public Asp(double x1, double y1, double z1, double x2, double y2, double z2, int splitThreshold, Metrics metrics) {
		this.metrics = metrics;
		root = new AspNode<>(new Point3D(x1, y1, z1), new Point3D(x2, y2, z2), splitThreshold, metrics);
	}

	public Asp(double x1, double y1, double z1, double x2, double y2, double z2, int splitThreshold) {
		this(x1, y1, z1, x2, y2, z2, splitThreshold, new NoOpMetrics());
	}

	public Collection<T> findIntersecting(double x, double y, double z, double radius) {
		metrics.onFindBegin();
		try {
			List<T> result = new ArrayList<T>();
			root.addIntersectingToList(new Point3D(x, y, z), radius, result);
			return result;
		} finally {
			metrics.onFindEnd();
		}
	}

	public void add(T entry, double x, double y, double z, double radius) {
		metrics.onAddBegin();
		try {
			Entry<T> internalEntry = new Entry<>(entry, x, y, z, radius);
			root.add(internalEntry);
			entryLookup.put(entry, internalEntry);
		} finally {
			metrics.onAddEnd();
		}
	}

	public int getNumberOfChildNodes() {
		return root.getNumberOfChildNodes();
	}

	public void move(T entry, double x, double y, double z) {
		metrics.onMoveBegin();
		try {
			Entry<T> internalEntry = entryLookup.get(entry);
			AspNode<T> currentNode = internalEntry.getNode();
			if (currentNode.isWithinBounds(x, y, z, internalEntry.getRadius())) {
				currentNode.updateNodeAndPositionForEntry(internalEntry, x, y, z);
				return;
			}
			root.updateNodeAndPositionForEntry(internalEntry, x, y, z);
		} finally {
			metrics.onMoveEnd();
		}
	}
}
