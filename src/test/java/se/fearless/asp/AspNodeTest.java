package se.fearless.asp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AspNodeTest {

	private AspNode<String> node;

	@Before
	public void setUp() throws Exception {
		node = new AspNode<>();
	}

	@Test
	public void addingANodeIntersectingTheMiddleLinesDoesNotAddChildren() throws Exception {
		node.add(new Entry<>("Foo", 0, 10, 0, 5));
		int numberOfChildNodes = node.getNumberOfChildNodes();

		assertEquals(0, numberOfChildNodes);
	}
}