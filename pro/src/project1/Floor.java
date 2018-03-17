package project1;

import org.newdawn.slick.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class Floor.
 *
 * @author rishabgarg
 */
public class Floor extends Tile {
	
	/**
	 * Instantiates a new floor.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Floor(float x, float y) {
		super("res/floor.png", x, y);
	}
	
	/* (non-Javadoc)
	 * @see project1.Sprite#render(org.newdawn.slick.Graphics)
	 */
	public void render(Graphics g) {
		super.render(g);
	}

}
