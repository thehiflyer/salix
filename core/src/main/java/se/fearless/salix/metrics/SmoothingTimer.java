package se.fearless.salix.metrics;

public class SmoothingTimer {
	private final ValueSmoothingMeter meter = new ValueSmoothingMeter(0.001);
	private long startTime;

	public void start() {
		startTime = System.nanoTime();
	}

	public void stop() {
		meter.add(System.nanoTime() - startTime);
	}

	@Override
	public String toString() {
		return "" + meter.get();
	}
}
