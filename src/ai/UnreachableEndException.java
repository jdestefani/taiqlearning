package ai;

/**
 * The Class UnreachableEndException.
 */
public class UnreachableEndException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 399607920399638437L;

	/**
	 * Instantiates a new unreachable end exception.
	 */
	public UnreachableEndException() {
		super();
	}

	/**
	 * Instantiates a new unreachable end exception.
	 *
	 * @param arg0 the arg0
	 * @param arg1 the arg1
	 */
	public UnreachableEndException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Instantiates a new unreachable end exception.
	 *
	 * @param arg0 the arg0
	 */
	public UnreachableEndException(String arg0) {
		super(arg0);
	}

	/**
	 * Instantiates a new unreachable end exception.
	 *
	 * @param arg0 the arg0
	 */
	public UnreachableEndException(Throwable arg0) {
		super(arg0);
	}

	
	
}
