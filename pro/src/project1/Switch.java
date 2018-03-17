package project1;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class Switch.
 */
public class Switch extends Tile {
	
	/** The Door open. */
	private static boolean DoorOpen=false;
	
	/**
	 * Instantiates a new switch.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Switch(float x, float y) {
		super("res/switch.png", x, y);
	}
	
	/**
	 * Open door.
	 *
	 * @param x the x
	 * @param y the y
	 */
	//checks if coordinate is occupied by switch and open the door by boolean.
	public static  void openDoor(float x , float y) {
		ArrayList<Sprite> sprit = World.getsprite();
		for(int i=0;i>sprit.size();i++) {
			if(sprit.get(i).equals("res/switch.png")&& sprit.get(i).getX()==x && sprit.get(i).getY()==y) {
				DoorOpen = true;
			}
		}
	
	}
	
	/**
	 * Checks if is door open.
	 *
	 * @return boolean
	 * returns true if door is open by switch.
	 */
	public static boolean isDoorOpen() {
		return DoorOpen;
	}
	
	/**
	 * Sets the door open.
	 *
	 * @param doorOpen the new door open
	 */
	public static void setDoorOpen(boolean doorOpen) {
		DoorOpen = doorOpen;
	}


}
