package germangb.quadtree;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AABB {

	private int x, y;
	private int width, height;
	
	public AABB (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public static boolean overlaps (AABB a, AABB b) {
		return  a.x+a.width > b.x &&
				b.x+b.width > a.x &&
				a.y+a.height > b.y &&
				b.y+b.height > a.y;
	}
	
	public void debugDraw (ShapeRenderer shape) {
		shape.setColor(0, 1, 0, 1);
		shape.rect(x, y, width, height);
	}
	
	public int getX () {
		return x;
	}
	
	public int getY () {
		return y;
	}
	
	public int getWidth () {
		return width;
	}
	
	public int getHeight () {
		return height;
	}
}
