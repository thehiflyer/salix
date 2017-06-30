package se.fearless.salix;

import javafx.geometry.Point3D;
import org.junit.Before;
import org.junit.Test;
import se.fearless.salix.metrics.CountingMetrics;

import static org.junit.Assert.*;

public class SalixNodeMetricsTest {

	private SalixNode<String> node;
	private CountingMetrics metrics;

	@Before
	public void setUp() throws Exception {
		metrics = new CountingMetrics();
		node = new SalixNode<>(new Point3D(-100, -100, -100), new Point3D(100, 100, 100), 3, metrics, "test", 0);
	}

	@Test
	public void addFourEntriesTriggersOneSplit() throws Exception {
		node.add(new Entry<>("TOP_LEFT_BACK_1", -11, 10, 10, 5));
		node.add(new Entry<>("TOP_LEFT_BACK_2", -10, 10, 10, 5));

		node.add(new Entry<>("TOP_RIGHT_BACK", 10, 12, 13, 5));
		node.add(new Entry<>("BOTTOM_RIGHT_FRONT", 15, -14, 20, 5));

		assertEquals(1, metrics.getAboveThresholdWhenAddingEvents());
	}


	@Test
	public void addFiveEntriesTriggersTwoSplits() throws Exception {
		node.add(new Entry<>("TOP_LEFT_BACK_1", -11, 10, 10, 5));
		node.add(new Entry<>("TOP_LEFT_BACK_2", -10, 10, 10, 5));
		node.add(new Entry<>("TOP_LEFT_BACK_3", -9, 10, 10, 5));
		node.add(new Entry<>("TOP_LEFT_BACK_4", -8, 10, 10, 5));

		node.add(new Entry<>("TOP_RIGHT_BACK", 10, 12, 13, 5));
		node.add(new Entry<>("BOTTOM_RIGHT_FRONT", 15, -14, 20, 5));

		System.out.println(metrics);
		System.out.println(node.getNumberOfChildNodes());
		assertEquals(2, metrics.getAboveThresholdWhenAddingEvents());
	}

}