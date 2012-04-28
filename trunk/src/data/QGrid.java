package data;

import javax.swing.JButton;


public class QGrid {

	private QGridCell[][] grid;
	public static final int MAPWIDTH = 30;
	public static final int MAPHEIGHT = 20;

	
	
	public QGrid() {
		super();
		this.grid = new QGridCell[QGrid.MAPHEIGHT][QGrid.MAPWIDTH];
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				this.grid[i][j] = new QGridCell(i,j);
				if(j%3 == 0){
					this.grid[i][j].setCellType(CellType.AGENT);
				}
				if(j%5 == 0){
					this.grid[i][j].setCellType(CellType.BONUS);
				}
				if(j%7 == 0){
					this.grid[i][j].setCellType(CellType.ENDPOINT);
				}
				if(j%11 == 0){
					this.grid[i][j].setCellType(CellType.WALL);
				}
			}
		}
	}

	public QGrid(QGridCell[][] aGrid) throws IndexOutOfBoundsException,NullPointerException {
		super();
		checkGrid(aGrid);
		this.grid = aGrid;
	}

	public QGridCell[][] getGrid() {
		return grid;
	}

	public void setGrid(QGridCell[][] aGrid) {
		this.grid = aGrid;
	}
	
	public QGridCell getCell(int aRowIndex,int aColumnIndex) throws IndexOutOfBoundsException{
		QGridCell.verifyIndexes(aRowIndex, aColumnIndex);
		return this.grid[aRowIndex][aColumnIndex];
	}
	
	private boolean hasOnlyOneAgent(){
		boolean agentFound = false;
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(this.grid[i][j].getCellType() == CellType.AGENT && agentFound){
					return false;
				}
				if(this.grid[i][j].getCellType() == CellType.AGENT && !agentFound){
					agentFound = true;
				}
				
			}
		}
		
		return true;
	}
	
	private boolean arePortal1Coupled(){
		int portal1Number = 0;
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(portal1Number > 2){
					return false;
				}
				if(this.grid[i][j].getCellType() == CellType.PORTAL1){
					portal1Number++;
				}
				
			}
		}
		
		return true;
	}
	
	private boolean arePortal2Coupled(){
		int portal2Number = 0;
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(portal2Number > 2){
					return false;
				}
				if(this.grid[i][j].getCellType() == CellType.PORTAL1){
					portal2Number++;
				}
				
			}
		}
		
		return true;
	}
	
	private boolean arePortal3Coupled(){
		int portal3Number = 0;
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(portal3Number > 2){
					return false;
				}
				if(this.grid[i][j].getCellType() == CellType.PORTAL1){
					portal3Number++;
				}
				
			}
		}
		
		return true;
	}
	
	private boolean arePortal4Coupled(){
		int portal4Number = 0;
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(portal4Number > 2){
					return false;
				}
				if(this.grid[i][j].getCellType() == CellType.PORTAL1){
					portal4Number++;
				}
				
			}
		}
		
		return true;
	}
	
	private boolean hasOnlyOneEndpoint(){
		boolean exitFound = false;
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(this.grid[i][j].getCellType() == CellType.ENDPOINT && exitFound){
					return false;
				}
				if(this.grid[i][j].getCellType() == CellType.ENDPOINT && !exitFound){
					exitFound = true;
				}
				
			}
		}
		
		return true;
	}
	
	private boolean addDefaultReachableCells(int rowNumber, int columnNumber){
		if(rowNumber < 0 || rowNumber > QGrid.MAPHEIGHT){
			return false;
		}
		
		if(columnNumber < 0 || columnNumber > QGrid.MAPWIDTH){
			return false;
		}
		
		if(rowNumber == 0){
			this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber+1][columnNumber]);
			if(columnNumber == 0 ){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
			}
			if(columnNumber > 0 && columnNumber < QGrid.MAPWIDTH){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
			}
			if(columnNumber == QGrid.MAPWIDTH ){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
			}
		}
		
		if(rowNumber == QGrid.MAPHEIGHT){
			this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber-1][columnNumber]);
			if(columnNumber == 0 ){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
			}
			if(columnNumber > 0 && columnNumber < QGrid.MAPWIDTH){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
			}
			if(columnNumber == QGrid.MAPWIDTH ){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
			}
		}
		
		if(columnNumber == 0){
			this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
			
			if(rowNumber > 0 && rowNumber < QGrid.MAPHEIGHT){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber-1][columnNumber]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber+1][columnNumber]);
			}
		
		}
		
		if(columnNumber == QGrid.MAPWIDTH){
			this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
			
			if(rowNumber > 0 && rowNumber < QGrid.MAPHEIGHT){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber-1][columnNumber]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber+1][columnNumber]);
			}
		
		}
		
		if((rowNumber > 0 && rowNumber < QGrid.MAPHEIGHT) && (columnNumber > 0 && columnNumber < QGrid.MAPWIDTH) ){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber-1][columnNumber]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber+1][columnNumber]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
			}
		
		return true;
	}
	
	private void checkGrid(QGridCell[][] aGrid) throws IndexOutOfBoundsException,NullPointerException{
		int rowLength = 0;
		int columnLength = 0;
		
		if(aGrid == null){
			throw new NullPointerException();
		}
		
		for(QGridCell[] row : aGrid){
			rowLength = 0;
			for(QGridCell cell : row){
				rowLength++;
			}
			if(rowLength > QGrid.MAPWIDTH){
				throw new IndexOutOfBoundsException("Row index out of bounds");
			}
			columnLength++;
		}
		
		if(columnLength > QGrid.MAPHEIGHT){
			throw new IndexOutOfBoundsException("Column index out of bounds");
		}
	}
}
