package se.fearless.asp;

import javafx.geometry.Point3D;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlaneTest {

	private static final double EPSILON = 10e-6;

	@Test
	public void pointOnThePlusSide() throws Exception {
		Plane plane = new Plane(new Point3D(0, 0, 0), new Point3D(1, 0, 0));
		double distance = plane.distance(new Point3D(10, 0, 0));
		assertEquals(10, distance, EPSILON);
	}

	@Test
	public void pointOnThePlaneHasZeroDistance() throws Exception {
		Plane plane = new Plane(new Point3D(10, 0, 0), new Point3D(1, 0, 0));
		double distance = plane.distance(new Point3D(10, 0, 0));
		assertEquals(0, distance, EPSILON);
	}

	@Test
	public void pointOnTheMinusSide() throws Exception {
		Plane plane = new Plane(new Point3D(0, 10, 0), new Point3D(0, 1, 0));
		double distance = plane.distance(new Point3D(0, 0, 0));
		assertEquals(-10, distance, EPSILON);
	}

	@Test
	public void sphereIsInside() throws Exception {
		Plane plane = new Plane(new Point3D(0, 10, 0), new Point3D(0, 1, 0));
		Point3D sphereCenter = new Point3D(0, 5, 0);
		assertTrue(plane.isSphereInside(sphereCenter, 3));
		assertFalse(plane.isSphereOutside(sphereCenter, 3));
	}

	@Test
	public void sphereIsNotInside() throws Exception {
		Plane plane = new Plane(new Point3D(0, 0, -10), new Point3D(0, 0, -1));
		Point3D sphereCenter = new Point3D(0, 0, -15);
		assertFalse(plane.isSphereInside(sphereCenter, 3));
		assertTrue(plane.isSphereOutside(sphereCenter, 3));
	}

	@Test
	public void sphereIntersectsFromInside() throws Exception {
		Plane plane = new Plane(new Point3D(0, 10, 0), new Point3D(0, 1, 0));
		Point3D sphereCenter = new Point3D(0, 8, 0);
		assertTrue(plane.doesSphereItersect(sphereCenter, 3));
	}

	@Test
	public void sphereIntersectsFromOutside() throws Exception {
		Plane plane = new Plane(new Point3D(0, 10, 0), new Point3D(0, 1, 0));
		Point3D sphereCenter = new Point3D(0, 12, 0);
		assertTrue(plane.doesSphereItersect(sphereCenter, 3));
	}

	@Test
	public void sphereDoesNotIntersect() throws Exception {
		Plane plane = new Plane(new Point3D(0, 10, 0), new Point3D(0, 1, 0));
		Point3D sphereCenter = new Point3D(0, 14, 0);
		assertFalse(plane.doesSphereItersect(sphereCenter, 3));
	}

}