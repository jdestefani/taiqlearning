package ai;

import java.awt.Dimension;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JButton;

import data.CellType;
import data.MapCell;
import data.QGridCell;



// TODO: Auto-generated Javadoc
/**
 * The Class QGrid.
 */
public class QGrid implements GameGrid {

	/** The grid. */
	private QGridCell[][] grid;
	
	/** The portal reachable cells. */
	private ArrayList<MapCell>[] portalReachableCells;
	
	/** The curr agent cell. */
	private QGridCell currAgentCell;
	
	/** The init agent cell. */
	private QGridCell initAgentCell;
	
	/** The agent cell. */
	private QGridCell agentCell;
	
	/** The end cell. */
	private QGridCell endCell;
	
	/** The last cell type. */
	private CellType lastCellType;
	
	/** The Bonus cells. */
	private ArrayList<MapCell> BonusCells;
	
	/** The visited cells. */
	private ArrayList<MapCell> visitedCells;
	
	
	/**
	 * Instantiates a new q grid.
	 */
	public QGrid() {
		super();
		lastCellType = CellType.PLAIN;
		grid = new QGridCell[MAPHEIGHT][MAPWIDTH];
		BonusCells = new ArrayList<MapCell>();
		visitedCells = new ArrayList<MapCell>();
		
		portalReachableCells = new ArrayList[PORTALNUMBER];
		for(int i=0; i<PORTALNUMBER ; i++){
			portalReachableCells[i] = new ArrayList<MapCell>();
		}
		
		//generateDeterministicTestMap();
		//generateRandomTestMap();
		generateMazeMap();
		
		for(int i=0; i<MAPHEIGHT ; i++){
			for(int j=0 ; j<MAPWIDTH; j++){
				this.getCell(i, j).addDefaultReachableCells(grid);
			}
		}
		
		placeComponents();
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				this.getCell(i, j).addPortalReachableCells(grid,portalReachableCells);
			}
		}
	}

	/**
	 * Gets the visited cells.
	 *
	 * @return the visited cells
	 */
	public ArrayList<MapCell> getVisitedCells() {
		return visitedCells;
	}

	/**
	 * Instantiates a new q grid.
	 *
	 * @param aGrid the a grid
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 * @throws NullPointerException the null pointer exception
	 */
	public QGrid(QGridCell[][] aGrid) throws IndexOutOfBoundsException,NullPointerException {
		super();
		lastCellType = CellType.PLAIN;
		checkGrid(aGrid);
		grid = aGrid;
	}

	/**
	 * Gets the grid.
	 *
	 * @return the grid
	 */
	public MapCell[][] getGrid() {
		return grid;
	}

	/**
	 * Sets the grid.
	 *
	 * @param aGrid the new grid
	 */
	public void setGrid(QGridCell[][] aGrid) {
		this.grid = aGrid;
	}
	
	/**
	 * Gets the cell.
	 *
	 * @param aRowIndex the a row index
	 * @param aColumnIndex the a column index
	 * @return the cell
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	public QGridCell getCell(int aRowIndex,int aColumnIndex) throws IndexOutOfBoundsException{
		QGridCell.verifyIndexes(aRowIndex, aColumnIndex);
		return this.grid[aRowIndex][aColumnIndex];
	}
	
	/**
	 * Checks for only one agent.
	 *
	 * @return true, if successful
	 */
	private boolean hasOnlyOneAgent(){
		boolean agentFound = false;
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(this.getCell(i,j).getCellType() == CellType.AGENT && agentFound){
					return false;
				}
				if(this.getCell(i,j).getCellType() == CellType.AGENT && !agentFound){
					agentFound = true;
				}
				
			}
		}
		
		return true;
	}
	
	/**
	 * Are portal1 coupled.
	 *
	 * @return true, if successful
	 */
	private boolean arePortal1Coupled(){
		int portal1Number = 0;
		int index = 0;
		ArrayList<QGridCell> foundPortals1 = new ArrayList<QGridCell>();
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(portal1Number > PORTALREACHABILITY){
					return false;
				}
				if(this.getCell(i,j).getCellType() == CellType.PORTAL1){
					portal1Number++;
					foundPortals1.add(this.getCell(i,j));
				}
				
			}
		}
		
		if(foundPortals1.size() > PORTALREACHABILITY){
			return false;
		}
		else{
			if(foundPortals1.size() < PORTALREACHABILITY){
				return false;
			}
			else{
				for(QGridCell currCell : foundPortals1){
					this.portalReachableCells[0].addAll(currCell.getReachableCells());
					index++;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Are portal2 coupled.
	 *
	 * @return true, if successful
	 */
	private boolean arePortal2Coupled(){
		int portal2Number = 0;
		int index = 0;
		ArrayList<QGridCell> foundPortals2 = new ArrayList<QGridCell>();

		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(portal2Number > PORTALREACHABILITY){
					return false;
				}
				if(this.getCell(i,j).getCellType() == CellType.PORTAL2){
					portal2Number++;
					foundPortals2.add(this.getCell(i,j));
				}
				
			}
		}
		
		if(foundPortals2.size() > PORTALREACHABILITY){
			return false;
		}
		else{
			if(foundPortals2.size() < PORTALREACHABILITY){
				return false;
			}
			else{
				for(QGridCell currCell : foundPortals2){
					this.portalReachableCells[1].addAll(currCell.getReachableCells());
					index++;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Are portal3 coupled.
	 *
	 * @return true, if successful
	 */
	private boolean arePortal3Coupled(){
		int portal3Number = 0;
		int index = 0;
		ArrayList<QGridCell> foundPortals3 = new ArrayList<QGridCell>();
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(portal3Number > PORTALREACHABILITY){
					return false;
				}
				if(this.getCell(i,j).getCellType() == CellType.PORTAL3){
					portal3Number++;
					foundPortals3.add(this.getCell(i, j));
				}
				
			}
		}
		
		if(foundPortals3.size() > PORTALREACHABILITY){
			return false;
		}
		else{
			if(foundPortals3.size() < PORTALREACHABILITY){
				return false;
			}
			else{
				for(QGridCell currCell : foundPortals3){
					this.portalReachableCells[2].addAll(currCell.getReachableCells());
					index++;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Are portal4 coupled.
	 *
	 * @return true, if successful
	 */
	private boolean arePortal4Coupled(){
		int portal4Number = 0;
		int index = 0;
		ArrayList<QGridCell> foundPortals4 = new ArrayList<QGridCell>();
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(portal4Number > PORTALREACHABILITY){
					return false;
				}
				if(this.getCell(i,j).getCellType() == CellType.PORTAL4){
					portal4Number++;
					foundPortals4.add(this.getCell(i, j));
				}
				
			}
		}
		
		if(foundPortals4.size() > PORTALREACHABILITY){
			return false;
		}
		else{
			if(foundPortals4.size() < PORTALREACHABILITY){
				return false;
			}
			else{
				for(QGridCell currCell : foundPortals4){
					this.portalReachableCells[3].addAll(currCell.getReachableCells());
					index++;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Checks for only one endpoint.
	 *
	 * @return true, if successful
	 */
	private boolean hasOnlyOneEndpoint(){
		boolean exitFound = false;
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(this.getCell(i,j).getCellType() == CellType.ENDPOINT && exitFound){
					return false;
				}
				if(this.getCell(i,j).getCellType() == CellType.ENDPOINT && !exitFound){
					exitFound = true;
				}
				
			}
		}
		
		return true;
	}
	
	/**
	 * Reset visited.
	 */
	public void resetVisited()
	{
		for(int i = 0; i < QGrid.MAPHEIGHT; i++)
		{
			for(int j = 0; j < QGrid.MAPWIDTH; j++)
			{
				this.getCell(i, j).setHasBeenVisited(false);
			}
		}
	}

	/**
	 * Check grid.
	 *
	 * @param aGrid the a grid
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 * @throws NullPointerException the null pointer exception
	 */
	private void checkGrid(MapCell[][] aGrid) throws IndexOutOfBoundsException,NullPointerException{
		int rowLength = 0;
		int columnLength = 0;
		
		if(aGrid == null){
			throw new NullPointerException();
		}
		
		for(MapCell[] row : aGrid){
			rowLength = 0;
			for(MapCell cell : row){
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

	/**
	 * Generate deterministic test map.
	 */
	private void generateDeterministicTestMap(){
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				this.grid[i][j] = new QGridCell(i,j);
				if(j%3 == 0){
					this.getCell(i,j).setCellType(CellType.AGENT);
				}
				if(j%5 == 0){
					if(j%10 == 0){
						this.getCell(i,j).setCellType(CellType.BONUS);
					}
					else{
						this.getCell(i,j).setCellType(CellType.MALUS);	
					}
				}
				if(j%7 == 0){
					this.getCell(i,j).setCellType(CellType.ENDPOINT);
				}
				if(j%11 == 0){
					this.getCell(i,j).setCellType(CellType.WALL);
				}
			}
		}
	}
	
	/**
	 * Generate random test map.
	 */
	private void generateRandomTestMap(){
		Random rngMap = new Random();
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				grid[i][j] = new QGridCell(i,j);
				switch(rngMap.nextInt(600)){
				case 1 : grid[i][j].setCellType(CellType.AGENT);
						 break;
				case 2 : grid[i][j].setCellType(CellType.BONUS);
				 		 break;
				case 3 : grid[i][j].setCellType(CellType.ENDPOINT);
		 		 		 break;
				case 4 : grid[i][j].setCellType(CellType.MALUS);
				 		 break;
				case 5 : grid[i][j].setCellType(CellType.PORTAL1);
						 break;
				case 6 : grid[i][j].setCellType(CellType.PORTAL2);
		 		 		 break;
				case 7 : grid[i][j].setCellType(CellType.WALL);
		 		 		 break;
				case 8 : grid[i][j].setCellType(CellType.PORTAL3);
				 		 break;
				case 9 : grid[i][j].setCellType(CellType.PORTAL4);
		 		 		 break;
				default : grid[i][j].setCellType(CellType.PLAIN);
				 		 break;
				}
			}
		}
	}
	
	/**
	 * Generate maze map.
	 */
	private void generateMazeMap(){
		
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				grid[i][j] = new QGridCell(i,j);
			}
		}
		
		splitMap(0, 0, MAPHEIGHT, MAPWIDTH);
	}
	
	/**
	 * Split map.
	 *
	 * @param aUpperCornerRow the a upper corner row
	 * @param aUpperCornerColumn the a upper corner column
	 * @param aLowerCornerRow the a lower corner row
	 * @param aLowerCornerColumn the a lower corner column
	 */
	private void splitMap(int aUpperCornerRow,int aUpperCornerColumn,int aLowerCornerRow,int aLowerCornerColumn){
		int frameHeight = aLowerCornerRow - aUpperCornerRow;
		int frameWidth =  aLowerCornerColumn - aUpperCornerColumn;
		
		if(frameHeight <= MAPHEIGHT/10*2 || frameWidth <= MAPWIDTH/10*3){
			return;
		}
		
		Random bordersRng = new Random();
		//Borders must not be on the border of a frame
		int hBorderStart = aUpperCornerRow + 1 + bordersRng.nextInt(frameHeight - 2);
		int vBorderStart = aUpperCornerColumn + 1 + bordersRng.nextInt(frameWidth - 2);
		int intersectRow = 0;
		int intersectColumn = 0;
		int holeRow = 0;
		int holeColumn = 0;
		
		//Vertical border creation
		for(int i=0; i<frameHeight; i++){
			grid[aUpperCornerRow+i][vBorderStart].setCellType(CellType.WALL);
		}
		
		//Vertical border creation
		for(int i=0; i<frameWidth; i++){
			if(grid[hBorderStart][aUpperCornerColumn+i].getCellType() == CellType.WALL){
				intersectRow = hBorderStart;
				intersectColumn = aUpperCornerColumn+i;
			}
			grid[hBorderStart][aUpperCornerColumn+i].setCellType(CellType.WALL);
		}
		
		//Holes creation
		/*   |    Vertical parts = 1,2
		 * -----  Horizontal parts = 3,4
		 *   | 
		 * */
		//Holes cannot be neither on the inserection point nor on the border
		switch(bordersRng.nextInt(40)){
		case 0: if(intersectColumn - aUpperCornerColumn - 2 > 0){
					holeColumn = aUpperCornerColumn + 1 + bordersRng.nextInt(intersectColumn - aUpperCornerColumn - 2);
				}
				else{
					holeColumn = aUpperCornerColumn + 1;
				}
				grid[hBorderStart][holeColumn].setCellType(CellType.PLAIN);
				
				if(aLowerCornerColumn - intersectColumn - 2 > 0){
					holeColumn = intersectColumn + 1 + bordersRng.nextInt(aLowerCornerColumn - intersectColumn - 2);
				}
				else{
					holeColumn = intersectColumn + 1;
				}
				grid[hBorderStart][holeColumn].setCellType(CellType.PLAIN);
				
				
				if(aLowerCornerRow - intersectRow - 2 > 0){
					holeRow = intersectRow + 1 + bordersRng.nextInt(aLowerCornerRow - intersectRow - 2);
				}
				else{
					holeRow = intersectRow + 1;
				}
				grid[holeRow][vBorderStart].setCellType(CellType.PLAIN);
				
				break;
				
		case 1: if(intersectColumn - aUpperCornerColumn - 2 > 0){
					holeColumn = aUpperCornerColumn + 1 + bordersRng.nextInt(intersectColumn - aUpperCornerColumn - 2);
				}
				else{
					holeColumn = aUpperCornerColumn + 1;
				}
				grid[hBorderStart][holeColumn].setCellType(CellType.PLAIN);
				
				if(aLowerCornerColumn - intersectColumn - 2 > 0){
					holeColumn = intersectColumn + 1 + bordersRng.nextInt(aLowerCornerColumn - intersectColumn - 2);
				}
				else{
					holeColumn = intersectColumn + 1;
				}
				grid[hBorderStart][holeColumn].setCellType(CellType.PLAIN);
				
				
				if(intersectRow - aUpperCornerRow - 2 > 0){
					holeRow = aUpperCornerRow + 1 + bordersRng.nextInt(intersectRow - aUpperCornerRow - 2);
				}
				else{
					holeRow = aUpperCornerRow + 1;
				}
				grid[holeRow][vBorderStart].setCellType(CellType.PLAIN);
				break;
				
		case 2:if(aLowerCornerColumn - intersectColumn - 2 > 0){
					holeColumn = intersectColumn + 1 + bordersRng.nextInt(aLowerCornerColumn - intersectColumn - 2);
				}
				else{
					holeColumn = intersectColumn + 1;
				}
			  	grid[hBorderStart][holeColumn].setCellType(CellType.PLAIN);
			  	
			  	if(intersectRow - aUpperCornerRow - 2 > 0){
					holeRow = aUpperCornerRow + 1 + bordersRng.nextInt(intersectRow - aUpperCornerRow - 2);
				}
				else{
					holeRow = aUpperCornerRow + 1;
				}
			  	grid[holeRow][vBorderStart].setCellType(CellType.PLAIN);
			  	
			  	if(aLowerCornerRow - intersectRow - 2 > 0){
					holeRow = intersectRow + 1 + bordersRng.nextInt(aLowerCornerRow - intersectRow - 2);
				}
				else{
					holeRow = intersectRow + 1;
				}
				grid[holeRow][vBorderStart].setCellType(CellType.PLAIN);
			  	break;
			  	
		case 3: if(intersectColumn - aUpperCornerColumn - 2 > 0){
					holeColumn = aUpperCornerColumn + 1 + bordersRng.nextInt(intersectColumn - aUpperCornerColumn - 2);
				}
				else{
					holeColumn = aUpperCornerColumn + 1;
				}
				grid[hBorderStart][holeColumn].setCellType(CellType.PLAIN);
		
				if(intersectRow - aUpperCornerRow - 2 > 0){
						holeRow = aUpperCornerRow + 1 + bordersRng.nextInt(intersectRow - aUpperCornerRow - 2);
				}
				else{
						holeRow = aUpperCornerRow + 1;
				}
	  			grid[holeRow][vBorderStart].setCellType(CellType.PLAIN);
	  			
	  			if(aLowerCornerRow - intersectRow - 2 > 0){
					holeRow = intersectRow + 1 + bordersRng.nextInt(aLowerCornerRow - intersectRow - 2);
				}
				else{
					holeRow = intersectRow + 1;
				}
	  			grid[holeRow][vBorderStart].setCellType(CellType.PLAIN);
	  			break;
	  			
		default:if(intersectColumn - aUpperCornerColumn - 2 > 0){
					holeColumn = aUpperCornerColumn + 1 + bordersRng.nextInt(intersectColumn - aUpperCornerColumn - 2);
				}
				else{
					holeColumn = aUpperCornerColumn + 1;
				}
				grid[hBorderStart][holeColumn].setCellType(CellType.PLAIN);
		
				if(aLowerCornerColumn - intersectColumn - 2 > 0){
					holeColumn = intersectColumn + 1 + bordersRng.nextInt(aLowerCornerColumn - intersectColumn - 2);
				}
				else{
					holeColumn = intersectColumn + 1;
				}
				grid[hBorderStart][holeColumn].setCellType(CellType.PLAIN);
				
				if(intersectRow - aUpperCornerRow - 2 > 0){
					holeRow = aUpperCornerRow + 1 + bordersRng.nextInt(intersectRow - aUpperCornerRow - 2);
				}
				else{
					holeRow = aUpperCornerRow + 1;
				}
				grid[holeRow][vBorderStart].setCellType(CellType.PLAIN);
		
				if(aLowerCornerRow - intersectRow - 2 > 0){
					holeRow = intersectRow + 1 + bordersRng.nextInt(aLowerCornerRow - intersectRow - 2);
				}
	else{
		holeRow = intersectRow + 1;
		}
		grid[holeRow][vBorderStart].setCellType(CellType.PLAIN);
		break;
		
		}
		
		splitMap(aUpperCornerRow, aUpperCornerColumn, intersectRow, intersectColumn);
		splitMap(intersectRow, intersectColumn,aLowerCornerRow,aLowerCornerColumn);
		splitMap(aUpperCornerRow, intersectColumn,intersectRow,aLowerCornerColumn);
		splitMap(intersectRow, aUpperCornerColumn,aLowerCornerRow,intersectColumn);
	}
	
	/**
	 * Place components.
	 */
	private void placeComponents(){
		ArrayList<QGridCell> plainCells = new ArrayList<QGridCell>();
		ArrayList<Integer> portalOrder = new ArrayList<Integer>(PORTALNUMBER*PORTALREACHABILITY);
		int binsNumber = 0;
		int index = 0;
		QGridCell currCell = null;
		Random componentsRng = new Random();
		
		//Listing of the free cells
		for(int i=0; i<QGrid.MAPHEIGHT ; i++){
			for(int j=0 ; j<QGrid.MAPWIDTH; j++){
				if(this.getCell(i, j).getCellType() == CellType.PLAIN){
					plainCells.add(this.getCell(i, j));
				}
			}
		}
		
		//Initializing the placement order for portals
		for(int i=0; i<PORTALREACHABILITY ; i++){
			for(int j=0 ; j<PORTALNUMBER; j++){
				portalOrder.add(new Integer(j));
			}
		}
		
		//Dividing the number of plain cells in bins of the same size, except for the last one
		binsNumber = plainCells.size() / BINSIZE;
		//Shuffling the order of plain cells and portal placement order to have a random placement of components.
		Collections.shuffle(plainCells);
		Collections.shuffle(portalOrder);
		
		/* Placement Rules:
		 * 
		 * 1st Bin : Agent
		 * 2nd to 10th Bin: Portals + Bonus/Malus
		 * 10th to (n-1)th : Bonus/Malus
		 * nth : Goal
		 * */
		for(index=0; index < binsNumber ; index++){
			if(index == 0){
				agentCell = plainCells.get(index*BINSIZE+componentsRng.nextInt(BINSIZE));
				agentCell.setCellType(CellType.AGENT);
				initAgentCell = agentCell;
			}
			else{
				do{
					currCell = plainCells.get(index*BINSIZE+componentsRng.nextInt(BINSIZE));
				}while(currCell.getCellType() != CellType.PLAIN);
				
				if(componentsRng.nextInt(binsNumber) < 0.60*binsNumber){
					currCell.setCellType(CellType.BONUS);
				}
				else{
					currCell.setCellType(CellType.MALUS);
				}
				
				do{
					currCell = plainCells.get(index*BINSIZE+componentsRng.nextInt(BINSIZE));
				}while(currCell.getCellType() != CellType.PLAIN);
				
				if(index >= 2 && index < 2 + PORTALNUMBER*PORTALREACHABILITY){
					switch(portalOrder.get(index-2)){
						case 0: currCell.setCellType(CellType.PORTAL1);
								this.portalReachableCells[0].addAll(currCell.getReachableCells());
								break;
						case 1: currCell.setCellType(CellType.PORTAL2);
								this.portalReachableCells[1].addAll(currCell.getReachableCells());
								break;
						case 2: currCell.setCellType(CellType.PORTAL3);
								this.portalReachableCells[2].addAll(currCell.getReachableCells());
								break;
						case 3: currCell.setCellType(CellType.PORTAL4);
								this.portalReachableCells[3].addAll(currCell.getReachableCells());
								break;
						default: break;
					}
				}
				
				if(index == binsNumber - 1){
					do{
						currCell = plainCells.get(index*BINSIZE+componentsRng.nextInt(BINSIZE));
					}while(currCell.getCellType() != CellType.PLAIN);
					endCell = currCell;
					endCell.setCellType(CellType.ENDPOINT);
				}
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see ai.GameGrid#getEndCell()
	 */
	public MapCell getEndCell() {
		return endCell;
	}

	/* (non-Javadoc)
	 * @see ai.GameGrid#getAgentCell()
	 */
	public MapCell getAgentCell() {		
		return agentCell;
	}

	/**
	 * Gets the inits the agent cell.
	 *
	 * @return the inits the agent cell
	 */
	public QGridCell getInitAgentCell() {
		return initAgentCell;
	}
	
	/**
	 * Gets the bonus cells.
	 *
	 * @return the bonus cells
	 */
	public ArrayList<MapCell> getBonusCells() {
		return BonusCells;
	}

	/**
	 * Reset bonus.
	 */
	public void resetBonus()
	{
		Iterator<MapCell> iter = BonusCells.iterator();
		while(iter.hasNext())
		{
			iter.next().setCellType(CellType.BONUS);
		}
		BonusCells.clear();
	}
	
	/**
	 * Reset agent.
	 */
	public void resetAgent() {
		moveAgent(initAgentCell.getRowIndex(), initAgentCell.getColumnIndex());
	}
	
	/**
	 * Move agent.
	 *
	 * @param aRowIndex the a row index
	 * @param aColumnIndex the a column index
	 */
	public void moveAgent(int aRowIndex, int aColumnIndex){
		agentCell.setCellType(lastCellType);
		agentCell = getCell(aRowIndex, aColumnIndex);
		lastCellType = agentCell.getCellType();
		switch(agentCell.getCellType()){
			case BONUS:	
					lastCellType = CellType.PLAIN;
					BonusCells.add(agentCell);
					break;
			case ENDPOINT: break;
			case WALL:  break;
			case MALUS:
					break;
			default:  break;
		
		}
		agentCell.setCellType(CellType.AGENT);
	}
}
