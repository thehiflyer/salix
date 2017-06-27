package se.fearless.asp.metrics;

import se.fearless.asp.AspNode;

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
