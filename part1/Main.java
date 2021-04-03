import java.io.IOException;

class Main {
	public static void main(String[] args) {
		try {
			System.out.println("Insert valid string for calculation:");
			System.out.println("\n==============================\nResult: " + (new Calculator(System.in)).evaluate());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
