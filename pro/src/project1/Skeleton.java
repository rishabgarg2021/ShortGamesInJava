package project1;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

// TODO: Auto-generated Javadoc
/**
 * The Class Skeleton.
 */
public class Skeleton extends Unit {
	
	/** The Constant INITIAL_DIRECTION. */
	private static final int INITIAL_DIRECTION = DIR_UP;
	
	/** The direction. */
	private static int direction = INITIAL_DIRECTION;
	
	/** The time to change direction. */
	private static int TIME_TO_CHANGE_DIRECTION = 1000;
	
	/** The time. */
	private static int time = 0;

	/**
	 * Instantiates a new skeleton.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Skeleton(float x, float y) {
		super("res/skull.png", x, y);
	}

	/* (non-Javadoc)
	 * @see project1.Sprite#update(org.newdawn.slick.Input, int)
	 */
	//skeleton moves up and down irrespective of player movement and changes direction based on collision.
	public void update(Input input, int delta) {
		time = time + delta;
		//after 1 sec skeleton changes its position.
		if (time > TIME_TO_CHANGE_DIRECTION) {
			skeletonMove();

		}

	}
	
	/**
	 * Skeleton move.
	 */
	//skeleton changes the direction when it is hit by wall or blocks.
	public void skeletonMove() {
		
		
		if (Loader.isBlocked(super.getX(), super.getY() - App.TILE_SIZE)
				||  World.collision(super.getX(), super.getY() - App.TILE_SIZE)) {
		
			direction = DIR_DOWN;
		} 
		
		else if (Loader.isBlocked(super.getX(), super.getY() + App.TILE_SIZE)
				||  World.collision(super.getX(), super.getY() + App.TILE_SIZE)) {
			direction = DIR_UP;
			
		}

		if (moveToDest(direction)) {

			setX(getX() + delta_x);
			setY(getY() + delta_y);

		}

		time = 0;

	}
	

}
