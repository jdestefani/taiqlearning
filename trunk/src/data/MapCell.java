package data;

import java.util.ArrayList;

import ai.QGrid;

public class MapCell {

	protected int rowIndex;
	protected int columnIndex;
	protected CellType cellType;
	protected ArrayList<MapCell> reachableCells;
	protected boolean hasBeenVisited;
	

	public static void verifyIndexes(int aRowIndex, int aColumnIndex) {
		if(aRowIndex < 0 || aRowIndex > QGrid.MAPHEIGHT){
			throw new IndexOutOfBoundsException("Row index out of bounds.");
		}
		if(aColumnIndex < 0 || aColumnIndex > QGrid.MAPWIDTH){
			throw new IndexOutOfBoundsException("Row index out of bounds.");
		}
		
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
	
	public ArrayList<MapCell> getReachableCells() {
		return reachableCells;
	}

	public void setReachableCells(ArrayList<MapCell> reachableCells) {
		this.reachableCells = reachableCells;
	}

	public void addReachableCell(MapCell reachableCell) {
		this.reachableCells.add(reachableCell);
	}

	public void removeReachableCell(MapCell reachableCell) {
		this.reachableCells.remove(reachableCell);
	}
	
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
	

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return new String("("+rowIndex+","+columnIndex+")");
	}

	public boolean hasBeenVisited() {
		return hasBeenVisited;
	}

	public void setHasBeenVisited(boolean hasBeenVisited) {
		this.hasBeenVisited = hasBeenVisited;
	}

	public MapCell() {
		super();
	}

}