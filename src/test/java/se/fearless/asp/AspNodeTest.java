package se.fearless.asp;

import javafx.geometry.Point3D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AspNodeTest {

	private AspNode<String> node;

	@Before
	public void setUp() throws Exception {
		node = new AspNode<>(new Point3D(-128, -128, -128), new Point3D(128, 128, 128), 3);
	}

	@Test
	public void addingANodeIntersectingTheMiddleLinesDoesNotAddChildren() throws Exception {
		node.add(new Entry<>("Foo", 0, 10, 0, 5));
		int numberOfChildNodes = node.getNumberOfChildNodes();

		assertEquals(0, numberOfChildNodes);
	}

	@Test
	public void addingOneNodeInTopLeftBackOctantDoesNotAddAChild() throws Exception {
		node.add(new Entry<>("Foo", -10, 10, 10, 5));
		int numberOfChildNodes = node.getNumberOfChildNodes();

		assertEquals(0, numberOfChildNodes);
	}

	@Test
	public void addingFourObjectsWillCauseNodeToAddChildren() throws Exception {
		node.add(new Entry<>("TOP_LEFT_BACK_1", -11, 10, 10, 5));
		node.add(new Entry<>("TOP_LEFT_BACK_2", -10, 10, 10, 5));

		node.add(new Entry<>("TOP_RIGHT_BACK", 10, 12, 13, 5));
		node.add(new Entry<>("BOTTOM_RIGHT_FRONT", 15, -14, 20, 5));

		int numberOfChildNodes = node.getNumberOfChildNodes();
		assertEquals(3, numberOfChildNodes);
	}
}