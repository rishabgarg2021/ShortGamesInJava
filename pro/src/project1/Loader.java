 package project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Loader.
 */
public class Loader {
	
	/** The types. */
	private static String[][] types;
	
	/** The world width. */
	private static int world_width;
	
	/** The world height. */
	private static int world_height;
	
	/** The offset x. */
	private static int offset_x;
	
	/** The offset y. */
	private static int offset_y;
	
	/** The total target. */
	private static int totalTarget;
	
	/** The current target. */
	private static int currentTarget;
	
	/**
	 * Sets the total target.
	 *
	 * @param totalTarget the new total target
	 */
	public static void setTotalTarget(int totalTarget) {
		Loader.totalTarget = totalTarget;
	}

	/**
	 * Gets the current target.
	 *
	 * @return the current target
	 */
	public static int getCurrentTarget() {
		return currentTarget;
	}

	/**
	 * Sets the current target.
	 *
	 * @param currentTarget the new current target
	 */
	public static void setCurrentTarget(int currentTarget) {
		Loader.currentTarget = currentTarget;
	}

	/**
	 * Gets the total target.
	 *
	 * @return the total target
	 */
	public static int getTotalTarget() {
		return totalTarget;
	}

	/**
	 * Create the appropriate sprite given a name and location.
	 * @param name	the name of the sprite
	 * @param x		the x position
	 * @param y		the y position
	 * @return		the sprite object
	 */
	public static Sprite createSprite(String name, float x, float y) {
		switch (name) {
			case "wall":
				return new Wall(x, y);
			case "floor":
				return new Floor(x, y);
			case "stone":
				return new Stone(x, y);
			case "target":
				totalTarget++;
				return new Target(x, y);
			case "player":
				return new Player(x, y);
			case "rogue":
				return new Rogue(x,y);
			case "skeleton":
				return new Skeleton(x,y);
			case "mage":
				return new Mage(x,y);
			case "ice":
				return new Ice(x,y);
			case "cracked":
				return new CrackedWall(x,y);
			case "switch":
				return new Switch(x,y);
			case "tnt":
				return new TNT(x,y);
			case "door":
				return new Door(x,y);
		}
		return null;
	}
	
	
	// Converts a world coordinate to a tile coordinate,
	/**
	 * Checks if is blocked.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if is blocked
	 */
	// and returns if that location is a blocked tile
	public static boolean isBlocked(float x, float y) {
		x -= offset_x;
		x /= App.TILE_SIZE;
		y -= offset_y;
		y /= App.TILE_SIZE;
		
		// Rounding is important here
		x = Math.round(x);
		y = Math.round(y);
		
		// Do bounds checking!
		if (x >= 0 && x < world_width && y >= 0 && y < world_height) {
			return types[(int)x][(int)y].equals("wall") ;
		}
		// Default to blocked
		return true;
	}
	
	/**
	 * Checks if is crack wall.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if is crack wall
	 */
	public static boolean isCrackWall(float x, float y) {
		x -= offset_x;
		x /= App.TILE_SIZE;
		y -= offset_y;
		y /= App.TILE_SIZE;
		
		// Rounding is important here
		x = Math.round(x);
		y = Math.round(y);
		
		
		if(CrackedWall.explosionDone) {
			return false;
		}
		// Do bounds checking!
		if (x >= 0 && x < world_width && y >= 0 && y < world_height) {
			return types[(int)x][(int)y].equals("cracked");
		}
		// Default to blocked
		return true;
	}
	
	/**
	 * Checks if is door.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if is door
	 */
	//returns if that location is a blocked door
	public static boolean isDoor(float x, float y) {
		x -= offset_x;
		x /= App.TILE_SIZE;
		y -= offset_y;
		y /= App.TILE_SIZE;
		
		// Rounding is important here
		x = Math.round(x);
		y = Math.round(y);
		if(Switch.isDoorOpen()) {
			return false;
		}
	
		if (x >= 0 && x < world_width && y >= 0 && y < world_height) {
			return types[(int)x][(int)y].equals("door");
		}
		// Default to blocked
		return true;
	}
	
	/**
	 * Checks if is switch.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if is switch
	 */
	//returns if that location is a blocked switch
	public static boolean isSwitch(float x, float y) {
		x -= offset_x;
		x /= App.TILE_SIZE;
		y -= offset_y;
		y /= App.TILE_SIZE;
		
		// Rounding is important here
		x = Math.round(x);
		y = Math.round(y);
		
	
		if (x >= 0 && x < world_width && y >= 0 && y < world_height) {
			return types[(int)x][(int)y].equals("switch");
		}
		// Default to blocked
		return true;
	}
		
		
	/**
	 * Loads the sprites from a given file.
	 *
	 * @param filename the filename
	 * @return ArrayList
	 */
	public static ArrayList<Sprite> loadSprites(String filename) {
		ArrayList<Sprite> list = new ArrayList<>();
		
		// Open the file
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			
			// Find the world size
			line = reader.readLine();
			String[] parts = line.split(",");
			world_width = Integer.parseInt(parts[0]);
			world_height = Integer.parseInt(parts[1]);
			
			// Create the array of object types
			types = new String[world_width][world_height];
			
			// Calculate the top left of the tiles so that the level is
			// centred
			offset_x = (App.SCREEN_WIDTH - world_width * App.TILE_SIZE) / 2;
			offset_y = (App.SCREEN_HEIGHT - world_height * App.TILE_SIZE) / 2;

			// Loop over every line of the file
			while ((line = reader.readLine()) != null) {
				String name;
				float x, y;
				
				// Split the line into parts
				parts = line.split(",");
				name = parts[0];
				x = Integer.parseInt(parts[1]);
				y = Integer.parseInt(parts[2]);
				types[(int)x][(int)y] = name;
				
				// Adjust for the grid
				x = offset_x + x * App.TILE_SIZE;
				y = offset_y + y * App.TILE_SIZE;
				
				// Create the sprite
				list.add(createSprite(name, x, y));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	
}
