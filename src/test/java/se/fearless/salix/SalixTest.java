package se.fearless.salix;

import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;
import se.fearless.salix.metrics.CountingMetrics;

import java.util.Collection;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SalixTest {

	private Salix<String> salix;
	private CountingMetrics metrics;

	@Before
	public void setUp() throws Exception {
		metrics = new CountingMetrics();
		salix = new Salix<>(-1024, -1024, -1024, 1024, 1024, 1024, 30, metrics, 256);
	}

	@Test
	public void searchEmptyTreeForIntersectionFindsNothing() throws Exception {
		Iterable<String> iterable = salix.findIntersecting(0, 0, 0, 100);
		assertTrue(Iterables.isEmpty(iterable));
	}

	@Test
	public void addingEntryAtOrigoAndThenSearchingThereFindsIt() throws Exception {
		String element = "foo";
		salix.add(element, 0, 0, 0, 10);
		Iterable<String> iterable = salix.findIntersecting(0, 0, 0, 100);
		assertTrue(Iterables.contains(iterable, element));
	}

	@Test
	public void addingFiveElementsCloseAndFiveFarAwayOnlyFindsTheCloseOnes() throws Exception {
		salix.add("c1", 105, 97, 102, 2);
		salix.add("c2", 95, 97, 102, 2);
		salix.add("c3", 105, 104, 102, 2);
		salix.add("c4", 105, 100, 93, 2);
		salix.add("c5", 101, 97, 96, 2);

		salix.add("f1", -201, -106, 96, 2);
		salix.add("f2", -201, -100, 102, 2);
		salix.add("f3", -200, -96, 100, 2);
		salix.add("f4", -201, -102, 98, 2);
		salix.add("f5", 401, -207, -196, 2);

		Iterable<String> intersecting = salix.findIntersecting(100, 100, 100, 20);

		assertThat(intersecting).hasSize(5).contains("c1", "c2", "c3", "c4", "c5");
	}

	@Test
	public void addOneElementAndThenMoveItAwayWillFindNothing() throws Exception {
		salix.add("waldo", 100, 100, 100, 5);
		salix.move("waldo", 0, -100, 0);

		Iterable<String> intersecting = salix.findIntersecting(100, 100, 100, 20);

		assertThat(intersecting).isEmpty();
	}

	@Test
	public void addOneElementAndThenMoveItWillMakeItBeFoundAtTheNewPosition() throws Exception {
		salix.add("waldo", 100, 100, 100, 5);
		salix.move("waldo", 0, -100, 0);

		Collection<String> intersecting = salix.findIntersecting(0, -105, 0, 20);

		assertThat(intersecting).hasSize(1).contains("waldo");
	}

	@Test
	public void addFiveElementsAndThenMoveAwayOneWillResultInFourFoundAtTheOldLocation() throws Exception {
		salix.add("c1", 105, 97, 102, 2);
		salix.add("c2", 95, 97, 102, 2);
		salix.add("c3", 105, 104, 102, 2);
		salix.add("c4", 105, 100, 93, 2);
		salix.add("c5", 101, 97, 96, 2);

		salix.move("c4", -100, -100, -100);

		Collection<String> intersecting = salix.findIntersecting(100, 100, 100, 20);

		assertThat(intersecting).hasSize(4).doesNotContain("c4");
	}

	@Test
	public void addFiveElementsAndThenMoveAwayOneWillResultInOneFoundAtTheNewLocation() throws Exception {
		salix.add("c1", 105, 97, 102, 2);
		salix.add("c2", 95, 97, 102, 2);
		salix.add("c3", 105, 104, 102, 2);
		salix.add("c4", 105, 100, 93, 2);
		salix.add("c5", 101, 97, 96, 2);

		salix.move("c4", -100, -100, -100);

		Collection<String> intersecting = salix.findIntersecting(-100, -100, -100, 20);

		assertThat(intersecting).hasSize(1).contains("c4");
	}

	@Test
	public void movingAnEntryThroughAWorldFullOfStaticEntriesFindsItAllTheWay() throws Exception {
		long seed = System.currentTimeMillis();

		Random random = new Random(seed);
		for (int i = 0; i < 100000; i++) {
			salix.add("" + i, getRandom(random), getRandom(random), getRandom(random), 10);
		}
		System.out.println("1 - " + (System.currentTimeMillis() - seed));
		System.out.println(salix.getNumberOfChildNodes());

		int halfExtent = 1000;
		salix.add("waldo", -halfExtent, -halfExtent, -halfExtent, 10);

		for (int i = 0; i < halfExtent * 2; i++) {
			salix.move("waldo", -halfExtent + i, -halfExtent + i, -halfExtent + i);
			Collection<String> intersecting = salix.findIntersecting(-halfExtent + i, -halfExtent + i, -halfExtent + i, 50);
			assertThat(intersecting).contains("waldo").withFailMessage("Seed: " + seed);
		}
		System.out.println("2 - " + (System.currentTimeMillis() - seed));
		System.out.println(salix.getNumberOfChildNodes());

	}

	@Test
	public void populateComplexity() throws Exception {
		long init = System.nanoTime();

		Random random = new Random(1234);
		for (int i = 0; i < 100000; i++) {
			long start = System.nanoTime();
			salix.add("" + i, getRandom(random), getRandom(random), getRandom(random), 10);
		}

		System.out.println(salix.getNumberOfChildNodes());
		System.out.println(metrics);
	}

	private int getRandom(Random random) {
		return random.nextInt(2000) - 1000;
	}
}