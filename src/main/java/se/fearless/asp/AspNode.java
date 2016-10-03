package se.fearless.asp;

import java.util.ArrayList;
import java.util.List;

public class AspNode<T> {
	private final List<Entry<T>> nodes = new ArrayList<>();
	private final AspNode<T>[] children = new AspNode[8];

	public void add(Entry<T> entry) {
		nodes.add(entry);
	}

	public void addIntersectingToList(double x, double y, double z, double radius, List<T> result) {
		nodes.stream().map(Entry::getValue).forEachOrdered(result::add);
	}

	public int getNumberOfChildNodes() {
		int sum = 0;
		for (int i = 0; i < children.length; i++) {
			AspNode<T> child = children[i];
			if (child != null) {
				sum++;
			}
		}
		return sum;
	}

	public AspNode<T> getChildNode(Octant octant) {
		AspNode<T> child = children[octant.getIndex()];
		if (child == null) {
			return EMPTY_NODE;
		}
		return child;
	}

	private final AspNode<T> EMPTY_NODE = new AspNode<T>() {
		@Override
		public int getNumberOfChildNodes() {
			return 0;
		}
	};
}

enum Octant {
	TOP_LEFT_BACK(0),
	TOP_RIGHT_BACK(1),
	TOP_LEFT_FRONT(2),
	TOP_RIGHT_FRONT(3),
	BOTTOM_LEFT_BACK(4),
	BOTTOM_RIGHT_BACK(5),
	BOTTOM_LEFT_FRONT(6),
	BOTTOM_RIGHT_FRONT(7);

	private final int index;

	Octant(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}