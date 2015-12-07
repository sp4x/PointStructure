package points;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

public class KdTreeTest {

	@Test
	public void should_calculate_median_between_indexes() {
		assertEquals(3, KdTree.median(0, 6));
		assertEquals(5, KdTree.median(4, 6));
		assertEquals(4, KdTree.median(4, 5));
	}
	
	@Test
	public void should_build_a_null_tree() {
		Point[] array = new Point[0];
		KdTree kdTree = new KdTree(array);
		assertTrue(kdTree.isNull());
	}
	
	@Test
	public void should_build_a_leaf_tree() {
		Point[] array = {new Point(0, 0)};
		KdTree kdTree = new KdTree(array);
		assertTrue(kdTree.isLeaf());
	}
	
	@Test
	public void should_have_depth_2() {
		Point[] array = {
				new Point(7,2),
				new Point(5,4),
				new Point(9,6),
				new Point(2,3),
				new Point(4,7),
				new Point(8,1)
		};
		KdTree kdTree = new KdTree(array);
		assertEquals(2, kdTree.getDepth());
	}
	
	@Test
	public void median_should_be_root() {
		Point[] array = {
				new Point(7,2),
				new Point(5,4),
				new Point(9,6),
				new Point(2,3),
				new Point(4,7),
				new Point(8,1)
		};
		KdTree kdTree = new KdTree(array);
		Arrays.sort(array, (a, b) -> a.getX().compareTo(b.getX()));
		int median = KdTree.median(0, array.length);
		assertEquals(array[median], kdTree.getRoot());
	}
	
	@Test
	public void should_represent_the_tree_structure() {
		Point[] array = {
				new Point(7,2),
				new Point(5,4),
				new Point(9,6),
				new Point(2,3),
				new Point(4,7),
				new Point(8,1)
		};
		KdTree kdTree = new KdTree(array);
		String expected = "0: (7,2){,}{,}\n"
				+ "1: (5,4){,7}{,}(9,6){7,}{,}\n"
				+ "2: (2,3){,7}{,4}(4,7){,7}{4,}(8,1){7,}{,6}";
		assertEquals(expected, kdTree.toString());
	}
	
	@Test
	public void should_add_a_new_point() {
		Point[] array = {
				new Point(7,2),
				new Point(5,4),
				new Point(9,6),
				new Point(2,3),
				new Point(4,7),
				new Point(8,1)
		};
		KdTree kdTree = new KdTree(array);
		kdTree.add(new Point(9, 7));
		String expected = "0: (7,2){,}{,}\n"
				+ "1: (5,4){,7}{,}(9,6){7,}{,}\n"
				+ "2: (2,3){,7}{,4}(4,7){,7}{4,}(8,1){7,}{,6}(9,7){7,}{6,}";
		assertEquals(expected, kdTree.toString());
	}
	
	@Test
	public void should_count_the_points() {
		Point[] array = {
				new Point(7,2),
				new Point(5,4),
				new Point(9,6),
				new Point(2,3),
				new Point(4,7),
				new Point(8,1)
		};
		KdTree kdTree = new KdTree(array);
		assertEquals(array.length, kdTree.count());
	}
	
	@Test
	public void should_remove_a_point() {
		Point[] array = {
				new Point(7,2),
				new Point(5,4),
				new Point(9,6),
				new Point(2,3),
				new Point(4,7),
				new Point(8,1)
		};
		KdTree kdTree = new KdTree(array);
		kdTree.remove(new Point(5, 4));
		String expected = "0: (7,2){,}{,}\n"
				+ "1: (4,7){,7}{,}(9,6){7,}{,}\n"
				+ "2: (2,3){,7}{,7}(8,1){7,}{,6}";
		assertEquals(expected, kdTree.toString());
	}
	
	@Test
	public void should_iterate_between_all_nodes() {
		Point[] array = {
				new Point(7,2),
				new Point(5,4),
				new Point(9,6),
				new Point(2,3),
				new Point(4,7),
				new Point(8,1)
		};
		KdTree kdTree = new KdTree(array);
		Set<Point> visited = new HashSet<>();
		kdTree.stream().forEach(p -> visited.add(p));
		assertTrue(visited.containsAll(Arrays.asList(array)));
	}
	
	@Test
	public void should_extract_array() {
		Point[] array = {
				new Point(7,2),
				new Point(5,4),
				new Point(9,6),
				new Point(2,3),
				new Point(4,7),
				new Point(8,1)
		};
		KdTree kdTree = new KdTree(array);
		String expected = "[(7,2), (5,4), (9,6), (2,3), (4,7), (8,1)]";
		assertEquals(expected, Arrays.toString(kdTree.stream().toArray()));
	}
	
	@Test
	public void should_match_only_one_node() {
		Point[] array = {
				new Point(7,2),
				new Point(5,4),
				new Point(9,6),
				new Point(2,3),
				new Point(4,7),
				new Point(8,1)
		};
		KdTree kdTree = new KdTree(array);
		Collection<Point> visited = new LinkedList<>();
		kdTree.stream().filter(p -> p.equals(new Point(8, 1))).forEach(p -> visited.add(p));
		assertEquals("[(8,1)]", visited.toString());
	}
	
	@Test
	public void should_exclude_two_points() {
		Point[] array = {
				new Point(7,2),
				new Point(5,4),
				new Point(9,6),
				new Point(2,3),
				new Point(4,7),
				new Point(8,1)
		};
		KdTree kdTree = new KdTree(array);
		Collection<Point> visited = new LinkedList<>();
		kdTree.stream().exclude(r -> r.maxX <= 7).forEach(p -> visited.add(p));
		assertEquals("[(7,2), (9,6), (8,1)]", visited.toString());
	}

		

}
