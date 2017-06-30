package se.fearless.salix;

import javafx.geometry.Point3D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {

	private Box collisions;

	@Before
	public void setUp() throws Exception {
		collisions = new Box(new Point3D(-10, -10, -10), new Point3D(10, 10, 10));

	}

	@Test
	public void outsideToTheLeftBottomAndFront() throws Exception {
		boolean result = collisions.isSphereInside(-13, -14, -14, 2);
		assertFalse(result);
	}

	@Test
	public void rightInTheMiddle() throws Exception {
		boolean result = collisions.isSphereInside(0, 0, 0, 2);
		assertTrue(result);
	}



	@Test
	public void insideCloseToTheLeftBound() throws Exception {
		boolean result = collisions.isSphereInside(-7.8, 0, 0, 2);
		assertTrue(result);
	}

	@Test
	public void outsideCloseToTheLeftBound() throws Exception {
		boolean result = collisions.isSphereInside(-12.1, 0, 0, 2);
		assertFalse(result);
	}

	@Test
	public void insideCloseToTheRightBound() throws Exception {
		boolean result = collisions.isSphereInside(7.8, 0, 0, 2);
		assertTrue(result);
	}

	@Test
	public void outsideCloseToTheRightBound() throws Exception {
		boolean result = collisions.isSphereInside(10.1, 0, 0, 2);
		assertFalse(result);
	}


	@Test
	public void insideCloseToTheBottomBound() throws Exception {
		boolean result = collisions.isSphereInside(0, -7.9, 0, 2);
		assertTrue(result);
	}

	@Test
	public void outsideCloseToTheBottomBound() throws Exception {
		boolean result = collisions.isSphereInside(0, -12.1, 0, 2);
		assertFalse(result);
	}

	@Test
	public void insideCloseToTheTopBound() throws Exception {
		boolean result = collisions.isSphereInside(0, 7.8, 0, 2);
		assertTrue(result);
	}

	@Test
	public void outsideCloseToTheTopBound() throws Exception {
		boolean result = collisions.isSphereInside(0, 10.1, 0, 2);
		assertFalse(result);
	}


	@Test
	public void insideCloseToTheFrontBound() throws Exception {
		boolean result = collisions.isSphereInside(0, 0, -7.9, 2);
		assertTrue(result);
	}

	@Test
	public void outsideCloseToTheFrontBound() throws Exception {
		boolean result = collisions.isSphereInside(0, 0, -12.1, 2);
		assertFalse(result);
	}

	@Test
	public void insideCloseToTheBackBound() throws Exception {
		boolean result = collisions.isSphereInside(0, 0, 7.8, 2);
		assertTrue(result);
	}

	@Test
	public void outsideCloseToThebackBound() throws Exception {
		boolean result = collisions.isSphereInside(0, 0, 10.1, 2);
		assertFalse(result);
	}


	@Test
	public void calculateCenterForBoxInOrigo() throws Exception {
		Point3D center = collisions.getCenter();

		assertEquals(new Point3D(0, 0, 0), center);
	}

	@Test
	public void sphereIntersectsTopFace() throws Exception {
		assertTrue(collisions.isSphereIntersecting(0, 0, 9, 2));
		assertFalse(collisions.isSphereInside(0, 0, 9, 2));
	}

	@Test
	public void sphereIntersectsBottomFace() throws Exception {
		assertTrue(collisions.isSphereIntersecting(0, 0, -9, 2));
		assertFalse(collisions.isSphereInside(0, 0, -9, 2));
	}

	@Test
	public void sphereDoesNotIntersectWhenOutside() throws Exception {
		assertFalse(collisions.isSphereIntersecting(0, 0, -13, 2));
	}



}