public class ParseException extends Exception {
	public ParseException(int lookahead) {
		super("Did not expect '" + (char) lookahead + "' (" + lookahead +")");
	}
}
