package se.fearless.asp.metrics;

public interface Metrics {
	void onAddBegin();
	void onAddEnd();

	void onMoveBegin();
	void onMoveEnd();

	void onFindBegin();
	void onFindEnd();
}
