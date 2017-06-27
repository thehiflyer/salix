package se.fearless.asp.metrics;

import se.fearless.asp.AspNode;

public class NoOpMetrics implements Metrics {
	@Override
	public void onAddEntryBegin() {

	}

	@Override
	public void onAddEntryEnd() {

	}

	@Override
	public void onMoveEntryBegin() {

	}

	@Override
	public void onMoveEntryEnd() {

	}

	@Override
	public void onFindBegin() {

	}

	@Override
	public void onFindEnd() {

	}

	@Override
	public <T> void onSplitAttemptBegin(AspNode<T> node) {

	}

	@Override
	public <T> void onSplitAttemptEnd(AspNode<T> node) {

	}

	@Override
	public void onNodeChildCreation() {

	}
}
