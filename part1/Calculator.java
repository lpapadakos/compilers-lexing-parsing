/* See part1/README.txt for the explanation behind the grammar */

import java.io.IOException;
import java.io.InputStream;
import java.lang.Math;

//TODO: Fix exceptions

class Calculator {
	private final InputStream in;
	private int lookahead, index = 0;

	public Calculator(InputStream in) throws IOException {
		this.in = in;

		lookahead = in.read();
		System.out.println("Read '" + (char) lookahead + "' (" + lookahead + ")");
	}

	private void verify(int symbol) throws IOException, ParseException {
		System.out.println("------------------\nVerify '" + (char) symbol + "' (" + symbol + ")");
		if (lookahead == symbol) {
			// TODO: Skip whitespace
			// TODO: Work with EOF
			//do {
				lookahead = in.read();
				index++;
				System.out.println("Read '" + (char) lookahead + "' (" + lookahead + ")\n------------------");
			//} while (Character.isWhitespace(lookahead));
		} else {
			throw new ParseException(lookahead, index);
		}
	}

	public int evaluate() throws IOException, ParseException {
		int value = exp();

		//if (lookahead != -1 || lookahead != '\n')
		//	throw new ParseException(lookahead);

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

	// TODO: Maybe recursion?
	private int num() throws IOException, ParseException {
		StringBuffer str = new StringBuffer();

		while (Character.isDigit(lookahead)) {
			str.append((char) lookahead);
			System.out.println("------------------\nAppend (" + lookahead + ")");
			verify(lookahead);
		}

		int value = Integer.parseInt(str.toString());
		System.out.println("Number: " + value + "\n------------------");

		return value;
	}
}
