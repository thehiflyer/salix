package se.fearless.asp;

public class Entry<T> {
	private final T value;
	private final double x;
	private final double y;
	private final double z;
	private final double radius;

	public Entry(T value, double x, double y, double z, double radius) {
		this.value = value;
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
	}

	public T getValue() {
		return value;
	}
}
