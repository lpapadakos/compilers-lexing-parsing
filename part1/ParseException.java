public class ParseException extends Exception {
	public ParseException(String func) {
		super("Parse error @" + func);
	}
}
