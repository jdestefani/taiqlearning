package main;

import gui.MapGUI;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import data.QGridCell;

public class TAIQLearningApp {
	
	private MapGUI mapGUI;
	private JFrame appWindow;
	private Logger appLogger;
	private QGridCell qGridMap[][];
	public static final int MAPHEIGHT = 20;
	public static final int MAPWIDTH = 30;
	
	public TAIQLearningApp() {
		super();
		this.appLogger = Logger.getLogger("src.appLogger");
		this.appWindow = new JFrame();
		this.appWindow.addWindowListener(new AppWindowListener());
		this.qGridMap = new QGridCell[MAPHEIGHT][MAPWIDTH];
		for(int i=0; i<TAIQLearningApp.MAPHEIGHT ; i++){
			for(int j=0 ; j<TAIQLearningApp.MAPWIDTH; j++){
				this.qGridMap[i][j] = new QGridCell();
			}
		}
		this.mapGUI = new MapGUI(this);
		this.updateCurrentWindow("Grid World", 640, 500, this.mapGUI.getContentPane());
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

	public QGridCell[][] getqGridMap() {
		return qGridMap;
	}

	public void setqGridMap(QGridCell[][] qGridMap) {
		this.qGridMap = qGridMap;
	}

	public static int getMapheight() {
		return MAPHEIGHT;
	}

	public static int getMapwidth() {
		return MAPWIDTH;
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
		Logger.getLogger("src.appLogger").addHandler(fileHandler);
		Logger.getLogger("src.appLogger").setLevel(Level.INFO);
		
		SimpleFormatter formatter = new SimpleFormatter();
	    fileHandler.setFormatter(formatter);

	}
	
	public void updateCurrentWindow(String title,int width, int height, JPanel contentPane){
		appWindow.setTitle("TAIQLearning - "+ title);
		appWindow.setPreferredSize(new Dimension(width, height));
		appWindow.setResizable(false);
        appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appWindow.setContentPane(contentPane);
        appWindow.pack();
        appWindow.setVisible(true);
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
