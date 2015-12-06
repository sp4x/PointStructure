package points;

public class Point {

	private Double x;
	private Double y;

	public Point(Double x, Double y) {
		this.x = x;
		this.y = y;
	}
	
	
	
	public Point(int x, int y) {
		this.x = Double.valueOf(x);
		this.y = Double.valueOf(y);
	}

	


	public Double getX() {
		return x;
	}



	public void setX(Double x) {
		this.x = x;
	}



	public Double getY() {
		return y;
	}



	public void setY(Double y) {
		this.y = y;
	}



	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point other = (Point) obj;
			return other.x.equals(x) && other.y.equals(y);
		}
		return super.equals(obj);
	}



	public static Double distance(Point p1, Point p2) {
		return Math.sqrt(
	            (p1.getX() - p2.getX()) *  (p1.getX() - p2.getX()) + 
	            (p1.getY() - p2.getY()) *  (p1.getY() - p2.getY())
	        );
	}
	
	@Override
	public String toString() {
		return String.format("(%f,%f)", x, y);
	}

}
