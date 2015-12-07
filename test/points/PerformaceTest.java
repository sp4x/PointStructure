package points;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

public class PerformaceTest {

	private static final int NUMBER_OF_SEARCH = 10;
	private static final int MAX_INT = Integer.MAX_VALUE;
	private static final int NUMBER_OF_POINTS = 1000000;

	private Random generator;
	private PointStructure points;
	private Point[] pointArray;

	@Before
	public void setUp() {
		generator = new Random(512);
		points = new PointStructure();
		for (int i = 0; i < NUMBER_OF_POINTS; i++) {
			points.add(nextPoint());
		}
		pointArray = points.tree.stream().toArray();
	}

	@Test
	public void test_rebuilding_tree() {
		points.rebuildTree();
		for (int i = 0; i < NUMBER_OF_SEARCH; i++) {
			Point target = nextPoint();
			Integer testDistance = nextInt();
			Collection<Point> accumulator = new ArrayList<>(NUMBER_OF_POINTS);
			points.treeSearch(target, testDistance).forEach(p -> accumulator.add(p));
		}
	}
	
	@Test
	public void test_without_rebuilding_tree() {
		for (int i = 0; i < NUMBER_OF_SEARCH; i++) {
			Point target = nextPoint();
			Integer testDistance = nextInt();
			Collection<Point> accumulator = new ArrayList<>(NUMBER_OF_POINTS);
			points.treeSearch(target, testDistance).forEach(p -> accumulator.add(p));
		}
	}
	
	@Test
	public void test_linear_search() {
		for (int i = 0; i < NUMBER_OF_SEARCH; i++) {
			Point target = nextPoint();
			Integer testDistance = nextInt();
			Collection<Point> accumulator = new ArrayList<>(NUMBER_OF_POINTS);
			Predicate<Point> predicate = p -> !p.equals(target) && Point.distance(p, target) <= (double) testDistance;
			Arrays.stream(pointArray).filter(predicate).forEach(p -> accumulator.add(p));
		}
	}

	private Point nextPoint() {
		return new Point(nextInt(), nextInt());
	}

	private int nextInt() {
		return generator.nextInt(MAX_INT);
	}

}
