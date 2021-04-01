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
			//do {
				lookahead = in.read();
			//} while (lookahead != -1 && Character.isWhitespace(lookahead));
		} else
			throw new ParseException("expect()");

		System.out.println("Read '" + (char) lookahead + "' (" + lookahead + ")");
	}

	public int evaluate() throws IOException, ParseException {
		int value = exp();

		//if (lookahead != -1 || lookahead != '\n')
		//	throw new ParseException("evaluate()");

		return value;
	    }

	private int exp() throws IOException, ParseException {
		int a = term();
		return exp2(a);
	}

	private int exp2(int a) throws IOException, ParseException {
		switch (lookahead) {
			case '+':
			//TODO: Maybe exp2(a+b)
				expect('+');
				a += term();
				return exp2(a);
			case '-':
				expect('-');
				a -= term();
				return exp2(a);
			default:
				return a;
		}

		//throw new ParseException();
	}

	private int term() throws IOException, ParseException {
		int a = factor();
		return term2(a);
	}

	private int term2(int a) throws IOException, ParseException {
		switch (lookahead) {
			case '*':
				expect('*');
				expect('*');
				a = (int) Math.pow(a, factor());
				return term2(a);
			default:
				return a;
		}

		//throw new ParseException();
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

		throw new ParseException("factor()");
	}

	// TODO: Maybe recurse?
	private int num() throws IOException, ParseException {
		StringBuffer str = new StringBuffer();

		while (Character.isDigit(lookahead)) {
			str.append((char) lookahead);
			expect(lookahead);
		}

		int val = Integer.parseInt(str.toString());
		System.out.println("num: " + val);

		return val;
	}
}
