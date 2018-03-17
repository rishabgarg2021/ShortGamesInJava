package project1;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class CrackedWall.
 *
 * @author rishabgarg
 */
public class CrackedWall extends Tile {
	
	/** The image. */
	private Image image = null;
	
	/** The Constant TIME. */
	private final static int TIME = 400;
	
	/** The explosion time. */
	private int explosionTime=0;
	
	/** The explosion done. */
	public static boolean explosionDone = false;


	/**
	 * Instantiates a new cracked wall.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public CrackedWall(float x, float y) {
		super("res/cracked_wall.png", x, y);
	}

	/* (non-Javadoc)
	 * @see project1.Sprite#update(org.newdawn.slick.Input, int)
	 */
	
	public void update(Input input, int delta) {

		try {
			//it checks if tnt is being hit by cracked wall and sets the image of crackedwall by explosion image.
			//and set its coordinate to 0,0.
			if (TNT.isExplosion()  && !explosionDone) {
				super.setImage(new Image("res/explosion.png"));
				
				explosionTime += delta;
				if (explosionTime > TIME ) {
					
					explosionDone = true;
					
					super.setX(0);
					super.setY(0);
					

				}
			}
		} catch (SlickException e) {
			e.printStackTrace();

		}

	}

}
