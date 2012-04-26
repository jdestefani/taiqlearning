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
import data.QGridCell;

public class GridFileHandler {

	private TAIQLearningApp mainApp;
	
	public static String MAPPATH = new String("");
	public static String cellSeparator = new String(" ");
	public static String valueSeparator = new String(",");
	
	public GridFileHandler(TAIQLearningApp mainApp) {
		super();
		this.mainApp = mainApp;
	}
	
	public static QGridCell[][] readMapFromFile(String filename){
		File mapFile = new File(filename);
		Scanner lineScanner = null;
		try {
			lineScanner = new Scanner(mapFile);
		} catch (FileNotFoundException e) {
			Logger.getLogger("src.appLogger").severe("File" + filename + "not found!");
			e.printStackTrace();
		}
		Scanner cellScanner = null;
		Scanner valueScanner = null;
		QGridCell gridMap[][] = new QGridCell[TAIQLearningApp.MAPHEIGHT][TAIQLearningApp.MAPWIDTH];
		int i = 0;
		int j = 0;
		double currReward;
		CellType currType;
		
		try{
		while(lineScanner.hasNextLine()){
			cellScanner = new Scanner(lineScanner.nextLine());
			cellScanner.useDelimiter(cellSeparator);
			
			while(cellScanner.hasNext()){
			valueScanner = new Scanner(cellScanner.next());
			valueScanner.useDelimiter(valueSeparator);
			
			String cellTypeString = valueScanner.next();
			
			if(cellTypeString.length() != 1){
				Logger.getLogger("src.AppLogger").severe("Error while reading cell type");
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
					 
			default :Logger.getLogger("src.AppLogger").severe("Error while reading cell type");
					 return null;
			}
			
			String rewardString = valueScanner.next();
			
			try{
			currReward = Double.parseDouble(rewardString);
			}
			catch(NumberFormatException e){
				Logger.getLogger("src.AppLogger").severe("Error while reading cell reward");
				return null;
			}
			
			gridMap[i][j] = new QGridCell(currType, currReward);
			i++;
			}
		j++;
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
		char currSymb = 0;
		Double currReward = null;
		
		for(int i=0; i<TAIQLearningApp.MAPHEIGHT ; i++){
			for(int j=0 ; j<TAIQLearningApp.MAPWIDTH; j++){
				switch(this.mainApp.getqGridMap()[i][j].getCellType()){
				case AGENT:currSymb = 'A';
						   break;
				case BONUS:currSymb = 'B';
						   break;
				case ENDPOINT:currSymb = 'E';
							  break;
				case WALL:currSymb = 'W';
						  break;
				default:break;
				}
				currReward = this.mainApp.getqGridMap()[i][j].getCellReward();
				try {
					out.append(new String(currSymb+valueSeparator+currReward.toString()+cellSeparator));
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			try {
				out.append("\n");
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
}
