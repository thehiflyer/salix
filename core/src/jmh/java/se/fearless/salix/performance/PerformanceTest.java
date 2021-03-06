package se.fearless.salix.performance;

import org.openjdk.jmh.annotations.*;
import se.fearless.salix.Salix;

import java.util.Random;
import java.util.stream.DoubleStream;

@State(Scope.Benchmark)
public class PerformanceTest {

	public static final int NUMBER_OF_ENTRIES_TO_ADD = 10000;
	public static final double MIN_BOUND = -10000.0;
	public static final double MAX_BOUND = 10000.0;
	private Salix<String> salix;
	private Random random;
	private double[] randomNumbers;

	@Setup(Level.Trial)
	public void setUp() {
		salix = new Salix<>(MIN_BOUND, MIN_BOUND, MIN_BOUND, MAX_BOUND, MAX_BOUND, MAX_BOUND, 3);
		random = new Random(974612);

		DoubleStream doubles = random.doubles(3 * NUMBER_OF_ENTRIES_TO_ADD, MIN_BOUND + 20, MAX_BOUND - 20);
		randomNumbers = doubles.toArray();
	}

	@Benchmark
	@BenchmarkMode(Mode.SampleTime)
	public int addNodes() {
		for (int i = 0; i < NUMBER_OF_ENTRIES_TO_ADD; i++) {
			salix.add("entry" + i, randomNumbers[i], randomNumbers[i + NUMBER_OF_ENTRIES_TO_ADD], randomNumbers[i+ 2 * NUMBER_OF_ENTRIES_TO_ADD], 10);
		}
		return salix.getNumberOfChildNodes();
	}
}
