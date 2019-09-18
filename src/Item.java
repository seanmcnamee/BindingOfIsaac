package src;

import java.awt.Image;

public interface Item {

	Image img = null;
	int[] stat = {0, 0, 0, 0};
	
	/**
	 * Stats:
	 * 
	 * 
	 * 
	 * Health (number of hearts (each heart has 2 halves))     0 - Health
	 * Speed (How fast you move around the map)                 1 - Speed
	 * Dexterity (How fast you shoot )                                    2 - Dexterity
	 * Attack (how much damage you deal)                            3 - Attack
	 * 
	 *  
	 *Defense (Temporary Heart?) <- Later
	 * 
	 */
	
	public void useItem();
	
}
