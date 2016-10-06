package se.fearless.asp;

import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.List;

/*
                +----------------+
               /                /|
              /----------------/ |
             /                /  |
            /                /   |
		     +----------------+ y  |
		     |                |    |
		     |                |    |
		     |                |    |
		     |                |    + z
		     |                |   /
		     |                |  /
		     |                | /
		     |                |/
		 -x  +----------------+  +x

*/

public class AspNode<T> {
	private final List<Entry<T>> nodes = new ArrayList<>();
	private final AspNode<T>[] children = new AspNode[8];
	Box bounds;
	private static final int NUMBER_OF_OBJECTS_BEFORE_SPLIT = 3;

	public AspNode(Point3D a, Point3D b) {
		bounds = new Box(a, b);
	}

	public void add(Entry<T> entry) {
		if (nodes.size() >= NUMBER_OF_OBJECTS_BEFORE_SPLIT) {
			List<Entry<T>> toKeep = new ArrayList<>();
			for (Entry<T> node : nodes) {
				addToChildOrList(toKeep, node);

			}
			addToChildOrList(toKeep, entry);

			nodes.clear();
			nodes.addAll(toKeep);
		} else {
			nodes.add(entry);
		}
	}

	private void addToChildOrList(List<Entry<T>> toKeep, Entry<T> node) {
		boolean added = addToChildNode(node);
		if (!added) {
			toKeep.add(node);
		}
	}

	private boolean addToChildNode(Entry<T> entry) {
		Point3D mid = entry.getPosition();
		boolean left = mid.getX() - bounds.getCenter().getX() < 0;
		boolean top = mid.getY() - bounds.getCenter().getY() > 0;
		boolean front = mid.getZ() - bounds.getCenter().getZ() < 0;

		Octant octant = Octant.mapOctant(left, top, front);
		Box octantBounds = octant.createBounds(bounds);
		Point3D position = entry.getPosition();
		if (octantBounds.isSphereInside(position.getX(), position.getY(), position.getZ(), entry.getRadius())) {
			if (children[octant.getIndex()] == null) {
				children[octant.getIndex()] = new AspNode<T>(octantBounds.getA(), octantBounds.getB());
			}
			children[octant.getIndex()].add(entry);
			return true;
		}
		return false;
	}


	public AspNode<T> getChildNode(Octant octant) {
		AspNode<T> child = children[octant.getIndex()];
		if (child == null) {
			return EMPTY_NODE;
		}
		return child;
	}

	public void addIntersectingToList(double x, double y, double z, double radius, List<T> result) {
		nodes.stream().map(Entry::getValue).forEachOrdered(result::add);
	}

	public int getNumberOfChildNodes() {
		int sum = 0;
		for (AspNode<T> child : children) {
			if (child != null) {
				sum++;
			}
		}
		return sum;
	}

	public static final AspNode EMPTY_NODE = new AspNode(new Point3D(0, 0, 0), new Point3D(0, 0, 0)) {
		@Override
		public int getNumberOfChildNodes() {
			return 0;
		}
	};
}

