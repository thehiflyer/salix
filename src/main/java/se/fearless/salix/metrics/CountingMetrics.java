package se.fearless.salix.metrics;

import se.fearless.salix.SalixNode;

public class CountingMetrics implements Metrics {

	private long adds;
	private long moves;
	private long finds;
	private long aboveThresholdWhenAddingEvents;
	private long childCreations;
	private SmoothingTimer timeSpentOnAdd = new SmoothingTimer();
	private SmoothingTimer timeSpentOnChildCreation = new SmoothingTimer();

	@Override
	public void onAddEntryBegin() {
		timeSpentOnAdd.start();
	}

	@Override
	public void onAddEntryEnd() {
		adds++;
		timeSpentOnAdd.end();
	}

	@Override
	public void onMoveEntryBegin() {
	}

	@Override
	public void onMoveEntryEnd() {
		moves++;
	}

	@Override
	public void onFindBegin() {
	}

	@Override
	public void onFindEnd() {
		finds++;
	}

	@Override
	public <T> void onNodeAboveThresholdWhenAddingBegin(SalixNode<T> node) {

	}

	@Override
	public <T> void onNodeAboveThresholdWhenAddingEnd(SalixNode<T> node) {
		aboveThresholdWhenAddingEvents++;
	}

	@Override
	public void onNodeChildCreation() {
		childCreations++;
	}

	public long getAdds() {
		return adds;
	}

	public long getMoves() {
		return moves;
	}

	public long getFinds() {
		return finds;
	}

	public long getAboveThresholdWhenAddingEvents() {
		return aboveThresholdWhenAddingEvents;
	}

	public long getChildCreations() {
		return childCreations;
	}

	@Override
	public String toString() {
		return "CountingMetrics{" +
				"adds=" + adds +
				", moves=" + moves +
				", finds=" + finds +
				", aboveThresholdWhenAddingEvents=" + aboveThresholdWhenAddingEvents +
				", childCreations=" + childCreations +
				'}';
	}
}
