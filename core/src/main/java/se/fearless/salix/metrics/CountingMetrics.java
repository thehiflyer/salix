package se.fearless.salix.metrics;

import se.fearless.salix.SalixNode;

public class CountingMetrics implements Metrics {

	private long adds;
	private long moves;
	private long finds;
	private long aboveThresholdWhenAddingEvents;
	private long childCreations;
	private SmoothingTimer timeSpentOnAdd = new SmoothingTimer();
	private NestedSmoothingTimer<SalixNode> timeSpentOnAboveThreshold = new NestedSmoothingTimer<>();
 	private SmoothingTimer timeSpentOnChildCreation = new SmoothingTimer();

	@Override
	public void onAddEntryBegin() {
		timeSpentOnAdd.start();
	}

	@Override
	public void onAddEntryEnd() {
		adds++;
		timeSpentOnAdd.stop();
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
		timeSpentOnAboveThreshold.start(node);
	}

	@Override
	public <T> void onNodeAboveThresholdWhenAddingEnd(SalixNode<T> node) {
		timeSpentOnAboveThreshold.stop(node);
		aboveThresholdWhenAddingEvents++;
	}

	@Override
	public void onNodeChildCreationBegin() {
		timeSpentOnChildCreation.start();
	}

	@Override
	public void onNodeChildCreationEnd() {
		timeSpentOnChildCreation.stop();
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
				", timeSpentOnAdd=" + timeSpentOnAdd +
				", timeSpentOnAboveThreshold=" + timeSpentOnAboveThreshold +
				", timeSpentOnChildCreation=" + timeSpentOnChildCreation +
				'}';
	}
}
