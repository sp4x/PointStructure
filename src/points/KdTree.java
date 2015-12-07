package points;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class KdTree {

	private Point root;
	private KdTree right;
	private KdTree left;

	final Region region;
	final int depth;
	private Comparator<? super Point> pointComparator;

	public KdTree(Point[] array) {
		this(array, 0, array.length, 0, new Region());
	}

	public KdTree() {
		this(new Region(), 0);
	}

	private KdTree(Region region, int depth) {
		this.region = region;
		this.depth = depth;
		this.pointComparator = (a, b) -> axis(a).compareTo(axis(b));
	}

	private KdTree(Point[] array, int startInclusive, int endExclusive, int depth, Region region) {
		this(region, depth);
		rebuild(array, startInclusive, endExclusive, depth);
	}

	private void rebuild(Point[] array, int startInclusive, int endExclusive, int depth) {
		if (endExclusive > startInclusive) {
			Arrays.sort(array, startInclusive, endExclusive, pointComparator);
			int median = median(startInclusive, endExclusive);
			root = array[median];
			Region[] subregions = splitRegion();
			left = new KdTree(array, startInclusive, median, depth + 1, subregions[0]);
			right = new KdTree(array, median + 1, endExclusive, depth + 1, subregions[1]);
		} else {
			root = null;
		}
	}

	public boolean contains(Point p) {
		if (isNull()) {
			return false;
		} else if (root.equals(p)) {
			return true;
		} else if (axis(p) >= axis(root)) {
			return getRight().contains(p);
		} else {
			return getLeft().contains(p);
		}
	}

	public boolean add(Point p) {
		if (isNull()) {
			rebuild(new Point[]{p}, 0, 1, depth);
			return true;
		} else if (root.equals(p)) {
			return false;
		} else if (axis(p) >= axis(root)) {
			return getRight().add(p);
		} else {
			return getLeft().add(p);
		}
	}
	
	public int count() {
		if (isNull()) {
			return 0;
		}
		return 1 + getLeft().count() + getRight().count();
	}
	

	public boolean remove(Point point) {
		if (isNull()) {
			return false;
		} else if (root.equals(point)) {
			removeElementAndRebuild(point);
			return true;
		} else if (axis(point) >= axis(root)) {
			return getRight().remove(point);
		} else {
			return getLeft().remove(point);
		}
	}

	private void removeElementAndRebuild(Point point) {
		Point[] array = stream().filter(p -> !p.equals(point)).toArray();
		rebuild(array, 0, array.length, depth);
	}

	private Region[] splitRegion() {
		return depth % 2 == 0 ? region.splitOnXAxis(root.getX()) : region.splitOnYAxis(root.getY());
	}

	Integer axis(Point p) {
		return depth % 2 == 0 ? p.getX() : p.getY();
	}

	public boolean isNull() {
		return root == null;
	}

	static int median(int startInclusive, int endExclusive) {
		return startInclusive + ((endExclusive - startInclusive) / 2);
	}

	public Point getRoot() {
		return root;
	}

	public KdTree getLeft() {
		return left;
	}

	public KdTree getRight() {
		return right;
	}

	public boolean isLeaf() {
		return getLeft().isNull() && getRight().isNull();
	}

	public int getDepth() {
		return isNull() ? depth - 1 : Math.max(getLeft().getDepth(), getRight().getDepth());
	}

	public KdTreeSearch stream() {
		return new KdTreeSearch(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Queue<KdTree> queue = new LinkedList<>();
		queue.add(this);
		toString(builder, queue);
		return builder.toString().trim();
	}

	private static void toString(StringBuilder builder, Queue<KdTree> queue) {
		int currentDepth = -1;
		while (!queue.isEmpty()) {
			KdTree toPrint = queue.poll();
			if (!toPrint.isNull()) {
				if (toPrint.depth != currentDepth) {
					currentDepth = toPrint.depth;
					builder.append(String.format("\n%s: ", currentDepth));
				}
				builder.append(toPrint.root);
				builder.append(toPrint.region);
				queue.add(toPrint.left);
				queue.add(toPrint.right);
			}
		}
	}

}
