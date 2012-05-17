/*
 * 
 */
package ai;

import data.AStarCell;
import data.MapCell;

/**
 * The Interface GameGrid.
 */
public interface GameGrid {

	/** The Constant MAPWIDTH. */
	public static final int MAPWIDTH = 30;
	
	/** The Constant MAPHEIGHT. */
	public static final int MAPHEIGHT = 20;
	
	/** The Constant PORTALNUMBER. */
	public static final int PORTALNUMBER = 4;
	
	/** The Constant PORTALREACHABILITY. */
	public static final int PORTALREACHABILITY = 2;
	
	/** The Constant BINSIZE. */
	public static final int BINSIZE = 30;
	
	/**
	 * Gets the end cell.
	 *
	 * @return the end cell
	 */
	public abstract MapCell getEndCell();
	
	/**
	 * Gets the agent cell.
	 *
	 * @return the agent cell
	 */
	public abstract MapCell getAgentCell();
	

}