package se.fearless.asp.performance;

import org.openjdk.jmh.annotations.*;
import se.fearless.asp.Asp;

import java.util.Random;
import java.util.stream.DoubleStream;

@State(Scope.Benchmark)
public class SearchPerformanceTest {

	public static final int NUMBER_OF_ENTRIES_TO_ADD = 10000;
	public static final double MIN_BOUND = -10000.0;
	public static final double MAX_BOUND = 10000.0;

	public static final int NUMBER_OF_SEARCHES = 100;

	private Asp<String> asp;
	private Random random;
	private double[] randomNumbers;

	@Setup(Level.Trial)
	public void setUp() {
		asp = new Asp<>(MIN_BOUND, MIN_BOUND, MIN_BOUND, MAX_BOUND, MAX_BOUND, MAX_BOUND);
		random = new Random(974612);

		DoubleStream doubles = random.doubles(3 * (NUMBER_OF_ENTRIES_TO_ADD + NUMBER_OF_SEARCHES), MIN_BOUND, MAX_BOUND);
		randomNumbers = doubles.toArray();
		for (int i = 0; i < NUMBER_OF_ENTRIES_TO_ADD; i++) {
			asp.add("entry" + i, randomNumbers[i], randomNumbers[i + NUMBER_OF_ENTRIES_TO_ADD], randomNumbers[i+ 2 * NUMBER_OF_ENTRIES_TO_ADD], 10);
		}

	}

	@Benchmark
	@BenchmarkMode(Mode.SampleTime)
	public Iterable<String> search() {
		Iterable<String> intersecting = null;
		for (int i = 0; i < NUMBER_OF_SEARCHES; i++) {
			int offset = 3 * NUMBER_OF_ENTRIES_TO_ADD + i * 3;
			intersecting = asp.findIntersecting(randomNumbers[offset], randomNumbers[offset + 1], randomNumbers[offset + 2], 150);
		}
		return intersecting;
	}
}