public class ParseException extends Exception {
	public ParseException(int lookahead, int index) {
		super("Did not expect '" + (char) lookahead + "' (" + lookahead + ") at index " + index);
	}
}
