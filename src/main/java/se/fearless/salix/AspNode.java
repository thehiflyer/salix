package se.fearless.salix;

import javafx.geometry.Point3D;
import se.fearless.salix.metrics.Metrics;
import se.fearless.salix.metrics.NoOpMetrics;

import java.util.ArrayList;
import java.util.List;

/*
                +----------------+
               /                /|
              /----------------/ |
             /                /  |
            /                /   |
		     +----------------+ +y |
		     |                |    |
		     |                |    |
		     |                |    |
		     |                |    + +z
		     |                |   /
		     |                |  /
		     |                | /
		     |                |/
		 -x  +----------------+  +x -y -z

*/

public class AspNode<T> {
	private final List<Entry<T>> entries = new ArrayList<>();
	private final AspNode<T>[] childNodes = new AspNode[8];
	private final Metrics metrics;
	private final double minBoundingSide;
	private final String name;
	private Box bounds;
	private final int splitThreshold;

	public AspNode(Point3D a, Point3D b, int splitThreshold, Metrics metrics, String name, double minBoundingSide) {
		this.metrics = metrics;
		this.minBoundingSide = minBoundingSide;
		bounds = new Box(a, b);
		this.splitThreshold = splitThreshold;
		this.name = name;
	}

	public void add(Entry<T> entry) {
		if (entries.size() >= splitThreshold && isSmallerThanBounds()) {
			metrics.onNodeAboveThresholdWhenAddingBegin(this);
			List<Entry<T>> toKeep = new ArrayList<>();
			for (Entry<T> existingEntry : entries) {
				if (existingEntry.isIntersectsNodeBounds()) {
					toKeep.add(existingEntry);
				} else {
					boolean added = addToChildNode(existingEntry);
					if (!added) {

						toKeep.add(existingEntry);
						existingEntry.updateIntersectionOfNodeBounds();
					}
				}
			}
			addToChildOrList(toKeep, entry);

			entries.clear();
			entries.addAll(toKeep);
			metrics.onNodeAboveThresholdWhenAddingEnd(this);
		} else {
			entries.add(entry);
			entry.updateNode(this);
		}
	}

	private boolean isSmallerThanBounds() {
		return bounds.getShortestSide() > minBoundingSide;
	}

	private void addToChildOrList(List<Entry<T>> toKeep, Entry<T> node) {
		boolean added = addToChildNode(node);
		if (!added) {
			toKeep.add(node);
		}
	}

	private boolean addToChildNode(Entry<T> entry) {
		Point3D position = entry.getPosition();
		Octant octant = getOctant(position.getX(), position.getY(), position.getZ());
		Box octantBounds = getOctantBounds(octant);
		if (octantBounds.isSphereInside(position.getX(), position.getY(), position.getZ(), entry.getRadius())) {
			if (childNodes[octant.getIndex()] == null) {
				metrics.onNodeChildCreation();
				childNodes[octant.getIndex()] = new AspNode<T>(octantBounds.getA(), octantBounds.getB(), splitThreshold, metrics, buildName(name, octant), minBoundingSide);
			}
			childNodes[octant.getIndex()].add(entry);
			return true;
		}
		return false;
	}

	private String buildName(String name, Octant octant) {
		return name + "." + octant.name();
	}

	private Box getOctantBounds(Octant octant) {
		if (childNodes[octant.getIndex()] != null) {
			return childNodes[octant.getIndex()].bounds;
		}
		return octant.createBounds(bounds);
	}

	private Octant getOctant(double x, double y, double z) {
		boolean left = x - bounds.getCenter().getX() < 0;
		boolean top = y - bounds.getCenter().getY() > 0;
		boolean front = z - bounds.getCenter().getZ() < 0;

		return Octant.mapOctant(left, top, front);
	}


	public AspNode<T> getChildNode(Octant octant) {
		AspNode<T> child = childNodes[octant.getIndex()];
		if (child == null) {
			return EMPTY_NODE;
		}
		return child;
	}

	public void addIntersectingToList(Point3D position, double radius, List<T> result) {
		for (int i = 0; i < childNodes.length; i++) {
			AspNode<T> child = childNodes[i];
			if (child != null) {
				boolean sphereIsOutside = child.bounds.isSphereOutside(position.getX(), position.getY(), position.getZ(), radius);
				if (!sphereIsOutside) {
					child.addIntersectingToList(position, radius, result);
				}
			}
		}
		for (Entry<T> node : entries) {
			if (node.getPosition().distance(position) <= radius) {
				result.add(node.getValue());
			}
		}
	}


	public int getNumberOfChildNodes() {
		int sum = 0;
		for (AspNode<T> child : childNodes) {
			if (child != null) {
				sum++;
				sum += child.getNumberOfChildNodes();
			}
		}
		return sum;
	}

	public static final AspNode EMPTY_NODE = new AspNode(new Point3D(0, 0, 0), new Point3D(0, 0, 0), 0, new NoOpMetrics(), "EMPTY", 0) {
		@Override
		public int getNumberOfChildNodes() {
			return 0;
		}
	};

	public boolean isWithinBounds(double x, double y, double z, double radius) {
		return bounds.isSphereInside(x, y, z, radius);
	}

	public boolean isIntersectingSplitPlanes(double x, double y, double z, double radius) {
		for (AspNode<T> node : childNodes) {
			if (node != null) {
				boolean sphereIntersecting = node.bounds.isSphereIntersecting(x, y, z, radius);
				if (sphereIntersecting) {
					return true;
				}
			}
		}
		return false;
	}

	public void updateNodeAndPositionForEntry(Entry<T> entry, double x, double y, double z) {
		Octant octant = getOctant(x, y, z);
		AspNode<T> childNode = getChildNode(octant);
		if (childNode != EMPTY_NODE) {
			childNode.updateNodeAndPositionForEntry(entry, x, y, z);
		} else {
			if (childNode == entry.getNode()) {
				entry.updatePosition(x, y, z);
			} else {
				entry.getNode().remove(entry);
				entry.updatePosition(x, y, z);
				add(entry);
			}
		}
	}

	private void remove(Entry<T> entry) {
		entries.remove(entry);
	}

	@Override
	public String toString() {
		return name + '\'' +
				", bounds=" + bounds;
	}
}

