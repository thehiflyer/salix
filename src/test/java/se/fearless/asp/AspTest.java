package se.fearless.asp;

import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AspTest {

	private Asp<String> asp;

	@Before
	public void setUp() throws Exception {
		asp = new Asp<>(-1024, -1024, -1024, 1024, 1024, 1024, 15);
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

	@Test
	public void addOneElementAndThenMoveItWillMakeItBeFoundAtTheNewPosition() throws Exception {
		asp.add("waldo", 100, 100, 100, 5);
		asp.move("waldo", 0, -100, 0);

		Collection<String> intersecting = asp.findIntersecting(0, -105, 0, 20);

		assertThat(intersecting).hasSize(1).contains("waldo");
	}

	@Test
	public void addFiveElementsAndThenMoveAwayOneWillResultInFourFoundAtTheOldLocation() throws Exception {
		asp.add("c1", 105, 97, 102, 2);
		asp.add("c2", 95, 97, 102, 2);
		asp.add("c3", 105, 104, 102, 2);
		asp.add("c4", 105, 100, 93, 2);
		asp.add("c5", 101, 97, 96, 2);

		asp.move("c4", -100, -100, -100);

		Collection<String> intersecting = asp.findIntersecting(100, 100, 100, 20);

		assertThat(intersecting).hasSize(4).doesNotContain("c4");
	}

	@Test
	public void addFiveElementsAndThenMoveAwayOneWillResultInOneFoundAtTheNewLocation() throws Exception {
		asp.add("c1", 105, 97, 102, 2);
		asp.add("c2", 95, 97, 102, 2);
		asp.add("c3", 105, 104, 102, 2);
		asp.add("c4", 105, 100, 93, 2);
		asp.add("c5", 101, 97, 96, 2);

		asp.move("c4", -100, -100, -100);

		Collection<String> intersecting = asp.findIntersecting(-100, -100, -100, 20);

		assertThat(intersecting).hasSize(1).contains("c4");
	}

	@Test
	public void movingAnEntryThroughAWorldFullOfStaticEntriesFindsItAllTheWay() throws Exception {
		long seed = System.currentTimeMillis();

		Random random = new Random(seed);
		for (int i = 0; i < 100000; i++) {
			asp.add("" + i, getRandom(random), getRandom(random), getRandom(random), 10);
		}
		System.out.println("1 - " + (System.currentTimeMillis() - seed));
		System.out.println(asp.getNumberOfChildNodes());

		int halfExtent = 1000;
		asp.add("waldo", -halfExtent, -halfExtent, -halfExtent, 10);

		for (int i = 0; i < halfExtent * 2; i++) {
			asp.move("waldo", -halfExtent + i, -halfExtent + i, -halfExtent + i);
			Collection<String> intersecting = asp.findIntersecting(-halfExtent + i, -halfExtent + i, -halfExtent + i, 50);
			assertThat(intersecting).contains("waldo").withFailMessage("Seed: " + seed);
		}
		System.out.println("2 - " + (System.currentTimeMillis() - seed));
		System.out.println(asp.getNumberOfChildNodes());

	}

	@Test
	public void populateComplexity() throws Exception {
		long init = System.nanoTime();

		Random random = new Random(1234);
		for (int i = 0; i < 30000; i++) {
			long start = System.nanoTime();
			asp.add("" + i, getRandom(random), getRandom(random), getRandom(random), 10);
			System.out.println(i + "\t" + (System.nanoTime() - init));
		}
		//System.out.println("1 - " + (System.currentTimeMillis() - init));
	}

	private int getRandom(Random random) {
		return random.nextInt(2000) - 1000;
	}
}