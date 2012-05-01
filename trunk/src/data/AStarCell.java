package data;

import java.util.ArrayList;

public class AStarCell extends MapCell {
	
	private int distanceFromAgent;
	private double heuristicDistanceFromGoal;
	private ArrayList<AStarCell> reachableCells;
	
	public AStarCell() {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = CellType.PLAIN;
		this.reachableCells = new ArrayList<AStarCell>();
		this.distanceFromAgent = 0;
		this.heuristicDistanceFromGoal = 0.0;
	}
	
	public AStarCell(CellType aCellType) {
		super();
		this.rowIndex = 0;
		this.columnIndex = 0;
		this.cellType = aCellType;
		this.reachableCells = new ArrayList<AStarCell>();
		this.distanceFromAgent = 0;
		this.heuristicDistanceFromGoal = 0.0;
	}
	
	public AStarCell(int aRowIndex, int aColumnIndex) throws IndexOutOfBoundsException {
		super();
		verifyIndexes(aRowIndex, aColumnIndex);
		this.rowIndex = aRowIndex;
		this.columnIndex = aColumnIndex;
		this.cellType = CellType.PLAIN;
		this.reachableCells = new ArrayList<AStarCell>();
		this.distanceFromAgent = 0;
		this.heuristicDistanceFromGoal = 0.0;
	}

	public int getDistanceFromAgent() {
		return distanceFromAgent;
	}

	public void setDistanceFromAgent(int distanceFromAgent) {
		this.distanceFromAgent = distanceFromAgent;
	}

	public double getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}

	public void setHeuristicDistanceFromGoal(double heuristicDistanceFromGoal) {
		this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
	}
	
	

}
