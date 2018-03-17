package project1;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Unit.
 */
public abstract class Unit extends Sprite {




	/**
	 * Instantiates a new unit.
	 *
	 * @param image_src the image src
	 * @param x the x
	 * @param y the y
	 */
	public Unit(String image_src, float x, float y) {
		super(image_src, x, y);

	}

	/**
	 * Next move.
	 *
	 * @param direction the direction
	 */
	public void nextMove(int direction) {
		if (moveToDest(direction)) {
			if (!Loader.isDoor(super.getX() + delta_x, super.getY() + delta_y)) {
				
				if (!World.collision(super.getX() + delta_x, super.getY() + delta_y)
						&& !Loader.isCrackWall(super.getX() + delta_x, super.getY() + delta_y)) {

					setX(getX() + delta_x);
					setY(getY() + delta_y);
					if (Player.keyPressed) {
						Mage.move(getX() - delta_x, getY() - delta_y);
					}
				
					//if next to next positions are not blocked of unit by any wall or blocks move the unit to next coordinate.
				} else if (!World.collision(super.getX() + delta_x + delta_x, super.getY() + delta_y + delta_y)
						&& !Loader.isBlocked(super.getX() + delta_x + delta_x, super.getY() + delta_y + delta_y)
						&& !Loader.isCrackWall(super.getX() + delta_x, super.getY() + delta_y)) {

					setX(getX() + delta_x);
					setY(getY() + delta_y);
					String image;
					//gets image which was moved by unit and call Blocks class to move blocks.
					image = World.getBlockImg();

					if (Player.keyPressed) {
						//mage is moved on each key press of player.
						Mage.move(getX() - delta_x, getY() - delta_y);
					}

					if (image.equals("res/stone.png")) {
						Stone.move(super.getX(), super.getY(), direction);
					}
					if (image.equals("res/ice.png")) {
						Ice.move(super.getX(), super.getY(), direction);
					}
					if (image.equals("res/tnt.png")) {
						TNT.move(super.getX(), super.getY(), direction);
					}

				}
			}

		}
	}

}
