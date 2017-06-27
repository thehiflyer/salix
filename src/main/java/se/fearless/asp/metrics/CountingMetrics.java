package se.fearless.asp.metrics;

import se.fearless.asp.AspNode;

public class CountingMetrics implements Metrics {

	private long adds;
	private long moves;
	private long finds;
	private long splits;
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
	public <T> void onSplitAttemptBegin(AspNode<T> node) {

	}

	@Override
	public <T> void onSplitAttemptEnd(AspNode<T> node) {
		splits++;
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

	public long getSplits() {
		return splits;
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
				", splits=" + splits +
				", childCreations=" + childCreations +
				'}';
	}
}
