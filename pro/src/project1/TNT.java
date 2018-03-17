package project1;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

// TODO: Auto-generated Javadoc
/**
 * The Class TNT.
 */
public class TNT extends Block {
	
	/** The explosion. */
	private static boolean explosion = false;
	
	/**
	 * Checks if is explosion.
	 *
	 * @return true, if is explosion
	 */
	public static boolean isExplosion() {
		return explosion;
	}

	/**
	 * Sets the explosion.
	 *
	 * @param explosion the new explosion
	 */
	public void setExplosion(boolean explosion) {
		this.explosion = explosion;
	}

	/**
	 * Instantiates a new tnt.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public TNT(float x, float y) {
		super("res/tnt.png", x, y);
	}
	
	/* (non-Javadoc)
	 * @see project1.Sprite#update(org.newdawn.slick.Input, int)
	 */
	public void update(Input input, int delta) {
	
	}

	/**
	 * Move.
	 *
	 * @param x the x
	 * @param y the y
	 * @param direction the direction
	 */
	public static void move(float x, float y, int direction) {
		float track = 0;
		ArrayList<Sprite> sprite = World.getsprite();
		for (int i = 0; i < sprite.size(); i++) {
			if ((sprite.get(i).getImage().equals("res/tnt.png")) && sprite.get(i).getX() == x
					&& sprite.get(i).getY() == y) {
				if (sprite.get(i).moveToDest(direction)) {
					
					//if we have a crack wall on next move of tnt we need to have explosion true which is checked when 
					//crack wall update is used.
					if (explosion
							|| Loader.isCrackWall(sprite.get(i).getX() + delta_x, sprite.get(i).getY() + delta_y)) {
						sprite.get(i).setX(0);
						sprite.get(i).setY(0);
						explosion = true;

					} 
					//makes it move if it is not blocked by other blocks or wall.
					else if (!World.collision(sprite.get(i).getX() + delta_x, sprite.get(i).getY() + delta_y)) {

						sprite.get(i).setX(sprite.get(i).getX() + delta_x);
						sprite.get(i).setY(sprite.get(i).getY() + delta_y);
						
						

					}
				}

			}

		}
		
	}
}
