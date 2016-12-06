package se.fearless.asp.performance;

import org.openjdk.jmh.annotations.*;
import se.fearless.asp.Asp;

import java.util.Random;
import java.util.stream.DoubleStream;

@State(Scope.Benchmark)
public class PerformanceTest {

	public static final int NUMBER_OF_ENTRIES_TO_ADD = 10000;
	public static final double MIN_BOUND = -10000.0;
	public static final double MAX_BOUND = 10000.0;
	private Asp<String> asp;
	private Random random;
	private double[] randomNumbers;

	@Setup(Level.Trial)
	public void setUp() {
		asp = new Asp<>(MIN_BOUND, MIN_BOUND, MIN_BOUND, MAX_BOUND, MAX_BOUND, MAX_BOUND);
		random = new Random(974612);

		DoubleStream doubles = random.doubles(3 * NUMBER_OF_ENTRIES_TO_ADD, MIN_BOUND, MAX_BOUND);
		randomNumbers = doubles.toArray();
	}

	@Benchmark
	@BenchmarkMode(Mode.SampleTime)
	public int addNodes() {
		for (int i = 0; i < NUMBER_OF_ENTRIES_TO_ADD; i++) {
			asp.add("entry" + i, randomNumbers[i], randomNumbers[i + NUMBER_OF_ENTRIES_TO_ADD], randomNumbers[i+ 2 * NUMBER_OF_ENTRIES_TO_ADD], 10);
		}
		return asp.getNumberOfChildNodes();
	}
}
