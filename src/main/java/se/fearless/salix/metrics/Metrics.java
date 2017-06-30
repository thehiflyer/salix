package se.fearless.salix.metrics;

import se.fearless.salix.AspNode;

public interface Metrics {
	void onAddEntryBegin();
	void onAddEntryEnd();

	void onMoveEntryBegin();
	void onMoveEntryEnd();

	void onFindBegin();
	void onFindEnd();

	<T> void onNodeAboveThresholdWhenAddingBegin(AspNode<T> node);
	<T> void onNodeAboveThresholdWhenAddingEnd(AspNode<T> node);

	void onNodeChildCreation();
}
