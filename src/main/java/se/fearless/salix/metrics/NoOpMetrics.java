package se.fearless.salix.metrics;

import se.fearless.salix.SalixNode;

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
	public <T> void onNodeAboveThresholdWhenAddingBegin(SalixNode<T> node) {

	}

	@Override
	public <T> void onNodeAboveThresholdWhenAddingEnd(SalixNode<T> node) {

	}

	@Override
	public void onNodeChildCreation() {

	}
}
