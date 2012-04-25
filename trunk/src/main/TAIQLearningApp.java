package main;

import gui.MapGUI;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JFrame;

public class TAIQLearningApp {
	
	private MapGUI mapGUI;
	private JFrame appWindow;
	private Logger appLogger;
	
	public TAIQLearningApp() {
		super();
		this.appLogger = Logger.getLogger("src.appLogger");
		this.appWindow = new JFrame();
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

}
