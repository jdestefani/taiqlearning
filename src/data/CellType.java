package data;

public enum CellType {
	PLAIN,
	BONUS,
	WALL,
	MALUS,
	STARTPOINT,
	ENDPOINT,
	AGENT,
	PORTAL1,
	PORTAL2,
	PORTAL3,
	PORTAL4;
	
	public static double PLAINREWARD = -1.0;
	public static double BONUSREWARD = -1.0;
	public static double MALUSREWARD = -10.0;
	public static double WALLREWARD = -1000.0;
	public static double ENDPOINTREWARD = 1000.0;
}
