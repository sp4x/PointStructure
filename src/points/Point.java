package points;

public class Point {

	private final Integer x;
	private final Integer y;

	public Point(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point other = (Point) obj;
			return other.x.equals(x) && other.y.equals(y);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return (int) (x * x + y);
	}

	public static Double distance(Point p1, Point p2) {
		double x = p1.getX() - p2.getX();
		double y = p1.getY() - p2.getY();
		Double distance = Math.sqrt(x * x + y * y);
		return distance;
	}

	@Override
	public String toString() {
		return String.format("(%d,%d)", x, y);
	}

}
