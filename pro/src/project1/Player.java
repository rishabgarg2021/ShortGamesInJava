package project1;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player extends Unit {
	
	/** The key pressed. */
	public static boolean keyPressed = false;
	
	/** The store move. */
	private static boolean storeMove = false;
	
	/** The undo move. */
	private static boolean undoMove = false;
	
	/** The restart. */
	private static boolean restart = false;
	
	/** The moves. */
	private static int moves = 0;

	/**
	 * Checks if is restart.
	 *
	 * @return true, if is restart
	 */
	public static boolean isRestart() {
		return restart;
	}

	/**
	 * Sets the restart.
	 *
	 * @param restart the new restart
	 */
	public static void setRestart(boolean restart) {
		Player.restart = restart;
	}

	/**
	 * Checks if is undo move.
	 *
	 * @return true, if is undo move
	 */
	public static boolean isUndoMove() {
		return undoMove;
	}

	/**
	 * Sets the undo move.
	 *
	 * @param undoMove the new undo move
	 */
	public static void setUndoMove(boolean undoMove) {
		Player.undoMove = undoMove;
	}

	/**
	 * Instantiates a new player.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Player(float x, float y) {
		super("res/player_left.png", x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see project1.Sprite#update(org.newdawn.slick.Input, int)
	 */
	@Override
	
	public void update(Input input, int delta) {
		int dir = DIR_NONE;

		if (input.isKeyPressed(Input.KEY_LEFT)) {
			
			dir = DIR_LEFT;
		} else if (input.isKeyPressed(Input.KEY_RIGHT)) {

			dir = DIR_RIGHT;
		} else if (input.isKeyPressed(Input.KEY_UP)) {

			dir = DIR_UP;

		} else if (input.isKeyPressed(Input.KEY_DOWN)) {

			dir = DIR_DOWN;
		} else if (input.isKeyPressed(Input.KEY_Z)) {
			undoMove = true;
			//need to make sure moves dont go negative on pressing more number of undo.
			if (moves >= 1) {
				moves--;
			}
			
		//restart level on pressing r.
		} else if (input.isKeyPressed(Input.KEY_R)) {

			restart = true;
			moves = 0;
		}

		//it makes keypress true if player presses button which helps to move in world.
		if (dir != DIR_NONE && !undoMove && !restart) {
			storeMove = true;
			keyPressed = true;

			moves++;

		}
		
		if (moveToDest(dir) && !restart) {

			nextMove(dir);

		}

	}

	/**
	 * Gets the moves.
	 *
	 * @return the moves
	 */
	public static int getMoves() {
		return moves;
	}

	/**
	 * Sets the moves.
	 *
	 * @param moves the new moves
	 */
	public static void setMoves(int moves) {
		Player.moves = moves;
	}

	/**
	 * Checks if is store move.
	 *
	 * @return true, if is store move
	 */
	public static boolean isStoreMove() {
		return storeMove;
	}

	/**
	 * Sets the store move.
	 *
	 * @param storeMove the new store move
	 */
	public static void setStoreMove(boolean storeMove) {
		Player.storeMove = storeMove;
	}

}
