package se.fearless.salix;

import javafx.geometry.Point3D;

enum Octant {
	TOP_LEFT_BACK(0) {
		@Override
		public Box createBounds(Box parentBounds) {
			return new Box(new Point3D(parentBounds.getA().getX(), parentBounds.getCenter().getY(), parentBounds.getCenter().getZ()),
					new Point3D(parentBounds.getCenter().getX(), parentBounds.getB().getY(), parentBounds.getB().getZ()));
		}
	},
	TOP_RIGHT_BACK(1) {
		@Override
		public Box createBounds(Box parentBounds) {
			return new Box(new Point3D(parentBounds.getCenter().getX(), parentBounds.getCenter().getY(), parentBounds.getCenter().getZ()),
					new Point3D(parentBounds.getB().getX(), parentBounds.getB().getY(), parentBounds.getB().getZ()));
		}
	},
	TOP_LEFT_FRONT(2) {
		@Override
		public Box createBounds(Box parentBounds) {
			return new Box(new Point3D(parentBounds.getA().getX(), parentBounds.getCenter().getY(), parentBounds.getA().getZ()),
					new Point3D(parentBounds.getCenter().getX(), parentBounds.getB().getY(), parentBounds.getCenter().getZ()));
		}
	},
	TOP_RIGHT_FRONT(3) {
		@Override
		public Box createBounds(Box parentBounds) {
			return new Box(new Point3D(parentBounds.getCenter().getX(), parentBounds.getCenter().getY(), parentBounds.getA().getZ()),
					new Point3D(parentBounds.getB().getX(), parentBounds.getB().getY(), parentBounds.getCenter().getZ()));
		}
	},
	BOTTOM_LEFT_BACK(4) {
		@Override
		public Box createBounds(Box parentBounds) {
			return new Box(new Point3D(parentBounds.getA().getX(), parentBounds.getA().getY(), parentBounds.getCenter().getZ()),
					new Point3D(parentBounds.getCenter().getX(), parentBounds.getCenter().getY(), parentBounds.getB().getZ()));
		}
	},
	BOTTOM_RIGHT_BACK(5) {
		@Override
		public Box createBounds(Box parentBounds) {
			return new Box(new Point3D(parentBounds.getCenter().getX(), parentBounds.getA().getY(), parentBounds.getCenter().getZ()),
					new Point3D(parentBounds.getB().getX(), parentBounds.getCenter().getY(), parentBounds.getB().getZ()));
		}
	},
	BOTTOM_LEFT_FRONT(6) {
		@Override
		public Box createBounds(Box parentBounds) {
			return new Box(new Point3D(parentBounds.getA().getX(), parentBounds.getA().getY(), parentBounds.getA().getZ()),
					new Point3D(parentBounds.getCenter().getX(), parentBounds.getCenter().getY(), parentBounds.getCenter().getZ()));
		}
	},
	BOTTOM_RIGHT_FRONT(7) {
		@Override
		public Box createBounds(Box parentBounds) {
			return new Box(new Point3D(parentBounds.getCenter().getX(), parentBounds.getA().getY(), parentBounds.getA().getZ()),
					new Point3D(parentBounds.getB().getX(), parentBounds.getCenter().getY(), parentBounds.getCenter().getZ()));
		}
	};

	private final int index;

	Octant(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public static Octant mapOctant(boolean left, boolean top, boolean front) {
		if (left && top && !front) {
			return TOP_LEFT_BACK;
		} else if(!left && top && !front){
			return TOP_RIGHT_BACK;
		} else if (left && top) {
			return TOP_LEFT_FRONT;
		} else if (!left && top) {
			return TOP_RIGHT_FRONT;
		} else if (left && !front) {
			return BOTTOM_LEFT_BACK;
		} else if (!left && !front) {
			return BOTTOM_RIGHT_BACK;
		} else if (left) {
			return BOTTOM_LEFT_FRONT;
		} else {
			return BOTTOM_RIGHT_FRONT;
		}
	}

	public abstract Box createBounds(Box parentBounds);
}
