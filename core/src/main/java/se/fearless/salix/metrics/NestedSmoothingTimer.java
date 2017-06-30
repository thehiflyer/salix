package se.fearless.salix.metrics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NestedSmoothingTimer<T> {
	private final ValueSmoothingMeter meter = new ValueSmoothingMeter(0.001);
	private Map<T, Long> startTimes = new ConcurrentHashMap<>();

	public void start(T key) {
		startTimes.put(key, System.nanoTime());
	}

	public void stop(T key) {
		meter.add(System.nanoTime() - startTimes.get(key));
	}

	@Override
	public String toString() {
		return "" + meter.get();
	}
}
