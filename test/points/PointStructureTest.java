package points;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class PointStructureTest {
	
	@Test
	public void new_struct_has_null_root() {
		PointStructure struct = new PointStructure();
		assertNull(struct.getRoot());
	}

	@Test
	public void the_first_point_is_added_to_the_root() {
		PointStructure struct = new PointStructure();
		Point point = new Point(1,1);
		struct.add(point);
		assertEquals(point, struct.getRoot());
	}
	
	@Test
	public void cannot_add_two_equal_points() {
		PointStructure struct = new PointStructure();
		Point point = new Point(1,1);
		assertTrue(struct.add(point));
		assertFalse(struct.add(point));
	}
	
	@Test
	public void at_depth_1_higher_x_are_added_to_the_rigth() {
		PointStructure struct = new PointStructure();
		struct.add(new Point(1,1));
		struct.add(new Point(2,1));
		assertEquals(new Point(2,1), struct.getRight().getRoot());
	}
	
	@Test
	public void at_depth_1_lower_x_are_added_to_the_left() {
		PointStructure struct = new PointStructure();
		struct.add(new Point(1,1));
		struct.add(new Point(0,1));
		assertEquals(new Point(0,1), struct.getLeft().getRoot());
	}
	
	@Test
	public void at_depth_2_higher_y_are_added_to_the_rigth() {
		PointStructure struct = new PointStructure();
		struct.add(new Point(1,1));
		struct.add(new Point(2,1));
		struct.add(new Point(1,2));
		assertEquals(new Point(1,2), struct.getRight().getRight().getRoot());
	}
	
	@Test
	public void should_find_an_existing_point() {
		PointStructure struct = new PointStructure();
		struct.add(new Point(1,1));
		struct.add(new Point(2,1));
		struct.add(new Point(1,2));
		assertTrue(struct.contains(new Point(1,2)));
	}
	
	@Test
	public void should_find_a_point_within_a_distance() {
		Set<Point> points = new HashSet<>();
		points.add(new Point(7,2));
		points.add(new Point(5,4));
		points.add(new Point(9,6));
		points.add(new Point(2,3));
		points.add(new Point(4,7));
		points.add(new Point(8,1));
		PointStructure struct = new PointStructure(points);
		
		Point point = new Point(5,4);
		double testDistance = 3;
		Set<Point> within = struct.searchWithin(point, testDistance);
		Predicate<? super Point> predicate = p -> Point.distance(p, point) <= testDistance;
		Set<Point> expected = points.stream().filter(predicate).collect(Collectors.toSet());
		boolean allMatch = within.stream().allMatch(p -> Point.distance(p, point) <= testDistance);
		long excluded = within.stream().filter(p -> !expected.contains(p)).count();
		assertTrue(allMatch);
		assertEquals(0, excluded);
	}
	
	

}
