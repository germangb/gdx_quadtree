package germangb.test;

import java.util.HashSet;
import java.util.Set;

import germangb.quadtree.QuadTree;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class GdxLab extends ApplicationAdapter {
	
	QuadTree<Integer> tree;
	ShapeRenderer shape;
	
	@Override
	public void create () {
		shape = new ShapeRenderer();
		tree = new QuadTree<Integer>(8, 8, 551, 512);
	}
	
	private void rand () {
		tree.clear();
		for (int i = 0; i < 64; ++i) {
			int rx = MathUtils.random(40, 500);
			int ry = MathUtils.random(40, 400);
			tree.insert(i, rx, ry, 8+MathUtils.random(4, 8), 16+MathUtils.random(4, 8));
		}
		
		Set<Integer> get = new HashSet<Integer>();
		tree.get(get, 100, 100, 100, 200);
		System.out.println(get.size()+" things in the yellow rectangle");
	}

	@Override
	public void render () {
		if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.R)) rand();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.setAutoShapeType(true);
		shape.begin();
		tree.debugDraw(shape);
		shape.setColor(1, 1, 0, 1);
		shape.rect(100, 100, 100, 200);
		shape.end();
	}
	
	@Override
	public void dispose () {
		shape.dispose();
	}
}
