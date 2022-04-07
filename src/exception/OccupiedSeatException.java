package exception;

public class OccupiedSeatException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public OccupiedSeatException(String m) {
		super(m);
	}
}
