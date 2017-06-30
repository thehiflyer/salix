package se.fearless.salix;

import javafx.geometry.Point3D;
import se.fearless.salix.metrics.Metrics;
import se.fearless.salix.metrics.NoOpMetrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Asp<T> {
	private final AspNode<T> root;
	private final Map<T, Entry<T>> entryLookup = new ConcurrentHashMap<>();
	private final Metrics metrics;

	public Asp(double x1, double y1, double z1, double x2, double y2, double z2, int splitThreshold, Metrics metrics, double minBoundingSide) {
		this.metrics = metrics;
		root = new AspNode<>(new Point3D(x1, y1, z1), new Point3D(x2, y2, z2), splitThreshold, metrics, "root", minBoundingSide);
	}

	public Asp(double x1, double y1, double z1, double x2, double y2, double z2, int splitThreshold) {
		this(x1, y1, z1, x2, y2, z2, splitThreshold, new NoOpMetrics(), 0.0);
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
		metrics.onAddEntryBegin();
		try {
			Entry<T> internalEntry = new Entry<>(entry, x, y, z, radius);
			root.add(internalEntry);
			entryLookup.put(entry, internalEntry);
		} finally {
			metrics.onAddEntryEnd();
		}
	}

	public int getNumberOfChildNodes() {
		return root.getNumberOfChildNodes();
	}

	public void move(T entry, double x, double y, double z) {
		metrics.onMoveEntryBegin();
		try {
			Entry<T> internalEntry = entryLookup.get(entry);
			AspNode<T> currentNode = internalEntry.getNode();
			if (currentNode.isWithinBounds(x, y, z, internalEntry.getRadius())) {
				currentNode.updateNodeAndPositionForEntry(internalEntry, x, y, z);
				return;
			}
			root.updateNodeAndPositionForEntry(internalEntry, x, y, z);
		} finally {
			metrics.onMoveEntryEnd();
		}
	}
}
