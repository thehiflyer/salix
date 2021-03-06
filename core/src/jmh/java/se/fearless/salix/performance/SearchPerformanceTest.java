package se.fearless.salix.performance;

import org.openjdk.jmh.annotations.*;
import se.fearless.salix.Salix;

import java.util.Random;
import java.util.stream.DoubleStream;

@State(Scope.Benchmark)
public class SearchPerformanceTest {

	public static final int NUMBER_OF_ENTRIES_TO_ADD = 10000;
	public static final double MIN_BOUND = -10000.0;
	public static final double MAX_BOUND = 10000.0;

	public static final int NUMBER_OF_SEARCHES = 100;

	private Salix<String> salix;
	private Random random;
	private double[] randomNumbers;

	@Setup(Level.Trial)
	public void setUp() {
		salix = new Salix<>(MIN_BOUND, MIN_BOUND, MIN_BOUND, MAX_BOUND, MAX_BOUND, MAX_BOUND, 3);
		random = new Random(974612);

		DoubleStream doubles = random.doubles(3 * (NUMBER_OF_ENTRIES_TO_ADD + NUMBER_OF_SEARCHES), MIN_BOUND + 20, MAX_BOUND - 20);
		randomNumbers = doubles.toArray();
		for (int i = 0; i < NUMBER_OF_ENTRIES_TO_ADD; i++) {
			salix.add("entry" + i, randomNumbers[i], randomNumbers[i + NUMBER_OF_ENTRIES_TO_ADD], randomNumbers[i+ 2 * NUMBER_OF_ENTRIES_TO_ADD], 10);
		}

	}

	@Benchmark
	@BenchmarkMode(Mode.SampleTime)
	public Iterable<String> search() {
		Iterable<String> intersecting = null;
		for (int i = 0; i < NUMBER_OF_SEARCHES; i++) {
			int offset = 3 * NUMBER_OF_ENTRIES_TO_ADD + i * 3;
			intersecting = salix.findIntersecting(randomNumbers[offset], randomNumbers[offset + 1], randomNumbers[offset + 2], 150);
		}
		return intersecting;
	}

	@Benchmark
	@BenchmarkMode(Mode.SampleTime)
	public Iterable<String> moving() {
		Iterable<String> intersecting = null;
		for (int i = 0; i < NUMBER_OF_SEARCHES; i++) {
			int offset = 3 * NUMBER_OF_ENTRIES_TO_ADD + i * 3;
			int index = random.nextInt(NUMBER_OF_ENTRIES_TO_ADD);

			try {
				salix.move("entry" + index, randomNumbers[offset], randomNumbers[offset + 1], randomNumbers[offset + 2]);
			} catch (Exception e) {
				System.out.println("Index " + index + " moved to " + randomNumbers[offset] + ", " + randomNumbers[offset + 1] + ", " + randomNumbers[offset + 2]);
			}

		}
		intersecting = salix.findIntersecting(randomNumbers[random.nextInt(randomNumbers.length)], randomNumbers[random.nextInt(randomNumbers.length)], randomNumbers[random.nextInt(randomNumbers.length)], 150);
		System.out.println(salix.getNumberOfChildNodes());
		return intersecting;
	}

}
