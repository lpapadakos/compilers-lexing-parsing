import java.io.IOException;

class Main {
	public static void main(String[] args) {
		try {
			System.out.println((new Calculator(System.in)).evaluate());
		} catch (Exception e) {
			System.err.println("parse error");

			// More detailed message with index where the error was found
			//e.printStackTrace();
		}
	}
}
