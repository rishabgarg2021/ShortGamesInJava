package project1;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

// TODO: Auto-generated Javadoc
/**
 * The Class Ice.
 *
 * @author rishabgarg
 */
/**
 **/
public class Ice extends Block {
	
	/** The first move. */
	//firstmove indicated that block is not pushed by an unit till now.
	private static boolean firstMove = false;
	
	/** The Constant SPEED. */
	//400ms till ice slide to next tile.
	private final static int SPEED = 400;
	
	/** The time. */
	//keep track of time to move ice  
	private static int time = 0;
	
	/** The x direction. */
	//x and y directions calculated according to direction of ice pushed
	private static float xDirection;
	
	/** The y direction. */
	private static float yDirection;
	
	
	/**
	 * Instantiates a new ice.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Ice(float x, float y) {
		super("res/ice.png", x, y);

	}
	
	/* (non-Javadoc)
	 * @see project1.Sprite#render(org.newdawn.slick.Graphics)
	 */
	public void render(Graphics g) {
		super.render(g);
	}

	
	/* (non-Javadoc)
	 * @see project1.Sprite#update(org.newdawn.slick.Input, int)
	 */
	//updates the ice according to firstmove.
	public void update(Input input, int delta) {
		if (firstMove ) {
			ArrayList<Sprite> sprite = World.getsprite();

			for (int i = 0; i < sprite.size(); i++) {
				if ((sprite.get(i).getImage().equals("res/ice.png"))) {

					if (World.collision(sprite.get(i).getX() + xDirection, sprite.get(i).getY() + yDirection) || (Loader
							.isBlocked(sprite.get(i).getX() + xDirection, sprite.get(i).getY() + yDirection))) {

						changeTargetScore(sprite.get(i));
						firstMove = false;
						xDirection = 0.0f;
						yDirection = 0.0f;
						time = 0;
						
					} else if (firstMove
							|| !World.collision(sprite.get(i).getX() + xDirection, sprite.get(i).getY() + yDirection)
									&& (!Loader.isBlocked(sprite.get(i).getX() + xDirection,
											sprite.get(i).getY() + yDirection))) {

						time = time + delta;
						//if time is exceeded we need to push the ice to next tile.
						if (time > SPEED) {

							sprite.get(i).setX(sprite.get(i).getX() + xDirection);
							sprite.get(i).setY(sprite.get(i).getY() + yDirection);
							Switch.openDoor(sprite.get(i).getX() , sprite.get(i).getY() );
							time = 0;

						}
					}
				}
			}
		}
	}

	/**
	 * Move.
	 *
	 * @param x the x
	 * @param y the y
	 * @param direction the direction
	 */
	//it helps to move the ice for first time if we found ice at x and y position , according to given direction.
	public static void move(float x, float y, int direction) {
		float track = 0;
	
		ArrayList<Sprite> sprites = World.getsprite();
		for (int i = 0; i < sprites.size(); i++) {
			if ((sprites.get(i).getImage().equals("res/ice.png")) && sprites.get(i).getX() == x
					&& sprites.get(i).getY() == y) {
				firstMove = false;
				time = 0;
				moveIceFirstTime(sprites.get(i), direction);

			}
		}

	}

	/**
	 * Move ice first time.
	 *
	 * @param sprite the sprite
	 * @param direction the direction
	 */
	//it sets the coordinate of ice and make first move true which helps to slide ice with no push required further more.
	private static void moveIceFirstTime(Sprite sprite, int direction) {

		if (sprite.moveToDest(direction)) {

			xDirection = delta_x;
			yDirection = delta_y;

			if (!World.collision(sprite.getX() + delta_x, sprite.getY() + delta_y)
					&& (!Loader.isBlocked(sprite.getX() + delta_x, sprite.getY() + delta_y))) {

				sprite.setX(sprite.getX() + delta_x);
				sprite.setY(sprite.getY() + delta_y);
				Switch.openDoor(sprite.getX() , sprite.getY() );
				time = 0;
				
				firstMove = true;
			}
		}
	}

	//target score is updated according to conditions check if it was previously occupied by ice or not and thus further reducing or 
	/**
	 * Change target score.
	 *
	 * @param sprite the sprite
	 */
	//increasig the target score.
	public static void changeTargetScore(Sprite sprite) {
		if (World.isTargetOccupied(sprite.getX() - xDirection, sprite.getY() - yDirection)
				&& World.isTargetOccupied(sprite.getX(), sprite.getY())) {

		} else if (World.isTargetOccupied(sprite.getX(), sprite.getY())) {
			Loader.setCurrentTarget(Loader.getCurrentTarget() + 1);
		}

		else if (World.isTargetOccupied(sprite.getX() - xDirection, sprite.getY() - yDirection)
				&& !World.isTargetOccupied(sprite.getX(), sprite.getY())) {
			Loader.setCurrentTarget(Loader.getCurrentTarget() - 1);

		}
	}

	/**
	 * Checks if is first move.
	 *
	 * @return boolean
	 */
	
	public static boolean isFirstMove() {
		return firstMove;
	}

	/**
	 * Sets the first move.
	 *
	 * @param firstMove2 the new first move
	 */
	public static void setFirstMove(boolean firstMove2) {
		// TODO Auto-generated method stub
		Ice.firstMove = firstMove2;
		
	}

}
