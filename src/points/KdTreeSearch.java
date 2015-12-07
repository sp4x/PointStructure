package points;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class KdTreeSearch {

	Queue<KdTree> queue;

	Collection<Predicate<Point>> filters = new LinkedList<>();
	Collection<Predicate<Region>> exclusions = new LinkedList<>();
	


	public KdTreeSearch(KdTree root) {
		super();
		this.queue = new LinkedList<>();
		this.queue.add(root);
	}

	public void forEach(Consumer<Point> consumer) {
		while (!queue.isEmpty()) {
			KdTree tree = queue.poll();
			if (!tree.isNull() && !excluded(tree)) {
				consume(consumer, tree);
				queue.add(tree.getLeft());
				queue.add(tree.getRight());
			}
		}
	}
	
	public Point[] toArray() {
		List<Point> list = new LinkedList<>();
		forEach(p1 -> list.add(p1));
		Point[] array = new Point[list.size()];
		list.toArray(array);
		return array;
	}

	private boolean excluded(KdTree tree) {
		return exclusions.stream().anyMatch(predicate -> predicate.test(tree.region));
	}

	private void consume(Consumer<Point> consumer, KdTree tree) {
		Point currentPoint = tree.getRoot();
		if (filters.stream().allMatch(predicate -> predicate.test(currentPoint))) {
			consumer.accept(currentPoint);
		}
	}

	public KdTreeSearch filter(Predicate<Point> predicate) {
		filters.add(predicate);
		return this;
	}

	public KdTreeSearch exclude(Predicate<Region> predicate) {
		exclusions.add(predicate);
		return this;
	}

	

}
