/* See part1/README.txt for the explanation behind the grammar */

import java.io.IOException;
import java.io.InputStream;
import java.lang.Math;

//TODO: Fix exceptions

class Calculator {
	private final InputStream in;
	private int lookahead;

	public Calculator(InputStream in) throws IOException {
		this.in = in;

		lookahead = in.read();
		System.out.println("Read '" + (char) lookahead + "' (" + lookahead + ")");
	}

	private void expect(int symbol) throws IOException, ParseException {
		if (lookahead == symbol) {
			// TODO: Skip whitespace
			// TODO: Work with EOF
			//do {
				lookahead = in.read();
			//} while (lookahead != -1 && Character.isWhitespace(lookahead));
		} else
			throw new ParseException(lookahead);

		System.out.println("Read '" + (char) lookahead + "' (" + lookahead + ")");
	}

	public int evaluate() throws IOException, ParseException {
		//int value = exp();

		//if (lookahead != -1 || lookahead != '\n')
		//	throw new ParseException("evaluate()");

		return exp();
	    }

	private int exp() throws IOException, ParseException {
		int t = term();
		return exp2(t);
	}

	private int exp2(int t) throws IOException, ParseException {
		switch (lookahead) {
			case '+':
				expect('+');
				t += term();

				return exp2(t);
			case '-':
				expect('-');
				t -= term();

				return exp2(t);
			default:
				return t;
		}
	}

	private int term() throws IOException, ParseException {
		int f = factor();
		return term2(f);
	}

	private int term2(int f) throws IOException, ParseException {
		if (lookahead == '*') {
			expect('*');
			expect('*');
			f = (int) Math.pow(f, factor());

			return term2(f);
		} else {
			return f;
		}
	}

	private int factor() throws IOException, ParseException {
		if (lookahead == '(') {
			expect('(');
			int e = exp();
			expect(')');

			return e;
		} else if (Character.isDigit(lookahead)) {
			return num();
		}

		throw new ParseException(lookahead);
	}

	// TODO: Maybe recursion?
	private int num() throws IOException, ParseException {
		StringBuffer str = new StringBuffer();

		while (Character.isDigit(lookahead)) {
			str.append((char) lookahead);
			expect(lookahead);
		}

		int val = Integer.parseInt(str.toString());
		System.out.println("Number: " + val);

		return val;
	}
}
