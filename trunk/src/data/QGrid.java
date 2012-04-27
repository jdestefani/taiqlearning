package data;

import javax.swing.JButton;

import main.TAIQLearningApp;

public class QGrid {

	private QGridCell[][] grid;

	
	
	public QGrid() {
		super();
		this.grid = new QGridCell[TAIQLearningApp.MAPHEIGHT][TAIQLearningApp.MAPWIDTH];
		for(int i=0; i<TAIQLearningApp.MAPHEIGHT ; i++){
			for(int j=0 ; j<TAIQLearningApp.MAPWIDTH; j++){
				this.grid[i][j] = new QGridCell();
			}
		}
	}

	public QGrid(QGridCell[][] grid) {
		super();
		this.grid = grid;
	}

	public QGridCell[][] getGrid() {
		return grid;
	}

	public void setGrid(QGridCell[][] grid) {
		this.grid = grid;
	}
	
	private boolean hasOnlyOneAgent(){
		boolean agentFound = false;
		for(int i=0; i<TAIQLearningApp.MAPHEIGHT ; i++){
			for(int j=0 ; j<TAIQLearningApp.MAPWIDTH; j++){
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
		for(int i=0; i<TAIQLearningApp.MAPHEIGHT ; i++){
			for(int j=0 ; j<TAIQLearningApp.MAPWIDTH; j++){
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
		for(int i=0; i<TAIQLearningApp.MAPHEIGHT ; i++){
			for(int j=0 ; j<TAIQLearningApp.MAPWIDTH; j++){
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
		for(int i=0; i<TAIQLearningApp.MAPHEIGHT ; i++){
			for(int j=0 ; j<TAIQLearningApp.MAPWIDTH; j++){
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
		for(int i=0; i<TAIQLearningApp.MAPHEIGHT ; i++){
			for(int j=0 ; j<TAIQLearningApp.MAPWIDTH; j++){
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
		for(int i=0; i<TAIQLearningApp.MAPHEIGHT ; i++){
			for(int j=0 ; j<TAIQLearningApp.MAPWIDTH; j++){
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
		if(rowNumber < 0 || rowNumber > TAIQLearningApp.MAPHEIGHT){
			return false;
		}
		
		if(columnNumber < 0 || columnNumber > TAIQLearningApp.MAPWIDTH){
			return false;
		}
		
		if(rowNumber == 0){
			this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber+1][columnNumber]);
			if(columnNumber == 0 ){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
			}
			if(columnNumber > 0 && columnNumber < TAIQLearningApp.MAPWIDTH){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
			}
			if(columnNumber == TAIQLearningApp.MAPWIDTH ){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
			}
		}
		
		if(rowNumber == TAIQLearningApp.MAPHEIGHT){
			this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber-1][columnNumber]);
			if(columnNumber == 0 ){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
			}
			if(columnNumber > 0 && columnNumber < TAIQLearningApp.MAPWIDTH){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
			}
			if(columnNumber == TAIQLearningApp.MAPWIDTH ){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
			}
		}
		
		if(columnNumber == 0){
			this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
			
			if(rowNumber > 0 && rowNumber < TAIQLearningApp.MAPHEIGHT){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber-1][columnNumber]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber+1][columnNumber]);
			}
		
		}
		
		if(columnNumber == TAIQLearningApp.MAPWIDTH){
			this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
			
			if(rowNumber > 0 && rowNumber < TAIQLearningApp.MAPHEIGHT){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber-1][columnNumber]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber+1][columnNumber]);
			}
		
		}
		
		if((rowNumber > 0 && rowNumber < TAIQLearningApp.MAPHEIGHT) && (columnNumber > 0 && columnNumber < TAIQLearningApp.MAPWIDTH) ){
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber-1][columnNumber]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber+1][columnNumber]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber-1]);
				this.grid[rowNumber][columnNumber].addReachableCell(this.grid[rowNumber][columnNumber+1]);
			}
		
		return true;
	}
}
