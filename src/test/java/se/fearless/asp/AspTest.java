package se.fearless.asp;

import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AspTest {

	private Asp<String> asp;

	@Before
	public void setUp() throws Exception {
		asp = new Asp<>();
	}

	@Test
	public void searchEmptyTreeForIntersectionFindsNothing() throws Exception {
		Iterable<String> iterable = asp.findIntersecting(0, 0, 0, 100);
		assertTrue(Iterables.isEmpty(iterable));
	}

	@Test
	public void addingEntryAtOrigoAndThenSearchingThereFindsIt() throws Exception {
		String element = "foo";
		asp.add(element, 0, 0, 0, 10);
		Iterable<String> iterable = asp.findIntersecting(0, 0, 0, 100);
		assertTrue(Iterables.contains(iterable, element));
	}
}