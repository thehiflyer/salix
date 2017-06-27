package se.fearless.asp.metrics;

public class CountingMetrics implements Metrics {

	private long adds;
	private long moves;
	private long finds;


	@Override
	public void onAddBegin() {
	}

	@Override
	public void onAddEnd() {
		adds++;
	}

	@Override
	public void onMoveBegin() {
	}

	@Override
	public void onMoveEnd() {
		moves++;
	}

	@Override
	public void onFindBegin() {
	}

	@Override
	public void onFindEnd() {
		finds++;
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
}
