package main;

import gui.MapGUI;
import io.GridFileHandler;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ai.AStarPathFinder;
import ai.UnreachableEndException;
import data.QGrid;

public class TAIQLearningApp {
	
	private MapGUI mapGUI;
	private GridFileHandler fileHandler;
	private JFrame appWindow;
	private Logger appLogger;
	private QGrid qGridMap;
	private AStarPathFinder aStarGridMap;
	private final static String IMAGEPATH = new String("../img/");
	public final static String LOGGERNAME = new String("appLogger");
	
	public TAIQLearningApp() {
		super();
		
		boolean isEndReachable = false;
		
		appLogger = Logger.getLogger(LOGGERNAME);
		setupLogger();
		appWindow = new JFrame();
		appWindow.addWindowListener(new AppWindowListener());
		try {
			qGridMap = new QGrid();
			aStarGridMap = new AStarPathFinder(this,this.qGridMap);
			isEndReachable = true;
		} catch (UnreachableEndException e) {
			isEndReachable = false;
			JOptionPane.showMessageDialog(appWindow,"The goal is unreachable on the generated map.\nPlease regenerate the map.","Warning",JOptionPane.WARNING_MESSAGE);
		}
		mapGUI = new MapGUI(this,isEndReachable);
		fileHandler = new GridFileHandler(this);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		updateCurrentWindow("Grid World", screenSize.width, screenSize.height, this.mapGUI.getContentPane());
	}
	

	public AStarPathFinder getaStarGridMap() {
		return aStarGridMap;
	}



	public void setaStarGridMap(AStarPathFinder aStarGridMap) {
		this.aStarGridMap = aStarGridMap;
	}



	public GridFileHandler getFileHandler() {
		return fileHandler;
	}



	public void setFileHandler(GridFileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}



	public static String getImagepath() {
		return IMAGEPATH;
	}

	public MapGUI getMapGUI() {
		return mapGUI;
	}

	public void setMapGUI(MapGUI mapGUI) {
		this.mapGUI = mapGUI;
	}

	public JFrame getAppWindow() {
		return appWindow;
	}

	public void setAppWindow(JFrame appWindow) {
		this.appWindow = appWindow;
	}

	public Logger getAppLogger() {
		return appLogger;
	}

	public void setAppLogger(Logger appLogger) {
		this.appLogger = appLogger;
	}


	public QGrid getqGridMap() {
		return qGridMap;
	}


	public void setqGridMap(QGrid qGridMap) {
		this.qGridMap = qGridMap;
	}


	public static int getMapheight() {
		return QGrid.MAPHEIGHT;
	}

	public static int getMapwidth() {
		return QGrid.MAPWIDTH;
	}

	/**
	 * Inizializza il logger e gli associa il file sui registrare le informazioni.
	 */
	public void setupLogger(){
		Handler fileHandler = null;
		try {
			fileHandler = new FileHandler("./log/appLog.log");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.getLogger(LOGGERNAME).addHandler(fileHandler);
		Logger.getLogger(LOGGERNAME).setLevel(Level.INFO);
		
		SimpleFormatter formatter = new SimpleFormatter();
	    fileHandler.setFormatter(formatter);

	}
	
	public void updateCurrentWindow(String title,int width, int height, JPanel contentPane){
		appWindow.setTitle("TAIQLearning - "+ title);
		appWindow.setPreferredSize(new Dimension(width, height));
		//appWindow.setResizable(false);
        appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appWindow.setContentPane(contentPane);
        appWindow.pack();
        appWindow.setVisible(true);
	}
	
	public static ImageIcon importImage(String fileName){
		return new ImageIcon(TAIQLearningApp.class.getResource(
							 IMAGEPATH+fileName));
	}
	
	public void printOnConsoleAndLog(String aMsg){
		Logger.getLogger(TAIQLearningApp.LOGGERNAME).info(aMsg);
		this.mapGUI.getInfoConsole().append(aMsg+System.getProperty("line.separator"));
	}
	
	private class AppWindowListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
