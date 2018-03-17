package project1;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

// TODO: Auto-generated Javadoc
/**
 * The Class Stone.
 */
public class Stone extends Block {


	/**
	 * Instantiates a new stone.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Stone(float x, float y) {
		super("res/stone.png", x, y);
	}

	/**
	 * Move.
	 *
	 * @param x the x
	 * @param y the y
	 * @param direction the direction
	 */
	//need to scan to get the stone from sprite array and move it to x and y position based on collision and direction given by player.
	public static void move(float x, float y, int direction) {
		float track = 0;
		ArrayList<Sprite> sprite = World.getsprite();
		
		for (int i = 0; i < sprite.size(); i++) {
			if ((sprite.get(i).getImage().equals("res/stone.png")) && sprite.get(i).getX() == x
					&& sprite.get(i).getY() == y) {

				if (sprite.get(i).moveToDest(direction)) {
					if (!Loader.isDoor(sprite.get(i).getX() + delta_x, sprite.get(i).getY() + delta_y)
							&& !World.collision(sprite.get(i).getX() + delta_x, sprite.get(i).getY() + delta_y)) {
						if (World.isTargetOccupied(sprite.get(i).getX(), sprite.get(i).getY())) {
							track = 1;
						}

						sprite.get(i).setX(sprite.get(i).getX() + delta_x);
						sprite.get(i).setY(sprite.get(i).getY() + delta_y);
						//check if coordinates is occupied by switch and open the door.
						Switch.openDoor(sprite.get(i).getX(), sprite.get(i).getY());
						
						//keeps track of target covered by stone.
						if (track == 1 && World.isTargetOccupied(sprite.get(i).getX(), sprite.get(i).getY())) {

						} else if (World.isTargetOccupied(sprite.get(i).getX(), sprite.get(i).getY())) {
							Loader.setCurrentTarget(Loader.getCurrentTarget() + 1);
						}

						else if (track == 1 && !World.isTargetOccupied(sprite.get(i).getX(), sprite.get(i).getY())) {
							Loader.setCurrentTarget(Loader.getCurrentTarget() - 1);

						}

					}
				}

			}
		}

	}

	/* (non-Javadoc)
	 * @see project1.Sprite#update(org.newdawn.slick.Input, int)
	 */
	public void update(Input input, int delta) {
	}

}
