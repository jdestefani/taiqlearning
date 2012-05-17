package data;

// TODO: Auto-generated Javadoc
/**
 * The Enum CellType.
 */
public enum CellType {
	
	/** The PLAIN. */
	PLAIN,
	
	/** The BONUS. */
	BONUS,
	
	/** The WALL. */
	WALL,
	
	/** The MALUS. */
	MALUS,
	
	/** The STARTPOINT. */
	STARTPOINT,
	
	/** The ENDPOINT. */
	ENDPOINT,
	
	/** The AGENT. */
	AGENT,
	
	/** The PORTA l1. */
	PORTAL1,
	
	/** The PORTA l2. */
	PORTAL2,
	
	/** The PORTA l3. */
	PORTAL3,
	
	/** The PORTA l4. */
	PORTAL4;
	
	/** The PLAINREWARD. */
	public static double PLAINREWARD = -1.0;
	
	/** The BONUSREWARD. */
	public static double BONUSREWARD = 0.0;
	
	/** The MALUSREWARD. */
	public static double MALUSREWARD = -10.0;
	
	/** The WALLREWARD. */
	public static double WALLREWARD = -1000.0;
	
	/** The ENDPOINTREWARD. */
	public static double ENDPOINTREWARD = 1000.0;
}
