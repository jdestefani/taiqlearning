package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Logger;

import main.TAIQLearningApp;
import data.CellType;
import data.QGrid;
import data.QGridCell;

public class GridFileHandler {

	private TAIQLearningApp mainApp;
	
	public static String MAPPATH = new String("");
	public static String cellSeparator = new String(" ");
	public static String valueSeparator = new String(",");
	public static String lineSeparator = System.getProperty("line.separator");
	
	public GridFileHandler(TAIQLearningApp mainApp) {
		super();
		this.mainApp = mainApp;
	}
	
	public static QGridCell[][] readMapFromFile(String filename){
		File mapFile = new File(filename);
		Scanner lineScanner = null;
		try {
			lineScanner = new Scanner(mapFile);
			lineScanner.useDelimiter(lineSeparator);
		} catch (FileNotFoundException e) {
			Logger.getLogger("src.appLogger").severe("File" + filename + "not found!");
			e.printStackTrace();
		}
		Scanner cellScanner = null;
		Scanner valueScanner = null;
		QGridCell gridMap[][] = new QGridCell[QGrid.MAPHEIGHT][QGrid.MAPWIDTH];
		int i = 0;
		int j = 0;
		double currReward;
		CellType currType;
		
		try{
		while(lineScanner.hasNext()){
			cellScanner = new Scanner(lineScanner.next());
			cellScanner.useDelimiter(cellSeparator);
			
			j=0;
			while(cellScanner.hasNext()){
			valueScanner = new Scanner(cellScanner.next());
			valueScanner.useDelimiter(valueSeparator);
			
			String cellTypeString = valueScanner.next();
			System.out.println(cellTypeString);
			
			if(cellTypeString.length() >2){
				Logger.getLogger("src.AppLogger").severe("Cell["+i+"]["+j+"]: Error while reading cell type");
				return null;
			}
			
			switch(cellTypeString.charAt(0)){
			case 'A':currType = CellType.AGENT;
					 break;
			case 'B':currType = CellType.BONUS;
					 break;
			case 'E':currType = CellType.ENDPOINT;
					 break;
			case 'W':currType = CellType.WALL;
					 break;
			case 'F':currType = CellType.PLAIN;
					 break;
			case 'P':switch(cellTypeString.charAt(1)){
					  		case '1':currType = CellType.PORTAL1;
					  				 break;
					  		case '2':currType = CellType.PORTAL2;
			  				 	     break;
					  		case '3':currType = CellType.PORTAL3;
			  				 		 break;
					  		case '4':currType = CellType.PORTAL4;
			  				 		 break;
					  		default :Logger.getLogger("src.AppLogger").severe("Cell["+i+"]["+j+"]: Error while reading cell type");
					  				 return null;
					 }
					 break;
			default :Logger.getLogger("src.AppLogger").severe("Cell["+i+"]["+j+"]: Error while reading cell type");
					 return null;
			}
			
			String rewardString = valueScanner.next();
			
			try{
			currReward = Double.parseDouble(rewardString);
			}
			catch(NumberFormatException e){
				Logger.getLogger("src.AppLogger").severe("Cell["+i+"]["+j+"]: Error while reading cell reward");
				return null;
			}
			
			gridMap[i][j] = new QGridCell(currType, currReward);
			j++;
			}
		i++;
		}
		}
		finally{
			lineScanner.close();
		}
		
		return gridMap;
	}
	
	public boolean writeMapToFile(String filename){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filename);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			Logger.getLogger("src.appLogger").info("File" + filename + "not found!");
			return false;
		} 
		OutputStreamWriter out;
		try {
			out = new OutputStreamWriter(fos, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			Logger.getLogger("src.appLogger").info("Unsupported Encoding!");
			e1.printStackTrace();
			return false;
		}
		String currSymb = null;
		Double currReward = null;
		QGridCell currCell = null;
		
		try{
			for(int i=0; i<QGrid.MAPHEIGHT ; i++){
				for(int j=0 ; j<QGrid.MAPWIDTH; j++){
					
					try{
						currCell = this.mainApp.getqGridMap().getCell(i, j);
					}catch (IndexOutOfBoundsException e) {
						Logger.getLogger("src.appLogger").severe(e.getMessage());
						return false;
					}
					
					switch(currCell.getCellType()){
					case AGENT:currSymb = new String("A");
						   	   break;
					case BONUS:currSymb = new String("B");
						   	   break;
					case ENDPOINT:currSymb = new String("E");
							   break;
					case WALL:currSymb = new String("W");
						  	   break;
					case PLAIN:currSymb = new String("F");
						   	   break;
					case PORTAL1:currSymb = new String("P1");
				   	   		   break;
					case PORTAL2:currSymb = new String("P2");
		   	   		   		   break;
					case PORTAL3:currSymb = new String("P3");
		   	   		   		   break;
					case PORTAL4:currSymb = new String("P4");
		   	   		   		   break;
					default:break;
				}
					currReward = currCell.getCellReward();
					try {
						out.append(new String(currSymb+valueSeparator+currReward.toString()+cellSeparator));
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				}
				try {
					out.append(lineSeparator);
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				Logger.getLogger("src.appLogger").info("Error in closing file!");
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
}
