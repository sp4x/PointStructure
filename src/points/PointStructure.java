package points;

import java.util.Arrays;
import java.util.stream.Stream;

public class PointStructure {

	KdTree tree = new KdTree();

	public boolean add(int x, int y) {
		return add(new Point(x, y));
	}

	public boolean remove(int x, int y) {
		return remove(new Point(x, y));
	}

	public boolean move(int x1, int y1, int x2, int y2) {
		return move(new Point(x1, y1), new Point(x2, y2));
	}

	public boolean add(Point p) {
		return tree.add(p);
	}

	public boolean remove(Point p) {
		return tree.remove(p);
	}

	public boolean move(Point src, Point dest) {
		return remove(src) && add(dest);
	}

	public int count() {
		return tree.count();
	}

	public Stream<Point> linearSearch(Point target, Integer distance) {
		Point[] array = tree.stream().toArray();
		return Arrays.stream(array).filter(p -> !p.equals(target) && Point.distance(p, target) <= distance);
	}

	public KdTreeSearch treeSearch(Point target, Integer distance) {
		KdTreeSearch search = tree.stream();
		search.filter(p -> !p.equals(target) && Point.distance(target, p) <= distance);
		search.exclude(r -> r.maxX < target.getX() - (double) distance);
		search.exclude(r -> r.minX > target.getX() + (double) distance);
		search.exclude(r -> r.maxY < target.getY() - (double) distance);
		search.exclude(r -> r.minY > target.getY() + (double) distance);
		return search;
	}

	public void rebuildTree() {
		Point[] array = tree.stream().toArray();
		tree = new KdTree(array);
	}

}
