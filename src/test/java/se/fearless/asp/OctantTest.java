package se.fearless.asp;

import javafx.geometry.Point3D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OctantTest {

	private Box parentBounds;

	@Before
	public void setUp() throws Exception {
		parentBounds = new Box(new Point3D(-100, -200, -300), new Point3D(100, 200, 300));
	}

	@Test
	public void topLeftFrontBounds() throws Exception {
		Box bounds = Octant.TOP_LEFT_FRONT.createBounds(parentBounds);
		assertEquals(new Point3D(-100, 0, -300), bounds.getA());
		assertEquals(new Point3D(0, 200, 0), bounds.getB());
	}

	@Test
	public void topRightFrontBounds() throws Exception {
		Box bounds = Octant.TOP_RIGHT_FRONT.createBounds(parentBounds);
		assertEquals(new Point3D(0, 0, -300), bounds.getA());
		assertEquals(new Point3D(100, 200, 0), bounds.getB());
	}

	@Test
	public void topLeftBackBounds() throws Exception {
		Box bounds = Octant.TOP_LEFT_BACK.createBounds(parentBounds);
		assertEquals(new Point3D(-100, 0, 0), bounds.getA());
		assertEquals(new Point3D(0, 200, 300), bounds.getB());
	}

	@Test
	public void topRightBackBounds() throws Exception {
		Box bounds = Octant.TOP_RIGHT_BACK.createBounds(parentBounds);
		assertEquals(new Point3D(0, 0, 0), bounds.getA());
		assertEquals(new Point3D(100, 200, 300), bounds.getB());
	}


	@Test
	public void bottomLeftFrontBounds() throws Exception {
		Box bounds = Octant.BOTTOM_LEFT_FRONT.createBounds(parentBounds);
		assertEquals(new Point3D(-100, -200, -300), bounds.getA());
		assertEquals(new Point3D(0, 0, 0), bounds.getB());
	}

	@Test
	public void bottomRightFrontBounds() throws Exception {
		Box bounds = Octant.BOTTOM_RIGHT_FRONT.createBounds(parentBounds);
		assertEquals(new Point3D(0, -200, -300), bounds.getA());
		assertEquals(new Point3D(100, 0, 0), bounds.getB());
	}

	@Test
	public void bottomLeftBackBounds() throws Exception {
		Box bounds = Octant.BOTTOM_LEFT_BACK.createBounds(parentBounds);
		assertEquals(new Point3D(-100, -200, 0), bounds.getA());
		assertEquals(new Point3D(0, 0, 300), bounds.getB());
	}

	@Test
	public void bottomRightBackBounds() throws Exception {
		Box bounds = Octant.BOTTOM_RIGHT_BACK.createBounds(parentBounds);
		assertEquals(new Point3D(0, -200, 0), bounds.getA());
		assertEquals(new Point3D(100, 0, 300), bounds.getB());
	}
}