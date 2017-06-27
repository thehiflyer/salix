package se.fearless.asp.metrics;

import se.fearless.asp.AspNode;

public class CountingMetrics implements Metrics {

	private long adds;
	private long moves;
	private long finds;
	private long aboveThresholdWhenAddingEvents;
	private long childCreations;


	@Override
	public void onAddEntryBegin() {
	}

	@Override
	public void onAddEntryEnd() {
		adds++;
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
	public <T> void onNodeAboveThresholdWhenAddingBegin(AspNode<T> node) {

	}

	@Override
	public <T> void onNodeAboveThresholdWhenAddingEnd(AspNode<T> node) {
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
