package project1;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class Mage.
 *
 * @author rishabgarg
 */
public class Mage extends Unit {

	/** The dist X. */
	private static float distX;

	/** The dist Y. */
	private static float distY;

	/** The sign. */
	private static int sign;

	/**
	 * Instantiates a new mage.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public Mage(float x, float y) {
		super("res/mage.png", x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see project1.Sprite#render(org.newdawn.slick.Graphics)
	 */
	public void render(Graphics g) {
		super.render(g);
	}

	/**
	 * Move.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public static void move(float x, float y) {
		float track = 0;

		ArrayList<Sprite> sprite = World.getsprite();
		// it scans the location of mage and it works according to algorithm which
		// states
		// 1: Let distx and disty be the x and y distances respectively between the mage
		// and the player in pixels.
		// 2: Let sgn(x) be −1 if x < 0, and 1 otherwise.
		// 3: If |distx| > |disty| and the movement is possible, move T ILE SIZE ∗
		// sgn(distx) pixels in the x direction.
		// 4: Otherwise, if the movement is possible, move T ILE SIZE ∗ sgn(disty)
		// pixels in the y direction
		for (int i = 0; i < sprite.size(); i++) {
			if ((sprite.get(i).getImage().equals("res/mage.png"))) {

				Player.keyPressed = false;

				distX = x - sprite.get(i).getX();
				distY = y - sprite.get(i).getY();

				if (Math.abs(distX) > Math.abs(distY)) {

					if (!Loader.isBlocked(sprite.get(i).getX() + App.TILE_SIZE * sign(distX), sprite.get(i).getY())) {

						sprite.get(i).setX(sprite.get(i).getX() + App.TILE_SIZE * sign(distX));

					}
				} else {

					if (!Loader.isBlocked(sprite.get(i).getX(), sprite.get(i).getY() + App.TILE_SIZE * sign(distY))) {

						
						sprite.get(i).setY(sprite.get(i).getY() + App.TILE_SIZE * sign(distY));

					}

				}

			}
		}

	}

	/**
	 * Sign.
	 *
	 * @param x
	 *            the x
	 * @return 1 or -1.
	 */
	// it return the 1 or -1 according to sign.
	public static int sign(float x) {
		if (x >= 0) {
			return 1;
		}
		return -1;
	}
}
