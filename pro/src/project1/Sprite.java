package project1;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class Sprite.
 */
public class Sprite {
	
	/** The img. */
	private String img;
	
	/** The image. */
	private Image image = null;
	
	/** The x. */
	private float x;
	
	/** The y. */
	private float y;
	
	/** The delta x. */
	//it helps to store the x and y direction of an unit moved according to direction.
	protected static float delta_x = 0;
	
	/** The delta y. */
	protected static float delta_y = 0;
	
	/** The Constant DIR_NONE. */
	// Look up enums to find a more elegant solution!
	public static final int DIR_NONE = 0;
	
	/** The Constant DIR_LEFT. */
	public static final int DIR_LEFT = 1;
	
	/** The Constant DIR_RIGHT. */
	public static final int DIR_RIGHT = 2;
	
	/** The Constant DIR_UP. */
	public static final int DIR_UP = 3;
	
	/** The Constant DIR_DOWN. */
	public static final int DIR_DOWN = 4;

	
	/**
	 * Instantiates a new sprite.
	 *
	 * @param image_src the image src
	 * @param x the x
	 * @param y the y
	 */
	public Sprite(String image_src, float x, float y) {
		try {
			img = image_src;
			image = new Image(image_src);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.setX(x);
		this.setY(y);

	}

	/**
	 * Render.
	 *
	 * @param g the g
	 */
	public void render(Graphics g) {
		image.drawCentered(x, y);
	}

	/**
	 * Update.
	 *
	 * @param input the input
	 * @param delta the delta
	 */
	public void update(Input input, int delta) {

	}

	/**
	 * Snap to grid.
	 */
	public void snapToGrid() {

	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return img;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Move to dest.
	 *
	 * @param dir the dir
	 * @return boolean
	 */
	//it takes the direction as argument and calculates the x and y coordinate and return boolean if 
	// object is not blocked by wall.
	public boolean moveToDest(int dir) {
		float speed = 32;

		// Translate the direction to an x and y displacement
		delta_x = 0;
		delta_y = 0;
		switch (dir) {
		case DIR_LEFT:
			delta_x = -speed;

			break;
		case DIR_RIGHT:
			delta_x = speed;

			break;
		case DIR_UP:
			delta_y = -speed;

			break;
		case DIR_DOWN:
			delta_y = speed;

			break;
		}
		if (!Loader.isBlocked(x + delta_x, y + delta_y)) {
			return true;
		}
		return false;
	}

}
