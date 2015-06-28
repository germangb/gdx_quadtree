package germangb.quadtree;

public class QTWrap<T> {
	
	T wrap;
	AABB aabb;
	
	public QTWrap (T e, AABB ab) {
		wrap = e;
		aabb = ab;
	}
	
	public QTWrap (T e, int x, int y, int w, int h) {
		this(e, new AABB(x, y, w, h));
	}
}
