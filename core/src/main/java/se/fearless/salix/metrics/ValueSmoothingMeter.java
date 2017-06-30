package se.fearless.salix.metrics;


import java.util.concurrent.atomic.AtomicLong;

public class ValueSmoothingMeter {
	private final double weight;
	private final AtomicLong smoothedValue = new AtomicLong();

	public ValueSmoothingMeter(double weight) {
		this.weight = weight;
	}

	public void add(long value) {
		synchronized (smoothedValue) {
			smoothedValue.set(((long) (smoothedValue.get() * (1.0 - weight) + value * 1000L * weight)));
		}
	}

	public long get() {
		return smoothedValue.get()/1000L;
	}
}
