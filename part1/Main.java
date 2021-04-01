import java.io.IOException;

class Main {
	public static void main(String[] args) {
		try {
			System.out.println("Insert valid string for calculation:");
			System.out.println("Result: " + (new Calculator(System.in)).evaluate());
		} catch (IOException | ParseError e) {
			System.err.println(e.getMessage());
		}
	}
}
