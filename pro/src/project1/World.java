package project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class World {

	/** The block img. */
	private static String blockImg;

	/** The x pos. */
	private static float xPos;

	/** The y pos. */
	private static float yPos;

	/** The previous moves. heps to store history as whole arraylist. */
	private static ArrayList<ArrayList<Sprite>> previousMoves;

	/** The last target. */
	private static int lastTarget;

	/** The index. */
	private static int index = 0;

	/** The current level. is level which we want to begin our game. */
	private static int currentLevel = 4;

	/**
	 * Gets the array size.
	 *
	 * @return the array size
	 */
	public int getArraySize() {
		return arraySize;
	}

	/**
	 * Sets the array size.
	 *
	 * @param arraySize
	 *            the new array size
	 */
	public void setArraySize(int arraySize) {
		this.arraySize = arraySize;
	}

	/** The array size. */
	private int arraySize = 0;

	/**
	 * Gets the x pos.
	 *
	 * @return the x pos
	 */
	public static float getxPos() {
		return xPos;
	}

	/**
	 * Sets the x pos.
	 *
	 * @param xPos
	 *            the new x pos
	 */
	public static void setxPos(float xPos) {
		World.xPos = xPos;
	}

	/**
	 * Gets the y pos.
	 *
	 * @return the y pos
	 */
	public static float getyPos() {
		return yPos;
	}

	/**
	 * Sets the y pos.
	 *
	 * @param yPos
	 *            the new y pos
	 */
	public static void setyPos(float yPos) {
		World.yPos = yPos;
	}

	/**
	 * Gets the block img.
	 *
	 * @return the block img
	 */
	public static String getBlockImg() {
		return blockImg;
	}

	/**
	 * Sets the block img.
	 *
	 * @param blockImg
	 *            the new block img
	 */
	public static void setBlockImg(String blockImg) {
		World.blockImg = blockImg;
	}

	/**
	 * Instantiates a new world.
	 */
	public World() {

		previousMoves = new ArrayList<ArrayList<Sprite>>();
		previousMoves.add(Loader.loadSprites("res/levels/" + currentLevel + ".lvl"));
		lastTarget = Loader.getCurrentTarget();

		Loader.setTotalTarget(Loader.getTotalTarget());

	}

	/**
	 * Store sprite.
	 *
	 * @param previousMoves
	 *            the previous moves
	 */
	// stores sprite by doing deep copying of each object from the latest previous
	// moves as whole arraylist .
	private static void storeSprite(ArrayList<ArrayList<Sprite>> previousMoves) {
		ArrayList<Sprite> spr = new ArrayList<Sprite>();

		if (previousMoves.get(previousMoves.size() - 1) != null) {

			for (int i = 0; i < previousMoves.get(previousMoves.size() - 1).size(); i++) {

				spr.add(instSprite(previousMoves.get(previousMoves.size() - 1).get(i).getImage(),
						previousMoves.get(previousMoves.size() - 1).get(i).getX(),
						previousMoves.get(previousMoves.size() - 1).get(i).getY()));

			}
			previousMoves.add(spr);

		}

	}

	/**
	 * Gets the sprite.
	 *
	 * @return the sprite
	 */
	public static ArrayList<Sprite> getsprite() {
		return previousMoves.get(previousMoves.size() - 1);
	}

	/**
	 * Update.
	 *
	 * @param input
	 *            the input
	 * @param delta
	 *            the delta
	 */
	public void update(Input input, int delta) {
		if (currentLevel != 5) {
			for (Sprite sprite : previousMoves.get(previousMoves.size() - 1)) {

				if (sprite != null) {
					sprite.update(input, delta);
				}
			}
			// need to restart if player presses r
			if (Player.isRestart()) {
				Player.setRestart(false);
				restartLevel();

			}

			// if player gets kill after updating position by player and checking
			// if coordinates of it matches to player of enemy it dies
			if (playerKill(previousMoves.get(previousMoves.size() - 1))) {
				Player.setMoves(0);
				restartLevel();
			}

			// it remove the whole arraylist from previous position and update the last
			// position.
			if (Player.isUndoMove()) {

				if (previousMoves.size() > 1) {

					previousMoves.remove(previousMoves.get(previousMoves.size() - 1));
					lastTarget = countTargets(previousMoves.get(previousMoves.size() - 1));
					Loader.setCurrentTarget(lastTarget);
					undoDoorState(previousMoves.get(previousMoves.size() - 1));
				}
				// need to stop the ice if it was moving or not if player undo the last
				// position.
				Ice.setFirstMove(false);
				Player.setUndoMove(false);
				Player.setStoreMove(false);
				blockImg = null;
				Player.keyPressed = false;

			}
			// it stores the sprite if it doesnt want to restart level.
			if (Player.isStoreMove()) {
				Player.setUndoMove(false);
				Player.setStoreMove(false);

				storeSprite(previousMoves);

			}
			// need to check door state if it is covered by blocks and make it open
			// depending upon position of blocks on it.
			undoDoorState(previousMoves.get(previousMoves.size() - 1));

			LoadnextLevel();
		}

	}

	/**
	 * Checks if is target occupied.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if is target occupied by the x and y coordinate.
	 */

	public static boolean isTargetOccupied(float x, float y) {
		if (World.collision(x, y)) {
			for (int i = 0; i < previousMoves.get(previousMoves.size() - 1).size(); i++) {
				if (((previousMoves.get(previousMoves.size() - 1).get(i).getImage().equals("res/Target.png")
						&& previousMoves.get(previousMoves.size() - 1).get(i).getX() == x
						&& previousMoves.get(previousMoves.size() - 1).get(i).getY() == y))) {

					return true;
				}
			}
		}
		return false;

	}

	/**
	 * Collision.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return boolean if x and y ccordinate is filled by stone, ice or tnt. and
	 *         sets the image as blockImg which is blocked at that position.
	 */
	public static boolean collision(float x, float y) {

		for (int i = 0; i < previousMoves.get(previousMoves.size() - 1).size(); i++) {

			if (((previousMoves.get(previousMoves.size() - 1).get(i).getImage().equals("res/stone.png"))
					|| previousMoves.get(previousMoves.size() - 1).get(i).getImage().equals("res/ice.png")
					|| previousMoves.get(previousMoves.size() - 1).get(i).getImage().equals("res/tnt.png"))
					&& previousMoves.get(previousMoves.size() - 1).get(i).getX() == x
					&& previousMoves.get(previousMoves.size() - 1).get(i).getY() == y) {

				blockImg = previousMoves.get(previousMoves.size() - 1).get(i).getImage();
				xPos = x;
				yPos = y;
				return true;
			}

		}
		return false;
	}

	/**
	 * Inst sprite.
	 *
	 * @param name
	 *            the name
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the sprite
	 */
	// it helps to create deep copy of sprites arraylist again to store previous
	// moves.
	public static Sprite instSprite(String name, float x, float y) {
		switch (name) {
		case "res/wall.png":
			return new Wall(x, y);
		case "res/floor.png":
			return new Floor(x, y);
		case "res/stone.png":
			return new Stone(x, y);
		case "res/Target.png":
			return new Target(x, y);
		case "res/player_left.png":
			return new Player(x, y);
		case "res/rogue.png":
			return new Rogue(x, y);
		case "res/skull.png":
			return new Skeleton(x, y);
		case "res/mage.png":
			return new Mage(x, y);
		case "res/ice.png":
			return new Ice(x, y);
		case "res/cracked_wall.png":
			return new CrackedWall(x, y);
		case "res/switch.png":
			return new Switch(x, y);
		case "res/tnt.png":
			return new TNT(x, y);
		case "res/door.png":
			return new Door(x, y);
		}
		return null;
	}

	/**
	 * Loadnext level.
	 */
	// checks if all targets are filled and loads the next level.
	public void LoadnextLevel() {

		if (Loader.getTotalTarget() == Loader.getCurrentTarget()) {

			blockImg = null;
			Switch.setDoorOpen(false);
			previousMoves.clear();
			previousMoves = new ArrayList<ArrayList<Sprite>>();
			currentLevel++;
			Loader.setCurrentTarget(0);
			Loader.setTotalTarget(0);
			Player.setMoves(0);
			if (currentLevel < 5) {
				previousMoves.clear();

				previousMoves = new ArrayList<ArrayList<Sprite>>();

				previousMoves.add(Loader.loadSprites("res/levels/" + currentLevel + ".lvl"));

				Loader.setTotalTarget(Loader.getTotalTarget());

			} else {
				previousMoves = new ArrayList<ArrayList<Sprite>>();

				previousMoves.add(Loader.loadSprites("res/levels/" + 5 + ".lvl"));

			}
		}
	}

	/**
	 * Render. the game except when it is switch is occupied and explosion is done.
	 * 
	 */
	public void render(Graphics g) {
		g.drawString("Moves: " + Player.getMoves(), 0, 0);
		for (Sprite sprite : previousMoves.get(previousMoves.size() - 1)) {

			if (sprite != null) {

				if ((sprite.getImage().equals("res/door.png") && Switch.isDoorOpen())
						|| (sprite.getImage().equals("res/tnt.png") && TNT.isExplosion())
						|| (sprite.getImage().equals("res/cracked_wall.png") && CrackedWall.explosionDone)) {

				} else {

					sprite.render(g);
				}
			}
		}
	}

	/**
	 * Restart level. again loads the level if player dies or it presses r.
	 */
	public void restartLevel() {

		blockImg = null;

		previousMoves.clear();
		previousMoves = new ArrayList<ArrayList<Sprite>>();
		Switch.setDoorOpen(false);
		Rogue.setStop(0);
		Loader.setCurrentTarget(0);
		
		Loader.setTotalTarget(0);

		previousMoves.clear();
		previousMoves = new ArrayList<ArrayList<Sprite>>();

		previousMoves.add(Loader.loadSprites("res/levels/" + currentLevel + ".lvl"));

		Loader.setTotalTarget(Loader.getTotalTarget());

	}

	/**
	 * Player kill.
	 * 
	 * @return true, if it gets same coordinate as enemies.
	 */
	public boolean playerKill(ArrayList<Sprite> sp) {
		float x;
		float y;

		int total = 0;

		for (int i = 0; i < sp.size(); i++) {
			if (sp.get(i).getImage().equals("res/player_left.png")) {
				x = sp.get(i).getX();
				y = sp.get(i).getY();

				for (int j = 0; j < sp.size(); j++) {
					if (((sp.get(j).getImage().equals("res/mage.png") || sp.get(j).getImage().equals("res/rogue.png")
							|| sp.get(j).getImage().equals("res/skull.png"))
							&& ((sp.get(j).getX() == x) && (sp.get(j).getY() == y)))) {

						return true;

					}

				}

			}

		}
		return false;

	}

	/**
	 * Count targets.
	 *
	 * @param sp
	 *            the sp
	 * @return the int
	 */
	public static int countTargets(ArrayList<Sprite> sp) {
		float x;
		float y;

		int total = 0;
		for (int i = 0; i < sp.size(); i++) {
			if (sp.get(i).getImage().equals("res/Target.png")) {
				x = sp.get(i).getX();
				y = sp.get(i).getY();
				for (int j = 0; j < sp.size(); j++) {
					if (((sp.get(j).getImage().equals("res/stone.png") || sp.get(j).getImage().equals("res/ice.png")
							|| sp.get(j).getImage().equals("res/tnt.png")) && (sp.get(j).getX() == x)
							&& (sp.get(j).getY() == y))) {
						total++;

					}

				}

			}

		}
		return total;

	}

	/**
	 * Undo door state. gets the door state true or false depending upon unit on it.
	 * it helped me if i undo and need to restore the position of door.
	 */
	public static void undoDoorState(ArrayList<Sprite> sp) {
		float x;
		float y;

		int total = 0;
		for (int i = 0; i < sp.size(); i++) {
			if (sp.get(i).getImage().equals("res/switch.png")) {
				x = sp.get(i).getX();
				y = sp.get(i).getY();
				for (int j = 0; j < sp.size(); j++) {
					if (((sp.get(j).getImage().equals("res/stone.png") || sp.get(j).getImage().equals("res/ice.png")
							|| sp.get(j).getImage().equals("res/tnt.png")) && (sp.get(j).getX() == x)
							&& (sp.get(j).getY() == y))) {
						Switch.setDoorOpen(true);
						return;

					}

				}

			}

		}
		Switch.setDoorOpen(false);

	}
}
