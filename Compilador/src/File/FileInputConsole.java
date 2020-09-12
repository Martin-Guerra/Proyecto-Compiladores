package File;

import java.io.File;
import java.util.Scanner;

public class FileInputConsole {

	
	
		public static void main(String[] args) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter a file name: ");
			System.out.flush();
			String filename = scanner.nextLine();
			File file = new File(filename);
				if (0 < args.length) {
					 filename = args[0];
					 file = new File(filename);
				}
		}
}
