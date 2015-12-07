# PointStructure

I've been playing around with Java Streams and [K-d-trees](https://en.wikipedia.org/wiki/K-d_tree)

Points are stored in a 2-d-tree (so duplicate points are not allowed)

Calling the method `stream()` on a `KdTree` instance will return a Stream-like object, `KdTreeSearch`. It does not implement the `Stream` interface but supports the `forEach` and `filter`. 

An additional method, `exclude`, allows pruning entire subtrees based on their boundaries. This is used to optimize the search of points within distance

    search.exclude(r -> r.maxX < target.getX() - (double) distance);
    search.exclude(r -> r.minX > target.getX() + (double) distance);
    search.exclude(r -> r.maxY < target.getY() - (double) distance);
    search.exclude(r -> r.minY > target.getY() + (double) distance);

Although a simple linear search is always faster on my tests

### Find points within a distance of another point

The method `PointStructure.treeSearch` will instantiate a `KdTreeSearch` and setup the correct filters and exclusions.
You can iterate on the search results using the `forEach` method.

### Assumptions

* `treeSeach` accepts as input even points not included in `PointStructure`
* `treeSearch` will not return the input point itself
