package ai;

import data.AStarCell;
import data.MapCell;

public interface GameGrid {

	public static final int MAPWIDTH = 30;
	public static final int MAPHEIGHT = 20;
	public static final int PORTALNUMBER = 4;
	public static final int PORTALREACHABILITY = 2;
	public static final int BINSIZE = 30;
	
	public abstract MapCell getEndCell();
	public abstract MapCell getAgentCell();
	

}