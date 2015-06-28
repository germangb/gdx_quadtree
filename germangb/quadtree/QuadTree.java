package germangb.quadtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class QuadTree<T> {

	private static final int MAX_ENTITIES_NODE = 4;
	private static final int MAX_LEVELS = 4;
	
	private int level;
	private AABB aabb;
	private List<QTWrap<T>> entities;
	private QuadTree<T>[] child;
	
	private int x, y;
	private int width, height;
	
	private QuadTree (int level, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.level = level;
		this.entities = new ArrayList<QTWrap<T>>();
		this.child = null;
		this.aabb = new AABB(x, y, width, height);
	}
	
	public QuadTree (int x, int y, int width, int height) {
		this(0, x, y, width, height);
	}
	
	public void clear () {
		entities.clear();
		child = null;
	}
	
	@SuppressWarnings("unchecked")
	public void insert (T thing, int x, int y, int width, int height) {
		AABB test = new AABB(x, y, width, height);
		entities.add(new QTWrap<T>(thing, test));
		if (child != null || entities.size() >= MAX_ENTITIES_NODE && level < MAX_LEVELS) {
			if (child == null) {
				child = new QuadTree[4];
				for (int i = 0; i < 4; ++i) {
					int splitWidth = this.width/2;
					int splitHeight = this.height/2;
					int cx = this.x+splitWidth*(i%2);
					int cy = this.y+splitHeight*(i/2);
					if (i%2==1) splitWidth += this.width%2;
					if (i/2==1) splitHeight += this.height%2;
					child[i] = new QuadTree<T>(level+1, cx, cy, splitWidth, splitHeight);
				}
			}
			
			for (QTWrap<T> e : entities) {
				for (int i = 0; i < 4; ++i) {
					if (AABB.overlaps(e.aabb, child[i].aabb)) {
						child[i].insert(thing, x, y, width, height);
					}
				}
			}
			entities.clear();
		}
	}
	
	public Set<T> get (Set<T> out, int x, int y, int width, int height) {
		AABB test = new AABB(x, y, width, height);
		if (child != null) {
			for (int i = 0; i < 4; ++i) {
				if (AABB.overlaps(child[i].aabb, test)) {
					child[i].get(out, x, y, width, height);
				}
			}
		} else {
			for (QTWrap<T> e : entities) {
				if (AABB.overlaps(test, e.aabb)) {
					out.add(e.wrap);
				}
			}
		}
		return out;
	}
	
	public void debugDraw (ShapeRenderer shape) {
		shape.setColor(1, 0, 1, 1);
		shape.rect(x, y, width, height);
		if (child != null) {
			for (int i = 0; i < 4; ++i) {
				child[i].debugDraw(shape);
			}
		} else {
			for (QTWrap<T> q : entities) {
				q.aabb.debugDraw(shape);
			}
		}
	}
}
