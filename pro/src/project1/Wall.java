package project1;

import org.newdawn.slick.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class Wall.
 */
public class Wall extends Tile {
	
	/**
	 * Instantiates a new wall.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Wall(float x, float y) {
		super("res/wall.png", x, y);
	}
	
	/* (non-Javadoc)
	 * @see project1.Sprite#render(org.newdawn.slick.Graphics)
	 */
	public void render(Graphics g) {
		super.render(g);
	}

}
