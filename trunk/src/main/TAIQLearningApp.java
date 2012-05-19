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

import ai.AStarGrid;
import ai.AStarThread;
import ai.QGrid;
import ai.QLearning;
import ai.UnreachableEndException;

// TODO: Auto-generated Javadoc
/**
 * The Class TAIQLearningApp.
 */
public class TAIQLearningApp {
	
	/** The map gui. */
	private MapGUI mapGUI;
	
	/** The file handler. */
	private GridFileHandler fileHandler;
	
	/** The app window. */
	private JFrame appWindow;
	
	/** The app logger. */
	private Logger appLogger;
	
	/** The q grid map. */
	private QGrid qGridMap;
	
	/** The a star grid map. */
	private AStarThread aStarPathfinder;
	
	/** The a qlearning. */
	private QLearning aQlearning;
	
	/** The Constant IMAGEPATH. */
	private final static String IMAGEPATH = new String("../img/");
	
	/** The Constant LOGGERNAME. */
	public final static String LOGGERNAME = new String("appLogger");
	
	/**
	 * Instantiates a new tAIQ learning app.
	 */
	public TAIQLearningApp() {
		super();
		
		boolean isEndReachable = false;
		
		appLogger = Logger.getLogger(LOGGERNAME);
		setupLogger();
		appWindow = new JFrame();
		try {
			qGridMap = new QGrid();
			aStarPathfinder = new AStarThread(this);
			aQlearning = new QLearning(this, this.qGridMap);
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
	

	/**
	 * Gets the a qlearning.
	 *
	 * @return the a qlearning
	 */
	public QLearning getaQlearning() {
		return aQlearning;
	}



	public AStarThread getaStarPathfinder() {
		return aStarPathfinder;
	}


	public void setaStarPathfinder(AStarThread aStarPathfinder) {
		this.aStarPathfinder = aStarPathfinder;
	}


	/**
	 * Gets the file handler.
	 *
	 * @return the file handler
	 */
	public GridFileHandler getFileHandler() {
		return fileHandler;
	}



	/**
	 * Sets the file handler.
	 *
	 * @param fileHandler the new file handler
	 */
	public void setFileHandler(GridFileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}



	/**
	 * Gets the imagepath.
	 *
	 * @return the imagepath
	 */
	public static String getImagepath() {
		return IMAGEPATH;
	}

	/**
	 * Gets the map gui.
	 *
	 * @return the map gui
	 */
	public MapGUI getMapGUI() {
		return mapGUI;
	}

	/**
	 * Sets the map gui.
	 *
	 * @param mapGUI the new map gui
	 */
	public void setMapGUI(MapGUI mapGUI) {
		this.mapGUI = mapGUI;
	}

	/**
	 * Gets the app window.
	 *
	 * @return the app window
	 */
	public JFrame getAppWindow() {
		return appWindow;
	}

	/**
	 * Sets the app window.
	 *
	 * @param appWindow the new app window
	 */
	public void setAppWindow(JFrame appWindow) {
		this.appWindow = appWindow;
	}

	/**
	 * Gets the app logger.
	 *
	 * @return the app logger
	 */
	public Logger getAppLogger() {
		return appLogger;
	}

	/**
	 * Sets the app logger.
	 *
	 * @param appLogger the new app logger
	 */
	public void setAppLogger(Logger appLogger) {
		this.appLogger = appLogger;
	}


	/**
	 * Gets the q grid map.
	 *
	 * @return the q grid map
	 */
	public QGrid getqGridMap() {
		return qGridMap;
	}


	/**
	 * Sets the q grid map.
	 *
	 * @param qGridMap the new q grid map
	 */
	public void setqGridMap(QGrid qGridMap) {
		this.qGridMap = qGridMap;
	}


	/**
	 * Gets the mapheight.
	 *
	 * @return the mapheight
	 */
	public static int getMapheight() {
		return QGrid.MAPHEIGHT;
	}

	/**
	 * Gets the mapwidth.
	 *
	 * @return the mapwidth
	 */
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
	
	/**
	 * Update current window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 * @param contentPane the content pane
	 */
	public void updateCurrentWindow(String title,int width, int height, JPanel contentPane){
		appWindow.setTitle("TAIQLearning - "+ title);
		appWindow.setPreferredSize(new Dimension(width, height));
		//appWindow.setResizable(false);
        appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appWindow.setJMenuBar(mapGUI.getDisplayMenuBar());
        appWindow.setContentPane(contentPane);        
        appWindow.pack();
        appWindow.setVisible(true);
	}
	
	/**
	 * Import image.
	 *
	 * @param fileName the file name
	 * @return the image icon
	 */
	public static ImageIcon importImage(String fileName){
		return new ImageIcon(TAIQLearningApp.class.getResource(
							 IMAGEPATH+fileName));
	}
	
		
}
