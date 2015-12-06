package points;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Stopwatch;

public class PerformaceTest {

	private static final int N = 1000000;
	private PointStructure struct;
	private Set<Point> points;
	private Point target;
	private double testDistance;

	@Test
	public void test_struct() {

		points = new HashSet<>();
		Random r = new Random();
		for (int i = 0; i < N; i++) {
			Point point = new Point(r.nextDouble(), r.nextDouble());
			points.add(point);
		}
		struct = new PointStructure(points);

		for (int i = 0; i < 100; i++) {

			target = new Point(r.nextDouble(), r.nextDouble());
			testDistance = r.nextDouble();

			Predicate<? super Point> predicate = p -> Point.distance(p, target) <= testDistance;

			long startTime = System.currentTimeMillis();
			Set<Point> expected = points.stream().filter(predicate).collect(Collectors.toSet());
			System.out.println(System.currentTimeMillis() - startTime);

			startTime = System.currentTimeMillis();
			Set<Point> actual = struct.searchWithin(target, testDistance);
			System.out.println(System.currentTimeMillis() - startTime);

			System.out.println("---");
		}

		
	}

}
