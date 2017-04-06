package se.fearless.asp;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AspTest {

	private Asp<String> asp;

	@Before
	public void setUp() throws Exception {
		asp = new Asp<>(-1024, -1024, -1024, 1024, 1024, 1024);
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

	@Test
	public void addingFiveElementsCloseAndFiveFarAwayOnlyFindsTheCloseOnes() throws Exception {
		asp.add("c1", 105, 97, 102, 2);
		asp.add("c2", 95, 97, 102, 2);
		asp.add("c3", 105, 104, 102, 2);
		asp.add("c4", 105, 100, 93, 2);
		asp.add("c5", 101, 97, 96, 2);

		asp.add("f1", -201, -106, 96, 2);
		asp.add("f2", -201, -100, 102, 2);
		asp.add("f3", -200, -96, 100, 2);
		asp.add("f4", -201, -102, 98, 2);
		asp.add("f5", 401, -207, -196, 2);

		Iterable<String> intersecting = asp.findIntersecting(100, 100, 100, 20);

		assertThat(intersecting).hasSize(5).contains("c1", "c2", "c3", "c4", "c5");
	}

	@Test
	public void addOneElementAndThenMoveItAwayWillFindNothing() throws Exception {
		asp.add("waldo", 100, 100, 100, 5);
		asp.move("waldo", 0, -100, 0);

		Iterable<String> intersecting = asp.findIntersecting(100, 100, 100, 20);

		assertThat(intersecting).isEmpty();
	}


}