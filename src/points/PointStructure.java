package points;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class PointStructure {

	private static final Comparator<Point> byX = (Point a, Point b) -> a.getX().compareTo(b.getX());
	private static final Comparator<Point> byY = (Point a, Point b) -> a.getY().compareTo(b.getY());

	private Point root;
	private PointStructure right;
	private PointStructure left;

	private Function<Point, Double> axis;
	private int depth;

	public PointStructure() {
		this(0);
	}

	private PointStructure(int depth) {
		super();
		this.depth = depth;
		this.axis = depth % 2 == 0 ? p -> p.getX() : p -> p.getY();
	}

	public boolean add(Point point) {
		if (root == null) {
			root = point;
			return true;
		} else if (root.equals(point)) {
			return false;
		} else if (axis.apply(point) >= axis.apply(root)) {
			return getRight().add(point);
		} else {
			return getLeft().add(point);
		}
	}

	public Object getRoot() {
		return root;
	}

	public PointStructure getLeft() {
		if (left == null) {
			left = new PointStructure(depth + 1);
		}
		return left;
	}

	public PointStructure getRight() {
		if (right == null) {
			right = new PointStructure(depth + 1);
		}
		return right;
	}

	public boolean contains(Point point) {
		if (root == null) {
			return false;
		}
		if (root.equals(point)) {
			return true;
		}
		boolean isLeft = getLeft().contains(point);
		boolean isRight = getRight().contains(point);
		return isLeft || isRight;
	}

	public Set<Point> searchWithin(Point point, double distance) {
		Set<Point> result = new HashSet<>();
		if (root == null) {
			return result;
		}
		if (!root.equals(point) && Point.distance(point, root) <= distance) {
			result.add(root);
		}
		boolean excludeRight = axis.apply(point) + distance < axis.apply(root);
		boolean excludeLeft = axis.apply(point) - distance > axis.apply(root);
		if (!excludeRight) {
			result.addAll(getRight().searchWithin(point, distance));
		}
		if (!excludeLeft) {
			result.addAll(getLeft().searchWithin(point, distance));
		}
		return result;
	}

	public Stream<Point> within(Point point, double distance) {
		return searchWithin(point, distance).stream();
	}

	public PointStructure(Set<Point> points) {
		this(points.toArray(), 0);
	}

	private PointStructure(Object[] array, int depth) {
		this(depth);
		if (array.length == 0) {
			return;
		}
		Comparator<? super Point> comparator = (a, b) -> axis.apply(a).compareTo(axis.apply(b));
		Object[] sorted = Arrays.stream(array).map(o -> (Point) o).sorted(comparator).toArray();
		int median = sorted.length / 2;
		root = (Point) array[median];
		if (median > 0) {
			left = new PointStructure(Arrays.copyOfRange(sorted, 0, median - 1), depth + 1);
			right = new PointStructure(Arrays.copyOfRange(sorted, median + 1, sorted.length), depth + 1);
		}
	}

}
