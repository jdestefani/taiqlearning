package data;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class AStarCell.
 */
public class AStarCell extends MapCell {
	
	/** The distance from agent. */
	private int distanceFromAgent;
	
	/** The heuristic distance from goal. */
	private double heuristicDistanceFromGoal;
	
	/** The previous a path cell. */
	private AStarCell previousAPathCell;
	
	/**
	 * Instantiates a new a star cell.
	 */
	public AStarCell() {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = CellType.PLAIN;
		this.reachableCells = new ArrayList<MapCell>();
		this.distanceFromAgent = Integer.MAX_VALUE;
		this.heuristicDistanceFromGoal = 0.0;
		this.hasBeenVisited = false;
		previousAPathCell = null;
	}
	
	/**
	 * Instantiates a new a star cell.
	 *
	 * @param aCellType the a cell type
	 */
	public AStarCell(CellType aCellType) {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = aCellType;
		this.reachableCells = new ArrayList<MapCell>();
		this.distanceFromAgent = Integer.MAX_VALUE;
		this.heuristicDistanceFromGoal = 0.0;
		this.hasBeenVisited = false;
		previousAPathCell = null;
	}
	
	/**
	 * Instantiates a new a star cell.
	 *
	 * @param aRowIndex the a row index
	 * @param aColumnIndex the a column index
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	public AStarCell(int aRowIndex, int aColumnIndex) throws IndexOutOfBoundsException {
		super();
		verifyIndexes(aRowIndex, aColumnIndex);
		this.rowIndex = aRowIndex;
		this.columnIndex = aColumnIndex;
		this.cellType = CellType.PLAIN;
		this.reachableCells = new ArrayList<MapCell>();
		this.distanceFromAgent = Integer.MAX_VALUE;
		this.heuristicDistanceFromGoal = 0.0;
		this.hasBeenVisited = false;
		previousAPathCell = null;
	}

	/**
	 * Gets the distance from agent.
	 *
	 * @return the distance from agent
	 */
	public int getDistanceFromAgent() {
		return distanceFromAgent;
	}

	/**
	 * Sets the distance from agent.
	 *
	 * @param distanceFromAgent the new distance from agent
	 */
	public void setDistanceFromAgent(int distanceFromAgent) {
		this.distanceFromAgent = distanceFromAgent;
	}

	/**
	 * Gets the heuristic distance from goal.
	 *
	 * @return the heuristic distance from goal
	 */
	public double getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}

	/**
	 * Sets the heuristic distance from goal.
	 *
	 * @param heuristicDistanceFromGoal the new heuristic distance from goal
	 */
	public void setHeuristicDistanceFromGoal(double heuristicDistanceFromGoal) {
		this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
	}
	
	/**
	 * Visit.
	 */
	public void visit(){
		this.hasBeenVisited = true;
	}

	/**
	 * Gets the previous a path cell.
	 *
	 * @return the previous a path cell
	 */
	public AStarCell getPreviousAPathCell() {
		return previousAPathCell;
	}

	/**
	 * Sets the previous a path cell.
	 *
	 * @param previousAPathCell the new previous a path cell
	 */
	public void setPreviousAPathCell(AStarCell previousAPathCell) {
		this.previousAPathCell = previousAPathCell;
	}
	
	

}
