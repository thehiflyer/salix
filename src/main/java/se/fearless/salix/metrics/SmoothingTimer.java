package se.fearless.salix.metrics;

public class SmoothingTimer {
	private final ValueSmoothingMeter meter = new ValueSmoothingMeter(0.001);
	private long startTime;

	public void start() {
		startTime = System.nanoTime();
	}

	public void end() {
		meter.add(System.nanoTime() - startTime);
	}
}
