package project1;

import org.newdawn.slick.Input;

// TODO: Auto-generated Javadoc
/**
 * The Class Rogue.
 *
 */
public class Rogue extends Unit {
	
	public static int getStop() {
		return stop;
	}

	public static void setStop(int stop) {
		Rogue.stop = stop;
	}

	/** The Constant INITIAL_DIRECTION. */
	private static final int INITIAL_DIRECTION = DIR_LEFT;
	
	/** The direction. */
	private static int direction = INITIAL_DIRECTION;

	/** The stop. */
	private static int stop = 0;

	/**
	 * Instantiates a new rogue.
	 *
	 * @param x sets the x and y coordinate of rogue while creating object.
	 * @param y the y
	 */
	public Rogue(float x, float y) {
		super("res/rogue.png", x, y);
	}
	//The rogue takes one step left each time the player moves, unless the rogue would
	//walk into a wall; the rogue then reverses direction and moves right until they reach a wall, and
	//so on. 
	
	/* (non-Javadoc)
	 * @see project1.Sprite#update(org.newdawn.slick.Input, int)
	 */
	public void update(Input input, int delta) {
		//rogue is moved if player has pressed key.
		if (Player.keyPressed) {
			
			//rogue changes its position if it gets blocked by blocks.
			if (Loader.isBlocked(super.getX() - App.TILE_SIZE, super.getY())
					|| (Loader.isBlocked(super.getX() - App.TILE_SIZE - App.TILE_SIZE, super.getY())
							&& World.collision(super.getX() - App.TILE_SIZE, super.getY()))
					|| (World.collision(super.getX() - App.TILE_SIZE, super.getY())
							&& World.collision(super.getX() - App.TILE_SIZE - App.TILE_SIZE, super.getY()))) {

				direction = DIR_RIGHT;

				//need to stop rogue from changing direction if it hits wall.
				if (Loader.isBlocked(super.getX() - App.TILE_SIZE, super.getY())) {

					stop++;
				}

			} else if (Loader.isBlocked(super.getX() + App.TILE_SIZE, super.getY())
					|| (Loader.isBlocked(super.getX() + App.TILE_SIZE + App.TILE_SIZE, super.getY())
							&& World.collision(super.getX() + App.TILE_SIZE, super.getY()))

					|| (World.collision(super.getX() + App.TILE_SIZE, super.getY())
							&& World.collision(super.getX() + App.TILE_SIZE + App.TILE_SIZE, super.getY()))) {
				direction = DIR_LEFT;

				if (Loader.isBlocked(super.getX() + App.TILE_SIZE, super.getY())) {

					stop++;
				}
			}
			if (stop != 1) {
				if (moveToDest(direction)) {
					nextMove(direction);
				}
				stop = 0;

			}

			Player.keyPressed = false;
		} 

	}

}
