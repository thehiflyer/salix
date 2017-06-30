package se.fearless.salix.metrics;

import se.fearless.salix.SalixNode;

public interface Metrics {
	void onAddEntryBegin();
	void onAddEntryEnd();

	void onMoveEntryBegin();
	void onMoveEntryEnd();

	void onFindBegin();
	void onFindEnd();

	<T> void onNodeAboveThresholdWhenAddingBegin(SalixNode<T> node);
	<T> void onNodeAboveThresholdWhenAddingEnd(SalixNode<T> node);

	void onNodeChildCreation();
}
