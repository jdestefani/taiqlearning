package data;

import java.util.ArrayList;

import ai.QGrid;

// TODO: Auto-generated Javadoc
/**
 * The Class MapCell.
 */
public class MapCell {

	/** The row index. */
	protected int rowIndex;
	
	/** The column index. */
	protected int columnIndex;
	
	/** The cell type. */
	protected CellType cellType;
	
	/** The reachable cells. */
	protected ArrayList<MapCell> reachableCells;
	
	/** The has been visited. */
	protected boolean hasBeenVisited;
	

	/**
	 * Verify indexes.
	 *
	 * @param aRowIndex the a row index
	 * @param aColumnIndex the a column index
	 */
	public static void verifyIndexes(int aRowIndex, int aColumnIndex) {
		if(aRowIndex < 0 || aRowIndex > QGrid.MAPHEIGHT){
			throw new IndexOutOfBoundsException("Row index out of bounds.");
		}
		if(aColumnIndex < 0 || aColumnIndex > QGrid.MAPWIDTH){
			throw new IndexOutOfBoundsException("Row index out of bounds.");
		}
		
	}

	/**
	 * Gets the row index.
	 *
	 * @return the row index
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * Gets the column index.
	 *
	 * @return the column index
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * Gets the cell type.
	 *
	 * @return the cell type
	 */
	public CellType getCellType() {
		return cellType;
	}

	/**
	 * Sets the cell type.
	 *
	 * @param cellType the new cell type
	 */
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
	
	/**
	 * Gets the reachable cells.
	 *
	 * @return the reachable cells
	 */
	public ArrayList<MapCell> getReachableCells() {
		return reachableCells;
	}

	/**
	 * Sets the reachable cells.
	 *
	 * @param reachableCells the new reachable cells
	 */
	public void setReachableCells(ArrayList<MapCell> reachableCells) {
		this.reachableCells = reachableCells;
	}

	/**
	 * Adds the reachable cell.
	 *
	 * @param reachableCell the reachable cell
	 */
	public void addReachableCell(MapCell reachableCell) {
		this.reachableCells.add(reachableCell);
	}

	/**
	 * Removes the reachable cell.
	 *
	 * @param reachableCell the reachable cell
	 */
	public void removeReachableCell(MapCell reachableCell) {
		this.reachableCells.remove(reachableCell);
	}
	
	/**
	 * Adds the default reachable cells.
	 *
	 * @param aGrid the a grid
	 * @return true, if successful
	 */
	public boolean addDefaultReachableCells(MapCell[][] aGrid){
		MapCell reachableCell = null;
		if(rowIndex < 0 || rowIndex > QGrid.MAPHEIGHT - 1){
			return false;
		}
		
		if(columnIndex < 0 || columnIndex > QGrid.MAPWIDTH - 1){
			return false;
		}
		
		if(rowIndex == 0){
			reachableCell = aGrid[rowIndex+1][columnIndex];
			if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
			}
			
			if(columnIndex == 0 ){
				reachableCell = aGrid[rowIndex][columnIndex+1];
				if(reachableCell.getCellType() != CellType.WALL){
					aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
				
			}
			if(columnIndex > 0 && columnIndex < QGrid.MAPWIDTH - 1){
				reachableCell = aGrid[rowIndex][columnIndex+1];
				if(reachableCell.getCellType() != CellType.WALL){
					aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
				reachableCell = aGrid[rowIndex][columnIndex-1];
				if(reachableCell.getCellType() != CellType.WALL){
					aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
			if(columnIndex == QGrid.MAPWIDTH - 1){
				reachableCell = aGrid[rowIndex][columnIndex-1];
				if(reachableCell.getCellType() != CellType.WALL){
					aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
		}
		
		if(rowIndex == QGrid.MAPHEIGHT - 1){
			reachableCell = aGrid[rowIndex - 1][columnIndex];
			if(reachableCell.getCellType() != CellType.WALL){
			aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
			}
			
			if(columnIndex == 0 ){
				reachableCell = aGrid[rowIndex][columnIndex+1];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
			if(columnIndex > 0 && columnIndex < QGrid.MAPWIDTH - 1){
				reachableCell = aGrid[rowIndex][columnIndex-1];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
				
				reachableCell = aGrid[rowIndex][columnIndex+1];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
			if(columnIndex == QGrid.MAPWIDTH - 1){
				reachableCell = aGrid[rowIndex][columnIndex-1];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
		}
		
		if(columnIndex == 0){
			reachableCell = aGrid[rowIndex][columnIndex + 1];
			if(reachableCell.getCellType() != CellType.WALL){
			aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
			}
			
			if(rowIndex == 0 ){
				reachableCell = aGrid[rowIndex + 1][columnIndex];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
			if(rowIndex > 0 && rowIndex < QGrid.MAPHEIGHT - 1){ 
				reachableCell = aGrid[rowIndex + 1][columnIndex];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
				reachableCell = aGrid[rowIndex - 1][columnIndex];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
			if(rowIndex == QGrid.MAPHEIGHT - 1 ){
				reachableCell = aGrid[rowIndex - 1][columnIndex];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
		
		}
		
		if(columnIndex == QGrid.MAPWIDTH - 1 ){
			reachableCell = aGrid[rowIndex][columnIndex - 1];
			if(reachableCell.getCellType() != CellType.WALL){
			aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
			}
			
			if(rowIndex == 0 ){
				reachableCell = aGrid[rowIndex + 1][columnIndex];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
			if(rowIndex > 0 && rowIndex < QGrid.MAPHEIGHT - 1){
				reachableCell = aGrid[rowIndex + 1][columnIndex];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
				reachableCell = aGrid[rowIndex - 1][columnIndex];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
			if(rowIndex == QGrid.MAPHEIGHT - 1 ){
				reachableCell = aGrid[rowIndex - 1][columnIndex];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
		
		}
		
		if((rowIndex > 0 && rowIndex < QGrid.MAPHEIGHT - 1) && (columnIndex > 0 && columnIndex < QGrid.MAPWIDTH - 1) ){
				reachableCell = aGrid[rowIndex-1][columnIndex];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
				reachableCell = aGrid[rowIndex+1][columnIndex];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
				reachableCell = aGrid[rowIndex][columnIndex+1];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
				reachableCell = aGrid[rowIndex][columnIndex-1];
				if(reachableCell.getCellType() != CellType.WALL){
				aGrid[rowIndex][columnIndex].addReachableCell(reachableCell);
				}
			}
		
		return true;
	}
	
	/**
	 * Adds the portal reachable cells.
	 *
	 * @param aGrid the a grid
	 * @param aPortalReachableCells the a portal reachable cells
	 */
	public void addPortalReachableCells(MapCell[][] aGrid,ArrayList<MapCell>[] aPortalReachableCells){
				
		switch(aGrid[rowIndex][columnIndex].getCellType()){
				
				case PORTAL1:	aGrid[rowIndex][columnIndex].setReachableCells(aPortalReachableCells[0]);
								break;
				
				case PORTAL2:	aGrid[rowIndex][columnIndex].setReachableCells(aPortalReachableCells[1]);
								break;
				
				case PORTAL3:	aGrid[rowIndex][columnIndex].setReachableCells(aPortalReachableCells[2]);
								break;
				
				case PORTAL4:	aGrid[rowIndex][columnIndex].setReachableCells(aPortalReachableCells[3]);
								break;
				default : break;
				}
				
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new String("("+rowIndex+","+columnIndex+")");
	}

	/**
	 * Checks for been visited.
	 *
	 * @return true, if successful
	 */
	public boolean hasBeenVisited() {
		return hasBeenVisited;
	}

	/**
	 * Sets the checks for been visited.
	 *
	 * @param hasBeenVisited the new checks for been visited
	 */
	public void setHasBeenVisited(boolean hasBeenVisited) {
		this.hasBeenVisited = hasBeenVisited;
	}

	/**
	 * Instantiates a new map cell.
	 */
	public MapCell() {
		super();
	}

}