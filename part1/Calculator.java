/* See part1/README.txt for the explanation behind the grammar */

import java.io.IOException;
import java.io.InputStream;

class Calculator {
	private final InputStream in;
	private int lookahead, index = 0;

	public Calculator(InputStream in) throws IOException {
		this.in = in;
		lookahead = in.read();

		//System.out.println("Lookahead '" + (char) lookahead + "' (" + lookahead + ")");
	}

	private void verify(int symbol) throws IOException, ParseException {
		if (lookahead == symbol) {
			//System.out.print("Verify '" + (char) symbol + "' (" + symbol + ")");

			// FIXME: Work with EOF is tricky, on first Ctrl-D read() blocks instead of returning -1
			lookahead = in.read();
			index++;

			//System.out.println(" | Lookahead '" + (char) lookahead + "' (" + lookahead + ")");
		} else {
			throw new ParseException(lookahead, index);
		}
	}

	public int evaluate() throws IOException, ParseException {
		int value = exp();

		if (lookahead != '\n' && lookahead != -1)
			throw new ParseException(lookahead, index);

		return value;
	}

	// Grammar time

	private int exp() throws IOException, ParseException {
		return exp2(term());
	}

	private int exp2(int t) throws IOException, ParseException {
		switch (lookahead) {
			case '+':
				verify('+');
				return exp2(t + term());
			case '-':
				verify('-');
				return exp2(t - term());
			case ')':
			case '\n':
			case -1:
				return t;
		}

		throw new ParseException(lookahead, index);
	}

	private int term() throws IOException, ParseException {
		return term2(factor());
	}

	private int term2(int f) throws IOException, ParseException {
		switch (lookahead) {
			case '*':
				verify('*');
				verify('*');

				return term2((int) Math.pow(f, factor()));
			case '+':
			case '-':
			case ')':
			case '\n':
			case -1:
				return f;
		}

		throw new ParseException(lookahead, index);
	}

	private int factor() throws IOException, ParseException {
		if (lookahead == '(') {
			verify('(');
			int e = exp();
			verify(')');

			return e;
		} else if (Character.isDigit(lookahead)) {
			return num();
		}

		throw new ParseException(lookahead, index);
	}

	private int num() throws IOException, ParseException {
		if (Character.isDigit(lookahead)) {
			// Consume '0'..'9'
			StringBuffer str = new StringBuffer(1);
			str.append((char) lookahead);
			verify(lookahead);

			digit(str);

			int value = Integer.parseInt(str.toString());
			//System.out.println("Number: " + value);

			return value;
		}

		throw new ParseException(lookahead, index);
	}

	private void digit(StringBuffer s) throws IOException, ParseException {
		if (Character.isDigit(lookahead)) {
			s.append(num());
			return;
		} else if (lookahead == '+' || lookahead == '-' ||
		           lookahead == '*' || lookahead == ')' ||
		           lookahead == '\n' || lookahead == -1) {
			return;
		}

		throw new ParseException(lookahead, index);
	}
}
